package com.ezendai.credit2.apply.service;

import com.ezendai.credit2.apply.model.EcifTransfer;
import com.ezendai.credit2.apply.vo.EcifTransferVO;
import com.ezendai.credit2.framework.util.Pager;

public interface EcifTransferService {

	EcifTransfer insert(EcifTransfer ecifTransfer);
	Pager findWithPg(EcifTransferVO ecifTransferVO);
}
