package com.ezendai.credit2.rule.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.rule.assembler.LoanRuleAssembler;
import com.ezendai.credit2.rule.dao.RepayDateRuleDao;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * <pre>
 * 还款日规则
 * </pre>
 *
 * @author chenzx
 * @version $Id: DueDateRuleServiceImpl.java, v 0.1 2014年8月22日 下午1:47:04 HQ-AT6 Exp $
 */
@Service
public class RepayDateRuleServiceImpl implements  RepayDateRuleService{
	@Autowired
	private RepayDateRuleDao repayDateRuleDao;
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 
	 * <pre>
	 * 根据参数查询规则
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	@Override
	public Pager findSysRuleByParam(LoanRuleVO loanRuleVO) {
		return repayDateRuleDao.findWithPg(loanRuleVO);
	}
	/***
	 * 保存还款规则
	 * @param loanRule
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#insert(com.ezendai.credit2.rule.model.LoanRule)
	 */
	@Override
	@Transactional
	public String insert(LoanRule loanRule) {
		String result = "success";
		String name=loanRule.getName();
		Integer repaydateRule =loanRule.getRepaydateRule();
		loanRule.setName(null);
		loanRule.setRepaydateRule(null);
		LoanRuleVO loanRuleVo=LoanRuleAssembler.transferModel2VO(loanRule);
		loanRuleVo.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
		loanRuleVo.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		List<LoanRule>  loanRuleList=repayDateRuleDao.findListByVo(loanRuleVo);
		if(CollectionUtil.isNotEmpty(loanRuleList)){
			result = "相同类型的规则已经存在";
			return result;
			
		}
		loanRule.setName(name);
		loanRule.setRepaydateRule(repaydateRule);
		loanRule.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
		loanRule.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		loanRule.setIsExecuted(EnumConstants.YesOrNo.NO.getValue());	
		repayDateRuleDao.insert(loanRule);
		//插入系统日志  操作模块 12 ，操作类型 1201，日志内容 规则名+规则id +新增
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.REPAYDATE_RULE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_REPAY_RULE.getValue());
		sysLog.setRemark("规则名:"+loanRule.getName()+"规则ID"+loanRule.getId()+"新增");
		sysLogService.insert(sysLog);
		
		return result;
	}
	/***
	 * 根据ID获取规则
	 * @param ruleId
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#get(java.lang.Long)
	 */
	@Override
	public LoanRule get(Long  ruleId) {
		return repayDateRuleDao.get(ruleId);
	}
	/***
	 * 
	 * <pre>
	 * 根据参数查询规则
	 * </pre>
	 *
	 * @param loanRuleVo
	 * @return
	 */
	@Override
	public List<LoanRule> findRuleByVO(LoanRuleVO loanRuleVo){
		return repayDateRuleDao.findListByVo(loanRuleVo);
	}
	/***
	 * 
	 * <pre>
	 * 根据参数查询规则
	 * </pre>
	 *  状态为 未删除 可执行
	 * @param loanRuleVo
	 * @return
	 */
	@Override
	public List<LoanRule> findRuleByParams(LoanRuleVO loanRuleVo){
		loanRuleVo.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		loanRuleVo.setIsExecuted(EnumConstants.YesOrNo.YES.getValue());
		return repayDateRuleDao.findListByVo(loanRuleVo);
	}
	/***
	 * 
	 * <pre>
	 * 更新规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	/** 
	 * @param LoanRuleVo
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#update(com.ezendai.credit2.rule.vo.LoanRuleVO)
	 */
	@Override
	@Transactional
	public int update(LoanRuleVO LoanRuleVo){		
		
		int ret = repayDateRuleDao.update(LoanRuleVo);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.REPAYDATE_RULE.getValue());		
		sysLog.setOptType(EnumConstants.OptionType.EDIT_REPAY_RULE.getValue());
		sysLog.setRemark("规则名："+LoanRuleVo.getName()+"规则ID"+LoanRuleVo.getId()+"还款日规则"+LoanRuleVo.getRepaydateRule()+"修改");		
		sysLogService.insert(sysLog);
		return ret;
	}
	/***
	 * 
	 * <pre>
	 * 设置还款日规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	/** 
	 * @param LoanRuleVo
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#update(com.ezendai.credit2.rule.vo.LoanRuleVO)
	 */
	@Override
	@Transactional
	public int configRepayDateRule(LoanRuleVO loanRuleVo){

		//无效清空设置时间
		if (1 == loanRuleVo.getIsExecuted()) {
			loanRuleVo.setExecuteTime(new Date());
		} else {
			loanRuleVo.setExecuteTimeNull(true);
		}		
		int ret = repayDateRuleDao.update(loanRuleVo);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.REPAYDATE_RULE.getValue());		
		sysLog.setOptType(EnumConstants.OptionType.SET_REPAY_RULE.getValue());
		if(loanRuleVo.getIsExecuted()==1){
				sysLog.setRemark("规则名: "+loanRuleVo.getName()+" 规则ID "+loanRuleVo.getId()+" 设为有效 "+"设置");
		}else{
				sysLog.setRemark("规则名："+loanRuleVo.getName()+" 规则ID "+loanRuleVo.getId()+" 设为无效 "+"设置");
		}
		sysLogService.insert(sysLog);
		return ret;
	}
	/***
	 * 
	 * <pre>
	 * 根据List<Long> idList 删除规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	@Override
	@Transactional
	public void deleteByIdList(LoanRuleVO LoanRuleVo){
		repayDateRuleDao.deleteByIdList(LoanRuleVo);
			//插入系统日志 
			if(LoanRuleVo!=null&& CollectionUtil.isNotEmpty(LoanRuleVo.getIdList())){
				for(Long id:LoanRuleVo.getIdList()){
					SysLog sysLog = new SysLog();			
					sysLog.setOptModule(EnumConstants.OptionModule.REPAYDATE_RULE.getValue());
					sysLog.setOptType(EnumConstants.OptionType.DELETE_REPAY_RULE.getValue());
					sysLog.setRemark("规则ID "+id+" 逻辑删除");
					sysLogService.insert(sysLog);
				}
			}	
	}
	
}
