package com.ezendai.credit2.master.assembler;

import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**
 * 
 * <pre>
 * 工作地点信息  VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: WorkPlaceInfoAssembler.java, v 0.1 2014年8月5日 上午10:43:14 zhangshihai Exp $
 */
public class WorkPlaceInfoAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param workPlaceInfo
	 * @return
	 */
	public static WorkPlaceInfoVO transferModel2VO (WorkPlaceInfo workPlaceInfo) {
		if (workPlaceInfo == null) {
			return null;
		}
		
		WorkPlaceInfoVO workPlaceInfoVO = new WorkPlaceInfoVO();
		workPlaceInfoVO.setId(workPlaceInfo.getId());
		workPlaceInfoVO.setTel(workPlaceInfo.getTel());
		workPlaceInfoVO.setCityNo(workPlaceInfo.getCityNo());
		workPlaceInfoVO.setZoneCode(workPlaceInfo.getZoneCode());
		workPlaceInfoVO.setSite(workPlaceInfo.getSite());
		workPlaceInfoVO.setCreator(workPlaceInfo.getCreator());
		workPlaceInfoVO.setCreatorId(workPlaceInfo.getCreatorId());
		workPlaceInfoVO.setCreatedTime(workPlaceInfo.getCreatedTime());
		workPlaceInfoVO.setModifier(workPlaceInfo.getModifier());
		workPlaceInfoVO.setModifierId(workPlaceInfo.getModifierId());
		workPlaceInfoVO.setModifiedTime(workPlaceInfo.getModifiedTime());
		workPlaceInfoVO.setVersion(workPlaceInfo.getVersion());
		
		return workPlaceInfoVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model类型
	 * </pre>
	 *
	 * @param workPlaceInfoVO
	 * @return
	 */
	public static WorkPlaceInfo transferVO2Model (WorkPlaceInfoVO workPlaceInfoVO) {
		if (workPlaceInfoVO == null) {
			return null;
		}
		
		WorkPlaceInfo workPlaceInfo = new WorkPlaceInfo();
		workPlaceInfo.setId(workPlaceInfoVO.getId());
		workPlaceInfo.setTel(workPlaceInfoVO.getTel());
		workPlaceInfo.setCityNo(workPlaceInfoVO.getCityNo());
		workPlaceInfo.setZoneCode(workPlaceInfoVO.getZoneCode());
		workPlaceInfo.setSite(workPlaceInfoVO.getSite());
		workPlaceInfo.setCreator(workPlaceInfoVO.getCreator());
		workPlaceInfo.setCreatorId(workPlaceInfoVO.getCreatorId());
		workPlaceInfo.setCreatedTime(workPlaceInfoVO.getCreatedTime());
		workPlaceInfo.setModifier(workPlaceInfoVO.getModifier());
		workPlaceInfo.setModifierId(workPlaceInfoVO.getModifierId());
		workPlaceInfo.setModifiedTime(workPlaceInfoVO.getModifiedTime());
		workPlaceInfo.setVersion(workPlaceInfoVO.getVersion());
		
		return workPlaceInfo;
	}
}
