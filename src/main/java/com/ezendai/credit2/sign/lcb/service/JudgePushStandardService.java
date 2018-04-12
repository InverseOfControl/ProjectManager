package com.ezendai.credit2.sign.lcb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ezendai.credit2.apply.dao.LoanDao;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.sign.constant.LcbConfig;
import com.ezendai.credit2.sign.lcb.dao.JudgePushStandardDao;
import com.ezendai.credit2.sign.lcb.dao.ZonganRiskDao;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 判断是否已经推标
 * @author YM10180
 *
 */
@SignHandler(flowNode="JudgePushStandardService")
public class JudgePushStandardService<T,U> extends BaseSign<T, U> {
	private static final Logger logger = Logger.getLogger(JudgePushStandardService.class);
	private Gson gson = new Gson();
	
	//密钥
	@Value("${lcbSignSecretKey}")
	private String lcbSignSecretKey;
	
//	@Value("${lcbStopLoan}")
//	private String lcbStopLoan;
	
	 @Autowired
	 private LcbConfig lcbConfig;
	 
	@Autowired
	private JudgePushStandardDao judgePushStandardDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(T reqObj, U resObj) {
		UserSession session=ApplicationContext.getUser();
		Map<String,Object> reqMap = (Map<String, Object>) reqObj;
		Map<String,Object> resMap = (Map<String, Object>) resObj;
		logger.info(gson.toJson(reqMap)+"--------------参数------------JudgePushStandardService");
		//判断是否推送标的到捞财宝,查询表字段
		Map<String,Object> returnLoanData=judgePushStandardDao.findLonaById((Long)reqMap.get("loanId"));
		logger.info(gson.toJson(returnLoanData)+"-------------------------根据ID查询LOAN表获取是否推标");
		Object lcbBorrowNo=returnLoanData.get("LCB_BORROW_NO");
		Object lcbBorrowStatus=returnLoanData.get("LCB_BORROW_STATUS");
		Boolean isPushLcb;
		if(lcbBorrowStatus != null){
			isPushLcb=false;
		}else{
			
			if(null==lcbBorrowNo){
				isPushLcb=false;
			}else{
				isPushLcb=true;
			}
		}
		
		
		if(isPushLcb){//如果是true ,推送到捞财宝了，需调用捞财宝接口终止借款接口
			Map<String,String> map=new HashMap<String,String>();
			
			map.put("borrowNo", lcbBorrowNo.toString());
			map.put("reason", (String) reqMap.get("LoanCancelSelect"));
			
			String repsonseEntity=HttpUtils.postGateway(lcbConfig.getLcbStopLoan(),map,lcbSignSecretKey);
			
			
			logger.info("http返回String--------------------"+repsonseEntity);
			ObjectMapper mapper = new ObjectMapper(); 
		       
	        try {
	        	JsonNode node = mapper.readTree(repsonseEntity);  
	        	JsonNode nodeRepCode=node.get("repCode");
	        	if(nodeRepCode.asText().equals("000000")){
	        		logger.info("http返回成功--------------------"+repsonseEntity);
	        		resMap.put("repCode", "000000");
	        	}else{
	        		logger.info("http返回失败--------------------"+repsonseEntity);
	        		resMap.put("repCode", "000001");
	        	}
			} catch (Exception e) {
				logger.info("JSON字符串获取jsonNode报错--------------------"+e.getMessage());
			}
			return true;
		}else{//否则false ， 不调用捞财宝接口直接取消
			logger.info("不去调用捞财宝终止借款接口--------------------");
			resMap.put("repCode", "000000");
			return true;
		}
		
	}
}
