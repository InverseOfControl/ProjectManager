/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 







import com.ezendai.credit2.after.service.AfterLoanService;
import com.ezendai.credit2.after.service.OfferFileService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ComprehensivesearchDao;
import com.ezendai.credit2.master.service.ComprehensiveSearchService;
import com.ezendai.credit2.master.vo.ComprehensiveSearchVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.service.SysLogService;
 
@Service
public class ComprehensiveSearchServiceImpl implements ComprehensiveSearchService {

	@Autowired
	private PersonService personService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private OfferFileService offerFileService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private AfterLoanService afterLoanService;
	@Autowired
	private RepayInfoService repayInfoService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ComprehensivesearchDao comprehensivesearchDao;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Override
	public Pager getComprehensiveSearchList(ComprehensiveSearchVO csVo) {
		// TODO Auto-generated method stub
		return comprehensivesearchDao.getComprehensiveSearchList(csVo);
	}
	
	
	@Override
	public RepayEntryDetailsVO viewEdit(Long loanId) {
		Date nowDate = DateUtil.getToday();
		Loan loan = loanService.get(loanId);
		Person person;
		if (loan != null) {
			person = personService.get(loan.getPersonId());
		} else {
			Extension extension = extensionService.get(loanId);
			person = personService.get(extension.getPersonId());
		}
		String name = person.getName();
		String idnum = person.getIdnum();
		String mobilePhone = person.getMobilePhone();

		RepayEntryDetailsVO repayEntryDetailsVO = new RepayEntryDetailsVO();
		repayEntryDetailsVO.setLoanId(loanId);
		repayEntryDetailsVO.setPersonName(name);
		repayEntryDetailsVO.setPersonIdnum(idnum);
		repayEntryDetailsVO.setPersonMobilePhone(mobilePhone);
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loanId);
		List<RepaymentPlan> repaymentPlanList2 = repayService.getAllInterestOrLoan(loanId);
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		//设置挂账金额,期末预收
		repayEntryDetailsVO.setAccAmount(accAmount);
		Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
		/**当期剩余本金*/
		BigDecimal curRemainingPrincipalTotal=BigDecimal.ZERO;
		
		
		//判断如果该期期数为0,则从已结清的还款计划表中获取
		if (currTerm.compareTo(0) == 0) {
			List<RepaymentPlan> repaymentPlanSettleList = repayService.getAllInterestOrLoanBySettle(nowDate, loanId);
			currTerm = repayService.getCurrTerm(repaymentPlanSettleList, nowDate);
			int size = repaymentPlanSettleList.size();
			if (size > 0) {
				Date repayDay = repaymentPlanSettleList.get(size - 1).getRepayDay();
				//设置当期还款日
				repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));
			}
		}
		repayEntryDetailsVO.setCurrTerm(currTerm);
		//设置当前期数
		BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(nowDate, loanId);
		//设置减免金额
		repayEntryDetailsVO.setReliefOfFine(reliefOfFine);
		//设置还款日期
		repayEntryDetailsVO.setNowDate(DateUtil.defaultFormatDay(nowDate));
		BigDecimal fine = repayService.getFine(repaymentPlanList, nowDate);
		//设置罚息
		repayEntryDetailsVO.setFine(fine);
		BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, nowDate);
		BigDecimal overdueAllAmount = overdueAmount.add(fine);
		//设置逾期应金额
		repayEntryDetailsVO.setOverdueAmount(overdueAmount);
	

		BigDecimal repayAmount = overdueAllAmount;
		//如果结果小于0,则返回0
		if (repayAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAmount(BigDecimal.ZERO);
		} else {
			//应还总额（不含当期)
			repayEntryDetailsVO.setRepayAmount(repayAmount);
		}
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			int size = repaymentPlanList.size();
			Integer overdueTermCount = repayService.getOverdueTermCount(repaymentPlanList, nowDate);
			//设置逾期总数
			repayEntryDetailsVO.setOverdueTerm(overdueTermCount);
			//如果逾期期数大于等于1 
			if (overdueTermCount >= 1) {
				//设置逾期起始日
				repayEntryDetailsVO.setOverdueStartDate(DateUtil.defaultFormatDay(repaymentPlanList.get(0).getRepayDay()));
			}
			int overdueFineDay = repayService.getOverdueFineDay(repaymentPlanList, nowDate);
			//设置罚息天数
			repayEntryDetailsVO.setFineDay(overdueFineDay);

			if (overdueFineDay >= 1) {
				//设置罚息起算日
				repayEntryDetailsVO.setFineDate(DateUtil.defaultFormatDay(DateUtil.getDateBefore(overdueFineDay)));
				// 当期归还利息(还款利息)
				//BigDecimal interestAmt = pactMoney.multiply(new BigDecimal(String.valueOf(monthRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
			 
				
				// 当期归还本金
				//BigDecimal principalAmt = averageCapital.subtract(interestAmt);
			 
				
				repayEntryDetailsVO.setCurRemainingPrincipal((new BigDecimal(0)));
				repayEntryDetailsVO.setCurRemainingInterestAmt((new BigDecimal(0)));
			}
			Date repayDay = repaymentPlanList.get(size - 1).getRepayDay();
			//设置当期还款日
			repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));
			BigDecimal overduePricipal=	repayService.getOverduePrincipal(repaymentPlanList,nowDate);
			repayEntryDetailsVO.setOverduePrincipal(overduePricipal);
			// 逾期利息
			BigDecimal interest = repayService.getOverdueInterest(repaymentPlanList, nowDate);
			repayEntryDetailsVO.setInterest(interest);
			BigDecimal pactMoney = loan.getAuditMoney();
			// 期数
			Integer auditTime = loan.getAuditTime();
			Product product = productService.get(loan.getProductId());
			// 平息利率
			BigDecimal rate = product.getRate();
			// 每月应减去管理费(等额本息)
			BigDecimal a = pactMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP);
			BigDecimal b =new BigDecimal(0);
			if(rate!=null){
				 b = pactMoney.multiply(rate).setScale(8, BigDecimal.ROUND_HALF_UP);
			}
		
			BigDecimal averageCapital = a.add(b);
			BigDecimal annuity = pactMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP);
			if(rate!=null){
				annuity = annuity.add(pactMoney.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			double monthRate = rate(pactMoney.doubleValue(), annuity.doubleValue(), auditTime.doubleValue(), 200, 10);
			
			if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - nowDate.getTime() >= 0) {
				repayEntryDetailsVO.setCurRemainingInterestAmt(repaymentPlanList.get(size - 1).getCurRemainingInterestAmt());
				repayEntryDetailsVO.setCurRemainingPrincipal(repaymentPlanList.get(size - 1).getCurRemainingPrincipal());
			}
			
		}
		if(repaymentPlanList2 !=null  && repaymentPlanList2.size() > 0){
			/**剩余金额汇总*/
			for(int i=0;i<repaymentPlanList2.size();i++){
			 
					curRemainingPrincipalTotal=curRemainingPrincipalTotal.add(repaymentPlanList2.get(i).getCurRemainingPrincipal());
				 
			}
		    BigDecimal currInterest=getCurrInterest(repaymentPlanList2, nowDate);
		 
			//BigDecimal currInterest = repayService.getOverdueInterest(repaymentPlanList, nowDate);
			// 剩余本息和
			repayEntryDetailsVO.setCurRemainingAmount(currInterest.add(curRemainingPrincipalTotal));
		}
		boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment(loanId);
		BigDecimal repayAllAmount = BigDecimal.ZERO;
		if (oneTimeRepayment) {
			//一次性结清
			BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(repaymentPlanList, nowDate);
			repayEntryDetailsVO.setOnetimeRepaymentAmount(onetimeRepaymentAmount);
			repayEntryDetailsVO.setCurrAmountLabel("一次性还款金额");
			repayEntryDetailsVO.setCurrAmount(onetimeRepaymentAmount);
			//应还总额 =一次性结清 + 逾期应还 - 期末预收
			repayAllAmount = onetimeRepaymentAmount.add(overdueAllAmount);

		} else {
			BigDecimal nextRepayAmount = repayService.getNextRepayAmount(repaymentPlanList, nowDate);
			repayEntryDetailsVO.setCurrAmountLabel("当期应还总额");
			//判断如果没有逾期的话则取当期还款金额，有逾期金额的话当期还款金额为逾期应还总额
		 	//		if (overdueAllAmount.compareTo(BigDecimal.ZERO) == 0) {
		//	//				repayEntryDetailsVO.setCurrAmount(currRepayAmount);
			//			} else {
			//				repayEntryDetailsVO.setCurrAmount(overdueAllAmount);
			//			}
			repayEntryDetailsVO.setCurrAmount(nextRepayAmount);
			
			//应还总额（包含当期） = 当期应还总额+应还总额（不含当期）
			if (repayEntryDetailsVO.getCurrAmount() != null) {
				repayAllAmount = repayAmount.add(repayEntryDetailsVO.getCurrAmount());
			}
		}
		//如果结果小于0,则返回0
		if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAllAmount(BigDecimal.ZERO);
		} else {
			repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);
		}
		return repayEntryDetailsVO;
	}
	public BigDecimal getCurrInterest(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		 
			 for(RepaymentPlan rp:repaymentPlanList){
				result =result.add (rp.getCurRemainingInterestAmt());
			 }
		 
		return result;

	}

	/**
	 * 实际利率法
	 * 
	 * @author Bean
	 * @param a
	 *            现值--合同金额
	 * @param b
	 *            年金
	 * @param c
	 *            期数
	 * @param cnt
	 *            运算次数
	 * @param ina
	 *            误差位数
	 * @return 利率
	 */
	private static double rate(double a, double b, double c, int cnt, int ina) {
		double rate = 1, x, jd = 0.1, side = 0.1, i = 1;
		do {
			x = a / b - (Math.pow(1 + rate, c) - 1) / (Math.pow(rate + 1, c) * rate);
			if (x * side > 0) {
				side = -side;
				jd *= 10;
			}
			rate += side / jd;
		} while (i++ < cnt && Math.abs(x) >= 1 / Math.pow(10, ina));
		if (i > cnt)
			return Double.NaN;
		return rate;
	}
}
    
   
