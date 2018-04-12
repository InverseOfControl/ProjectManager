package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.vo.VehicleVO;

/**
 * <pre>
 * vo和model转换
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: VehicleAssembler.java, v 0.1 2014-6-24 下午3:49:38 fangqingyuan Exp $
 */
public class VehicleAssembler {

	/**
	 * <pre>
	 * vo对象转换成model对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static Vehicle transferVO2Model(VehicleVO vehicleVO) {
		if (vehicleVO == null) {
			return null;
		}
		
		Vehicle vehicle = new Vehicle();
		vehicle.setId(vehicleVO.getId());
		vehicle.setPersonId(vehicleVO.getPersonId());
		vehicle.setBrand(vehicleVO.getBrand());
		vehicle.setModel(vehicleVO.getModel());
		vehicle.setCoty(vehicleVO.getCoty());
		vehicle.setMileage(vehicleVO.getMileage());
		vehicle.setRemark(vehicleVO.getRemark());
		vehicle.setFrameNumber(vehicleVO.getFrameNumber());
		vehicle.setPlateNumber(vehicleVO.getPlateNumber());
		vehicle.setCreator(vehicleVO.getCreator());
		vehicle.setCreatorId(vehicleVO.getCreatorId());
		vehicle.setCreatedTime(vehicleVO.getCreatedTime());
		vehicle.setModifier(vehicleVO.getModifier());
		vehicle.setModifierId(vehicleVO.getModifierId());
		vehicle.setModifiedTime(vehicleVO.getModifiedTime());
		vehicle.setVersion(vehicleVO.getVersion());
		vehicle.setLoanId(vehicleVO.getLoanId());
		return vehicle;
	}

	/**
	 * <pre>
	 * Model对象转换成Vo对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static VehicleVO transferModel2VO(Vehicle vehicle) {
		if (vehicle == null) {
			return null;
		}

		VehicleVO vehicleVo = new VehicleVO();
		vehicleVo.setId(vehicle.getId());
		vehicleVo.setPersonId(vehicle.getPersonId());
		vehicleVo.setBrand(vehicle.getBrand());
		vehicleVo.setModel(vehicle.getModel());
		vehicleVo.setCoty(vehicle.getCoty());
		vehicleVo.setMileage(vehicle.getMileage());
		vehicleVo.setRemark(vehicle.getRemark());
		vehicleVo.setFrameNumber(vehicle.getFrameNumber());
		vehicleVo.setPlateNumber(vehicle.getPlateNumber());
		vehicleVo.setCreator(vehicle.getCreator());
		vehicleVo.setCreatorId(vehicle.getCreatorId());
		vehicleVo.setCreatedTime(vehicle.getCreatedTime());
		vehicleVo.setModifier(vehicle.getModifier());
		vehicleVo.setModifierId(vehicle.getModifierId());
		vehicleVo.setModifiedTime(vehicle.getModifiedTime());
		vehicleVo.setVersion(vehicle.getVersion());
		vehicleVo.setLoanId(vehicle.getLoanId());
		return vehicleVo;
	}

}
