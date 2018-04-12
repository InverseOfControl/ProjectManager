package com.ezendai.credit2.sign.lcb.dao;

import java.util.Map;

import com.ezendai.credit2.apply.model.LoanManage;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface JudgePushStandardDao extends BaseDao<LoanManage>{

	Map<String,Object> findLonaById(Long id);
}
