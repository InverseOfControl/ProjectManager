<#include "../../macros/constant_output_macro.ftl">
<#assign staticPath="audit/contract" />
<#assign staticName="contract" />
<@htmlCommonHead/>
</head>
<body>

 <script type="text/javascript">var bank='${vo.bank}';</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/contract/contract.js"></script>
<script type="text/javascript"> var isverificationBank ='${isverificationBank}';
 </script>

<form id="ff" method="post">
<input type="hidden" id="isSign" name="isSign" value="${vo.isSign()!''}" />
<input type="hidden" id="loanId" name="loanId" value="${vo.loanId!''}" />

<table id="#generateExtensionContract" style="margin-left:30px">
<div id="generateExtensionContract" style="text-align:right;">
	<tr>
		<td><label for="contractSrc">合同来源：</label></td>
		<td>
		<input id="contractSrcComb" name="contractSrc" class="easyui-combobox"  editable="false"  data-options="width:80"/> 
		</td>
	</tr>
	<tr>
		<td ><label for="accountName">开户人：</label></td>
		<td align="left"><label>${vo.accountName!''}</label><input class="easyui-validatebox" type="hidden" name="accountName" value="${vo.accountName!''}" /></td>
	</tr>
	<tr>
		<td ><label for="bankAccount">银行卡号：</label></td>
		<td align="left"><input class="easyui-validatebox" type="text" id="bankAccount" name="bankAccount" validType="integerCheck"  data-options="required:true" maxlength="30"   value="${vo.bankAccount!''}"/></td>
	</tr>
	<tr>
		<td ><label for="bank" >开户行：</label></td>
		<td >
			<input id="bankComb" id="bank" name="bank" class="easyui-combobox"  editable="false"  data-options="width:150"/>  
		</td>
	</tr>
	<tr>
		<td ><label for="bankBranch">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="bankBranch" name="bankBranch" data-options="required:true"  value="${vo.bankBranch!''}"/></td>
	</tr>
	
	</div>
</table>
<div style='padding-top:10px;padding-bottom:10px'>
		<a style='margin-left:50px' class="easyui-linkbutton" iconCls="icon-ok" onclick="generateExtensionContract();">生成合同</a>
		<a style='margin-left:80px' class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#generateExtensionContractDlg').dialog('close')">取消</a>
</div>
</form>
<script>
           $(function(){
                $('#generateContract1 a').css(
                {
                
                "padding-right":"5px"
                }
                );
               
               $('#generateContract2 a').css(
                {
                
                "padding-left":"50px"
                }
                );
                
			
				
            })
</script>
</body>

</html>
