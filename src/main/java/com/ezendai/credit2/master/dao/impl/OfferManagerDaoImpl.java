package com.ezendai.credit2.master.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.OfferManagerDao;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.vo.OfferManagerVO;

/**
 * 
 * @Description: 报盘管理信息
 * @author 徐安龙
 * @date 2016年7月28日
 */
@Repository
public class OfferManagerDaoImpl extends BaseDaoImpl<OfferManager> implements OfferManagerDao {

	/**
	 * 获取列表
	 */
	@Override
	public Pager getOfferList(OfferManagerVO vo) {
		Object offerCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			offerCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getOfferCount", vo);
			if(null != offerCount && offerCount != ""){
				count = Integer.parseInt(offerCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<OfferManager> offerList = null;
		
			offerList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getOfferList", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != offerList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(offerList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return page;
	}

	@Override
	public OfferManager insert(OfferManager object) {
		return super.insert(object);
	}


	@Override
	public OfferManager get(Long id) {
		return super.get(id);
	}
	

	@Override
	public int updateOffer(OfferManagerVO vo) {
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateOffer", vo);
	}

	@Override
	public int deleteOffer(Long id) {
		int i = 0;
		try{
			i = getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteOffer" , id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<OfferManager> getOfferListByStatus() {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".getOfferListByStatus");
	}
}
