package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.vo.ContacterVO;

public interface ContacterService {

	
	public List<Contacter> getContacterListByBorrowerId(Long borrowerId, Long loanId);

	int updateContacter(Contacter contacter);

	Contacter insert(Contacter contacter);

	int update(ContacterVO contacterVO);
	
	void delete(Long id);
	
	Contacter get(Long id);
}
