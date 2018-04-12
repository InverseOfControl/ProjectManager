package com.ezendai.credit2.finance.dao;

import java.util.List;

import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.vo.FlowVO;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * 
 * @author Administrator
 *
 */
public interface FlowDao extends BaseDao<Flow> {
	 List<Flow> getListFlowByVO(FlowVO flowVO);
}
