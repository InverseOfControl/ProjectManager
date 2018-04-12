<html>
<head>
</head>
<body>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/system/organ/organAdd.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
    <form id="organAddForm" name="orgAddForm" method="post" style="padding: 5px;">
    	<div id="organTableDiv">
		    <table width="100%" cellpadding="5" id="productTable" class="chedai" cellspacing="10" style="text-align:left">
		    <tr>
		        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>机构内部编号：</label> 
	        	 <input id="code" name="code" class="easyui-validatebox" maxlength="30" required="true" style="width: 160px"/>
		        </td>
		        <td>
		        	<span class="pre_span"><font color="red">*</font></span>
		        	<label>机构名称： </label> 
		        	<input id="name" name="name" class="easyui-validatebox" maxlength="30" required="true" style="width: 160px"/>
		        </td>
		        <td>
		           <label>机构等级： </label> 
		           <input id="orgLevel" name="orgLevel" class="easyui-validatebox" maxlength="30"  style="width: 160px"/>
		        </td>
		    </tr>
		    <tr>
		        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>机构地址：</ label> 
	        	 <input id="address" name="address" class="easyui-validatebox" maxlength="100" required="true" style="width: 260px"/>
		        </td>
		        <td colspan="2">
		        	<span class="pre_span"><font color="red">*</font></span>
		        	<label>机构电话： </label> 
		        	<input id="tel" name="tel" class="easyui-validatebox required="true" validType="phoneTelCheck"  style="width: 160px"/>
		        </td>
		    </tr>
		    <tr>
		        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>法定代表人姓名：</label> 
	        	 <input id="legalRepresentative" name="legalRepresentative" class="easyui-validatebox" maxlength="100" required="true" style="width: 160px"/>
		        </td>
		        <td colspan="2">
		        	<span class="pre_span"><font color="red">*</font></span>
		        	<label>法定代表人身份证号： </label> 
		        	<input id="legalRepresentativeId" name="legalRepresentativeId" validType="idCheck" class="easyui-validatebox" maxlength="30" required="true" style="width: 160px"/>
		        </td>
		    </tr>
		     <tr>
		        <td> 
	        	 <span class="pre_span"><font color="red">*</font></span>
	        	 <label>法人联系电话：</label> 
	        	 <input id="legalTel" name="legalTel" class="easyui-validatebox" required="true" validType="phoneTelCheck" style="width: 160px"/>
		        </td>
		        <td>
		        	<span class="pre_span"><font color="red">*</font></span>
		        	<label>签约日期： </label> 
		        	<!--<input id="signedDate" name="signedDate" class="Wdate easyui-validatebox"  type="text" required="true"  onfocus="WdatePicker()"/> -->
		        	<input  id="signedDate" name="signedDate"  editable="false"  required="true"   class="easyui-datebox" />
		        </td>
		        <td>
		           <label>保证金： </label> 
		           <input id="margin" name="margin" class="easyui-validatebox" maxlength="30"  style="width: 160px"/>
		        </td>
		    </tr>
		    </table>
    	</div>
        <div id="organBankTableDiv" style="text-align:left">
         &nbsp;<label>银行卡信息：</label> 
         <a id="bankCardAdd" name="bankCardAdd"  class="easyui-linkbutton" iconCls="icon-add">添加银行卡</a>
	      <table width="100%" cellpadding="5" id="bankAccountTable" class="chedai" cellspacing="10" style="text-align:left; display:none"></table>
        </div>
        <!--memo -->
	    <div id="memoTableDiv" style="display:block">
	        <table width="100%" cellpadding="5"  class="chedai" cellspacing="10" style="text-align:left">
	        <tr>
	         <td>
	       	    &nbsp;<label >备注：</label>
	         <td>
	         <td>
	       		<textarea id="memo" name="memo" rows="5" cols="100"  maxlength="1000" /></textarea>
	         <td>
	        </tr>
	        </table>
	     </div>
	    <!--签约门店： -->
       <div id="departmentTableDiv" width="100%" cellpadding="5" cellspacing="10" class="chedai" style="display:block">
	       <table width="100%" cellpadding="5"  class="chedai" cellspacing="10" style="text-align:left">
	        <tr>
	         <td>
		        <span>签约门店：</span><input id="salesDeptComb" class="easyui-combobox"  editable="false" data-options="width:150"/>  
		        <a  class="easyui-linkbutton" iconCls="icon-add" id="salesDepartmentAdd">添加</a>        
	         </td>
	        </tr>
	        </table>
	        <table width="100%" cellpadding="5" id="salesDepartmentTable" class="chedai" cellspacing="10" style="text-align:left">
	        </table>
        </div>
        <!--签约客户经理： -->
	   <div id="salesManagerTableDiv" width="100%" cellpadding="5" cellspacing="10" class="chedai" style="display:block">
	       <table width="55%" cellpadding="5" id="salesManagerTable" class="chedai" cellspacing="10" style="text-align:left">
		        <tr>
		        <td> 
		     
			    	 <label>客户经理：</label> 
			    	 <input id="saleManagerName0" name="salesManagerList[0].salesManager"  class="easyui-validatebox" maxlength="30" style="width: 80px"/>
			    </td>
			    <td>
			 
		        	<label>工号： </label> 
		        	<input id="saleManagerCode0" name="salesManagerList[0].code"  class="easyui-validatebox" maxlength="30" style="width: 80px"/>
			        <a  class="easyui-linkbutton" iconCls="icon-add" id="salesManagerAdd">增加客户经理</a>
			    </td>
		        </tr>
	       </table>
	     </div>
	   <div id="organAdd-buttons" style="text-align:center;"  > 
        <a class="easyui-linkbutton" id="organAddCancel" iconCls="icon-cancel" plain="true" >撤销</a>
        <a class="easyui-linkbutton" id="organAddSave" iconCls="icon-ok" plain="true">保存</a>
      </div>
     </form>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>		
</body>
</html>