package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.vo.BankManagerVO;

/**
 * 
 * @Description: ibatis中对接xml的dao与mybatis中的mapper同一作用
 * @author 张宜超
 * @date 2016年1月25日
 */
public interface BankManagerDao extends BaseDao<BankManager>{

	/**
	 * 
	 * @Description: 获取银行列表
	 * @param @param vo
	 * @param @return   
	 * @return List<Bank>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	public Pager getBankList(BankManagerVO vo);
	
	/**
	 * 
	 * @Description: 根据银行id获取银行信息
	 * @param id
	 * @param   
	 * @return Bank  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	BankManager getBank(Long id);
	
	/**
	 * 
	 * @Description: 多条件查询一条银行信息
	 * @param @param vo
	 * @param @return   
	 * @return Bank  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	List<BankManager> getBankByConditions(BankManagerVO vo);
	
	/**
	 * 
	 * @Description: 添加银行信息
	 * @param @param bank   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	void addBank(BankManagerVO bank);
	
	/**
	 * 
	 * @Description: 修改银行信息
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
	 * @Description: 删除银行
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	int deleteBank(Long id);
}

