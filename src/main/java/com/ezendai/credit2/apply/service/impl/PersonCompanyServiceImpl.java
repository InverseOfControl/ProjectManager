package com.ezendai.credit2.apply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.CompanyDao;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.vo.CompanyVO;

/***
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: ApplyServiceImpl.java, v 0.1 2014年6月24日 上午9:04:27 HQ-AT6 Exp $
 */
@Service
public class PersonCompanyServiceImpl implements PersonCompanyService {
    
    
    @Autowired
    CompanyDao companyDao;
    /***
     * 根据法人代表查找公司信息
     * <pre>
     * 
     * </pre>
     *
     * @param legalRepresentative
     * @return
     */
    @Override
   public Company getCompanyById(Long id){
       return  companyDao.get(id);
   }
    /***
     * 
     * <pre>
     * 更新公司信息 
     * </pre>
     *
     * @param company
     * @return
     */
    @Override
    public int update(CompanyVO companyVo){
        return companyDao.update(companyVo);
    }
    /**
     * 插入一条公司信息 
     * <pre>
     * 
     * </pre>
     *
     * @param company
     * @return
     */
    @Override
    public Company insert(Company company){
        return companyDao.insert(company);
    }
    
    
}
