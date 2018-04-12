package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.framework.dao.BaseDao;



public interface CompanyDao  extends BaseDao<Company>{
    /**
     * 根据法人代表查询车辆信息
     * <pre>
     * 
     * </pre>
     *
     * @param legalRepresentative
     * @return
     */
    public List<Company> findCompanyByLegalRepresentative(Long legalRepresentative);

}