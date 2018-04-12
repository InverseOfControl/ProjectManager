/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.ExtensionDao;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.AuditService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: ExtensionServiceImpl.java, v 0.1 2015年3月10日 下午2:30:41 00221921 Exp $
 */
@Service
public class ExtensionServiceImpl implements ExtensionService{
	@Autowired
	private ExtensionDao extensionDao;
	
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private LedgerService ledgerService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private SysParameterService sysParameterService;

	private static BigDecimal carLoanMonthRate = new BigDecimal(String.valueOf(0.007));
	/** 
	 * @param extension
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#insert(com.ezendai.credit2.apply.model.Extension)
	 */
	@Override
	public Extension insert(Extension extension) {
		return extensionDao.insert(extension);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.apply.service.ExtensionService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		extensionDao.deleteById(id);
	}

	/** 
	 * @param extensionVO
	 * @see com.ezendai.credit2.apply.service.ExtensionService#deleteByIdList(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public void deleteByIdList(ExtensionVO extensionVO) {
		extensionDao.deleteByIdList(extensionVO);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#update(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public int update(ExtensionVO extensionVO) {
		return extensionDao.update(extensionVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#get(java.lang.Long)
	 */
	@Override
	public Extension get(Long id) {
		return extensionDao.get(id);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#findListByVo(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public List<Extension> findListByVo(ExtensionVO extensionVO) {
		return extensionDao.findListByVo(extensionVO);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#exists(java.util.Map)
	 */
	@Override
	public boolean exists(Map<String, Object> map) {
		return extensionDao.exists(map);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#findWithPg(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public Pager findWithPg(ExtensionVO extensionVO) {
		return extensionDao.findWithPg(extensionVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(Long id) {
		return extensionDao.exists(id);
	}

	@Override
	public int updateExtensionByStatus(ExtensionVO extensionVO) {
		return extensionDao.updateExtensionByStatus(extensionVO);
	}

	/** 
	 * @param extensionId
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#existExtension(java.lang.Long)
	 */
	@Override
	public Extension getNextExtension(Long extensionId) {
		Extension e = extensionDao.get(extensionId);
		if(e == null) {
			return null;
		}
		ExtensionVO extensionVO = new ExtensionVO();
		Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
		extensionVO.setLoanId(loanId);
		Integer extensionTime = e.getExtensionTime();
		extensionVO.setExtensionTime(extensionTime);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.展期拒绝.getValue());
		statusList.add(EnumConstants.LoanStatus.取消.getValue());
		extensionVO.setStatusList(statusList);
		return extensionDao.getExtensionByLoanId(extensionVO);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#get(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public Extension getExtensionByLoanId(Long loanId) {
		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setLoanId(loanId);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.展期拒绝.getValue());
		statusList.add(EnumConstants.LoanStatus.取消.getValue());
		extensionVO.setStatusList(statusList);
		
		return extensionDao.getExtensionByLoanId(extensionVO);
	}
	
	/** 
	 * @param loanVO
	 * @return
	 */
	@Override
	public List<Extension> findLastOverdueList(ExtensionVO vo) {
		return extensionDao.findLastOverdueList(vo);
	}
	@Override
	public boolean checkIsExtension(Long loanId)
	{
		boolean result =false;
		Extension extension = getExtensionByLoanId(loanId);
		if(extension==null)
		{
			extension = getNextExtension(loanId);
			if(extension==null)
			{
				result=false;
			}
			else
			{
				result=true;
			}
		}
		else
		{
			result=true;
		}
		return result;
		
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#updateByIdList(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public int updateByIdList(ExtensionVO extensionVO) {
		return extensionDao.updateByIdList(extensionVO);
	}

	/** 
	 * @param loanId
	 * @see com.ezendai.credit2.apply.service.ExtensionService#updateLastLoanRepaymentPlan(java.lang.Long)
	 */
	@Override
	@Transactional
	public void updateLastLoanRepaymentPlan(Long extensionId) {
		Extension extension = get(extensionId);
		// 原借款(loan或extension)的最后一期还款计划
		RepaymentPlan preLastRepaymentPlan;
		// 新展期的第一期还款计划
		RepaymentPlanVO newFirstRepaymentPlanVO;
		// 本金差额
		BigDecimal principalDiff;
		// 2.更改上一借款(loan或extension)的剩余本金
		Integer extensionTime = extension.getExtensionTime();
		// 第一次展期
		SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
		 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
		 Date date=new Date();
			Loan loan =null;
		try {
			date = sdftime.parse(parameter.getParameterValue());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		if (extensionTime.compareTo(new Integer(1)) == 0) {
			Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			  loan = loanService.get(loanId);
			// 本金差额
			principalDiff = loan.getPactMoney().subtract(extension.getAuditMoney());

			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setResidualPactMoney(principalDiff);
			loanService.update(loanVO);

			// 原loan的最后一期还款计划
			preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime()
				.intValue());
			// 展期的第一期还款计划
//			newFirstRepaymentPlanVO = repaymentPlanService.getByLoanIdAndCurNum(extensionId,
//				new Integer(1)); //TBD
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0){
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTermNew(extension);
			}else{
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTerm(extension);
			}
			
		}else { // 非第一次展期
			Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			  loan = loanService.get(loanId);
			Long preExtensionId = loanExtensionService
				.getPreExtensionId(extensionId, extensionTime);
			Extension preExtension = get(preExtensionId);
			// 本金差额
			principalDiff = preExtension.getPactMoney().subtract(extension.getAuditMoney());

			ExtensionVO preExtensionVO = new ExtensionVO();
			preExtensionVO.setId(preExtensionId);
			preExtensionVO.setResidualPactMoney(principalDiff);
			update(preExtensionVO);

			// 上一展期的最后一期还款计划
			preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(preExtension.getId(),
				new Integer(3));
			// 新展期的第一期还款计划
//			newFirstRepaymentPlanVO = repaymentPlanService.getByLoanIdAndCurNum(extensionId,
//				new Integer(1)); //TBD
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0){
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTermNew(extension);
			}else{
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTerm(extension);
			}
		}
		// 3.更改上一借款(loan或extension)最后一期还款计划的金额
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setId(preLastRepaymentPlan.getId());
		// 风险金
		BigDecimal risk = newFirstRepaymentPlanVO.getRisk();
		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		// 咨询费
		BigDecimal consult = newFirstRepaymentPlanVO.getReferRate();
		repaymentPlanVO.setReferRate(consult);
		repaymentPlanVO.setCurRemainingReferRate(consult);
		// 评估费
		BigDecimal assessment = newFirstRepaymentPlanVO.getEvalRate();
		repaymentPlanVO.setEvalRate(assessment);
		repaymentPlanVO.setCurRemainingEvalRate(assessment);
		// 丙方管理费
		BigDecimal cManage = newFirstRepaymentPlanVO.getManagePart1Fee();
		repaymentPlanVO.setManagePart1Fee(cManage);
		repaymentPlanVO.setCurRemainingManagePart1Fee(cManage);
		// 利息	利息不变，沿用上次借款的利息
		BigDecimal interest = preLastRepaymentPlan.getInterestAmt();
		if(loan.getProductId().compareTo(
				EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			repaymentPlanVO.setInterestAmt(newFirstRepaymentPlanVO.getInterestAmt());
			repaymentPlanVO.setCurRemainingInterestAmt(newFirstRepaymentPlanVO.getInterestAmt());
		}
		
		// 还款本金
		repaymentPlanVO.setPrincipalAmt(principalDiff);
		// 剩余本金
		repaymentPlanVO.setCurRemainingPrincipal(principalDiff);
		//  一次性还款金额 (本金差额 + 利息)
		// repaymentPlanVO.setOneTimeRepaymentAmount(principalDiff.add(interest));
		BigDecimal repayAmount = risk.add(consult).add(assessment).add(cManage).add(principalDiff)
			.add(interest);
		BigDecimal interestAmt=new BigDecimal(0);
		
		Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
		  loan = loanService.get(loanId);
		if(loan.getCreatedTime().after(date)&&loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			 interestAmt = extension.getAuditMoney().multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		repayAmount=	repayAmount.add(interestAmt);
		// 还款金额
		repaymentPlanVO.setRepayAmount(repayAmount);
		//  剩余欠款 (初始值为还款金额)
		repaymentPlanVO.setDeficit(repayAmount);
		// 更新原loan的最后一期还款计划
		repaymentPlanService.update(repaymentPlanVO);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.ExtensionService#getCountByExtensionVO(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public int getCountByExtensionVO(ExtensionVO extensionVO) {
		return extensionDao.getCountByExtensionVO(extensionVO);
	}

	@Override
	public List<Extension> findExtensionByCondition(ExtensionVO extensionVO) {
		
		return extensionDao.findExtensionByCondition(extensionVO);
	}

	@Override
	@Transactional
	public void updateLastLoanRepaymentPlanNew(Long extensionId) {
		Extension extension = get(extensionId);
		// 原借款(loan或extension)的最后一期还款计划
		RepaymentPlan preLastRepaymentPlan;
		// 新展期的第一期还款计划
		RepaymentPlanVO newFirstRepaymentPlanVO;
		// 本金差额
		BigDecimal principalDiff;
		// 2.更改上一借款(loan或extension)的剩余本金
		Integer extensionTime = extension.getExtensionTime();
		// 第一次展期
		SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
		 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
		 Date date=new Date();
			Loan loan =null;
		try {
			date = sdftime.parse(parameter.getParameterValue());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		if (extensionTime.compareTo(new Integer(1)) == 0) {
			Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			  loan = loanService.get(loanId);
			// 本金差额
			principalDiff = loan.getPactMoney().subtract(extension.getAuditMoney());

			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setResidualPactMoney(principalDiff);
			loanService.update(loanVO);

			// 原loan的最后一期还款计划
			preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(loanId, loan.getTime()
				.intValue());
			// 展期的第一期还款计划
//			newFirstRepaymentPlanVO = repaymentPlanService.getByLoanIdAndCurNum(extensionId,
//				new Integer(1)); //TBD
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0){
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTermNewFresh(extension);
			}else{
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTerm(extension);
			}
			
		}else { // 非第一次展期
			Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			  loan = loanService.get(loanId);
			Long preExtensionId = loanExtensionService
				.getPreExtensionId(extensionId, extensionTime);
			Extension preExtension = get(preExtensionId);
			// 本金差额
			principalDiff = preExtension.getPactMoney().subtract(extension.getAuditMoney());

			ExtensionVO preExtensionVO = new ExtensionVO();
			preExtensionVO.setId(preExtensionId);
			preExtensionVO.setResidualPactMoney(principalDiff);
			update(preExtensionVO);

			// 上一展期的最后一期还款计划
			preLastRepaymentPlan = repaymentPlanService.getByLoanIdAndCurNum(preExtension.getId(),
				new Integer(3));
			// 新展期的第一期还款计划
//			newFirstRepaymentPlanVO = repaymentPlanService.getByLoanIdAndCurNum(extensionId,
//				new Integer(1)); //TBD
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getProductId().compareTo(
							EnumConstants.ProductList.CAR_LOAN.getValue()) == 0){
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTermNewFresh(extension);
			}else{
				newFirstRepaymentPlanVO = auditService.getExtensionRepaymentPlanFirstTerm(extension);
			}
		}
		// 3.更改上一借款(loan或extension)最后一期还款计划的金额
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setId(preLastRepaymentPlan.getId());
		// 风险金
		BigDecimal risk = newFirstRepaymentPlanVO.getRisk();
		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		// 咨询费
		BigDecimal consult = newFirstRepaymentPlanVO.getReferRate();
		repaymentPlanVO.setReferRate(consult);
		repaymentPlanVO.setCurRemainingReferRate(consult);
		// 评估费
		BigDecimal assessment = newFirstRepaymentPlanVO.getEvalRate();
		repaymentPlanVO.setEvalRate(assessment);
		repaymentPlanVO.setCurRemainingEvalRate(assessment);
		// 丙方管理费
		BigDecimal cManage = newFirstRepaymentPlanVO.getManagePart1Fee();
		repaymentPlanVO.setManagePart1Fee(cManage);
		repaymentPlanVO.setCurRemainingManagePart1Fee(cManage);
		// 利息	利息不变，沿用上次借款的利息
		BigDecimal interest = preLastRepaymentPlan.getInterestAmt();
		if(loan.getProductId().compareTo(
				EnumConstants.ProductList.CAR_LOAN.getValue()) == 0&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			repaymentPlanVO.setInterestAmt(newFirstRepaymentPlanVO.getInterestAmt());
			repaymentPlanVO.setCurRemainingInterestAmt(newFirstRepaymentPlanVO.getInterestAmt());
		}
		
		// 还款本金
		repaymentPlanVO.setPrincipalAmt(principalDiff);
		// 剩余本金
		repaymentPlanVO.setCurRemainingPrincipal(principalDiff);
		//  一次性还款金额 (本金差额 + 利息)
		// repaymentPlanVO.setOneTimeRepaymentAmount(principalDiff.add(interest));
		BigDecimal repayAmount = risk.add(consult).add(assessment).add(cManage).add(principalDiff)
			.add(interest);
		BigDecimal interestAmt=new BigDecimal(0);
		
		Long loanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
		  loan = loanService.get(loanId);
		if(loan.getCreatedTime().after(date)&&loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			 interestAmt = extension.getAuditMoney().multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		repayAmount=	repayAmount.add(interestAmt);
		// 还款金额
		repaymentPlanVO.setRepayAmount(repayAmount);
		//  剩余欠款 (初始值为还款金额)
		repaymentPlanVO.setDeficit(repayAmount);
		// 更新原loan的最后一期还款计划
		repaymentPlanService.update(repaymentPlanVO);
		
	}
}
