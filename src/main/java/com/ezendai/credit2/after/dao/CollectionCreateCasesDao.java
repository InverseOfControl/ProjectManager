package com.ezendai.credit2.after.dao;

 

import java.util.List;

import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCasesRecord;
import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.CollectionCreateCases;
 
import com.ezendai.credit2.audit.model.PersonContacterAdditional;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
 
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface CollectionCreateCasesDao extends BaseDao<CollectionCreateCases> {
	
	 
	/**
	 * <pre>
	 * 创建催收案件查询
	 * @param returnDate
	 * @return
	 */
	Pager findCollectionCreateCasesWithPG(CollectionCreateCasesVO vo);
	
	void insert(OverdueReceivablesCaseVO overdueReceivablesCase) ;
	
	CollectionCreateCases	selectLoanInfoByLoanId(Long id);
	
	Pager findManagerCasesWithPG(CollectionCreateCasesVO vo);
	Pager findCollectionCasesTaskWithPG(CollectionCreateCasesVO vo);
	void  dispatchCases(CollectionCreateCasesVO vo);
	void  casesClosed(CollectionCreateCasesVO vo);
	Pager findPersonWithPG(CollectionCreateCasesVO vo);
	Pager findCasesRecordWithPG(CollectionCreateCasesVO vo);
	CollectionCasesTask selectTaskById(Long id) ; 
	
	void updateCaseInfo(CollectionCreateCasesVO vo);
	CollectionCreateCases selectCaseInfo(Long id) ; 
	void insertTask(CollectionTaskVO vo);
	void updateTask (CollectionTaskVO vo);
	
	
	
	void  insertRecord (CollectionCasesRecord cr);
	
	CollectionCasesRecord	selectRecordById(Long id) ; 
	void 	updateTaskById (CollectionTaskVO vo);
	Pager findManagerTaskWithPG(CollectionCreateCasesVO vo);
	void 	updateRecord (CollectionCasesRecord cr);
	void deleteRecord(Long id);
	CasesPerson selectCollectionManager(String loginName);
	
	 List<CasesPerson>  seleCtcontactsTel(CasesPerson cp);
	 List<CasesPerson>  selePersonTel(CasesPerson cp) ;
	 CollectionCreateCases 	selectLoanId(Long id);
	 
	 CollectionCasesTask seleTaskById(Long id);
	 CollectionCreateCases	selectLoanInfoById(Long id);
	 
	void assignmentCaseInfo( CollectionTaskVO vo);
	CollectionCasesTask assignmentByLoanId( Long id);
	CollectionCreateCases	selectLoanInfoByCaseId(Long id);
	
	Pager findPersonContactAdditionalnMap(PersonContacterAdditional pca);
	void insertPersonContacterAdditional(PersonContacterAdditional pca) ;
	void deletePersonContacterAdditional(Long id) ; 
	void  casesChange(CollectionCreateCasesVO vo);
	void  taskChange(CollectionTaskVO vo);
	Pager findCollectionContacerWithPG(CasesPerson cp);
	Long insertCollectionContacer(CasesPerson cp);
	void updateCollectionContacer(CasesPerson cp);
	CasesPerson selectCollectionContacerById(Long id);
	void updatePersonContacterAdditional(CasesPerson cp);
	CollectionCreateCases selectLastAmountAndDate(Long loanId);
}
