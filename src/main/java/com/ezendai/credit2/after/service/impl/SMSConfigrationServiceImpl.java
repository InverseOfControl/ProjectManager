package com.ezendai.credit2.after.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.dao.SMSConfigrationDao;
import com.ezendai.credit2.after.service.SMSConfigrationService;
import com.ezendai.credit2.after.vo.SMSConfigrationVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class SMSConfigrationServiceImpl implements SMSConfigrationService{
	@Autowired
	private SMSConfigrationDao configrationDao;

	@Override
	public List<SMSConfigrationVO> getCityList() {
		// TODO Auto-generated method stub
		return configrationDao.getCityList();
	}

	@Override
	public Pager getSmsConfigrationList(SMSConfigrationVO configrationVO) {
		// TODO Auto-generated method stub
		return configrationDao.getSmsConfigrationList(configrationVO);
	}

	@Override
	public int plUpdCityPhone(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return configrationDao.plUpdCityPhone(map);
	}

	@Override
	public int addCityPhone(List<String> list,String phone) {
		int count=0;
		Map<String,Object> map = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			map.put("cityId", list.get(i));
			map.put("servicePhone", phone);
			int reulst=checkPhoneIsExit(map);
			if(reulst>0){
				count+=plUpdCityPhone(map);
			}else{
				count+=configrationDao.addCityPhone(map);
			}
		}
		return count;
	}

	@Override
	public int checkPhoneIsExit(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return configrationDao.checkPhoneIsExit(map);
	}
}
