package com.ezendai.credit2.apply.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.LoanDao;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

@Repository
public class LoanDaoImpl extends BaseDaoImpl<Loan> implements LoanDao {

	@Override
	public int loanCount(LoanVO loanVO) {
		Object object = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", loanVO);
		return Integer.parseInt(object.toString());
	}

	@Override
	public int updateLoanByIdList(LoanVO loanVO) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".updateByIdList", loanVO);
	}
	/**
	 * <pre>
	 * 更新状态不是某个状态的记录
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@Override
	public int updateLoanByStatus(LoanVO loanVO) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".updateByStatus", loanVO);
	}
	/**
	 * 
	 * <pre>
	 * 对公还款->领取借款记录列表
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pager findBusinessLoanListWithPg(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".businessCount", loanVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findBusinessLoanWithPG", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#changeManageFindWithPG(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pager changeManageFindWithPG(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".changeManageCount", loanVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".changeManageFindWithPG", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Pager findRepayTrialWithPG(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countRepayTrial", loanVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findRepayTrialWithPG", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#specialRepaymentFindWithPG(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pager specialRepaymentFindWithPG(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".specialRepaymentCount", loanVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".specialRepaymentFindWithPG", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Pager findRepayEntryWithPG(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countRepayEntry", loanVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findRepayEntryWithPG", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#findListByVOWithUnion(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Long> findListByVOWithUnion(LoanVO loanVO) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOWithUnion", loanVO);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pager findWithPGUnionExtension(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countUnionExtension", loanVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPGUnionExtension", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#findListByVOUnionExtension(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Loan> findListByVOUnionExtension(LoanVO loanVO) {
		List<Loan> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOUnionExtension", loanVO);
		return rstList;
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#findLastOverdueList(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Loan> findLastOverdueList(LoanVO loanVO) {
		List<Loan> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findLastOverdueList", loanVO);
		return rstList;
	}
	
	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanDao#findListByVoExtension(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Loan> findListByVoExtension(LoanVO loanVO) {
		List<Loan> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVOExtension", loanVO);
		return rstList;
	}
	@Override
	public List<Loan> alreadyProcessedList(LoanVO loanVO) {
		List<Loan> rstList = null;
		try{
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".alreadyProcessedList", loanVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rstList;
		
	}

	@Override
	public Pager plFindWithPGUnionExtension(LoanVO loanVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".plCountUnionExtension", loanVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = loanVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".plFindWithPGUnionExtension", loanVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, loanVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);

		return pg;
	}

	@Override
	public void updatePushLcbReturnFraud(Map<String, Object> map) {
		super.getSqlSession().update(getIbatisMapperNameSpace() + ".updatePushLcbReturnFraud",map);
		
	}

	@Override
	public BigDecimal findResidualPactMoney(String idNum) {
		Map<String, Object> param = new HashMap<>();
		param.put("idNum", idNum);
		Object o = super.getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findResidualPactMoney", param);
		if(o == null){
			o = 0;
		}
		return new BigDecimal(o.toString());
	}
}