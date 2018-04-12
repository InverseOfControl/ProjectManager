package com.ezendai.credit2.master.vo;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @Description: 产品明细VO
 * @author 张宜超
 * @date 2016年3月8日
 */
public class ProductDetailManagerVO extends BaseVO{

	private static final long serialVersionUID = -4895136546870967915L;

	private Long id;

	/**产品Id**/
	private Long productId;
	/**产品名称*/
	private String productName;
	/**贷款产品类型（针对车贷产品细分为移交类和流通类）**/
	private Integer carProductType;
	/**综合费率**/
	private BigDecimal sumRate;
	/**借款期限**/
	private Integer term;
	/**借款金额下限**/
	private BigDecimal lowerLimit;
	/**借款金额上限**/
	private BigDecimal upperLimit;
	/**状态**/
	private Integer status;
	/**备注**/
	private String remark;
	/**版本**/
	private Long version;
	/**年利率**/
	private BigDecimal yearRate;
	/**会员类型,免费会员(1),付费会员（半年以下）(2),付费会员（半年以上）(3)**/
	private Integer memberType;
	/**风险金利率**/
	private BigDecimal riskRate;
	/**月利率**/
	private BigDecimal monthRate;
	/**第三方费率**/
	private BigDecimal thirdFeeRate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getCarProductType() {
		return carProductType;
	}
	public void setCarProductType(Integer carProductType) {
		this.carProductType = carProductType;
	}
	public BigDecimal getSumRate() {
		return sumRate;
	}
	public void setSumRate(BigDecimal sumRate) {
		this.sumRate = sumRate;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public BigDecimal getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
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
	public BigDecimal getYearRate() {
		return yearRate;
	}
	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public BigDecimal getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(BigDecimal riskRate) {
		this.riskRate = riskRate;
	}
	public BigDecimal getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}
	public BigDecimal getThirdFeeRate() {
		return thirdFeeRate;
	}
	public void setThirdFeeRate(BigDecimal thirdFeeRate) {
		this.thirdFeeRate = thirdFeeRate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
