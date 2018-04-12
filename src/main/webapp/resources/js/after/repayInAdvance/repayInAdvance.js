/**
 * 
 */

$(function (){
	// 获取该营业店所有客户经理列表
	$("#toolbar #crmList").combobox({
		url:'after/repayInAdvance/getCrmList',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){    
	  		  var data = $(this).combobox('getData');    		
	  	       $(this).combobox('select',data[0].id);
		}
	});
	//产品类型
	$('#toolbar #productComb').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
        }
    });
	
	$("#repayInAdvancePageTb").datagrid({
		url : 'after/repayInAdvance/getRepayInAdvancePage',
		fitColumns : true,
        border : false,
        singleSelect:true,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        columns : [[
                    {field : 'name' ,title : '姓名' ,width : 50},
                    {field : 'idNum' ,title : '身份证号' ,width : 80},
                    {field : 'productId' ,title : '产品类型' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_ID');
                    }, width : 50},
                    {field : 'productSubType' ,title : '借款子类型' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
                    }, width : 50},
                    {field : 'pactMoney' ,title : '合同金额' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);//保留两位小数
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'currRepayAmount' ,title : '当期应还' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'overdueAllAmount' ,title : '逾期应还' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'fine' ,title : '逾期违约金' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'oneTimeRepayment' ,title : '一次性还款金额' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'accAmount' ,title : '期末预收' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    		return value;
                    	}
                    }, width : 60},
                    {field : 'time' ,title : '期数' , width : 30},
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
                    {field : 'status' ,title : '借款状态' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'LOAN_STATUS');
                    }, width : 50},
                    {field : 'operations' ,title : '操作' ,formatter : formatOperationsCell, width : 100}
                   ]]
	});
	
	
	function formatOperationsCell(value, row, index){
		var operations = row.operations.split("|");
		var formattedOperations="";
		
		for(var i = 0; i < operations.length; i++) {
			var operation = operations[i];
			
			if(operation == "提前扣款取消"){
				operation = '<a href="javascript:void(0)" onclick="cancelRepayInAdvance('+row.loanId+')">提前扣款取消</a>';
			}
			else if(operation == "一次性结清取消") {
				operation = '<a href="javascript:void(0)" onclick="cancelRepayOneTime('+row.loanId+')">一次性结清取消</a>';
			}
			else if(operation == "提前扣款") {
				operation = '<a href="javascript:void(0)" onclick="repayInAdvance('+row.loanId+')">提前扣款</a>';
			}
			else if(operation == "一次性结清") {
				operation = '<a href="javascript:void(0)" onclick="repayOneTime('+row.loanId + ')">一次性结清</a>';
			} else if(operation == "不可操作"){
				operation = '&nbsp;';
			}
			
			if(formattedOperations =="") {
				formattedOperations = operation;
			} else {
				formattedOperations = formattedOperations + "&nbsp;&nbsp;&nbsp;&nbsp;" + operation;
			}
		}
		return formattedOperations;
	}
	
	 // 设置分页控件
    var p = $('#repayInAdvancePageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
	
	// 点击【查询】按钮
	$('#searchBtn').bind('click', search);
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
});


function search(){
	var queryParams = $('#repayInAdvancePageTb').datagrid('options').queryParams;
	// 姓名模糊查询
	queryParams.personFuzzyName = $('#toolbar #personName').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	queryParams.personMobilePhone = $('#toolbar #mobilePhone').val();
	queryParams.crmId = $('#toolbar #crmList').combobox('getValue');
	 setFirstPage("#repayInAdvancePageTb");
	$('#repayInAdvancePageTb').datagrid('options').queryParams = queryParams;
	$("#repayInAdvancePageTb").datagrid('reload');
}

// 提前扣款取消
function cancelRepayInAdvance(loanId){
	$.messager.confirm("Confirm", "您确定要取消吗？", function (r) {
		if(r) {
			$.ajax({
				type:"POST",
				url:"after/repayInAdvance/cancelRepayInAdvance",
				dataType:"json",
				data:{
					loanId : loanId
				},
				success: function(message){
	                if(message=="success"){
	                    $.messager.show({
	                        title:'提示',
	                        msg:'取消成功！',
	                        showType:'slide'
	                    });
	                    $("#repayInAdvancePageTb").datagrid('reload');
                	} else {
                		 $.messager.show({
 	                        title:'提示',
 	                        msg:message,
 	                        showType:'slide'
 	                    });
                		 $("#repayInAdvancePageTb").datagrid('reload');
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

// 一次性还款取消
function cancelRepayOneTime(loanId){
	$.messager.confirm("Confirm", "您确定要取消吗？", function (r) {
		if(r) {
			$.ajax({
				type:"POST",
				url:"after/repayInAdvance/cancelRepayOneTime",
				dataType:"json",
				data:{
					loanId : loanId
				},
				success: function(message){
	                if(message=="success"){
	                    $.messager.show({
	                        title:'提示',
	                        msg:'取消成功！',
	                        showType:'slide'
	                    });
	                    $("#repayInAdvancePageTb").datagrid('reload');
                	} else {
                		 $.messager.show({
 	                        title:'提示',
 	                        msg:message,
 	                        showType:'slide'
 	                    });
                		 $("#repayInAdvancePageTb").datagrid('reload');
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

// 提前扣款
function repayInAdvance(loanId){
	// 判断loanId是否有展期
	$.ajax({
		type:"POST",
		url:"after/repayInAdvance/hasExtension",
		dataType:"json",
		data:{
			loanId : loanId
		},
		success: function (message) {
			var waring = "您确定要提交吗？";
			if(message == true) {
				waring = "该笔借款有展期，如果申请，将会取消其展期，您确定要提交吗？";
			}
			$.messager.confirm("Confirm", waring, function (r) {
				if(r) {
					$.ajax({
						type:"POST",
						url:"after/repayInAdvance/submitRepayInAdvance",
						dataType:"json",
						data:{
							loanId : loanId
						},
						success: function(message){
			                if(message=="success"){
			                    $.messager.show({
			                        title:'提示',
			                        msg:'提交成功！',
			                        showType:'slide'
			                    });
			                    $("#repayInAdvancePageTb").datagrid('reload');
		                	} else {
		                		 $.messager.show({
		 	                        title:'提示',
		 	                        msg:message,
		 	                        showType:'slide'
		 	                    });
		                		 $("#repayInAdvancePageTb").datagrid('reload');
		                	}
		                },
		                error:function(){
		                    $.messager.show({
		                        title:'提交',
		                        msg:'提交失败！',
		                        showType:'slide'
		                    });
		                }
					});
				}
			});
		}
	});
}

// 一次性还款
function repayOneTime(loanId){
	// 判断loanId是否有展期
	$.ajax({
		type:"POST",
		url:"after/repayInAdvance/hasExtension",
		dataType:"json",
		data:{
			loanId : loanId
		},
		success: function (message) {
			var waring = "您确定要提交吗？";
			if(message == true) {
				waring = "该笔借款有展期，如果申请，将会取消其展期，您确定要提交吗？";
			}
			$.messager.confirm("Confirm", waring, function (r) {
				if(r) {
					$.ajax({
						type:"POST",
						url:"after/repayInAdvance/submitRepayOneTime",
						dataType:"json",
						data:{
							loanId : loanId
						},
						success: function(message){
							if(message=="success"){
								$.messager.show({
									title:'提示',
									msg:'提交成功！',
									showType:'slide'
								});
								$("#repayInAdvancePageTb").datagrid('reload');
							} else {
								$.messager.show({
									title:'提示',
									msg:message,
									showType:'slide'
								});
								$("#repayInAdvancePageTb").datagrid('reload');
							}
						},
						error:function(){
							$.messager.show({
								title:'提交',
								msg:'提交失败！',
								showType:'slide'
							});
						}
					});
				}
			});
	}
	});
}