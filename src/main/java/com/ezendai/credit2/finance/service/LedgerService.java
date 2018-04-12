package com.ezendai.credit2.finance.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.vo.LedgerVO;

/**
 * 
 *  @author liyuepeng
 *
 */
public interface LedgerService {
	
	boolean isExists(Map map);
	
	Ledger insert(Ledger ledger);
	
	List<Ledger> findListByVo(LedgerVO ledgerVo);
	
	int update(LedgerVO ledgerVo);
	
	Ledger get(Long ledgerId);
	
	/**
	 * 更新咨询账户总账(乙方管理费)
	 * 
	 * @param loan
	 */
	public void updateBrokerBManage(Loan loan);
	
	/**
	 * 更新咨询账户总账(咨询费)
	 * 
	 * @param loan
	 */
	public void updateBrokerConsult(Loan loan);
	
	/**
	 * 更新咨询账户总账(评估费)
	 * 
	 * @param loan
	 */
	public void updateBrokerAssessmentFee(Loan loan);
	
	/**
	 * 更新财富账户总账(丙方管理费)
	 * 
	 * @param loan
	 */
	public void updateTreasureCManage(Loan loan);
	
	/**
	 * 更新居间人总账(合同金额)
	 * 
	 * @param loan
	 */
	public void updateBrokerPactMoney(Loan loan);
	
	/**
	 * 更新风险金账户总账(风险金)
	 * 
	 * @param loan
	 */
	public void updateRisk(Loan loan);
	
	/**
	 * <pre>
	 * 根据accountId获取该accountId对应的总账信息 
	 * </pre>
	 *
	 * @param accountId
	 * @return
	 */
	Ledger getLedgerByAccountId(Long accountId);
	
	/**
	 * 根据科目号记总账
	 * @return
	 */
	public boolean updateLedgerByAcctTitle(Long accountId, String acctTitle, String DorC, BigDecimal amount);

	/**
	 * 
	 * <pre>
	 * 根据还款日获取借款金额大于0的借款ID
	 * </pre>
	 *
	 * @param returnDate
	 * @return
	 */
	List<Long> getAccountByReturnDate(Integer returnDate);

	
	/**
	 * 
	 * <pre>
	 * 记账处理
	 * </pre>
	 *
	 * @param flow
	 * @return
	 */
	public boolean accounting(Flow flow);


}
