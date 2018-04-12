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
        url: 'businessCompany/getSearchJson',
        fitColumns : true,
	    border : false,
	    fit:true,
	    singleSelect:false,
	    pagination : true,
	    striped: true,
	    pageSize:10,     
	    rownumbers : true,
	    checkOnSelect:true,	
        columns : [ [ 
		{
			field : 'id',
			title : 'ID'
		},                    
		{
			field : 'name',
			title : '本方账户名称'
		},                    
        {
			field : 'shortName',
			title : '本方账户简称' ,
			
		},
	    {
	    	field : 'account',
	    	title : '本方账户'
	    },
	    {
	    	field : 'type',
	    	title : '账号类型'
	    },
	    {
	    	field : 'businessCode',
	    	title : 'TPP配置的业务代码'
	    },
	    {
	    	field : 'status',
	    	title : '状态'
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="businessCompanyEditWindow(' + row.id + ');">修改</a>' ;
//				oper += ' | <a href="javascript:void(0)" onclick="deleteBusinessCompany(' + row.id + ');">删除</a>';
				//oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="lockSysUser(' + row.id + ',' + (row.status == 0?1:0) + ');">' + (row.status == 0?'锁定':'恢复') + '</a>';
				/*if(userType == 1){
					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedUser(' + row.id + ');">删除</a>';
				}*/
				return oper;
			},
			width : 150
	    }	
		] ]
	});

    // 设置分页控件
   var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });

    $('#addPanel').window('close');
	$('#updatePanel').window('close');
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
	
	// 新建按钮
	//$('#createBt').bind('click', create);
    
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	
  
});

function openWindows (){
	$('#addPanel').window({
		modal:true
	});
}



//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.Name = $('#toolbar #name').val();
	
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};


//新增参数 保存
function submitAdd() {
	var formObj = $('#addForm');
	if (formObj.form("validate")) {
		$.post('businessCompany/addBusinessCompany', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addPanel').window('close');
			}
		})
	}
}

//修改参数 保存
function submitUpdateManager() {
	var formObj = $('#updateForm');
	if (formObj.form("validate")) {
		$.post('businessCompany/updateBusinessCompany', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.success) {
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


//获取并修改参数信息 弹窗
function businessCompanyEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'businessCompany/getBusinessCompany',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#updatePanel').window({
					modal:true
				});
				$('#updatePanel').form('clear');
				$('#updatePanel').form('load', result.businessCompany);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
	
}

//删除
function deleteBusinessCompany (id) {
	if (!id) {return;}
	$.ajax({
		url : 'businessCompany/deleteBusinessCompany',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			var isErr = '';
			if (result.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
	
}

function isDisabled(value){
	var rebvalue = "有效";
	if(value == null || value == 0){
		rebvalue = "有效";
	}else if(value == 1){
		rebvalue = "无效";
	}
	return rebvalue;
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

function CurentTime(num)
{ 
	var preDate = new Date();
	var  now= new Date(preDate.getTime() +num*24*60*60*1000);
    
   
    var year = now.getFullYear();       //年
    var month = now.getMonth()+1;     //月
    var day = now.getDate();     //日
   
   
    var clock = year + "-";
   
    clock += month+"-";     
    clock += day;
   
    return(clock); 
} 
