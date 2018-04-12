package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.dao.SysSerialNumDao;
import com.ezendai.credit2.system.model.SysSerialNum;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: SysSerialNumDaoImpl.java, v 0.1 2014-7-5 下午3:14:13 fangqingyuan Exp $
 */
@Repository
public class SysSerialNumDaoImpl extends BaseDaoImpl<SysSerialNum> implements SysSerialNumDao {
	private static final String UPDATE = ".update";

	@Override
	public int update(BaseVO vo) {
		UserSession user = ApplicationContext.getUser();
		if (user != null) {
			vo.setModifierId(user.getId());
			vo.setModifier(user.getName());
			vo.setModifiedTime(DateUtil.getTodayHHmmss());
		} else {
			vo.setModifierId(Constants.SYSTME_ID);
			vo.setModifier(Constants.SYSTEM_NAME);
			vo.setModifiedTime(DateUtil.getTodayHHmmss());
		}
		if (vo.getVersion() != null) {
			vo.setVersion(vo.getVersion() + 1);
		}
		int affectNum = 0;
		affectNum = getSqlSession().update(getIbatisMapperNameSpace() + UPDATE, vo);
		return affectNum;
	}
}
