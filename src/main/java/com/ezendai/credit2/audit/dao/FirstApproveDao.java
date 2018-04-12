package com.ezendai.credit2.audit.dao;

import java.util.Map;

import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface FirstApproveDao extends BaseDao<FirstApprove> {
	
	 
	/**
	 * <pre>
	 * 初审-审核列表
	 * @param returnDate
	 * @return
	 */
	Pager findFirstApproveWithPG(FirstApproveVO vo);
	
	String getAcceptAudit(long id);
	
	void updateAcceptAudit(Map map);
	
	int selectSysUserCount(String code);

	
}
