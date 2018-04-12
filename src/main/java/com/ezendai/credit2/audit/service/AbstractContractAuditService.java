package com.ezendai.credit2.audit.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.assembler.BankAccountAssembler;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.LoanChangeLog;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.LoanChangeLogService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.system.service.SysSerialNumService;
import com.ezendai.credit2.system.vo.SerialNumResult;

public abstract class AbstractContractAuditService {
	


    @Autowired
    private BankAccountService bankAccountService;



    @Autowired
    private GuaranteeService guaranteeService;

    @Autowired
    private BankService bankService;
    
	@Autowired
	private PersonBankService personBankService;
	
	@Autowired
	private RepayDateRuleService repayDateRuleService;
	
	@Autowired
	private SignLoanRuleService signLoanRuleService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private ContractService contractService;	

    @Autowired
    private BaseAreaService baseAreaService;
    
    @Autowired
    private WorkPlaceInfoService workPlaceInfoService;
    
    @Autowired
    private SysSerialNumService sysSerialNumService;
    
    @Autowired
    private LoanChangeLogService loanChangeLogService;
    
    
    

	public int GUARANTEE_SEQ=5;//担保协议序列号从5开始

	
	 /**
     * <pre>
     * 处理担保人信息
     * </pre>
     *
     * @param generateContractVO
     * @param loan
     * @param guaranteeVO
     * @param g
     */
    protected void processGuarntee(GenerateContractVO generateContractVO, Loan loan, GuaranteeVO guaranteeVO, Guarantee g) {
        BankAccount guarantBankAccount;
        BankAccountVO guaranteeBankAccountVO = new BankAccountVO();
        Bank guaranteeBank;
     
        // 自然人1
        if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getNaturalGuaranteeName1()).trim())) {
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
            // 自然人2
        } else if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getNaturalGuaranteeName2()).trim())) {
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
        } else if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getNaturalGuaranteeName3()).trim())) {
            guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount3());
            guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch3());
            guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank3()));
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
        } else if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getNaturalGuaranteeName4()).trim())) {
            guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount4());
            guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch4());
            guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank4()));
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
        } 
        // 法人1
        else if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getLegalGuaranteeName1()).trim())) {
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
            // 法人2
        } else if (g.getName().trim().equals(StringUtil.notNullString(generateContractVO.getLegalGuaranteeName2()).trim())) {
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
            guaranteeLoan.setId(loan.getId());
            guaranteeVO.setLoan(guaranteeLoan);
            guaranteeVO.setPersonId(loan.getPersonId());
            guaranteeVO.setBankAccountId(guarantBankAccount.getId());
            guaranteeService.update(guaranteeVO);
        }
    }

        /**
     * <pre>
     * 生成首期还款日
     * </pre>
     *
     * @param generateContractVO
     * @param contractCreatedDate
     * @param loanVO
     * @param product
     * @param productDetail
     * @return
     */
    protected Date generateRepayDate(GenerateContractVO generateContractVO, Date contractCreatedDate, LoanVO loanVO, Product product, ProductDetail productDetail) {
        Date repayDay = new Date();
        // 查看是否有还款日规则
        LoanRuleVO loanRuleVo = new LoanRuleVO();
        loanRuleVo.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
        loanRuleVo.setProductType(product.getProductType());
        if (productDetail != null && productDetail.getCarProductType() != null) {
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
            // 固定还款日规则的
            repayDay = getRepayRuleDate(contractCreatedDate);
            // 当前月天数
            int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
            Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
            if ((nowDay >= 13 && nowDay <= 15) || (dayOfMonth - nowDay) < 3) {
                // 是否有特殊签单规则
                loanRuleVo.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());
                loanRuleVo.setContractSrc(null);
                List<LoanRule> loanSignRuleList = signLoanRuleService.findSignRuleByLoanRuleVo(loanRuleVo);
                if (CollectionUtil.isNotEmpty(loanSignRuleList)) {
                    if (loanSignRuleList.size() > 1) {
                        throw new BusinessException("特殊签单规则大于1条");
                    }
                    repayDay = getSignRuleDate(repayDay);
                    // add by LIN 特殊签单的话,在主表写标记
                    loanVO.setFlag(EnumConstants.LoanRuleType.SIGN_RULE.getValue());
                }
            }
        } else {
            // 非固定还款日规则的
            repayDay = getNotRepayRuleDate(contractCreatedDate);
        }
        return repayDay;
    }
    
    /**
     * <pre>
     * 删除原先数据
     * 1.合同;2.还款计划;3.银行信息;4.客户银行卡关联
     * </pre>
     *
     * @param loan
     */
    protected void processOrginalData(GenerateContractVO generateContractVO,Loan loan) {
    	
    	logContractChange(generateContractVO,loan);
    	
        // 已经生成过合同
        if (StringUtils.isNotEmpty(loan.getContractNo())) {
            // 删除还款计划
            repaymentPlanService.deleteRepaymentPlanByLoanId(loan.getId());
            // 删除合同信息
            contractService.deleteContractByLoanId(loan.getId());
            // 删除银行账户信息和客户银行关联
            PersonBankVO personBankVO = new PersonBankVO();
            personBankVO.setPersonId(loan.getPersonId());
            personBankVO.setLoanId(loan.getId());
            List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
            if (CollectionUtil.isNotEmpty(personBankList)) {
                for (PersonBank personBank : personBankList) {
                    Long bankAccountId = personBank.getBankAccountId();
                    bankAccountService.deleteById(bankAccountId);
                    personBankService.deletePersonBank(personBank.getId());
                }
            }
        }
    }
    
    /**
     * <pre>
     *
     * </pre>
     *
     * @param generateContractVO
     * @param loan
     * @return
     */
    protected Bank processBankInfoAndPersonBank(GenerateContractVO generateContractVO, Loan loan) {
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
        personBank.setPersonId(loan.getPersonId());
        personBank.setLoanId(loan.getId());
        personBankService.insertPersonBank(personBank);
        bank.setAccountId(personBankAccount.getId());
        return bank;
    }
    
    /**
     * 固定还款日规则日期变化 如签约日为1-15号，还款日为下月1号，否则为下月16号
     */
    protected Date getRepayRuleDate(Date contractCreatedDate) {
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
     *
     * @param date
     * @return
     */
    protected Date getSignRuleDate(Date date) {
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
     * 非固定还款日规则日期变化 7月27号签约，8月27号首期还款；28-31号签约，统一为下月28号还款
     *
     * @return
     */
    protected Date getNotRepayRuleDate(Date contractCreatedDate) {
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

    protected  String generateAgreeNo(String contractNo ) {
        String contractNum = "";
		DecimalFormat df=new DecimalFormat("-000");
		contractNum=contractNo+ df.format(GUARANTEE_SEQ);
		GUARANTEE_SEQ = GUARANTEE_SEQ + 1;
        return contractNum;
    }
    
    
    /**
     * <pre>
     * 生成规则：ZDB-SME-WS+城市编号(work_place_info.zone_code)+yyyymmdd+3位数字序列号(当天第N份合同)
     * 例:ZDB-SME-WS002120150625001表示: 上海地区,20150625第1份网商贷合同
     * </pre>
     *
     * @param loanId
     * @param contractCreatedDate
     * @return
     */
    @Transactional
    protected String createdContractNo(Long baseAreaId,String contractNoChar,SerialNum serialNum) {
        StringBuilder contractNo = new StringBuilder();
        contractNo.append(contractNoChar);
        // 地区
        BaseArea baseArea = baseAreaService.get(baseAreaId);
        Long workPlaceInfoId = baseArea.getWorkPlaceInfoId();
        WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(workPlaceInfoId);
        contractNo.append(workPlaceInfo.getZoneCode());
        // 获取当前日期
        String nowDate = DateUtil.getDate(DateUtil.getToday(), DateUtil.pattern_day);
        contractNo.append(nowDate);
        // 3位数字序列号
        Long seqNumber;
        // 网商贷合同
        SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(serialNum);
        seqNumber = serialNumResult.getSeq();
        // 流水号不足3位补0
        DecimalFormat df = new DecimalFormat("000");
        String seq = df.format(seqNumber);
        contractNo.append(seq);
        return contractNo.toString();
    }
    
    /**
     * <pre>
     * 生成规则：ZDB-SME-ZX+yyyymmdd+4位数字序列号(当天第N份合同)
     * 例:ZDB-SME-ZX201506250001表示: 20150625第1份助学贷合同
     * </pre>
     *
     * @param loanId
     * @param contractCreatedDate
     * @return
     */
    @Transactional
    protected String createdEduContractNo(Long baseAreaId,String contractNoChar,SerialNum serialNum) {
        StringBuilder contractNo = new StringBuilder();
        contractNo.append(contractNoChar);
        // 获取当前日期
        String nowDate = DateUtil.getDate(DateUtil.getToday(), DateUtil.pattern_day);
        contractNo.append(nowDate);
        // 4位数字序列号
        Long seqNumber;
        // 助学贷贷合同
        SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(serialNum);
        seqNumber = serialNumResult.getSeq();
        // 流水号不足3位补0
        DecimalFormat df = new DecimalFormat("0000");
        String seq = df.format(seqNumber);
        contractNo.append(seq);
        return contractNo.toString();
    }

    
    /**
     * <pre>
     *
     * </pre>
     *
     * @param generateContractVO
     * @param loan
     * @return
     */
    protected void logContractChange(GenerateContractVO generateContractVO, Loan loan) {
    	if (StringUtils.isNotEmpty(loan.getContractNo())) {
    		Long userId = ApplicationContext.getUser().getId();
	    	Date operatorTime=new Date();
	//    	loan.setRepayAccountId(bank.getAccountId());
	    	//获取之前的个人银行信息及关联信息
	        PersonBankVO personBankVO = new PersonBankVO();
	        personBankVO.setPersonId(loan.getPersonId());
	        personBankVO.setLoanId(loan.getId());
	        PersonBank personBank = personBankService.getPersonBank(personBankVO);
	        Long bankAccountId = personBank.getBankAccountId();
	        BankAccount bankaccount= bankAccountService.get(bankAccountId);
	       
	        //比对现在的各项合同信息
			    	if(!loan.getContractSrc().equals(generateContractVO.getContractSrc())){
			    		LoanChangeLog loanChangeLog = new LoanChangeLog();
			    		loanChangeLog.setChangeChoice("合同來源");
			    		loanChangeLog.setChangeBefore(transContractSrc(loan.getContractSrc()));
			    		loanChangeLog.setChangeAfter(transContractSrc(loan.getContractSrc()));
			    		loanChangeLog.setLoanId(loan.getId());
			    		loanChangeLog.setOperatorId(userId);
			    		loanChangeLog.setOperatorTime(operatorTime);
			    		loanChangeLogService.insert(loanChangeLog);
			    	}
			    	if(loan.getPersonId()==8){
				    	BankAccount bankGrantOld = bankAccountService.get(loan.getGrantAccountId());
				    	BankAccount bankGrantNew = bankAccountService.get(generateContractVO.getOrganBankId());
				    	if(!bankGrantOld.getAccount().equals(bankGrantNew.getAccount())){
				    		LoanChangeLog loanChangeLog = new LoanChangeLog();
				    		loanChangeLog.setChangeChoice("放款机构银行账号");
				    		loanChangeLog.setChangeBefore(bankGrantOld.getAccount());
				    		loanChangeLog.setChangeAfter(bankGrantNew.getAccount());
				    		loanChangeLog.setLoanId(loan.getId());
				    		loanChangeLog.setOperatorId(userId);
				    		loanChangeLog.setOperatorTime(operatorTime);
				    		loanChangeLogService.insert(loanChangeLog);
				    	}
				    	if(!bankGrantOld.getBankName().equals(bankGrantNew.getBankName())){
				    		LoanChangeLog loanChangeLog = new LoanChangeLog();
				    		loanChangeLog.setChangeChoice("放款机构银行");
				    		loanChangeLog.setChangeBefore(bankGrantOld.getBankName());
				    		loanChangeLog.setChangeAfter(bankGrantNew.getBankName());
				    		loanChangeLog.setLoanId(loan.getId());
				    		loanChangeLog.setOperatorId(userId);
				    		loanChangeLog.setOperatorTime(operatorTime);
				    		loanChangeLogService.insert(loanChangeLog);
				    	}
				    	if(!bankGrantOld.getBranchName().equals(bankGrantNew.getBranchName())){
				    		LoanChangeLog loanChangeLog = new LoanChangeLog();
				    		loanChangeLog.setChangeChoice("放款机构银行支行");
				    		loanChangeLog.setChangeBefore(bankGrantOld.getBranchName());
				    		loanChangeLog.setChangeAfter(bankGrantNew.getBranchName());
				    		loanChangeLog.setLoanId(loan.getId());
				    		loanChangeLog.setOperatorId(userId);
				    		loanChangeLog.setOperatorTime(operatorTime);
				    		loanChangeLogService.insert(loanChangeLog);
				    	}
			    	}
			    	Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
			    	if(!bankaccount.getAccount().equals(generateContractVO.getBankAccount())){
			    		LoanChangeLog loanChangeLog = new LoanChangeLog();
			    		loanChangeLog.setChangeChoice("借款人开户账户");
			    		loanChangeLog.setChangeBefore(bankaccount.getAccount());
			    		loanChangeLog.setChangeAfter(generateContractVO.getBankAccount());
			    		loanChangeLog.setLoanId(loan.getId());
			    		loanChangeLog.setOperatorId(userId);
			    		loanChangeLog.setOperatorTime(operatorTime);
			    		loanChangeLogService.insert(loanChangeLog);
			    	}
			    	if(!bankaccount.getBankName().equals(bank.getBankName())){
			    		LoanChangeLog loanChangeLog = new LoanChangeLog();
			    		loanChangeLog.setChangeChoice("借款人开户银行");
			    		loanChangeLog.setChangeBefore(bankaccount.getBankName());
			    		loanChangeLog.setChangeAfter(bank.getBankName());
			    		loanChangeLog.setLoanId(loan.getId());
			    		loanChangeLog.setOperatorId(userId);
			    		loanChangeLog.setOperatorTime(operatorTime);
			    		loanChangeLogService.insert(loanChangeLog);
			    	}
			    	if(!bankaccount.getBranchName().equals(generateContractVO.getBankBranch())){
			    		LoanChangeLog loanChangeLog = new LoanChangeLog();
			    		loanChangeLog.setChangeChoice("借款人开户银行支行");
			    		loanChangeLog.setChangeBefore(bankaccount.getBranchName());
			    		loanChangeLog.setChangeAfter(generateContractVO.getBankBranch());
			    		loanChangeLog.setLoanId(loan.getId());
			    		loanChangeLog.setOperatorId(userId);
			    		loanChangeLog.setOperatorTime(operatorTime);
			    		loanChangeLogService.insert(loanChangeLog);
			    	}
	        }
    	}
    	
    
    public String transContractSrc(Integer src){
    	if(src.compareTo(EnumConstants.ContractSrc.AITE.getValue())==0){
    		return "证大爱特";
    	}else if(src.compareTo(EnumConstants.ContractSrc.P2P.getValue())==0){
    		return "证大P2P";
    	}else{
    		return "";
    	}
    }
}
