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
 * 罚息减免
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PenaltyReduceController.java, v 0.1 2014年12月10日 上午11:39:02 00221921 Exp $
 */
@Controller
@RequestMapping("/after/penaltyReduce")
public class PenaltyReduceController extends BaseController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private PenaltyReduceService penaltyReduceService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private SysParameterService sysParameterService;
	
	private static final Logger logger = Logger.getLogger(PenaltyReduceController.class);

	
	@RequestMapping("/penaltyReduceMain")
	public String init(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.LOAN_STATUS ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/penaltyReduce/penaltyReduce";
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
	 * 获取罚息减免列表
	 * </pre>
	 *
	 * @param loanVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPenaltyReducePage")
	@ResponseBody
	public Map<String, Object> getPenaltyReducePage(LoanVO loanVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<PenaltyReduce> penaltyReduceList = new ArrayList<PenaltyReduce>();
		if (StringUtils.isNotEmpty(loanVO.getPersonName()) || StringUtils.isNotEmpty(loanVO.getPersonIdnum()) || StringUtils.isNotEmpty(loanVO.getPersonMobilePhone())
				|| StringUtils.isNotEmpty(loanVO.getFrameNumber())) {
			loanVO.setStatus(EnumConstants.LoanStatus.逾期.getValue());
			// 客户经理选择"全部"
			if (loanVO.getCrmId() != null && loanVO.getCrmId().compareTo(0L) == 0) {
				loanVO.setCrmId(null);
			}
			if(loanVO.getFrameNumber() != null && !"".equals(loanVO.getFrameNumber())){
				loanVO.setFrameNumber(loanVO.getFrameNumber().trim());
			}

			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("AUDIT_DATE");
			p.setSort("ASC");
			loanVO.setPager(p);

			Pager loanPager = loanService.specialRepaymentFindWithPG(loanVO);
			List<Loan> loanList = loanPager.getResultList();
			PenaltyReduce penaltyReduce;
			// 将Loan集合转化为PenaltyReduce集合
			for (Loan loan : loanList) {
				penaltyReduce = new PenaltyReduce();
				penaltyReduce.setLoanId(loan.getId());
				if (loan.getPerson() != null) {
					penaltyReduce.setName(loan.getPerson().getName());
					penaltyReduce.setIdNum(loan.getPerson().getIdnum());
				}
				if(loan.getVehicle().getFrameNumber() != null){
					penaltyReduce.setFrameNumber(loan.getVehicle().getFrameNumber());
				}
				//产品类型
				penaltyReduce.setProductId(loan.getProductId());
				// 借款类型
				penaltyReduce.setProductType(loan.getProductType().longValue());
				// 合同金额
				penaltyReduce.setPactMoney(loan.getPactMoney());
				// 期限
				penaltyReduce.setTime(loan.getTime());
				penaltyReduce.setStatus(loan.getStatus());
				// 展期期次
				penaltyReduce.setExtensionTime(loan.getExtensionTime());
				// 获取该借款当天申请罚息减免的金额
				BigDecimal amount = specialRepaymentService.getReliefOfFine(DateUtil.getToday(), loan.getId());
				// 减免金额
				penaltyReduce.setAmount(amount);
				// 该借款没有处于"申请"状态的 [罚息减免] 申请
				if (amount != null && amount.compareTo(BigDecimal.ZERO) == 0) {
					penaltyReduce.setOperations("|申请");
				}
				// 该借款有处于"申请"状态的 [罚息减免] 申请
				else if (amount != null
						&& amount.compareTo(BigDecimal.ZERO) > 0) {
					// 如果当前用户是该笔借款 [罚息减免] 的申请人
					if (currUserIsProposer(loan.getId(), DateUtil.getToday())) {
						penaltyReduce.setOperations("|取消申请");
					} else {
						penaltyReduce.setOperations("|不可操作");
					}
				}
				setPenaltyReduceMoney(loan.getId(), penaltyReduce);

				penaltyReduceList.add(penaltyReduce);
			}
			result.put("total", loanPager.getTotalCount());
			result.put("rows", penaltyReduceList);
		} else {
			result.put("total", 0);
			result.put("rows", penaltyReduceList);
		}
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 判断当前用户是否是该借款罚息减免的申请人
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
		// 获取loanId指定借款的罚息减免申请
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
		Map<String, Object> resultMap = penaltyReduceService.batchUpload(request, response);
		String result = resultMap.get("ErrorMsg").toString();
		if (!StringUtils.isEmpty(result)) {
			throw new ExcelException(resultMap.get("ErrorMsg").toString());
		}
		return null;
	}
	
	/**
	 * 
	 * <pre>
	 * 设置罚息减免查询结果实体类的逾期金额(逾期应还，逾期违约金，逾期本金，逾期利息，逾期管理费，期末预收)
	 * </pre>
	 *
	 * @param loanId
	 * @param penaltyReduce
	 */
	private void setPenaltyReduceMoney(Long loanId, PenaltyReduce penaltyReduce) {
		Date nowDate = DateUtil.getToday();
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loanId);
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			
			// 逾期应还
			BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, nowDate);
			penaltyReduce.setOverdueAmount(overdueAmount);
			
			// 逾期违约金(罚息)
			BigDecimal fine = repayService.getFine( repaymentPlanList, nowDate);
			penaltyReduce.setFine(fine);
			
			// 逾期应还总额
			penaltyReduce.setOverdueAllAmount(overdueAmount.add(fine));
			
			// 逾期本金
			BigDecimal principal = repayService.getOverduePrincipal(repaymentPlanList, nowDate);
			penaltyReduce.setPrincipal(principal);
			
			// 逾期利息
			BigDecimal interest = repayService.getOverdueInterest(repaymentPlanList, nowDate);
			penaltyReduce.setInterest(interest);
			
			// 逾期费用
				// 逾期管理费
			BigDecimal managementFee = repayService.getOverdueManageFee(repaymentPlanList, nowDate);
				// 车贷短期(评估费+咨询费+风险金)
			BigDecimal overdueOtherFee = repayService.getOverdueOtherFee(repaymentPlanList, nowDate);
			penaltyReduce.setManagementFee(managementFee.add(overdueOtherFee));
		}
		
		// 期末预收
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		penaltyReduce.setAccAmount(accAmount);
	}
	
	/**
	 * 
	 * <pre>
	 * 验证罚息减免申请金额的有效性
	 * </pre>
	 *
	 * @param amount 申请金额
	 * @param overdueAmount 逾期应还
	 * @param fine 罚息
	 * @return
	 */
	@RequestMapping("/checkValidAmount")
	@ResponseBody
	public Map<String, Object> checkValidAmount(BigDecimal amount, BigDecimal overdueAllAmount, BigDecimal fine) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(amount != null && overdueAllAmount != null && fine != null) {
			if(amount.compareTo(BigDecimal.ZERO) <= 0) {
				resultMap.put("result", "error");
				resultMap.put("message", "请输入大于 0 的数字");
				return resultMap;
			}
			// 申请金额大于逾期应还
			else if(amount.compareTo(overdueAllAmount) > 0) {
				resultMap.put("result", "error");
				resultMap.put("message", "申请金额不得大于应还总额");
				return resultMap;
			}
			// 申请金额小于等于罚息
			else if(amount.compareTo(fine) <= 0) {
				resultMap.put("result", "success");
				return resultMap;
			}
			// 申请金额大于罚息，小于等于应还总额
			else if(amount.compareTo(fine) > 0 && amount.compareTo(overdueAllAmount) <= 0) {
				resultMap.put("result", "confirm");
				resultMap.put("message", "减免金额大于罚息，请确认！");
				return resultMap;
			}
		} else {
			resultMap.put("result", "error");
			resultMap.put("message", "该借款数据不正规");
			return resultMap;
		} 
		
		return resultMap;
	}
	
	/**
	 * 
	 * <pre>
	 * 申请罚息减免
	 * </pre>
	 *
	 * @param loanId
	 * @param amount
	 * @return
	 */
	@RequestMapping("/submitPenaltyReduce")
	@ResponseBody
	public String submitPenaltyReduce(Long loanId, BigDecimal amount, Integer extensionTime) {
		return specialRepaymentService.submitPenaltyReduce(loanId, amount, extensionTime);
	}
	
	/** 
	 * 
	 * <pre>
	 * 取消罚息减免
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/cancelPenaltyReduce")
	@ResponseBody
	public String cancelPenaltyReduce(Long loanId) {
		return specialRepaymentService.cancelPenaltyReduce(loanId);
	}
	
	
	/**
	 * <pre>
	 * 批量导入小企业贷罚息减免数据
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importData", method = RequestMethod.POST)
	public String importEntData(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if (!ipValidationCheck) {
				result= "发起请求的IP地址错误!";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			Map<String, Object> resultMap = penaltyReduceService.batchUpload(request, response);
			result = resultMap.get("ErrorMsg").toString();	
			if(!StringUtils.isEmpty(result))
			{
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
			}
		}
		catch (Exception e) {
			logger.error("调用 importData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("importData result: " + result);
		return result;
	}
}
