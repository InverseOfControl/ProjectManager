<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/report/repaymentReportList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
<script type="text/javascript">
var enumJson='${gridEnumJson}';
var repayDateStart='${repayDateStart}';
var repayDateEnd='${repayDateEnd}';

function exportExcel(){
	var repaymentType = $('#toolbar #repaymentType').combobox('getValue');
	var productType = $('#toolbar #productComb').combobox('getValue');
	var repayDateStart = $('#toolbar #repayDateStart').val();
	var repayDateEnd = $('#toolbar #repayDateEnd').val();
	var salesDeptId = $('#toolbar #salesDeptComb').combobox('getValue');
	
	var params = 'repaymentType='+repaymentType+'&productType='+
    productType+'&repayDateStart='+repayDateStart+'&repayDateEnd='+repayDateEnd+'&salesDeptId='+salesDeptId;
    
	var checkExportURL='${WebConstants.webUrl}${WebConstants.contextPath}/'+'repayment/report/checkExportNum?'+params;
    
	var exportURL='${WebConstants.webUrl}${WebConstants.contextPath}/'+'repayment/report/exportExcel?'+params;

    $.ajax({
	  	 url : checkExportURL,	  
	  	 type:"POST",
	  	 success : function(result){
		 	  if(result=="success"){
		 	 	 self.location.href=exportURL;			
		 	  }else{
				 $.messager.show({
						title : '提示',
						msg : result
				});
		  	 }		    			
	   }
	});	
}

</script>
</head>
<body>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>明细种类：</span>
        <select id="repaymentType" editable="false" class="easyui-combobox" data-options="width:80">  
        	<option value="1">有还款记录</option>
            <option value="0">无还款记录</option>	 	
        </select>
        <span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:80"/>  
        <span>实际还款日：</span>
     	<input id="repayDateStart" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
     	&nbsp;—&nbsp;
     	<input id="repayDateEnd" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
	    <span>营业网点：</span><input id="salesDeptComb" class="easyui-combobox"  editable="false" data-options="width:150"/>  
        <!-- <span>合同来源：</span>
        <input id="contractSrcComb" name="contractSrc" class="easyui-combobox"  editable="false"  data-options="width:80"/> 
        -->
         </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <a href="javascript:void(0)" id="exportExcelBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出</a> 
		 </div>
	</div>
	<div  id="list_result_repayment_div" style="display:none">
	  <table id="list_result_repayment"   toolbar="#toolbar" ></table>
	</div>
	<div id="list_result_NoRepayment_div" style="display:none">
 	  <table id="list_result_NoRepayment" toolbar="#toolbar" ></table>
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