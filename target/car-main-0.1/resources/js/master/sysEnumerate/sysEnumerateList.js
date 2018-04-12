var url;
$(function () {
	// 查询按钮
	$('#searchBut').bind('click', search);
	
    $('#list_result').datagrid({
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
        url: 'sysEnumerate/getSysEnumerateList',
        fitColumns : true,
	    border : false,
	    singleSelect:false,
	    fit:true,
	    pagination : true,
	    striped: true,
	    pageSize:10,     
	    rownumbers : true,
	    checkOnSelect:true,	
        queryParams: {
        	enumType : $('#enumType').val(),
        	enumCode : $('#enumCode').val(),
        	enumValue  : $('#enumValue').val(),
		},
        columns : [ [ 
		{
			field : 'enumType',
			title : '数据类型',
			//formatter : linkDetail
		},                    
        {
			field : 'enumCode',
			title : '数据代码' ,
			
		},
	    {
	    	field : 'enumValue',
	    	title : '数据值',
	    },
	    {
	    	field : 'enumversion',
	    	title : '版本',
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="enumEditWindow(' + row.id + ');">修改</a>';
				oper += ' | <a href="javascript:void(0)" onclick="enumDeleteWindow(' + row.id + ');">删除</a>';
				//oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="lockSysUser(' + row.id + ',' + (row.status == 0?1:0) + ');">' + (row.status == 0?'锁定':'恢复') + '</a>';
				/*if(userType == 1){
					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedUser(' + row.id + ');">删除</a>';
				}*/
				return oper;
			},
			width : 150,
	    }	
		] ]
	});

    // 设置分页控件
   var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });

   
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	
	$('#addEnumPanel').window('close');
	$('#updateEnumPanel').window('close');
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
		
  
});

function openWindows (){
	$('#addEnumPanel').window({
		modal:true
	});
}

//删除枚举信息
function enumDeleteWindow(id){
	if (!id) {return;}
	var paramData = {id:id};
	$.post('sysEnumerate/deleteEnumerate', paramData, function(data) {
		var isErr = '';
		if (data.isSuccess) {
		} else {
			isErr = 'error';
		}
		$.messager.alert('操作提示', data.msg, isErr);
		$('#searchBut').trigger('click');
		
	})
	
}

//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.enumType = $('#toolbar #enumType').val();
	queryParams.enumCode = $('#toolbar #enumCode').val();
	queryParams.enumValue  = $('#toolbar #enumValue').val();
	
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

//获取并修改银行信息 弹窗
function enumEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'sysEnumerate/getSysEnumerate',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#updateEnumPanel').window({
					modal:true
				});
				$('#updateEnumPanel').form('clear');
				$('#updateEnumPanel').form('load', result.SysEnumerate);
				$('#updateEnumForm').find('input[name="id"]').val(result.SysEnumerate.id);
				$('#updateEnumForm').find('input[name="enumType"]').val(result.SysEnumerate.enumType);
				$('#updateEnumForm').find('input[name="enumCode"]').val(result.SysEnumerate.enumCode);
				$('#updateEnumForm').find('input[name="enumValue"]').val(result.SysEnumerate.enumValue);
				$('#updateEnumForm').find('input[name="enumversion"]').val(result.SysEnumerate.enumversion);

			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}


//修改银行 保存
function submitUpdateEnum() {
	var formObj = $('#updateEnumForm');
	var id = $('#updateEnumForm #id').val();
	var enumType = $('#updateEnumForm #enumType').val();
	var enumCode = $('#updateEnumForm #enumCode').val();
	var enumValue = $('#updateEnumForm #enumValue').val();
	var enumversion = $('#updateEnumForm #enumversion').val();
	var enumData = {id:id,enumType:enumType,enumCode:enumCode,enumValue:enumValue,enumversion:enumversion};
	if (formObj.form("validate")) {
		$.post('sysEnumerate/updateSysEnumerate', enumData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateEnumPanel').window('close');
			}
		})
	}
}

//新增银行 保存
function submitAddEnum() {
	var formObj = $('#addEnumForm');
	var id = $('#addEnumForm #id').val();
	var enumType = $('#addEnumForm #enumType').val();
	var enumCode = $('#addEnumForm #enumCode').val();
	var enumValue = $('#addEnumForm #enumValue').val();
	var enumversion = $('#addEnumForm #enumversion').val();
	var enumData = {id:id,enumType:enumType,enumCode:enumCode,enumValue:enumValue,enumversion:enumversion};
	if (formObj.form("validate")) {
		$.post('sysEnumerate/addSysEnumerate', enumData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addEnumPanel').window('close');
			}
		})
	}
}

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