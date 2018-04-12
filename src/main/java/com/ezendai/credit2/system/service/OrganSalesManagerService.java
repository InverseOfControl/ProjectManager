package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;

/**
 * @author LinSanfu
 */

public interface OrganSalesManagerService {
	int update(OrganSalesManagerVO organSalesManagerVO);
	
	OrganSalesManager insert(OrganSalesManager organSalesManager);
	
	void deleteListByVo(OrganSalesManagerVO organSalesManagerVO);
	
	/** 通过查询vo查找符合条件的Offer集合 */
	List<OrganSalesManager> findListByVo(OrganSalesManagerVO organSalesManagerVO);
	
	/** 通过查询vo查找符合条件的Offer集合 */
	Pager findWithPg(OrganSalesManagerVO organSalesManagerVO);
	
	/**通过vo查询单日符合条件记录的总和**/
	Integer count(OrganSalesManagerVO organSalesManagerVO);
	
	/**获取Organ对象通过Organ id**/
	OrganSalesManager get(Long id);
}
