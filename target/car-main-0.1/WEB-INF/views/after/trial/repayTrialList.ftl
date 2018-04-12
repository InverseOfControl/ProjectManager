<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/report/LodopFuncs.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/trial/repayTrial.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
  <script type="text/javascript">var today='${today}';</script>
</head>
<body>
 	<table id="list_result" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>姓名：</span><input type="text" id="personFuzzyName" maxlength="25"  style="width: 90px" />  
	    <span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" />  
        <span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:80"/>  
        <span>还款日期：</span>
     	<input id="repayDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
        <span>手机号码：</span><input type="text" id="personMobilePhone" maxlength="11" />  
     	<span>还款类型：</span>
		<select  id="repaymentType"  editable="false" class="easyui-combobox" data-options="width:80">  
        		<option value="1">正常还款</option>
                <option value="2">一次性还款</option>	 	
         </select>
         <span>展期期次：</span>
		<select  id="extensionTimeComb"  editable="false" class="easyui-combobox" data-options="width:60">  
				 	 	 	
		 </select>
         </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	      
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