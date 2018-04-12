<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/rule/repayDateRule.js"></script>
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<style>
	#editDLG table td,#addDLG table td{
    border-bottom:1px dashed #BFC5C5;
    border-left:#dfe4e5;
    border-right:#dfe4e5;
    height:40px;
    line-height:18px;
    width:290px;
    
}
	#editDLG label,#addDLG label{
	
	display:inline-block;
	width:80px;
	
	}
	.title{
	margin-left:10px;
	font-weight:bold;
	}
</style>
</head>
<body>
 	<table id="loanPageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>借款类型：</span><select  id="loanType"  editable="false" class="easyui-combobox">  
                    <option value="all">全部</option>	
        			<option value="1">小企业贷</option>
                    <option value="2">车贷</option>	
        			</select>
        <select id="loanTypeChild"  editable="false"  disabled="disabled" style="width:108px" class="easyui-combobox">
                <option value="1">移交类</option>
                <option value="2">流通类</option> 
                <option value="all">全部</option>	         
         </select>		
        <span>合同来源</span>
        <select  id="contractSource"  editable="false" style="width:108px" class="easyui-combobox">  
        			<option value="1">证大P2P</option>
                    <option value="2">证大爱特</option>
                    <option value="all">全部</option>		
        </select>
        <span>还款日规则</span>
        <select  id="repaydateRule"  editable="false" class="easyui-combobox">  
        			<option value="0">固定还款日</option>
                    <option value="1">非固定还款日</option>	
                    <option value="all">全部</option>	
        </select>
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <a class="easyui-linkbutton" iconCls="icon-add" id ="newButton" plain="true" >新建</a>
	         <a class="easyui-linkbutton" iconCls="icon-edit" id="editButton" plain="true">编辑</a>
	          <a class="easyui-linkbutton" iconCls="icon-remove" id="removeButton" plain="true" >删除</a>
	           <a class="easyui-linkbutton" iconCls="icon-m-set" id="setRuleButton" plain="true" >设置</a>
		</div>
	</div>
	<!--  新增 -->
	<div id="addDLG" class="easyui-dialog" style="top: 150px;height:330px;width: 310px;" modal="true" closed="true">
		<form id="newRuleForm" method="post">	
		<table style="padding-left:20px;padding-right:20px">
		<tr>
			<td>
				<label class='title'>规则名称</label>
				<input id="name" name = "name" style="width:108px"  class="easyui-validatebox"/>
			</td>
		</tr>
		
		<tr>
			<td>
				 <label class='title'>借款类型</label>
			     <select  id="loanType2"  editable="false" style="width:108px" class="easyui-combobox">  
	        			<option value="1">小企业贷</option>
	                    <option value="2">车贷</option>	
	        			</select>
			</td>
		</tr>
		<tr>
			<td>
			<label class='title'>子类型&nbsp;</label>
			
	        <select  id="productSubtype" name="productSubtype"  editable="false" disabled="disabled" style="width:108px" class="easyui-combobox">
	                <option value="1">移交类</option>
	                <option value="2">流通类</option>  
	         </select>
	         </td>
         </tr>
         <tr>
	         <td>		
	 		 <label class='title'>合同来源</label>
	        <select  id="contractSrc"  editable="false" style="width:108px" class="easyui-combobox">  
	        			<option value="1">证大P2P</option>
	                    <option value="2">证大爱特</option>	
	        </select>
	        </td>
        </tr>
        <tr>
        	<td>
		    <label class='title'>还款日规则</label>
	        <select  id="repaydateRule2"  editable="false" style="width:108px"  class="easyui-combobox">  
	        			<option value="0">固定还款日</option>
	                    <option value="1">非固定还款日</option>	
	        </select>
	        </td>
        </tr>
        </table>
		</form>
		<div style="margin-top:20px">
					 <a   style="margin-left:50px" id="addRule" class="easyui-linkbutton" iconCls="icon-ok" plain="true" >确认</a>
					 <a   style="margin-left:30px"  class="easyui-linkbutton" iconCls="icon-ok" id="addCancel" plain="true" >取消</a>
		</div>
	</div>
	<!--  编辑  -->
	<div id="editDLG" class="easyui-dialog" style="top: 150px;height:330px;width: 310px;" modal="true" closed="true">
		<table style="margin-left:15px">
		<tr>
			<td>
			<label class='title'>规则名称 </label><input id="productName" class="easyui-validatebox"/>
			<input type="hidden" id="ruleId" name="ruleId"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<label class='title'>借款类型</label>
				<label id="productType"></label>
				         
	        </td>
         </tr>
         <tr>
     		<td>
     			<label class='title' id="child">子类型</label>
	            <label id="childDisplay">   </label>   
     		</td>
         </tr>
         
         <tr>
	         <td>	
		 		 <label class='title'>合同来源</label>
		 		 <label id="editContractSource"></label>
	        </td>
         </tr>
         
        <tr>
        <td>
        	<label class='title'>还款日规则</label>
	        <select  id="dueDateRule"  editable="false" class="easyui-combobox">  
	        			<option value="0">固定还款日</option>
	                    <option value="1">非固定还款日</option>	
	        </select>
	        </td>
        </tr>
        </table>
        
		<div style="margin-top:20px">
					 <a   style="margin-left:50px"  class="easyui-linkbutton" iconCls="icon-ok" id="editRule" plain="true" >确认</a>
					 <a   style="margin-left:30px"  class="easyui-linkbutton" iconCls="icon-ok" id="editCancel" plain="true" >取消</a>
		</div>
	</div>
</body>
</html>