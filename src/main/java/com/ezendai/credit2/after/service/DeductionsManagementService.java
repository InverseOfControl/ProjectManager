package com.ezendai.credit2.after.service;

import java.io.OutputStream;
import java.util.List;

import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.framework.util.Pager;

/***
 * 划扣管理
 * @author 陈忠星
 *
 */
public interface DeductionsManagementService {
	
	/***
	 * 划扣管理查询列表
	 * @param offerVO
	 * @return
	 */
	Pager findWithPg(OfferVO offerVO);

	Offer getOfferById(Long id);
	/***
	 * 修改金额
	 * @param offerVO
	 * @return
	 */
	int updateOfferAmount(OfferVO offerVO);
	/***
	 * 显示担保人信息
	 * @param OfferVO	
	 * @return
	 */
	List<Guarantee> findGuaranteeListByOfferVO(OfferVO offerVO);
	/***
	 * 显示担保人银行账号
	 * @param bankAccountId
	 *
	 * @return
	 */
	BankAccount getGuarateeBankAccountByBankAccountId(Long bankAccountId);
	/***
	 * 担保代扣
	 * @param offerVO
	 * @return
	 */
	int updateGuaratee(OfferVO offerVO);
	/***
	 * 查询可导出的数据
	 * @param offerVO
	 * @return
	 */
	List<Offer> findListByOfferVO(OfferVO offerVO);

	void exportExcel(List<Offer> offerList, String sheetName, OutputStream os);
	/***
	 * 是否有担保人
	 * @param OfferVO	 
	 * @return true 有 false 无
	 */
	boolean hasGuarantee(Offer offer);

	int updateOfferTpp(OfferVO offerVo);
	
	
	/**
	 * 获取报盘失败的数据
	 * @param offerList
	 * @param sheetName
	 * @param os
	 */
	public void exportFailExcel(List<Offer> offerList, String sheetName, OutputStream os);
	
	/**
	 * 检验筛选符合条件的数据
	 *
	 */
	boolean isCheckData(Long loanId);
}
