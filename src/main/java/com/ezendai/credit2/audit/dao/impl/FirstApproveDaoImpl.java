package com.ezendai.credit2.audit.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.FirstApproveDao;
import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

/***
 * <pre>
 * 批复
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
@Repository
public class FirstApproveDaoImpl extends BaseDaoImpl<FirstApprove> implements FirstApproveDao {

	@SuppressWarnings("rawtypes")
	@Override
	public Pager findFirstApproveWithPG(FirstApproveVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".firsApproveCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findFirstApproveWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

	@Override
	public String getAcceptAudit(long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getAcceptAudit", id);
	}

	@Override
	public void updateAcceptAudit(Map map) {
		// TODO Auto-generated method stub
		   getSqlSession().update(getIbatisMapperNameSpace() + ".updateAcceptAudit", map);	
	}

	@Override
	public int selectSysUserCount(String code) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectSysUserCount", code);
	}
 
	 
}