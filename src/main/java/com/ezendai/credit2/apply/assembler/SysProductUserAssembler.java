package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.SysProductUser;
import com.ezendai.credit2.apply.vo.SysProductUserVO;

/**
 * 
 * <pre>
 * 用户产品 VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysProductUserAssembler.java, v 0.1 2014年7月31日 下午1:33:34 zhangshihai Exp $
 */
public class SysProductUserAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysProductUser
	 * @return
	 */
	public static SysProductUserVO transferModel2VO (SysProductUser sysProductUser) {
		if (sysProductUser == null) {
			return null;
		}
		
		SysProductUserVO sysProductUserVO = new SysProductUserVO();
		sysProductUserVO.setId(sysProductUser.getId());
		sysProductUserVO.setProductId(sysProductUser.getProductId());
		sysProductUserVO.setUserId(sysProductUser.getUserId());
		sysProductUserVO.setCreator(sysProductUser.getCreator());
		sysProductUserVO.setCreatorId(sysProductUser.getCreatorId());
		sysProductUserVO.setCreatedTime(sysProductUser.getCreatedTime());
		sysProductUserVO.setModifier(sysProductUser.getModifier());
		sysProductUserVO.setModifierId(sysProductUser.getModifierId());
		sysProductUserVO.setModifiedTime(sysProductUser.getModifiedTime());
		sysProductUserVO.setVersion(sysProductUser.getVersion());
		
		return sysProductUserVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param sysProductUserVO
	 * @return
	 */
	public static SysProductUser transferVO2Model (SysProductUserVO sysProductUserVO) {
		if (sysProductUserVO == null) {
			return null;
		}
		
		SysProductUser sysProductUser = new SysProductUser();
		sysProductUser.setId(sysProductUserVO.getId());
		sysProductUser.setProductId(sysProductUserVO.getProductId());
		sysProductUser.setUserId(sysProductUserVO.getUserId());
		sysProductUser.setCreator(sysProductUserVO.getCreator());
		sysProductUser.setCreatorId(sysProductUserVO.getCreatorId());
		sysProductUser.setCreatedTime(sysProductUserVO.getCreatedTime());
		sysProductUser.setModifier(sysProductUserVO.getModifier());
		sysProductUser.setModifierId(sysProductUserVO.getModifierId());
		sysProductUser.setModifiedTime(sysProductUserVO.getModifiedTime());
		sysProductUser.setVersion(sysProductUserVO.getVersion());
		
		return sysProductUser;
	}
}
