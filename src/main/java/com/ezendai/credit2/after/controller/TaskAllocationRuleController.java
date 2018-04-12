package com.ezendai.credit2.after.controller; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.LateDetails;
import com.ezendai.credit2.after.model.TaskAllocationRule;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
import com.ezendai.credit2.after.service.TaskAllocationRuleService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.after.vo.TaskAllocationRuleVO;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.framework.util.WDWUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.report.service.DeliveryDetailService;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;
@Controller
@RequestMapping("/TaskAllocationRule/Main")
public class TaskAllocationRuleController extends BaseController {
	/**
	 * 区域service
	 */
	@Autowired
	private AreaService areaService;
	@Autowired
	private TaskAllocationRuleService  taskAllocationRuleService;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DeliveryDetailService deliveryDetailService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private CollectionCreateCasesService  createCasesService;
	@Autowired
	private Credit2Properties credit2Properties;
	private static final Logger logger = Logger.getLogger(TaskAllocationRuleController.class);
	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
	 
	 	return "after/taskAllocationRule/taskAllocationRuleList"; 
	/*	return "/finance/financialAudit/financialAuditList";*/
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/taskAllocationRuleList")
	@ResponseBody
	public Map taskAllocationRuleList( TaskAllocationRuleVO ruleVO, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		ruleVO.setPager(p);
		UserSession user = ApplicationContext.getUser();
		SysUser sysUser = sysUserService.get(user.getId());
		if (sysUser == null)
			return null;
			// 根据用户ID获取营业网点的ID
			List<Long> canBrowseSalesDeptList = sysUserService
					.getSalesDeptIdByUserId(user.getId());
			
		ruleVO.setSalesDeptIdList(canBrowseSalesDeptList);
		Pager pager = taskAllocationRuleService.TaskAllocationRuleWithPG(ruleVO);
	 	List<TaskAllocationRule> casesList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}
			@RequestMapping("/allocationRuleUI/{id}/{ruleId}")
			public String updateRecordUI (@PathVariable("id") String id,@PathVariable("ruleId") String ruleId,HttpServletRequest request,ModelMap map) throws Exception{
				/*设置数据字典*/
				SysUser user=	sysUserService.get(Long.parseLong(id));
				 
			   	map.put("user", user);
			  
			   	if(ruleId.equals("0")){ruleId=	"";}  
			   	map.put("ruleId", ruleId);
			 	
				return "after/taskAllocationRule/allocationRule";
		}
	
			
			@RequestMapping("/allocationRule")
			@ResponseBody
			public Map<String, Object> allocationRule(TaskAllocationRule rule) throws Exception{
				Map< String, Object> result = new HashMap<String, Object>();
				//edit
				try {
					SysUser user=	sysUserService.get(rule.getId());
					rule.setSalesDeptId(user.getAreaId());
					rule.setUserName(user.getName());
					rule.setUserLoginName(user.getLoginName());
					rule.setSalesDeptId(user.getAreaId());
				
					if(StringUtil.isEmpty(rule.getRuleStrId())){
						 rule.setStatus(0);
						taskAllocationRuleService.insertTaskAllocationRule(rule);
					}else{
						rule.setRuleId(Long.parseLong(rule.getRuleStrId()));
						taskAllocationRuleService.updateTaskAllocationRule(rule);
					
					} 
					result.put("success", true);
				} catch (Exception e) {
					e.printStackTrace();
					result.put("success", false);
					result.put("msg", e.getMessage());
				}
				return result;
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
		List<Product> products = productService.findProductTypeByUserId(user.getId());
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
				if(user.getUserType().equals(12)){
					List<Long> areaId=new ArrayList<Long>();
					salesDepts.clear();
					areaId=sysUserService.getSalesDeptIdByUserIdAndDeptType(user.getId());
					BaseArea baseAreaA = new BaseArea();
					baseAreaA.setName("全部");
					baseAreaA.setId(null);
					salesDepts.add(0, baseAreaA);
					for(Long aId:areaId){
						BaseArea ba = baseAreaService.get(aId);
						salesDepts.add(ba);
					}
			}
			return salesDepts;

		} else if (products.get(0).getProductType() == (long)EnumConstants.ProductList.CAR_LOAN.getValue() || products.get(0).getProductType() == (long)EnumConstants.ProductList.CAR_NEW_LOAN.getValue()) {// 车贷事业部显示所有车贷营业部

			salesDepts = deliveryDetailService.getCarLoanSalesDepts(mapCar);
			BaseArea baseArea = new BaseArea();
			baseArea.setName("全部");
			baseArea.setId(null);
			salesDepts.add(0, baseArea);
			return salesDepts;
		} else if (sysUser.getUserType() == EnumConstants.UserType.SYSTEM_ADMIN.getValue() || sysUser.getUserType() == EnumConstants.UserType.FINANCE.getValue()) {// admin和财务账号显示车贷和企业贷营业部
			salesDepts = deliveryDetailService.getStudentLoanSalesDepts(mapCar);
 
			/*studentLoanSalesDepts = deliveryDetailService.getStudentLoanSalesDepts(mapCar);
			if (salesDepts == null && studentLoanSalesDepts == null)
				return null;
			for (int i = 0; i < studentLoanSalesDepts.size(); i++) {
				salesDepts.add(studentLoanSalesDepts.get(i));
			}*/
			return salesDepts;
		} else {// 查询小企业贷所有营业部
			studentLoanSalesDepts = deliveryDetailService.getStudentLoanSalesDepts(mapSe);
			BaseArea baseArea = new BaseArea();
			baseArea.setName("全部");
			baseArea.setId(null);
			studentLoanSalesDepts.add(0, baseArea);
			return studentLoanSalesDepts;
		}
	}
	
	@RequestMapping("/exportExcelUI")
	public String updateRecordUI (HttpServletRequest request,ModelMap map) throws Exception{
		/*设置数据字典*/
	 
		return "after/collectionTaskChange/excelImport";
}
	@RequestMapping(value="/exportExcel")
	@ResponseBody
	public String  init(TaskAllocationRuleVO vo, HttpServletRequest request,HttpServletResponse response ) {
		long startTime = System.currentTimeMillis();
		/*设置数据字典*/
		List<Long> salesDeptList=new ArrayList<Long>();
		List<Integer> loanTypetList=new ArrayList<Integer>();
	
		 
			List<BaseArea> baseAreaVOList=getDepts();
			List<Long> salesDeptIdList=new ArrayList<Long>();
			 
			for(BaseArea area :baseAreaVOList){
				if(StringUtil.isNotEmpty(String.valueOf(area.getId()))&&!"null".equals(String.valueOf(area.getId()))){
					salesDeptIdList.add(area.getId());
				}
			}
			
			vo.setSalesDeptIdList(salesDeptIdList);
		 
		Date nowDate =new Date();
		Integer count = 0;
        try {//collection
          OutputStream os ;
          String fileName = "CollectionJobAssignme"  +DateUtil.defaultFormatMSDate(nowDate)+ ".xlsx";
          String downloadPathfileName = "催收作业分配明细表"  +DateUtil.defaultFormatDay(nowDate)+ ".xlsx";
  		String downloadPath = credit2Properties.getDownloadPath();
  		File file = new File(downloadPath + File.separator + "report" +File.separator + "after"+ File.separator + DateUtil.defaultFormatDay(nowDate));
  		if (!file.exists()) {//不存在则创建该目录
  			file.mkdirs();
  		}
  		os = new FileOutputStream(downloadPath + File.separator
  				+ "report" +File.separator + "after"+ File.separator + DateUtil.defaultFormatDay(nowDate)+ File.separator
				+ fileName.trim().toString());
       
  		List<LateDetails> detailsList = taskAllocationRuleService.getLateDetailsList(vo);
  		count=detailsList.size();
  		 
  		taskAllocationRuleService.exportExcel("ff", os, detailsList, vo);
        String filePath = downloadPath+  File.separator + "report" +File.separator + "after"+ File.separator + DateUtil.defaultFormatDay(nowDate) + File.separator + fileName;
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, downloadPathfileName, "xlsx")) {
				// 导出成功后删除导出的文件
				//FileUtil.deleteFile(filePath);
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long endTime = System.currentTimeMillis();
 		//插入系统日志  
 		SysLog sysLog = new SysLog();			
 		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_CHANGE.getValue());
 		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_JOB_ASSIGNME.getValue());
 		sysLog.setRemark("催收作业分配明细表导出用时" + (double)(endTime - startTime) / 1000 + "秒  导出" + count + "行;统计日期："+vo.getStartDate());
 		sysLogService.insert(sysLog);
        return null;
	 
		}
	@RequestMapping(value = "/ExcelImport",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String excelImport(@RequestParam(value = "filename", required = false) MultipartFile file,HttpServletRequest request, ModelMap modelMap) {
		//Map< String, Object> result = new HashMap<String, Object>();
		JSONObject jb=new JSONObject();	
		UserSession user1 = ApplicationContext.getUser();
		long startTime = System.currentTimeMillis();
		  int count=0;
		try {
		  String name=file.getOriginalFilename();
		  String path=  request.getSession().getServletContext().getRealPath("/upload");
		  boolean flag=validateExcel(name);
		  if(!flag){
				jb.put("issu", "2");
			  jb.put("msg", "文件格式不正确");
				return jb.toString();
		  } 
		  	 
		    List<TaskAllocationRule> list=taskAllocationRuleService.getExcelInfo(name, file) ;
		    SysUserVO sysUserVo=new SysUserVO();
		   ArrayList<Integer> userTypelist=new ArrayList<Integer>();
		   	userTypelist.add(2);
		   	userTypelist.add(6);
		    sysUserVo.setUserTypeList(userTypelist);
		    if(list.size()<1){
		    	 
				jb.put("issu", "0");
				jb.put("msg", "导入失败,数据不正确！");
				return jb.toString();
		    }
		    for(TaskAllocationRule rule:list){
		    		sysUserVo.setLoginName(rule.getUserLoginName());
		    		List<SysUser> userList=sysUserService.findListByVO(sysUserVo);
		    		if(userList.size()<1){
		    			jb.put("issu", "0");
		    			jb.put("msg", "导入失败,登录名：【"+rule.getUserLoginName()+"】不是客服或门店经理！");
		  				return jb.toString();
		    		}
		    		//SysUser user=sysUserService.getSysUserByLoginName();
		    		CollectionCreateCasesVO vo=new CollectionCreateCasesVO();
					CollectionTaskVO tvo=new CollectionTaskVO();
					vo.setOperatorId(userList.get(0).getId());
					vo.setModifiedTime(new Date()); 
					vo.setModifier(user1.getLoginName());
					vo.setModifierId(user1.getId());
					tvo.setOperatorId(userList.get(0).getId());
					tvo.setModifiedTime(new Date()); 
					tvo.setModifier(user1.getLoginName());
					tvo.setModifierId(user1.getId());
					CollectionCasesTask task=createCasesService.seleTaskById(Long.parseLong(rule.getTaskId()));
					vo.setId(task.getCaseId());
					tvo.setId(Long.parseLong(rule.getTaskId()));
					createCasesService.casesChange(vo);
					createCasesService.taskChange(tvo);
					count++;
					 
		    }
		    Date nowDate =new Date();
		    String fileName = "CollectionJobAssignme-Upload"  +DateUtil.defaultFormatMSDate(nowDate)+ ".xlsx";
	       
	  		String uploadPath = credit2Properties.getUploadPath();
	  		File f = new File(uploadPath + File.separator + "report" +File.separator + "after"+ File.separator + DateUtil.defaultFormatDay(nowDate)+File.separator+fileName);
	  		if (!f.exists()) {//不存在则创建该目录
	  			f.mkdirs();
	  		}
	  		file.transferTo(f);
	  		jb.put("issu", "1");
	  		jb.put("msg", "分配成功");
		}catch (Exception e) {
			e.printStackTrace();
			jb.put("issu", "0");
			jb.put("msg", "导入失败");
		}
			long endTime = System.currentTimeMillis();
	 		//插入系统日志  
	 		SysLog sysLog = new SysLog();			
	 		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_CHANGE.getValue());
	 		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_JOB_ASSIGNME_IMPORT.getValue());
	 		sysLog.setRemark("催收作业分配明细表导入用时" + (double)(endTime - startTime) / 1000 + "秒  分配" + count + "条  " );
	 		sysLogService.insert(sysLog);
		return jb.toString();
	}
			//批量分配
			@RequestMapping(value = "/batchRule/{ids}")
			@ResponseBody
			public String ipsDelete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
				 
				
				for(int i=0;i<ids.length;i++){
					String id=ids[i];
					TaskAllocationRule rule =new TaskAllocationRule();
					SysUser user=	sysUserService.get(Long.parseLong(id));
					rule.setSalesDeptId(user.getAreaId());
					rule.setUserName(user.getName());
					rule.setUserLoginName(user.getLoginName());
					rule.setStatus(1);
					rule.setNum(0);
					taskAllocationRuleService.insertTaskAllocationRule(rule);
				 
				}
				
				 
				return null;
			}
			
		 
			@RequestMapping(value = "/distribution/{ruleId}/{status}")
			@ResponseBody
			public String distribution(@PathVariable("ruleId") String ruleId,@PathVariable("status") String status,HttpServletRequest request, ModelMap modelMap) {
				Long id =Long.parseLong(ruleId);
				int rulestatus =Integer.parseInt(status);
				TaskAllocationRule rule =new TaskAllocationRule();
				rule.setRuleId(id);
				if(rulestatus==1){
					rule.setStatus(0);
				}else if(rulestatus==0){
					rule.setStatus(1);
					}
				taskAllocationRuleService.updateTaskAllocationRule(rule);
				return null;
			}
	@RequestMapping("checkExportNum")
	@ResponseBody
	public String checkExportNum(TaskAllocationRuleVO vo, HttpServletResponse response) {
		List<Long> salesDeptList=new ArrayList<Long>();
		List<Integer> loanTypetList=new ArrayList<Integer>();
	
		 
			List<BaseArea> baseAreaVOList=getDepts();
			List<Long> salesDeptIdList=new ArrayList<Long>();
			 
			for(BaseArea area :baseAreaVOList){
				if(StringUtil.isNotEmpty(String.valueOf(area.getId()))&&!"null".equals(String.valueOf(area.getId()))){
					salesDeptIdList.add(area.getId());
				}
			}
			
			vo.setSalesDeptIdList(salesDeptIdList);
		 
		Integer count =  taskAllocationRuleService.getLateDetailsListCount(vo);
		if (count.compareTo(0) == 0) {
			return "没有可以符合条件的数据！";
		}
		return "success";
	}
	
	/**
	 * 描述：验证EXCEL文件
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath){
		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
			return false;  
		}  
		return true;
	}
	}

