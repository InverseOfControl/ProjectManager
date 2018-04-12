<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/master/comprehensiveSearch/comprehensiveMain.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/textResourceJsonHandle.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/ajaxfileupload.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
 <script type="text/javascript">var textResourceJson='${textResourceJson}';</script>
</head>
<body>
 	<table id="list_result" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>&nbsp;&nbsp;姓名：</span><input type="text" id="personName" maxlength="25"  style="width: 100px" />
        <span>联系电话：</span><input type="text" id="tel" maxlength="18"style="width: 100px" />
	    <span>身份证号：</span><input type="text" id="idNum" maxlength="18"style="width: 160px" />
     	<span>借款状态：</span><input id="status" class="easyui-combobox" editable="false" data-options="width:100" />
     	<span>客户经理：</span><input id="crmId" class="easyui-combobox" editable="false" data-options="width:100" />
     	<span>产品类型：</span><input id="productType" class="easyui-combobox"  editable="false"  data-options="width:100"/>  
     	<span>营业部<span><input class="easyui-combobox"  style="width:150px;"  id="salesDeptId">
     	<span>展期期次<span><input  id="extensionTimeComb"  editable="false" class="easyui-combobox" data-options="width:60" />
        </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	      <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	      <a href="javascript:void(0)" id="resetBt" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a> 
	      <div id="result"></div>
	     
		 </div>
	</div>
 	 
		 
		
	<!--台账对话框-->
	<div id="showLoanLedgerDlg" style="top: 20px;height:600px;width:1000px;">
		<table id="loanLedgerList"></table>
	</div>	
	
	 
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