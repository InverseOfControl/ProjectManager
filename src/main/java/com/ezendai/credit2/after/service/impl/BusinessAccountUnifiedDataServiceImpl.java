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
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
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
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ezendai.credit2.after.dao.BusinessAccountDao;
import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.service.BusinessAccountService;
import com.ezendai.credit2.after.service.BusinessAccountUnifieDataService;
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
public class BusinessAccountUnifiedDataServiceImpl implements BusinessAccountUnifieDataService {

	@Autowired
	private BusinessAccountDao businessAccountDao;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SpecBusinessLogService specBusinessLogService;
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

		private static String COLUMN_AMOUNT_IS_EMPTY = "[贷方发生金额]不能为空";
		private static String COLUMN_REPAY_DATE_IS_EMPTY = "[交易日期]不能为空";
		private static String REPAY_DATE_FORMAT_IS_WRONG = "[交易日期]格式错误";
		private static String COLUMN_REPAY_TIME_IS_EMPTY = "[交易时间]不能为空";
		private static String COLUMN_HAS_EXIST = "该记录已经存在";
		
		 

		/**
		 * <pre>
		 * 对公还款确认领取
		 * </pre>
		 *流程 ：1 判断个贷开关是否打开 开：调用个贷领取 ,关:不调用,判断证方开关是否打开  开：调用个贷领取 ,关:不调用,都关 返回开关没打开不能调用
		 *    2 个贷，证方都返回success，领取成功，其中一个返回success 另一个超时或者返回失败 ,就要执行撤销操作 
		 *  
		 *  
		 */	
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
			String sendMessageZf = "FLASE";
			String sendMessageZd = "FLASE";
			//调用个贷
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				 	//调用个贷  判断开关是否开启
					String zd = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CREDIT).getParameterValue();
					String zf = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CZ).getParameterValue();
					if(zd.equals("1")&&zf.equals("1")){
						logger.info("confirmReceive:  开关全部关闭 ，只更新本系统状态" );
						sendMessage="SUCCESS";
					}
					if(zd.equals("0")){
						sendMessageZd = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditServiceUrl());
						sendMessage=sendMessageZd;
						if(sendMessageZd.equals("err")){
							sendMessage="调用个贷对公账户接口错误:"+sendMessageZd;
							return sendMessage;
						}
						logger.info("个贷sendMessage:" + sendMessageZd);
					}
					//调用证方
					if(zf.equals("0")){
						if(zd.equals("0")){
							sendMessageZd=	sendMessageZd.replaceAll("\"", "");
							if(StringUtils.endsWithIgnoreCase(sendMessageZd, "SUCCESS")){
								try {
									sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
									sendMessage=sendMessageZf;
									if(sendMessage.equals("err")){
										sendMessage="调用证方对公账户接口错误:"+sendMessage;
									}
								} catch (Exception e) {
									//调用个贷已成功 ,调用证方异常处理：撤销个贷领取
									logger.error("confirmReceive 错误:  ", e);
									result = "confirmReceive 错误:" + e.getMessage();
									return result;
								}
							
								logger.info("证方sendMessage:" + sendMessageZf);
								
							}else{
								sendMessage ="调用个贷对公账户接口错误" +sendMessageZd;
							}
						}else{
							sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
							sendMessage = sendMessageZf;
							if(sendMessageZf.equals("err")){
								sendMessage="调用证方对公账户接口错误:"+sendMessage;
							}
							logger.info("证方sendMessage:" + sendMessageZf);
						
						}	
					}
					
			}else{
				sendMessage="SUCCESS";
			}
			sendMessage=	sendMessage.replaceAll("\"", "");
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
				result = "调用对公账户接口错误:" + sendMessage;
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
			String sendMessageZf = "FLASE";
			String sendMessageZd = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				//调用个贷  判断开关是否开启
				String zd = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CREDIT).getParameterValue();
				String zf = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CZ).getParameterValue();
				if(zd.equals("1")&&zf.equals("1")){
					logger.info("confirmReceiveCancel:  开关全部关闭 ，只更新本系统状态" );
					sendMessage="SUCCESS";
				}
				if(zd.equals("0")){
					sendMessageZd = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditServiceUrl());
					sendMessage=sendMessageZd;
					if(sendMessageZd.equals("err")){
						sendMessage="调用个贷对公账户接口错误:"+sendMessageZd;
						return sendMessage;
					}
					logger.info("个贷sendMessage:" + sendMessageZd);
				}
				//调用证方
				if(zf.equals("0")){
					if(zd.equals("0")){
						sendMessageZd=	sendMessageZd.replaceAll("\"", "");
						if(StringUtils.endsWithIgnoreCase(sendMessageZd, "SUCCESS")){
							try {
								sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
								sendMessageZf=sendMessageZf.replaceAll("\"", "");
								sendMessage=sendMessageZf;
								if(sendMessage.equals("err")){
									sendMessage="调用证方对公账户接口错误:"+sendMessage;
								}
							} catch (Exception e) {
								//调用个贷已成功 ,调用证方异常处理：撤销个贷领取
								logger.error("confirmReceiveCancel 错误:  ", e);
								result = "confirmReceiveCancel 错误:" + e.getMessage();
								return result;
							}
						
							logger.info("证方sendMessage:" + sendMessageZf);
							
						}else{
							sendMessage ="调用个贷对公账户接口错误" +sendMessageZd;
						}
					}else{
						sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
						logger.info("证方sendMessage:" + sendMessageZf);
						sendMessage = sendMessageZf;
						if(sendMessageZf.equals("err")){
							sendMessage="调用证方对公账户接口错误:"+sendMessage;
						}
					}	
				}
			}else{
				sendMessage="SUCCESS";
			}
			sendMessage=	sendMessage.replaceAll("\"", "");
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
				result = "调用对公账户接口错误:" + sendMessage;
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
			String sendMessageZf = "FLASE";
			String sendMessageZd = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				//调用个贷  判断开关是否开启
				String zd = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CREDIT).getParameterValue();
				String zf = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CZ).getParameterValue();
				if(zd.equals("1")&&zf.equals("1")){
					logger.info("withoutClaim:  开关全部关闭 ，只更新本系统状态" );
					sendMessage="SUCCESS";
				}
				if(zd.equals("0")){
					sendMessageZd = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditServiceUrl());
					sendMessage=sendMessageZd;
					if(sendMessageZd.equals("err")){
						sendMessage="调用个贷对公账户接口错误:"+sendMessageZd;
						return sendMessage;
					}
					logger.info("个贷sendMessage:" + sendMessageZd);
				}
				//调用证方
				if(zf.equals("0")){
					if(zd.equals("0")){
						sendMessageZd=	sendMessageZd.replaceAll("\"", "");
						if(StringUtils.endsWithIgnoreCase(sendMessageZd, "SUCCESS")){
							try {
								sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
								sendMessageZf=sendMessageZf.replaceAll("\"", "");
								sendMessage=sendMessageZf;
								if(sendMessage.equals("err")){
									sendMessage="调用证方对公账户接口错误:"+sendMessage;
								}
							} catch (Exception e) {
								//调用个贷已成功 ,调用证方异常处理：撤销个贷领取
								logger.error("withoutClaim 错误:  ", e);
								result = "withoutClaim 错误:" + e.getMessage();
								return result;
							}
						
							logger.info("证方sendMessage:" + sendMessageZf);
							
						}else{
							sendMessage ="调用个贷对公账户接口错误" +sendMessageZd;
						}
					}else{
						sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
						logger.info("证方sendMessage:" + sendMessageZf);
						sendMessage = sendMessageZf;
						if(sendMessageZf.equals("err")){
							sendMessage="调用证方对公账户接口错误:"+sendMessage;
						}
					}	
				}
				
				
				
			}else{
				sendMessage="SUCCESS";
			}
			sendMessage=sendMessage.replaceAll("\"", "");
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
				result =   sendMessage;
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
			String sendMessageZf = "FLASE";
			String sendMessageZd = "FLASE";
			if(businessAccount.getFirstAccount()!=null && containsAccountArray(businessAccount.getFirstAccount().trim())){
				//调用个贷  判断开关是否开启
				String zd = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CREDIT).getParameterValue();
				String zf = sysParameterService.getByCodeNoCache(EnumConstants.BUSINESS_ACCOUNT_CZ).getParameterValue();
				if(zd.equals("1")&&zf.equals("1")){
					logger.info("withoutClaimCancel:  开关全部关闭 ，只更新本系统状态" );
					sendMessage="SUCCESS";
				}
				if(zd.equals("0")){
					sendMessageZd = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditServiceUrl());
					sendMessage=sendMessageZd;
					if(sendMessageZd.equals("err")){
						sendMessage="调用个贷对公账户接口错误:"+sendMessageZd;
						return sendMessage;
					}
					logger.info("个贷sendMessage:" + sendMessageZd);
				}
				//调用证方
				if(zf.equals("0")){
					if(zd.equals("0")){
						sendMessageZd=	sendMessageZd.replaceAll("\"", "");
						if(StringUtils.endsWithIgnoreCase(sendMessageZd, "SUCCESS")){
							try {
								sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
								sendMessageZf=sendMessageZf.replaceAll("\"", "");
								sendMessage=sendMessageZf;
								if(sendMessage.equals("err")){
									sendMessage="调用证方对公账户接口错误:"+sendMessage;
								}
							} catch (Exception e) {
								//调用个贷已成功 ,调用证方异常处理：撤销个贷领取
								logger.error("withoutClaimCancel 错误:  ", e);
								result = "withoutClaimCancel 错误:" + e.getMessage();
								return result;
							}
						
							logger.info("证方sendMessage:" + sendMessageZf);
							
						}else{
							sendMessage ="调用个贷对公账户接口错误" +sendMessageZd;
						}
					}else{
						sendMessageZf = creditService.sendBusinessAccountDataUnifie(repayDate, repayTime, secondCompany, amount, type,credit2Properties.getCreditUnifiedServiceUrl());
						logger.info("证方sendMessage:" + sendMessageZf);
						sendMessage = sendMessageZf;
						if(sendMessageZf.equals("err")){
							sendMessage="调用证方对公账户接口错误:"+sendMessage;
						}
					}	
				}
				
			}else{
				sendMessage="SUCCESS";
			}
			sendMessage=sendMessage.replaceAll("\"", "");
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
				String system =  request.getParameter("system");
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
							.getValue()))
					    || (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.ZF_RECEIVE.getValue()))
					    || (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.ZF_CHANNEL_CONFIRM
							.getValue()))
							) {
						logger.info("businessAccountId:" + businessAccountId + ",type:" + type + ",status:" + status  +",system:" + system );
						result = processBusinessAccountStatus(businessAccountId, type,system);
					} else {
						//判断如果是确认领取且状态是被个贷 证方已认领,则直接返回success
						if ((type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.CREDIT_RECEIVE.getValue())&& system.equals(EnumConstants.system.credit.getValue()))
								 ||(type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.ZF_RECEIVE.getValue())&& system.equals(EnumConstants.system.zf.getValue()))
								 ||(type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue()))
								 ||(type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.CREDIT_CHANNEL_CONFIRM.getValue())&& system.equals(EnumConstants.system.credit.getValue()))
								 ||(type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.ZF_CHANNEL_CONFIRM.getValue())&& system.equals(EnumConstants.system.zf.getValue()))
								 ||(type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue()) && status.equals(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue()))) {
							logger.info("else businessAccountId:" + businessAccountId + ",type:" + type + ",status:" + status +",system:" + system);
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

	private String processBusinessAccountStatus(Long businessAccountId, Integer type,String system) {
		String result = null;
		BusinessAccountVO businessAccountVo = new BusinessAccountVO();
		if (type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue())) {

			businessAccountVo.setId(businessAccountId);
			if(system.equals(EnumConstants.system.credit.getValue())){
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.CREDIT_RECEIVE.getValue());
			}else{
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.ZF_RECEIVE.getValue());
			}
		

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue())) {

			businessAccountVo.setId(businessAccountId);
			
			if(system.equals(EnumConstants.system.credit.getValue())){
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.CREDIT_CHANNEL_CONFIRM.getValue());
			}else{
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.ZF_CHANNEL_CONFIRM.getValue());
			}

		} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue())) {

			businessAccountVo.setId(businessAccountId);
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
		}
		logger.info("businessAccountDao update start businessAccountId" + businessAccountId + ",type:" + type+ ",system:" + system);
		businessAccountVo.setSystem(system);
		int affectNum = businessAccountDao.update(businessAccountVo);
		logger.info("affectNum : " + affectNum);

		if (affectNum == 1) {
			SpecBusinessLog specBusinessLog = new SpecBusinessLog();
			specBusinessLog.setKeyId(businessAccountVo.getId());
			specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
			if (type.equals(EnumConstants.CreditBusinessAccountOptType.CONFIRM_RECEIVE.getValue())) {
				if(system.equals(EnumConstants.system.credit.getValue())){
					specBusinessLog.setMessage("被个贷认领");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_RECEIVE.getValue());
				}else{
					specBusinessLog.setMessage("被证方认领");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.ZF_RECEIVE.getValue());
				}
				
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_RECEIVE.getValue())) {
				specBusinessLog.setMessage("撤销");
				if(system.equals(EnumConstants.system.credit.getValue())){
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CANCEL.getValue());
				}else{
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.ZF_CANCEL.getValue());
				}
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CHANNEL_CONFIRM.getValue())) {
				if(system.equals(EnumConstants.system.credit.getValue())){
					specBusinessLog.setMessage("被个贷渠道确认");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CHANNEL_CONFIRM.getValue());
				}else{
					specBusinessLog.setMessage("被证方渠道确认");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.ZF_CHANNEL_CONFIRM.getValue());
				}
			
			} else if (type.equals(EnumConstants.CreditBusinessAccountOptType.CANCEL_CHANNEL_CONFIRM.getValue())) {
				if(system.equals(EnumConstants.system.credit.getValue())){
					specBusinessLog.setMessage("撤销");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.CREDIT_CANCEL.getValue());
				}else{
					specBusinessLog.setMessage("撤销");
					specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.ZF_CANCEL.getValue());
				}
				
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
}
