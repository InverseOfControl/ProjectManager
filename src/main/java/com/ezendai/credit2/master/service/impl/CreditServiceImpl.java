package com.ezendai.credit2.master.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.HttpUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.CreditService;
import com.ezendai.credit2.system.Credit2Properties;

@Service
public class CreditServiceImpl  implements CreditService {

	
	@Autowired
	private Credit2Properties credit2Properties;
	
	@Override
	public String sendBusinessAccountData(String repayDate, String repayTime, String secondCompany, String amount,String type) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("repayDate",repayDate);
		map.put("repayTime",repayTime);
		map.put("secondCompany", secondCompany);
		map.put("amount", amount);
		map.put("type", type);
		String sendMessage = HttpUtil.postDataByHTTP(map, credit2Properties.getCreditServiceUrl() + "/businessAccount/updateBusinessAccountStatus");
		return sendMessage;
	}
	
	@Override
	public String sendBusinessAccountDataUnifie(String repayDate, String repayTime, String secondCompany, String amount,String type,String url) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("repayDate",repayDate);
		map.put("repayTime",repayTime);
		map.put("secondCompany", secondCompany);
		map.put("amount", amount);
		map.put("type", type);
		map.put("system", EnumConstants.system.car.getValue());
		String sendMessage = HttpUtil.postDataByHTTP(map, url + "/businessAccount/updateBusinessAccountStatus");
		return sendMessage;
	}
 
}
