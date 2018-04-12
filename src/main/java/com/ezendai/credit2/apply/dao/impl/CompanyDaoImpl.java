package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.CompanyDao;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {

    @Override
    public List<Company> findCompanyByLegalRepresentative(Long legalRepresentative) {
        return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".findCompanyByLegalRepresentative", legalRepresentative);
    }

}
