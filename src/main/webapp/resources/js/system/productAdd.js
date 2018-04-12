$(function() {
	
	$("#checkAllCitys").click(function() {
		$('input[name="cityIds"]').attr("checked",this.checked); 
	});
	var $cityIds = $("input[name='cityIds']");
	$cityIds.click(function(){
		$("#checkAllCitys").attr("checked",$cityIds.length == $("input[name='cityIds']:checked").length ? true : false);
	});
	
	//product onchange
	$("#productType").combobox({
		onChange : function(value) {
			if (value == "2") { //车贷
				$("#carTermsTableDiv").css('display', 'block');
				$("#seTermsTableDiv").css('display', 'none');
			} else { //小企业贷
				$("#carTermsTableDiv").css('display', 'none');
				$("#seTermsTableDiv").css('display', 'block');
			}
		}
	});
});

function productSave() {
	var isValidate = $('#productAddForm').form('validate');
	if (!isValidate) {
		return false;
	}
	$.messager.confirm('确认对话框', '确定要提交吗？', function(r) {
		if (r) {
			doSaveProduct();
		} else {
			return false;
		}
	});
}

function doSaveProduct() {
	var cityIdList = '';
	$('input[name=cityIds]:checked').each(function(){
		cityIdList += $(this).val() + "|";
	});
	$('#cityIdList').val(cityIdList);
	
	var termList = '';
	$('input[name=terms]:checked').each(function(){
		termList += $(this).val() + "|";
	});
	$('#termList').val(termList);
	
	$.ajax({
		type : 'post',
		url : 'product/management/productSave',
		data : $('#productAddForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '提交成功！'
				});
				$('#productAddDlg').dialog('close');
				$("#list_result").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}
