package com.ezendai.credit2.master.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.dao.BlacklistDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.vo.BlacklistVO;

@Service
public class BlacklistServiceImpl implements BlacklistService {

	@Autowired
	private BlacklistDao blacklistDao;

	@Override
	public Blacklist insert(Blacklist blacklist) {
		return blacklistDao.insert(blacklist);
	}

	@Override
	public List<Blacklist> findListByVo(BlacklistVO blacklistVO) {
		return blacklistDao.findListByVo(blacklistVO);
	}
	
	@Override
	public void checkBlacklist(LoanDetailsVO loanDetailsVo) {
		//借款类型ID(车贷或小企贷)
		Integer productTypeId = loanDetailsVo.getProductTypeId();
		BlacklistVO blacklistVO = new BlacklistVO();
		blacklistVO.setIdnum(loanDetailsVo.getIdnum());
		//查询黑名单表
		List<Blacklist> blacklistList = blacklistDao.findListByVo(blacklistVO);
		if (CollectionUtil.isNotEmpty(blacklistList)) {
			Date seLoanLimitDate = null;
			//小企业贷限制理由
			String seLoanLimitRemark = null;
			//车贷限制理由
			Date carLoanLimitDate = null;
			String carLoanLimitRemark = null;
			for (int i = 0; i < blacklistList.size(); i++) {
				Blacklist blacklist = blacklistList.get(i);
				//获取黑名单借款类型
				Integer loanType = blacklist.getLoanType();
				String remark = blacklist.getRemark();
				//获取黑名单限制天数
				Integer limitDays = blacklist.getLimitDays() + 1;
				Date rejectTime = blacklist.getRejectTime();
				Date rejectDate = DateUtil.formatDate(rejectTime);
				//获取受限制后的日期
				Date dateAfterByDays = DateUtil.getDateAfterDay(rejectDate, limitDays);
				//获取小企业贷的最大限制日期
				if (loanType.compareTo(EnumConstants.BlacklistLoanType.SE_LOAN.getValue()) == 0 || loanType.compareTo(EnumConstants.BlacklistLoanType.ALL_LOAN.getValue()) == 0) {
					if (seLoanLimitDate == null || dateAfterByDays.after(seLoanLimitDate)) {
						seLoanLimitDate = dateAfterByDays;
						seLoanLimitRemark = remark;
					}
				}
				//获取车贷的最大限制日期
				else if (loanType.compareTo(EnumConstants.BlacklistLoanType.CAR_LOAN.getValue()) == 0 || loanType.compareTo(EnumConstants.BlacklistLoanType.ALL_LOAN.getValue()) == 0) {
					if (carLoanLimitDate == null || dateAfterByDays.after(carLoanLimitDate)) {
						carLoanLimitDate = dateAfterByDays;
						carLoanLimitRemark = remark;
					}
				}
			}
			//被小企业贷拒绝后，限制时间内申请任意小企业贷，不准进件
			if (productTypeId.compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0 && seLoanLimitDate != null && seLoanLimitDate.after(DateUtil.getToday())) {
				throw new BusinessException(seLoanLimitRemark);
			}
			//被车贷拒绝后，限制时间内申请任意车贷，不准进件
			else if (productTypeId.compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0 && carLoanLimitDate != null && carLoanLimitDate.after(DateUtil.getToday())) {
				throw new BusinessException(carLoanLimitRemark);
			}
			//被车贷拒绝后，限制时间内申请小企业贷，显示拒绝理由，提供是否继续进件的选项
			else if (productTypeId.compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0 && carLoanLimitDate != null && carLoanLimitDate.after(DateUtil.getToday())) {
				loanDetailsVo.setRefuseReason("车贷拒绝理由:" + carLoanLimitRemark);
			}
			//被小企业贷拒绝后，限制时间内申请车贷，显示拒绝理由，提供是否继续进件的选项
			else if (productTypeId.compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0 && seLoanLimitDate != null && seLoanLimitDate.after(DateUtil.getToday())) {
				loanDetailsVo.setRefuseReason("小企业贷拒绝理由:" + seLoanLimitRemark);
			}
		}
	}

	@Override
	public int updateLimitDays(BlacklistVO blacklistVO) {
		// TODO Auto-generated method stub
		return blacklistDao.updateLimitDays(blacklistVO);
	}
}
