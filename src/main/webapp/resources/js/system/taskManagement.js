//多选下拉框
$(function() {
	$('#state').combo({
		required : true,
		editable : true,
		multiple : true
	});
	$('#sp').appendTo($('#state').combo('panel'));
	// 设置下拉默认值
	$('#state').combo('setValue', "3").combo('setText', "失败")
			.combo('hidePanel');

	$("#sp input").click(
			function() {
				var _value = "";
				var _text = "";
				$("[name=resultState]:input:checked").each(function() {
					_value += $(this).val() + ",";
					_text += $(this).next("span").text() + ",";
				});
				// 设置下拉选中值
				$('#state').combo('setValue', _value).combo('setText', _text)
						.combo('hidePanel');
			});
});
$(function() {
	// 查询按钮
	$('#searchBt').bind('click', search);

	// 设置下拉默认值
	$('#state').combo('setValue', "3").combo('setText', "失败")
			.combo('hidePanel');
	$('#list_result')
			.datagrid(
					{
						onLoadSuccess : function(data) {
							if (data.total == 0) {
								$.messager.show({
									title : '结果',
									msg : '没查到符合条件的数据！',
									showType : 'slide'
								});
							}
						},
						url : 'taskManagement/pageerTask',
						fitColumns : true,
						border : false,
						singleSelect : true,
						fit : true,
						pagination : true,
						striped : true,
						pageSize : 10,
						checkOnSelect : false,
						selectOnCheck : false,
						rownumbers : true,
						columns : [ [

								{
									field : 'id',
									title : 'ID',
								},
								{
									field : 'taskName',
									title : '任务名称',
									formatter : function(value, row, index) {
										return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger(\''
												+ row.taskName
												+ '\')">'
												+ row.taskName + '</a>';

									},
								},
								{
									field : 'className',
									title : '类名',
								},
								{
									field : 'executionDate',
									title : '日期',
								},
								{
									field : 'executionTime',
									title : '时间',
									formatter : formatterdate,
								},
								{
									field : 'taskDetailed',
									title : '任务详述'
								},
								{
									field : 'startTime',
									title : '最近一次执行时间开始时间',
								},
								{
									field : 'when',
									title : '用时(s)',
								},
								{
									field : 'resultState',
									title : '处理结果状态',
									formatter : function(value) {
										if (value == 1) {
											return "成功";
										} else if (value == 2) {
											return "部分成功";
										} else if (value == 3) {
											return "失败";
										}
									},
								},
								{
									field : 'handleNum',
									title : '处理条数',
								},
								{
									field : 'successNum',
									title : '处理条数成功',
								},
								{
									field : 'errorMessage',
									title : '错误信息',
								},
								{
									field : 'remark',
									title : '备注',
								},
								{
									field : 'runIp',
									title : '运行机器IP',
								},
								{
									field : 'a',
									title : '操作',
									formatter : function(value, row, index) {
										var oper = '';
										oper = '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="loadTaskManagementToWindow('
												+ row.id + ');">修改</a>'
										oper += '&nbsp;<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="deletedTaskManagement('
												+ row.id + ');">删除</a>';
										return oper;
									}
								} ] ]
					});
	// 设置分页控件
	var p = $('#list_result').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});

});
// 时间格式化
function formatterdate(val) {
	if (val != null) {
		var date = new Date(val);
		if (date.getHours() == 0 || date.getHours() < 10) {
			var hours = "0" + date.getHours();
		} else {
			var hours = date.getHours();
		}
		if (date.getMinutes() == 0 || date.getMinutes() < 10) {
			var minutes = "0" + date.getMinutes();
		} else {
			minutes = date.getMinutes();
		}
		if (date.getSeconds() == 0 || date.getSeconds() < 10) {
			var seconds = "0" + date.getSeconds();
		} else {
			seconds = date.getSeconds();
		}
		return hours + ":" + minutes + ":" + seconds;
	}
}
// 查询
function search() {
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.taskName = $('#toolbar #taskName').val();
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}
// 弹窗
function showLoanLedger(taskName) {
	// 查询按钮
	$('#searchSysJobLogBt').bind('click', searchSysJobLogBt);

	$('#showLoanLedgerDlg').dialog({
		title : '任务详情',
		inline : true,
		closed : false,
		cache : false,
		maximizable : true,
	});
	var url = 'taskManagement/sysJobLogList/' + taskName;
	$('#loanLedgerList').datagrid({
		url : url,
		fitColumns : true,
		border : false,
		singleSelect : true,
		pagination : true,
		striped : true,
		pageSize : 10,
		rownumbers : true,
		columns : [ [ {
			field : 'name',
			title : '名称',
		}, {
			field : 'runIP',
			title : '运行机器IP',
		}, {
			field : 'startTime',
			title : '开始时间',
		}, {
			field : 'endTime',
			title : '结束时间',
		}, {
			field : 'resultState',
			title : '处理结果状态',
			formatter : function(value) {
				if (value == 1) {
					return "成功";
				} else if (value == 2) {
					return "部分成功";
				} else if (value == 3) {
					return "失败";
				}
			},
		}, {
			field : 'handleNum',
			title : '处理条数',
		}, {
			field : 'successNum',
			title : '处理条数成功',
		}, {
			field : 'errorMessage',
			title : '错误信息',
		}, {
			field : 'remark',
			title : '备注',
		} ] ]
	});
	// 设置分页控件
	var p = $('#loanLedgerList').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});
}

// 弹窗查询按钮
function searchSysJobLogBt() {
	var queryParams = $('#loanLedgerList').datagrid('options').queryParams;
	var value = "";
	// 处理结果状态
	$("[name=resultState]:input:checked").each(function() {
		value += $(this).val() + ",";
	});
	queryParams.resultState = value;
	setFirstPage("#loanLedgerList");
	$('#loanLedgerList').datagrid('options').queryParams = queryParams;
	$("#loanLedgerList").datagrid('reload');
}
$().ready(
		function() {

			$("#showLoanLedgerDlg").dialog(
					{
						closed : true,
						onClose : function() {
							
							var queryParams = $('#loanLedgerList').datagrid('options').queryParams;
							queryParams.resultState = null;
							$('#list_result').datagrid('options').queryParams.resultState = queryParams;

							// 设置下拉默认值
							$('#state').combo('setValue', "3").combo('setText',
									"失败").combo('hidePanel');

							$("[name=resultState]:input:checked").each(
									function() {
										if ($(this).val() != "3") {
											$(this).removeAttr("checked");
										}
									});
						}
					});

			// 先关闭新增和修改弹窗
			$('#taskManagementPanel').window('close');

			// 新增弹窗
			$('#insertTaskManagementBut').click(function() {
				$('#taskManagementPanel').window({
					// width:300,
					height : $(window).height() / 2 - 20,
					modal : true
				});
				$('#taskManagementPanel').form('clear');
			})
			// 取消按钮
			$('.commonCloseBut').click(function() {
				$(this).parents('div .easyui-window').window('close');
			});
		})

// 加载某员工信息填充到表单
function loadTaskManagementToWindow(id) {
	if (!id) {
		return;
	}
	// 修改弹窗
	$
			.ajax({
				url : 'taskManagement/loadTaskManagement',
				data : {
					id : id
				},
				type : 'POST',
				success : function(result) {
					if (result.isSuccess) {
						$('#taskManagementPanel').window({
							// width:300,
							height : $(window).height() / 2 - 20,
							modal : true
						});
						result.taskManagement.executionTime = formatterdate(result.taskManagement.executionTime);
						$('#taskManagementPanel').form('clear');
						$('#taskManagementPanel').form('load',
								result.taskManagement);
						$("#panelTaskName").val(result.taskManagement.taskName);
					} else {
						$.messager.alert('操作提示', result.msg, 'error');
					}
				},
				error : function(data) {
					$.messager.alert('操作提示', 'error', 'error');
				}
			});
}
// 新增、修改定时任务
function doModifyTaskManagement() {
	var formObj = $('#taskManagementForm');
	if (formObj.form("validate")) {
		var id = $('#id').val();
		id = (id == "" ? null : id);
		var taskName = $('#panelTaskName').val();
		var className = $('#className').val();
		var executionDate = $('#executionDate').val();
		var executionTime = $('#executionTime').val();
		var taskDetailed = $('#taskDetailed').val();
		$.ajax({
			type : 'POST',
			url : 'taskManagement/saveTaskManagement',
			data : {
				'id' : id,
				'taskName' : taskName,
				'className' : className,
				'executionDate' : executionDate,
				'executionTime' : executionTime,
				'taskDetailed' : taskDetailed
			},
			dataType : 'json',
			success : function(data) {
				var isErr = '';
				if (data.isSuccess) {

				} else {
					isErr = 'error';
				}
				$.messager.alert('操作提示', data.msg, isErr);
				$('#searchBt').trigger('click');
				$('#taskManagementPanel').window('close');
			}
		});
	}
}
// 删除定时任务
function deletedTaskManagement(id) {
	if (!id) {
		return;
	}
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
		if (r) {
			$.ajax({
				url : 'taskManagement/deletedTaskManagement',
				data : {
					id : id
				},
				type : 'POST',
				success : function(result) {
					if (result.isSuccess) {
						$.messager.alert('操作提示', '操作成功');
						$("#list_result").datagrid('reload');
					} else {
						$.messager.alert('操作提示', result.msg, 'error');
					}
				},
				error : function(data) {
					$.messager.alert('操作提示', 'error', 'error');
				}
			});
		}
	});

}
