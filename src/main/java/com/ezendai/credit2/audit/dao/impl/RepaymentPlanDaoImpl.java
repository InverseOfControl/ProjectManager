package com.ezendai.credit2.audit.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.RepaymentPlanDao;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/***
 * 还款计划
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: RepaymentPlanDaoImpl.java, v 0.1 2014年6月23日 下午4:12:56 HQ-AT6 Exp $
 */
@Repository
public class RepaymentPlanDaoImpl extends BaseDaoImpl<RepaymentPlan> implements RepaymentPlanDao {

	@Override
	public int insertRepaymentPlan(RepaymentPlan record) {
		return 0;
	}

	/***
	 * 根据贷款ID查询还款记录
	 * @param loanId
	 * @return
	 * @see com.ezendai.credit2.apply.dao.RepaymentPlanDao#getRepaymentPlanByLoanId(java.lang.Long)
	 */
	@Override
	public List<RepaymentPlan> getRepaymentPlanByLoanId(Long loanId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".getByLoanId", loanId);
	}
	/**
	 * 
	 * <pre>
	 * 查询需要提取的风险金金额
	 * </pre>
	 *
	 * @param repaymentPlanVo
	 * @return
	 */
	@Override
	public BigDecimal getRemainingPrincipalByVO(RepaymentPlanVO repaymentPlanVo){
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getRemainingPrincipalByVO", repaymentPlanVo);
	}
	@Override
	public int insertSelective(RepaymentPlan record) {
		return 0;
	}

	@Override
	public int updateById(RepaymentPlan record) {
		return this.getSqlSession().update(getIbatisMapperNameSpace() + ".updateById", record);
	}

	@Override
	public List<RepaymentPlan> queryDetailFee(Long loanId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryDetailFee", loanId);
	}

	@Override
	public void deleteRepaymentPlanByLoanId(Long loanId) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteRepaymentPlanByLoanId", loanId);
	}
	
	@Override
	public int updateByStatus(RepaymentPlanVO repaymentPlanVo) {
		return this.getSqlSession().update(getIbatisMapperNameSpace() + ".updateByStatus", repaymentPlanVo);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.audit.dao.RepaymentPlanDao#getIdByLoanIdCurNum(java.util.Map)
	 */
	@Override
	public RepaymentPlan getByLoanIdAndCurNum(RepaymentPlanVO repaymentPlanVo) {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getByLoanIdAndCurNum", repaymentPlanVo);
	}

	@Override
	public List<RepaymentPlan> getRepaymentPlanExtends(
			RepaymentPlanVO repaymentPlanVo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".getRepaymentPlanExtends", repaymentPlanVo);
	}

}