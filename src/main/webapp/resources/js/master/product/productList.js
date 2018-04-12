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
        url: 'productManager/productManagerList',
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
	    	productCode : $('#productCode').val(),
	    	productName : $('#productName').val(),
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
			field : 'productCode',
			title : '产品代码',
			//formatter : linkDetail
		},                    
        {
			field : 'productName',
			title : '产品名称' ,
			
		},
	    {
	    	field : 'consultingFeeRate',
	    	title : '咨询费费率',
	    },
	    {
	    	field : 'managePartRate',
	    	title : '丙方管理费费率',
	    },
	    {
	    	field : 'manageFeeRate',
	    	title : '管理费费率',
	    },
	    {
	    	field : 'overdueInterestRate',
	    	title : '逾期罚息费率',
	    },
	    {
	    	field : 'riskRate',
	    	title : '风险金比例',
	    },
	    {
	    	field : 'assessmentFeeRate',
	    	title : '评估费费率',
	    },
	    {
	    	field : 'rate',
	    	title : '平息利率',
	    },
	    {
	    	field : 'thirdFeeRate',
	    	title : '同城费费率',
	    },
	    {
	    	field : 'monthRate',
	    	title : '月利率',
	    },
	    {
	    	field : 'penaltyRate',
	    	title : '提前还款违约金费率',
	    },
	    {
	    	field : 'yearRate',
	    	title : '年利率',
	    },
	    {
	    	field : 'productType',
	    	title : '产品类型',
	    },
	    {
	    	field : 'productTypeName',
	    	title : '产品类型名称',
	    },
	    {
	    	field : 'productChannelId',
	    	title : '渠道ID',
	    },
	    {
	    	field : 'productChannelName',
	    	title : '渠道名称',
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="productEditWindow(' + row.id + ');">修改</a>' ;
				oper += ' | <a href="javascript:void(0)" onclick="productDetailWindow(' + row.id + ');">详情</a>';
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

    $('#addProductPanel').window('close');
	$('#updateProductPanel').window('close');
	$('#detailProductPanel').window('close');
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
	$('#addProductPanel').window({
		modal:true
	});
}



//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.productCode = $('#toolbar #productCode').val();
	queryParams.productName = $('#toolbar #productName').val();
	
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};



//获取并修改参数信息 弹窗
function productEditWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'productManager/getProductManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#updateProductPanel').window({
					modal:true
				});
				$('#updateProductPanel').form('clear');
				$('#updateProductPanel').form('load', result.productManager);
				$('#updateProductForm').find('input[name="id"]').val(result.productManager.id);
				$('#updateProductForm').find('input[name="productCode"]').val(result.productManager.productCode);
				$('#updateProductForm').find('input[name="productName"]').val(result.productManager.productName);
				$('#updateProductForm').find('input[name="consultingFeeRate"]').val(result.productManager.consultingFeeRate);
				$('#updateProductForm').find('input[name="managePartRate"]').val(result.productManager.managePartRate);
				$('#updateProductForm').find('input[name="manageFeeRate"]').val(result.productManager.manageFeeRate);
				$('#updateProductForm').find('input[name="overdueInterestRate"]').val(result.productManager.overdueInterestRate);
				$('#updateProductForm').find('input[name="riskRate"]').val(result.productManager.riskRate);
				$('#updateProductForm').find('input[name="assessmentFeeRate"]').val(result.productManager.assessmentFeeRate);
				$('#updateProductForm').find('input[name="rate"]').val(result.productManager.rate);
				$('#updateProductForm').find('input[name="status"]').val(result.productManager.status);
				$('#updateProductForm').find('input[name="remark"]').val(result.productManager.remark);
				$('#updateProductForm').find('input[name="version"]').val(result.productManager.version);
				$('#updateProductForm').find('input[name="productType"]').val(result.productManager.productType);
				$('#updateProductForm').find('input[name="productTypeName"]').val(result.productManager.productTypeName);
				$('#updateProductForm').find('input[name="thirdFeeRate"]').val(result.productManager.thirdFeeRate);
				$('#updateProductForm').find('input[name="monthRate"]').val(result.productManager.monthRate);
				$('#updateProductForm').find('input[name="penaltyRate"]').val(result.productManager.penaltyRate);
				$('#updateProductForm').find('input[name="productChannelId"]').val(result.productManager.productChannelId);
				$('#updateProductForm').find('input[name="productChannelName"]').val(result.productManager.productChannelName);
				$('#updateProductForm').find('input[name="yearRate"]').val(result.productManager.yearRate);
				//$('#updateProductForm').find('input[name="isDisabled"] option[value="'+result.ProductManager.isDisabled+'"]').attr("selected",true);

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
function submitUpdateproductManager() {
	var formObj = $('#updateProductForm');
	var id = $('#updateProductForm #id').val();
	var productCode = $('#updateProductForm #productCode').val();
	var productName = $('#updateProductForm #productName').val();
	var consultingFeeRate = $('#updateProductForm #consultingFeeRate').val();
	var managePartRate  = $('#updateProductForm #managePartRate').val();
	var manageFeeRate = $('#updateProductForm #manageFeeRate').val();
	var overdueInterestRate = $('#updateProductForm #overdueInterestRate').val();
	var riskRate = $('#updateProductForm #riskRate').val();
	var assessmentFeeRate = $('#updateProductForm #assessmentFeeRate').val();
	var rate = $('#updateProductForm #rate').val();
	var status = $('#updateProductForm #status').val();
	var remark = $('#updateProductForm #remark').val();
	var version = $('#updateProductForm #version').val();
	var productType = $('#updateProductForm #productType').val();
	var productTypeName = $('#updateProductForm #productTypeName').val();
	var thirdFeeRate = $('#updateProductForm #thirdFeeRate').val();
	var monthRate = $('#updateProductForm #monthRate').val();
	var penaltyRate = $('#updateProductForm #penaltyRate').val();
	var productChannelId = $('#updateProductForm #productChannelId').val();
	var productChannelName = $('#updateProductForm #productChannelName').val();
	var yearRate = $('#updateProductForm #yearRate').val();
	//var isDisabled = $('#updateProductForm #isDisabled').combobox('getValue');
	var spmData = {id:id,productCode:productCode,productName:productName,consultingFeeRate:consultingFeeRate,managePartRate:managePartRate,
			manageFeeRate:manageFeeRate,overdueInterestRate:overdueInterestRate,riskRate:riskRate,assessmentFeeRate:assessmentFeeRate,rate:rate,status:status,
			remark:remark,version:version,productType:productType,productTypeName:productTypeName,thirdFeeRate:thirdFeeRate,
			monthRate:monthRate,penaltyRate:penaltyRate,productChannelId:productChannelId,productChannelName:productChannelName,yearRate:yearRate};
	if (formObj.form("validate")) {
		$.post('productManager/updateProductManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateProductPanel').window('close');
			}
		})
	}
}

//新增参数 保存
function submitAddproduct() {
	var formObj = $('#addProductForm');
	var productCode = $('#addProductForm #productCode').val();
	var productName = $('#addProductForm #productName').val();
	var consultingFeeRate = $('#addProductForm #consultingFeeRate').val();
	var managePartRate  = $('#addProductForm #managePartRate').val();
	var manageFeeRate = $('#addProductForm #manageFeeRate').val();
	var overdueInterestRate = $('#addProductForm #overdueInterestRate').val();
	var riskRate = $('#addProductForm #riskRate').val();
	var assessmentFeeRate = $('#addProductForm #assessmentFeeRate').val();
	var rate = $('#addProductForm #rate').val();
	var status = $('#addProductForm #status').val();
	var remark = $('#addProductForm #remark').val();
	var version = $('#addProductForm #version').val();
	var productType = $('#addProductForm #productType').val();
	var productTypeName = $('#addProductForm #productTypeName').val();
	var thirdFeeRate = $('#addProductForm #thirdFeeRate').val();
	var monthRate = $('#addProductForm #monthRate').val();
	var penaltyRate = $('#addProductForm #penaltyRate').val();
	var productChannelId = $('#addProductForm #productChannelId').val();
	var productChannelName = $('#addProductForm #productChannelName').val();
	var yearRate = $('#addProductForm #yearRate').val();
	var spmData = {productCode:productCode,productName:productName,consultingFeeRate:consultingFeeRate,managePartRate:managePartRate,manageFeeRate:manageFeeRate,
			overdueInterestRate:overdueInterestRate,riskRate:riskRate,assessmentFeeRate:assessmentFeeRate,rate:rate,status:status,
			remark:remark,version:version,productType:productType,productTypeName:productTypeName,thirdFeeRate:thirdFeeRate,
			monthRate:monthRate,penaltyRate:penaltyRate,productChannelId:productChannelId,productChannelName:productChannelName,yearRate:yearRate};
	if (formObj.form("validate")) {
		$.post('productManager/addProductManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addProductPanel').window('close');
			}
		})
	}
}

//参数详细信息 弹窗
function productDetailWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'productManager/getProductManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#detailProductPanel').window({
					modal:true
				});
				$('#detailProductPanel').form('clear');
				$('#detailProductPanel').form('load', result.productManager);
				$('#detailProductForm').find('input[name="id"]').val(result.ProductManager.id);
				$('#detailProductForm').find('input[name="productCode"]').val(result.ProductManager.productCode);
				$('#detailProductForm').find('input[name="productName"]').val(result.ProductManager.productName);
				$('#detailProductForm').find('input[name="consultingFeeRate"]').val(result.ProductManager.consultingFeeRate);
				$('#detailProductForm').find('input[name="managePartRate"]').val(result.ProductManager.managePartRate);
				$('#detailProductForm').find('input[name="manageFeeRate"]').val(result.ProductManager.manageFeeRate);
				$('#detailProductForm').find('input[name="overdueInterestRate"]').val(result.ProductManager.overdueInterestRate);
				$('#detailProductForm').find('input[name="riskRate"]').val(result.ProductManager.riskRate);
				$('#detailProductForm').find('input[name="assessmentFeeRate"]').val(result.ProductManager.assessmentFeeRate);
				$('#detailProductForm').find('input[name="rate"]').val(result.ProductManager.rate);
				$('#detailProductForm').find('input[name="status"]').val(result.ProductManager.status);
				$('#detailProductForm').find('input[name="remark"]').val(result.ProductManager.remark);
				$('#detailProductForm').find('input[name="version"]').val(result.ProductManager.version);
				$('#detailProductForm').find('input[name="productType"]').val(result.ProductManager.productType);
				$('#detailProductForm').find('input[name="productTypeName"]').val(result.ProductManager.productTypeName);
				$('#detailProductForm').find('input[name="thirdFeeRate"]').val(result.ProductManager.thirdFeeRate);
				$('#detailProductForm').find('input[name="monthRate"]').val(result.ProductManager.monthRate);
				$('#detailProductForm').find('input[name="penaltyRate"]').val(result.ProductManager.penaltyRate);
				$('#detailProductForm').find('input[name="productChannelId"]').val(result.ProductManager.productChannelId);
				$('#detailProductForm').find('input[name="productChannelName"]').val(result.ProductManager.productChannelName);
				$('#detailProductForm').find('input[name="yearRate"]').val(result.ProductManager.yearRate);

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
