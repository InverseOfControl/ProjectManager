package com.ezendai.credit2.system.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.vo.SysParameterVO;


/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterDao.java, v 0.1 2014-8-19 下午02:45:38 liyuepeng Exp $
 */
public interface SysParameterDao extends BaseDao<SysParameter>{
	
	void updateByCode(SysParameterVO sysParameterVo);
}

