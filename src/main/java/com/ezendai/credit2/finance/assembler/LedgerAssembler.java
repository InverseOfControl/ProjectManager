package com.ezendai.credit2.finance.assembler;

import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.vo.LedgerVO;

public class LedgerAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 * 
	 * @param ledger
	 * @return
	 */
	public static LedgerVO transferModel2VO(Ledger ledger) {
		if (ledger == null) {
			return null;
		}

		LedgerVO ledgerVO = new LedgerVO();
		ledgerVO.setId(ledger.getId());
		ledgerVO.setAccountId(ledger.getAccountId());
		ledgerVO.setAssessmentFeeExpense(ledger.getAssessmentFeeExpense());
		ledgerVO.setAssessmentFeeIncome(ledger.getAssessmentFeeIncome());
		ledgerVO.setCash(ledger.getCash());
		ledgerVO.setConsultExpense(ledger.getConsultExpense());
		ledgerVO.setConsultIncome(ledger.getConsultIncome());
		ledgerVO.setFinePayable(ledger.getFinePayable());
		ledgerVO.setFineReceivable(ledger.getFineReceivable());
		ledgerVO.setInterestExpense(ledger.getInterestExpense());
		ledgerVO.setInterestPayable(ledger.getInterestPayable());
		ledgerVO.setInterestReceivable(ledger.getInterestReceivable());
		ledgerVO.setLoanAmount(ledger.getLoanAmount());
		ledgerVO.setManageExpense(ledger.getManageExpense());
		ledgerVO.setManageIncome(ledger.getManageIncome());
		ledgerVO.setNonbusinessExpense(ledger.getNonbusinessExpense());
		ledgerVO.setNonbusinessIncome(ledger.getNonbusinessIncome());
		ledgerVO.setOtherExpense(ledger.getOtherExpense());
		ledgerVO.setOtherIncome(ledger.getOtherIncome());
		ledgerVO.setOtherPayable(ledger.getOtherPayable());
		ledgerVO.setOtherReceivable(ledger.getOtherReceivable());
		ledgerVO.setOverdueInterestIncome(ledger.getOverdueInterestIncome());
		ledgerVO.setPenaltyExpense(ledger.getPenaltyExpense());
		ledgerVO.setPenaltyIncome(ledger.getPenaltyIncome());
		ledgerVO.setRemark(ledger.getRemark());
		ledgerVO.setType(ledger.getType());
		ledgerVO.setOverdueInterestExpense(ledger.getOverdueInterestExpense());
		ledgerVO.setInterestIncome(ledger.getInterestIncome());
		ledgerVO.setVersion(ledger.getVersion());
		return ledgerVO;
	}
}
