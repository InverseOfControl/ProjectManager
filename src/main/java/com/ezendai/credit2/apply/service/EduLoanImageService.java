package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.EduLoanImage;

public interface EduLoanImageService {
	List<EduLoanImage> findByContractNo(String contractNo);

}
