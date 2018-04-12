package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.vo.ProductDetailVO;

/**
 * 
 * <pre>
 * 产品详细信息 VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ProductDetailAssembler.java, v 0.1 2014年7月31日 下午12:45:51 zhangshihai Exp $
 */
public class ProductDetailAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param productDetail
	 * @return
	 */
	public static ProductDetailVO transferModel2VO (ProductDetail productDetail) {
		if (productDetail == null) {
			return null;
		}
		
		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setId(productDetail.getId());
		productDetailVO.setProductId(productDetail.getProductId());
		productDetailVO.setCarProductType(productDetail.getCarProductType());
		productDetailVO.setSumRate(productDetail.getSumRate());
		productDetailVO.setYearRate(productDetail.getYearRate());
		productDetailVO.setTerm(productDetail.getTerm());
		productDetailVO.setLowerLimit(productDetail.getLowerLimit());
		productDetailVO.setUpperLimit(productDetail.getUpperLimit());
		productDetailVO.setStatus(productDetail.getStatus());
		productDetailVO.setRemark(productDetail.getRemark());
		productDetailVO.setCreator(productDetail.getCreator());
		productDetailVO.setCreatorId(productDetail.getCreatorId());
		productDetailVO.setCreatedTime(productDetail.getCreatedTime());
		productDetailVO.setModifier(productDetail.getModifier());
		productDetailVO.setModifierId(productDetail.getModifierId());
		productDetailVO.setModifiedTime(productDetail.getModifiedTime());
		productDetailVO.setVersion(productDetail.getVersion());
		productDetailVO.setMemberType(productDetail.getMemberType());
		
		return productDetailVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param productDetailVO
	 * @return
	 */
	public static ProductDetail transferVO2Model (ProductDetailVO productDetailVO) {
		if (productDetailVO == null) {
			return null;
		}
		
		ProductDetail productDetail = new ProductDetail();
		productDetail.setId(productDetailVO.getId());
		productDetail.setProductId(productDetailVO.getProductId());
		productDetail.setCarProductType(productDetailVO.getCarProductType());
		productDetail.setSumRate(productDetailVO.getSumRate());
		productDetail.setYearRate(productDetailVO.getYearRate());
		productDetail.setTerm(productDetailVO.getTerm());
		productDetail.setLowerLimit(productDetailVO.getLowerLimit());
		productDetail.setUpperLimit(productDetailVO.getUpperLimit());
		productDetail.setStatus(productDetailVO.getStatus());
		productDetail.setRemark(productDetailVO.getRemark());
		productDetail.setCreator(productDetailVO.getCreator());
		productDetail.setCreatorId(productDetailVO.getCreatorId());
		productDetail.setCreatedTime(productDetailVO.getCreatedTime());
		productDetail.setModifier(productDetailVO.getModifier());
		productDetail.setModifierId(productDetailVO.getModifierId());
		productDetail.setModifiedTime(productDetailVO.getModifiedTime());
		productDetail.setVersion(productDetailVO.getVersion());
		productDetail.setMemberType(productDetailVO.getMemberType());
		
		return productDetail;
	}
}
