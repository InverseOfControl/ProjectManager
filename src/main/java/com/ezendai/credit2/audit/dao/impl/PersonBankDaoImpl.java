package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.PersonBankDao;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

@Repository
public class PersonBankDaoImpl extends BaseDaoImpl< PersonBank> implements PersonBankDao {

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.audit.dao.PersonBankDao#findListByVoExtension(com.ezendai.credit2.audit.vo.PersonBankVO)
	 */
	@Override
	public List<PersonBank> findListByVoExtension(PersonBankVO vo) {
		List<PersonBank> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOExtension", vo);
		return rstList;
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.audit.dao.PersonBankDao#findWithPgExtension(com.ezendai.credit2.framework.vo.BaseVO)
	 */
	@Override
	public Pager findWithPgExtension(PersonBankVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countExtension", vo);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPGExtension", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.audit.dao.PersonBankDao#getExtension(java.lang.Long)
	 */
	@Override
	public PersonBank getExtension(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getExtension", id);
	}

	@Override
	public Pager findWithPG(PersonBankVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", vo);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

}
