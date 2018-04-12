<#include "../macros/constant_output_macro.ftl">
<#assign staticPath="apply" />
<#assign staticName="bankAccount" />
<@htmlCommonHead/>
</head>
<body>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/bankAccount.js"></script>
<script type="text/javascript"> var isverificationBank ='${isverificationBank}';
 </script>
<form id="ff" method="post">
<input type="hidden" id="openBankId" name="openBankId" value="${vo.bankId!''}" />
<input type="hidden" id="personId" name="personId" value="${vo.personId!''}" />
<input type="hidden" id="loanId" name="loanId" value="${vo.loanId!''}" />
<table id="#changeBankAccount" style="margin-left:30px">
<div id="changeBankAccount" style="text-align:center;">
	<tr>
		<td ><label for="accountName">开户人：</label></td>
		<td align="left"><label>${vo.personName!''}</label></td>
	</tr>
	<tr>
		<td ><label for="bankAccount">银行卡号：</label></td>
		<td align="left"><input id="changeBankAccountId" class="easyui-validatebox" type="text" name="account" value="${vo.account!''}" validType="integerCheck"  data-options="required:true" maxlength="30" /></td>
	</tr>
	<tr>
		<td ><label for="bank" >开户行：</label></td>
		<td >
			<input style="width: 150px;" editable="false" id="bankId" name="bankId" class="easyui-combobox" />
			<script>
			$('#bankId').combobox({   
    			url:'bank/getBankList.json',   
    			valueField:'id',   
    			textField:'bankName',
    			onLoadSuccess:function() {
    			$(this).combobox('select', ${vo.bankId!''});
    			}  
			}); 
			</script>
		</td>
	</tr>
	<tr>
		<td ><label for="bankBranch">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" name="branchName" value="${vo.branchName!''}" data-options="required:true" /></td>
	</tr>
</div>
</table>
<div style='padding-top:10px;padding-bottom:10px;margin-left:30px'>
<input type="checkbox" id="updateAll" value="0" />&nbsp;更改该客户所有借款相关银行卡
<input type="hidden" id="isAllUpdate" name="isAllUpdate" value="false" />
</div>
<div style='padding-top:10px;padding-bottom:10px'>
		<a id = 'changeBankAccountConfirm' style='margin-left:50px' class="easyui-linkbutton" iconCls="icon-ok" >更改银行卡</a>
		<a id = 'changeBankAccountCancel'style='margin-left:80px' class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
</div>
</form>
</body>
</html>