var url;
$(function () {
//设置默认时间--15天前
$('#toolbar #buildDateStart').val(buildDateStart);
$('#syncPreRepaymentTb').datagrid({
	url:'externalLoan/getSyncPreRepayment',
     border : false,
     singleSelect:true,
     pagination : true,
     striped: true,
     pageSize:10,
     rownumbers : true,
     fit:true,
     columns : [[
               {
    			field : 'companyNo',
    			title : '第三方机构编号',
    			width : 100
    		},	{
    			field : 'loanId',
    			title : '借款编号',
    			width : 80
    		},  {
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
    			field : 'loanType',
    			title : '借款类型',
    			width : 80
    		}, {
    			field : 'remark',
    			title : '异常原因',
    			width : 160
    		}, {
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
//点击【查询】按钮
$('#searchBt').bind('click', search);
});

//设置分页控件
var p = $('#syncPreRepaymentTb').datagrid('getPager');
$(p).pagination({
    pageList: [ 10, 20, 50 ]
});


//按Enter键查询
$(document).keydown(function(e) {
	if (e.which == 13){	
		$('#searchBt').click();
	}
});

function search() {
	var queryParams = $('#syncPreRepaymentTb').datagrid('options').queryParams;
	queryParams.name = $('#toolbar #name').val();
	queryParams.idNum = $('#toolbar #idNum').val();
	queryParams.contractNo = $('#toolbar #contractNo').val();
	queryParams.buildDateStart = $('#toolbar #buildDateStart').val();
	queryParams.buildDateEnd = $('#toolbar #buildDateEnd').val();
	queryParams.sendDateStart = $('#toolbar #sendDateStart').val();
	queryParams.sendDateEnd = $('#toolbar #sendDateEnd').val();
	queryParams.status = $('#toolbar #status').combobox('getValue');
	 setFirstPage("#syncPreRepaymentTb");
	$('#syncPreRepaymentTb').datagrid('options').queryParams = queryParams;
	$("#syncPreRepaymentTb").datagrid('reload');
}
