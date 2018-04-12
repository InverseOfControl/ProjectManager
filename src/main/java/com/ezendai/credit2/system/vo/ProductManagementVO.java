package com.ezendai.credit2.system.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.model.BaseAreaProduct;

public class ProductManagementVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	/** 产品编号 */
	private String productCode;

	/** 产品名称 */
	private String productName;
	
	/** 产品类型 */
	private Integer productType;
	
	/** 产品类型名称 */
	private String productTypeName;

	/** 咨询费费率 */
	private BigDecimal consultingFeeRate;

	/** 丙方管理费费率 */
	private BigDecimal managePartRate;

	/** 管理费费率 */
	private BigDecimal manageFeeRate;
	
	/** 逾期罚息费率 */
	private BigDecimal  overdueInterestRate;

	/** 风险金比例 */
	private BigDecimal riskRate;

	/** 评估费费率 */
	private BigDecimal assessmentFeeRate;

	/** 平息利率 */
	private BigDecimal rate;

	/** 状态 */
	private Integer status;
	
	/**期数**/
	private Integer term3;
	private Integer term6;
	private Integer term9;
	private Integer term12;
	private Integer term18;
	private Integer term24;
	
	
	/**小企业 综合费率 */
	private BigDecimal sumRate3;
	private BigDecimal sumRate6;
	private BigDecimal sumRate9;
	private BigDecimal sumRate12;
	private BigDecimal sumRate18;
	private BigDecimal sumRate24;
	/** 流通类综合费率 */
	private BigDecimal sumRate3Flow;
	private BigDecimal sumRate6Flow;
	private BigDecimal sumRate9Flow;
	private BigDecimal sumRate12Flow;
	private BigDecimal sumRate18Flow;
	private BigDecimal sumRate24Flow;
	/** 移交类综合费率 */
	private BigDecimal sumRate3Transfer;
	private BigDecimal sumRate6Transfer;
	private BigDecimal sumRate9Transfer;
	private BigDecimal sumRate12Transfer;
	private BigDecimal sumRate18Transfer;
	private BigDecimal sumRate24Transfer;
	/**借款金额下限 **/
	private BigDecimal lowerLimit3;
	private BigDecimal lowerLimit6;
	private BigDecimal lowerLimit9;
	private BigDecimal lowerLimit12;
	private BigDecimal lowerLimit18;
	private BigDecimal lowerLimit24;
	
	/**借款金额上限 **/
	private BigDecimal upperLimit3;
	private BigDecimal upperLimit6;
	private BigDecimal upperLimit9;
	private BigDecimal upperLimit12;
	private BigDecimal upperLimit18;
	private BigDecimal upperLimit24;
	
	/**期数列表 */
	private String termList ;
	/**城市列表 */
	private String cityIdList ;

	/** 备注 */
	private String remark;
	
	private Long userId;
	
	private Product product;
	
	private List<BaseAreaProduct>  baseAreaProductList;
	

	public BigDecimal getOverdueInterestRate() {
		return overdueInterestRate;
	}

	public void setOverdueInterestRate(BigDecimal overdueInterestRate) {
		this.overdueInterestRate = overdueInterestRate;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductCode() {
		return productCode;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode == null ? null : productCode.trim();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public BigDecimal getConsultingFeeRate() {
		return consultingFeeRate;
	}

	public void setConsultingFeeRate(BigDecimal consultingFeeRate) {
		this.consultingFeeRate = consultingFeeRate;
	}

	public BigDecimal getManagePartRate() {
		return managePartRate;
	}

	public void setManagePartRate(BigDecimal managePartRate) {
		this.managePartRate = managePartRate;
	}

	public BigDecimal getManageFeeRate() {
		return manageFeeRate;
	}

	public void setManageFeeRate(BigDecimal manageFeeRate) {
		this.manageFeeRate = manageFeeRate;
	}

	public BigDecimal getRiskRate() {
		return riskRate;
	}

	public void setRiskRate(BigDecimal riskRate) {
		this.riskRate = riskRate;
	}

	public BigDecimal getAssessmentFeeRate() {
		return assessmentFeeRate;
	}

	public void setAssessmentFeeRate(BigDecimal assessmentFeeRate) {
		this.assessmentFeeRate = assessmentFeeRate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getSumRate3() {
		return sumRate3;
	}

	public void setSumRate3(BigDecimal sumRate3) {
		this.sumRate3 = sumRate3;
	}

	public BigDecimal getSumRate6() {
		return sumRate6;
	}

	public void setSumRate6(BigDecimal sumRate6) {
		this.sumRate6 = sumRate6;
	}

	public BigDecimal getSumRate9() {
		return sumRate9;
	}

	public void setSumRate9(BigDecimal sumRate9) {
		this.sumRate9 = sumRate9;
	}

	public BigDecimal getSumRate12() {
		return sumRate12;
	}

	public void setSumRate12(BigDecimal sumRate12) {
		this.sumRate12 = sumRate12;
	}

	public BigDecimal getSumRate18() {
		return sumRate18;
	}

	public void setSumRate18(BigDecimal sumRate18) {
		this.sumRate18 = sumRate18;
	}

	public BigDecimal getSumRate24() {
		return sumRate24;
	}

	public void setSumRate24(BigDecimal sumRate24) {
		this.sumRate24 = sumRate24;
	}

	public String getTermList() {
		return termList;
	}

	public void setTermList(String termList) {
		this.termList = termList;
	}

	public String getCityIdList() {
		return cityIdList;
	}

	public void setCityIdList(String cityIdList) {
		this.cityIdList = cityIdList;
	}

	public BigDecimal getLowerLimit3() {
		return lowerLimit3;
	}

	public void setLowerLimit3(BigDecimal lowerLimit3) {
		this.lowerLimit3 = lowerLimit3;
	}

	public BigDecimal getLowerLimit6() {
		return lowerLimit6;
	}

	public void setLowerLimit6(BigDecimal lowerLimit6) {
		this.lowerLimit6 = lowerLimit6;
	}

	public BigDecimal getLowerLimit9() {
		return lowerLimit9;
	}

	public void setLowerLimit9(BigDecimal lowerLimit9) {
		this.lowerLimit9 = lowerLimit9;
	}

	public BigDecimal getLowerLimit12() {
		return lowerLimit12;
	}

	public void setLowerLimit12(BigDecimal lowerLimit12) {
		this.lowerLimit12 = lowerLimit12;
	}

	public BigDecimal getLowerLimit18() {
		return lowerLimit18;
	}

	public void setLowerLimit18(BigDecimal lowerLimit18) {
		this.lowerLimit18 = lowerLimit18;
	}

	public BigDecimal getLowerLimit24() {
		return lowerLimit24;
	}

	public void setLowerLimit24(BigDecimal lowerLimit24) {
		this.lowerLimit24 = lowerLimit24;
	}

	public BigDecimal getUpperLimit3() {
		return upperLimit3;
	}

	public void setUpperLimit3(BigDecimal upperLimit3) {
		this.upperLimit3 = upperLimit3;
	}

	public BigDecimal getUpperLimit6() {
		return upperLimit6;
	}

	public void setUpperLimit6(BigDecimal upperLimit6) {
		this.upperLimit6 = upperLimit6;
	}

	public BigDecimal getUpperLimit9() {
		return upperLimit9;
	}

	public void setUpperLimit9(BigDecimal upperLimit9) {
		this.upperLimit9 = upperLimit9;
	}

	public BigDecimal getUpperLimit12() {
		return upperLimit12;
	}

	public void setUpperLimit12(BigDecimal upperLimit12) {
		this.upperLimit12 = upperLimit12;
	}

	public BigDecimal getUpperLimit18() {
		return upperLimit18;
	}

	public void setUpperLimit18(BigDecimal upperLimit18) {
		this.upperLimit18 = upperLimit18;
	}

	public BigDecimal getUpperLimit24() {
		return upperLimit24;
	}

	public void setUpperLimit24(BigDecimal upperLimit24) {
		this.upperLimit24 = upperLimit24;
	}

	public BigDecimal getSumRate3Flow() {
		return sumRate3Flow;
	}

	public void setSumRate3Flow(BigDecimal sumRate3Flow) {
		this.sumRate3Flow = sumRate3Flow;
	}

	public BigDecimal getSumRate6Flow() {
		return sumRate6Flow;
	}

	public void setSumRate6Flow(BigDecimal sumRate6Flow) {
		this.sumRate6Flow = sumRate6Flow;
	}

	public BigDecimal getSumRate9Flow() {
		return sumRate9Flow;
	}

	public void setSumRate9Flow(BigDecimal sumRate9Flow) {
		this.sumRate9Flow = sumRate9Flow;
	}

	public BigDecimal getSumRate12Flow() {
		return sumRate12Flow;
	}

	public void setSumRate12Flow(BigDecimal sumRate12Flow) {
		this.sumRate12Flow = sumRate12Flow;
	}

	public BigDecimal getSumRate18Flow() {
		return sumRate18Flow;
	}

	public void setSumRate18Flow(BigDecimal sumRate18Flow) {
		this.sumRate18Flow = sumRate18Flow;
	}

	public BigDecimal getSumRate24Flow() {
		return sumRate24Flow;
	}

	public void setSumRate24Flow(BigDecimal sumRate24Flow) {
		this.sumRate24Flow = sumRate24Flow;
	}

	public BigDecimal getSumRate3Transfer() {
		return sumRate3Transfer;
	}

	public void setSumRate3Transfer(BigDecimal sumRate3Transfer) {
		this.sumRate3Transfer = sumRate3Transfer;
	}

	public BigDecimal getSumRate6Transfer() {
		return sumRate6Transfer;
	}

	public void setSumRate6Transfer(BigDecimal sumRate6Transfer) {
		this.sumRate6Transfer = sumRate6Transfer;
	}

	public BigDecimal getSumRate9Transfer() {
		return sumRate9Transfer;
	}

	public void setSumRate9Transfer(BigDecimal sumRate9Transfer) {
		this.sumRate9Transfer = sumRate9Transfer;
	}

	public BigDecimal getSumRate12Transfer() {
		return sumRate12Transfer;
	}

	public void setSumRate12Transfer(BigDecimal sumRate12Transfer) {
		this.sumRate12Transfer = sumRate12Transfer;
	}

	public BigDecimal getSumRate18Transfer() {
		return sumRate18Transfer;
	}

	public void setSumRate18Transfer(BigDecimal sumRate18Transfer) {
		this.sumRate18Transfer = sumRate18Transfer;
	}

	public BigDecimal getSumRate24Transfer() {
		return sumRate24Transfer;
	}

	public void setSumRate24Transfer(BigDecimal sumRate24Transfer) {
		this.sumRate24Transfer = sumRate24Transfer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<BaseAreaProduct> getBaseAreaProductList() {
		return baseAreaProductList;
	}

	public void setBaseAreaProductList(List<BaseAreaProduct> baseAreaProductList) {
		this.baseAreaProductList = baseAreaProductList;
	}

	public Integer getTerm3() {
		return term3;
	}

	public void setTerm3(Integer term3) {
		this.term3 = term3;
	}

	public Integer getTerm6() {
		return term6;
	}

	public void setTerm6(Integer term6) {
		this.term6 = term6;
	}

	public Integer getTerm9() {
		return term9;
	}

	public void setTerm9(Integer term9) {
		this.term9 = term9;
	}

	public Integer getTerm12() {
		return term12;
	}

	public void setTerm12(Integer term12) {
		this.term12 = term12;
	}

	public Integer getTerm18() {
		return term18;
	}

	public void setTerm18(Integer term18) {
		this.term18 = term18;
	}

	public Integer getTerm24() {
		return term24;
	}

	public void setTerm24(Integer term24) {
		this.term24 = term24;
	}
	
}