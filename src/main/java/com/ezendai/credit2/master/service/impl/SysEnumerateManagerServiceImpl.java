package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysEnumerateManagerDao;
import com.ezendai.credit2.master.model.SysEnumerateManager;
import com.ezendai.credit2.master.service.SysEnumerateManagerService;
import com.ezendai.credit2.master.vo.SysEnumerateManagerVO;

/**
 * 
 * @Description: 枚举表服务接口
 * @author 张宜超
 * @date 2016年1月25日
 */
@Service
@Transactional
public class SysEnumerateManagerServiceImpl implements SysEnumerateManagerService {

	@Autowired
	private SysEnumerateManagerDao SysEnumerateDao ;

	/**
	 * 获取枚举列表
	 */
	@Override
	public Pager getSysEnumerateList(SysEnumerateManagerVO vo) {
		
		return SysEnumerateDao.getSysEnumerateList(vo);
	}

	/**
	 * 获取单个枚举信息
	 */
	@Override
	public SysEnumerateManager getSysEnumerate(Long id) {
		
		return SysEnumerateDao.getSysEnumerate(id);
	}

	/**
	 * 添加枚举
	 */
	@Override
	public void addSysEnumerate(SysEnumerateManagerVO sysEnumerate) {
		if(sysEnumerate.getEnumversion() == null){
			sysEnumerate.setEnumversion(1);
		}
		SysEnumerateDao.addSysEnumerate(sysEnumerate);
		
	}

	/**
	 * 更新枚举信息
	 */
	@Override
	public int updateSysEnumerate(SysEnumerateManagerVO vo) {
		
		return SysEnumerateDao.updateSysEnumerate(vo);
	}

	/**
	 * 多条件查询枚举信息
	 */
	@Override
	public List<SysEnumerateManager> getSysEnumerateByConditions(SysEnumerateManagerVO vo) {
		
		return SysEnumerateDao.getSysEnumerateByConditions(vo);
	}

	/**
	 * 删除枚举信息
	 */
	@Override
	public int deleteSysEnumerate(Long id) {
		
		return SysEnumerateDao.deleteSysEnumerate(id);
	}
	
	
	
}
