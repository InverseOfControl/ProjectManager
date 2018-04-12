package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.CreditHistoryDao;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class CreditHistoryDaoImpl extends BaseDaoImpl<CreditHistory> implements CreditHistoryDao{
    
   
    /***
     * 根据借款人ID查询历史记录
     * <pre>
     * 
     * </pre>
     *
     * @author HQ-AT6
     * @version $Id: ContacterDaoImpl.java, v 0.1 2014年6月25日 上午11:09:26 HQ-AT6 Exp $
     */
    @Override
    public List<CreditHistory> getCreditHistoryByPersonId(Long personId){
        return getSqlSession().selectList(getIbatisMapperNameSpace()+".getCreditHistoryByPersonId", personId);
    }

	@Override
	public int updateCreditHistoryByPersonId(CreditHistory creditHistory) {
		return getSqlSession().update(getIbatisMapperNameSpace()+".updateCreditHistoryByPersonId", creditHistory);
	}
    
    
}
