package com.ezendai.credit2.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.after.controller.RepayEntryController;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.assembler.LedgerAssembler;
import com.ezendai.credit2.finance.dao.LedgerDao;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.finance.vo.LedgerVO;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;

/**
 * 
 * @author liyuepeng
 * 
 */
@Service
public class LedgerServiceImpl implements LedgerService {
	
	private static final Logger logger = Logger.getLogger(LedgerServiceImpl.class);


	@Autowired
	private LedgerDao ledgerDao;

	@Override
	public boolean isExists(Map map) {
		return ledgerDao.exists(map);
	}
	/**
	 * 
	 * <pre>
	 * 根据还款日获取借款金额大于0的借款ID
	 * </pre>
	 *
	 * @param returnDate
	 * @return
	 */
	@Override
	public List<Long> getAccountByReturnDate(Integer returnDate){
		return ledgerDao.getAccountByReturnDate(returnDate);
	}
	@Override
	public Ledger insert(Ledger ledger) {
		return ledgerDao.insert(ledger);
	}

	@Override
	public List<Ledger> findListByVo(LedgerVO ledgerVo) {
		return ledgerDao.findListByVo(ledgerVo);
	}

	@Override
	public int update(LedgerVO ledgerVo) {
		return ledgerDao.update(ledgerVo);
	}

	@Override
	public void updateBrokerBManage(Loan loan) {
		LedgerVO brokerLedgerVo = new LedgerVO();
		brokerLedgerVo
				.setAccountId(EnumConstants.InternalAccount.CONSULT_ACCOUNT
						.getValue().longValue());
		brokerLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> brokerLedgerList = ledgerDao.findListByVo(brokerLedgerVo);
		if (CollectionUtil.isNullOrEmpty(brokerLedgerList)
				|| brokerLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger brokerLedger = brokerLedgerList.get(0);
			brokerLedgerVo.setVersion(brokerLedger.getVersion());
			BigDecimal manageIncome = brokerLedger.getManageIncome().add(
					loan.getbManage());
			brokerLedgerVo.setManageIncome(manageIncome);
			brokerLedgerVo.setId(brokerLedger.getId());
			int result;
			try {
				result = ledgerDao.update(brokerLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户管理费处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户管理费处理失败，交易未完成！");
			}
		}

	}

	@Override
	public void updateBrokerConsult(Loan loan) {
		LedgerVO brokerLedgerVo = new LedgerVO();
		brokerLedgerVo
				.setAccountId(EnumConstants.InternalAccount.CONSULT_ACCOUNT
						.getValue().longValue());
		brokerLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> brokerLedgerList = ledgerDao.findListByVo(brokerLedgerVo);
		if (CollectionUtil.isNullOrEmpty(brokerLedgerList)
				|| brokerLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger brokerLedger = brokerLedgerList.get(0);
			brokerLedgerVo.setVersion(brokerLedger.getVersion());
			BigDecimal consultIncome = brokerLedger.getConsultIncome()
					.add(loan.getConsult());
			brokerLedgerVo.setConsultIncome(consultIncome);
			brokerLedgerVo.setId(brokerLedger.getId());
			int result;
			try {
				result = ledgerDao.update(brokerLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户咨询费处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户咨询费处理失败，交易未完成！");
			}
		}

	}

	@Override
	public void updateBrokerAssessmentFee(Loan loan) {
		LedgerVO brokerLedgerVo = new LedgerVO();
		brokerLedgerVo
				.setAccountId(EnumConstants.InternalAccount.CONSULT_ACCOUNT
						.getValue().longValue());
		brokerLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> brokerLedgerList = ledgerDao.findListByVo(brokerLedgerVo);
		if (CollectionUtil.isNullOrEmpty(brokerLedgerList)
				|| brokerLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger brokerLedger = brokerLedgerList.get(0);
			brokerLedgerVo.setVersion(brokerLedger.getVersion());
			BigDecimal assessmentFeeIncome = brokerLedger
					.getAssessmentFeeIncome().add(loan.getAssessment());
			brokerLedgerVo.setAssessmentFeeIncome(assessmentFeeIncome);
			brokerLedgerVo.setId(brokerLedger.getId());
			int result;
			try {
				result = ledgerDao.update(brokerLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户评估费处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户评估费处理失败，交易未完成！");
			}
		}

	}

	@Override
	public void updateTreasureCManage(Loan loan) {
		LedgerVO treasureLedgerVo = new LedgerVO();
		treasureLedgerVo
				.setAccountId(EnumConstants.InternalAccount.TREASURE_ACCOUNT
						.getValue().longValue());
		treasureLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> treasureLedgerList = ledgerDao.findListByVo(treasureLedgerVo);
		if (CollectionUtil.isNullOrEmpty(treasureLedgerList)
				|| treasureLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger treasureLedger = treasureLedgerList.get(0);
			treasureLedgerVo.setVersion(treasureLedger.getVersion());
			BigDecimal manageIncome =treasureLedger.getManageIncome().add(
					loan.getcManage());
			treasureLedgerVo.setManageIncome(manageIncome);
			treasureLedgerVo.setId(treasureLedger.getId());
			int result;
			try {
				result = ledgerDao.update(treasureLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户丙方管理费处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户丙方管理费处理失败，交易未完成！");
			}
		}

	}

	@Override
	public void updateBrokerPactMoney(Loan loan) {
		LedgerVO brokerLedgerVo = new LedgerVO();
		brokerLedgerVo
				.setAccountId(EnumConstants.InternalAccount.BROKER_ACCOUNT
						.getValue().longValue());
		brokerLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> brokerLedgerList = ledgerDao.findListByVo(brokerLedgerVo);
		if (CollectionUtil.isNullOrEmpty(brokerLedgerList)
				|| brokerLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger brokerLedger = brokerLedgerList.get(0);
			brokerLedgerVo.setVersion(brokerLedger.getVersion());
			BigDecimal cash = brokerLedger.getCash().subtract(loan.getPactMoney());
			brokerLedgerVo.setCash(cash);
			brokerLedgerVo.setId(brokerLedger.getId());
			int result;
			try {
				result = ledgerDao.update(brokerLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户贷款本金记账处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户贷款本金记账处理失败，交易未完成！");
			}
		}

	}

	@Override
	public void updateRisk(Loan loan) {
		LedgerVO riskLedgerVo = new LedgerVO();
		riskLedgerVo.setAccountId(EnumConstants.InternalAccount.RISK_ACCOUNT
				.getValue().longValue());
		riskLedgerVo.setType(EnumConstants.AccountType.INTERNAL_ACCOUNT
				.getValue());
		List<Ledger> riskLedgerList = ledgerDao.findListByVo(riskLedgerVo);
		if (CollectionUtil.isNullOrEmpty(riskLedgerList)
				|| riskLedgerList.size() > 1) {
			throw new BusinessException();
		} else {
			Ledger riskLedger = riskLedgerList.get(0);
			riskLedgerVo.setVersion(riskLedger.getVersion());
			BigDecimal riskIncome = riskLedger.getOtherIncome().add(loan.getRisk());
			riskLedgerVo.setOtherIncome(riskIncome);
			riskLedgerVo.setId(riskLedger.getId());
			int result;
			try {
				result = ledgerDao.update(riskLedgerVo);
			} catch (BusinessException e) {
				result = 0;
				throw new BusinessException("新开户风险金处理失败，交易未完成！");
			}
			if (result > 1) {
				throw new BusinessException("新开户风险金处理失败，交易未完成！");
			}
		}
	}

	@Override
	public Ledger get(Long ledgerId) {
		return ledgerDao.get(ledgerId);
	}

	@Override
	public Ledger getLedgerByAccountId(Long accountId) {
		return ledgerDao.findLedgerByAccountId(accountId);
	}

	@Override
	public boolean updateLedgerByAcctTitle(Long accountId, String acctTitle, String DorC, BigDecimal amount) {
		Ledger ledger = ledgerDao.findLedgerByAccountId(accountId);
		if(ledger==null){
			ledger = new Ledger();
			ledger.setAccountId(accountId);
			ledgerDao.insert(ledger);
			return true;
		}
		logger.info(JSONObject.toJSONString(ledger));;
		LedgerVO ledgerVO = LedgerAssembler.transferModel2VO(ledger);
		//现金
		if(acctTitle.equals(EnumConstants.AccountTitle.AMOUNT.getValue())){
			BigDecimal cash;
			if(DorC.endsWith("D")){
				cash = ledger.getCash().add(amount);
			}else{
				cash = ledger.getCash().subtract(amount);
			}
			ledgerVO.setCash(cash);
		}
		//应收利息
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_RECEIVABLE.getValue())){
			BigDecimal interestReceivable;
			if(DorC.endsWith("D")){
				interestReceivable = ledger.getInterestReceivable().add(amount);
			}else{
				interestReceivable = ledger.getInterestReceivable().subtract(amount);
			}
			ledgerVO.setInterestReceivable(interestReceivable);
		}
		//应收罚息
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_RECEIVABLE.getValue())){
			BigDecimal fineReceivable;
			if(DorC.endsWith("D")){
				fineReceivable = ledger.getFineReceivable().add(amount);
			}else{
				fineReceivable = ledger.getFineReceivable().subtract(amount);
			}
			ledgerVO.setFineReceivable(fineReceivable);
		}
		//借款本金
		else if(acctTitle.equals(EnumConstants.AccountTitle.LOAN_AMOUNT.getValue())){
			BigDecimal loanAmount;
			if(DorC.endsWith("D")){
				loanAmount = ledger.getLoanAmount().subtract(amount);
			}else{
				loanAmount = ledger.getLoanAmount().add(amount);
			}
			ledgerVO.setLoanAmount(loanAmount);
		}
				
		//应付利息
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_PAYABLE.getValue())){
			BigDecimal interestPayable;;
			if(DorC.endsWith("D")){
				interestPayable = ledger.getInterestPayable().subtract(amount);
			}else{
				interestPayable = ledger.getInterestPayable().add(amount);
			}
			ledgerVO.setInterestPayable(interestPayable);
		}
		//应付罚息
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_PAYABLE.getValue())){
			BigDecimal finePayable;
			if(DorC.endsWith("D")){
				finePayable = ledger.getFinePayable().subtract(amount);
			}else{
				finePayable = ledger.getFinePayable().add(amount);
			}
			ledgerVO.setFinePayable(finePayable);
		}
		//利息收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_INCOME.getValue())){
			BigDecimal interestIncome;
			if(DorC.endsWith("D")){
				interestIncome = ledger.getInterestIncome().subtract(amount);
			}else{
				interestIncome = ledger.getInterestIncome().add(amount);
			}
			ledgerVO.setInterestIncome(interestIncome);
		}
				
		//咨询费收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.CONSULT_INCOME.getValue())) {
			BigDecimal consultIncome;
			if(DorC.endsWith("D")) {
				consultIncome = ledger.getConsultIncome().subtract(amount);
			} else {
				consultIncome = ledger.getConsultIncome().add(amount);
			}
			ledgerVO.setConsultIncome(consultIncome);
		}
		//评估费收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.ASSESSMENT_FEE_INCOME.getValue())) {
			BigDecimal assessmentFeeIncome;
			if(DorC.endsWith("D")) {
				assessmentFeeIncome = ledger.getAssessmentFeeIncome().subtract(amount);
			} else {
				assessmentFeeIncome = ledger.getAssessmentFeeIncome().add(amount);
			}
			ledgerVO.setAssessmentFeeIncome(assessmentFeeIncome);
		}
		//管理费收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.MANAGE_INCOME.getValue())) {
			BigDecimal manageIncome;
			if(DorC.endsWith("D")) {
				manageIncome = ledger.getManageIncome().subtract(amount);
			} else {
				manageIncome = ledger.getManageIncome().add(amount);
			}
			ledgerVO.setManageIncome(manageIncome);
		}
		//违约金收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.PENALTY_INCOME.getValue())) {
			BigDecimal penaltyIncome;
			if(DorC.endsWith("D")) {
				penaltyIncome = ledger.getPenaltyIncome().subtract(amount);
			} else {
				penaltyIncome = ledger.getPenaltyIncome().add(amount);
			}
			ledgerVO.setPenaltyIncome(penaltyIncome);
		}
		//其他营业收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_INCOME.getValue())) {
			BigDecimal otherIncome;
			if(DorC.endsWith("D")) {
				otherIncome = ledger.getOtherIncome().add(amount);
			} else {
				otherIncome = ledger.getOtherIncome().subtract(amount);
			}
			ledgerVO.setOtherIncome(otherIncome);
		}
		//营业外收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.NONBUSINESS_INCOME.getValue())) {
			BigDecimal nonbusinessIncome;
			if(DorC.endsWith("D")) {
				nonbusinessIncome = ledger.getNonbusinessIncome().subtract(amount);
			} else {
				nonbusinessIncome = ledger.getNonbusinessIncome().add(amount);
			}
			ledgerVO.setNonbusinessIncome(nonbusinessIncome);
		}
		//利息支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue())) {
			BigDecimal interestExpense;
			if(DorC.endsWith("D")) {
				interestExpense = ledger.getInterestExpense().add(amount);
			} else {
				interestExpense = ledger.getInterestExpense().subtract(amount);
			}
			ledgerVO.setInterestExpense(interestExpense);
		}
				
		//咨询费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue())) {
			BigDecimal consultExpense;
			if(DorC.endsWith("D")) {
				consultExpense = ledger.getConsultExpense().add(amount);
			} else {
				consultExpense = ledger.getConsultExpense().subtract(amount);
			}
			ledgerVO.setConsultExpense(consultExpense);
		}
		//评估费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue())) {
			BigDecimal assessmentFeeExpense;
			if(DorC.endsWith("D")) {
				assessmentFeeExpense = ledger.getAssessmentFeeExpense().add(amount);
			} else {
				assessmentFeeExpense = ledger.getAssessmentFeeExpense().subtract(amount);
			}
			ledgerVO.setAssessmentFeeExpense(assessmentFeeExpense);
		}
		//管理费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.MANAGE_EXPENSE.getValue())) {
			BigDecimal manageExpense;
			if(DorC.endsWith("D")) {
				manageExpense = ledger.getManageExpense().add(amount);
			} else {
				manageExpense = ledger.getManageExpense().subtract(amount);
			}
			ledgerVO.setManageExpense(manageExpense);
		}
		//丙方管理费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue())) {
			BigDecimal cManageExpense;
			if(DorC.endsWith("D")) {
				cManageExpense = ledger.getManageExpense().add(amount);
			} else {
				cManageExpense = ledger.getManageExpense().subtract(amount);
			}
			ledgerVO.setManageExpense(cManageExpense);
		}
		//违约金支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.PENALTY_EXPENSE.getValue())) {
			BigDecimal penaltyExpense;
			if(DorC.endsWith("D")) {
				penaltyExpense = ledger.getPenaltyExpense().add(amount);
			} else {
				penaltyExpense = ledger.getPenaltyExpense().subtract(amount);
			}
			ledgerVO.setPenaltyExpense(penaltyExpense);
		}
		//其他营业支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_EXPENSE.getValue())) {
			BigDecimal otherExpense;
			if(DorC.endsWith("D")) {
				otherExpense = ledger.getOtherExpense().add(amount);
			} else {
				otherExpense = ledger.getOtherExpense().subtract(amount);
			}
			ledgerVO.setOtherExpense(otherExpense);
		}
		//营业外支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.NONBUSINESS_EXPENSE.getValue())) {
			BigDecimal nonbusinessExpense;
			if(DorC.endsWith("D")) {
				nonbusinessExpense = ledger.getNonbusinessExpense().add(amount);
			} else {
				nonbusinessExpense = ledger.getNonbusinessExpense().subtract(amount);
			}
			ledgerVO.setNonbusinessExpense(nonbusinessExpense);
		}
		//其他应付款
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_PAYABLE.getValue())){
			BigDecimal otherPayable;
			if(DorC.endsWith("D")){
				otherPayable = ledger.getOtherPayable().subtract(amount);
			}else{
				otherPayable = ledger.getOtherPayable().add(amount);
			}
			ledgerVO.setOtherPayable(otherPayable);
		}
		//风险金收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.RISK_INCOME.getValue())) {
			BigDecimal otherIncome;
			if(DorC.endsWith("C")) {
				otherIncome = ledger.getOtherIncome().add(amount);
			}else{
				otherIncome = ledger.getOtherIncome().subtract(amount);
			}
			ledgerVO.setOtherIncome(otherIncome);
		}
		//风险金支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.RISK_EXPENSE.getValue())){
			BigDecimal otherExpense;
			if(DorC.endsWith("D")){
				otherExpense = ledger.getOtherExpense().add(amount);
			}else{
				otherExpense = ledger.getOtherExpense().subtract(amount);
			}
			ledgerVO.setOtherExpense(otherExpense);
		}
		// 投资金额 -还本金 理财户 居间人
		else if(acctTitle.equals(EnumConstants.AccountTitle.INVEST_AMOUNT.getValue())){
			BigDecimal cash;
			if(DorC.endsWith("D")){
				cash = ledger.getCash().subtract(amount);
			}else{
				cash = ledger.getCash().add(amount);
			}
			ledgerVO.setCash(cash);
		}
		// 罚息支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_EXPENSE.getValue())){
			BigDecimal overdueInterestExpense;
			if(DorC.endsWith("D")){
				overdueInterestExpense = ledger.getOverdueInterestExpense().add(amount);
			}else{
				overdueInterestExpense = ledger.getOverdueInterestExpense().subtract(amount);
			}
			ledgerVO.setOverdueInterestExpense(overdueInterestExpense);
		}
		//罚息收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_INCOME.getValue())){
			BigDecimal overdueInterestIncome;
			if(DorC.endsWith("D")){
				overdueInterestIncome = ledger.getOverdueInterestIncome().subtract(amount);
			}else{
				overdueInterestIncome = ledger.getOverdueInterestIncome().add(amount);
			}
			ledgerVO.setOverdueInterestIncome(overdueInterestIncome);
		}
		//乙方管理费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue())){
			BigDecimal manageIncome;
			if(DorC.endsWith("D")){
				manageIncome = ledger.getManageIncome().subtract(amount);
			}else{
				manageIncome = ledger.getManageIncome().add(amount);
			}
			ledgerVO.setManageIncome(manageIncome);
		}
		ledgerDao.update(ledgerVO);
		return true;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 记账处理
	 * </pre>
	 *
	 * @param flow
	 * @return
	 */
	@Override
	public boolean accounting(Flow flow) {
		if (flow == null) {
			return true;
		}
		boolean result = true;
		if (flow.getAccountId() != null && StringUtil.isNotEmpty(flow.getAccountTitle())) {
			result = updateLedgerByAcctTitle(flow.getAccountId(), flow.getAccountTitle(), flow.getdOrC(), flow.getTradeAmount());
		}
		if (flow.getOppAccount() != null && StringUtil.isNotEmpty(flow.getOppAccountTitle())) {
			result = updateLedgerByAcctTitle(flow.getOppAccount(), flow.getOppAccountTitle(), flow.getOppDOrC(), flow.getTradeAmount());
		}
		return result;
	}

}
