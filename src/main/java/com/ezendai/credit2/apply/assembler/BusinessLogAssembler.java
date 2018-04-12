package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.vo.BusinessLogVO;

/**
 * 
 * <pre>
 * 业务日志  VO与Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessLogAssembler.java, v 0.1 2014年7月30日 下午12:44:38 zhangshihai Exp $
 */
public class BusinessLogAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param businessLog
	 * @return
	 */
	public static BusinessLogVO transferModel2VO (BusinessLog businessLog) {
		if (businessLog == null) {
			return null;
		}
		
		BusinessLogVO businessLogVO = new BusinessLogVO();
		
		businessLogVO.setId(businessLog.getId());
		businessLogVO.setLoanId(businessLog.getLoanId());
		businessLogVO.setMessage(businessLog.getMessage());
		businessLogVO.setCreateDate(businessLog.getCreateDate());
		businessLogVO.setOperator(businessLog.getOperator());
		businessLogVO.setFlowStatus(businessLog.getFlowStatus());
		businessLogVO.setVersion(businessLog.getVersion());
		
		return businessLogVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param businessLogVO
	 * @return
	 */
	public static BusinessLog transferVO2Model (BusinessLogVO businessLogVO) {
		if (businessLogVO == null) {
			return null;
		}
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setId(businessLogVO.getId());
		businessLog.setLoanId(businessLogVO.getLoanId());
		businessLog.setMessage(businessLogVO.getMessage());
		businessLog.setCreateDate(businessLogVO.getCreateDate());
		businessLog.setOperator(businessLogVO.getOperator());
		businessLog.setFlowStatus(businessLogVO.getFlowStatus());
		businessLog.setVersion(businessLogVO.getVersion());
		
		return businessLog;
	}
}
