package com.ericsson.eus.aps.cache;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.eus.aps.dao.IAutoPayDiscountDetailsDao;
import com.ericsson.eus.aps.dao.ICefCriteriaDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.IEbiCacheConfigDao;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.common.ApsConfig;


@Component
public class CacheQuartzListener {

	@Autowired
	private ApsConfig config ;
	
	@Autowired
	IEbiCacheConfigDao ebiCacheConfigDao;
	
	@Autowired
	IDiscountDao discountDao;
	
	@Autowired
	private ICefCriteriaDao cefCriteriaDao;
	
	@Autowired
	private IVuRatePlanDao vuRatePlanDao;
	
	@Autowired
	private IAutoPayDiscountDetailsDao autoPayDiscountDetailsDao;
	
	
	private static Logger logger = LogManager.getLogger(CacheQuartzListener.class);
	
	
	@PostConstruct
	public void init() {
		Long cacheRefreshPeriod = config.getCacheRefreshPeriod()/60000;	// ms to minutes
		logger.debug("Before scheduler");
		
		try {
			
			if(config.getCacheReloadEnabled())
			{
				logger.debug("setting up scheduler");
			
				JobDetail job = JobBuilder.newJob(CacheRefreshJob.class)
						.withIdentity("CacheRefreshJob", "CacheRefreshGroup").build();
				
				job.getJobDataMap().put(ApsConstants.EBI_CACHE_CONFIG_UPDATE_INFO_DAO, ebiCacheConfigDao);
				job.getJobDataMap().put(ApsConstants.DISCOUNT_DAO, discountDao);
				job.getJobDataMap().put(ApsConstants.CEF_CRITERIA_DAO, cefCriteriaDao);
				job.getJobDataMap().put(ApsConstants.VU_RATE_PLAN_DAO, vuRatePlanDao);
				job.getJobDataMap().put(ApsConstants.AUTO_PAY_DISCOUNT_DETAILS_DAO, autoPayDiscountDetailsDao);
				// Set the scheduler timings.
				Trigger trigger = TriggerBuilder
						.newTrigger()
						.withIdentity("CacheRefreshTrigger", "CacheRefreshGroup")
						.withSchedule(CronScheduleBuilder.cronSchedule("0 0/"+cacheRefreshPeriod+" * * * ?")).build(); 
				
				// Execute the job.
				
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();

				if (scheduler.checkExists(job.getKey())) {
					scheduler.deleteJob(job.getKey());
					reschedulejob();
				} else {
					scheduler.start();
					scheduler.scheduleJob(job, trigger);
				}

			}else {
				logger.error("Cache refresh is set to false in application.properties");
			}
			
		}
		

		catch (JobExecutionException e) {

			logger.error("Error in Scheduling APS Jobs while scheduling jobs in quartz");
		}

		catch (Exception e) {

			logger.error("Error in Scheduling APS Jobs in CacheQuartzListener");
		}

	}

	public void destroy() {
		logger.info("destroying context");
	}

	public void reschedulejob() {
		init();
	}
	

}
