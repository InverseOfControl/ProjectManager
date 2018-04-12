package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.vo.CreditHistoryVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: CreditAssembler.java, v 0.1 2014年7月30日 下午5:22:27 zhangshihai Exp $
 */
public class CreditHistoryAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param creditHistory
	 * @return
	 */
	public static CreditHistoryVO transferModel2VO (CreditHistory creditHistory) {
		if (creditHistory == null) {
			return null;
		}
		
		CreditHistoryVO creditHistoryVO = new CreditHistoryVO();
		creditHistoryVO.setId(creditHistory.getId());
		creditHistoryVO.setPersonId(creditHistory.getPersonId());
		creditHistoryVO.setHasCreditCard(creditHistory.getHasCreditCard());
		creditHistoryVO.setCardNum(creditHistory.getCardNum());
		creditHistoryVO.setTotalAmount(creditHistory.getTotalAmount());
		creditHistoryVO.setOverdrawAmount(creditHistory.getOverdrawAmount());
		creditHistoryVO.setCreator(creditHistory.getCreator());
		creditHistoryVO.setCreatorId(creditHistory.getCreatorId());
		creditHistoryVO.setCreatedTime(creditHistory.getCreatedTime());
		creditHistoryVO.setModifier(creditHistory.getModifier());
		creditHistoryVO.setModifierId(creditHistory.getModifierId());
		creditHistoryVO.setVersion(creditHistory.getVersion());
		creditHistoryVO.setLoanId(creditHistory.getLoanId());
		return creditHistoryVO;
	}
	
	public static CreditHistory transferVO2Model (CreditHistoryVO creditHistoryVO) {
		if (creditHistoryVO == null) {
			return null;
		}
		
		CreditHistory creditHistory = new CreditHistory();
		creditHistory.setId(creditHistoryVO.getId());
		creditHistory.setPersonId(creditHistoryVO.getPersonId());
		creditHistory.setHasCreditCard(creditHistoryVO.getHasCreditCard());
		creditHistory.setCardNum(creditHistoryVO.getCardNum());
		creditHistory.setTotalAmount(creditHistoryVO.getTotalAmount());
		creditHistory.setOverdrawAmount(creditHistoryVO.getOverdrawAmount());
		creditHistory.setCreator(creditHistoryVO.getCreator());
		creditHistory.setCreatorId(creditHistoryVO.getCreatorId());
		creditHistory.setCreatedTime(creditHistoryVO.getCreatedTime());
		creditHistory.setModifier(creditHistoryVO.getModifier());
		creditHistory.setModifierId(creditHistoryVO.getModifierId());
		creditHistory.setVersion(creditHistoryVO.getVersion());
		creditHistory.setLoanId(creditHistoryVO.getLoanId());
		return creditHistory;
	}
}
