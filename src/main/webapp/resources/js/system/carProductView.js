
function loanCarProductDetail(productId) {
	var strData = getProductDetails(productId);
	var carProductDetails = $.parseJSON(strData);
	var url = 'product/management/carProductView';
	$('#carProductViewDlg').dialog({
		title : '新增产品',
		closed : false,
		cache : false,
		href : url,
		modal : true,
		onLoad : function (){
			//product info
			 $('#carProductViewForm #status').text(formatEnumName(carProductDetails.product.status,'PRODUCT_STATUS'));
			 $('#carProductViewForm #productName').text(carProductDetails.product.productName);
			 $('#carProductViewForm #consultingFeeRate').text(carProductDetails.product.consultingFeeRate);
			 $('#carProductViewForm #assessmentFeeRate').text(carProductDetails.product.assessmentFeeRate);
			 $('#carProductViewForm #manageFeeRate').text(carProductDetails.product.manageFeeRate);
			 $('#carProductViewForm #managePartRate').text(carProductDetails.product.managePartRate);
			 $('#carProductViewForm #overdueInterestRate').text(carProductDetails.product.overdueInterestRate);
			 $('#carProductViewForm #riskRate').text(carProductDetails.product.riskRate);
			 $('#carProductViewForm #rate').text(carProductDetails.product.rate);
			//product detail info
			 if(carProductDetails.term3){
				 $(":checkbox[value=3]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate3Flow').text(carProductDetails.sumRate3Flow);
				 $('#carProductViewForm #sumRate3Transfer').text(carProductDetails.sumRate3Transfer);
				 $('#carProductViewForm #lowerLimit3').text(carProductDetails.lowerLimit3);
				 $('#carProductViewForm #upperLimit3').text(carProductDetails.upperLimit3);
			 }
			 if(carProductDetails.term6){
				 $(":checkbox[value=6]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate6Flow').text(carProductDetails.sumRate6Flow);
				 $('#carProductViewForm #sumRate6Transfer').text(carProductDetails.sumRate6Transfer);
				 $('#carProductViewForm #lowerLimit6').text(carProductDetails.lowerLimit6);
				 $('#carProductViewForm #upperLimit6').text(carProductDetails.upperLimit6);
			 }
			 if(carProductDetails.term9){
				 $(":checkbox[value=9]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate9Flow').text(carProductDetails.sumRate9Flow);
				 $('#carProductViewForm #sumRate9Transfer').text(carProductDetails.sumRate9Transfer);
				 $('#carProductViewForm #lowerLimit9').text(carProductDetails.lowerLimit9);
				 $('#carProductViewForm #upperLimit9').text(carProductDetails.upperLimit9);
			 }
			 if(carProductDetails.term12){
				 $(":checkbox[value=12]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate12Flow').text(carProductDetails.sumRate12Flow);
				 $('#carProductViewForm #sumRate12Transfer').text(carProductDetails.sumRate12Transfer);
				 $('#carProductViewForm #lowerLimit12').text(carProductDetails.lowerLimit12);
				 $('#carProductViewForm #upperLimit12').text(carProductDetails.upperLimit12);
			 }
			 if(carProductDetails.term18){
				 $(":checkbox[value=18]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate18Flow').text(carProductDetails.sumRate18Flow);
				 $('#carProductViewForm #sumRate18Transfer').text(carProductDetails.sumRate18Transfer);
				 $('#carProductViewForm #lowerLimit18').text(carProductDetails.lowerLimit18);
				 $('#carProductViewForm #upperLimit18').text(carProductDetails.upperLimit18);
			 }
			 if(carProductDetails.term24){
				 $(":checkbox[value=24]").attr("checked",'checked');
				 $('#carProductViewForm #sumRate24Flow').text(carProductDetails.sumRate24Flow);
				 $('#carProductViewForm #sumRate24Transfer').text(carProductDetails.sumRate24Transfer);
				 $('#carProductViewForm #lowerLimit24').text(carProductDetails.lowerLimit24);
				 $('#carProductViewForm #upperLimit24').text(carProductDetails.upperLimit24);
			 }
			//city info
			 if(carProductDetails.baseAreaProductList){
				 for(var i =0;i<carProductDetails.baseAreaProductList.length;i++){
					 var baseAreaProduct = carProductDetails.baseAreaProductList[i];
					 $(":checkbox[value="+baseAreaProduct.areaId+"]").attr("checked",'checked');
				 }
			 }
		}
	});
}






