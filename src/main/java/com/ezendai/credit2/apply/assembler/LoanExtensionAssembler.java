/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: LoanExtensionAssembler.java, v 0.1 2015年3月10日 下午1:52:28 00221921 Exp $
 */
public class LoanExtensionAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转VO
	 * </pre>
	 *
	 * @param loanExtension
	 * @return
	 */
	public static LoanExtensionVO transferModel2VO(LoanExtension loanExtension) {
		if(loanExtension == null) {
			return null;
		}
		
		LoanExtensionVO loanExtensionVO = new LoanExtensionVO();
		loanExtensionVO.setId(loanExtension.getId());
		loanExtensionVO.setLoanId(loanExtension.getLoanId());
		loanExtensionVO.setExtensionId(loanExtension.getExtensionId());
		
		return loanExtensionVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转Model
	 * </pre>
	 *
	 * @param loanExtensionVO
	 * @return
	 */
	public static LoanExtension transferVO2Model(LoanExtensionVO loanExtensionVO) {
		if(loanExtensionVO == null) {
			return null;
		}
		
		LoanExtension loanExtension = new LoanExtension();
		loanExtension.setId(loanExtensionVO.getId());
		loanExtension.setLoanId(loanExtensionVO.getLoanId());
		loanExtension.setExtensionId(loanExtensionVO.getExtensionId());
		
		return loanExtension;
	}
}
