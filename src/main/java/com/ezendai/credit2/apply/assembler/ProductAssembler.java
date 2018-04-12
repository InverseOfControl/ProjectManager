package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.vo.ProductVO;

/**
 * 
 * <pre>
 * 产品	VO/Model 类型转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ProductAssembler.java, v 0.1 2014年7月31日 上午10:55:35 zhangshihai Exp $
 */
public class ProductAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param product
	 * @return
	 */
	public static ProductVO transferModel2VO (Product product) {
		if (product == null) {
			return null;
		}
		
		ProductVO productVO = new ProductVO();
		productVO.setId(product.getId());
		productVO.setProductCode(product.getProductCode());
		productVO.setProductName(product.getProductName());
		productVO.setProductType(product.getProductType());
		productVO.setProductTypeName(product.getProductTypeName());
		productVO.setConsultingFeeRate(product.getConsultingFeeRate());
		productVO.setManagePartRate(product.getManagePartRate());
		productVO.setManageFeeRate(product.getManageFeeRate());
		productVO.setOverdueInterestRate(product.getOverdueInterestRate());
		productVO.setRiskRate(product.getRiskRate());
		productVO.setAssessmentFeeRate(product.getAssessmentFeeRate());
		productVO.setRate(product.getRate());
		productVO.setStatus(product.getStatus());
		productVO.setRemark(product.getRemark());
		productVO.setCreator(product.getCreator());
		productVO.setCreatorId(product.getCreatorId());
		productVO.setCreatedTime(product.getCreatedTime());
		productVO.setModifier(product.getModifier());
		productVO.setModifierId(product.getModifierId());
		productVO.setModifiedTime(product.getModifiedTime());
		productVO.setVersion(product.getVersion());
		productVO.setThirdFeeRate(product.getThirdFeeRate());
		productVO.setMonthRate(product.getMonthRate());
		productVO.setPenaltyRate(product.getPenaltyRate());
		productVO.setYearRate(product.getYearRate());
		return productVO;
	}

	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param productVO
	 * @return
	 */
	public static Product transferVO2Model (ProductVO productVO) {
		if (productVO == null) {
			return null;
		}
		
		Product product = new Product();
		product.setId(productVO.getId());
		product.setProductCode(productVO.getProductCode());
		product.setProductName(productVO.getProductName());
		product.setProductType(productVO.getProductType());
		product.setProductTypeName(productVO.getProductTypeName());
		product.setConsultingFeeRate(productVO.getConsultingFeeRate());
		product.setManagePartRate(productVO.getManagePartRate());
		product.setManageFeeRate(productVO.getManageFeeRate());
		product.setOverdueInterestRate(productVO.getOverdueInterestRate());
		product.setRiskRate(productVO.getRiskRate());
		product.setAssessmentFeeRate(productVO.getAssessmentFeeRate());
		product.setRate(productVO.getRate());
		product.setStatus(productVO.getStatus());
		product.setRemark(productVO.getRemark());
		product.setCreator(productVO.getCreator());
		product.setCreatorId(productVO.getCreatorId());
		product.setCreatedTime(productVO.getCreatedTime());
		product.setModifier(productVO.getModifier());
		product.setModifierId(productVO.getModifierId());
		product.setModifiedTime(productVO.getModifiedTime());
		product.setVersion(productVO.getVersion());
		product.setThirdFeeRate(productVO.getThirdFeeRate());
		product.setMonthRate(productVO.getMonthRate());
		product.setPenaltyRate(productVO.getPenaltyRate());
		product.setYearRate(productVO.getYearRate());
		return product;
	}
}
