package com.ezendai.credit2.master.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * <pre>
 * 分区管理
 * </pre>
 *
 * @author majl
 * @version $Id: AreaServiceImpl.java, v 0.1 2014-6-23 下午2:02:59 majl Exp $
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private BaseAreaDao dao;

	@Autowired
	private SysLogService sysLogService;
	/**
	 * 新增分区
	 * @param vo
	 * @see com.ezendai.credit2.master.service.AreaService#addArea(com.ezendai.credit2.master.vo.BaseAreaVO)
	 */
	@Override
	public void addArea(BaseAreaVO vo) {
		int exist = dao.existsArea(vo);
		if (exist > 0) {
			throw new BusinessException();
		
		}
		BaseArea area = assembleVo(vo);
		dao.insert(area);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.ADD_NETWORK.getValue());
		sysLog.setRemark( "网点全称:" +area.getFullName());
		sysLogService.insert(sysLog);
	}

	/**
	 * 修改分区信息
	 * @param vo
	 * @see com.ezendai.credit2.master.service.AreaService#editArea(com.ezendai.credit2.master.vo.BaseAreaVO)
	 */
	@Override
	public void editArea(BaseAreaVO vo) {
		BaseArea area = dao.get(vo.getId());
		if (area.getName().equals(vo.getName())) {
			//无任何修改
			throw new BusinessException();
		}
		vo.setIdentifier(BizConstants.CREDIT2_AREA);
		vo.setCompanyId(area.getCompanyId());
		int exists = dao.existsArea(vo);
		if (exists < 1) {
			BaseArea company = dao.get(vo.getCompanyId());
			vo.setFullName(company.getName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
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

	/**
	 * 删除分区
	 * @param id
	 * @see com.ezendai.credit2.master.service.AreaService#deleteArea(java.lang.Long)
	 */
	@Override
	public void deleteArea(Long id) {
		dao.deleteById(id);
	}

	/**
	 * <pre>
	 * 分页查询全部区域
	 * </pre>
	 *
	 * @param identifier
	 * @return
	 */
	@Override
	public List<BaseArea> loadAllBaseArea(String identifier) {
		return dao.queryAllBaseArea(identifier);
	}

	/**
	 * <pre>
	 * 单条查询
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@Override
	public BaseArea loadOneBaseArea(Long id) {
		return dao.get(id);
	}

	/**
	 * <pre>
	 * 画面参数转换对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	private BaseArea assembleVo(BaseAreaVO vo) {
		BaseArea area = dao.maxCodeAreaItem(vo.getCompanyId());
		if (area == null) {
			area = new BaseArea();
			area.setCode(dao.get(Long.valueOf(vo.getCompanyId())).getCode().concat("01"));
			area.setFullName(dao.get(Long.valueOf(vo.getCompanyId())).getName().concat(BizConstants.SPLIT_FLAG)
				.concat(vo.getName()));
		} else {
			String currentMaxCode = area.getCode();
			long toLong = Long.parseLong(currentMaxCode);
			toLong++;
			if (currentMaxCode.startsWith("0")) {
				currentMaxCode = "0".concat(String.valueOf(toLong));
			} else {
				currentMaxCode = String.valueOf(toLong);
			}
			area.setCode(currentMaxCode);
			area.setFullName(dao.get(Long.valueOf(vo.getCompanyId())).getName().concat(BizConstants.SPLIT_FLAG)
					.concat(vo.getName()));
		}
		area.setName(vo.getName());
		area.setIdentifier(BizConstants.CREDIT2_AREA);
		area.setCompanyId(vo.getCompanyId());
		area.setWorkPlaceInfoId(vo.getWorkPlaceInfoId());
		area.setVersion(0L);
		area.setDeleted(0);
		return area;
	}

	@Override
	public BaseArea getBaseAreaByVO(BaseAreaVO baseAreaVO) {
		return dao.get(baseAreaVO);
	}

	@Override
	public List<Long> getDeptsByUserIdAndDeptsTypes(BaseAreaVO baseAreaVO) {
		List<BaseArea> list = dao.getDeptsByUserIdAndDeptsTypes(baseAreaVO);
		List<Long> listLong = new ArrayList<Long>();
		if(list !=null&& list.size()>0){
			for(BaseArea baseArea : list){
				listLong.add(baseArea.getId());
			}
		}
		return listLong;
	}
}
