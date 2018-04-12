<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/taskAllocationRule/taskAllocationRule.js"></script>
<script type="text/javascript" src="resources/js/after/easyui.button.changeStatus.js"></script> 
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

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
	<table id="overduePageTb" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<form id="overdueForm"   method="post" >
	<div id="toolbar" style="height:120px;padding-top:8px;">
	 
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     		<span>姓名：</span><input  id="name" style="width: 180px"   type="text"  ></input> 
      			  				 
        </div>
          <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	  		<a href="javascript:void(0)" id="dispatchBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">批量分配</a>   
		</div>	
	</div>
	<form>
	 <div id="allocationRuleDlg" style="top: 20px;height:600px;width:900px;"></div>   
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