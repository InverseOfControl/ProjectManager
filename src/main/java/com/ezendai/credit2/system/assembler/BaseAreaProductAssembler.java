/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.BaseAreaProduct;
import com.ezendai.credit2.system.vo.BaseAreaProductVO;

/**
 * <pre>
 * VO/Model转换
 * </pre>
 *
 * @author 00226557
 * @version $Id: BaseAreaProductAssembler.java, v 0.1 2015年4月15日 下午9:19:16 00226557 Exp $
 */
public class BaseAreaProductAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	public static BaseAreaProductVO transferModel2VO (BaseAreaProduct baseAreaProduct) {
		if (baseAreaProduct == null) {
			return null;
		}
		BaseAreaProductVO vo = new BaseAreaProductVO();
		vo.setAreaId(baseAreaProduct.getAreaId());
		vo.setProductId(baseAreaProduct.getProductId());
		return vo;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param sysUserVO
	 * @return
	 */
	public static BaseAreaProduct transferVO2Model (BaseAreaProductVO vo) {
		if (vo == null) {
			return null;
		}
		BaseAreaProduct  baseAreaProduct= new BaseAreaProduct();
		baseAreaProduct.setAreaId(vo.getAreaId());
		baseAreaProduct.setProductId(vo.getProductId());
		return baseAreaProduct;
	}
}
