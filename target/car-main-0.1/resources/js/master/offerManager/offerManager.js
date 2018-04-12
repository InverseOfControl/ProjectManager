var url;
$(function () {
	// 查询按钮
	$('#searchBut').bind('click', search);
	//按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	$('#addOfferPanel').window('close');
	$('#detailOfferPanel').window('close');
	$('#updateOfferPanel').window('close');
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
	
	
	
    $('#list_result').datagrid({
    	onLoadSuccess:function(data){
    		if(data.total==0){
    			$.messager.show({
                  title:'结果',
                  msg:'没查到符合条件的数据！',
                  showType:'slide'
              });
  		  	}
    	},
        url: 'offerManager/offerManagerListData',
        fitColumns : true,
        fit:true,
	    border : false,
	    singleSelect:false,
	    pagination : true,
	    striped: true,
	    pageSize:10,     
	    rownumbers : true,
	    checkOnSelect:true,	
        queryParams: {
        	offerDay : $('#offerDay').val()
		},
        columns : [ [ 
		{
			field : 'day',
			title : '报盘日期',
		},                    
        {
			field : 'beforeDay',
			title : '前几天' ,
		},
	    {
	    	field : 'afterDay',
	    	title : '后几天',
	    },
	    {
	    	field : 'generateTime',
	    	title : '生成报盘时间',
	    },
	    {
	    	field : 'sendTime',
	    	title : '发送报盘时间',
	    },
	    {
	    	field : 'status',
	    	title : '是否有效',
	    	formatter: function(value, row, index){
				if( value ==1){
					return '是';
				}else 
					return '否';
			}
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper += '<a href="javascript:void(0)" onclick="offerDetail(' + row.id + ');">详情</a>';
				if(row.status==0){
					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="setStatus(' + row.id + ',1);">启用</a>';
				}else{
					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="setStatus(' + row.id + ',0);">禁用</a>';
				}
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="updateOfferManage(' + row.id + ');">修改</a>';
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
});

function setStatus(id,status){
	var spmData = {"id":id,"status":status};
	$.post('offerManager/setStatus', spmData, function(data) {
		$('#searchBut').trigger('click');
		$.messager.alert('操作提示', data.msg);
	});
}

//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.offerDay =  $('#offerDay').val();
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

function openWindows (){
	$('#addOfferPanel').window({
		modal:true
	});
}

//新增参数 保存
function submitAddOffer() {
	var formObj = $('#addOfferForm');
	var spmData = $('#addOfferForm').serialize();
	
	if (formObj.form("validate")) {
		$.post('offerManager/addOfferManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addOfferPanel').window('close');
			}
		});
	}
}


//获取并修改参数信息 弹窗
function updateOfferManage(id) {
	if (!id) {return;}
	$.ajax({
		url : 'offerManager/getOfferManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#updateOfferPanel').window({
					modal:true
				});
				$('#updateOfferPanel').form('clear');
				$('#updateOfferPanel').form('load', result.productDetailManager);
				$('#updateOfferForm').find('input[name="id"]').val(result.offerManager.id);
				$('#updateOfferForm').find('input[name="status"]').val(result.offerManager.status);
				$('#updateOfferForm').find('#day').numberbox('setValue', result.offerManager.day);
				$('#updateOfferForm').find('#beforeDay').numberbox('setValue', result.offerManager.beforeDay);
				$('#updateOfferForm').find('#afterDay').numberbox('setValue', result.offerManager.afterDay);
				$('#updateOfferForm').find('input[name="generateTime"]').val(result.offerManager.generateTime);
				$('#updateOfferForm').find('input[name="sendTime"]').val(result.offerManager.sendTime);
				$('#updateOfferForm').find('#remark').val(result.offerManager.remark);
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
function submitUpdateOffer() {
	var formObj = $('#updateOfferForm');
	var spmData = $('#updateOfferForm').serialize();
	
	if (formObj.form("validate")) {
		$.post('offerManager/updateOfferManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateOfferPanel').window('close');
			}
		});
	}
}



//参数详细信息 弹窗
function offerDetail (id) {
	if (!id) {return;}
	$.ajax({
		url : 'offerManager/getOfferManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#detailOfferPanel').window({
					modal:true
				});
				$('#detailOfferPanel').form('clear');
				$('#detailOfferPanel').form('load', result.offerManager);
				$('#detailOfferForm').find('#day').val(result.offerManager.day);
				$('#detailOfferForm').find('#beforeDay').val(result.offerManager.beforeDay);
				$('#detailOfferForm').find('#afterDay').val(result.offerManager.afterDay);
				$('#detailOfferForm').find('#generateTime').val(result.offerManager.generateTime);
				$('#detailOfferForm').find('#sendTime').val(result.offerManager.sendTime);
				$('#detailOfferForm').find('#remark').val(result.offerManager.remark);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

