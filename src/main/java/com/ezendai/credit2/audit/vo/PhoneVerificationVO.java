package com.ezendai.credit2.audit.vo;
import java.util.Date;
import com.ezendai.credit2.framework.model.BaseModel;
public class PhoneVerificationVO extends BaseModel {
 
	private static final long serialVersionUID = 8138929267019640819L;
	private Long id;
	/** 电核内容 */
	private String content;
	/** 调查时间 */
	private Date inquiryTime ;
	/** 借款ID */
	private Long loanId ;
	/** 姓名 */
	private String name;
	/** 操作人Id */
	private Long  operator_id;
	/**电话*/
	private String tel;
	/** 公司 */
	private String company;
	/** 关系 */
	private String relation ;
	/**电话类型 */
	private Integer telType;
	/**录入时间 */
	private Date createdTime;	
	/** 学校公司名称 */
	private String scName;
	/** 学校公司地址 */
	private String scAddress;
	/** 家庭住址 */
	private String homeAddress;
	/**手机 */
	private String phone;
	/** 固定电话 */
	private String homePhone;
	/** 父母名 */
	private String parentName;
	/** 父母手机 */
	private String parentPhone;
	/** 同事名*/
	private String colleagueName;
	/** 同事手机 */
	private String colleaguePhone;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getInquiryTime() {
		return inquiryTime;
	}
	public void setInquiryTime(Date inquiryTime) {
		this.inquiryTime = inquiryTime;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Long getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(Long operator_id) {
		this.operator_id = operator_id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Integer getTelType() {
		return telType;
	}
	public void setTelType(Integer telType) {
		this.telType = telType;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getScName() {
		return scName;
	}
	public void setScName(String scName) {
		this.scName = scName;
	}
	public String getScAddress() {
		return scAddress;
	}
	public void setScAddress(String scAddress) {
		this.scAddress = scAddress;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentPhone() {
		return parentPhone;
	}
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
	public String getColleagueName() {
		return colleagueName;
	}
	public void setColleagueName(String colleagueName) {
		this.colleagueName = colleagueName;
	}
	public String getColleaguePhone() {
		return colleaguePhone;
	}
	public void setColleaguePhone(String colleaguePhone) {
		this.colleaguePhone = colleaguePhone;
	}
	
}