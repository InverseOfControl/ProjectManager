package com.ezendai.credit2.master.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.master.model.LoanChangeLog;
import com.ezendai.credit2.master.service.LoanChangeLogService;
import com.ezendai.credit2.master.vo.LoanChangeLogVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;


@Controller
@RequestMapping("/loanChangeLog")
public class LoanChangeLogController {
	private static final Logger logger = Logger.getLogger(LoanChangeLogController.class);
	
	@Autowired
	Credit2Properties credit2Properties;
	
	@Autowired
	LoanChangeLogService loanChangeLogService;
	
	@Autowired
	SysParameterService sysParameterService;
	
	@RequestMapping("/checkExportNum")
	@ResponseBody
	public String checkExportNum(HttpServletRequest request, HttpServletResponse response,LoanChangeLogVO loanChangeLogVO) throws ParseException {

		List<LoanChangeLog> loanChangeLogList=loanChangeLogService.findListByVO(loanChangeLogVO);
		Integer count = loanChangeLogList.size();
		if (count.compareTo(0) == 0) {
			return "没有可以符合条件的数据导出！";
		}
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
		if (sysParameter != null) {
			if (count.compareTo(Integer.valueOf(sysParameter.getParameterValue())) > 0) {
				return "excel导出条数过多！";
			}
		}
		return "success";
	}
	
	/***
	 * 
	 * <pre>
	 * 导出Excel
	 * </pre>
	 *
	 * @param loanVo
	 * @return
	 * @throws ParseException 
	 *
	 */
	@RequestMapping("/exportExcel/{operatorStartTime}/{operatorEndTime}")
	@ResponseBody
	public String exportExcel(HttpServletResponse response,@PathVariable("operatorStartTime")String operatorStartTime,@PathVariable("operatorEndTime")String operatorEndTime) throws ParseException {
		
		LoanChangeLogVO loanChangeLogVO = new LoanChangeLogVO();
		loanChangeLogVO.setOperatorStartTime(DateUtil.strToDateTime(operatorStartTime));
		loanChangeLogVO.setOperatorEndTime(DateUtil.strToDateTime(operatorEndTime));
		List<LoanChangeLog> loanChangeLogList=loanChangeLogService.findListByVO(loanChangeLogVO);
		
		String dates = DateUtil.getDate(new Date(), "yyyyMMdd");
		//文件名 
		String fileName = "合同生成日志表" + dates + ".xlsx";
		String downloadPath = credit2Properties.getDownloadPath();
//		File file = new File( downloadPath + File.separator + "report");
//		if (!file.exists()) {//不存在则创建该目录
//			file.mkdirs();
//		}
		File file2 = new File(  downloadPath + File.separator + "report"+ File.separator + "loanLogs");
		if (!file2.exists()) {//不存在则创建该目录
			file2.mkdirs();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "report" + File.separator + "loanLogs"+File.separator+fileName.trim().toString());
			//生成Excel文件			
			loanChangeLogService.exportExcel(loanChangeLogList,"合同生成日志", os);
		} catch (FileNotFoundException e) {
			logger.info("生成Excel文件出错"+e.getMessage ());
			e.printStackTrace();
		}
		//下载Excel文件的路径
		String filePath = downloadPath + File.separator + "report" + File.separator + "loanLogs"+File.separator+fileName.trim().toString() ;
		try {
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
				//插入系统日志  
//				SysLog sysLog = new SysLog();			
//				sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_AUDIT.getValue());
//				sysLog.setOptType(EnumConstants.OptionType.FINANCE_AUDIT_EXPORT.getValue());
//				sysLog.setRemark("审核明细表导出xcel");
//				sysLogService.insert(sysLog);
				return "success";
			} else {
				return "下载Excel出错!";
			}
		} catch (Exception e) {
			logger.error("下载Excel出错!",e);
		}
		return "success";

	}
}
