package com.ezendai.credit2.after.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayServiceImpl.java, v 0.1 2014年11月26日 上午11:26:20 chenqi Exp $
 */
@Service
public class RepayServiceImpl implements RepayService {
	/**
	 * 展期loan关联service
	 */
	@Autowired
	private LoanExtensionService loanExtensionService;
	/**
	 * 参数service
	 */
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private LedgerService legderService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private ExtensionService extensionService;

	@Override
	public BigDecimal getOfferAmount(Date currDate, Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		try {
			currDate = DateUtil.strToDate(DateUtil.defaultFormatDate(currDate), null);
			List<RepaymentPlan> repaymentPlanList = getAllInterestOrLoan(currDate, loanId);
			//是否一次性
			if (specialRepaymentService.isOneTimeRepayment(loanId)) {
				result = getOnetimeRepaymentAmount(repaymentPlanList, currDate).add(getOverdueAmount(repaymentPlanList, currDate)).add(getFine(repaymentPlanList, currDate))
					.subtract(getAccAmount(loanId));
			} else {
				result = getSumDeficit(repaymentPlanList, currDate).add(getFine(repaymentPlanList, currDate)).subtract(getAccAmount(loanId));
			}
			//获取减免罚息,如果上面结果大于0
			if (result.compareTo(BigDecimal.ZERO) == 1) {
				BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(currDate, loanId);
				if (reliefOfFine.compareTo(BigDecimal.ZERO) == 1) {
					result = result.subtract(reliefOfFine);
				}
			}
			//得到最终结果,如果结果小于0,则返回0
			if (result.compareTo(BigDecimal.ZERO) == -1)
				return BigDecimal.ZERO;
			return result;
		} catch (Exception ex) {
			return result;
		}
	}

	/**
	 * <pre>
	 * 获取一次性还款的金额
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	public BigDecimal getOnetimeRepaymentAmount(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		if (repaymentPlanList == null || repaymentPlanList.size() < 1)
			return BigDecimal.ZERO;
		List<RepaymentPlan> repaymentPlanAllList = getAllInterestOrLoan(repaymentPlanList.get(0).getLoanId());
		int size = repaymentPlanAllList.size();
		if (size > 0) {
			RepaymentPlan repaymentPlan = repaymentPlanAllList.get(size - 1);
			Date lastTermRepayDay = repaymentPlan.getRepayDay();
			//判断如果当前日期在逾期借款最后一期还款日之后,则一次性为0
			if (currDate.after(lastTermRepayDay)) {
				return BigDecimal.ZERO;
			}
		}
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal oneTimeRepaymentAmount = repaymentPlanList.get(repaymentPlanList.size() - 1).getOneTimeRepaymentAmount();
		BigDecimal repayAmount = repaymentPlanList.get(repaymentPlanList.size() - 1).getRepayAmount();
		BigDecimal deficit = repaymentPlanList.get(repaymentPlanList.size() - 1).getDeficit();
		if (oneTimeRepaymentAmount != null && repayAmount != null && deficit != null) {
			result = oneTimeRepaymentAmount.subtract(repayAmount).add(deficit);
		}
		if (result.compareTo(BigDecimal.ZERO) == 1) {
			return result;
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * <pre>
	 *  根据交易日期、约定还款日取得当期的还款日
	 * </pre>
	 *
	 * @param tradeDate
	 * @param promiseReturnDate
	 * @return
	 */
	private Date getCurrTermReturnDate(Date tradeDate, int promiseReturnDate) {
		Date dateAfterMonth = null;
		int dayOfMonth = DateUtil.getDayOfMonth(tradeDate);
		if (dayOfMonth > promiseReturnDate) {
			dateAfterMonth = DateUtil.getDateAfterMonth(tradeDate, 1, promiseReturnDate);
		} else {
			dateAfterMonth = DateUtil.getDateAfterMonth(tradeDate, 0, promiseReturnDate);
		}
		return dateAfterMonth;
	}

	/**
	 * <pre>
	 *根据还款日期和loan返回所有逾期或者未还款的还款计划
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	public List<RepaymentPlan> getAllInterestOrLoan(Date currDate, Long loanId) {
		Loan loan = loanService.get(loanId);
		Date currTermReturnDate = null;
		if (loan != null ) {
			if( loan.getReturnDate()!=null ){
				currTermReturnDate = getCurrTermReturnDate(currDate, loan.getReturnDate());
			}
		} else {
			Extension extension = extensionService.get(loanId);
			currTermReturnDate = getCurrTermReturnDate(currDate, extension.getReturnDate());
		}
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setRepayDayEnd(currTermReturnDate);
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setStatusExcept(EnumConstants.RepaymentPlanState.SETTLE.getValue());
		return repaymentPlanService.findListByVO(repaymentPlanVO);

	}

	/**
	 * <pre>
	 *根据还款日期和loan返回所有已结清的还款计划
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	public List<RepaymentPlan> getAllInterestOrLoanBySettle(Date currDate, Long loanId) {
		Loan loan = loanService.get(loanId);
		Date currTermReturnDate = null;
		if (loan != null) {
			if(loan.getReturnDate()!=null){
			currTermReturnDate = getCurrTermReturnDate(currDate, loan.getReturnDate());
			}
		} else {
			Extension extension = extensionService.get(loanId);
			currTermReturnDate = getCurrTermReturnDate(currDate, extension.getReturnDate());
		}
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setRepayDayEnd(currTermReturnDate);
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
		return repaymentPlanService.findListByVO(repaymentPlanVO);

	}

	/**
	 * <pre>
	 *根据loan返回所有逾期或者未还款的还款计划
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@Override
	public List<RepaymentPlan> getAllInterestOrLoan(Long loanId) {
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setStatusExcept(EnumConstants.RepaymentPlanState.SETTLE.getValue());
		return repaymentPlanService.findListByVO(repaymentPlanVO);
	}

	/**
	 * <pre>
	 * 通过借款ID获取挂账金额
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	public BigDecimal getAccAmount(Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		if (loanId == null)
			return result;
		Ledger ledger = legderService.getLedgerByAccountId(loanId);
		if (ledger != null && ledger.getCash() != null) {
			return ledger.getCash();
		}
		return result;
	}

	/**
	 * <pre>
	 * 通过传入借款ID和日期得到罚息减免的金额
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	public BigDecimal getReliefOfFine(Date currDate, Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		if (currDate == null || loanId == null)
			return result;
		BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(currDate, loanId);
		return reliefOfFine;
	}

	/**
	 * <pre>
	 *  通过传入借款计划list和日期获取逾期总额 不包含罚息
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	public BigDecimal getOverdueAmount(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		for (int i = 0; i < size; i++) {
			Date repayDay = repaymentPlanList.get(i).getRepayDay();
			if (repayDay.getTime() - currDate.getTime() < 0) {
				//剩余欠款
				BigDecimal deficit = repaymentPlanList.get(i).getDeficit();
				if (deficit != null) {
					result = result.add(deficit);
				}
			}
		}

		return result;
	}

	/**
	 * <pre>
	 *  通过传入借款计划list和日期 获得愈息罚息天数
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	public int getOverdueFineDay(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		int result = 0;
		if (CollectionUtil.isNullOrEmpty(repaymentPlanList))
			return result;
		if (repaymentPlanList.get(0).getPenaltyDate() != null) {
			result = (int) DateUtil.getDiffDay(repaymentPlanList.get(0).getPenaltyDate(), currDate);
		}
		return result < 0 ? 0 : result;
	}

	/**
	 * <pre>
	 *  通过传入借款计划list和日期 获取逾期罚息
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	public BigDecimal getFine(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		//愈息罚息天数
		long overdueFineDay = getOverdueFineDay(repaymentPlanList, currDate);
		if (overdueFineDay > 0) {
			Long loanId = repaymentPlanList.get(0).getLoanId();
			Loan loan = loanService.get(loanId);
			Long productId = null;
			//放款期限
			Long time = null;
			BigDecimal auditMoney=null;
			BigDecimal pactMoney=null;
			if (loan == null) {
				Extension extension = extensionService.get(loanId);
				productId = extension.getProductId();
				time = extension.getTime().longValue();
				auditMoney = extension.getAuditMoney();
				pactMoney=extension.getPactMoney();
				//如果传入的是展期ID，则通过展期ID获取loan
				//获取loanId
				Long id = loanExtensionService.getLoanIdByExtensionId(loanId);
				loan = loanService.get(id);
			} else {
				productId = loan.getProductId();
				time = loan.getTime();
				auditMoney = loan.getAuditMoney();
				pactMoney=loan.getPactMoney();
			}
			Product product = productService.get(productId);
			//逾期罚息费率 
			BigDecimal overdueInterestRate = product.getOverdueInterestRate();
			//车贷新费率（2016.9.1号后执行）
			BigDecimal overdueInterestRateCar = product.getOverdueInterestRateCar();
			//小企业贷
			if (product.getProductType().compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
				BigDecimal sumCurRemainingPrincipal = getSumCurRemainingPrincipal(loanId);
				BigDecimal sumCurRemainingInterestAmt = getSumCurRemainingInterestAmt(loanId);
				// 剩余本金 +剩余利息()
				BigDecimal remainPrincipalAndInterest = sumCurRemainingPrincipal.add(sumCurRemainingInterestAmt);
				//剩余本息*逾期天数*0.05%
				result = remainPrincipalAndInterest.multiply(BigDecimal.valueOf(overdueFineDay)).multiply(overdueInterestRate).setScale(2, BigDecimal.ROUND_HALF_UP);//保留小数点后两位，ROUND_HALF_UP 四舍五入
			} else if (product.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
				
//				if (time.compareTo(4L) > 0) {
//					BigDecimal sumCurRemainingPrincipal = getSumCurRemainingPrincipal(loanId);
//					//剩余本金*逾期天数*0.4%
//					result = sumCurRemainingPrincipal.multiply(BigDecimal.valueOf(overdueFineDay)).multiply(overdueInterestRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//				}
//				else
//				{
//					//剩余本金*逾期天数*0.4%
//					result =auditMoney.multiply(BigDecimal.valueOf(overdueFineDay)).multiply(overdueInterestRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//				}
				
				//如果借款创建时间是2016.9.1后以后，则费率为新费率，如果是之前，则费率是旧费率
				result =pactMoney.multiply(BigDecimal.valueOf(overdueFineDay)).multiply(overdueInterestRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//设置时间为2016-09-01 00:00:00
				String dateStr = sysParameterService.getByCode("CAR_FINE_NEW_RULE_EXECUTE_TIME").getParameterValue();
				if(StringUtils.isNotEmpty(dateStr)){
					if(loan !=null && loan.getCreatedTime() !=null){
						try {
							if(loan.getCreatedTime().getTime()-sdf.parse(dateStr).getTime()>=0){
								//如果是2016年9月1日之后创建的数据，则采用新费率
								result = null;
								result = pactMoney.multiply(BigDecimal.valueOf(overdueFineDay)).multiply(overdueInterestRateCar).setScale(2, BigDecimal.ROUND_HALF_UP);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(result.compareTo(new BigDecimal(100))== -1){//若罚息金额小于100
					result=new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			}
		}
		return result;
	}

	/**
	 * <pre>
	 *  计算至当前所有应还总和
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	public BigDecimal getSumDeficit(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		boolean inAdvanceRepayment = specialRepaymentService.isInAdvanceRepayment(repaymentPlanList.get(0).getLoanId());
		for (int i = 0; i < size; i++) {
			//如果申请了提前扣款的,则把当期的剩余欠款也加上
			if (inAdvanceRepayment) {
				if (repaymentPlanList.get(i).getDeficit() != null) {
					result = result.add(repaymentPlanList.get(i).getDeficit());
				}
			} else {
				if (repaymentPlanList.get(i).getRepayDay().getTime() - currDate.getTime() <= 0) {
					if (repaymentPlanList.get(i).getDeficit() != null) {
						result = result.add(repaymentPlanList.get(i).getDeficit());
					}
				}
			}
		}
		return result;
	}

	public BigDecimal getSumCurRemainingPrincipal(Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setStatusExcept(EnumConstants.RepaymentPlanState.SETTLE.getValue());
		List<RepaymentPlan> repaymentPlanList = repaymentPlanService.findListByVO(repaymentPlanVO);
		if (repaymentPlanList == null || repaymentPlanList.size() < 1)
			return result;
		for (int i = 0; i < repaymentPlanList.size(); i++) {
			if (repaymentPlanList.get(i).getCurRemainingPrincipal() != null) {
				result = result.add(repaymentPlanList.get(i).getCurRemainingPrincipal());
			}

		}
		return result;
	}

	public BigDecimal getSumCurRemainingInterestAmt(Long loanId) {
		BigDecimal result = BigDecimal.ZERO;
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		repaymentPlanVO.setStatusExcept(EnumConstants.RepaymentPlanState.SETTLE.getValue());
		List<RepaymentPlan> repaymentPlanList = repaymentPlanService.findListByVO(repaymentPlanVO);
		if (repaymentPlanList == null || repaymentPlanList.size() < 1)
			return result;
		for (int i = 0; i < repaymentPlanList.size(); i++) {
			if (repaymentPlanList.get(i).getCurRemainingInterestAmt() != null) {
				result = result.add(repaymentPlanList.get(i).getCurRemainingInterestAmt());
			}
		}
		return result;
	}

	@Override
	public BigDecimal getCurrPrincipal(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null ||  repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() >= 0) {
			if (repaymentPlanList.get(size - 1).getCurRemainingPrincipal() != null) {
				result = repaymentPlanList.get(size - 1).getCurRemainingPrincipal();
			}

		}
		if (result.compareTo(BigDecimal.ZERO) == 1) {
			return result;
		} else {
			return BigDecimal.ZERO;
		}
	}

	@Override
	public BigDecimal getCurrInterest(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() >= 0) {
			if (repaymentPlanList.get(size - 1).getCurRemainingInterestAmt() != null) {
				result = repaymentPlanList.get(size - 1).getCurRemainingInterestAmt();
			}
		}
		return result;

	}

	@Override
	public BigDecimal getCurrManageFee(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() >= 0) {
			BigDecimal curRemainingManagePart0Fee = repaymentPlanList.get(size - 1).getCurRemainingManagePart0Fee();
			BigDecimal curRemainingManagePart1Fee = repaymentPlanList.get(size - 1).getCurRemainingManagePart1Fee();
			if (curRemainingManagePart0Fee != null && curRemainingManagePart1Fee != null) {
				result = curRemainingManagePart0Fee.add(curRemainingManagePart1Fee);
			}
		}
		return result;
	}

	@Override
	public BigDecimal getPenalty(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0 || repaymentPlanList.get(repaymentPlanList.size() - 1).getRepayDay().getTime() - currDate.getTime() < 0)
			return result;
		int size = repaymentPlanList.size();
		result = repaymentPlanList.get(size - 1).getPenalty();
		if (result == null)
			return BigDecimal.ZERO;
		return result;
	}

	@Override
	public int getCurrTerm(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		int result = 0;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() >= 0) {
			result = repaymentPlanList.get(size - 1).getCurNum();
		}
		return result;
	}

	@Override
	public BigDecimal getCurrDeficitForRepayDay(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() == 0) {
			if (repaymentPlanList.get(size - 1).getDeficit() != null) {
				result = repaymentPlanList.get(size - 1).getDeficit();
			}
		}
		return result;
	}

	@Override
	public BigDecimal getCurrRepayAmount(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (currDate.getTime() - repaymentPlanList.get(size - 1).getRepayDay().getTime() >= 0) {
			if (repaymentPlanList.get(size - 1).getDeficit() != null) {
				result = repaymentPlanList.get(size - 1).getDeficit();
			}
		}
		return result;
	}

	@Override
	public BigDecimal getNextRepayAmount(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() >= 0) {
			if (repaymentPlanList.get(size - 1).getDeficit() != null) {
				result = repaymentPlanList.get(size - 1).getDeficit();
			}
		}
		return result;
	}

	@Override
	public int getOverdueTermCount(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		int result = 0;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		if (repaymentPlanList.get(size - 1).getRepayDay().getTime() - currDate.getTime() < 0) {
			result = repaymentPlanList.size();
		} else {
			result = repaymentPlanList.size() - 1;
		}
		return result;
	}

	@Override
	public BigDecimal getOverduePrincipal(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		for (int i = 0; i < size; i++) {
			Date repayDay = repaymentPlanList.get(i).getRepayDay();
			if (repayDay.before(currDate)) {
				BigDecimal curRemainingPrincipal = repaymentPlanList.get(i).getCurRemainingPrincipal();
				if (curRemainingPrincipal != null) {
					result = result.add(curRemainingPrincipal);
				}
			}
		}

		return result;
	}

	@Override
	public BigDecimal getOverdueInterest(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		for (int i = 0; i < size; i++) {
			Date repayDay = repaymentPlanList.get(i).getRepayDay();
			if (repayDay.before(currDate)) {
				BigDecimal curRemainingInterestAmt = repaymentPlanList.get(i).getCurRemainingInterestAmt();
				if (curRemainingInterestAmt != null) {
					result = result.add(curRemainingInterestAmt);
				}
			}
		}

		return result;
	}

	@Override
	public BigDecimal getOverdueManageFee(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		for (int i = 0; i < size; i++) {
			Date repayDay = repaymentPlanList.get(i).getRepayDay();
			if (repayDay.before(currDate)) {
				BigDecimal curRemainingManagePart0Fee = repaymentPlanList.get(i).getCurRemainingManagePart0Fee();
				BigDecimal curRemainingManagePart1Fee = repaymentPlanList.get(i).getCurRemainingManagePart1Fee();
				if (curRemainingManagePart0Fee != null && curRemainingManagePart1Fee != null) {
					result = result.add(curRemainingManagePart0Fee).add(curRemainingManagePart1Fee);
				}
			}
		}

		return result;
	}

	@Override
	public BigDecimal getOverdueOtherFee(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		int size = repaymentPlanList.size();
		for (int i = 0; i < size; i++) {
			Date repayDay = repaymentPlanList.get(i).getRepayDay();
			if (repayDay.before(currDate)) {
				//当期剩余评估费
				BigDecimal curRemainingEvalRate = repaymentPlanList.get(i).getCurRemainingEvalRate();
				//当期剩余咨询费
				BigDecimal curRemainingReferRate = repaymentPlanList.get(i).getCurRemainingReferRate();
				//当期剩余风险金
				BigDecimal curRemainingRisk = repaymentPlanList.get(i).getCurRemainingRisk();
				if (curRemainingEvalRate != null && curRemainingReferRate != null && curRemainingRisk != null) {
					result = result.add(curRemainingEvalRate).add(curRemainingReferRate).add(curRemainingRisk);
				}
			}
		}

		return result;
	}

	@Override
	public BigDecimal getMaxRepayMoney(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			//一次性还款金额
			BigDecimal onetimeRepaymentAmount = getOnetimeRepaymentAmount(repaymentPlanList, currDate);
			//逾期金额
			BigDecimal overdueAmount = getOverdueAmount(repaymentPlanList, currDate);
			//罚息金额
			BigDecimal fine = getFine(repaymentPlanList, currDate);
			//挂账金额
			BigDecimal accAmount = getAccAmount(repaymentPlanList.get(0).getLoanId());
			if (onetimeRepaymentAmount != null && overdueAmount != null && fine != null && accAmount != null) {
				BigDecimal maxRepayMoney = onetimeRepaymentAmount.add(overdueAmount).add(fine).subtract(accAmount);
				result = maxRepayMoney;
			}
		}
		return result;
	}

	@Override
	public BigDecimal getCarExtensionMaxRepayMoney(List<RepaymentPlan> repaymentPlanList, Date currDate) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() == 0)
			return result;
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			//一次性还款金额
			BigDecimal deficit = repaymentPlanList.get(repaymentPlanList.size() - 1).getDeficit();
			//逾期金额
			BigDecimal overdueAmount = getOverdueAmount(repaymentPlanList, currDate);
			//罚息金额
			BigDecimal fine = getFine(repaymentPlanList, currDate);
			//挂账金额
			BigDecimal accAmount = getAccAmount(repaymentPlanList.get(0).getLoanId());
			if (deficit != null && overdueAmount != null && fine != null && accAmount != null) {
				BigDecimal maxRepayMoney = deficit.add(overdueAmount).add(fine).subtract(accAmount);
				result = maxRepayMoney;
			}
		}
		return result;
	}
}
