package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.service.CarExtensionService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * 
 * <pre>
 * 用来生成展期合同协议
 * </pre>
 *
 * @author 00226557
 * @version $Id: CarExtensionService.java, v 0.1 2015年3月25日 下午1:30:58 00226557 Exp $
 */
@Service
public class CarExtensionServiceImpl implements CarExtensionService{
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private BaseAreaDao baseAreaDao;
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private WorkPlaceInfoService workPlaceInfoService;
	@Autowired
	private BaseAreaService baseAreaService;
	
	// 截取城市关键字-市
	private static String cityStopWord = "市";

	// 截取城市关键字-区
	private static String districtStopWord = "区";

	// 截取城市关键字-县
	private static String countyStopWord = "县";
	
	/**
	 * 生成车贷展期-008-抵押借款展期协议
	 */
	@Transactional
	public void createCarLoanContract(Extension extension,ExtensionVO extensionVO, Person person, BankAccount personBankAccount, BigDecimal totalInterestAmt,
										String salesDepartmentAddress) {
		Contract contract = new Contract();
		contract.setLoanId(extension.getId());
		contract.setContractNo(extensionVO.getContractNo());
		contract.setType(EnumConstants.ContractType.CAR_LOAN_EXT.getValue());
		contract.setContractName("抵押借款展期协议");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setContact(person.getMobilePhone());
		contract.setEmail(person.getEmail());
		if(extension.getExtensionTime().compareTo(1)>0){
			Long oldExtensionId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
			Extension oldExtension = extensionService.get(oldExtensionId);
			contract.setSignDate(oldExtension.getSignDate()); 
			contract.setOrgPactMoney(oldExtension.getPactMoney()); 
		}else{
			Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extension.getId());
			Loan oldLoan = loanService.get(oldLoanId);
			contract.setSignDate(oldLoan.getSignDate());  
			contract.setOrgPactMoney(oldLoan.getPactMoney()); 
		}
		contract.setLoanAgreeNum(extension.getExtensionTime().toString());//展期期数 
		
		contract.setPactMoney(extension.getAuditMoney());
		contract.setTimes(extension.getAuditTime());
		contract.setStartRepayDate(DateUtil.getNowDateAfter(-1, extensionVO.getStartRepayDate()));
		contract.setEndRepayDate(extensionVO.getEndRepayDate());
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		if (totalInterestAmt.compareTo(BigDecimal.ZERO) != 0) {
			contract.setTotalAmount(totalInterestAmt);
		}
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		Long salesDepotId = extension.getSalesDeptId();
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
		contractService.insertSelective(contract);
	}
	
	/**
	 * <pre>
	 * 生成车贷展期-009 个人借款咨询服务风险基金协议(展期)
	 * </pre>
	 *
	 */
	@Transactional
	public void createCarPersonLoanContract(Extension extension,ExtensionVO extensionVO, Person person, BigDecimal risk, String salesDepartmentAddress){
		Contract contract = new Contract();
		contract.setLoanId(extension.getId());
		contract.setContractNo(extensionVO.getContractNo());
		contract.setStartRepayDate(DateUtil.getNowDateAfter(-1, extensionVO.getStartRepayDate()));
		contract.setType(EnumConstants.ContractType.CAR_PERSON_LOAN_EXT.getValue());
		contract.setContractName("个人借款咨询服务风险基金协议(展期)");
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
		contract.setLoanAgreeNum(extension.getExtensionTime().toString());//展期期数 
		contract.setPactMoney(extension.getAuditMoney());
		contract.setTimes(extension.getTime());
		contractService.insertSelective(contract);
	};
	
	/**
	 * 生成车贷展期-010 还款温馨提示函(展期) 
	 * 
	 */
	@Transactional
	public void createCarRepaymentFundContract(Extension extension,ExtensionVO extensionVO, Person person,   
												String salesDepartmentAddress){
		Contract contract = new Contract();
		contract.setLoanId(extension.getId());
		contract.setContractNo(extensionVO.getContractNo());
		contract.setType(EnumConstants.ContractType.CAR_REPAYMENT_FUND_EXT.getValue());
		contract.setContact(person.getMobilePhone());
		contract.setContractName("还款温馨提示函(展期)");
		String cityName = getCityByAddress(salesDepartmentAddress);
		contract.setCityName(cityName);
		String areaName = getAreaByAddress(salesDepartmentAddress);
		contract.setAreaName(areaName);
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contract.setTimes(extensionVO.getTime());
		contract.setPactMoney(extension.getAuditMoney());
		contract.setLoanAgreeNum(extension.getExtensionTime().toString());//展期期数 
		//add 营业部电话
		// 获取该借款所属区域
		BaseArea baseArea = baseAreaService.get(extension.getSalesDeptId());
		if(baseArea != null) {
			// 获取该区域营业部地址
			WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
			if(workPlaceInfo != null) {
				// 返回营业部电话
				contract.setTell(workPlaceInfo.getTel());
			}
		}
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(extensionVO.getStartRepayDate())));
		contractService.insertSelective(contract);
	};
	
	/**
	 * 生成车贷展期-011 委托扣款授权书（无风险基金的描述)
	 * 
	 */
	@Transactional
	public void createCarEntrustContract(Extension extension,ExtensionVO extensionVO, Person person,  BankAccount personBankAccount){
		Contract contract = new Contract();
		contract.setLoanId(extension.getId());
		contract.setContractNo(extensionVO.getContractNo());
		contract.setStartRepayDate(DateUtil.getNowDateAfter(-1, extensionVO.getStartRepayDate()));
		contract.setType(EnumConstants.ContractType.CAR_ENTRUST_EXT.getValue());
		if(extension.getExtensionTime().compareTo(1)>0){
			Long oldExtensionId = loanExtensionService.getPreExtensionId(extension.getId(), extension.getExtensionTime());
			Extension oldExtension = extensionService.get(oldExtensionId);
			contract.setSignDate(oldExtension.getSignDate()); 
		}else{
			Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extension.getId());
			Loan oldLoan = loanService.get(oldLoanId);
			contract.setSignDate(oldLoan.getSignDate());  
		}
		contract.setContractName("委托扣款授权书（无风险基金的描述)");
		contract.setContact(person.getMobilePhone());
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contract.setLoanAgreeNum(extension.getExtensionTime().toString());//展期期数 
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(personBankAccount.getAccount());
		contract.setOpeningBank(personBankAccount.getBankName());
		contract.setBankBranchName(personBankAccount.getBranchName());
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		Long salesDepotId = extension.getSalesDeptId();
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
		contract.setTimes(extensionVO.getTime());
		contract.setPactMoney(extension.getAuditMoney());
		contractService.insertSelective(contract);
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

}
