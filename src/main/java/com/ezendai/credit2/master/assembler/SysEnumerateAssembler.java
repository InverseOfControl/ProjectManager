package com.ezendai.credit2.master.assembler;

import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;

/**
 * 
 * <pre>
 * 系统枚举  VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysEnumerateAssembler.java, v 0.1 2014年8月5日 上午10:16:13 zhangshihai Exp $
 */
public class SysEnumerateAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysEnumerate
	 * @return
	 */
	public static SysEnumerateVO transferModel2VO (SysEnumerate sysEnumerate) {
		if (sysEnumerate == null) {
			return null;
		}
		
		SysEnumerateVO sysEnumerateVO = new SysEnumerateVO();
		sysEnumerateVO.setId(sysEnumerate.getId());
		sysEnumerateVO.setEnumType(sysEnumerate.getEnumType());
		sysEnumerateVO.setEnumValue(sysEnumerate.getEnumValue());
		sysEnumerateVO.setEnumCode(sysEnumerate.getEnumCode());
		sysEnumerateVO.setCreator(sysEnumerate.getCreator());
		sysEnumerateVO.setCreatorId(sysEnumerate.getCreatorId());
		sysEnumerateVO.setCreatedTime(sysEnumerate.getCreatedTime());
		sysEnumerateVO.setModifier(sysEnumerate.getModifier());
		sysEnumerateVO.setModifierId(sysEnumerate.getModifierId());
		sysEnumerateVO.setModifiedTime(sysEnumerate.getModifiedTime());
		sysEnumerateVO.setVersion(sysEnumerate.getVersion());
		
		return sysEnumerateVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param sysEnumerateVO
	 * @return
	 */
	public static SysEnumerate transferVO2Model (SysEnumerateVO sysEnumerateVO) {
		
		SysEnumerate sysEnumerate = new SysEnumerate();
		sysEnumerate.setId(sysEnumerateVO.getId());
		sysEnumerate.setEnumType(sysEnumerateVO.getEnumType());
		sysEnumerate.setEnumValue(sysEnumerateVO.getEnumValue());
		sysEnumerate.setEnumCode(sysEnumerateVO.getEnumCode());
		sysEnumerate.setCreator(sysEnumerateVO.getCreator());
		sysEnumerate.setCreatorId(sysEnumerateVO.getCreatorId());
		sysEnumerate.setCreatedTime(sysEnumerateVO.getCreatedTime());
		sysEnumerate.setModifier(sysEnumerateVO.getModifier());
		sysEnumerate.setModifierId(sysEnumerateVO.getModifierId());
		sysEnumerate.setModifiedTime(sysEnumerateVO.getModifiedTime());
		sysEnumerate.setVersion(sysEnumerateVO.getVersion());
		
		return sysEnumerate;
	}
}
