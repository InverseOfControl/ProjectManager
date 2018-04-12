package com.ezendai.credit2.audit.dao;

import java.util.List;

import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface PersonBankDao extends BaseDao<PersonBank>{

	List<PersonBank> findListByVoExtension(PersonBankVO vo);
	
	/** 根据查询条件vo物理分页查询出数据行：{mapper.xml需要实现} */
	Pager findWithPgExtension(PersonBankVO vo);
	
	PersonBank getExtension(Long id);
	/**
	 * 分页查询客户银行单表信息
	 * @param vo
	 * @return
	 */
	Pager findWithPG(PersonBankVO vo);
}
