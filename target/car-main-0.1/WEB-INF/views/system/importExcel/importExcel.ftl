<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
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
	<div style="height:120px;padding-top:8px;">
		<form id="excelImp" method="post" enctype="multipart/form-data" 
			action="importExcel/importExcelMain/importData">

			<table>
				<tr>
					<td>导入文件:</td>
					<td> 
						<input id="filename" type="file" name="filename"    />
					</td>
				
				</tr>
				<tr>
					<td></td> 
					<td> 
					<button type="submit" value="Submit">Submit</button>
					</td>
				
				</tr>
				
			</table>
		</form>	
	</div>
	
	<div  style="height:120px;padding-top:8px;">
		<form id="excelImpLoan" method="post" enctype="multipart/form-data" 
			action="importExcel/importExcelMain/importDataLoan">

			<table>
				<tr>
					<td>导入文件助学贷相关:</td>
					<td> 
						<input id="filename" type="file" name="filename"    />
					</td>
				
				</tr>
				<tr>
					<td></td> 
					<td> 
					<button type="submit" value="Submit">Submit</button>
					</td>
				
				</tr>
				
			</table>
		</form>	
	</div>
		<div  style="height:120px;padding-top:8px;">
		<form id="excelImpLoan" method="post" enctype="multipart/form-data" 
			action="importExcel/importExcelMain/deleteDataLoan">
			<table>
				<tr>
					<td>获取删除助学贷Loan相关SQL:</td>
					<td> 
					<button type="submit" value="Submit">Submit</button>
					</td>
				</tr>
				
			</table>
		</form>	
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