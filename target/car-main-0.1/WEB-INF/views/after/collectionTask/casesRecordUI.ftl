<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
  	<script type="text/javascript" charset="UTF-8" src="resources/js/after/collectionTask/caseRecord.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
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
   	<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
   	     <span  style="display:none;"> 
 	   <input type="hidden" id="personId" name="personId" value="${personId}"  />	
  		 <input type="hidden" id="loanId" name="loanId" value="${loanId}"  />	
   		<input type="hidden"  name="taskId1" id="taskId"	value="${taskId}"/>
   		 <input type="hidden"   id="caseId"	value="${task.caseId}"/>
   		  </span>
    	<div   id="telTab" class="easyui-panel" title="电催记录" style="width:100;"></div>
                <table id="telPageTb"  ></table>   
                 <div   style="text-align:center;"  > 
      	  	 		 <a class="easyui-linkbutton"    id="personSaveBt" iconCls="icon-ok"  onclick="toRecordUi(1);" >新增电催</a>
    		</div>	
          <div   id="visitTab" class="easyui-panel" title="外访记录" style="width:100;"></div>
          		<table id="visitTb"  ></table>   
          		  <div   style="text-align:center;"  > 
          		 <a class="easyui-linkbutton"   id="personSaveBt" iconCls="icon-ok"  onclick="toRecordUi(2);" >新增外访</a>  
          </div>	
      	  <div   id="caseOptTab" class="easyui-panel" title="作业信息" style="width:100;"> 
      	  	  <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
      	  	  <tr>
                            <td><label> 客户承若还款金额: ${task.promisedRepayMent!""}  </label></td>
                            
                            <td> <label>客户实际还款金额:${task.factRepayment!""}  </label></td>
                          
                            <td><label>作业状态: <#if task.taskState??>
		  									<#elseif (task.taskState==1)>
		  									 进行中
		  									<#elseif (task.taskState==2)>
		  									正常移交
		  									<#elseif (task.taskState==3)>
		  									 异常移交
		  									<#elseif (task.taskState=4)>
		  									完成_未全部收回 
		  									<#elseif (task.taskState==5)>
		  									完成_全部收回
		  								</#if></label></td>
             </tr>
             <tr>
             	 <td><label>作业备注:${task.taskMemo!""} </label></td>
                
             </tr>
      	  </table>
      	  </div>
        <div id="recordDlg" style="top: 20px;height:600px;width:1000px;"></div>   
     <script>	
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                
              
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
              
           
            })
</script>
</body>
</html>