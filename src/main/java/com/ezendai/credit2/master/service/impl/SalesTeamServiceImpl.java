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
import com.ezendai.credit2.master.service.SalesTeamService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * <pre>
 * 销售团队管理
 * </pre>
 *
 * @author majl
 * @version $Id: SalesTeamServiceImpl.java, v 0.1 2014-6-24 下午4:20:06 majl Exp $
 */
@Service
public class SalesTeamServiceImpl implements SalesTeamService {

	@Autowired
	private BaseAreaDao dao;
	@Autowired
	private  SysLogService sysLogService;
	/**
	 * <pre>
	 * 新增销售团队
	 * </pre>
	 *
	 * @param vo
	 */
    public void addSalesTeam(BaseAreaVO vo) {
        int exist=dao.existsSalesTeam(vo);
        if(exist>0){
        	throw new BusinessException();
        }

        BaseArea area=assembleVo(vo);
        dao.insert(area);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.ADD_NETWORK.getValue());
		sysLog.setRemark("网点名称:" + area.getFullName());
		sysLogService.insert(sysLog);
    }

    /**
     * 修改销售团队信息
     * @param vo
     * @see com.ezendai.credit2.master.service.SalesTeamService#editSalesTeam(com.ezendai.credit2.master.vo.BaseAreaVO)
     */
    public void editSalesTeam(BaseAreaVO vo) {
        BaseArea area=dao.get(vo.getId());
        if(isEdited(area,vo)){
        	throw new BusinessException();
        }
        vo.setIdentifier(BizConstants.CREDIT2_SALESTEAM);
        vo.setSalesDeptId(vo.getSalesDeptId());
        int exists=dao.existsSalesTeam(vo);
        if(exists<1){
            BaseArea salesDepartment=dao.get(NumberUtils.toLong(vo.getSalesDeptId()));
            vo.setFullName(salesDepartment.getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
            vo.setRemark("name被修改!oldValue:"+area.getName());
        }
        dao.update(vo);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DOT_MODIFICATION.getValue());
		sysLog.setRemark( "原网点全称:" +area.getFullName() + "——新网点全称：" + vo.getFullName());
		sysLogService.insert(sysLog);
    }

    /**
     * 删除销售团队
     * @param id
     * @see com.ezendai.credit2.master.service.SalesTeamService#deleteSalesTeam(java.lang.Long)
     */
    public void deleteSalesTeam(Long id) {
        dao.deleteById(id);
    }

    /**
     * <pre>
     * 分页查询全部销售团队
     * </pre>
     *
     * @param identifier
     * @return
     */
    public List<BaseArea> loadAllBaseArea(String identifier) {
        return dao.queryAllBaseArea(identifier);
    }
    
    /**
     * <pre>
     * 单条查询 by Id
     * </pre>
     *
     * @param id
     * @return
     */
    public BaseArea loadOneBaseAreaById(Long id) {
        return dao.get(id);
    }
    
    /**
     * <pre>
     * 页面传入参数进行对象转换
     * </pre>
     * @param vo
     */
    private BaseArea assembleVo(BaseAreaVO vo) {
        BaseArea area=dao.maxCodeSalesTeamItem(vo.getSalesDeptId());
        if(area==null){
            area=new BaseArea();
            area.setCode(dao.get(Long.valueOf(vo.getSalesDeptId())).getCode().concat("00001"));
            area.setFullName(dao.get(Long.valueOf(vo.getSalesDeptId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
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
            area.setFullName(dao.get(Long.valueOf(vo.getSalesDeptId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
        }
        area.setName(vo.getName());
        area.setIdentifier(BizConstants.CREDIT2_SALESTEAM);
        area.setSalesDeptId(vo.getSalesDeptId());
        area.setWorkPlaceInfoId(vo.getWorkPlaceInfoId());
        area.setVersion(0L);
        area.setDeleted(0);
    	area.setDeptType(vo.getDeptType());
        return area;
    }

    /**
     * <pre>
     * 判断销售团队是否存在
     * </pre>
     *
     * @param area
     * @param vo
     * @return
     */
    private boolean isEdited(BaseArea area , BaseAreaVO vo){
        return (area.getName().equals(vo.getName()) && area.getDeptType() == vo.getDeptType());
    }
}
