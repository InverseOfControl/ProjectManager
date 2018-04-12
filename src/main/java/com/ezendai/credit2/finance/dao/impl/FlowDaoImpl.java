package com.ezendai.credit2.finance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.finance.dao.FlowDao;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.vo.FlowVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * 
 * @author Administrator
 *
 */
@Repository
public class FlowDaoImpl extends BaseDaoImpl<Flow> implements FlowDao {

	@Override
	public List<Flow> getListFlowByVO(FlowVO flowVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getListFlowByVO",flowVO);
	}

}
