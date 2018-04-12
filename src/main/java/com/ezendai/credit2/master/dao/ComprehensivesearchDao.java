package com.ezendai.credit2.master.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.ComprehensiveSearch;
import com.ezendai.credit2.master.vo.ComprehensiveSearchVO;

 
public interface ComprehensivesearchDao extends BaseDao<ComprehensiveSearch> {

	public Pager getComprehensiveSearchList(ComprehensiveSearchVO csVo) ;

}
