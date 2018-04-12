package com.ezendai.credit2.after.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.util.Pager;

public interface SpecialRepaymentService {
	/** 插入SpecialRepayment */
	SpecialRepayment insert(SpecialRepayment specialRepayment);

	/** 根据ID，删除SpecialRepayment */
	void deletedById(Long id);

	/** 根据I的List，删除SpecialRepayment */
	void deletedByIdList(SpecialRepaymentVO specialRepaymentVO);

	/** 更新SpecialRepayment */
	int update(SpecialRepaymentVO specialRepaymentVO);

	/** 根据id,查找SpecialRepayment */
	SpecialRepayment get(Long id);

	/** 通过查询vo查找符合条件的SpecialRepayment集合 */
	List<SpecialRepayment> findListByVo(SpecialRepaymentVO specialRepaymentVO);

	/** 通过查询vo查找符合条件的SpecialRepayment集合 */
	Pager findWithPg(SpecialRepaymentVO specialRepaymentVO);

	/**
	 * <pre>
	 *  根据借款ID判断是否申请过一次性还款，且是否处于申请状态
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	boolean isOneTimeRepayment(Long loanId);
	
	/**
	 * 
	 * <pre>
	 * 根据借款ID,判断是否申请过提前还款,且是出于"申请"状态
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	boolean isInAdvanceRepayment(Long loanId);
	
	/**
	 * <pre>
	 * 通过传入借款ID和日期得到罚息减免的金额
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	BigDecimal getReliefOfFine(Date currDate, Long loanId);
	
	/***
	 * 
	 * <pre>
	 * 通过传入loan的创建时间来关联特殊还款表
	 * </pre>
	 *
	 * @param specialRepaymentVO
	 * @return
	 */
	List<Long> findListByParams(SpecialRepaymentVO specialRepaymentVO);
	
	/**
	 * 
	 * <pre>
	 * 根据loanID和type更新特殊还款表状态
	 * </pre>
	 *
	 * @param loanId
	 * @param type
	 * @return
	 */
	int updateSpecialRepaymentState(Integer state, Long loanId,Integer type);
	
	/**
	 * 
	 * <pre>
	 * 申请【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String submitRepayInAdvance(Long loanId);
	/**
	 * 
	 * <pre>
	 * 批量申请【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String plSubmitRepayInAdvance(Long loanId);
	
	
	/**
	 * 
	 * <pre>
	 * 申请【提前扣款】-展期批准后自动添加
	 * </pre>
	 *
	 * @param loanId
	 * @param extensionApproveFlag
	 * @return
	 */
	String submitRepayInAdvance(Long loanId,boolean extensionApproveFlag);
	
	/**
	 * 
	 * <pre>
	 * 批量申请【提前扣款】-展期批准后自动添加
	 * </pre>
	 *
	 * @param loanId
	 * @param extensionApproveFlag
	 * @return
	 */
	String plSubmitRepayInAdvance(Long loanId,boolean extensionApproveFlag);
	
	/**
	 * 
	 * <pre>
	 * 取消【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String cancelRepayInAdvance(Long loanId);
	/**
	 * 
	 * <pre>
	 * 批量取消【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String plCancelRepayInAdvance(Long loanId);
	
	
	/**
	 * 
	 * <pre>
	 * 申请【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String submitRepayOneTime(Long loanId);
	/**
	 * 
	 * <pre>
	 * 批量申请【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String plSubmitRepayOneTime(Long loanId);
	
	/**
	 * 
	 * <pre>
	 * 取消【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String cancelRepayOneTime(Long loanId);
	/**
	 * 
	 * <pre>
	 * 批量取消【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String plCancelRepayOneTime(Long loanId);
	
	/**
	 * 
	 * <pre>
	 * 申请【罚息减免】
	 * </pre>
	 *
	 * @param loanId
	 * @param amount
	 * @return
	 */
	String submitPenaltyReduce(Long loanId, BigDecimal amount, Integer extensionTime);
	
	/**
	 * 
	 * <pre>
	 * 取消【罚息减免】
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	String cancelPenaltyReduce(Long loanId);
	
	/**
	 * 
	 * <pre>
	 * 获取某借款特殊申请的申请人ID
	 * </pre>
	 *
	 * @param loanId
	 * @param specType	特殊申请类型(提前扣款、一次性结清)
	 * @return
	 */
	Long getProposerID(Long loanId, Integer specType);
	
	/**
	 * 
	 * <pre>
	 * 重置还款计划值
	 * </pre>
	 *
	 * @param repaymentPlanVO
	 * @param repayMount 应还总额
	 * @param pactMoney 还款本金
	 */
	void resetRepaymentPlan(RepaymentPlanVO repaymentPlanVO, BigDecimal repayMount, BigDecimal pactMoney);
	
	/** 通过查询vo查找符合条件的SpecialRepayment集合 */
	List<SpecialRepayment> findListByVOWihtUnion(SpecialRepaymentVO specialRepaymentVO);
	/**
	 * 
	 * <pre>
	 * 通过查询vo获取特殊还款表关联loan表状态为130和140的交易
	 * </pre>
	 *
	 * @param specialRepaymentVO
	 * @return
	 */
	Pager findListByVOWihtExtend(SpecialRepaymentVO specialRepaymentVO);
	
	/**
	 * 
	 * <pre>
	 * 通过查询vo获取特殊还款表关联loan表的信息
	 * </pre>
	 *
	 * @param specialRepaymentVO
	 * @return
	 */
	Pager findListByVOWihtBaseExtend(SpecialRepaymentVO specialRepaymentVO);
	/**
	 * 
	 * <pre>
	 * 申请【停止报盘】
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	String submitAbrogateGene(Long loanId);
	/**
	 * 
	 * <pre>
	 * 取消【停止报盘】
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	String cancelAbrogateOffer(Long id);
	/**
	 * CAR_FINE_NEW_CAL_EXECUTE_TIME  配置的时间后启用
	 * <pre>
	 * 申请【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	String submitRepayOneTimeNew(Long loanId);
}
