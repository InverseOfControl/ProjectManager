package com.ezendai.credit2.rule.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.rule.assembler.LoanRuleAssembler;
import com.ezendai.credit2.rule.dao.SignLoanRuleDao;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * <pre>
 * 特殊签单规则业务逻辑的实现
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SignLoanServiceImpl.java, v 0.1 2014年8月22日 下午1:57:04 zhangshihai Exp $
 */
@Service
public class SignLoanRuleServiceImpl implements SignLoanRuleService{
	@Autowired
	private SignLoanRuleDao signLoanRuleDao;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Override
	public Pager findSignLoanListByParam(LoanRuleVO loanRuleVO) {
		return signLoanRuleDao.findWithPg(loanRuleVO);
	}

	@Override
	@Transactional
	public String insert(LoanRule loanRule) {
		if(loanRule.getProductType() == 1) {
			loanRule.setProductSubtype(null);
		}
		// 设置规则类型
		loanRule.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());

		// 设置失效时间
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		// 月中
		if(today >= 13 && today <= 15) {	
			// 失效时间为当月16号凌晨
			Date date = DateUtil.getMonthSixteenDay();
			loanRule.setOverdueDate(date);
			if(loanRule.getProductType() == 1) {
				loanRule.setName("小企业贷月中签单");
			}else if(loanRule.getProductSubtype() == 1) {
				loanRule.setName("车贷移交类月中签单");
			}else if(loanRule.getProductSubtype() == 2) {
				loanRule.setName("车贷流通类月中签单");
			}
		}
		int dayOfMonth = DateUtil.getDayOfMonth();
		// 月末
		if((dayOfMonth - today) < 3) {
			// 失效时间为下月1号凌晨
			Date date = DateUtil.getNextMonthFirstDay();
			loanRule.setOverdueDate(date);
			if(loanRule.getProductType() == 1) {
				loanRule.setName("小企业贷月末签单");
			}else if(loanRule.getProductSubtype() == 1) {
				loanRule.setName("车贷移交类月末签单");
			}else if(loanRule.getProductSubtype() == 2) {
				loanRule.setName("车贷流通类月末签单");
			}
		}

		// 特殊签单属于"固定还款日"
		loanRule.setRepaydateRule(EnumConstants.RepayDateRuleType.FIXED_DATE.getValue());
		// 默认此规则不逻辑删除
		loanRule.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		// 默认此规则不执行
		loanRule.setIsExecuted(EnumConstants.YesOrNo.NO.getValue());
		LoanRuleVO loanRuleVO = LoanRuleAssembler.transferModel2VO(loanRule);
		List<LoanRule> ruleList = signLoanRuleDao.findListByVo(loanRuleVO);
		if(CollectionUtil.isNotEmpty(ruleList)) {
			return "此规则已存在";
		}

		signLoanRuleDao.insert(loanRule);
		
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SIGN_RULE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_SIGN_RULE.getValue());
		sysLog.setRemark("规则ID：" + loanRule.getId() + "新增");
		sysLogService.insert(sysLog);
		
		return "success";
	}

	@Override
	public LoanRule get(Long ruleId) {
		return signLoanRuleDao.get(ruleId);
	}

	@Override
	@Transactional
	public void deleteSignLoanRule(LoanRuleVO loanRuleVO) {
		signLoanRuleDao.deleteByIdList(loanRuleVO);
		
		if(loanRuleVO!=null&&CollectionUtil.isNotEmpty(loanRuleVO.getIdList())){
			for(Long id:loanRuleVO.getIdList()){
				SysLog sysLog = new SysLog();			
				sysLog.setOptModule(EnumConstants.OptionModule.SIGN_RULE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DELETE_SIGN_RULE.getValue());
				sysLog.setRemark("规则ID"+id+"逻辑删除");
				sysLogService.insert(sysLog);
			}
		}
	}

	@Override
	@Transactional
	public String update(LoanRuleVO loanRuleVO) {
		long ruleId =  loanRuleVO.getId();
		loanRuleVO.setId(null);
		loanRuleVO.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		if(loanRuleVO.getProductType()==1){
			loanRuleVO.setProductSubtypeNull(true);
			loanRuleVO.setProductSubtype(null);
		}else if(loanRuleVO.getProductType()==2){
			loanRuleVO.setProductSubtypeNull(false);
		}
		
		List<LoanRule> ruleList = signLoanRuleDao.findListByVo(loanRuleVO);
		if(ruleList.size() == 1 && ruleId != ruleList.get(0).getId()){
			return "此规则已存在";
		}
		
		loanRuleVO.setId(ruleId);
		
		signLoanRuleDao.update(loanRuleVO);
		
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SIGN_RULE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EDIT_SIGN_RULE.getValue());
		sysLog.setRemark("规则ID：" + loanRuleVO.getId() + "修改");
		sysLogService.insert(sysLog);
		
		return "success";
	}

	@Override
	public List<LoanRule> findRuleListByVO(LoanRuleVO loanRuleVO) {
		return signLoanRuleDao.findListByVo(loanRuleVO);
	}

	@Override
	public List<LoanRule> findSignRuleByLoanRuleVo(LoanRuleVO loanRuleVO) {
		Calendar calendar = Calendar.getInstance();
		int dayOfMonth = DateUtil.getDayOfMonth();
		// 获取当前日期
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		// 当前日期为月中
		if(today >= 13 && today <= 15) {
			loanRuleVO.setOverdueDate(DateUtil.getMonthSixteenDay());
		}
		// 当前日期为月末
		else if ((dayOfMonth - today) < 3) {
			loanRuleVO.setOverdueDate(DateUtil.getNextMonthFirstDay());
		}
		// 未删除
		loanRuleVO.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		// 可执行
		loanRuleVO.setIsExecuted(EnumConstants.YesOrNo.YES.getValue());
		List<LoanRule> loanRuleList = signLoanRuleDao.findListByVo(loanRuleVO);
		
		return loanRuleList;
	}

	@Override
	@Transactional
	public String setSignLoanRule(LoanRuleVO loanRuleVO) {
		String message = "不执行";
		long ruleId = loanRuleVO.getId();
		
		if(loanRuleVO.getIsExecuted() == 1) {
			loanRuleVO.setId(null);
			if(loanRuleVO.getProductType()==1){
				loanRuleVO.setProductSubtypeNull(true);
				loanRuleVO.setProductSubtype(null);
			}else if(loanRuleVO.getProductType()==2){
				loanRuleVO.setProductSubtypeNull(false);
			}
			List<LoanRule> ruleList = signLoanRuleDao.findListByVo(loanRuleVO);
			if(ruleList.size() == 1 && ruleId != ruleList.get(0).getId()){
				return "有效的相同规则已存在！";
			}
			loanRuleVO.setId(ruleId);
			// 如果设置为有效,则将执行时间设为当前时间
			loanRuleVO.setExecuteTime(new Date());
			message = "执行";
		}else{
			//如果设置为无效，清空执行时间
			loanRuleVO.setExecuteTimeNull(true);
		}
		signLoanRuleDao.update(loanRuleVO);
		
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SIGN_RULE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.SET_SIGN_RULE.getValue());
		sysLog.setRemark("规则ID：" + loanRuleVO.getId() + message);
		sysLogService.insert(sysLog);
		
		return "success";
	}

	@Override
	public int invalidRule(LoanRuleVO loanRuleVO) {
		return signLoanRuleDao.invalidRule(loanRuleVO);
	}

	@Override
	public int getCountByVO(LoanRuleVO loanRuleVO) {
		return signLoanRuleDao.getCountByVO(loanRuleVO);
	}
	
}
