package com.ezendai.credit2.audit.dao;

import java.math.BigDecimal;
import java.util.List;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface RepaymentPlanDao extends BaseDao<RepaymentPlan> {

	public int insertRepaymentPlan(RepaymentPlan record);

	public int insertSelective(RepaymentPlan record);

	public int updateById(RepaymentPlan record);

	public List<RepaymentPlan> getRepaymentPlanByLoanId(Long loanId);

	List<RepaymentPlan> queryDetailFee(Long loanId);

	void deleteRepaymentPlanByLoanId(Long loanId);
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
	
	int updateByStatus(RepaymentPlanVO repaymentPlanVo);

	/**
	 * 
	 * <pre>
	 * 根据loanId、curNum查询某repaymentPlan记录
	 * </pre>
	 *
	 * @param map
	 * @return
	 */
	RepaymentPlan getByLoanIdAndCurNum(RepaymentPlanVO repaymentPlanVo);
	
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