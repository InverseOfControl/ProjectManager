/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.assembler.RepayInfoAssembler;
import com.ezendai.credit2.finance.dao.RepayInfoDao;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.finance.vo.RepayInfoVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.system.service.SysSerialNumService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SerialNumResult;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author liyuepeng
 * @version $Id: RepayInfoServiceImpl.java, v 0.1 2014-9-11 下午05:25:12 liyuepeng
 *          Exp $
 */
@Service
public class RepayInfoServiceImpl implements RepayInfoService {

	@Autowired
	private RepayInfoDao repayInfoDao;

	@Autowired
	private SysSerialNumService sysSerialNumService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public RepayInfo insert(RepayInfoVO repayInfoVo) {
		RepayInfo repayInfo = RepayInfoAssembler.transferVO2Model(repayInfoVo);
		return repayInfoDao.insert(repayInfo);
	}
	@Override
	public List<RepayInfo> findListByVO(RepayInfoVO repayInfoVo) {
		return repayInfoDao.findListByVo(repayInfoVo);
	}
	@Override
	public RepayInfo makeLoanCreateRepayInfo(Loan loan) {
		RepayInfoVO repayInfoVo = new RepayInfoVO();
		repayInfoVo.setAccountId(loan.getId());
		repayInfoVo.setTradeTime(new Date());
		repayInfoVo.setTradeAmount(loan.getPactMoney());
		repayInfoVo.setTradeNo(this.generateLoanTradeNo());
		repayInfoVo.setTeller(ApplicationContext.getUser().getId());
//		BaseArea salesTeam = sysUserService.getCurSalesDept();
//		if (salesTeam != null) {
//			repayInfoVo.setSalesdepartmentId(salesTeam.getId());
//		}
		repayInfoVo.setSalesdepartmentId(loan.getSalesDeptId());
		repayInfoVo.setTerm(loan.getAuditTime().longValue());
		repayInfoVo.setPayType(EnumConstants.TradeType.CASH.getValue());
		repayInfoVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		Long productId = loan.getProductId();
		if(EnumConstants.ProductList.STUDENT_LOAN.getValue().compareTo(productId)==0)
		{
			repayInfoVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC_ASC);
		}
		else
		{
			repayInfoVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		}
		RepayInfo repayInfo = RepayInfoAssembler.transferVO2Model(repayInfoVo);
		return repayInfoDao.insert(repayInfo);
	}

	@Override
	public void delete(Long id) {
		repayInfoDao.deleteById(id);
	}
	
	 /**
     * 
     * <pre>
     * 生成当天放款交易流水号
     * </pre>
     *
     * @param sysSerialNumService
     * @return 交易流水号
     * @exception 若失败throw BusinessException
     */
	@Override
	public String generateLoanTradeNo() {
		// 流水号
		StringBuffer tradeNo = new StringBuffer();
		// 当前日期
		String nowDateString = DateUtil.getDate(new Date(), "yyyyMMdd");
		tradeNo.append(nowDateString);
		SerialNumResult serialNumResult = sysSerialNumService
			.getSerialNum(SerialNum.REPAY_INFO_LOAN_NO);
		if (serialNumResult.isSuccess()) {
			// 流水号不足10位补0
			DecimalFormat df = new DecimalFormat("0000000000");
			String seq = df.format(serialNumResult.getSeq());
			tradeNo.append(seq);
		} else {
			throw new BusinessException("生成流水号失败，请重试。");
		}
		return tradeNo.toString();
	}
	
	/**
	 * 
	 * <pre>
	 * 生成当天还款交易流水号
	 * </pre>
	 *
	 * @param sysSerialNumService
	 * @return 交易流水号
	 * @exception 若失败throw BusinessException
	 */
	@Override
	public String generatePayTradeNo()  {
		// 流水号
		StringBuffer tradeNo = new StringBuffer();
		// 当前日期
		String nowDateString = DateUtil.getDate(new Date(), "yyyyMMdd");
		tradeNo.append(nowDateString);
		SerialNumResult serialNumResult = sysSerialNumService
			.getSerialNum(SerialNum.REPAY_INFO_PAY_NO);
		if (serialNumResult.isSuccess()) {
			// 流水号不足10位补0
			DecimalFormat df = new DecimalFormat("0000000000");
			String seq = df.format(serialNumResult.getSeq());
			tradeNo.append(seq);
		} else {
			throw new BusinessException("生成流水号失败，请重试。");
		}
		return tradeNo.toString();
	}
}
