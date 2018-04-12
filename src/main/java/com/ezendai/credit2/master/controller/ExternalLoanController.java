package com.ezendai.credit2.master.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SyncLoan;
import com.ezendai.credit2.master.service.SyncLoanService;
import com.ezendai.credit2.master.service.SyncPreRepaymentInfoService;
import com.ezendai.credit2.master.service.SyncRepayInfoService;
import com.ezendai.credit2.master.service.SyncRepaymentPlanService;
import com.ezendai.credit2.master.vo.SyncLoanVO;
import com.ezendai.credit2.master.vo.SyncPreRepaymentInfoVO;
import com.ezendai.credit2.master.vo.SyncRepayInfoVO;
import com.ezendai.credit2.master.vo.SyncRepaymentPlanVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Controller
//@RequestMapping("/externalLoan")
public class ExternalLoanController extends BaseController {

	private static final Logger log = Logger.getLogger(ExternalLoanController.class);
	
	@Autowired
	private SyncLoanService syncLoanService;

	@Autowired
	private SyncRepaymentPlanService syncRepaymentPlanService;
	
	@Autowired
	private SyncRepayInfoService syncRepayInfoService;

	@Autowired
	private SyncPreRepaymentInfoService syncPreRepaymentInfoService;
	
	@Autowired
	
	private LoanService loanService;
	
	@Autowired
	private ProductService  productService;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private Credit2Properties credit2Properties;
	//页面上选择所有
	private final Long SELECTED_ALL =60L;
	
	@RequestMapping("/syncLoan")
	public String viewListsyncLoan(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.SYNC_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("buildDateStart", DateUtil.getDateBefore(15));
		return "master/externalLoan/syncLoan";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getSyncLoanData")
	@ResponseBody
	public Map<String, Object> getSyncLoanData(SyncLoanVO syncLoanVO, int rows, int page) {

		//handle the end date
		if (syncLoanVO.getBuildDateStart() == null) {
			syncLoanVO.setBuildDateStart(DateUtil.getDateBefore(15));
		}
		if (syncLoanVO.getBuildDateEnd() != null) {
			syncLoanVO.setBuildDateEnd(DateUtil.getEndTime(syncLoanVO.getBuildDateEnd()));
		}
		if (syncLoanVO.getSendDateEnd() != null) {
			syncLoanVO.setSendDateEnd(DateUtil.getEndTime(syncLoanVO.getSendDateEnd()));
		}
		if(syncLoanVO.getStatus()!=null && SELECTED_ALL.equals(syncLoanVO.getStatus())){
			syncLoanVO.setStatus(null);//状态选择所有
		}
		
		String strLoanType = "";
		if(null != syncLoanVO.getLoanType() && syncLoanVO.getLoanType() != ""){
			Integer loanType = Integer.parseInt(syncLoanVO.getLoanType());
			strLoanType = retLoanType(loanType);
		}
		syncLoanVO.setLoanType(strLoanType);
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		pager.setSidx("SYNC_LOAN.ID");
		pager.setSort("DESC");
		syncLoanVO.setPager(pager);
		pager = syncLoanService.findList(syncLoanVO);
		syncLoanVO.setPager(pager);
		Product  product=null;
		Loan loan=null;
		List<SyncLoan> loanList = pager.getResultList();
		for (SyncLoan syncLoan : loanList) {
			if(syncLoan.getLoanId() !=null)
			loan = loanService.get(syncLoan.getLoanId());
			if(loan != null){
				if(loan.getProductId() !=null)
				 product = productService.get(loan.getProductId());
				if(loan.getGrantDate() != null){
					
					syncLoan.setGrantDate(loan.getGrantDate());
				}
			}
			if(product!=null)
			syncLoan.setLoanType(product.getProductName());
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}

	@RequestMapping("/syncRepaymentPlan")
	public String viewListsyncRepaymentPlan(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.SYNC_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("buildDateStart", DateUtil.getDateBefore(15));
		return "master/externalLoan/syncRepaymentPlan";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getSyncRepaymentPlan")
	@ResponseBody
	public Map<String, Object> getSyncRepaymentPlan(SyncRepaymentPlanVO syncRepaymentPlanVO,int rows, int page) {

		//handle the end date
		if (syncRepaymentPlanVO.getBuildDateStart() == null) {
			syncRepaymentPlanVO.setBuildDateStart(DateUtil.getDateBefore(15));
		}
		if (syncRepaymentPlanVO.getBuildDateEnd() != null) {
			syncRepaymentPlanVO.setBuildDateEnd(DateUtil.getEndTime(syncRepaymentPlanVO.getBuildDateEnd()));
		}
		if (syncRepaymentPlanVO.getSendDateEnd() != null) {
			syncRepaymentPlanVO.setSendDateEnd(DateUtil.getEndTime(syncRepaymentPlanVO.getSendDateEnd()));
		}
		if(syncRepaymentPlanVO.getStatus()!=null && SELECTED_ALL.equals(syncRepaymentPlanVO.getStatus())){
			syncRepaymentPlanVO.setStatus(null);//状态选择所有
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		pager.setSidx("SYNC_REPAYMENT_PLAN.ID");
		pager.setSort("DESC");
		syncRepaymentPlanVO.setPager(pager);
		pager = syncRepaymentPlanService.findList(syncRepaymentPlanVO);
		syncRepaymentPlanVO.setPager(pager);
		List<SyncRepaymentPlanVO> loanList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}
	
	

	@RequestMapping("/syncPreRepayment")
	public String viewListsyncPreRepayment(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.SYNC_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("buildDateStart", DateUtil.getDateBefore(15));
		return "master/externalLoan/syncPreRepayment";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSyncPreRepayment")
	@ResponseBody
	public Map<String, Object> getSyncPreRepayment(SyncPreRepaymentInfoVO syncPreRepaymentInfoVO,int rows, int page) {

		//handle the end date
		if (syncPreRepaymentInfoVO.getBuildDateStart() == null) {
			syncPreRepaymentInfoVO.setBuildDateStart(DateUtil.getDateBefore(15));
		}
		if (syncPreRepaymentInfoVO.getBuildDateEnd() != null) {
			syncPreRepaymentInfoVO.setBuildDateEnd(DateUtil.getEndTime(syncPreRepaymentInfoVO.getBuildDateEnd()));
		}
		if (syncPreRepaymentInfoVO.getSendDateEnd() != null) {
			syncPreRepaymentInfoVO.setSendDateEnd(DateUtil.getEndTime(syncPreRepaymentInfoVO.getSendDateEnd()));
		}
		if(syncPreRepaymentInfoVO.getStatus()!=null && SELECTED_ALL.equals(syncPreRepaymentInfoVO.getStatus())){
			syncPreRepaymentInfoVO.setStatus(null);//状态选择所有
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		pager.setSidx("SYNC_PREREPAYMENT_INFO.ID");
		pager.setSort("DESC");
		syncPreRepaymentInfoVO.setPager(pager);
		pager = syncPreRepaymentInfoService.findList(syncPreRepaymentInfoVO);
		syncPreRepaymentInfoVO.setPager(pager);
		List<SyncPreRepaymentInfoVO> loanList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}


	@RequestMapping("/syncRepayInfo")
	public String viewListsyncRepayInfo(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.SYNC_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("buildDateStart", DateUtil.getDateBefore(15));
		return "master/externalLoan/syncRepayInfo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSyncRepayInfo")
	@ResponseBody
	public Map<String, Object> getSyncRepayInfo(SyncRepayInfoVO syncRepayInfoVO,int rows, int page) {

		//handle the end date
		if (syncRepayInfoVO.getBuildDateStart() == null) {
			syncRepayInfoVO.setBuildDateStart(DateUtil.getDateBefore(15));
		}
		if (syncRepayInfoVO.getBuildDateEnd() != null) {
			syncRepayInfoVO.setBuildDateEnd(DateUtil.getEndTime(syncRepayInfoVO.getBuildDateEnd()));
		}
		if (syncRepayInfoVO.getSendDateEnd() != null) {
			syncRepayInfoVO.setSendDateEnd(DateUtil.getEndTime(syncRepayInfoVO.getSendDateEnd()));
		}
		if(syncRepayInfoVO.getStatus()!=null && SELECTED_ALL.equals(syncRepayInfoVO.getStatus())){
			syncRepayInfoVO.setStatus(null);//状态选择所有
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		pager.setSidx("SYNC_REPAY_INFO.ID");
		pager.setSort("DESC");
		syncRepayInfoVO.setPager(pager);
		pager = syncRepayInfoService.findList(syncRepayInfoVO);
		syncRepayInfoVO.setPager(pager);
		List<SyncRepayInfoVO> loanList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/checkExportExcel")
	@ResponseBody
	public String preExportExcel(SyncLoanVO svo,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		log.info("|-----开始外债贷款导出数据检验....");
		String rname  = request.getParameter("name").toString();
		String idNum = request.getParameter("idNum");
		String contractNo = request.getParameter("contractNo");
		String rloanType   = request.getParameter("loanType").toString();
		String rbuildDateStart = request.getParameter("buildDateStart");
		String rbuildDateEnd   = request.getParameter("buildDateEnd");
		String rsendDateStart  = request.getParameter("sendDateStart");
		String rsendDateEnd    = request.getParameter("sendDateEnd");
		String rstatus = request.getParameter("status");
		
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		//转码获取中文
		String name = URLDecoder.decode(rname);
		
		String strLoanType = "";
		if(null != rloanType && rloanType != ""){
			Integer loanType = Integer.parseInt(rloanType);
			strLoanType = retLoanType(loanType);
		}
		
		Long status = null;
		if(null != rstatus && !"".equals(rstatus)){
			status = Long.parseLong(rstatus);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date buildDateStart = null;
		if(rbuildDateStart != null && rbuildDateStart != ""){
			buildDateStart = sdf.parse(rbuildDateStart);
		}
		
		Date buildDateEnd = null;
		if(rbuildDateEnd != null && rbuildDateEnd != ""){
			buildDateEnd = sdf.parse(rbuildDateEnd);
		}
		
		Date sendDateStart = null;
		if(rsendDateStart != null && rsendDateStart != ""){
			sendDateStart = sdf.parse(rsendDateStart);
		}
		
		Date sendDateEnd = null;
		if(rsendDateEnd != null && rsendDateEnd != ""){
			sendDateEnd = sdf.parse(rsendDateEnd);
		}
		
		SyncLoanVO vo = new SyncLoanVO();
		
		vo.setName(name);
		vo.setIdNum(idNum);
		vo.setContractNo(contractNo);
		vo.setLoanType(strLoanType);
		vo.setBuildDateStart(buildDateStart);
		vo.setBuildDateEnd(buildDateEnd);
		vo.setSendDateStart(sendDateStart);
		vo.setSendDateEnd(sendDateEnd);
		vo.setStatus(status);
		
		
		int loanCount = syncLoanService.syncLoanCount(vo);
		
		if(loanCount > 0){
			log.info("准备导出的数据条数为：" + loanCount);
		}else{
			log.info("没有数据可导出");
			return "没有数据可导出";
		}
		log.info("|-----结束外债贷款导出数据检验....");
		return "success";
	}
	
	
	/**
	 * 
	 * @Description: 外债贷款增加导出
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws ParseException 
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月18日
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		long startTime = System.currentTimeMillis();
		String rname  = request.getParameter("name");
		String idNum = request.getParameter("idNum");
		String contractNo = request.getParameter("contractNo");
		String rloanType   = request.getParameter("loanType");
		String rbuildDateStart = request.getParameter("buildDateStart");
		String rbuildDateEnd   = request.getParameter("buildDateEnd");
		String rsendDateStart  = request.getParameter("sendDateStart");
		String rsendDateEnd    = request.getParameter("sendDateEnd");
		String rstatus = request.getParameter("status");
		
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		String name = URLDecoder.decode(rname);
		
		String strLoanType = "";
		if(null != rloanType && rloanType != ""){
			Integer loanType = Integer.parseInt(rloanType);
			strLoanType = retLoanType(loanType);
		}
		
		Long status = null;
		if(null != rstatus && !"".equals(rstatus)){
			status = Long.parseLong(rstatus);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date buildDateStart = null;
		if(rbuildDateStart != null && rbuildDateStart != ""){
			buildDateStart = sdf.parse(rbuildDateStart);
		}
		
		Date buildDateEnd = null;
		if(rbuildDateEnd != null && rbuildDateEnd != ""){
			buildDateEnd = sdf.parse(rbuildDateEnd);
		}
		
		Date sendDateStart = null;
		if(rsendDateStart != null && rsendDateStart != ""){
			sendDateStart = sdf.parse(rsendDateStart);
		}
		
		Date sendDateEnd = null;
		if(rsendDateEnd != null && rsendDateEnd != ""){
			sendDateEnd = sdf.parse(rsendDateEnd);
		}
		
		SyncLoanVO vo = new SyncLoanVO();
		
		vo.setName(name);
		vo.setIdNum(idNum);
		vo.setContractNo(contractNo);
		vo.setLoanType(strLoanType);
		vo.setBuildDateStart(buildDateStart);
		vo.setBuildDateEnd(buildDateEnd);
		vo.setSendDateStart(sendDateStart);
		vo.setSendDateEnd(sendDateEnd);
		vo.setStatus(status);
		
		Product  product=null;
		Loan loan=null;
		
		List<SyncLoan> loanList = syncLoanService.findListBySyncLoanVO(vo);
		List<SyncLoan> loansList = new ArrayList<SyncLoan>();
		for (SyncLoan syncLoan : loanList) {
			if(syncLoan.getLoanId() !=null)
			loan = loanService.get(syncLoan.getLoanId());
			if(loan != null){
				if(loan.getProductId() !=null)
				 product = productService.get(loan.getProductId());
				if(loan.getGrantDate() != null){
					syncLoan.setGrantDate(loan.getGrantDate());
				}
			}
			if(product!=null)
			syncLoan.setLoanType(product.getProductName());
			loansList.add(syncLoan);
		}

		
		if(null != loansList){
			Log.info("准备导出的数据条数为：" + loansList.size());
		}
		
		Map map = new HashMap();
		map.put("loanType", vo.getLoanType());
		
		Date nowDate =new Date();
		// 文件名
		String fileName = "loanList" + DateUtil.defaultFormatMSDate(nowDate) + ".xlsx";
		String downloadPathfileName = "外债贷款" + DateUtil.defaultFormatDay(nowDate) + ".xlsx";
		String downloadPath = credit2Properties.getDownloadPath();
		File file = new File(downloadPath + File.separator + "report" + File.separator + "loanList"+ File.separator + DateUtil.defaultFormatDay(nowDate));
		if (!file.exists()) {// 不存在则创建该目录
			file.mkdirs();
		}
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "report" + File.separator + "loanList" + File.separator + DateUtil.defaultFormatDay(nowDate)+ File.separator + fileName.trim().toString());
			/**导出数据**/
			exportDatas(loansList,vo,map,os,response);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "导出excel出错";
		}
		// 下载Escel文件的路径
		String filePath = downloadPath + File.separator + "report" + File.separator + "loanList" + File.separator + DateUtil.defaultFormatDay(nowDate)+ File.separator + fileName;
		try {
			// 下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, downloadPathfileName, "xlsx")) {
				// 导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
			} else {
				return "导出excel出错";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "导出excel出错";
		}			
		long endTime = System.currentTimeMillis();
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.EXTERNAL_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EXTERNAL_LOAN_REPORT.getValue());
		sysLog.setRemark("外债贷款导出用时" +(double)(endTime - startTime) / 1000 +"秒");
		sysLogService.insert(sysLog);
		log.info("插入系统日志结束");
		return "success";
	}
	
	//真正导出数据
	public void exportDatas(List<SyncLoan> loansList,SyncLoanVO vo,Map map,OutputStream os,HttpServletResponse response){
		try{
			
			XSSFWorkbook wb = new XSSFWorkbook();
			//行显示样式
			XSSFCellStyle cellStyle_CN = wb.createCellStyle();//创建数据单元格样式(数据库数据样式)
		 	cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN); 
			cellStyle_CN.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中       
			cellStyle_CN.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			cellStyle_CN.setWrapText(false);//不换行显示
			
			//标头
			XSSFCellStyle condition_cellStyle = wb.createCellStyle();//创建一个单元格样式
			XSSFFont condition_font = wb.createFont();
			condition_font.setFontName("宋体");//设置头部字体为宋体
			condition_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			//condition_font.setFontHeightInPoints((short)20);
			condition_cellStyle.setFont(condition_font);//单元格样式使用字体
			condition_cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			condition_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			condition_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			condition_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			condition_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			condition_cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中 
			condition_cellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
			condition_cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			condition_cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			
			//内容
			XSSFCellStyle con_style = wb.createCellStyle();//创建一个单元格样式
			con_style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			con_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			con_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			con_style.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			con_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			con_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中 
			
			//标题
			XSSFCellStyle titlest = wb.createCellStyle();//创建一个单元格样式
			XSSFFont title_font = wb.createFont();
			title_font.setFontName("宋体");//设置头部字体为宋体
			title_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			title_font.setFontHeightInPoints((short)40);
			titlest.setFont(title_font);//单元格样式使用字体
			titlest.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			titlest.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			titlest.setBorderRight(XSSFCellStyle.BORDER_THIN);
			titlest.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			titlest.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中   
			
			
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			/*XSSFFont font = wb.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			style.setFont(font);*/
			//标题
			String[] title = {"借款编号","第三方机构编号","第三方机构名称","债权编号","客户姓名","身份证号","职业类型","产品类型",
					"借款金额/合同金额","借款期限","债权到期日/还款到期日","放款日期","用途","月还款能力","同步状态",
					"异常信息","生成时间","请求时间","反馈时间"};
			log.info("sheet 脚本创建成功。");
			
			XSSFSheet sheet = wb.createSheet();
			sheet.setDefaultColumnWidth(18);
			
			XSSFCell cell1 = null;
			XSSFRow row = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,18));
			sheet.getNumMergedRegions() ;
			row.setHeight((short)1000);
			cell1 = row.createCell(0);
			cell1.setCellStyle(titlest);
			cell1.setCellValue("外债贷款");
			
			XSSFCell cell2 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			row = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,18));
			cell2 = row.createCell(0);
			if(null != vo.getBuildDateStart() && null != vo.getBuildDateEnd()){
				cell2.setCellValue("生成时间：" + sdf.format(vo.getBuildDateStart()) + " ~ " + sdf.format(vo.getBuildDateEnd()) );
			}else if (null != vo.getBuildDateStart() && null == vo.getBuildDateEnd()){
				cell2.setCellValue("生成时间：" + sdf.format(vo.getBuildDateStart()) + " ~ " );
			}else if (null == vo.getBuildDateStart() && null != vo.getBuildDateEnd()){
				cell2.setCellValue("生成时间：" + " ~ " + sdf.format(vo.getBuildDateEnd())) ;
			}else{
				cell2.setCellValue("生成时间：" );
			}
				
			row = sheet.createRow(2);
			cell2 = row.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(2,2,0,18));
			if(null != vo.getSendDateStart() && null != vo.getSendDateEnd()){
				cell2.setCellValue("请求时间：" + sdf.format(vo.getSendDateStart()) + " ~ " + sdf.format(vo.getSendDateEnd()) );
			}else if (null != vo.getSendDateStart() && null == vo.getSendDateEnd()){
				cell2.setCellValue("请求时间：" + sdf.format(vo.getSendDateStart()) + " ~ " );
			}else if (null == vo.getSendDateStart() && null != vo.getSendDateEnd()){
				cell2.setCellValue("请求时间：" + " ~ " + sdf.format(vo.getSendDateEnd())) ;
			}else{
				cell2.setCellValue("请求时间：" );
			}
			
			XSSFCell cell4 = null;
			row = sheet.createRow(4);
			for(int ti = 0; ti < title.length; ti++){
				cell4 = row.createCell(ti);
				cell4.setCellStyle(condition_cellStyle);
				cell4.setCellValue(title[ti]);
			}
			for(int i=0;i<loansList.size();i++){
				 row = sheet.createRow(i+5);
					XSSFCell cell = null;
					//for(int j=0;j<title.length;j++){
						cell = row.createCell(0);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getLoanId())
							cell.setCellValue(loansList.get(i).getLoanId());
						else
							cell.setCellValue("");
						
						cell = row.createCell(1);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getCompanyNo())
							cell.setCellValue(loansList.get(i).getCompanyNo());
						else
							cell.setCellValue("");
						
						cell = row.createCell(2);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getCompanyName())
							cell.setCellValue(loansList.get(i).getCompanyName());
						else
							cell.setCellValue("");
						
						cell = row.createCell(3);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getContractNo())
							cell.setCellValue(loansList.get(i).getContractNo());
						else
							cell.setCellValue("");
						
						cell = row.createCell(4);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getName())
							cell.setCellValue(loansList.get(i).getName());
						else
							cell.setCellValue("");
						
						cell = row.createCell(5);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getIdNum())
							cell.setCellValue(loansList.get(i).getIdNum());
						else
							cell.setCellValue("");
						
						cell = row.createCell(6);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getProfessionType())
							cell.setCellValue(loansList.get(i).getProfessionType());
						else
							cell.setCellValue("");
						
						cell = row.createCell(7);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getLoanType())
							cell.setCellValue(loansList.get(i).getLoanType());
						else
							cell.setCellValue("");
						
						cell = row.createCell(8);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getPactMoney())
							cell.setCellValue(loansList.get(i).getPactMoney());
						else
							cell.setCellValue("");
						
						cell = row.createCell(9);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getPactTime())
							cell.setCellValue(sdf.format(loansList.get(i).getPactTime()));
						else
							cell.setCellValue("");
						
						cell = row.createCell(10);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getEndRepayDate())
							cell.setCellValue(sdf.format(loansList.get(i).getEndRepayDate()));
						else
							cell.setCellValue("");
						
						cell = row.createCell(11);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getSignDate())
							cell.setCellValue(sdf.format(loansList.get(i).getSignDate()));
						else
							cell.setCellValue("");
						
						cell = row.createCell(12);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getPurpose())
							cell.setCellValue(loansList.get(i).getPurpose());
						else
							cell.setCellValue("");
						
						cell = row.createCell(13);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getMaxRepayAmount())
							cell.setCellValue(loansList.get(i).getMaxRepayAmount());
						else
							cell.setCellValue("");
						
						cell = row.createCell(14);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getStatus())
							cell.setCellValue(loansList.get(i).getStatus());
						else
							cell.setCellValue("");
						
						cell = row.createCell(15);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getMsg())
							cell.setCellValue(loansList.get(i).getMsg());
						else
							cell.setCellValue("");
						
						cell = row.createCell(16);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getBuildDate())
							cell.setCellValue(sdf.format(loansList.get(i).getBuildDate()));
						else
							cell.setCellValue("");
						
						cell = row.createCell(17);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getSendDate())
							cell.setCellValue(sdf.format(loansList.get(i).getSendDate()));
						else
							cell.setCellValue("");
						
						cell = row.createCell(18);
						cell.setCellStyle(con_style);
						if(null != loansList.get(i).getReturnDate())
							cell.setCellValue(sdf.format(loansList.get(i).getReturnDate()));
						else
							cell.setCellValue("");
						
				}
			wb.write(os);
			os.flush();
			os.close();
			log.info("导出结束！");
		} catch (Exception exception) {
			log.error("导出失败！",exception);
		}
	}
	
	private String retLoanType(Integer loanType){
		String strLoanType = "";
		switch(loanType){
			case 1 : 
				strLoanType = "小企业贷";
				break;
			case 2 : 
				strLoanType = "车贷";
				break;
			case 4 : 
				strLoanType = "车贷新产品";
				break;
			case 5 : 
				strLoanType = "同城POS贷";
				break;
			case 6 : 
				strLoanType = "同城小微贷";
				break;
			case 7 : 
				strLoanType = "网商贷";
				break;
			case 8 : 
				strLoanType = "助学贷";
				break;
		}
		return strLoanType;
	}
}
