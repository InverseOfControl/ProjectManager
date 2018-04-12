package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.BankManagerDao;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.service.BankManagerService;
import com.ezendai.credit2.master.vo.BankManagerVO;

/**
 * 
 * @Description: 银行表服务接口
 * @author 张宜超
 * @date 2016年1月25日
 */
@Service
@Transactional
public class BankManagerServiceImpl implements BankManagerService {

	@Autowired
	private BankManagerDao bankDao ;

	/**
	 * 获取银行列表
	 */
	@Override
	public Pager getBankList(BankManagerVO vo) {
		
		return bankDao.getBankList(vo);
	}

	/**
	 * 获取单个银行信息
	 */
	@Override
	public BankManager getBank(Long id) {
		
		return bankDao.getBank(id);
	}

	/**
	 * 添加银行
	 */
	@Override
	public void addBank(BankManagerVO bank) {
		bankDao.addBank(bank);
		
	}

	/**
	 * 更新银行信息
	 */
	@Override
	public int updateBank(BankManagerVO vo) {
		
		return bankDao.updateBank(vo);
	}

	/**
	 * 多条件查询银行信息
	 */
	@Override
	public List<BankManager> getBankByConditions(BankManagerVO vo) {
		
		return bankDao.getBankByConditions(vo);
	}

	/**
	 * 删除银行信息
	 */
	@Override
	public int deleteBank(Long id) {
		return bankDao.deleteBank(id);
		
	}
	
	
	
}
