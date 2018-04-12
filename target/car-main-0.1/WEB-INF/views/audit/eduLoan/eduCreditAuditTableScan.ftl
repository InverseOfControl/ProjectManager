<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<title>审核检查表 </title>
<base href="${WebConstants.webUrl}${WebConstants.contextPath}/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="resources/css/swfupload/easyui.css" type="text/css">
<link rel="stylesheet" href="resources/css/swfupload/icon.css" type="text/css">
<link rel="stylesheet" href="resources/css/swfupload/m_style.css" type="text/css">
<script type="text/javascript" src="resources/js/jquery-1.8.0.min.js"
	charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.cookie.js"
	charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js"
	charset="utf-8"></script>
<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8"
	src="resources/js/jquery.validate.min.js"></script>
	
<script type="text/javascript">
    var loanId='${loanId}';
    var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
    
</script>
<script type="text/javascript" src="resources/js/audit/eduCreditAuditTableScan.js"></script>
<style type="text/css">
	table.m2 { margin-top:10px;
	 margin-bottom:10px;
	}
	table.m2 td { 
		padding:2px 0;
	}
	td.tdblue{
		background-color:#99BBE8;
		color:#0E2D5F;
	}
	.eduTable {
		background-color:rgb(241, 252, 254);
		height: 100%;
    	width: 100%;
    	margin-bottom: 0px;
	}
	.edutitle {
		font-size:18px;
		font-weight : bold;
	}
</style>
</head>
<body>
<div class="eduTable">
<form id="auditListForm" method="post" enctype="multipart/form-dat">
	<input type="hidden" id="id" name="id" />
	<input type="hidden" id="personid" name="personid" value="${loan.personId}" />
	<input type="hidden" id="loanId" name="loanId" value="${loanId}" />
	<table id="base" align="center" class="m2 ">
		<tr>
			<td colspan='8'><label class="edutitle">基本信息</label></td>
		</tr>
		<tr>
			<td class="tdblue">申请金额</td>
			<td><label>${loan.requestMoney}</label></td>
			<td class="tdblue">审核人员</td>
			<td><label>${auditName}</label></td>
			<td class="tdblue">日期</td>
			<td><label id="nowDate"></label></td>
			<td class="tdblue">销售名称</td>
			<td><label>${crmName}</label></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label>客户信息</label></td>
		</tr>
		<tr>
			<td class="tdblue">姓名</td>
			<td><label>${personName}</label></td>
			<td class="tdblue">性别</td>
			<td><label >
				<#if personDetails.sex??>
	      		  		 <#if personDetails.sex = "1">   
 							男
						  </#if>  
						   <#if personDetails.sex = "0">   
 							女
						  </#if>   
	      		  </#if>
			</label></td>
			<td class="tdblue">年龄</td>
			<td><label >${age}</label></td>
			<td ></td>
			<td><label></label></td>
		</tr>
		<tr>
			<td class="tdblue">身份证所在地</td>
			<td><label>${placeDomicile}</label></td>
			<td class="tdblue">婚姻状况</td>
			<td><label >
				<#if personDetails.married??>
	      		  		 <#if personDetails.married = 0>   
 							未婚
						  </#if>  
						   <#if personDetails.married = 1>   
 							已婚
						  </#if> 
						  <#if personDetails.married = 2>   
 							离异
						  </#if>  
						   <#if personDetails.married = 3>   
 							再婚
						  </#if> 
						  <#if personDetails.married = 4>   
 							丧偶 
						  </#if>  
						   <#if personDetails.married = 5>   
 							其它
						  </#if> 
	      		  </#if>
			</label></td>
			<td class="tdblue">住房状况</td>
			<td><label>${liveType}</label></td>
			<td ></td>
			<td><label></label></td>
		</tr>
		<tr>
			<td class="tdblue">身份证</td>
			<td colspan=''><label>${personDetails.idnum}</label></td>
			 <td class="tdblue">办公地状况</td>
			<td><input id="compAddStatus" name="compAddStatus" class="easyui-validatebox "></input></td>
		<td class="tdblue">单位租赁有效期</td>
			<td><input
				class="Wdate easyui-validatebox validatebox-text"
				id="rentDate" name="rentDate" style="width: 140px;"
				onfocus="WdatePicker()" type="text" /></td>
			<td class="tdblue"><label style="color:red" >*</label>核实收入</td>
			<td><input id="incomePerMonth" name="incomePerMonth" class="easyui-validatebox validateItem"></input></td>
		</tr>
		<tr>
			<td colspan='8'  class="edutitle"><label>流水负债</label></td>
		</tr>
		<tr>
			<td class="tdblue">公司名称</td>
			<td><input id="companyName" name="companyName" class="easyui-validatebox "></input></td>
			<td class="tdblue">公司成立时间</td>
			<td><input
				class="Wdate easyui-validatebox validatebox-text"
				id="foundedDate" name="compCreateDate" style="width: 140px;"
				onfocus="WdatePicker()" type="text" /></td>
			<td class="tdblue">主营业务</td>
			<td><input id="majorBusiness" name="majorBusiness" class="easyui-validatebox "></input></td>
			
		</tr>
		<tr>
			<td class="tdblue">占股比例</td>
			<td><input id="ratioOfInvestments" name="ratioOfInvestments" class="easyui-validatebox " validType="integerCheck" maxlength="3"></input>%</td>
			<td class="tdblue">注册资金</td>
			<td><input id="compRegAmount" name="compRegAmount" class="easyui-validatebox " validType="moneyCheck"></input></td>
			<td class="tdblue">经营时间</td>
			<td><input id="businessTime" name="businessTime" class="easyui-validatebox "></input></td>
			
		</tr>
		<tr>
		<td colspan='8'>
		<br/>
		</td>
		</tr>
		<tr >
		<td colspan='8' >
		<table id="ToC" width="100%" class="m1">
			<tr >
			<td class="tdblue" width="140">对公贷</td>
			<td width="160" >1</td>
			<td width="160" class="tdblue">2</td>
			<td width="160" >3</td>
			<td width="160" class="tdblue">4</td>
			<td width="160" >5</td>
			<td width="160" class="tdblue" >6</td>
			<td></td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td colspan='8' id="CSum">
		</td>
		</tr>
		<tr>
		<td colspan='8'>
		<br/>
		</td>
		</tr>
		<tr >
		<td colspan='8'>
		<table id="ToP" width="100%" class="m1">
			<tr >
			<td class="tdblue" width="140">对私贷</td>
			<td width="160">1</td>
			<td class="tdblue" width="160">2</td>
			<td width="160">3</td>
			<td width="160" class="tdblue">4</td>
			<td width="160" >5</td>
			<td width="160" class="tdblue">6</td>
			<td></td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td colspan='8' id='PSum'>
		</td>
		</tr>
		<tr>
		<td colspan='8'>
		<br/>
		</td>
		</tr>
		<tr>
			<td class="tdblue">信贷负债</td>
			<td><input id="creditAmount" name="creditAmount" type="number"
				class="easyui-validatebox " /></td>
			<td class="tdblue">贷款负债</td>
			<td><input id="auditAmount" name="auditAmount" type="number"
				class="easyui-validatebox " /></td>
			<td class="tdblue">信贷月还款</td>
			<td><input id="auditMonthAmount" name="auditMonthAmount"
				type="number" class="easyui-validatebox " /></td>
			<td class="tdblue">其他</td>
			<td><input id="otherAmount" name="otherAmount" type="number"
				class="easyui-validatebox " /></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label>网查情况</label></td>
		</tr>
		<tr>
			<td class="tdblue">工商企业信息</td>
			<td><input id="companyInfo" name="companyInfo" class="easyui-validatebox "></input></td>
			<td class="tdblue">信贷管理系统</td>
			<td><input id="auditSystem" name="auditSystem" class="easyui-validatebox "></input></td>
			<td class="tdblue">网查黑名单</td>
			<td><input id="blackList" name="blackList" class="easyui-validatebox "></input></td>
			<td class="tdblue">网查负面信息</td>
			<td><input id="blackInfo" name="blackInfo" class="easyui-validatebox "></input></td>
		</tr>
		<tr>
			<td class="tdblue">企业法院执行记</td>
			<td><input id="courtCompinfo" name="courtCompinfo" class="easyui-validatebox "></input></td>
			<td class="tdblue">个人法院执行记</td>
			<td><input id="courtPersonInfo" name="courtPersonInfo" class="easyui-validatebox "></input></td>
			<td ></td>
			<td></td>
			<td ></td>
			<td></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label>经营历史及家庭情况</label></td>
		</tr>
		<tr>
			<td colspan='8'><textarea id="passInfo" name="passInfo" rows="2"
					cols="70" maxlength="140" class="easyui-validatebox " /></textarea></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label>盈利模式及经营现状</label></td>
		</tr>
		<tr>
			<td colspan='8'><textarea id="nowInfo" name="nowInfo" rows="2"
					cols="70" maxlength="140" class="easyui-validatebox " /></textarea></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label>综合评价</label></td>
		</tr>
		<tr>
			<td colspan='8'><textarea id="evaluationOverall"
					name="evaluationOverall" rows="2" cols="70" maxlength="140" class="easyui-validatebox " /></textarea></td>
		</tr>
		<tr>
			<td colspan='8' class="edutitle"><label style="color:red" >*</label><label>初审结论</label></td>
		</tr>
		<tr>
			<td colspan='8'><textarea id="auditResult" name="auditResult"
					rows="2" cols="70" maxlength="140" class="easyui-validatebox " readonly/></textarea></td>
		</tr>
		<#if inType=='2'>
		<tr>
			<td colspan='8' class="edutitle"><label style="color:red" >*</label><label>终审结论</label></td>
		</tr>
		<tr>
			<td colspan='8'><textarea id="auditResultFinal" name="auditResultFinal"
					rows="2" cols="70" maxlength="140" class="easyui-validatebox validateItem" /></textarea></td>
		</tr>
		</#if>
	</table>
</form>
</div>
</body>
</html>