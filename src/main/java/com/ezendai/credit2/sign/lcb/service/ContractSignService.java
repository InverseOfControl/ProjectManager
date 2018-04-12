package com.ezendai.credit2.sign.lcb.service;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.sign.constant.ErrorCodeEnum;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;

/**
 * @description 合同签约
 * @author YM10159
 * @param <T> 请求对象
 * @param <U> 响应对象
 */
@SignHandler(flowNode="lcbContractSign")
public class ContractSignService<T,U> extends BaseSign<T, U> {
	
	private static final Logger logger = Logger.getLogger(ContractSignService.class);
	
	@Autowired
	private EleSignatureService eleSignatureService;
	@Autowired
	private IContractGenerateDao contractGenerateDao;
	
	@Override
	public boolean execute(T reqObj, U resObj) {
		Map<String,Object> reqMap = (Map<String, Object>) reqObj;
		Map<String,Object> resMap = (Map<String, Object>) resObj;
		String loanId = ObjectUtils.toString(reqMap.get("loanId"));
		
		if(!elSignName(loanId, resMap)){
			return false;
		}
		return true;
	}
	
	/**
	 * description:电子签章
	 * 1、先把html模版填充数据并转换未pdf格式的文件
	 * 2、把pdf合同文件推送到电子签章系统指定的文件目录
	 * autor:ym10159
	 * date:2018年1月15日 上午10:54:08
	 * @param loanId 借款编号
	 * @param resMap 响应对象
	 * @return boolean
	 */
	private boolean elSignName(String loanId,Map<String,Object> resMap) {
		logger.info("借款ID【"+loanId+"】->对接电子签章系统开始");
		Map<String,Object> loanInfoMap = contractGenerateDao.getLoanInfo(loanId);
		String contractCreatedTime = ObjectUtils.toString(loanInfoMap.get("CONTRACT_CREATED_TIME")); //合同生成日期
		
		if(!isSameDay(contractCreatedTime)){
			resMap.put("repCode", "000001");
			resMap.put("repMsg", "合同生成日期和合同签订日期必须是同一天");
			return false;
		}
		
		//获取合同模版
		List<String> contractTmpList = eleSignatureService.getContractTempletePath();
		for (String string : contractTmpList) {
			String code = new File(string).getName().split("_")[0];
			
			//单个合同模版路径
			String temepleteHtml = eleSignatureService.getContractTempleteHtml(string);
			//替换模版动态内容
			Map<String,Object> map = new HashMap<>();
			map.put("borrowerName", "张三");
			map.put("idNo", "11111111111111");
			String htmlContent = eleSignatureService.replaceText(temepleteHtml, map);
			//生成pdf
			String pdfContractPath = eleSignatureService.htmlToPdf(htmlContent, code);
			if(StringUtils.isBlank(pdfContractPath)){
				resMap.put("repCode", ErrorCodeEnum.HTML_TO_PDF_ERROR.getErrorCode());
				resMap.put("repMsg", ErrorCodeEnum.HTML_TO_PDF_ERROR.getDefaultMessage());
				return false;
			}
			//PIC上传合同
			Map<String,Object> uploadContractMap = new HashMap<>();
			uploadContractMap.put("sysName", "car");
			uploadContractMap.put("dataSources", "1");
			uploadContractMap.put("nodeKey", "contractAward");
			uploadContractMap.put("appNo",loanId);
			uploadContractMap.put("code",code);
			uploadContractMap.put("operator",ApplicationContext.getUser().getLoginName());
			uploadContractMap.put("jobNumber",ApplicationContext.getUser().getLoginName());
			uploadContractMap.put("filePath",pdfContractPath);
			String retUrl = eleSignatureService.uploadContract(uploadContractMap);
			
			if(StringUtils.isBlank(retUrl)){
				resMap.put("repCode", "000001");
				resMap.put("repMsg", "PIC上传合同文件失败");
				return false;
			}
			
			//合同上传成功之后，把上传的地址信息推送给签章系统
			Map<String,Object> paraMap = new HashMap<String,Object>();
			String retFileName = retUrl.substring(retUrl.lastIndexOf("/")+1, retUrl.length());
			retUrl = retUrl.substring(0,retUrl.lastIndexOf("/")+1);
			paraMap.put("appNo", loanId);
			paraMap.put("contractType",codeConvert(code));
			paraMap.put("saveDirectory",retUrl);
			paraMap.put("fileName", retFileName);
			paraMap.put("userName", loanInfoMap.get("NAME"));
			paraMap.put("idNum", loanInfoMap.get("IDNUM"));
			paraMap.put("mobile", loanInfoMap.get("MOBILE_PHONE"));
			paraMap.put("sysCode", "000002");
			paraMap.put("fundsSources", "捞财宝");
			
			JSONObject pushSignContractResultObj = eleSignatureService.pushSignContract(new JSONObject(paraMap).toString());
			
			if(null == pushSignContractResultObj || !"000000".equals(pushSignContractResultObj.getString("resCode"))){
				resMap.put("repCode", pushSignContractResultObj.getString("resCode"));
				resMap.put("repMsg", "推送待签约合同信息失败！");
				return false;
			}
		}
		logger.info("借款ID【"+loanId+"】->对接电子签章系统结束");
		return true;
	}
	
	public static String getCode(String path){
		return path.substring(path.lastIndexOf("\\")+1,path.length()).split("\\.")[0].split("_")[0];
	}
	
	/**
	 * description:协议类型转换
	 * autor:ym10159
	 * date:2018年1月26日 上午10:00:52
	 */
	public static String codeConvert(String code){
		String ret = "";
		if(code.equals("S8")) ret = "02";
		return ret;
	}
	
	public static boolean isSameDay(String contractCreatedTime) {  
	    Calendar calDateA = Calendar.getInstance();  
	    Calendar calDateB = Calendar.getInstance();  
	    try {
			calDateA.setTime(DateUtil.strToDateTime(contractCreatedTime));
			calDateB.setTime(new Date());  
		} catch (ParseException e) {}  
	  
	    if(calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) 
	    		&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH) 
	    		&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH)){
	    	return true;
	    }
	    return false;
	} 
	
	
	public static void main(String args[]){
		String str = "//var//www//htmlContractTemplete//S8_123.html";
		String path = str;
		System.out.println(path.substring(path.lastIndexOf("//")+1,path.length()).split("//.")[0].split("_")[0]);
	}
}
