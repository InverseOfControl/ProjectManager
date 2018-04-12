package com.ezendai.credit2.system.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.assembler.OrganAssembler;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.service.OrganSalesDepartmentService;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.OrganBankVO;
import com.ezendai.credit2.system.vo.OrganDetailVO;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;
import com.ezendai.credit2.system.vo.OrganVO;
import com.ezendai.credit2.system.vo.SysGroupUserVO;
import com.ezendai.credit2.system.vo.SysUserVO;

/*
 * author:00226557
 */
@Controller
@RequestMapping("/organ/management")
public class OrganManagementController  extends BaseController{
    
    @Autowired
    private OrganService organService;
    @Autowired
    private OrganBankService organBankService;
    @Autowired
    private OrganSalesDepartmentService organSalesDepartmentService;
    @Autowired
    private OrganSalesManagerService organSalesManagerService;
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
    private SysLogService sysLogService;
   
    @Autowired
    private SysGroupUserService sysGroupUserService;
    
    
    private static final Logger logger = Logger.getLogger(OrganManagementController.class);
    
    
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.CARD_TYPE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		SysGroupUserVO sysGroupUserVO =new SysGroupUserVO();
		sysGroupUserVO.setUserId(ApplicationContext.getUser().getId());
		SysGroupUser sysGroupUser = sysGroupUserService.findByVO(sysGroupUserVO);
		request.setAttribute("groupId", sysGroupUser.getGroupId());
		return "system/organ/organList";
	}
  
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int rows, @ModelAttribute("vo") OrganVO vo) {
		List<Organ> organList = new ArrayList<Organ>();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ORGAN.CREATED_TIME");
		p.setSort("DESC");
		vo.setPager(p);
		vo.setUser(ApplicationContext.getUser());//增加用户角色权限
		p = organService.findWithPg(vo);
		organList = p.getResultList();
		if (CollectionUtil.isNotEmpty(organList)) {
			for (Organ organ : organList) {
				StringBuilder operations = new StringBuilder();
				operations.append("attachment").append("|acctInfo");
				organ.setOperations(operations.toString());
			}
		}
		result.put("total", p.getTotalCount());
		result.put("rows", organList);
		return result;
	}

	@RequestMapping("/organSave")
	@ResponseBody
	@Transactional
	public Map<String, Object> organSave(OrganDetailVO vo) throws Exception{
		Map< String, Object> result = new HashMap<String, Object>();
		try {
			if (vo != null) {
				OrganVO checkVO = new OrganVO();
				checkVO.setCode(vo.getCode());
				List<Organ> organList = organService.findListByVo(checkVO);
				if (CollectionUtil.isNotEmpty(organList)) {
					result.put("success", false);
					result.put("msg", "系统已存在机构内部编号!");
					return result;
				}
				//客户经理有效性检查
				if(salesManagerCheck(vo, result)){
					return result;
				}
				//客户经理与已添加的签名门店有效性检查
/*				if(salesManagerDeptCheck(vo, result)){
					return result;
				}*/
				organService.processOrganInsert(vo);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.ORG_MANAGE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.CREATE_ORG.getValue());
				sysLog.setRemark("新建机构，机构内部代码:" + vo.getCode()+ ",机构名称:" + vo.getName());
				sysLogService.insert(sysLog);
			}
			result.put("success", true);
			result.put("msg", "机构添加才成功!");
		} catch (Exception e) {
			logger.error("机构添加异常", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param vo
	 * @param result
	 */
/*	private boolean salesManagerDeptCheck(OrganDetailVO vo, Map<String, Object> result) {
		if (vo.getSalesManagerList() != null) {
			StringBuilder sb = new StringBuilder();
			boolean salesDeptCheck = false;
			for (OrganSalesManager user : vo.getSalesManagerList()) {
				if (user != null && StringUtil.isNotEmpty(user.getSalesManager())) {
					SysUser sysUser = sysUserService.getSysUserByCode(user.getCode());
					BaseArea userDept = baseAreaService.getUpperBaseAreaByIdentifier(sysUser.getDataPermission(), BizConstants.CREDIT2_SALESDEPARTMENT);
					boolean valid = false;
					if (vo.getSalesDepartmentList() != null){
						for (BaseArea baseArea : vo.getSalesDepartmentList()) {
							if (userDept !=null && baseArea != null && baseArea.getId() != null) {
								if (baseArea.getId().equals(userDept.getId())) {
									valid = true;
									break;
								}
							}
						}
					}
					if (!valid) {
						salesDeptCheck = true;
						sb.append(user.getSalesManager()).append(":").append(user.getCode()).append(",");
					}
				}
			}
			if(salesDeptCheck){
				result.put("success", false);
				result.put("msg", sb+"不在签约门店列表中!");
				return true;
			}
		}
		return false;
	}*/

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param vo
	 * @param result
	 */
	private boolean salesManagerCheck(OrganDetailVO vo, Map<String, Object> result) {
		if (vo.getSalesManagerList() != null) {
			StringBuilder sb = new StringBuilder();
			boolean salesCheck = false;
			for (OrganSalesManager user : vo.getSalesManagerList()) {
				if (user != null && StringUtil.isNotEmpty(user.getSalesManager())) {
					SysUserVO sysUserVo = new SysUserVO();
					sysUserVo.setCode(user.getCode());
					sysUserVo.setName(user.getSalesManager());
					List<SysUser> sysList = sysUserService.findListByVO(sysUserVo);
					if (CollectionUtil.isNullOrEmpty(sysList)) {
						salesCheck = true;
						sb.append(user.getSalesManager()).append(":").append(user.getCode()).append(",");
					}
				}
			}
			if(salesCheck){
				result.put("success", false);
				result.put("msg", sb+"输入的客户经理不正确!");
				return true;
			}
		}
		return false;
	}

	
	@RequestMapping("/organEditSave")
	@ResponseBody
	@Transactional
	public Map<String, Object> organEditSave(OrganDetailVO vo,Long organId) throws Exception{
		Map< String, Object> result = new HashMap<String, Object>();
		try {
			if (vo != null) {
				//客户经理有效性检查
				if(salesManagerCheck(vo, result)){
					return result;
				}
				//客户经理与已添加的签名门店有效性检查
/*				if(salesManagerDeptCheck(vo, result)){
					return result;
				}*/
				organService.processOrganEdit(vo, organId);
				//插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.ORG_MANAGE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.EDIT_ORG.getValue());
				sysLog.setRemark("编辑机构,机构内部代码:" + vo.getCode()+ ",机构名称:" + vo.getName());
				sysLogService.insert(sysLog);
			}
			result.put("success", true);
			result.put("msg", "机构编辑才成功!");
		} catch (Exception e) {
			logger.error("机构编辑异常", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	
	@RequestMapping("/getOrganDetail")
	@ResponseBody
	public OrganDetailVO getOrganDetail(Long organId) throws Exception {
		OrganDetailVO organDetailVO = new OrganDetailVO();
		Organ organ = organService.get(organId);
		OrganAssembler.convertOrgan2OrganDetailVO(organDetailVO, organ);
		OrganBankVO organBankVO = new OrganBankVO();
		organBankVO.setOrganId(organId);
		List<BankAccount> organBankList = organBankService.findBankAccountListByVo(organBankVO);
		organDetailVO.setBankAccountList(organBankList);
		OrganSalesDepartmentVO organSalesDepartmentVO = new OrganSalesDepartmentVO();
		organSalesDepartmentVO.setOrganId(organId);
		List<BaseArea> salesDeptList = organSalesDepartmentService
			.findSalesDeptListByVo(organSalesDepartmentVO);
		organDetailVO.setSalesDepartmentList(salesDeptList);
		OrganSalesManagerVO salesManager = new OrganSalesManagerVO();
		salesManager.setOrganId(organId);
		List<OrganSalesManager> salesManagerList = organSalesManagerService
			.findListByVo(salesManager);
		organDetailVO.setSalesManagerList(salesManagerList);
		return organDetailVO;
	}
	
	@RequestMapping("/organAdd")
	public String organAdd(HttpServletRequest request,Model model) throws Exception{
		return "system/organ/organAdd";
	}
	@RequestMapping("/organViewDetail")
	public String organViewDetail(HttpServletRequest request,Model model) throws Exception{
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		SysGroupUserVO sysGroupUserVO =new SysGroupUserVO();
		sysGroupUserVO.setUserId(ApplicationContext.getUser().getId());
		SysGroupUser sysGroupUser = sysGroupUserService.findByVO(sysGroupUserVO);
		request.setAttribute("groupId", sysGroupUser.getGroupId());
		return "system/organ/organViewDetail";
	}
	@RequestMapping("/organModify")
	public String organModify(HttpServletRequest request,Model model) throws Exception{
		return "system/organ/organModify";
	}
	
	@RequestMapping("/validationOrgan")
	@ResponseBody
	public Map<String, String> validationOrgan(Long organId) {
		Map<String, String> result = new HashMap<String, String>();
		boolean valid = organService.existValidCheckPlan(organId);
		if (!valid) {
			result.put("success", "0");
		} else {
			result.put("success", "1");
			result.put("msg", "该机构存在批复通过的方案!");
		}
		return result;
	}
	
	@RequestMapping("/doOrganDelele")
	@ResponseBody
	public String doOrganDelele(Long organId) {
		String result ="删除成功";
		Organ organ = organService.get(organId);
		try {
			 organService.deleteOrgan(organId,organ);
		} catch (Exception e) {
			result = e.getMessage();
			logger.error("", e);
		}
		//插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.ORG_MANAGE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EDIT_ORG.getValue());
		sysLog.setRemark("删除机构,机构内部代码:" + organ.getCode()+ ",机构名称:" + organ.getName());
		sysLogService.insert(sysLog);
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 获取可操作助学贷产品的营业网点
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getAllSalesDept")
	@ResponseBody
	public List<BaseArea> getAllSalesDept() {
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		baseAreaVO.setPager(null);
		List<BaseArea> allSalesDepts = baseAreaService.getStudentLoanSalesDepts();
		return allSalesDepts;
	}
	
	@RequestMapping("/showOrganCard/{organId}")
	public String showOrganCard(@PathVariable("organId") Long organId, Model model) {
		Organ organ = organService.get(organId);
		model.addAttribute("organName", organ.getName());
		model.addAttribute("organCode", organ.getCode());
		return "system/organ/showOrganCard";
	}
	
	@SuppressWarnings({"rawtypes"})
	@RequestMapping("/organCardList")
	@ResponseBody
	public Map organCardList(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int rows, @ModelAttribute("vo") OrganVO vo) {
		//TBD
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", 0);
		result.put("rows", new ArrayList());
		return result;
	}
	
	@RequestMapping("/organImageUploadView/{organId}")
	public String organImageUploadView(@PathVariable("organId") Long organId, Model model) {
		model.addAttribute("loanId", organId);//Using organId for loanId
		model.addAttribute("personId", BizConstants.SYSTEM_ID);
		model.addAttribute("productId", EnumConstants.ProductType.SE_LOAN.getValue());
		Organ organ = organService.get(organId-10000000000000L);//前面传入的organId加了10000000000000L
		model.addAttribute("organName", organ.getName());
		model.addAttribute("optModule", EnumConstants.OptionModule.ORG_MANAGE.getValue());
		model.addAttribute("userType", ApplicationContext.getUser().getUserType());
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(";");
			if (attachmentArray != null) {
				if (!StringUtils.isEmpty(attachmentArray[0])) {
					fileSizeLimit = attachmentArray[0];
				}
				if (!StringUtils.isEmpty(attachmentArray[1])) {
					fileQueueLimit = attachmentArray[1];
				}
			}
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		}
		return "system/organ/organImageUploadView";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
