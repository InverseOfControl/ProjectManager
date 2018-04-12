package com.ezendai.credit2.report.dao.impl;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.report.dao.SmallrpDao;
import com.ezendai.credit2.report.model.Smallrp;

@Repository
public class SmallrpDaoImpl extends BaseDaoImpl<Smallrp> implements SmallrpDao {
	public Smallrp getSmallrpByContractNo(Map<String,Object> map) {
		return  (Smallrp) getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSmallrpByContractNo", map);
	}

    @Override
    public Smallrp queryCarCreditByContractNo(Map<String,Object> map) {
        return (Smallrp)getSqlSession().selectOne(getIbatisMapperNameSpace()+".queryCarCreditByContractNo",map);
    }
    
    @Override
    public Smallrp queryCarCreditByExtensionId(Map<String,Object> map) {
    	return (Smallrp)getSqlSession().selectOne(getIbatisMapperNameSpace()+".queryCarCreditByExtensionId",map);
    }
}
