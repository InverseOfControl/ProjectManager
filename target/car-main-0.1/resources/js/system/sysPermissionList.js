/**
 * 
 */
$(function () {
	// 查询按钮
	$('#searchBut').bind('click', search);
	
    $('#sysPermissionGrid').datagrid({
  	  onLoadSuccess:function(data){ 
		  if(data.total==0)
		  {
		    $.messager.show({
                title:'结果',
                msg:'没查到符合条件的数据！',
                showType:'slide'
            });
		  }
  	  },
        url: 'sysPermission/list',
    	fitColumns : true,
    	fit:true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,
        checkOnSelect: false,
        selectOnCheck: false,
        rownumbers : true,
        columns : [ [ 

        {
			field : 'id',
			title :  'ID',
        },
        {
        	field : 'code',
        	title : '权限编号',
        },
        {
        	field : 'name',
        	title : '权限名称',
   
        },
        {
			field : 'url',
			title : 'URL地址',
		},
        {
        	field : 'levels',
        	title : '权限等级',

        },
        {
        	field : 'levelOrder',
        	title : '排序',
        },
        {
        	field : 'parentId',
        	title : '上级编号',
        },
        {
			field : 'type',
			title : '类型',
		},
        {
        	field : 'status',
        	title : '状态',
        },

		{
			field : 'createdTime',
			title : '创建时间',
		},	
		{
			field : 'creatorId',
			title : '创建人ID',
		},
		{
			field : 'creator',
			title : '创建人'
		},

		{
			field : 'modifiedTime',
			title : '修改时间'
		},
        {
			field : 'modifierId',
			title : '修改人ID',
        },
        {
			field : 'modifierId',
			title : '修改人',
		},
		{
            field : 'version',
            title : '版本号',
       
        },
		{
			field : 'a',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="loadSyspermissionToWindow(' + row.id + ');">修改</a>'
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedPermission(' + row.id + ');">删除</a>';
				
				return oper;
			}
		}
		] ]
	});
    // 设置分页控件
   var p = $('#sysPermissionGrid').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });  
	$('#sysPermissionPanel').window('close');
	$('#updatePanel').window('close');
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
});

function openWindows (){
	$('#sysPermissionPanel').window({
		modal:true
	});
}
/**
 * 条件查询
 * */
function search(){
	var queryParams = $('#sysPermissionGrid').datagrid('options').queryParams;
	queryParams.code = $('#toolbar #code').val();
	queryParams.name =  $('#toolbar #name').val();
	queryParams.levels =  $('#toolbar #levels').val();
	queryParams.parentId =  $('#toolbar #parentId').val();
    setFirstPage("#sysPermissionGrid");
    $('#sysPermissionGrid').datagrid('options').queryParams = queryParams;
    $("#sysPermissionGrid").datagrid('reload');
}
/**
 * 加某个权限菜单
 * */
function loadSyspermissionToWindow(id){
	if (!id) {return;}
	$.ajax({
		url : 'sysPermission/loadSysPermission',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#updatePanel').window({
					modal:true
				});
				$('#updatePanel').form('clear');
				$('#updatePanel').form('load', result.sysPermission);
				$('#udpateForm').find('input[name="id"]').val(result.sysPermission.id);
				$('#udpateForm').find('input[name="name"]').val(result.sysPermission.name);
				$('#udpateForm').find('input[name="code"]').val(result.sysPermission.code);
				$('#udpateForm').find('input[name="url"]').val(result.sysPermission.url);
				$('#udpateForm').find('input[name="levels"]').val(result.sysPermission.levels);
				$('#udpateForm').find('input[name="levelOrder"]').val(result.sysPermission.levelOrder);
				$('#udpateForm').find('input[name="parentId"]').val(result.sysPermission.parentId);
				
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

/**
 * 新增权限
 */
function insertSysPermission() {
	var formObj = $('#sysPermissionForm');
	if (formObj.form("validate")) {
		$.post('sysPermission/insertSysPermission', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#sysPermissionPanel').window('close');
			}
		})
	}
}
/**
 * 修改权限
 */
function update() {
	var formObj = $('#updateForm');
	if (formObj.form("validate")) {
		$.post('sysPermission/update', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updatePanel').window('close');
			}
		})
	}
}
/**
 * 删除操作
 */
function deletedPermission(id) {	
	if (!id) {return;}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.ajax({
	    		url : 'sysPermission/delete',
	    		data : {
	    			id : id						
	    		},
	    		type:'POST',
	    		success : function(result){
	    			if (result.isSuccess) {
	    				$.messager.alert('操作提示', '操作成功');
	    				 $("#sysPermissionGrid").datagrid('reload');
	    			} else {
	    				$.messager.alert('操作提示', result.msg,'error');
	    			}
	    		},
	    		error:function(data){
	    			$.messager.alert('操作提示', 'error','error');
	    		}
	    	});
	    }    
	}); 

}
//自定义 验证规则validType="selectValueRequired"
$.extend($.fn.validatebox.defaults.rules, {
	selectValueRequired : {
	    validator : function(value, param) {
		    if (value == "") {
			    return false;
		    } else {
			    return true;
		    }
	    },
	    message : '该下拉框为必选项'
	}
});