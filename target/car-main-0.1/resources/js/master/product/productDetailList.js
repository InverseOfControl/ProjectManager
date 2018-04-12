var url;
$(function () {
	// 查询按钮
	$('#searchBut').bind('click', search);
	
	//产品类型
    $('#toolbar #productId').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length>1)
                $(this).combobox('select', data[0].id);
            userProductType = data[0].id; 
        }
    }); 
    
  // 借款类型
   /* $('#toolbar #carProductType').combobox({
        url:'productDetailManager/getProductType',
        valueField:'productType',
        textField:'productTypeName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length>1)
                $(this).combobox('select', data[0].productType);
            userProductType = data[0].productType;
        }
    });*/
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
        url: 'productDetailManager/productDetailManagerList',
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
        	productId : $('#productId').val(),
        	carProductDetailType : $('#carProductDetailType').val(),
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
			field : 'productName',
			title : '产品名称',
			//formatter : linkDetail
		},                    
        {
			field : 'carProductType',
			title : '贷款产品类型' ,
			formatter: function(value, row, index){
				if(value == 0 || value =='' || value == null){
					return '';
				}else if(value == 1){
					return '流通类';
				}else if(value == 2){
					return '移交类';
				}
	        }
			
		},
	    {
	    	field : 'sumRate',
	    	title : '综合费率',
	    },
	    {
	    	field : 'term',
	    	title : '借款期限',
	    },
	    {
	    	field : 'lowerLimit',
	    	title : '借款金额下限',
	    },
	    {
	    	field : 'upperLimit',
	    	title : '借款金额上限',
	    },
	    {
	    	field : 'yearRate',
	    	title : '年利率',
	    },
	    {
	    	field : 'memberType',
	    	title : '会员类型',
	    	formatter: function(value, row, index){
				if( value =='' || value == null){
					return '';
				}else if(value == 1){
					return '免费会员';
				}else if(value == 2){
					return '付费会员（半年以下）';
				}else if(value == 3){
					return '付费会员（半年以上）';
				}
	        }
	    },
	    {
	    	field : 'riskRate',
	    	title : '风险金利率',
	    },
	    {
	    	field : 'monthRate',
	    	title : '月利率',
	    },
	    {
	    	field : 'thirdFeeRate',
	    	title : '第三方费率',
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
				var oper = '';
				oper += '<a href="javascript:void(0)" onclick="productDetailWindow(' + row.id + ');">详情</a>';
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="updateProductDetail(' + row.id + ');">修改</a>';
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

    $('#addProductDetailPanel').window('close');
	$('#updateProductDetailPanel').window('close');
	$('#detailProductDetailPanel').window('close');
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
	$('#addProductDetailPanel').window({
		modal:true
	});
	//产品名称下拉框
    $('#addProductId').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length>1)
                $(this).combobox('select', data[0].id);
            userProductType = data[0].id; 
        }
    });
}

//获取并修改参数信息 弹窗
function updateProductDetail(id) {
	if (!id) {return;}
	$.ajax({
		url : 'productDetailManager/getProductDetailManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#updateProductDetailPanel').window({
					modal:true
				});
				
				//产品名称下拉框
			    $('#updateProductId').combobox({
			        url:'apply/getProductType',
			        valueField:'id',
			        textField:'productName',
			        onLoadSuccess:function(){
			           	$(this).combobox('select', result.productDetailManager.productId);
			        }
			    });
				$('#updateProductDetailPanel').form('clear');
				$('#updateProductDetailPanel').form('load', result.productDetailManager);
				$('#updateProductDetailForm').find('input[name="id"]').val(result.productDetailManager.id);
				$('#updateProductDetailForm').find('input[name="productId]').val(result.productDetailManager.productName);
				$('#updateProductDetailForm').find('select[name="carProductType"]').val(result.productDetailManager.carProductType);
				$('#updateProductDetailForm').find('input[name="sumRate"]').val(result.productDetailManager.sumRate);
				$('#updateProductDetailForm').find('input[name="term"]').val(result.productDetailManager.term);
				$('#updateProductDetailForm').find('input[name="lowerLimit"]').val(result.productDetailManager.lowerLimit);
				$('#updateProductDetailForm').find('input[name="upperLimit"]').val(result.productDetailManager.upperLimit);
				$('#updateProductDetailForm').find('input[name="status"]').val(result.productDetailManager.status);
				$('#updateProductDetailForm').find('input[name="remark"]').val(result.productDetailManager.remark);
				$('#updateProductDetailForm').find('input[name="version"]').val(result.productDetailManager.version);
				$('#updateProductDetailForm').find('input[name="yearRate"]').val(result.productDetailManager.yearRate);
				$('#updateProductDetailForm').find('select[name="memberType"]').val(result.productDetailManager.memberType);
				$('#updateProductDetailForm').find('input[name="riskRate"]').val(result.productDetailManager.riskRate);
				$('#updateProductDetailForm').find('input[name="monthRate"]').val(result.productDetailManager.monthRate);
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
function submitUpdateproduct() {
	var formObj = $('#updateProductDetailForm');
	var spmData = $('#updateProductDetailForm').serialize();
	
	if($('#updateProductId').combobox('getValue')==''){
		alert('请选择产品名称');
		return false;
	}
	if (formObj.form("validate")) {
		$.post('productDetailManager/updateProductDetailManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '修改失败')) {
				$('#updateProductDetailPanel').window('close');
			}
		});
	}
}



//查询
function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.productId = $('#toolbar #productId').combobox('getValue');
	queryParams.carProductType = $('#toolbar #carProductType').combobox('getValue');
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};



//参数详细信息 弹窗
function productDetailWindow (id) {
	if (!id) {return;}
	$.ajax({
		url : 'productDetailManager/getProductDetailManager',
		data : {
			id : id						
		},
		type:'POST',
		success : function(result){
			if (result.success) {
				$('#detailProductDetailPanel').window({
					modal:true
				});
				$('#detailProductDetailPanel').form('clear');
				$('#detailProductDetailPanel').form('load', result.productDetailManager);
				$('#detailProductDetailForm').find('input[name="id"]').val(result.productDetailManager.id);
				$('#detailProductDetailForm').find('input[name="productName"]').val(result.productDetailManager.productName);
				$('#detailProductDetailForm').find('select[name="carProductType"]').val(result.productDetailManager.carProductType);
				$('#detailProductDetailForm').find('input[name="sumRate"]').val(result.productDetailManager.sumRate);
				$('#detailProductDetailForm').find('input[name="term"]').val(result.productDetailManager.term);
				$('#detailProductDetailForm').find('input[name="lowerLimit"]').val(result.productDetailManager.lowerLimit);
				$('#detailProductDetailForm').find('input[name="upperLimit"]').val(result.productDetailManager.upperLimit);
				$('#detailProductDetailForm').find('input[name="status"]').val(result.productDetailManager.status);
				$('#detailProductDetailForm').find('input[name="remark"]').val(result.productDetailManager.remark);
				$('#detailProductDetailForm').find('input[name="version"]').val(result.productDetailManager.version);
				$('#detailProductDetailForm').find('input[name="yearRate"]').val(result.productDetailManager.yearRate);
				$('#detailProductDetailForm').find('select[name="memberType"]').val(result.productDetailManager.memberType);
				$('#detailProductDetailForm').find('input[name="riskRate"]').val(result.productDetailManager.riskRate);
				$('#detailProductDetailForm').find('input[name="monthRate"]').val(result.productDetailManager.monthRate);

			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}


//新增参数 保存
function submitAddproductDetail() {
	var formObj = $('#addProductDetailForm');
	var spmData = $('#addProductDetailForm').serialize();
	
	if($('#addProductId').combobox('getValue')==''){
		alert('请选择产品名称');
		return false;
	}
	if (formObj.form("validate")) {
		$.post('productDetailManager/addProductDetailManager', spmData, function(data) {
			var isErr = '';
			if (data.success) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#addProductDetailPanel').window('close');
			}
		});
	}
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
