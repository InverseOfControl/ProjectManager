package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.assembler.VehicleAssembler;
import com.ezendai.credit2.apply.dao.VehicleDao;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 车辆服务实现类
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: VehicleServiceImpl.java, v 0.1 2014-6-24 下午2:25:07 fangqingyuan Exp $
 */
@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleDao vehicleDao;

	@Override
	public Vehicle insert(VehicleVO vo) {
		Vehicle model = VehicleAssembler.transferVO2Model(vo);
		Vehicle result = vehicleDao.insert(model);
		return result;
	}

	@Override
	public void update(VehicleVO vo) {
		vehicleDao.update(vo);
	}

	@Override
	public List<Vehicle> getVehicleListByPersonId(Long personId,Long loanId) {
		VehicleVO vo = new VehicleVO();
		vo.setLoanId(loanId);
		vo.setPersonId(personId);
		List<Vehicle> result = vehicleDao.findListByVo(vo);
		return result;
	}

	@Override
	public Pager findWithPg(VehicleVO vo) {
		return vehicleDao.findWithPg(vo);
	}

	@Override
	public void delete(Long id) {
		vehicleDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(BaseVO vo) {
		vehicleDao.deleteByIdList(vo);
	}

	@Override
	public List<Vehicle> findListByVo(VehicleVO vo) {
		return vehicleDao.findListByVo(vo);
	}

	@Override
	public Integer getIdNoTwo(String id) {
		return vehicleDao.getIdNoTwo(id);
	}

}
