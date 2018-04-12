package com.ezendai.credit2.system.service;

 
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

 

/**
 * @author LinSanfu
 */

public interface ExcelAnalyticalService {
	 
	
	/**
	 *  
	 * excel数据解析
	 *  
	 */
	 Map<String, Object>Analytical(String fileName,MultipartFile Mfile);

	 Map<String, Object> AnalyticalLoan(String fileName, MultipartFile Mfile);	
}
