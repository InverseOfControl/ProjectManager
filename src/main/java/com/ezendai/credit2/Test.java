package com.ezendai.credit2;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.model.LcbBidPushModel;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	
	private static IContractGenerateDao contractGenerateDao = null;
	
	public static void main(String args[]){
		System.setProperty("global.config.path", "D:\\eclipse_workSpace\\projects\\car-conf\\car-main");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationMainContext.xml");
		
		contractGenerateDao = ctx.getBean(IContractGenerateDao.class);
		Map<String,Object> resMap = new HashMap<>(); 
		String loanId = "46098";
		new Test().bidPush("66666666",resMap);
	}
	
	public boolean bidPush(String loanId,Map<String,Object> resMap) {
		
		String lcbBorrowNo = "CD_"+loanId+"_AITE_0";
		LcbBidPushModel lcbBidPushModel = new LcbBidPushModel();
		
		lcbBidPushModel.setBorrowNo(lcbBorrowNo); //推送到捞财宝的借款编号
		lcbBidPushModel.setBorrowAmount(new BigDecimal("50000.00"));
		lcbBidPushModel.setBorrowTerm("3");
		lcbBidPushModel.setRemitMoney("47655.00");
		lcbBidPushModel.setYearRate("0.0840");
		lcbBidPushModel.setServiceFee("2345.00");
		lcbBidPushModel.setRiskFund("0");  //???
		lcbBidPushModel.setConsultFee("211.25");
		lcbBidPushModel.setAuditFee("211.25");
		lcbBidPushModel.setManagementFee("422.50");
		lcbBidPushModel.setOtherManagementFee("0");
		lcbBidPushModel.setBorrowRate(new BigDecimal("0"));  //???
		lcbBidPushModel.setRepaymentType("先息后本");	
		lcbBidPushModel.setBorrowPurpose("先息后本测试");
		lcbBidPushModel.setProductName("新车贷产品");
		lcbBidPushModel.setBorrowerName("先息后本测试");
		lcbBidPushModel.setBorrowerPhone("13677778888");
		lcbBidPushModel.setIdType("1");
		lcbBidPushModel.setIdNo("230600199008247291");
		lcbBidPushModel.setSex("1");
		lcbBidPushModel.setMaritalStatus("1");
		lcbBidPushModel.setBirth("1989-02-11");
		lcbBidPushModel.setCity("上海市");
		lcbBidPushModel.setEducationLevel("6");
		lcbBidPushModel.setHasCar("1");
		lcbBidPushModel.setHasHourse("1"); 
		lcbBidPushModel.setTrade("建筑业");
		lcbBidPushModel.setPost("测试");
		lcbBidPushModel.setCompanyNature("3");
		lcbBidPushModel.setMonthlyIncome("6000");
		lcbBidPushModel.setCreditNums("1");
		lcbBidPushModel.setLoanNums("1");
		lcbBidPushModel.setHasCarLoan("1");
		lcbBidPushModel.setHasHourseLoan("1");
		
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);  
		@SuppressWarnings("unchecked")
		Map<String,String> lcbBidPushMap = om.convertValue(lcbBidPushModel, Map.class);
		//调用推标接口
		String result = HttpUtils.postGateway("http://172.16.230.175:8000/fund-deposit/targetPushed",lcbBidPushMap,"3C0E21DEE4AD75F8F83F2E2FB75E8F5F",null);
		
		JSONObject jsonObject= JSON.parseObject(result);
		if(!jsonObject.getString("repCode").equals("000000")){
			resMap.put("repCode", jsonObject.getString("repCode"));
			resMap.put("repMsg", jsonObject.getString("repMsg"));
            return false;
		}
		return true;
	}
}
