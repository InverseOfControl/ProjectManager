package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.audit.assembler.RepaymentPlanAssembler;
import com.ezendai.credit2.audit.dao.RepaymentPlanDao;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;

@Service
public class RepaymentPlanServiceImpl implements RepaymentPlanService {

	@Autowired
	private RepaymentPlanDao repaymentPlanDao;

	@Transactional
	@Override
	public RepaymentPlan insert(RepaymentPlanVO repaymentPlanVO) {
		RepaymentPlan RepaymentPlan = RepaymentPlanAssembler.transferVO2Model(repaymentPlanVO);
		return repaymentPlanDao.insert(RepaymentPlan);
	}

	@Override
	public List<RepaymentPlan> queryRepaymentPlans(Long loanId) {
		return repaymentPlanDao.queryDetailFee(loanId);
	}

	@Transactional
	@Override
	public void deleteRepaymentPlanByLoanId(Long loanId) {
		repaymentPlanDao.deleteRepaymentPlanByLoanId(loanId);
	}

	@Override
	public List<RepaymentPlan> findListByVO(RepaymentPlanVO repaymentPlanVO) {
		return repaymentPlanDao.findListByVo(repaymentPlanVO);
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
		return repaymentPlanDao.getRemainingPrincipalByVO(repaymentPlanVo);
	}

	@Override
	public int update(RepaymentPlanVO repaymentPlanVO) {
		return repaymentPlanDao.update(repaymentPlanVO);
	}

	@Override
	public RepaymentPlan get(Long repaymentPlanId) {
		return repaymentPlanDao.get(repaymentPlanId);
	}

	@Override
	public int updateByStatus(RepaymentPlanVO repaymentPlanVO) {
		return repaymentPlanDao.updateByStatus(repaymentPlanVO);
	}

	/** 
	 * @param loanId
	 * @param curNum
	 * @return
	 * @see com.ezendai.credit2.audit.service.RepaymentPlanService#getIdByLoanIdCurNum(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public RepaymentPlan getByLoanIdAndCurNum(Long loanId, Integer curNum) {
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setCurNum(curNum);
		return repaymentPlanDao.getByLoanIdAndCurNum(repaymentPlanVO);
	}

	@Override
	public List<RepaymentPlan> getRepaymentPlanExtends(
			RepaymentPlanVO repaymentPlanVo) {
		// TODO Auto-generated method stub
		return repaymentPlanDao.getRepaymentPlanExtends(repaymentPlanVo);
	}

}
