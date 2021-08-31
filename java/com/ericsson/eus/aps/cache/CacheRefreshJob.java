/**
 * 
 */
package com.ericsson.eus.aps.cache;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;

import com.ericsson.ebi.alarms.util.Alarms;
import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IAutoPayDiscountDetailsDao;
import com.ericsson.eus.aps.dao.ICefCriteriaDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.IEbiCacheConfigDao;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.dao.impl.AutoPayDiscountDetailsDaoImpl;
import com.ericsson.eus.aps.dao.impl.CefCriteriaDaoImpl;
import com.ericsson.eus.aps.dao.impl.DiscountDaoImpl;
import com.ericsson.eus.aps.dao.impl.EbiCacheConfigDaoImpl;
import com.ericsson.eus.aps.dao.impl.VuRatePlanDaoImpl;
import com.ericsson.eus.aps.dao.model.EbiCacheConfigUpdateInfoRecord;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.common.ApsConfig;
import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 * @author eznraps
 * 
 */

public class CacheRefreshJob implements Job {

	private IEbiCacheConfigDao ebiCacheConfigDao;

	private IDiscountDao discountDao;

	private ICefCriteriaDao cefCriteriaDao;

	private IVuRatePlanDao vuRatePlanDao;

	private IAutoPayDiscountDetailsDao autoPayDiscountDetailsDaoImpl;

	private final Logger logger = LogManager.getLogger(CacheRefreshJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (logger.isDebugEnabled()) {
			logger.debug("Application start time " + ApsUtil.getCurrentDateTime());
		}
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		ebiCacheConfigDao = (EbiCacheConfigDaoImpl) jobDataMap.get(ApsConstants.EBI_CACHE_CONFIG_UPDATE_INFO_DAO);
		discountDao = (DiscountDaoImpl) jobDataMap.get(ApsConstants.DISCOUNT_DAO);
		cefCriteriaDao = (CefCriteriaDaoImpl) jobDataMap.get(ApsConstants.CEF_CRITERIA_DAO);
		vuRatePlanDao = (VuRatePlanDaoImpl) jobDataMap.get(ApsConstants.VU_RATE_PLAN_DAO);
		autoPayDiscountDetailsDaoImpl = (AutoPayDiscountDetailsDaoImpl) jobDataMap
				.get(ApsConstants.AUTO_PAY_DISCOUNT_DETAILS_DAO);
		doPeriodicCacheRefresh();
	}

	private void doPeriodicCacheRefresh() {

		try {
			EbiCacheConfigUpdateInfoRecord ebiCacheConfigUpdateInfoRecord = ebiCacheConfigDao
					.getEbiCacheConfigUpdateInfo();
			final Date currentDate = Calendar.getInstance().getTime();
			final long currentTimeStampMS = currentDate.getTime();
			EbiCacheConfigUpdateInfoRecord ebiCacheConfigUpdateInfoRecordLocal = ApsConfig
					.getEbiCacheConfigUpdateInfoRecordLocal();

			final long lastConfigCacheLocalTimeMS = ebiCacheConfigUpdateInfoRecordLocal.getLastCacheConfigUpdateTS()
					.getTime();
			long timeStampDiffMS = currentTimeStampMS - lastConfigCacheLocalTimeMS;
			long forceCacheIntervalMS = ebiCacheConfigUpdateInfoRecord.getForceCacheRefershInterval();
			if (logger.isDebugEnabled()) {
				logger.debug("timeStampDiffMS : " + timeStampDiffMS);
				logger.debug("FORCE Refresh Interval at Startup : " + forceCacheIntervalMS);
				logger.debug(
						"lastConfigCacheLocalTimeMS=" + lastConfigCacheLocalTimeMS + "   \nLastCacheConfigUpdateTS="
								+ ebiCacheConfigUpdateInfoRecord.getLastCacheConfigUpdateTS().getTime());
			}

			long appStartTimeStampDiffMS = currentTimeStampMS - ApsConfig.getAppstarttime().getTime();

			if (timeStampDiffMS >= forceCacheIntervalMS && appStartTimeStampDiffMS >= forceCacheIntervalMS) {
				if (logger.isDebugEnabled()) {
					logger.debug("Force Cache Refresh is in process .... at time started : " + currentDate);
				}
				reloadCache();
				logger.debug("Cache Refreshed Successfully At: " + new Date());
				// update value from db to the local value stored
				ApsConfig.getEbiCacheConfigUpdateInfoRecordLocal()
						.setLastCacheConfigUpdateTS(new Timestamp((currentTimeStampMS / 1000) * 1000));
			} else if (lastConfigCacheLocalTimeMS < ebiCacheConfigUpdateInfoRecord.getLastCacheConfigUpdateTS()
					.getTime()) {
				logger.debug("check local updated value with db update value, get current value form db ");
				logger.debug("Periodic cache refresh started");
				reloadCache();

				logger.debug("Cache Refreshed Successfully At: " + new Date());
				// update value from db to the local value stored
				ApsConfig.getEbiCacheConfigUpdateInfoRecordLocal()
						.setLastCacheConfigUpdateTS(ebiCacheConfigUpdateInfoRecord.getLastCacheConfigUpdateTS());
			}

		} catch (DataAccessException | AutopayDaoException ex) {
			Alarms.getInstance().raise(Alarms.DSA.DB_UNAVAILABLE, 1);
			Alarms.getInstance().raise(Alarms.DSA.CACHE_REFRESH_FAIL, 1);
			logger.error(ex.getMessage());
			logger.debug(ex.toString());
		}
		Alarms.getInstance().clear(Alarms.DSA.DB_UNAVAILABLE, 1);
	}

	private void reloadCache() {
		discountDao.updateDiscount();// evict cache
		discountDao.updateDiscountByRatePlanId(); // evict cache
		cefCriteriaDao.updateCriteriaEvalId(); // evict cache
		cefCriteriaDao.updateCefCriteriaKeyLists(); // evict cache
		vuRatePlanDao.updateRatePlan();// evict cache
		discountDao.updateStandardDiscount(); //evict cache 
		discountDao.updateAutoPayEnhancedDiscount();
		autoPayDiscountDetailsDaoImpl.updateAutoPayDiscountDetails();
		discountDao.updateEarlierDateDisCountCode();		
	}

}
