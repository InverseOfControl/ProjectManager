var url;
$(function () {
//设置默认时间--15天前
$('#toolbar #buildDateStart').val(buildDateStart);
$('#syncRepaymentPlanTb').datagrid({
	url:'externalLoan/getSyncRepaymentPlan',
     border : false,
     singleSelect:true,
     pagination : true,
     striped: true,
     pageSize:10,
     rownumbers : true,
     fit:true,
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
    			field : 'contractNo',
    			title : '债权编号',
    			width : 150
    		}, {
    			field : 'curNum',
    			title : '当前期数',
    			width : 80
    		}, {
    			field : 'repayDay',
    			title : '当前应还日期',
    			width : 100,
    			formatter : function(value, row, index) {
    				return value.substring(0,10);
    			}
    		}, {
    			field : 'principalAmt',
    			title : '当前应还本金',
    			width : 80,
    			formatter : function(value, row, index) {
    				return value.toFixed(2);
    			}
    		}, {
    			field : 'interestAmt',
    			title : '当前应还利息',
    			width : 80,
    			formatter : function(value, row, index) {
    				return value.toFixed(2);
    			}
    		}, {
    			field : 'totalAmt',
    			title : '应还总额',
    			width : 80,
    			formatter : function(value, row, index) {
    				return value.toFixed(2);
    			}
    		},  {
    			field : 'remainingPrincipal',
    			title : '本金余额',
    			width : 80,
    			formatter : function(value, row, index) {
    				return value.toFixed(2);
    			}
    		}, {
    			field : 'oneTimeRepaymentAmount',
    			title : '一次性还款总额',
    			width : 100,
    			formatter : function(value, row, index) {
    				return value.toFixed(2);
    			}
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
    		},{
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
var p = $('#SyncRepaymentPlanTb').datagrid('getPager');
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
	var queryParams = $('#syncRepaymentPlanTb').datagrid('options').queryParams;
	queryParams.contractNo = $('#toolbar #contractNo').val();
	queryParams.buildDateStart = $('#toolbar #buildDateStart').val();
	queryParams.buildDateEnd = $('#toolbar #buildDateEnd').val();
	queryParams.sendDateStart = $('#toolbar #sendDateStart').val();
	queryParams.sendDateEnd = $('#toolbar #sendDateEnd').val();
	queryParams.status = $('#toolbar #status').combobox('getValue');
	 setFirstPage("#syncRepaymentPlanTb");
	$('#syncRepaymentPlanTb').datagrid('options').queryParams = queryParams;
	$("#syncRepaymentPlanTb").datagrid('reload');
}
