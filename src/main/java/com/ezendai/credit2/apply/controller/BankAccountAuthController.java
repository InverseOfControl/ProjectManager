/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.assembler.BankAccountAssembler;
import com.ezendai.credit2.apply.model.AccountAuthLog;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.AccountAuthLogService;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.assembler.PersonBankAssembler;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.service.IAuthService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardAuthReqVo;

/**
 * <pre>
 * 变更银行卡信息
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BankAccountController.java, v 0.1 2015年3月9日 下午4:07:56 00221921 Exp $
 */

@Controller
@RequestMapping("/apply/authBankAccount")
public class BankAccountAuthController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private ProductService productService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private BankService bankService;
	@Autowired
	private BusinessLogService businessLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private IAuthService iAuthService;
	@Autowired
	private AccountAuthLogService accountAuthLogService;
	@Autowired
	private SysParameterService sysParameterService;

	/**
	 * 
	 * <pre>
	 * 银行卡变更主界面
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String bankAccountAuthList(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "apply/bankAccountAuthList";
	}
	
	/**
	 * <pre>
	 * 显示列表	
	 * </pre>
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBankAccountAuthPage")
	@ResponseBody
	public Map<String, Object> getBankAccountAuthPage(PersonBankVO personBankVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("loanId");
			p.setSort("DESC");
			personBankVO.setPager(p);
//			personBankVO.setAccountAuthType(0);
			personBankVO.setHasTppType("YES");
			Pager personBankPager = personBankService.findWithPgExtension(personBankVO);
			List<PersonBank> personBankList = personBankPager.getResultList();
			result.put("total", personBankPager.getTotalCount());
			result.put("rows", personBankList);
		return result;
	}
	
	@RequestMapping("/doAuthBankAccount")
	@ResponseBody
	public Map<String, Object> doAuthBankAccount(PersonBankVO personBankVO){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		List<PersonBank> personBank=personBankService.findListByVOExtension(personBankVO);
		BankCardAuthReqVo bankCardAuthReqVoGene=null;
		if(personBank!=null && personBank.size()!=0){
			bankCardAuthReqVoGene=new BankCardAuthReqVo();
//			SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE.name());
			bankCardAuthReqVoGene.setBizSysNo(BizSys.ZENDAI_2018_SYS.getCode());//业务系统编码
//			bankCardAuthReqVoGene.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());//信息类别编码
			bankCardAuthReqVoGene.setInfoCategoryCode("10001");
			bankCardAuthReqVoGene.setBankCardType("1");//银行卡类型
			bankCardAuthReqVoGene.setBankCardNo(personBank.get(0).getAccount());//银行卡号
			bankCardAuthReqVoGene.setIdType("0");//证件类型
			bankCardAuthReqVoGene.setIdNo(personBank.get(0).getPersonIdnum());//证件号
			bankCardAuthReqVoGene.setRealName(personBank.get(0).getPersonName());//客户名
		}else{
			resultMap.put("success", "false");
			resultMap.put("message", "找不到对应的信息");
			return resultMap;
		}
		BankCardAuthReqVo bankCardAuthReqVo=bankCardAuthReqVoGene;
		if(bankCardAuthReqVo!=null){
			Date sendTime=new Date();
			Response response =  iAuthService.bankCardAuth(bankCardAuthReqVo);
			//记录银行卡验证信息
			AccountAuthLog accountAuthLog = new AccountAuthLog();
			accountAuthLog.setBankId(personBank.get(0).getBankId());
			accountAuthLog.setCardNo(bankCardAuthReqVo.getBankCardNo());
			accountAuthLog.setOperatorId(ApplicationContext.getUser().getId());
			accountAuthLog.setSendMsg(JSONObject.toJSONString(bankCardAuthReqVo));
			accountAuthLog.setReturnCode(response.getCode());
			accountAuthLog.setReturnMsg(response.getMsg());
			accountAuthLog.setSendTime(sendTime);
			accountAuthLog.setName(bankCardAuthReqVoGene.getRealName());
			accountAuthLog.setLoanId(personBank.get(0).getLoanId());
			accountAuthLogService.insert(accountAuthLog);
			if(response.getCode().equals("000000")){
		        BankAccountVO personBankAccount = new BankAccountVO();
		        personBankAccount.setId(personBank.get(0).getBankAccountId());
		        personBankAccount.setAccountAuthType(1);
		        bankAccountService.update(personBankAccount);
				resultMap.put("success", "true");
				resultMap.put("message", "成功");
			}else{
				resultMap.put("success", "false");
				resultMap.put("message", response.getMsg());
			}
		}
		return resultMap;
	}
	
}
