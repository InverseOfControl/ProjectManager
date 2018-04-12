/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.BaseAreaProduct;
import com.ezendai.credit2.system.vo.ProductManagementVO;

/**
 * <pre>
 * 
 * </pre>
 *
 */
public class ProductManagementAssembler {

	/**百份比因子**/
	private static final BigDecimal percent = new BigDecimal("100");

	/**期数**/
	private static final Integer TERM3 = new Integer("3");
	private static final Integer TERM6 = new Integer("6");
	private static final Integer TERM9 = new Integer("9");
	private static final Integer TERM12 = new Integer("12");
	private static final Integer TERM18 = new Integer("18");
	private static final Integer TERM24 = new Integer("24");

	public static void transferProduct2VO(Product product,ProductManagementVO vo) {
		if (product == null) {
			return;
		}
		Product newProduct = new Product();
		newProduct.setId(product.getId());
		newProduct.setProductCode(product.getProductCode());
		newProduct.setAssessmentFeeRate(product.getAssessmentFeeRate().multiply(percent));
		newProduct.setConsultingFeeRate(product.getConsultingFeeRate().multiply(percent));
		newProduct.setManageFeeRate(product.getManageFeeRate().multiply(percent));
		newProduct.setManagePartRate(product.getManagePartRate().multiply(percent));
		newProduct.setOverdueInterestRate(product.getOverdueInterestRate().multiply(percent));
		if (vo.getProductName() != null) {
			newProduct.setProductName(product.getProductName().trim());
		}
		newProduct.setRate(product.getRate().multiply(percent));
		newProduct.setProductType(product.getProductType());
		if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(product.getProductType())) {
			newProduct.setProductTypeName("车贷");
		} else if (EnumConstants.ProductType.SE_LOAN.getValue().equals(product.getProductType())) {
			newProduct.setProductTypeName("小企业贷");
		}
		newProduct.setRiskRate(product.getRiskRate().multiply(percent));
		newProduct.setStatus(product.getStatus());
		newProduct.setProductName(product.getProductName());
		vo.setProduct(newProduct);
	}
	public static void transferProductDetailList2VO(List<ProductDetail> productDetailList,
													ProductManagementVO vo,ProductService productService) {
		if (CollectionUtil.isNullOrEmpty(productDetailList)) {
			return;
		}
		for (int i = 0; i < productDetailList.size(); i++) {
			ProductDetail productDetail = productDetailList.get(i);
			Product product = productService.get(productDetail.getProductId());
			//车贷
			if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(product.getProductType())) {
				if (TERM3.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate3Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate3Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm3(TERM3);
					vo.setLowerLimit3(productDetail.getLowerLimit());
					vo.setUpperLimit3(productDetail.getUpperLimit());
				} else if (TERM6.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate6Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate6Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm6(TERM6);
					vo.setLowerLimit6(productDetail.getLowerLimit());
					vo.setUpperLimit6(productDetail.getUpperLimit());
				} else if (TERM9.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate9Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate9Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm9(TERM9);
					vo.setLowerLimit9(productDetail.getLowerLimit());
					vo.setUpperLimit9(productDetail.getUpperLimit());
				} else if (TERM12.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate12Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate12Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm12(TERM12);
					vo.setLowerLimit12(productDetail.getLowerLimit());
					vo.setUpperLimit12(productDetail.getUpperLimit());
				} else if (TERM18.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate18Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate18Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm18(TERM18);
					vo.setLowerLimit18(productDetail.getLowerLimit());
					vo.setUpperLimit18(productDetail.getUpperLimit());
				} else if (TERM24.equals(productDetail.getTerm())) {
					if (EnumConstants.ProductCarType.TURN_OVER.getValue().equals(
						productDetail.getCarProductType())) {
						vo.setSumRate24Transfer(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					} else {
						vo.setSumRate24Flow(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					}
					vo.setTerm24(TERM24);
					vo.setLowerLimit24(productDetail.getLowerLimit());
					vo.setUpperLimit24(productDetail.getUpperLimit());
				}
			} else {//小企业贷
				if (TERM3.equals(productDetail.getTerm())) {
					vo.setTerm3(TERM3);
					vo.setSumRate3(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit3(productDetail.getLowerLimit());
					vo.setUpperLimit3(productDetail.getUpperLimit());
				} else if (TERM6.equals(productDetail.getTerm())) {
					vo.setTerm6(TERM6);
					vo.setSumRate6(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit6(productDetail.getLowerLimit());
					vo.setUpperLimit6(productDetail.getUpperLimit());
				} else if (TERM9.equals(productDetail.getTerm())) {
					vo.setTerm9(TERM9);
					vo.setSumRate9(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit9(productDetail.getLowerLimit());
					vo.setUpperLimit9(productDetail.getUpperLimit());
				} else if (TERM12.equals(productDetail.getTerm())) {
					vo.setTerm12(TERM12);
					vo.setSumRate12(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit12(productDetail.getLowerLimit());
					vo.setUpperLimit12(productDetail.getUpperLimit());
				} else if (TERM18.equals(productDetail.getTerm())) {
					vo.setTerm18(TERM18);
					vo.setSumRate18(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit18(productDetail.getLowerLimit());
					vo.setUpperLimit18(productDetail.getUpperLimit());
				} else if (TERM24.equals(productDetail.getTerm())) {
					vo.setTerm24(TERM24);
					vo.setSumRate24(getZeroBigDecimal(productDetail.getSumRate()).multiply(percent));
					vo.setLowerLimit24(productDetail.getLowerLimit());
					vo.setUpperLimit24(productDetail.getUpperLimit());
				}
			}
		}
	}

	
	public static Product transferVO2ProductModel(ProductManagementVO vo) {
		if (vo == null) {
			return null;
		}
		Product product = new Product();
		product.setId(vo.getId());
		product.setProductCode(vo.getProductCode());
		product.setAssessmentFeeRate(vo.getAssessmentFeeRate().divide(percent));
		product.setConsultingFeeRate(vo.getConsultingFeeRate().divide(percent));
		product.setManageFeeRate(vo.getManageFeeRate().divide(percent));
		product.setManagePartRate(vo.getManagePartRate().divide(percent));
		product.setOverdueInterestRate(vo.getOverdueInterestRate().divide(percent));
		if (vo.getProductName() != null) {
			product.setProductName(vo.getProductName().trim());
		}
		product.setRate(vo.getRate().divide(percent));
		product.setProductType(vo.getProductType());
		if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(vo.getProductType())) {
			product.setProductTypeName("车贷");
		} else if (EnumConstants.ProductType.SE_LOAN.getValue().equals(vo.getProductType())) {
			product.setProductTypeName("小企业贷");
		}
		product.setRiskRate(vo.getRiskRate().divide(percent));
		product.setStatus(vo.getStatus());
		product.setProductName(vo.getProductName());
		return product;
	}

	public static List<ProductDetail> transferVO2ProductDetailList(ProductManagementVO vo) {
		List<ProductDetail> list = new ArrayList<ProductDetail>();
		if (vo == null) { 
			return list;
		}
		if (vo.getTermList() != null) {
			String[] termsIdAarry = vo.getTermList().split("[|]");
			//车贷
			if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(vo.getProductType())) {
				for (String termId : termsIdAarry) {
					//for 流通类综合费率
					ProductDetail detail = new ProductDetail();
					if ("3".equals(termId)) {
						detail.setSumRate(vo.getSumRate3Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit3());
						detail.setUpperLimit(vo.getUpperLimit3());
					} else if ("6".equals(termId)) {
						detail.setSumRate(vo.getSumRate6Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit6());
						detail.setUpperLimit(vo.getUpperLimit6());
					} else if ("9".equals(termId)) {
						detail.setSumRate(vo.getSumRate9Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit9());
						detail.setUpperLimit(vo.getUpperLimit9());
					} else if ("12".equals(termId)) {
						detail.setSumRate(vo.getSumRate12Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit12());
						detail.setUpperLimit(vo.getUpperLimit12());
					} else if ("18".equals(termId)) {
						detail.setSumRate(vo.getSumRate18Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit18());
						detail.setUpperLimit(vo.getUpperLimit18());
					} else if ("24".equals(termId)) {
						detail.setSumRate(vo.getSumRate24Flow().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit24());
						detail.setUpperLimit(vo.getUpperLimit24());
					}
					detail.setTerm(Integer.valueOf(termId));
					detail.setCarProductType(EnumConstants.ProductCarType.CIRCULATE.getValue());
					detail.setStatus(EnumConstants.ProductStatus.VALID.getValue());
					list.add(detail);
					//for 移交类综合费率
					ProductDetail detail2 = new ProductDetail();
					if ("3".equals(termId)) {
						detail2.setSumRate(vo.getSumRate3Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit3());
						detail2.setUpperLimit(vo.getUpperLimit3());
					} else if ("6".equals(termId)) {
						detail2.setSumRate(vo.getSumRate6Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit6());
						detail2.setUpperLimit(vo.getUpperLimit6());
					} else if ("9".equals(termId)) {
						detail2.setSumRate(vo.getSumRate9Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit9());
						detail2.setUpperLimit(vo.getUpperLimit9());
					} else if ("12".equals(termId)) {
						detail2.setSumRate(vo.getSumRate12Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit12());
						detail2.setUpperLimit(vo.getUpperLimit12());
					} else if ("18".equals(termId)) {
						detail2.setSumRate(vo.getSumRate18Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit18());
						detail2.setUpperLimit(vo.getUpperLimit18());
					} else if ("24".equals(termId)) {
						detail2.setSumRate(vo.getSumRate24Transfer().divide(percent));
						detail2.setLowerLimit(vo.getLowerLimit24());
						detail2.setUpperLimit(vo.getUpperLimit24());
					}
					detail2.setTerm(Integer.valueOf(termId));
					detail2.setCarProductType(EnumConstants.ProductCarType.TURN_OVER.getValue());
					detail2.setStatus(EnumConstants.ProductStatus.VALID.getValue());
					list.add(detail2);
				}
			} else if (EnumConstants.ProductType.SE_LOAN.getValue().equals(vo.getProductType())) {
				//小企业贷
				for (String termId : termsIdAarry) {
					//for 综合费率
					ProductDetail detail = new ProductDetail();
					if ("3".equals(termId)) {
						detail.setSumRate(vo.getSumRate3().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit3());
						detail.setUpperLimit(vo.getUpperLimit3());
					} else if ("6".equals(termId)) {
						detail.setSumRate(vo.getSumRate6().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit6());
						detail.setUpperLimit(vo.getUpperLimit6());
					} else if ("9".equals(termId)) {
						detail.setSumRate(vo.getSumRate9().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit9());
						detail.setUpperLimit(vo.getUpperLimit9());
					} else if ("12".equals(termId)) {
						detail.setSumRate(vo.getSumRate12().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit12());
						detail.setUpperLimit(vo.getUpperLimit12());
					} else if ("18".equals(termId)) {
						detail.setSumRate(vo.getSumRate18().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit18());
						detail.setUpperLimit(vo.getUpperLimit18());
					} else if ("24".equals(termId)) {
						detail.setSumRate(vo.getSumRate24().divide(percent));
						detail.setLowerLimit(vo.getLowerLimit24());
						detail.setUpperLimit(vo.getUpperLimit24());
					}
					detail.setTerm(Integer.valueOf(termId));
					detail.setStatus(EnumConstants.ProductStatus.VALID.getValue());
					list.add(detail);
				}
			}
		}
		return list;
	}

	public static List<BaseAreaProduct> transferVO2BaseAreaProductList(ProductManagementVO vo) {
		List<BaseAreaProduct> list = new ArrayList<BaseAreaProduct>();
		if (vo == null) {
			return list;
		}
		if (vo.getCityIdList() != null) {
			String[] citysIdAarry = vo.getCityIdList().split("[|]");
			for (String cityId : citysIdAarry) {
				BaseAreaProduct baseAreaProduct = new BaseAreaProduct();
				baseAreaProduct.setAreaId(Long.valueOf(cityId));
				list.add(baseAreaProduct);
			}
		}
		return list;
	}
	
	private  static BigDecimal getZeroBigDecimal(BigDecimal v){
		if(v == null){
			return BigDecimal.ZERO;
		}else{
			return v;
		}
	}
}
