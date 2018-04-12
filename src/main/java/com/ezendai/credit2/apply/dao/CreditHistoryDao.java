package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface CreditHistoryDao  extends BaseDao<CreditHistory>{
    
    /***
     * 根据借款人ID查询历史记录
     * <pre>
     * 
     * </pre>
     *
     * @author HQ-AT6
     * @version $Id: ContacterDaoImpl.java, v 0.1 2014年6月25日 上午11:09:26 HQ-AT6 Exp $
     */
    List<CreditHistory> getCreditHistoryByPersonId(Long personId);
    
    int updateCreditHistoryByPersonId(CreditHistory creditHistory);
   

}
