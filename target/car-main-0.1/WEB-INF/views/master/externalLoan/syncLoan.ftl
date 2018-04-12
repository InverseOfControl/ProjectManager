<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/master/syncLoan/syncLoan.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript">
var enumJson='${gridEnumJson}';
var buildDateStart='${buildDateStart?date}';
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
</head>
<body>
 	<table id="syncLoanTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>姓名：</span><input type="text" id="name" maxlength="25"  style="width: 90px" />  
	    <span>身份证号：</span><input type="text" id="idNum" maxlength="18" />  
	    <span>产品类型：</span><input id="loanType" class="easyui-combobox"  editable="false"  data-options="width:120"/>
        <!--<select  id="loanType"  editable="false" class="easyui-combobox" data-options="width:120">  
				<option value="1">小企业贷</option>
				<option value="2">车贷</option>
		        <option value="4">车贷新产品</option>	 
		        <option value="5">同城POS贷</option>	
		        <option value="6">同城小微贷</option>	
		        <option value="7">网商贷</option>		
		        <option value="8">同城小微贷</option>		
		</select> -->
        <#include "syncMultiSearchMacro.ftl">
        </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">		
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a> 
	        <a href="javascript:void(0)" id="exportExcelBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出</a>
		 </div>
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
</body>
</html>	
