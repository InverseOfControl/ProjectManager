package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * Author: kimi
 * Date: 14-6-24
 * Time: 下午5:10
 */
public interface CompanyService {
    /**
     * 新增一个公司
     * @param vo
     */
    void addCompany(BaseAreaVO vo);

    /**
     *编辑公司
     * @param vo
     */
    void editCompany(BaseAreaVO vo);

    /**
     *删除公司
     * @param id
     */
    void deleteCompany(Long id);

    /**
     *
     * @param id
     * @return
     */
    BaseAreaVO loadBaseArea(Long id);
    
    /**
     * 查询所有公司
     * @param identifier
     * @return
     */
    List<BaseArea> loadAllBaseArea(String identifier);
}
