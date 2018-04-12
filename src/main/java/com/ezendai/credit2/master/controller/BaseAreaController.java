package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.CityService;
import com.ezendai.credit2.master.service.CompanyService;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.master.service.SalesTeamService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Controller
@RequestMapping("/baseArea")
public class BaseAreaController {
	
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CompanyService companyService ;
	@Autowired 
	private CityService  cityService;
	@Autowired
	private SalesTeamService salesTeamService;
	@Autowired
	private SalesDepartmentService salesDepartmentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SysLogService sysLogService;

	
	

	
	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		return "/system/netWork";
	}
	
	/**
	 * 查询营业网点数据
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/getSearchJson")
	@ResponseBody
	public Map getSearchJson(BaseAreaVO baseAreaVO,int rows, int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("CODE");
		pager.setSort("ASC");
		baseAreaVO.setPager(pager);
		
		//查询
		pager = baseAreaService.findWithPGExtension(baseAreaVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	
	@RequestMapping("/getSearchJson1")
	@ResponseBody
	public List<BaseArea> getSearchJson1(BaseAreaVO baseAreaVO) {
		List<BaseArea> list = baseAreaService.findWithList(baseAreaVO);
		return list;
	}
	@RequestMapping("/getSearchDepts")
	@ResponseBody
	public List<BaseArea> getSearchDepts(BaseAreaVO baseAreaVO) {
		UserSession user = ApplicationContext.getUser();
		if(!user.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())){
			// 车贷或小企贷
			List<Product> products = productService.findProductTypeByUserId(user.getId());
			for (Product product : products) {
				if(product.getProductType() == 2){
					baseAreaVO.setDeptType(EnumConstants.ProductType.CAR_LOAN.getValue());
				}else{
					baseAreaVO.setDeptType(Integer.valueOf(EnumConstants.ProductType.SE_LOAN.getValue().toString()));
				}
			}
			//当前登入的是门店的人
			baseAreaVO.setIsDeptPerson(0);
		}
		List<BaseArea> list = baseAreaService.findListByVo(baseAreaVO);
		return list;
	}
	/**
	 * 新增地区
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/addArea",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map addArea(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			areaService.addArea(baseAreaVO);
			msg = "新增地区成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}


	/**
	 * 新增城市
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/addCity",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map addCity(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
					cityService.addCity(baseAreaVO);
					msg = "新增城市成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 新增营业部
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/addDept",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map addDept(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
					salesDepartmentService.addSalesDepartment(baseAreaVO);
					msg = "新增营业部成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
					
	/**
	 * 新增团队
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/addTeam",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map addTeam(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
					salesTeamService.addSalesTeam(baseAreaVO);
					msg = "新增团队成功";
				} catch(BusinessException ex) {
					isSuccess = false;
					msg = "网点名称重复";
				} catch(Exception ex) {
					isSuccess = false;
					msg = ex.getMessage();
				}
				map.put("isSuccess", isSuccess);
				map.put("msg", msg);
				return map;
			}

	/**
	 * 修改公司
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/updateCompany",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map updateCompany(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
					//更新操作
					companyService.editCompany(baseAreaVO);

					msg = "修改公司名成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 修改地区
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/updateArea",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map updateArea(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			//更新操作
			areaService.editArea(baseAreaVO);
			msg = "修改成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 修改城市
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/updateCity",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map updateCity(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			//更新操作
			cityService.editCity(baseAreaVO);
			msg = "修改成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 修改营业部
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/updateDept",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map updateDept(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			//更新操作
			salesDepartmentService.editSalesDepartment(baseAreaVO);
			msg = "操作成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	
	/**
	 * 修改团队
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/updateTeam",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map updateTeam(BaseAreaVO baseAreaVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			//更新操作
			salesTeamService.editSalesTeam(baseAreaVO);
			msg = "操作成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "网点名称重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	
	
	
	
	/**
	 * 加载某网点信息
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/loadNetWorkInfo")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map loadNetWorkInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		BaseArea baseArea = baseAreaService.get(id);
		if (baseArea == null) {
	
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("baseArea", baseArea);
		map.put("msg", msg);
		return map;
	}


	/**
	 * 删除网点
	 * @author 
	 * @return
	 */
	@RequestMapping("/deletedBaseArea")
	@ResponseBody
	public Map deletedBaseArea(@RequestParam(value="id")Long id) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			BaseArea baseArea = baseAreaService.get(id);
			if (baseArea != null) {
				BaseAreaVO baseAreaVO = new BaseAreaVO();
				baseAreaVO.setId(id);
				baseAreaVO.setIsDeleted(1);
				//将状态改为以删除
				baseAreaService.updateBaseArea(baseAreaVO);
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.NETWORK_INFORMATION_MAINTENANCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DELETE_DOT.getValue());
				sysLog.setRemark("ID:" + baseArea.getId() + "网点名称:" +baseArea.getName());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
}