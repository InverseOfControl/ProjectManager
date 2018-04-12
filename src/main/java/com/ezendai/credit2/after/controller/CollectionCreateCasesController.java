package com.ezendai.credit2.after.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.report.service.DeliveryDetailService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

@Controller
@RequestMapping("/CollectionCreateCases/Main")
public class CollectionCreateCasesController extends BaseController {

	/**
	 * 区域service
	 */
	@Autowired
	private AreaService areaService;
	@Autowired
	private CollectionCreateCasesService createCasesService;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private DeliveryDetailService deliveryDetailService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysParameterService sysParameterService;
	private static final Logger logger = Logger
			.getLogger(CollectionCreateCasesController.class);

	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		return "after/collectionCreateCases/collectionCreateCasesMain";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionList")
	@ResponseBody
	public Map collectionList(CollectionCreateCasesVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		vo.setProductType(1);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(130);
		statusList.add(140);
		statusList.add(160);
		vo.setStatusList(statusList);
		vo.setWithoutNoSend("Yes");
		List<BaseArea> baseAreaVOList = getStudentDept();
		List<Long> salesDeptIdList = new ArrayList<Long>();
		for (BaseArea baseAreaId : baseAreaVOList) {
			salesDeptIdList.add(baseAreaId.getId());
		}
		vo.setSalesDeptIdList(salesDeptIdList);
		// vo.setLoanId(10);
		UserSession user = ApplicationContext.getUser();
		List<Product> products = productService.findProductTypeByUserId(user
				.getId());
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		if (products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_LOAN
				.getValue()
				|| products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_NEW_LOAN
						.getValue()) {// 车贷事业部显示所有车贷营业部
			vo.setDeptSerch("2222");
		} else if (user.getUserType().equals(
				EnumConstants.UserType.SYSTEM_ADMIN.getValue())
				|| user.getUserType() == EnumConstants.UserType.FINANCE
						.getValue()) {// admin和财务账号显示车贷和企业贷营业部
			vo.setDeptSerch("2222");
		} else if (user.getLoginName().equals(overdueManagerCode)) {
			vo.setDeptSerch("1111");
		} else if (user.getLoginName().length() >= 4) {
			if (user.getLoginName().substring(0, 4).equals("test")) {
				vo.setDeptSerch("2222");
			} else {
				if (user.getUserType().equals(11)) {
					vo.setDeptSerch("2222");
				} else {
					vo.setDeptSerch("1111");
				}
			}

		} else {
			vo.setDeptSerch("1111");
		}
		Pager pager = createCasesService.findCollectionCreateCasesWithPG(vo);
		List<CollectionCreateCases> casesList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	@RequestMapping("/searchLoan/{id}")
	public String searchLoan(@PathVariable("id") String id,
			HttpServletRequest request, ModelMap map) throws Exception {
		/* 设置数据字典 */
		/*
		 * setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		 * request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		 */
		/*
		 * List<BaseArea> allCitys = baseAreaService.getAllCitys();
		 * model.addAttribute("cityList",allCitys);
		 */
		CollectionCreateCases loan = createCasesService.selectLoanInfoById(Long
				.valueOf(id));
		loan.setProductTypeName(sysEnumerateService.findEnumValue(
				"PRODUCT_TYPE", loan.getProductType()));
		loan.setStatusName(sysEnumerateService.findEnumValue("LOAN_STATUS",
				loan.getStatus()));
		map.put("loan", loan);
		return "after/collectionCreateCases/addCases";
	}

	@RequestMapping("/casesAdd")
	@ResponseBody
	public Map<String, Object> productEditSave(OverdueReceivablesCaseVO vo)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			// 新增待审核
			UserSession user = ApplicationContext.getUser();
			vo.setCreatedTime(new Date());
			vo.setCreator(user.getLoginName());
			vo.setCreatorId(user.getId());
			vo.setCaseState(2);
			vo.setCaseType(1);
			vo.setCaseFrom(2);
			vo.setTransferDate(new Date());
			vo.setOperatorId(user.getId());
			createCasesService.insert(vo);
			result.put("success", true);
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.MANUAL_CRATE_CASE
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CHANGE_TO_FIRST
					.getValue());
			sysLog.setRemark("借款ID   " + vo.getLoanId().toString());
			sysLogService.insert(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	public List<BaseArea> getStudentDept() {
		List<BaseArea> salesDepts = new ArrayList<BaseArea>();
		List<BaseArea> studentLoanSalesDepts = new ArrayList<BaseArea>();
		UserSession user = ApplicationContext.getUser();
		// 车贷或小企贷
		List<Product> products = productService.findProductTypeByUserId(user
				.getId());
		SysUser sysUser = sysUserService.get(user.getId());
		if (sysUser == null)
			return null;
		Map<String, String> mapCar = new HashMap<String, String>();
		Map<String, String> mapSe = new HashMap<String, String>();
		if (!sysUser.getLoginName().substring(0, 4).equals("test")) {

			mapCar.put("deptId", "2222");
			mapSe.put("deptId", "1111");
		}
		if (sysUser.getDataPermission().length() > 2) {
			// 小企业贷门店人员，显示当前所在的营业部
			if (sysUser.getAreaId() != null) {
				BaseArea baseArea = baseAreaService.get(sysUser.getAreaId());
				salesDepts.add(baseArea);
			}
			return salesDepts;

		} else if (products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_LOAN
				.getValue()
				|| products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_NEW_LOAN
						.getValue()) {// 车贷事业部显示所有车贷营业部

			salesDepts = deliveryDetailService.getCarLoanSalesDepts(mapCar);
			return salesDepts;
		} else if (sysUser.getUserType() == EnumConstants.UserType.SYSTEM_ADMIN
				.getValue()
				|| sysUser.getUserType() == EnumConstants.UserType.FINANCE
						.getValue()) {// admin和财务账号显示车贷和企业贷营业部
			salesDepts = deliveryDetailService.getCarLoanSalesDepts(mapCar);
			studentLoanSalesDepts = deliveryDetailService
					.getStudentLoanSalesDepts(mapSe);
			if (salesDepts == null && studentLoanSalesDepts == null)
				return null;
			for (int i = 0; i < studentLoanSalesDepts.size(); i++) {
				salesDepts.add(studentLoanSalesDepts.get(i));
			}
			return salesDepts;
		} else {// 查询小企业贷所有营业部
			studentLoanSalesDepts = deliveryDetailService
					.getStudentLoanSalesDepts(mapSe);
			return studentLoanSalesDepts;
		}
	}

	/**
	 * 获取营业部列表
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/getDepts")
	@ResponseBody
	public List<BaseArea> getDepts() {
		List<BaseArea> salesDepts = new ArrayList<BaseArea>();
		List<BaseArea> studentLoanSalesDepts = new ArrayList<BaseArea>();
		UserSession user = ApplicationContext.getUser();
		// 车贷或小企贷
		List<Product> products = productService.findProductTypeByUserId(user
				.getId());
		SysUser sysUser = sysUserService.get(user.getId());
		if (sysUser == null)
			return null;
		Map<String, String> mapCar = new HashMap<String, String>();
		Map<String, String> mapSe = new HashMap<String, String>();
		if (!sysUser.getLoginName().substring(0, 4).equals("test")) {
			mapCar.put("deptId", "2222");
			mapSe.put("deptId", "1111");
		}
		if (sysUser.getDataPermission().length() > 2) {
			// 根据用户ID获取营业网点的ID
			List<Long> canBrowseSalesDeptList = sysUserService
					.getSalesDeptIdByUserId(user.getId());
			
			/**
			 * 根据areaId和userType来确定用户是否是某一个经理下的客服如：
			         如果是上海事业部的客服（刘娜下的客服人员），则可以查看的营业部网点为上海、沈阳、青岛三个营业网点
			 */
			//查询出这个经理的相关信息
			SysUserVO sysUserVo = new SysUserVO();
			sysUserVo.setAreaId(sysUser.getAreaId());
			sysUserVo.setUserType(2);
			List<SysUser> sysManager = sysUserService.findListByVO(sysUserVo);
			
			Long userId = user.getId();
			boolean flag = false;
			if(sysManager !=null && sysManager.size()>0){
				//到userbaseArea表中查询这个经理是否有操作其他营业网点的权限
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(sysManager.get(0).getId());
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				List<Long> tempList = null;
				//如果结果有值，证明这个经理有操作其他网点的权限，则将useId置成这个经理的userid
				tempList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				if(tempList !=null &&tempList.size()>0){
					flag = true;
					//重置ID
					userId = sysManager.get(0).getId();
				}
			}
			 // isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
			  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))||flag){
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(userId);
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				canBrowseSalesDeptList = null;
				// 获取所有的营业网点ID
				canBrowseSalesDeptList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				BaseArea baseAreaB = new BaseArea();
				baseAreaB.setName("全部");
				baseAreaB.setId(null);
				salesDepts.add(0, baseAreaB);
			}

			if (canBrowseSalesDeptList != null
					&& canBrowseSalesDeptList.size() > 0) {
				BaseArea baseArea = new BaseArea();
				for (Long id : canBrowseSalesDeptList) {
					baseArea = baseAreaService.get(id);
					salesDepts.add(baseArea);
				}
			}

			if (user.getUserType().equals(12)) {
				List<Long> areaId = new ArrayList<Long>();
				salesDepts.clear();
				areaId = sysUserService.getSalesDeptIdByUserIdAndDeptType(user
						.getId());
				BaseArea baseAreaA = new BaseArea();
				baseAreaA.setName("全部");
				baseAreaA.setId(null);
				salesDepts.add(0, baseAreaA);
				for (Long aId : areaId) {
					BaseArea ba = baseAreaService.get(aId);
					salesDepts.add(ba);
				}
			}
			return salesDepts;

		} else if (products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_LOAN
				.getValue()
				|| products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_NEW_LOAN
						.getValue()) {// 车贷事业部显示所有车贷营业部

			salesDepts = deliveryDetailService.getCarLoanSalesDepts(mapCar);
			BaseArea baseArea = new BaseArea();
			baseArea.setName("全部");
			baseArea.setId(null);
			salesDepts.add(0, baseArea);
			return salesDepts;
		} else if (sysUser.getUserType() == EnumConstants.UserType.SYSTEM_ADMIN
				.getValue()
				|| sysUser.getUserType() == EnumConstants.UserType.FINANCE
						.getValue()) {// admin和财务账号显示车贷和企业贷营业部
			salesDepts = deliveryDetailService.getStudentLoanSalesDepts(mapCar);
			BaseArea baseArea = new BaseArea();
			baseArea.setName("全部");
			baseArea.setId(null);
			salesDepts.add(0, baseArea);
			/*
			 * studentLoanSalesDepts =
			 * deliveryDetailService.getStudentLoanSalesDepts(mapCar); if
			 * (salesDepts == null && studentLoanSalesDepts == null) return
			 * null; for (int i = 0; i < studentLoanSalesDepts.size(); i++) {
			 * salesDepts.add(studentLoanSalesDepts.get(i)); }
			 */
			return salesDepts;
		} else {// 查询小企业贷所有营业部
			studentLoanSalesDepts = deliveryDetailService
					.getStudentLoanSalesDepts(mapSe);
			BaseArea baseArea = new BaseArea();
			baseArea.setName("全部");
			baseArea.setId(null);
			studentLoanSalesDepts.add(0, baseArea);
			return studentLoanSalesDepts;
		}
	}
}
