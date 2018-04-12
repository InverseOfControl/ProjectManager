package com.ezendai.credit2.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.report.dao.DeliveryDetailDao;
import com.ezendai.credit2.report.model.DeliveryDetail;
import com.ezendai.credit2.report.vo.DeliveryDetailVO;

/**   
*    
* 项目名称：credit2-report   
* 类名称：DeliveryDetailDaoImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年11月6日 下午2:54:00   
* 修改人：liboyan   
* 修改时间：2015年11月6日 下午2:54:00   
* 修改备注：   
* @version    
*    
*/
@Repository
public class DeliveryDetailDaoImpl extends BaseDaoImpl<DeliveryDetail> implements DeliveryDetailDao {

	/* (non-Javadoc)
	 * @see com.ezendai.credit2.report.dao.DeliveryDetailDao#findWithPG(com.ezendai.credit2.report.vo.DeliveryDetailVO)
	 */
	@Override
	public Pager findWithPG(DeliveryDetailVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List<DeliveryDetail> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}
	@Override
	public List<DeliveryDetail> findWith(DeliveryDetailVO vo) {
		List<DeliveryDetail> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWith", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rstList;
	}
	@Override
	public Integer getCountByDeliveryDetailVO(DeliveryDetailVO deliveryDetailVO) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", deliveryDetailVO);
	}
	@Override
	public List<DeliveryDetail> makeSummaryList(DeliveryDetailVO deliveryDetailVO) {
		List<DeliveryDetail> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".makeSummaryList", deliveryDetailVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		return 	rstList;
	}
	@Override
	public List<BaseArea> getStudentLoanSalesDepts(Map<String, String> map) {
		List<BaseArea> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getStudentLoanSalesDepts",map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		return 	rstList;
	}
	@Override
	public List<BaseArea> getCarLoanSalesDepts(Map<String, String> map) {
		List<BaseArea> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getCarLoanSalesDepts",map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		return 	rstList;
	}
	@Override
	public Integer countmakeSummaryList(DeliveryDetailVO deliveryDetailVO) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countmakeSummaryList", deliveryDetailVO);
	}
}
