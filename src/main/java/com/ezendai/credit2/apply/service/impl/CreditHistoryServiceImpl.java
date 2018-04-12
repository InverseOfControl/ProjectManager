package com.ezendai.credit2.apply.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.CreditHistoryDao;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.service.CreditHistoryService;
import com.ezendai.credit2.apply.vo.CreditHistoryVO;

/***
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: ApplyServiceImpl.java, v 0.1 2014年6月24日 上午9:04:27 HQ-AT6 Exp $
 */
@Service
public class CreditHistoryServiceImpl implements CreditHistoryService {

	@Autowired
    private CreditHistoryDao creditHistoryDao;

	
	/**
	 * 插入一条申请人信贷历史信息
	 * @param creditHistory
	 * @return
	 * @see com.ezendai.credit2.apply.service.ApplyService#insertCreditHistory(com.ezendai.credit2.apply.model.CreditHistory)
	 */
	@Override
	public CreditHistory insertCreditHistory(CreditHistory creditHistory){	    
	   return  creditHistoryDao.insert(creditHistory);
	}

	/**
     * 根据ID查询一条申请人信贷历史信息
     * @param creditHistory
     * @return
     * @see com.ezendai.credit2.apply.service.ApplyService#insertCreditHistory(com.ezendai.credit2.apply.model.CreditHistory)
     */
    @Override
    public CreditHistory getCreditHistoryById(Long  id){      
       return  creditHistoryDao.get(id);
               
    }
   /**
     * 根据借款人ID查询一条申请人信贷历史信息
     * @param creditHistory
     * @return
     * @see com.ezendai.credit2.apply.service.ApplyService#insertCreditHistory(com.ezendai.credit2.apply.model.CreditHistory)
     */
    @Override
    public List<CreditHistory> getCreditHistoryByPersonId(Long  personId, Long loanId){
       CreditHistoryVO creditHistoryVo = new CreditHistoryVO();
       creditHistoryVo.setPersonId(personId);
       creditHistoryVo.setLoanId(loanId);
       return  creditHistoryDao.findListByVo(creditHistoryVo);
               
    }
	/**
     * 根据ID删除申请人信贷历史信息
     * @param creditHistory
     * @return
     * @see com.ezendai.credit2.apply.service.ApplyService#insertCreditHistory(com.ezendai.credit2.apply.model.CreditHistory)
     */
    @Override
    public void deleteCreditHistoryById(Long id){      
         creditHistoryDao.deleteById(id);
    }

	@Override
	public int updateCreditHistoryByPersonId(CreditHistory creditHistory) {
		if(creditHistory.getHasCreditCard() == 0 ){
			creditHistory.setCardNum(0);
			creditHistory.setTotalAmount(new BigDecimal(0));
			creditHistory.setOverdrawAmount(new BigDecimal(0));
		}
		return creditHistoryDao.updateCreditHistoryByPersonId(creditHistory);
	}

	


}
