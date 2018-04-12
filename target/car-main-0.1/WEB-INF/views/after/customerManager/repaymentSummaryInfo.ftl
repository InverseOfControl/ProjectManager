<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
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
	
}
 </script>

<style type="text/css">

</style>
</head>
<body>
	<div>
	  <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
                        <tr>
                            <td><label>姓名:${person.name!""} </label></td>
                            <td><label>证件类型:身份证 </label></td>
                            <td><label>身份证号:${person.idnum!""} </label></td>
                            <td><label>手机:${person.mobilePhone!""} </label></td>
                       </tr>
                       <tr>
                            <td><label>逾期起始日:${re.overdueStartDate!""}</label></td>
                            <td><label>逾期总数:${re.overdueTerm!""}</label></td>
                            <td><label>逾期利息:<#if re.interest??>${re.interest?string('0.00')}<#else>0.00</#if></label></td>
                            <td><label>逾期本金:<#if re.overduePrincipal??>${re.overduePrincipal?string('0.00')}<#else>0.00</#if></label></td>
                        </tr>
                        <tr>
                            <td><label>罚息起算日:${re.fineDate!""}</label></td>
                         	<td><label>罚息天数:${re.fineDay!""}</label></td>
                         	<td><label>剩余本息和:<#if re.curRemainingAmount??>${re.curRemainingAmount?string('0.00')}<#else>0.00</#if></label></td>
                         	<td><label>罚息金额:<#if re.fine??>${re.fine?string('0.00')}<#else>0.00</#if></label></td>
                        </tr>
                         <tr>
                            <td><label>当期还款日:${re.curRepayDate!""} </label></td>
                         	<td><label>当前期数:${re.currTerm!""}</label></td>
                         	<td><label>当期利息:<#if re.curRemainingInterestAmt??>${re.curRemainingInterestAmt?string('0.00')}<#else>0.00</#if></label></td>
                         	<td><label>当期本金:<#if re.curRemainingPrincipal??>${re.curRemainingPrincipal?string('0.00')}<#else>0.00</#if></label></td>
                        </tr> 
                        <tr>
                            <td><label>挂账金额:<#if re.accAmount??>${re.accAmount?string('0.00')}<#else>0.00</#if></label></td>
                         	<td><label>减免金额:<#if re.reliefOfFine??>${re.reliefOfFine?string('0.00')}<#else>0.00</#if></label></td>
                         	<td><label>应还总额（不含当期）:<#if re.repayAmount??>${re.repayAmount?string('0.00')}<#else>0.00</#if></label></td>
                            <td><label>应还总额（包含当期）:<#if re.repayAllAmount??>${re.repayAllAmount?string('0.00')}<#else>0.00</#if></label></td>
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