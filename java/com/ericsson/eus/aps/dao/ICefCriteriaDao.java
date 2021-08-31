package com.ericsson.eus.aps.dao;

import java.util.List;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.CefCriteriaEvaluations;
import com.ericsson.eus.aps.dao.model.CefCriteriaKeyLists;

public interface ICefCriteriaDao {

	List<CefCriteriaEvaluations> getCriteriaEvalId(Integer criteriaEvalId) throws AutopayDaoException;

	List<CefCriteriaKeyLists> getCefCriteriaKeyLists(Integer keyListId) throws AutopayDaoException;

	public void updateCriteriaEvalId();

	public void updateCefCriteriaKeyLists();
}
