package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.master.dao.SysEnumerateDao;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.master.vo.SysEnumerateVO;
/**
 * 枚举服务实现
 * 
 * @author chenqi
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
@Service
public class SysEnumerateServiceImpl implements SysEnumerateService {
	@Autowired
	private SysEnumerateDao sysEnumerateDao;

	@Override
	public List<SysEnumerate> findSysEnumerateListByType(String enumType) {
		// TODO Auto-generated method stub
		List<SysEnumerate> listEnum = sysEnumerateDao.findSysEnumerateListByType(enumType);
		return listEnum;
	}

	@Override
	public String findEnumValue(String enumType, Integer enumCode) {
		
		SysEnumerateVO sysEnumerateVO = new SysEnumerateVO();
		sysEnumerateVO.setEnumType(enumType);
		sysEnumerateVO.setEnumCode(enumCode);
		SysEnumerate sysEnumerate = sysEnumerateDao.findSysEnumerateByTypeAndCode(sysEnumerateVO);
		if (sysEnumerate != null) {
			return sysEnumerate.getEnumValue();
		}

		return null;
	}

	@Override
	public List<SysEnumerate> findEnumList(String enumType, Integer[] enumCodes) {
		if (enumCodes == null || enumCodes.length ==0) {
			return null;
		}
		List<SysEnumerate> list = findSysEnumerateListByType(enumType);
		for (int i = 0; i < list.size(); i++) {
			SysEnumerate sysEnumerate = list.get(i);
			if (!enumCodeIsInSysEnumerateList(sysEnumerate.getEnumCode(), enumCodes)) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}

	
	@Override
	public List<SysEnumerate> findEnumListExclusionEnumCodes(String enumType,
			Integer[] exclusionEnumCodes) {
		if (exclusionEnumCodes == null || exclusionEnumCodes.length == 0) {
			return null;
		}
		List<SysEnumerate> list = findSysEnumerateListByType(enumType);
		for (int i = 0; i < list.size(); i++) {
			SysEnumerate sysEnumerate = list.get(i);
			if (enumCodeIsInSysEnumerateList(sysEnumerate.getEnumCode(), exclusionEnumCodes)) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}
	
	private boolean enumCodeIsInSysEnumerateList(Integer enumCode, Integer[] enumCodes){		
		for (int i = 0; i < enumCodes.length; i++) {
			if (enumCode.equals(enumCodes[i])) {
				return true;
			}
		}
		return false;
	}
}
