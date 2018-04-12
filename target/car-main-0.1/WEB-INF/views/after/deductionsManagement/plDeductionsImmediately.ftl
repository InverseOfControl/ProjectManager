<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/after/deductionsManagement/plDeductionsImmediately.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/eduLoanApply.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script> 
  <script type="text/javascript">var startDate='${startDate}';</script> 
  <script type="text/javascript">var endDate='${endDate}';</script> 

<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#offerAmountForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #offerAmountForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            
            
        }
        #offerAmountForm table tr td:nth-child(even){
            background: #FFFFFF;
        }	
        #guarateeAmountForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #guarateeAmountForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            
            
        }
        #guarateeAmountForm table tr td:nth-child(even){
            background: #FFFFFF;
        }	
</style>
</head>
<body>
	<table id="plList_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:auto;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<!-- <span>姓名：</span><input  id="personName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
		<span>身份证号：</span><input  id="personIdnum" maxlength="25" style="width: 180px" class="easyui-validatebox" />  --> 
			<span>应还款日：</span> 
     	 <input id="repayDay" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
			<span>城市：</span><input id="cityComb" class="easyui-combobox"  editable="false" data-options="width:100"/>  		  	 
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a href="javascript:void(0)" id="plGenerateBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">生成实时划扣记录</a> 
		</div>	
	</div>
	<div id="plMessage" style="visibility:hidden;"></div>
 </div>
      <!-- 新增小企业贷对话框 -->
     	<div id="seLoanAdd" style="top: 20px;height:600px;width:1000px;"></div>  
     	<!-- 查看车贷对话框 -->
	<div id="seLoanDetail" style="top: 20px;height:600px;width:1000px;"></div>   
     <!-- 编辑小企业贷对话框 -->
     	<div id="seLoanModify" style="top: 20px;height:600px;width:1000px;"></div>    
    <!-- 新增车贷对话框 -->
	<div id="carLoanAdd" style="top: 20px;height:600px;width:1200px;"></div>
	<!-- 查看车贷对话框 -->
	<div id="carLoanDetail" style="top: 20px;height:600px;width:1000px;"></div>
		<!-- 查看车贷对话框 -->
	<div id="carLoanExtensionDetail" style="top: 20px;height:600px;width:1000px;"></div>
	<!-- 编辑车贷对话框 -->
	<div id="carLoanModify" style="top: 20px;height:600px;width:1200px;"></div>
	<!--借款日志对话框-->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
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
</body>
</html>