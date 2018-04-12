package com.ezendai.credit2.after.view;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 费用减免查询返回结果实体类
 * </pre>
 *
 * @author liuhy
 * @version $Id: CostReduce.java, v 0.1 2016年1月14日 下午1:15:45 00237489 Exp $
 */
public class CostReduce  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8975842923791768676L;

	
	/** 借款ID */
	private Long loanId;

	/** 姓名(借款人) */
	private String name;

	/** 身份证号码 */
	private String idNum;

	/** 借款类型 */
	private Long productType;
	
	/** 产品类型 */
	private Long productId;
	
	/** 合同金额 */
	private BigDecimal pactMoney;
	
	/**剩余本金*/
	private BigDecimal remainingPrincipal;

	/** 违约金 */
	private BigDecimal penalty;
	
	/**当期剩余还款利息*/
	private BigDecimal curRemainingInterestAmt;
	
	/**当期剩余本金*/
	private BigDecimal curRemainingPrincipal;
	
	/**当期剩余本息*/
	private BigDecimal curRemainingAmount;
	
	/** 当前期数 */
	private Integer currNum;
	
	/** 借款期限 */
	private Long time;
	
	/** 贷款状态 */
	private Integer status;
	
	/** 减免金额 */
	private BigDecimal amount;

	/** 操作 */
	private String operations;
	
	/**展期时间*/
	private Integer extensionTime;
	
	/** 操作 */
	private String frameNumber;
    

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public BigDecimal getRemainingPrincipal() {
		return remainingPrincipal;
	}

	public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getCurRemainingInterestAmt() {
		return curRemainingInterestAmt;
	}

	public void setCurRemainingInterestAmt(BigDecimal curRemainingInterestAmt) {
		this.curRemainingInterestAmt = curRemainingInterestAmt;
	}

	public BigDecimal getCurRemainingPrincipal() {
		return curRemainingPrincipal;
	}

	public void setCurRemainingPrincipal(BigDecimal curRemainingPrincipal) {
		this.curRemainingPrincipal = curRemainingPrincipal;
	}

	public BigDecimal getCurRemainingAmount() {
		return curRemainingAmount;
	}

	public void setCurRemainingAmount(BigDecimal curRemainingAmount) {
		this.curRemainingAmount = curRemainingAmount;
	}

	public Integer getCurrNum() {
		return currNum;
	}

	public void setCurrNum(Integer currNum) {
		this.currNum = currNum;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	
}
