package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.CreditHistory;

public interface CreditHistoryService {

    CreditHistory insertCreditHistory(CreditHistory creditHistory);

    CreditHistory getCreditHistoryById(Long id);

    void deleteCreditHistoryById(Long id);

    List<CreditHistory> getCreditHistoryByPersonId(Long personId, Long loanId);
    
    int updateCreditHistoryByPersonId(CreditHistory creditHistory);

}
