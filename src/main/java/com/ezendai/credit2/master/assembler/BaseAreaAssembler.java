package com.ezendai.credit2.master.assembler;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * 
 * <pre>
 * 基本区域表
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BaseAreaAssembler.java, v 0.1 2014年8月4日 下午1:24:54 zhangshihai Exp $
 */
public class BaseAreaAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param baseArea
	 * @return
	 */
	public static BaseAreaVO transferModel2VO (BaseArea baseArea) {
		if (baseArea == null) {
			return null;
		}
		
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		baseAreaVO.setId(baseArea.getId());
		baseAreaVO.setCode(baseArea.getCode());
		baseAreaVO.setFullName(baseArea.getFullName());
		baseAreaVO.setName(baseArea.getName());
		baseAreaVO.setIdentifier(baseArea.getIdentifier());
		baseAreaVO.setCityId(baseArea.getCityId());
		baseAreaVO.setAreaId(baseArea.getAreaId());
		baseAreaVO.setServiceTel(baseArea.getServiceTel());
		baseAreaVO.setSalesDeptId(baseArea.getSalesDeptId());
		baseAreaVO.setCompanyId(baseArea.getCompanyId());
		baseAreaVO.setWorkPlaceInfoId(baseArea.getWorkPlaceInfoId());
		baseAreaVO.setDeptNo(baseArea.getDeptNo());
		baseAreaVO.setRemark(baseArea.getRemark());
		baseAreaVO.setDeleted(baseArea.getDeleted());
		baseAreaVO.setDeptType(baseArea.getDeptType());
		baseAreaVO.setCreator(baseArea.getCreator());
		baseAreaVO.setCreatorId(baseArea.getCreatorId());
		baseAreaVO.setCreatedTime(baseArea.getCreatedTime());
		baseAreaVO.setModifier(baseArea.getModifier());
		baseAreaVO.setModifierId(baseArea.getModifierId());
		baseAreaVO.setModifiedTime(baseArea.getModifiedTime());
		baseAreaVO.setVersion(baseArea.getVersion());
		
		return baseAreaVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param baseAreaVO
	 * @return
	 */
	public static BaseArea transferVO2Model (BaseAreaVO baseAreaVO) {
		if (baseAreaVO == null) {
			return null;
		}
		
		BaseArea baseArea = new BaseArea();
		baseArea.setId(baseAreaVO.getId());
		baseArea.setCode(baseAreaVO.getCode());
		baseArea.setFullName(baseAreaVO.getFullName());
		baseArea.setName(baseAreaVO.getName());
		baseArea.setIdentifier(baseAreaVO.getIdentifier());
		baseArea.setCityId(baseAreaVO.getCityId());
		baseArea.setAreaId(baseAreaVO.getAreaId());
		baseArea.setServiceTel(baseAreaVO.getServiceTel());
		baseArea.setSalesDeptId(baseAreaVO.getSalesDeptId());
		baseArea.setCompanyId(baseAreaVO.getCompanyId());
		baseArea.setWorkPlaceInfoId(baseAreaVO.getWorkPlaceInfoId());
		baseArea.setDeptNo(baseAreaVO.getDeptNo());
		baseArea.setRemark(baseAreaVO.getRemark());
		baseArea.setDeleted(baseAreaVO.getDeleted());
		baseArea.setDeptType(baseAreaVO.getDeptType());
		baseArea.setCreator(baseAreaVO.getCreator());
		baseArea.setCreatorId(baseAreaVO.getCreatorId());
		baseArea.setCreatedTime(baseAreaVO.getCreatedTime());
		baseArea.setModifier(baseAreaVO.getModifier());
		baseArea.setModifierId(baseAreaVO.getModifierId());
		baseArea.setModifiedTime(baseAreaVO.getModifiedTime());
		baseArea.setVersion(baseAreaVO.getVersion());
		
		return baseArea;
	}
}
