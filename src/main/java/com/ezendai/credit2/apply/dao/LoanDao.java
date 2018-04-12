package com.ezendai.credit2.apply.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author zhuyiguo
 * @version $Id: LoanDao.java, v 0.1 2014年6月26日 上午9:15:02 zhuyiguo Exp $
 */
public interface LoanDao extends BaseDao<Loan> {
	
	/** 获取符合条件的借款总数 */
	int loanCount(LoanVO loanVO);
	
	/** 批量更新Loan */
	int updateLoanByIdList(LoanVO loanVO);

	/** 变更管理之分页查询 */
	Pager changeManageFindWithPG(LoanVO loanVO);
	
	/**
	 * 
	 * <pre>
	 * 特殊还款查询
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager specialRepaymentFindWithPG(LoanVO loanVO);
	
	/**
	 * <pre>
	 * 试算查询
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findRepayTrialWithPG(LoanVO loanVO);
	/**
	 * <pre>
	 * 更新状态不是某个状态的记录
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int updateLoanByStatus(LoanVO loanVO);
	/**
	 * <pre>
	 * 还款录入查询
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findRepayEntryWithPG(LoanVO loanVO);
	/**
	 * 
	 * <pre>
	 * 对公还款->领取借款记录列表
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findBusinessLoanListWithPg(LoanVO loanVO);
	
	/**
	 * 
	 * <pre>
     * 约定还款日为当日日期且状态为“正常”的借款
     * 所有逾期的记录（loan状态为逾期）
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	List<Long> findListByVOWithUnion(LoanVO loanVO);
	
	
	/**
	 * <pre>
	 * 借款数据和展期数据的合并抽取
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findWithPGUnionExtension(LoanVO loanVO);
	/**
	 * <pre>
	 * 借款数据和展期数据的合并抽取(批量)
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager plFindWithPGUnionExtension(LoanVO loanVO);
	
	/**
	 * 
	 * <pre>
	 * 借款数据、展期数据的合并集合
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	List<Loan> findListByVOUnionExtension(LoanVO loanVO);
	
	/**
	 * 
	 * <pre>
	 * 查询最后一期是逾期的记录
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	List<Loan> findLastOverdueList(LoanVO loanVO);
	/**
	 * 
	 * <pre>
	 *通过查询vo查找符合条件的数据行(Extension版)：{mapper.xml需要实现} 
	 * </pre>
	 *
	 * @param loanvo
	 * @return
	 */
	List<Loan> findListByVoExtension(LoanVO vo);
	
	
	/**
	 *查询 初审已处理的单子
	 * @param loanVO
	 * @return
	 */
	List<Loan> alreadyProcessedList(LoanVO loanVO);
	
	
	/**
	 * 把返回的众安反欺诈的值置为不可用
	 */
	void updatePushLcbReturnFraud(Map<String,Object> map);

	/**
	 * 根据身份证号查找未结清金额
	 * @param idNum
	 * @return
	 */
	BigDecimal findResidualPactMoney(String idNum);
}
