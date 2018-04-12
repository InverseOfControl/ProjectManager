package com.ezendai.credit2.sign.lcb.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.common.util.FTPUtils;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.sign.constant.ErrorCodeEnum;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.model.LcbBidPushModel;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;
import com.ezendai.credit2.system.service.SysParameterService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description 合同生成
 * @author YM10159
 * @param <T> 请求对象
 * @param <U> 响应对象
 */
@SignHandler(flowNode="lcbContractGenerate")
public class ContractGenerateService<T,U> extends BaseSign<T, U> {

	private static final Logger logger = LoggerFactory.getLogger(ContractGenerateService.class);
	 
	//存管网关接口地址
	@Value("${lcbService}")
	private String lcbGatewayPath;
	//密钥
	@Value("${lcbSignSecretKey}")
	private String lcbSignSecretKey;
	//捞财宝支持的银行
	@Value("${lcbBank}")
	private String lcbBank;
	//pic服务器路径
	@Value("${picContractUpload}")
	private String picContractUpload;
	//捞财宝FTP服务
	@Value("${serverIp}")
	private String serverIp;
	@Value("${serverPort}")
	private String serverPort;
	@Value("${serverUsername}")
	private String serverUsername;
	@Value("${serverPassword}")
	private String serverPassword;
	//上传生份证正反面
	@Value("${web_url}")
	private String loanUrl;
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private IContractGenerateDao contractGenerateDao;
	@Autowired
	private SysParameterService sysParameterService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(T reqObj, U resObj) {
		Map<String,Object> reqMap = (Map<String, Object>) reqObj;
		Map<String,Object> resMap = (Map<String, Object>) resObj;
		String loanId = ObjectUtils.toString(reqMap.get("loanId"));

		LcbModel lcbModel = contractGenerateDao.getPersonInfoByLoanId(reqMap.get("loanId").toString());
		String lcbStatusNode = ObjectUtils.toString(reqMap.get("lcbStatusNode"));
		int lcbAuthStatus = Integer.valueOf(lcbModel.getLcbAuthStatus());
		String oldBankAccount = lcbModel.getBankAccount();
		
		/**校验银行是否是捞财宝指定的银行*/
		if(lcbStatusNode.equals("8")){
			if(!validateBank(ObjectUtils.toString(reqMap.get("bankId")),lcbModel)){
				resMap.put("repCode", ErrorCodeEnum.BANK_CARD_ERROR.getErrorCode());
				resMap.put("repMsg", "当前银行卡暂不支持放款，请更换！");
				return false;
			}
		}
		
		/**验证手机号一致性*/
		if(lcbStatusNode.equals("0") && lcbAuthStatus < 10){
			return validatePhone(lcbModel, resMap);
		}
		
		/**注册*/
		if(lcbStatusNode.equals("1") && lcbAuthStatus < 20){
			lcbModel.setLcbVerifyCode(ObjectUtils.toString(reqMap.get("lcbVerifyCode")));
			if(!register(lcbModel, resMap)){
				return false;
			}
		}
		/**登录*/
		if(lcbStatusNode.equals("2")){
			lcbModel.setLcbVerifyCode(ObjectUtils.toString(reqMap.get("lcbVerifyCode")));
			if(!login(lcbModel, resMap)){
				return false;
			}
		}
		/**实名*/
		if(lcbStatusNode.equals("3") && lcbAuthStatus < 30){
			if(!realName(lcbModel, resMap)){
				return false;
			}
		}
		/**绑卡*/
		if(lcbStatusNode.equals("4")){
			String newBankAccount = ObjectUtils.toString(reqMap.get("bankCard"));
			if(lcbAuthStatus < 40 || (StringUtils.isNotBlank(oldBankAccount) && !newBankAccount.equals(oldBankAccount))){
				//设置bankCode
				validateBank(ObjectUtils.toString(reqMap.get("bankId")),lcbModel);
				lcbModel.setBankCard(ObjectUtils.toString(reqMap.get("bankCard")));
				if(!bindingBankCard(lcbModel, resMap)){
					return false;
				}
			}		
		}
		/**获取验证码*/
		if(lcbStatusNode.equals("9")){
			return getVerifyCode(lcbModel, resMap);
		}
		/**推标和上传文件*/
		if(lcbStatusNode.equals("10")){
			if(!bidPush(loanId, resMap)){
				return false;
			}
			//上传身份证正反面
			if(!uploadIDCardFileToLcb(loanId,resMap)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * description:验证手机号一致性
	 * autor:ym10159
	 * date:2018年1月3日 下午4:18:42
	 */
	private boolean validatePhone(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝手机一致性校验接口开始");
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("cellPhone", lcbModel.getMobilePhone());
		paramsMap.put("idNo", lcbModel.getIdnum());

		String result = HttpUtils.postGateway(lcbGatewayPath+"verifyCellPhone", paramsMap, lcbSignSecretKey, null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝手机一致性校验接口响应："+result);
		
		JSONObject jsonObject = JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
			return false;
		}
		if(jsonObject.getString("resultCode").equals("true")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", "常用手机号["+lcbModel.getMobilePhone()+"]与捞财宝注册的手机号码不一致,请至捞财宝变更手机号码或改签");
			return false;
		}
		updateLcbStatus("phone", ObjectUtils.toString(lcbModel.getPersonId()));
		resMap.put("repMsg", "手机一致性校验成功");
		return true;
	}

	/**
	 * description:捞财宝注册
	 * autor:ym10159
	 * date:2018年1月3日 下午4:31:42
	 */
	private boolean register(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝注册接口开始");
		if (StringUtils.isBlank(lcbModel.getLcbVerifyCode())) {
			resMap.put("repCode", ErrorCodeEnum.VERIFY_CODE_NULL.getErrorCode());
			resMap.put("repMsg", "验证码不能为空,请先获取验证码");
			return false;
		}
		
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("cellPhone", lcbModel.getMobilePhone());
		paramsMap.put("validateCode", lcbModel.getLcbVerifyCode());
		String result= HttpUtils.postGateway(lcbGatewayPath+"register",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝注册接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
			return false;
		}
		updateLcbStatus("register", ObjectUtils.toString(lcbModel.getPersonId()), jsonObject.getString("customerId"));
		return true;
	}

	/**
	 * description:登录捞财宝
	 * autor:ym10159
	 * date:2018年1月3日 下午5:04:35
	 */
	private boolean login(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝登录接口开始");
		if (StringUtils.isBlank(lcbModel.getLcbVerifyCode())) {
			resMap.put("repCode", ErrorCodeEnum.VERIFY_CODE_NULL.getErrorCode());
			resMap.put("repMsg", "验证码不能为空,请先获取验证码");
			return false;
		}
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("cellPhone",lcbModel.getMobilePhone());
		paramsMap.put("validateCode",lcbModel.getLcbVerifyCode());
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"entry",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝登录接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", ErrorCodeEnum.VERIFY_CODE_ERROR.getErrorCode());
			resMap.put("repMsg", jsonObject.getString("repMsg"));
			return false;
		}
		return true;
	}
	
	/**
	 * description:获取验证码
	 * autor:ym10159
	 * date:2018年1月3日 下午5:04:35
	 */
	private boolean getVerifyCode(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝获取验证码接口开始");
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("cellPhone",lcbModel.getMobilePhone());
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"getVerificationCode",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝获取验证码接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", ErrorCodeEnum.VERIFY_CODE_ERROR.getErrorCode());
			resMap.put("repMsg", jsonObject.getString("repMsg"));
			return false;
		}
		return true;
	}
	
	/**
	 * description:捞财宝实名认证
	 * autor:ym10159
	 * date:2018年1月4日 上午9:39:21
	 */
	private boolean realName(LcbModel lcbModel, Map<String,Object> resMap) {
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝实名认证接口开始");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("customerId", lcbModel.getCustomerId());
        paramsMap.put("name", lcbModel.getName());
        paramsMap.put("idNo", lcbModel.getIdnum());
        
        String result = HttpUtils.postGateway(lcbGatewayPath+"realName", paramsMap, lcbSignSecretKey, null);
        logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝实名认证接口响应："+result);
        
        JSONObject jsonObject = JSON.parseObject(result);
		if (!jsonObject.getString("repCode").equals("000000")) {
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
        }
		updateLcbStatus("realname", ObjectUtils.toString(lcbModel.getPersonId()));
		return true;
    }
	
	/**
	 * description:绑定银行卡
	 * autor:ym10159
	 * date:2018年1月4日 上午9:50:58
	 */
	private boolean bindingBankCard(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝绑卡接口开始");
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("customerId", lcbModel.getCustomerId());
		paramsMap.put("bankCard",lcbModel.getBankCard());
		paramsMap.put("cellPhone",lcbModel.getMobilePhone());
		paramsMap.put("bankCode",lcbModel.getBankCode());
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"bindBankCard",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝绑卡接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
		}
		updateLcbStatus("bindcard", ObjectUtils.toString(lcbModel.getPersonId()));
		return true;
	}
	
	/**
	 * description:查询设密状态
	 * autor:ym10159
	 * date:2018年2月8日 上午10:58:32
	 */
	public boolean querySetPwdStatus(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝查询设密状态接口开始");
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("customerId", lcbModel.getCustomerId());
		paramsMap.put("cellPhone",lcbModel.getMobilePhone());
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"querySetPwd ",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝查询设密状态接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
		}
		
		//0.未设密 1.未免密 2.已设密已免密
		String status = ObjectUtils.toString(jsonObject.get("status"));
		return true;
	}
	
	/**
	 * description:设置密码
	 * autor:ym10159
	 * date:2018年2月8日 上午10:58:32
	 */
	public boolean setPwd(LcbModel lcbModel, Map<String,Object> resMap){
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝设置密码接口开始");
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("customerId", lcbModel.getCustomerId());
		paramsMap.put("cellPhone",lcbModel.getMobilePhone());
		paramsMap.put("notifyUrl","");
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"querySetPwd ",paramsMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+lcbModel.getLoanId()+"】->调用捞财宝设置密码接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
		}
		String url = jsonObject.getString("url");
		return true;
	}
	
	/**
	 * description:记录捞财宝注册、实名、绑卡状态
	 * autor:ym10159
	 * date:2018年1月24日 上午10:16:38
	 * @param flag 状态标识
	 * @param personId 客户ID
	 * @param args 参数
	 */
	public void updateLcbStatus(String flag, String personId, String ...args){
		Map<String,Object> map = new HashMap<>();
		map.put("personId", personId);
		
		if("phone".equals(flag)) map.put("lcbAuthStatus", "10");
		if("register".equals(flag)) {
			map.put("lcbAuthStatus", "20");
			map.put("customerId", args[0]);
		}
		if("realname".equals(flag)) map.put("lcbAuthStatus", "30");
		if("bindcard".equals(flag)) map.put("lcbAuthStatus", "40");
		contractGenerateDao.updateLcbStatus(map);
	}
	
	private boolean validateBank(String bankId,LcbModel lcbModel){
		Map<String,Object> bankInfoMap = contractGenerateDao.getBankCodeById(bankId);
		String bankCode = ObjectUtils.toString(bankInfoMap.get("BANK_CODE"));
		
		String[] lcbBankArrStr = lcbBank.split(",");
		Map<String,Object> bankMap = new HashMap<>();
		for (String string : lcbBankArrStr) {
			bankMap.put(string.split("=")[0], string.split("=")[1]);
		}
		if(StringUtils.isBlank(ObjectUtils.toString(bankMap.get(bankCode)))){
			return false;
		}
		lcbModel.setBankCode(ObjectUtils.toString(bankMap.get(bankCode)));
		return true;
	}
	
	
	/**
	 * description:推送标到捞财宝
	 * autor:ym10159
	 * date:2018年1月4日 上午11:28:43
	 */
	private boolean bidPush(String loanId,Map<String,Object> resMap) {
		logger.info("借款ID【"+loanId+"】->调用捞财宝推标接口开始");
		LcbModel lcbModel = contractGenerateDao.getPersonInfoByLoanId(loanId.toString());
		String lcbBorrowStatus = lcbModel.getLcbBorrowStatus();
		String lcbBorrowNo = lcbModel.getLcbBorrowNo();
		
		//如果是终止借款或是流标，要把之前的借款编号+1，再推送
		if(StringUtils.isNotBlank(lcbBorrowStatus) && (lcbBorrowStatus.equals("10") || lcbBorrowStatus.equals("20"))){
			String lcbBorrowNoPre = lcbBorrowNo.substring(0,lcbBorrowNo.lastIndexOf("_")+1);
			String lcbBorrowNoLast = lcbBorrowNo.substring(lcbBorrowNo.lastIndexOf("_")+1,lcbBorrowNo.length());
			lcbBorrowNo = lcbBorrowNoPre + (Integer.valueOf(lcbBorrowNoLast) + 1);
		}else{
			lcbBorrowNo = "CD_"+loanId+"_AITE_0";
		}
		
		//计算各种费用
		Map<String,Object> loanFeesMap = calLoanFees(loanId);
		//获取借款信息
		LcbBidPushModel lcbBidPushModel = contractGenerateDao.getBidPushData(loanId);
		
		lcbBidPushModel.setBorrowNo(lcbBorrowNo); //推送到捞财宝的借款编号
		lcbBidPushModel.setIdType("1"); //身份证
		lcbBidPushModel.setMaritalStatus(enumValConvert("maritalStatus", lcbBidPushModel.getMaritalStatus())); //婚姻状况
		lcbBidPushModel.setHasCar("1"); //是否有车
		lcbBidPushModel.setHasHourseLoan(StringUtils.isBlank(lcbBidPushModel.getHasHourseLoan()) ? "0" : "1"); //是否有房贷
		lcbBidPushModel.setHasHourse("1"); //是否有房 
		lcbBidPushModel.setSex(getGenderByIdNo(lcbBidPushModel.getIdNo())); //性别
		lcbBidPushModel.setBirth(getBirthDayByIdNo(lcbBidPushModel.getIdNo())); //出生日期
		lcbBidPushModel.setHasCarLoan(enumValConvert("hasCarLoan", lcbBidPushModel.getHasCarLoan())); //是否有车贷
		lcbBidPushModel.setEducationLevel(enumValConvert("educationLevel", lcbBidPushModel.getEducationLevel())); //教育等级
		lcbBidPushModel.setRepaymentType("等额本息"); //还款方式
		lcbBidPushModel.setCompanyNature(enumValConvert("companyNature", lcbBidPushModel.getCompanyNature())); //单位性质
		lcbBidPushModel.setTrade(enumValConvert("trade", lcbBidPushModel.getTrade())); //行业
		
		lcbBidPushModel.setManagementFee(ObjectUtils.toString(loanFeesMap.get("managePart0Fee"))); //管理费1
		lcbBidPushModel.setOtherManagementFee(ObjectUtils.toString(loanFeesMap.get("managePart1Fee"))); //管理费2
		lcbBidPushModel.setServiceFee(ObjectUtils.toString(loanFeesMap.get("serviceFee"))); //服务费
		lcbBidPushModel.setAuditFee(ObjectUtils.toString(loanFeesMap.get("assessmentFee"))); //审核费
		lcbBidPushModel.setConsultFee(ObjectUtils.toString(loanFeesMap.get("consultingFee"))); //咨询费
		lcbBidPushModel.setRemitMoney(ObjectUtils.toString(loanFeesMap.get("grantMoney"))); //放款金额
		lcbBidPushModel.setRiskFund(ObjectUtils.toString(loanFeesMap.get("risk"))); //风险金
		lcbBidPushModel.setBorrowAmount(new BigDecimal(loanFeesMap.get("pactMoney").toString())); //合同金额
		lcbBidPushModel.setAttachmentUrl(lcbFileDownPath(loanId)); //附件路径
		
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);  
		@SuppressWarnings("unchecked")
		Map<String,String> lcbBidPushMap = om.convertValue(lcbBidPushModel, Map.class);
		//调用推标接口
		String result = HttpUtils.postGateway(lcbGatewayPath+"targetPushed",lcbBidPushMap,lcbSignSecretKey,null);
		logger.info("借款ID【"+loanId+"】->调用捞财宝推标接口响应："+result);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
		}
		//记录推送给捞财宝的借款编号
		Map<String,Object> tempMap = new HashMap<>();
		tempMap.put("id", loanId);
		tempMap.put("flag", "1");
		tempMap.put("lcbBorrowNo", lcbBorrowNo);
		contractGenerateDao.insertLcbBorrowNo(tempMap);
		return true;
	}
	
	/**
	 * 计算风险金、咨询费、审核费、管理费1、管理费2
	 */
	private Map<String,Object> calLoanFees(String id){
		BigDecimal newCarLoanMonthRate = new BigDecimal(String.valueOf(0.01)); //车贷新产品月利率
		
		Map<String,Object> loanInfoMap = contractGenerateDao.getLoanInfo(id);
		Long productId = Long.valueOf(loanInfoMap.get("PRODUCT_ID").toString()); //产品ID
		BigDecimal consultingFeeRate = new BigDecimal(sysParameterService.getByCode(EnumConstants.CAR_NEW_CON_RATE_NEW).getParameterValue()); //咨询费费率
		BigDecimal assessmentFeeRate = new BigDecimal(sysParameterService.getByCode(EnumConstants.CAR_NEW_ASS_RATE_NEW).getParameterValue()); //评估费费率
		BigDecimal sumRate = new BigDecimal(sysParameterService.getByCode(EnumConstants.CAR_NEW_SUM_RATE_NEW).getParameterValue()); //综合费率
		BigDecimal riskRate = (BigDecimal) loanInfoMap.get("RISK_RATE"); //风险金比例
		
		BigDecimal auditMoney = (BigDecimal) loanInfoMap.get("AUDIT_MONEY"); //审批金额
		BigDecimal auditTime = new BigDecimal(ObjectUtils.toString(loanInfoMap.get("AUDIT_TIME"))); //审批期限
		BigDecimal pactMoney = auditMoney; //合同金额
		
		BigDecimal risk = BigDecimal.ZERO; //风险金
		if (productId.compareTo(2L) != 0) {
			risk = (pactMoney.multiply(riskRate)).divide(new BigDecimal(12), 8,BigDecimal.ROUND_HALF_UP).multiply(auditTime).setScale(2, BigDecimal.ROUND_HALF_UP);
			pactMoney = pactMoney.add(risk);
		}
		
		//咨询费(审批金额*咨询费费率=咨询费)
		BigDecimal consultingFee = auditMoney.multiply(consultingFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		//评估费(审批金额*评估费费率=评估费)
		BigDecimal assessmentFee = auditMoney.multiply(assessmentFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);;
		//每月还款金额
		BigDecimal repayAmount = auditMoney.divide(auditTime, 8, BigDecimal.ROUND_HALF_UP).add(auditMoney.multiply(sumRate)).setScale(2, BigDecimal.ROUND_HALF_UP);
		//应还本息
		BigDecimal averageCapital = new BigDecimal(String.valueOf(PMT(newCarLoanMonthRate.doubleValue(), auditTime.doubleValue(), pactMoney.doubleValue()))).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		//丙方管理费=合同金额*4%/12
		BigDecimal managePart1Fee = pactMoney.multiply(new BigDecimal(String.valueOf(0.04))).divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		//乙方管理费=每月还款金额-应还本息-丙方管理费
		BigDecimal managePart0Fee = repayAmount.subtract(averageCapital).subtract(managePart1Fee).setScale(2, BigDecimal.ROUND_HALF_UP);
		//划拨金额（划款金额=合同金额-风险金-咨询费-评估费）
		BigDecimal grantMoney = pactMoney.subtract(risk).subtract(consultingFee).subtract(assessmentFee).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("risk", risk);
		returnMap.put("consultingFee", consultingFee); 
		returnMap.put("assessmentFee", assessmentFee);
		returnMap.put("managePart1Fee", managePart1Fee);
		returnMap.put("managePart0Fee", managePart0Fee);
		returnMap.put("grantMoney", grantMoney);
		returnMap.put("serviceFee", consultingFee.add(assessmentFee).add(risk));
		returnMap.put("pactMoney", pactMoney);
		return returnMap;
	}
	
	/**
	 * description:上传身份证信息到捞财宝ftp服务器
	 * autor:ym10159
	 * date:2018年1月29日 下午4:07:54
	 */
	public boolean uploadIDCardFileToLcb(String loanId,Map<String,Object> resMap) {
		logger.info("借款ID【"+loanId+"】->调用上传身份证正反面接口开始");
		boolean bool = false;
		Map<String, String> rquestMap = new HashMap<String, String>();

		rquestMap.put("appNo", loanId);
		rquestMap.put("subclass_sort", "B");
		rquestMap.put("operator",ApplicationContext.getUser().getLoginName());
		rquestMap.put("jobNumber",ApplicationContext.getUser().getLoginName());
		
		//获取身份证信息
		String baseFilePath = "";
		String linuxFilePath = "/uploads/"+loanId+"/"+loanId+"_1/"+loanId+"_1_206/";
		if(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")){
			baseFilePath = uploadPath+"/"+loanId+"/"+loanId+"_1/"+loanId+"_1_206/";
		}else{
			baseFilePath = linuxFilePath;
		}
		
		File[] picList = new File(baseFilePath).listFiles();
		if(null == picList || picList.length == 0){
			resMap.put("repCode", "000001");
			resMap.put("repMsg", "没有查询到要上传的身份证附件信息");
			return false;
		}
		
		try {
			bool = uploadIDCardFile(picList,linuxFilePath,loanId);
		} catch (Exception e) {
			logger.error("借款ID【"+loanId+"】->调用上传身份证正反面接口异常",e);
			resMap.put("repCode", "000002");
			resMap.put("repMsg", "身份证附件上传失败");
			return false;
		}
		if(!bool){
			resMap.put("repCode", "000003");
			resMap.put("repMsg", "身份证附件上传失败");
			return false;
		}
		return bool;
	}
	
	public boolean uploadIDCardFile(File[] picList,String relativeFilePath,String loanId) throws Exception {
		FTPUtils ftpUtils = new FTPUtils();
		boolean bool = false;
		
		FTPClient ftp = ftpUtils.connect(serverIp, Integer.parseInt(serverPort), serverUsername, serverPassword);
		
		String SF = lcbFileDownPath(loanId);
		ftpUtils.createDirs(ftp, SF);

		//上传省份证附件
		for (int i = 0; i < picList.length; i++) {
			File file = picList[i];
			BufferedInputStream bis = ftpUtils.getPic(loanUrl+relativeFilePath+file.getName());
			bool = ftpUtils.uploadFile(ftp, SF, file.getName(), bis);
			if(!bool){
				return false;
			}
		}
		ftpUtils.close(ftp);
		return bool;
	}
	
	/**
	 * description:捞财宝附件下载地址
	 * autor:ym10159
	 * date:2018年2月9日 上午10:51:36
	 */
	public String lcbFileDownPath(String loanId){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String mouth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		
		return year + "/" + mouth + "/" + day + "/" + loanId + "/" + "SF/";
	}
	
	/**
	 * description:车贷系统枚举值转换成捞财宝需要的枚举值
	 * autor:ym10159
	 * date:2018年1月12日 下午3:38:06
	 */
	private String enumValConvert(String flag, String val){
		String ret = "";
		if(flag.equals("maritalStatus")){
			if(val.equals("0")) ret = "2"; //未婚
			if(val.equals("1")) ret = "1"; //已婚
			if(val.equals("2")) ret = "4"; //离异
			if(val.equals("5")) ret = "3"; //其它
		}
		if(flag.equals("hasCarLoan")){
			if(val.equals("1")) ret = "1"; //有
			if(val.equals("2")) ret = "0"; //无
		}
		if(flag.equals("educationLevel")){
			if(val.equals("1")) ret = "7"; //硕士
			if(val.equals("2")) ret = "6"; //本科
			if(val.equals("3")) ret = "5"; //大专
			if(val.equals("5")) ret = "4"; //高中
			if(val.equals("6")) ret = "3"; //初中及以下
		}
		//行业
		if(flag.equals("trade")){
			if(val.equals("1")) ret = "农、林、牧、渔业";
			if(val.equals("2")) ret = "能源、采矿业";
			if(val.equals("3")) ret = "食品、药品、工业原料、服装、日用品等制造业";
			if(val.equals("4")) ret = "电力、热力、燃气及水生产和供应业";
			if(val.equals("5")) ret = "建筑业";
			if(val.equals("6")) ret = "批发和零售业";
			if(val.equals("7")) ret = "交通运输、仓储和邮政业";
			if(val.equals("8")) ret = "住宿、旅游、餐饮业";
			if(val.equals("9")) ret = "信息传输、软件和信息技术服务业";
			if(val.equals("10")) ret = "金融业";
			if(val.equals("11")) ret = "房地产业";
			if(val.equals("12")) ret = "租凭和商务服务业";
			if(val.equals("13")) ret = "科学研究、技术服务业";
			if(val.equals("14")) ret = "水利、环境和公共设施管理业";
			if(val.equals("15")) ret = "居民服务、修理和其他服务业";
			if(val.equals("16")) ret = "教育、培训";
			if(val.equals("17")) ret = "卫生、医疗、社会保障、社会福利";
			if(val.equals("18")) ret = "文化、体育和娱乐业";
			if(val.equals("19")) ret = "政府、非盈利机构和社会组织";
			if(val.equals("20")) ret = "警察、消防、军人";
			if(val.equals("21")) ret = "其他";
		}
		//单位性质
		if(flag.equals("companyNature")){
			if(val.equals("0") || val.equals("1") || val.equals("2")) ret = "3"; //事业
			if(val.equals("3")) ret = "2"; //外资
			if(val.equals("4") || val.equals("5")) ret = "0"; //民营
			if(val.equals("6")) ret = "100"; //其它
			if(val.equals("7")) ret = "1"; //合资
		}
		return ret;
	}
	
	/**
	 * description:通过身份证号获取出生年月日
	 * autor:ym10159
	 * date:2018年1月12日 下午3:33:00
	 */
	private static String getBirthDayByIdNo(String idNo){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isBlank(idNo)) return null;

		int year = Integer.parseInt(idNo.substring(6, 10));
		int month = Integer.parseInt(idNo.substring(10, 12));
		int date = Integer.parseInt(idNo.substring(12, 14));

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return df.format(cal.getTime());
	}
	
	/**
	 * description:通过身份证号获取性别
	 * autor:ym10159
	 * date:2018年1月12日 下午3:34:12
	 */
	private static String getGenderByIdNo(String idNo){
		int sex = Integer.parseInt(idNo.substring(16, 17));
		return sex % 2 == 0 ? "1" : "2";
	}
	
	/**
	 * 计算月供
	 * @param rate 月利率
	 * @param term 贷款期数，单位月
	 * @param financeAmount 贷款金额
	 * @return 月供
	 */
	private double PMT(double rate, double term, double financeAmount) {
		double v = (1 + rate);
		double t = (-(term / 12) * 12);
		double result = (financeAmount * rate) / (1 - Math.pow(v, t));
		return result;
	}
}
