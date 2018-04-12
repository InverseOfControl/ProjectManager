var url;

$(function () {
	//借款类型
	$('#toolbar #productComb').combobox({
	    url:'apply/getLoanType',
	    valueField:'productType',
	    textField:'productTypeName',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length==1)
        		$(this).combobox('select', data[0].id);
        }
	});
	
	//城市
	$('#toolbar #cityComb').combobox({
        url:'audit/getAllCitys',
        valueField:'id',  
        textField:'name',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length==1)
        		$(this).combobox('select', data[0].id);
        }
	});
	// 列表
    $('#list_result').datagrid({
    	  onLoadSuccess:function(data){ 
    		  if(data.total==0)
    		  {
    		    $.messager.show({
                    title:'结果',
                    msg:'没有数据！',
                    showType:'slide'
                });
    		  }
    	  },
        url: 'product/management/list.json',
        fitColumns: true,
        border: false,
        fit:true,
        singleSelect:true,
        pagination: true,
        pageSize: 10,
        striped: true,
        rownumbers: true,	
        columns : [ [ 
		{
			field : 'productTypeName',
			title : '借款类型'
		},                    
        {
			field : 'productName',
			title : '产品名称',  
			formatter : linkDetail
		}/*,{
			field : 'term',
			title : '期数'
		}*/,
	    {
	    	field : 'consultingFeeRate',
	    	title : '咨询费费率'
	    },
	    {
	    	field : 'assessmentFeeRate',
	    	title : '评估费费率'
	    },
	    {
	    	field : 'manageFeeRate',
	    	title : '管理费费率'
	    },
	    {
	    	field : 'managePartRate',
	    	title : '丙方管理费费率'
	    },
	    {
	    	field : 'overdueInterestRate',
	    	title : '逾期罚息费率'
	    },
	    {
	    	field : 'riskRate',
	    	title : '风险金比例'
	    },
	    {
	    	field : 'rate',
	    	title : '平息利率'
	    },
	    {
	    	field : 'status',
	    	title : '状态',
	    	formatter: function(value, row, index){
	          	return  formatEnumName(value,'PRODUCT_STATUS');
           }
	    },		
	    {
	    	field : 'operation',
	    	title : '操作',  
			formatter : processLink
	    }		
		] ]
	});
    // 设置分页控件
    var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
	// 查询按钮
	//$('#searchBt').bind('click', search);
	// 导出按钮
//	$('#createBt').bind('click', create);
	
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	
  $.extend($.fn.validatebox.defaults.rules, {
	//金额校验
	moneyCheck:{
	    validator: function (value) {
	        return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
	     },
	     message:'请输入正确的金额'
	} ,
	  });
  
});

/** 新建产品页面**/
function productAdd() {
	var url = 'product/management/productAdd';
	$('#productAddDlg').dialog({
		title : '新增产品',
		closed : false,
		cache : false,
		href : url,
		modal : true,
	});
}

function linkDetail(value,row,index){
	if(row.productType==2){ //车贷
		return '<a style="font-weight:bolder" href="javascript:void(0)" onclick="browseCarDetail('+row.id+')">' + row.productName + '</a>';
	}else{
		return '<a style="font-weight:bolder" href="javascript:void(0)" onclick="browseSeDetail('+row.id+ ')">' + row.productName + '</a>';
	}
}

function processLink(value,row,index){
	var operations="";
	if(row.status==1){ //生效
		operations = '<a style="font-weight:bolder" href="javascript:void(0)" onclick="editProductDetail('+row.id+','+row.productType+')">编辑 </a>';
	}else if(row.status==0){//失效
		operations = '<a style="font-weight:bolder" href="javascript:void(0)" onclick="editProductDetail('+row.id+','+row.productType+')">编辑 </a>';
		operations += " " +'<a style="font-weight:bolder" href="javascript:void(0)" onclick="deleteProduct('+row.id+ ')"> 删除 </a>';
	}
	return operations;
}

/**删除**/
function deleteProduct(productId) {
	$.messager.confirm('确认对话框', '确定要删除吗？', function(r) {
		if (r) {
			doDeleteProduct(productId);
		} else {
			return false;
		}
	});
}
function doDeleteProduct(productId) {
	$.ajax({
		type : 'post',
		url : 'product/management/deleteProduct',
		data : {productId: productId},
		async : false,
		success : function(result) {
			if (result.success) {
				$.messager.show({
					title : '提示',
					msg : '提交成功！'
				});
			} else {
				$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}


/** 编辑产品页面**/
function editProductDetail(productId,productType) {
	if(productType == 2){ //车贷
		editCarProductDetail(productId);
	}else{ //小企业贷
		editSeProductDetail(productId);
	}
}



/** 查看车贷产品页面**/
function browseCarDetail(productId) {
    loanCarProductDetail(productId);
}
/** 查看小企业贷产品页面**/
function browseSeDetail(productId) {
	loanSeProductDetail(productId);
}

/**获取产品详细信息*/
function getProductDetails(productId){
    var response = $.ajax({
        type: "POST",
        url: "product/management/getProductDetails",
        dataType: "json",
        async:false,
        data: {productId:productId},
        error:function(){
            $.messager.show({
                title:'加载产品详细信息',
                msg:'加载产品信息详细失败！',
                showType:'slide'
            });
        }
    });
    return response.responseText;
}

function formatDate(value){
	if(value && value.length>=10){
		return value.substr(0, 10);
	}else{
		return "";
	}
}

function carProductClose(){
	$('#carProductViewDlg').dialog('close');
}
function seProductClose(){
	$('#seProductViewDlg').dialog('close');
}
 
