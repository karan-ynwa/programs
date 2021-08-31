package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IEbiCacheConfigDao;
import com.ericsson.eus.aps.dao.model.EbiCacheConfigUpdateInfoRecord;

@Repository("ebiCacheConfigDao")
public class EbiCacheConfigDaoImpl extends DaoBase implements IEbiCacheConfigDao {
	private static final String QUERY_EBI_CACHE_CONFIG_UPDATE_INFO = "SELECT * FROM EBI_CACHE_CONFIG_UPDATE_INFO";
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String CACHE_CONFIG_UPDATE_ID = "CACHE_CONFIG_UPDATE_ID";
	public static final String FORCE_CACHE_REFRESH_INTERVAL = "FORCE_CACHE_REFRESH_INTERVAL";
	public static final String LAST_CACHE_CONFIG_UPDATE_TS = "LAST_CACHE_CONFIG_UPDATE_TS";

	/**
	 * Get EBI_CACHE_CONFIG_UPDATE_INFO
	 * 
	 * @param msisdn
	 * @return
	 * @throws DiscountDaoException
	 * @throws BEADaoException
	 */
	@Override
	public EbiCacheConfigUpdateInfoRecord getEbiCacheConfigUpdateInfo() throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_EBI_CACHE_CONFIG_UPDATE_INFO);
		}
		return (EbiCacheConfigUpdateInfoRecord) doQueryForObject(QUERY_EBI_CACHE_CONFIG_UPDATE_INFO, null,
				new EbiCacheConfigUpdateInfoRecordRowMapper());
	}

	public class EbiCacheConfigUpdateInfoRecordRowMapper implements RowMapper<EbiCacheConfigUpdateInfoRecord> {

		@Override
		public EbiCacheConfigUpdateInfoRecord mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			EbiCacheConfigUpdateInfoRecord object = new EbiCacheConfigUpdateInfoRecord();
			object.setCacheConfigUpdateId(resultSet.getInt(EbiCacheConfigDaoImpl.CACHE_CONFIG_UPDATE_ID));
			object.setForceCacheRefershInterval(resultSet.getLong(EbiCacheConfigDaoImpl.FORCE_CACHE_REFRESH_INTERVAL));
			object.setLastCacheConfigUpdateTS(
					resultSet.getTimestamp(EbiCacheConfigDaoImpl.LAST_CACHE_CONFIG_UPDATE_TS));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
