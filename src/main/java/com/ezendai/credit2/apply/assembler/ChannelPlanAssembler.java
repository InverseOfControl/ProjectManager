package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;

public class ChannelPlanAssembler {

	
	/**
	 * <pre>
	 * Model对象转换成Vo对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static ChannelPlanVO transferModel2VO(ChannelPlan channelPlan) {
		if (channelPlan == null) {
			return null;
		}
		ChannelPlanVO channelPlanVO=new ChannelPlanVO();
		channelPlanVO.setActualRate(channelPlan.getActualRate());
		channelPlanVO.setApproverId(channelPlan.getApproverId());
		channelPlanVO.setCode(channelPlan.getCode());
		channelPlanVO.setCreatedTime(channelPlan.getCreatedTime());
		channelPlanVO.setCreator(channelPlan.getCreator());
		channelPlanVO.setCreatorId(channelPlan.getCreatorId());
		channelPlanVO.setEndDate(channelPlan.getEndDate());
		channelPlanVO.setId(channelPlan.getId());
		channelPlanVO.setMargin(channelPlan.getMargin());
		channelPlanVO.setMemo(channelPlan.getMemo());
		channelPlanVO.setModifiedTime(channelPlan.getModifiedTime());
		channelPlanVO.setModifier(channelPlan.getModifier());
		channelPlanVO.setModifierId(channelPlan.getModifierId());
		channelPlanVO.setName(channelPlan.getName());
		channelPlanVO.setOperatorId(channelPlan.getOperatorId());
		channelPlanVO.setOrganizationId(channelPlan.getOrganizationId());
		channelPlanVO.setOrgFeeState(channelPlan.getOrgFeeState());
		channelPlanVO.setOrgRepayTerm(channelPlan.getOrgRepayTerm());
		channelPlanVO.setPactMoney(channelPlan.getPactMoney());
		channelPlanVO.setPlanState(channelPlan.getPlanState());
		channelPlanVO.setPlanType(channelPlan.getPlanType());
		channelPlanVO.setRateSum(channelPlan.getRateSum());
		channelPlanVO.setRequestMoney(channelPlan.getRequestMoney());
		channelPlanVO.setReturneterm1(channelPlan.getReturneterm1());
		channelPlanVO.setReturneterm2(channelPlan.getReturneterm2());
		channelPlanVO.setSendBackMemo(channelPlan.getSendBackMemo());
		channelPlanVO.setStartDate(channelPlan.getStartDate());
		channelPlanVO.setTime(channelPlan.getTime());
		channelPlanVO.setToTerm1(channelPlan.getToTerm1());
		channelPlanVO.setToTerm2(channelPlan.getToTerm2());
		channelPlanVO.setVersion(channelPlan.getVersion());
		channelPlanVO.setIsDeleted(channelPlan.getIsDeleted());
		channelPlanVO.setReturnType(channelPlan.getReturnType());
		return channelPlanVO;
		
		
		
		
	}
	
	
	/**
	 * <pre>
	 * Model对象转换成Vo对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static ChannelPlan transferVO2Model(ChannelPlanVO channelPlanVO) {
		if (channelPlanVO == null) {
			return null;
		}
		ChannelPlan channelPlan=new ChannelPlan();
		channelPlan.setActualRate(channelPlanVO.getActualRate());
		channelPlan.setApproverId(channelPlanVO.getApproverId());
		channelPlan.setCode(channelPlanVO.getCode());
		channelPlan.setCreatedTime(channelPlanVO.getCreatedTime());
		channelPlan.setCreator(channelPlanVO.getCreator());
		channelPlan.setCreatorId(channelPlanVO.getCreatorId());
		channelPlan.setEndDate(channelPlanVO.getEndDate());
		channelPlan.setId(channelPlanVO.getId());
		channelPlan.setMargin(channelPlanVO.getMargin());
		channelPlan.setMemo(channelPlanVO.getMemo());
		channelPlan.setModifiedTime(channelPlanVO.getModifiedTime());
		channelPlan.setModifier(channelPlanVO.getModifier());
		channelPlan.setModifierId(channelPlanVO.getModifierId());
		channelPlan.setName(channelPlanVO.getName());
		channelPlan.setOperatorId(channelPlanVO.getOperatorId());
		channelPlan.setOrganizationId(channelPlanVO.getOrganizationId());
		channelPlan.setOrgFeeState(channelPlanVO.getOrgFeeState());
		channelPlan.setOrgRepayTerm(channelPlanVO.getOrgRepayTerm());
		channelPlan.setPactMoney(channelPlanVO.getPactMoney());
		channelPlan.setPlanState(channelPlanVO.getPlanState());
		channelPlan.setPlanType(channelPlanVO.getPlanType());
		channelPlan.setRateSum(channelPlanVO.getRateSum());
		channelPlan.setRequestMoney(channelPlanVO.getRequestMoney());
		channelPlan.setReturneterm1(channelPlanVO.getReturneterm1());
		channelPlan.setReturneterm2(channelPlanVO.getReturneterm2());
		channelPlan.setSendBackMemo(channelPlanVO.getSendBackMemo());
		channelPlan.setStartDate(channelPlanVO.getStartDate());
		channelPlan.setTime(channelPlanVO.getTime());
		channelPlan.setToTerm1(channelPlanVO.getToTerm1());
		channelPlan.setToTerm2(channelPlanVO.getToTerm2());
		channelPlan.setVersion(channelPlanVO.getVersion());
		channelPlan.setIsDeleted(channelPlanVO.getIsDeleted());
		channelPlan.setReturnType(channelPlanVO.getReturnType());
		return channelPlan;
		
		
		
		
	}


	public static ChannelPlanCheckVO transferVO2CheckVO(
			ChannelPlanVO channelPlanVO) {
		if (channelPlanVO == null) {
			return null;
		}
		ChannelPlanCheckVO channelPlanCheckVO=new ChannelPlanCheckVO();
		channelPlanCheckVO.setActualRate(channelPlanVO.getActualRate());
		channelPlanCheckVO.setApproverId(channelPlanVO.getApproverId());
		channelPlanCheckVO.setCode(channelPlanVO.getCode());
		channelPlanCheckVO.setEndDate(channelPlanVO.getEndDate());
		channelPlanCheckVO.setMargin(channelPlanVO.getMargin());
		channelPlanCheckVO.setMemo(channelPlanVO.getMemo());
		channelPlanCheckVO.setName(channelPlanVO.getName());
		channelPlanCheckVO.setOperatorId(channelPlanVO.getOperatorId());
		channelPlanCheckVO.setOrganizationId(channelPlanVO.getOrganizationId());
		channelPlanCheckVO.setOrgRepayTerm(channelPlanVO.getOrgRepayTerm());
		channelPlanCheckVO.setOrgFeeState(channelPlanVO.getOrgFeeState());
		channelPlanCheckVO.setPactMoney(channelPlanVO.getPactMoney());
		channelPlanCheckVO.setPlan_id(channelPlanVO.getId());
		channelPlanCheckVO.setRateSum(channelPlanVO.getRateSum());
		channelPlanCheckVO.setRequestMoney(channelPlanVO.getRequestMoney());
		channelPlanCheckVO.setReturneterm1(channelPlanVO.getReturneterm1());
		channelPlanCheckVO.setReturneterm2(channelPlanVO.getReturneterm2());
		channelPlanCheckVO.setSendBackMemo(channelPlanVO.getSendBackMemo());
		channelPlanCheckVO.setStartDate(channelPlanVO.getStartDate());
		channelPlanCheckVO.setTime(channelPlanVO.getTime());
		channelPlanCheckVO.setToTerm1(channelPlanVO.getToTerm1());
		channelPlanCheckVO.setToTerm2(channelPlanVO.getToTerm2());
		channelPlanCheckVO.setVersion(channelPlanVO.getVersion());
		channelPlanCheckVO.setReturnType(channelPlanVO.getReturnType());
		channelPlanCheckVO.setPager(channelPlanVO.getPager());
		channelPlanCheckVO.setPlanType(channelPlanVO.getPlanType());
		channelPlanCheckVO.setIsDeleted(channelPlanVO.getIsDeleted());
		return channelPlanCheckVO;
	}


	public static ChannelPlanVO transferCheckVO2VO(
			ChannelPlanCheckVO channelPlanCheckVO) {
		// TODO Auto-generated method stub
		ChannelPlanVO channelPlanVO=new ChannelPlanVO();
		channelPlanVO.setActualRate(channelPlanCheckVO.getActualRate());
		channelPlanVO.setApproverId(channelPlanCheckVO.getApproverId());
		channelPlanVO.setCode(channelPlanCheckVO.getCode());
		channelPlanVO.setCreatedTime(channelPlanCheckVO.getCreatedTime());
		channelPlanVO.setCreator(channelPlanCheckVO.getCreator());
		channelPlanVO.setCreatorId(channelPlanCheckVO.getCreatorId());
		channelPlanVO.setEndDate(channelPlanCheckVO.getEndDate());
		channelPlanVO.setId(channelPlanCheckVO.getPlan_id());
		channelPlanVO.setMargin(channelPlanCheckVO.getMargin());
		channelPlanVO.setMemo(channelPlanCheckVO.getMemo());
		channelPlanVO.setModifiedTime(channelPlanCheckVO.getModifiedTime());
		channelPlanVO.setModifier(channelPlanCheckVO.getModifier());
		channelPlanVO.setModifierId(channelPlanCheckVO.getModifierId());
		channelPlanVO.setName(channelPlanCheckVO.getName());
		channelPlanVO.setOperatorId(channelPlanCheckVO.getOperatorId());
		channelPlanVO.setOrganizationId(channelPlanCheckVO.getOrganizationId());
		channelPlanVO.setOrgFeeState(channelPlanCheckVO.getOrgFeeState());
		channelPlanVO.setOrgRepayTerm(channelPlanCheckVO.getOrgRepayTerm());
		channelPlanVO.setPactMoney(channelPlanCheckVO.getPactMoney());
		channelPlanVO.setPlanType(channelPlanCheckVO.getPlanType());
		channelPlanVO.setRateSum(channelPlanCheckVO.getRateSum());
		channelPlanVO.setRequestMoney(channelPlanCheckVO.getRequestMoney());
		channelPlanVO.setReturneterm1(channelPlanCheckVO.getReturneterm1());
		channelPlanVO.setReturneterm2(channelPlanCheckVO.getReturneterm2());
		channelPlanVO.setSendBackMemo(channelPlanCheckVO.getSendBackMemo());
		channelPlanVO.setStartDate(channelPlanCheckVO.getStartDate());
		channelPlanVO.setTime(channelPlanCheckVO.getTime());
		channelPlanVO.setToTerm1(channelPlanCheckVO.getToTerm1());
		channelPlanVO.setToTerm2(channelPlanCheckVO.getToTerm2());
		channelPlanVO.setVersion(channelPlanCheckVO.getVersion());
		channelPlanVO.setIsDeleted(channelPlanCheckVO.getIsDeleted());
		channelPlanVO.setReturnType(channelPlanCheckVO.getReturnType());
		return channelPlanVO;
	}


}
