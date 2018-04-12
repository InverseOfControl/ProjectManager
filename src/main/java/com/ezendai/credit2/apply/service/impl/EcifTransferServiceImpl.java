package com.ezendai.credit2.apply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.EcifTransferDao;
import com.ezendai.credit2.apply.model.EcifTransfer;
import com.ezendai.credit2.apply.service.EcifTransferService;
import com.ezendai.credit2.apply.vo.EcifTransferVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.util.Pager;
@Service
public class EcifTransferServiceImpl implements EcifTransferService {

	@Autowired
	private EcifTransferDao ecifTransferDao;
	@Override
	public EcifTransfer insert(EcifTransfer ecifTransfer) {
		
		
		return ecifTransferDao.insert(ecifTransfer);
	}
	@Override
	public Pager findWithPg(EcifTransferVO ecifTransferVO) {
		return ecifTransferDao.findWithPg(ecifTransferVO);
	}

}
