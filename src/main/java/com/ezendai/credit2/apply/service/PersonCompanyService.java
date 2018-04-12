package com.ezendai.credit2.apply.service;

import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.vo.CompanyVO;


/****
 * 公司信息
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: CompanyService.java, v 0.1 2014年6月25日 下午3:47:53 HQ-AT6 Exp $
 */
public interface PersonCompanyService {
    
   
   /***
    * 
    * <pre>
    * 插入一条公司信息
    * </pre>
    *
    * @param company
    * @return
    */
    public  Company insert(Company company);
    /**
     * 根据法人代表查找公司公司信息
     * <pre>
     * 
     * </pre>
     *
     * @param legalRepresentative
     * @return
     */
    public Company getCompanyById(Long id);
    
    int update(CompanyVO companyVo);
    
    
}
