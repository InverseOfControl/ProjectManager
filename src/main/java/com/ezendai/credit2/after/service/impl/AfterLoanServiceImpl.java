package com.ezendai.credit2.after.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.after.controller.RepayEntryController;
import com.ezendai.credit2.after.service.AfterLoanService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.finance.assembler.RepayInfoAssembler;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.FlowService;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.finance.vo.RepayInfoVO;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;

/***
 * 
 * @author liyuepeng
 * 
 */
@Service
public class AfterLoanServiceImpl implements AfterLoanService {

	private static final Logger logger = Logger.getLogger(AfterLoanServiceImpl.class);
	@Autowired
	private LoanService loanService;

	@Autowired
	private RepaymentPlanService repaymentPlanService;

	@Autowired
	private RepayInfoService repayInfoService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private LedgerService ledgerService;

	@Autowired
	private RepayService repayService;

	@Autowired
	private SpecialRepaymentService specialRepaymentService;

	@Autowired
	private ExtensionService extensionService;

	/**
	 * 
	 * 还款
	 * @param repayInfo
	 * @return
	 * @see com.ezendai.credit2.after.service.AfterLoanService#RepayDeal(com.ezendai.credit2.finance.model.RepayInfo)
	 */
	@Transactional
	@Override
	public boolean repayDeal(RepayInfo repayInfo) {

		RepayInfoVO repayInfoVO = RepayInfoAssembler.transferModel2VO(repayInfo);
		repayInfo = repayInfoService.insert(repayInfoVO);

		//实际还款金额
		BigDecimal countAMT = repayInfo.getTradeAmount();
		Loan loan = loanService.get(repayInfo.getAccountId());
		if (loan == null) {
			throw new BusinessException("没有对应的借款，交易未完成！");
		}
		//当期期数
		Long currNum = loan.getTime() - loan.getResidualTime() + 1;

		//交易时间
		Date tradeTime = DateUtil.formatDate(repayInfo.getTradeTime());

		//结束还款日
		Date endRepayDate = DateUtil.formatDate(loan.getEndRepayDate());

		LoanVO loanVO = new LoanVO();
		loanVO.setId(loan.getId());
		// 所有到期未还贷款 逾期/当期
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(tradeTime,
			loan.getId());

		// 没有要还款 ，没到还款日时
		if (CollectionUtil.isNullOrEmpty(repaymentPlanList)) {
			if (repayInfo.getTradeAmount().compareTo(new BigDecimal("0.01")) < 0) {
				repayInfoService.delete(repayInfo.getId());
				return true;
			}
			//生成挂账
			handleCountAMT(countAMT, repayInfo);
			return true;
		}

		//计算挂账金额
		BigDecimal amount = repayService.getAccAmount(repayInfo.getAccountId());
		if (amount.compareTo(new BigDecimal("0.01")) >= 0) {
			//销账
			//生成销账流水
			Flow flow = flowService.createFlow(repayInfo, amount,
				EnumConstants.AccountTitle.AMOUNT.getValue(), "挂账部分销账", "C", "C", currNum.intValue());
			logger.info(JSONObject.toJSONString(flow));
			if (!ledgerService.accounting(flow)) {
				throw new BusinessException("多余现金挂账处理失败，交易未完成！");
			}
			countAMT = countAMT.add(amount);
		}

		//获取罚息
		amount = repayService.getFine(repaymentPlanList, tradeTime);
		//获取罚息减免
		BigDecimal relief = specialRepaymentService.getReliefOfFine(tradeTime, loan.getId());

		//开始分账
		//如果存在罚息，则先还罚息
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			//如果减免金额 = 0.01也要保证可以进行减免操作
			if (relief.compareTo(new BigDecimal("0.01")) >= 0) {
				//如果罚息大于减免
				if (amount.compareTo(relief) >= 0) {
					//计算罚息减免后的罚息金额
					countAMT = countAMT.subtract(amount.subtract(relief));
					//如果还款总额+罚息减免-罚息 小于零 则进行挂账
					if (countAMT.compareTo(BigDecimal.ZERO) < 0) {
						//还原还款总金额
						countAMT = countAMT.add(amount.subtract(relief));
						//生成挂账
						handleCountAMT(countAMT, repayInfo);
						return true;
					} else {
						//如果进行罚息扣款后的还款总金额 >=0 则将减免金额归零
						relief = BigDecimal.ZERO;
					}
				} else {
					//减免大于罚息的情况 肯定有钱还罚息
					relief = relief.subtract(amount);
					//还款总额里面将罚息减免-罚息的剩余金额加入还款总额
					countAMT = countAMT.add(relief);
					relief = BigDecimal.ZERO;
				}
			} else if (countAMT.compareTo(amount) >= 0) {
				//如果没有罚息减免，并且还款金额大于罚息
				countAMT = countAMT.subtract(amount);
			} else {
				//如果没有罚息减免，并且还款金额不够还罚息金额，则进行挂账
				handleCountAMT(countAMT, repayInfo);
				return true;
			}
			//还罚息
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//更新还款计划表
					RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
					repaymentPlanVO.setPenaltyDate(tradeTime);
					//					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setId(repaymentPlan.getId());
					repaymentPlanService.update(repaymentPlanVO);
				}
			}
			//生成罚息流水
			Flow amountFlow = flowService.createFlow(repayInfo, amount,
				EnumConstants.AccountTitle.FINE_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
			if (!ledgerService.accounting(amountFlow)) {
				throw new BusinessException("逾期罚息记账处理失败，交易未完成！");
			}
		}

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			handleRelief(tradeTime, loan.getId(), repayInfo, relief);
			return true;
		}

		//获取逾期管理费
		BigDecimal overdueManageFee = repayService
			.getOverdueManageFee(repaymentPlanList, tradeTime);
		//还逾期管理费
		if (overdueManageFee.compareTo(BigDecimal.ZERO) > 0) {
			//逾期管理费分账
			//如果存在逾期管理费，则分账，先还逾期管理费
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingManageRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingManageRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//逾期乙方管理费
					BigDecimal curRemainingManagePart0Fee = repaymentPlan
						.getCurRemainingManagePart0Fee();
					//逾期丙方管理费
					BigDecimal curRemainingManagePart1Fee = repaymentPlan
						.getCurRemainingManagePart1Fee();
					//逾期咨询费
					BigDecimal curRemainingReferRate = repaymentPlan.getCurRemainingReferRate();
					//逾期评估费
					BigDecimal curRemainingEvalRate = repaymentPlan.getCurRemainingEvalRate();

					//该期全部逾期管理费
					BigDecimal allCurRemainingManage = curRemainingManagePart0Fee
						.add(curRemainingManagePart1Fee).add(curRemainingReferRate)
						.add(curRemainingEvalRate);
					if (allCurRemainingManage.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.add(relief).compareTo(allCurRemainingManage) >= 0) {
							//逾期乙方管理费
							if (curRemainingManagePart0Fee.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingManagePart0Fee) > 0) {
									relief = relief.subtract(curRemainingManagePart0Fee);
								} else {
									countAMT = countAMT.subtract(curRemainingManagePart0Fee
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期乙方管理费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
								repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
								//生成流水
								Flow curRemainingManagePart0FeeFlow = flowService.createFlow(
									repayInfo, curRemainingManagePart0Fee,
									EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingManagePart0FeeFlow)) {
									throw new BusinessException("逾期乙方管理费流水记账处理失败，交易未完成！");
								}
							}
							//逾期丙方管理费
							if (curRemainingManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingManagePart1Fee) > 0) {
									relief = relief.subtract(curRemainingManagePart1Fee);
								} else {
									countAMT = countAMT.subtract(curRemainingManagePart1Fee
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期丙方管理费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
								repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
								//生成流水
								Flow curRemainingManagePart1FeeFlow = flowService.createFlow(
									repayInfo, curRemainingManagePart1Fee,
									EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingManagePart1FeeFlow)) {
									throw new BusinessException("逾期丙方管理费流水记账处理失败，交易未完成！");
								}
							}
							//逾期咨询费
							if (curRemainingReferRate.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingReferRate) > 0) {
									relief = relief.subtract(curRemainingReferRate);
								} else {
									countAMT = countAMT.subtract(curRemainingReferRate
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期咨询费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingReferRate(BigDecimal.ZERO);
								repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
								//生成流水
								Flow curRemainingReferRateFlow = flowService.createFlow(repayInfo,
									curRemainingReferRate,
									EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingReferRateFlow)) {
									throw new BusinessException("逾期咨询费流水记账处理失败，交易未完成！");
								}
							}
							//逾期评估费
							if (curRemainingEvalRate.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingEvalRate) > 0) {
									relief = relief.subtract(curRemainingEvalRate);
								} else {
									countAMT = countAMT.subtract(curRemainingEvalRate
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期评估费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingEvalRate(BigDecimal.ZERO);
								//生成流水
								Flow curRemainingEvalRateFlow = flowService.createFlow(repayInfo,
									curRemainingEvalRate,
									EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingEvalRateFlow)) {
									throw new BusinessException("逾期评估费流水记账处理失败，交易未完成！");
								}
							}
							//更新还款计划表
							if (loan.getTime() < 6) {
								curRemainingManageRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK.getValue());
							} else {
								curRemainingManageRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
										.getValue());
							}
							BigDecimal deficit = getDeficit(repaymentPlan.getId(),
								allCurRemainingManage);
							curRemainingManageRepaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
						} else {
							//记罚息减免流水和总账
							handleRelief(tradeTime, loan.getId(), repayInfo, relief);
							//生成挂账
							handleCountAMT(countAMT, repayInfo);
							return true;
						}
					}
				}
			}
		}

		//TODO why
		//		repaymentPlanList = repayService.getAllInterestOrLoan(tradeTime, loan.getId());
		//逾期风险金（只有短期车贷有）
		BigDecimal remainingRisk = getOverdueRemainingRisk(repaymentPlanList, tradeTime);
		if (remainingRisk.compareTo(BigDecimal.ZERO) > 0) {
			if (countAMT.add(relief).subtract(remainingRisk).compareTo(BigDecimal.ZERO) >= 0) {
				for (RepaymentPlan repaymentPlan : repaymentPlanList) {
					RepaymentPlanVO remainingRiskPlanVO = new RepaymentPlanVO();
					remainingRiskPlanVO.setId(repaymentPlan.getId());
					if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
						//逾期风险金
						BigDecimal curRemainingRisk = repaymentPlan.getCurRemainingRisk();
						if (curRemainingRisk.compareTo(BigDecimal.ZERO) > 0) {
							if (relief.compareTo(new BigDecimal("0.01")) > 0
								&& relief.compareTo(curRemainingRisk) > 0) {
								relief = relief.subtract(curRemainingRisk);
							} else {
								if (countAMT.compareTo(curRemainingRisk.subtract(relief)) < 0) {
									remainingRiskPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK
											.getValue());
									repaymentPlanService.update(remainingRiskPlanVO);
									//记罚息减免流水和总账
									handleRelief(tradeTime, loan.getId(), repayInfo, relief);
									//生成挂账
									handleCountAMT(countAMT, repayInfo);
									return true;
								} else {
									countAMT = countAMT.subtract(curRemainingRisk.subtract(relief));
									relief = BigDecimal.ZERO;
								}
							}
							//更新还款计划对应的风险金
							remainingRiskPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), curRemainingRisk);
							remainingRiskPlanVO.setDeficit(deficit);
							//生成流水
							Flow curEvalRateFlow = flowService.createFlow(repayInfo,
								curRemainingRisk, EnumConstants.AccountTitle.RISK_EXPENSE
									.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
							//记总账
							if (!ledgerService.accounting(curEvalRateFlow)) {
								throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
							}
							//更新还款计划表
							remainingRiskPlanVO
								.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
							repaymentPlanService.update(remainingRiskPlanVO);
						}
					}
				}
			} else {
				//记罚息减免流水和总账
				handleRelief(tradeTime, loan.getId(), repayInfo, relief);
				//生成挂账
				handleCountAMT(countAMT, repayInfo);
				return true;
			}
		}

		//还风险金
		repayRiskFee(repayInfo, countAMT.add(relief));

		//计算逾期利息
		BigDecimal overdueRemainingInterest = repayService.getOverdueInterest(repaymentPlanList,
			tradeTime);
		//还逾期利息
		if (overdueRemainingInterest.compareTo(BigDecimal.ZERO) > 0) {
			//逾期利息分账
			//如果存在逾期利息，则分账，还逾期利息
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingInterestAmtRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingInterestAmtRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//逾期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					//逾期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						if (relief.compareTo(new BigDecimal("0.01")) > 0
							&& relief.compareTo(curRemainingInterestAmt) > 0) {
							relief = relief.subtract(curRemainingInterestAmt);
							if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
								curRemainingInterestAmtRepaymentPlanVO.setFactReturnDate(tradeTime);
							} else {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
							}
							curRemainingInterestAmtRepaymentPlanVO
								.setCurRemainingInterestAmt(BigDecimal.ZERO);
						} else {
							if (countAMT.compareTo(curRemainingInterestAmt.subtract(relief)) < 0) {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
										.getValue());
								curRemainingInterestAmtRepaymentPlanVO
									.setCurRemainingInterestAmt(curRemainingInterestAmt.subtract(
										countAMT).subtract(relief));
								curRemainingInterestAmt = countAMT.add(relief);
								countAMT = BigDecimal.ZERO;

							} else {
								countAMT = countAMT.subtract(curRemainingInterestAmt
									.subtract(relief));
								if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
									curRemainingInterestAmtRepaymentPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.SETTLE
											.getValue());
									curRemainingInterestAmtRepaymentPlanVO
										.setFactReturnDate(tradeTime);
								} else {
									curRemainingInterestAmtRepaymentPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
											.getValue());
								}
								curRemainingInterestAmtRepaymentPlanVO
									.setCurRemainingInterestAmt(BigDecimal.ZERO);
							}
							relief = BigDecimal.ZERO;
						}
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("逾期利息流水记账处理失败，交易未完成！");
						}
						//更新还款计划表
						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingInterestAmt);
						curRemainingInterestAmtRepaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(curRemainingInterestAmtRepaymentPlanVO);
						updateLoanStatusByRepaymentPlanStatus(loan.getId(), tradeTime);
					}
				}
			}
		}

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			//记罚息减免流水和总账
			handleRelief(tradeTime, loan.getId(), repayInfo, relief);
			//根据还款计划表状态修改loan状态
			return true;
		}
		//剩余本金
		BigDecimal residualPactMoney = loan.getResidualPactMoney();
		//计算逾期本金
		BigDecimal overdueRemainingPrincipal = repayService.getOverduePrincipal(repaymentPlanList,
			tradeTime);
		//还逾期本金
		if (overdueRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
			//逾期本金分账
			//如果存在逾期本金，则分账，还逾期本金
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingPrincipalRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingPrincipalRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//逾期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						//该期本金全部还清
						if (relief.compareTo(new BigDecimal("0.01")) > 0
							&& relief.compareTo(curRemainingPrincipal) > 0) {
							relief = relief.subtract(curRemainingPrincipal);
							curRemainingPrincipalRepaymentPlanVO
								.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
							curRemainingPrincipalRepaymentPlanVO
								.setCurRemainingPrincipal(BigDecimal.ZERO);
							curRemainingPrincipalRepaymentPlanVO.setFactReturnDate(tradeTime);
						} else {
							//该期本金没有还清
							if (countAMT.compareTo(curRemainingPrincipal.subtract(relief)) < 0) {
								curRemainingPrincipalRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
								curRemainingPrincipalRepaymentPlanVO
									.setCurRemainingPrincipal(curRemainingPrincipal.subtract(
										countAMT).subtract(relief));
								curRemainingPrincipal = countAMT.add(relief);
								countAMT = BigDecimal.ZERO;
							} else {
								//该期本金全部还清
								countAMT = countAMT
									.subtract(curRemainingPrincipal.subtract(relief));
								curRemainingPrincipalRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
								curRemainingPrincipalRepaymentPlanVO
									.setCurRemainingPrincipal(BigDecimal.ZERO);
								curRemainingPrincipalRepaymentPlanVO.setFactReturnDate(tradeTime);
							}
							relief = BigDecimal.ZERO;
						}
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("逾期本金流水记账处理失败，交易未完成！");
						}
						//更新还款计划表
						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingPrincipal);
						curRemainingPrincipalRepaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(curRemainingPrincipalRepaymentPlanVO);
						residualPactMoney = residualPactMoney.subtract(curRemainingPrincipal);
					}
				}
			}
			loanVO.setResidualPactMoney(residualPactMoney);
			loanService.update(loanVO);
			//根据还款计划表状态修改loan状态
			updateLoanStatusByRepaymentPlanStatus(loan.getId(), tradeTime);
		}

		//记罚息减免流水和总账
		handleRelief(tradeTime, loan.getId(), repayInfo, relief);

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			return true;
		}
		//若减免金额不为0并且状态不为预结清的则加到还款金额中继续进行后续计算
		else if(relief.compareTo(BigDecimal.ZERO)> 0&&loan.getStatus().compareTo(
				EnumConstants.LoanStatus.预结清.getValue()) != 0){
			countAMT=countAMT.add(relief);
		}

		//计算一次性还款金额
		BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(
			repaymentPlanList, tradeTime);

		//一次性还款
		if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)
			&& countAMT.compareTo(onetimeRepaymentAmount) >= 0) {
			if (tradeTime.compareTo(endRepayDate) <= 0) {
				Date curDay = DateUtil.formatDate(new Date());
				if (curDay.compareTo(tradeTime) > 0) {
					Date nextRepayDate = DateUtil.getNextRepayDateIncludeToday(tradeTime);
					if (nextRepayDate.getDay() != loan.getReturnDate()) {
						nextRepayDate = DateUtil.getNextRepayDate(nextRepayDate);
					}
					if (curDay.compareTo(nextRepayDate) <= 0) {
						if (repayInfo.getPayType().compareTo(
							EnumConstants.TradeType.ON_TICK.getValue()) != 0) {

							//没有到还款日，预结清
							Flow flow = flowService.createFlow(repayInfo, countAMT,
								EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum.intValue());
							if (!ledgerService.accounting(flow)) {
								throw new BusinessException("一次性还款金额足额挂账处理失败，交易未完成！");
							}

							//更新loan表为预结清
							loanVO.setStatus(EnumConstants.LoanStatus.预结清.getValue());
							loanService.update(loanVO);
							return true;
						}
					}
				} else {
					if (repayInfo.getPayType()
						.compareTo(EnumConstants.TradeType.ON_TICK.getValue()) != 0) {
						//没有到还款日，预结清
						Flow flow = flowService.createFlow(repayInfo, countAMT,
							EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum.intValue());
						if (!ledgerService.accounting(flow)) {
							throw new BusinessException("一次性还款金额足额挂账处理失败，交易未完成！");
						}
						//更新loan表为预结清
						loanVO.setStatus(EnumConstants.LoanStatus.预结清.getValue());
						loanService.update(loanVO);
						return true;
					}
				}
			}

			//申请还款日已过最后一期
			Date lastRepayDate = DateUtil.formatDate(repaymentPlanList.get(
				repaymentPlanList.size() - 1).getRepayDay());
			if (lastRepayDate.compareTo(tradeTime) < 0) {
				//多余挂账
				Flow flow = flowService.createFlow(repayInfo, countAMT,
					EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum.intValue());
				if (!ledgerService.accounting(flow)) {
					throw new BusinessException("一次性还款后剩余现金挂账处理失败，交易未完成！");
				}
				//更新特殊还款表
				specialRepaymentService.updateSpecialRepaymentState(
					EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
					EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
				return true;
			}

			RepaymentPlan currRepaymentPlan = repaymentPlanList.get(repaymentPlanList.size() - 1);
			//一次性还款分账
			//还违约金
			if (currRepaymentPlan.getPenalty().compareTo(BigDecimal.ZERO) > 0) {
				Flow flow = flowService.createFlow(repayInfo, currRepaymentPlan.getPenalty(),
					EnumConstants.AccountTitle.PENALTY_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
				if (!ledgerService.accounting(flow)) {
					throw new BusinessException("违约金支出记账处理失败，交易未完成！");
				}
				countAMT = countAMT.subtract(currRepaymentPlan.getPenalty());
			}
			//一次性还款分账
			//退费
			if (currRepaymentPlan.getRefund().compareTo(BigDecimal.ZERO) > 0) {
				Flow curRefund = flowService.createFlow(repayInfo,
					currRepaymentPlan.getRefund(),
					EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue(), "", "C", "D", currNum.intValue());
				//记总账
				if (!ledgerService.accounting(curRefund)) {
					throw new BusinessException("一次性还款退费流水记账处理失败，交易未完成！");
				}
				//增加一次性还款金额退费金额相加 by ares.liu 00237489
				countAMT = countAMT.add(currRepaymentPlan.getRefund());
			}

			//车贷短期非约定还款日的第一期就做了一次性还款
			loan = loanService.get(loan.getId());
			if (loan.getTime().compareTo(4L) == 0 && loan.getResidualTime().compareTo(4) == 0) {
				//还本金
				BigDecimal remainingPrincipal = loan.getResidualPactMoney();
				if (remainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
					Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
						remainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT.getValue(), "",
						"D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
						throw new BusinessException("一次性还款本金流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingPrincipal);
				}
				//第二期的还款金额-前期费用 拆分				
				RepaymentPlanVO secondRepaymentPlanVO = new RepaymentPlanVO();
				secondRepaymentPlanVO.setLoanId(loan.getId());
				secondRepaymentPlanVO.setCurNum(2);
				List<RepaymentPlan> secondRepaymentPlanList = repaymentPlanService
					.findListByVO(secondRepaymentPlanVO);

				//拆分 利息
				RepaymentPlan secondRepaymentPlan = secondRepaymentPlanList.get(0);
				BigDecimal remainingInterestAmt = secondRepaymentPlan.getInterestAmt();
				if (remainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
					Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
						remainingInterestAmt,
						EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
						throw new BusinessException("一次性还款利息流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingInterestAmt);
				}
				//拆分风险金
				BigDecimal remainingRisk1 = secondRepaymentPlan.getRisk().subtract(loan.getRisk());
				if (remainingRisk1.compareTo(BigDecimal.ZERO) > 0) {

					Flow curRiskFlow = flowService.createFlow(repayInfo, remainingRisk1,
						EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curRiskFlow)) {
						throw new BusinessException("一次性还款风险金流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingRisk1);
				}
				//拆分咨询费
				BigDecimal remainingReferRate = secondRepaymentPlan.getReferRate().subtract(
					loan.getConsult());
				if (remainingReferRate.compareTo(BigDecimal.ZERO) > 0) {
					Flow curReferRateFlow = flowService.createFlow(repayInfo, remainingReferRate,
						EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curReferRateFlow)) {
						throw new BusinessException("一次性还款咨询费流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingReferRate);
				}
				//拆分评估费
				BigDecimal remainingEvalRate = secondRepaymentPlan.getEvalRate().subtract(
					loan.getAssessment());
				if (remainingEvalRate.compareTo(BigDecimal.ZERO) > 0) {

					Flow curEvalRateFlow = flowService.createFlow(repayInfo, remainingEvalRate,
						EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curEvalRateFlow)) {
						throw new BusinessException("一次性还款评估费流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingEvalRate);
				}
				//拆分丙方管理费
				BigDecimal remainingManagePart1Fee = secondRepaymentPlan.getManagePart1Fee()
					.subtract(loan.getcManage());
				if (remainingManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
					Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
						remainingManagePart1Fee,
						EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curManagePart1FeeFlow)) {
						throw new BusinessException("一次性还款丙方管理费流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingManagePart1Fee);
				}
			} else {
				if (loan.getAuditTime() > 4) {
					//还乙方管理费
					BigDecimal remainingManagePart0Fee = currRepaymentPlan
						.getCurRemainingManagePart0Fee();
					if (remainingManagePart0Fee.compareTo(BigDecimal.ZERO) > 0) {
						Flow curManagePart0FeeFlow = flowService.createFlow(repayInfo,
							remainingManagePart0Fee,
							EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
						//记总账
						if (!ledgerService.accounting(curManagePart0FeeFlow)) {
							throw new BusinessException("一次性还款乙方管理费流水记账处理失败，交易未完成！");
						}
						countAMT = countAMT.subtract(remainingManagePart0Fee);
					}
					//还丙方管理费
					BigDecimal remainingManagePart1Fee = currRepaymentPlan
						.getCurRemainingManagePart1Fee();
					if (remainingManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
						Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
							remainingManagePart1Fee,
							EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
						//记总账
						if (!ledgerService.accounting(curManagePart1FeeFlow)) {
							throw new BusinessException("一次性还款丙方管理费流水记账处理失败，交易未完成！");
						}
						countAMT = countAMT.subtract(remainingManagePart1Fee);
					}
					//还咨询费
					BigDecimal remainingReferRate = currRepaymentPlan.getCurRemainingReferRate();
					if (remainingReferRate.compareTo(BigDecimal.ZERO) > 0) {
						Flow curReferRateFlow = flowService.createFlow(repayInfo,
							remainingReferRate,
							EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
						//记总账
						if (!ledgerService.accounting(curReferRateFlow)) {
							throw new BusinessException("一次性还款咨询费流水记账处理失败，交易未完成！");
						}
						countAMT = countAMT.subtract(remainingReferRate);
					}
					//还评估费
					BigDecimal remainingEvalRate = currRepaymentPlan.getCurRemainingEvalRate();
					if (remainingEvalRate.compareTo(BigDecimal.ZERO) > 0) {

						Flow curEvalRateFlow = flowService.createFlow(repayInfo, remainingEvalRate,
							EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(), "", "D",
							"C", currNum.intValue());
						//记总账
						if (!ledgerService.accounting(curEvalRateFlow)) {
							throw new BusinessException("一次性还款评估费流水记账处理失败，交易未完成！");
						}
						countAMT = countAMT.subtract(remainingEvalRate);
					}
					//还风险金
					BigDecimal remainingRisk1 = currRepaymentPlan.getCurRemainingRisk();
					if (remainingRisk1.compareTo(BigDecimal.ZERO) > 0) {

						Flow curRiskFlow = flowService.createFlow(repayInfo, remainingRisk1,
							EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
						//记总账
						if (!ledgerService.accounting(curRiskFlow)) {
							throw new BusinessException("一次性还款风险金流水记账处理失败，交易未完成！");
						}
						countAMT = countAMT.subtract(remainingRisk1);
					}
				}
				//还利息
				BigDecimal remainingInterestAmt = currRepaymentPlan.getCurRemainingInterestAmt();
				if (remainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
					Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
						remainingInterestAmt,
						EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
						throw new BusinessException("一次性还款利息流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingInterestAmt);
				}

				//还本金
				BigDecimal remainingPrincipal = loan.getResidualPactMoney();
				if (remainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
					Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
						remainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT.getValue(), "",
						"D", "C", currNum.intValue());
					//记总账
					if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
						throw new BusinessException("一次性还款本金流水记账处理失败，交易未完成！");
					}
					countAMT = countAMT.subtract(remainingPrincipal);
				}
			}
			if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
				// 生成挂账流水，记总账
				Flow flow = flowService.createFlow(repayInfo, countAMT,
					EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum.intValue());
				if (!ledgerService.accounting(flow)) {
					throw new BusinessException("多余现金挂账处理失败，交易未完成！");
				}
			}
			//更新还款计划表，更新为结清
			RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
			repaymentPlanVO.setLoanId(loan.getId());
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
			repaymentPlanVO.setFactReturnDate(tradeTime);
			repaymentPlanVO.setDeficit(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
			List<Integer> notStatusList = new ArrayList<Integer>();
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_MANAGE.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_RISK.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_AMOUNT.getValue());
			repaymentPlanVO.setNotStatusList(notStatusList);
			repaymentPlanService.updateByStatus(repaymentPlanVO);

			//更新loan表为结清
			loanVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
			loanVO.setResidualPactMoney(BigDecimal.ZERO);
			loanVO.setResidualTime(0);
			loanService.update(loanVO);

			//特殊还款表一次性还款改为结束
			specialRepaymentService.updateSpecialRepaymentState(
				EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
				EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
			return true;
		} else {
			loan = loanService.get(loan.getId());
			//正常还款
			//是否为还款日判断
			if (getCurrTermReturnDate(tradeTime, loan.getReturnDate()).compareTo(tradeTime) > 0) {
				if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
					// 生成挂账流水，记总账
					Flow flow = flowService.createFlow(repayInfo, countAMT,
						EnumConstants.AccountTitle.AMOUNT.getValue(), "over", "D", "D", currNum.intValue());
					if (!ledgerService.accounting(flow)) {
						throw new BusinessException("多余现金挂账处理失败，交易未完成！");
					}
				}
				return true;
			}
			//非日终处理时，一次性还款金额不足时挂账
			if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)
				&& (!repayInfo.getPayType().equals(EnumConstants.TradeType.ON_TICK.getValue()))) {
				if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
					// 生成挂账流水，记总账
					Flow flow = flowService.createFlow(repayInfo, countAMT,
						EnumConstants.AccountTitle.AMOUNT.getValue(), "over", "D", "D", currNum.intValue());
					if (!ledgerService.accounting(flow)) {
						throw new BusinessException("多余现金挂账处理失败，交易未完成！");
					}
				}
				return true;
			}

			RepaymentPlanVO currRepaymentPlanVO = new RepaymentPlanVO();
			currRepaymentPlanVO.setLoanId(loan.getId());
			currRepaymentPlanVO.setRepayDay(tradeTime);
			//当期还款计划
			List<RepaymentPlan> curRepaymentPlanList = repaymentPlanService
				.findListByVO(currRepaymentPlanVO);
			RepaymentPlan repaymentPlan = repaymentPlanList.get(repaymentPlanList.size() - 1);
			if (CollectionUtil.isNotEmpty(curRepaymentPlanList)) {
				repaymentPlan = curRepaymentPlanList.get(0);
			}

			if (repaymentPlan.getRepayDay().compareTo(tradeTime) >= 0) {
				//如果够还当期，则修改Loan信息
				if (countAMT.compareTo(repaymentPlan.getDeficit()) >= 0) {
					//当前期数为最后一期
					if (repaymentPlan.getCurNum().compareTo(loan.getTime().intValue()) != 0) {
						//更新loan的本金余额和剩余还款月数
						if (loan.getTime().compareTo(4L) > 0) {
							loanVO.setResidualPactMoney(repaymentPlan.getRemainingPrincipal());
						}
						loanVO.setResidualTime(loan.getResidualTime() - 1);
						loanVO.setStatus(EnumConstants.LoanStatus.正常.getValue()); //Add lin 20150629
					} else {
						loanVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
						loanVO.setResidualPactMoney(BigDecimal.ZERO);
						loanVO.setResidualTime(0);
					}
					loanService.update(loanVO);

					//当期管理费分账
					RepaymentPlanVO curManageRepaymentPlanVO = new RepaymentPlanVO();
					curManageRepaymentPlanVO.setId(repaymentPlan.getId());
					//当期乙方管理费
					BigDecimal curManagePart0Fee = repaymentPlan.getCurRemainingManagePart0Fee();
					if (curManagePart0Fee.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curManagePart0Fee);
						//更新还款计划对应的当期乙方管理费
						curManageRepaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
						//生成流水
						Flow curManagePart0FeeFlow = flowService.createFlow(repayInfo,
							curManagePart0Fee, EnumConstants.AccountTitle.B_MANAGE_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curManagePart0FeeFlow)) {
							throw new BusinessException("当期乙方管理费流水记账处理失败，交易未完成！");
						}
					}

					//当期丙方管理费
					BigDecimal curManagePart1Fee = repaymentPlan.getCurRemainingManagePart1Fee();
					if (curManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curManagePart1Fee);
						//更新还款计划对应的当期丙方管理费
						curManageRepaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
						//生成流水
						Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
							curManagePart1Fee, EnumConstants.AccountTitle.C_MANAGE_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curManagePart1FeeFlow)) {
							throw new BusinessException("当期丙方管理费流水记账处理失败，交易未完成！");
						}
					}

					//当期咨询费
					BigDecimal curReferRate = repaymentPlan.getCurRemainingReferRate();
					if (curReferRate.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curReferRate);
						//更新还款计划对应的当期咨询费
						curManageRepaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
						//生成流水
						Flow curReferRateFlow = flowService.createFlow(repayInfo, curReferRate,
							EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(), repaymentPlan
								.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curReferRateFlow)) {
							throw new BusinessException("当期咨询费流水记账处理失败，交易未完成！");
						}
					}

					//当期评估费
					BigDecimal curEvalRate = repaymentPlan.getCurRemainingEvalRate();
					if (curEvalRate.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curEvalRate);
						//更新还款计划对应的当期评估费
						curManageRepaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
						//生成流水
						Flow curEvalRateFlow = flowService.createFlow(repayInfo, curEvalRate,
							EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
							repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curEvalRateFlow)) {
							throw new BusinessException("当期评估费流水记账处理失败，交易未完成！");
						}
					}

					//当期风险金
					BigDecimal currRisk = repaymentPlan.getCurRemainingRisk();
					//还当期风险金
					if (currRisk.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(currRisk);
						//更新还款计划对应的当期风险金
						curManageRepaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
						//生成流水
						Flow curRiskFlow = flowService.createFlow(repayInfo, currRisk,
							EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), repaymentPlan
								.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRiskFlow)) {
							throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
						}
					}

					//计算当期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curRemainingInterestAmt);
						//更新还款计划对应的利息
						curManageRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("当期利息流水记账处理失败，交易未完成！");
						}
					}
					//计算当期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curRemainingPrincipal);
						//更新还款计划对应的本金
						curManageRepaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("当期本金流水记账处理失败，交易未完成！");
						}
					}
					curManageRepaymentPlanVO.setFactReturnDate(tradeTime);
					curManageRepaymentPlanVO.setDeficit(BigDecimal.ZERO);
					curManageRepaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
						.getValue());

					repaymentPlanService.update(curManageRepaymentPlanVO);
					//生成挂账
					handleCountAMT(countAMT, repayInfo);
				} else {
					//只够部分还款
					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						return true;
					}
					RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
					repaymentPlanVO.setId(repaymentPlan.getId());
					BigDecimal currManageFee = getCurrManageFee(repaymentPlan);
					if (currManageFee.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.subtract(currManageFee).compareTo(BigDecimal.ZERO) >= 0) {
							countAMT = countAMT.subtract(currManageFee);
							//当期乙方管理费
							BigDecimal curManagePart0Fee = repaymentPlan
								.getCurRemainingManagePart0Fee();
							if (curManagePart0Fee.compareTo(BigDecimal.ZERO) > 0) {
								//更新还款计划对应的当期乙方管理费
								repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);

								//生成流水
								Flow curManagePart0FeeFlow = flowService.createFlow(repayInfo,
									curManagePart0Fee,
									EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curManagePart0FeeFlow)) {
									throw new BusinessException("当期乙方管理费流水记账处理失败，交易未完成！");
								}
							}

							//当期丙方管理费
							BigDecimal curManagePart1Fee = repaymentPlan
								.getCurRemainingManagePart1Fee();
							if (curManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {

								//更新还款计划对应的当期丙方管理费
								repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);

								//生成流水
								Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
									curManagePart1Fee,
									EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curManagePart1FeeFlow)) {
									throw new BusinessException("当期丙方管理费流水记账处理失败，交易未完成！");
								}
							}

							//当期咨询费
							BigDecimal curReferRate = repaymentPlan.getCurRemainingReferRate();
							if (curReferRate.compareTo(BigDecimal.ZERO) > 0) {
								//更新还款计划对应的当期咨询费
								repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);

								//生成流水
								Flow curReferRateFlow = flowService.createFlow(repayInfo,
									curReferRate,
									EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curReferRateFlow)) {
									throw new BusinessException("当期咨询费流水记账处理失败，交易未完成！");
								}
							}

							//当期评估费
							BigDecimal curEvalRate = repaymentPlan.getCurRemainingEvalRate();
							if (curEvalRate.compareTo(BigDecimal.ZERO) > 0) {
								//更新还款计划对应的当期评估费
								repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);

								//生成流水
								Flow curEvalRateFlow = flowService.createFlow(repayInfo,
									curEvalRate,
									EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curEvalRateFlow)) {
									throw new BusinessException("当期评估费流水记账处理失败，交易未完成！");
								}
							}
							//更新还款计划表
							if (loan.getAuditTime().compareTo(3) == 0) {
								repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK
									.getValue());
							} else {
								repaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
										.getValue());
							}
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), currManageFee);
							repaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(repaymentPlanVO);
						} else {
							//挂账
							handleCountAMT(countAMT, repayInfo);
							if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
								//更新特殊还款表
								specialRepaymentService.updateSpecialRepaymentState(
									EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan
										.getId(),
									EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT
										.getValue());
							}
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								loan.getId(),
								EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
							return true;
						}
					}
					//当期风险金
					BigDecimal currRisk = repaymentPlan.getCurRemainingRisk();
					//还当期风险金
					if (currRisk.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.subtract(currRisk).compareTo(BigDecimal.ZERO) >= 0) {
							countAMT = countAMT.subtract(currRisk);
							//更新还款计划对应的当期风险金
							repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
								.getValue());
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), currRisk);
							repaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(repaymentPlanVO);
							//生成流水
							Flow curRiskFlow = flowService.createFlow(repayInfo, currRisk,
								EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), repaymentPlan
									.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
							//记总账
							if (!ledgerService.accounting(curRiskFlow)) {
								throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
							}
						} else {
							//挂账
							handleCountAMT(countAMT, repayInfo);
							if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
								//更新特殊还款表
								specialRepaymentService.updateSpecialRepaymentState(
									EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan
										.getId(),
									EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT
										.getValue());
							}
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								loan.getId(),
								EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
							return true;
						}
					}

					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								loan.getId(),
								EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
						}
						//更新特殊还款表
						specialRepaymentService.updateSpecialRepaymentState(
							EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
							EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
						return true;
					}
					//计算当期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					//计算当期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();

					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.compareTo(curRemainingInterestAmt) >= 0) {
							countAMT = countAMT.subtract(curRemainingInterestAmt);
							if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
								repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
									.getValue());
								repaymentPlanVO.setFactReturnDate(tradeTime);
							} else {
								repaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
							}
							//更新还款计划对应的利息
							repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
						} else {
							//更新还款计划对应的利息
							repaymentPlanVO.setCurRemainingInterestAmt(curRemainingInterestAmt
								.subtract(countAMT));
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
								.getValue());
							curRemainingInterestAmt = countAMT;
							countAMT = BigDecimal.ZERO;
						}

						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingInterestAmt);
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(repaymentPlanVO);
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("当期利息流水记账处理失败，交易未完成！");
						}
					}
					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								loan.getId(),
								EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
						}
						//更新特殊还款表
						specialRepaymentService.updateSpecialRepaymentState(
							EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
							EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
						return true;
					}
					if (countAMT.compareTo(BigDecimal.ZERO) > 0
						&& curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.compareTo(curRemainingPrincipal) >= 0) {
							countAMT = countAMT.subtract(curRemainingPrincipal);
							//更新还款计划对应的本金
							repaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
								.getValue());
						} else {
							//更新还款计划对应的本金
							repaymentPlanVO.setCurRemainingPrincipal(curRemainingPrincipal
								.subtract(countAMT));
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
								.getValue());
							curRemainingPrincipal = countAMT;
							countAMT = BigDecimal.ZERO;
						}

						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingPrincipal);
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(repaymentPlanVO);
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("当期本金流水记账处理失败，交易未完成！");
						}

						//更新loan的剩余本金
						loanVO.setResidualPactMoney(loan.getResidualPactMoney().subtract(
							curRemainingPrincipal));
						loanService.update(loanVO);
					}
				}
			}
		}

		if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
			//更新特殊还款表
			specialRepaymentService.updateSpecialRepaymentState(
				EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
				EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		}
		//更新特殊还款表
		specialRepaymentService.updateSpecialRepaymentState(
			EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loan.getId(),
			EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		return true;
	}

	/**
	 * 
	 * <pre>
	 * 计算逾期风险金和
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	private BigDecimal getOverdueRemainingRisk(List<RepaymentPlan> repaymentPlanList, Date tradeTime) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlanList == null || repaymentPlanList.size() < 1)
			return result;
		for (RepaymentPlan repaymentPlan : repaymentPlanList) {
			if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
				if (repaymentPlan.getCurRemainingRisk() != null) {
					result = result.add(repaymentPlan.getCurRemainingRisk());
				}
			}
		}
		return result;
	}

	/*
	 * 
	 */
	private BigDecimal getDeficit(Long repaymentPlanId, BigDecimal subtractAmount) {
		BigDecimal result = BigDecimal.ZERO;
		RepaymentPlan repaymentPlan = repaymentPlanService.get(repaymentPlanId);
		if (repaymentPlan == null) {
			return result;
		} else {
			return repaymentPlan.getDeficit().subtract(subtractAmount);
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
	 * 
	 * <pre>
	 * 根据还款计划表状态修改loan状态
	 * </pre>
	 *
	 * @param loanId
	 * @param tradeTime
	 */
	private void updateLoanStatusByRepaymentPlanStatus(Long loanId, Date tradeTime) {
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanId);
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		List<RepaymentPlan> repaymentPlanList = repaymentPlanService.findListByVO(repaymentPlanVO);
		Loan loan = loanService.get(loanId);
		Long time = loan.getTime();
		Integer	residualTime = loan.getResidualTime();
		//如果还款计划都已结清，则贷款为逾期，则改为正常
		for (RepaymentPlan repaymentPlan : repaymentPlanList) {
			if (repaymentPlan.getRepayDay().compareTo(tradeTime) <= 0) {
				if ((repaymentPlan.getStatus().compareTo(
					EnumConstants.RepaymentPlanState.SETTLE.getValue()) == 0)) {
					loanVO.setStatus(EnumConstants.LoanStatus.正常.getValue());
					if (repaymentPlan.getCurNum().compareTo(loan.getTime().intValue()) != 0) {
						//更新loan的本金余额和剩余还款月数
						residualTime = time.intValue() - repaymentPlan.getCurNum();
					} else {
						loanVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
						residualTime = 0;
						break;
					}
				} else {
					loanVO.setStatus(EnumConstants.LoanStatus.逾期.getValue());
					break;
				}
			}
		}
		loanVO.setResidualTime(residualTime);
		loanService.update(loanVO);
	}

	/**
	 * 
	 * <pre>
	 * 根据还款计划表状态修改extension状态
	 * </pre>
	 *
	 * @param extensionId
	 * @param tradeTime
	 */
	private void updateExtensionStatusByRepaymentPlanStatus(Long extensionId, Date tradeTime) {
		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(extensionId);
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(extensionId);
		List<RepaymentPlan> repaymentPlanList = repaymentPlanService.findListByVO(repaymentPlanVO);
		Extension extension = extensionService.get(extensionId);
		Integer time = extension.getTime();
		Integer	residualTime = extension.getResidualTime();
		//如果还款计划都已结清，则贷款为逾期，则改为正常
		for (RepaymentPlan repaymentPlan : repaymentPlanList) {
			if (repaymentPlan.getRepayDay().compareTo(tradeTime) <= 0) {
				if ((repaymentPlan.getStatus().compareTo(
					EnumConstants.RepaymentPlanState.SETTLE.getValue()) == 0)) {
					extensionVO.setStatus(EnumConstants.LoanStatus.正常.getValue());
					if (repaymentPlan.getCurNum().compareTo(extension.getTime().intValue()) != 0) {
						//更新loan的本金余额和剩余还款月数
						residualTime = time.intValue() - repaymentPlan.getCurNum();
					} else {
						extensionVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
						residualTime = 0;
						break;
					}
				} else {
					extensionVO.setStatus(EnumConstants.LoanStatus.逾期.getValue());
					break;
				}
			}
		}
		extensionVO.setResidualTime(residualTime);
		extensionService.update(extensionVO);
	}
	
	/**
	 * 
	 * <pre>
	 * 获得管理费之和
	 * </pre>
	 *
	 * @param repaymentPlan
	 */
	private BigDecimal getCurrManageFee(RepaymentPlan repaymentPlan) {
		BigDecimal result = BigDecimal.ZERO;
		if (repaymentPlan == null) {
			return result;
		} else {
			return repaymentPlan.getCurRemainingEvalRate()
				.add(repaymentPlan.getCurRemainingManagePart0Fee())
				.add(repaymentPlan.getCurRemainingManagePart1Fee())
				.add(repaymentPlan.getCurRemainingReferRate());
		}
	}

	/**
	 * 
	 * <pre>
	 * 生成罚息减免流水并记总账
	 * </pre>
	 *
	 * @param tradeTime
	 * @param loanId
	 * @param repayInfo
	 * @param relief
	 */
	private void handleRelief(Date tradeTime, Long loanId, RepayInfo repayInfo, BigDecimal relief) {
		//记罚息减免流水和总账
		BigDecimal oldRelief = specialRepaymentService.getReliefOfFine(tradeTime, loanId);
//		if (oldRelief.compareTo(relief) != 0) {
//			oldRelief = oldRelief.subtract(relief);
			if (oldRelief.compareTo(BigDecimal.ZERO) > 0) {
				Flow reliefFlow = flowService.createFlow(repayInfo, oldRelief,
					EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue(),
					EnumConstants.AccountTitle.FINE_EXPENSE.getValue(), repayInfo.getAccountId(),
					EnumConstants.AccountTitle.FINE_INCOME.getValue(), "罚息减免流水", "D", "C");
				if (!ledgerService.accounting(reliefFlow)) {
					throw new BusinessException("逾期罚息减免流水记账处理失败，交易未完成！");
				}
				//更新特殊还款表			
				specialRepaymentService.updateSpecialRepaymentState(
					EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), loanId,
					EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
			}
//		}
	}

	/**
	 * 
	 * <pre>
	 * 剩余金额挂账
	 * </pre>
	 *
	 * @param countAMT
	 * @param repayInfo
	 */
	private void handleCountAMT(BigDecimal countAMT, RepayInfo repayInfo) {
		//分账结束，剩余部分挂账
		if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
			// 生成挂账流水，记总账
			Flow flow = flowService.createFlow(repayInfo, countAMT,
				EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", repayInfo.getTerm().intValue());
			if (!ledgerService.accounting(flow)) {
				throw new BusinessException("多余现金挂账处理失败，交易未完成！");
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 还风险金
	 * </pre>
	 *
	 * @param loan
	 * @param repayInfo
	 * @param countAMT
	 */
	private void repayRiskFee(RepayInfo repayInfo, BigDecimal countAMT) {
		Ledger ledger = ledgerService.getLedgerByAccountId(repayInfo.getAccountId());
		BigDecimal otherPayable = ledger.getOtherPayable();
		if (otherPayable.compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}
		//获取应还风险金
		BigDecimal riskFee = countAMT.compareTo(otherPayable) > 0 ? otherPayable : countAMT;
		//生成风险金垫付流水
		Flow riskFeeFlow = flowService.createFlow(repayInfo, riskFee,
			EnumConstants.AccountTitle.OTHER_PAYABLE.getValue(), "", "D", "C",repayInfo.getTerm().intValue());
		//记总账
		if (!ledgerService.accounting(riskFeeFlow)) {
			throw new BusinessException("还保证金记账失败，交易未完成！");
		}
	}

	/**
	 * 
	 * @param repayInfo
	 * @return
	 * @see com.ezendai.credit2.after.service.AfterLoanService#repayDealExtension(com.ezendai.credit2.finance.model.RepayInfo)
	 */
	@Transactional
	@Override
	public boolean repayDealExtension(RepayInfo repayInfo) {
		RepayInfoVO repayInfoVO = RepayInfoAssembler.transferModel2VO(repayInfo);
		repayInfo = repayInfoService.insert(repayInfoVO);

		//实际还款金额
		BigDecimal countAMT = repayInfo.getTradeAmount();
		Extension extension = extensionService.get(repayInfo.getAccountId());
		if (extension == null) {
			throw new BusinessException("没有对应的借款，交易未完成！");
		}

		//当前期数
		Integer currNum = extension.getTime() - extension.getResidualTime() + 1;
		//交易时间
		Date tradeTime = DateUtil.formatDate(repayInfo.getTradeTime());

		//结束还款日
		Date endRepayDate = DateUtil.formatDate(extension.getEndRepayDate());

		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(extension.getId());
		// 所有到期未还贷款 逾期/当期
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(tradeTime,
			extension.getId());

		// 没有要还款 ，没到还款日时
		if (CollectionUtil.isNullOrEmpty(repaymentPlanList)) {
			if (repayInfo.getTradeAmount().compareTo(new BigDecimal("0.01")) < 0) {
				repayInfoService.delete(repayInfo.getId());
				return true;
			}
			//生成挂账
			handleCountAMT(countAMT, repayInfo);
			return true;
		}

		//计算挂账金额
		BigDecimal amount = repayService.getAccAmount(repayInfo.getAccountId());
		if (amount.compareTo(new BigDecimal("0.01")) >= 0) {
			//销账
			//生成销账流水
			Flow flow = flowService.createFlow(repayInfo, amount,
				EnumConstants.AccountTitle.AMOUNT.getValue(), "挂账部分销账", "C", "C", currNum);
			if (!ledgerService.accounting(flow)) {
				throw new BusinessException("多余现金挂账处理失败，交易未完成！");
			}
			countAMT = countAMT.add(amount);
		}

		//获取罚息
		amount = repayService.getFine(repaymentPlanList, tradeTime);
		//获取罚息减免
		BigDecimal relief = specialRepaymentService.getReliefOfFine(tradeTime, extension.getId());

		//开始分账
		//如果存在罚息，则先还罚息
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			if (relief.compareTo(new BigDecimal("0.01")) > 0) {
				//计算罚息减免后的罚息金额
				if (amount.compareTo(relief) >= 0) {
					countAMT = countAMT.subtract(amount.subtract(relief));
					if (countAMT.compareTo(BigDecimal.ZERO) < 0) {
						countAMT = countAMT.add(amount.subtract(relief));
						//生成挂账
						handleCountAMT(countAMT, repayInfo);
						return true;
					} else {
						relief = BigDecimal.ZERO;
					}
				} else {
					relief = relief.subtract(amount);
					//还款总额里面将罚息减免-罚息的剩余金额加入还款总额
					countAMT = countAMT.add(relief);
					relief = BigDecimal.ZERO;
				}
			} else if (countAMT.compareTo(amount) >= 0) {
				countAMT = countAMT.subtract(amount);
			} else {
				//生成挂账
				handleCountAMT(countAMT, repayInfo);
				return true;
			}
			//还罚息
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//更新还款计划表
					RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
					repaymentPlanVO.setPenaltyDate(tradeTime);
					//					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setId(repaymentPlan.getId());
					repaymentPlanService.update(repaymentPlanVO);
				}
			}
			//生成罚息流水
			Flow amountFlow = flowService.createFlow(repayInfo, amount,
				EnumConstants.AccountTitle.FINE_EXPENSE.getValue(), "", "D", "C", currNum);
			if (!ledgerService.accounting(amountFlow)) {
				throw new BusinessException("逾期罚息记账处理失败，交易未完成！");
			}
		}

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			handleRelief(tradeTime, extension.getId(), repayInfo, relief);
			return true;
		}

		//获取逾期管理费
		BigDecimal overdueManageFee = repayService
			.getOverdueManageFee(repaymentPlanList, tradeTime);
		//还逾期管理费
		if (overdueManageFee.compareTo(BigDecimal.ZERO) > 0) {
			//逾期管理费分账
			//如果存在逾期管理费，则分账，先还逾期管理费
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingManageRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingManageRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {

					//逾期丙方管理费
					BigDecimal curRemainingManagePart1Fee = repaymentPlan
						.getCurRemainingManagePart1Fee();
					//逾期咨询费
					BigDecimal curRemainingReferRate = repaymentPlan.getCurRemainingReferRate();
					//逾期评估费
					BigDecimal curRemainingEvalRate = repaymentPlan.getCurRemainingEvalRate();

					//该期全部逾期管理费
					BigDecimal allCurRemainingManage = curRemainingManagePart1Fee.add(
						curRemainingReferRate).add(curRemainingEvalRate);
					if (allCurRemainingManage.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.add(relief).compareTo(allCurRemainingManage) >= 0) {
							//逾期丙方管理费
							if (curRemainingManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingManagePart1Fee) > 0) {
									relief = relief.subtract(curRemainingManagePart1Fee);
								} else {
									countAMT = countAMT.subtract(curRemainingManagePart1Fee
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期丙方管理费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
								repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
								//生成流水
								Flow curRemainingManagePart1FeeFlow = flowService.createFlow(
									repayInfo, curRemainingManagePart1Fee,
									EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingManagePart1FeeFlow)) {
									throw new BusinessException("逾期丙方管理费流水记账处理失败，交易未完成！");
								}
							}
							//逾期咨询费
							if (curRemainingReferRate.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingReferRate) > 0) {
									relief = relief.subtract(curRemainingReferRate);
								} else {
									countAMT = countAMT.subtract(curRemainingReferRate
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期咨询费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingReferRate(BigDecimal.ZERO);
								repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
								//生成流水
								Flow curRemainingReferRateFlow = flowService.createFlow(repayInfo,
									curRemainingReferRate,
									EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingReferRateFlow)) {
									throw new BusinessException("逾期咨询费流水记账处理失败，交易未完成！");
								}
							}
							//逾期评估费
							if (curRemainingEvalRate.compareTo(BigDecimal.ZERO) > 0) {
								if (relief.compareTo(new BigDecimal("0.01")) > 0
									&& relief.compareTo(curRemainingEvalRate) > 0) {
									relief = relief.subtract(curRemainingEvalRate);
								} else {
									countAMT = countAMT.subtract(curRemainingEvalRate
										.subtract(relief));
									relief = BigDecimal.ZERO;
								}
								//更新还款计划对应的逾期评估费
								curRemainingManageRepaymentPlanVO
									.setCurRemainingEvalRate(BigDecimal.ZERO);
								//生成流水
								Flow curRemainingEvalRateFlow = flowService.createFlow(repayInfo,
									curRemainingEvalRate,
									EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curRemainingEvalRateFlow)) {
									throw new BusinessException("逾期评估费流水记账处理失败，交易未完成！");
								}
							}
							//更新还款计划表
							curRemainingManageRepaymentPlanVO
								.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK.getValue());
							BigDecimal deficit = getDeficit(repaymentPlan.getId(),
								allCurRemainingManage);
							curRemainingManageRepaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(curRemainingManageRepaymentPlanVO);
						} else {
							//记罚息减免流水和总账
							handleRelief(tradeTime, extension.getId(), repayInfo, relief);
							//生成挂账
							handleCountAMT(countAMT, repayInfo);
							return true;
						}
					}
				}
			}
		}

		//逾期风险金（只有短期车贷有）
		BigDecimal remainingRisk = getOverdueRemainingRisk(repaymentPlanList, tradeTime);
		if (remainingRisk.compareTo(BigDecimal.ZERO) > 0) {
			if (countAMT.add(relief).subtract(remainingRisk).compareTo(BigDecimal.ZERO) >= 0) {
				for (RepaymentPlan repaymentPlan : repaymentPlanList) {
					RepaymentPlanVO remainingRiskPlanVO = new RepaymentPlanVO();
					remainingRiskPlanVO.setId(repaymentPlan.getId());
					if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
						//逾期风险金
						BigDecimal curRemainingRisk = repaymentPlan.getCurRemainingRisk();
						if (curRemainingRisk.compareTo(BigDecimal.ZERO) > 0) {
							if (relief.compareTo(new BigDecimal("0.01")) > 0
								&& relief.compareTo(curRemainingRisk) > 0) {
								relief = relief.subtract(curRemainingRisk);
							} else {
								if (countAMT.compareTo(curRemainingRisk.subtract(relief)) < 0) {
									remainingRiskPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK
											.getValue());
									repaymentPlanService.update(remainingRiskPlanVO);
									//记罚息减免流水和总账
									handleRelief(tradeTime, extension.getId(), repayInfo, relief);
									//生成挂账
									handleCountAMT(countAMT, repayInfo);
									return true;
								} else {
									countAMT = countAMT.subtract(curRemainingRisk.subtract(relief));
									relief = BigDecimal.ZERO;
								}
							}
							//更新还款计划对应的风险金
							remainingRiskPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), curRemainingRisk);
							remainingRiskPlanVO.setDeficit(deficit);
							//生成流水
							Flow curEvalRateFlow = flowService.createFlow(repayInfo,
								curRemainingRisk, EnumConstants.AccountTitle.RISK_EXPENSE
									.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
							//记总账
							if (!ledgerService.accounting(curEvalRateFlow)) {
								throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
							}
							//更新还款计划表
							remainingRiskPlanVO
								.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
							repaymentPlanService.update(remainingRiskPlanVO);
						}
					}
				}
			} else {
				//记罚息减免流水和总账
				handleRelief(tradeTime, extension.getId(), repayInfo, relief);
				//生成挂账
				handleCountAMT(countAMT, repayInfo);
				return true;
			}
		}

		//还风险金
		repayRiskFee(repayInfo, countAMT.add(relief));

		//计算逾期利息
		BigDecimal overdueRemainingInterest = repayService.getOverdueInterest(repaymentPlanList,
			tradeTime);
		//还逾期利息
		if (overdueRemainingInterest.compareTo(BigDecimal.ZERO) > 0) {
			//逾期利息分账
			//如果存在逾期利息，则分账，还逾期利息
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingInterestAmtRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingInterestAmtRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//逾期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					//逾期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						if (relief.compareTo(new BigDecimal("0.01")) > 0
							&& relief.compareTo(curRemainingInterestAmt) > 0) {
							relief = relief.subtract(curRemainingInterestAmt);
							if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
								curRemainingInterestAmtRepaymentPlanVO.setFactReturnDate(tradeTime);
							} else {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
							}
							curRemainingInterestAmtRepaymentPlanVO
								.setCurRemainingInterestAmt(BigDecimal.ZERO);
						} else {
							if (countAMT.compareTo(curRemainingInterestAmt.subtract(relief)) < 0) {
								curRemainingInterestAmtRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
										.getValue());
								curRemainingInterestAmtRepaymentPlanVO
									.setCurRemainingInterestAmt(curRemainingInterestAmt.subtract(
										countAMT).subtract(relief));
								curRemainingInterestAmt = countAMT.add(relief);
								countAMT = BigDecimal.ZERO;

							} else {
								countAMT = countAMT.subtract(curRemainingInterestAmt
									.subtract(relief));
								if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
									curRemainingInterestAmtRepaymentPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.SETTLE
											.getValue());
									curRemainingInterestAmtRepaymentPlanVO
										.setFactReturnDate(tradeTime);
								} else {
									curRemainingInterestAmtRepaymentPlanVO
										.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
											.getValue());
								}
								curRemainingInterestAmtRepaymentPlanVO
									.setCurRemainingInterestAmt(BigDecimal.ZERO);
							}
							relief = BigDecimal.ZERO;
						}
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("逾期利息流水记账处理失败，交易未完成！");
						}
						//更新还款计划表
						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingInterestAmt);
						curRemainingInterestAmtRepaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(curRemainingInterestAmtRepaymentPlanVO);
						updateExtensionStatusByRepaymentPlanStatus(extension.getId(), tradeTime);
					}
				}
			}
		}

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			//记罚息减免流水和总账
			handleRelief(tradeTime, extension.getId(), repayInfo, relief);
			//根据还款计划表状态修改loan状态
			return true;
		}
		//剩余本金
		BigDecimal residualPactMoney = extension.getResidualPactMoney();
		//计算逾期本金
		BigDecimal overdueRemainingPrincipal = repayService.getOverduePrincipal(repaymentPlanList,
			tradeTime);
		//还逾期本金
		if (overdueRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
			//逾期本金分账
			//如果存在逾期本金，则分账，还逾期本金
			for (RepaymentPlan repaymentPlan : repaymentPlanList) {
				RepaymentPlanVO curRemainingPrincipalRepaymentPlanVO = new RepaymentPlanVO();
				curRemainingPrincipalRepaymentPlanVO.setId(repaymentPlan.getId());
				// 该期为逾期
				if (repaymentPlan.getRepayDay().compareTo(tradeTime) < 0) {
					//逾期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						//该期本金全部还清
						if (relief.compareTo(new BigDecimal("0.01")) > 0
							&& relief.compareTo(curRemainingPrincipal) > 0) {
							relief = relief.subtract(curRemainingPrincipal);
							curRemainingPrincipalRepaymentPlanVO
								.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
							curRemainingPrincipalRepaymentPlanVO
								.setCurRemainingPrincipal(BigDecimal.ZERO);
							curRemainingPrincipalRepaymentPlanVO.setFactReturnDate(tradeTime);
						} else {
							//该期本金没有还清
							if (countAMT.compareTo(curRemainingPrincipal.subtract(relief)) < 0) {
								curRemainingPrincipalRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
								curRemainingPrincipalRepaymentPlanVO
									.setCurRemainingPrincipal(curRemainingPrincipal.subtract(
										countAMT).subtract(relief));
								curRemainingPrincipal = countAMT.add(relief);
								countAMT = BigDecimal.ZERO;
							} else {
								//该期本金全部还清
								countAMT = countAMT
									.subtract(curRemainingPrincipal.subtract(relief));
								curRemainingPrincipalRepaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
								curRemainingPrincipalRepaymentPlanVO
									.setCurRemainingPrincipal(BigDecimal.ZERO);
								curRemainingPrincipalRepaymentPlanVO.setFactReturnDate(tradeTime);
							}
							relief = BigDecimal.ZERO;
						}
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("逾期本金流水记账处理失败，交易未完成！");
						}
						//更新还款计划表
						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingPrincipal);
						curRemainingPrincipalRepaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(curRemainingPrincipalRepaymentPlanVO);
						residualPactMoney = residualPactMoney.subtract(curRemainingPrincipal);
					}
				}
			}
			extensionVO.setResidualPactMoney(residualPactMoney);
			extensionService.update(extensionVO);
			//根据还款计划表状态修改loan状态
			//updateLoanStatusByRepaymentPlanStatus(extension.getId(), tradeTime);
			updateExtensionStatusByRepaymentPlanStatus(extension.getId(), tradeTime);
		}

		//记罚息减免流水和总账
		handleRelief(tradeTime, extension.getId(), repayInfo, relief);

		if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
			return true;
		}
		//若减免金额不为0并且状态不为预结清的则加到还款金额中继续进行后续计算
		else if(relief.compareTo(BigDecimal.ZERO)> 0&& extension.getStatus().compareTo(
				EnumConstants.LoanStatus.预结清.getValue()) != 0){
			countAMT=countAMT.add(relief);
		}

		//计算一次性还款金额
		BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(
			repaymentPlanList, tradeTime);

		//一次性还款
		if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)
			&& countAMT.compareTo(onetimeRepaymentAmount) >= 0) {
			if (tradeTime.compareTo(endRepayDate) <= 0) {
				Date curDay = DateUtil.formatDate(new Date());
				if (curDay.compareTo(tradeTime) > 0) {
					Date nextRepayDate = DateUtil.getNextRepayDateIncludeToday(tradeTime);
					if (nextRepayDate.getDay() != extension.getReturnDate()) {
						nextRepayDate = DateUtil.getNextRepayDate(nextRepayDate);
					}
					if (curDay.compareTo(nextRepayDate) <= 0) {
						if (repayInfo.getPayType().compareTo(
							EnumConstants.TradeType.ON_TICK.getValue()) != 0) {

							//没有到还款日，预结清
							Flow flow = flowService.createFlow(repayInfo, countAMT,
								EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum);
							if (!ledgerService.accounting(flow)) {
								throw new BusinessException("一次性还款金额足额挂账处理失败，交易未完成！");
							}

							//更新loan表为预结清
							extensionVO.setStatus(EnumConstants.LoanStatus.预结清.getValue());
							extensionService.update(extensionVO);
							return true;
						}
					}
				} else {
					if (repayInfo.getPayType()
						.compareTo(EnumConstants.TradeType.ON_TICK.getValue()) != 0) {
						//没有到还款日，预结清
						Flow flow = flowService.createFlow(repayInfo, countAMT,
							EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum);
						if (!ledgerService.accounting(flow)) {
							throw new BusinessException("一次性还款金额足额挂账处理失败，交易未完成！");
						}
						//更新loan表为预结清
						extensionVO.setStatus(EnumConstants.LoanStatus.预结清.getValue());
						extensionService.update(extensionVO);
						return true;
					}
				}
			}

			//申请还款日已过最后一期
			Date lastRepayDate = DateUtil.formatDate(repaymentPlanList.get(
				repaymentPlanList.size() - 1).getRepayDay());
			if (lastRepayDate.compareTo(tradeTime) < 0) {
				//多余挂账
				Flow flow = flowService.createFlow(repayInfo, countAMT,
					EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum);
				if (!ledgerService.accounting(flow)) {
					throw new BusinessException("一次性还款后剩余现金挂账处理失败，交易未完成！");
				}
				//更新特殊还款表
				specialRepaymentService.updateSpecialRepaymentState(
					EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), extension.getId(),
					EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
				return true;
			}

			RepaymentPlan currRepaymentPlan = repaymentPlanList.get(repaymentPlanList.size() - 1);
			//还利息
			BigDecimal remainingInterestAmt = currRepaymentPlan.getCurRemainingInterestAmt();
			if (remainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
				Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
					remainingInterestAmt,
					EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue(), "", "D", "C", currNum.intValue());
				//记总账
				if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
					throw new BusinessException("一次性还款利息流水记账处理失败，交易未完成！");
				}
				countAMT = countAMT.subtract(remainingInterestAmt);
			}

			//还本金
			BigDecimal remainingPrincipal = extension.getResidualPactMoney();
			if (remainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
				Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
					remainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT.getValue(), "",
					"D", "C", currNum.intValue());
				//记总账
				if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
					throw new BusinessException("一次性还款本金流水记账处理失败，交易未完成！");
				}
				countAMT = countAMT.subtract(remainingPrincipal);
			}
			
			//一次性还款分账
			//车贷短期非约定还款日的第一期就做了一次性还款
			if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
				// 生成挂账流水，记总账
				Flow flow = flowService.createFlow(repayInfo, countAMT,
					EnumConstants.AccountTitle.AMOUNT.getValue(), "", "D", "D", currNum);
				if (!ledgerService.accounting(flow)) {
					throw new BusinessException("多余现金挂账处理失败，交易未完成！");
				}
			}
			//更新还款计划表，更新为结清
			RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
			repaymentPlanVO.setLoanId(extension.getId());
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
			repaymentPlanVO.setFactReturnDate(tradeTime);
			repaymentPlanVO.setDeficit(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
			List<Integer> notStatusList = new ArrayList<Integer>();
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_MANAGE.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_RISK.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
			notStatusList.add(EnumConstants.RepaymentPlanState.NOT_AMOUNT.getValue());
			repaymentPlanVO.setNotStatusList(notStatusList);
			repaymentPlanService.updateByStatus(repaymentPlanVO);

			//更新extension表为结清
			extensionVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
			extensionVO.setResidualPactMoney(BigDecimal.ZERO);
			extensionVO.setResidualTime(0);
			extensionService.update(extensionVO);
			

			//特殊还款表一次性还款改为结束
			specialRepaymentService.updateSpecialRepaymentState(
				EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), extension.getId(),
				EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
			return true;
		} else {
			extension = extensionService.get(extension.getId());
			//正常还款
			//是否为还款日判断
			if (getCurrTermReturnDate(tradeTime, extension.getReturnDate()).compareTo(tradeTime) > 0) {
				if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
					// 生成挂账流水，记总账
					Flow flow = flowService.createFlow(repayInfo, countAMT,
						EnumConstants.AccountTitle.AMOUNT.getValue(), "over", "D", "D", currNum);
					if (!ledgerService.accounting(flow)) {
						throw new BusinessException("多余现金挂账处理失败，交易未完成！");
					}
				}
				return true;
			}
			//非日终处理时，一次性还款金额不足时挂账
			if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)
				&& (!repayInfo.getPayType().equals(EnumConstants.TradeType.ON_TICK.getValue()))) {
				if (countAMT.compareTo(BigDecimal.ZERO) > 0) {
					// 生成挂账流水，记总账
					Flow flow = flowService.createFlow(repayInfo, countAMT,
						EnumConstants.AccountTitle.AMOUNT.getValue(), "over", "D", "D", currNum);
					if (!ledgerService.accounting(flow)) {
						throw new BusinessException("多余现金挂账处理失败，交易未完成！");
					}
				}
				return true;
			}

			RepaymentPlanVO currRepaymentPlanVO = new RepaymentPlanVO();
			currRepaymentPlanVO.setLoanId(extension.getId());
			currRepaymentPlanVO.setRepayDay(tradeTime);
			//当期还款计划
			List<RepaymentPlan> curRepaymentPlanList = repaymentPlanService
				.findListByVO(currRepaymentPlanVO);
			RepaymentPlan repaymentPlan = repaymentPlanList.get(repaymentPlanList.size() - 1);
			if (CollectionUtil.isNotEmpty(curRepaymentPlanList)) {
				repaymentPlan = curRepaymentPlanList.get(0);
			}

			if (repaymentPlan.getRepayDay().compareTo(tradeTime) >= 0) {
				//如果够还当期，则修改Loan信息
				if (countAMT.compareTo(repaymentPlan.getDeficit()) >= 0) {
					//当前期数为最后一期
					if (repaymentPlan.getCurNum().compareTo(extension.getTime().intValue()) != 0) {
						extensionVO.setResidualTime(extension.getResidualTime() - 1);
						extensionVO.setStatus(EnumConstants.LoanStatus.正常.getValue());//Add for LIN 20150629
					} else {
						extensionVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
						extensionVO.setResidualPactMoney(BigDecimal.ZERO);
						extensionVO.setResidualTime(0);
					}
					extensionService.update(extensionVO);

					//当期管理费分账
					RepaymentPlanVO curManageRepaymentPlanVO = new RepaymentPlanVO();
					curManageRepaymentPlanVO.setId(repaymentPlan.getId());

					//当期丙方管理费
					BigDecimal curManagePart1Fee = repaymentPlan.getCurRemainingManagePart1Fee();
					if (curManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curManagePart1Fee);
						//更新还款计划对应的当期丙方管理费
						curManageRepaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
						//生成流水
						Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
							curManagePart1Fee, EnumConstants.AccountTitle.C_MANAGE_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curManagePart1FeeFlow)) {
							throw new BusinessException("当期丙方管理费流水记账处理失败，交易未完成！");
						}
					}

					//当期咨询费
					BigDecimal curReferRate = repaymentPlan.getCurRemainingReferRate();
					if (curReferRate.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curReferRate);
						//更新还款计划对应的当期咨询费
						curManageRepaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
						//生成流水
						Flow curReferRateFlow = flowService.createFlow(repayInfo, curReferRate,
							EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(), repaymentPlan
								.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curReferRateFlow)) {
							throw new BusinessException("当期咨询费流水记账处理失败，交易未完成！");
						}
					}

					//当期评估费
					BigDecimal curEvalRate = repaymentPlan.getCurRemainingEvalRate();
					if (curEvalRate.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curEvalRate);
						//更新还款计划对应的当期评估费
						curManageRepaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
						//生成流水
						Flow curEvalRateFlow = flowService.createFlow(repayInfo, curEvalRate,
							EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
							repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curEvalRateFlow)) {
							throw new BusinessException("当期评估费流水记账处理失败，交易未完成！");
						}
					}
					
					//当期风险金
					BigDecimal currRisk = repaymentPlan.getCurRemainingRisk();
					//还当期风险金
					if (currRisk.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(currRisk);
						//更新还款计划对应的当期风险金
						curManageRepaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
						//生成流水
						Flow curRiskFlow = flowService.createFlow(repayInfo, currRisk,
							EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), repaymentPlan
								.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRiskFlow)) {
							throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
						}
					}

					//计算当期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curRemainingInterestAmt);
						//更新还款计划对应的利息
						curManageRepaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("当期利息流水记账处理失败，交易未完成！");
						}
					}
					//计算当期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();
					if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						countAMT = countAMT.subtract(curRemainingPrincipal);
						//更新还款计划对应的本金
						curManageRepaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("当期本金流水记账处理失败，交易未完成！");
						}
					}
					curManageRepaymentPlanVO.setFactReturnDate(tradeTime);
					curManageRepaymentPlanVO.setDeficit(BigDecimal.ZERO);
					curManageRepaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
						.getValue());

					repaymentPlanService.update(curManageRepaymentPlanVO);
					//生成挂账
					handleCountAMT(countAMT, repayInfo);
				} else {
					//只够部分还款
					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						return true;
					}
					RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
					repaymentPlanVO.setId(repaymentPlan.getId());
					BigDecimal currManageFee = getCurrManageFee(repaymentPlan);
					if (currManageFee.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.subtract(currManageFee).compareTo(BigDecimal.ZERO) >= 0) {
							countAMT = countAMT.subtract(currManageFee);

							//当期丙方管理费
							BigDecimal curManagePart1Fee = repaymentPlan
								.getCurRemainingManagePart1Fee();
							if (curManagePart1Fee.compareTo(BigDecimal.ZERO) > 0) {

								//更新还款计划对应的当期丙方管理费
								repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);

								//生成流水
								Flow curManagePart1FeeFlow = flowService.createFlow(repayInfo,
									curManagePart1Fee,
									EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curManagePart1FeeFlow)) {
									throw new BusinessException("当期丙方管理费流水记账处理失败，交易未完成！");
								}
							}
							
							//当期咨询费
							BigDecimal curReferRate = repaymentPlan.getCurRemainingReferRate();
							if (curReferRate.compareTo(BigDecimal.ZERO) > 0) {
								//更新还款计划对应的当期咨询费
								repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);

								//生成流水
								Flow curReferRateFlow = flowService.createFlow(repayInfo,
									curReferRate,
									EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curReferRateFlow)) {
									throw new BusinessException("当期咨询费流水记账处理失败，交易未完成！");
								}
							}

							//当期评估费
							BigDecimal curEvalRate = repaymentPlan.getCurRemainingEvalRate();
							if (curEvalRate.compareTo(BigDecimal.ZERO) > 0) {
								//更新还款计划对应的当期评估费
								repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);

								//生成流水
								Flow curEvalRateFlow = flowService.createFlow(repayInfo,
									curEvalRate,
									EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue(),
									repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
								//记总账
								if (!ledgerService.accounting(curEvalRateFlow)) {
									throw new BusinessException("当期评估费流水记账处理失败，交易未完成！");
								}
							}

							//更新还款计划表
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_RISK
								.getValue());
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), currManageFee);
							repaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(repaymentPlanVO);
						} else {
							//挂账
							handleCountAMT(countAMT, repayInfo);
							if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
								//更新特殊还款表
								specialRepaymentService.updateSpecialRepaymentState(
									EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
									extension.getId(),
									EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT
										.getValue());
							}
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								extension.getId(),
								EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
							return true;
						}
					}
					//当期风险金
					BigDecimal currRisk = repaymentPlan.getCurRemainingRisk();
					//还当期风险金
					if (currRisk.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.subtract(currRisk).compareTo(BigDecimal.ZERO) >= 0) {
							countAMT = countAMT.subtract(currRisk);
							//更新还款计划对应的当期风险金
							repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
								.getValue());
							BigDecimal deficit = getDeficit(repaymentPlan.getId(), currRisk);
							repaymentPlanVO.setDeficit(deficit);
							repaymentPlanService.update(repaymentPlanVO);
							//生成流水
							Flow curRiskFlow = flowService.createFlow(repayInfo, currRisk,
								EnumConstants.AccountTitle.RISK_EXPENSE.getValue(), repaymentPlan
									.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
							//记总账
							if (!ledgerService.accounting(curRiskFlow)) {
								throw new BusinessException("当期风险金流水记账处理失败，交易未完成！");
							}
						} else {
							//挂账
							handleCountAMT(countAMT, repayInfo);
							if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
								//更新特殊还款表
								specialRepaymentService.updateSpecialRepaymentState(
									EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
									extension.getId(),
									EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT
										.getValue());
							}
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								extension.getId(),
								EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
							return true;
						}
					}

					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								extension.getId(),
								EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
						}
						//更新特殊还款表
						specialRepaymentService.updateSpecialRepaymentState(
							EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
							extension.getId(),
							EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
						return true;
					}
					//计算当期利息
					BigDecimal curRemainingInterestAmt = repaymentPlan.getCurRemainingInterestAmt();
					//计算当期本金
					BigDecimal curRemainingPrincipal = repaymentPlan.getCurRemainingPrincipal();

					if (curRemainingInterestAmt.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.compareTo(curRemainingInterestAmt) >= 0) {
							countAMT = countAMT.subtract(curRemainingInterestAmt);
							if (curRemainingPrincipal.compareTo(BigDecimal.ZERO) == 0) {
								repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
									.getValue());
								repaymentPlanVO.setFactReturnDate(tradeTime);
							} else {
								repaymentPlanVO
									.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
										.getValue());
							}
							//更新还款计划对应的利息
							repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
						} else {
							//更新还款计划对应的利息
							repaymentPlanVO.setCurRemainingInterestAmt(curRemainingInterestAmt
								.subtract(countAMT));
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST
								.getValue());
							curRemainingInterestAmt = countAMT;
							countAMT = BigDecimal.ZERO;
						}

						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingInterestAmt);
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(repaymentPlanVO);
						//生成流水
						Flow curRemainingInterestAmtFlow = flowService.createFlow(repayInfo,
							curRemainingInterestAmt, EnumConstants.AccountTitle.INTEREST_EXPENSE
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingInterestAmtFlow)) {
							throw new BusinessException("当期利息流水记账处理失败，交易未完成！");
						}
					}
					if (countAMT.compareTo(BigDecimal.ZERO) == 0) {
						if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
							//更新特殊还款表
							specialRepaymentService.updateSpecialRepaymentState(
								EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
								extension.getId(),
								EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
						}
						//更新特殊还款表
						specialRepaymentService.updateSpecialRepaymentState(
							EnumConstants.SpecialRepaymentStatus.FINISH.getValue(),
							extension.getId(),
							EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
						return true;
					}
					if (countAMT.compareTo(BigDecimal.ZERO) > 0
						&& curRemainingPrincipal.compareTo(BigDecimal.ZERO) > 0) {
						if (countAMT.compareTo(curRemainingPrincipal) >= 0) {
							countAMT = countAMT.subtract(curRemainingPrincipal);
							//更新还款计划对应的本金
							repaymentPlanVO.setCurRemainingPrincipal(BigDecimal.ZERO);
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.SETTLE
								.getValue());
						} else {
							//更新还款计划对应的本金
							repaymentPlanVO.setCurRemainingPrincipal(curRemainingPrincipal
								.subtract(countAMT));
							repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT
								.getValue());
							curRemainingPrincipal = countAMT;
							countAMT = BigDecimal.ZERO;
						}

						BigDecimal deficit = getDeficit(repaymentPlan.getId(),
							curRemainingPrincipal);
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanService.update(repaymentPlanVO);
						//生成流水
						Flow curRemainingPrincipalFlow = flowService.createFlow(repayInfo,
							curRemainingPrincipal, EnumConstants.AccountTitle.LOAN_AMOUNT
								.getValue(), repaymentPlan.getCurNum().toString(), "D", "C", repaymentPlan.getCurNum());
						//记总账
						if (!ledgerService.accounting(curRemainingPrincipalFlow)) {
							throw new BusinessException("当期本金流水记账处理失败，交易未完成！");
						}

						//更新loan的剩余本金
						extension.setResidualPactMoney(extension.getResidualPactMoney().subtract(
							curRemainingPrincipal));
						extensionService.update(extensionVO);
					}
				}
			}
		}

		if (repayInfo.getTradeCode().equals(BizConstants.TRADE_CODE_ONEOFF)) {
			//更新特殊还款表
			specialRepaymentService.updateSpecialRepaymentState(
				EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), extension.getId(),
				EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		}
		//更新特殊还款表
		specialRepaymentService.updateSpecialRepaymentState(
			EnumConstants.SpecialRepaymentStatus.FINISH.getValue(), extension.getId(),
			EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue());
		return true;
	}
}
