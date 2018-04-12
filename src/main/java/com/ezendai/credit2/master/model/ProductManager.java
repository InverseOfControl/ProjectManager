package com.ezendai.credit2.master.model;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * @Description: 产品管理
 * @author 张宜超
 * @date 2016年3月4日
 */
public class ProductManager extends BaseModel{

	private static final long serialVersionUID = -5088631546799601762L;

	private Long id;

	/**产品编号**/
	private String productCode;
	/**产品名称**/
	private String productName;
	/**咨询费费率**/
	private BigDecimal consultingFeeRate;
	/**丙方管理费费率**/
	private BigDecimal managePartRate;
	/**管理费费率**/
	private BigDecimal manageFeeRate;
	/**逾期罚息费率**/
	private BigDecimal overdueInterestRate;
	/**风险金比例**/
	private BigDecimal riskRate;
	/**评估费费率**/
	private BigDecimal assessmentFeeRate;
	/**平息利率**/
	private BigDecimal rate;
	/**状态**/
	private Integer status;
	/**备注**/
	private String remark;
	/**版本**/
	private Long version;
	/**产品类型**/
	private Integer productType;
	/**产品类型名称**/
	private String productTypeName;
	/**同城费费率**/
	private BigDecimal thirdFeeRate;
	/**月利率**/
	private BigDecimal monthRate;
	/**提前还款违约金费率**/
	private BigDecimal penaltyRate;
	/**产品渠道ID**/
	private Integer productChannelId;
	/**产品渠道名称**/
	private String productChannelName;
	/**年利率**/
	private BigDecimal yearRate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public BigDecimal getOverdueInterestRate() {
		return overdueInterestRate;
	}
	public void setOverdueInterestRate(BigDecimal overdueInterestRate) {
		this.overdueInterestRate = overdueInterestRate;
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
		this.remark = remark;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	public Integer getProductChannelId() {
		return productChannelId;
	}
	public void setProductChannelId(Integer productChannelId) {
		this.productChannelId = productChannelId;
	}
	public String getProductChannelName() {
		return productChannelName;
	}
	public void setProductChannelName(String productChannelName) {
		this.productChannelName = productChannelName;
	}
	public BigDecimal getYearRate() {
		return yearRate;
	}
	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	
}
