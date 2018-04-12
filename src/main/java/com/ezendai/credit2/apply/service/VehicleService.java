package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 车辆服务类
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: VehicleService.java, v 0.1 2014-6-24 下午2:12:39 fangqingyuan Exp $
 */
public interface VehicleService {

	/**
	 * <pre>
	 * 插入车辆信息
	 * </pre>
	 * @param vo
	 *
	 * @return
	 */
	Vehicle insert(VehicleVO vo);

	/**
	 * <pre>
	 * 修改车辆信息
	 * </pre>
	 * @param vo
	 *
	 * @return
	 */
	void update(VehicleVO vo);

	/**
	 * <pre>
	 * 根据用户id查询车辆信息
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */
	List<Vehicle> getVehicleListByPersonId(Long personId, Long loanId);
	
	void delete (Long id);
	
	void deleteByIdList(BaseVO vo);
	
	/**
	 * <pre>
	 * 分页查询
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	Pager findWithPg(VehicleVO vo);
	
	List<Vehicle> findListByVo(VehicleVO vo);

	Integer getIdNoTwo(String id);
}
