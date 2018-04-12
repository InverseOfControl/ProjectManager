package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.CompanyService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * Author: kimi
 * Date: 14-6-24
 * Time: 下午5:18
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private BaseAreaDao dao;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public void addCompany(BaseAreaVO vo) {
        vo.setIdentifier(BizConstants.CREDIT2_COMPANY);
        dao.insert(assembleVo(vo));
    }

    private BaseArea assembleVo(BaseAreaVO vo){
        BaseArea area=new BaseArea();
        area.setName(vo.getName());
        area.setWorkPlaceInfoId(vo.getWorkPlaceInfoId());
        String current=dao.selectMaxCode(vo);
        int toInt=Integer.parseInt(current);
        toInt++;
        if(current.startsWith("0")){
            current="0".concat(String.valueOf(toInt));
        }else{
            current=String.valueOf(toInt);
        }
        area.setCode(current);
        area.setFullName(vo.getName());
        area.setIdentifier(vo.getIdentifier());
        area.setVersion(0L);
        area.setDeleted(0);
        return area;
    }

    @Override
    public void editCompany(BaseAreaVO vo) {
        BaseArea area=dao.get(vo.getId());
        if(area.getName().equals(vo.getName())){
            //无任何修改
          throw new BusinessException();
        }
        vo.setIdentifier(BizConstants.CREDIT2_COMPANY);
        int exists=dao.existsCompany(vo);
        if(exists<1){
            vo.setRemark("name被修改!oldValue:"+area.getName());
        }
        dao.update(vo);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DOT_MODIFICATION.getValue());
		sysLog.setRemark( "原网点全称:" + area.getFullName() + "——新网点全称：" + vo.getFullName());
		sysLogService.insert(sysLog);
    }

    @Override
    public void deleteCompany(Long id) {
        dao.deleteById(id);
    }

    @Override
    public BaseAreaVO loadBaseArea(Long id) {
        BaseAreaVO vo=new BaseAreaVO();
        BaseArea area=dao.get(id);
        vo.setIdentifier(area.getIdentifier());
        //TO-DO
//        List<String> areaList=dao.queryAreaOfCompany(vo);
//        vo.setAreaList(areaList);
        return vo;
    }
    
    @Override
    public List<BaseArea> loadAllBaseArea(String identifier) {
        return dao.queryAllBaseArea(identifier);
    }
}
