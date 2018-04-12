package com.ezendai.credit2.sign.lcb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.sign.constant.LcbConfig;
import com.ezendai.credit2.sign.lcb.dao.ZonganRiskDao;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 众安风险调用捞财宝
 * @author YM10180
 *
 */
@SignHandler(flowNode="LcbZonganRiskService")
public class ZonganRiskService<T,U> extends BaseSign<T, U>{
	private static final Logger logger = Logger.getLogger(ZonganRiskService.class);
	private Gson gson = new Gson();
	
//	@Value("${lcbBorrowerInfo}")
//	private String lcbBorrowerInfo;
	//密钥
	@Value("${lcbSignSecretKey}")
	private String lcbSignSecretKey;
	
	@Autowired
	private ZonganRiskDao zonganRiskDao;
	
	 @Autowired
	 private LcbConfig lcbConfig;
	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(T reqObj, U resObj) {
		UserSession session=ApplicationContext.getUser();
		Map<String,Object> reqMap = (Map<String, Object>) reqObj;
		Map<String,Object> resMap = (Map<String, Object>) resObj;
		logger.info(gson.toJson(reqMap)+"--------------参数------------ZonganRiskService");
		
		//提前先判断下保存众安欺诈返回结果对应的借款是否已经存在数据(状态等于1),如果表里面有数据，就不擦入
		Integer statusSize=zonganRiskDao.findLoanIdAndStatus((String)reqMap.get("id"));
				
		if(statusSize>0){
			return true;
		}
				
		String personIdnum=(String) reqMap.get("personIdnum");
		String sex=getGenderByIdCard(personIdnum);
		String date=getBirthByIdCard(personIdnum);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
		try {
			date=sdf1.format(sdf.parse(date));
		} catch (ParseException e) {
			logger.info("日期转换出错------------"+gson.toJson(date));
			e.printStackTrace();
		}
		String name=(String) reqMap.get("personName");
		String phone=(String) reqMap.get("mobilePhoneLoan");
		Map<String,String> map=new HashMap<String,String>();
		map.put("birthday", date);
		map.put("cellPhone", phone);
		map.put("idNo", personIdnum);
		map.put("name", name);
		map.put("sex", sex);
		String repsonseEntity=HttpUtils.postGateway(lcbConfig.getLcbBorrowerInfo(),map,lcbSignSecretKey);
		
		//String repsonseEntity="{\"repCode\":\"000000\",\"repMsg\":\"处理成功!\",\"riskGrade\":\"A0\",\"auditResult\":\"0\"}";
		
		logger.info("http返回String--------------------"+repsonseEntity);
		ObjectMapper mapper = new ObjectMapper(); 
	       
        try {
        	JsonNode node = mapper.readTree(repsonseEntity);  
        	JsonNode nodeRepCode=node.get("repCode");
        	if(nodeRepCode.asText().equals("000000")){
        		JsonNode riskGrade=node.get("riskGrade");//等级
        		JsonNode auditResult=node.get("auditResult");//结果
        		String loanId=(String) reqMap.get("id");
        		//保存数据到PUSH_LCB_RETURN_ZONGAN_FRAUD表
        		Map<String,Object> insertMap=new HashMap<String,Object>();
        		insertMap.put("riskGrade", riskGrade.asText().toString());
        		insertMap.put("auditResult", auditResult.asText().toString());
        		insertMap.put("loanId", Long.parseLong(loanId));
        		insertMap.put("createdId", session.getId());
        		insertMap.put("createdName", session.getLoginName());
        		zonganRiskDao.insertZonganReturnData(insertMap);
        	}else{
        		logger.info("http返回失败--------------------"+repsonseEntity);
        	}
		} catch (Exception e) {
			logger.info("JSON字符串获取jsonNode报错--------------------"+e.getMessage());
		}
		return true;
	}
	
	//获取性别
	public static String getGenderByIdCard(String idCard) {
        String sGender = "未知";

        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "2";//男
        } else {
            sGender = "1";//女
        }
        return sGender;
    }
	
	//获取出身日期
	public static String getBirthByIdCard(String idCard) {
	    return idCard.substring(6, 14);
	}
	
	public static void main(String[] args) throws ParseException {
		String a="350881199304042174";
		String aaa=getBirthByIdCard(a);
		System.out.println(aaa);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf1.format(sdf.parse(aaa)));
		String bbb=getGenderByIdCard(a);
		System.out.println(bbb);
		String aaaaa="{\"repCode\":\"000000\",\"repMsg\":\"处理成功!\",\"riskGrade\":\"A0\",\"auditResult\":\"0\"}";
		System.out.println(aaaaa);
		ObjectMapper mapper = new ObjectMapper(); 
	       
        try {
        	JsonNode node = mapper.readTree(aaaaa);  
        	JsonNode nodeString=node.get("repCode");
        	JsonNode nodeString1=node.get("riskGrade");
//        	approvalOpinionsVO = mapper.readValue(nodeString.toString(), ApprovalOpinionsVO.class);
//        	
//        	if(approvalOpinionsVO == null){
//        		return new ApprovalOpinionsVO();
//        	}
        	System.out.println("a");
		} catch (Exception e) {
			logger.info("JSON字符串转实体报错--------------------"+e.getMessage());
		}
	}
}
