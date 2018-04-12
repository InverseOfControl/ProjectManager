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
	
}
 </script>

<style type="text/css">

</style>
</head>
<body>
	<div>
	  <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
	  			 <tr>
                        <td><label>产品类型:${ld.prudentName!""}</label></td>
                 </tr>       
                        <tr>
                            <td><label>借款人:${ld.personName!""} </label></td>
                            <td><label>性别:${ld.sex!""} </label></td>
                            <td><label>身份证号:${ld.idNum!""} </label></td>
                        </tr>
                       <tr>
                            <td><label>申请金额(元):<#if ld.requestMoney??>${ld.requestMoney?string('0.00')}</#if></label></td>
                            <td><label>申请期限(月):${ld.requestTime!""}</label></td>
                            <td><label>申请日期:<#if ld.requestDate??>${ld.requestDate?string("yyyy/MM/dd")}</#if></label></td>
                        </tr>
                        <tr>
                            <td><label>审批金额(元):<#if ld.auditMoney??>${ld.auditMoney?string('0.00')}</#if></label></td>
                         	<td><label>借款期限(月):${ld.time!""}</label></td>
                        </tr>
                         <tr>
                            <td><label>状态:${ld.statusStr!""} </label></td>
                         	<td><label>月还款能力(元):${ld.maxRepayAmount!""}</label></td>
                         	<td><label>用途:${ld.purpose!""}</label></td>
                        </tr> 
                        <tr>
                            <td><label>营业部:${ld.salesDept!""} </label></td>
                         	<td><label>还款银行:${ld.repayBank!""}</label></td>
                         	<td><label>放款银行:${ld.gantBank!""}</label></td>
                        </tr> 
                            <tr>
                            <td><label>客户经理:${ld.crmName!""}</label></td>
                            <td><label>客服:${ld.serviceName!""}</label></td>
                            <td><label>合同来源:${ld.contractSource!""}</label></td>
                        </tr>  
                    </table>
                  </div>
<script>	
           $(function(){
                $('table tr td').css(
                {
                "background":"#E0ECFF",
               	"font-size":"15px",
                "padding-right":"10px",
                "padding-top":"10px"
                }
                );
               $('.datagrid-htable tr td').css("padding-right","0");
            })
</script>
</body>
</html>