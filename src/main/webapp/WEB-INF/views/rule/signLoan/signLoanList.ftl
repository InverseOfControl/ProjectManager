<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/rule/signLoanRule.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
 	<table id="loanPageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<input type="hidden" id="ruleId" name="ruleId"/>
 			<span>借款类型：</span>
 				<select  id="loanType"  editable="false" class="easyui-combobox">  
					<option value="0">全部</option>
					<option value="1">小企业贷</option>
					<option value="2">车贷</option>
				</select>
			<span>子类型：</span>
				<select id="loanTypeChild"  editable="false" disabled="disabled" style="width:108px" class="easyui-combobox">
					<option value="1">移交类</option>
					<option value="2">流通类</option>
					<option value="0">全部</option>
				</select>
			<span>失效时间 : </span>
				 <input id="overdueDateStartDate" editable="false" class="easyui-datebox" ></input>
  		 		—
     	 		 <input id="overdueDateEndDate" editable="false" class="easyui-datebox" ></input>
		</div>
		<div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a class="easyui-linkbutton" iconCls="icon-add" id="newButton" plain="true" onclick="addSignLoanRule()">新建</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" id="editButton" plain="true" onclick="editSignLoanRule()">编辑</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" id="removeButton" plain="true" onclick="removeRule()">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-m-set" id="setRuleButton" plain="true" onclick="setRule()">设置</a>
		</div>
	</div>
	
	<!-- 新建弹出窗口 -->
	<div id="addDlg" class="easyui-dialog" style="top: 150px;height:200px;width: 450px;" closed="true">
		<form id="newRuleForm" method="post">
			<div style="margin-left:80px;margin-top:15px">
				<div>
					<label>借款类型</label>
					<select  id="loanType1"  name="productType" editable ="false" onSelect ="selectChild()" class="easyui-combobox" style="width:100px">  
						<option value="1">小企业贷</option>
						<option value="2">车贷</option>	
					</select>
					<select id="loanTypeChild1"  name="productSubtype" editable="false" disabled="disabled" style="width:80px" class="easyui-combobox">
						<option value="1">移交类</option>
						<option value="2">流通类</option>
					</select>
				</div>
				<!--
				<div style="margin-top:20px">
					<label>月中/月末签单</label>
					<select  id="signType"  editable="false" style="width:108px" class="easyui-combobox">
						<option value="0">默认签单规则</option>
						<option value="1">月中特殊签单</option>
						<option value="2">月末特殊签单</option>	
					</select>
				</div>
				-->
			</div>	
		</form>
		<div style="margin-top:50px;margin-left:140px">
			<a	style="margin-right:50px" class="easyui-linkbutton" iconCls="icon-ok" id="addRule" plain="true" onclick="submitAdd()">确认</a>
			 <a	style="margin-right:0" class="easyui-linkbutton" iconCls="icon-ok" id="addCancel" plain="true" onclick="cancelAdd()">取消</a>
		</div>
	</div>
	
	<!-- 编辑弹出窗口 -->
	<div id="editDlg" class="easyui-dialog" style="top: 150px;height:200px;width: 450px;" closed="true">
		<form id="editRuleForm" method="post">
			<div style="margin-left:80px;margin-top:15px">
				<div>
					<label>借款类型</label>
					<select  id="loanType2"  name="productType" editable ="false" onSelect ="selectChild()" class="easyui-combobox" style="width:100px">  
						<option value="1">小企业贷</option>
						<option value="2">车贷</option>	
					</select>
					<label>子类型</label>
					<select id="loanTypeChild2"  name="productSubtype" editable="false" disabled="disabled" style="width:80px" class="easyui-combobox">
						<option value="1">移交类</option>
						<option value="2">流通类</option>
					</select>
				</div>
				<input type="hidden" name="overDueDate" id="overDueDate"/>
				<input type="hidden" name="ruleType" id="ruleType"/>
				<input type="hidden" name="isExecuted" id="isExecuted"/>
				<!--
				<div style="margin-top:20px">
					<label>月中/月末签单</label>
					<select  id="contractSrc"  editable="false" style="width:108px" class="easyui-combobox">
						<option value="0">默认签单规则</option>
						<option value="1">月中特殊签单</option>
						<option value="2">月末特殊签单</option>	
					</select>
				</div>
				-->
			</div>	
		</form>
		<div style="margin-top:50px;margin-left:140px">
			<a	style="margin-right:50px" class="easyui-linkbutton" iconCls="icon-ok" id="editRule" plain="true" onclick="submitEdit()">确认</a>
			 <a	style="margin-right:0" class="easyui-linkbutton" iconCls="icon-ok" id="editCancel" plain="true" onclick="cancelEdit()">取消</a>
		</div>
	</div>
</body>
</html>