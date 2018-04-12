<#include "../../macros/constant_output_macro.ftl">
<#assign staticPath="audit/contract" />
<#assign staticName="contract" />
<@htmlCommonHead/>
</head>

<body onLoad="setDefaultDate()">
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/contract/contract.js"></script>
<script type="text/javascript"> var isverificationBank ='${isverificationBank}';
 </script>


<form id="ff" method="post">
<input type="hidden" id="isSign" name="isSign" value="${vo.isSign()!''}" />
<input type="hidden" id="loanId" name="loanId" value="${vo.loanId!''}" />
<table id="#generateContract" style="margin-left:30px">

<div id="generateContract" style="text-align:right;">
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
		<td align="left"><input class="easyui-validatebox" type="text"  id="bankAccount" name="bankAccount" validType="integerCheck"  data-options="required:true" maxlength="20" /></td>
	</tr>
	<tr>
		<td ><label for="bank" >开户行：</label></td>
		<td >
		<#if vo.isEduAudit()>
			<input style="width: 150px;" editable="false" id="bank" name="bank" class="easyui-combobox" data-options="valueField:'id',textField:'bankName',url:'bank/getBankListIn.json'"/>
		<#else>
			<input style="width: 150px;" editable="false" id="bank" name="bank" class="easyui-combobox" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'"/>
		</#if>
		</td>
	</tr>
	<tr>
		<td ><label for="bankBranch">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="bankBranch" name="bankBranch" data-options="required:true" /></td>
	</tr>
	<!--增加合同生成选择时间,只支持车贷-->
	<#if vo.getProductType()=2>
	<tr style="display:none;">
	  <td ><label for="contractCreatedDate">合同生成日期：</label></td>
	  <td>
		<input name="contractCreatedDate" id="contractCreatedDate"  class="Wdate easyui-validatebox" value="${defaultDate?string('yyyy-MM-dd')}"  type="text"  onfocus="WdatePicker({minDate:'%y-%M-{%d-30}',maxDate:'%y-%M-{%d+30}'})" required="true"></input>
	  </td>
	</tr>
	</#if>
	
	<#if vo.isHasNaturalGuarantee1()>
	<tr>
		<td ><label for="naturalGuaranteeName1">自然担保人1：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeName1" name="naturalGuaranteeName1" value="${vo.naturalGuaranteeName1!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankAccount1">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankAccount1" name="naturalGuaranteeBankAccount1"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBank1">开户行：</label></td>
		<td >
			<input id="naturalGuaranteeBank1" name="naturalGuaranteeBank1" class="easyui-combobox"   style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankBranch1">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankBranch1" name="naturalGuaranteeBankBranch1" maxlength="75" required="true"/></td>
	</tr>
	</#if>
	<#if vo.isHasNaturalGuarantee2()>
	<tr>
		<td ><label for="naturalGuaranteeName2">自然担保人2：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeName2" name="naturalGuaranteeName2" value="${vo.naturalGuaranteeName2!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankAccount2">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankAccount2" name="naturalGuaranteeBankAccount2"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBank2">开户行：</label></td>
		<td >
			<input id="naturalGuaranteeBank2" name="naturalGuaranteeBank2" class="easyui-combobox"  required=true  style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankBranch2">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankBranch2" name="naturalGuaranteeBankBranch2" data-options="required:true" maxlength="75"/></td>
	</tr>
	</#if>

	<#if vo.isHasNaturalGuarantee3()>
	<tr>
		<td ><label for="naturalGuaranteeName3">自然担保人3：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeName3" name="naturalGuaranteeName3" value="${vo.naturalGuaranteeName3!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankAccount3">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankAccount3" name="naturalGuaranteeBankAccount3"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBank3">开户行：</label></td>
		<td >
			<input id="naturalGuaranteeBank3" name="naturalGuaranteeBank3" class="easyui-combobox"  required=true  style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankBranch3">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankBranch3" name="naturalGuaranteeBankBranch3" data-options="required:true" maxlength="75"/></td>
	</tr>
	</#if>

	<#if vo.isHasNaturalGuarantee4()>
	<tr>
		<td ><label for="naturalGuaranteeName4">自然担保人3：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeName4" name="naturalGuaranteeName4" value="${vo.naturalGuaranteeName4!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankAccount4">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankAccount4" name="naturalGuaranteeBankAccount4"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBank4">开户行：</label></td>
		<td >
			<input id="naturalGuaranteeBank4" name="naturalGuaranteeBank4" class="easyui-combobox"  required=true  style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="naturalGuaranteeBankBranch4">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="naturalGuaranteeBankBranch4" name="naturalGuaranteeBankBranch4" data-options="required:true" maxlength="75"/></td>
	</tr>
	</#if>

	<#if vo.isEduAudit()>
		<tr>
		<td colspan=2><label for="organName4">放款机构银行卡信息</label></td>
	</tr>
	<tr>
		<td ><label for="organBankAccount4">放款银行卡号：</label></td>
		<td >
		<input id="organBankAccount4" name="organBankId" editable="false"  class="easyui-combobox" data-options="valueField:'id',textField:'account',url:'audit/contract/getBankAccountList/${vo.loanId}',
		onSelect: function(rec){    
            $('#organBank4').val(rec.bankId);
            $('#organBankName4').val(rec.bankName);
            $('#organBankBranch4').val(rec.branchName);
        } ,
         onLoadSuccess:function(){
         	var data = $(this).combobox('getData');
         	if(data[0] != null){
         		$(this).combobox('select', data[0].id);
         	}
         }
		"
			        style="width: 150px;"  maxlength="75" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td ><label for="organBank4">放款开户行：</label></td>
		<td >
			<input class="easyui-validatebox" type="text" id="organBankName4" name="organBankName4"   data-options="required:true" readonly="readonly" />
			<input class="easyui-validatebox" type="hidden" id="organBank4" name="organBank4"   />
			
		</td>
	</tr>
	<tr>
		<td ><label for="organBankBranch4">放款开户支行：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="organBankBranch4"  name="organBankBranch4" data-options="required:true" maxlength="75" readonly="readonly" /></td>
	</tr>
	</#if>
	
	<#if vo.isOrganPay()>
		<tr>
		<td colspan=2><label for="organName5">还款机构银行卡信息</label></td>
	</tr>
	<tr>
		<td ><label for="organBankAccount5">还款银行卡号：</label></td>
		<td >
		<input id="organBankAccount5" name="organBankId5" editable="false" class="easyui-combobox" data-options="required:true,valueField:'id',textField:'account',url:'audit/contract/getBankAccountList/${vo.loanId}',
		onSelect: function(rec){    
            $('#organBank5').val(rec.bankId);
            $('#organBankName5').val(rec.bankName);
            $('#organBankBranch5').val(rec.branchName);
        } ,
         onLoadSuccess:function(){
         	var data = $(this).combobox('getData');
         	if(data[0] != null){
         		$(this).combobox('select', data[0].id);
         	}
         }
		"
			        style="width: 150px;"  maxlength="75" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td ><label for="organBank5">还款开户行：</label></td>
		<td >
			<input class="easyui-validatebox" type="text" id="organBankName5" name="organBankName5"    data-options="required:true" readonly="readonly" />
			<input class="easyui-validatebox" type="hidden" id="organBank5" name="organBank5"   />
			
		</td>
	</tr>
	<tr>
		<td ><label for="organBankBranch5">还款开户支行：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="organBankBranch5" name="organBankBranch5" data-options="required:true" maxlength="75" readonly="readonly" /></td>
	</tr>
	</#if>


	<#if vo.isHasLegalGuarantee1()>
	<!-- <tr>
		<td ><label for="legalGuaranteeName1">法人担保人1：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeName1" name="legalGuaranteeName1" value="${vo.legalGuaranteeName1!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBankAccount1">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeBankAccount1" name="legalGuaranteeBankAccount1"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBank1">开户行：</label></td>
		<td >
			<input id="legalGuaranteeBank1" name="legalGuaranteeBank1" class="easyui-combobox"  required=true  style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBankBranch1">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeBankBranch1" name="legalGuaranteeBankBranch1" data-options="required:true" maxlength="75"/></td>
	</tr> -->
	</#if>
		<#if vo.isHasLegalGuarantee2()>
	<!-- <tr>
		<td ><label for="legalGuaranteeName2">法人担保人2：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeName2" name="legalGuaranteeName2" value="${vo.legalGuaranteeName2!''}" data-options="required:true" readOnly="readOnly"/></td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBankAccount2">银行卡号：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeBankAccount2" name="legalGuaranteeBankAccount2"   validType="integerCheck"  data-options="required:true" /></td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBank2">开户行：</label></td>
		<td >
			<input id="legalGuaranteeBank2" name="legalGuaranteeBank2" class="easyui-combobox"  required=true  style="width: 150px;" data-options="valueField:'id',textField:'bankName',url:'bank/getBankList.json'" maxlength="75"/>
		</td>
	</tr>
	<tr>
		<td ><label for="legalGuaranteeBankBranch2">开户网点：</label></td>
		<td ><input class="easyui-validatebox" type="text" id="legalGuaranteeBankBranch2" name="legalGuaranteeBankBranch2" data-options="required:true" maxlength="75"/></td>
	</tr> -->
	</#if>
	
	</div>
</table>
<!-- hiddle field-->
<input type="hidden" name="productId" id="productId" value="${vo.productId!''}"/>

<div style='padding-top:10px;padding-bottom:10px'>
		<a style='margin-left:50px' class="easyui-linkbutton" iconCls="icon-ok" onclick="generateContract();">生成合同</a>
		<a style='margin-left:80px' class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#generateContractDlg').dialog('close')">取消</a>
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
