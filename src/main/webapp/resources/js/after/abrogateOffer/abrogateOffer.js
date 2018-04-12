/**
 * 
 */
$(function() {
	// 获取所有客户经理
	$("#toolbar #crmList").combobox({
		url:'after/abrogateOffer/getAllCrmList',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){    
	  		  var data = $(this).combobox('getData');    		
	  	       $(this).combobox('select',data[0].id);
		}
	});
	
	$("#abrogateOfferPageTb").datagrid({
		url : 'after/abrogateOffer/getAbrogateOfferPage',
		fitColumns : true,
        border : false,
        singleSelect:true,
        pagination : true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        fit:true,
        columns : [[
                    {field : 'name' ,title : '姓名' ,width : 50},
                    {field : 'idnum' ,title : '身份证号' ,width : 80},
                    {field : 'productId' ,title : '产品类型' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_ID');
                    }, width : 50},
                    {field : 'pactMoney' ,title : '合同金额' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);//保留两位小数
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'auditTime',
            			title : '审批期限', width : 30},
                    {
            			field : 'extensionTime',
            			title : '展期期次',
            			formatter : function(value, row, index) {
            				if(value == 0) {
            					return "无";
            				} else {
            					return value;
            				}
            			},
            			width : 60
            		}, 
                    {field : 'loanStatus' ,title : '借款状态' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'LOAN_STATUS');
                    }, width : 50},
                    {field : 'operations' ,title : '操作' ,formatter : formatOperationsCell, width : 100}
                   ]]
	});
	
	// 设置分页控件
    var p = $('#abrogateOfferPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
	
	
	function formatOperationsCell(value, row, index){
		var operation = '<a href="javascript:void(0)" onclick="cancelAbrogateOffer('+row.id+')">取消申请</a>';
		return operation;
	}
	

	// 点击【查询】按钮
	$('#searchBtn').bind('click', search);
	// 取消报盘  界面生成
	$("#generateBtn").bind('click', windowOpen);
	
	$(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
	
});

function windowOpen(){
	 window.open(rayUseUrl+"after/abrogateOffer/abrogateOfferGene" 
			 ,"newwindow2","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");

}

function search() {
	var queryParams = $('#abrogateOfferPageTb').datagrid('options').queryParams;
	queryParams.name = $('#toolbar #personName').val();
	queryParams.idnum = $('#toolbar #personIdnum').val();
	queryParams.mobilePhone = $('#toolbar #mobilePhone').val();
	 setFirstPage("#abrogateOfferPageTb");
	$('#abrogateOfferPageTb').datagrid('options').queryParams = queryParams;
	$("#abrogateOfferPageTb").datagrid('reload');
}


// 取消费用减免
function cancelAbrogateOffer(id) {
	$.messager.confirm("提示", "您确定要取消吗？", function (r) {
		if(r){
			$.ajax({
				type:"POST",
				url:"after/abrogateOffer/cancelAbrogateOffer",
				data:{
					id : id
				},
				success: function(message){
					if(message.result=="success"){
						$.messager.show({
							title:'提示',
							msg:'取消成功！',
							showType:'slide'
					});
					$("#abrogateOfferPageTb").datagrid('reload');
					} else {
						$.messager.show({
							title:'提示',
							msg:message.message,
							showType:'slide'
                    });
					}
				},
				error:function(){
					$.messager.show({
						title:'提交',
						msg:'取消失败！',
						showType:'slide'
					});
				}
			});
        }
	});
}

