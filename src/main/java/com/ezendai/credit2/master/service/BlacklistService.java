package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.vo.BlacklistVO;

/**
 * 黑服务
 * 
 * @author chenqi
 * @version 1.0, 2014-08-27
 * @since 1.0
 */
public interface BlacklistService {
	
	
	/**
	 * <pre>
	 *  新增黑名单
	 * </pre>
	 *
	 * @param blacklist
	 * @return
	 */
	Blacklist insert(Blacklist blacklist);
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param blacklistVO
	 * @return
	 */
	List<Blacklist> findListByVo(BlacklistVO blacklistVO);
	
	
	/**
	 * <pre>
	 * 根据借款条件判断是否符合黑名单条件
	 * </pre>
	 *
	 * @param loanDetailsVo
	 */
	void checkBlacklist(LoanDetailsVO loanDetailsVo);


	int updateLimitDays(BlacklistVO blacklistVO); 
}
