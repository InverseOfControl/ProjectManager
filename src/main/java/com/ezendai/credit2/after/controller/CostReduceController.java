/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.service.PenaltyReduceService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.view.CostReduce;
import com.ezendai.credit2.after.view.PenaltyReduce;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.ExcelException;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 费用减免
 * </pre>
 *
 * @author liuhy
 * @version $Id: CostReduceController.java, v 0.1 2016年1月14日 上午13:39:02 00237489 Exp $
 */
@Controller
@RequestMapping("/after/costReduce")
public class CostReduceController extends BaseController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private PenaltyReduceService penaltyReduceService;
	
	
	private static final Logger logger = Logger.getLogger(CostReduceController.class);

	
	@RequestMapping("/costReduceMain")
	public String init(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.LOAN_STATUS ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/costReduce/costReduce";
	}
	
	/**
	 * 
	 * <pre>
	 * 获取所有的客户经理
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getAllCrmList")
	@ResponseBody
	public List<SysUser> getAllCrmList() {
		List<SysUser> allCrmList = new ArrayList<SysUser>();
		SysUser all = new SysUser();
		all.setId(0L);
		all.setName("全部");
		allCrmList.add(all);
		// 获取所有客户经理
		List<SysUser> crmList = sysUserService.findAllCrmList();
		allCrmList.addAll(crmList);
		return allCrmList;
	}
	
	/**
	 * 
	 * <pre>
	 * 获取费用减免列表
	 * </pre>
	 *
	 * @param specialRepaymentVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCostReducePage")
	@ResponseBody
	public Map<String, Object> getCostReducePage(SpecialRepaymentVO specialRepaymentVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<CostReduce> costReduceList = new ArrayList<CostReduce>();
		if (StringUtils.isNotEmpty(specialRepaymentVO.getName()) || StringUtils.isNotEmpty(specialRepaymentVO.getFrameNumber()) || StringUtils.isNotEmpty(specialRepaymentVO.getIdnum()) || StringUtils.isNotEmpty(specialRepaymentVO.getMobilePhone())) {
//			// 客户经理选择"全部"
			if (specialRepaymentVO.getCrmId() != null && specialRepaymentVO.getCrmId().compareTo(0L) == 0) {
				specialRepaymentVO.setCrmId(null);
			}
			if(specialRepaymentVO.getFrameNumber() != null && !"".equals(specialRepaymentVO.getFrameNumber())){
				specialRepaymentVO.setFrameNumber(specialRepaymentVO.getFrameNumber().trim());
			}
			specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
			specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue());
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("REQUEST_DATE");
			p.setSort("ASC");
			specialRepaymentVO.setPager(p);
			Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtExtend(specialRepaymentVO);
			List<SpecialRepayment> specialRepaymentList=specialRepaymentPager.getResultList();
			CostReduce costReduce;
			// 将SpecialRepayment集合转化为CostReduce集合
			for (SpecialRepayment specialRepayment : specialRepaymentList) {
				costReduce = new CostReduce();
				costReduce.setLoanId(specialRepayment.getLoanId());
				if (specialRepayment.getName() != null) {
					costReduce.setName(specialRepayment.getName());
					costReduce.setIdNum(specialRepayment.getIdnum());
				}
				if(specialRepayment.getVehicle().getFrameNumber() != null){
					costReduce.setFrameNumber(specialRepayment.getVehicle().getFrameNumber());
				}
				//产品类型
				costReduce.setProductId(specialRepayment.getProductId());
				// 借款类型
				costReduce.setProductType(specialRepayment.getProductType().longValue());
				// 合同金额
				costReduce.setPactMoney(specialRepayment.getPactMoney());
				// 期限
				costReduce.setTime(specialRepayment.getTime());
				//借款状态
				costReduce.setStatus(specialRepayment.getLoanStatus());
				// 展期期次
				costReduce.setExtensionTime(specialRepayment.getExtensionTime());
			
				
				// 获取该借款当天申请减免的金额
				BigDecimal amount = specialRepaymentService.getReliefOfFine(DateUtil.getToday(), specialRepayment.getLoanId());
				// 减免金额
				costReduce.setAmount(amount);
				// 该借款没有处于"申请"状态的 [费用减免] 申请
				if (amount != null && amount.compareTo(BigDecimal.ZERO) == 0) {
					costReduce.setOperations("|申请");
				}
				// 该借款有处于"申请"状态的 [费用减免] 申请
				else if (amount != null
						&& amount.compareTo(BigDecimal.ZERO) > 0) {
					// 如果当前用户是该笔借款 [费用减免] 的申请人
					if (currUserIsProposer(specialRepayment.getLoanId(), DateUtil.getToday())) {
						costReduce.setOperations("|取消申请");
					} else {
						costReduce.setOperations("|不可操作");
					}
				}
				setCostReduceMoney(specialRepayment.getLoanId(), costReduce);

				costReduceList.add(costReduce);
			}
			result.put("total", specialRepaymentPager.getTotalCount());
			result.put("rows", costReduceList);
		} else {
			result.put("total", 0);
			result.put("rows", costReduceList);
		}
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 判断当前用户是否是该借款费用减免的申请人
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	private boolean currUserIsProposer(Long loanId, Date currDate) {
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setLoanId(loanId);
		specialRepaymentVO.setRequestDate(currDate);
		specialRepaymentVO.setType(EnumConstants.SpecialRepaymentType.REDUCE_PENALTY.getValue());
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		// 获取loanId指定借款的费用减免申请
		List<SpecialRepayment> specialRepaymentList = specialRepaymentService.findListByVo(specialRepaymentVO);
		if (specialRepaymentList != null && specialRepaymentList.size() == 1) {
			Long creatorId = specialRepaymentList.get(0).getCreatorId();
			Long userId = ApplicationContext.getUser().getId();
			if (userId.compareTo(creatorId) == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	/**
	 * 
	 * <pre>
	 * 设置费用减免查询结果实体类的剩余金额(剩余本金、剩余利息、剩余本息和、违约金)
	 * </pre>
	 *
	 * @param loanId
	 * @param penaltyReduce
	 */
	private void setCostReduceMoney(Long loanId, CostReduce costReduce) {
		Date nowDate = DateUtil.getToday();
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan( loanId);
		List<RepaymentPlan> repaymentPlanList2 = repayService.getAllInterestOrLoan(nowDate, loanId);
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			/**当期剩余还款利息*/
//			BigDecimal curRemainingInterestAmtTotal=BigDecimal.ZERO;
			
			/**当期剩余本金*/
			BigDecimal curRemainingPrincipalTotal=BigDecimal.ZERO;
			
			//当前期限
			Integer currTerm = repayService.getCurrTerm(repaymentPlanList2, nowDate);
			costReduce.setCurrNum(currTerm);
			
			/**剩余金额汇总*/
			for(int i=0;i<repaymentPlanList.size();i++){
				if(repaymentPlanList.get(i).getCurNum()>=currTerm){
					curRemainingPrincipalTotal=curRemainingPrincipalTotal.add(repaymentPlanList.get(i).getCurRemainingPrincipal());
				}
//				curRemainingInterestAmtTotal=curRemainingInterestAmtTotal.add(repaymentPlan.getCurRemainingInterestAmt());
			}
			//违约金
			BigDecimal penalty=repayService.getPenalty(repaymentPlanList2, nowDate);
			costReduce.setPenalty(penalty);
			// 剩余本金
			costReduce.setRemainingPrincipal(curRemainingPrincipalTotal);
			
			BigDecimal currInterest=repayService.getCurrInterest(repaymentPlanList2, nowDate);
			// 剩余利息
			costReduce.setCurRemainingInterestAmt(currInterest);
			
			// 剩余本息和
			costReduce.setCurRemainingAmount(currInterest.add(curRemainingPrincipalTotal));
			
		
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 验证费用减免申请金额的有效性
	 * </pre>
	 *
	 * @param amount 申请金额
	 * @return
	 */
	@RequestMapping("/checkValidAmount")
	@ResponseBody
	public Map<String, Object> checkValidAmount(BigDecimal amount) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(amount != null) {
			if(amount.compareTo(BigDecimal.ZERO) <= 0) {
				resultMap.put("result", "error");
				resultMap.put("message", "请输入大于 0 的数字");
				return resultMap;
			}else{
				resultMap.put("result", "success");
				return resultMap;
			}
//			// 申请金额大于逾期应还
//			else if(amount.compareTo(overdueAllAmount) > 0) {
//				resultMap.put("result", "error");
//				resultMap.put("message", "申请金额不得大于应还总额");
//				return resultMap;
//			}
//			// 申请金额小于等于罚息
//			else if(amount.compareTo(fine) <= 0) {
//				resultMap.put("result", "success");
//				return resultMap;
//			}
//			// 申请金额大于罚息，小于等于应还总额
//			else if(amount.compareTo(fine) > 0 && amount.compareTo(overdueAllAmount) <= 0) {
//				resultMap.put("result", "confirm");
//				resultMap.put("message", "减免金额大于罚息，请确认！");
//				return resultMap;
//			}
		} else {
			resultMap.put("result", "error");
			resultMap.put("message", "该借款数据不正规");
			return resultMap;
		} 
	}
	
	/**
	 * 
	 * <pre>
	 * 申请费用减免
	 * </pre>
	 *
	 * @param loanId
	 * @param amount
	 * @return
	 */
	@RequestMapping("/submitCostReduce")
	@ResponseBody
	public String submitCostReduce(Long loanId, BigDecimal amount, Integer extensionTime) {
		return specialRepaymentService.submitPenaltyReduce(loanId, amount, extensionTime);
	}
	
	/** 
	 * 
	 * <pre>
	 * 取消费用减免
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/cancelCostReduce")
	@ResponseBody
	public String cancelCostReduce(Long loanId) {
		return specialRepaymentService.cancelPenaltyReduce(loanId);
	}
	
	/**
	 * 
	 * <pre>
	 * 批量导入
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = penaltyReduceService.batchCostUpload(request, response);
		String result = resultMap.get("ErrorMsg").toString();
		if (!StringUtils.isEmpty(result)) {
			throw new ExcelException(resultMap.get("ErrorMsg").toString());
		}
		return null;
	}
}
