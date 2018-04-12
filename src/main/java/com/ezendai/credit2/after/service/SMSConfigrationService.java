package com.ezendai.credit2.after.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.after.vo.SMSConfigrationVO;
import com.ezendai.credit2.framework.util.Pager;

public interface SMSConfigrationService {
	/**
	 * 获取所有城市列表
	 * @return
	 */
	 List<SMSConfigrationVO> getCityList();
	 /**
	  * 获取可配置服务电话的城市列表
	  * @param configrationVO
	  * @return
	  */
	 Pager getSmsConfigrationList(SMSConfigrationVO configrationVO);
	 /**
	  * 批量更新城市电话
	  */
	 int plUpdCityPhone(Map<String,Object> map);
	 /**
	  * 批量插入
	  * @param map
	  * @return
	  */
	 int addCityPhone(List<String> list,String phone);
	 
	 int checkPhoneIsExit(Map<String,Object> map);

}
