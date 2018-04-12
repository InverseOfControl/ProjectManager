/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.assembler;

import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.vo.RepayInfoVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: RepayInfoAssembler.java, v 0.1 2014-9-11 下午05:35:51 liyuepeng Exp $
 */
public class RepayInfoAssembler {

	public static RepayInfo transferVO2Model(RepayInfoVO repayInfoVo) {
		if (repayInfoVo == null) {
			return null;
		}
		RepayInfo repayInfo = new RepayInfo();
		repayInfo.setAccountId(repayInfoVo.getAccountId());
		repayInfo.setAuthorizedTeller(repayInfoVo.getAuthorizedTeller());
		repayInfo.setOfferId(repayInfoVo.getOfferId());
		repayInfo.setPayType(repayInfoVo.getPayType());
		repayInfo.setReversedNo(repayInfoVo.getReversedNo());
		repayInfo.setSalesdepartmentId(repayInfoVo.getSalesdepartmentId());
		repayInfo.setTeller(repayInfoVo.getTeller());
		repayInfo.setTerm(repayInfoVo.getTerm());
		repayInfo.setTradeAmount(repayInfoVo.getTradeAmount());
		repayInfo.setTradeCode(repayInfoVo.getTradeCode());
		repayInfo.setTradeKind(repayInfoVo.getTradeKind());
		repayInfo.setTradeNo(repayInfoVo.getTradeNo());
		repayInfo.setTradeTime(repayInfoVo.getTradeTime());
		repayInfo.setVersion(repayInfoVo.getVersion());
		repayInfo.setRemark(repayInfoVo.getRemark());
		return repayInfo;
	}
	
	public static RepayInfoVO transferModel2VO(RepayInfo repayInfo) {
		if (repayInfo == null) {
			return null;
		}
		RepayInfoVO repayInfoVO = new RepayInfoVO();
		repayInfoVO.setAccountId(repayInfo.getAccountId());
		repayInfoVO.setAuthorizedTeller(repayInfo.getAuthorizedTeller());
		repayInfoVO.setOfferId(repayInfo.getOfferId());
		repayInfoVO.setPayType(repayInfo.getPayType());
		repayInfoVO.setReversedNo(repayInfo.getReversedNo());
		repayInfoVO.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		repayInfoVO.setTeller(repayInfo.getTeller());
		repayInfoVO.setTerm(repayInfo.getTerm());
		repayInfoVO.setTradeAmount(repayInfo.getTradeAmount());
		repayInfoVO.setTradeCode(repayInfo.getTradeCode());
		repayInfoVO.setTradeKind(repayInfo.getTradeKind());
		repayInfoVO.setTradeNo(repayInfo.getTradeNo());
		repayInfoVO.setTradeTime(repayInfo.getTradeTime());
		repayInfoVO.setVersion(repayInfo.getVersion());
		repayInfoVO.setRemark(repayInfo.getRemark());
		return repayInfoVO;
	}
}
