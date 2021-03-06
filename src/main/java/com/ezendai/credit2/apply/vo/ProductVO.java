package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class ProductVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	private List<Long> idList;

	/** 产品编号 */
	private String productCode;

	/** 产品名称 (模糊查询) */
	private String productName;
	
	/** 产品名称 (精确查询) */
	private String productFullName;
	
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

	/** 备注 */
	private String remark;
	
	private Long userId;
	
	private List<Integer> statusList;
	/** 同城费费率 */
	private BigDecimal thirdFeeRate ;
	/** 月利率 */
	private BigDecimal monthRate ;
	
	/** 提前还款违约金费率 */
	private BigDecimal penaltyRate ;
	
	/** 年利率 */
	private BigDecimal yearRate;

	public BigDecimal getOverdueInterestRate() {
		return overdueInterestRate;
	}

	public void setOverdueInterestRate(BigDecimal overdueInterestRate) {
		this.overdueInterestRate = overdueInterestRate;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public String getProductFullName() {
		return productFullName;
	}

	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
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

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getThirdFeeRate() {
		return thirdFeeRate;
	}

	public void setThirdFeeRate(BigDecimal thirdFeeRate) {
		this.thirdFeeRate = thirdFeeRate;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	public BigDecimal getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(BigDecimal penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	
}