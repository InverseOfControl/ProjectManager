package com.ezendai.credit2.report.dao;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.vo.OverdueMsgLogVO;

public interface OverdueMsgLogDao{
	
	Pager  findWithPG(OverdueMsgLogVO OverdueMsgLogVO);
}
