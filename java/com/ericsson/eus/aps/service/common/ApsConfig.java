package com.ericsson.eus.aps.service.common;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ericsson.eus.aps.dao.model.EbiCacheConfigUpdateInfoRecord;

/**
 * 
 * @author eznraps
 *
 */

@Configuration
@PropertySource("classpath:ext/application.properties")
@PropertySource(value = "file:./data/ext/application.properties", ignoreResourceNotFound = true)
public class ApsConfig {


	@Value( "${cache.refresh.period}" )
	private Long cacheRefreshPeriod = 60000l;	// (in MS)

	
	
	@Value( "${cache.reload.enabled}" )
	private Boolean cacheReloadEnabled = Boolean.TRUE ;

	@Value("${fixed_amount_topupcycle_8}")
	private String fixedAmountTopupcycle8 = "25";

	private static final Date appStartTime =  Calendar.getInstance().getTime();
	
	private static EbiCacheConfigUpdateInfoRecord ebiCacheConfigUpdateInfoRecordLocal;
	
	
	public static EbiCacheConfigUpdateInfoRecord getEbiCacheConfigUpdateInfoRecordLocal() {
		return ebiCacheConfigUpdateInfoRecordLocal;
	}


	public static void setEbiCacheConfigUpdateInfoRecordLocal(EbiCacheConfigUpdateInfoRecord ebiCacheConfigUpdateInfoRecordLocal) {
		ApsConfig.ebiCacheConfigUpdateInfoRecordLocal = ebiCacheConfigUpdateInfoRecordLocal;
	}

	public static Date getAppstarttime() {
		return appStartTime;
	}


	public Long getCacheRefreshPeriod() {
		return cacheRefreshPeriod;
	}


	public void setCacheRefreshPeriod(Long cacheRefreshPeriod) {
		this.cacheRefreshPeriod = cacheRefreshPeriod;
	}


	public Boolean getCacheReloadEnabled() {
		return cacheReloadEnabled;
	}


	public void setCacheReloadEnabled(Boolean cacheReloadEnabled) {
		this.cacheReloadEnabled = cacheReloadEnabled;
	}

	/**
	 * @return the fixedAmountTopupcycle8
	 */
	public String getFixedAmountTopupcycle8() {
		return fixedAmountTopupcycle8;
	}

	/**
	 * @param fixedAmountTopupcycle8
	 *            the fixedAmountTopupcycle8 to set
	 */
	public void setFixedAmountTopupcycle8(String fixedAmountTopupcycle8) {
		this.fixedAmountTopupcycle8 = fixedAmountTopupcycle8;
	}
}
