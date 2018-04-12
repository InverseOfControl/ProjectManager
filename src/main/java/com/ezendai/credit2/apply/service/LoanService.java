package com.ezendai.credit2.apply.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;

/**
 * @author zhuyiguo
 * @version $Id: LoanService.java, v 0.1 2014年6月26日 上午9:14:21 zhuyiguo Exp $
 */
public interface LoanService {

	Loan insert(Loan loan);

	void deleteById(Long id);

	void deleteByIdList(LoanVO loanVO);

	int update(LoanVO loanVO);

	Loan get(Long id);

	List<Loan> findListByVO(LoanVO loanVO);
	
	List<Loan> findListByVOExtension(LoanVO loanVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(LoanVO loanVO);

	Loan get(LoanVO loanVO);

	boolean exists(Long id);

	boolean canEdit(Loan loan, Long userId);

	boolean canSubmit(Loan loan, Long userId);

	void submit(LoanVO loanVO, Long userId);

	boolean canApprove(Loan loan, Long userId);
	
	boolean checkManager(Long serviceId, Long managerId, Long assessorId);
	
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
	 * 
	 * <pre>
	 * 生成客户编号
	 * </pre>
	 *
	 * @param serialNum
	 * @return
	 */
	public String getPersonNoByType(SerialNum serialNum,Long salesDeptId);
	
	/**
	 * 
	 * <pre>
	 * 是否可以进行合同签约操作
	 * 
	 * 合同签约的条件：
	 * 1.已经执行生成合同操作
	 * 2.合同的状态是合同签订
	 * </pre>
	 *
	 * @param loan
	 * @param userId
	 * @return
	 */
	boolean canContractSign(Loan loan, Long userId);
	
	
	/**
	 * <pre>
	 * 是否可以进行展期合同签约操作
	 * 展期合同签约的条件：
	 * 1.已经执行生成展期合同操作
	 * 2.展期合同的状态是展期合同签订
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canExtensionContractSign(Loan loan);
	
	/**
	 * 
	 * <pre>
	 * 是否可以进行合同提交操作
	 * 
	 * 合同提交的条件：
	 * 状态为合同确认退回/财务审核 退回/财务放款退回
	 * </pre>
	 *
	 * @param loan
	 * @param userId
	 * @return
	 */
	boolean canContractSubmit(Loan loan, Long userId);
	
	
	
	/**
	 * <pre>
	 * 是否可以进行展期合同提交操作
	 * 展期合同提交的条件：
	 * 状态为展期合同确认退回
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canExtensionContractSubmit(Loan loan);
	
	/**
	 * 
	 * <pre>
	 * 获取符合条件的loan总条数
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int getCountByLoanVO(LoanVO loanVO);

	/**
	 * <pre>
	 * 批量更新借款
	 * </pre>
	 * @param loanVO
	 * @return
	 */
	String updateByIdList(LoanVO loanVO);
	
	/**
	 * 
	 * <pre>
	 * 变更管理之分页查询
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager changeManageFindWithPG(LoanVO loanVO);
	/**
	 * <pre>
	 * 还款试算查询
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findRepayTrialWithPG(LoanVO loanVO);
	
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
	 * <pre>
	 * 判断是否能够申请展期
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canExtension(Loan loan);
	
	
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
	 * 借款数据和展期数据的合并抽取
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
	 * <pre>
	 * 生成展期表和关联表数据
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Map<String,String> createdExtension(LoanVO loanVO);
	

	/**
	 * <pre>
	 * 判断是否能够展期签批
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canExtensionApprove(Loan loan);
	
	
	/**
	 * <pre>
	 * 判断是否能够展期提交
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canExtensionSubmit(Loan loan);
	
	
	/**
	 * <pre>
	 * 展期提交
	 * </pre>
	 *
	 * @param extensionVO
	 */
	void extensionSubmit(ExtensionVO extensionVO);
	
	/**
	 * 
	 * <pre>
	 * 根据loanVO的ID，获取Loan或Extension，依此修改
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int updateLoanOrExtension(LoanVO loanVO);
	
	
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
	 * 判断借款是否结清适用于loan和extension
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	boolean isCloseOut(Long id);
	
	/**
	 * <pre>
	 * 判断是否能够取消借款
	 * 非展期合同,借款状态在合同确认前(合同状态小于合同确认(70))可以取消
	 * </pre>
	 *
	 * @param loan
	 * @return
	 */
	boolean canCancelLoan(Loan loan);
	
	/**
	 * 根据personId 获取最新一次贷款
	 * @param personId
	 * @param contacterList
	 * @param guaranteeList
	 */
	Loan getLatestLoanByperson(Long personId);
	/**
	 * 批量分配
	 * @param loanVO
	 * 
	 * 
	 */
	String updateLoanByIdList(LoanVO vo);
	/**
	 * 借款恢复,超时自动取消的借款可显示恢复功能
	 * @param loan
	 * @param userId
	 * @return
	 */
	boolean canRestoreLoan(Loan loan,Long userId);

	/**
	 * 提交需要的恢复借款
	 * @param loan
	 * @param userId
	 * @return
	 */
	boolean canSubmitRestore(Loan loan, Long userId);
	
	
	/**
	 * 查询初审已处理的单子
	 * @param loanVO
	 * @return
	 */
	List<Loan> alreadyProcessedList(LoanVO loanVO);
	
	/**
	 * 更新众安反欺诈值置为不可用 
	 */
	void updatePushLcbReturnFraud(Map<String,Object> map);

	/**
	 * 根据身份证号 查找未结清金额
	 * @param idNum 身份证号
	 * @return
	 */
	JSONObject findResidualPactMoney(String name, String idNum);
}
