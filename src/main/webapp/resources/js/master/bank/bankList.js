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
        url: 'bankManager/getBankList',
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
	    	bankName : $('#bankName').val(),
	    	bankCode : $('#bankCode').val(),
	    	tppType  : $('#tppType').combobox('getValue'),
	    	bankType : $('#bankType').combobox('getValue'),
		},
        columns : [ [ 
         {
        	 field : 'id',
        	 title : 'ID',
        	 //formatter : linkDetail
         },                    
		{
			field : 'bankName',
			title : '银行名称',
			//formatter : linkDetail
		},                    
        {
			field : 'bankCode',
			title : '银行代码' ,
			
		},
	    {
	    	field : 'tppBankCode',
	    	title : 'Tpp银行代码',
	    },
	    {
	    	field : 'tppType',
	    	title : 'Tpp类型',
	    	formatter: function(value, row, index){
				if(value == 10){
					return '通联划扣';
				}else if(value == 20){
					return '富有划扣';
				}else if(value == 30){
					return '银联划扣';
				}else{
					return '';
				}
	        }
	    },
	    {
	    	field : 'bankType',
	    	title : '银行类型',
	    	formatter: function(value, row, index){
				if(value == 1){
					return '国外银行';
				}else if(value == 0){
					return '国内银行';
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
				oper = '<a href="javascript:void(0)" onclick="bankEditWindow(' + row.id + ');">修改</a>';
				oper += ' | <a href="javascript:void(0)" onclick="bankDeleteWindow(' + row.id + ');">删除</a>';
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

    $('#addBankPanel').window('close');
	$('#updateBankPanel').window('close');
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
	$('#addBankPanel').window({
		modal:true
	});
}

//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.bankName = $('#toolbar #bankName').val();
	queryParams.bankCode = $('#toolbar #bankCode').val();
	queryParams.tppType  = $('#toolbar #tppType').combobox('getValue');
	
	queryParams.bankType = $('#toolbar #bankType').combobox('getValue');
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

//获取并修改银行信息 弹窗
function bankEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'bankManager/getBank',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#updateBankPanel').window({
					modal:true
				});
				$('#updateBankPanel').form('clear');
				$('#updateBankPanel').form('load', result.bank);
				$('#updateBankForm').find('input[name="id"]').val(result.bank.id);
				$('#updateBankForm').find('input[name="bankName"]').val(result.bank.bankName);
				$('#updateBankForm').find('input[name="bankCode"]').val(result.bank.bankCode);
				$('#updateBankForm').find('input[name="tppBankCode"]').val(result.bank.tppBankCode);
				//var bk = formatBankType(result.bank.bankType);
				$('#updateBankForm').find('input[name="tppType"] option[value="'+result.bank.tppType+'"]').attr("selected",true);
				$('#updateBankForm').find('input[name="bankType"] option[value="'+result.bank.bankType+'"]').attr("selected",true);
				//$('#updateBankForm #selectBankType').html(formatBankType(result.bank.bankType)); 
				
				//$('#updateBankForm #bankType').combobox(result.bank.bankType); 

			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//删除银行信息
function bankDeleteWindow(id){
	if (!id) {return;}
	var paramData = {id:id};
	$.post('bankManager/deleteBank', paramData, function(data) {
		var isErr = '';
		if (data.isSuccess) {
		} else {
			isErr = 'error';
		}
		$.messager.alert('操作提示', data.msg, isErr);
		$('#searchBut').trigger('click');
		
	})
	
}

function formatBankType(bankType){
   var selectstr = bankType ;
	   if(bankType == 0)
		   selectstr = 10;
	return selectstr;
}

//修改银行 保存
function submitUpdateBank() {
	var formObj = $('#updateBankForm');
	var id = $('#updateBankForm #id').val();
	var bankName = $('#updateBankForm #bankName').val();
	var bankCode = $('#updateBankForm #bankCode').val();
	var tppBankCode = $('#updateBankForm #tppBankCode').val();
	var tppType  = $('#updateBankForm #tppType').combobox('getValue');
	var bankType = $('#updateBankForm #bankType').combobox('getValue');
	var bankData = {id:id,bankName:bankName,bankCode:bankCode,tppBankCode:tppBankCode,tppType:tppType,bankType:bankType};
	if (formObj.form("validate")) {
		$.post('bankManager/updateBank', bankData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateBankPanel').window('close');
			}
			
		})
	}
}

//新增银行 保存
function submitAddBank() {
	var formObj = $('#addBankForm');
	var bankName = $('#addBankForm #bankName').val();
	var bankCode = $('#addBankForm #bankCode').val();
	var tppBankCode = $('#addBankForm #tppBankCode').val();
	var tppType  = $('#addBankForm #tppType').combobox('getValue');
	var bankType = $('#addBankForm #bankType').combobox('getValue');
	var sysParameterData = {bankName:bankName,bankCode:bankCode,tppBankCode:tppBankCode,tppType:tppType,bankType:bankType};
	if (formObj.form("validate")) {
		$.post('bankManager/addBank', sysParameterData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addBankPanel').window('close');
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