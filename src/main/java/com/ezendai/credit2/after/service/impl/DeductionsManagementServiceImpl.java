/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.after.dao.DeductionsManagementDao;
import com.ezendai.credit2.after.dao.OfferDao;
import com.ezendai.credit2.after.expdata.common.TPPConstants;
import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.service.DeductionsManagementService;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;

/***
 * 
 * <pre>
 * 划扣管理
 * </pre>
 *
 * @author 陈忠星
 * @version $Id: DeductionsManagementImpl.java, v 0.1 2014年11月26日 上午10:43:53 陈忠星 Exp $
 */
@Service
public class DeductionsManagementServiceImpl implements DeductionsManagementService {
	@Autowired
	private GuaranteeService guaranteeService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private OfferDao offerDao;
	@Autowired
	private DeductionsManagementDao deductionsManagementDao;
	/***
	 * 划扣管理查询列表
	 * @param offerVO
	 * @return
	 */
	@Override
	public Pager findWithPg(OfferVO offerVO) {
		return offerDao.findWithPg(offerVO);
	}

	@Override
	public Offer getOfferById(Long id) {
		return offerDao.get(id);
	}

	/***
	 * 查询可导出的数据
	 * @param offerVO
	 * @return
	 */
	@Override
	public List<Offer> findListByOfferVO(OfferVO offerVO) {
		return offerDao.findListByVo(offerVO);
	}

	/***
	 * 修改金额
	 * @param offerVO
	 * @return
	 */
	@Override
	public int updateOfferAmount(OfferVO offerVO) {
		return offerDao.update(offerVO);

	}
	/***
	 * 是否有担保人
	 * @param OfferVO	 
	 * @return true 有 false 无
	 */
	@Override
	public boolean hasGuarantee(Offer offer) {
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(offer.getPersonId(),offer.getLoanId());
		for (Guarantee guarantee : guaranteeList) {	
			if (guarantee.getFlag() != null) {
				return true;
			}
		}
		return false;

	}
	
	/***
	 * 显示担保人信息
	 * @param OfferVO	 
	 * @return
	 */
	@Override
	public List<Guarantee> findGuaranteeListByOfferVO(OfferVO offerVO) {
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(offerVO.getPersonId(),
			offerVO.getLoanId());
		List<Guarantee> guaranteeList2 = new ArrayList<Guarantee>();
		for (Guarantee guarantee : guaranteeList) {
			//指定的自然人
			if (guarantee.getFlag() != null
				&& guarantee.getGuaranteeType().compareTo(
					EnumConstants.GuaranteeType.NATURAL_GUARANTEE.getValue()) == 0) {
				guaranteeList2.add(guarantee);
			}

		}
		//借款人的信息
		PersonBankVO personBankVO = new PersonBankVO();
		personBankVO.setPersonId(offerVO.getPersonId());
		personBankVO.setLoanId(offerVO.getLoanId());
		PersonBank personBank = personBankService.getPersonBank(personBankVO);
		BankAccount bankAccount = bankAccountService.get(personBank.getBankAccountId());
		Guarantee guarantee = new Guarantee();
		guarantee.setName(offerVO.getPersonName());
		guarantee.setBankAccountId(bankAccount.getId());
		guaranteeList2.add(guarantee);
		return guaranteeList2;

	}

	/***
	 * 显示担保代人银行账户信息
	 * @return
	 */
	@Override
	public BankAccount getGuarateeBankAccountByBankAccountId(Long bankId) {
		return bankAccountService.get(bankId);
	}

	/***
	 * 担保代扣
	 * @param offerVO
	 * @return
	 */
	@Override
	public int updateGuaratee(OfferVO offerVO) {
		return offerDao.update(offerVO);

	}

	/***
	 * 
	 * <pre>
	 * 包装查询的数据后导出Excel
	 * </pre>
	 *
	 */
	@Override
	@Transactional
	public void exportExcel(List<Offer> offerList, String sheetName, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[17];
		//列头
		column[0] = new String("借款人                ");
		column[1] = new String("银行         ");
		column[2] = new String("账号            ");
		column[3] = new String("应报金额       ");
		column[4] = new String("实报金额       ");
		column[5] = new String("回盘金额        ");
		column[6] = new String("回盘日期        ");
		column[7] = new String("备注      ");
		column[8] = new String("划扣结果   ");
		column[9] = new String("划扣状态   ");
		column[10] = new String("划扣通道");
		column[11] = new String("合同来源");
		column[12] = new String("公司名称");
		column[13] = new String("担保人");
		column[14] = new String("担保人银行");
		column[15] = new String("担保人账号");
		column[16] = new String("放款营业部");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(offerList)) {
			// 每一行要显示的内容
			for (Offer offer : offerList) {
				String[] row = new String[17];
				row[0] = new String(offer.getPersonName());
				row[1] = new String(offer.getBankName());
				row[2] = new String(offer.getBankAccount());
				row[3] = new String(offer.getReceivableAmount().toString());
				row[4] = new String(offer.getOfferAmount().toString());
				if(offer.getSuccessAmount()!=null){
					row[5] = new String(offer.getSuccessAmount().toString());
				}else {
					row[5] = new String("");
				}
				if (offer.getTppCallBackData() != null
					&& offer.getTppCallBackData().getReceiveTime() != null) {
					row[6] = new String(DateUtil.getDate(offer.getTppCallBackData()
						.getReceiveTime(), "yyyy-MM-dd hh:mm:ss"));
				} else {
					row[6] = new String("");
				}
				if (offer.getTppCallBackData() != null
					&& offer.getTppCallBackData().getRemark() != null) {
					row[7] = new String(offer.getTppCallBackData().getRemark());
				} else {
					row[7] = new String("");
				}
				if (offer.getTppCallBackData() != null
					&& offer.getTppCallBackData().getReturnCode() != null) {
					if (TPPConstants.HANDLE_SUCCESS_CODE.equals(offer.getTppCallBackData().getReturnCode())) {
						row[8] = new String("成功");
					} else if (TPPConstants.HANDLE_FAILURE_CODE.equals(offer.getTppCallBackData().getReturnCode())) {
						row[8] = new String("失败");
					}  else if (TPPConstants.HANDLE_PARTSUCCESS_CODE.equals(offer.getTppCallBackData().getReturnCode())) {
						row[8] = new String("部分还款");
					} else {
						row[8] = new String("");
					}
				} else {
					row[8] = new String("");
				}
				if (offer.getStatus() != null) {
					if (offer.getStatus().compareTo(
							EnumConstants.OfferState.CALL_BACK_OFFER.getValue()) == 0) {
						row[9] = new String("已回盘");
					} else if (offer.getStatus().compareTo(
						EnumConstants.OfferState.HAS_TO_OFFER.getValue()) == 0) {
						row[9] = new String("已报盘");
					} else if (offer.getStatus().compareTo(
						EnumConstants.OfferState.NOT_OFFER.getValue()) == 0) {
						row[9] = new String("未报盘");
					} else {
						row[9] = new String("");
					}

				} else {
					row[9] = new String("");
				}
				if (offer.getTppType() != null) {
					if(offer.getTppType()==10){
						row[10] = new String("通联");
					}else if(offer.getTppType()==20){
						row[10] = new String("富友");
					}else if(offer.getTppType()==30){
						row[10] = new String("银联");
					}else if(offer.getTppType()==40){
						row[10] = new String("快捷通");
					}else{
						row[10] = new String("");
					}
						
					
				} else {
					row[10] = new String("");
				}
				if (offer.getLoan() != null && offer.getLoan().getContractSrc() != null) {
					if (offer.getLoan().getContractSrc()
						.compareTo(EnumConstants.ContractSrc.P2P.getValue()) == 0) {
						row[11] = new String("证大P2P");
					} else if (offer.getLoan().getContractSrc()
						.compareTo(EnumConstants.ContractSrc.AITE.getValue()) == 0) {
						row[11] = new String("证大爱特");
					} else {
						row[11] = new String("");
					}
				} else {
					row[11] = new String("");
				}
				if (offer.getBusinessCompany()!=null && StringUtil.isNotBlank(offer.getBusinessCompany().getShortName())) {
					row[12] = new String(offer.getBusinessCompany().getShortName().trim());
				} else {
					row[12] = new String("");
				}
				if (StringUtil.isNotBlank(offer.getGuaranteeName())) {
					row[13] = new String(offer.getGuaranteeName());
				} else {
					row[13] = new String("");
				}
				if (StringUtil.isNotBlank(offer.getGuaranteeBankName())) {
					row[14] = new String(offer.getGuaranteeBankName());
				} else {
					row[14] = new String("");
				}
				if (StringUtil.isNotBlank(offer.getGuaranteeBankAccount())) {
					row[15] = new String(offer.getGuaranteeBankAccount());
				} else {
					row[15] = new String("");
				}
				if (StringUtil.isNotBlank(offer.getSalesDeptName())) {
					row[16] = new String(offer.getSalesDeptName());
				} else {
					row[16] = new String("");
				}
				rows.add(row);
			}

		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		//生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(exportExcelList, os);
	}

	@Override
	public int updateOfferTpp(OfferVO offerVo) {
		// TODO Auto-generated method stub
		return offerDao.updateOfferTpp(offerVo);
	}
	
	
	/***
	 * 
	 * <pre>
	 * 包装划扣失败数据进行导出
	 * </pre>
	 *
	 */
	@Override
	@Transactional
	public void exportFailExcel(List<Offer> offerList, String sheetName, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[9];
		//列头
		column[0] = new String("姓名                ");
		column[1] = new String("身份证号         ");
		column[2] = new String("开户行            ");
		column[3] = new String("账号       ");
		column[4] = new String("应报金额       ");
		column[5] = new String("实报金额        ");
		column[6] = new String("回盘金额        ");
		column[7] = new String("生成时间  ");
		column[8] = new String("失败原因 ");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(offerList)) {
			// 每一行要显示的内容
			for (Offer offer : offerList) {
				String[] row = new String[9];
				//姓名 
				row[0] = new String(offer.getPersonName());
				//身份证号
				row[1] = new String("");
				if(offer.getPerson()!=null){
					if(offer.getPerson().getIdnum() !=null){
						row[1] = new String(offer.getPerson().getIdnum());
					}
				}
				row[2] = new String(offer.getBankName());
				row[3] = new String(offer.getBankAccount());
				row[4] = new String("");
				if(offer.getReceivableAmount() !=null){
					row[4] = new String(offer.getReceivableAmount().toString());
				}
				row[5] = new String("");
				if(offer.getOfferAmount() !=null){
					row[5] = new String(offer.getOfferAmount().toString());
				}
				row[6] = new String("");
				if(offer.getSuccessAmount()!=null){
					row[6] = new String(offer.getSuccessAmount().toString());
				}
				row[7] = new String("");
				if(offer.getCreatedTime() !=null){
					row[7] = new String(DateUtil.getDate(offer.getCreatedTime(),"yyyy-MM-dd hh:mm:ss"));
				}
				row[8] = new String("");
				if(offer.getTppCallBackData() !=null){
					if(StringUtils.isNotEmpty(offer.getTppCallBackData().getFailReason())){
						row[8] = new String(offer.getTppCallBackData().getFailReason());
					}
				}
				rows.add(row);
			}

		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		//生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(exportExcelList, os);
	}

	@Override
	public boolean isCheckData(Long loanId) {
		boolean flag=false;
		int ruselt=deductionsManagementDao.isCheckData(loanId);
		if(ruselt>0){
			flag=true;
		}
		return flag;
	}

}
