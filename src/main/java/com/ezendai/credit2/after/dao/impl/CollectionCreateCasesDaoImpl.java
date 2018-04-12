package com.ezendai.credit2.after.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.CollectionCreateCasesDao;
import com.ezendai.credit2.audit.dao.FirstApproveDao;
import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCasesRecord;
import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.model.PersonContacterAdditional;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

/***
 * <pre>
 * 
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
@Repository
public class CollectionCreateCasesDaoImpl extends BaseDaoImpl<CollectionCreateCases> implements CollectionCreateCasesDao {

	@SuppressWarnings("rawtypes")
	@Override
	public Pager findCollectionCreateCasesWithPG(CollectionCreateCasesVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findCollectionCreateCasesCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findCollectionCreateCasesWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	public void	insert(OverdueReceivablesCaseVO overdueReceivablesCase) {
		getSqlSession().insert(getIbatisMapperNameSpace() + ".insert", overdueReceivablesCase);
	}
	@Override
	public CollectionCreateCases selectLoanInfoByLoanId(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectLoanInfoByLoanId", id);
	}
	@Override
	public Pager findManagerCasesWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findManagerCasesCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findManagerCasesWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public void dispatchCases(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		  getSqlSession().update(getIbatisMapperNameSpace() + ".dispatchCases", vo);
	}
	@Override
	public void casesClosed(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".casesClosed", vo);
	}
	@Override
	public Pager findCollectionCasesTaskWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findCollectionCasesTaskCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findCollectionCasesTaskWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public Pager findPersonWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findPersonCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findPersonWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public Pager findCasesRecordWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findCasesRecordCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findCasesRecordWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public CollectionCasesTask selectTaskById(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectTaskById", id);
	}
	@Override
	public void updateCaseInfo(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		 getSqlSession().update(getIbatisMapperNameSpace() + ".updateCaseInfo", vo);
	}
	@Override
	public CollectionCreateCases selectCaseInfo(Long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectCaseInfo", id);
	}
	@Override
	public void insertTask(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		   getSqlSession().insert(getIbatisMapperNameSpace() + ".insertTask", vo);
	}
	@Override
	public void updateTask(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		  getSqlSession().update(getIbatisMapperNameSpace() + ".updateTask", vo);
	}
	@Override
	public void insertRecord(CollectionCasesRecord cr) {
		// TODO Auto-generated method stub
		 getSqlSession().insert(getIbatisMapperNameSpace() + ".insertRecord", cr);
	}
	@Override
	public CollectionCasesRecord selectRecordById(Long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectRecordById", id);
	}
	@Override
	public void updateTaskById(CollectionTaskVO vo) {
		// TODO Auto-generated method stub 
		 getSqlSession().update(getIbatisMapperNameSpace() + ".updateTaskById", vo);
	}
	@Override
	public Pager findManagerTaskWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findManagerTaskCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findManagerTaskWithPG", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public void updateRecord(CollectionCasesRecord cr) {
		// TODO Auto-generated method stub
		 getSqlSession().update(getIbatisMapperNameSpace() + ".updateRecord", cr);
	}
	@Override
	public void deleteRecord(Long id) {
		// TODO Auto-generated method stub
		  getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteRecord", id);
	}
	@Override
	public CasesPerson selectCollectionManager(String loginName){
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectCollectionManager",loginName);
	}
	@Override
	public List<CasesPerson>    seleCtcontactsTel(CasesPerson cp ) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".seleCtcontactsTel",cp);
	}
	@Override
	public List<CasesPerson>   selePersonTel(CasesPerson cp) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".selePersonTel",cp);
	}
	@Override
	public CollectionCreateCases selectLoanId(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectLoanId",id);
	}
	@Override
	public CollectionCasesTask seleTaskById(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".seleTaskById",id);
	}
	@Override
	public CollectionCreateCases selectLoanInfoById(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectLoanInfoById",id);
	}
	 
	@Override
	public void assignmentCaseInfo(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		 getSqlSession().update(getIbatisMapperNameSpace() + ".assignmentCaseInfo",vo);
	}
	@Override
	public CollectionCasesTask assignmentByLoanId(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".assignmentByLoanId",id);
	}
	@Override
	public CollectionCreateCases selectLoanInfoByCaseId(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectLoanInfoByCaseId",id);
	}
	@Override
	public Pager findPersonContactAdditionalnMap(PersonContacterAdditional pca) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findPersonContactAdditionalnCount", pca);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = pca.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findPersonContactAdditionalnMap", pca);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, pca);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public void insertPersonContacterAdditional(PersonContacterAdditional pca) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getIbatisMapperNameSpace() + ".insertPersonContacterAdditional",pca);
	}
	@Override
	public void deletePersonContacterAdditional(Long id) {
		// TODO Auto-generated method stub
		getSqlSession().delete(getIbatisMapperNameSpace() + ".deletePersonContacterAdditional",id);
	}
	@Override
	public void casesChange(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".casesChange",vo);
	}
	@Override
	public void taskChange(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".taskChange",vo);
	}
	@Override
	public Pager findCollectionContacerWithPG(CasesPerson cp) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findCollectionContacerCount", cp);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = cp.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findCollectionContacerWithPG", cp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, cp);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}
	@Override
	public Long insertCollectionContacer(CasesPerson cp) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getIbatisMapperNameSpace() + ".insertCollectionContacer",cp);
		return cp.getId();
	}
	@Override
	public void updateCollectionContacer(CasesPerson cp) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".updateCollectionContacer",cp);
	}
	@Override
	public CasesPerson selectCollectionContacerById(Long id) {
		// TODO Auto-generated method stub
		return	getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectCollectionContacerById",id);
	}
	@Override
	public void updatePersonContacterAdditional(CasesPerson cp) {
		// TODO Auto-generated method stub
		 	getSqlSession().update(getIbatisMapperNameSpace() + ".updatePersonContacterAdditional",cp);
	}
	@Override
	public CollectionCreateCases selectLastAmountAndDate(Long loanId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectLastAmountAndDate",loanId);
	}
	 
	
	 
}