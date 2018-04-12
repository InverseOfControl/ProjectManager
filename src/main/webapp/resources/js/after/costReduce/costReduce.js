/**
 * 
 */
$(function() {
	// 获取所有客户经理
	$("#toolbar #crmList").combobox({
		url:'after/costReduce/getAllCrmList',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){    
	  		  var data = $(this).combobox('getData');    		
	  	       $(this).combobox('select',data[0].id);
		}
	});
	
	$("#costReducePageTb").datagrid({
		url : 'after/costReduce/getCostReducePage',
		fitColumns : true,
        border : false,
        singleSelect:true,
        pagination : true,
        striped: true,
        pageSize:10,
        fit:true,
        rownumbers : true,
        columns : [[
                    {field : 'name' ,title : '客户姓名' ,width : 50},
                    {field : 'productId' ,title : '产品类型' , formatter:showExtensionLoanLedgerRepay, width : 50},
                    {field : 'idNum' ,title : '身份证号' ,width : 100},
                    {field : 'frameNumber' ,title : '车架号' ,width : 110},
                    {field : 'pactMoney' ,title : '合同金额' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);//保留两位小数
                    	}else{
                    	return value;
                    	}
                    }, width : 50},
                    {field : 'currNum' ,title : '当前期限' , width : 50},
                    {field : 'time' ,title : '借款期限' , width : 50},
                    {field : 'remainingPrincipal' ,title : '剩余本金' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    	return value;
                    	}
                    }, width : 50},
                    {field : 'curRemainingInterestAmt' ,title : '剩余还款利息' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    	return value;
                    	}
                    }, width : 50},
                    {field : 'curRemainingAmount' ,title : '剩余本息' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    	return value;
                    	}
                    }, width : 50},
                    {field : 'penalty' ,title : '违约金' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);
                    	}else{
                    	return value;
                    	}
                    }, width : 50},
                    {field : 'status' ,title : '借款状态' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'LOAN_STATUS');
                    }, width : 100},
                    {field : 'amount' ,title : '减免金额' , formatter: function(value, row,index){
                    	if(value == 0) {
                    		return '<input type="text" name="amount" id="'+row.loanId+'" />';
                    	}else{
                    		return '<input type = "text" name="amount" id="'+row.loanId+'" value="' + value + '" disabled  />';
                    	}
                    }, width : 100},
                    {field : 'operations' ,title : '操作' ,formatter : formatOperationsCell, width : 100}
                    ]]
	});
	
	function formatOperationsCell(value, row, index){
		var operations = row.operations.split("|");
		var formattedOperations="";
		
		for(var i = 0; i < operations.length; i++) {
			var operation = operations[i];
			
			if(operation == "申请"){
				operation = '<a href="javascript:void(0)" onclick="checkValidAmount('+row.loanId+','+row.extensionTime+')">申请</a>';
			}
			else if(operation == "取消申请") {
				operation = '<a href="javascript:void(0)" onclick="cancelCostReduce('+row.loanId+')">取消申请</a>';
			}
			else if(operation == "不可操作") {
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
    var p = $('#costReducePageTb').datagrid('getPager');
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
	
	$('#uploadBtn').bind('click', openUploadFileDLG);
});

function search() {
	var queryParams = $('#costReducePageTb').datagrid('options').queryParams;
	queryParams.name = $('#toolbar #personName').val();
	queryParams.idnum = $('#toolbar #personIdnum').val();
	queryParams.mobilePhone = $('#toolbar #mobilePhone').val();
	queryParams.crmId = $('#toolbar #crmList').combobox('getValue');
	queryParams.frameNumber = $('#toolbar #frameNumber').val();
	 setFirstPage("#costReducePageTb");
	$('#costReducePageTb').datagrid('options').queryParams = queryParams;
	$("#costReducePageTb").datagrid('reload');
}

function checkValidAmount(loanId, extensionTime) {
	var amount = $('#' + loanId).val();
	$.ajax({
		type:"POST",
		url:"after/costReduce/checkValidAmount",
		data:{
			amount:amount
		},
		success: function(message){
			if(message.result == "error") {
				$.messager.alert("操作提示", message.message);
			}else if(message.result == "success") {
				submitcostReduce(loanId, extensionTime);
			}else if(message.result == "confirm") {
				$.messager.confirm("Confirm", "减免金额大于罚息，请确认！", function (r) {
					if(r) {
						submitcostReduce(loanId, extensionTime);
					}
				});
			}
		}
	});
}

// 申请费用减免
function submitcostReduce(loanId, extensionTime){
	var amount = $('#' + loanId).val();
	$.ajax({
		type:"POST",
		url:"after/costReduce/submitCostReduce",
		data:{
			loanId : loanId,
			amount : amount,
			extensionTime : extensionTime
		},
		success: function(message){
            if(message=="success"){
                $.messager.show({
                    title:'提示',
                    msg:'申请成功！',
                    showType:'slide'
                });
                $("#costReducePageTb").datagrid('reload');
        	} else {
        		 $.messager.show({
                        title:'提示',
                        msg:message,
                        showType:'slide'
                    });
        	}
        },
        error:function(){
            $.messager.show({
                title:'提交',
                msg:'申请失败！',
                showType:'slide'
            });
        }
	});
}

// 取消费用减免
function cancelCostReduce(loanId) {
	$.messager.confirm("提示", "您确定要取消吗？", function (r) {
		if(r){
			$.ajax({
				type:"POST",
				url:"after/costReduce/cancelCostReduce",
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
					$("#costReducePageTb").datagrid('reload');
					} else {
						$.messager.show({
							title:'提示',
							msg:message,
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

// 打开上传文件窗口
function openUploadFileDLG() {
	$("#fileName").val('');
	$('#fileUploadDialog').dialog('open').dialog('setTitle', '批量导入');
	$('#fileUploadForm').form('clear');
}

function ajaxFileUpload(saveBtn) {
	 $.ajaxFileUpload({
            url: 'after/costReduce/upload', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file', //文件上传域的ID    
            dataType: "json",
            async:false,
            success: function (data)  //服务器成功响应处理函数
            {
            	$.messager.show({
					title: 'warning',
					msg: data.responseText
				});
 	        },  
 	       error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText,
						timeout: 0
					});
		   }
      });
	 $('#fileUploadDialog').dialog('close');
	 $("#costReducePageTb").datagrid('reload');
}

