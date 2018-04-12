package com.ezendai.credit2.system.service.impl;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.framework.util.SystemUtil;
import com.ezendai.credit2.system.assembler.SysParameterAssembler;
import com.ezendai.credit2.system.dao.SysParameterDao;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.vo.SysParameterVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterServiceImpl.java, v 0.1 2014-8-19 下午03:10:07 liyuepeng Exp $
 */
@Service
public class SysParameterServiceImpl implements SysParameterService {

	@Autowired
	private SysParameterDao sysParameterDao;

	@Autowired
	private CacheService cacheService;

	public static final Log log = LogFactory.getLog(SysParameterServiceImpl.class);

	/**memcached key 加上一些开始字符串，防止key冲突*/
	private static final String PARAMETER_START = "CREDIT2_SYSPARA_";

	@Override
	@Transactional
	public SysParameter insertSysParameter(SysParameter sysParameter) {
		return sysParameterDao.insert(sysParameter);
	}

	@Override
	@Transactional
	public void deleteSysParameterById(Long id) {
		sysParameterDao.deleteById(id);
	}

	@Override
	@Transactional
	public void updateSysParameter(SysParameterVO sysParameterVo) {
		sysParameterDao.update(sysParameterVo);
	}

	@Override
	public List<SysParameter> findList(SysParameterVO sysParameterVo) {
		return sysParameterDao.findListByVo(sysParameterVo);
	}
	@Override
	@Transactional
	public void deleteSysParameterByIdList(SysParameterVO sysParameterVo) {
		sysParameterDao.deleteByIdList(sysParameterVo);
	}

	@Override
	public SysParameter get(Long id) {
		return sysParameterDao.get(id);
	}

	/**
	 *  先从缓存中，找不到去数据库中查找
	 * @param code
	 * @return
	 * @see com.ezendai.credit2.system.service.SysParameterService#getByCode(java.lang.String)
	 */
	@Override
	public SysParameter getByCode(String code) {
		// 由于系统配送表里存的都是大写，所以先把code转为大写，以便调用时可以不关心大小写
		if (code != null) {
			code = code.toUpperCase();
		}
		//异常处理
		Object obj = null;
		try {
			obj = cacheService.getDataFromCache(PARAMETER_START + code);
		} catch (Exception e) {
			log.error("memcached query error!", e);
			//如果去缓存取有异常时，从数据库中取一次，并且放到memcached中
			SysParameter para = getByCodeFromDBAndSet2Cache(code);
			if (para != null) {
				return para;
			}
		}
		if (obj != null) {
			return (SysParameter) obj;
		}
		return getByCodeFromDBAndSet2Cache(code);
	}

	private SysParameter getByCodeFromDBAndSet2Cache(String code){
		SysParameterVO SysParameterVo = new SysParameterVO();
		SysParameterVo.setCode(code);
		List<SysParameter> sysParameterList = findList(SysParameterVo);
		SysParameter para = CollectionUtil.isNotEmpty(sysParameterList) ? sysParameterList.get(0) : null;
		try {
			cacheService.data2Cache(PARAMETER_START + code, para);
		} catch (Exception e) {
			log.error("memcached set error!", e);
		}
		return para;
	}


	@Override
	public void add(SysParameterVO sysParameterVo) {
		SysParameter sysParameter = new SysParameter();
		SysParameterAssembler.setModelProperty(sysParameterVo, sysParameter);
		insertSysParameter(sysParameter);
		try {
			cacheService.data2Cache(PARAMETER_START + sysParameterVo.getCode(), sysParameter);
		} catch (Exception e) {
			log.error("memcached set error!", e);
			
		}
	}

	@Override
	public void updateByCodeAndRefreshCache(SysParameterVO sysParameterVo) {
		sysParameterDao.updateByCode(sysParameterVo);
		
		getByCodeFromDBAndSet2Cache(sysParameterVo.getCode());
		
	}

	@Override
	public void modify(SysParameterVO sysParameterVo) {
		sysParameterDao.update(sysParameterVo);
		getByCodeFromDBAndSet2Cache(sysParameterVo.getCode());
	}

	@Override
	public boolean ipValidationCheck(String code, String ipAddr) throws UnknownHostException{
		log.info("平台服务器IP地址 : " + ipAddr+", code : " + code);
		SysParameter sysParameter = getByCode(code);
		if (sysParameter == null || StringUtil.isEmpty(sysParameter.getParameterValue())) {
			log.info("没有配置平台服务器IP地址");
			return false;
		}
		// 获取IP列表,并存储到一个String 数组中
		String[] ipArray = sysParameter.getParameterValue().split(";");
		// 如果IP列表中不包含本机IP
		if (!SystemUtil.containsLocalIP(ipArray, ipAddr)) {
			log.info("平台请求的IP地址不在配置列表中 : " + ipAddr);
			return false;
		}
		return true;
	}
	
	/**
	 *  实时查询配置表
	 * @param code
	 * @return
	 */
	@Override
	public SysParameter getByCodeNoCache(String code) {
		// 由于系统配送表里存的都是大写，所以先把code转为大写，以便调用时可以不关心大小写
		if (code != null) {
			code = code.toUpperCase();
		}
		SysParameterVO sysParameterVo = new SysParameterVO();
		sysParameterVo.setCode(code);
		List<SysParameter> sysParameterList = findList(sysParameterVo);
		SysParameter para = CollectionUtil.isNotEmpty(sysParameterList) ? sysParameterList.get(0) : null;
		return para;
	}
}
