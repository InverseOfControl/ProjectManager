package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ComprehensivesearchDao;
import com.ezendai.credit2.master.model.ComprehensiveSearch;
import com.ezendai.credit2.master.vo.ComprehensiveSearchVO;

@Repository
public class ComprehensiveSearchDaoImpl extends BaseDaoImpl<ComprehensiveSearch> implements ComprehensivesearchDao {
	
	 
 
	@Override
	public Pager getComprehensiveSearchList(ComprehensiveSearchVO csVo) {
		Pager pager = csVo.getPager();
		/** 查询总行数 **/
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getComprehensiveSearchCount", csVo);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		//查询员工列表
		List<ComprehensiveSearch> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getComprehensiveSearchList",csVo);
		pager.setResultList(resultList);;
		return pager;
	}

	 
	 
}
