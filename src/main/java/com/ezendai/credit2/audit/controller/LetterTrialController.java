package com.ezendai.credit2.audit.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.LoanTotal;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.LoanStatus;
import com.ezendai.credit2.master.enumerate.EnumConstants.ProductList;
import com.ezendai.credit2.master.enumerate.EnumConstants.ProductType;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

/**
 * 
 * 项目名称：credit2-main 类名称：LetterTrialController 类描述： 助学贷信审分单 创建人：liboyan
 * 创建时间：2015年9月16日 下午5:14:35 修改人：liboyan 修改时间：2015年9月16日 下午5:14:35 修改备注：
 * 
 * @version
 * 
 */

@Controller
@RequestMapping("/letterTrial")
public class LetterTrialController extends BaseController {

	@Autowired
	ProductService productService;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	LoanService loanService;
	
	
	private static final Logger logger = Logger.getLogger(LetterTrialController.class);
	
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_ID});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "audit/letterTrial";
	}

	/***
	 * 
	 * <pre>
	 * 显示列表
	 * </pre>
	 *
	 * @param model
	 * @return
	 */

	@RequestMapping("/loanList")
	@ResponseBody
	public Map<String, Object> loanList(LoanVO loanVO, int rows, int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("LOAN.CREATED_TIME");
		pager.setSort("ASC");
		loanVO.setPager(pager);
		List<Integer> statusList = new ArrayList<Integer>();
		if (loanVO.getStatus() == null) {// 默认查询的状态
			statusList.add(EnumConstants.LoanStatus.初审待分配.getValue());
			statusList.add(EnumConstants.LoanStatus.门店重提.getValue());
			loanVO.setStatusList(statusList);
			loanVO.setFirstTrialId(Long.valueOf(0));
		}else if(loanVO.getStatus() == 0){
			loanVO.setStatus(null);
			statusList.add(EnumConstants.LoanStatus.初审待分配.getValue());
			statusList.add(EnumConstants.LoanStatus.初审中.getValue());
			statusList.add(EnumConstants.LoanStatus.门店重提.getValue());
			loanVO.setStatusList(statusList);
		}
		// 查询
		loanVO.setProductId(ProductList.STUDENT_LOAN.getValue());
		loanVO.setProductType(ProductType.SE_LOAN.getValue());
		pager = loanService.findWithPg(loanVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	/***
	 * 
	 * <pre>
	 * 初审单子分派
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/reassign")
	@ResponseBody
	public HashMap<String,Object> reassign(@RequestParam(value = "datas[]") long[] datas){
		HashMap<String, Object> map=new HashMap<String, Object>();
		if(datas.length > 0){
			LoanVO loanVO =new LoanVO();
			loanVO.setFirstTrialId(datas[0]);
			List<Long> idList = new ArrayList<Long>();
			for (int i = 1; i < datas.length; i++) {
				idList.add(datas[i]);
			}
			loanVO.setIdList(idList);
			String msg = loanService.updateLoanByIdList(loanVO);
			map.put("msg", msg);
		}else{
			map.put("msg", null);
		}
		return map;
	}
	/***
	 * 
	 * <pre>
	 * 加载助学审核员
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getFirstTrials")
	@ResponseBody
	public List<SysUser> getFirstTrials(){
		SysUserVO  sysUserVo=new SysUserVO();
		sysUserVo.setUserType(UserType.APPROVE.getValue());
		 List<SysUser> userList=sysUserService.AssessorList(sysUserVo);
		SysUser sysUser = new SysUser();
		sysUser.setId(null);
		sysUser.setName("全部");
		userList.add(0,sysUser);
		return userList;
	}
	/***
	 * 
	 * <pre>
	 * 加载助学审核员检查接单状态是否开启
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getFirstTrials2")
	@ResponseBody
	public List<SysUser> getFirstTrials2(){
		SysUserVO  sysUserVo=new SysUserVO();
		sysUserVo.setUserType(UserType.APPROVE.getValue());
		//初审员是否开启接单状态1：开启、0:关闭
		sysUserVo.setAcceptAuditTask(1);
		 List<SysUser> userList=sysUserService.AssessorList(sysUserVo);
		return userList;
	}
	/**
	 * 审核员当天未处理借款
	 * */
	@RequestMapping("/loanStatusList")
	@ResponseBody
	public  List<LoanTotal> loanStatusList(){
		LoanVO vo =new LoanVO();
		vo.setProductId(ProductList.STUDENT_LOAN.getValue());
		vo.setProductType(ProductType.SE_LOAN.getValue());
		List<Integer> list = new ArrayList<Integer>();
		list.add(LoanStatus.初审中.getValue());
		list.add(LoanStatus.门店重提.getValue());
		list.add(LoanStatus.终审退回初审.getValue());
		list.add(LoanStatus.初审挂起.getValue());
		vo.setStatusList(list);
		//未处理单子
		List<Loan>  loanList = loanService.findListByVO(vo);
		SysUserVO  sysUserVo=new SysUserVO();
		sysUserVo.setUserType(UserType.APPROVE.getValue());
		 List<SysUser> userList=sysUserService.AssessorList(sysUserVo);
		 List<LoanTotal> loanTotalList =new ArrayList<LoanTotal>();
		for (int i=0;i<userList.size();i++) {
			LoanTotal  total=new LoanTotal();
			total.setFirstTrialId(userList.get(i).getId());
			total.setName(userList.get(i).getName());
			long temp=0;
			for (Loan loan : loanList) {
				if(userList.get(i).getId().equals(loan.getFirstTrialId())){
					temp += 1;
				}
			}
			total.setUntreated(temp);
			loanTotalList.add(total);
		}
		//已处理单子
		Date date = new Date(System.currentTimeMillis());
		  String defaultFormatDay = DateUtil.defaultFormatDay(date);
		  LoanVO  loanVo =  new LoanVO();
		  try {
			  loanVo.setAuditDateStart(DateUtil.strToDate(defaultFormatDay + " 00:00:00",DateUtil.default_pattern));
			  loanVo.setAuditDateEnd(DateUtil.strToDate(defaultFormatDay + " 23:59:59",DateUtil.default_pattern));
		  } catch (ParseException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		
		loanVo.setStatus(LoanStatus.终审中.getValue());
		List<Loan>  loanList2 = loanService.alreadyProcessedList(loanVo);
		 for (LoanTotal loanTotal : loanTotalList) {
			long temp=0;
			for (Loan loan : loanList2) {
				if(loanTotal.getFirstTrialId().equals(loan.getFirstTrialId())){
					temp += 1;
				}
			}
			loanTotal.setSettled(temp);
		}
		return loanTotalList;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/***
	 * 
	 * <pre>
	 * 加载助学审核员
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getFirstTrials3")
	@ResponseBody
	public List<SysUser> getFirstTrials3(){
		SysUserVO  sysUserVo=new SysUserVO();
		sysUserVo.setUserType(UserType.APPROVE.getValue());
//		//初审员是否开启接单状态1：开启、0:关闭
//		sysUserVo.setAcceptAuditTask(1);
		 List<SysUser> userList=sysUserService.AssessorList(sysUserVo);
		return userList;
	}
}
