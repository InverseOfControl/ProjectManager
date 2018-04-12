package com.ezendai.credit2.audit.service;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;

public interface RepaymentPlanService {

	RepaymentPlan insert(RepaymentPlanVO repaymentPlanVO);

	List<RepaymentPlan> queryRepaymentPlans(Long loanId);

	void deleteRepaymentPlanByLoanId(Long loanId);

	List<RepaymentPlan> findListByVO(RepaymentPlanVO repaymentPlanVO);
	/**
	 * 
	 * <pre>
	 * 查询需要提取的风险金金额
	 * </pre>
	 *
	 * @param repaymentPlanVo
	 * @return
	 */
	BigDecimal getRemainingPrincipalByVO(RepaymentPlanVO repaymentPlanVo);
	
	/**
	 * 
	 * <pre>
	 * 根据VO更新还款计划
	 * </pre>
	 *
	 * @param repaymentPlanVO
	 * @return
	 */
	int update(RepaymentPlanVO repaymentPlanVO);
	
	/**
	 * 
	 * <pre>
	 * 根据repaymentPlanId获得repaymentPlan
	 * </pre>
	 *
	 * @param repaymentPlanId
	 * @return
	 */
	RepaymentPlan get(Long repaymentPlanId);
	
	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param repaymentPlanVO
	 * @return
	 */
	int updateByStatus(RepaymentPlanVO repaymentPlanVO);

	/**
	 * 
	 * <pre>
	 * 根据loanId、curNum获取某repaymentPlan记录
	 * </pre>
	 *
	 * @param loanId
	 * @param curNum
	 * @return
	 */
	RepaymentPlan getByLoanIdAndCurNum(Long loanId, Integer curNum);
	
	/**
	 * 
	 * <pre>
	 * 根据repaymentPlanVo查询某台账记录
	 * </pre>
	 *
	 * @param map
	 * @return
	 */
	
	List<RepaymentPlan> getRepaymentPlanExtends(RepaymentPlanVO repaymentPlanVo);
}
