package com.ezendai.credit2.sign.lcb.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * 电子签章
 * @author YM10159
 */
@Service
public class EleSignatureService {
	private static final Logger logger = Logger.getLogger(EleSignatureService.class);
	//电子签章模版路径
	@Value("${eleSignaturePath}")
	private String eleSignaturePath;
	//本地合同存放路径
	@Value("${windowsPath}")
	private String windowsPath;
	@Value("${linuxPath}")
	private String linuxPath;
	//PIC合同文件上传接口
	@Value("${picContractUpload}")
	private String picContractUpload;
	//电子签章合同推送
	@Value("${eleSignatureSysPath}")
	private String eleSignatureSysPath;
	//存管网关接口地址
	@Value("${lcbService}")
	private String lcbGatewayPath;
	//密钥
	@Value("${lcbSignSecretKey}")
	private String lcbSignSecretKey;
	//密钥
	@Value("${eleSignNameCallbackPath}")
	private String eleSignNameCallbackPath;
	
		
	@Autowired
	private IContractGenerateDao contractGenerateDao;
	
	/**
	 * description:获取合同模版文件路径
	 * autor:ym10159
	 * date:2018年1月16日 下午3:24:25
	 */
	public List<String> getContractTempletePath(){
		List<String> list = new ArrayList<>();
		File file = new File(eleSignaturePath);
		for (File f : file.listFiles()) {
			list.add(f.getPath());
		}
		return list;
	}

	/**
	 * description:获取合同模版
	 * autor:ym10159
	 * date:2018年1月16日 上午10:20:30
	 * @param fileType 文件类型
	 */
	public String getContractTempleteHtml(String templetePath) {
		InputStream is = null; 
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String lineStr = "";
		try {
			is = new FileInputStream(new File(templetePath));
			br = new BufferedReader(new InputStreamReader(is));
			while ((lineStr = br.readLine()) != null) {
				sb.append(lineStr);
			}
		} catch (Exception e) {
			logger.error("电子签章模版读取异常",e);
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				logger.error("电子签章模版读取流关闭异常",e);
			}
		}
		return sb.toString();
	}


	/**
	 * description:把html模版中的占位符${}替换为真实的数据
	 * autor:ym10159
	 * date:2018年1月16日 上午9:46:00
	 * @param content html模版
	 * @param map 填充的内容
	 * @return 填充后的html模版
	 */
	public String replaceText(String content, Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return content;
		}

		Pattern pattern = Pattern.compile("\\$\\{(\\w*)\\}");
		Matcher matcher = pattern.matcher(content);
		String value = null;
		Object temp = null;
		while (matcher.find()) {
			temp = map.get(matcher.group(1));
			value = temp == null ? "" : temp.toString();
			content = content.replace(matcher.group(), value);
		}
		return content;
	}

	/**
	 * description:把html文件转换成pdf文件
	 * autor:ym10159
	 * date:2018年1月16日 上午11:41:20
	 * @param content html文件内容
	 * @param fileName 文件名称
	 */
	public String htmlToPdf(String content, String code)  {
		Document document = new Document(PageSize.A4);
		FileOutputStream fos = null;
		PdfWriter writer = null;
		String contractPathRoot = getContractPath();
		String date = DateUtil.getDate(new Date(), "yyyyMMdd");
		String uuId = UUID.randomUUID().toString().replaceAll("-","");
		String contractPath = contractPathRoot + date + File.separator + code + File.separator;
		//创建目录
		File file = new File(contractPath);
		if(!file.exists()){
			file.mkdirs();
		}
		//删除之前生成的文件
		File[] fileArr = file.listFiles();
		if(fileArr.length > 0){
			for (File f : fileArr) {
				f.delete();
			}
		}
		//生成pdf
		String pdfContractPath = contractPath + uuId + ".pdf";
		try {
			fos = new FileOutputStream(pdfContractPath);
			writer = PdfWriter.getInstance(document, fos);
			writer.setViewerPreferences(PdfWriter.HideToolbar);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,new ByteArrayInputStream(content.getBytes("utf-8")),Charset.forName("UTF-8"));
			logger.info("html文件转换pdf成功");
			return pdfContractPath;
		} catch (Exception e) {
			logger.error("html文件转换pdf失败，异常信息：", e);
		} finally {
			document.close();
			writer.close();
			IOUtils.closeQuietly(fos);
		}
		return null;
	}

	/**
	 * description:PIC上传合同文件
	 * autor:ym10159
	 * date:2018年1月16日 下午2:00:49
	 */
	public String uploadContract(Map<String, Object> paramMap) {
		String retUrl = "";
		String filePath = ObjectUtils.toString(paramMap.get("filePath"));
		paramMap.remove("filePath");

		StringBuffer urlBuf = new StringBuffer(picContractUpload + "api/filedata/uploadfile").append("?");
		Iterator<String> keys = paramMap.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			try {
				urlBuf.append(key).append("=").append(URLEncoder.encode(String.valueOf(paramMap.get(key)),"utf-8")).append("&");
			} catch (UnsupportedEncodingException e) {}
		}
		String url=urlBuf.toString().substring(0, urlBuf.toString().length()-1);

		PostMethod filePost = new PostMethod(url);
		File file = new File(filePath);
		
		FilePart filePart = null;
		HttpClient httpClient = null;
		try {
			filePart = new FilePart("file",file.getName(), file);
			String mimeType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
			filePart.setContentType(mimeType);
			Part[] parts =new Part[]{filePart};
			MultipartRequestEntity mre = new MultipartRequestEntity(parts, filePost.getParams());
			filePost.setRequestEntity(mre);
			filePost.setRequestHeader("Content-Disposition", "form-data");  
			
			httpClient = new HttpClient();
			int status = httpClient.executeMethod(filePost);
			
			if(HttpStatus.SC_OK == status){
				String result = filePost.getResponseBodyAsString();
				logger.info("借款ID【"+paramMap.get("appNo")+"】->PIC上传合同文件响应结果："+result);
				
				JSONObject jsonObj = JSON.parseObject(result);
				if("000000".equals(jsonObj.getString("errorcode"))){
					JSONObject resultObj = jsonObj.getJSONObject("result");
					String fileUrl = resultObj.getString("url");
					retUrl = fileUrl.substring(fileUrl.indexOf("/car"),fileUrl.length());
				}
			}
		} catch (IOException e) {
			logger.error("借款ID【"+paramMap.get("appNo")+"】->PIC上传合同文件异常",e);
		}
		return retUrl;
	}

	/**
	 * description:电子签章-推送合同待签约信息
	 * autor:ym10159
	 * date:2018年1月16日 下午1:57:10
	 */
	public JSONObject pushSignContract(String param) {
		JSONObject jsonObj = JSON.parseObject(param);
		String loanId = jsonObj.getString("appNo");
		logger.info("借款ID【"+loanId+"】->推送待签约合同信息接口请求参数："+param);
		
		PostMethod postMethod = null;
		try {
			HttpClient httpClient = new HttpClient();
			postMethod = new PostMethod(eleSignatureSysPath+"pushSignContract");
			RequestEntity requestEntity = new StringRequestEntity(jsonObj.toString(), "application/json","UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String reaponse = postMethod.getResponseBodyAsString();
			logger.info("借款ID【"+loanId+"】->推送待签约合同信息接口响应："+reaponse);
			
			return JSON.parseObject(reaponse);
		} catch (Exception e) {
			logger.error("借款ID【"+loanId+"】->推送待签约合同信息接口异常：", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return null;
	}
	
	/**
	 * description:电子签章-签名
	 * autor:ym10159
	 * date:2018年1月25日 上午11:50:41
	 * @param param
	 */
	public String loginPcClientSignature(String loanId) {
		String url = getSignNamePageUrl(loanId);
		logger.info("借款ID【"+loanId+"】->调用电子签章系统签章接口请求参数："+url);
		PostMethod postMethod = null;
		try {
			HttpClient httpClient = new HttpClient();
			postMethod = new PostMethod(url);
			httpClient.executeMethod(postMethod);
			String reaponse = postMethod.getResponseBodyAsString();
			logger.info("借款ID【"+loanId+"】->调用电子签章系统签章接口响应："+reaponse);
			return reaponse;
		} catch (Exception e) {
			logger.error("借款ID【"+loanId+"】->调用电子签章系统签章接口异常：", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * description:操作系统
	 * autor:ym10159
	 * date:2018年1月16日 下午3:13:08
	 */
	public static String osType(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")) return "windows";
		else return "linux";
	}

	/**
	 * description:获取合同路径
	 * autor:ym10159
	 * date:2018年1月16日 下午3:13:33
	 */
	private String getContractPath(){
		String osType = osType();
		if(osType.equals("windows")) return windowsPath;
		else return linuxPath;
	}
	
	public static String buildUrlWithQueryString(Map<String,Object> map,String path){
		StringBuilder sb = new StringBuilder();
		sb.append(path).append("?");
		for (Entry<String, Object> entry : map.entrySet()) {
        	String k = ObjectUtils.toString(entry.getKey());
        	String v = ObjectUtils.toString(entry.getValue());
            if(StringUtils.isNotBlank(v)){
            	try {
					v = URLEncoder.encode(v, "utf-8");
				} catch (UnsupportedEncodingException e) {}
            }
            sb.append(k).append('=').append(v);
            sb.append('&');
        }
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
	/**
	 * description:调用电子签章系统签章页面
	 * autor:ym10159
	 * date:2018年1月29日 上午11:50:45
	 * @param loanId 借款ID
	 * @param String 电子签章接口地址
	 */
	public String getSignNamePageUrl(String loanId){
		String callbackURL = eleSignNameCallbackPath+"eleSignCallback?loanId="+loanId;
		logger.info("借款ID【"+loanId+"】->打开电子签章签名页面开始，callbackURL="+callbackURL);
		
		Map<String,Object> map = new HashMap<>();
		map.put("appNo", loanId);
		map.put("returnUrl", callbackURL);
		
		String signPageUrl = buildUrlWithQueryString(map,eleSignatureSysPath+"loginPcClientSignature");
		logger.info("借款ID【"+loanId+"】->打开电子签章签名页面结束，signPageUrl="+signPageUrl);
		
		return signPageUrl;
	}
	
	/**
	 * description:调用捞财宝电子签章系统签章页面
	 * autor:ym10159
	 * date:2018年1月29日 上午11:50:45
	 * @param loanId 借款ID
	 */
	public String getLcbSignNamePageUrl(String loanId){
		String signPageUrl = "";
		LcbModel lcbModel = contractGenerateDao.getPersonInfoByLoanId(loanId);
		String callbackURL = eleSignNameCallbackPath+"lcbEleSignCallback?loanId="+loanId;
		logger.info("借款ID【"+loanId+"】->打开捞财宝电子签章签名页面开始，callbackURL="+callbackURL);
		
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("borrowNo", lcbModel.getLcbBorrowNo());
		paramsMap.put("callbackURL",callbackURL);
		
		String result= HttpUtils.postGateway(lcbGatewayPath+"contractSign",paramsMap,lcbSignSecretKey,null);
		JSONObject jsonObject= JSON.parseObject(result);
		if(jsonObject.getString("repCode").equals("000000")){
			signPageUrl =  ObjectUtils.toString(jsonObject.get("url"));
		}
		logger.info("借款ID【"+loanId+"】->打开捞财宝电子签章签名页面结束,signPageUrl="+signPageUrl);
		
		return signPageUrl;
	}
}
