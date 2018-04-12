/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 展期信息业务逻辑
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ExtensionService.java, v 0.1 2015年3月10日 下午2:29:44 00221921 Exp $
 */
public interface ExtensionService {
	
	Extension insert(Extension extension);
	
	void deleteById(Long id);
	
	void deleteByIdList(ExtensionVO extensionVO);
	
	int update(ExtensionVO extensionVO);
	
	int updateByIdList(ExtensionVO extensionVO);
	
	Extension get(Long id);
	
	/**
	 * 
	 * <pre>
	 * 根据loan的id，获取其申请的最近的、有效的展期extension
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	Extension getExtensionByLoanId(Long loanId);
	
	List<Extension> findListByVo(ExtensionVO extensionVO);
	
	boolean exists(Map<String, Object> map);

	Pager findWithPg(ExtensionVO extensionVO);

	boolean exists(Long id);
	
	/**
	 * <pre>
	 * 更新状态不是某个状态的记录
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int updateExtensionByStatus(ExtensionVO extensionVO);
	
	/**
	 * 
	 * <pre>
	 * 获取该extensionId指定的展期申请的最近的、有效的新的展期
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	Extension getNextExtension(Long extensionId);
	
	/**
	 * 
	 * <pre>
	 * 查询最后一期是逾期的记录
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	List<Extension> findLastOverdueList(ExtensionVO vo);
	
	 /**
	 * <pre>
	 *  根据借款id或者展期id判断是否对该笔借款做过展期
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	boolean checkIsExtension(Long loanId);
	
	/**
	 * <pre>
	 *  
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	void updateLastLoanRepaymentPlan(Long extensionId);
	
	/**
	 * 
	 * <pre>
	 * 获取符合条件的extension总条数
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int getCountByExtensionVO(ExtensionVO extensionVO);
	
	/**
	 * 获取展期数据根据条件（现用于申请展期唯一性判断）
	 * @param extensionVO
	 * @return
	 */
	List<Extension> findExtensionByCondition(ExtensionVO extensionVO);

	/**
	 * <pre>
	 *  CAR_FINE_NEW_CAL_EXECUTE_TIME后新计算，展期账单变化
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	void updateLastLoanRepaymentPlanNew(Long extensionId);
}
