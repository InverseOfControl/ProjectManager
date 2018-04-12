package com.ezendai.credit2.sign.lcb.dao;

import java.util.Map;

import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.sign.lcb.model.LcbBidPushModel;
import com.ezendai.credit2.sign.lcb.model.LcbModel;

public interface IContractGenerateDao extends BaseDao<Contract> {
	/**
	 * description:根据loanId获取用户信息
	 * autor:ym10159
	 * date:2018年1月4日 下午2:24:36
	 */
	LcbModel getPersonInfoByLoanId(String loanId);
	
	/**
	 * description:记录用户是否在捞财宝注册、认证、绑卡状态
	 * autor:ym10159
	 * date:2018年1月5日 下午2:27:49
	 * @param flag 节点标识 
	 */
	int updateLcbStatus(Map<String,Object> map);
	
	/**
	 * description:通过bankId获取bankCode
	 * autor:ym10159
	 * date:2018年1月9日 下午2:20:30
	 * @param id 银行ID
	 * @return map 银行信息
	 */
	Map<String,Object> getBankCodeById(String id);
	
	/**
	 * description:获取推标信息
	 * autor:ym10159
	 * date:2018年1月11日 下午3:41:38
	 * @param id 借款ID
	 */
	LcbBidPushModel getBidPushData(String id);
	
	/**
	 * description:获取借款信息
	 * autor:ym10159
	 * date:2018年1月23日 上午10:35:46
	 * @param id 借款ID
	 * @return map
	 */
	Map<String,Object> getLoanInfo(String id);
	
	/**
	 * description:记录捞财宝借款编号
	 * autor:ym10159
	 * date:2018年1月24日 上午9:49:19
	 */
	int insertLcbBorrowNo(Map<String,Object> map);
}
