package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.apply.assembler.BankAccountAssembler;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.audit.dao.ApproveResultDao;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.AuditService;
import com.ezendai.credit2.audit.service.CarExtensionService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.service.RejectReasonService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.dao.ZonganRiskDao;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.ezendai.credit2.sign.lcb.service.ZonganRiskService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysSerialNumService;
import com.ezendai.credit2.system.vo.SerialNumResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


/***
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author HQ-AT6
 * @version $Id: ApplyServiceImpl.java, v 0.1 2014年6月24日 上午9:04:27 HQ-AT6 Exp $
 */
@Service
public class AuditServiceImpl implements AuditService {
	private static final Logger logger = Logger.getLogger(AuditServiceImpl.class);
	private Gson gson = new Gson();
	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private ApproveResultDao approveResultDao;

	@Autowired
	private LoanService loanService;

	@Autowired
	private PersonService personService;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private RepaymentPlanService repaymentPlanService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BaseAreaDao baseAreaDao;

	@Autowired
	private WorkPlaceInfoService workPlaceInfoService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
	private PersonBankService personBankService;

	@Autowired
	private GuaranteeService guaranteeService;

	@Autowired
	private BankService bankService;

	@Autowired
	private BusinessLogService businessLogService;

	@Autowired
	private SysSerialNumService sysSerialNumService;

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private BaseAreaService baseAreaService;

	@Autowired
	private SysParameterService sysParameterService;

	@Autowired
	private RejectReasonService rejectReasonService;

	@Autowired
	private BlacklistService blacklistService;

	@Autowired
	private RepayDateRuleService repayDateRuleService;

	@Autowired
	private SignLoanRuleService signLoanRuleService;

	@Autowired
	private ExtensionService extensionService;

	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private CarExtensionService carExtensionService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private ZonganRiskDao zonganRiskDao;
	@Autowired
	private IContractGenerateDao contractGenerateDao;

	@Value("${contractMany}")
	private String contractMany;


	@Value("${hexinInfo}")
	private String hexinInfo;

	// 小企业贷违约金，暂写死
	private static final BigDecimal peanutsPenaltyRate = new BigDecimal(String.valueOf(0.03));

	// 出借人 
	private static final String lender = "戴卫新";
	//
	//	// 车贷短期申请日期界限<=15日为上半月
	//	private static final int modeByDay = 15;

	// 车贷还款方式根据期数判断，大于3个月为等额本息
	private static final int carLoanModeByMonth = 3;
	// 车贷还款方式根据期数判断，6期
	private static final int carLoanModeByMonthSix = 6;

	// 截取城市关键字-市
	private static String cityStopWord = "市";

	// 截取城市关键字-区
	private static String districtStopWord = "区";

	// 截取城市关键字-县
	private static String countyStopWord = "县";

	// 车贷月利率
	private static BigDecimal carLoanMonthRate = new BigDecimal(String.valueOf(0.007));

	private static BigDecimal newCarLoanMonthRate = new BigDecimal(String.valueOf(0.01));

	private static BigDecimal newCarLoanCounterFee = new BigDecimal(String.valueOf(0.02));

	//2017年6月28日14:48:37
	//月利率
	private static BigDecimal carLoanCounterFeeNew = new BigDecimal(String.valueOf(0.03));
	//车贷短期，违约金比例
	private static BigDecimal carShortPenaltyRate = new BigDecimal(String.valueOf(0.015));
	//车贷6期，违约金比例
	private static BigDecimal carSixPenaltyRate = new BigDecimal(String.valueOf(0.025));

	/***
	 * 新增车辆信息
	 * 
	 * @param vehicleVo
	 * @return
	 * @see com.ezendai.credit2.audit.service.AuditService#insertVehicleVO(com.ezendai.credit2.apply.vo.VehicleVO)
	 */
	@Override
	public Vehicle insertVehicleVO(VehicleVO vehicleVo) {
		return vehicleService.insert(vehicleVo);
	}

	@Override
	public ApproveResult insertSelective(ApproveResult record) {
		return approveResultDao.insert(record);
	}

	/***
	 * 根据贷款ID查询审批结果
	 * 
	 * @param loanId
	 * @return
	 * @see com.ezendai.credit2.audit.service.AuditService#getApproveResultByLoanId(java.lang.Long)
	 */
	@Override
	public List<ApproveResult> getApproveResultByLoanId(Long loanId) {
		return approveResultDao.getApproveResultByLoanId(loanId);
	}

	@Override
	public Boolean isCreate(Long loanId) {
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			if (loan.getStatus().compareTo(EnumConstants.LoanStatus.合同签订.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.合同确认退回.getValue()) == 0
					|| loan.getStatus().compareTo(EnumConstants.LoanStatus.财务审核退回.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.财务放款退回.getValue()) == 0) {
				return true;
			}
		}
		return false;
	}
	@Override
	public Boolean isCreateExtension(Long loanId) {
		Extension extension = extensionService.get(loanId);
		if (extension != null) {
			if (extension.getStatus().compareTo(EnumConstants.LoanStatus.展期合同签订.getValue()) == 0 || extension.getStatus().compareTo(EnumConstants.LoanStatus.展期合同确认退回.getValue()) == 0)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	@Transactional
	public void createdContract(GenerateContractVO generateContractVO) {
		// 开户人银行帐号信息已经存在则不保存，否则将客户账号信息保存到bank_account表中
		Long loanId = Long.valueOf(generateContractVO.getLoanId());
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			Long personId = loan.getPersonId();
			// 已经生成过合同
			if (StringUtils.isNotEmpty(loan.getContractNo())) {
				// 删除还款计划
				repaymentPlanService.deleteRepaymentPlanByLoanId(loanId);
				// 删除合同信息
				contractService.deleteContractByLoanId(loanId);
				// 删除银行账户信息和客户银行关联
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(personId);
				personBankVO.setLoanId(loanId);
				List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
				if (CollectionUtil.isNotEmpty(personBankList)) {
					for (PersonBank personBank : personBankList) {	
						Long bankAccountId = personBank.getBankAccountId();
						bankAccountService.deleteById(bankAccountId);
						personBankService.deletePersonBank(personBank.getId());
					}
				}
			}

			Person person = personService.get(personId);
			// 借款人
			BankAccount personBankAccount = new BankAccount();
			personBankAccount.setAccount(generateContractVO.getBankAccount());
			personBankAccount.setBranchName(generateContractVO.getBankBranch());
			Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
			personBankAccount.setBank(bank);
			personBankAccount.setBankName(bank.getBankName());
			personBankAccount.setStatus(EnumConstants.BankAccountStatus.ENABLED.getValue());
			personBankAccount.setAccountAuthType(generateContractVO.getAccountAuthType());
			personBankAccount = bankAccountService.insert(personBankAccount);
			// 插入客户银行关联
			PersonBank personBank = new PersonBank();
			personBank.setBankAccountId(personBankAccount.getId());
			personBank.setPersonId(personId);
			personBank.setLoanId(loanId);
			personBankService.insertPersonBank(personBank);
			// 更新贷款信息还款银行和放款银行
			Date contractCreatedDate = generateContractVO.getContractCreatedDate();
			if(contractCreatedDate == null){
				contractCreatedDate = new Date();//小企业贷没有合同日期选择.
			}
			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setContractSrc(generateContractVO.getContractSrc());
			loanVO.setGrantAccountId(bank.getId());
			loanVO.setRepayAccountId(bank.getId());
			// 签约日期
			loanVO.setSignDate(contractCreatedDate); 

			String contractNo = createdContractNo(loanId,contractCreatedDate);
			loanVO.setContractNo(contractNo);
			// 担保人
			GuaranteeVO guaranteeVO = new GuaranteeVO();
			guaranteeVO.setLoan(loan);
			guaranteeVO.setPersonId(personId);
			guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
			BankAccount guarantBankAccount = null;
			if (CollectionUtil.isNotEmpty(guaranteeList)) {
				for (Guarantee g : guaranteeList) {
					BankAccountVO guaranteeBankAccountVO = new BankAccountVO();
					Bank guaranteeBank;
					// 自然人1
					if (g.getName().equals(generateContractVO.getNaturalGuaranteeName1())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount1());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch1());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank1()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//自然人2
					} else if (g.getName().equals(generateContractVO.getNaturalGuaranteeName2())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount2());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch2());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank2()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//法人1
					} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName1())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount1());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch1());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank1()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//法人2
					} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName2())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount2());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch2());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank2()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);
					}
				}
			}

			Product product = productService.get(loan.getProductId());

			ProductDetailVO productDetailVO = new ProductDetailVO();
			productDetailVO.setProductId(product.getId());
			productDetailVO.setTerm(loan.getAuditTime());
			if (product.getProductType().compareTo(2) == 0) {
				productDetailVO.setCarProductType(loan.getLoanType().intValue());
			}

			ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);

			// 生成还款信息
			RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
			repaymentPlanVO.setLoanId(loan.getId());
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());

			// 期数
			Integer auditTime = loan.getAuditTime();

			// 平息利率
			BigDecimal rate = product.getRate();
			// 管理费率
			BigDecimal manageFeeRate = product.getManageFeeRate();

			//非固定还款日的
			// 还款开始日期
			Date startRepayDate = new Date();
			Date repayDay = new Date();

			//查看是否有还款日规则
			LoanRuleVO loanRuleVo = new LoanRuleVO();
			loanRuleVo.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
			loanRuleVo.setProductType(product.getProductType());
			if (productDetail.getCarProductType() != null) {
				loanRuleVo.setProductSubtype(productDetail.getCarProductType());
			}
			loanRuleVo.setContractSrc(generateContractVO.getContractSrc());
			List<LoanRule> loanRepayDateRuleList = repayDateRuleService.findRuleByParams(loanRuleVo);
			if (CollectionUtil.isNullOrEmpty(loanRepayDateRuleList)) {
				throw new BusinessException("没有还款日规则");
			}
			if (loanRepayDateRuleList.size() > 1) {
				throw new BusinessException("还款日规则大于1条");
			}
			LoanRule loanRepayDate = loanRepayDateRuleList.get(0);
			if (loanRepayDate.getRepaydateRule().equals(EnumConstants.RepayDateRuleType.FIXED_DATE.getValue())) {
				//固定还款日规则的
				startRepayDate = getRepayRuleDate(contractCreatedDate);
				repayDay = getRepayRuleDate(contractCreatedDate);
				// 当前月天数
				int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
				Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
				if ((nowDay >= 13 && nowDay <= 15) || (dayOfMonth - nowDay) < 3) {
					//是否有特殊签单规则
					loanRuleVo.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());
					loanRuleVo.setContractSrc(null);
					List<LoanRule> loanSignRuleList = signLoanRuleService.findSignRuleByLoanRuleVo(loanRuleVo);
					if (CollectionUtil.isNotEmpty(loanSignRuleList)) {
						if (loanSignRuleList.size() > 1) {
							throw new BusinessException("特殊签单规则大于1条");
						}
						startRepayDate = getSignRuleDate(startRepayDate);
						repayDay = getSignRuleDate(repayDay);
						//add by LIN 特殊签单的话,在主表写标记
						loanVO.setFlag(EnumConstants.LoanRuleType.SIGN_RULE.getValue());
					}
				}
			} else {
				// 非固定还款日规则的
				startRepayDate = getNotRepayRuleDate(contractCreatedDate);
				repayDay = getNotRepayRuleDate(contractCreatedDate);
			}
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			Calendar c = Calendar.getInstance();
			c.setTime(repayDay);
			c.add(Calendar.MONTH, auditTime - 1);
			DateUtil.setHMSM(c, 0, 0, 0, 0);
			Date endRepayDate = c.getTime();
			// 合同金额
			BigDecimal pactMoney = loan.getAuditMoney();



			// 车贷
			BigDecimal preparatoryAmount = BigDecimal.ZERO;
			BigDecimal risk = BigDecimal.ZERO;;

			BigDecimal totalInterestAmt = BigDecimal.ZERO;
			BigDecimal consultingFeeRate = product.getConsultingFeeRate();
			BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();

			//前期风险金
			BigDecimal raskAmount = BigDecimal.ZERO;
			if (auditTime.compareTo(carLoanModeByMonth) > 0) {
				if (product.getId().compareTo(2L) != 0) {
					BigDecimal riskRate = product.getRiskRate();
					risk = (pactMoney.multiply(riskRate)).divide(new BigDecimal(12), 8,
							BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(loan.getAuditTime()));
					pactMoney = pactMoney.add(risk);
				}
				BigDecimal auditMoney = loan.getAuditMoney();
				loanVO.setTime(auditTime.longValue());
				// 中长期车贷
				// 第1期
				// 当前期数
				int curNum = 1;
				repaymentPlanVO.setCurNum(curNum);
				//前期费用=咨询费+评估费 + 风险基金
				// 咨询费
				BigDecimal referRate = auditMoney.multiply(consultingFeeRate);
				loanVO.setConsult(referRate);
				// 评估费
				BigDecimal evalRate = auditMoney.multiply(assessmentFeeRate);
				loanVO.setAssessment(evalRate);
				//风险基金
				BigDecimal riskRate = product.getRiskRate();
				risk = (auditMoney.multiply(riskRate)).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(loanVO.getTime()));
				loanVO.setRisk(risk);
				preparatoryAmount = referRate.add(evalRate).add(risk);
				// 月利率
				loanVO.setMonthRate(newCarLoanMonthRate);
				BigDecimal averageCapital = new BigDecimal(String.valueOf(PMT(newCarLoanMonthRate.doubleValue(), auditTime.doubleValue(), pactMoney.doubleValue()))).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setAverageCapital(averageCapital);
				// 当期归还利息
				BigDecimal interestAmt = pactMoney.multiply(newCarLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				totalInterestAmt = totalInterestAmt.add(interestAmt);
				repaymentPlanVO.setInterestAmt(interestAmt);
				repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
				// 当期归还本金
				BigDecimal principalAmt = averageCapital.subtract(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setPrincipalAmt(principalAmt);
				repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
				// 第1到第n-1期当期归还本金
				BigDecimal totalPrincipalAmt = principalAmt;
				// 剩余本金
				BigDecimal remainingPrincipal = pactMoney.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
				// 违约金比例
				BigDecimal carPenaltyRate = getCarPenaltyRate(auditTime, curNum - 1,product.getId());
				// 提前还款违约金
				BigDecimal penalty = remainingPrincipal.multiply(carPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setPenalty(penalty);
				// 每月还款金额
				BigDecimal repayAmount = auditMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP).add(auditMoney.multiply(productDetail.getSumRate()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setRepayAmount(repayAmount);
				// 当期一次性还款金额
				BigDecimal oneTimeRepaymentAmount = penalty.add(remainingPrincipal).add(repayAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
				// 丙方管理费 = 合同金额*4%/12
				BigDecimal managePart1Fee = pactMoney.multiply(new BigDecimal(String.valueOf(0.04))).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);

				repaymentPlanVO.setManagePart1Fee(managePart1Fee);
				repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
				loanVO.setcManage(BigDecimal.ZERO);
				// 乙方管理费 = 每月还款金额 - 应还本息 - 丙方管理费
				BigDecimal managePart0Fee = repayAmount.subtract(averageCapital).subtract(managePart1Fee).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setCurRemainingManagePart0Fee(managePart0Fee);
				repaymentPlanVO.setManagePart0Fee(managePart0Fee);
				loanVO.setbManage(BigDecimal.ZERO);
				BigDecimal deficit = repaymentPlanVO.getCurRemainingInterestAmt()
						.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
						.add(repaymentPlanVO.getCurRemainingPrincipal());
				repaymentPlanVO.setDeficit(deficit);
				repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
				repaymentPlanService.insert(repaymentPlanVO);
				// 第2期到第n-1期
				for (curNum = 2; curNum < loanVO.getTime(); curNum++) {
					repaymentPlanVO.setCurNum(curNum);
					// 当期归还利息
					interestAmt = remainingPrincipal.multiply(newCarLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					// 当期归还本金
					principalAmt = averageCapital.subtract(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setPrincipalAmt(principalAmt);
					repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
					totalPrincipalAmt = totalPrincipalAmt.add(principalAmt);
					// 剩余本金
					remainingPrincipal = remainingPrincipal.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
					// 违约金比例
					carPenaltyRate = getCarPenaltyRate(auditTime, curNum - 1,product.getId());
					// 提前还款违约金
					penalty = remainingPrincipal.multiply(carPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setPenalty(penalty);
					// 每月还款金额
					repayAmount = auditMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP).add(auditMoney.multiply(productDetail.getSumRate()))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRepayAmount(repayAmount);
					// 当期一次性还款金额
					oneTimeRepaymentAmount = penalty.add(remainingPrincipal).add(repayAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					repayDay = DateUtil.getNowDateAfter(1, repayDay);
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					deficit = repaymentPlanVO.getCurRemainingInterestAmt()
							.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingPrincipal());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
				}
				// 最后1期
				// 当前期数
				repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
				// 当期归还本金
				principalAmt = pactMoney.subtract(totalPrincipalAmt);
				repaymentPlanVO.setPrincipalAmt(principalAmt);
				repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
				// 当期归还利息
				interestAmt = averageCapital.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setInterestAmt(interestAmt);
				repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
				totalInterestAmt = totalInterestAmt.add(interestAmt);
				// 当期一次性还款金额
				oneTimeRepaymentAmount = repayAmount;
				repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
				repayDay = DateUtil.getNowDateAfter(1, repayDay);
				repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
				repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
				deficit = repaymentPlanVO.getCurRemainingInterestAmt()
						.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
						.add(repaymentPlanVO.getCurRemainingPrincipal());
				repaymentPlanVO.setDeficit(deficit);
				repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
				//剩余本金
				repaymentPlanVO.setRemainingPrincipal(BigDecimal.ZERO);
				//提前还款违约金
				repaymentPlanVO.setPenalty(BigDecimal.ZERO);
				repaymentPlanService.insert(repaymentPlanVO);
			} else {
				// 短期车贷
				// 当前月天数
				SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);

				SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
				Date date=new Date();
				try {
					date = sdftime.parse(parameter.getParameterValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){

					int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
					Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
					if (nowDay == 1 || nowDay == 16) {
						loanVO.setTime(auditTime.longValue());
					} else {
						loanVO.setTime(4L);
						// 还款结束日期
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(repayDay);
						calendar.add(Calendar.MONTH, loanVO.getTime().intValue() - 1);
						DateUtil.setHMSM(calendar, 0, 0, 0, 0);
						endRepayDate = calendar.getTime();
					}
					//当日到首期付款剩余天数
					int residueDays = dayOfMonth - nowDay + 1;

					if (nowDay > 15) {
						residueDays = dayOfMonth - nowDay + 1 + 15;
					}
					SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_SHORT_CIRCULATE_SUM_RATE);

					// 费率
					BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());
					//前期费用
					//D10-D9
					BigDecimal a = carRate.subtract(carLoanMonthRate);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
					BigDecimal b = a.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal d = b.multiply(new BigDecimal(residueDays));

					BigDecimal carShortRistRate = product.getRiskRate();
					//					// 还款日期为下月1日
					//					repayDay = DateUtil.getNextMonthFirstDay();
					// 下半月
					//					if (nowDay.compareTo(modeByDay) > 0) {
					//						// 还款日期为下月16日
					//						repayDay = DateUtil.getNextMonthSixteenDay();
					//					}
					//第1期
					int curNum = 1;
					repaymentPlanVO.setCurNum(curNum);
					//利率

					// 还款金额
					//=$D$7*$D$10
					BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

					repaymentPlanVO.setRepayAmount(repayAmount);
					// 风险基金
					risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRisk(risk);
					repaymentPlanVO.setCurRemainingRisk(risk);
					//前期风险金
					raskAmount = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 12, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(residueDays)).setScale(2, BigDecimal.ROUND_HALF_UP);
					//总风险金
					loanVO.setRisk(raskAmount);
					//D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3)
					BigDecimal e = carLoanMonthRate.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(residueDays));
					// 前期利息
					//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
					BigDecimal  preparatoryInterestAmt = pactMoney.multiply(e).setScale(2, BigDecimal.ROUND_HALF_UP);
					//ROUND(D7*A5,2)+D13
					BigDecimal f=pactMoney.multiply(newCarLoanCounterFee).setScale(2, BigDecimal.ROUND_HALF_UP).add(preparatoryInterestAmt);
					preparatoryAmount = pactMoney.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP).add(f);	
					//前期服务费 =D12-ROUND(D14,2)-D13
					BigDecimal preparatoryServeRate = preparatoryAmount.subtract(raskAmount).subtract(preparatoryInterestAmt);
					loanVO.setGrantMoney(pactMoney.subtract(preparatoryAmount));
					//前期咨询费
					BigDecimal preparatoryReferRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setConsult(preparatoryReferRate);
					//前期评估费
					BigDecimal preparatoryEvalRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setAssessment(preparatoryEvalRate);
					//前期丙方管理费
					BigDecimal preparatoryManagePart1Fee = preparatoryServeRate.subtract(preparatoryReferRate).subtract(preparatoryEvalRate);
					loanVO.setcManage(preparatoryManagePart1Fee);
					//前期乙方管理费
					loanVO.setbManage(BigDecimal.ZERO);
					//To be delete
					System.out.println("e="+e);
					System.out.println("e="+dayOfMonth);
					System.out.println("e="+residueDays);


					// 利息

					BigDecimal  interestAmt=new BigDecimal(0);
					BigDecimal interest=new  BigDecimal(0);
					if ( nowDay> 15){
						//=ROUND(I7*I9/(G21-DATE(YEAR(I3),MONTH(I3),16))*(G21-I3),2);
						//(G21-DATE(YEAR(I3),MONTH(I3),16))
						int g=(int) DateUtil.getDiffDay(Dateof16(contractCreatedDate),repayDay);
						//(G21-I3)
						int h=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						interest = interestAmt.divide(new BigDecimal(g),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(h)).setScale(2, BigDecimal.ROUND_HALF_UP);
						loanVO.setProphaseInterest(interest);
					}else{
						//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
						//=ROUND($D$7*$D$9,2)
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						// DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
						Calendar calendar = new GregorianCalendar(); 
						calendar.setTime(contractCreatedDate); //放入你的日期 
						int m=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						//(B21-D3);
						int n=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
						interest = interestAmt.divide(new BigDecimal(m),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(n)).setScale(2, BigDecimal.ROUND_HALF_UP);
						loanVO.setProphaseInterest(interest);
					}
					totalInterestAmt = interest;

					// 服务费
					BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					// 咨询费
					BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setReferRate(referRate);
					repaymentPlanVO.setCurRemainingReferRate(referRate);
					// 评估费
					BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setEvalRate(evalRate);
					repaymentPlanVO.setCurRemainingEvalRate(evalRate);
					// 丙方管理费
					BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
					repaymentPlanVO.setManagePart1Fee(managePart1Fee);
					repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
					// 乙方管理费
					repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					loanVO.setbManage(BigDecimal.ZERO);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}

					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
					if (nowDay.compareTo(16) == 0 || nowDay.compareTo(1) == 0) {
						oneTimeRepaymentAmount = pactMoney;
					} else {
						//D7*D10+D7*A5-D12+D7
						oneTimeRepaymentAmount = pactMoney.multiply(carRate).add(pactMoney.multiply(newCarLoanCounterFee)) .subtract(preparatoryAmount).add(pactMoney);
					}
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
							.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingReferRate());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
					oneTimeRepaymentAmount = pactMoney;
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					for (curNum = 2; curNum < loanVO.getTime(); curNum++) {

						repaymentPlanVO.setCurNum(curNum);
						// 还款金额
						repayAmount = pactMoney.multiply(carRate);
						repaymentPlanVO.setRepayAmount(repayAmount);

						if (curNum > 1) {
							repayDay = DateUtil.getNowDateAfter(1, repayDay);
						}
						repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
						repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
						// 利息
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						totalInterestAmt = totalInterestAmt.add(interestAmt);
						repaymentPlanVO.setInterestAmt(interestAmt);
						repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
						deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
								.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
								.add(repaymentPlanVO.getCurRemainingReferRate());
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
						repaymentPlanService.insert(repaymentPlanVO);
					}
					// 最后一期
					repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
					// 还款金额
					repayAmount = pactMoney;
					repaymentPlanVO.setRepayAmount(repayAmount);

					repaymentPlanVO.setInterestAmt(null);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					// 还款本金
					repaymentPlanVO.setPrincipalAmt(pactMoney);
					repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					repaymentPlanVO.setRisk(BigDecimal.ZERO);
					// 咨询费
					repaymentPlanVO.setReferRate(BigDecimal.ZERO);
					// 评估费
					repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
					// 丙方管理费
					repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
					//deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
					deficit = repaymentPlanVO.getRepayAmount();
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
					repaymentPlanService.insert(repaymentPlanVO);
				}else{
					int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
					Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
					if (nowDay == 1 || nowDay == 16) {
						loanVO.setTime(auditTime.longValue());
					} else {
						loanVO.setTime(4L);
						// 还款结束日期
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(repayDay);
						calendar.add(Calendar.MONTH, loanVO.getTime().intValue() - 1);
						DateUtil.setHMSM(calendar, 0, 0, 0, 0);
						endRepayDate = calendar.getTime();
					}
					//当日到首期付款剩余天数
					int residueDays = dayOfMonth - nowDay + 1;

					if (nowDay > 15) {
						residueDays = dayOfMonth - nowDay + 1 + 15;
					}

					// 费率
					BigDecimal carRate = productDetail.getSumRate();

					//前期费用
					//D10-D9
					BigDecimal a = carRate.subtract(carLoanMonthRate);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
					BigDecimal b = a.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal d = b.multiply(new BigDecimal(residueDays));
					preparatoryAmount = pactMoney.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal carShortRistRate = product.getRiskRate();
					//					// 还款日期为下月1日
					//					repayDay = DateUtil.getNextMonthFirstDay();
					// 下半月
					//					if (nowDay.compareTo(modeByDay) > 0) {
					//						// 还款日期为下月16日
					//						repayDay = DateUtil.getNextMonthSixteenDay();
					//					}
					//第1期
					int curNum = 1;
					repaymentPlanVO.setCurNum(curNum);
					//利率
					//D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal e = carLoanMonthRate.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(residueDays));
					// 还款金额
					BigDecimal repayAmount = pactMoney.multiply(a.add(e)).setScale(2, BigDecimal.ROUND_HALF_UP);

					repaymentPlanVO.setRepayAmount(repayAmount);
					// 风险基金
					risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRisk(risk);
					repaymentPlanVO.setCurRemainingRisk(risk);
					//前期风险金
					raskAmount = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 12, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(residueDays)).setScale(2, BigDecimal.ROUND_HALF_UP);
					//总风险金
					loanVO.setRisk(raskAmount);
					//前期服务费
					BigDecimal preparatoryServeRate = preparatoryAmount.subtract(raskAmount);
					//前期咨询费
					BigDecimal preparatoryReferRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setConsult(preparatoryReferRate);
					//前期评估费
					BigDecimal preparatoryEvalRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setAssessment(preparatoryEvalRate);
					//前期丙方管理费
					BigDecimal preparatoryManagePart1Fee = preparatoryServeRate.subtract(preparatoryReferRate).subtract(preparatoryEvalRate);
					loanVO.setcManage(preparatoryManagePart1Fee);
					//前期乙方管理费
					loanVO.setbManage(BigDecimal.ZERO);
					//To be delete
					System.out.println("e="+e);
					System.out.println("e="+dayOfMonth);
					System.out.println("e="+residueDays);
					// 利息
					//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3),2)
					BigDecimal interestAmt = pactMoney.multiply(e).setScale(2, BigDecimal.ROUND_HALF_UP);

					totalInterestAmt = interestAmt;

					// 服务费
					BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					// 咨询费
					BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setReferRate(referRate);
					repaymentPlanVO.setCurRemainingReferRate(referRate);
					// 评估费
					BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setEvalRate(evalRate);
					repaymentPlanVO.setCurRemainingEvalRate(evalRate);
					// 丙方管理费
					BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
					repaymentPlanVO.setManagePart1Fee(managePart1Fee);
					repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
					// 乙方管理费
					repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					loanVO.setbManage(BigDecimal.ZERO);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}

					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
					if (nowDay.compareTo(16) == 0 || nowDay.compareTo(1) == 0) {
						oneTimeRepaymentAmount = pactMoney.add(interestAmt);
					} else {
						oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carRate)).subtract(preparatoryAmount);
					}
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
							.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingReferRate());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
					oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					for (curNum = 2; curNum < loanVO.getTime(); curNum++) {

						repaymentPlanVO.setCurNum(curNum);
						// 还款金额
						repayAmount = pactMoney.multiply(carRate);
						repaymentPlanVO.setRepayAmount(repayAmount);

						if (curNum > 1) {
							repayDay = DateUtil.getNowDateAfter(1, repayDay);
						}
						repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
						repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
						// 利息
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						totalInterestAmt = totalInterestAmt.add(interestAmt);
						repaymentPlanVO.setInterestAmt(interestAmt);
						repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
						deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
								.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
								.add(repaymentPlanVO.getCurRemainingReferRate());
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
						repaymentPlanService.insert(repaymentPlanVO);
					}
					// 最后一期
					repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
					// 还款金额
					repayAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
					repaymentPlanVO.setRepayAmount(repayAmount);

					repaymentPlanVO.setInterestAmt(interestAmt);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					// 还款本金
					repaymentPlanVO.setPrincipalAmt(pactMoney);
					repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					repaymentPlanVO.setRisk(BigDecimal.ZERO);
					// 咨询费
					repaymentPlanVO.setReferRate(BigDecimal.ZERO);
					// 评估费
					repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
					// 丙方管理费
					repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
					deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
					repaymentPlanService.insert(repaymentPlanVO);
				}
				//营业网点地址

			}
			Long salesDeptId = loan.getSalesDeptId();
			BaseArea baseArea = baseAreaService.get(salesDeptId);
			WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
			createCarRepaymentContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, preparatoryAmount, contractNo, totalInterestAmt, loanVO.getTime(),
					raskAmount, workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, totalInterestAmt, contractNo, loanVO.getTime(), preparatoryAmount,
					workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarPersonLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, risk, contractNo, loanVO.getTime(), raskAmount, workPlaceInfo.getSite(),pactMoney
					,contractCreatedDate);
			createCarVehicleContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, contractNo, loanVO.getTime(), workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarEntrustContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, contractNo, loanVO.getTime(),pactMoney,contractCreatedDate);
			createCarRepaymentFundContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, contractNo, loanVO.getTime(), workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarCollectionDetailContract(loan, contractNo, loanVO.getTime(),pactMoney,contractCreatedDate);
			loanVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
			loanVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));
			loanVO.setPactMoney(pactMoney);
			loanVO.setContractCreatedTime(new Date());

			// 放款金额
			// 小企业贷： 划款金额=合同金额-风险金-咨询费-评估费
			// 车贷（3期）：划款金额=合同金额-风险金-咨询费--评估费-丙方管理费
			// 车贷（6、9、12期）：划款金额=合同金额-风险金-咨询费-评估费
			//等额本息的产品为：小企业贷，车贷6.9.12期
			//先息后本的产品为：车贷3期
			BigDecimal grantMoney = loanVO.getPactMoney()
					.subtract(loanVO.getRisk()).subtract(loanVO.getConsult())
					.subtract(loanVO.getAssessment());
			if (loan.getProductType().compareTo(
					EnumConstants.ProductType.CAR_LOAN.getValue()) == 0
					&& loan.getAuditTime().compareTo(3) == 0) {
				grantMoney = grantMoney.subtract(loanVO.getcManage());
				loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.BEFORE_INTEREST_AFTER_PRINCIPAL_PAYMENT
						.getValue());
			} else {
				loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST
						.getValue());
			}
			SysParameter parameter=sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
			SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())&&auditTime.compareTo(carLoanModeByMonth) <=0){
			}else{
				loanVO.setGrantMoney(grantMoney);
			}
			//还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(loanVO.getEndRepayDate());
			loanVO.setReturnDate(calendar.get(Calendar.DATE));
			loanVO.setResidualPactMoney(pactMoney);
			loanVO.setResidualTime(loanVO.getTime().intValue());
			// 更新贷款信息表
			loanService.update(loanVO);
			// 插入日志
			BusinessLog businessLog = new BusinessLog();
			businessLog.setLoanId(loanId);
			businessLog.setMessage("合同生成");
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue());
			businessLogService.insert(businessLog);

			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CREATE_CONTRACT.getValue());
			sysLog.setRemark("借款ID   "+loanId.toString());
			sysLogService.insert(sysLog);
		}
	}

	// 生成规则：ZDB-1（小企业贷）-2014（年份）-01（上海）-1（版本）-6位数字 （当年第N份合同）。
	// 例：ZDB12014011000001代表：上海地区，2014年第1份小企业贷合同，合同版本为1。同理，车贷为2。
	@Transactional
	private String createdContractNo(Long loanId,Date contractCreatedDate) {
		Loan loan = loanService.get(loanId);
		Product product = productService.get(loan.getProductId());
		StringBuffer contractNo = new StringBuffer();
		contractNo.append("ZDB");
		// 1为小企业贷，2为车贷
		contractNo.append(product.getProductType());
		// 获取当前日期
		Calendar c = Calendar.getInstance();
		c.setTime(contractCreatedDate);
		int year = c.get(Calendar.YEAR);
		contractNo.append(year);
		// 地区
		Long baseAreaId = loan.getSalesDeptId();
		BaseArea baseArea = baseAreaDao.get(baseAreaId);
		Long workPlaceInfoId = baseArea.getWorkPlaceInfoId();
		WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(workPlaceInfoId);
		contractNo.append(workPlaceInfo.getCityNo());
		//合同版本  现在都为1
		contractNo.append("1");
		// 6位数字，取各种类型对于的seq
		Long seqNumber;
		if (product.getProductType()== 1) {
			// 小企业贷合同
			SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(SerialNum.CONTRACT1);
			seqNumber = serialNumResult.getSeq();
		} else {
			// 车贷合同
			SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(SerialNum.CONTRACT2);
			seqNumber = serialNumResult.getSeq();
		}
		// 流水号不足6位补0
		DecimalFormat df = new DecimalFormat("000000");
		String seq = df.format(seqNumber);
		contractNo.append(seq);
		return contractNo.toString();
	}

	/**
	 * 固定还款日规则日期变化
	 * 如签约日为1-15号，还款日为下月1号，否则为下月16号
	 */
	private Date getRepayRuleDate(Date contractCreatedDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(contractCreatedDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day > 15) {
			c.set(year, month + 1, 16);
		} else {
			c.set(year, month + 1, 1);
		}
		return c.getTime();
	}

	/**
	 * 特殊签单日期变化
	 * @param date
	 * @return
	 */
	private Date getSignRuleDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day == 16) {
			c.set(year, month + 1, 1);
		} else {
			c.set(year, month, 16);
		}
		return c.getTime();

	}

	/**
	 * 非固定还款日规则日期变化
	 * 7月27号签约，8月27号首期还款；28-31号签约，统一为下月28号还款
	 * @return
	 */
	private Date getNotRepayRuleDate(Date contractCreatedDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(contractCreatedDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day >= 28) {
			c.set(year, month + 1, 28);
		} else {
			c.set(year, month + 1, day);
		}
		return c.getTime();
	}

	/**
	 * 生成小企业-个人借款咨询服务风险基金协议合同
	 * 
	 * @param
	 */
	@Transactional
	private void createPeanutsPersonLoanContract(Loan loan, Person person, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_PERSON_LOAN.getValue());
		contract.setSignDate(new Date());
		String cityName = "上海";
		contract.setCityName(cityName);
		String areaName = "浦东新";
		contract.setAreaName(areaName);
		contract.setContractName("个人借款咨询服务风险基金协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		BigDecimal pactMoney = loan.getAuditMoney();
		Product product = productService.get(loan.getProductId());
		BigDecimal riskRate = product.getRiskRate();
		contract.setPayAmount(pactMoney.multiply(riskRate));
		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-借款协议合同
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsLoanContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, BankAccount personBankAccount, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_LOAN.getValue());
		contract.setSignDate(new Date());
		String cityName = "上海";
		contract.setCityName(cityName);
		String areaName = "浦东新";
		contract.setAreaName(areaName);
		contract.setContractName("借款协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setZipCode(person.getZipCode());
		contract.setPurpose(loan.getPurpose());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setMonthInterestAmount(repaymentPlanVO.getAverageCapital());
		//TODO 未确定
		contract.setTimes(loan.getAuditTime());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));

		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-个人借款咨询服务协议合同
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsRepaymentContract(Loan loan, Person person,
			RepaymentPlanVO repaymentPlanVO,
			BankAccount personBankAccount, String contractNo,
			BigDecimal referRate, BigDecimal assessmentFees) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_REPAYMENT.getValue());
		contract.setSignDate(new Date());
		String cityName = "上海";
		contract.setCityName(cityName);
		String areaName = "浦东新";
		contract.setAreaName(areaName);
		contract.setContractName("个人借款咨询服务协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setReferRate(referRate);
		contract.setAssessmentFees(assessmentFees);
		BigDecimal manageFees = repaymentPlanVO.getManagePart0Fee().add(repaymentPlanVO.getManagePart1Fee());
		contract.setManageFees(manageFees);	
		Product product = productService.get(loan.getProductId());
		BigDecimal ttpManageFees = loan.getAuditMoney().multiply(product.getManagePartRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
		contract.setTtpManageFees(ttpManageFees);
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setZipCode(person.getZipCode());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setTimes(loan.getAuditTime());
		BigDecimal riskRate = product.getRiskRate();
		contract.setPayAmount(loan.getAuditMoney().multiply(riskRate));
		contract.setBankBranchName(personBankAccount.getBranchName());
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		Long salesDepotId = loan.getSalesDeptId();
		//网点
		baseAreaVO.setId(salesDepotId);
		baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		BaseArea baseArea = baseAreaDao.get(baseAreaVO);
		//城市
		BaseAreaVO cityBaseAreaVO = new BaseAreaVO();
		cityBaseAreaVO.setId(Long.valueOf(baseArea.getCityId()));
		cityBaseAreaVO.setIdentifier(BizConstants.CREDIT2_CITY);
		BaseArea cityBaseArea = baseAreaDao.get(cityBaseAreaVO);
		//区域
		BaseAreaVO areaBaseAreaVO = new BaseAreaVO();
		areaBaseAreaVO.setId(Long.valueOf(cityBaseArea.getAreaId()));
		areaBaseAreaVO.setIdentifier(BizConstants.CREDIT2_AREA);
		BaseArea areaBaseArea = baseAreaDao.get(areaBaseAreaVO);
		//公司
		BaseAreaVO companyBaseAreaVO = new BaseAreaVO();
		companyBaseAreaVO.setId(areaBaseArea.getCompanyId());
		companyBaseAreaVO.setIdentifier(BizConstants.CREDIT2_COMPANY);
		BaseArea companyBaseArea = baseAreaDao.get(companyBaseAreaVO);

		WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
		contract.setBusinessCompanyName(companyBaseArea.getName());
		contract.setBusinessAddress(workPlaceInfo.getSite());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-委托扣款授权书合同
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsEntrustContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, BankAccount personBankAccount, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_ENTRUST.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("委托扣款授权书");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setContact(person.getMobilePhone());
		contract.setBankBranchName(personBankAccount.getBranchName());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-委托扣款授权书合同(担保人)
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsEntrustGuaranteeContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, BankAccount guarantBankAccount, String contractNo, Guarantee guarantee) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setSignDate(new Date());
		contract.setType(EnumConstants.ContractType.PEANUTS_ENTRUST_GUARANTEE.getValue());
		contract.setContractName("委托扣款授权书（担保人）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setGuaranteeName(guarantee.getName());
		contract.setGuaranteeIdNum(guarantee.getIdnum());

		contract.setBankAccountName(guarantee.getName());
		contract.setBankAccountNum(guarantBankAccount.getAccount());
		contract.setOpeningBank(guarantBankAccount.getBankName());
		contract.setBankBranchName(guarantBankAccount.getBranchName());
		contract.setContact(guarantee.getMobilePhone());

		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-还款提醒函 合同
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsRepaymentFundContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, String contractNo, Date startRepayDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setSignDate(new Date());
		contract.setType(EnumConstants.ContractType.PEANUTS_REPAYMENT_FUND.getValue());
		contract.setContractName("还款提醒函 ");
		contract.setPersonName(person.getName());
		BigDecimal manageFees = repaymentPlanVO.getManagePart0Fee().add(repaymentPlanVO.getManagePart1Fee());
		contract.setManageFees(manageFees);
		contract.setMonthAmount(repaymentPlanVO.getRepayAmount());
		contract.setMonthInterestAmount(repaymentPlanVO.getAverageCapital());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));
		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-担保合同书（自然人担保）
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsNaturalGuaranteeContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, String contractNo, Guarantee guarantee) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_NATURAL_GUARANTEE.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("担保合同书（自然人担保）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-005");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		BigDecimal pactMoney = loan.getAuditMoney();
		Product product = productService.get(loan.getProductId());
		BigDecimal riskRate = product.getRiskRate();
		contract.setPayAmount(pactMoney.multiply(riskRate));
		contract.setPactMoney(pactMoney);
		contract.setGuaranteeName(guarantee.getName());
		contract.setGuaranteeIdNum(guarantee.getIdnum());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成小企业-担保合同书（法人担保）
	 * 
	 * @param loan
	 */
	@Transactional
	private void createPeanutsNaturalLegalContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, String contractNo, String guaranteeName) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.PEANUTS_NATURAL_LEGAL.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("担保合同书（法人担保）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-007");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setPactMoney(loan.getAuditMoney());
		BigDecimal pactMoney = loan.getAuditMoney();
		Product product = productService.get(loan.getProductId());
		BigDecimal riskRate = product.getRiskRate();
		contract.setPayAmount(pactMoney.multiply(riskRate));
		contract.setLegalGuarantee(guaranteeName);
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-个人借款咨询服务协议(抵押)
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarRepaymentContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, BankAccount personBankAccount,
			BigDecimal preparatoryAmount, String contractNo, BigDecimal totalInterestAmt, Long time, BigDecimal raskAmount, String salesDepartmentAddress,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_REPAYMENT.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContractName("个人借款咨询服务协议(抵押)");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		Long salesDepotId = loan.getSalesDeptId();
		baseAreaVO.setId(salesDepotId);
		baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		BaseArea baseArea = baseAreaDao.get(baseAreaVO);
		BaseAreaVO companyBaseAreaVO = new BaseAreaVO();
		Long companyId = baseArea.getCompanyId();
		companyBaseAreaVO.setId(companyId);
		companyBaseAreaVO.setIdentifier(BizConstants.CREDIT2_COMPANY);
		BaseArea companyBaseArea = baseAreaDao.get(companyBaseAreaVO);
		WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(companyBaseArea.getWorkPlaceInfoId());
		contract.setBusinessCompanyName(companyBaseArea.getName());
		contract.setBusinessAddress(workPlaceInfo.getSite());
		contract.setPactMoney(pactMoney);
		if (preparatoryAmount.compareTo(BigDecimal.ZERO) != 0) {
			contract.setPreparatoryAmount(preparatoryAmount);
		}
		if (raskAmount.compareTo(BigDecimal.ZERO) != 0) {
			contract.setRaskAmount(raskAmount);
		}
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		if (totalInterestAmt.compareTo(BigDecimal.ZERO) != 0) {
			contract.setTotalAmount(totalInterestAmt);
		}
		contract.setAuditMoney(loan.getAuditMoney());
		contract.setTimes(time.intValue());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-借款抵押协议（抵押）
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarLoanContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, BankAccount personBankAccount, BigDecimal totalInterestAmt,
			String contractNo, Long time, BigDecimal preparatoryAmount, String salesDepartmentAddress,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_LOAN.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContractName("借款抵押协议（抵押）");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);

		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setContact(person.getMobilePhone());
		contract.setEmail(person.getEmail());
		contract.setPurpose(loan.getPurpose());
		contract.setPactMoney(pactMoney);
		contract.setTimes(loan.getAuditTime());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		contract.setPrincipalAmount(loan.getAuditMoney());
		if (totalInterestAmt.compareTo(BigDecimal.ZERO) != 0) {
			contract.setTotalAmount(totalInterestAmt);
		}
		if (preparatoryAmount.compareTo(BigDecimal.ZERO) != 0) {
			contract.setPreparatoryAmount(preparatoryAmount);
		}
		contract.setTimes(time.intValue());
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-个人借款咨询服务风险基金协议
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarPersonLoanContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, BigDecimal risk, String contractNo, Long time,
			BigDecimal raskAmount, String salesDepartmentAddress,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_PERSON_LOAN.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContractName("个人借款咨询服务风险基金协议");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setContact(person.getMobilePhone());
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		if (risk.compareTo(BigDecimal.ZERO) != 0) {
			contract.setRaskAmount(risk);
		}
		if (raskAmount.compareTo(BigDecimal.ZERO) != 0) {
			contract.setRaskAmount(raskAmount);
		}
		contract.setPactMoney(pactMoney);
		contract.setTimes(time.intValue());
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-车辆处置特别授权委托书
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarVehicleContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, String contractNo, Long time, String salesDepartmentAddress,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_VEHICLE.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContractName("车辆处置特别授权委托书");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contract.setIsMarried(person.getMarried().intValue());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-004");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(),loan.getId());
		if (!CollectionUtils.isEmpty(vehicleList)) {
			Vehicle vehicle = vehicleList.get(0);
			contract.setPlateNumber(vehicle.getPlateNumber());
			contract.setFrameNumber(vehicle.getFrameNumber());
			contract.setBrand(vehicle.getBrand());
		}
		contract.setContact(person.getMobilePhone());
		contract.setPactMoney(pactMoney);
		contract.setLender(lender);
		contract.setTimes(time.intValue());
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-委托扣款授权书（无风险基金的描述）
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarEntrustContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, BankAccount personBankAccount, String contractNo, Long time,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_ENTRUST.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContractName("委托扣款授权书（无风险基金的描述）");
		contract.setContact(person.getMobilePhone());
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-005");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		Long salesDepotId = loan.getSalesDeptId();
		baseAreaVO.setId(salesDepotId);
		baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		BaseArea baseArea = baseAreaDao.get(baseAreaVO);
		BaseAreaVO companyBaseAreaVO = new BaseAreaVO();
		Long companyId = baseArea.getCompanyId();
		companyBaseAreaVO.setId(companyId);
		companyBaseAreaVO.setIdentifier(BizConstants.CREDIT2_COMPANY);
		BaseArea companyBaseArea = baseAreaDao.get(companyBaseAreaVO);
		contract.setBusinessCompanyName(companyBaseArea.getName());
		contract.setTimes(time.intValue());
		contract.setPactMoney(pactMoney);
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-还款温馨提示函(抵押)
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarRepaymentFundContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, String contractNo, Long time,
			String salesDepartmentAddress,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_REPAYMENT_FUND.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setContact(person.getMobilePhone());
		contract.setContractName("还款温馨提示函(抵押)");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contract.setTimes(time.intValue());
		contract.setPactMoney(pactMoney);
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 生成车贷-附件：催收费用明细
	 * 
	 * @param loan
	 */
	@Transactional
	private void createCarCollectionDetailContract(Loan loan, String contractNo, Long time,BigDecimal pactMoney,Date contractCreatedDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CAR_COLLECTION_DETAIL.getValue());
		contract.setSignDate(contractCreatedDate);
		contract.setTimes(time.intValue());
		contract.setPactMoney(pactMoney);
		contract.setContractName("附件：催收费用明细");
		contract.setAuditMoney(loan.getAuditMoney());
		contractService.insertSelective(contract);
	}

	/**
	 * 计算月供
	 * 
	 * @param rate
	 *            月利率
	 * @param term
	 *            贷款期数，单位月
	 * @param financeAmount
	 *            贷款金额
	 * @return
	 */
	private double PMT(double rate, double term, double financeAmount) {
		double v = (1 + rate);
		double t = (-(term / 12) * 12);
		double result = (financeAmount * rate) / (1 - Math.pow(v, t));
		return result;
	}

	/**
	 * 实际利率法
	 * 
	 * @author Bean
	 * @param a
	 *            现值--合同金额
	 * @param b
	 *            年金
	 * @param c
	 *            期数
	 * @param cnt
	 *            运算次数
	 * @param ina
	 *            误差位数
	 * @return 利率
	 */
	private static double rate(double a, double b, double c, int cnt, int ina) {
		double rate = 1, x, jd = 0.1, side = 0.1, i = 1;
		do {
			x = a / b - (Math.pow(1 + rate, c) - 1) / (Math.pow(rate + 1, c) * rate);
			if (x * side > 0) {
				side = -side;
				jd *= 10;
			}
			rate += side / jd;
		} while (i++ < cnt && Math.abs(x) >= 1 / Math.pow(10, ina));
		if (i > cnt)
			return Double.NaN;
		return rate;
	}

	private String getCityByAddress(String address) {
		int endCityIndex = address.indexOf(cityStopWord);
		return address.substring(0, endCityIndex);
	}

	private String getAreaByAddress(String address) {
		int endCityIndex = address.indexOf(cityStopWord);
		int endDistrictIndex = address.indexOf(countyStopWord);
		if (endDistrictIndex < 0) {
			endDistrictIndex = address.indexOf(districtStopWord);
		}

		return address.substring(endCityIndex + 1, endDistrictIndex);
	}

	// 车贷中长期违约金比例计算
	private BigDecimal getCarPenaltyRate(Integer totalMonths, Integer returnMonths, Long productId) {
		if (totalMonths.compareTo(24) == 0) {
			if (returnMonths.compareTo(12) < 0) {
				return new BigDecimal(String.valueOf(0.05));
			} else if (returnMonths.compareTo(17) < 0) {
				return new BigDecimal(String.valueOf(0.03));
			} else if (returnMonths.compareTo(23) < 0) {
				return new BigDecimal(String.valueOf(0.02));
			} else {
				return new BigDecimal(String.valueOf(0));
			}
		}
		if (totalMonths.compareTo(12) == 0) {
			if (returnMonths.compareTo(6) < 0) {
				return new BigDecimal(String.valueOf(0.05));
			} else if (returnMonths.compareTo(8) < 0) {
				return new BigDecimal(String.valueOf(0.03));
			} else if (returnMonths.compareTo(12) < 0) {
				return new BigDecimal(String.valueOf(0.02));
			} else {
				return new BigDecimal(String.valueOf(0));
			}
		}
		if (totalMonths.compareTo(9) == 0) {
			if (productId.compareTo(2L) != 0) {
				return new BigDecimal(String.valueOf(0.05));
			}
			if (returnMonths.compareTo(4) < 0) {
				return new BigDecimal(String.valueOf(0.05));
			} else if (returnMonths.compareTo(6) < 0) {
				return new BigDecimal(String.valueOf(0.03));
			} else if (returnMonths.compareTo(8) < 0) {
				return new BigDecimal(String.valueOf(0.02));
			} else {
				return new BigDecimal(String.valueOf(0));
			}
		}
		if (totalMonths.compareTo(6) == 0) {
			if (returnMonths.compareTo(3) < 0) {
				return new BigDecimal(String.valueOf(0.05));
			} else if (returnMonths.compareTo(3) == 0) {
				return new BigDecimal(String.valueOf(0.03));
			} else if (returnMonths.compareTo(4) == 0) {
				return new BigDecimal(String.valueOf(0.02));
			} else {
				return new BigDecimal(String.valueOf(0));
			}
		}
		return new BigDecimal(String.valueOf(0));
	}

	/**
	 * <pre>
	 * 黑名单客户数据保存
	 * </pre>
	 * @param rejectReason
	 * @param approveVO
	 * @param loan
	 */
	private void saveBlacklist(RejectReason rejectReason, ApproveResultVO approveVO, Loan loan) {
		//获取二级原因
		Person person = personService.get(loan.getPersonId());
		GuaranteeVO queryGuaranteeVO = new GuaranteeVO();
		queryGuaranteeVO.setPersonId(person.getId());
		queryGuaranteeVO.setLoan(loan);
		queryGuaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
		Guarantee guarantee = guaranteeService.get(queryGuaranteeVO);
		Blacklist bl = new Blacklist();
		bl.setName(person.getName());
		bl.setIdnum(person.getIdnum());
		bl.setRejectTime(new Date());
		bl.setMphone(person.getMobilePhone());
		bl.setTel(person.getHomePhone());
		bl.setSalesDepartmentId(loan.getSalesDeptId());
		if (loan.getProductType().equals(EnumConstants.ProductType.SE_LOAN.getValue())) {
			bl.setLoanType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (loan.getProductType().equals(EnumConstants.ProductType.CAR_LOAN.getValue())) {
			bl.setLoanType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}
		bl.setLimitDays(rejectReason.getCanRequestDays());
		if (guarantee != null && !StringUtil.isEmpty(guarantee.getCompanyFullName())) {
			bl.setCompany(guarantee.getCompanyFullName());
		}
		bl.setComeFrom(EnumConstants.BlacklistComeFrom.AUDIT.getValue());
		bl.setRejectReasonId(rejectReason.getId());
		bl.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		bl.setRemark("黑名单客户生成,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason());
		blacklistService.insert(bl);
	}

	@Transactional
	public void approve(ApproveResultVO approveVO, Long userId) {
		Loan loan = loanService.get(approveVO.getLoanId());
		Product product = productService.get(loan.getProductId());
		if (product.getProductTypeName().equals("小企业贷")) {
			if (EnumConstants.ApproveResultState.APPROVAL.getValue().equals(approveVO.getState())) {//同意
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setReason(approveVO.getReason());
				ar.setState(approveVO.getState());
				ar.setRequestMoney(approveVO.getRequestMoney());
				ar.setTerm(approveVO.getTerm());
				ar.setContractMatters(approveVO.getContractMatters());
				checkMemberType(approveVO,ar);
				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setAuditMoney(approveVO.getAuditMoney());
				loanVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
				loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
				loanVO.setHasHouse(approveVO.getHasHouse());
				loanVO.setLoanType(approveVO.getLoanType());

				checkMemberType(approveVO, loanVO);
				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				businessLog.setMessage("审贷会决议,审批意见:同意" + (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));
				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			} else if (EnumConstants.ApproveResultState.REFUSE_TO.getValue().equals(approveVO.getState())) {//拒绝
				//黑名单数据保存
				RejectReason rejectReason = rejectReasonService.get(approveVO.getRefuseSecondReasonId());
				saveBlacklist(rejectReason, approveVO, loan);
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setState(approveVO.getState());
				ar.setReason(approveVO.getReason());
				ar.setReason1(rejectReason.getParent().getReason());
				ar.setReason2(rejectReason.getReason());
				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setStatus(EnumConstants.LoanStatus.拒绝.getValue());

				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				businessLog.setMessage("审贷会决议,审批意见:拒绝,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason());
				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			} else if (EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue().equals(approveVO.getState())) {//有条件同意
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setState(approveVO.getState());
				ar.setReason(approveVO.getReason());
				if (approveVO.getCertificates1() != null && !approveVO.getCertificates1().trim().equals(""))
					ar.setCertificates1(approveVO.getCertificates1());
				if (approveVO.getCertificates2() != null && !approveVO.getCertificates2().trim().equals(""))
					ar.setCertificates2(approveVO.getCertificates2());
				if (approveVO.getCertificates3() != null && !approveVO.getCertificates3().trim().equals(""))
					ar.setCertificates3(approveVO.getCertificates3());
				ar.setRequestMoney(approveVO.getRequestMoney());
				ar.setTerm(approveVO.getTerm());
				ar.setContractMatters(approveVO.getContractMatters());

				checkMemberType(approveVO,ar);


				if(approveVO.getGuaIds().length()>0){
					updateGuaranteeFlag(approveVO);
				}


				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setAuditMoney(approveVO.getAuditMoney());
				loanVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
				loanVO.setStatus(EnumConstants.LoanStatus.有条件同意.getValue());
				if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName1())){
					loanVO.setNaturalGuaranteeName1(approveVO.getNaturalGuaranteeName1());
				}
				if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName2())){
					loanVO.setNaturalGuaranteeName2(approveVO.getNaturalGuaranteeName2());
				}
				if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname1())){
					loanVO.setLegalGuaranteeCname1(approveVO.getLegalGuaranteeCname1());
				}
				if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname2())){
					loanVO.setLegalGuaranteeCname2(approveVO.getLegalGuaranteeCname2());
				}
				if (StringUtil.isNotBlank(approveVO.getGuaranteeName())){
					loanVO.setGuaranteeName(approveVO.getGuaranteeName());
				}
				loanVO.setHasHouse(approveVO.getHasHouse());
				checkMemberType(approveVO,loanVO);
				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				String neededCertificate1 = approveVO.getCertificates1() == null ? "" : approveVO.getCertificates1();
				String neededCertificate2 = approveVO.getCertificates2() == null ? "" : approveVO.getCertificates2();
				String neededCertificate3 = approveVO.getCertificates3() == null ? "" : approveVO.getCertificates3();
				String neededCertificates = "";
				StringBuffer neededCertificates_ = new StringBuffer("");
				if (neededCertificate1 != null && !neededCertificate1.trim().equals(""))
					neededCertificates_.append("," + neededCertificate1.trim());
				if (neededCertificate2 != null && !neededCertificate2.trim().equals(""))
					neededCertificates_.append("," + neededCertificate2.trim());
				if (neededCertificate3 != null && !neededCertificate3.trim().equals(""))
					neededCertificates_.append("," + neededCertificate3.trim());
				if (neededCertificates_.length() > 0)
					neededCertificates = neededCertificates_.substring(1);

				businessLog.setMessage("审贷会决议,审批意见:有条件同意" +						
						(approveVO.getNaturalGuaranteeName1() != null&&!"".equals(approveVO.getNaturalGuaranteeName1()) ? " ,自然人担保1：" + approveVO.getNaturalGuaranteeName1() : "")+
						(approveVO.getNaturalGuaranteeName2() != null&&!"".equals(approveVO.getNaturalGuaranteeName2()) ? " ,自然人担保2：" + approveVO.getNaturalGuaranteeName2() : "")+
						(approveVO.getLegalGuaranteeCname1() != null&&!"".equals(approveVO.getLegalGuaranteeCname1()) ? " ,法人担保1：" + approveVO.getLegalGuaranteeCname1() : "")+ 
						(approveVO.getLegalGuaranteeCname2() != null &&!"".equals(approveVO.getLegalGuaranteeCname2()) ? " ,法人担保2：" + approveVO.getLegalGuaranteeCname2() : "")+
						(approveVO.getGuaranteeName()!= null &&!"".equals(approveVO.getGuaranteeName()) ? " ,所有担保人：" + approveVO.getGuaranteeName() : "")+
						(neededCertificates.equals("") ? "" : " ,增加附件：" + neededCertificates)
						+ (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));

				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			}
		} else { // 车贷 （目前同小企业贷逻辑）
			if (EnumConstants.ApproveResultState.APPROVAL.getValue().equals(approveVO.getState())) {//同意
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setReason(approveVO.getReason());
				ar.setState(approveVO.getState());
				ar.setRequestMoney(approveVO.getRequestMoney());
				ar.setTerm(approveVO.getTerm());
				ar.setContractMatters(approveVO.getContractMatters());
				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setAuditMoney(approveVO.getAuditMoney());
				loanVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
				loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
				loanVO.setLoanType(approveVO.getLoanType());
				loanVO.setProductId(Long.valueOf(approveVO.getProductId()));

				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				businessLog.setMessage("审贷会决议,审批意见:同意" + (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));
				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			} else if (EnumConstants.ApproveResultState.REFUSE_TO.getValue().equals(approveVO.getState())) {//拒绝
				RejectReason rejectReason = rejectReasonService.get(approveVO.getRefuseSecondReasonId());
				saveBlacklist(rejectReason, approveVO, loan);
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setState(approveVO.getState());
				ar.setReason(approveVO.getReason());
				ar.setReason1(rejectReason.getParent().getReason());
				ar.setReason2(rejectReason.getReason());
				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setStatus(EnumConstants.LoanStatus.拒绝.getValue());

				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				businessLog.setMessage("审贷会决议,审批意见:拒绝,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason());
				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			}else if (EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue().equals(approveVO.getState())) {//有条件同意
				ApproveResult ar = new ApproveResult();
				ar.setLoanId(approveVO.getLoanId());
				ar.setState(approveVO.getState());
				ar.setReason(approveVO.getReason());
				if (approveVO.getCertificates1() != null && !approveVO.getCertificates1().trim().equals(""))
					ar.setCertificates1(approveVO.getCertificates1());
				if (approveVO.getCertificates2() != null && !approveVO.getCertificates2().trim().equals(""))
					ar.setCertificates2(approveVO.getCertificates2());
				if (approveVO.getCertificates3() != null && !approveVO.getCertificates3().trim().equals(""))
					ar.setCertificates3(approveVO.getCertificates3());
				ar.setRequestMoney(approveVO.getRequestMoney());
				ar.setTerm(approveVO.getTerm());
				ar.setContractMatters(approveVO.getContractMatters());
				approveResultDao.insert(ar);

				LoanVO loanVO = new LoanVO();
				loanVO.setId(approveVO.getLoanId());
				loanVO.setAuditDate(new Date());
				loanVO.setAuditMoney(approveVO.getAuditMoney());
				loanVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
				loanVO.setStatus(EnumConstants.LoanStatus.有条件同意.getValue());
				//				if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName1())){
				//					loanVO.setNaturalGuaranteeName1(approveVO.getNaturalGuaranteeName1());
				//				}
				//				if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName2())){
				//					loanVO.setNaturalGuaranteeName2(approveVO.getNaturalGuaranteeName2());
				//				}
				//				if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname1())){
				//					loanVO.setLegalGuaranteeCname1(approveVO.getLegalGuaranteeCname1());
				//				}
				//				if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname2())){
				//					loanVO.setLegalGuaranteeCname2(approveVO.getLegalGuaranteeCname2());
				//				}
				//				loanVO.setHasHouse(approveVO.getHasHouse());
				loanService.update(loanVO);

				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
				businessLog.setLoanId(loan.getId());
				String neededCertificate1 = approveVO.getCertificates1() == null ? "" : approveVO.getCertificates1();
				String neededCertificate2 = approveVO.getCertificates2() == null ? "" : approveVO.getCertificates2();
				String neededCertificate3 = approveVO.getCertificates3() == null ? "" : approveVO.getCertificates3();
				String neededCertificates = "";
				StringBuffer neededCertificates_ = new StringBuffer("");
				if (neededCertificate1 != null && !neededCertificate1.trim().equals(""))
					neededCertificates_.append("," + neededCertificate1.trim());
				if (neededCertificate2 != null && !neededCertificate2.trim().equals(""))
					neededCertificates_.append("," + neededCertificate2.trim());
				if (neededCertificate3 != null && !neededCertificate3.trim().equals(""))
					neededCertificates_.append("," + neededCertificate3.trim());
				if (neededCertificates_.length() > 0)
					neededCertificates = neededCertificates_.substring(1);

				//				businessLog.setMessage("审贷会决议,审批意见:有条件同意" +						
				//						(approveVO.getNaturalGuaranteeName1() != null&&!"".equals(approveVO.getNaturalGuaranteeName1()) ? " ,自然人担保1：" + approveVO.getNaturalGuaranteeName1() : "")+
				//						(approveVO.getNaturalGuaranteeName2() != null&&!"".equals(approveVO.getNaturalGuaranteeName2()) ? " ,自然人担保2：" + approveVO.getNaturalGuaranteeName2() : "")+
				//						(approveVO.getLegalGuaranteeCname1() != null&&!"".equals(approveVO.getLegalGuaranteeCname1()) ? " ,法人担保1：" + approveVO.getLegalGuaranteeCname1() : "")+ 
				//						(approveVO.getLegalGuaranteeCname2() != null &&!"".equals(approveVO.getLegalGuaranteeCname2()) ? " ,法人担保2：" + approveVO.getLegalGuaranteeCname2() : "")+
				//						(neededCertificates.equals("") ? "" : " ,增加附件：" + neededCertificates)
				//						+ (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));
				businessLog.setMessage("审贷会决议,审批意见:有条件同意" +	 (neededCertificates.equals("") ? "" : " ,增加附件：" + neededCertificates));

				businessLogService.insert(businessLog);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
				sysLog.setRemark("借款ID   "+loan.getId().toString());
				sysLogService.insert(sysLog);
			}
		}

		if (EnumConstants.ApproveResultState.RETURN.getValue().equals(approveVO.getState())) {//退回		
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setState(approveVO.getState());
			ar.setContractMatters(approveVO.getContractMatters());
			ar.setReason(approveVO.getReason());			
			approveResultDao.insert(ar);

			LoanVO loanVO = new LoanVO();
			loanVO.setId(approveVO.getLoanId());
			loanVO.setAuditDate(new Date());
			loanVO.setStatus(EnumConstants.LoanStatus.退回门店.getValue());

			loanService.update(loanVO);

			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
			businessLog.setLoanId(loan.getId());
			businessLog.setMessage("审贷会决议,审批意见:退回门店,退回原因：" +ar.getContractMatters());
			businessLogService.insert(businessLog);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
			sysLog.setRemark("退回门店    借款ID   "+loan.getId().toString());
			sysLogService.insert(sysLog);
		}
	}

	@Transactional
	public void extensionApprove(ApproveResultVO approveVO) {
		Extension extension = extensionService.get(approveVO.getLoanId());
		if (EnumConstants.ApproveResultState.APPROVAL.getValue().equals(approveVO.getState())) {//同意
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setReason(approveVO.getReason());
			ar.setState(approveVO.getState());
			ar.setRequestMoney(approveVO.getRequestMoney());
			ar.setTerm(approveVO.getTerm());
			ar.setContractMatters(approveVO.getContractMatters());
			approveResultDao.insert(ar);

			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(approveVO.getLoanId());
			extensionVO.setAuditDate(new Date());
			extensionVO.setAuditMoney(approveVO.getAuditMoney());
			extensionVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
			extensionVO.setStatus(EnumConstants.LoanStatus.展期合同签订.getValue());

			extensionService.update(extensionVO);

			//代码控制 2017年6月29日15:33:15  李璇
			if(extension.getId() != null){
				// 新计算规则启用参数
				SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);

				SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
				Date date=new Date();
				try {
					date = sdftime.parse(parameter.getParameterValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				Long loanId = loanExtensionService.getLoanIdByExtensionId(extension.getId());
				Loan loan = loanService.get(loanId);
				if (loan != null) {
					if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						//Add LIN for延期展期申请
						extensionService.updateLastLoanRepaymentPlanNew(extension.getId());
					} else {
						//Add LIN for延期展期申请
						extensionService.updateLastLoanRepaymentPlan(extension.getId());
					}
				} else {
				}

			}

			//在老借款的最后一个还款日之前,增加一条老借款提前扣款申请
			Date lastRepayDate = null;
			Long loanId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
			Loan loan = loanService.get(loanId);
			if(loan != null){
				lastRepayDate = loan.getEndRepayDate();
			}else{
				Extension ext = extensionService.get(loanId);
				lastRepayDate = ext.getEndRepayDate();
			}
			Date nowDate = DateUtil.getToday();
			if (nowDate.before(lastRepayDate)) { 
				specialRepaymentService.submitRepayInAdvance(loanId, true);
			}
			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("展期审贷会决议,审批意见:同意" + (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));
			businessLogService.insert(businessLog);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
			sysLog.setRemark("展期借款ID   "+extension.getId().toString());
			sysLogService.insert(sysLog);
		} else if (EnumConstants.ApproveResultState.REFUSE_TO.getValue().equals(approveVO.getState())) {//拒绝
			RejectReason rejectReason = rejectReasonService.get(approveVO.getRefuseSecondReasonId());
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setState(approveVO.getState());
			ar.setReason(approveVO.getReason());
			ar.setReason1(rejectReason.getParent().getReason());
			ar.setReason2(rejectReason.getReason());
			approveResultDao.insert(ar);


			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(approveVO.getLoanId());
			extensionVO.setAuditDate(new Date());
			extensionVO.setStatus(EnumConstants.LoanStatus.展期拒绝.getValue());
			extensionService.update(extensionVO);

			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("展期审贷会决议,审批意见:拒绝,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason());
			businessLogService.insert(businessLog);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
			sysLog.setRemark("展期借款ID   "+extension.getId().toString());
			sysLogService.insert(sysLog);
		}else if (EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue().equals(approveVO.getState())) {//有条件同意
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setState(approveVO.getState());
			ar.setReason(approveVO.getReason());
			if (approveVO.getCertificates1() != null && !approveVO.getCertificates1().trim().equals(""))
				ar.setCertificates1(approveVO.getCertificates1());
			if (approveVO.getCertificates2() != null && !approveVO.getCertificates2().trim().equals(""))
				ar.setCertificates2(approveVO.getCertificates2());
			if (approveVO.getCertificates3() != null && !approveVO.getCertificates3().trim().equals(""))
				ar.setCertificates3(approveVO.getCertificates3());
			ar.setRequestMoney(approveVO.getRequestMoney());
			ar.setTerm(approveVO.getTerm());
			ar.setContractMatters(approveVO.getContractMatters());
			approveResultDao.insert(ar);

			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(approveVO.getLoanId());
			extensionVO.setAuditDate(new Date());
			extensionVO.setAuditMoney(approveVO.getAuditMoney());
			extensionVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
			extensionVO.setStatus(EnumConstants.LoanStatus.有条件同意.getValue());
			//			if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName1())){
			//				loanVO.setNaturalGuaranteeName1(approveVO.getNaturalGuaranteeName1());
			//			}
			//			if (StringUtil.isNotBlank(approveVO.getNaturalGuaranteeName2())){
			//				loanVO.setNaturalGuaranteeName2(approveVO.getNaturalGuaranteeName2());
			//			}
			//			if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname1())){
			//				loanVO.setLegalGuaranteeCname1(approveVO.getLegalGuaranteeCname1());
			//			}
			//			if (StringUtil.isNotBlank(approveVO.getLegalGuaranteeCname2())){
			//				loanVO.setLegalGuaranteeCname2(approveVO.getLegalGuaranteeCname2());
			//			}
			//			loanVO.setHasHouse(approveVO.getHasHouse());
			extensionService.update(extensionVO);

			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
			businessLog.setLoanId(extension.getId());
			String neededCertificate1 = approveVO.getCertificates1() == null ? "" : approveVO.getCertificates1();
			String neededCertificate2 = approveVO.getCertificates2() == null ? "" : approveVO.getCertificates2();
			String neededCertificate3 = approveVO.getCertificates3() == null ? "" : approveVO.getCertificates3();
			String neededCertificates = "";
			StringBuffer neededCertificates_ = new StringBuffer("");
			if (neededCertificate1 != null && !neededCertificate1.trim().equals(""))
				neededCertificates_.append("," + neededCertificate1.trim());
			if (neededCertificate2 != null && !neededCertificate2.trim().equals(""))
				neededCertificates_.append("," + neededCertificate2.trim());
			if (neededCertificate3 != null && !neededCertificate3.trim().equals(""))
				neededCertificates_.append("," + neededCertificate3.trim());
			if (neededCertificates_.length() > 0)
				neededCertificates = neededCertificates_.substring(1);

			//			businessLog.setMessage("审贷会决议,审批意见:有条件同意" +						
			//					(approveVO.getNaturalGuaranteeName1() != null&&!"".equals(approveVO.getNaturalGuaranteeName1()) ? " ,自然人担保1：" + approveVO.getNaturalGuaranteeName1() : "")+
			//					(approveVO.getNaturalGuaranteeName2() != null&&!"".equals(approveVO.getNaturalGuaranteeName2()) ? " ,自然人担保2：" + approveVO.getNaturalGuaranteeName2() : "")+
			//					(approveVO.getLegalGuaranteeCname1() != null&&!"".equals(approveVO.getLegalGuaranteeCname1()) ? " ,法人担保1：" + approveVO.getLegalGuaranteeCname1() : "")+ 
			//					(approveVO.getLegalGuaranteeCname2() != null &&!"".equals(approveVO.getLegalGuaranteeCname2()) ? " ,法人担保2：" + approveVO.getLegalGuaranteeCname2() : "")+
			//					(neededCertificates.equals("") ? "" : " ,增加附件：" + neededCertificates)
			//					+ (approveVO.getContractMatters().equals("") ? "" : " ,签约事项：" + approveVO.getContractMatters()));
			businessLog.setMessage("审贷会决议,审批意见:有条件同意" +	 (neededCertificates.equals("") ? "" : " ,增加附件：" + neededCertificates));

			businessLogService.insert(businessLog);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
			sysLog.setRemark("借款ID   "+extension.getId().toString());
			sysLogService.insert(sysLog);
		}

		if (EnumConstants.ApproveResultState.RETURN.getValue().equals(approveVO.getState())) {//退回		
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setState(approveVO.getState());
			ar.setContractMatters(approveVO.getContractMatters());
			ar.setReason(approveVO.getReason());			
			approveResultDao.insert(ar);


			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(approveVO.getLoanId());
			extensionVO.setAuditDate(new Date());
			extensionVO.setStatus(EnumConstants.LoanStatus.展期退回门店.getValue());
			extensionService.update(extensionVO);


			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("展期审贷会决议,审批意见:退回门店,退回原因：" +ar.getContractMatters());
			businessLogService.insert(businessLog);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPROVE_LOAN.getValue());
			sysLog.setRemark("退回门店   展期 借款ID   "+extension.getId().toString());
			sysLogService.insert(sysLog);
		}
	}

	@Override
	@Transactional
	public void createdExtensionContract(GenerateContractVO generateContractVO) {
		// 开户人银行帐号信息已经存在则不保存，否则将客户账号信息保存到bank_account表中
		Long extensionId = Long.valueOf(generateContractVO.getLoanId());
		Extension extension = extensionService.get(extensionId);
		if (extension != null) {
			Long personId = extension.getPersonId();
			// 已经生成过合同
			if (StringUtils.isNotEmpty(extension.getContractNo())) {
				// 删除还款计划
				repaymentPlanService.deleteRepaymentPlanByLoanId(extensionId);
				// 删除合同信息
				contractService.deleteContractByLoanId(extensionId);
				// 删除银行账户信息和客户银行关联
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(personId);
				personBankVO.setLoanId(extensionId);
				List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
				if (CollectionUtil.isNotEmpty(personBankList)) {
					for (PersonBank personBank : personBankList) {
						personBankService.deletePersonBank(personBank.getId());
						Long bankAccountId = personBank.getBankAccountId();
						bankAccountService.deleteById(bankAccountId);

					}
				}
			}
			Person person = personService.get(personId);
			// 借款人
			BankAccount personBankAccount = new BankAccount();
			personBankAccount.setAccount(generateContractVO.getBankAccount());
			personBankAccount.setBranchName(generateContractVO.getBankBranch());
			Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
			personBankAccount.setBank(bank);
			personBankAccount.setBankName(bank.getBankName());
			personBankAccount.setStatus(EnumConstants.BankAccountStatus.ENABLED.getValue());
			personBankAccount.setAccountAuthType(generateContractVO.getAccountAuthType());
			personBankAccount = bankAccountService.insert(personBankAccount);

			// 插入客户银行关联
			PersonBank personBank = new PersonBank();
			personBank.setBankAccountId(personBankAccount.getId());
			personBank.setPersonId(personId);
			personBank.setLoanId(extensionId);
			personBankService.insertPersonBank(personBank);
			// 更新贷款信息还款银行和放款银行
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(extensionId);
			extensionVO.setContractSrc(generateContractVO.getContractSrc());
			extensionVO.setGrantAccountId(bank.getId());
			extensionVO.setRepayAccountId(bank.getId());
			extensionVO.setTime(extension.getAuditTime());
			// 签约日期
			extensionVO.setSignDate(new Date());
			// 合同金额
			BigDecimal pactMoney = extension.getAuditMoney();
			extensionVO.setPactMoney(pactMoney);

			//			//原借款id
			//			Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			//上一笔借款id
			Long preExtensionId = loanExtensionService.getPreExtensionId(extensionId, extension.getExtensionTime());
			if(extension.getExtensionTime().compareTo(1)>0){
				Extension oldExtesion = extensionService.get(preExtensionId);
				extensionVO.setContractNo(oldExtesion.getContractNo());
			}else{
				Loan oldLoan = loanService.get(preExtensionId);
				extensionVO.setContractNo(oldLoan.getContractNo());
			}
			Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			Loan loan=loanService.get(oldLoanId);
			SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);

			SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			RepaymentPlanVO repaymentPlanVO=null;
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				// 生成还款信息
				repaymentPlanVO = getExtensionRepaymentPlanFirstTermNew(extension);
			}else{
				repaymentPlanVO = getExtensionRepaymentPlanFirstTerm(extension);
			}
			//第1期
			int curNum = 1;
			Date repayDay = repaymentPlanVO.getRepayDay();
			// 还款金额
			BigDecimal repayAmount = repaymentPlanVO.getRepayAmount();
			BigDecimal interestAmt = repaymentPlanVO.getInterestAmt();
			BigDecimal deficit = repaymentPlanVO.getDeficit();
			Date startRepayDate = repayDay;
			Date endRepayDate =  DateUtil.getNowDateAfter(extension.getAuditTime()-1,startRepayDate);
			// 咨询费
			extensionVO.setConsult(repaymentPlanVO.getReferRate());
			// 评估费
			extensionVO.setAssessment(repaymentPlanVO.getEvalRate());
			// 乙方管理费
			extensionVO.setbManage(BigDecimal.ZERO);
			// 丙方管理费
			extensionVO.setcManage(repaymentPlanVO.getManagePart1Fee());
			// 风险基金
			BigDecimal risk = repaymentPlanVO.getRisk();
			BigDecimal totalInterestAmt = BigDecimal.ZERO;
			totalInterestAmt = totalInterestAmt.add(repaymentPlanVO.getInterestAmt().multiply(new BigDecimal("3")));

			repaymentPlanService.insert(repaymentPlanVO);
			//第2期
			curNum = 2;
			repaymentPlanVO.setCurNum(curNum);
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			repaymentPlanService.insert(repaymentPlanVO);
			//第3期
			repaymentPlanVO.setCurNum(extension.getAuditTime());
			// 还款金额
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				// 生成还款信息
				repayAmount = pactMoney;
				repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
				repaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
				deficit = repayAmount;
			}else{
				repayAmount = interestAmt.add(pactMoney);
			}

			repaymentPlanVO.setRepayAmount(repayAmount);
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			// 还款本金
			repaymentPlanVO.setPrincipalAmt(pactMoney);
			repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
			repaymentPlanVO.setRisk(BigDecimal.ZERO);
			// 咨询费
			repaymentPlanVO.setReferRate(BigDecimal.ZERO);
			// 评估费
			repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
			// 丙方管理费
			repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			}else{
				deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
			}
			repaymentPlanVO.setDeficit(deficit);
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);

			repaymentPlanService.insert(repaymentPlanVO);

			//营业网点地址
			Long salesDeptId = extension.getSalesDeptId();
			BaseArea baseArea = baseAreaService.get(salesDeptId);
			WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());

			extensionVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
			extensionVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));

			extensionVO.setContractCreatedTime(new Date());

			//还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(extensionVO.getEndRepayDate());
			extensionVO.setReturnDate(calendar.get(Calendar.DATE));
			extensionVO.setResidualPactMoney(pactMoney);
			extensionVO.setResidualTime(extensionVO.getTime().intValue());

			//TODO
			carExtensionService.createCarLoanContract(extension,extensionVO, person,  personBankAccount, totalInterestAmt, workPlaceInfo.getSite());
			carExtensionService.createCarPersonLoanContract(extension, extensionVO,person, risk,  workPlaceInfo.getSite());
			carExtensionService.createCarRepaymentFundContract(extension, extensionVO, person,  workPlaceInfo.getSite());
			carExtensionService.createCarEntrustContract(extension,extensionVO, person,  personBankAccount);


			// 更新贷款信息表
			extensionService.update(extensionVO);




			// 插入日志
			BusinessLog businessLog = new BusinessLog();
			businessLog.setLoanId(extensionId);
			businessLog.setMessage("展期合同生成");
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue());
			businessLogService.insert(businessLog);

			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CREATE_CONTRACT.getValue());
			sysLog.setRemark("展期借款ID   "+extensionId.toString());
			sysLogService.insert(sysLog);
		}

	}

	public RepaymentPlanVO getExtensionRepaymentPlanFirstTerm(Extension extension){
		//上一笔借款id
		Long preExtensionId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
		Date loanEndRepayDate;
		if(extension.getExtensionTime().compareTo(1)>0){
			Extension oldExtesion = extensionService.get(preExtensionId);
			loanEndRepayDate = oldExtesion.getEndRepayDate();
		}else{
			Loan oldLoan = loanService.get(preExtensionId);
			loanEndRepayDate = oldLoan.getEndRepayDate();
		}
		Date startRepayDate = DateUtil.getNowDateAfter(1,loanEndRepayDate);
		Date repayDay = startRepayDate;
		// 合同金额
		BigDecimal pactMoney = extension.getAuditMoney();
		Product product = productService.get(extension.getProductId());

		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setProductId(extension.getProductId());
		productDetailVO.setTerm(extension.getAuditTime());
		productDetailVO.setCarProductType(extension.getLoanType().intValue());

		ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);

		// 生成还款信息
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(extension.getId());
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());

		repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
		repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));

		BigDecimal risk = BigDecimal.ZERO;


		// 费率
		BigDecimal carRate = productDetail.getSumRate();
		BigDecimal carShortRistRate = product.getRiskRate();

		//第1期
		int curNum = 1;
		repaymentPlanVO.setCurNum(curNum);
		// 还款金额
		BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		repaymentPlanVO.setRepayAmount(repayAmount);
		// 风险基金
		risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		// 利息
		//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3),2)
		BigDecimal interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		// 服务费
		BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);
		//		BigDecimal totalInterestAmt = BigDecimal.ZERO;
		//		totalInterestAmt = totalInterestAmt.add(interestAmt.multiply(new BigDecimal("3")));

		repaymentPlanVO.setInterestAmt(interestAmt);
		repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
		// 咨询费
		BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setReferRate(referRate);
		repaymentPlanVO.setCurRemainingReferRate(referRate);

		// 评估费
		BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setEvalRate(evalRate);
		repaymentPlanVO.setCurRemainingEvalRate(evalRate);

		// 丙方管理费
		BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
		repaymentPlanVO.setManagePart1Fee(managePart1Fee);
		repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
		// 乙方管理费
		repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);

		BigDecimal oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
		repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
		BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
				.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
				.add(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
		return repaymentPlanVO;
	}
	public RepaymentPlanVO getExtensionRepaymentPlanFirstTermNew(Extension extension){
		//上一笔借款id
		Long preExtensionId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
		Date loanEndRepayDate;
		if(extension.getExtensionTime().compareTo(1)>0){
			Extension oldExtesion = extensionService.get(preExtensionId);
			loanEndRepayDate = oldExtesion.getEndRepayDate();
		}else{
			Loan oldLoan = loanService.get(preExtensionId);
			loanEndRepayDate = oldLoan.getEndRepayDate();
		}
		Date startRepayDate = DateUtil.getNowDateAfter(1,loanEndRepayDate);
		Date repayDay = startRepayDate;
		// 合同金额
		BigDecimal pactMoney = extension.getAuditMoney();
		Product product = productService.get(extension.getProductId());

		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setProductId(extension.getProductId());
		productDetailVO.setTerm(extension.getAuditTime());
		productDetailVO.setCarProductType(extension.getLoanType().intValue());

		ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);

		// 生成还款信息
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(extension.getId());
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());

		repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
		repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));

		BigDecimal risk = BigDecimal.ZERO;;

		SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_SHORT_CIRCULATE_SUM_RATE);

		// 费率
		BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());	
		// 费率
		//BigDecimal carRate = productDetail.getSumRate();
		BigDecimal carShortRistRate = product.getRiskRate();

		//第1期
		int curNum = 1;
		repaymentPlanVO.setCurNum(curNum);
		// 还款金额
		BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		repaymentPlanVO.setRepayAmount(repayAmount);
		// 风险基金
		risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		// 利息
		//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
		BigDecimal interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		// 服务费
		BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);
		//		BigDecimal totalInterestAmt = BigDecimal.ZERO;
		//		totalInterestAmt = totalInterestAmt.add(interestAmt.multiply(new BigDecimal("3")));

		repaymentPlanVO.setInterestAmt(interestAmt);
		repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
		// 咨询费
		BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setReferRate(referRate);
		repaymentPlanVO.setCurRemainingReferRate(referRate);

		// 评估费
		BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setEvalRate(evalRate);
		repaymentPlanVO.setCurRemainingEvalRate(evalRate);

		// 丙方管理费
		BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
		repaymentPlanVO.setManagePart1Fee(managePart1Fee);
		repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
		// 乙方管理费
		repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);

		//BigDecimal oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
		BigDecimal oneTimeRepaymentAmount = pactMoney;
		repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
		BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
				.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
				.add(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
		return repaymentPlanVO;
	}
	private void checkMemberType(ApproveResultVO approveVO,ApproveResult ar ){
		if(approveVO.getAuditMemberType() != null){
			ar.setAuditMemberType(approveVO.getAuditMemberType());
		}

	}
	private void checkMemberType(ApproveResultVO approveVO,LoanVO loanVO ){
		if(approveVO.getAuditMemberType() != null){
			loanVO.setAuditMemberType(approveVO.getAuditMemberType());
		}

	}

	private void updateGuaranteeFlag(ApproveResultVO approveVO) {
		String[] guaIds= approveVO.getGuaIds().split(",");
		for(String guaId:guaIds){
			GuaranteeVO guaranteeVO = new GuaranteeVO();

			guaranteeVO.setId(Long.valueOf(guaId));
			guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			guaranteeService.update(guaranteeVO);
		}
		//		
	}
	public Date  Dateof16(Date date) {
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date); 
		calendar.set(Calendar.DATE,16);
		return calendar.getTime();
	}

	@Override
	@Transactional
	public void createdContractNew(GenerateContractVO generateContractVO) {
		// 开户人银行帐号信息已经存在则不保存，否则将客户账号信息保存到bank_account表中
		Long loanId = Long.valueOf(generateContractVO.getLoanId());
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			Long personId = loan.getPersonId();
			// 已经生成过合同
			if (StringUtils.isNotEmpty(loan.getContractNo())) {
				// 删除还款计划
				repaymentPlanService.deleteRepaymentPlanByLoanId(loanId);
				// 删除合同信息
				contractService.deleteContractByLoanId(loanId);
				// 删除银行账户信息和客户银行关联
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(personId);
				personBankVO.setLoanId(loanId);
				List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
				if (CollectionUtil.isNotEmpty(personBankList)) {
					for (PersonBank personBank : personBankList) {	
						Long bankAccountId = personBank.getBankAccountId();
						bankAccountService.deleteById(bankAccountId);
						personBankService.deletePersonBank(personBank.getId());
					}
				}
			}

			Person person = personService.get(personId);
			// 借款人
			BankAccount personBankAccount = new BankAccount();
			personBankAccount.setAccount(generateContractVO.getBankAccount());
			personBankAccount.setBranchName(generateContractVO.getBankBranch());
			Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
			personBankAccount.setBank(bank);
			personBankAccount.setBankName(bank.getBankName());
			personBankAccount.setStatus(EnumConstants.BankAccountStatus.ENABLED.getValue());
			personBankAccount.setAccountAuthType(generateContractVO.getAccountAuthType());
			personBankAccount = bankAccountService.insert(personBankAccount);
			// 插入客户银行关联
			PersonBank personBank = new PersonBank();
			personBank.setBankAccountId(personBankAccount.getId());
			personBank.setPersonId(personId);
			personBank.setLoanId(loanId);
			personBankService.insertPersonBank(personBank);
			// 更新贷款信息还款银行和放款银行
			Date contractCreatedDate = generateContractVO.getContractCreatedDate();
			if(contractCreatedDate == null){
				contractCreatedDate = new Date();//小企业贷没有合同日期选择.
			}
			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setContractSrc(generateContractVO.getContractSrc());
			loanVO.setGrantAccountId(bank.getId());
			loanVO.setRepayAccountId(bank.getId());
			// 签约日期
			loanVO.setSignDate(contractCreatedDate); 

			String contractNo = createdContractNo(loanId,contractCreatedDate);
			loanVO.setContractNo(contractNo);
			// 担保人
			GuaranteeVO guaranteeVO = new GuaranteeVO();
			guaranteeVO.setLoan(loan);
			guaranteeVO.setPersonId(personId);
			guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
			BankAccount guarantBankAccount = null;
			if (CollectionUtil.isNotEmpty(guaranteeList)) {
				for (Guarantee g : guaranteeList) {
					BankAccountVO guaranteeBankAccountVO = new BankAccountVO();
					Bank guaranteeBank;
					// 自然人1
					if (g.getName().equals(generateContractVO.getNaturalGuaranteeName1())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount1());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch1());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank1()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//自然人2
					} else if (g.getName().equals(generateContractVO.getNaturalGuaranteeName2())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount2());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch2());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank2()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//法人1
					} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName1())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount1());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch1());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank1()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);

						//法人2
					} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName2())) {
						guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount2());
						guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch2());
						guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank2()));
						guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
						guaranteeBankAccountVO.setBank(guaranteeBank);
						// 查看银行账户是否存在,如不存在则插入
						guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
						if (guarantBankAccount == null) {
							guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
							guarantBankAccount = bankAccountService.insert(guarantBankAccount);
						}
						// 更新担保人信息
						guaranteeVO.setId(g.getId());
						Loan guaranteeLoan = new Loan();
						guaranteeLoan.setId(loanId);
						guaranteeVO.setLoan(guaranteeLoan);
						guaranteeVO.setPersonId(personId);
						guaranteeVO.setBankAccountId(guarantBankAccount.getId());
						guaranteeService.update(guaranteeVO);
					}
				}
			}

			Product product = productService.get(loan.getProductId());

			ProductDetailVO productDetailVO = new ProductDetailVO();
			productDetailVO.setProductId(product.getId());
			productDetailVO.setTerm(loan.getAuditTime());
			if (product.getProductType().compareTo(2) == 0) {
				productDetailVO.setCarProductType(loan.getLoanType().intValue());
			}

			ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);

			// 生成还款信息
			RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
			repaymentPlanVO.setLoanId(loan.getId());
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());

			// 期数
			Integer auditTime = loan.getAuditTime();

			// 平息利率
			BigDecimal rate = product.getRate();
			// 管理费率
			BigDecimal manageFeeRate = product.getManageFeeRate();

			//非固定还款日的
			// 还款开始日期
			Date startRepayDate = new Date();
			Date repayDay = new Date();

			//查看是否有还款日规则
			LoanRuleVO loanRuleVo = new LoanRuleVO();
			loanRuleVo.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
			loanRuleVo.setProductType(product.getProductType());
			if (productDetail.getCarProductType() != null) {
				loanRuleVo.setProductSubtype(productDetail.getCarProductType());
			}
			loanRuleVo.setContractSrc(generateContractVO.getContractSrc());
			List<LoanRule> loanRepayDateRuleList = repayDateRuleService.findRuleByParams(loanRuleVo);
			if (CollectionUtil.isNullOrEmpty(loanRepayDateRuleList)) {
				throw new BusinessException("没有还款日规则");
			}
			if (loanRepayDateRuleList.size() > 1) {
				throw new BusinessException("还款日规则大于1条");
			}
			LoanRule loanRepayDate = loanRepayDateRuleList.get(0);
			if (loanRepayDate.getRepaydateRule().equals(EnumConstants.RepayDateRuleType.FIXED_DATE.getValue())) {
				//固定还款日规则的
				startRepayDate = getRepayRuleDate(contractCreatedDate);
				repayDay = getRepayRuleDate(contractCreatedDate);
				// 当前月天数
				int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
				Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
				if ((nowDay >= 13 && nowDay <= 15) || (dayOfMonth - nowDay) < 3) {
					//是否有特殊签单规则
					loanRuleVo.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());
					loanRuleVo.setContractSrc(null);
					List<LoanRule> loanSignRuleList = signLoanRuleService.findSignRuleByLoanRuleVo(loanRuleVo);
					if (CollectionUtil.isNotEmpty(loanSignRuleList)) {
						if (loanSignRuleList.size() > 1) {
							throw new BusinessException("特殊签单规则大于1条");
						}
						startRepayDate = getSignRuleDate(startRepayDate);
						repayDay = getSignRuleDate(repayDay);
						//add by LIN 特殊签单的话,在主表写标记
						loanVO.setFlag(EnumConstants.LoanRuleType.SIGN_RULE.getValue());
					}
				}
			} else {
				// 非固定还款日规则的
				startRepayDate = getNotRepayRuleDate(contractCreatedDate);
				repayDay = getNotRepayRuleDate(contractCreatedDate);
			}
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			Calendar c = Calendar.getInstance();
			c.setTime(repayDay);
			c.add(Calendar.MONTH, auditTime - 1);
			DateUtil.setHMSM(c, 0, 0, 0, 0);
			Date endRepayDate = c.getTime();
			// 合同金额
			BigDecimal pactMoney = loan.getAuditMoney();



			// 车贷
			BigDecimal preparatoryAmount = BigDecimal.ZERO;
			BigDecimal risk = BigDecimal.ZERO;;

			BigDecimal totalInterestAmt = BigDecimal.ZERO;

			//车贷计算器-等额本息新模型
			SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);

			SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 

			BigDecimal consultingFeeRate = product.getConsultingFeeRate();
			BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();

			//前期风险金
			BigDecimal raskAmount = BigDecimal.ZERO;
			if (auditTime.compareTo(carLoanModeByMonth) > 0) {
				//车贷 老产品 流通类6期特殊处理规则
				//Date1.after(Date2),当Date1大于Date2时
				if(loan.getCreatedTime().after(date)
						&&("002".equals(product.getProductCode()))
						&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
						&&auditTime.compareTo(carLoanModeByMonthSix) <= 0){

					Map<String,Object> result = carLoanCirculateSix(auditTime, endRepayDate, product, repaymentPlanVO, pactMoney, risk, raskAmount, loanVO, preparatoryAmount, contractCreatedDate, repayDay, totalInterestAmt);
					//为后面准备数据
					endRepayDate = (Date) result.get("endRepayDate");
					repayDay = (Date) result.get("repayDay");
					preparatoryAmount = (BigDecimal) result.get("preparatoryAmount");
					risk = (BigDecimal) result.get("risk");
					raskAmount = (BigDecimal) result.get("raskAmount");
					totalInterestAmt = (BigDecimal) result.get("totalInterestAmt");

				} else {
					//Date1.after(Date2),当Date1大于Date2时
					if(loan.getCreatedTime().after(date)){
						// 咨询费费率
						SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_CON_RATE_NEW);
						product.setConsultingFeeRate(new BigDecimal (parameterRate.getParameterValue()));
						// 评估费费率
						parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_ASS_RATE_NEW);
						product.setAssessmentFeeRate(new BigDecimal (parameterRate.getParameterValue()));
						// 综合费率
						parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_SUM_RATE_NEW);
						productDetail.setSumRate(new BigDecimal	(parameterRate.getParameterValue()));

						consultingFeeRate = product.getConsultingFeeRate();
						assessmentFeeRate = product.getAssessmentFeeRate();
					}

					if (product.getId().compareTo(2L) != 0) {
						BigDecimal riskRate = product.getRiskRate();
						risk = (pactMoney.multiply(riskRate)).divide(new BigDecimal(12), 8,
								BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(loan.getAuditTime()));
						pactMoney = pactMoney.add(risk);
					}
					BigDecimal auditMoney = loan.getAuditMoney();
					loanVO.setTime(auditTime.longValue());
					// 中长期车贷
					// 第1期
					// 当前期数
					int curNum = 1;
					repaymentPlanVO.setCurNum(curNum);
					//前期费用=咨询费+评估费 + 风险基金
					// 咨询费
					BigDecimal referRate = auditMoney.multiply(consultingFeeRate);
					loanVO.setConsult(referRate);
					// 评估费
					BigDecimal evalRate = auditMoney.multiply(assessmentFeeRate);
					loanVO.setAssessment(evalRate);
					//风险基金
					BigDecimal riskRate = product.getRiskRate();
					risk = (auditMoney.multiply(riskRate)).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(loanVO.getTime()));
					loanVO.setRisk(risk);
					preparatoryAmount = referRate.add(evalRate).add(risk);
					// 月利率
					loanVO.setMonthRate(newCarLoanMonthRate);
					BigDecimal averageCapital = new BigDecimal(String.valueOf(PMT(newCarLoanMonthRate.doubleValue(), auditTime.doubleValue(), pactMoney.doubleValue()))).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setAverageCapital(averageCapital);
					// 当期归还利息
					BigDecimal interestAmt = pactMoney.multiply(newCarLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					// 当期归还本金
					BigDecimal principalAmt = averageCapital.subtract(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setPrincipalAmt(principalAmt);
					repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
					// 第1到第n-1期当期归还本金
					BigDecimal totalPrincipalAmt = principalAmt;
					// 剩余本金
					BigDecimal remainingPrincipal = pactMoney.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
					// 违约金比例
					BigDecimal carPenaltyRate = getCarPenaltyRate(auditTime, curNum - 1,product.getId());
					// 提前还款违约金
					BigDecimal penalty = remainingPrincipal.multiply(carPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setPenalty(penalty);
					// 每月还款金额
					BigDecimal repayAmount = auditMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP).add(auditMoney.multiply(productDetail.getSumRate()))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRepayAmount(repayAmount);
					// 当期一次性还款金额
					BigDecimal oneTimeRepaymentAmount = penalty.add(remainingPrincipal).add(repayAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					// 丙方管理费 = 合同金额*4%/12
					BigDecimal managePart1Fee = pactMoney.multiply(new BigDecimal(String.valueOf(0.04))).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);

					repaymentPlanVO.setManagePart1Fee(managePart1Fee);
					repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
					loanVO.setcManage(BigDecimal.ZERO);
					// 乙方管理费 = 每月还款金额 - 应还本息 - 丙方管理费
					BigDecimal managePart0Fee = repayAmount.subtract(averageCapital).subtract(managePart1Fee).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setCurRemainingManagePart0Fee(managePart0Fee);
					repaymentPlanVO.setManagePart0Fee(managePart0Fee);
					loanVO.setbManage(BigDecimal.ZERO);
					BigDecimal deficit = repaymentPlanVO.getCurRemainingInterestAmt()
							.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingPrincipal());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
					// 第2期到第n-1期
					for (curNum = 2; curNum < loanVO.getTime(); curNum++) {
						repaymentPlanVO.setCurNum(curNum);
						// 当期归还利息
						interestAmt = remainingPrincipal.multiply(newCarLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setInterestAmt(interestAmt);
						repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
						totalInterestAmt = totalInterestAmt.add(interestAmt);
						// 当期归还本金
						principalAmt = averageCapital.subtract(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setPrincipalAmt(principalAmt);
						repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
						totalPrincipalAmt = totalPrincipalAmt.add(principalAmt);
						// 剩余本金
						remainingPrincipal = remainingPrincipal.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
						// 违约金比例
						carPenaltyRate = getCarPenaltyRate(auditTime, curNum - 1,product.getId());
						// 提前还款违约金
						penalty = remainingPrincipal.multiply(carPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setPenalty(penalty);
						// 每月还款金额
						repayAmount = auditMoney.divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP).add(auditMoney.multiply(productDetail.getSumRate()))
								.setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setRepayAmount(repayAmount);
						// 当期一次性还款金额
						oneTimeRepaymentAmount = penalty.add(remainingPrincipal).add(repayAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
						repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
						repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
						repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
						deficit = repaymentPlanVO.getCurRemainingInterestAmt()
								.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
								.add(repaymentPlanVO.getCurRemainingPrincipal());
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
						repaymentPlanService.insert(repaymentPlanVO);
					}
					// 最后1期
					// 当前期数
					repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
					// 当期归还本金
					principalAmt = pactMoney.subtract(totalPrincipalAmt);
					repaymentPlanVO.setPrincipalAmt(principalAmt);
					repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
					// 当期归还利息
					interestAmt = averageCapital.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					// 当期一次性还款金额
					oneTimeRepaymentAmount = repayAmount;
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					repayDay = DateUtil.getNowDateAfter(1, repayDay);
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					deficit = repaymentPlanVO.getCurRemainingInterestAmt()
							.add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingPrincipal());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					//剩余本金
					repaymentPlanVO.setRemainingPrincipal(BigDecimal.ZERO);
					//提前还款违约金
					repaymentPlanVO.setPenalty(BigDecimal.ZERO);
					repaymentPlanService.insert(repaymentPlanVO);

				}



			} else {
				// 短期车贷
				// 当前月天数
				if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){

					int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
					Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
					if (nowDay == 1 || nowDay == 16) {
						loanVO.setTime(auditTime.longValue());
					} else {
						loanVO.setTime(4L);
						// 还款结束日期
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(repayDay);
						calendar.add(Calendar.MONTH, loanVO.getTime().intValue() - 1);
						DateUtil.setHMSM(calendar, 0, 0, 0, 0);
						endRepayDate = calendar.getTime();
					}
					//当日到首期付款剩余天数
					int residueDays = dayOfMonth - nowDay + 1;

					if (nowDay > 15) {
						residueDays = dayOfMonth - nowDay + 1 + 15;
					}
					SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_SHORT_CIR_SUM_RATE_NEW);

					// 费率
					BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());
					//前期费用
					//D10-D9
					BigDecimal a = carRate.subtract(carLoanMonthRate);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
					BigDecimal b = a.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal d = b.multiply(new BigDecimal(residueDays));

					BigDecimal carShortRistRate = product.getRiskRate();
					//					// 还款日期为下月1日
					//					repayDay = DateUtil.getNextMonthFirstDay();
					// 下半月
					//					if (nowDay.compareTo(modeByDay) > 0) {
					//						// 还款日期为下月16日
					//						repayDay = DateUtil.getNextMonthSixteenDay();
					//					}
					//第1期
					int curNum = 1;
					repaymentPlanVO.setCurNum(curNum);
					//利率

					// 还款金额
					//=$D$7*$D$10
					BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

					repaymentPlanVO.setRepayAmount(repayAmount);
					// 风险基金
					risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRisk(risk);
					repaymentPlanVO.setCurRemainingRisk(risk);
					//前期风险金
					raskAmount = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 12, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(residueDays)).setScale(2, BigDecimal.ROUND_HALF_UP);
					//总风险金
					loanVO.setRisk(raskAmount);
					//D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3)
					BigDecimal e = carLoanMonthRate.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(residueDays));
					// 前期利息
					//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
					BigDecimal  preparatoryInterestAmt = pactMoney.multiply(e).setScale(2, BigDecimal.ROUND_HALF_UP);
					//ROUND(D7*A5,2)+D13
					BigDecimal f=pactMoney.multiply(carLoanCounterFeeNew).setScale(2, BigDecimal.ROUND_HALF_UP).add(preparatoryInterestAmt);
					preparatoryAmount = pactMoney.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP).add(f);	
					//前期服务费 =D12-ROUND(D14,2)-D13
					BigDecimal preparatoryServeRate = preparatoryAmount.subtract(raskAmount).subtract(preparatoryInterestAmt);
					loanVO.setGrantMoney(pactMoney.subtract(preparatoryAmount));
					//前期咨询费
					BigDecimal preparatoryReferRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setConsult(preparatoryReferRate);
					//前期评估费
					BigDecimal preparatoryEvalRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setAssessment(preparatoryEvalRate);
					//前期丙方管理费
					BigDecimal preparatoryManagePart1Fee = preparatoryServeRate.subtract(preparatoryReferRate).subtract(preparatoryEvalRate);
					loanVO.setcManage(preparatoryManagePart1Fee);
					//前期乙方管理费
					loanVO.setbManage(BigDecimal.ZERO);
					//To be delete
					System.out.println("e="+e);
					System.out.println("e="+dayOfMonth);
					System.out.println("e="+residueDays);


					// 利息

					BigDecimal  interestAmt=new BigDecimal(0);
					BigDecimal interest=new  BigDecimal(0);
					if ( nowDay> 15){
						//=ROUND(I7*I9/(G21-DATE(YEAR(I3),MONTH(I3),16))*(G21-I3),2);
						//(G21-DATE(YEAR(I3),MONTH(I3),16))
						int g=(int) DateUtil.getDiffDay(Dateof16(contractCreatedDate),repayDay);
						//(G21-I3)
						int h=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						interest = interestAmt.divide(new BigDecimal(g),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(h)).setScale(2, BigDecimal.ROUND_HALF_UP);
						loanVO.setProphaseInterest(interest);
					}else{
						//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
						//=ROUND($D$7*$D$9,2)
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						// DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
						Calendar calendar = new GregorianCalendar(); 
						calendar.setTime(contractCreatedDate); //放入你的日期 
						int m=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						//(B21-D3);
						int n=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
						interest = interestAmt.divide(new BigDecimal(m),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(n)).setScale(2, BigDecimal.ROUND_HALF_UP);
						loanVO.setProphaseInterest(interest);
					}
					totalInterestAmt = interest;

					// 服务费
					BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					// 咨询费
					BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setReferRate(referRate);
					repaymentPlanVO.setCurRemainingReferRate(referRate);
					// 评估费
					BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setEvalRate(evalRate);
					repaymentPlanVO.setCurRemainingEvalRate(evalRate);
					// 丙方管理费
					BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
					repaymentPlanVO.setManagePart1Fee(managePart1Fee);
					repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
					// 乙方管理费
					repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					loanVO.setbManage(BigDecimal.ZERO);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}

					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					// 当期一次性还款金额
					BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
					//当为1号或者16号时，到下次还款正好满一个月，变成首期一次性还款无利息
					BigDecimal penalty = BigDecimal.ZERO;
					if (nowDay.compareTo(16) == 0 || nowDay.compareTo(1) == 0) {

						oneTimeRepaymentAmount = pactMoney;
					} else {
						//D7*D10+D7*A5-D12+D7
						oneTimeRepaymentAmount = pactMoney.multiply(carRate).add(pactMoney.multiply(carLoanCounterFeeNew)) .subtract(preparatoryAmount).add(pactMoney);
					}
					repaymentPlanVO.setPenalty(penalty);
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
							.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingReferRate());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
					//首月无违约金，次月起1.5%
					boolean firstRepayment = true;
					if(oneTimeRepaymentAmount == pactMoney){
						firstRepayment = false;
					}

					for (curNum = 2; curNum < loanVO.getTime(); curNum++) {
						if(firstRepayment){
							penalty = BigDecimal.ZERO;
							repaymentPlanVO.setOneTimeRepaymentAmount(pactMoney);
							firstRepayment = false;
						} else {
							//=IF(A30="",'3期模型'!$D$7,'3期模型'!$D$7*(1+1.5%))
							// 提前还款违约金
							penalty = pactMoney.multiply(carShortPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
							// 当期一次性还款金额
							oneTimeRepaymentAmount = pactMoney.add(penalty);
							repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
						}
						repaymentPlanVO.setPenalty(penalty);
						repaymentPlanVO.setCurNum(curNum);
						// 还款金额
						repayAmount = pactMoney.multiply(carRate);
						repaymentPlanVO.setRepayAmount(repayAmount);

						if (curNum > 1) {
							repayDay = DateUtil.getNowDateAfter(1, repayDay);
						}
						repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
						repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
						// 利息
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						totalInterestAmt = totalInterestAmt.add(interestAmt);
						repaymentPlanVO.setInterestAmt(interestAmt);
						repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
						deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
								.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
								.add(repaymentPlanVO.getCurRemainingReferRate());
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
						repaymentPlanService.insert(repaymentPlanVO);
					}
					// 最后一期
					repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
					// 提前还款违约金
					repaymentPlanVO.setPenalty(BigDecimal.ZERO);
					// 当期一次性还款金额
					repaymentPlanVO.setOneTimeRepaymentAmount(pactMoney);
					// 还款金额
					repayAmount = pactMoney;
					repaymentPlanVO.setRepayAmount(repayAmount);

					repaymentPlanVO.setInterestAmt(null);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					// 还款本金
					repaymentPlanVO.setPrincipalAmt(pactMoney);
					repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					repaymentPlanVO.setRisk(BigDecimal.ZERO);
					// 咨询费
					repaymentPlanVO.setReferRate(BigDecimal.ZERO);
					// 评估费
					repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
					// 丙方管理费
					repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
					//deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
					deficit = repaymentPlanVO.getRepayAmount();
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
					repaymentPlanService.insert(repaymentPlanVO);
				}else{
					int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
					Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
					if (nowDay == 1 || nowDay == 16) {
						loanVO.setTime(auditTime.longValue());
					} else {
						loanVO.setTime(4L);
						// 还款结束日期
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(repayDay);
						calendar.add(Calendar.MONTH, loanVO.getTime().intValue() - 1);
						DateUtil.setHMSM(calendar, 0, 0, 0, 0);
						endRepayDate = calendar.getTime();
					}
					//当日到首期付款剩余天数
					int residueDays = dayOfMonth - nowDay + 1;

					if (nowDay > 15) {
						residueDays = dayOfMonth - nowDay + 1 + 15;
					}

					// 费率
					BigDecimal carRate = productDetail.getSumRate();

					//前期费用
					//D10-D9
					BigDecimal a = carRate.subtract(carLoanMonthRate);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
					BigDecimal b = a.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP);
					//(D10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal d = b.multiply(new BigDecimal(residueDays));
					preparatoryAmount = pactMoney.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal carShortRistRate = product.getRiskRate();
					//					// 还款日期为下月1日
					//					repayDay = DateUtil.getNextMonthFirstDay();
					// 下半月
					//					if (nowDay.compareTo(modeByDay) > 0) {
					//						// 还款日期为下月16日
					//						repayDay = DateUtil.getNextMonthSixteenDay();
					//					}
					//第1期
					int curNum = 1;
					repaymentPlanVO.setCurNum(curNum);
					//利率
					//D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
					BigDecimal e = carLoanMonthRate.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(residueDays));
					// 还款金额
					BigDecimal repayAmount = pactMoney.multiply(a.add(e)).setScale(2, BigDecimal.ROUND_HALF_UP);

					repaymentPlanVO.setRepayAmount(repayAmount);
					// 风险基金
					risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRisk(risk);
					repaymentPlanVO.setCurRemainingRisk(risk);
					//前期风险金
					raskAmount = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 12, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(residueDays)).setScale(2, BigDecimal.ROUND_HALF_UP);
					//总风险金
					loanVO.setRisk(raskAmount);
					//前期服务费
					BigDecimal preparatoryServeRate = preparatoryAmount.subtract(raskAmount);
					//前期咨询费
					BigDecimal preparatoryReferRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setConsult(preparatoryReferRate);
					//前期评估费
					BigDecimal preparatoryEvalRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					loanVO.setAssessment(preparatoryEvalRate);
					//前期丙方管理费
					BigDecimal preparatoryManagePart1Fee = preparatoryServeRate.subtract(preparatoryReferRate).subtract(preparatoryEvalRate);
					loanVO.setcManage(preparatoryManagePart1Fee);
					//前期乙方管理费
					loanVO.setbManage(BigDecimal.ZERO);
					//To be delete
					System.out.println("e="+e);
					System.out.println("e="+dayOfMonth);
					System.out.println("e="+residueDays);
					// 利息
					//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3),2)
					BigDecimal interestAmt = pactMoney.multiply(e).setScale(2, BigDecimal.ROUND_HALF_UP);

					totalInterestAmt = interestAmt;

					// 服务费
					BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

					repaymentPlanVO.setInterestAmt(interestAmt);
					repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
					// 咨询费
					BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setReferRate(referRate);
					repaymentPlanVO.setCurRemainingReferRate(referRate);
					// 评估费
					BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setEvalRate(evalRate);
					repaymentPlanVO.setCurRemainingEvalRate(evalRate);
					// 丙方管理费
					BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
					repaymentPlanVO.setManagePart1Fee(managePart1Fee);
					repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
					// 乙方管理费
					repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					loanVO.setbManage(BigDecimal.ZERO);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}

					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
					if (nowDay.compareTo(16) == 0 || nowDay.compareTo(1) == 0) {
						oneTimeRepaymentAmount = pactMoney.add(interestAmt);
					} else {
						oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carRate)).subtract(preparatoryAmount);
					}
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
							.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
							.add(repaymentPlanVO.getCurRemainingReferRate());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanService.insert(repaymentPlanVO);
					oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
					repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
					for (curNum = 2; curNum < loanVO.getTime(); curNum++) {

						repaymentPlanVO.setCurNum(curNum);
						// 还款金额
						repayAmount = pactMoney.multiply(carRate);
						repaymentPlanVO.setRepayAmount(repayAmount);

						if (curNum > 1) {
							repayDay = DateUtil.getNowDateAfter(1, repayDay);
						}
						repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
						repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
						// 利息
						interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
						totalInterestAmt = totalInterestAmt.add(interestAmt);
						repaymentPlanVO.setInterestAmt(interestAmt);
						repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
						deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
								.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
								.add(repaymentPlanVO.getCurRemainingReferRate());
						repaymentPlanVO.setDeficit(deficit);
						repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
						repaymentPlanService.insert(repaymentPlanVO);
					}
					// 最后一期
					repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
					// 还款金额
					repayAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
					repaymentPlanVO.setRepayAmount(repayAmount);

					repaymentPlanVO.setInterestAmt(interestAmt);
					if (curNum > 1) {
						repayDay = DateUtil.getNowDateAfter(1, repayDay);
					}
					repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
					repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
					// 还款本金
					repaymentPlanVO.setPrincipalAmt(pactMoney);
					repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
					totalInterestAmt = totalInterestAmt.add(interestAmt);
					repaymentPlanVO.setRisk(BigDecimal.ZERO);
					// 咨询费
					repaymentPlanVO.setReferRate(BigDecimal.ZERO);
					// 评估费
					repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
					// 丙方管理费
					repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
					deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
					repaymentPlanVO.setDeficit(deficit);
					repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
					repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
					repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
					repaymentPlanService.insert(repaymentPlanVO);
				}
				//营业网点地址

			}
			Long salesDeptId = loan.getSalesDeptId();
			BaseArea baseArea = baseAreaService.get(salesDeptId);
			WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
			createCarRepaymentContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, preparatoryAmount, contractNo, totalInterestAmt, loanVO.getTime(),
					raskAmount, workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, totalInterestAmt, contractNo, loanVO.getTime(), preparatoryAmount,
					workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarPersonLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, risk, contractNo, loanVO.getTime(), raskAmount, workPlaceInfo.getSite(),pactMoney
					,contractCreatedDate);
			createCarVehicleContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, contractNo, loanVO.getTime(), workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarEntrustContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, personBankAccount, contractNo, loanVO.getTime(),pactMoney,contractCreatedDate);
			createCarRepaymentFundContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, contractNo, loanVO.getTime(), workPlaceInfo.getSite(),pactMoney,contractCreatedDate);
			createCarCollectionDetailContract(loan, contractNo, loanVO.getTime(),pactMoney,contractCreatedDate);
			loanVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
			loanVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));
			loanVO.setPactMoney(pactMoney);
			loanVO.setContractCreatedTime(new Date());

			// 放款金额
			// 小企业贷： 划款金额=合同金额-风险金-咨询费-评估费
			// 车贷（3期）：划款金额=合同金额-风险金-咨询费--评估费-丙方管理费
			// 车贷（6、9、12期）：划款金额=合同金额-风险金-咨询费-评估费
			//等额本息的产品为：小企业贷，车贷6.9.12期
			//先息后本的产品为：车贷3期,6期
			BigDecimal grantMoney = loanVO.getPactMoney()
					.subtract(loanVO.getRisk()).subtract(loanVO.getConsult())
					.subtract(loanVO.getAssessment());
			if (loan.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0
					&& (loan.getAuditTime().compareTo(3) == 0)) {
				grantMoney = grantMoney.subtract(loanVO.getcManage());
				loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.BEFORE_INTEREST_AFTER_PRINCIPAL_PAYMENT
						.getValue());
			} else if(("002".equals(product.getProductCode()))
					&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&auditTime.compareTo(carLoanModeByMonthSix) == 0){
				loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.BEFORE_INTEREST_AFTER_PRINCIPAL_PAYMENT
						.getValue());
			}else {
				loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST
						.getValue());
			}

			if(loan.getCreatedTime().after(date)
					&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&(auditTime.compareTo(carLoanModeByMonth) <=0 || ("002".equals(product.getProductCode()) && auditTime.compareTo(carLoanModeByMonthSix) ==0 ))
					){
			}else{
				loanVO.setGrantMoney(grantMoney);
			}
			//还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(loanVO.getEndRepayDate());
			loanVO.setReturnDate(calendar.get(Calendar.DATE));
			loanVO.setResidualPactMoney(pactMoney);
			loanVO.setResidualTime(loanVO.getTime().intValue());
			
			//合同确认退回、流标、终止借款，重新生产合同时，要合同签订   2018/2/1
			LcbModel lcbModel = contractGenerateDao.getPersonInfoByLoanId(loanId.toString());
			String lcbBorrowStatus = lcbModel.getLcbBorrowStatus();
			if(StringUtils.isNotBlank(lcbBorrowStatus) && (lcbBorrowStatus.equals("10") || lcbBorrowStatus.equals("20"))){
				loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
			}
			
			// 更新贷款信息表
			loanService.update(loanVO);
			
			//合同确认退回、流标、终止借款，重新生产合同成功后，清空lcbBorrowStatus   2018/2/1
			Map<String,Object> tempMap = new HashMap<>();
			tempMap.put("id", loanId);
			tempMap.put("flag", "2");
			contractGenerateDao.insertLcbBorrowNo(tempMap);
			
			// 插入日志
			BusinessLog businessLog = new BusinessLog();
			businessLog.setLoanId(loanId);
			businessLog.setMessage("合同生成");
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue());
			businessLogService.insert(businessLog);

			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CREATE_CONTRACT.getValue());
			sysLog.setRemark("借款ID   "+loanId.toString());
			sysLogService.insert(sysLog);
		}
	}

	@Override
	public void createdExtensionContractNew(GenerateContractVO generateContractVO) {
		// 开户人银行帐号信息已经存在则不保存，否则将客户账号信息保存到bank_account表中
		Long extensionId = Long.valueOf(generateContractVO.getLoanId());
		Extension extension = extensionService.get(extensionId);
		if (extension != null) {
			Long personId = extension.getPersonId();
			// 已经生成过合同
			if (StringUtils.isNotEmpty(extension.getContractNo())) {
				// 删除还款计划
				repaymentPlanService.deleteRepaymentPlanByLoanId(extensionId);
				// 删除合同信息
				contractService.deleteContractByLoanId(extensionId);
				// 删除银行账户信息和客户银行关联
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(personId);
				personBankVO.setLoanId(extensionId);
				List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
				if (CollectionUtil.isNotEmpty(personBankList)) {
					for (PersonBank personBank : personBankList) {
						personBankService.deletePersonBank(personBank.getId());
						Long bankAccountId = personBank.getBankAccountId();
						bankAccountService.deleteById(bankAccountId);

					}
				}
			}
			Person person = personService.get(personId);
			// 借款人
			BankAccount personBankAccount = new BankAccount();
			personBankAccount.setAccount(generateContractVO.getBankAccount());
			personBankAccount.setBranchName(generateContractVO.getBankBranch());
			Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
			personBankAccount.setBank(bank);
			personBankAccount.setBankName(bank.getBankName());
			personBankAccount.setStatus(EnumConstants.BankAccountStatus.ENABLED.getValue());
			personBankAccount.setAccountAuthType(generateContractVO.getAccountAuthType());
			personBankAccount = bankAccountService.insert(personBankAccount);

			// 插入客户银行关联
			PersonBank personBank = new PersonBank();
			personBank.setBankAccountId(personBankAccount.getId());
			personBank.setPersonId(personId);
			personBank.setLoanId(extensionId);
			personBankService.insertPersonBank(personBank);
			// 更新贷款信息还款银行和放款银行
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(extensionId);
			extensionVO.setContractSrc(generateContractVO.getContractSrc());
			extensionVO.setGrantAccountId(bank.getId());
			extensionVO.setRepayAccountId(bank.getId());
			extensionVO.setTime(extension.getAuditTime());
			// 签约日期
			extensionVO.setSignDate(new Date());
			// 合同金额
			BigDecimal pactMoney = extension.getAuditMoney();
			extensionVO.setPactMoney(pactMoney);

			//					//原借款id
			//					Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			//上一笔借款id
			Long preExtensionId = loanExtensionService.getPreExtensionId(extensionId, extension.getExtensionTime());
			if(extension.getExtensionTime().compareTo(1)>0){
				Extension oldExtesion = extensionService.get(preExtensionId);
				extensionVO.setContractNo(oldExtesion.getContractNo());
			}else{
				Loan oldLoan = loanService.get(preExtensionId);
				extensionVO.setContractNo(oldLoan.getContractNo());
			}
			Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
			Loan loan=loanService.get(oldLoanId);
			SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);

			SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			RepaymentPlanVO repaymentPlanVO=null;
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				// 生成还款信息
				repaymentPlanVO = getExtensionRepaymentPlanFirstTermNewFresh(extension);
			}else{
				repaymentPlanVO = getExtensionRepaymentPlanFirstTerm(extension);
			}
			//第1期
			int curNum = 1;
			Date repayDay = repaymentPlanVO.getRepayDay();
			// 还款金额
			BigDecimal repayAmount = repaymentPlanVO.getRepayAmount();
			BigDecimal interestAmt = repaymentPlanVO.getInterestAmt();
			BigDecimal deficit = repaymentPlanVO.getDeficit();
			Date startRepayDate = repayDay;
			Date endRepayDate =  DateUtil.getNowDateAfter(extension.getAuditTime()-1,startRepayDate);
			// 咨询费
			extensionVO.setConsult(repaymentPlanVO.getReferRate());
			// 评估费
			extensionVO.setAssessment(repaymentPlanVO.getEvalRate());
			// 乙方管理费
			extensionVO.setbManage(BigDecimal.ZERO);
			// 丙方管理费
			extensionVO.setcManage(repaymentPlanVO.getManagePart1Fee());
			// 风险基金
			BigDecimal risk = repaymentPlanVO.getRisk();
			BigDecimal totalInterestAmt = BigDecimal.ZERO;
			totalInterestAmt = totalInterestAmt.add(repaymentPlanVO.getInterestAmt().multiply(new BigDecimal("3")));

			// 当期一次性还款金额
			BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
			//=IF(A30="",'3期模型'!$D$7,'3期模型'!$D$7*(1+1.5%))
			// 提前还款违约金
			BigDecimal penalty = pactMoney.multiply(carShortPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			repaymentPlanVO.setPenalty(penalty);
			// 当期一次性还款金额
			oneTimeRepaymentAmount = pactMoney.add(penalty);
			repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);

			repaymentPlanService.insert(repaymentPlanVO);
			//第2期
			curNum = 2;
			repaymentPlanVO.setCurNum(curNum);
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			repaymentPlanService.insert(repaymentPlanVO);
			//第3期
			repaymentPlanVO.setCurNum(extension.getAuditTime());
			// 还款金额
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				// 生成还款信息
				repayAmount = pactMoney;
				repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
				repaymentPlanVO.setInterestAmt(BigDecimal.ZERO);
				deficit = repayAmount;
			}else{
				repayAmount = interestAmt.add(pactMoney);
			}

			repaymentPlanVO.setRepayAmount(repayAmount);
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			// 还款本金
			repaymentPlanVO.setPrincipalAmt(pactMoney);
			repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
			repaymentPlanVO.setRisk(BigDecimal.ZERO);
			// 咨询费
			repaymentPlanVO.setReferRate(BigDecimal.ZERO);
			// 评估费
			repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
			// 丙方管理费
			repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
			}else{
				deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
			}

			repaymentPlanVO.setPenalty(BigDecimal.ZERO);
			// 当期一次性还款金额
			repaymentPlanVO.setOneTimeRepaymentAmount(pactMoney);

			repaymentPlanVO.setDeficit(deficit);
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);

			repaymentPlanService.insert(repaymentPlanVO);

			//营业网点地址
			Long salesDeptId = extension.getSalesDeptId();
			BaseArea baseArea = baseAreaService.get(salesDeptId);
			WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());

			extensionVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
			extensionVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));

			extensionVO.setContractCreatedTime(new Date());

			//还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(extensionVO.getEndRepayDate());
			extensionVO.setReturnDate(calendar.get(Calendar.DATE));
			extensionVO.setResidualPactMoney(pactMoney);
			extensionVO.setResidualTime(extensionVO.getTime().intValue());

			//TODO
			carExtensionService.createCarLoanContract(extension,extensionVO, person,  personBankAccount, totalInterestAmt, workPlaceInfo.getSite());
			carExtensionService.createCarPersonLoanContract(extension, extensionVO,person, risk,  workPlaceInfo.getSite());
			carExtensionService.createCarRepaymentFundContract(extension, extensionVO, person,  workPlaceInfo.getSite());
			carExtensionService.createCarEntrustContract(extension,extensionVO, person,  personBankAccount);


			// 更新贷款信息表
			extensionService.update(extensionVO);




			// 插入日志
			BusinessLog businessLog = new BusinessLog();
			businessLog.setLoanId(extensionId);
			businessLog.setMessage("展期合同生成");
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue());
			businessLogService.insert(businessLog);

			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CREATE_CONTRACT.getValue());
			sysLog.setRemark("展期借款ID   "+extensionId.toString());
			sysLogService.insert(sysLog);
		}

	}
	public RepaymentPlanVO getExtensionRepaymentPlanFirstTermNewFresh(Extension extension){
		//上一笔借款id
		Long preExtensionId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
		Date loanEndRepayDate;
		if(extension.getExtensionTime().compareTo(1)>0){
			Extension oldExtesion = extensionService.get(preExtensionId);
			loanEndRepayDate = oldExtesion.getEndRepayDate();
		}else{
			Loan oldLoan = loanService.get(preExtensionId);
			loanEndRepayDate = oldLoan.getEndRepayDate();
		}
		Date startRepayDate = DateUtil.getNowDateAfter(1,loanEndRepayDate);
		Date repayDay = startRepayDate;
		// 合同金额
		BigDecimal pactMoney = extension.getAuditMoney();
		Product product = productService.get(extension.getProductId());

		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setProductId(extension.getProductId());
		productDetailVO.setTerm(extension.getAuditTime());
		productDetailVO.setCarProductType(extension.getLoanType().intValue());

		ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);

		// 生成还款信息
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(extension.getId());
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());

		repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
		repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));

		BigDecimal risk = BigDecimal.ZERO;;

		SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_SHORT_CIR_SUM_RATE_NEW);

		// 费率
		BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());	
		// 费率
		//BigDecimal carRate = productDetail.getSumRate();
		BigDecimal carShortRistRate = product.getRiskRate();

		//第1期
		int curNum = 1;
		repaymentPlanVO.setCurNum(curNum);
		// 还款金额
		BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		repaymentPlanVO.setRepayAmount(repayAmount);
		// 风险基金
		risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		// 利息
		//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
		BigDecimal interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		// 服务费
		BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);
		//		BigDecimal totalInterestAmt = BigDecimal.ZERO;
		//		totalInterestAmt = totalInterestAmt.add(interestAmt.multiply(new BigDecimal("3")));

		repaymentPlanVO.setInterestAmt(interestAmt);
		repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
		// 咨询费
		BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setReferRate(referRate);
		repaymentPlanVO.setCurRemainingReferRate(referRate);

		// 评估费
		BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setEvalRate(evalRate);
		repaymentPlanVO.setCurRemainingEvalRate(evalRate);

		// 丙方管理费
		BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
		repaymentPlanVO.setManagePart1Fee(managePart1Fee);
		repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
		// 乙方管理费
		repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);

		//BigDecimal oneTimeRepaymentAmount = pactMoney.add(pactMoney.multiply(carLoanMonthRate));
		BigDecimal oneTimeRepaymentAmount = pactMoney;
		repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
		BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
				.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
				.add(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
		return repaymentPlanVO;
	}

	/**
	 * 车贷，流通类，6期
	 * @param parameterRate
	 * @param dayOfMonth
	 * @param residueDays
	 * @param product
	 * @param repaymentPlanVO
	 * @param pactMoney
	 * @param risk
	 * @param raskAmount
	 * @param loanVO
	 * @param preparatoryAmount
	 * @param nowDay
	 * @param contractCreatedDate
	 * @param repayDay
	 * @param totalInterestAmt
	 */
	public Map<String, Object> carLoanCirculateSix(Integer auditTime, Date endRepayDate,
			Product product,RepaymentPlanVO repaymentPlanVO,
			BigDecimal pactMoney,BigDecimal risk,
			BigDecimal raskAmount,LoanVO loanVO,
			BigDecimal preparatoryAmount,
			Date contractCreatedDate,Date repayDay,BigDecimal totalInterestAmt){

		Map<String, Object> result = new HashMap<String, Object>();

		int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
		Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
		if (nowDay == 1 || nowDay == 16) {
			loanVO.setTime(auditTime.longValue());
		} else {
			loanVO.setTime(7L);
			// 还款结束日期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(repayDay);
			calendar.add(Calendar.MONTH, loanVO.getTime().intValue() - 1);
			DateUtil.setHMSM(calendar, 0, 0, 0, 0);
			endRepayDate = calendar.getTime();
		}
		result.put("endRepayDate", endRepayDate);
		//当日到首期付款剩余天数
		int residueDays = dayOfMonth - nowDay + 1;

		if (nowDay > 15) {
			residueDays = dayOfMonth - nowDay + 1 + 15;
		}

		// 车贷，流通类，6期,第一期特殊处理
		Map<String, Object> resultFirst = carLoanCirculateSixFirstTream(dayOfMonth, residueDays, nowDay, auditTime, endRepayDate, product, repaymentPlanVO, pactMoney, risk, raskAmount, loanVO, preparatoryAmount, contractCreatedDate, repayDay, totalInterestAmt);
		result.putAll(resultFirst);

		//为后面准备数据
		preparatoryAmount = (BigDecimal) result.get("preparatoryAmount");
		risk = (BigDecimal) result.get("risk");
		raskAmount = (BigDecimal) result.get("raskAmount");
		totalInterestAmt = (BigDecimal) result.get("totalInterestAmt");
		repayDay = (Date) result.get("repayDay");



		//其他期，2.59%，为后面参数做准备
		SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_CIR_SIX_RATE_NEW);

		// 费率
		BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());

		int curNum = 1;
		repaymentPlanVO.setCurNum(curNum);
		//利率

		// 还款金额
		//=$D$7*$D$10
		BigDecimal repayAmount = pactMoney.multiply(carRate).setScale(2, BigDecimal.ROUND_HALF_UP);

		repaymentPlanVO.setRepayAmount(repayAmount);

		// 利息

		BigDecimal  interestAmt = (BigDecimal) result.get("interestAmt");
		totalInterestAmt = (BigDecimal) result.get("totalInterestAmt");

		// 服务费
		BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

		//还款利息
		repaymentPlanVO.setInterestAmt(interestAmt);
		repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
		// 咨询费
		BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setReferRate(referRate);
		repaymentPlanVO.setCurRemainingReferRate(referRate);
		// 评估费
		BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setEvalRate(evalRate);
		repaymentPlanVO.setCurRemainingEvalRate(evalRate);
		// 丙方管理费
		BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
		repaymentPlanVO.setManagePart1Fee(managePart1Fee);
		repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
		// 乙方管理费
		repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);

		// 当期一次性还款金额
		BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
		//D7*(1+2.5%)
		// 提前还款违约金
		BigDecimal penalty = pactMoney.multiply(carSixPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setPenalty(penalty);
		// 当期一次性还款金额
		oneTimeRepaymentAmount = pactMoney.add(penalty);
		repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
		BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
				.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
				.add(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());


		for (curNum = 2; curNum < loanVO.getTime(); curNum++) {
			repaymentPlanVO.setCurNum(curNum);
			// 还款金额
			repayAmount = pactMoney.multiply(carRate);
			repaymentPlanVO.setRepayAmount(repayAmount);

			if (curNum > 1) {
				repayDay = DateUtil.getNowDateAfter(1, repayDay);
			}
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			// 利息
			interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			totalInterestAmt = totalInterestAmt.add(interestAmt);
			repaymentPlanVO.setInterestAmt(interestAmt);
			repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
			deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
					.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
					.add(repaymentPlanVO.getCurRemainingReferRate());
			repaymentPlanVO.setDeficit(deficit);
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			repaymentPlanService.insert(repaymentPlanVO);
		}
		// 最后一期
		repaymentPlanVO.setCurNum(loanVO.getTime().intValue());
		// 提前还款违约金
		repaymentPlanVO.setPenalty(BigDecimal.ZERO);
		// 当期一次性还款金额
		repaymentPlanVO.setOneTimeRepaymentAmount(pactMoney);
		// 还款金额
		repayAmount = pactMoney;
		repaymentPlanVO.setRepayAmount(repayAmount);

		repaymentPlanVO.setInterestAmt(null);
		if (curNum > 1) {
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
		}
		result.put("repayDay", repayDay);
		repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
		repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
		// 还款本金
		repaymentPlanVO.setPrincipalAmt(pactMoney);
		repaymentPlanVO.setCurRemainingPrincipal(pactMoney);
		totalInterestAmt = totalInterestAmt.add(interestAmt);
		repaymentPlanVO.setRisk(BigDecimal.ZERO);
		// 咨询费
		repaymentPlanVO.setReferRate(BigDecimal.ZERO);
		// 评估费
		repaymentPlanVO.setEvalRate(BigDecimal.ZERO);
		// 丙方管理费
		repaymentPlanVO.setManagePart1Fee(BigDecimal.ZERO);
		//deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingPrincipal());
		deficit = repaymentPlanVO.getRepayAmount();
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
		repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingInterestAmt(BigDecimal.ZERO);
		repaymentPlanService.insert(repaymentPlanVO);


		result.put("totalInterestAmt", totalInterestAmt);
		return result;
	}
	/**
	 * 车贷，流通类，6期,第一期特殊处理
	 * @param auditTime
	 * @param endRepayDate
	 * @param product
	 * @param repaymentPlanVO
	 * @param pactMoney
	 * @param risk
	 * @param raskAmount
	 * @param loanVO
	 * @param preparatoryAmount
	 * @param contractCreatedDate
	 * @param repayDay
	 * @param totalInterestAmt
	 */
	public Map<String, Object> carLoanCirculateSixFirstTream(int dayOfMonth,int residueDays,Integer nowDay,Integer auditTime, Date endRepayDate,
			Product product,RepaymentPlanVO repaymentPlanVO,
			BigDecimal pactMoney,BigDecimal risk,
			BigDecimal raskAmount,LoanVO loanVO,
			BigDecimal preparatoryAmount,
			Date contractCreatedDate,Date repayDay,BigDecimal totalInterestAmt){

		Map<String, Object> result = new HashMap<String, Object>();

		SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_FIRST_CIR_SIX_RATE_NEW);

		// 费率
		BigDecimal carRate = new BigDecimal	(parameterRate.getParameterValue());
		//前期费用
		//A10-D9
		BigDecimal a = carRate.subtract(carLoanMonthRate);
		//(A10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
		BigDecimal b = a.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP);
		//(A10-D9)/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B20-D3)
		BigDecimal d = b.multiply(new BigDecimal(residueDays));

		BigDecimal carShortRistRate = product.getRiskRate();
		//					// 还款日期为下月1日
		//					repayDay = DateUtil.getNextMonthFirstDay();
		// 下半月
		//					if (nowDay.compareTo(modeByDay) > 0) {
		//						// 还款日期为下月16日
		//						repayDay = DateUtil.getNextMonthSixteenDay();
		//					}
		//第1期
		int curNum = 1;
		repaymentPlanVO.setCurNum(curNum);
		//利率

		// 还款金额
		//=$D$7*$D$10
		BigDecimal  repayAmount=new BigDecimal(0);
		SysParameter parameterRateOne=	sysParameterService.getByCode(EnumConstants.CAR_CIR_SIX_RATE_NEW);
		// 一期费率，回复
		BigDecimal carRateOne = new BigDecimal	(parameterRateOne.getParameterValue());
		repayAmount = pactMoney.multiply(carRateOne).setScale(2, BigDecimal.ROUND_HALF_UP);

		repaymentPlanVO.setRepayAmount(repayAmount);
		// 风险基金
		risk = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
		result.put("risk", risk);

		repaymentPlanVO.setRisk(risk);
		repaymentPlanVO.setCurRemainingRisk(risk);
		//前期风险金
		raskAmount = pactMoney.multiply(carShortRistRate).divide(new BigDecimal(12), 12, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(residueDays)).setScale(2, BigDecimal.ROUND_HALF_UP);
		result.put("raskAmount", raskAmount);
		//总风险金
		loanVO.setRisk(raskAmount);
		//D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3)
		BigDecimal e = carLoanMonthRate.divide(new BigDecimal(dayOfMonth), 12, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(residueDays));
		// 前期利息
		//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
		BigDecimal  preparatoryInterestAmt = pactMoney.multiply(e).setScale(2, BigDecimal.ROUND_HALF_UP);
		//ROUND(D7*A5,2)+D13
		BigDecimal f=pactMoney.multiply(carLoanCounterFeeNew).setScale(2, BigDecimal.ROUND_HALF_UP).add(preparatoryInterestAmt);

		preparatoryAmount = pactMoney.multiply(d).setScale(2, BigDecimal.ROUND_HALF_UP).add(f);	
		result.put("preparatoryAmount", preparatoryAmount);
		//前期服务费 =D12-ROUND(D14,2)-D13
		BigDecimal preparatoryServeRate = preparatoryAmount.subtract(raskAmount).subtract(preparatoryInterestAmt);
		loanVO.setGrantMoney(pactMoney.subtract(preparatoryAmount));
		//前期咨询费
		BigDecimal preparatoryReferRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		loanVO.setConsult(preparatoryReferRate);
		//前期评估费
		BigDecimal preparatoryEvalRate = preparatoryServeRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		loanVO.setAssessment(preparatoryEvalRate);
		//前期丙方管理费
		BigDecimal preparatoryManagePart1Fee = preparatoryServeRate.subtract(preparatoryReferRate).subtract(preparatoryEvalRate);
		loanVO.setcManage(preparatoryManagePart1Fee);
		//前期乙方管理费
		loanVO.setbManage(BigDecimal.ZERO);
		//To be delete
		System.out.println("e="+e);
		System.out.println("e="+dayOfMonth);
		System.out.println("e="+residueDays);


		// 利息

		BigDecimal  interestAmt=new BigDecimal(0);
		BigDecimal interest=new  BigDecimal(0);
		if ( nowDay> 15){
			//=ROUND(I7*I9/(G21-DATE(YEAR(I3),MONTH(I3),16))*(G21-I3),2);
			//(G21-DATE(YEAR(I3),MONTH(I3),16))
			int g=(int) DateUtil.getDiffDay(Dateof16(contractCreatedDate),repayDay);
			//(G21-I3)
			int h=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
			interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			interest = interestAmt.divide(new BigDecimal(g),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(h)).setScale(2, BigDecimal.ROUND_HALF_UP);
			result.put("interestAmt", interestAmt);
			result.put("interest", interest);
			loanVO.setProphaseInterest(interest);
		}else{
			//ROUND(D7*D9/DAY(DATE(YEAR(D3),MONTH(D3)+1,0))*(B21-D3),2)
			//=ROUND($D$7*$D$9,2)
			interestAmt = pactMoney.multiply(carLoanMonthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			result.put("interestAmt", interestAmt);
			// DAY(DATE(YEAR(D3),MONTH(D3)+1,0))
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(contractCreatedDate); //放入你的日期 
			int m=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			//(B21-D3);
			int n=(int) DateUtil.getDiffDay(contractCreatedDate,repayDay);
			interest = interestAmt.divide(new BigDecimal(m),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(n)).setScale(2, BigDecimal.ROUND_HALF_UP);
			result.put("interest", interest);
			loanVO.setProphaseInterest(interest);
		}
		totalInterestAmt = interest;
		result.put("totalInterestAmt", totalInterestAmt);

		// 服务费
		BigDecimal serveRate = repayAmount.subtract(interestAmt).subtract(risk);

		//还款利息
		repaymentPlanVO.setInterestAmt(interestAmt);
		//当期剩余利息
		repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
		// 咨询费
		BigDecimal referRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setReferRate(referRate);
		repaymentPlanVO.setCurRemainingReferRate(referRate);
		// 评估费
		BigDecimal evalRate = serveRate.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setEvalRate(evalRate);
		repaymentPlanVO.setCurRemainingEvalRate(evalRate);
		// 丙方管理费
		BigDecimal managePart1Fee = serveRate.subtract(referRate).subtract(evalRate);
		repaymentPlanVO.setManagePart1Fee(managePart1Fee);
		repaymentPlanVO.setCurRemainingManagePart1Fee(managePart1Fee);
		// 乙方管理费
		repaymentPlanVO.setManagePart0Fee(BigDecimal.ZERO);
		repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
		loanVO.setbManage(BigDecimal.ZERO);
		if (curNum > 1) {
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
		}
		result.put("repayDay", repayDay);
		repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
		repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
		// 当期一次性还款金额
		BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;

		//D7*(1+2.5%)
		// 提前还款违约金
		BigDecimal penalty = pactMoney.multiply(carSixPenaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		repaymentPlanVO.setPenalty(penalty);

		// 当期一次性还款金额
		oneTimeRepaymentAmount = pactMoney.add(penalty);
		if (nowDay.compareTo(16) == 0 || nowDay.compareTo(1) == 0) {

		} else {
			// 当期一次性还款金额+加上前期费用
			//D7*A10+D7*A5-D12+D7*(1+2.5%)
			oneTimeRepaymentAmount = pactMoney.multiply(carRate).add(pactMoney.multiply(carLoanCounterFeeNew)) .subtract(preparatoryAmount).add(oneTimeRepaymentAmount);
		}
		repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
		BigDecimal deficit = repaymentPlanVO.getCurRemainingEvalRate().add(repaymentPlanVO.getCurRemainingInterestAmt())
				.add(repaymentPlanVO.getRisk()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
				.add(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlanVO.setDeficit(deficit);
		repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
		repaymentPlanService.insert(repaymentPlanVO);

		return result;
	}

	@Override
	public Map<String, Object> getZongAnReturnData(String loanId) {
		return zonganRiskDao.getZongAnReturnData(loanId);
	}

	@Override
	public Map<String, Object> contractManeyOverProof(String name, String idNo, String loanId,String agreeMoneyInput,String agreeTimeComb) {
		logger.info("***********************************"+name+","+idNo+","+loanId+","+agreeMoneyInput+","+agreeTimeComb);
		logger.info("******************合同最多金额*****************"+contractMany);
		//合同金额上线配置文件配置
		BigDecimal contractManyBig = new BigDecimal(contractMany);
		//当前这次车贷的合同金额
		BigDecimal curttenMoney = new BigDecimal("0");
		//核心返回的个贷未还完的金额
		BigDecimal hexinMoney = new BigDecimal("0");
		//客户除了这次车贷借款之外的其他车贷借款金额
		BigDecimal belongToMoney = new BigDecimal("0");

		//当前这次车贷借款对应的合同金额的取值逻辑
		curttenMoney=findContractMoneyBeforeWant(loanId,agreeMoneyInput,agreeTimeComb);
		logger.info("******************当前这次车贷借款对应的合同金额*****************"+curttenMoney);
		//调用核心获取个贷未还款的取值逻辑
		Map<String,String> sendHexinMap=new HashMap<String,String>();
		sendHexinMap.put("idNum", idNo);
		sendHexinMap.put("name", name);
		String responseData=HttpUtils.post(hexinInfo, sendHexinMap);
		logger.info("******************调用核心获取个贷未还款*****************"+responseData);
		ObjectMapper mapper = new ObjectMapper(); 

		try {
			JsonNode node = mapper.readTree(responseData);  
			JsonNode nodeString=node.get("code");
			if(nodeString.asText().equals("000000")){
				JsonNode money=node.get("residualPactMoney");
				hexinMoney=new BigDecimal(money.asText().toString());
			}else{
				logger.info("返回的repCode不等于000000--------------------");
				Map<String,Object> mapreturn=new HashMap<String,Object>();
				mapreturn.put("SUMMONEY", true);
				return mapreturn;
			}


		} catch (Exception e) {
			logger.info("JSON字符串转实体报错--------------------"+e.getMessage());
			Map<String,Object> mapreturn=new HashMap<String,Object>();
			mapreturn.put("SUMMONEY", true);
			return mapreturn;
		}




		//该客户除了这次车贷借款合同以外的其他车贷借款金额取值逻辑
		Map<String,Object> map=zonganRiskDao.findLoanMoneyNoBelongTo(idNo,loanId);
		if(null==map){
			//默认已经赋值0了
		}else{
			belongToMoney=(BigDecimal) map.get("SUMMONEY");
		}
		logger.info("******************客户除了这次车贷借款之外的其他车贷借款金额*****************"+belongToMoney);

		Map<String,Object> mapreturn=new HashMap<String,Object>();
		if(curttenMoney.add(hexinMoney).add(belongToMoney).compareTo(contractManyBig)>0){
			mapreturn.put("SUMMONEY", true);
			return mapreturn;
		}else{
			mapreturn.put("SUMMONEY", false);
			return  mapreturn;
		}
	}

	//捞财宝不支持三期的签约 确记!!!!!
	public BigDecimal findContractMoneyBeforeWant(String loanID,String agreeMoneyInput,String agreeTimeComb){
		Long loanId = Long.valueOf(loanID);
		Loan loan = loanService.get(loanId);
		//logger.info("******************返回的loan****************"+gson.toJson(loan));
		// 合同金额
		BigDecimal pactMoney = new BigDecimal(agreeMoneyInput);
		Product product = productService.get(loan.getProductId());

		logger.info("******************返回的PRODUCT****************"+gson.toJson(product));

		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setProductId(product.getId());
		productDetailVO.setTerm(Integer.parseInt(agreeTimeComb));
		if (product.getProductType().compareTo(2) == 0) {
			productDetailVO.setCarProductType(loan.getLoanType().intValue());
		}

		ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);
		logger.info("******************返回的ProductDetail****************"+gson.toJson(productDetail));
		// 车贷
		BigDecimal preparatoryAmount = BigDecimal.ZERO;
		BigDecimal risk = BigDecimal.ZERO;;
		BigDecimal totalInterestAmt = BigDecimal.ZERO;

		//车贷计算器-等额本息新模型
		SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);

		SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
		Date date=new Date();
		try {
			date = sdftime.parse(parameter.getParameterValue());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		BigDecimal consultingFeeRate = product.getConsultingFeeRate();
		BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();

		//前期风险金
		BigDecimal raskAmount = BigDecimal.ZERO;
		Integer term=Integer.parseInt(agreeTimeComb);
		if (term.compareTo(carLoanModeByMonth) > 0) {
			logger.info("******************大于三期***************");
			//Date1.after(Date2),当Date1大于Date2时
			if(loan.getCreatedTime().after(date)){
				// 咨询费费率
				SysParameter parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_CON_RATE_NEW);
				product.setConsultingFeeRate(new BigDecimal (parameterRate.getParameterValue()));
				// 评估费费率
				parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_ASS_RATE_NEW);
				product.setAssessmentFeeRate(new BigDecimal (parameterRate.getParameterValue()));
				// 综合费率
				parameterRate=	sysParameterService.getByCode(EnumConstants.CAR_NEW_SUM_RATE_NEW);
				productDetail.setSumRate(new BigDecimal	(parameterRate.getParameterValue()));

				consultingFeeRate = product.getConsultingFeeRate();
				assessmentFeeRate = product.getAssessmentFeeRate();
			}

			if (product.getId().compareTo(2L) != 0) {
				BigDecimal riskRate = product.getRiskRate();
				risk = (pactMoney.multiply(riskRate)).divide(new BigDecimal(12), 8,
						BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(term));
				pactMoney = pactMoney.add(risk);
			}

			logger.info("******************合同金额1***************"+pactMoney);
			return pactMoney;
		} else {
			logger.info("******************小于等于三期***************");
			logger.info("******************合同金额2***************"+pactMoney);
			//合同金额就是审批金额
			return pactMoney;
		}


	}


}
