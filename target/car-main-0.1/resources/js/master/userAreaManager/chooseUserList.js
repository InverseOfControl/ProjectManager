$(function () {
	$('#searchUser').bind('click', userSearch);
	$('#clearChooseUser').bind('click', userRefresh);
	$('#chooseUserList').datagrid({
		url : 'sysUser/getSearchJson',
		singleSelect:true,
		fitColumns : true,
		pagination : true,
		striped : true,
		pageSize :20,
		//fit:true,
		rownumbers : true,
		queryParams:{},
		pagePosition :'bottom',
		columns : [ [ {
			field : 'code',
			title : '工号',
			formatter : function(value, row, index) {
				return value;
			},
			width : 60
		}, {
			field : 'name',
			title : '姓名',
			width : 80
		},
		{
			field : 'id',
			title : '用户ID',
			hidden : true,
			width : 80
		}
		, {
			field : 'a',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="selectedUser({\'id\':' + row.id + ',\'name\':\'' + row.name + '\'});">选择</a>'
				return oper;
			},
			width : 150
		} ] ]
	});
	
});

//重置查询条件
function userRefresh(){
	$('#userSearchCode').val("");
	$('#userSearchName').val("");
	$('#chooseUserList').datagrid('options').queryParams = {};
	 $("#chooseUserList").datagrid('reload');
	
};

//选中用户赋值操作
function selectedUser(user) {
	var id = user.id;
	var name = user.name;
	$('#userId').val(id);
	$('#dlg_add').find('input[name="userName"]').val(name);
	$('#window_chooseUser').window('close');
}
//搜索
function userSearch(){
	var queryParams = $('#chooseUserList').datagrid('options').queryParams;
	queryParams.code = $('#window_chooseUser #userSearchCode').val();
	queryParams.name = $('#window_chooseUser #userSearchName').val();
	setFirstPage("#chooseUserList");
	$('#chooseUserList').datagrid('options').queryParams = queryParams;
	$("#chooseUserList").datagrid('reload');

}




