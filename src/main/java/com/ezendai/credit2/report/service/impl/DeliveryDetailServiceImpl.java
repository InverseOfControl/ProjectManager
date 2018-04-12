package com.ezendai.credit2.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.report.dao.DeliveryDetailDao;
import com.ezendai.credit2.report.model.DeliveryDetail;
import com.ezendai.credit2.report.service.DeliveryDetailService;
import com.ezendai.credit2.report.vo.DeliveryDetailVO;

/**   
*    
* 项目名称：credit2-report   
* 类名称：DeliveryDetailServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年11月6日 下午2:52:41   
* 修改人：liboyan   
* 修改时间：2015年11月6日 下午2:52:41   
* 修改备注：   
* @version    
*    
*/
@Service
public class DeliveryDetailServiceImpl implements DeliveryDetailService {
	@Autowired
	private DeliveryDetailDao deliveryDetailDao;
	@Override
	public Pager findWithPG(DeliveryDetailVO vo) {
		return deliveryDetailDao.findWithPG(vo);
	}
	@Override
	public List<DeliveryDetail> findWith(DeliveryDetailVO vo) {
		return deliveryDetailDao.findWith(vo);
	}
	@Override
	public Integer getCountByDeliveryDetailVO(DeliveryDetailVO deliveryDetailVO) {
		return deliveryDetailDao.getCountByDeliveryDetailVO(deliveryDetailVO);
	}
	@Override
	public List<DeliveryDetail> makeSummaryList(DeliveryDetailVO deliveryDetailVO) {
		return deliveryDetailDao.makeSummaryList(deliveryDetailVO);
	}
	@Override
	public List<BaseArea> getStudentLoanSalesDepts(Map<String, String> map) {
		return deliveryDetailDao.getStudentLoanSalesDepts(map);
	}
	@Override
	public List<BaseArea> getCarLoanSalesDepts(Map<String, String> map) {
		return deliveryDetailDao.getCarLoanSalesDepts(map);
	}
	@Override
	public Integer countmakeSummaryList(DeliveryDetailVO deliveryDetailVO) {
		return deliveryDetailDao.countmakeSummaryList(deliveryDetailVO);
	}
}
