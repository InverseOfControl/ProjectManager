package com.ezendai.credit2.sign.lcb.dao;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.PushZonganFraud;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface ZonganRiskDao extends BaseDao<PushZonganFraud>{

	void insertZonganReturnData(Map<String,Object> map);
	
	Integer findLoanIdAndStatus(String id);
	
	/**
	 * 审贷会决议那边，回显调用捞财宝众安反欺诈返回的值
	 */
	Map<String,Object> getZongAnReturnData(String loanID);
	
	/**
	 * 查询客户车贷不属于当前这次车贷的其他车贷贷款
	 */
	Map<String,Object> findLoanMoneyNoBelongTo(String idNo,String laonId);
}
