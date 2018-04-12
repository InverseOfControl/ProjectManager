package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.audit.model.BusinessLogView;
import com.ezendai.credit2.framework.util.Pager;

public interface BusinessLogService {
    /**
     * 已经在接口中 businessLog.setOperator 
     * 已经在接口中 businessLog.setCreateDate
     * @param businessLog
     * @return
     * @see com.ezendai.credit2.apply.service.BusinessLogService#insert(com.ezendai.credit2.apply.model.BusinessLog)
     */
    BusinessLog insert(BusinessLog businessLog);

    Pager findWithPg(BusinessLogVO businessLogVO);
    
    List<BusinessLogView> findWihtPgService(BusinessLogVO businessLogVO);
    
    Integer getCountByVO(BusinessLogVO businessLogVO);
    
    List<BusinessLog> getLogByVO(BusinessLogVO businessLogVO);
}
