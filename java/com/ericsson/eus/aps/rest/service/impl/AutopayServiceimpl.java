package com.ericsson.eus.aps.rest.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.dao.IAccountAutopayDtlsDao;
import com.ericsson.eus.aps.dao.IAutopayDao;
import com.ericsson.eus.aps.dao.IAutopayHistoryDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.dao.impl.AccountAutopayDtlsDaoImpl;
import com.ericsson.eus.aps.dao.impl.VuAccountSubscriberDtlDaoImpl;
import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.dao.model.AutoPayTransactions;
import com.ericsson.eus.aps.dao.model.VuAccountSubscriberDtl;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;
import com.ericsson.eus.aps.dao.model.VuRatePlan;
import com.ericsson.eus.aps.rest.common.RESTConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.rest.service.AutoPayDiscountService;
import com.ericsson.eus.aps.rest.service.AutopayService;
import com.ericsson.eus.aps.service.data.beans.ApsHistory;
import com.ericsson.eus.aps.service.data.beans.AutoPayDetailBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountCalculationBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayEnrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayEnrollResponseBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayHistoryRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayHistoryResponseBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayNextCycleDateRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayNextCycleDateResponseBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUnenrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUnenrollResponseBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateResponseBean;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;
import com.ericsson.eus.aps.service.util.DateUtils;

/**
 *
 * FILE_NAME: AutopayServiceimpl.java
 *
 * MODULE DESCRIPTION:  US84027
 *
 * @author ekxrxax, Date: Sep 14, 2020 3:46:11 PM 2020
 *
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
@Service
public class AutopayServiceimpl implements AutopayService{

	private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().
																		parseCaseInsensitive().
																		appendPattern(DATE_TIME_FORMAT).
																		toFormatter(Locale.ENGLISH);
	private static final String RATE_PLAN_FOUND_STR = "Rate Plan  found :: {}";
	private static final int RECURRING_TYPE_7 = 7;
	private static final int RECURRING_TYPE_6 = 6;
	private static final int RECURRING_TYPE_PAY_AS_GO = 8;
	private static final int RECURRING_TYPE_MONTHLY = 9;
	private static final String AUTOPAY_ALREADY_DISABLED = " Autopay already disabled for this MSISDN";
	private static final String AUTOPAY_ALREADY_ENABLED = " Autopay already enabled for this MSISDN";
	private static final String DATABASE_ERROR = "Database error: ";
	private static final String MSISDN_IS_NOT_AVAILABLE_IN_AUTPAY = "MSISDN is not enrolled in Autopay";
	private static final String MSISDN_DOES_NOT_EXIST = "MSISDN does not exist";
	private static final String MULTILINE ="MULTILINE";
	private static final String MEMBER   =  "MEMBER";
	private final Logger logger = LogManager.getLogger(AutopayServiceimpl.class);
	@Autowired
	private IAccountAutopayDtlsDao accountAutopayDtlsDaoImpl;
	@Autowired
	private VuAccountSubscriberDtlDaoImpl vuAccountSubscriberDtlDaoImpl;
	@Autowired
	private IDiscountDao discountDao;
	@Autowired
	private IAutopayDao autopayDao;
	@Autowired IVuRatePlanDao ratePlanDao;
	@Autowired
	private IAutopayHistoryDao autopayHistoryDao;

	@Autowired
	private AutoPayDiscountService autopayDiscountService;

	/**
	 * User: eihsnad , Date: Sep 30, 2020 1:33:57 PM 2020
	 *
	 * Purpose: service layer for performing autopay unenroll
	 *
	 * US/D/F Number: US84042
	 *
	 * Return Type: void
	 *
	 * @param msisdn
	 * @param comment
	 * @throws AutopayServiceException
	 */
	public BaseResponse autopayUnenroll(AutoPayUnenrollRequestBean request)
	{
		AutoPayUnenrollResponseBean response = null;
		try {
			//F80979 : Logic added for AutoPayUnEnroll
			logger.debug("Inside autopayUnEnroll :: {}" , request);
			VuAccountSubscriberDtl subscriber = getSubscriberDtl(request);
			logger.debug("Selected subscriber :: {}" , subscriber);
			if(null != subscriber) {
				String accountId = subscriber.getAccountId();
				request.setAccountId(Long.valueOf(accountId));
				AccountAutopayDtls autopayDtls = checkUnEnrollAllowed(accountId, request.isHardUnenroll());
				if (request.isHardUnenroll()) {
					/*
					 * Flow for Hard-UnEnroll set status INACTIVE
					 */
					autopayDtls.setAutopayStatus(ApsConstants.INACTIVE_STATUS);
					autopayDao.autopayUnenroll(request, autopayDtls, true);
				}
				else {
					// Flow for Soft-UnEnroll
					autopayDtls.setAutopayStatus(ApsConstants.SOFT_INACTIVE_STATUS);
					autopayDao.autopayUnenroll(request, autopayDtls, false);
				}

				response = new AutoPayUnenrollResponseBean();
				response.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
				response.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);

				AutoPayDetailBean detailBean = new AutoPayDetailBean();
				if(autopayDtls.getRecurringTopupCycleCDType()!=null) {
					detailBean.setRecurringtopupcyclecdType(Long.parseLong(autopayDtls.getRecurringTopupCycleCDType()));
				}
				response.setDetailBean(detailBean);

			}
			else {
				throw new AutopayServiceException(FaultGlobalCode.INVALID_MSISDN, MSISDN_DOES_NOT_EXIST);
			}
		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}
		return response;
	}

	/**
	 * User: eihsnad , Date: Dec 11, 2020 3:55:26 PM 2020
	 *
	 * Purpose: handle subscriber not found 5002
	 *
	 * US/D/F Number: US90619
	 *
	 * Return Type: void
	 *
	 * @param request
	 */
	private VuAccountSubscriberDtl getSubscriberDtl(AutoPayUnenrollRequestBean request) {
		try {
			return vuAccountSubscriberDtlDaoImpl.getAccountDtls(request.getMsisdn());
		} catch (AutopayDaoException ex) {
			return null;
		}
	}

	/**
	 * User: eihsnad , Date: Oct 8, 2020 11:27:09 AM 2020
	 *
	 * US/D/F Number:F1217
	 *
	 * Return Type: VuAccountAutopayDtls
	 *
	 * @param accountId
	 * @param hardUnenroll
	 * @return
	 * @throws AutopayServiceException
	 */
	private AccountAutopayDtls checkUnEnrollAllowed(String accountId, boolean hardUnenrollEnable) {
		AccountAutopayDtls autopayDtls = null;
		autopayDtls = accountAutopayDtlsDaoImpl.getAutoPayDtls(accountId);
		if (autopayDtls == null) {
			throw new AutopayServiceException(FaultGlobalCode.MANDATORY_FIELD_MISSING,
					MSISDN_IS_NOT_AVAILABLE_IN_AUTPAY);
		}
		/** Throw Exception is Status is already INACTIVE */
		if (autopayDtls.getAutopayStatus().equals(ApsConstants.INACTIVE_STATUS)) {
			throw new AutopayServiceException(FaultGlobalCode.AUTOPAY_ALREADY_DISABLED, AUTOPAY_ALREADY_DISABLED);
		}
		/**
		 * Throw Exception if status already SOFT-INACTIVE for soft-unenroll
		 **/
		if (!hardUnenrollEnable && autopayDtls.getAutopayStatus().equals(ApsConstants.SOFT_INACTIVE_STATUS)) {
			throw new AutopayServiceException(FaultGlobalCode.AUTOPAY_ALREADY_DISABLED, AUTOPAY_ALREADY_DISABLED);
		}

		return autopayDtls;
	}

	/**
	 * User: eiansrv , Date: Nov 18, 2020 9:24:35 PM 2020
	 *
	 *
	 * US/D/F Number: 84041
	 *
	 * Return Type: void
	 *
	 * @param accountId
	 * @return
	 * @throws AutopayServiceException
	 */
	private void checkAutopayEnrollAllowed(String accountId) {
		AccountAutopayDtls autopayDtls = accountAutopayDtlsDaoImpl.getAutoPayDtls(accountId);
		/** Throw Exception is Status is already ACTIVE */
		if (null != autopayDtls && autopayDtls.getAutopayStatus().equals(ApsConstants.ACTIVE)) {
			throw new AutopayServiceException(FaultGlobalCode.AUTOPAY_ALREADY_ENABLED, AUTOPAY_ALREADY_ENABLED);
		}
	}

	/**
	 * User: eiansrv , Date: Oct 01, 2020 1:05:10 PM 2020
	 *
	 * Purpose: service layer for performing autopay enroll
	 *
	 * US/D/F Number: US84041
	 *
	 * Return Type: void
	 *
	 * @param msisdn
	 * @param comment
	 * @throws AutopayServiceException
	 */
	public BaseResponse autopayEnroll(final AutoPayEnrollRequestBean request) {
		AutoPayEnrollResponseBean response = null;
		try {
			logger.debug("Inside autopayEnroll :: {}" , request);
			VuAccountSubscriberDtl subscriber = vuAccountSubscriberDtlDaoImpl.getAccountDtls(request.getMsisdn());
			logger.debug("Selected subscriber :: {}" , subscriber);
			if(null != subscriber) {	
				checkAutopayEnrollAllowed(subscriber.getAccountId());
				if (null != subscriber.getAccountId()) {
					VuRatePlan ratePlan = ratePlanDao.getRatePlan(subscriber.getRatePlanId());
					logger.debug(RATE_PLAN_FOUND_STR , ratePlan);
					if(ratePlan == null) {
						logger.error("Rate plan is not found for msisdn :: {} ", request.getMsisdn());
						throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + "Rate Plan not found");
					}
					// check if recurring id not in 8 or 9
					isRecurringIdValid(ratePlan.getApRecurringId());
					Date nextAutoPayCycleDate = getNextAPCycleDate(ratePlan.getApRecurringId(), subscriber, request);
					//This condition will only calculate discount code For ML Owner and Non ML type subscriber.
				    String apDiscountCode = null;
					apDiscountCode = getDiscountMLMember(subscriber, ratePlan, apDiscountCode);
					logger.debug("Discount code  found :: {}" , apDiscountCode);
					
					//find bonus units
					List<VuDsaEvaluations> bonusUnits = discountDao.getDiscount(apDiscountCode);
					
					logger.debug("AutoPayEnroll final request:: {}" , request);
					autopayDao.autopayEnroll(subscriber.getAccountId(), request, ratePlan.getApRecurringId(), nextAutoPayCycleDate, apDiscountCode);
					response = new AutoPayEnrollResponseBean();
					AutoPayDetailBean detailBean = new AutoPayDetailBean();
					detailBean.setRatePlanCode(ratePlan.getRatePlanCode());
					detailBean.setRatePlanName(ratePlan.getRatePlanName());
					detailBean.setActivationDate(subscriber.getActivateDate());
					detailBean.setAccountNo(subscriber.getAccountNumber());
					detailBean.setRecurringtopupcyclecdType(ratePlan.getApRecurringId());
					detailBean.setApDiscountCode(apDiscountCode);
					detailBean.setNextAutopayCycleDate(nextAutoPayCycleDate);
					if (!CollectionUtils.isEmpty(bonusUnits) && null != bonusUnits.get(0).getBonusUnits()) {
						detailBean.setBonusUnits(bonusUnits.get(0).getBonusUnits());
					} else {
						detailBean.setBonusUnits(0);
					}
					response.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
					response.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);
					response.setDetailBean(detailBean);
					response.setMsisdn(Long.valueOf(request.getMsisdn()));
				}
			}else {
				throw new AutopayServiceException(FaultGlobalCode.INVALID_MSISDN, MSISDN_DOES_NOT_EXIST);
			}
		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		} 
		
		return response;
	}

	/**
	 * User: ekmsusu , Date: Aug 4, 2021 12:56:44 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param subscriber
	 * @param ratePlan
	 * @param apDiscountCode
	 * @return
	 * @throws AutopayDaoException
	 */
	private String getDiscountMLMember(VuAccountSubscriberDtl subscriber, VuRatePlan ratePlan, String apDiscountCode)
			throws AutopayDaoException {
		if(!(MULTILINE.equalsIgnoreCase(subscriber.getRplMirrorType()) && MEMBER.equalsIgnoreCase(subscriber.getMltLineType()))) {
		 apDiscountCode = autopayDiscountService.getAutoPayEnrollDiscount(getAutoPayDiscountCalculationBean(subscriber,  String.valueOf(ratePlan.getApRecurringId())));
		}
		return apDiscountCode;
	}

	/**
	 * User: eihsnad , Date: Jan 28, 2021 2:55:17 PM 2021
	 *
	 * Purpose: calculate Next AP Cycle Date
	 *
	 * US/D/F Number: ATTPRE-96159
	 *
	 * Return Type: void
	 *
	 * @param ratePlan
	 * @param subscriber
	 * @param nextAutoPayCycleDate
	 * @param request
	 */
	private Date getNextAPCycleDate(long recurringId, VuAccountSubscriberDtl subscriber,
			AutoPayEnrollRequestBean request) {
		Date nextAutoPayCycleDate = null;
		if (recurringId == RECURRING_TYPE_MONTHLY) {
			/***
			 * F89086->ATTPRE-96159 if Subscriber register multi month rate plan then Use
			 * ACCT_MCRP_END_DATE as expiry date
			 ***/
			if (subscriber.getAcctMCRPEndDate() != null) {
				nextAutoPayCycleDate = calculateNextAutopayDate(subscriber.getAcctMCRPEndDate(), null);
			} else {
				nextAutoPayCycleDate = calculateNextAutopayDate(subscriber.getAccountRatePlanExpireDate(), null);
			}
		} else if (recurringId == RECURRING_TYPE_PAY_AS_GO) {
			nextAutoPayCycleDate = calculateNextAutopayDate(null, request.getSupervisionExpiryDate());
		}
		if (nextAutoPayCycleDate == null) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE,
					AccountAutopayDtlsDaoImpl.NEXT_AUTOPAY_CYCLE_DATE + " can't be NULL.");
		}
		return nextAutoPayCycleDate;
	}

	private void isRecurringIdValid(long recurringId) {
		if(recurringId == RECURRING_TYPE_6 || recurringId == RECURRING_TYPE_7 ) {
			logger.error("Recurring topup cycle is not valid :: {} ", recurringId);
			throw new AutopayServiceException(FaultGlobalCode.LEGACY_RECURRING_ID_NOT_SUPPORTED, "Legacy Recurring topup cycle is not supported");
		}
	}

	private Date calculateNextAutopayDate(Date ratePlanExpiryDate, Date superVisionExpiryDate) {
		if(ratePlanExpiryDate != null) {
			return DateUtils.addDays(ratePlanExpiryDate, -1);
		}else if(superVisionExpiryDate != null) {
			return DateUtils.addDays(superVisionExpiryDate, -2);
		}else {
			return null;
		}
	}

	/**
	 * User: eihsnad , Date: Nov 12, 2020 8:02:22 PM 2020
	 *
	 * Purpose: Service impl for AutoPay update
	 *
	 * US/D/F Number:US84043
	 *
	 * Return Type: BaseResponse
	 *
	 * @param request
	 * @return
	 */
	@Override
	public BaseResponse autopayUpdate(AutoPayUpdateRequestBean request) {
		AutoPayUpdateResponseBean response = null;
		try {
			VuAccountSubscriberDtl subscriber = vuAccountSubscriberDtlDaoImpl.getAccountDtls(request.getMsisdn());
			if (null != subscriber && null != subscriber.getAccountId()) {
				AccountAutopayDtls autoPayDtls = checkUpdateAllowed(subscriber.getAccountId());
				logger.debug("Current Discount code {}", autoPayDtls.getApCurrentdiscountCode());
				autopayDao.autopayUpdate(subscriber.getAccountId(), request);
				response = new AutoPayUpdateResponseBean();
				response.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
				response.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);
				response.setMsisdn(Long.valueOf(request.getMsisdn()));
				List<VuDsaEvaluations> bonusUnits = discountDao.getDiscount(autoPayDtls.getApCurrentdiscountCode());
				logger.debug("List of Bonus Units found {}", bonusUnits);

				AutoPayDetailBean detailBean = new AutoPayDetailBean();
				if(autoPayDtls.getRecurringTopupCycleCDType()!=null) {
					detailBean.setRecurringtopupcyclecdType(Long.parseLong(autoPayDtls.getRecurringTopupCycleCDType()));
				}

				if (!CollectionUtils.isEmpty(bonusUnits) && null != bonusUnits.get(0).getBonusUnits()) {
					detailBean.setBonusUnits(bonusUnits.get(0).getBonusUnits());
				} else {
					logger.debug("Bonus units is not found");
					detailBean.setBonusUnits(0);
				}
				response.setDetailBean(detailBean);
			}
			else {
				throw new AutopayServiceException(FaultGlobalCode.INVALID_MSISDN, MSISDN_DOES_NOT_EXIST);
			}
		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}
		return response;
	}

	/**
	 * User: ekaramp , Date: Nov 27, 2020 7:57:48 PM 2020
	 *
	 * Purpose: Check if Update required for this msisdn
	 *
	 * US/D/F Number: F1217
	 *
	 * Return Type: void
	 *
	 * @param accountId
	 */
	private AccountAutopayDtls checkUpdateAllowed(String accountId) {
		AccountAutopayDtls autopayDtls = null;
		autopayDtls = accountAutopayDtlsDaoImpl.getAutoPayDtls(accountId);
		if (autopayDtls == null) {
			throw new AutopayServiceException(FaultGlobalCode.MANDATORY_FIELD_MISSING,
					MSISDN_IS_NOT_AVAILABLE_IN_AUTPAY);
		}
		/** Throw Exception is Status is already INACTIVE */
		if (autopayDtls.getAutopayStatus().equals(ApsConstants.INACTIVE_STATUS)
				|| autopayDtls.getAutopayStatus().equals(ApsConstants.SOFT_INACTIVE_STATUS)) {
			throw new AutopayServiceException(FaultGlobalCode.AUTOPAY_ALREADY_DISABLED, AUTOPAY_ALREADY_DISABLED);
		}

		return autopayDtls;
	}
	/**
	 * User: ekxrxax , Date: Nov 27, 2020 8:02:22 PM 2020
	 *
	 * Purpose: Service impl for AutoPay history
	 *
	 * US/D/F Number:US91272
	 *
	 * Return Type: BaseResponse
	 *
	 * @param request
	 * @return
	 */
	public BaseResponse getAutopayHistory(AutoPayHistoryRequestBean request) {
		AutoPayHistoryResponseBean autoPayHistoryResponseBean = new AutoPayHistoryResponseBean();
		List<ApsHistory> apHistoryList = new ArrayList<>();

		try {
			VuAccountSubscriberDtl subscriber = vuAccountSubscriberDtlDaoImpl.getAccountDtls(request.getMsisdn());

			AccountAutopayDtls accountAutopayDtls = null;
			if (null != subscriber) {
				accountAutopayDtls = accountAutopayDtlsDaoImpl.getAutoPayDtls(subscriber.getAccountId());
			}

			Date startDate = Date.from(LocalDateTime.parse(request.getStartdate(), DATE_TIME_FORMATTER)
					.atZone(ZoneId.systemDefault()).toInstant());
			Date endDate = Date.from(LocalDateTime.parse(request.getEnddate(), DATE_TIME_FORMATTER)
					.atZone(ZoneId.systemDefault()).toInstant());
			startDate = processStartDate(subscriber, startDate);
			endDate = processEndDate(subscriber,startDate, endDate);

			logger.debug("start date: {} end date: {} ", startDate , endDate);

			List<AutoPayTransactions> autopayTransactions = autopayHistoryDao.getAutoPayHistory(request.getMsisdn(),
					DateUtils.convertDateToDBFormat(startDate, false), DateUtils.convertDateToDBFormat(endDate, true),request.getNumberOfRecords().toString());
			logger.debug("list:: {} " , autopayTransactions);
			if (null != autopayTransactions && !autopayTransactions.isEmpty()) {
				apHistoryList=	populateHistoryList(apHistoryList, accountAutopayDtls,
						autopayTransactions);

			}
			autoPayHistoryResponseBean.setApHistoryList(apHistoryList);
			autoPayHistoryResponseBean.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
			autoPayHistoryResponseBean.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);

		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}

		return autoPayHistoryResponseBean;
	}

	/**
	 * User: ekxrxax , Date: Dec 21, 2020 1:55:37 PM 2020
	 *
	 * Purpose: Setting End date in case later than current date or more than year old
	 *
	 * US/D/F Number: US93616
	 *
	 * Return Type: Date
	 *
	 * @param subscriber
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Date processEndDate(VuAccountSubscriberDtl subscriber,
			Date startDate, Date date) {
		Date currentDate = new Date();
		Date endDate = date;
		if ((null != subscriber && date.before(subscriber.getActivateDate()))
				|| date.before(startDate) || date.after(currentDate)) {
			endDate = currentDate;
		}
		return endDate;
	}

	/**
	 * User: ekxrxax , Date: Dec 21, 2020 1:54:05 PM 2020
	 *
	 * Purpose: setting the start date if less than year old or later than current date
	 *
	 * US/D/F Number: US93616
	 *
	 * Return Type: Date
	 *
	 * @param subscriber
	 * @param startDate
	 * @return
	 */
	private Date processStartDate(VuAccountSubscriberDtl subscriber, Date date) {
		Date startDate = date;
		Date currentDate = new Date();
		Date yearBeforeDate = DateUtils.addDays(currentDate, -365);
		if (null != subscriber) {
			if ((date.before(yearBeforeDate)
					&& subscriber.getActivateDate().before(yearBeforeDate)) || date.after(currentDate)) {
				startDate = yearBeforeDate;
			} else if ((date.before(yearBeforeDate) && subscriber
					.getActivateDate().after(yearBeforeDate))
					|| date.after(yearBeforeDate)
					&& subscriber.getActivateDate().after(date)) {
				startDate = subscriber.getActivateDate();
			}
		}
		return startDate;
	}

	private List<ApsHistory> populateHistoryList(List<ApsHistory> apHistoryList,
			AccountAutopayDtls accountAutopayDtls,
			List<AutoPayTransactions> autopayTransactions) {
		for (AutoPayTransactions autopayTransaction : autopayTransactions) {
			ApsHistory autoPayHistoryResponse = new ApsHistory();
			autoPayHistoryResponse.setPdofLast4(autopayTransaction.getPdofLast4());
			autoPayHistoryResponse.setRecurringTopupCycleCode(autopayTransaction.getRecurringTopupCycleCode());
			autoPayHistoryResponse.setComments(autopayTransaction.getComments());
			autoPayHistoryResponse.setModifiedBy(autopayTransaction.getModifiedBy());
			autoPayHistoryResponse.setTransactionType(autopayTransaction.getTransactionType());
			autoPayHistoryResponse.setTransactionTimeStamp(autopayTransaction.getTransactionTimeStamp());
			autoPayHistoryResponse.setActivationdate(autopayTransaction.getActivationdate());
			autoPayHistoryResponse.setEnrollmentdate(autopayTransaction.getEnrollmentdate());
			autoPayHistoryResponse.setEnrollmentchannel(autopayTransaction.getEnrollmentchannel());
			autoPayHistoryResponse.setUnenrollmentdate(autopayTransaction.getUnenrollmentdate());
			autoPayHistoryResponse.setRateplancode(autopayTransaction.getRateplancode());
			autoPayHistoryResponse.setPromotionamount(autopayTransaction.getPromotionamount());
			autoPayHistoryResponse.setNextautopaycycledate(autopayTransaction.getNextautopaycycledate());
			if (accountAutopayDtls != null) {
				autoPayHistoryResponse.setIsActive(accountAutopayDtls.getAutopayStatus());
			}
			apHistoryList.add(autoPayHistoryResponse);
		}
		return apHistoryList;
	}

	/**
	 * User: epinmon , Date: Jan 6, 2021 1:29:17 PM 2021
	 *
	 * Purpose: this method will calculate then validate and finally updates next
	 * auto pay cycle date. US/D/F Number: F1221/US88638
	 *
	 * @param request
	 * @param response
	 * @return response
	 * @throws AutopayServiceException
	 */
	@Override
	public BaseResponse validateAndUpdateNextAutopayCycleDate(AutoPayNextCycleDateRequestBean request)
			throws AutopayServiceException {
		AutoPayNextCycleDateResponseBean response = new AutoPayNextCycleDateResponseBean();
		try {
			Date nextAutoPayCycleDate = null;
			if (logger.isDebugEnabled()) {
				logger.debug("validateAndUpdateNextAutopayCycleDate Request Values :" + request.toString());
			}
			nextAutoPayCycleDate = calculateNextAutopayDate(request.getAccountRatePlanExpireDate(),
					request.getSupervisionExpiryDate());
			if (nextAutoPayCycleDate == null) {
				throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE,
						DATABASE_ERROR + AccountAutopayDtlsDaoImpl.NEXT_AUTOPAY_CYCLE_DATE + " can't be NULL.");
			}
			response.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
			response.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);
			response.setNextAutopayCycleDate(nextAutoPayCycleDate);
			if (isNextCycleDateUpdated(request.getCurrentNextAutopayCycleDate(), nextAutoPayCycleDate)) {
				response.setHasNextAutopayCycleDateUpdated(true);
				autopayDao.autopayUpdateNextCycleDate(request.getAccountId(), nextAutoPayCycleDate);
			}

		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}
		return response;
	}

	/**
	 * User: epinmon , Date: Feb 1, 2021 3:57:18 PM 2021
	 *
	 * Purpose: This method compares the current next cycle date with updated one,
	 * if it's updated it will return true else false.
	 *
	 * US/D/F Number:F1221/US96608
	 *
	 * Return Type: boolean
	 *
	 * @param oldDate
	 * @param updatedDate
	 * @return boolean
	 */
	private static boolean isNextCycleDateUpdated(Date oldDate, Date updatedDate) {
		if (null != oldDate && null != updatedDate) {
			String previousDate = DateUtils.convertDateToDBFormat(oldDate, false);
			String currentUpdatedDate = DateUtils.convertDateToDBFormat(updatedDate, false);
			if (!previousDate.equals(currentUpdatedDate)) {
				return true;
			}
		}
		return false;
	}


	/**
	 *
	 * User: erohaer , Date: Mar 1, 2021 1:09:29 PM 2021
	 *
	 * Purpose: Create AutoPayDiscountCalculationBean
	 *
	 * US/D/F Number:
	 *
	 * Return Type: AutoPayDiscountCalculationBean
	 *
	 * @param subscriber
	 * @return
	 */
	private AutoPayDiscountCalculationBean getAutoPayDiscountCalculationBean(VuAccountSubscriberDtl subscriber, String recurringId) {
		AutoPayDiscountCalculationBean autoPayDiscountCalculationBean = new AutoPayDiscountCalculationBean();
		autoPayDiscountCalculationBean.setActivationStartDate(subscriber.getActivateDate());
		autoPayDiscountCalculationBean.setMsisdn(subscriber.getMsisdn());
		autoPayDiscountCalculationBean.setRatePlanId(String.valueOf(subscriber.getRatePlanId()));
		autoPayDiscountCalculationBean.setTopUpCycleValue(recurringId);

		return autoPayDiscountCalculationBean;
	}

}