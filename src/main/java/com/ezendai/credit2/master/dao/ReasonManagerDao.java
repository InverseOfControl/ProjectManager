package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.RefusedReason;
import com.ezendai.credit2.master.vo.RefusedReasonVO;

public interface ReasonManagerDao extends BaseDao<RefusedReason> {

	public Pager getReasonList(RefusedReasonVO vo);
	
	public List<RefusedReason> findAllParentReasonList();
	
	public int addReason(RefusedReasonVO vo);
	
	public RefusedReason getReasonById(Long id);
	
	public int updateReason(RefusedReasonVO vo);
	
	public int disableReason(RefusedReasonVO reason);
}
