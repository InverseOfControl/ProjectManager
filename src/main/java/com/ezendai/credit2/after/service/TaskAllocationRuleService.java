package com.ezendai.credit2.after.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ezendai.credit2.after.model.LateDetails;
import com.ezendai.credit2.after.model.TaskAllocationRule;
import com.ezendai.credit2.after.vo.TaskAllocationRuleVO;
import com.ezendai.credit2.framework.util.Pager;


/***
 * 
 * @author liyuepeng
 *
 */
public interface TaskAllocationRuleService {
	Pager TaskAllocationRuleWithPG(TaskAllocationRuleVO ruleVO);
	void insertTaskAllocationRule (TaskAllocationRule rule);
	void updateTaskAllocationRule (TaskAllocationRule rule);
	 List<LateDetails> getLateDetailsList(TaskAllocationRuleVO vo); 
     void exportExcel( String sheetName, OutputStream os, List<LateDetails> excelRepaymentPlan,TaskAllocationRuleVO vo ) throws ParseException ;
	List<TaskAllocationRule> getExcelInfo(String name, MultipartFile file);
	 int getLateDetailsListCount(TaskAllocationRuleVO ruleVO) ;
}
