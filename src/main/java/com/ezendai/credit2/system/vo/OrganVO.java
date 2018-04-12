package com.ezendai.credit2.system.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.vo.BaseVO;

/***
 * 
 * <pre>
 * 机构管理
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class OrganVO extends BaseVO {

	/**  */
	private static final long serialVersionUID = -42671223055484395L;

	private String code;
	private String address;
	private String tel;
	private String name;
	private String postCode;
	private String legalRepresentative;
	private String legalRepresentativeId;
	private String legalTel;
	private Date signedDate;
	private BigDecimal margin;
	private String memo;
	private String orgLevel;
	private Integer status;
	private UserSession user;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getLegalRepresentativeId() {
		return legalRepresentativeId;
	}
	public void setLegalRepresentativeId(String legalRepresentativeId) {
		this.legalRepresentativeId = legalRepresentativeId;
	}
	public Date getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	
	public BigDecimal getMargin() {
		return margin;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLegalTel() {
		return legalTel;
	}
	public void setLegalTel(String legalTel) {
		this.legalTel = legalTel;
	}
	public UserSession getUser() {
		return user;
	}
	public void setUser(UserSession user) {
		this.user = user;
	}
	
}