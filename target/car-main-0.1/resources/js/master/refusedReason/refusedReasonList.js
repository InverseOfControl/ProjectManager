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
        url: 'reasonManager/getReasonList',
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
	    	reason : $('#toolbar #refusedReason').val(),
		},
        columns : [ [ 
         {
        	 field : 'id',
        	 title : 'ID',
        	 //formatter : linkDetail
         },                    
		{
			field : 'reason',
			title : '拒绝原因',
			//formatter : linkDetail
		},                    
        {
			field : 'type',
			title : '拒绝分类' ,
	    	formatter: function(value, row, index){
				if(value == 1){
					return '一级原因';
				}else if(value == 2){
					return '二级原因';
				}else{
					return '';
				}
	        }
			
		},
	    {
	    	field : 'parentReason',
	    	title : '父原因',
	    },
	    {
	    	field : 'levelOrder',
	    	title : '排序',
	    },
	    {
	    	field : 'canRequestDays',
	    	title : '限制时间（天）',
	    },
	    {
	    	field : 'isDeleted',
	    	title : '是否有效',
	    	formatter: function(value, row, index){
				if(value == 1){
					return '无效';
				}else if(value == 0){
					return '有效';
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
				var isDeleted = row.isDeleted;
				var str ='';
				if(isDeleted == 0){
					str = "禁用";
				}else{
					str = "启用";
				}
				oper = '<a href="javascript:void(0)" onclick="openUpdatePanel(' + row.id + ');">修改</a>';
				oper += ' | <a href="javascript:void(0)" onclick="reasonDeleteWindow(' + row.id +','+row.isDeleted+ ');">'+str+'</a>';
				oper += ' | <a href="javascript:void(0)" onclick="reasonDetailWindow(' + row.id + ');">详情</a>';
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

    $('#addReasonPanel').window('close');
	$('#updateReasonPanel').window('close');
	$('#reasonDetailPanel').window('close');
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
	
	//校验数字
    $.extend($.fn.validatebox.defaults.rules, {
	//金额校验
	    checkNum:{
	        validator: function (value) {
	            return (/^[1-9]\d*$/).test(value);
	         },
	         message:'请输入正确的数字'
	    }
      });
  
});

function openWindows (){
	$('#addReasonPanel').window({
		modal:true
	});
	
    //加载父原因下拉列表
    $('#addReasonPanel #addReasonForm #parentId').combobox({
    		  url:'reasonManager/findAllParentReasonList',
    		    valueField:'id',
    		    textField:'reason',
    		    onLoadSuccess:function(){
    	        	var data = $(this).combobox('getData');
    	        	if(data.length > 0)
    	        		$(this).combobox('select', data[0].id);
    	        }
    });
    
//    $('#addReasonPanel #addReasonForm #reason').val("");
//    $('#addReasonPanel #addReasonForm #levelOrder').val("");
//    $('#addReasonPanel #addReasonForm #canRequestDays').val("");
//    $('#addReasonPanel #addReasonForm #remark').val("");
    $('#addReasonPanel').form('clear');
}

//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.reason = $('#toolbar #refusedReason').val();
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

function openUpdatePanel(id){
	$('#updateReasonPanel').window({
		modal:true
	});
  
    reasonEditWindow(id);
}

function reasonDetailWindow(id){
	$('#reasonDetailPanel').window({
		modal:true
	});
  
    reasonDetailShowWindow(id);
}

//弹出修改页面（获取并修改原因信息 弹窗）
function reasonEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'reasonManager/getReasonById',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				var levelOrder = result.reason.levelOrder;
				var canRequestDays = result.reason.canRequestDays;
				  //加载父原因下拉列表
			    $('#updateReasonPanel #updateReasonForm #parentId').combobox({
			    		  url:'reasonManager/findAllParentReasonList',
			    		    valueField:'id',
			    		    textField:'reason',
			    		    onLoadSuccess:function(){
			    	        	var data = $(this).combobox('getData');
			    	        	if(data.length > 0)
			    	        		$(this).combobox('select', result.reason.parentId); //选中当前的父原因
			    	        }
			    });
				$('#updateReasonPanel').form('clear');
				$('#updateReasonPanel').form('load', result.reason);
				$('#updateReasonForm').find('input[name="reason"]').val(result.reason.id);
				$('#updateReasonForm').find('input[name="reason"]').val(result.reason.reason);
				$('#updateReasonForm').find('input[name="levelOrder"]').val(levelOrder);
				$('#updateReasonForm').find('input[name="remark"]').val(result.reason.remark);
				$('#updateReasonForm').find('input[name="canRequestDays"]').val(canRequestDays);
				$('#updateReasonForm').find('input[name="version"]').val(result.reason.version);
				$('#updateReasonForm').find('input[name="isDeleted"]').val(result.reason.isDeleted);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//查看详情
function reasonDetailShowWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'reasonManager/getReasonById',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				var levelOrder = result.reason.levelOrder;
				var canRequestDays = result.reason.canRequestDays;
				  //加载父原因下拉列表
			    $('#reasonDetailPanel #reasonDetailForm #parentId').combobox({
			    		  url:'reasonManager/findAllParentReasonList',
			    		    valueField:'id',
			    		    textField:'reason',
			    		    onLoadSuccess:function(){
			    	        	var data = $(this).combobox('getData');
			    	        	if(data.length > 0)
			    	        		$(this).combobox('select', result.reason.parentId); //选中当前的父原因
			    	        }
			    });
				$('#reasonDetailPanel').form('clear');
				$('#reasonDetailPanel').form('load', result.reason);
				$('#reasonDetailForm').find('input[name="reason"]').val(result.reason.id);
				$('#reasonDetailForm').find('input[name="reason"]').val(result.reason.reason);
				$('#reasonDetailForm').find('input[name="levelOrder"]').val(levelOrder);
				$('#reasonDetailForm').find('input[name="remark"]').val(result.reason.remark);
				$('#reasonDetailForm').find('input[name="canRequestDays"]').val(canRequestDays);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//禁用/启用
function reasonDeleteWindow(id,isDeleted){
	if (!id) {return;}
	var paramData = {id:id,isDeleted:isDeleted};
	$.post('reasonManager/disableReason', paramData, function(data) {
		var isErr = '';
		if (data.isSuccess) {
		} else {
			isErr = 'error';
		}
		$.messager.alert('操作提示', data.msg, isErr);
		$('#searchBut').trigger('click');
		
	});
	
}

//修改拒绝原因保存
function submitUpdateReason() {
	var formObj = $('#updateReasonForm');
	var id = $('#updateReasonForm #id').val();
	var version = $('#updateReasonForm #version').val();
	var isDeleted = $('#updateReasonForm #isDeleted').val();
	var reason = $('#updateReasonForm #reason').val();
	var parentId = $('#updateReasonForm #parentId').combobox('getValue');
	var levelOrder = $('#updateReasonForm #levelOrder').val();
	var canRequestDays = $('#updateReasonForm #canRequestDays').val();
	var remark  = $('#updateReasonForm #remark').val();
	var sysParameterData = {id:id,reason:reason,parentId:parentId,levelOrder:levelOrder,canRequestDays:canRequestDays,remark:remark,version:version,isDeleted:isDeleted};
	if (formObj.form("validate")) {
		$.post('reasonManager/updateReason', sysParameterData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateReasonPanel').window('close');
			}
			
		});
	}
}

//新增拒绝原因保存
function submitAddReason() {
	var formObj = $('#addReasonForm');
	var reason = $('#addReasonForm #reason').val();
	var parentId = $('#addReasonForm #parentId').combobox('getValue');
	var levelOrder = $('#addReasonForm #levelOrder').val();
	var canRequestDays = $('#addReasonForm #canRequestDays').val();
	var remark  = $('#addReasonForm #remark').val();
	var sysParameterData = {reason:reason,parentId:parentId,levelOrder:levelOrder,canRequestDays:canRequestDays,remark:remark};
	if (formObj.form("validate")) {
		$.post('reasonManager/addReason', sysParameterData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addReasonPanel').window('close');
			}
		});
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