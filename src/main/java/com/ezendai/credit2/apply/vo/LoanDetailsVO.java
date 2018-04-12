package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.apply.model.Attachment;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.GuaranteeBankAccount;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.SysUser;

/***
 * 
 * <pre>
 * 用于显示编辑和查看页面
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: ApplyVO.java, v 0.1 2014年6月30日 下午6:27:09 HQ-AT6 Exp $
 */
public class LoanDetailsVO extends BaseVO {

	private static final long serialVersionUID = -7830351321940189969L;
	private Long loanId;
	/** 产品信息ID */
	private Long productId;
	private Long userId; //用来保存当前用户
	//客户信息
	private Long personId;
	private String personName;
	private String personSex;
	private String personIdnum;
	private Long personMarried;
	private String personEducationLevel;
	private Long personHasChildren;
	private Short personChildrenNum;
	private String personZipCode;
	private String personAddress;
	private String personMobilePhone;
	private String personeEmail;
	private String personHomePhone;
	private String personHomePhone2;
	private BigDecimal personIncomePerMonth;
	private String sourceOfRepay;

	/***
	 * 车贷
	 */
	private String carPersonName;
	private String carPersonSex;
	private String carPersonIdnum;
	private Long carPersonMarried;
	private String carPersonEducationLevel;
	
	private String carPersonMobilePhone;//小车贷手机号码
	private String carPersoneEmail;
	private Long carPersonHasChildren; //是否有子女
	private String  carPersonAddress;  //居住地址
	private String  carPersonHomePhone; //家庭电话
	private String  carPersonZipCode; //邮政编码
	private BigDecimal 	carRentPerMonth;//每月房贷
	private String carCompanyName;  //单位名称 
	private String  carCompanyAddress;//单位地址
	private Date carRequestDate;
	private Integer carCompanyType;
	private Date carFoundedDate;//车贷公司成立时间
	
	
	private BigDecimal carIncomePerMonth;//月基本薪金

	private String personType;
	
	private Integer loanTypes;//流通类  移交类
	//企业信息
	private Long companyId;
	private String companyName;
	private String companyZipCode;
	private String companyAddress;//企业地址 


	private String loanType;
	private String idnum;
	private String personNo;//客户经理工号
	private Long managerName;//客户经理
	private Long assessorName;
	private String productName;
	private Loan loan;

	private Person person;
	private Company company;
	private Vehicle vehicle;
	private Long vehicleId;
	private Long creditHistoryId;
	
	//拒绝理由
	private String refuseReason;
	
	

	/***
	 * 申请人信贷历史记录
	 */
	private CreditHistory creditHistory;

	private Attachment attachment;

	private Bank bank;

	private List<Contacter> contacterList;

	private List<Guarantee> guaranteeList;

	private GuaranteeBankAccount guaranteeBankAccount;

	private List<Attachment> attachmentList;
	//签批结果
	private List<ApproveResult> approveResultList;

	private Product product;

	private SysUser crm;

	private SysUser service;
	
	private SysUser director;

	public SysUser getDirector() {
		return director;
	}

	public void setDirector(SysUser director) {
		this.director = director;
	}

	private SysUser assessor;

	private BaseArea salesDept;

	private ProductDetail productDetail;
	
	private String hasHouseString;
	
	private Extension extension;
	

	/** 放款银行账户 */
	private BankAccount grantAccount;
	/** 还款银行账户 */
	private BankAccount repayAccount;
	
	/** 借款类型ID */
	private Integer productTypeId;
	
	/** 产品类型名称 */
	private String productTypeName;
	
	private List<Organ> organList;
	private String livingWithType;
	private String livingWithNum;
	private String livingWithOtherText;
	private String requestTimeValue;
	
     
   
     private String salaryIncome;
     private String rentIncome;
     private String otherIncomeAmount;
     
     private PersonTraining personTraining;
	
	
	
	
	

	


     
	public String getLivingWithOtherText() {
		return livingWithOtherText;
	}

	public void setLivingWithOtherText(String livingWithOtherText) {
		this.livingWithOtherText = livingWithOtherText;
	}

	public PersonTraining getPersonTraining() {
		return personTraining;
	}

	public void setPersonTraining(PersonTraining personTraining) {
		this.personTraining = personTraining;
	}

	public String getSalaryIncome() {
		return salaryIncome;
	}

	public void setSalaryIncome(String salaryIncome) {
		this.salaryIncome = salaryIncome;
	}

	public String getRentIncome() {
		return rentIncome;
	}

	public void setRentIncome(String rentIncome) {
		this.rentIncome = rentIncome;
	}

	

	public String getOtherIncomeAmount() {
		return otherIncomeAmount;
	}

	public void setOtherIncomeAmount(String otherIncomeAmount) {
		this.otherIncomeAmount = otherIncomeAmount;
	}

	public String getRequestTimeValue() {
		return requestTimeValue;
	}

	public void setRequestTimeValue(String requestTimeValue) {
		this.requestTimeValue = requestTimeValue;
	}

	public String getLivingWithType() {
		return livingWithType;
	}

	public void setLivingWithType(String livingWithType) {
		this.livingWithType = livingWithType;
	}

	public String getLivingWithNum() {
		return livingWithNum;
	}

	public void setLivingWithNum(String livingWithNum) {
		this.livingWithNum = livingWithNum;
	}

	public List<Organ> getOrganList() {
		return organList;
	}

	public void setOrganList(List<Organ> organList) {
		this.organList = organList;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Person getPerson() {
		return person;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public GuaranteeBankAccount getGuaranteeBankAccount() {
		return guaranteeBankAccount;
	}

	public void setGuaranteeBankAccount(GuaranteeBankAccount guaranteeBankAccount) {
		this.guaranteeBankAccount = guaranteeBankAccount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public List<Contacter> getContacterList() {
		return contacterList;
	}

	public void setContacterList(List<Contacter> contacterList) {
		this.contacterList = contacterList;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonSex() {
		return personSex;
	}

	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}

	public Long getPersonMarried() {
		return personMarried;
	}

	public void setPersonMarried(Long personMarried) {
		this.personMarried = personMarried;
	}

	public String getPersonEducationLevel() {
		return personEducationLevel;
	}

	public void setPersonEducationLevel(String personEducationLevel) {
		this.personEducationLevel = personEducationLevel;
	}

	public Long getPersonHasChildren() {
		return personHasChildren;
	}

	public void setPersonHasChildren(Long personHasChildren) {
		this.personHasChildren = personHasChildren;
	}

	public Short getPersonChildrenNum() {
		return personChildrenNum;
	}

	public void setPersonChildrenNum(Short personChildrenNum) {
		this.personChildrenNum = personChildrenNum;
	}

	public String getPersonZipCode() {
		return personZipCode;
	}

	public void setPersonZipCode(String personZipCode) {
		this.personZipCode = personZipCode;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	public String getPersonMobilePhone() {
		return personMobilePhone;
	}

	public void setPersonMobilePhone(String personMobilePhone) {
		this.personMobilePhone = personMobilePhone;
	}

	public String getPersoneEmail() {
		return personeEmail;
	}

	public void setPersoneEmail(String personeEmail) {
		this.personeEmail = personeEmail;
	}

	public String getPersonHomePhone() {
		return personHomePhone;
	}

	public void setPersonHomePhone(String personHomePhone) {
		this.personHomePhone = personHomePhone;
	}

	public String getPersonHomePhone2() {
		return personHomePhone2;
	}

	public void setPersonHomePhone2(String personHomePhone2) {
		this.personHomePhone2 = personHomePhone2;
	}

	public BigDecimal getPersonIncomePerMonth() {
		return personIncomePerMonth;
	}

	public void setPersonIncomePerMonth(BigDecimal personIncomePerMonth) {
		this.personIncomePerMonth = personIncomePerMonth;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public CreditHistory getCreditHistory() {
		return creditHistory;
	}

	public void setCreditHistory(CreditHistory creditHistory) {
		this.creditHistory = creditHistory;
	}

	public List<Guarantee> getGuaranteeList() {
		return guaranteeList;
	}

	public void setGuaranteeList(List<Guarantee> guaranteeList) {
		this.guaranteeList = guaranteeList;
	}

	public SysUser getCrm() {
		return crm;
	}

	public void setCrm(SysUser crm) {
		this.crm = crm;
	}

	public SysUser getService() {
		return service;
	}

	public void setService(SysUser service) {
		this.service = service;
	}

	public SysUser getAssessor() {
		return assessor;
	}

	public void setAssessor(SysUser assessor) {
		this.assessor = assessor;
	}

	public BaseArea getSalesDept() {
		return salesDept;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setSalesDept(BaseArea salesDept) {
		this.salesDept = salesDept;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public Long getManagerName() {
		return managerName;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getCreditHistoryId() {
		return creditHistoryId;
	}

	public void setCreditHistoryId(Long creditHistoryId) {
		this.creditHistoryId = creditHistoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setManagerName(Long managerName) {
		this.managerName = managerName;
	}

	public Long getAssessorName() {
		return assessorName;
	}

	public String getCompanyZipCode() {
		return companyZipCode;
	}

	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}

	public void setAssessorName(Long assessorName) {
		this.assessorName = assessorName;
	}

	public Integer getLoanTypes() {
		return loanTypes;
	}

	public String getCarPersonMobilePhone() {
		return carPersonMobilePhone;
	}

	public void setCarPersonMobilePhone(String carPersonMobilePhone) {
		this.carPersonMobilePhone = carPersonMobilePhone;
	}

	public void setLoanTypes(Integer loanTypes) {
		this.loanTypes = loanTypes;
	}

	public String getCarPersoneEmail() {
		return carPersoneEmail;
	}

	public void setCarPersoneEmail(String carPersoneEmail) {
		this.carPersoneEmail = carPersoneEmail;
	}

	public Long getCarPersonHasChildren() {
		return carPersonHasChildren;
	}

	public void setCarPersonHasChildren(Long carPersonHasChildren) {
		this.carPersonHasChildren = carPersonHasChildren;
	}

	public String getCarPersonAddress() {
		return carPersonAddress;
	}

	public void setCarPersonAddress(String carPersonAddress) {
		this.carPersonAddress = carPersonAddress;
	}

	public String getCarPersonHomePhone() {
		return carPersonHomePhone;
	}

	public void setCarPersonHomePhone(String carPersonHomePhone) {
		this.carPersonHomePhone = carPersonHomePhone;
	}

	public String getCarPersonZipCode() {
		return carPersonZipCode;
	}

	public void setCarPersonZipCode(String carPersonZipCode) {
		this.carPersonZipCode = carPersonZipCode;
	}

	public BigDecimal getCarRentPerMonth() {
		return carRentPerMonth;
	}

	public void setCarRentPerMonth(BigDecimal carRentPerMonth) {
		this.carRentPerMonth = carRentPerMonth;
	}

	public String getCarCompanyName() {
		return carCompanyName;
	}

	public void setCarCompanyName(String carCompanyName) {
		this.carCompanyName = carCompanyName;
	}
	
	public String getCarCompanyAddress() {
		return carCompanyAddress;
	}

	public void setCarCompanyAddress(String carCompanyAddress) {
		this.carCompanyAddress = carCompanyAddress;
	}

	public String getCarPersonName() {
		return carPersonName;
	}

	public void setCarPersonName(String carPersonName) {
		this.carPersonName = carPersonName;
	}

	public String getCarPersonSex() {
		return carPersonSex;
	}

	public void setCarPersonSex(String carPersonSex) {
		this.carPersonSex = carPersonSex;
	}

	public String getCarPersonIdnum() {
		return carPersonIdnum;
	}

	public void setCarPersonIdnum(String carPersonIdnum) {
		this.carPersonIdnum = carPersonIdnum;
	}

	public Long getCarPersonMarried() {
		return carPersonMarried;
	}

	public void setCarPersonMarried(Long carPersonMarried) {
		this.carPersonMarried = carPersonMarried;
	}

	public String getCarPersonEducationLevel() {
		return carPersonEducationLevel;
	}

	public void setCarPersonEducationLevel(String carPersonEducationLevel) {
		this.carPersonEducationLevel = carPersonEducationLevel;
	}

	public BigDecimal getCarIncomePerMonth() {
		return carIncomePerMonth;
	}

	public void setCarIncomePerMonth(BigDecimal carIncomePerMonth) {
		this.carIncomePerMonth = carIncomePerMonth;
	}

	public Date getCarRequestDate() {
		return carRequestDate;
	}

	public void setCarRequestDate(Date carRequestDate) {
		this.carRequestDate = carRequestDate;
	}

	public Integer getCarCompanyType() {
		return carCompanyType;
	}

	public void setCarCompanyType(Integer carCompanyType) {
		this.carCompanyType = carCompanyType;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public List<ApproveResult> getApproveResultList() {
		return approveResultList;
	}

	public void setApproveResultList(List<ApproveResult> approveResultList) {
		this.approveResultList = approveResultList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCarFoundedDate() {
		return carFoundedDate;
	}

	public void setCarFoundedDate(Date carFoundedDate) {
		this.carFoundedDate = carFoundedDate;
	}
	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getHasHouseString() {
		return hasHouseString;
	}

	public void setHasHouseString(String hasHouseString) {
		this.hasHouseString = hasHouseString;
	}

	public BankAccount getGrantAccount() {
		return grantAccount;
	}

	public void setGrantAccount(BankAccount grantAccount) {
		this.grantAccount = grantAccount;
	}

	public BankAccount getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(BankAccount repayAccount) {
		this.repayAccount = repayAccount;
	}

	public String getSourceOfRepay() {
		return sourceOfRepay;
	}

	public void setSourceOfRepay(String sourceOfRepay) {
		this.sourceOfRepay = sourceOfRepay;
	}
	
	private String isModify;

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	

	
}
