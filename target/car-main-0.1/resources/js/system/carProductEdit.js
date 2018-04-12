
function editCarProductDetail(productId) {
	var strData = getProductDetails(productId);
	var carProductDetails = $.parseJSON(strData);
	var url = 'product/management/carProductEdit';
	$('#carProductEditDlg').dialog({
		title : '编辑产品',
		closed : false,
		cache : false,
		href : url,
		modal : true,
		onLoad : function (){
			loadCarProductDetails(carProductDetails);
		}
	});
}

function loadCarProductDetails(carProductDetails){

	//product info
	 $('#productCarEditForm').find('#id').val(carProductDetails.product.id);
	 $('#productCarEditForm').find('#status').val(formatEnumName(carProductDetails.product.status,'PRODUCT_STATUS'));
	 $('#productCarEditForm').find('#productName').val(carProductDetails.product.productName);
	 $('#productCarEditForm').find('#consultingFeeRate').val(carProductDetails.product.consultingFeeRate);
	 $('#productCarEditForm').find('#assessmentFeeRate').val(carProductDetails.product.assessmentFeeRate);
	 $('#productCarEditForm').find('#manageFeeRate').val(carProductDetails.product.manageFeeRate);
	 $('#productCarEditForm').find('#managePartRate').val(carProductDetails.product.managePartRate);
	 $('#productCarEditForm').find('#overdueInterestRate').val(carProductDetails.product.overdueInterestRate);
	 $('#productCarEditForm').find('#riskRate').val(carProductDetails.product.riskRate);
	 $('#productCarEditForm').find('#rate').val(carProductDetails.product.rate);
	//product detail info
	 if(carProductDetails.term3){
		 $(":checkbox[value=3]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate3Flow').val(carProductDetails.sumRate3Flow);
		 $('#productCarEditForm').find('#sumRate3Transfer').val(carProductDetails.sumRate3Transfer);
		 $('#productCarEditForm').find('#lowerLimit3').val(carProductDetails.lowerLimit3);
		 $('#productCarEditForm').find('#upperLimit3').val(carProductDetails.upperLimit3);
	 }
	 if(carProductDetails.term6){
		 $(":checkbox[value=6]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate6Flow').val(carProductDetails.sumRate6Flow);
		 $('#productCarEditForm').find('#sumRate6Transfer').val(carProductDetails.sumRate6Transfer);
		 $('#productCarEditForm').find('#lowerLimit6').val(carProductDetails.lowerLimit6);
		 $('#productCarEditForm').find('#upperLimit6').val(carProductDetails.upperLimit6);
	 }
	 if(carProductDetails.term9){
		 $(":checkbox[value=9]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate9Flow').val(carProductDetails.sumRate9Flow);
		 $('#productCarEditForm').find('#sumRate9Transfer').val(carProductDetails.sumRate9Transfer);
		 $('#productCarEditForm').find('#lowerLimit9').val(carProductDetails.lowerLimit9);
		 $('#productCarEditForm').find('#upperLimit9').val(carProductDetails.upperLimit9);
	 }
	 if(carProductDetails.term12){
		 $(":checkbox[value=12]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate12Flow').val(carProductDetails.sumRate12Flow);
		 $('#productCarEditForm').find('#sumRate12Transfer').val(carProductDetails.sumRate12Transfer);
		 $('#productCarEditForm').find('#lowerLimit12').val(carProductDetails.lowerLimit12);
		 $('#productCarEditForm').find('#upperLimit12').val(carProductDetails.upperLimit12);
	 }
	 if(carProductDetails.term18){
		 $(":checkbox[value=18]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate18Flow').val(carProductDetails.sumRate18Flow);
		 $('#productCarEditForm').find('#sumRate18Transfer').val(carProductDetails.sumRate18Transfer);
		 $('#productCarEditForm').find('#lowerLimit18').val(carProductDetails.lowerLimit18);
		 $('#productCarEditForm').find('#upperLimit18').val(carProductDetails.upperLimit18);
	 }
	 if(carProductDetails.term24){
		 $(":checkbox[value=24]").attr("checked",'checked');
		 $('#productCarEditForm').find('#sumRate24Flow').val(carProductDetails.sumRate24Flow);
		 $('#productCarEditForm').find('#sumRate24Transfer').val(carProductDetails.sumRate24Transfer);
		 $('#productCarEditForm').find('#lowerLimit24').val(carProductDetails.lowerLimit24);
		 $('#productCarEditForm').find('#upperLimit24').val(carProductDetails.upperLimit24);
	 }
	//city info
	 if(carProductDetails.baseAreaProductList){
		 for(var i =0;i<carProductDetails.baseAreaProductList.length;i++){
			 var baseAreaProduct = carProductDetails.baseAreaProductList[i];
			 $(":checkbox[value="+baseAreaProduct.areaId+"]").attr("checked",'checked');
		 }
	 }
}

/** 编辑产品保存**/
function productEditSave() {
	var isValidate = $("#productCarEditForm").form('validate');
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
	$.ajax({
		type : 'post',
		url : 'product/management/productEditSave',
		data : $('#productCarEditForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '提交成功！'
				});
				$('#carProductEditDlg').dialog('close');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}





