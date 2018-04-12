package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.vo.OfferManagerVO;

public interface OfferManagerServcie {
	
	/**
	 * @Description: 获取产品详情列表
	 * @param @param OfferManagerVO
	 * @return Pager  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月1日
	 */
	Pager getOfferManagerList(OfferManagerVO vo);
	
	/**
	 * @Description: 根据id获取数据详情
	 * @param @param id
	 * @return OfferManager  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月1日
	 */
	OfferManager getOfferManagerDetail(Long id);
	
	
	/**
	 * @Description: 新增报盘管理数据
	 * @param @param ProductDetailManager
	 * @return ProductDetailManager  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月2日
	 */
	OfferManager addOfferManager(OfferManager om);
	
	/**
	 * 
	 * @Description: 更新报盘管理数据
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月2日
	 */
	int updateOfferManager(OfferManagerVO vo);
	
	String addJob(OfferManager om,String add) throws Exception;
	
	String updateJob(OfferManagerVO vo) throws Exception;
	
	String removeJob(OfferManager om);
	
	List<OfferManager> getOfferListByStatus();
}
