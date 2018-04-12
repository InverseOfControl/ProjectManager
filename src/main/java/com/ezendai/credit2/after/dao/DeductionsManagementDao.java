package com.ezendai.credit2.after.dao;

import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface DeductionsManagementDao extends BaseDao<Offer>{
	/**
	 * 检验筛选符合条件的数据
	 *
	 */
	Integer isCheckData(Long loanId);
}
