package com.ezendai.credit2.after.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.dao.LoanOnlineTestDao;
import com.ezendai.credit2.after.model.LoanOnlineTest;
import com.ezendai.credit2.after.service.LoanOnlineTestService;
import com.ezendai.credit2.after.vo.LoanOnlineTestVO;

/**
 * @author LinSanfu
 */
@Service
public class LoanOnlineTestServiceImpl implements LoanOnlineTestService{
	@Autowired
	private  LoanOnlineTestDao dao;
	
	@Override
	public List<LoanOnlineTest> findListByVo(LoanOnlineTestVO vo) {
		return dao.findListByVo(vo);
	}
}
