package com.ezendai.credit2.system;

public class Credit2Properties {

	private String webUrl;

	private String uploadPath;
	//下载文件的临时目录
	private String downloadPath;	
	//个贷service地址
	private String creditServiceUrl;	
	//redit2-report 地址
	public static String webReportUrl;
	//redit2-report 名称
	public static String contextReportPath;
	//征信地址
	public static String honorUrl;
	//个贷service地址
	private String creditUnifiedServiceUrl;	
	
	private String webJobUrl;

	
	private String ftpHost;
	private String ftpPort;
	private String ftpUserName;
	private String ftpPassWord;
	private String ftpRemotePath; 
	
	public String getCreditUnifiedServiceUrl() {
		return creditUnifiedServiceUrl;
	}

	public void setCreditUnifiedServiceUrl(String creditUnifiedServiceUrl) {
		this.creditUnifiedServiceUrl = creditUnifiedServiceUrl;
	}

	
	public static String getHonorUrl() {
		return honorUrl;
	}

	public static void setHonorUrl(String honorUrl) {
		Credit2Properties.honorUrl = honorUrl;
	}
	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassWord() {
		return ftpPassWord;
	}

	public void setFtpPassWord(String ftpPassWord) {
		this.ftpPassWord = ftpPassWord;
	}

	public String getFtpRemotePath() {
		return ftpRemotePath;
	}

	public void setFtpRemotePath(String ftpRemotePath) {
		this.ftpRemotePath = ftpRemotePath;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getCreditServiceUrl() {
		return creditServiceUrl;
	}

	public void setCreditServiceUrl(String creditServiceUrl) {
		this.creditServiceUrl = creditServiceUrl;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public static String getContextReportPath() {
		return contextReportPath;
	}

	public static void setContextReportPath(String contextReportPath) {
		Credit2Properties.contextReportPath = contextReportPath;
	}

	public static String getWebReportUrl() {
		return webReportUrl;
	}

	public static void setWebReportUrl(String webReportUrl) {
		Credit2Properties.webReportUrl = webReportUrl;
	}

	public String getWebJobUrl() {
		return webJobUrl;
	}

	public void setWebJobUrl(String webJobUrl) {
		this.webJobUrl = webJobUrl;
	}
	
}
