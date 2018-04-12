var url;
$(function () {
	//设置默认时间--15天前
    $('#toolbar #buildDateStart').val(buildDateStart);
    
	$('#syncLoanTb').datagrid({
		 url:'externalLoan/getSyncLoanData',
	     border : false,
	     singleSelect:false,
	     pagination : true,
	     striped: true,
	     pageSize:10,
	     fit:true,
	     rownumbers : true,
	     checkOnSelect:true,     
	     columns : [[
	              {
	    			field : 'loanId',
	    			title : '借款编号',
	    			width : 80
	    		}, {
	    			field : 'companyNo',
	    			title : '第三方机构编号',
	    			width : 100
	    		}, {
	    			field : 'companyName',
	    			title : '第三方机构名称',
	    			width : 100
	    		}, {
	    			field : 'contractNo',
	    			title : '债权编号',
	    			width : 150
	    		}, {
	    			field : 'name',
	    			title : '客户姓名',
	    			width : 80
	    		}, {
	    			field : 'idNum',
	    			title : '身份证号',
	    			width : 120
	    		}, {
	    			field : 'professionType',
	    			title : '职业类型 ',
	    			width : 80
	    		}, {
	    			field : 'loanType',
	    			title : '产品类型',
	    			width : 80,
	    		}, {
	    			field : 'pactMoney',
	    			title : '借款金额/合同金额',
	    			width : 120,
	    			formatter : function(value, row, index) {
	    				if (value)
	    				return value.toFixed(2);
	    			}
	    		},  {
	    			field : 'pactTime',
	    			title : '借款期限',
	    			width : 80
	    		}, {
	    			field : 'endRepayDate',
	    			title : '债权到期日/还款到期日',
	    			width : 160,
	    			formatter : function(value, row, index) {
	    				return value.substring(0,10);
	    			}
	    		}, {
	    			field : 'signDate',
	    			title : '放款日期',
	    			width : 100,
	    			formatter : function(value, row, index) {
	    				return  value.substring(0,10);
	    			}
	    		}, {
	    			field : 'purpose',
	    			title : '用途',
	    			width : 160
	    		}, {
	    			field : 'maxRepayAmount',
	    			title : '月还款能力',
	    			width : 100,
	    			formatter : function(value, row, index) {
	    				if (value)
	    				return value.toFixed(2);
	    			}
	    		},{
	    			field : 'status',
	    			title : '同步状态',formatter: function(value, row, index){
			          	return  formatEnumName(value,'SYNC_STATUS');
			        },
	    			width : 80
	    		}, {
	    			field : 'msg',
	    			title : '异常信息',
	    			width : 160
	    		}, {
	    			field : 'buildDate',
	    			title : '生成时间',
	    			width : 120
	    		}, {
	    			field : 'sendDate',
	    			title : '请求时间',
	    			width : 120
	    		}, {
	    			field : 'returnDate',
	    			title : '反馈时间',
	    			width : 120
	    		}
	            ]],
	});
	
	//设置分页控件
	var p = $('#syncLoanTb').datagrid('getPager');
	$(p).pagination({
		pageList: [ 10, 20, 50 ]
	});
	
	//产品类型
	$('#toolbar #loanType').combobox({
		url:'apply/getProductType',
		valueField:'id',
		textField:'productName',
		onLoadSuccess:function(){
			var data = $(this).combobox('getData');
			if(data.length>1)
				$(this).combobox('select', data[0].productType);
			userProductType = data[0].productType;
		}
	});
	
	// 点击【查询】按钮
	$('#searchBt').bind('click', search);
	
	 // 导出按钮
	$("#exportExcelBt").bind('click',exportExcel);
	
	
});


//导出excel
function exportExcel(){
	var params='';
	var name   = $('#toolbar #name').val();
	var idNum = $('#toolbar #idNum').val();
	var contractNo = $('#toolbar #contractNo').val();
	var loanType    = $('#toolbar #loanType').combobox('getValue');
	var buildDateStart    = $('#toolbar #buildDateStart').val();
	var buildDateEnd    = $('#toolbar #buildDateEnd').val();
	var sendDateStart    = $('#toolbar #sendDateStart').val();
	var sendDateEnd    = $('#toolbar #sendDateEnd').val();
	var status    = $('#toolbar #status').combobox('getValue');
	var url = rayUseUrl+'externalLoan/checkExportExcel?name=';
	var url2 = rayUseUrl+'externalLoan/exportExcel?name=';
	
	if(name != ''){
		params += encodeURI(encodeURI(name)); //转码获取中文
	}
	if(idNum != ''){
		params += '&idNum=' + idNum;
	}
	if(contractNo != ''){
		params += '&contractNo=' + contractNo;
	}
	if(loanType != '全部' ){
		params += '&loanType=' + loanType;
	}
	if(buildDateStart != ''){
		params += '&buildDateStart=' + buildDateStart;
	}
	if(buildDateEnd != ''){
		params += '&buildDateEnd=' + buildDateEnd;
	}
	if(sendDateStart != ''){
		params += '&sendDateStart=' + sendDateStart;
	}
	if(sendDateEnd != ''){
		params += '&sendDateEnd=' + sendDateEnd;
	}
	if(status != ''){
		params += '&status=' + status;
	}
	
	if(params != ''){
		url += params;
		url2 += params;
	}
	$.ajax({
	  	 url : url,	  
	  	 type:"POST",
	  	 success : function(result){
		 	   if(result == "success"){
		 		  self.location.href = url2;
		 	   }else{
					 $.messager.show({
							title : '提示',
							msg : result
					});
			   }		    			
 			 
	   }
	});	

}


//按Enter键查询
$(document).keydown(function(e) {
	if (e.which == 13){	
		$('#searchBt').click();
	}
});

function search() {
	var queryParams = $('#syncLoanTb').datagrid('options').queryParams;
	queryParams.name = $('#toolbar #name').val();
	queryParams.idNum = $('#toolbar #idNum').val();
	queryParams.contractNo = $('#toolbar #contractNo').val();
	queryParams.loanType = $('#toolbar #loanType').combobox('getValue');
	queryParams.buildDateStart = $('#toolbar #buildDateStart').val();
	queryParams.buildDateEnd = $('#toolbar #buildDateEnd').val();
	queryParams.sendDateStart = $('#toolbar #sendDateStart').val();
	queryParams.sendDateEnd = $('#toolbar #sendDateEnd').val();
	queryParams.status = $('#toolbar #status').combobox('getValue');
	 setFirstPage("#syncLoanTb");
	$('#syncLoanTb').datagrid('options').queryParams = queryParams;
	$("#syncLoanTb").datagrid('reload');
}
