<html>
<head>
</head>
<body>
    <form id="productCarEditForm"  method="post" style="padding: 5px;">
    <div id="productTableDiv">
	    <table width="100%" cellpadding="5" id="productTable" class="chedai" cellspacing="10" style="text-align:left">
	    <!--生效/失效 -->
	     <tr>
	        <td colspan="4"> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>产品状态</label> 
	        	<select id="status" name="status" editable="false" class="easyui-combobox" style="width: 90px">
	                <option value="1" selected>有效</option>
	                <option value="0">无效</option>               
	            </select>
	        </td>
	    </tr>
	    <tr>
	        <td> 
        	 <span class="pre_span"><font color="red">*</font></span>
        	 <label>借款类型 </label> 
             <select  id="productType" name="productType" editable="false" class="easyui-combobox" style="width: 90px">
                <option value="2">车贷</option>
            </select>
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>产品名称 </label> 
	        	<input id="productName" name="productName" class="easyui-validatebox" maxlength="30" required="true" style="width: 160px"/>
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>咨询费费率 </label> 
	            <input id="consultingFeeRate" name="consultingFeeRate" validType="moneyCheck" class="easyui-validatebox"  maxlength="10" required="true"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>评估费费率 </label> 
	            <input id="assessmentFeeRate" name="assessmentFeeRate" validType="moneyCheck" class="easyui-validatebox"  maxlength="10" required="true"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	    </tr>
	    <tr>
	        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>管理费费率</label> 
	             <input id="manageFeeRate" name="manageFeeRate" validType="moneyCheck" class="easyui-validatebox"  maxlength="10" required="true"   style="width: 90px">
	             &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>丙方管理费费率</label> 
	            <input id="managePartRate" name="managePartRate" validType="moneyCheck" class="easyui-validatebox"  maxlength="10" required="true"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>逾期罚息费率 </label> 
	            <input id="overdueInterestRate" name="overdueInterestRate" validType="moneyCheck" class="easyui-validatebox"  maxlength="10" required="true"  style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>风险金比例 </label> 
	            <input id="riskRate" name="riskRate" validType="moneyCheck" class="easyui-validatebox" maxlength="10" required="true"  style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	    </tr>
	    <!--other rates -->
	     <tr>
	        <td colspan="4"> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>平息利率</label> 
	             <input id="rate" name="rate" validType="moneyCheck" class="easyui-validatebox" maxlength="10" required="true"  style="width: 90px"/>
	             &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	    </tr>
	    </table>
    </div>
    <div id="carTermsTableDiv" style="display:block">
	    <!--期数table -->
	    <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left">
	     <tr>
	        <td colspan="5"> 
	        	 &nbsp;
	        	 <label style="font-weight:bold">期数</label> 
	        </td>
	    </tr>
	    <!-- 3 term -->
	    <tr>
	        <td> 
	             &nbsp;
	        	 <input type="checkbox" name="terms" value="3"/>
	        	 <label style="font-weight:bold">3期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate3Flow"   name="sumRate3Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate3Transfer" name="sumRate3Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit3" name="lowerLimit3" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	             &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit3" name="upperLimit3" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 6 term -->
	    <tr>
	        <td> 
	       &nbsp;
	        	 <input type="checkbox" name="terms" value="6"/>
	        	 <label style="font-weight:bold">6期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate6Flow" name="sumRate6Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	             &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate6Transfer" name="sumRate6Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit6" name="lowerLimit6" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit6" name="upperLimit6" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 9 term -->
	    <tr>
	        <td> 
	      &nbsp;
	        	 <input type="checkbox" name="terms" value="9"/>
	        	 <label style="font-weight:bold">9期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate9Flow" name="sumRate9Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate9Transfer" name="sumRate9Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit9" name="lowerLimit9" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit9" name="upperLimit9" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 12 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="12"/>
	        	 <label style="font-weight:bold">12期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate12Flow" name="sumRate12Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate12Transfer" name="sumRate12Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit12" name="lowerLimit12" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit12" name="upperLimit12" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 18 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="18"/>
	        	 <label style="font-weight:bold">18期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate18Flow" name="sumRate18Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate18Transfer" name="sumRate18Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit18" name="lowerLimit18" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit18" name="upperLimit18" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 24 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="24"/>
	        	 <label style="font-weight:bold">24期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	            <input id="sumRate24Flow" name="sumRate24Flow" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	            <input id="sumRate24Transfer" name="sumRate24Transfer" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	            <input id="lowerLimit24" name="lowerLimit24" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	            <input id="upperLimit24" name="upperLimit24" validType="moneyCheck" class="easyui-validatebox" maxlength="10"   style="width: 90px"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    </table>
    </div>
    
	 <!--city lists -->
    <div id="citysTableDiv">
	   	    <table width="100%" cellpadding="5" id="citysTable" class="chedai"  cellspacing="10" style="text-align:left"> 
		     <tr>
		       &nbsp;&nbsp;&nbsp;&nbsp;<input id="checkAllCitys" type="checkbox" />
		       <label style="font-weight:bold">全选</label>
		     </tr>
		     <#list cityList as item>
		      <#if (item_index % 6 ==0)>
		           <tr>
	           </#if>
		          <td>
		           &nbsp;&nbsp;<input type="checkbox" name="cityIds"  value="${item.id}" />${item.name} &nbsp
		          </td>
		       <#if (item_index+1 % 6 ==0)>
		          <tr>
	           </#if>
	         </#list>
			</table>
    </div>

    <input id="cityIdList" name="cityIdList" value=""  type="hidden">
    <input id="termList" name="termList"  value=""  type="hidden"> 
    <!--product id -->
    <input id="id" name="id"   type="hidden"> 
    </form>
    <div id="addCarlg-buttons" style="text-align:center;"  > 
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true"  onclick="productEditSave()" >保存</a>
    </div>
    </div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
            })
</script>		
</body>
</html>