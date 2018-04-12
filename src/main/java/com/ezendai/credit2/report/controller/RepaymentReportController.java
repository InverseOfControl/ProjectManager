package com.ezendai.credit2.report.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.report.model.RepaymentReport;
import com.ezendai.credit2.report.service.RepaymentReportService;
import com.ezendai.credit2.report.vo.RepaymentReportVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;

/*
 * author:00226557
 */
@Controller
@RequestMapping("/repayment/report")
public class RepaymentReportController  extends BaseController{
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private RepaymentReportService repaymentReportService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
	private Credit2Properties credit2Properties;
    
    @Autowired
    private LoanService loanService;
    @Autowired
    private ProductService productService;
    
    private static final Logger logger = Logger.getLogger(RepaymentReportController.class);
    
    
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse respose) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_SUB_TYPE,EnumConstants.TRADE_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("repayDateStart", DateUtil.defaultFormatDay(DateUtil.getMonthFirstDay()));
		request.setAttribute("repayDateEnd", DateUtil.defaultFormatDay(DateUtil.getToday()));
		return "report/repaymentReportList";
	}
  
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(HttpServletRequest request,int rows, int page) throws Exception{
	
		RepaymentReportVO vo = buildQueryVO(request);
		List<RepaymentReport> repaymentReportList = new ArrayList<RepaymentReport>();
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ID");
		p.setSort("DESC");
		vo.setPager(p);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Pager pager = null;
		if(EnumConstants.YesOrNo.YES.getValue().equals(vo.getRepaymentType())){ //查询有还款记录
			reBuildVO(vo);
			pager = repaymentReportService.findWithPgOnRepayment(vo);
			repaymentReportList = pager.getResultList();
		}else{ //查询无还款记录
			pager = repaymentReportService.findWithPgOnNoRepayment(vo);
			repaymentReportList = pager.getResultList();		
		}
		if(repaymentReportList !=null){
			for (RepaymentReport repaymentReport : repaymentReportList) {
				if(repaymentReport.getId() != null){
					Loan loan = loanService.get(repaymentReport.getId());
					if(loan != null)
					if(loan.getProductId() !=null){
						Product product = productService.get(loan.getProductId());
						if(product.getId() !=null )
						repaymentReport.setProductId(Integer.valueOf(product.getId().toString()));
					}
				}
			}
		}
		result.put("total", pager.getTotalCount());
		result.put("rows", repaymentReportList);
		return result;
	}

	@RequestMapping("checkExportNum")
	@ResponseBody
	public String checkExportNum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RepaymentReportVO vo = buildQueryVO(request);
		int count =0;
		if(EnumConstants.YesOrNo.YES.getValue().equals(vo.getRepaymentType())){ //查询有还款记录
			reBuildVO(vo);
			count = repaymentReportService.queryRepaymentReportCount(vo);
		}else{ //查询无还款记录
			count = repaymentReportService.queryNoRepaymentReportCount(vo);
		}
		if (count  == 0) {
			return "没有可以符合条件的数据导出！";
		}
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
		if (sysParameter != null) {
			if (count > Integer.parseInt(sysParameter.getParameterValue())) {
				return "excel导出条数过多！";
			}
		}
		return "success";
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param vo
	 */
	private void reBuildVO(RepaymentReportVO vo) {
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		statusList.add(EnumConstants.LoanStatus.预结清.getValue());
		statusList.add(EnumConstants.LoanStatus.结清.getValue());
		vo.setStatusList(statusList);
	}
	
	/***
	 * 导出Excel
	 * @return
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		RepaymentReportVO vo = buildQueryVO(request);
		/*//用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		if(!userType.equals(EnumConstants.UserType.FINANCE.getValue())){
			return "您没有权限导出";
		}	*/
		String fileName="";//文件名 
		List<RepaymentReport> repaymentReportList = new ArrayList<RepaymentReport>();
		if(EnumConstants.YesOrNo.YES.getValue().equals(vo.getRepaymentType())){ //查询有还款记录
			reBuildVO(vo);
			repaymentReportList = repaymentReportService.queryRepaymentReport(vo);
			fileName = "有还款记录导出"+DateUtil.getDate(new Date(), "yy-MM-dd") + ".xlsx";
		}else{ //查询无还款记录
			repaymentReportList = repaymentReportService.queryNoRepaymentReport(vo);
			fileName = "无还款记录导出"+DateUtil.getDate(new Date(), "yy-MM-dd") + ".xlsx";
		}
		String downloadPath = credit2Properties.getDownloadPath();
		File file = new File(downloadPath + File.separator + "repaymentReport");
		if (!file.exists()) {//不存在则创建该目录
			file.mkdir();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "repaymentReport" + File.separator
										+ fileName.trim().toString());
			//生成Excel文件	
			if(EnumConstants.YesOrNo.YES.getValue().equals(vo.getRepaymentType())){
				repaymentReportService.exportRepaymentReportExcel(vo,repaymentReportList, "导出结果", os);//查询有还款记录
			}else{
				repaymentReportService.exportNoRepaymentReportExcel(vo,repaymentReportList, "导出结果", os);
			}
		} catch (FileNotFoundException e) {
			logger.error("生成Excel文件出错",  e);
			throw new BusinessException("生成Excel文件出错!");			
		}
		//下载Excel文件的路径
		String filePath = downloadPath + File.separator + "repaymentReport" + File.separator + fileName;
		try {
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
				return "success";
			} else {
				return "下载Excel出错!";
			}
		} catch (Exception e) {
			logger.error("下载Excel出错!", e);
			throw new BusinessException("下载Excel出错!");
		}		
	}
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private RepaymentReportVO buildQueryVO(HttpServletRequest request) throws ParseException {
		String repaymentType = request.getParameter("repaymentType");
		String productType = request.getParameter("productType");
		String repayDateStart = request.getParameter("repayDateStart");
		String repayDateEnd = request.getParameter("repayDateEnd");
		String salesDeptId = request.getParameter("salesDeptId");
		RepaymentReportVO vo = new RepaymentReportVO();
	    if(salesDeptId !=null && !"".equals(salesDeptId)){ 
	    	vo.setSalesDeptId(Integer.parseInt(salesDeptId));
	    }
	    if(productType !=null && !"".equals(productType)){ 
	    	vo.setProductType(Integer.parseInt(productType));
	    }
		vo.setRepaymentType(Integer.parseInt(repaymentType));
		vo.setRepayDateStart(DateUtil.strToDateTime(repayDateStart,DateUtil.default_pattern_day));
		vo.setRepayDateEnd(DateUtil.getEndTime(DateUtil.strToDateTime(repayDateEnd,DateUtil.default_pattern_day)));
		//当传空值的时候传当天日期起止日期
		if (vo.getRepayDateStart() == null) {
			vo.setRepayDateStart(DateUtil.getToday());
		}
		//当传空值的时候传当天日期最后的时间
		if (vo.getRepayDateEnd() == null) {
			vo.setRepayDateEnd(DateUtil.getEndTime());
		}
		vo.setQueryDate(DateUtil.getToday());//查询时一定要设置
		return vo;
	}
	/**
	 * 
	 * <pre>
	 * 获取系统所有的营业网点
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getAllSalesDept")
	@ResponseBody
	public List<BaseArea> getAllSalesDept() {
		BaseAreaVO baseAreaVO = new BaseAreaVO();
		baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		List<BaseArea> allSalesDepts = baseAreaService.findListByVo(baseAreaVO);
		BaseArea baseArea = new BaseArea();
		baseArea.setId(null);
		baseArea.setName("全部");
		allSalesDepts.add(0, baseArea);
		return allSalesDepts;
	}
}
