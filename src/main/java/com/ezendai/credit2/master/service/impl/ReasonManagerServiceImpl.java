package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ReasonManagerDao;
import com.ezendai.credit2.master.model.RefusedReason;
import com.ezendai.credit2.master.service.ReasonManagerService;
import com.ezendai.credit2.master.vo.RefusedReasonVO;

@Service
@Transactional
public class ReasonManagerServiceImpl implements ReasonManagerService {

	@Autowired
	private ReasonManagerDao reasonDao;
	
	@Override
	public Pager getReasonList(RefusedReasonVO vo) {
		// TODO Auto-generated method stub
		return reasonDao.getReasonList(vo);
	}

	/**
	 * 查询所有的父原因，用于填充下拉列表
	 */
	@Override
	public List<RefusedReason> findAllParentReasonList(){
		return reasonDao.findAllParentReasonList();
	}
	
	@Override
	public int addReason(RefusedReasonVO vo){
		return reasonDao.addReason(vo);
	}
	
	@Override
	public RefusedReason getReasonById(Long id){
		return reasonDao.getReasonById(id);
	}
	
	@Override
	public int updateReason(RefusedReasonVO vo){
		return reasonDao.updateReason(vo);
	}
	
	@Override
	public int disableReason(RefusedReasonVO reason){
		return reasonDao.disableReason(reason);
	}
}
