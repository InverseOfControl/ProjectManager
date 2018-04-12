package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.dao.WorkPlaceInfoDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * <pre>
 * 网点管理
 * </pre>
 *
 * @author majl
 * @version $Id: SalesDepartmentServiceImpl.java, v 0.1 2014-6-25 下午4:22:15 majl
 *          Exp $
 */
@Service
public class SalesDepartmentServiceImpl implements SalesDepartmentService {

	@Autowired
	private BaseAreaDao dao;
	@Autowired
	private WorkPlaceInfoDao workPlaceInfoDao;
	@Autowired
	private SysLogService sysLogService;
	/**
	 * <pre>
	 * 新增网点
	 * </pre>
	 *
	 * @param vo
	 */
	public void addSalesDepartment(BaseAreaVO vo) {
		int exist = dao.existsSalesDepartment(vo);
		if (exist > 0) {
			throw new BusinessException();
		}

		vo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		BaseArea area = assembleVo(vo);
		dao.insert(area);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.ADD_NETWORK.getValue());
		sysLog.setRemark("网点名称:" + area.getFullName());
		sysLogService.insert(sysLog);
	}

	/**
	 * 修改网点信息
	 * 
	 * @param vo
	 * @see com.ezendai.credit2.master.service.SalesDepartmentService#editSalesDepartment(com.ezendai.credit2.master.vo.BaseAreaVO)
	 */
	public void editSalesDepartment(BaseAreaVO vo) {
		BaseArea area = dao.get(vo.getId());
		if (isEdited(area, vo)) {
			throw new BusinessException();
		}
		vo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		vo.setCityId(area.getCityId());
		int exists = dao.existsSalesDepartment(vo);
		if (exists < 1) {
			BaseArea city = dao.get(NumberUtils.toLong(vo.getCityId()));
			vo.setFullName(city.getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
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
	 * 删除网点
	 * 
	 * @param id
	 * @see com.ezendai.credit2.master.service.SalesDepartmentService#deleteSalesDepartment(java.lang.Long)
	 */
	public void deleteSalesDepartment(Long id) {
		dao.deleteById(id);
	}

	/**
	 *
	 * <pre>
	 * 分页查询全部网点
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
	 * 单条查询 by Name
	 * </pre>
	 *
	 * @param name
	 * @return
	 */
	public BaseArea loadOneBaseAreaByName(String name) {
		BaseAreaVO vo = new BaseAreaVO();
		vo.setName(name);
		return dao.get(vo);
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
		BaseArea area = dao.maxCodeSalesDepartmentItem(vo.getCityId());
			
		if (area == null) {
			area = new BaseArea();
			area.setCode(dao.get(Long.valueOf(vo.getCityId())).getCode().concat("00001"));
			area.setFullName(dao.get(Long.valueOf(vo.getCityId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
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
			area.setFullName(dao.get(Long.valueOf(vo.getCityId())).getFullName().concat(BizConstants.SPLIT_FLAG).concat(vo.getName()));
		}
		String zoneCode = workPlaceInfoDao.get(vo.getWorkPlaceInfoId()).getZoneCode();
		area.setDeptNo(zoneCode.concat(String.format("%3d", dao.countSalesDepartment(vo) + 1)));
		area.setName(vo.getName());
		area.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		area.setCityId(vo.getCityId());
		area.setWorkPlaceInfoId(vo.getWorkPlaceInfoId());
		area.setVersion(0L);
		area.setDeleted(0);
		area.setDeptType(vo.getDeptType());
		return area;
	}

	/**
	 * <pre>
	 * 判断是否被修改
	 * </pre>
	 *
	 * @param area
	 * @param vo
	 * @return
	 */
	private boolean isEdited(BaseArea area, BaseAreaVO vo) {
		return ((area.getName().equals(vo.getName()) && area.getWorkPlaceInfoId().equals(vo.getWorkPlaceInfoId())) && area.getDeptType() == vo.getDeptType());
	}

	public static void main(String... args) {
		String code = "010100010000100034";
		Long value = Long.parseLong(code);
		value++;
		if (code.startsWith("0")) {
			code = "0".concat(String.valueOf(value));
		} else {
			code = String.valueOf(value);
		}
		System.out.println(code);
		System.out.println(String.format("%03d", 123456));
	}
}
