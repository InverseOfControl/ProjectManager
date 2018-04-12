package com.ezendai.credit2.after.service;

import java.util.List;

import com.ezendai.credit2.after.model.LoanOnlineTest;
import com.ezendai.credit2.after.vo.LoanOnlineTestVO;

/**
 * @author LinSanfu
 */

public interface LoanOnlineTestService {
	/** 通过查询vo查找符合条件的LoanOnlineTest集合 */
	List<LoanOnlineTest> findListByVo(LoanOnlineTestVO LoanOnlineTest);
}
