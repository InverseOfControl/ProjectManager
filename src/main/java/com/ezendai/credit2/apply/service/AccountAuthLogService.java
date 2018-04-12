package com.ezendai.credit2.apply.service;

import com.ezendai.credit2.apply.model.AccountAuthLog;
import com.ezendai.credit2.apply.vo.AccountAuthLogVO;
import com.ezendai.credit2.framework.util.Pager;

public interface AccountAuthLogService {

	AccountAuthLog insert(AccountAuthLog accountAuthLog);
	
	Pager findWithPg(AccountAuthLogVO accountAuthLogVO);
}
