package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.vo.OfferManagerVO;

/**
 * 
 * @Description: ibatis中对接xml的dao与mybatis中的mapper同一作用
 * @author 徐安龙
 * @date 2016年7月28日
 */
public interface OfferManagerDao extends BaseDao<OfferManager>{

	/**
	 * 
	 * @Description: 获取报盘管理列表
	 * @param @param vo
	 * @param @return   
	 * @return List<OfferManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	public Pager getOfferList(OfferManagerVO vo);
	
	
	/**
	 * 
	 * @Description: 修改报盘管理信息
	 * @param  vo
	 * @return int  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年7月28日
	 */
	int updateOffer(OfferManagerVO vo);
	
	/**
	 * 
	 * @Description: 删除银行
	 * @param  id   
	 * @return int  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年7月28日
	 */
	int deleteOffer(Long id);
	
	List<OfferManager> getOfferListByStatus();
}

