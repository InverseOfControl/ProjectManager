package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * 城市管理Service
 * Author: kimi
 * Date: 14-6-25
 * Time: 下午6:14
 */
public interface CityService {
    /**
     * 新增城市
     * @param vo
     */
    void addCity(BaseAreaVO vo);

    /**
     * 删除城市
     * @param id
     */
    void delete(Long id);

    /**
     * 修改城市
     * @param vo
     * @return 修改是否成功
     */
    void editCity(BaseAreaVO vo);

    /**
     * 根据id 查询
     * @param id
     * @return BaseArea
     */
    BaseArea loadCityById(Long id);

    /**
     * 根据城市名查询
     * @param name
     * @return BaseArea
     */
    BaseArea loadCityByName(String name);
    
    /**
     * 查询所有城市
     * @param identifier
     * @return
     */
    List<BaseArea> loadAllBaseArea(String identifier);
    /***
     * 
     * <pre>
     * 根据用户ID查找所在的营业网点 
     * </pre>
     *
     * @return
     */
    List<BaseArea> findByUserId(BaseAreaVO baseAreaVo);
}

