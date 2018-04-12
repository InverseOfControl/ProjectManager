package com.ezendai.credit2.after.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.after.dao.SpecialRepaymentDao;
import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;

@Service
public class SpecialRepaymentServiceImpl implements SpecialRepaymentService {
	@Autowired
	private SpecialRepaymentDao specialRepaymentDao;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private SysParameterService sysParameterService;

	@Override
	public SpecialRepayment insert(SpecialRepayment specialRepayment) {
		return specialRepaymentDao.insert(specialRepayment);
	}

	@Override
	public void deletedById(Long id) {
		specialRepaymentDao.deleteById(id);
	}

	@Override
	public void deletedByIdList(SpecialRepaymentVO specialRepaymentVO) {
		specialRepaymentDao.deleteByIdList(specialRepaymentVO);
	}

	@Override
	public int update(SpecialRepaymentVO specialRepaymentVO) {
		return specialRepaymentDao.update(specialRepaymentVO);
	}

	@Override
	public SpecialRepayment get(Long id) {
		return specialRepaymentDao.get(id);
	}

	@Override
	public List<SpecialRepayment> findListByVo(SpecialRepaymentVO specialRepaymentVO) {
		return specialRepaymentDao.findListByVo(specialRepaymentVO);
	}
	/***
	 * 
	 * <pre>
	 * 通过传入loan的创建时间来关联特殊还款表
	 * </pre>
	 *
	 * @param specialRepaymentVO
	 * @return
	 */
	@Override
	public List<Long> findListByParams(SpecialRepaymentVO specialRepaymentVO) {
		return specialRepaymentDao.selectListByParams(specialRepaymentVO);
	}
	@Override
	public Pager findWithPg(SpecialRepaymentVO specialRepaymentVO) {
		return specialRepaymentDao.findWithPg(specialRepaymentVO);
	}

	@Override
	public boolean isOneTimeRepayment(Long loanId) {
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = specialRepaymentDao.findListByVo(specialRepaymentVO);
		if (specialRepaymentList != null && specialRepaymentList.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public BigDecimal getReliefOfFine(Date currDate, Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		if (currDate == null || loanId == null)
			return result;
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setRequestDate(currDate);
		specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = specialRepaymentDao.findListByVo(specialRepaymentVO);
		if (specialRepaymentList != null && specialRepaymentList.size() == 1) {
			BigDecimal amount = specialRepaymentList.get(0).getAmount();
			return amount;
		}
		return result;

	}

	/** 
	 * @param loanId
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#isInAdvanceRepayment(java.lang.Long)
	 */
	@Override
	public boolean isInAdvanceRepayment(Long loanId) {
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = specialRepaymentDao.findListByVo(specialRepaymentVO);
		// 如果该loanId代表的借款，申请过"提前扣款",且状态为"申请",返回true,否则返回false;
		if(specialRepaymentList !=  null && specialRepaymentList.size() == 1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int updateSpecialRepaymentState(Integer state, Long loanId, Integer type) {
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setType(type);
		specialRepaymentVO.setStatus(state);
		specialRepaymentVO.setModifiedTime(new Date());
		specialRepaymentVO.setNotStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		if (ApplicationContext.getUser() != null) {
			specialRepaymentVO.setModifier(ApplicationContext.getUser().getName());
			specialRepaymentVO.setModifierId(ApplicationContext.getUser().getId());
		} else {
			specialRepaymentVO.setModifier("system");
			specialRepaymentVO.setModifierId(-1L);
		}
		return specialRepaymentDao.updateSpecialRepaymentState(specialRepaymentVO);
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayInAdvance(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String submitRepayInAdvance(Long loanId) {
		return buildRepayInAdvance(loanId, false);
	}
	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayInAdvance(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String plSubmitRepayInAdvance(Long loanId) {
		return plBuildRepayInAdvance(loanId, false);
	}
	

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#cancelRepayInAdvance(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String cancelRepayInAdvance(Long loanId) {
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setLoanId(loanId);
		// 查询类型为"提前扣款",状态为"申请"的特殊还款列表
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		specialRepaymentVO.setPlFlag("");
		this.update(specialRepaymentVO);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DEDUCT_IN_ADVANCE_CANCEL.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayOneTime(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String submitRepayOneTime(Long loanId) {
		String result = "success";
		if (isInAdvanceRepayment(loanId) || isOneTimeRepayment(loanId)) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		// 每申请一次【一次性结清】，插入一条新的记录
		insert(specialRepayment);
		
		// 如果是loan，而且申请了展期,则将展期置为"取消",并将loan的剩余本金、最后一期还款计划等数据更新还原.
		if(loan != null) {
			Extension e =  extensionService.getExtensionByLoanId(loanId);
			if(e != null) {
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(e.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.展期置为"取消"
				extensionService.update(extensionVO);
				
				if(e.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					LoanVO loanVO = new LoanVO();
					loanVO.setId(loanId);
					loanVO.setResidualPactMoney(loan.getPactMoney());
					// 2.更新还原loan的剩余本金
					loanService.update(loanVO);
			
					// loan的最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime().intValue());
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = loan.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					if(loan.getCreatedTime().after(date)&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = loan.getPactMoney();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);								
					}
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, loan.getPactMoney());
				}
			}
		} else {
			
			Extension  nextExtension = extensionService.getNextExtension(loanId);
			if(nextExtension != null) {
				// 如果extenion，而且申请了新的展期,则将新展期置为"取消",并将原extension的剩余本金、最后一期还款计划等数据更新还原.
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(nextExtension.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.新展期置为"取消"
				extensionService.update(extensionVO);
				
				if(nextExtension.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					Extension extension = extensionService.get(loanId);
					extensionVO.setId(loanId);
					extensionVO.setStatus(null);
					extensionVO.setResidualPactMoney(extension.getPactMoney());
					// 2.还原此展期的剩余本金
					extensionService.update(extensionVO);
			
					// 该展期最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, 3);
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = extension.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());	
					
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					if(extension.getCreatedTime().after(date)&&extension.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&extension.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = extension.getPactMoney();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);								
					}
					
					
					
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, extension.getPactMoney());
				}
			}
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.REPAY_ONE_TIME_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}
	
	@Override
	@Transactional
	public void resetRepaymentPlan(RepaymentPlanVO repaymentPlanVO, BigDecimal repayMount, BigDecimal pactMoney) {
		// 风险金
		repaymentPlanVO.setRisk(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
		// 咨询费
		repaymentPlanVO.setReferRate(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
		// 评估费
		repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
		// 丙方管理费
		repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
		// 还款本金
		repaymentPlanVO.setPrincipalAmt(pactMoney);
		// 剩余本金
		repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
		// 一次性还款金额
		repaymentPlanVO.setOneTimeRepaymentAmount(repayMount);
		// 还款金额
		repaymentPlanVO.setRepayAmount(repayMount);
		// 剩余欠款
		repaymentPlanVO.setDeficit(repayMount);
		// 3.更新还原loan最后一期还款计划
		repaymentPlanService.update(repaymentPlanVO);
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#cancelRepayOneTime(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String cancelRepayOneTime(Long loanId) {
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setLoanId(loanId);
		// 查询类型为"一次性还款",状态为"申请"的特殊还款列表
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		specialRepaymentVO.setPlFlag("");
		update(specialRepaymentVO);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.REPAY_ONE_TIME_CANCEL.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param loanId
	 * @param amount
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitPenaltyReduce(java.lang.Long, java.math.BigDecimal)
	 */
	@Override
	@Transactional
	public String submitPenaltyReduce(Long loanId, BigDecimal amount, Integer extensionTime) {
		BigDecimal result = this.getReliefOfFine(DateUtil.getToday(), loanId);
		if(result.compareTo(BigDecimal.ZERO) > 0) {
			return "该借款已申请罚息减免，不可重复申请!";
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		// 申请日期
		specialRepayment.setRequestDate(DateUtil.getToday());
		// 设置类型为"罚息减免"
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		// 申请金额
		specialRepayment.setAmount(amount);
		if(extensionTime.compareTo(new Integer(0)) == 0) {
			Loan loan = loanService.get(loanId);
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		insert(specialRepayment);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COST_REDUCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COST_REDUCE_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(loanId));
		sysLogService.insert(sysLog);
		return "success";
	}

	/** 
	 * @param loanId
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#cancelPenaltyReduce(java.lang.Long)
	 */
	@Override
	@Transactional
	public String cancelPenaltyReduce(Long loanId) {
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setLoanId(loanId);
		// 查询当天中，类型为"罚息减免",状态为"申请"的特殊还款列表
		tempSpecialRepaymentVO.setRequestDate(DateUtil.getToday());
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		update(specialRepaymentVO);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COST_REDUCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COST_REDUCE_CANCEL.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param loanId
	 * @param specType
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#getProposerID(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public Long getProposerID(Long loanId, Integer specType) {
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setType(specType);
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(specialRepaymentVO);
		if(specialRepaymentList != null && specialRepaymentList.size() == 1) {
			return specialRepaymentList.get(0).getCreatorId();
		}
		return null;
	}

	/** 
	 * @param loanId
	 * @param extensionApproveFlag
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayInAdvance(java.lang.Long, boolean)
	 */
	@Override
	public String submitRepayInAdvance(Long loanId, boolean extensionApproveFlag) {
		return buildRepayInAdvance(loanId, true);
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	private String buildRepayInAdvance(Long loanId, boolean extensionApproveFlag) {
		String result = "success";
		// 如果该借款有提前扣款申请，且处于"申请状态"
		if (isInAdvanceRepayment(loanId) || isOneTimeRepayment(loanId)) {
			result = "该客户已申请过特殊还款，不能再次申请,";
			return result;
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		if(extensionApproveFlag){
			specialRepayment.setExtensionFlag(EnumConstants.YesOrNo.NO.getValue());
		}
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		// 每申请一次【提前扣款】,插入一条新的记录
		insert(specialRepayment);
		/*
		// 如果是loan，而且申请了展期,则将展期置为"取消",并将loan的剩余本金、最后一期还款计划等数据更新还原.
				if(loan != null) {
					Extension e =  extensionService.getExtensionByLoanId(loanId);
					if(e != null) {
						ExtensionVO extensionVO = new ExtensionVO();
						extensionVO.setId(e.getId());
						extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
						// 1.展期置为"取消"
						extensionService.update(extensionVO);
						
						if(e.getStatus().compareTo(EnumConstants.LoanStatus.正常.getValue()) == 1) {
							LoanVO loanVO = new LoanVO();
							loanVO.setId(loanId);
							loanVO.setResidualPactMoney(loan.getPactMoney());
							// 2.更新还原loan的剩余本金
							loanService.update(loanVO);
					
							// loan的最后一期还款计划
							RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime().intValue());
							RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
							preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
							BigDecimal repayMount = loan.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
							// 3.更新还原loan最后一期还款计划
							resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, loan.getPactMoney());
						}
					}
				} else {
					Extension  nextExtension = extensionService.getNextExtension(loanId);
					if(nextExtension != null) {
						// 如果extenion，而且申请了新的展期,则将新展期置为"取消",并将原extension的剩余本金、最后一期还款计划等数据更新还原.
						ExtensionVO extensionVO = new ExtensionVO();
						extensionVO.setId(nextExtension.getId());
						extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
						// 1.新展期置为"取消"
						extensionService.update(extensionVO);
						
						if(nextExtension.getStatus().compareTo(EnumConstants.LoanStatus.正常.getValue()) == 1) {
							Extension extension = extensionService.get(loanId);
							extensionVO.setId(loanId);
							extensionVO.setStatus(null);
							extensionVO.setResidualPactMoney(extension.getPactMoney());
							// 2.还原此展期的剩余本金
							extensionService.update(extensionVO);
					
							// 该展期最后一期还款计划
							RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, 3);
							RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
							preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
							BigDecimal repayMount = extension.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
							// 3.更新还原loan最后一期还款计划
							resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, extension.getPactMoney());
						}
					}
				}*/
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DEDUCT_IN_ADVANCE_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param specialRepaymentVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#findListByVOWihtUnion(com.ezendai.credit2.after.vo.SpecialRepaymentVO)
	 */
	@Override
	public List<SpecialRepayment> findListByVOWihtUnion(SpecialRepaymentVO specialRepaymentVO) {
		return specialRepaymentDao.findListByVOWihtUnion(specialRepaymentVO);
	}

	@Override
	public Pager findListByVOWihtExtend(SpecialRepaymentVO specialRepaymentVO) {
		// TODO Auto-generated method stub
		return specialRepaymentDao.findListByVOWihtExtend(specialRepaymentVO) ;
	}

	@Override
	public Pager findListByVOWihtBaseExtend(
			SpecialRepaymentVO specialRepaymentVO) {
		// TODO Auto-generated method stub
		return specialRepaymentDao.findListByVOWihtBaseExtend(specialRepaymentVO);
	}

	@Override
	public String submitAbrogateGene(Long loanId) {
		// TODO Auto-generated method stub
		String result = "success";
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		// 每申请一次【停止报盘】,插入一条新的记录
		insert(specialRepayment);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REFUSE_OFFER.getValue());
		sysLog.setOptType(EnumConstants.OptionType.REFUSE_OFFER_GENE.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	@Override
	public String cancelAbrogateOffer(Long id) {
		// TODO Auto-generated method stub
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setId(id);
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		update(specialRepaymentVO);
		SpecialRepayment specialRepayment=get(id);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REFUSE_OFFER.getValue());
		sysLog.setOptType(EnumConstants.OptionType.REFUSE_OFFER_CANNEL.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}
	
	/** 
	 * @param loanId
	 * @param extensionApproveFlag
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayInAdvance(java.lang.Long, boolean)
	 */
	@Override
	public String plSubmitRepayInAdvance(Long loanId, boolean extensionApproveFlag) {
		return plBuildRepayInAdvance(loanId, true);
	}
	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	private String plBuildRepayInAdvance(Long loanId, boolean extensionApproveFlag) {
		String result = "提交成功!";
		// 如果该借款有提前扣款申请，且处于"申请状态"
		if (isInAdvanceRepayment(loanId) || isOneTimeRepayment(loanId)) {
			result = "该客户已申请过特殊还款，不能再次申请,";
			return result;
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		if(extensionApproveFlag){
			specialRepayment.setExtensionFlag(EnumConstants.YesOrNo.NO.getValue());
		}
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		specialRepayment.setPlFlag("pl");/*specialRepayment.setRemark("PL");*///向备注中添加标识
		// 每申请一次【提前扣款】,插入一条新的记录
		insert(specialRepayment);
		
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.PL_REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.PL_DEDUCT_IN_ADVANCE_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}
	
	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#cancelRepayInAdvance(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String plCancelRepayInAdvance(Long loanId) {
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setLoanId(loanId);
		// 查询类型为"提前扣款",状态为"申请"的特殊还款列表
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "取消失败！";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		this.update(specialRepaymentVO);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.PL_REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.PL_DEDUCT_IN_ADVANCE_CANCEL.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#submitRepayOneTime(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String plSubmitRepayOneTime(Long loanId) {
		String result = "提交成功!";
		if (isInAdvanceRepayment(loanId) || isOneTimeRepayment(loanId)) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		specialRepayment.setPlFlag("pl");/*specialRepayment.setRemark("PL");*///向备注中添加标识
		// 每申请一次【一次性结清】，插入一条新的记录
		insert(specialRepayment);
		
		
		// 如果是loan，而且申请了展期,则将展期置为"取消",并将loan的剩余本金、最后一期还款计划等数据更新还原.
		if(loan != null) {
			Extension e =  extensionService.getExtensionByLoanId(loanId);
			if(e != null) {
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(e.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.展期置为"取消"
				extensionService.update(extensionVO);
				
				if(e.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					LoanVO loanVO = new LoanVO();
					loanVO.setId(loanId);
					loanVO.setResidualPactMoney(loan.getPactMoney());
					// 2.更新还原loan的剩余本金
					loanService.update(loanVO);
			
					// loan的最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime().intValue());
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = loan.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					if(loan.getCreatedTime().after(date)&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = loan.getPactMoney();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
					}
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, loan.getPactMoney());
				}
			}
		} else {
			Extension  nextExtension = extensionService.getNextExtension(loanId);
			if(nextExtension != null) {
				// 如果extenion，而且申请了新的展期,则将新展期置为"取消",并将原extension的剩余本金、最后一期还款计划等数据更新还原.
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(nextExtension.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.新展期置为"取消"
				extensionService.update(extensionVO);
				
				if(nextExtension.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					Extension extension = extensionService.get(loanId);
					extensionVO.setId(loanId);
					extensionVO.setStatus(null);
					extensionVO.setResidualPactMoney(extension.getPactMoney());
					// 2.还原此展期的剩余本金
					extensionService.update(extensionVO);
			
					// 该展期最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, 3);
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = extension.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
					
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					if(extension.getCreatedTime().after(date)&&extension.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&extension.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = extension.getPactMoney();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);								
					}												
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, extension.getPactMoney());
				}
			}
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.PL_REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.PL_REPAY_ONE_TIME_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.SpecialRepaymentService#cancelRepayOneTime(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String plCancelRepayOneTime(Long loanId) {
		String result = "success";
		SpecialRepaymentVO tempSpecialRepaymentVO = new SpecialRepaymentVO();
		tempSpecialRepaymentVO.setLoanId(loanId);
		// 查询类型为"一次性还款",状态为"申请"的特殊还款列表
		tempSpecialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		tempSpecialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		List<SpecialRepayment> specialRepaymentList = this.findListByVo(tempSpecialRepaymentVO);
		if (specialRepaymentList == null || specialRepaymentList.size() != 1) {
			result = "取消失败！";
			return result;
		}
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepaymentList.get(0).getId());
		// 将该特殊还款记录状态修改为"取消"
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.CANCEL.getValue());
		update(specialRepaymentVO);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.PL_REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.PL_REPAY_ONE_TIME_CANCEL.getValue());
		sysLog.setRemark(Long.toString(loanId));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}

	@Override
	@Transactional
	public String submitRepayOneTimeNew(Long loanId) {
		String result = "success";
		if (isInAdvanceRepayment(loanId) || isOneTimeRepayment(loanId)) {
			result = "该客户已申请过特殊还款，不能再次申请";
			return result;
		}
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(loanId);
		specialRepayment.setRequestDate(new Date());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		// 设置申请人为当前登陆用户
		String sysUserName = ApplicationContext.getUser().getName();
		specialRepayment.setProposer(sysUserName);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			// 设置营业网点ID
			specialRepayment.setSalesDeptId(loan.getSalesDeptId());
		} else {
			Extension extension = extensionService.get(loanId);
			specialRepayment.setSalesDeptId(extension.getSalesDeptId());
		}
		// 每申请一次【一次性结清】，插入一条新的记录
		insert(specialRepayment);
		
		// 如果是loan，而且申请了展期,则将展期置为"取消",并将loan的剩余本金、最后一期还款计划等数据更新还原.
		if(loan != null) {
			Extension e =  extensionService.getExtensionByLoanId(loanId);
			if(e != null) {
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(e.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.展期置为"取消"
				extensionService.update(extensionVO);
				
				if(e.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					LoanVO loanVO = new LoanVO();
					loanVO.setId(loanId);
					loanVO.setResidualPactMoney(loan.getPactMoney());
					// 2.更新还原loan的剩余本金
					loanService.update(loanVO);
			
					// loan的最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime().intValue());
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = loan.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					if(loan.getCreatedTime().after(date)&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = preLastRepaymentPlan.getOneTimeRepaymentAmount();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);								
					}
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, loan.getPactMoney());
				}
			}
		} else {
			
			Extension  nextExtension = extensionService.getNextExtension(loanId);
			if(nextExtension != null) {
				// 如果extenion，而且申请了新的展期,则将新展期置为"取消",并将原extension的剩余本金、最后一期还款计划等数据更新还原.
				ExtensionVO extensionVO = new ExtensionVO();
				extensionVO.setId(nextExtension.getId());
				extensionVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
				// 1.新展期置为"取消"
				extensionService.update(extensionVO);
				
				if(nextExtension.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) >0) {
					Extension extension = extensionService.get(loanId);
					extensionVO.setId(loanId);
					extensionVO.setStatus(null);
					extensionVO.setResidualPactMoney(extension.getPactMoney());
					// 2.还原此展期的剩余本金
					extensionService.update(extensionVO);
			
					// 该展期最后一期还款计划
					RepaymentPlan preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, 3);
					RepaymentPlanVO preLastRepaymentPlanVO = new RepaymentPlanVO();
					preLastRepaymentPlanVO.setId(preLastRepaymentPlan.getId());
					BigDecimal repayMount = extension.getPactMoney().add(preLastRepaymentPlan.getInterestAmt());	
					
					SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
					SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					if(extension.getCreatedTime().after(date)&&extension.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&extension.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						  repayMount = preLastRepaymentPlan.getOneTimeRepaymentAmount();
						  preLastRepaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
						  preLastRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);								
					}
					
					
					
					// 3.更新还原loan最后一期还款计划
					resetRepaymentPlan(preLastRepaymentPlanVO, repayMount, extension.getPactMoney());
				}
			}
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REPAY_IN_ADVANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.REPAY_ONE_TIME_SUBMIT.getValue());
		sysLog.setRemark(Long.toString(specialRepayment.getLoanId()));
		// 插入系统日志
		sysLogService.insert(sysLog);
		return result;
	}
	
}
