package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;

public class SyncRepaymentMsgAssembler {

	
	public static SyncRepaymentMsgVO transferModel2VO (SyncRepaymentMsg syncRepaymentMsg) {
		if (syncRepaymentMsg == null) {
			return null;
		}
		SyncRepaymentMsgVO syncRepaymentMsgVO=new SyncRepaymentMsgVO();
		syncRepaymentMsgVO.setBuildDate(syncRepaymentMsg.getBuildDate());
		syncRepaymentMsgVO.setCurTime(syncRepaymentMsg.getCurTime());
		syncRepaymentMsgVO.setId(syncRepaymentMsg.getId());
		syncRepaymentMsgVO.setIdNum(syncRepaymentMsg.getIdNum());
		syncRepaymentMsgVO.setLoanId(syncRepaymentMsg.getLoanId());
		syncRepaymentMsgVO.setMobile(syncRepaymentMsg.getMobile());
		syncRepaymentMsgVO.setMsg(syncRepaymentMsg.getMsg());
		syncRepaymentMsgVO.setName(syncRepaymentMsg.getName());
		syncRepaymentMsgVO.setProductId(syncRepaymentMsg.getProductId());
		syncRepaymentMsgVO.setProductType(syncRepaymentMsg.getProductType());
		syncRepaymentMsgVO.setRepayAmount(syncRepaymentMsg.getRepayAmount());
		syncRepaymentMsgVO.setRepaymentId(syncRepaymentMsg.getRepaymentId());
		syncRepaymentMsgVO.setReturnDate(syncRepaymentMsg.getReturnDate());
		syncRepaymentMsgVO.setSendDate(syncRepaymentMsg.getSendDate());
		syncRepaymentMsgVO.setSendtimes(syncRepaymentMsg.getSendtimes());
		syncRepaymentMsgVO.setStatus(syncRepaymentMsg.getStatus());
		syncRepaymentMsgVO.setTempletId(syncRepaymentMsg.getTempletId());
		syncRepaymentMsgVO.setVersion(syncRepaymentMsg.getVersion());
		syncRepaymentMsgVO.setRepayDay(syncRepaymentMsg.getRepayDay());
		return syncRepaymentMsgVO;
	}
	
	public static SyncRepaymentMsg transferVO2Model (SyncRepaymentMsgVO syncRepaymentMsgVO) {
		if (syncRepaymentMsgVO == null) {
			return null;
		}
		SyncRepaymentMsg syncRepaymentMsg=new SyncRepaymentMsg();
		syncRepaymentMsg.setBuildDate(syncRepaymentMsgVO.getBuildDate());
		syncRepaymentMsg.setCurTime(syncRepaymentMsgVO.getCurTime());
		syncRepaymentMsg.setId(syncRepaymentMsgVO.getId());
		syncRepaymentMsg.setIdNum(syncRepaymentMsgVO.getIdNum());
		syncRepaymentMsg.setLoanId(syncRepaymentMsgVO.getLoanId());
		syncRepaymentMsg.setMobile(syncRepaymentMsgVO.getMobile());
		syncRepaymentMsg.setMsg(syncRepaymentMsgVO.getMsg());
		syncRepaymentMsg.setName(syncRepaymentMsgVO.getName());
		syncRepaymentMsg.setProductId(syncRepaymentMsgVO.getProductId());
		syncRepaymentMsg.setProductType(syncRepaymentMsgVO.getProductType());
		syncRepaymentMsg.setRepayAmount(syncRepaymentMsgVO.getRepayAmount());
		syncRepaymentMsg.setRepaymentId(syncRepaymentMsgVO.getRepaymentId());
		syncRepaymentMsg.setReturnDate(syncRepaymentMsgVO.getReturnDate());
		syncRepaymentMsg.setSendDate(syncRepaymentMsgVO.getSendDate());
		syncRepaymentMsg.setSendtimes(syncRepaymentMsgVO.getSendtimes());
		syncRepaymentMsg.setStatus(syncRepaymentMsgVO.getStatus());
		syncRepaymentMsg.setTempletId(syncRepaymentMsgVO.getTempletId());
		syncRepaymentMsg.setVersion(syncRepaymentMsgVO.getVersion());
		syncRepaymentMsg.setRepayDay(syncRepaymentMsgVO.getRepayDay());
		return syncRepaymentMsg;
	}
}
