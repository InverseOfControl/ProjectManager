<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript">var userType='${userType}';var groupId='${groupId}';</script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/system/organ/organList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/organ/organAdd.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
<script type="text/javascript">
var enumJson='${gridEnumJson}';
</script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>机构内部编号：</span><input  id="code" name="code" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
	     <span>机构名称：</span><input   id="name" name="name" maxlength="25"  style="width: 90px" class="easyui-validatebox"/> 
      </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	          <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <!--客户经理,客服,复核客服,副理,经理 ，客户呼入组，区域经理-->
	        <#if !(userType == 2 || userType == 3 || userType == 5 || userType == 6 || groupId == 55 || userType == 12)>
	           <a id="createBt" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="organAdd()">新建</a>
	        </#if>
		 </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
 	
 	 <!-- 新增机构对话框 -->
    <div id="organAddDlg"  style="top: 20px;height:600px;width:1020px;"></div>  
     <!-- 查看机构对话框 -->
	<div id="organViewDlg" style="top: 20px;height:600px;width:1020px;"></div>   
     <!-- 编辑机构对话框 -->
	<div id="organEditDlg" style="top: 20px;height:600px;width:1020px;"></div>   
     
	<script>
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>