package com.ezendai.credit2.report.dao;
import java.util.Map;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.report.model.Smallrp;

public interface SmallrpDao  extends BaseDao<Smallrp> {
	public Smallrp getSmallrpByContractNo(Map<String,Object> map);

    Smallrp queryCarCreditByContractNo(Map<String,Object> map);
    
    Smallrp queryCarCreditByExtensionId(Map<String,Object> map);
}
