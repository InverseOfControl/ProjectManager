<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/vehicle/vehicle.css">
<script type="text/javascript" charset="UTF-8" src="resources/js/vehicle/vehicle.js"></script>
</head>
<body>
	<table id="business_log_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar">
		<div class="fr">
			品牌:<input id="q_brand" >
			车型:<input id="q_model" >
			日期: <input class="easyui-datebox" style="width:80px">
			<a class="easyui-linkbutton" iconCls="icon-search" onclick="searchRecord()">搜索</a>
		</div>
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRecord()">新建</a>
		<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord()">编辑</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="batchRemoveRecord()">删除</a>
	</div>
	<!-- searchMenu  -->
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
			<input name="id" type="hidden" />
			<div class="fitem">
				<label>品牌:</label>
				<input name="brand" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>车型:</label>
				<input name="model" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addVehicle()">保存</a>
		<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>