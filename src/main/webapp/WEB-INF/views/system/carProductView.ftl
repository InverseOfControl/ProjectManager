<html>
<head>
</head>
<body>
<script type="text/javascript">
var enumJson='${gridEnumJson}';
</script>
    <form id="carProductViewForm" name="carProductViewForm" method="post" style="padding: 5px;">
    <div id="productTableDiv">
	    <table width="100%" cellpadding="5" id="productTable" class="chedai" cellspacing="10" style="text-align:left">
	    <!--生效/失效 -->
	     <tr>
	        <td colspan="4"> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>产品状态</label> 
	        	 &nbsp;<label style="font-weight:bold">:</label> 
	        	 <label id="status"/>
	        </td>
	    </tr>
	    <tr>
	        <td> 
        	 <span class="pre_span"><font color="red">*</font></span>
        	 <label>借款类型 </label>  <!--hard code -->
        	  &nbsp;<label style="font-weight:bold">:</label> 
        	 <label id="productType">车贷</label>
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>产品名称 </label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="productName"></label>
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>咨询费费率 </label> 
	        	 &nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="consultingFeeRate"></label>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>评估费费率 </label> 
	        	 &nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="assessmentFeeRate"></label>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	    </tr>
	    <tr>
	        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>管理费费率</label> 
	        	 &nbsp;<label style="font-weight:bold">:</label> 
	        	 <label id="manageFeeRate"></label>
	             &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>丙方管理费费率</label>  
	        	 &nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="managePartRate"></label>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>逾期罚息费率 </label>   
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="overdueInterestRate"></label>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        	<span class="pre_span"><font color="red">*</font></span>
	        	<label>风险金比例 </label> 
 				&nbsp;<label style="font-weight:bold">:</label>  
	        	<label id="riskRate"></label>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	    </tr>
	    <!--other rates -->
	     <tr>
	        <td colspan="4"> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>平息利率</label>  
 				 &nbsp;<label style="font-weight:bold">:</label> 
	        	 <label id="rate"></label>
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
	        	 <input type="checkbox" name="terms" value="3" disabled/>
	        	 <label style="font-weight:bold">3期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate3Flow"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate3Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit3"/>
	             &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit3"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 6 term -->
	    <tr>
	        <td> 
	       &nbsp;
	        	 <input type="checkbox" name="terms" value="6" disabled/>
	        	 <label style="font-weight:bold">6期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate6Flow"/>
	             &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate6Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit6"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit6"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 9 term -->
	    <tr>
	        <td> 
	      &nbsp;
	        	 <input type="checkbox" name="terms" value="9" disabled/>
	        	 <label style="font-weight:bold">9期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate9Flow"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate9Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit9"/>
	            &nbsp;<label style="font-weight:bold">元</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit9"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 12 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="12" disabled/>
	        	 <label style="font-weight:bold">12期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate12Flow"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate12Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit12"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit12"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 18 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="18" disabled/>
	        	 <label style="font-weight:bold">18期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate18Flow"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate18Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit18"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit18"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    <!-- 24 term -->
	    <tr>
	        <td> 
	        &nbsp;
	        	 <input type="checkbox" name="terms" value="24" disabled/>
	        	 <label style="font-weight:bold">24期</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>流通类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate24Flow"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>移交类综合费率</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="sumRate24Transfer"/>
	            &nbsp;<label style="font-weight:bold">%</label>
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款下限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="lowerLimit24"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	        <td>
	        &nbsp;
	        	<label>借款上限</label> 
	        	&nbsp;<label style="font-weight:bold">:</label> 
	        	<label id="upperLimit24"/>
	            &nbsp;<label style="font-weight:bold">元</label> 
	        </td>
	    </tr>
	    </table>
    </div>
    
	 <!--city lists -->
    <div id="citysTableDiv">
	   	    <table width="100%" cellpadding="5" id="citysTable" class="chedai"  cellspacing="10" style="text-align:left"> 
		     <tr>
		       &nbsp;&nbsp;&nbsp;&nbsp;
		      <!-- <input id="checkAllCitys" type="checkbox" />
		       <label style="font-weight:bold">全选</label> 
		      -->
		     </tr>
		     <#list cityList as item>
		      <#if (item_index % 6 ==0)>
		           <tr>
	           </#if>
		          <td>
		           &nbsp;&nbsp;<input type="checkbox" name="cityIds"  value="${item.id}" disabled />${item.name} &nbsp
		          </td>
		       <#if (item_index+1 % 6 ==0)>
		          <tr>
	           </#if>
	         </#list>
			</table>
    </div>

    <input id="cityIdList" name="cityIdList" type="hidden">
    <input id="termList" name="termList" type="hidden"> 
    </form>
    <div id="addCarlg-buttons" style="text-align:center;"  > 
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true"  onclick="carProductClose()" >关闭</a>
    </div>
    </div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
            })
</script>		
</body>
</html>