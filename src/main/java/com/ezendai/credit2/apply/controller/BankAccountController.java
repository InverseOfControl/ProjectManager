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
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.assembler.PersonBankAssembler;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
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
@RequestMapping("/apply/changeBankAccount")
public class BankAccountController extends BaseController {
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
	private SysParameterService sysParameterService;
	@Autowired
	private AccountAuthLogService accountAuthLogService;
	@Autowired
	private IAuthService iAuthService;
	@Autowired
	private PersonService personService;
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
	public String bankAccountList(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		//是否验证银行卡，到参数表总查询，0否1是
		return "apply/bankAccountList";
	}
	
	/**
	 * <pre>
	 * 显示列表	客服只能看自己所在门店的客户信息，事业部可看所有客户信息
	 * </pre>
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBankAccountPage")
	@ResponseBody
	public Map<String, Object> getBankAccountPage(PersonBankVO personBankVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		// 输入查询条件(姓名or身份证号)
		if(StringUtils.isNotBlank(personBankVO.getPersonName()) || StringUtils.isNotBlank(personBankVO.getPersonIdnum())) {
			Long userId = ApplicationContext.getUser().getId();
			// 客服、经理、副理只能查询该门店的单子
			if(ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) == 0
					|| ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.STORE_MANAGER.getValue()) == 0
					|| ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) == 0) {
						List<Long> baseAreaList = sysUserService.getSalesDeptIdByUserId(userId);
						// 客服，依据营业网点筛选
						personBankVO.setSalesDeptIdList(baseAreaList);
					} 
			// 事业部人员，只能查询所负责产品种类(车贷or小企贷)
			else if (ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.AUDIT.getValue()) == 0) {
						List<Product> productList = productService.findProductTypeByUserId(userId);
						List<Integer> productTypeList = new ArrayList<Integer>();
						for(Product p : productList) {
							productTypeList.add(p.getProductType());
						}
						// 事业部，依据产品类型筛选
						personBankVO.setProductTypeList(productTypeList);
			}
			else if (ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue()) == 0) {
				List<Product> productList = productService.findProductTypeByUserId(userId);
				List<Integer> productTypeList = new ArrayList<Integer>();
				for(Product p : productList) {
					productTypeList.add(p.getProductType());
				}
				//渠道到专员（业务支持岗）
				personBankVO.setProductTypeList(productTypeList);
				statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
				statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
				statusList.add(EnumConstants.LoanStatus.合同确认退回.getValue());
				statusList.add(EnumConstants.LoanStatus.展期合同确认退回.getValue());
				statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
				statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
				statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
				statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
				statusList.add(EnumConstants.LoanStatus.预结清.getValue());
				statusList.add(EnumConstants.LoanStatus.结清.getValue());
				statusList.add(EnumConstants.LoanStatus.坏账.getValue());
				statusList.add(EnumConstants.LoanStatus.取消.getValue());
	}
			personBankVO.setStatusList(statusList);
			
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("loanId");
			p.setSort("DESC");
			personBankVO.setPager(p);
			
			Pager personBankPager = personBankService.findWithPgExtension(personBankVO);
			List<PersonBank> personBankList = personBankPager.getResultList();
			
			result.put("total", personBankPager.getTotalCount());
			result.put("rows", personBankList);
		} 
		// 未输入查询条件,即初始页面，不显示任何数据
		else {
			result.put("total", 0);
			result.put("rows", null);
		}
	
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 更改银行卡
	 * </pre>
	 *
	 * @param bankAccountId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/initAccount/{personBankId}")
	public String initBankAccount(@PathVariable String personBankId, ModelMap modelMap) {
		if (StringUtils.isBlank(personBankId)) {
			return "apply/changeBankAccount";
		}
		PersonBank personBank = personBankService.getExtension(Long.valueOf(personBankId));
		//获取银行卡开关
		String isverificationBank = sysParameterService.getByCodeNoCache("IS_VERIFICATION_BANK_CARD").getParameterValue();
		modelMap.put("vo", personBank);
		//是否验证银行卡，到参数表总查询，0否1是
		modelMap.put("isverificationBank", isverificationBank);
				
		return "apply/changeBankAccount";
	}
	
	@RequestMapping("/changeAccount")
	@ResponseBody
	public Map<String, Object> changeAccount(BankAccountVO bankAccountVO) {
		Map< String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isBlank(bankAccountVO.getAccount())) {
			result.put("success", false);
			result.put("msg", "账号不能为空");
			return result;
		} else if(StringUtils.isBlank(bankAccountVO.getBankId())) {
			result.put("success", false);
			result.put("msg", "银行不能为空");
			return result;
		}else if(StringUtils.isBlank(bankAccountVO.getBranchName())) {
			result.put("success", false);
			result.put("msg", "网点不能为空");
			return result;
		} else {
			PersonBankVO personBankVO = new PersonBankVO();
			personBankVO.setPersonId(Long.valueOf(bankAccountVO.getPersonId()));
			
			// 如果要更改该客户所有在还借款(正常or逾期	最多两条)相关联银行账户
			if("true".equals(bankAccountVO.getIsAllUpdate())) {
				// 由personId获取多个(最多两个)personBank
				LoanVO loanVO = new LoanVO();
				loanVO.setPersonId(Long.valueOf(bankAccountVO.getPersonId()));
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				loanVO.setStatusList(statusList);
				List<Loan> loanList = loanService.findListByVOUnionExtension(loanVO);
				List<Long> loanIdList = new ArrayList<Long>();
				for(Loan l : loanList) {
					loanIdList.add(l.getId());
				}
				personBankVO.setLoanIdList(loanIdList);
			} 
			// 只更改该客户该笔借款相关联银行账户
			else {
				personBankVO.setLoanId(Long.valueOf(bankAccountVO.getLoanId()));
			}
			
			List<PersonBank> personBankList =  personBankService.findPersonBankList(personBankVO);
			if(personBankList == null || personBankList.size() < 1) {
				result.put("success", false);
				result.put("msg", "查询不到对应的客户信息");
				return result;
			}
			else {
				Bank bank = bankService.get(Long.valueOf(bankAccountVO.getBankId()));
				bankAccountVO.setBank(bank);
				bankAccountVO.setBankName(bank.getBankName());
				
				int accountAuthType=0;
				
				SysParameter accountCredit=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_CREDIT.name());
				//若为银联进行银行卡验证
				if(bank.getTppType().toString().equals(BizConstants.ChinaUnion) && accountCredit.getParameterValue().equals("0")){//银联的交易
					Person person=personService.get(Long.valueOf(bankAccountVO.getPersonId()));
					Date sendTime=new Date();
					BankCardAuthReqVo bankCardAuthReqVo=new BankCardAuthReqVo();
//					SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE.name());
					bankCardAuthReqVo.setBizSysNo(BizSys.ZENDAI_2018_SYS.getCode());//业务系统编码
//					bankCardAuthReqVo.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());//信息类别编码
					bankCardAuthReqVo.setInfoCategoryCode("10001");
					bankCardAuthReqVo.setBankCardType("1");//银行卡类型
					bankCardAuthReqVo.setBankCardNo(bankAccountVO.getAccount());//银行卡号
					bankCardAuthReqVo.setIdType("0");//证件类型
					bankCardAuthReqVo.setIdNo(person.getIdnum());//证件号
					bankCardAuthReqVo.setRealName(person.getName());//客户名
					Response response =  iAuthService.bankCardAuth(bankCardAuthReqVo);
					//记录银行卡验证信息
					AccountAuthLog accountAuthLog = new AccountAuthLog();
					accountAuthLog.setBankId(Long.valueOf(bankAccountVO.getBankId()));
					accountAuthLog.setCardNo(bankAccountVO.getAccount());
					accountAuthLog.setOperatorId(ApplicationContext.getUser().getId());
					accountAuthLog.setSendMsg(JSONObject.toJSONString(bankCardAuthReqVo));
					accountAuthLog.setReturnCode(response.getCode());
					accountAuthLog.setReturnMsg(response.getMsg());
					accountAuthLog.setSendTime(sendTime);
					accountAuthLog.setName(person.getName());
					if(bankAccountVO.getLoanId()!=null){
					accountAuthLog.setLoanId(Long.parseLong(bankAccountVO.getLoanId()));
					}
					accountAuthLogService.insert(accountAuthLog);
					if(!response.getCode().equals("000000")){
						result.put("success", false);
						result.put("msg", response.getMsg());
						return result;
					}
					accountAuthType=1;
				}
				
				
				// 2,查询BankAccount,无->3, 有->4
				List<BankAccount> bankAccountList = bankAccountService.findListByVo(bankAccountVO);
				
				// 3,新增personBank记录
				if(bankAccountList == null || bankAccountList.size() < 1) {
					bankAccountVO.setAccountAuthType(accountAuthType);
					// 新增bank_Account
					BankAccount bankAccount = bankAccountService.insert(BankAccountAssembler.transferVO2Model(bankAccountVO));
					for(PersonBank p : personBankList) {
						// 删除旧的person_bank
						personBankService.deletePersonBank(p.getId());
						personBankVO.setBankAccountId(bankAccount.getId());
						personBankVO.setLoanId(p.getLoanId());
						// 新增新的person_bank
						personBankService.insertPersonBank(PersonBankAssembler.transferVO2Model(personBankVO));
						// 更新loan或extension中的repay_account_id
						LoanVO loanVO = new LoanVO();
						loanVO.setId(p.getLoanId());
						loanVO.setRepayAccountId(bankAccount.getBankId());
						loanService.updateLoanOrExtension(loanVO);
					}
				}
				// 4, 更新personBank
				else if (bankAccountList.size() == 1){
					for(PersonBank p : personBankList) {
						BankAccountVO bankAccountBaseVO=new BankAccountVO();
						bankAccountBaseVO.setId(bankAccountList.get(0).getId());
						bankAccountBaseVO.setAccountAuthType(accountAuthType);
						bankAccountService.update(bankAccountBaseVO);
						personBankVO.setId(p.getId());
						personBankVO.setBankAccountId(bankAccountList.get(0).getId());
						personBankService.update(personBankVO);
						// 更新loan或extension中的repay_account_id
						LoanVO loanVO = new LoanVO();
						loanVO.setId(p.getLoanId());
						loanVO.setRepayAccountId(bankAccountList.get(0).getBankId());
						loanService.updateLoanOrExtension(loanVO);
					}
				} else {
					result.put("success", false);
					result.put("msg", "存在错误数据");
					return result;
				}
			}
			for(PersonBank p : personBankList) {
				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CHANGE_BANK_ACCOUNT.getValue());
				businessLog.setLoanId(p.getLoanId());
				businessLog.setMessage("更改银行账户");
				businessLogService.insert(businessLog);
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.CHANGE_BANK_ACCOUNT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.SUBMIT_CHANGE_BANK_ACCOUNT.getValue());
				sysLog.setRemark(String.valueOf(p.getLoanId()));
				sysLogService.insert(sysLog);
			}
			result.put("success", true);
			return result;
		}
	}
	
}
