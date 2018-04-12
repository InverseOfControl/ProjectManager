package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.model.SyncRepaymentMsgLog;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;


public class SyncRepaymentMsgLogAssembler {

	
	public static SyncRepaymentMsgLogVO transferModel2VO (SyncRepaymentMsgLog syncRepaymentMsgLog) {
		if (syncRepaymentMsgLog == null) {
			return null;
		}
		SyncRepaymentMsgLogVO syncRepaymentMsgLogVO=new SyncRepaymentMsgLogVO();
		syncRepaymentMsgLogVO.setBuildDate(syncRepaymentMsgLog.getBuildDate());
		syncRepaymentMsgLogVO.setCurTime(syncRepaymentMsgLog.getCurTime());
		syncRepaymentMsgLogVO.setId(syncRepaymentMsgLog.getId());
		syncRepaymentMsgLogVO.setIdNum(syncRepaymentMsgLog.getIdNum());
		syncRepaymentMsgLogVO.setLoanId(syncRepaymentMsgLog.getLoanId());
		syncRepaymentMsgLogVO.setMobile(syncRepaymentMsgLog.getMobile());
		syncRepaymentMsgLogVO.setMsg(syncRepaymentMsgLog.getMsg());
		syncRepaymentMsgLogVO.setName(syncRepaymentMsgLog.getName());
		syncRepaymentMsgLogVO.setProductId(syncRepaymentMsgLog.getProductId());
		syncRepaymentMsgLogVO.setProductType(syncRepaymentMsgLog.getProductType());
		syncRepaymentMsgLogVO.setRepayAmount(syncRepaymentMsgLog.getRepayAmount());
		syncRepaymentMsgLogVO.setRepaymentId(syncRepaymentMsgLog.getRepaymentId());
		syncRepaymentMsgLogVO.setReturnDate(syncRepaymentMsgLog.getReturnDate());
		syncRepaymentMsgLogVO.setSendDate(syncRepaymentMsgLog.getSendDate());
		syncRepaymentMsgLogVO.setSendtimes(syncRepaymentMsgLog.getSendtimes());
		syncRepaymentMsgLogVO.setStatus(syncRepaymentMsgLog.getStatus());
		syncRepaymentMsgLogVO.setTempletId(syncRepaymentMsgLog.getTempletId());
		syncRepaymentMsgLogVO.setVersion(syncRepaymentMsgLog.getVersion());
		syncRepaymentMsgLogVO.setRepayDay(syncRepaymentMsgLog.getRepayDay());
		return syncRepaymentMsgLogVO;
	}
	
	public static SyncRepaymentMsgLog transferVO2Model (SyncRepaymentMsgLogVO syncRepaymentMsgLogVO) {
		if (syncRepaymentMsgLogVO == null) {
			return null;
		}
		SyncRepaymentMsgLog syncRepaymentMsgLog=new SyncRepaymentMsgLog();
		syncRepaymentMsgLog.setBuildDate(syncRepaymentMsgLogVO.getBuildDate());
		syncRepaymentMsgLog.setCurTime(syncRepaymentMsgLogVO.getCurTime());
		syncRepaymentMsgLog.setId(syncRepaymentMsgLogVO.getId());
		syncRepaymentMsgLog.setIdNum(syncRepaymentMsgLogVO.getIdNum());
		syncRepaymentMsgLog.setLoanId(syncRepaymentMsgLogVO.getLoanId());
		syncRepaymentMsgLog.setMobile(syncRepaymentMsgLogVO.getMobile());
		syncRepaymentMsgLog.setMsg(syncRepaymentMsgLogVO.getMsg());
		syncRepaymentMsgLog.setName(syncRepaymentMsgLogVO.getName());
		syncRepaymentMsgLog.setProductId(syncRepaymentMsgLogVO.getProductId());
		syncRepaymentMsgLog.setProductType(syncRepaymentMsgLogVO.getProductType());
		syncRepaymentMsgLog.setRepayAmount(syncRepaymentMsgLogVO.getRepayAmount());
		syncRepaymentMsgLog.setRepaymentId(syncRepaymentMsgLogVO.getRepaymentId());
		syncRepaymentMsgLog.setReturnDate(syncRepaymentMsgLogVO.getReturnDate());
		syncRepaymentMsgLog.setSendDate(syncRepaymentMsgLogVO.getSendDate());
		syncRepaymentMsgLog.setSendtimes(syncRepaymentMsgLogVO.getSendtimes());
		syncRepaymentMsgLog.setStatus(syncRepaymentMsgLogVO.getStatus());
		syncRepaymentMsgLog.setTempletId(syncRepaymentMsgLogVO.getTempletId());
		syncRepaymentMsgLog.setVersion(syncRepaymentMsgLogVO.getVersion());
		syncRepaymentMsgLog.setRepayDay(syncRepaymentMsgLogVO.getRepayDay());
		return syncRepaymentMsgLog;
	}
	
	
	public static SyncRepaymentMsgLog transferSRM2SRML (SyncRepaymentMsg syncRepaymentMsg) {
		SyncRepaymentMsgLog syncRepaymentMsgLog=new SyncRepaymentMsgLog();
		syncRepaymentMsgLog.setBuildDate(syncRepaymentMsg.getBuildDate());
		syncRepaymentMsgLog.setCurTime(syncRepaymentMsg.getCurTime());
		syncRepaymentMsgLog.setId(syncRepaymentMsg.getId());
		syncRepaymentMsgLog.setIdNum(syncRepaymentMsg.getIdNum());
		syncRepaymentMsgLog.setLoanId(syncRepaymentMsg.getLoanId());
		syncRepaymentMsgLog.setMobile(syncRepaymentMsg.getMobile());
		syncRepaymentMsgLog.setMsg(syncRepaymentMsg.getMsg());
		syncRepaymentMsgLog.setName(syncRepaymentMsg.getName());
		syncRepaymentMsgLog.setProductId(syncRepaymentMsg.getProductId());
		syncRepaymentMsgLog.setProductType(syncRepaymentMsg.getProductType());
		syncRepaymentMsgLog.setRepayAmount(syncRepaymentMsg.getRepayAmount());
		syncRepaymentMsgLog.setRepaymentId(syncRepaymentMsg.getRepaymentId());
		syncRepaymentMsgLog.setReturnDate(syncRepaymentMsg.getReturnDate());
		syncRepaymentMsgLog.setSendDate(syncRepaymentMsg.getSendDate());
		syncRepaymentMsgLog.setSendtimes(syncRepaymentMsg.getSendtimes());
		syncRepaymentMsgLog.setStatus(syncRepaymentMsg.getStatus());
		syncRepaymentMsgLog.setTempletId(syncRepaymentMsg.getTempletId());
		syncRepaymentMsgLog.setVersion(syncRepaymentMsg.getVersion());
		syncRepaymentMsgLog.setRepayDay(syncRepaymentMsg.getRepayDay());
		return syncRepaymentMsgLog;
		
		
		
	}
}
