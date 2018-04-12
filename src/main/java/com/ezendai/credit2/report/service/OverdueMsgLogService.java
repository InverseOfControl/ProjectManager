package com.ezendai.credit2.report.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.vo.OverdueMsgLogVO;

public interface OverdueMsgLogService {
	Pager  findWithPG(OverdueMsgLogVO OverdueMsgLogVO);

}
