package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.vo.BankManagerVO;

/**
 * 
 * @Description: 银行service
 * @author 张宜超
 * @date 2016年1月25日
 */
public interface BankManagerService {

	/**
	 * 
	 * @Description: 根据条件获取银行列表
	 * @param @param vo
	 * @param @return   
	 * @return List<Bank>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	Pager getBankList(BankManagerVO vo);
	
	/**
	 * 
	 * @Description: 获取单个银行信息
	 * @param @param id
	 * @param @return   
	 * @return Bank  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	BankManager getBank(Long id);
	
	/**
	 * 
	 * @Description: 添加一条新银行信息
	 * @param @param bank   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	void addBank(BankManagerVO bank);
	
	/**
	 * 
	 * @Description: 修改某银行信息
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	int updateBank(BankManagerVO vo);
	
	/**
	 * 
	 * @Description: 多条件查询银行信息
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	List<BankManager> getBankByConditions(BankManagerVO vo);
	
	/**
	 * 
	 * @Description: 删除银行信息
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	int deleteBank(Long id);
}
