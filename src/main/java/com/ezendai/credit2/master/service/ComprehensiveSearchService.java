 
package com.ezendai.credit2.master.service;

import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.vo.ComprehensiveSearchVO;

 
public interface ComprehensiveSearchService {

	public Pager getComprehensiveSearchList(ComprehensiveSearchVO csVo) ;
	public RepayEntryDetailsVO viewEdit(Long loanId) ;
}
