var url;
$(function() {
	// 列表
	$('#list_result').datagrid({
		url : 'vehicle/list.json',
		fitColumns : true,
		border : false,
		pagination : true,
		pageSize:10,
		striped: true,
		rownumbers : true,
		fit:true,
		columns : [[ 
			{field : 'ck',checkbox : true},
			{field : 'id',title : '序号',width : 10},
			{field : 'brand',title : '品牌',width : 50},
			{field : 'model',title : '车型',width : 50},
			{field : 'coty',title : '车龄',width : 20},
			{field : 'mileage',title : '行驶里程',width : 20},
			{field : 'creator',title : '创建人',width : 50},
			{field : 'createdTime',title : '创建时间',width : 50,formatter : $.mFuc.dateS}
			
		]]
	});
	// 设置分页控件
	var p = $('#list_result').datagrid('getPager');
	$(p).pagination({
			pageList:[10,20,50]
	});
});
function searchRecord(){
	$('#list_result').datagrid('options').queryParams = {
		brand : $("#q_brand").val(),
		model : $("#q_model").val()
	};
	$('#list_result').datagrid({pageNumber:1});
	$('#list_result').datagrid('reload');
}
// 操作
function formatAction(val,row,index) {
	var url="vehicleListEdit.html";
	return '<img src="resources/static2/css/icons/cut.png" onclick="assignRoles()" title="分配角色" />&nbsp;<img src="resources/static2/css/icons/pencil.png" onclick="window.open(\''+url+'\',\'\',\'scrollbars=yes,width=800,height=600,left='+($.mFuc.winW()-800)/2+',top='+($.mFuc.winH()-700)/2+'\')" title="编辑" />&nbsp;<img src="resources/static2/css/icons/cancel.png" onclick="removeRecord('+index+')" title="删除" />';
};
// 增加记录
function addRecord() {
	 $('#dlg').dialog('open').dialog('setTitle','新建');
	 $('#fm').form('clear');
	 url = 'vehicle/addVehicle';
};
function addVehicle() {
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.success ){
				parent.$.messager.show({
					title : '提示',
					msg : '添加成功！'
				});
				$("#dlg").dialog('close');
				$('#list_result').datagrid('reload');
			} else {
				parent.$.messager.show({
					title: 'Error',
					msg: result.msg
				});
			}
		}
	});
}
// 批量删除
function batchRemoveRecord() {
	var rows = $('#list_result').datagrid('getChecked');
	if (rows.length > 0) {
		var ids = "";
		$.each(rows,function(i,n){
			if(i != 0){
				ids += "," ;
			}
			ids += (n.id) ;
		});
		parent.$.messager.confirm('确认','确认要删除所选记录吗？', function(r) {
			if(r) {
				$.post('vehicle/deleteVehicle',{idList:ids},function(result){
					 if (result.success){
						parent.$.messager.show({
							title : '提示',
							msg : '删除成功！'
						});
						$('#list_result').datagrid('reload'); // reload the user data
					 } else {
						 parent.$.messager.show({ // show error message
							 title: 'Error',
							 msg: result.errorMsg
						 });
					 }	
				});
			}
		});
	}else{
		parent.$.messager.show({
			title : '提示',
			msg : '请勾选要删除的记录！'
		});
	};
};
function editRecord() {
	var row = $('#list_result').datagrid('getSelected');
	if (row) {
		 $('#dlg').dialog('open').dialog('setTitle','编辑');
		 $('#fm').form('load',row);
		 url = 'vehicle/editVehicle';
	}else{
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
	}
}

function loadData(id,brand,model){
	    $('#id').val(id);
		$('#brand').val(brand);
		$('#model').val(model);
	
	}



// 删除记录
function removeRecord(index) {
	$('#addRecord_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('checkRow', index);
	batchRemoveRecord();
	$('#addRecord_datagrid').datagrid('uncheckAll').datagrid('unselectAll');
};

// 清空搜索
function clearSearch() {
	$('#list_result').datagrid('load',{});
	$('#list_search').searchbox('setValue','');
};