var url;
$(function() {
	//设置默认时间--15天前
    $('#toolbar #buildDateStart').val(buildDateStart);
	$('#syncRepayInfoTb').datagrid({
		url : 'externalLoan/getSyncRepayInfo',
		border : false,
		singleSelect : true,
		pagination : true,
		striped : true,
		pageSize : 10,
		rownumbers : true,
		fit:true,
		columns : [ [
		   {
			field : 'loanId',
			title : '借款编号',
			width : 80
		}, {
			field : 'companyNo',
			title : '第三方机构编号',
			width : 100
		}, {
			field : 'contractNo',
			title : '债权编号',
			width : 150
		}, {
			field : 'curNum',
			title : '当前期数',
			width : 80
		}, {
			field : 'totalAmount',
			title : '还款金额',
			width : 80,
			formatter : function(value, row, index) {
				if (value)
				return value.toFixed(2);
			}
		}, {
			field : 'payType',
			title : '还款方式 ',
			width : 80
		}, {
			field : 'remark',
			title : '备注',
			width : 180
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
		} ] ],
	});

	// 设置分页控件
	var p = $('#syncRepayInfoTb').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});
	// 点击【查询】按钮
	$('#searchBt').bind('click', search);
});



// 按Enter键查询
$(document).keydown(function(e) {
	if (e.which == 13) {
		$('#searchBt').click();
	}
});

function search() {
	var queryParams = $('#syncRepayInfoTb').datagrid('options').queryParams;
	queryParams.contractNo = $('#toolbar #contractNo').val();
	queryParams.buildDateStart = $('#toolbar #buildDateStart').val();
	queryParams.buildDateEnd = $('#toolbar #buildDateEnd').val();
	queryParams.sendDateStart = $('#toolbar #sendDateStart').val();
	queryParams.sendDateEnd = $('#toolbar #sendDateEnd').val();
	queryParams.status = $('#toolbar #status').combobox('getValue');
	setFirstPage("#syncRepayInfoTb");
	$('#syncRepayInfoTb').datagrid('options').queryParams = queryParams;
	$("#syncRepayInfoTb").datagrid('reload');
}
