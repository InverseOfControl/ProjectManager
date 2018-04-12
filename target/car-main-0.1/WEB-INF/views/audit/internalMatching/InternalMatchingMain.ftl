<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/internalMatching/internalMatching.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/eduLoanApply.js"></script>
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
	<!--<table id="casesPageTb" toolbar="#toolbar"></table>-->
	     <span  style="display:none;"> 
 			  <input type="hidden" id="personId" name="personId" value="${person.id!""}"  />	
 			   <input type="hidden" id="loanId" name="loanId" value="${loan.id!""}"  />	
     	 </span>
	 <div id="personInfoTab" class="easyui-panel" title="申请信息" style="width:100;">
                    <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
                        <tr>
                            <td><label>姓名: ${person.name!""} </label></td>
                            <td><label>身份证号: ${person.idnum!""} </label></td>
                            <td><label>申请贷款产品:${product.productName!""}  </label></td>
                            <td><label>费率: ${actualRate?string('0.0000')}</label></td>	 
                        </tr>
                    </table>
         </div>
         
          <div   id="loanHistoryTab" class="easyui-panel" title="借款历史" style="width:100;"></div>
                <table id="loanHistoryTb"  ></table>   
            
          <div   id="matchingTab" class="easyui-panel" title="匹配信息" style="width:100;"></div>
          		<table id="matchingTb"  ></table>     
      	 
      	 <div id="contactPersonDlg" style="top: 20px;height:600px;width:1000px;"></div>   
      	 <div id="addContactPersonDlg" style="top: 20px;height:600px;width:1000px;"></div>   
      	 
      	 
      	 
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
               //IE也能用textarea 
$("textarea[maxlength]").keyup(function(){ 
var area=$(this); 
var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
if(max>0){ 
if(area.val().length>max){ //textarea的文本长度大于maxlength 
area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
} 
} 
}); 
//复制的字符处理问题 
$("textarea[maxlength]").blur(function(){ 
var area=$(this); 
var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
if(max>0){ 
if(area.val().length>max){ //textarea的文本长度大于maxlength 
area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
} 
} 
}); 
            })
</script>
</body>
</html>