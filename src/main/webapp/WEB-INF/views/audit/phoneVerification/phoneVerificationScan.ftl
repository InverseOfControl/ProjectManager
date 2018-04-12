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
	<script type="text/javascript" src="resources/js/audit/phoneVerificationCon.js"></script>
	<form id="replySeEditForm" name="replySeEditForm" method="post" >
    <div id="productTableDiv" style=" closed="true" data-options="resizable:true">
	    <table style="font-size:15px; width:100%; text-align:right;height: 100%;padding:20px">
	    <tr style="height:40px" >
	        <td style="width:60px" > 
        		 姓名 ： 
	        </td>
	        <td align="left" style="width:50px"> 
	        		 ${pv.name!""}
	        		 <input type="hidden" id="nameInput" value="${pv.name!""}"/>
	        		 <input type="hidden" id="companyInput" value="${pv.scName!""}"/>
	        		 <input type="hidden" id="loanIdInput" value="${pv.loanId!""}"/>
	        		 <input type="hidden" id="phoneInput" value="${pv.phone!""}"/>
	        		 <input type="hidden" id="homePhone" value="${pv.homePhone!""}"/>
	        		 <input type="hidden" id="homePhone" value="${pv.homePhone!""}"/>
	        		 <input type="hidden" id="parentPhone" value="${pv.parentPhone!""}"/>
	        		 <input type="hidden" id="colleaguePhone" value="${pv.colleaguePhone!""}"/>
	        		 <input type="hidden" id="personId" value="${pv.personId!""}"/>
	        		 <input type="hidden" id="companyId" value="${pv.companyId!""}"/>
	        </td>
	        <td style="width:40px" > 
        		 学校／公司名称:
        		
	        </td>
	        <td align="left" align="left" style="width:50px">  
        		  <input type="text" id="scName" value="${pv.scName!""}" onblur='updatePerson(this,2)'/>
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:20px" >  
        		 学校／公司地址：
	        </td>
	        <td align="left" style="width:50px"> 
        	  <input type="text" id="scName"  value="${pv.scAddress!""}" onblur='updatePerson(this,3)'/>
         
	        </td>
	        <td> 
        		 家庭地址 ：
	        </td>
	        <td align="left"> 
        		
        		   <input type="text" id="scName" value=" ${pv.homeAddress!""}" onblur='updatePerson(this,1)'/>
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td  > 
        		 电核记录： 
	        </td>
	        <td align="left"> 
	        </td>
	        <td> 
        		  
	        </td>
	        <td align="left">
	        </td>
	    </tr>
	       <tr style="height:40px" >
	        <td   > 
					借款人：${pv.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	手机号码：${pv.phone!""}
	        </td>
	        <td> 
        					
	        </td>
	    </tr>
	       <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 
	     		 <table  id="mytable" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	
	     		 	<#if (type1)?? && type1?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	</tr>
	     		 	<#list type1 as pv>  
  						<tr  style="height:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>     
  							<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert(this,0)'>${pv.relation!""}</textarea></td>  
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  							<td><textarea   name="name2"   style="width:300px;height:200px;"   readonly="true" onblur='dataInsert(this,1)'>${pv.content!""}</textarea></td> 
  							<td></td>
  						</tr>
	     		 	  
					</#list>  
	     		 	
	     		 	  <#else> 
	     		 	   <tr id="trHidden" style="text-align:center;display:none">
	     		 	   	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 	
	     		 	 
	     		 	 
	     		 </table>
	     		</td> 
	     </tr>
	    <tr style="height:40px" >
	        <td   > 
					借款人：${pv.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	固定电话：${pv.homePhone!""}
	        </td>
	        <td> 
        				
        					
        					
	        </td>
	    </tr>
	    <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 
	     		 <table  id="mytable2" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	
	     		 	<#if (type2)?? && type2?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 		
	     		 	</tr>
	     		 	<#list type2 as pv>  
  						<tr  style="width:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>  
  							<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert2(this,0)'>${pv.relation!""}</textarea></td> 
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  						 
  							<td><textarea  name="name2"  style="width:300px;height:200px;"readonly="true" onblur='dataInsert2(this,1)'>${pv.content!""}</textarea></td>  
  							<td></td>
  						</tr>
	     		 	  
					</#list>  
	     		 	
	     		 	  <#else> 
	     		 	   <tr id="trHidden2" style="text-align:center;display:none">
	     		 	    <td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 	
	     		 	 
	     		 	 
	     		 </table>
	     		</td> 
	     </tr>
	       <#if (pv2)??>    
	      
	      <tr  >
	        <td   > 
					${pv2.relation!""}：${pv2.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	手机号码：${pv2.colleaguePhone!""}
	        </td>
	        <td> 
	        </td>
	    </tr>
	     <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 <table  id="mytable3" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	<#if (type3)?? && type3?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 		
	     		 	</tr>
	     		 	<#list type3 as pv>  
  						<tr  style="width:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>  
  							<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert3(this,0)'>${pv.relation!""}</textarea></td> 
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  							<td> <textarea  name="name2"  style="width:300px;height:200px;" readonly="true" onblur='dataInsert3(this,1)' >${pv.content!""}</textarea> </td>   
  							<td></td>
  						</tr>
					</#list>  
	     		 	<#else> 
	     		 	   <tr id="trHidden3" style="text-align:center;display:none">
	     		 	   	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 </table>
	     		</td> 
	     </tr> 
	      </#if> 
	        <#if (pv3)??>    
	     <tr   >
	        <td   > 
					${pv3.relation!""}：${pv3.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	手机号码：${pv3.colleaguePhone!""}
	        </td>
	        <td> 
	        </td>
	    </tr>
	         <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 <table  id="mytable4" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	<#if (type4)?? && type4?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 		
	     		 	</tr>
	     		 	<#list type4 as pv>  
  						<tr  style="width:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>  
  							<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert4(this,0)'>${pv.relation!""}</textarea></td> 
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  								<td><textarea  name="name2"  style="width:300px;height:200px;"readonly="true" onblur='dataInsert4(this,1)'>${pv.content!""}</textarea></td>   
  							<td></td>
  						</tr>
					</#list>  
	     		 	<#else> 
	     		 	   <tr id="trHidden4" style="text-align:center;display:none">
	     		 	   	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 </table>
	     		</td> 
	     </tr>
	      </#if>  
	         <#if (pv4)??>    
	        <tr   >
	        <td   > 
					${pv4.relation!""}：${pv4.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	手机号码：${pv4.colleaguePhone!""}
	        </td>
	        <td> 
	        </td>
	    </tr>
	         <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 <table  id="mytable5" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	<#if (type5)?? && type5?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 		
	     		 	</tr>
	     		 	<#list type5 as pv>  
  						<tr  style="width:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>  
  							<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert5(this,0)'>${pv.relation!""}</textarea></td> 
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  							<td><textarea  name="name2"  style="width:300px;height:200px;"readonly="true" onblur='dataInsert5(this,1)'>${pv.content!""}</textarea></td>  
  							<td></td>
  						</tr>
					</#list>  
	     		 	<#else> 
	     		 	   <tr id="trHidden5" style="text-align:center;display:none">
	     		 	   	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 </table>
	     		</td> 
	     </tr>
	      </#if> 
	     <#if (pv5)??>    
	      <tr   >
	        <td   > 
					${pv5.relation!""}：${pv5.name!""}
	        </td>
	        <td align="left"> 
        	 
        		  
        		 
	        </td> 
	        <td > 
        		  	手机号码：${pv5.colleaguePhone!""}
	        </td>
	        <td> 
	        </td>
	    </tr>
	    	
	         <tr>
	     		 <td  colspan="4"   align="center"> 
	     		 <table  id="mytable6" border="1"  bordercolor="#a0c6e5" style="border-collapse:collapse" >
	     		 	<#if (type6)?? && type6?size gt 0>  
	     		 	<tr style="text-align:center">
	     			 	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 		
	     		 	</tr>
	     		 	<#list type6 as pv>  
  						<tr  style="width:200px;text-align:center"> 
  							<td style="display:none">${pv.id!""}</td>  
  								<td><textarea  name="name1"  style="width:100px;height:200px;"readonly="true" onblur='dataInsert6(this,0)'>${pv.relation!""}</textarea></td>  
  							<td> 
  									<#if pv.inquiryTime??> 
										${pv.inquiryTime?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
  							</td>  
  								<td><textarea  name="name2"  style="width:300px;height:200px;"readonly="true" onblur='dataInsert6(this,1)'>${pv.content!""}</textarea></td>
  							<td></td>
  						</tr>
					</#list>  
	     		 	<#else> 
	     		 	   <tr id="trHidden6" style="text-align:center;display:none">
	     		 	   	<td style="display:none">ID</td>
	     		 		<td style="width:100px">接听人关系</td>
	     		 		<td style="width:150px">时间</td>
	     		 		<td style="width:300px">电核内容</td>
	     		 	 </tr> 
	     		 	 </#if> 
	     		 </table>
	     		</td> 
	     </tr> 
	     </#if>                        
	    </table>
    </div>
   
    </form>
</body>
</html>