package com.ezendai.credit2.after.dao;

import java.util.List;

import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface SpecialRepaymentDao extends BaseDao<SpecialRepayment>{
	/**
	 * 通过传入loan的创建时间来关联特殊还款表
	 * @param specialRepaymentVo
	 * @return
	 * @see com.ezendai.credit2.after.dao.SpecialRepaymentDao#selectListByParams(com.ezendai.credit2.after.vo.SpecialRepaymentVO)
	 */
	List<Long> selectListByParams(SpecialRepaymentVO specialRepaymentVo);
	
	/**
	 * 
	 * <pre>
	 * 根据loanID和type更新特殊还款表状态
	 * </pre>
	 *
	 * @param specialRepaymentVo
	 * @return
	 */
	int updateSpecialRepaymentState(SpecialRepaymentVO specialRepaymentVo);

	/** 通过查询vo查找符合条件的SpecialRepayment集合 */
	List<SpecialRepayment> findListByVOWihtUnion(SpecialRepaymentVO specialRepaymentVO);

	Pager findListByVOWihtExtend(SpecialRepaymentVO specialRepaymentVO);

	Pager findListByVOWihtBaseExtend(SpecialRepaymentVO specialRepaymentVO);
}
