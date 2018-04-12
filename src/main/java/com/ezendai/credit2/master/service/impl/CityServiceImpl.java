package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.CityService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * Author: kimi
 * Date: 14-6-25
 * Time: 下午6:21
 */
@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private BaseAreaDao dao;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public void addCity(BaseAreaVO vo) {
        int exist = dao.existsCity(vo);
        if (exist > 0) {
        	throw new BusinessException();
        }
        BaseArea area = assembleVo(vo);
        dao.insert(area);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.ADD_NETWORK.getValue());
		sysLog.setRemark("网点名称:" +area.getFullName());
		sysLogService.insert(sysLog);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void editCity(BaseAreaVO vo) {
        BaseArea area=dao.get(vo.getId());
        if(area.getName().equals(vo.getName())){
            //无任何修改
        	throw new BusinessException();
        }
        vo.setIdentifier(BizConstants.CREDIT2_CITY);
        vo.setAreaId(area.getAreaId());
        int exists=dao.existsCity(vo);
        if(exists<1){
            BaseArea district=dao.get(NumberUtils.toLong(vo.getAreaId()));
            vo.setFullName(district.getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
			vo.setRemark("name被修改!oldValue:" + area.getName());
        }
        dao.update(vo);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DOT_MODIFICATION.getValue());
		sysLog.setRemark( "原网点全称:" +area.getFullName() + "——新网点全称：" + vo.getFullName());
		sysLogService.insert(sysLog);
    }

    @Override
    public BaseArea loadCityById(Long id) {
        return dao.get(id);
    }

    @Override
    public BaseArea loadCityByName(String name) {
        return dao.querySingleCityByName(name);
    }
    
    @Override
    public List<BaseArea> loadAllBaseArea(String identifier) {
        return dao.queryAllBaseArea(identifier);
    }
    
    private BaseArea assembleVo(BaseAreaVO vo) {
        BaseArea area=dao.maxCodeCityItem(vo.getAreaId());
        if(area==null){
            area=new BaseArea();
            area.setCode(dao.get(Long.valueOf(vo.getAreaId())).getCode().concat("0001"));
            area.setFullName(dao.get(Long.valueOf(vo.getAreaId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
        }else{
            String currentMaxCode=area.getCode();
            long toLong=Long.parseLong(currentMaxCode);
            toLong++;
            if(currentMaxCode.startsWith("0")){
                currentMaxCode="0".concat(String.valueOf(toLong));
            }else{
                currentMaxCode=String.valueOf(toLong);
            }
            area.setCode(currentMaxCode);
            area.setFullName(dao.get(Long.valueOf(vo.getAreaId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
        }
        area.setName(vo.getName());
        area.setIdentifier(BizConstants.CREDIT2_CITY);
        area.setAreaId(vo.getAreaId());
        area.setWorkPlaceInfoId(vo.getWorkPlaceInfoId());
        area.setVersion(0L);
        area.setDeleted(0);
        return area;
    }
    @Override
    public List<BaseArea> findByUserId(BaseAreaVO baseAreaVo){
       return  dao.findBaseAreaById(baseAreaVo);
        
    }
}
