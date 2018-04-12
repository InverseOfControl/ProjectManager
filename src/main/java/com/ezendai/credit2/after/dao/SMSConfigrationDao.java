package com.ezendai.credit2.after.dao;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.after.vo.SMSConfigrationVO;
import com.ezendai.credit2.framework.util.Pager;

public interface SMSConfigrationDao {
	/**
	 * 获取城市列表
	 * @return
	 */
	 List<SMSConfigrationVO> getCityList();
	 
	 /**
	  * 获取可配置服务电话的城市列表
	  */
	 Pager getSmsConfigrationList(SMSConfigrationVO smsConfigrationVO);
	 /**
	  * 批量更新城市电话
	  */
	 int plUpdCityPhone(Map<String,Object> map);
	 /**
	  * 批量插入
	  * @param map
	  * @return
	  */
	 int addCityPhone(Map<String,Object> map);
	 
	 
	 int checkPhoneIsExit(Map<String,Object> map);
}
