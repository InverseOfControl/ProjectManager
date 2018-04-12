/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.assembler.ContacterAssembler;
import com.ezendai.credit2.apply.dao.ContacterDao;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.service.ContacterService;
import com.ezendai.credit2.apply.vo.ContacterVO;

/***
 * 联系人
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: ContacterServiceImpl.java, v 0.1 2014年6月28日 下午5:42:40 HQ-AT6 Exp $
 */
@Repository
public class ContacterServiceImpl implements ContacterService {

	@Autowired
	ContacterDao contacterDao;

	@Override
	public List<Contacter> getContacterListByBorrowerId(Long borrowerId, Long loanId) {
		ContacterVO contacterVo = new ContacterVO();
		contacterVo.setBorrowerId(borrowerId);
		contacterVo.setLoanId(loanId);
		return contacterDao.findListByVo(contacterVo);
	}

	/***
	 * 更新联系人信息  
	 * @param contacter
	 * @return
	 * @see com.ezendai.credit2.apply.service.ContacterService#updateContacter(com.ezendai.credit2.apply.model.Contacter)
	 */
	@Override
	public int updateContacter(Contacter contacter) {
		ContacterVO contacterVO = ContacterAssembler.transferModel2VO(contacter);
		return contacterDao.update(contacterVO);
	}

	/**
	 * 
	 * <pre>
	 * 新增联系人
	 * </pre>
	 *
	 * @param contacter
	 * @return
	 */
	@Override
	public Contacter insert(Contacter contacter) {
		return contacterDao.insert(contacter);
	}

	@Override
	public int update(ContacterVO contacterVO) {

		return contacterDao.update(contacterVO);
	}

	@Override
	public void delete(Long id) {
		contacterDao.deleteById(id);
		
	}

	@Override
	public Contacter get(Long id) {
		// TODO Auto-generated method stub
		return contacterDao.get(id);
	}
	

	

}
