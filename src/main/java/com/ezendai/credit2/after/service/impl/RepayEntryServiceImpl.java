package com.ezendai.credit2.after.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
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

import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.service.AfterLoanService;
import com.ezendai.credit2.after.service.OfferFileService;
import com.ezendai.credit2.after.service.RepayEntryService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.OfferFileVO;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.common.util.IDCardValidateCheck;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ibm.icu.text.DecimalFormat;

@Service
public class RepayEntryServiceImpl implements RepayEntryService {

	@Autowired
	private PersonService personService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private OfferFileService offerFileService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private AfterLoanService afterLoanService;
	@Autowired
	private RepayInfoService repayInfoService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private ProductService productService;

	private static final String UNDERLINE = "_";
	private String errorMsg;

	private static final String SLASH = File.separator;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;

	@Autowired
	private SysParameterService sysParameterService;
	private static final Logger logger = Logger.getLogger(RepayEntryServiceImpl.class);

	private String loanId;
	private BigDecimal repayAmount;
	private Integer payType;
	private String remark;
	private static int EXCEL_MAX_COLUMN_SIZE = 8;
	private static String COLUMN_NOT_RIGHT = "该行列数不正确";
	private static String COLUMN_PERSON_NAME_IS_EMPTY = "客户姓名不能为空";
	private static String COLUMN_PERSON_IDNUM_IS_EMPTY = "客户身份证号不能为空";
	private static String COLUMN_PERSON_IDNUM_IS_NOT_VALIDATE = "借款人身份证号码不合法";
	private static String COLUMN_PRODUCT_NAME_IS_EMPTY = "借款类型不能为空";
	private static String COLUMN_START_REPAY_DATE_IS_EMPTY = "首次还款日不能为空";
	private static String COLUMN_TRADE_TYPE_IS_EMPTY = "还款方式不能为空";
	private static String COLUMN_START_REPAY_DATE_ERROR_FORMAT = "首次还款日格式错误";
	private static String COLUMN_REPAY_AMOUNT_IS_EMPTY = "还款金额不能为空";
	private static String COLUMN_REPAY_AMOUNT_UNDER_ZERO = "还款金额小于0";
	private static String COLUMN_REPAY_AMOUNT_IS_MAX = "还款金额超过最大值";
	private static String COLUMN_REMARK_IS_EMPTY = "备注不能为空";
	private static String PERSON_IDNUM_IS_NOT_EXIST = "客户不存在";
	private static String PRODUCT_IS_NOT_EXIST = "产品不存在";
	private static String LOAN_IS_NOT_EXIST = "借款不存在";
	private static String REPAY_DEAL_SUCCESS = "还款成功";
	//	private static String REPAY_DEAL_FAILED = "记账出错，请重新执行还款操作";
	private static String EXCEL_SIZE_IS_EMPTY = "导入的还款文件为空，不能导入！";
	private static String EXCEL_SIZE_IS_OVER = "导入的还款文件过大，不能导入！";
	private static String FILE_TYPE_IS_ERROR = "导入的还款文件类型错误，不能导入！";

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

	/**
	 * <pre>
	 * 验证上传文件
	 * </pre>
	 *
	 * @param mFile
	 * @return
	 */
	public boolean validateUploadFile(MultipartFile mFile, String originalFilename, String suffix) {
		try {
			SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.BATCH_UPLOAD_SIZE_LIMIT.name());
			if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
				setErrorMsg("没有配置上传文件大小!");
				return false;
			}
			int size = Integer.parseInt(sysParameter.getParameterValue());

			if (mFile != null && mFile.getBytes().length <= 0) {
				setErrorMsg(EXCEL_SIZE_IS_EMPTY);
				return false;
			} else if (mFile.getBytes().length > 1024 * 1024 * size) {
				setErrorMsg(EXCEL_SIZE_IS_OVER);
				return false;
			} else if (!StringUtils.equalsIgnoreCase("xls", suffix) && !StringUtils.equalsIgnoreCase("xlsx", suffix)) {
				setErrorMsg(FILE_TYPE_IS_ERROR);
				return false;
			} else {
				OfferFileVO offerFileVO = new OfferFileVO();
				offerFileVO.setOriginalName(originalFilename);
				offerFileVO.setType(EnumConstants.OfferFileType.REPAY_ENTRY.getValue());
				List<OfferFile> offerFileList = offerFileService.findListByVo(offerFileVO);
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
			return false;
		}
	}

	/**
	 * <pre>
	 * 验证Excel的数据,返回错误信息,如正确返回为空
	 * </pre>
	 *
	 * @param xssfRow
	 * @return
	 */
	public String validateExcelData(Row row) {
		String result = null;
		String personName = null;
		String personIdnum = null;
		String productName = null;
		String tradeType = null;
		Date startRepayDate = null;
//		if (row.getLastCellNum() != EXCEL_MAX_COLUMN_SIZE) {
//			return COLUMN_NOT_RIGHT;
//		}
		for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cellNum == 0) {
				personName = getValue(cell);
				if (StringUtils.isEmpty(personName)) {
					return COLUMN_PERSON_NAME_IS_EMPTY;
				}
			} else if (cellNum == 1) {
				personIdnum = getValue(cell);
				if (StringUtils.isEmpty(personIdnum)) {
					return COLUMN_PERSON_IDNUM_IS_EMPTY;
				} else if (!IDCardValidateCheck.IDCardValidate(personIdnum).equals("success")) {
					return COLUMN_PERSON_IDNUM_IS_NOT_VALIDATE;
				}
			} else if (cellNum == 2) {
				productName = getValue(cell);
				if (StringUtils.isEmpty(productName)) {
					return COLUMN_PRODUCT_NAME_IS_EMPTY;
				}
			} else if (cellNum == 3) {
				String startRepayDateStr = getValue(cell);
				if (StringUtils.isEmpty(startRepayDateStr)) {
					return COLUMN_START_REPAY_DATE_IS_EMPTY;
				}
				try {
					startRepayDate = DateUtil.strToDate(startRepayDateStr, null);
				} catch (ParseException e) {
					return COLUMN_START_REPAY_DATE_ERROR_FORMAT;
				}
			} else if (cellNum == 4) {
				repayAmount = new BigDecimal(getValue(cell));
				if (repayAmount == null) {
					return COLUMN_REPAY_AMOUNT_IS_EMPTY;
				} else if (repayAmount.compareTo(BigDecimal.ZERO) == -1) {
					return COLUMN_REPAY_AMOUNT_UNDER_ZERO;
				}
			} else if (cellNum == 5) {
				tradeType = getValue(cell);
				if (StringUtils.isEmpty(tradeType)) {
					return COLUMN_TRADE_TYPE_IS_EMPTY;
				}
				if (StringUtils.equalsIgnoreCase(tradeType, "现金")) {
					payType = EnumConstants.TradeType.CASH.getValue();
				} else if (StringUtils.equalsIgnoreCase(tradeType, "转账")) {
					payType = EnumConstants.TradeType.TRANSFER.getValue();
				}
			} else if (cellNum == 6) {
				remark = getValue(cell);
				if (StringUtils.isEmpty(remark)) {
					return COLUMN_REMARK_IS_EMPTY;
				}
			}  else if (cellNum == 7) {
				loanId = getValue(cell);
//				if (loanId == null) {
//					return LOAN_IS_NOT_EXIST;
//				}
				
			}
		}
		ProductVO productVO=new ProductVO();
		productVO.setProductFullName(productName);
		Product product = productService.get(productVO);
		Integer productType = null;
		if (product != null) {
			productType = product.getProductType();
		} else {
			return PRODUCT_IS_NOT_EXIST;
		}
		PersonVO personVO = new PersonVO();
		personVO.setName(personName);
		personVO.setIdnum(personIdnum);
		personVO.setProductType(productType);
		Person person = personService.get(personVO);
		if (person == null) 
		{
			return PERSON_IDNUM_IS_NOT_EXIST;
		}
		LoanVO loanVO = new LoanVO();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		loanVO.setStatusList(statusList);
		loanVO.setProductType(productType);
		loanVO.setStartRepayDateStart(startRepayDate);
		loanVO.setStartRepayDateEnd(DateUtil.getDateAfterDay(startRepayDate, 1));
		if (loanId != null) {
			loanVO.setId((long)Math.ceil(Double.valueOf(loanId)));
		}
		else
		{
			loanVO.setPersonId(person.getId());
		}
		List<Loan> loanList = loanService.findListByVOUnionExtension(loanVO);
		if (loanList == null || loanList.size() != 1)  {
			return LOAN_IS_NOT_EXIST;
		}
		loanId=String.valueOf(loanList.get(0).getId());
		//Date nowDate = new Date();
		//List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, (long)Math.ceil(Double.valueOf(loanId)));

//		boolean checkIsExtensionResult = extensionService.checkIsExtension((long)Math.ceil(Double.valueOf(loanId)));
//		BigDecimal maxRepayMoney = null;
//		if (!checkIsExtensionResult) {
//			maxRepayMoney = repayService.getMaxRepayMoney(repaymentPlanList, nowDate);
//			if (repayAmount.compareTo(maxRepayMoney) == 1) {
//				return COLUMN_REPAY_AMOUNT_IS_MAX;
//			}
//		} else {
//			maxRepayMoney = repayService.getCarExtensionMaxRepayMoney(repaymentPlanList, nowDate);
//			if (repayAmount.compareTo(maxRepayMoney) == 1) {
//				return COLUMN_REPAY_AMOUNT_IS_MAX;
//			}
//		}
		return result;
	}

	/**
	 * <pre>
	 * 保存excel至服务器和offerfile表中
	 * </pre>
	 *
	 * @param mFile
	 * @param suffix
	 * @return
	 */
	private boolean saveExcelToServerAndOfferFile(MultipartFile mFile, String suffix) {
		Date nowDate = new Date();
		String defaultFormatMinutesDate = DateUtil.defaultFormatMinutesDate(nowDate);
		String uuid = CommonUtil.getUUID();
		String saveDir = credit2Properties.getUploadPath();
		String fileTruthName = defaultFormatMinutesDate + UNDERLINE + uuid;
		// 新的文件名
		String fullFileTruthName = fileTruthName + "." + suffix;
		//文件保存的路径
		String fullSavePath = saveDir + SLASH + EnumConstants.OfferFileType.REPAY_ENTRY.toString() + SLASH + DateUtil.defaultFormatDay(nowDate) + SLASH + fullFileTruthName;
		File saveFile = new File(fullSavePath);
		try {
			FileUtils.writeByteArrayToFile(saveFile, mFile.getBytes());
		} catch (IOException ex) {
			setErrorMsg(ex.getMessage());
			return false;
		} catch (Exception ex) {
			setErrorMsg(ex.getMessage());
			return false;
		}
		OfferFile offerFile = new OfferFile();
		offerFile.setName(fullFileTruthName);
		offerFile.setOriginalName(mFile.getOriginalFilename());
		offerFile.setType(EnumConstants.OfferFileType.REPAY_ENTRY.getValue());
		offerFile.setCreatedDate(nowDate);
		offerFileService.insert(offerFile);
		return true;
	}

	/**
	 * <pre>
	 * 调用还款逻辑
	 * </pre>
	 *
	 * @return
	 */

	@Transactional
	private String processRepayDeal() {
		String result = "";
		try {
			Date nowDate = new Date();
			String tradeNo=repayInfoService.generatePayTradeNo();
			RepayInfo repayInfo = new RepayInfo();
			repayInfo.setAccountId((long)Math.ceil(Double.valueOf(loanId)));
			repayInfo.setTradeAmount(repayAmount);
			repayInfo.setTradeTime(nowDate);
			repayInfo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
			repayInfo.setPayType(payType);
			repayInfo.setRemark(remark);
			repayInfo.setTradeNo(tradeNo);
			if(ApplicationContext.getUser()!=null)
			{
				repayInfo.setTeller(ApplicationContext.getUser().getId());
			}
			boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment((long)Math.ceil(Double.valueOf(loanId)));
			if (oneTimeRepayment) {
				repayInfo.setTradeCode(BizConstants.TRADE_CODE_ONEOFF);
			} else {
				repayInfo.setTradeCode(BizConstants.TRADE_CODE_NORMAL);
			}
			logger.info("processRepayDeal AccountId:" + loanId + ",TradeAmount:" + repayAmount + ",TradeTime:" + DateUtil.defaultFormatDate(nowDate) + ",TradeKind:"
						+ EnumConstants.TradeKind.NORMAL_TRADE.getValue() + ",PayType:" + payType + ",Remark:" + remark + ",TradeNo:" + tradeNo + ",Teller:"
						+ repayInfo.getTeller() + ",TradeCode:" + repayInfo.getTradeCode());
			Loan loan = loanService.get((long)Math.ceil(Double.valueOf(loanId)));
			if (loan != null) {
				repayInfo.setTerm(loan.getTime() - loan.getResidualTime() + 1);
				afterLoanService.repayDeal(repayInfo);
			} else {
				Extension extension = extensionService.get((long)Math.ceil(Double.valueOf(loanId)));
				repayInfo.setTerm((long) (extension.getTime() - extension.getResidualTime() + 1));
				// 展期
				afterLoanService.repayDealExtension(repayInfo);
			}
			logger.info("processRepayDeal AccountId:" + loanId + ",result:" + result);
			return result;
		} catch (BusinessException be) {
			logger.error("processRepayDeal BusinessException出现异常", be);
			return be.getMessage();
		} catch (Exception e) {
			logger.error("processRepayDeal Exception出现异常", e);
			if (e.getMessage() != null) {
				return e.getMessage();
			} else {
				return "空指针异常!";
			}
		}
	}

	@Override
	public Map<String, Object> batchUpload(HttpServletRequest request, HttpServletResponse response) {
		this.errorMsg = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mFile = multipartRequest.getFile("file");
			if (mFile != null) {
				//原始文件名
				String originalFilename = mFile.getOriginalFilename();
				//后缀名
				String suffix = FileUtil.getSuffixName(originalFilename);

				if (validateUploadFile(mFile, originalFilename, suffix) && saveExcelToServerAndOfferFile(mFile, suffix)) {
					InputStream inputStream = mFile.getInputStream();

					// 第三步:解析Excel
					Workbook workBook = null;
					// Excel 2003
					if (StringUtils.equals("xls", suffix)) {
						workBook = new HSSFWorkbook(inputStream);
					}
					// Excel 2007
					else if (StringUtils.equals("xlsx", suffix)) {
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
						firstRow.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue("反馈结果");
						// 创建导入失败德行头显示样式
						Font error_cell_font = workBook.createFont();
						error_cell_font.setColor(Font.COLOR_RED);
						CellStyle error_cell_style = workBook.createCellStyle();
						error_cell_style.setFont(error_cell_font);

						// 循环单个sheet中的row
						for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
							Row row = sheet.getRow(rowNum);
							if (row == null) {
								continue;
							}
							// 对excel每行记录数据进行有效性验证
							String validateExcelData = validateExcelData(row);
							// 数据有效性验证未通过
							if (!StringUtils.isEmpty(validateExcelData)) {
								if (row.getCell(0) != null) {
									row.getCell(0).setCellStyle(error_cell_style);
								}
								row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(validateExcelData);
								continue;
							}
							// 数据检验通过,向Sepcial_Repayment表插入记录
							else {
								String repayDealResult = processRepayDeal();
								if (!StringUtils.isEmpty(repayDealResult)) {
									if (row.getCell(0) != null) {
										row.getCell(0).setCellStyle(error_cell_style);
									}
									row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(repayDealResult);
									
								} else {
									row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(REPAY_DEAL_SUCCESS);
								}
							}
						}
					}
					// 写入输出文件
					response.reset();
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-disposition", "attachment; filename=" + getFilename(originalFilename, request));
					OutputStream out = response.getOutputStream();
					workBook.write(out);
					out.flush();
					out.close();

					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.REPAY_ENTRY.getValue());
					sysLog.setOptType(EnumConstants.OptionType.REPAY_ENTRY_BATCH_UPLOAD.getValue());
					sysLog.setRemark("还款批量上传操作,原始文件名:" + originalFilename);
					sysLogService.insert(sysLog);
				}
			}
			else
			{
				setErrorMsg("上传文件为空!");
			}
		} catch (Exception ex) {
			setErrorMsg(ex.getMessage());
		}

		resultMap.put("ErrorMsg", this.errorMsg);
		return resultMap;
	}

	@SuppressWarnings("static-access")
	private String getValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
				BigDecimal   b   =   new   BigDecimal(cell.getNumericCellValue());  
				double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
				DecimalFormat    df   = new DecimalFormat("#.00");  
				return String.valueOf(df.format(f1));
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		} else {
			return "";
		}
	}

	@Override
	public RepayEntryDetailsVO viewEdit(Long loanId) {
		Date nowDate = DateUtil.getToday();
		Loan loan = loanService.get(loanId);
		Person person;
		if (loan != null) {
			person = personService.get(loan.getPersonId());
		} else {
			Extension extension = extensionService.get(loanId);
			person = personService.get(extension.getPersonId());
		}
		String name = person.getName();
		String idnum = person.getIdnum();
		String mobilePhone = person.getMobilePhone();

		RepayEntryDetailsVO repayEntryDetailsVO = new RepayEntryDetailsVO();
		repayEntryDetailsVO.setLoanId(loanId);
		repayEntryDetailsVO.setPersonName(name);
		repayEntryDetailsVO.setPersonIdnum(idnum);
		repayEntryDetailsVO.setPersonMobilePhone(mobilePhone);
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loanId);
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		//设置挂账金额,期末预收
		repayEntryDetailsVO.setAccAmount(accAmount);
		Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
		//判断如果该期期数为0,则从已结清的还款计划表中获取
		if (currTerm.compareTo(0) == 0) {
			List<RepaymentPlan> repaymentPlanSettleList = repayService.getAllInterestOrLoanBySettle(nowDate, loanId);
			currTerm = repayService.getCurrTerm(repaymentPlanSettleList, nowDate);
			int size = repaymentPlanSettleList.size();
			if (size > 0) {
				Date repayDay = repaymentPlanSettleList.get(size - 1).getRepayDay();
				//设置当期还款日
				repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));
			}
		}
		repayEntryDetailsVO.setCurrTerm(currTerm);
		//设置当前期数
		BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(nowDate, loanId);
		//设置减免金额
		repayEntryDetailsVO.setReliefOfFine(reliefOfFine);
		//设置还款日期
		repayEntryDetailsVO.setNowDate(DateUtil.defaultFormatDay(nowDate));
		BigDecimal fine = repayService.getFine(repaymentPlanList, nowDate);
		//设置罚息
		repayEntryDetailsVO.setFine(fine);
		BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, nowDate);
		BigDecimal overdueAllAmount = overdueAmount.add(fine);
		//设置逾期应金额
		repayEntryDetailsVO.setOverdueAmount(overdueAmount);

		//BigDecimal repayAllAmount = currRepayAmount.add(overdueAllAmount).subtract(accAmount);
		//应还总额（包含当期)
		//repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);

		BigDecimal repayAmount = overdueAllAmount;
		//如果结果小于0,则返回0
		if (repayAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAmount(BigDecimal.ZERO);
		} else {
			//应还总额（不含当期)
			repayEntryDetailsVO.setRepayAmount(repayAmount);
		}
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			int size = repaymentPlanList.size();
			Integer overdueTermCount = repayService.getOverdueTermCount(repaymentPlanList, nowDate);
			//设置逾期总数
			repayEntryDetailsVO.setOverdueTerm(overdueTermCount);
			//如果逾期期数大于等于1 
			if (overdueTermCount >= 1) {
				//设置逾期起始日
				repayEntryDetailsVO.setOverdueStartDate(DateUtil.defaultFormatDay(repaymentPlanList.get(0).getRepayDay()));
			}
			int overdueFineDay = repayService.getOverdueFineDay(repaymentPlanList, nowDate);
			//设置罚息天数
			repayEntryDetailsVO.setFineDay(overdueFineDay);

			if (overdueFineDay >= 1) {
				//设置罚息起算日
				repayEntryDetailsVO.setFineDate(DateUtil.defaultFormatDay(DateUtil.getDateBefore(overdueFineDay)));
			}
			Date repayDay = repaymentPlanList.get(size - 1).getRepayDay();
			//设置当期还款日
			repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));

		}
		boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment(loanId);
		BigDecimal repayAllAmount = BigDecimal.ZERO;
		if (oneTimeRepayment) {
			//一次性结清
			BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(repaymentPlanList, nowDate);
			repayEntryDetailsVO.setOnetimeRepaymentAmount(onetimeRepaymentAmount);
			repayEntryDetailsVO.setCurrAmountLabel("一次性还款金额");
			repayEntryDetailsVO.setCurrAmount(onetimeRepaymentAmount);
			//应还总额 =一次性结清 + 逾期应还 - 期末预收
			repayAllAmount = onetimeRepaymentAmount.add(overdueAllAmount);

		} else {
			BigDecimal nextRepayAmount = repayService.getNextRepayAmount(repaymentPlanList, nowDate);
			repayEntryDetailsVO.setCurrAmountLabel("当期应还总额");
			//判断如果没有逾期的话则取当期还款金额，有逾期金额的话当期还款金额为逾期应还总额
			//			if (overdueAllAmount.compareTo(BigDecimal.ZERO) == 0) {
			//				repayEntryDetailsVO.setCurrAmount(currRepayAmount);
			//			} else {
			//				repayEntryDetailsVO.setCurrAmount(overdueAllAmount);
			//			}
			repayEntryDetailsVO.setCurrAmount(nextRepayAmount);
			//应还总额（包含当期） = 当期应还总额+应还总额（不含当期）
			if (repayEntryDetailsVO.getCurrAmount() != null) {
				repayAllAmount = repayAmount.add(repayEntryDetailsVO.getCurrAmount());
			}
		}
		//如果结果小于0,则返回0
		if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAllAmount(BigDecimal.ZERO);
		} else {
			repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);
		}
		return repayEntryDetailsVO;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Transactional
	@Override
	public String saveRepay(RepayEntryDetailsVO repayEntryDetailsVO) {
		try {
			if (repayEntryDetailsVO.getTradeAmount() == null) {
				return "请输入还款金额";
			}
			Date nowDate = new Date();
			List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, repayEntryDetailsVO.getLoanId());
//			boolean checkIsExtensionResult = extensionService.checkIsExtension(repayEntryDetailsVO.getLoanId());
//			if (!checkIsExtensionResult) {
//				BigDecimal maxRepayMoney = repayService.getMaxRepayMoney(repaymentPlanList, nowDate);
//				if (repayEntryDetailsVO.getTradeAmount().compareTo(maxRepayMoney) == 1) {
//					return "还款录入的钱大于应还总额";
//				}
//			} else {
//				BigDecimal carExtensionMaxRepayMoney = repayService.getCarExtensionMaxRepayMoney(repaymentPlanList, nowDate);
//				if (repayEntryDetailsVO.getTradeAmount().compareTo(carExtensionMaxRepayMoney) == 1) {
//					return "还款录入的钱大于应还总额";
//				}
//			}
			RepayInfo repayInfo = new RepayInfo();
			repayInfo.setAccountId(repayEntryDetailsVO.getLoanId());
			repayInfo.setTradeAmount(repayEntryDetailsVO.getTradeAmount());
			repayInfo.setTradeTime(nowDate);
			repayInfo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
			repayInfo.setPayType(repayEntryDetailsVO.getPayType());
			repayInfo.setRemark(repayEntryDetailsVO.getRemark());
			String tradeNo=repayInfoService.generatePayTradeNo();
			repayInfo.setTradeNo(tradeNo);
			repayInfo.setTeller(ApplicationContext.getUser().getId());
			boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment(repayEntryDetailsVO.getLoanId());
			if (oneTimeRepayment) {
				repayInfo.setTradeCode(BizConstants.TRADE_CODE_ONEOFF);
			} else {
				repayInfo.setTradeCode(BizConstants.TRADE_CODE_NORMAL);
			}

			logger.info("saveRepay AccountId:" + repayEntryDetailsVO.getLoanId() + ",TradeAmount:" + repayEntryDetailsVO.getTradeAmount() + ",TradeTime:" + DateUtil.defaultFormatDate(nowDate)
						+ ",TradeKind:" + EnumConstants.TradeKind.NORMAL_TRADE.getValue() + ",PayType:" + repayEntryDetailsVO.getPayType() + ",Remark:" + repayEntryDetailsVO.getRemark() + ",TradeNo:"
						+ tradeNo + ",Teller:" + ApplicationContext.getUser().getId() + ",TradeCode:" + repayInfo.getTradeCode());

			boolean repayDealResult;
			Loan loan = loanService.get(repayEntryDetailsVO.getLoanId());
			if (loan != null) {
				repayInfo.setTerm(loan.getTime() - loan.getResidualTime() + 1);
				repayDealResult = afterLoanService.repayDeal(repayInfo);
			} else {
				Extension extension = extensionService.get(repayEntryDetailsVO.getLoanId());
				repayInfo.setTerm((long) (extension.getTime() - extension.getResidualTime() + 1));
				// 展期
				repayDealResult = afterLoanService.repayDealExtension(repayInfo);
			}
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.REPAY_ENTRY.getValue());
			sysLog.setOptType(EnumConstants.OptionType.SUBMIT_REPAY_ENTRY.getValue());
			sysLog.setRemark("saveRepay, AccountId:" + repayEntryDetailsVO.getLoanId() + ",TradeAmount:" + repayEntryDetailsVO.getTradeAmount() + ",TradeTime:" + DateUtil.defaultFormatDate(nowDate)
								+ ",TradeKind:" + EnumConstants.TradeKind.NORMAL_TRADE.getValue() + ",PayType:" + repayEntryDetailsVO.getPayType() + ",Remark:" + repayEntryDetailsVO.getRemark()
								+ ",TradeNo:" + tradeNo + ",Teller:" + ApplicationContext.getUser().getId() + ",TradeCode:" + repayInfo.getTradeCode()
								+ ",repayDealResult:" + repayDealResult);
			sysLogService.insert(sysLog);
			logger.info("saveRepay repayDealResult:" + repayDealResult);
			if (repayDealResult) {
				return "success";
			} else {
				return "repayDeal false";
			}
		} catch (BusinessException be) {
			logger.error("saveRepay BusinessException出现异常", be);
			throw new BusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("saveRepay Exception出现异常", e);
			if (e.getMessage() != null) {
				throw new BusinessException(e.getMessage());
			} else {
				throw new BusinessException("空指针异常!");
			}
		}
	}
}
