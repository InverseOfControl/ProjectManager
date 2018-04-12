
function editSeProductDetail(productId) {
	var strData = getProductDetails(productId);
	var seProductDetails = $.parseJSON(strData);
	var url = 'product/management/seProductEdit';
	$('#seProductEditDlg').dialog({
		title : '编辑产品',
		closed : false,
		cache : false,
		href : url,
		modal : true,
		onLoad : function (){
			loadSeProductDetails(seProductDetails);
		}
	});
}

function loadSeProductDetails (seProductDetails){
	//product info
	 $('#productSeEditForm').find('#id').val(seProductDetails.product.id);
	 $('#productSeEditForm').find('#status').val(formatEnumName(seProductDetails.product.status,'PRODUCT_STATUS'));
	 $('#productSeEditForm').find('#productName').val(seProductDetails.product.productName);
	 $('#productSeEditForm').find('#consultingFeeRate').val(seProductDetails.product.consultingFeeRate);
	 $('#productSeEditForm').find('#assessmentFeeRate').val(seProductDetails.product.assessmentFeeRate);
	 $('#productSeEditForm').find('#manageFeeRate').val(seProductDetails.product.manageFeeRate);
	 $('#productSeEditForm').find('#managePartRate').val(seProductDetails.product.managePartRate);
	 $('#productSeEditForm').find('#overdueInterestRate').val(seProductDetails.product.overdueInterestRate);
	 $('#productSeEditForm').find('#riskRate').val(seProductDetails.product.riskRate);
	 $('#productSeEditForm').find('#rate').val(seProductDetails.product.rate);
	//product detail info
	 if(seProductDetails.term3){
		 $(":checkbox[value=3]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate3').val(seProductDetails.sumRate3);
		 $('#productSeEditForm').find('#lowerLimit3').val(seProductDetails.lowerLimit3);
		 $('#productSeEditForm').find('#upperLimit3').val(seProductDetails.upperLimit3);
	 }
	 if(seProductDetails.term6){
		 $(":checkbox[value=6]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate6').val(seProductDetails.sumRate6);
		 $('#productSeEditForm').find('#lowerLimit6').val(seProductDetails.lowerLimit6);
		 $('#productSeEditForm').find('#upperLimit6').val(seProductDetails.upperLimit6);
	 }
	 if(seProductDetails.term9){
		 $(":checkbox[value=9]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate9').val(seProductDetails.sumRate9);
		 $('#productSeEditForm').find('#lowerLimit9').val(seProductDetails.lowerLimit9);
		 $('#productSeEditForm').find('#upperLimit9').val(seProductDetails.upperLimit9);
	 }
	 if(seProductDetails.term12){
		 $(":checkbox[value=12]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate12').val(seProductDetails.sumRate12);
		 $('#productSeEditForm').find('#lowerLimit12').val(seProductDetails.lowerLimit12);
		 $('#productSeEditForm').find('#upperLimit12').val(seProductDetails.upperLimit12);
	 }
	 if(seProductDetails.term18){
		 $(":checkbox[value=18]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate18').val(seProductDetails.sumRate18);
		 $('#productSeEditForm').find('#lowerLimit18').val(seProductDetails.lowerLimit18);
		 $('#productSeEditForm').find('#upperLimit18').val(seProductDetails.upperLimit18);
	 }
	 if(seProductDetails.term24){
		 $(":checkbox[value=24]").attr("checked",'checked');
		 $('#productSeEditForm').find('#sumRate24').val(seProductDetails.sumRate24);
		 $('#productSeEditForm').find('#lowerLimit24').val(seProductDetails.lowerLimit24);
		 $('#productSeEditForm').find('#upperLimit24').val(seProductDetails.upperLimit24);
	 }
	//city info
	 if(seProductDetails.baseAreaProductList){
		 for(var i =0;i<seProductDetails.baseAreaProductList.length;i++){
			 var baseAreaProduct = seProductDetails.baseAreaProductList[i];
			 $(":checkbox[value="+baseAreaProduct.areaId+"]").attr("checked",'checked');
		 }
	 }
}

/** 编辑产品保存**/
function productEditSave() {
	var isValidate = $("#productSeEditForm").form('validate');
	if (!isValidate) {
		return false;
	}
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
	
	$.messager.confirm('确认对话框', '确定要提交吗？', function(r) {
		if (r) {
			doSaveEditProduct();
		} else {
			return false;
		}
	});
}

function doSaveEditProduct() {
//	alert( $('#productSeEditForm').serialize());
	$.ajax({
		type : 'post',
		url : 'product/management/productEditSave',
		data : $('#productSeEditForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '提交成功！'
				});
				$('#seProductEditDlg').dialog('close');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}






