<html>
<head>
</head>
<body>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/system/organ/organAdd.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>

    <form id="organViewForm" name="orgAddForm" method="post" style="padding: 5px;">
    <input id="organId" name="organId" type="hidden"/>  
    <input id="organName" name="organName" type="hidden"/> 
    <div id="browseTabs" class="easyui-tabs">
	       <div id="organBrowseTab"  title="机构信息" style="padding:20px">
			  <div id="organTableDiv">
					 <table width="100%" cellpadding="5" id="productTable" class="chedai" cellspacing="10" style="text-align:left">
				     <tr>
				        <td> 
			        	 <label>机构内部编号：</label> 
			        	 <label id="code" name="code"/>
				        </td>
				        <td>
				        	<label>机构名称： </label> 
				        	<label id="name" name="name"/>
				        </td>
				        <td>
				           <label>机构等级： </label> 
				           <label id="orgLevel" name="orgLevel"/>
				        </td>
				    </tr>
				    <tr>
				        <td> 
			        	 <label>机构地址：</label> 
			        	 <label id="address" name="address"/>
				        </td>
				        <td colspan="2">
				        	<label>机构电话： </label> 
				        	<label id="tel" required="true" validType="phoneTelCheck" name="tel"/>
				        </td>
				    </tr>
				    <tr>
				        <td> 
			        	 <label>法定代表人姓名：</label> 
			        	 <label id="legalRepresentative" name="legalRepresentative"/>
				        </td>
				        <td colspan="2">
				        	<label>法定代表人身份证号： </label> 
				        	<label id="legalRepresentativeId" name="legalRepresentativeId"/>
				        </td>
				    </tr>
				     <tr>
				        <td> 
			        	 <label>法人联系电话：</label> 
			        	 <label id="legalTel" required="true" validType="phoneTelCheck" name="legalTel" />
				        </td>
				        <td>
				        	<label>签约日期： </label> 
				        	<label id="signedDate" name="signedDate"/>
				        </td>
				        <td>
				           <label>保证金： </label> 
				           <label id="margin" name="margin"/>
				        </td>
				    </tr>
				    </table>
			     </div>
				  <div id="organBankTableDiv" style="text-align:left">
				      &nbsp;<label>银行卡信息：</label> 
				      <table width="100%" cellpadding="5" id="bankAccountTable" class="chedai" cellspacing="10" style="text-align:left; display:none">
				      </table>
				  </div>
				    <!--memo -->
				  <div id="memoTableDiv" style="display:block">
				      <table width="100%" cellpadding="5"  class="chedai" cellspacing="10" style="text-align:left">
				        <tr>
				         <td>
				       	    <label >备注：</label>
				       		<label id="memo" name="memo" style="text-align:left"/>
				         </td>
				        </tr>
				       </table>
				   </div>
				    <!--签约门店： -->
				  <div id="departmentTableDiv" width="100%" cellpadding="5" cellspacing="10" class="chedai" style="display:block">
				       <table width="100%" id="salesDepartmentTable" cellpadding="5"  class="chedai" cellspacing="10" style="text-align:left">
					        <span>签约门店：</span>  
				        </table>
				  </div>
				    <!--签约客户经理： -->
				  <div id="salesManagerTableDiv" width="100%" cellpadding="5" cellspacing="10" class="chedai" style="display:block">
				      <table width="45%" cellpadding="5" id="salesManagerTable" class="chedai" cellspacing="10" style="text-align:left">
					        <span>客户经理：</span>  
				      </table>
				  </div>
		   
				   <div id="organView-buttons" style="text-align:center;"> 
				       <!--客户经理,客服,复核客服,副理,经理，客户呼入组，区域经理 -->
				      <#if !(userType == 2 || userType == 3 || userType == 5 || userType == 6 || groupId == 55 || userType == 12)>
				        <a class="easyui-linkbutton" id="organViewEdit" iconCls="icon-edit" plain="true" >编辑</a>
				        <a class="easyui-linkbutton" id="organViewDelete" iconCls="icon-cancel" plain="true">删除</a>
				      </#if>
				   </div>
		    </div>
		    
            <div id="channelBrowseTab" title="方案信息" >
	             <iframe id="iframe2" src="channelPlan/changeChannelPlan"  style="border:0;width:100%;height:700px;"></iframe>
            </div>	
       </form>
      </div>
    
	<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
            })
	</script>		
</body>
</html>