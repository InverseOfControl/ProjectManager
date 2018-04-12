/**
 * 银行卡变更
 */

$(function() {
	$("#bankAccountPageTb").datagrid(
			{
				url : 'apply/changeBankAccount/getBankAccountPage',
				fitColumns : true,
				border : false,
				singleSelect : false,
				pagination : true,
				striped : true,
				pageSize : 10,
				rownumbers : true,
				fit : true,
				columns : [ [
						{
							field : 'personName',
							title : '客户姓名',
							width : 50
						},
						{
							field : 'personIdnum',
							title : '身份证号',
							width : 100
						},
						{
							field : 'bankName',
							title : '银行名称',
							width : 100
						},
						{
							field : 'branchName',
							title : '分行名称',
							width : 100
						},
						{
							field : 'account',
							title : '银行卡号',
							width : 100
						},
						{
							field : 'productType',
							title : '借款类型',
							formatter : function(value, row, index) {
								return formatEnumName(value, 'PRODUCT_TYPE');
							},
							width : 50
						},
						{
							field : 'pactMoney',
							title : '合同金额',
							width : 50
						},
						{
							field : 'loanStatus',
							title : '借款状态',
							formatter : function(value, row, index) {
								return formatEnumName(value, 'LOAN_STATUS');
							},
							width : 50
						},
						{
							field : 'operation',
							title : '操作',
							formatter : function(value, rowData, rowIndex) {
								return "<a href='javascript:initBankAccount("
										+ rowData.id
										+ ");'>更改</a>&nbsp;&nbsp;&nbsp;";
							},
							width : 50
						} ] ]
			});

	// 设置分页控件
	var p = $('#bankAccountPageTb').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});

	$('#searchBtn').bind('click', search);
	// 回车键搜索
	$(document).keydown(function(e) {
		if (e.which == 13) {
			$('#searchBtn').click();
		}
	});
});

$('#changeBankAccountConfirm').bind('click', changeBankAccount);
$('#changeBankAccountCancel').bind('click', function() {
	$('#changeBankAccountDlg').dialog('close');
});

function search() {
	var queryParams = $('#bankAccountPageTb').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	setFirstPage("#bankAccountPageTb");
	$('#bankAccountPageTb').datagrid('options').queryParams = queryParams;
	$("#bankAccountPageTb").datagrid('reload');
}
// 更改银行卡
function initBankAccount(personBankId) {
	var url = 'apply/changeBankAccount/initAccount/' + personBankId;

	$('#changeBankAccountDlg').dialog({
		title : '变更银行卡',
		width : 400,
		height : 280,
		closed : false,
		cache : false,
		href : url,
		fit : true,
		modal : true
	});
	// $('#changeBankAccountDlg').show();
}

function changeBankAccount() {
	var isValidate = $(this).form('validate');
	if (!isValidate) {
		return false;
	}
	if ($('#changeBankAccountId').val() == 0) {
		parent.$.messager.show({
			title : '提示',
			msg : '请输入银行卡号'
		});
		return false;
	}
	if(isverificationBank ==1){
		// 验证银行卡是否是10-20位数字
		var reg = /^\d{10,20}$/g;
		if (!reg.test($('#changeBankAccountId').val())) {
			parent.$.messager.show({
				title : '提示',
				msg : '银行卡号格式错误，应该是10-20位数字！'
			});
			return false;
		}
		
	}

	$.messager.confirm('确认对话框', '您确认要更改吗？', function(r) {
		if (r) {
			doChangeAccount();
		} else {
			return false;
		}
	});
}

function doChangeAccount() {
	if ($('#updateAll').is(':checked')) {
		$('#isAllUpdate').val("true");
	} else {
		$('#isAllUpdate').val("false");
	}

	$.ajax({
		type : 'post',
		url : 'apply/changeBankAccount/changeAccount',
		data : $('#ff').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '更改成功！'
				});
				$('#changeBankAccountDlg').dialog('close');
				search();
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}
