package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;

/**
 * @author LinSanfu
 */

public interface OrganSalesDepartmentService {
	int update(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	void deleteByVO(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	void deleteListByVo(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	OrganSalesDepartment insert(OrganSalesDepartment organSalesDepartment);
	
	List<OrganSalesDepartment> findListByVo(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	Pager findWithPg(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	Integer count(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	OrganSalesDepartment get(Long id);
	
	List<BaseArea> findSalesDeptListByVo(OrganSalesDepartmentVO organSalesDepartmentVO);
}
