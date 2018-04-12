package com.ezendai.credit2.master.service;

import java.io.OutputStream;
import java.util.List;

import com.ezendai.credit2.master.model.LoanChangeLog;
import com.ezendai.credit2.master.vo.LoanChangeLogVO;

public interface LoanChangeLogService {
	public void insert(LoanChangeLog loanChangeLog);
	public List<LoanChangeLog> findListByVO(LoanChangeLogVO loanChangeLogVO);
	public void exportExcel(List<LoanChangeLog> loanChangeLogList, String sheetName,
			OutputStream os);
}
