package com.ezendai.credit2.after.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.SpecialRepaymentDao;
import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

@Repository
public class SpecialRepaymentDaoImpl extends BaseDaoImpl<SpecialRepayment> implements SpecialRepaymentDao{
	/**
	 * 通过传入loan的创建时间来关联特殊还款表
	 * @param specialRepaymentVo
	 * @return
	 * @see com.ezendai.credit2.after.dao.SpecialRepaymentDao#selectListByParams(com.ezendai.credit2.after.vo.SpecialRepaymentVO)
	 */
	@Override
	public List<Long> selectListByParams(SpecialRepaymentVO  specialRepaymentVo) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByParams", specialRepaymentVo);
	}

	@Override
	public int updateSpecialRepaymentState(SpecialRepaymentVO specialRepaymentVo) {
		return this.getSqlSession().update(getIbatisMapperNameSpace() + ".updateSpecialRepaymentState", specialRepaymentVo);
	} 
	
	@Override
	public List<SpecialRepayment>  findListByVOWihtUnion(SpecialRepaymentVO specialRepaymentVo) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOWihtUnion", specialRepaymentVo);
	}

	@Override
	public Pager findListByVOWihtExtend(SpecialRepaymentVO specialRepaymentVO) {
		// TODO Auto-generated method stub

		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countListByVOWihtExtend", specialRepaymentVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = specialRepaymentVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOWihtExtend", specialRepaymentVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, specialRepaymentVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	@Override
	public Pager findListByVOWihtBaseExtend(
			SpecialRepaymentVO specialRepaymentVO) {
		// TODO Auto-generated method stub

		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countListByVOWihtBaseExtend", specialRepaymentVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = specialRepaymentVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOWihtBaseExtend", specialRepaymentVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, specialRepaymentVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	} 

}
