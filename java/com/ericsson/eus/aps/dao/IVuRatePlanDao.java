package com.ericsson.eus.aps.dao;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.VuRatePlan;

public interface IVuRatePlanDao {


	VuRatePlan getRatePlan(Long ratePlanId) throws AutopayDaoException;
	public void updateRatePlan();


}
