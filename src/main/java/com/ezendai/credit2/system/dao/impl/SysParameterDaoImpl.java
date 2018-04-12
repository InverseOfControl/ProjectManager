/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2012 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.SysParameterDao;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.vo.SysParameterVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterDaoImpl.java, v 0.1 2014-8-19 下午02:46:51 liyuepeng Exp $
 */
@Repository
public class SysParameterDaoImpl extends BaseDaoImpl<SysParameter> implements SysParameterDao {

	@Override
	public void updateByCode(SysParameterVO sysParameterVo) {
		this.getSqlSession().update(getIbatisMapperNameSpace()+".updateByCode",sysParameterVo);
	}

}

