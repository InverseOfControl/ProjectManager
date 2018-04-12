<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/fristApprove/approveList.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script> 
 <script type="text/javascript" charset="utf-8">
function exportExcel(){
	var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/findCountByParams?selectedProductId=';
	var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/exportExcel?selectedProductId=';
	var params='';
	var  personIdnum=null;
	var  personName=null;
	var  selectedProductId=null;
	var  status=null;
	var  contractConfirmStartDate=null;
	var  contractConfirmEndDate=null;
		personName = $('#personName').val();
		personIdnum = $('#personIdnum').val();
		contractConfirmStartDate = $('#contractConfirmStartDate').val();
		contractConfirmEndDate = $('#contractConfirmEndDate').val();
		var contractSrc =0;
		if($('#contractSrc').combobox('getValue')=="all"){
			 contractSrc = 0;
	    }else{
		   	 contractSrc = $('#contractSrc').combobox('getValue');
	    }
	   
		if($('#loanType').combobox('getValue')=="all"){
			selectedProductId =null;
	    }else{
	    	selectedProductId= $('#loanType').combobox('getValue');
	    	params+=selectedProductId;
	    }
		if($('#loanStatus').combobox('getValue')=="all"){
			status =0;
	    }else{
	    	status = $('#loanStatus').combobox('getValue');
	    }	    
		params+="&status="+status;
		if(personName!=''){
			//对汉字进行编码设置 ，服务端同样需要进行编码设置
			params+='&personName='+encodeURI(encodeURI(personName));
		}
		if(personIdnum!=''){
			params+='&personIdnum='+personIdnum;
		}
		if(contractSrc!=''||contractSrc==0){
			params+='&contractSrc='+contractSrc;
		}		
		if(contractConfirmStartDate!=''){
			params+='&contractConfirmStartDate='+contractConfirmStartDate;
		}
		if(contractConfirmEndDate!=''){
			params+='&contractConfirmEndDate='+contractConfirmEndDate;
		}
		url+=params;
		url2+=params;
		$.ajax({
	  	 url : url,	  
	  	 type:"POST",
	  	 success : function(result){
		 	  if(result=="success"){
		 	 	 self.location.href=url2;			
		 	  }else{
				 $.messager.show({
						title : '提示',
						msg : result
				});
		  	 }		    			
  			$('#checkPageTb').datagrid('reload');
	   }
	});	
}
 </script>

<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#refuseReasonForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #refuseReasonForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:left;
            
            
        }
        #refuseReasonForm table tr td:nth-child(even){
            background: #FFFFFF;
        }
</style>
</head>
<body>
	<table id="approvePage"  ></table>
	<!-- toolbar  -->
	<input id="reason12" name="reason12" type="hidden" oninput="inputChange(this.value);" onpropertyChange="propertyChange(this.value);">
	<div  id="flshDiv" tabindex="-1" onfocus="inputChange('focus');"  > </div>
	<div id="dlg" class="easyui-dialog" style="top: 150px;width: 750px;" closed="true">  	 	
		<form id="refuseReasonForm" method="post">	
		 		 <input type="hidden" id ="loanId" name="loanId" />  
				<table id="refuseTb" style="font-size:12px; width:100%;text-align:left; "> 
					<tr>
	    				<td>
							<label style="margin-right:50px">退回门店</label>
						</td>
				    </tr>
				    <tr>
				    	<td>	
		    				<label>一级原因</label>
		    				<input id="reason1" name="reason1" class="easyui-combobox" editable="false" required="true" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
		    			    <label>二级原因</label>
		    			    <input id="reason2" name="reason2"  class="easyui-combobox" editable="false" required="true" data-options="width:200" />
		    				
	    				</td>
    				</tr>
    				<tr>
	    				<td><label style="margin-right:50px">备注</label></td>
    				</tr>
    				<tr>
	    				<td ><textarea id="reason" name="reason" rows="10" cols="100" required="true" maxlength="390"/></textarea></td>
    				</tr>
    				
    				<tr>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>	
	    					<a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			
		</form>
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
	<!-- 审核日志对话框 -->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>
	<!-- 审核方案 -->
	<div id="seReplyEditDlg" style="top: 20px;height:600px;width:1000px;"></div>   
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