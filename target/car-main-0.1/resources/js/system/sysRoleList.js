/**
 * 
 */
$(function () {

	// 查询按钮
	$('#searchBut').bind('click', search);
	
    $('#sysRoleGrid').datagrid({
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
        url: 'sysRole/sysRoleList',
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
			title :  'ID'
        },
        {
        	field : 'code',
        	title : '角色编号'
        },
        {
        	field : 'name',
        	title : '角色名称'
   
        },
        {
			field : 'memo',
			title : '备注'
		},

        {
        	field : 'status',
        	title : '状态'
        },

		{
			field : 'createdTime',
			title : '创建时间'
		},	
		{
			field : 'creatorId',
			title : '创建人ID'
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
			title : '修改人ID'
        },
        {
			field : 'modifierId',
			title : '修改人'
		},
		{
            field : 'version',
            title : '版本号'
       
        },
		{
			field : 'a',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="loadSysRoleToWindow(' + row.id + ');">修改</a>'
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedSysRole(' + row.id + ');">删除</a>';
				
				return oper;
			}
		}
		] ]
	});
    // 设置分页控件
   var p = $('#sysRoleGrid').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });  
	$('#sysRolePanel').window('close');
	$('#updatePanel').window('close');
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
});

function openWindows (){
	$('#sysRolePanel').window({
		modal:true
	});
	
	/* CM-2747 zhangds begin */
	
	$('#sysRolePanel #code').val('');
	$('#sysRolePanel #name').val('');
	$('#sysRolePanel #memo').val('');
	
    //加载权限组数据
    $('#sysRolePanel #sysPermissionTree').tree({
    	url : 'sysPermission/sysPermissionTree',
    	method : 'post',
    	animate : true,
    	checkbox : true,
    	onLoadSuccess:function(){  
    		
    	}
    });
    /* CM-2747 zhangds end */
}
/**
 * 条件查询
 * */
function search(){
	var queryParams = $('#sysRoleGrid').datagrid('options').queryParams;
	queryParams.code = $('#toolbar #code').val();
	queryParams.name =  $('#toolbar #name').val();
    setFirstPage("#sysRoleGrid");
    $('#sysRoleGrid').datagrid('options').queryParams = queryParams;
    $("#sysRoleGrid").datagrid('reload');
}

/* CM-2747 zhangds begin */
/**
 * 加某个角色
 * */
function loadSysRoleToWindow(id){
	if (!id) {return;}
	
    //加载权限组数据
    $('#updatePanel #sysPermissionTree2').tree({
    	url : 'sysPermission/sysPermissionTree',
    	method : 'post',
    	animate : true,
    	checkbox : true,
    	lines : true,
    	onLoadSuccess:function(){  
    		//默认选中已绑定的权限菜单
    		var treeObj = $('#updatePanel #sysPermissionTree2');
    		$.ajax({
    			type: 'POST',
    			url: 'sysRole/queryMyPermissionList' ,
    			data: {'id':id} ,
    		    dataType: 'json',
    		    success: function(data) {
    		    	var permissions = data.permissions;
    		    	if (permissions) {
    		    		for (var i=0;i<permissions.length;i++) {
    		    			var permissionId = permissions[i];
    		    			var node = treeObj.tree('find',permissionId);
    		    			if (node) {
    		    				treeObj.tree('check',node.target);
    		    			}
    		    		}
    		    	}
    		    } ,
    		    error : function() {
    		    	$.messager.alert('操作提示', '请求失败,请检查URL和参数!','error');
    		    }
    		});
    	}
    });

	$.ajax({
		url : 'sysRole/loadSysRole',
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
				$('#updatePanel').form('load', result.sysRole);
				$('#udpateForm').find('input[name="id"]').val(result.sysRole.id);
				$('#udpateForm').find('input[name="name"]').val(result.sysRole.name);
				$('#udpateForm').find('input[name="code"]').val(result.sysRole.code);
				$('#udpateForm').find('input[name="memo"]').val(result.sysRole.url);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}
/* CM-2747 zhangds end */

/**
 * 新增角色
 */
function insertSysRole() {
	var formObj = $('#sysRoleForm');
	if (formObj.form("validate")) {
			var code = $('#sysRolePanel #code').val();
			var name = $('#sysRolePanel #name').val();
			var memo = $('#sysRolePanel #memo').val();
			var sysPermissionList = [];
			var  checkNodes = $('#sysRolePanel #sysPermissionTree').tree('getChecked');
			for(var i = 0 ; i < checkNodes.length; i ++){
				sysPermissionList.push(checkNodes[i].id);
			}
			
			/* CM-2747 zhangds begin */
			var indeterminateNodes = $('#sysRolePanel #sysPermissionTree').tree('getChecked','indeterminate');
			for(var i = 0 ; i < indeterminateNodes.length; i ++){
				if(indeterminateNodes[i].id!="0"){
					sysPermissionList.push(indeterminateNodes[i].id);
				}
			}
			/* CM-2747 zhangds end */
			
		$.ajax({
			type: 'POST',
			url: 'sysRole/insertSysRole',
			data: {'code':code,'name':name,'memo':memo,'sysPermissionList':sysPermissionList.join(',')} ,
		    dataType: 'json',
		    success: function(data) {
				var isErr = '';
				if(data.isSuccess){
					
				} else {
					isErr = 'error';
				}
				$.messager.alert('操作提示', data.msg,isErr);
				$('#searchBut').trigger('click');
				if (!(data.msg == '新增失败')) {
					$('#sysRolePanel').window('close');
				}
		    }	
		});
	}
}

/**
 * 修改角色
 */
function update() {
	var formObj = $('#updateForm');
	if (formObj.form("validate")) {
		var id = $('#updatePanel #id').val();
		var code = $('#updatePanel #code').val();
		var name = $('#updatePanel #name').val();
		var memo = $('#updatePanel #memo').val();
		var sysPermissionList = [];
		var  checkNodes = $('#updatePanel #sysPermissionTree2').tree('getChecked');
		for(var i = 0 ; i < checkNodes.length; i ++){
			sysPermissionList.push(checkNodes[i].id);
		}
		
		/* CM-2747 zhangds begin */
		var indeterminateNodes = $('#updatePanel #sysPermissionTree2').tree('getChecked','indeterminate');
		for(var i = 0 ; i < indeterminateNodes.length; i ++){
			if(indeterminateNodes[i].id!="0"){
				sysPermissionList.push(indeterminateNodes[i].id);
			}
		}
		/* CM-2747 zhangds end */
		
		$.ajax({
			type: 'POST',
			url: 'sysRole/update',
			data: {'id' : id,'code':code,'name':name,'memo':memo,'sysPermissionList':sysPermissionList.join(',')} ,
		    dataType: 'json',
		    success: function(data) {
				var isErr = '';
				if(data.isSuccess){
					
				} else {
					isErr = 'error';
				}
				$.messager.alert('操作提示', data.msg,isErr);
				$('#searchBut').trigger('click');
				if (!(data.msg == '修改失败')) {
					$('#updatePanel').window('close');
				}
		    }	
		});
	}
}

/**
 * 删除操作
 */
function deletedSysRole(id) {	
	if (!id) {return;}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.ajax({
	    		url : 'sysRole/delete',
	    		data : {
	    			id : id						
	    		},
	    		type:'POST',
	    		success : function(result){
	    			if (result.isSuccess) {
	    				$.messager.alert('操作提示', '操作成功');
	    				 $("#sysRoleGrid").datagrid('reload');
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