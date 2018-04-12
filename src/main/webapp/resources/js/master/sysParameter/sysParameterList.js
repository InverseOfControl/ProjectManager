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
        url: 'sysParameter/sysParameterList',
        fitColumns : true,
	    border : false,
	    fit:true,
	    singleSelect:false,
	    pagination : true,
	    striped: true,
	    pageSize:10,     
	    rownumbers : true,
	    checkOnSelect:true,	
        queryParams: {
	    	code : $('#code').val(),
	    	name : $('#name').val(),
	    	//createdTimeStart  : $('#createdTimeStart').val(),
	    	//createdTimeEnd  : $('#createdTimeEnd').val(),
	    	//modifiedTimeStart  : $('#modifiedTimeStart').val(),
	    	//modifiedTimeEnd  : $('#modifiedTimeEnd').val(),
		},
        columns : [ [ 
		{
			field : 'id',
			title : 'ID',
			//formatter : linkDetail
		},                    
		{
			field : 'code',
			title : '属性代码',
			//formatter : linkDetail
		},                    
        {
			field : 'name',
			title : '属性名称' ,
			
		},
	    {
	    	field : 'parameterValue',
	    	title : '属性值',
	    },
	    {
	    	field : 'spmversion',
	    	title : '版本',
	    },
	    {
	    	field : 'isDisabled',
	    	title : '是否有效',
	    	formatter: function(value, row, index){
				if(value == 0 || value =='' || value == null){
					return '有效';
				}else if(value == 1){
					return '无效';
				}else{
					return '';
				}
	        }
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="sysParamEditWindow(' + row.id + ');">修改</a>' ;
				oper += ' | <a href="javascript:void(0)" onclick="sysParamDeleteWindow(' + row.id + ');">删除</a>' ;
				oper += ' | <a href="javascript:void(0)" onclick="sysParamDetailWindow(' + row.id + ');">详情</a>';
				oper += ' | <a href="javascript:void(0)" onclick="sysParamFlushWindow(' + row.id + ');">刷新</a>';
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

    $('#addSysParameterPanel').window('close');
	$('#updateSysParameterPanel').window('close');
	$('#detailSysParameterPanel').window('close');
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
	
	$('#createdTimeStart').val(CurentTime(0));  
	$('#createdTimeEnd').val(CurentTime(0));  
	$('#modifiedTimeStart').val(CurentTime(0));  
	$('#modifiedTimeEnd').val(CurentTime(0));  
  
});

function openWindows (){
	$('#addSysParameterPanel').window({
		modal:true
	});
}

//刷新
function sysParamFlushWindow(id){
	if (!id) {return;}
	//获取当前
	var pageNo = $('#list_result').datagrid('getPager').data("pagination").options.pageNumber;
	var paramData = {id:id};
	$.post('sysParameter/sysParameterFlush', paramData, function(data) {
		var isErr = '';
		if (data.success) {
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
	queryParams.code = $('#toolbar #code').val();
	queryParams.name = $('#toolbar #name').val();
	queryParams.createdTimeStart  = $('#toolbar #createdTimeStart').val();
	queryParams.createdTimeEnd  = $('#toolbar #createdTimeEnd').val();
	queryParams.modifiedTimeStart  = $('#toolbar #modifiedTimeStart').val();
	queryParams.modifiedTimeEnd  = $('#toolbar #modifiedTimeEnd').val();
	
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

//删除枚举信息
function sysParamDeleteWindow(id){
	if (!id) {return;}
	var paramData = {id:id};
	$.post('sysParameter/deleteParameter', paramData, function(data) {
		var isErr = '';
		if (data.isSuccess) {
		} else {
			isErr = 'error';
		}
		$.messager.alert('操作提示', data.msg, isErr);
		$('#searchBut').trigger('click');
		
	})
	
}

//获取并修改参数信息 弹窗
function sysParamEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'sysParameter/getSysParameter',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#updateSysParameterPanel').window({
					modal:true
				});
				$('#updateSysParameterPanel').form('clear');
				$('#updateSysParameterPanel').form('load', result.sysParameter);
				$('#updateSysParameterForm').find('input[name="id"]').val(result.sysParameter.id);
				$('#updateSysParameterForm').find('input[name="code"]').val(result.sysParameter.code);
				$('#updateSysParameterForm').find('input[name="name"]').val(result.sysParameter.name);
				$('#updateSysParameterForm').find('input[name="parameterValue"]').val(result.sysParameter.parameterValue);
				$('#updateSysParameterForm').find('input[name="remark"]').val(result.sysParameter.remark);
				$('#updateSysParameterForm').find('input[name="spmversion"]').val(result.sysParameter.spmversion);
				$('#updateSysParameterForm').find('input[name="isDisabled"] option[value="'+result.sysParameter.isDisabled+'"]').attr("selected",true);

			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
	
}


//修改参数 保存
function submitUpdateSysParam() {
	var formObj = $('#updateSysParameterForm');
	var id = $('#updateSysParameterForm #id').val();
	var code = $('#updateSysParameterForm #code').val();
	var name = $('#updateSysParameterForm #name').val();
	var parameterValue = $('#updateSysParameterForm #parameterValue').val();
	var remark  = $('#updateSysParameterForm #remark').val();
	var version = $('#updateSysParameterForm #spmversion').val();
	var isDisabled = $('#updateSysParameterForm #isDisabled').combobox('getValue');
	var spmData = {id:id,code:code,name:name,parameterValue:parameterValue,remark:remark,spmversion:version,isDisabled:isDisabled};
	if (formObj.form("validate")) {
		$.post('sysParameter/updateSysParameter', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateSysParameterPanel').window('close');
			}
		})
	}
}

//新增参数 保存
function submitAddSysParam() {
	var formObj = $('#addSysParameterForm');
	var id = $('#addSysParameterForm #id').val();
	var code = $('#addSysParameterForm #code').val();
	var name = $('#addSysParameterForm #name').val();
	var parameterValue = $('#addSysParameterForm #parameterValue').val();
	var remark  = $('#addSysParameterForm #remark').val();
	var version = $('#addSysParameterForm #spmversion').val();
	var isDisabled = $('#addSysParameterForm #isDisabled').val();
	var spmData = {id:id,code:code,name:name,parameterValue:parameterValue,remark:remark,version:version,isDisabled:isDisabled};
	if (formObj.form("validate")) {
		$.post('sysParameter/addSysParameter', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addSysParameterPanel').window('close');
			}
		})
	}
}

//参数详细信息 弹窗
function sysParamDetailWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'sysParameter/getSysParameter',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#detailSysParameterPanel').window({
					modal:true
				});
				$('#detailSysParameterPanel').form('clear');
				$('#detailSysParameterPanel').form('load', result.sysParameter);
				$('#detailSysParameterForm').find('#code').val(result.sysParameter.code);
				$('#detailSysParameterForm').find('#name').val(result.sysParameter.name);
				$('#detailSysParameterForm').find('#parameterValue').val(result.sysParameter.parameterValue);
				$('#detailSysParameterForm').find('#remark').val(result.sysParameter.remark);
				$('#detailSysParameterForm').find('#spmversion').val(result.sysParameter.spmversion);
				$('#detailSysParameterForm').find('#isDisabled').val(isDisabled(result.sysParameter.isDisabled));

			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
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
