/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
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
import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.service.OfferFileService;
import com.ezendai.credit2.after.service.PenaltyReduceService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.OfferFileVO;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.common.util.IDCardValidateCheck;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 罚息减免
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PenaltyReduceServiceImpl.java, v 0.1 2014年12月16日 下午3:12:01 00221921 Exp $
 */
@Service
public class PenaltyReduceServiceImpl implements PenaltyReduceService {
	@Autowired
	private OfferFileService offerFileService;
	@Autowired
	private PersonService personService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private LoanService loanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private RepayService repayService;

	private String errorMsg;
	private static final String UNDERLINE = "_";
	private static final String SLASH = File.separator;

	// excel的最大列数
	private static int EXCEL_MAX_COLUMN_SIZE = 5;

	private Long loanId;
	private Long id;
	Integer extensionTime;
	private Long salesDeptId;
	private BigDecimal requestAmount;
	private String proposer;
	private String fullFileName;

	private static String EXCEL_SIZE_IS_EMPTY = "罚息批量减免文件为空，不能导入";
	private static String EXCEL_SIZE_IS_OVER = "罚息批量减免文件过大，超过50MB，不能导入";
	private static String FILE_TYPE_IS_ERROR = "罚息批量减免文件类型错误，须为excel文件";
	private static String COLUMN_PERSON_NAME_IS_EMPTY = "借款人姓名不能为空";
	private static String COLUMN_PERSON_IDNUM_IS_EMPTY = "借款人身份证不能为空";
	private static String COLUMN_PERSON_IDNUM_IS_NOT_VALIDATE = "借款人身份证号码不合法";
	private static String COLUMN_PROPOSER_NUMBER_IS_EMPTY = "申请人工号不能为空";
	private static String COLUMN_REQUEST_MONEY_IS_EMPTY = "申请金额不能为空";
	private static String COLUMN_REQUEST_MONEY_ERROR = "申请金额有误";
	private static String COLUMN_REQUEST_MONEY_MUST_GREATER_ZERO = "申请金额必须大于0";
	private static String COLUMN_REQUEST_MONEY_GREATER_PENALTY_MONEY = "减免大于逾期应还总额";
	private static String COLUMN_PRODUCT_TYPE_IS_EMPTY = "借款类型不能为空";
	private static String COLUMN_FRAME_NUMBER_IS_EMPTY = "车架号不能为空";
	private static String COLUMN_FRAME_NUMBER_IS_ERROR = "车架号不正确";
	private static String PROPOSER_IS_NOT_EXIST = "该申请人不存在";
	private static String PERSON_IDNUM_IS_NOT_EXIST = "该客户不存在";
	private static String LOAN_IS_NOT_EXIST = "该借款不存在";
	private static String LOAN_APPLYED_PENALTY_REDUCE = "该借款已申请罚息减免";
	private static String LOAN_APPLYED_COST_REDUCE = "该借款已申请费用减免";
	private static String IMPORT_SUCCESS = "导入成功";
	private static String IMPORT_FAILED = "导入失败";
	private static String NO_ONE_TIME_REPAYMENT = "没有一次性结清交易";

	/** 
	 * @param request
	 * @param response
	 * @return
	 * @see com.ezendai.credit2.after.service.PenaltyReduceService#batchUpload(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
			// 获取文件myFile原始名称
			String originalFilename = myFile.getOriginalFilename();
			// 获取文件myFile后缀名
			String suffixName = FileUtil.getSuffixName(originalFilename);

			// 第一步：验证excel文件，通过 —> 第二步：上传至服务器
			if (validateUploadFile(myFile, originalFilename, suffixName) && saveExcelToServerAndOfferFile(myFile, suffixName)) {
				InputStream inputStream = myFile.getInputStream();

				// 第三步:解析Excel
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
							SpecialRepayment isSuccessed = addSpecialRepayment();
							if (isSuccessed != null) {
								row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(IMPORT_SUCCESS);
							} else {
								row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(IMPORT_FAILED);
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
				inputStream.close();
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.PENALTY_REDUCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.PENALTY_REDUCE_BATCH_UPLOAD.getValue());
				sysLog.setRemark("罚息减免之批量导入");
				sysLogService.insert(sysLog);
			}
		} catch (IOException e) {
			setErrorMsg(e.getMessage());
		}
		resultMap.put("ErrorMsg", this.errorMsg);
		return resultMap;
	}

	
	
	
	/** 
	 * 费用减免
	 * @param request
	 * @param response
	 * @return
	 * @see com.ezendai.credit2.after.service.PenaltyReduceService#batchUpload(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@Transactional
	public Map<String, Object> batchCostUpload(HttpServletRequest request, HttpServletResponse response) {
		errorMsg = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取待上传文件myFile
			MultipartFile myFile = multipartRequest.getFile("file");
			// 获取文件myFile原始名称
			String originalFilename = myFile.getOriginalFilename();
			// 获取文件myFile后缀名
			String suffixName = FileUtil.getSuffixName(originalFilename);

			// 第一步：验证excel文件，通过 —> 第二步：上传至服务器
			if (validateUploadFile(myFile, originalFilename, suffixName) && saveExcelToServerAndOfferFile(myFile, suffixName)) {
				InputStream inputStream = myFile.getInputStream();

				// 第三步:解析Excel
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
						String validateExcelData = validateExcelCostData(row);
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
							SpecialRepayment isSuccessed = addSpecialRepayment();
							if (isSuccessed != null) {
								row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(IMPORT_SUCCESS);
							} else {
								row.createCell(EXCEL_MAX_COLUMN_SIZE).setCellValue(IMPORT_FAILED);
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
				inputStream.close();

				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.COST_REDUCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.COST_REDUCE_BATCH_UPLOAD.getValue());
				sysLog.setRemark("费用减免之批量导入");
				sysLogService.insert(sysLog);
			}
		} catch (IOException e) {
			setErrorMsg(e.getMessage());
		}
		resultMap.put("ErrorMsg", this.errorMsg);
		return resultMap;
	}

	
	
	
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 添加Special_Repayment记录
	 * </pre>
	 *
	 * @return
	 */
	private SpecialRepayment addSpecialRepayment() {
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setLoanId(id);
		specialRepayment.setSalesDeptId(salesDeptId);
		specialRepayment.setRequestDate(DateUtil.getToday());
		specialRepayment.setType(EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
		specialRepayment.setAmount(requestAmount);
		specialRepayment.setProposer(proposer);
		specialRepayment.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		String remark = "由" + fullFileName + "批量导入，导入日期:" + new Date();
		specialRepayment.setRemark(remark);

		return specialRepaymentService.insert(specialRepayment);
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
			setErrorMsg("没有配置文件大小限制");
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
				offerFileVO.setType(EnumConstants.OfferFileType.RELIEF_PENATLT.getValue());
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
		String fullSavePath = saveDir + SLASH + EnumConstants.OfferFileType.RELIEF_PENATLT.toString() + SLASH + DateUtil.defaultFormatDay(nowDate) + SLASH + fullFileTruthName;
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
		offerFile.setType(EnumConstants.OfferFileType.RELIEF_PENATLT.getValue());
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
	 */
	public String validateExcelData(Row row) {
		String result = null;
		// 借款人姓名
		String personName = null;
		// 借款人身份证
		String personIdnum = null;
		// 申请人工号
		String proposerNum = null;
		// 申请金额
		String requestMoney = null;
		// 借款类型
		String productType = "车贷";
		// 车架号
		String frameNumber = null;

		for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cellNum == 0) {
				personName = getValue(cell);
				if (StringUtils.isEmpty(personName)) {
					return COLUMN_PERSON_NAME_IS_EMPTY;
				} else {
					personName = personName.trim();
				}
			} else if (cellNum == 1) {
				personIdnum = getValue(cell);
				if (StringUtils.isEmpty(personIdnum)) {
					return COLUMN_PERSON_IDNUM_IS_EMPTY;
				} else if (!IDCardValidateCheck.IDCardValidate(personIdnum).equals("success")) {
					return COLUMN_PERSON_IDNUM_IS_NOT_VALIDATE;
				}
			} else if (cellNum == 2) {
				proposerNum = getValue(cell);
				if (StringUtils.isEmpty(proposerNum)) {
					return COLUMN_PROPOSER_NUMBER_IS_EMPTY;
				} else {
					proposerNum = proposerNum.trim();
				}
			} else if (cellNum == 3) {
				requestMoney = getValue(cell);
				if (StringUtils.isEmpty(requestMoney)) {
					return COLUMN_REQUEST_MONEY_IS_EMPTY;
				} else {
					requestMoney = requestMoney.trim();
					try {
						requestAmount = new BigDecimal(requestMoney);
					} catch (Exception e) {
						return COLUMN_REQUEST_MONEY_ERROR;
					}

				}
//			} else if (cellNum == 4) {
//				productType = getValue(cell);
//				if (StringUtils.isEmpty(productType)) {
//					return COLUMN_PRODUCT_TYPE_IS_EMPTY;
//				} else {
//					productType = productType.trim();
//				}
//			}
			//车贷模板的车驾号
			} else if (cellNum == 4 && StringUtils.equalsIgnoreCase(productType, "车贷")) {
				frameNumber = getValue(cell);
				if (StringUtils.isEmpty(frameNumber)) {
					return COLUMN_FRAME_NUMBER_IS_EMPTY;
				} else {
					frameNumber = frameNumber.trim();
				}

			}
		}

		SysUser sysUser = sysUserService.getSysUserByLoginName(proposerNum);
		if (sysUser == null) {
			return PROPOSER_IS_NOT_EXIST;
		} else {
			proposer = sysUser.getName();
		}

		PersonVO personVO = new PersonVO();
		personVO.setName(personName);
		personVO.setIdnum(personIdnum);
		if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
			personVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
			personVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}

		Person person = personService.get(personVO);
		Long personId = null;
		if (person != null) {
			personId = person.getId();
		} else {
			return PERSON_IDNUM_IS_NOT_EXIST;
		}
		LoanVO loanVO = new LoanVO();
		if (personId != null) {
			loanVO.setPersonId(personId);
		}
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		loanVO.setStatusList(statusList);
		if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
			personVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
			personVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}
		List<Loan> loanList = loanService.findListByVOUnionExtension(loanVO);
		if (loanList != null && loanList.size() >= 1) {
			//小企业贷
			if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
				id = loanList.get(0).getId();
				salesDeptId = loanList.get(0).getSalesDeptId();

			}
			//车贷
			else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
				boolean findFrameNumber = false;
				for (int i = 0; i < loanList.size(); i++) {
					loanId = loanList.get(i).getLoanId();
					id =  loanList.get(i).getId();
					extensionTime = loanList.get(i).getExtensionTime();
					salesDeptId = loanList.get(i).getSalesDeptId();
					VehicleVO vehicleVo = new VehicleVO();
					vehicleVo.setFrameNumber(frameNumber);
					vehicleVo.setLoanId(loanId);
					List<Vehicle> vehicleList = vehicleService.findListByVo(vehicleVo);
					if (vehicleList == null || vehicleList.size() != 1) {
						continue;
					} else {
						findFrameNumber = true;
						break;
					}
				}
				if (!findFrameNumber) {
					return COLUMN_FRAME_NUMBER_IS_ERROR;
				}
			}

		} else {
			return LOAN_IS_NOT_EXIST;
		}
		//申请金额为小于0
		if (!(requestAmount.compareTo(BigDecimal.ZERO) == 1)) {
			return COLUMN_REQUEST_MONEY_MUST_GREATER_ZERO;
		}
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(new Date(), id);
		// 罚息
		BigDecimal fine = repayService.getFine(repaymentPlanList, new Date());
		// 逾期应还总额
		BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, new Date()).add(fine).setScale(2, BigDecimal.ROUND_HALF_UP);
		if (requestAmount.compareTo(overdueAmount) == 1) {
			return COLUMN_REQUEST_MONEY_GREATER_PENALTY_MONEY;
		}
		BigDecimal amount = specialRepaymentService.getReliefOfFine(DateUtil.getToday(), id);
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			return LOAN_APPLYED_PENALTY_REDUCE;
		}
		return result;
	}

	@SuppressWarnings("static-access")
	private String getValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		} else {
			return "";
		}
	}
	
	

	/**
	 * <pre>
	 * 费用减免
	 * 验证Excel的数据,返回错误信息,如正确返回为空
	 * </pre>
	 *
	 * @param xssfRow
	 * @return
	 * @throws ParseException 
	 */
	public String validateExcelCostData(Row row) {
		String result = null;
		// 借款人姓名
		String personName = null;
		// 借款人身份证
		String personIdnum = null;
		// 申请人工号
		String proposerNum = null;
		// 申请金额
		String requestMoney = null;
		// 借款类型
		String productType = "车贷";
		// 车架号
		String frameNumber = null;

		for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cellNum == 0) {
				personName = getValue(cell);
				if (StringUtils.isEmpty(personName)) {
					return COLUMN_PERSON_NAME_IS_EMPTY;
				} else {
					personName = personName.trim();
				}
			} else if (cellNum == 1) {
				personIdnum = getValue(cell);
				if (StringUtils.isEmpty(personIdnum)) {
					return COLUMN_PERSON_IDNUM_IS_EMPTY;
				} else if (!IDCardValidateCheck.IDCardValidate(personIdnum).equals("success")) {
					return COLUMN_PERSON_IDNUM_IS_NOT_VALIDATE;
				}
			} else if (cellNum == 2) {
				proposerNum = getValue(cell);
				if (StringUtils.isEmpty(proposerNum)) {
					return COLUMN_PROPOSER_NUMBER_IS_EMPTY;
				} else {
					proposerNum = proposerNum.trim();
				}
			} else if (cellNum == 3) {
				requestMoney = getValue(cell);
				if (StringUtils.isEmpty(requestMoney)) {
					return COLUMN_REQUEST_MONEY_IS_EMPTY;
				} else {
					requestMoney = requestMoney.trim();
					try {
						requestAmount = new BigDecimal(requestMoney);
					} catch (Exception e) {
						return COLUMN_REQUEST_MONEY_ERROR;
					}

				}
//			} else if (cellNum == 4) {
//				productType = getValue(cell);
//				if (StringUtils.isEmpty(productType)) {
//					return COLUMN_PRODUCT_TYPE_IS_EMPTY;
//				} else {
//					productType = productType.trim();
//				}
//			}
			//车贷模板的车驾号
			} else if (cellNum == 4 && StringUtils.equalsIgnoreCase(productType, "车贷")) {
				frameNumber = getValue(cell);
				if (StringUtils.isEmpty(frameNumber)) {
					return COLUMN_FRAME_NUMBER_IS_EMPTY;
				} else {
					frameNumber = frameNumber.trim();
				}

			}
		}

		SysUser sysUser = sysUserService.getSysUserByLoginName(proposerNum);
		if (sysUser == null) {
			return PROPOSER_IS_NOT_EXIST;
		} else {
			proposer = sysUser.getName();
		}

		PersonVO personVO = new PersonVO();
		personVO.setName(personName);
		personVO.setIdnum(personIdnum);
		if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
			personVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
			personVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}

		Person person = personService.get(personVO);
		Long personId = null;
		if (person != null) {
			personId = person.getId();
		} else {
			return PERSON_IDNUM_IS_NOT_EXIST;
		}
		SpecialRepaymentVO specialRepaymentVO=new SpecialRepaymentVO();
		specialRepaymentVO.setName(personName);
		specialRepaymentVO.setIdnum(personIdnum);
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
		try {
			specialRepaymentVO.setCreatedTimeStart(DateUtil.strToDateTime("2014-12-10",DateUtil.default_pattern_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pager p = new Pager();
		p.setRows(20);
		p.setPage(1);
		p.setSidx("REQUEST_DATE");
		p.setSort("ASC");
		specialRepaymentVO.setPager(p);
		Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtExtend(specialRepaymentVO);
		List<SpecialRepayment> specialRepaymentList=specialRepaymentPager.getResultList();
		if(specialRepaymentList == null || specialRepaymentList.size()== 0 ){
			return NO_ONE_TIME_REPAYMENT;
		}
		LoanVO loanVO = new LoanVO();
		if (personId != null) {
			loanVO.setPersonId(personId);
		}
		//直接取有问题，需要再加判断取
		boolean flags = false;
		for(SpecialRepayment s : specialRepaymentList) {
			if(s.getVehicle().getFrameNumber().equals(frameNumber)){
				loanVO.setId(s.getLoanId());
				flags = true;
			}
		}
		if(!flags) {
			return COLUMN_FRAME_NUMBER_IS_ERROR;
		}   
		//loanVO.setId(specialRepaymentList.get(0).getLoanId());
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.预结清.getValue());
		statusList.add(EnumConstants.LoanStatus.结清.getValue());
		statusList.add(EnumConstants.LoanStatus.坏账.getValue());
		loanVO.setStatusList(statusList);
		if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
			personVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
			personVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}
		List<Loan> loanList = loanService.findListByVOUnionExtension(loanVO);
		if (loanList != null && loanList.size() >= 1) {
			//小企业贷
			if (StringUtils.equalsIgnoreCase(productType, "小企业贷")) {
				id = loanList.get(0).getId();
				salesDeptId = loanList.get(0).getSalesDeptId();

			}
			//车贷
			else if (StringUtils.equalsIgnoreCase(productType, "车贷")) {
				boolean findFrameNumber = false;
				for (int i = 0; i < loanList.size(); i++) {
					loanId = loanList.get(i).getLoanId();
					id =  loanList.get(i).getId();
					extensionTime = loanList.get(i).getExtensionTime();
					salesDeptId = loanList.get(i).getSalesDeptId();
					VehicleVO vehicleVo = new VehicleVO();
					vehicleVo.setFrameNumber(frameNumber);
					vehicleVo.setLoanId(loanId);
					List<Vehicle> vehicleList = vehicleService.findListByVo(vehicleVo);
					if (vehicleList == null || vehicleList.size() != 1) {
						continue;
					} else {
						findFrameNumber = true;
						break;
					}
				}
				if (!findFrameNumber) {
					return COLUMN_FRAME_NUMBER_IS_ERROR;
				}
			}

		} else {
			return LOAN_IS_NOT_EXIST;
		}

		//申请金额为小于0
		if (!(requestAmount.compareTo(BigDecimal.ZERO) == 1)) {
			return COLUMN_REQUEST_MONEY_MUST_GREATER_ZERO;
		}
		BigDecimal amount = specialRepaymentService.getReliefOfFine(DateUtil.getToday(), id);
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			return LOAN_APPLYED_COST_REDUCE;
		}
		
		return result;
	}
	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(BigDecimal requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
}
