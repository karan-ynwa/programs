package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class EbiCacheConfigUpdateInfoRecord implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -3674950511183560266L;

	/**
	 * 
	 */

	/**
	 * 
	 */
	
	private Integer cacheConfigUpdateId; // NUMBER

	private Long forceCacheRefershInterval; // NUMBER

	private Timestamp lastCacheConfigUpdateTS; // TIMESTAMP

	public Integer getCacheConfigUpdateId() {
		return cacheConfigUpdateId;
	}

	public void setCacheConfigUpdateId(Integer cacheConfigUpdateId) {
		this.cacheConfigUpdateId = cacheConfigUpdateId;
	}

	public Long getForceCacheRefershInterval() {
		return forceCacheRefershInterval;
	}

	public void setForceCacheRefershInterval(Long forceCacheRefershInterval) {
		this.forceCacheRefershInterval = forceCacheRefershInterval;
	}

	public Timestamp getLastCacheConfigUpdateTS() {
		return lastCacheConfigUpdateTS;
	}

	public void setLastCacheConfigUpdateTS(Timestamp lastCacheConfigUpdateTS) {
		this.lastCacheConfigUpdateTS = lastCacheConfigUpdateTS;
	}

	@Override
	public String toString() {
		return "EbiCacheConfigUpdateInfoRecord [cacheConfigUpdateId=" + cacheConfigUpdateId
				+ ", forceCacheRefershInterval=" + forceCacheRefershInterval + ", lastCacheConfigUpdateTS="
				+ lastCacheConfigUpdateTS + "]";
	}

	
}
