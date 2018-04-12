package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface BusinessLogDao extends BaseDao<BusinessLog> {
	
	Integer getCountByVO(BusinessLogVO businessLogVO);
	
	List<BusinessLog> getLogByVO(BusinessLogVO businessLogVO);

}
