package com.ezendai.credit2.finance.assembler;

import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.vo.FlowVO;

public class FlowAssembler {

	
	public static Flow transferVO2Model(FlowVO flowVo) {
		if (flowVo == null) {
			return null;
		}
		Flow flow = new Flow();
		flow.setAccountId(flowVo.getAccountId());
		flow.setdOrC(flowVo.getdOrC());
		flow.setAccountTitle(flowVo.getAccountTitle());
		flow.setTradeTime(flowVo.getTradeTime());
		flow.setTradeCode(flowVo.getTradeCode()); 
		flow.setTradeAmount(flowVo.getTradeAmount()); 
		flow.setBalance(flowVo.getBalance());
		flow.setTradeType(flowVo.getTradeType());
		flow.setTradeKind(flowVo.getTradeKind()); 
		flow.setTradeNo(flowVo.getTradeNo());
		flow.setReversedNo(flowVo.getReversedNo());
		flow.setTeller(flowVo.getTeller());
		flow.setAuthorizedTeller(flowVo.getAuthorizedTeller());
		flow.setSalesdepartmentId(flowVo.getSalesdepartmentId());
		flow.setOppAccount(flowVo.getOppAccount()); 
		flow.setOppAccountTitle(flowVo.getOppAccountTitle());
		flow.setOppDOrC(flowVo.getOppDOrC());
		flow.setTerm(flowVo.getTerm());
		flow.setRemark(flowVo.getRemark());
		return flow;

	}
}
