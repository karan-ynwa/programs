package com.ericsson.eus.aps.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.dao.IAccountAutopayDtlsDao;
import com.ericsson.eus.aps.dao.impl.AccountAutopayDtlsDaoImpl;
import com.ericsson.eus.aps.dao.impl.AutoPayEventTrackerDaoImpl;
import com.ericsson.eus.aps.dao.impl.AutoPaySubscriberDtlDaoImpl;
import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.dao.model.AutoPayEventTracker;
import com.ericsson.eus.aps.dao.model.AutoPaySubscriberDtl;
import com.ericsson.eus.aps.rest.common.RESTConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.rest.common.TopUpCycleCD;
import com.ericsson.eus.aps.rest.service.AutoPayInfoService;
import com.ericsson.eus.aps.service.common.ApsConfig;
import com.ericsson.eus.aps.service.common.AutopayServiceAgent;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountPromoApplied;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoDataBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoResponseBean;
import com.ericsson.eus.aps.service.data.beans.FeatureInfo;
import com.ericsson.eus.aps.service.data.beans.FeatureInfoBean;
import com.ericsson.eus.aps.service.data.beans.MsisdnDetails;
import com.ericsson.eus.aps.service.data.beans.SubscriberInfoBean;
import com.ericsson.eus.aps.service.util.ApsUtil;
import com.ericsson.eus.csi.beans.InquirePrepaidFeaturesRequest;
import com.ericsson.eus.csi.beans.InquirePrepaidFeaturesResponse;
import com.ericsson.eus.csi.domain.Feature;
import com.ericsson.eus.csi.domain.Mode;
import com.ericsson.eus.csi.exception.CSIServiceApplicationException;
import com.ericsson.eus.csi.exception.CSIServiceException;
import com.ericsson.eus.csi.service.CSIPluginService;

/**
 *
 * FILE_NAME: AutopayServiceimpl.java
 *
 * MODULE DESCRIPTION: US84027
 *
 * @author ekxrxax, Date: Sep 14, 2020 3:46:11 PM 2020
 *
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
/**
 *
 * FILE_NAME: AutoPayInfoServiceImpl.java
 *
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Jan 6, 2021 10:41:15 AM 2021
 *
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Service
public class AutoPayInfoServiceImpl implements AutoPayInfoService {
	private static final String DATABASE_ERROR = "Database error: ";
	private final Logger logger = LogManager.getLogger(AutoPayInfoServiceImpl.class);
	@Autowired
	private IAccountAutopayDtlsDao accountAutopayDtlsDaoImpl;
	@Autowired
	private AutoPaySubscriberDtlDaoImpl autoPaySubscriberDtlDaoImpl;
	@Autowired
	private AutopayServiceAgent autopayserviceagent;
	@Autowired
	private AutoPayEventTrackerDaoImpl autoPayEventTrackerDaoImpl;
	@Autowired
	private ApsConfig apsConfig;

	private static final String FIXED_AMOUNT_ERROR = "Fixed amount missing";

	/**
	 * User: ekxrxax , Date: Sep 14, 2020 3:57:50 PM 2020
	 *
	 * Purpose: fetching data for subscriber
	 *
	 * US/D/F Number:US84027
	 *
	 * Return Type: SubscriberData
	 *
	 * @param String
	 * @return
	 */
	@Override
	public AutoPayInfoDataBean getAutopayData(AutoPayInfoDataBean apDataBean, String msisdn) {

		try {

			AutoPaySubscriberDtl subscriber = autoPaySubscriberDtlDaoImpl.getAccountDtls(msisdn);
			AccountAutopayDtls apDetails = getAutoPayDtls(subscriber.getAccountId());
			apDataBean.setMsisdn(msisdn);
			if (null != apDetails.getApCurrentdiscountCode()) {
				apDataBean.setDiscountCode(apDetails.getApCurrentdiscountCode());
			}
			apDataBean.setAutoPaySubscriberDtl(subscriber);
			apDataBean.setApDetails(apDetails);

			if (logger.isDebugEnabled()) {
				logger.debug("subscriber: (" + subscriber + "), AccountAutopayDtls: " + apDetails);
			}

		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}
		return apDataBean;
	}

	/**
	 * User: esuspal , Date: Jan 6, 2021 10:41:39 AM 2021
	 *
	 * Purpose: Fetch recods from EBI_ACCOUNT_AUTOPAY and if no recods
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: AccountAutopayDtls
	 *
	 * @param accountId
	 * @return
	 */
	private AccountAutopayDtls getAutoPayDtls(String accountId) {
		AccountAutopayDtls apDetails = null;
		try {
			apDetails = accountAutopayDtlsDaoImpl.getAutoPayDtls(accountId);
		} catch (Exception e) {
			logger.debug("No recods found in EBI_ACCOUNT_AUTOPAY. Message:" + e.getMessage());
		}
		if (null == apDetails) {
			apDetails = new AccountAutopayDtls();
		}
		return apDetails;
	}

	/**
	 * User: esuspal , Date: Dec 7, 2020 12:25:29 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 *
	 * @param apDataBean
	 * @throws AutopayDaoException
	 */
	@Override
	public AutoPayInfoDataBean getAutoPayEventTracker(AutoPayInfoDataBean apDataBean) throws AutopayDaoException {
		List<AutoPayEventTracker> list = autoPayEventTrackerDaoImpl
				.getAutoPayEventTracker(apDataBean.getAutoPaySubscriberDtl().getAccountId());
		apDataBean.setAutoPayEventTrackerList(list != null ? list : new ArrayList<>());

		return apDataBean;
	}

	/**
	 * User: esuspal , Date: Dec 5, 2020 9:01:19 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: F1216
	 *
	 * @param apDataBean
	 * @param msisdn
	 * @return
	 */
	@Override
	public List<FeatureInfo> getFeatureInfoList(AutoPayInfoDataBean apDataBean, String msisdn) {
		if (null != apDataBean.getAutoPaySubscriberDtl().getFeatureCodeList()) {
			return getFeatureInfo(msisdn);
		}

		return new ArrayList<>();
	}

	/**
	 * User: ekxrxax , Date: Sep 14, 2020 7:48:12 PM 2020
	 *
	 * Purpose: fetching features from csi
	 *
	 * US/D/F Number: US84027
	 *
	 * Return Type: List<FeatureInfo>
	 *
	 * @param featList
	 * @param msisdn2
	 * @return
	 */
	private List<FeatureInfo> getFeatureInfo(String msisdn) {
		logger.debug("Fetching enrolled features");

		CSIPluginService csiPluginService = autopayserviceagent.getCSIServiceInstance();
		InquirePrepaidFeaturesRequest inquirePrepaidFeaturesRequest = new InquirePrepaidFeaturesRequest();
		inquirePrepaidFeaturesRequest.setMode(Mode.E);
		inquirePrepaidFeaturesRequest.setSubscriberNumber(msisdn);
		inquirePrepaidFeaturesRequest.setConversationId(ApsConstants.EMPTY_STRING);
		inquirePrepaidFeaturesRequest.setOriginatingRepresentativeId(ApsConstants.EMPTY_STRING);
		InquirePrepaidFeaturesResponse inquirePrepaidFeaturesResponse = null;
		List<Feature> features = null;
		try {
			inquirePrepaidFeaturesResponse = csiPluginService.inquirePrepaidFeatures(inquirePrepaidFeaturesRequest);
			logger.debug("Features response Fetched");
			if (inquirePrepaidFeaturesResponse != null) {
				features = inquirePrepaidFeaturesResponse.getFeatures();
			}
		} catch (CSIServiceException | CSIServiceApplicationException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE,
					"Error fetching the Features from CSI: " + e.getMessage());
		}
		List<FeatureInfo> featuresInfo = new ArrayList<>();
		logger.debug("Features list:: " + features);
		if (features != null) {
			for (Feature e : features) {
				FeatureInfo f = new FeatureInfo();
				f.setFeatureCode(e.getFeature().getFeatureCode());
				f.setFeatureAmount(e.getFeature().getFeatureCost());
				f.setFeatureDesc(e.getFeature().getFeatureDesc());
				featuresInfo.add(f);
			}
		}
		return featuresInfo;

	}

	/**
	 * User: esuspal , Date: Dec 7, 2020 1:30:35 PM 2020
	 *
	 * Purpose: Populate AutoPayInfoResponseBean
	 *
	 * US/D/F Number: F1216
	 *
	 * @param msisdnDetails
	 * @param apDataBean
	 * @return
	 */
	@Override
	public AutoPayInfoResponseBean getAutoPayInfoResponseBean(List<MsisdnDetails> msisdnDetails,
			AutoPayInfoDataBean apDataBean) {
		AutoPayInfoResponseBean autoPayInfoResponseBean = new AutoPayInfoResponseBean();
		try {
			if (null != apDataBean) {
				setSubscriberInfoResponse(autoPayInfoResponseBean, apDataBean);
				setFeatureInfo(autoPayInfoResponseBean, apDataBean);
				autoPayInfoResponseBean.setResponseCode(RESTConstants.SUCCESS_RESPONSE_CODE);
				autoPayInfoResponseBean.setResponseText(RESTConstants.AUTOPAY_RESPONSE_TEXT);

				List<AutoPayDiscountPromoApplied> autoPayDiscountPromoAppliedList = apDataBean
						.getAutoPayDiscountPromoApplied();

				setAutoPayDiscountPromoApplied(autoPayDiscountPromoAppliedList, apDataBean, autoPayInfoResponseBean);

				if (logger.isDebugEnabled()) {
					logger.debug("AutoPayInfoResponseBean: " + autoPayInfoResponseBean);
				}
			}
		} catch (AutopayServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Error: " + e.getMessage());
		}
		return autoPayInfoResponseBean;
	}

	/**
	 * User: esuspal , Date: Feb 1, 2021 1:31:39 PM 2021
	 *
	 * Purpose: AutoPay discount(apPromoAmount=0) will only be applied only if
	 * "FixedAmount>=AFAmount - AP promo amount" for TopUpCycle #6 and #7 Fixed
	 * amount is >= (base amount - the promo amount). If no topupcyclecd then there
	 * will be no promo applied.
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param autoPayDiscountPromoAppliedList
	 * @param apDataBean
	 * @param autoPayInfoResponseBean
	 */
	private void setAutoPayDiscountPromoApplied(List<AutoPayDiscountPromoApplied> autoPayDiscountPromoAppliedList,
			AutoPayInfoDataBean apDataBean, AutoPayInfoResponseBean autoPayInfoResponseBean) {
		if (null != autoPayDiscountPromoAppliedList && !autoPayDiscountPromoAppliedList.isEmpty()) {
			if (getTopUpCycleCD(apDataBean.getAutoPaySubscriberDtl(), apDataBean.getApDetails()) != -1) {
				if (isPromoApply(autoPayDiscountPromoAppliedList, apDataBean)) {
					logger.debug("Promo applied");
					autoPayInfoResponseBean.getApSubscriberInfo()
							.setAutoPayDiscountPromoApplied(autoPayDiscountPromoAppliedList);
				} else {
					logger.debug("Promo wont apply as condition failed");
					autoPayInfoResponseBean.getApSubscriberInfo().setAutoPayDiscountPromoApplied(null);
				}
			} else {
				logger.debug("No Valid Topup Cycle CD found. Promo will not apply");
				autoPayInfoResponseBean.getApSubscriberInfo().setAutoPayDiscountPromoApplied(null);
			}
		}
	}

	/**
	 * User: esuspal , Date: Jan 28, 2021 9:39:01 PM 2021
	 *
	 * Purpose: AutoPay discount(apPromoAmount=0) will only be applied only if
	 * "FixedAmount>AFAmount - AP promo amount" for TopUpCycle #6 and #7 Fixed
	 * amount is >= (base amount - the promo amount)
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: boolean
	 *
	 * @param autoPayDiscountPromoAppliedList
	 * @param apDataBean
	 * @return
	 */
	private boolean isPromoApply(List<AutoPayDiscountPromoApplied> autoPayDiscountPromoAppliedList,
			AutoPayInfoDataBean apDataBean) {
		if (logger.isDebugEnabled()) {
			logger.debug("Auto Pay promo discount(Before checking topup 6/7):" + autoPayDiscountPromoAppliedList);
		}

		int topUpCycleCD = getTopUpCycleCD(apDataBean.getAutoPaySubscriberDtl(), apDataBean.getApDetails());
		if (logger.isDebugEnabled()) {
			logger.debug("TopUpCycleCD:" + topUpCycleCD);
		}

		/**
		 * Condition => FixedAmnt < (afAmount - totalPromoAmnt) applies only for
		 * TopUpCycleCD=7
		 */
		if (TopUpCycleCD.is7(String.valueOf(topUpCycleCD))) {

			double totalPromoAmnt = 0;
			for (AutoPayDiscountPromoApplied autoPayDiscountPromoApplied : autoPayDiscountPromoAppliedList) {
				totalPromoAmnt = totalPromoAmnt + Double.parseDouble(autoPayDiscountPromoApplied.getApPromoAmount());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Total Promo amount:" + totalPromoAmnt + " , Fixed Amount:"
						+ apDataBean.getAutoPaySubscriberDtl().getFixedAmount());
			}

			if (NumberUtils.isParsable(apDataBean.getAutoPaySubscriberDtl().getFixedAmount())) {
				double fixedAmnt = Double.parseDouble(apDataBean.getAutoPaySubscriberDtl().getFixedAmount());
				double afAmount = Double.parseDouble(apDataBean.getAutoPaySubscriberDtl().getAfAmount());
				logger.debug("Fixed Amount:" + fixedAmnt + " , AF Amount:" + afAmount + ", Total Promo amount:"
						+ totalPromoAmnt);
				if (fixedAmnt < (afAmount - totalPromoAmnt)) {
					return false;
				}
			}

		}

		return true;
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 7:59:34 PM 2021
	 *
	 * Purpose: set setSubscriberInfo details in response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param autoPayInfoResponseBean
	 * @param apDataBean
	 */
	private void setSubscriberInfoResponse(AutoPayInfoResponseBean autoPayInfoResponseBean,
			AutoPayInfoDataBean apDataBean) {
		SubscriberInfoBean subscriberInfoBean = new SubscriberInfoBean();
		autoPayInfoResponseBean.setApSubscriberInfo(subscriberInfoBean);
		subscriberInfoBean.setMsisdn(apDataBean.getMsisdn());
		subscriberInfoBean
				.setActivationDate(ApsUtil.formatDate(apDataBean.getAutoPaySubscriberDtl().getActivateDate()));
		subscriberInfoBean.setRateplanCode(apDataBean.getAutoPaySubscriberDtl().getRatePlanCode());
		subscriberInfoBean.setRateplanName(apDataBean.getAutoPaySubscriberDtl().getRatePlanName());
		if (!StringUtils.equals(apDataBean.getMlType(), ApsConstants.ML_MEMBER)) {
			calculateAmount(apDataBean.getAutoPaySubscriberDtl(), apDataBean.getApDetails(), subscriberInfoBean);
		} else {
			subscriberInfoBean.setRateplanAmount(null);
			subscriberInfoBean.setFixedAmount(null);
		}
		setRatePlanExpirationDate(apDataBean, subscriberInfoBean);

		setAutoPayDtlsResponse(autoPayInfoResponseBean, apDataBean);
		setAutoPayEventTrackerResponse(autoPayInfoResponseBean, apDataBean);
		/**
		 * Validating mandatory AP parameter if subscriber details is present in
		 * EBI_ACCOUNT_AUTOPAY table
		 */
		if (StringUtils.isNotBlank(apDataBean.getApDetails().getAccountId())) {
			validateSubscriberInfoBean(subscriberInfoBean);
		}
	}

	/**
	 * User: esuspal , Date: Feb 11, 2021 12:50:40 PM 2021
	 *
	 * Purpose: Set rateplan expiration date in response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param apDataBean
	 * @param subscriberInfoBean
	 */
	private void setRatePlanExpirationDate(AutoPayInfoDataBean apDataBean, SubscriberInfoBean subscriberInfoBean) {
		/**
		 * US96159 if Recurring ID 7 or 9 and register with Multi month rateplan then
		 * Rateplan Expiry will be ACCT_MCRP_DATE
		 ***/
		int topUpCycleCD = getTopUpCycleCD(apDataBean.getAutoPaySubscriberDtl(), apDataBean.getApDetails());
		if (TopUpCycleCD.isMcrpTopCycleCD(String.valueOf(topUpCycleCD))
				&& NumberUtils.isParsable(apDataBean.getAutoPaySubscriberDtl().getMcrpNoOfCycles())
				&& Integer.parseInt(apDataBean.getAutoPaySubscriberDtl().getMcrpNoOfCycles()) > 0) {
			subscriberInfoBean.setRateplanExpirationDate(
					ApsUtil.formatDate(apDataBean.getAutoPaySubscriberDtl().getAcctMCRPEndDate()));
		} else {
			subscriberInfoBean.setRateplanExpirationDate(
					ApsUtil.formatDate(apDataBean.getAutoPaySubscriberDtl().getAccountRatePlanExpireDate()));
		}
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:00:06 PM 2021
	 *
	 * Purpose: For topup cycle 6,7,8 &9 validate mandatory AP parameters for AP
	 * enrolled subscriber
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param apDataBean
	 * @param subscriberInfoBean
	 * @throws AutopayServiceException
	 */
	private void validateSubscriberInfoBean(SubscriberInfoBean subscriberInfoBean) throws AutopayServiceException {
		if (StringUtils.isBlank(subscriberInfoBean.getApStatus())) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Invalid AutoPay parameter");

		}
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:00:46 PM 2021
	 *
	 * Purpose: For topupcycle cd 6/7 set fixed amount and 8/9 set rateplanamount.
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param subscriber
	 * @param apDetails
	 * @param subscriberInfoBean
	 */
	private void calculateAmount(AutoPaySubscriberDtl subscriber, AccountAutopayDtls apDetails,
			SubscriberInfoBean subscriberInfoBean) {
		int topUpCycleCD = getTopUpCycleCD(subscriber, apDetails);

		if (logger.isDebugEnabled()) {
			logger.debug("TopUpCycleCD: " + topUpCycleCD);
		}

		switch (topUpCycleCD) {
		case 6:
		case 7:
			setFixedAmount(subscriber, subscriberInfoBean);
			break;
		case 8:
			subscriberInfoBean.setFixedAmount(null);
			if (NumberUtils.isParsable(apsConfig.getFixedAmountTopupcycle8())) {
				subscriberInfoBean.setRateplanAmount(ApsUtil.roundDouble(apsConfig.getFixedAmountTopupcycle8()));
			} else {
				logger.error(
						"No valid fixed amont value for topup cycle 8 configured in application.properties. Default value $25 considered ");
				subscriberInfoBean.setRateplanAmount(ApsUtil.roundDouble(ApsConstants.FIXED_AMOUNT_TOPUP_8_DEFAULT));
			}
			break;
		case 9:
			subscriberInfoBean.setFixedAmount(null);
			subscriberInfoBean.setRateplanAmount(ApsUtil.roundDouble(subscriber.getAfAmount()));
			break;

		default:
			logger.debug("No TopupCycleCD found for subscriber: " + subscriber.getMsisdn() + " and rate plan: "
					+ subscriber.getRatePlanCode() + " falling rateplan amount");
			subscriberInfoBean.setFixedAmount(null);
			subscriberInfoBean.setRateplanAmount(ApsUtil.roundDouble(subscriber.getAfAmount()));
			break;

		}

	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:02:07 PM 2021
	 *
	 * Purpose: get topcyclecd from autopay account table and if topcyclecd is null
	 * in autopay account table then will check from rateplan table.
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: int
	 *
	 * @param subscriber
	 * @param apDetails
	 * @return
	 */
	private int getTopUpCycleCD(AutoPaySubscriberDtl subscriber, AccountAutopayDtls apDetails) {
		int topUpCycleCD = -1;

		if (null != apDetails && TopUpCycleCD.isValid(apDetails.getRecurringTopupCycleCDType())) {
			topUpCycleCD = Integer.parseInt(apDetails.getRecurringTopupCycleCDType());
		} else if (TopUpCycleCD.isValid(subscriber.getTopUpCycleCD())) {
			topUpCycleCD = Integer.parseInt(subscriber.getTopUpCycleCD());
		}

		return topUpCycleCD;
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:03:40 PM 2021
	 *
	 * Purpose: Set fixed amount for topup 6
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param subscriber
	 * @param subscriberInfoBean
	 */
	private void setFixedAmount(AutoPaySubscriberDtl subscriber, SubscriberInfoBean subscriberInfoBean) {
		/**
		 * Condition to check if the entry present in AP_PROCESS_CHARGE table to get
		 * FixedAmount value.
		 */
		if (NumberUtils.isParsable(subscriber.getFixedAmount())) {
			subscriberInfoBean.setFixedAmount(ApsUtil.roundDouble(subscriber.getFixedAmount()));
			subscriberInfoBean.setRateplanAmount(null);
		} else {
			logger.error("No Valid Fixed amount found in AP Process charge table for subscriber details: "
					+ subscriber.getMsisdn());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, FIXED_AMOUNT_ERROR);
		}
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:04:20 PM 2021
	 *
	 * Purpose: Set AutoPayDtl in response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param autoPayInfoResponseBean
	 * @param apDataBean
	 */
	private void setAutoPayDtlsResponse(AutoPayInfoResponseBean autoPayInfoResponseBean,
			AutoPayInfoDataBean apDataBean) {
		SubscriberInfoBean subscriberInfoBean = autoPayInfoResponseBean.getApSubscriberInfo();
		AccountAutopayDtls accountAutopayDtls = apDataBean.getApDetails();
		if (null != accountAutopayDtls) {
			subscriberInfoBean.setApEnrollmentDate(ApsUtil.formatDate(accountAutopayDtls.getEnrollmentStartdDate()));
			subscriberInfoBean.setApPaymentDate(ApsUtil.formatDate(accountAutopayDtls.getNextAPCycleDate()));
			subscriberInfoBean.setApTopupCycleTypeCDName(accountAutopayDtls.getRecurringTopupCycleCDValue());
			subscriberInfoBean.setApStatus(accountAutopayDtls.getAutopayStatus());
			subscriberInfoBean.setApTopupCycleTypeCD(accountAutopayDtls.getRecurringTopupCycleCDType());
		}
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:04:50 PM 2021
	 *
	 * Purpose: Set AutoPayEventTracker in Response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param autoPayInfoResponseBean
	 * @param apDataBean
	 */
	private void setAutoPayEventTrackerResponse(AutoPayInfoResponseBean autoPayInfoResponseBean,
			AutoPayInfoDataBean apDataBean) {
		SubscriberInfoBean subscriberInfoBean = autoPayInfoResponseBean.getApSubscriberInfo();
		List<AutoPayEventTracker> eventTrackerList = apDataBean.getAutoPayEventTrackerList();

		if (null != eventTrackerList) {
			for (AutoPayEventTracker autoPayEventTracker : eventTrackerList) {
				if (EnumUtils.isValidEnum(ApsConstants.AutoPayEventTracker.class,
						autoPayEventTracker.getEventTrackerCode())) {

					if (StringUtils.equals(ApsConstants.AutoPayEventTracker.PDOF_LAST4.name(),
							autoPayEventTracker.getEventTrackerCode())) {
						subscriberInfoBean.setApPDOFLast4Digits(autoPayEventTracker.getEventTrackerValue());
					} else if (StringUtils.equals(ApsConstants.AutoPayEventTracker.PDOF_TOKEN.name(),
							autoPayEventTracker.getEventTrackerCode())) {
						subscriberInfoBean.setApPDOFToken(autoPayEventTracker.getEventTrackerValue());
					}
				}

			}
		}

	}

	/**
	 * User: esuspal , Date: Nov 28, 2020 9:30:57 PM 2020
	 *
	 * Purpose: Set feature details in response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param response
	 * @param apDataBeanList
	 */
	private void setFeatureInfo(AutoPayInfoResponseBean response, AutoPayInfoDataBean apDataBean) {
		Double totalCost = null;
		List<FeatureInfoBean> featureInfoBeanList = new ArrayList<>();
		List<FeatureInfo> featureInfoList = apDataBean.getFeatureInfoList();
		if (featureInfoList != null) {
			for (FeatureInfo featureInfo : featureInfoList) {
				FeatureInfoBean featureInfoBean = new FeatureInfoBean();
				featureInfoBean.setFeatureCode(featureInfo.getFeatureCode());
				featureInfoBean.setFeatureCost(String.valueOf(featureInfo.getFeatureAmount()));
				featureInfoBean.setFeatureDesc(featureInfo.getFeatureDesc());
				featureInfoBeanList.add(featureInfoBean);
				totalCost = featureInfo.getFeatureAmount() + (totalCost != null ? totalCost : 0);
			}
		}
		setFeatureInfoIntoResponse(featureInfoBeanList, response, totalCost);
	}

	/**
	 * User: esuspal , Date: Feb 2, 2021 8:05:16 PM 2021
	 *
	 * Purpose: set FeatureInfo Into Response
	 *
	 * US/D/F Number: F1216
	 *
	 * Return Type: void
	 *
	 * @param featureInfoBeanList
	 * @param response
	 * @param totalCost
	 */
	private void setFeatureInfoIntoResponse(List<FeatureInfoBean> featureInfoBeanList, AutoPayInfoResponseBean response,
			Double totalCost) {
		if (!featureInfoBeanList.isEmpty()) {
			response.getApSubscriberInfo().setApFeatureInfo(featureInfoBeanList);

			if (null != totalCost) {
				response.getApSubscriberInfo().setApTotalFeatureAmount(ApsUtil.roundDouble(totalCost.toString()));
			}
		}
	}

}
