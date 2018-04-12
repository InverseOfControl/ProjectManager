<#setting url_escaping_charset='UTF-8'>
<#macro htmlCommonHead>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<link rel="shortcut icon" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${WebConstants.webReportUrl}${WebConstants.contextReportPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<title>${title!''} </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/easyui.css" />
<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/icon.css" />
<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/m_style.css" />
<#if staticName??><link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/<#if staticPath??>${staticPath}/</#if>${staticName}.css"></#if>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.cookie.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/m_common.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/m_fuc.js"></script>
<#if staticName??><script type="text/javascript" charset="UTF-8" src="resources/js/<#if staticPath??>${staticPath}/</#if>${staticName}.js"></script></#if>
</#macro>