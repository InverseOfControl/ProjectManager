/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ezendai.credit2.after.dao.BusinessAccountDao;
import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.service.BusinessAccountService;
import com.ezendai.credit2.after.service.OfferFileService;
import com.ezendai.credit2.after.vo.BusinessAccountVO;
import com.ezendai.credit2.after.vo.OfferFileVO;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ContacterService;
import com.ezendai.credit2.apply.service.CreditHistoryService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.PersonTrainingService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.PersonTrainingVO;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.SpecBusinessLog;
import com.ezendai.credit2.master.service.CreditService;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.master.service.SpecBusinessLogService;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 对公还款业务实现
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountServiceImpl.java, v 0.1 2014年12月11日 下午3:53:06 00221921 Exp $
 */
@Service
public class BusinessAccountServiceImpl implements BusinessAccountService {
	@Autowired
	private BusinessAccountDao businessAccountDao;
	@Autowired
	private LoanService loanService;
	@Autowired
	private SpecBusinessLogService specBusinessLogService;

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BankService bankService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonCompanyService personCompanyService;
	@Autowired
	private ContacterService contacterService;
	@Autowired
	private GuaranteeService guaranteeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CreditHistoryService creditHistoryService;
	@Autowired
	private SalesDepartmentService salesDeptService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private OfferFileService offerFileService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private LoanExtensionService loanExtensionService;
	
	@Autowired
	private OrganService organService;
	@Autowired
	ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	OrganSalesManagerService organSalesManagerService;
	
	
	@Autowired
	private PersonTrainingService personTrainingService;
	// 错误信息
	private String errorMsg;
	private static final String SLASH = File.separator;
	private static final String UNDERLINE = "_";
	// excel最大列数
	private static int EXCEL_MAX_COLUMN_SIZE = 11;
	
	private static int EXCEL_MAX_COLUMN_SIZE_NEW=10;

	private static int EXCEL_MAX_COLUMN_SIZE_CUR = 13;

	private String fullFileName;

	private static final Logger logger = Logger.getLogger(BusinessAccountServiceImpl.class);

	private static String EXCEL_SIZE_IS_EMPTY = "导入文件为空，不能导入";
	private static String EXCEL_SIZE_IS_OVER = "导入文件过大，超过50MB，不能导入";
	private static String FILE_TYPE_IS_ERROR = "导入文件类型错误，须为excel文件";
	//private static String COLUMN_NOT_RIGHT = "该行列数不正确";
	//private static String COLUMN_FIRST_ACCOUNT_IS_EMPTY = "[本方账号]不能为空";
	private static String COLUMN_AMOUNT_IS_EMPTY = "[贷方发生金额]不能为空";
	private static String COLUMN_REPAY_DATE_IS_EMPTY = "[交易日期]不能为空";
	private static String REPAY_DATE_FORMAT_IS_WRONG = "[交易日期]格式错误";
	private static String COLUMN_REPAY_TIME_IS_EMPTY = "[交易时间]不能为空";
	// private static String COLUMN_SECOND_COMPANY_IS_EMPTY = "[对方单位名称]不能为空";
	private static String COLUMN_HAS_EXIST = "该记录已经存在";
	private static String IMPORT_SUCCESS = "导入成功";
	private static String IMPORT_FAILED = "导入失败";
	/***
	 * 
	 * @param businessAccountVo
	 * @return 对公还款列表
	 * @see com.ezendai.credit2.after.service.BusinessAccountService#findWithPg(com.ezendai.credit2.after.vo.BusinessAccountVO)
	 */
	@Override
	public Pager findWithPg(BusinessAccountVO businessAccountVo) {
		return businessAccountDao.findWithPg(businessAccountVo);
	}

	/**
	 * 
	 * <pre>
	 * 对公还款->领取借款记录列表
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@Override
	public Pager findBusinessLoanListWithPg(LoanVO loanVo) {
		return loanService.findBusinessLoanListWithPg(loanVo);
	}

	/**
	 * 
	 * <pre>
	 * 特定业务日志
	 * </pre>
	 *
	 * @param specBusinessLogVo
	 * @return
	 */
	@Override
	public Pager findBusinessLogsListWithPg(SpecBusinessLogVO specBusinessLogVo) {
		return specBusinessLogService.findWithPg(specBusinessLogVo);
	}

	/** 
	 * @param businessAccountVo
	 * @return 查询出已认领的条数
	 * @see com.ezendai.credit2.after.service.OfferService#countExt(com.ezendai.credit2.after.vo.BusinessAccountVO)
	 */
	@Override
	public Integer receiveResultCount(BusinessAccountVO businessAccountVo) {
		return businessAccountDao.receiveResultCount(businessAccountVo);
	}

	/**
	 * 
	 * <pre>
	 * 查询出已认领的结果
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@Override
	public List<BusinessAccount> findReceiveResult(BusinessAccountVO businessAccountVo) {
		return businessAccountDao.findReceiveResult(businessAccountVo);
	}

	/**
	 * 
	 * <pre>
	 * 更新对公还款的状态
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@Override
	public int updateBusinessAccountStatus(BusinessAccountVO businessAccountVo) {
		return businessAccountDao.update(businessAccountVo);
	}

	/**
	 * 导出认领结果
	 * @param businessAccountList
	 * @param sheetName
	 * @param os
	 * @see com.ezendai.credit2.after.service.BusinessAccountService#exportReceiveResult(java.util.List, java.lang.String, java.io.OutputStream)
	 */
	@Override
	public void exportReceiveResult(List<BusinessAccount> businessAccountList, String sheetName, OutputStream os) {
		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[8];
		//列头
		column[0] = new String("借款人姓名     ");
		column[1] = new String("身份证号         ");
		column[2] = new String("产品名称       ");
		column[3] = new String("首次还款日       ");
		column[4] = new String("还款金额       ");
		column[5] = new String("还款方式 ");
		column[6] = new String("备注                                                                 ");
		column[7] = new String("借款ID ");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(businessAccountList)) {
			// 每一行要显示的内容
			for (BusinessAccount businessAccount : businessAccountList) {
				Long loanId = businessAccount.getLoanId();
				Long personId = null;
				Long productId = null;
				Date startRepayDate = null;
				Loan loan = loanService.get(loanId);
				if (loan != null) {
					personId = loan.getPersonId();
					productId = loan.getProductId();
					startRepayDate = loan.getStartRepayDate();

				} else {
					Extension extension = extensionService.get(loanId);
					if (extension != null) {
						personId = extension.getPersonId();
						productId = extension.getProductId();
						startRepayDate = extension.getStartRepayDate();
					}
				}
				Person person = personService.get(personId);
				if (person == null)
					continue;
				Product product = productService.get(productId);
				if (product == null)
					continue;
				String productName = product.getProductName();
				String[] row = new String[8];
				row[0] = new String(person.getName());
				row[1] = new String(person.getIdnum());
				row[2] = new String(productName);

				//首次还款日
				if (startRepayDate != null) {
					row[3] = new String(DateUtil.getDate(startRepayDate, "yyyy-MM-dd"));
				} else {
					row[3] = new String("");
				}
				//金额
				if (businessAccount.getAmount() != null) {
					//保留两位小数				
					row[4] = new String(businessAccount.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					row[4] = new String("");
				}
				//还款方式
				row[5] = new String("转账");
				//备注  对方单位 交易日期，交易时间 认领时间
				if (StringUtil.isNotBlank(businessAccount.getSecondCompany())) {
					row[6] = new String("对方单位：" + businessAccount.getSecondCompany() + "  交易时间: " + DateUtil.getDate(businessAccount.getRepayDate(), "yyyy-MM-dd") + " "
										+ businessAccount.getRepayTime() + "  认领日期:" + DateUtil.getDate(businessAccount.getRecTime(), "yyyy-MM-dd"));
				}
				row[7] = new String(loanId.toString());
				rows.add(row);
			}

		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		//生成Excel文件
		PoiExportExcel.poiBusinessAccountWriteExcel(exportExcelList, os);
	}

	/**
	 * 
	 * <pre>
	 * 显示车贷贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @param flag
	 * @return
	 */
	@Override
	public LoanDetailsVO toCarLoanDetail(Long loanId, String flag) {

		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		Loan loan = loanService.get(loanId);
		Person person = personService.get(loan.getPersonId());
		Product product = productService.get(loan.getProductId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		SysUser crm = sysUserService.get(loan.getCrmId());
		SysUser service = sysUserService.get(loan.getServiceId());
		SysUser assessor = sysUserService.get(loan.getAssessorId());

		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
		
		setGrantAndRepayAccount(loanDetailsVo, loan.getGrantAccountId(), loan.getRepayAccountId());
		

		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setVehicle((CollectionUtil.isNotEmpty(vehicleList)) ? vehicleList.get(0) : null);
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 显示车贷展期贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @param flag
	 * @return
	 */
	@Override
	public LoanDetailsVO toCarLoanExtensionDetail(Long loanId) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		LoanExtensionVO loanExtensionVO = new LoanExtensionVO();
		Loan loan = null;
		
		Extension extension = extensionService.get(loanId);
		Person person = personService.get(extension.getPersonId());
		Product product = productService.get(extension.getProductId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		SysUser crm = sysUserService.get(extension.getCrmId());
		SysUser service = sysUserService.get(extension.getServiceId());
		
		loanExtensionVO.setExtensionId(loanId);
		
		List<LoanExtension> loanExtensionList = loanExtensionService.findListByVo(loanExtensionVO);
		if (loanExtensionList.size() == 1) {
			loan = loanService.get(loanExtensionList.get(0).getLoanId());
		}

		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(extension.getSalesDeptId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
		
		setGrantAndRepayAccount(loanDetailsVo, extension.getGrantAccountId(), extension.getRepayAccountId());

		loanDetailsVo.setExtension(extension);
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setVehicle((CollectionUtil.isNotEmpty(vehicleList)) ? vehicleList.get(0) : null);
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 显示小企业贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @param flag
	 * @return
	 */
	@Override
	public LoanDetailsVO toBusinessLoanDetail(Long loanId, String flag) {

		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		Loan loan = loanService.get(loanId);
		Person person = personService.get(loan.getPersonId());
		Product product = productService.get(loan.getProductId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		SysUser crm = sysUserService.get(loan.getCrmId());

		SysUser service = sysUserService.get(loan.getServiceId());
		SysUser assessor = sysUserService.get(loan.getAssessorId());
		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
		
		populateCompany(company);
		populateLoanInfo(loan);

		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setContacterList(contacterList);
		populatePersonTraining(loanDetailsVo,loan,person);
		setGrantAndRepayAccount(loanDetailsVo, loan.getGrantAccountId(), loan.getRepayAccountId());

//		if (loan.getGrantAccountId() != null) {
//			BankAccount grantBankAccount = bankAccountService.get(loan.getGrantAccountId());
//			if (grantBankAccount != null) {
//				loanDetailsVo.setGrantAccount(grantBankAccount);
//
//			}
//		}
//		if (loan.getRepayAccountId() != null) {
//			BankAccount repayBankAccount = bankAccountService.get(loan.getRepayAccountId());
//			if (repayBankAccount != null) {
//				loanDetailsVo.setRepayAccount(repayBankAccount);
//			}
//		}

		if (CollectionUtil.isNotEmpty(guaranteeList)) {
			List<Guarantee> hasGuaranteeList = new ArrayList<Guarantee>();
			for (Guarantee guarantee : guaranteeList) {
				if (guarantee.getFlag() != null) {//选中的担保人
					hasGuaranteeList.add(guarantee);
				}
			}
			if (CollectionUtil.isNotEmpty(hasGuaranteeList)) {
				loanDetailsVo.setGuaranteeList(hasGuaranteeList);
			}

		}
		return loanDetailsVo;
	}
	/**
	 * 添加客户培训信息
	 * @param loanDetailsVo
	 * @param loan
	 * @param person
	 */
	private void populatePersonTraining(LoanDetailsVO loanDetailsVo,Loan loan,Person person){
		
		PersonTrainingVO personTrainingVO = new PersonTrainingVO();
		personTrainingVO.setLoanId(loan.getId());
		personTrainingVO.setPersonIdnum(person.getIdnum());
		List<PersonTraining> personTrainingList = personTrainingService.findListByVo(personTrainingVO );
		
		if (CollectionUtil.isNotEmpty(personTrainingList)) {
			loanDetailsVo.setPersonTraining(personTrainingList.get(0));
		}
	}

	@Override
	public boolean receive(Long businessAccountId) {
		BusinessAccount businessAccount = businessAccountDao.get(businessAccountId);
		if (businessAccount != null) {
			//判断如果该笔对公流水为未认领
			if (businessAccount.getStatus() != null && businessAccount.getStatus().equals(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public String confirmReceive(BusinessAccountVO businessAccountVo) {
		String result = null;
		try {
			String repayDate = DateUtil.defaultFormatDay(businessAccountVo.getRepayDate());
			String repayTime = businessAccountVo.getRepayTime();
			String secondCompany = businessAccountVo.getSecondCompany();
			String amount = String.valueOf(businessAccountVo.getAmount());
			String type = String.valueOf(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue());
			logger.info("confirmReceive repayDate:" + repayDate + ",repayTime:" + repayTime + ",secondCompany:" + secondCompany + ",amount:" + amount + ",type:" + type);
			BusinessAccount businessAccount=businessAccountDao.get(businessAccountVo.getId());
			String sendMessage = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				sendMessage = creditService.sendBusinessAccountData(repayDate, repayTime, secondCompany, amount, type);
				logger.info("sendMessage:" + sendMessage);
			}else{
				sendMessage="SUCCESS";
			}
			if (StringUtils.endsWithIgnoreCase(sendMessage, "SUCCESS")) {
				//确认领取操作
				businessAccountVo.setRepayDate(null);
				businessAccountVo.setRepayTime(null);
				businessAccountVo.setSecondCompany(null);
				businessAccountVo.setAmount(null);
				businessAccountVo.setRecOperatorId(ApplicationContext.getUser().getId());
				businessAccountVo.setRecTime(new Date());
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
				int affectNum = businessAccountDao.update(businessAccountVo);
				//如果更新成功则插入业务日志,系统日志
				if (affectNum == 1) {
					SpecBusinessLog specBusinessLog = new SpecBusinessLog();
					specBusinessLog.setKeyId(businessAccountVo.getId());
					specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
					specBusinessLog.setMessage("认领");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.RECEIVE.getValue());
					specBusinessLogService.insert(specBusinessLog);

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
					sysLog.setOptType(EnumConstants.OptionType.CONFIRM_RECEIVE.getValue());
					sysLogService.insert(sysLog);
					result = "success";
				} else {
					result = "确认领取更新businessAcount记录失败";
				}
			} else {
				logger.error("confirmReceive sendCreditMapData.sendMessage:" + sendMessage);
				result = "调用个贷对公账户接口错误:" + sendMessage;
			}
		} catch (Exception ex) {
			logger.error("confirmReceive 错误:  ", ex);
			result = "confirmReceive 错误:" + ex.getMessage();
		}
		return result;
	}

	@Override
	@Transactional
	public String confirmReceiveCancel(BusinessAccountVO businessAccountVo) {
		String result = null;
		try {
			String repayDate = DateUtil.defaultFormatDay(businessAccountVo.getRepayDate());
			String repayTime = businessAccountVo.getRepayTime();
			String secondCompany = businessAccountVo.getSecondCompany();
			String amount = String.valueOf(businessAccountVo.getAmount());
			String type = String.valueOf(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue());
			logger.info("confirmReceiveCancel repayDate:" + repayDate + ",repayTime:" + repayTime + ",secondCompany:" + secondCompany + ",amount:" + amount + ",type:" + type);
			BusinessAccount businessAccount=businessAccountDao.get(businessAccountVo.getId());
			String sendMessage = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				sendMessage = creditService.sendBusinessAccountData(repayDate, repayTime, secondCompany, amount, type);
				logger.info("sendMessage:" + sendMessage);
			}else{
				sendMessage="SUCCESS";
			}
			if (StringUtils.endsWithIgnoreCase(sendMessage, "SUCCESS")) {
				//确认领取撤销操作
				businessAccountVo.setRepayDate(null);
				businessAccountVo.setRepayTime(null);
				businessAccountVo.setSecondCompany(null);
				businessAccountVo.setAmount(null);
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
				int affectNum = businessAccountDao.update(businessAccountVo);
				//如果更新成功则插入业务日志,系统日志
				if (affectNum == 1) {
					SpecBusinessLog specBusinessLog = new SpecBusinessLog();
					specBusinessLog.setKeyId(businessAccountVo.getId());
					specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
					specBusinessLog.setMessage("撤销");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CANCEL.getValue());
					specBusinessLogService.insert(specBusinessLog);

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
					sysLog.setOptType(EnumConstants.OptionType.CANCEL_RECEIVE.getValue());
					sysLogService.insert(sysLog);
					result = "success";
				} else {
					result = "确认领取更新businessAcount记录失败";
				}
			} else {
				logger.error("confirmReceiveCancel sendCreditMapData.sendMessage:" + sendMessage);
				result = "调用个贷对公账户接口错误:" + sendMessage;
			}
		} catch (Exception ex) {
			logger.error("confirmReceiveCancel 错误:  ", ex);
			result = "confirmReceiveCancel 错误:" + ex.getMessage();
		}
		return result;
	}

	@Override
	@Transactional
	public String withoutClaim(BusinessAccountVO businessAccountVo) {
		String result = null;
		try {
			String repayDate = DateUtil.defaultFormatDay(businessAccountVo.getRepayDate());
			String repayTime = businessAccountVo.getRepayTime();
			String secondCompany = businessAccountVo.getSecondCompany();
			String amount = String.valueOf(businessAccountVo.getAmount());
			String type = String.valueOf(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue());
			logger.info("withoutClaim repayDate:" + repayDate + ",repayTime:" + repayTime + ",secondCompany:" + secondCompany + ",amount:" + amount + ",type:" + type);
			BusinessAccount businessAccount=businessAccountDao.get(businessAccountVo.getId());
			String sendMessage = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				sendMessage = creditService.sendBusinessAccountData(repayDate, repayTime, secondCompany, amount, type);
				logger.info("sendMessage:" + sendMessage);
			}else{
				sendMessage="SUCCESS";
			}
			if (StringUtils.endsWithIgnoreCase(sendMessage, "SUCCESS")) {
				//无需认领操作
				businessAccountVo.setRepayDate(null);
				businessAccountVo.setRepayTime(null);
				businessAccountVo.setSecondCompany(null);
				businessAccountVo.setAmount(null);
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NOT_NEED_RECEIVE.getValue());
				int affectNum = businessAccountDao.update(businessAccountVo);
				//如果更新成功则插入业务日志,系统日志
				if (affectNum == 1) {
					SpecBusinessLog specBusinessLog = new SpecBusinessLog();
					specBusinessLog.setKeyId(businessAccountVo.getId());
					specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
					specBusinessLog.setMessage("无需认领");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.NOT_NEED_RECEIVE.getValue());
					specBusinessLogService.insert(specBusinessLog);

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
					sysLog.setOptType(EnumConstants.OptionType.CHANNEL_CONFIRM.getValue());
					sysLogService.insert(sysLog);
					result = "success";
				} else {
					result = "确认领取更新businessAcount记录失败";
				}
			} else {
				logger.error("withoutClaim sendCreditMapData.sendMessage:" + sendMessage);
				result = "调用个贷对公账户接口错误:" + sendMessage;
			}
		} catch (Exception ex) {
			logger.error("withoutClaim 错误:  ", ex);
			result = "withoutClaim 错误:" + ex.getMessage();
		}
		return result;
	}

	@Override
	@Transactional
	public String withoutClaimCancel(BusinessAccountVO businessAccountVo) {
		String result = null;
		try {
			String repayDate = DateUtil.defaultFormatDay(businessAccountVo.getRepayDate());
			String repayTime = businessAccountVo.getRepayTime();
			String secondCompany = businessAccountVo.getSecondCompany();
			String amount = String.valueOf(businessAccountVo.getAmount());
			String type = String.valueOf(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue());
			logger.info("withoutClaimCancel repayDate:" + repayDate + ",repayTime:" + repayTime + ",secondCompany:" + secondCompany + ",amount:" + amount + ",type:" + type);
			BusinessAccount businessAccount=businessAccountDao.get(businessAccountVo.getId());
			String sendMessage = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				sendMessage = creditService.sendBusinessAccountData(repayDate, repayTime, secondCompany, amount, type);
				logger.info("sendMessage:" + sendMessage);
			}else{
				sendMessage="SUCCESS";
			}
			if (StringUtils.endsWithIgnoreCase(sendMessage, "SUCCESS")) {
				//无需认领操作
				businessAccountVo.setRepayDate(null);
				businessAccountVo.setRepayTime(null);
				businessAccountVo.setSecondCompany(null);
				businessAccountVo.setAmount(null);
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
				int affectNum = businessAccountDao.update(businessAccountVo);
				//如果更新成功则插入业务日志,系统日志
				if (affectNum == 1) {
					SpecBusinessLog specBusinessLog = new SpecBusinessLog();
					specBusinessLog.setKeyId(businessAccountVo.getId());
					specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
					specBusinessLog.setMessage("撤销");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CANCEL.getValue());
					specBusinessLogService.insert(specBusinessLog);

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
					sysLog.setOptType(EnumConstants.OptionType.CANCEL_CHANNEL_CONFIRM.getValue());
					sysLogService.insert(sysLog);
					result = "success";
				} else {
					result = "确认领取更新businessAcount记录失败";
				}
			} else {
				logger.error("withoutClaimCancel sendCreditMapData.sendMessage:" + sendMessage);
				result = "调用个贷对公账户接口错误:" + sendMessage;
			}
		} catch (Exception ex) {
			logger.error("withoutClaimCancel 错误:  ", ex);
			result = "withoutClaimCancel 错误:" + ex.getMessage();
		}
		return result;
	}

	/** 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 * @see com.ezendai.credit2.after.service.BusinessAccountService#batchUpload(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@Transactional
	public Map<String, Object> batchUpload(HttpServletRequest request, HttpServletResponse response) {
		errorMsg = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取待上传文件myFile
			MultipartFile myFile = multipartRequest.getFile("file");
			if (myFile != null) {
				// 获取文件myFile原始名称
				String originalFilename = myFile.getOriginalFilename();
				logger.info( "bytes:"+new String(originalFilename.getBytes(),"UTF-8"));
				System.out.println("encoder:"+java.net.URLEncoder.encode(originalFilename, "UTF-8"));
				logger.info("对公还款文件上传 originalFilename："+originalFilename);
				// 获取文件myFile后缀名
				String suffixName = FileUtil.getSuffixName(originalFilename);

				// 第一步：验证excel文件，通过 —> 第二步：上传至服务器 
				if (validateUploadFile(myFile, originalFilename, suffixName) && saveExcelToServerAndOfferFile(myFile, suffixName)) {
					InputStream inputStream = myFile.getInputStream();

					// 第三步：解析excel文件
					Workbook workBook = null;
					// Excel 2003
					if (StringUtils.equals("xls", suffixName)) {
						workBook = new HSSFWorkbook(inputStream);
					}
					// Excel 2007
					else if (StringUtils.equals("xlsx", suffixName)) {
						workBook = new XSSFWorkbook(inputStream);
					}
					// 循环sheet
					for (int sheetNum = 0; sheetNum < workBook.getNumberOfSheets(); sheetNum++) {
						Sheet sheet = workBook.getSheetAt(sheetNum);
						if (sheet == null) {
							continue;
						}
						
						Row firstRow = sheet.getRow(0);
						if (firstRow == null) {
							continue;
						}
						// 标题行(第一行)的列数
						short firRowCellNum = firstRow.getLastCellNum();
						// 非当天模板 11列
						if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE) {
							firstRow.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue("反馈结果");
						}
						// 当天模板 13列
						else if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE_CUR) {
							firstRow.createCell(EXCEL_MAX_COLUMN_SIZE_CUR).setCellValue("反馈结果");
						}
						// 当天模板10列
						else if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE_NEW){
							firstRow.createCell(EXCEL_MAX_COLUMN_SIZE_NEW).setCellValue("反馈结果");
						}
						// 创建导入失败德行头显示样式
						Font error_cell_font = workBook.createFont();
						error_cell_font.setColor(Font.COLOR_RED);
						CellStyle error_cell_style = workBook.createCellStyle();
						error_cell_style.setFont(error_cell_font);

						BusinessAccount businessAccount = new BusinessAccount();
						// 循环单个sheet中的row
						for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
							Row row = sheet.getRow(rowNum);
							if (row == null) {
								continue;
							}
							// 对excel每行记录数据进行有效性验证
							String validateExcelData = validateExcelData(row, firRowCellNum, businessAccount);
							// 数据有效性验证未通过
							if (!StringUtils.isEmpty(validateExcelData)) {
								if (row.getCell(0) != null) {
									row.getCell(0).setCellStyle(error_cell_style);
								}
								row.createCell(firRowCellNum).setCellValue(validateExcelData);
								continue;
							}
							// 数据检验通过,向Business_Account表插入记录
							else {
								businessAccount.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
								businessAccount.setVersion(1L);
								BusinessAccount isSuccessed = businessAccountDao.insert(businessAccount);
								if (isSuccessed != null) {
									row.createCell(firRowCellNum).setCellValue(IMPORT_SUCCESS);
								} else {
									row.createCell(firRowCellNum).setCellValue(IMPORT_FAILED);
								}
							}
						}
					}
					logger.info("originalFilename:" + originalFilename);
					// 写入输出文件
					response.reset();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-disposition", "attachment; filename=" + getFilename(originalFilename, request));
					OutputStream out = response.getOutputStream();
					workBook.write(out);
					out.flush();
					out.close();

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
					sysLog.setOptType(EnumConstants.OptionType.BUSINESS_ACCOUNT_BATCH_UPLOAD.getValue());
					sysLog.setRemark("对公还款之批量导入");
					sysLogService.insert(sysLog);
				}
			} else {
				setErrorMsg("上传文件为空!");
			}
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			logger.error("文件上传异常: ", e);
		}
		resultMap.put("ErrorMsg", this.errorMsg);
		return resultMap;
	}

	/**
	 * 
	 * <pre>
	 * 对上传文件进行验证（文件内容是否为空，大小是否过大，类型格式是否错误，文件名是否已存在）
	 * </pre>
	 *
	 * @param myFile	源文件
	 * @param originalFilename	原始文件名
	 * @param suffix	后缀名
	 * @return
	 */
	private boolean validateUploadFile(MultipartFile myFile, String originalFilename, String suffix) {
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.BATCH_UPLOAD_SIZE_LIMIT.name());
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			setErrorMsg("没有配置上传文件大小!");
			return false;
		}
		int size = Integer.parseInt(sysParameter.getParameterValue());
		try {
			// 文件内容为空，不能导入
			if (myFile != null && myFile.getBytes().length <= 0) {
				setErrorMsg(EXCEL_SIZE_IS_EMPTY);
				return false;
			}
			// 文件大小过大，不能导入
			else if (myFile.getBytes().length > 1024 * 1024 * size) {
				setErrorMsg(EXCEL_SIZE_IS_OVER);
				return false;
			}
			// 文件类型格式错误，不能导入
			else if (!StringUtils.equalsIgnoreCase("xls", suffix) && !StringUtils.equalsIgnoreCase("xlsx", suffix)) {
				setErrorMsg(FILE_TYPE_IS_ERROR);
				return false;
			} else {
				OfferFileVO offerFileVO = new OfferFileVO();
				offerFileVO.setOriginalName(originalFilename);
				offerFileVO.setType(EnumConstants.OfferFileType.BUSINESS_ACCOUNT.getValue());
				logger.info("对公还款 导入数据 查询验证：setOriginalName"+originalFilename +" , settype:"+EnumConstants.OfferFileType.BUSINESS_ACCOUNT.getValue());
				List<OfferFile> offerFileList = offerFileService.findListByVo(offerFileVO);
				//当前文件名已经存在，不能导入
				if (offerFileList != null && offerFileList.size() > 0) {
					setErrorMsg("文件已经存在,请重命名!");
					return false;
				}
				return true;
			}
		} catch (IOException e) {
			setErrorMsg(e.getMessage());
			return false;
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			logger.error("文件进行验证异常: ", e);
			return false;
		}
	}

	/**
	 * 
	 * <pre>
	 * 保存excel文件至服务器，并向OfferFile表插入记录
	 * </pre>
	 *
	 * @param myFile
	 * @param suffix
	 * @return
	 */
	private boolean saveExcelToServerAndOfferFile(MultipartFile myFile, String suffix) {
		Date nowDate = new Date();
		String defaultFormatMinutesDate = DateUtil.defaultFormatMinutesDate(nowDate);
		String uuid = CommonUtil.getUUID();
		String saveDir = credit2Properties.getUploadPath();
		String fileTruthName = defaultFormatMinutesDate + UNDERLINE + uuid;
		// 新的文件名
		String fullFileTruthName = fileTruthName + "." + suffix;
		fullFileName = fullFileTruthName;
		//文件最终保存的路径
		String fullSavePath = saveDir + SLASH + EnumConstants.OfferFileType.BUSINESS_ACCOUNT.toString() + SLASH + DateUtil.defaultFormatDay(nowDate) + SLASH + fullFileTruthName;
		File saveFile = new File(fullSavePath);
		try {
			// 开始上传
			FileUtils.writeByteArrayToFile(saveFile, myFile.getBytes());
		} catch (IOException ex) {
			setErrorMsg(ex.getMessage());
			return false;
		} catch (Exception ex) {
			setErrorMsg(ex.getMessage());
			return false;
		}
		OfferFile offerFile = new OfferFile();
		offerFile.setName(fullFileTruthName);
		offerFile.setOriginalName(myFile.getOriginalFilename());
		offerFile.setType(EnumConstants.OfferFileType.BUSINESS_ACCOUNT.getValue());
		offerFile.setCreatedDate(nowDate);
		// 向OfferFile表插入记录
		offerFileService.insert(offerFile);
		return true;
	}
	/**
	 * <pre>
	 * 验证Excel的数据,返回错误信息,如正确返回为空
	 * </pre>
	 *
	 * @param xssfRow
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public String validateExcelData(Row row, short firRowCellNum, BusinessAccount businessAccount) {
		String result = null;
		/** 本方账号 */
		String firstAccount;
		/** 对方账号 */
		String secondAccount;
		/** 对方单位名称 */
		String secondCompany = null;
		/** 对方行号 */
		String secondBank;
		/** 交易日期 */
		Date repayDate = null;
		/** 交易时间 */
		String repayTime = null;
		/** 贷方发生金额 */
		BigDecimal amount = null;
		/** 凭证号 */
		String voucherNo;
		/** 借/贷类型 */
		//Integer type;
		/** 用途 */
		String purpose;
		/** 摘要 */
		String remark;
		/** 附言 */
		String comments;
		
		SysParameter grantBatchUpload = sysParameterService.getByCode(SysParameterEnum.GRANT_BATCH_UPLOAD.name());
		if(firRowCellNum == EXCEL_MAX_COLUMN_SIZE_NEW){
			for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
				Cell cell = row.getCell(cellNum);
				// 交易日期
				if (cellNum == 0) {
					String repayDateStr = getValue(cell);
					// 老模板,对交易日期列进行校验
						if (StringUtils.isEmpty(repayDateStr)) {
							return COLUMN_REPAY_DATE_IS_EMPTY;
						} else if (!DateUtil.isValidDate(repayDateStr)) {
							return REPAY_DATE_FORMAT_IS_WRONG;
						}
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						repayDate = sdf.parse(repayDateStr);
					} catch (ParseException e) {
						return e.getMessage();
					}
					businessAccount.setRepayDate(repayDate);
				}// 交易时间
				else if (cellNum == 1) {
					if (cell != null && cell.getCellType() != cell.CELL_TYPE_STRING) {
						return "交易时间列格式错误,应为文本格式";
					}
					repayTime = getValue(cell);
					if (StringUtils.isEmpty(repayTime)) {
						return COLUMN_REPAY_TIME_IS_EMPTY;
					}
					// 将 [非数字] 字符替换为":"
					repayTime = repayTime.trim().replaceAll("\\D", ":");

					try {
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						repayTime = DateUtil.defaultFormatHMS(sdf.parse(repayTime));
					} catch (ParseException e) {
						return e.getMessage();
					}
					businessAccount.setRepayTime(repayTime.trim().replace(" ", ""));
				}// 贷方金额
				else if (cellNum == 3) {
					String amountStr = getValue(cell);
					if (StringUtils.isEmpty(amountStr)) {
						return COLUMN_AMOUNT_IS_EMPTY;
					}
					amount = new BigDecimal(amountStr);
					businessAccount.setType(EnumConstants.BorrowOrLoan.Loan.getValue());
					businessAccount.setAmount(amount);
				}
				// 对方账号
				else if (cellNum == 5) {
					secondAccount = getValue(cell);
					businessAccount.setSecondAccount(secondAccount.trim().replace(" ", ""));
				}// 对方单位
				else if (cellNum == 6) {
					secondCompany = getValueString(cell);
//					secondCompany = String.valueOf(cell.getStringCellValue());
					//				if (StringUtils.isEmpty(secondCompany)) {
					//					return COLUMN_SECOND_COMPANY_IS_EMPTY;
					//				}
					businessAccount.setSecondCompany(secondCompany.trim());
				}
				// 凭证号
				else if (cellNum == 7) {
					voucherNo = getValue(cell);
					businessAccount.setVoucherNo(voucherNo);
				}
				// 流水号
				else if (cellNum == 8) {
					comments = getValue(cell);
					businessAccount.setComments(comments.trim().replace(" ", ""));
				}
				// 摘要
				else if (cellNum == 9) {
					remark = getValue(cell);
					businessAccount.setRemark(remark.trim().replace(" ", ""));
				}
				if(grantBatchUpload!=null){
					businessAccount.setFirstAccount(grantBatchUpload.getParameterValue().trim().replace(" ", ""));
				}else{
					businessAccount.setFirstAccount("36510188000802176");
				}
			}
		}else{
			for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
				Cell cell = row.getCell(cellNum);
				// 交易日期
				if (cellNum == 0) {
					String repayDateStr = getValue(cell);
					// 老模板,对交易日期列进行校验
					if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE) {
						if (StringUtils.isEmpty(repayDateStr)) {
							return COLUMN_REPAY_DATE_IS_EMPTY;
						} else if (!DateUtil.isValidDate(repayDateStr)) {
							return REPAY_DATE_FORMAT_IS_WRONG;
						}
					}
					// 新模板,对交易日期列设为当前日期
					else if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE_CUR) {
						cell = row.createCell(0);
						cell.setCellValue(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
						repayDateStr = getValue(cell);
					}
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						repayDate = sdf.parse(repayDateStr);
					} catch (ParseException e) {
						return e.getMessage();
					}
					businessAccount.setRepayDate(repayDate);
				}
				// 本方账号
				else if (cellNum == 1) {
					firstAccount = getValue(cell);
					//				if (StringUtils.isEmpty(firstAccount)) {
					//					return COLUMN_FIRST_ACCOUNT_IS_EMPTY;
					//				}
					businessAccount.setFirstAccount(firstAccount.trim().replace(" ", ""));
				}
				// 对方账号
				else if (cellNum == 2) {
					secondAccount = getValue(cell);
					businessAccount.setSecondAccount(secondAccount.trim().replace(" ", ""));
				}
				// 交易时间
				else if (cellNum == 3) {
					if (cell != null && cell.getCellType() != cell.CELL_TYPE_STRING) {
						return "交易时间列格式错误,应为文本格式";
					}
					repayTime = getValue(cell);
					if (StringUtils.isEmpty(repayTime)) {
						return COLUMN_REPAY_TIME_IS_EMPTY;
					}
					// 将 [非数字] 字符替换为":"
					repayTime = repayTime.trim().replaceAll("\\D", ":");
	
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						repayTime = DateUtil.defaultFormatHMS(sdf.parse(repayTime));
					} catch (ParseException e) {
						return e.getMessage();
					}
	
					businessAccount.setRepayTime(repayTime.trim().replace(" ", ""));
				}
				// 借/贷
				else if (cellNum == 4) {
					String typeName = getValue(cell);
					if (typeName != null && StringUtils.equalsIgnoreCase(typeName, "借")) {
						businessAccount.setType(EnumConstants.BorrowOrLoan.Borrow.getValue());
					} else if (typeName != null && StringUtils.equalsIgnoreCase(typeName, "贷")) {
						businessAccount.setType(EnumConstants.BorrowOrLoan.Loan.getValue());
					}
				}
				// 金额
				else if (cellNum == 5) {
					String amountStr = getValue(cell);
					if (StringUtils.isEmpty(amountStr)) {
						return COLUMN_AMOUNT_IS_EMPTY;
					}
					amount = new BigDecimal(amountStr);
					businessAccount.setAmount(amount);
				}
				// 凭证号
				else if (cellNum == 6) {
					voucherNo = getValue(cell);
					businessAccount.setVoucherNo(voucherNo);
				}
				// 对方单位
				else if (cellNum == 7) {
					secondCompany = getValueString(cell);
	//				secondCompany = String.valueOf(cell.getStringCellValue());
					//				if (StringUtils.isEmpty(secondCompany)) {
					//					return COLUMN_SECOND_COMPANY_IS_EMPTY;
					//				}
					businessAccount.setSecondCompany(secondCompany.trim());
				}
				// 对方行号
				else if (cellNum == 8) {
					secondBank = getValue(cell);
					businessAccount.setSecondBank(secondBank.trim().replace(" ", ""));
				}
				// 用途
				else if (cellNum == 9) {
					purpose = getValue(cell);
					businessAccount.setPurpose(purpose.trim().replace(" ", ""));
				}
				// 摘要
				else if (cellNum == 10) {
					remark = getValue(cell);
					businessAccount.setRemark(remark.trim().replace(" ", ""));
				}
				// 新模板的附言
				else if (firRowCellNum == EXCEL_MAX_COLUMN_SIZE_CUR && cellNum == 11) {
					comments = getValue(cell);
					businessAccount.setComments(comments.trim());
				}
			}
		}
		
		if (!validateInsertBusinessAccount(repayDate, repayTime, amount, secondCompany)) {
			return COLUMN_HAS_EXIST;
		}

		return result;
	}

	/** 
	 * 
	 * <pre>
	 * 校验是否可以插入BusinessAccount
	 * </pre>
	 *
	 * @param repayDate
	 * @param repayTime
	 * @param amount
	 * @param secondCompany
	 * @return
	 */
	private boolean validateInsertBusinessAccount(Date repayDate, String repayTime, BigDecimal amount, String secondCompany) {
		BusinessAccountVO businessAccountVO = new BusinessAccountVO();
		businessAccountVO.setRepayDate(repayDate);
		businessAccountVO.setRepayTime(repayTime);
		businessAccountVO.setAmount(amount);
		businessAccountVO.setSecondCompany(secondCompany);
		List<BusinessAccount> businessAccountList = businessAccountDao.findListByVo(businessAccountVO);
		if (businessAccountList == null || businessAccountList.size() == 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-access")
	private String getValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
				// 此处主要是为了将 0.0 转为 0
				if (cell.getNumericCellValue() == 0) {
					return String.valueOf(0);
				} else {
					return String.valueOf(cell.getNumericCellValue());
				}
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("static-access")
	private String getValueString(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
				// 此处主要是为了将 0.0 转为 0
				if (cell.getNumericCellValue() == 0) {
					return String.valueOf(0);
				} else {
					DecimalFormat df = new DecimalFormat("0"); 
					return df.format(cell.getNumericCellValue());
				}
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		} else {
			return "";
		}
	}

	/**
	 * 读取文件名
	 * 
	 * @param name
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getFilename(String name, HttpServletRequest request) throws UnsupportedEncodingException {
		if (request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > -1) {
			return new String(name.getBytes("utf-8"), "iso8859-1");
		} else {
			return URLEncoder.encode(name, "utf-8");
		}
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	@Override
	@Transactional
	public String updateBusinessAccountStatus(HttpServletRequest request) {
		String result = null;
		try {
			if (!StringUtils.isEmpty(request.getParameter("repayDate")) && !StringUtils.isEmpty(request.getParameter("repayTime")) && !StringUtils.isEmpty(request.getParameter("amount"))
				&& !StringUtils.isEmpty(request.getParameter("type"))) {
				Date repayDate = DateUtil.strToDate(request.getParameter("repayDate"), null);
				String repayTime = request.getParameter("repayTime");
				String secondCompany = URLDecoder.decode(request.getParameter("secondCompany"), "utf-8");
				BigDecimal amount = new BigDecimal(request.getParameter("amount"));
				Integer type = Integer.valueOf(request.getParameter("type"));
				BusinessAccountVO businessAccountVO = new BusinessAccountVO();
				businessAccountVO.setRepayDate(repayDate);
				businessAccountVO.setRepayTime(repayTime);
				if (!StringUtils.equalsIgnoreCase(secondCompany, "null")) {
					businessAccountVO.setSecondCompany(secondCompany);
				}
				businessAccountVO.setAmount(amount);
				List<BusinessAccount> businessAccountList = businessAccountDao.findListByVo(businessAccountVO);
				logger.info("businessAccountList size:" + businessAccountList.size());
				if (businessAccountList != null && businessAccountList.size() == 1) {
					Long businessAccountId = businessAccountList.get(0).getId();
					Integer status = businessAccountList.get(0).getStatus();
					//操作类型为确认领取,且对公流水记录状态为未认领
					if ((type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue()))
						|| (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.CREDIT_RECEIVE.getValue()))
						|| (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue()))
						|| (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.CREDIT_CHANNEL_CONFIRM
							.getValue()))) {
						logger.info("businessAccountId:" + businessAccountId + ",type:" + type + ",status:" + status);
						result = processBusinessAccountStatus(businessAccountId, type);
					} else {
						//判断如果是确认领取且状态是被个贷已认领,则直接返回success
						if ((type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.CREDIT_RECEIVE.getValue()))) {
							result = "SUCCESS";
						} else {
							result = "type or status not right";
						}
					}

				} else {
					result = "record not equal 1";
				}
			} else {
				result = "param is empty";
			}
		} catch (Exception ex) {
			logger.error("updateBusinessAccountStatus :" + ex.getMessage());
			result = "updateBusinessAccountStatus Error";
		}
		return result;
	}

	private String processBusinessAccountStatus(Long businessAccountId, Integer type) {
		String result = null;
		BusinessAccountVO businessAccountVo = new BusinessAccountVO();
		if (type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.CREDIT_RECEIVE.getValue());

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.CREDIT_CHANNEL_CONFIRM.getValue());

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
		}
		logger.info("businessAccountDao update start businessAccountId" + businessAccountId + ",type:" + type);
		int affectNum = businessAccountDao.update(businessAccountVo);
		logger.info("affectNum : " + affectNum);

		if (affectNum == 1) {
			SpecBusinessLog specBusinessLog = new SpecBusinessLog();
			specBusinessLog.setKeyId(businessAccountVo.getId());
			specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
			if (type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue())) {
				specBusinessLog.setMessage("被个贷认领");
				specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_RECEIVE.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue())) {
				specBusinessLog.setMessage("撤销");
				specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CANCEL.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue())) {
				specBusinessLog.setMessage("被个贷渠道确认");
				specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CHANNEL_CONFIRM.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue())) {
				specBusinessLog.setMessage("撤销");
				specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CANCEL.getValue());
			}
			specBusinessLogService.insert(specBusinessLog);
			logger.info("specBusinessLogService insert end");
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
			if (type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue())) {
				sysLog.setOptType(EnumConstants.OptionType.CONFIRM_RECEIVE.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue())) {
				sysLog.setOptType(EnumConstants.OptionType.CANCEL_RECEIVE.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue())) {
				sysLog.setOptType(EnumConstants.OptionType.CHANNEL_CONFIRM.getValue());
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue())) {
				sysLog.setOptType(EnumConstants.OptionType.CANCEL_CHANNEL_CONFIRM.getValue());
			}
			sysLogService.insert(sysLog);
			logger.info("sysLogService insert end");
			result = "SUCCESS";
		}

		return result;
	}

	@Override
	@Transactional
	public String exportReceiveData(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			BusinessAccountVO businessAccountVo = new BusinessAccountVO();
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
			//已经认领的
			List<BusinessAccount> businessAccountList = businessAccountDao.findListByVo(businessAccountVo);
			int resultCount = businessAccountList.size();
			logger.info("resultCount:" + resultCount);
			if (resultCount <= 0) {
				result = "没有已领取导出记录!";
				return result;
			} else {
				SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
				if (sysParameter != null) {
					if (resultCount > Integer.parseInt(sysParameter.getParameterValue())) {
						result = "Excel导出数据过多!";
						return result;
					}
				}
			}

			//文件名 
			String fileName = "认领结果导出" + DateUtil.getDate(new Date(), "yyyy-MM-dd") + ".xlsx";
			logger.info("fileName:" + fileName);
			String downloadPath = credit2Properties.getDownloadPath();
			File file = new File(downloadPath + File.separator + "businessAccount");
			if (!file.exists()) {//不存在则创建该目录
				file.mkdir();
			}
			OutputStream os = new FileOutputStream(downloadPath + File.separator + "businessAccount" + File.separator + fileName.trim().toString());
			//生成Excel文件			
			exportReceiveResult(businessAccountList, "认领结果", os);
			logger.info("exportReceiveResult finished");
			//下载Excel文件的路径
			String filePath = downloadPath + File.separator + "businessAccount" + File.separator + fileName;
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//更新为已导出状态
				updateBusinessAccount(businessAccountList);
				//创建日志
				insertLogs(businessAccountList);
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
			} else {
				result = "Excel 下载出错!";
			}

		} catch (Exception ex) {
			logger.error("exportReceiveData :" + ex);
			result = "exportReceiveData Error";
		}
		return result;
	}

	//更新为已导出状态
	private void updateBusinessAccount(List<BusinessAccount> businessAccountList) {
		for (BusinessAccount businessAccount : businessAccountList) {
			BusinessAccountVO businessVo = new BusinessAccountVO();
			businessVo.setId(businessAccount.getId());
			businessVo.setStatus(EnumConstants.BusinessAccountStatus.EXPORT.getValue());
			updateBusinessAccountStatus(businessVo);
		}
	}

	//记录特殊业务日志 记录系统日志
	private void insertLogs(List<BusinessAccount> businessAccountList) {
		//记录特殊业务日志
		for (BusinessAccount businessAccount : businessAccountList) {
			SpecBusinessLog specBusinessLog = new SpecBusinessLog();
			specBusinessLog.setKeyId(businessAccount.getId());
			specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
			specBusinessLog.setMessage("已导出");
			specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.EXPORT.getValue());
			specBusinessLogService.insert(specBusinessLog);
		} 
		//记录系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.IMPORT_HAS_RECEIVED.getValue());
		sysLogService.insert(sysLog);
	}
	
	private void setGrantAndRepayAccount(LoanDetailsVO loanDetailsVo,Long grantAccount,Long repayAccount){
		BankAccount grantBankAccount = getBankAccount(grantAccount);
		BankAccount repayBankAccount = getBankAccount(repayAccount);
		if (grantBankAccount.getId() != null) {
			loanDetailsVo.setGrantAccount(grantBankAccount);

		}
		if (repayBankAccount.getId() != null) {
			loanDetailsVo.setRepayAccount(repayBankAccount);
		}
		
		if (grantBankAccount.getId() == null) {
			 grantBankAccount = bankAccountService.get(grantAccount);
			if (grantBankAccount != null) {
				loanDetailsVo.setGrantAccount(grantBankAccount);

			}
		}
			if (repayBankAccount.getId() == null) {
				  repayBankAccount = bankAccountService.get(repayAccount);
				if (repayBankAccount != null) {
					loanDetailsVo.setRepayAccount(repayBankAccount);
				}
			}
	
	}
	private BankAccount getBankAccount(Long bankId){
		BankAccount bankAccount = new BankAccount();
		if(bankId != null){
			Bank bank = bankService.get(bankId);
			bankAccount.setBank(bank);
		}
		
		return bankAccount;
	};
	/**
	 * 填充前端公司文本信息
	 * @param company
	 */
	private void populateCompany(Company company) {
		if(company.getMemberType()!=null){
			if(EnumConstants.MemberType.FREE.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.FREE.getText());
			}else if(EnumConstants.MemberType.HALF_YEAR.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.HALF_YEAR.getText());
				
			}else if(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getText());
				
			}
			
		}
		
	};
	
	private void populateLoanInfo(Loan loan) {
		loan.setCurUserType(ApplicationContext.getUser().getUserType());
		if(loan.getAuditMemberType()!=null){
			if(EnumConstants.MemberType.FREE.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.FREE.getText());
			}else if(EnumConstants.MemberType.HALF_YEAR.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.HALF_YEAR.getText());
				
			}else if(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getText());
				
			}
			
		}
		
		if(loan.getOrganID() != null){
			Organ organ = organService.get(loan.getOrganID());
			if(organ!=null)
			{
				loan.setOrgan(organ);
				loan.setOrganCode(organ.getCode());
				loan.setOrganName(organ.getName());
			}
			
			
			
		}
		if(loan.getSchemeID() != null){
			ChannelPlanCheck channelPlanCheck = channelPlanCheckService.getReplyById(loan.getSchemeID());
			if(channelPlanCheck!= null){
				loan.setChannelPlanCheck(channelPlanCheck);	
				
				loan.setChannelPlanName(channelPlanCheck.getName());
			}
		}
		
		
		
		
	}
	
	
	
	
	/**
	 * 本方账户数组AccountArray中是否包含本方Account
	 * @param AccountArray
	 * @return
	 * @throws UnknownHostException
	 */
	public  boolean containsAccountArray(String account) throws UnknownHostException {
		boolean flag = false;
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.FIRST_ACCOUNT_CONFIG.name());
		if (sysParameter == null
			|| StringUtil.isEmpty(sysParameter.getParameterValue())) {
			return flag;
		}
		// 获取IP列表,并存储到一个String 数组中
		String[]  accountArray = sysParameter.getParameterValue().split(";");
		for (int i = 0; i < accountArray.length; i++) {
			// 如果IPArray中包含IP，flag=true，并返回
			if(account.equals(accountArray[i])){
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public BusinessAccount getById(long id) {
		// TODO Auto-generated method stub
		return businessAccountDao.get(id);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		businessAccountDao.deleteById(id);;
	}

}
