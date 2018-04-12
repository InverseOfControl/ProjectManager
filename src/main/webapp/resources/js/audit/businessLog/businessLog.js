$(function() {
	var loanId = ($('#loanId').val() == '' ? 0 : $('#loanId').val());
	var url = 'audit/businessLog/detail.json/' + loanId;
	$('#business_log_result').datagrid({
		url : url,
		fitColumns : true,
		border : false,
		pagination : true,
		pageSize : 10,
		striped : true,
		rownumbers : true,
		fit:true,
		columns : [ [ {
			field : 'operator',
			title : '操作者',
			width : 50
		}, {
			field : 'flowStatus',
			title : '环节',
			width : 50
		}, {
			field : 'createDate',
			title : '操作时间',
			width : 50,
			formatter : $.mFuc.dateS
		}, {
			field : 'message',
			title : '日志内容',
			width : 200
		} ] ]
	});
	 // 设置分页控件
	   var p = $('#business_log_result').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
});