
function loanSeProductDetail(productId) {
	var strData = getProductDetails(productId);
	var seProductDetails = $.parseJSON(strData);
	var url = 'product/management/seProductView';
	$('#seProductViewDlg').dialog({
		title : '新增产品',
		closed : false,
		cache : false,
		href : url,
		modal : true,
		onLoad : function (){
			//product info
			 $('#seProductViewForm #status').text(formatEnumName(seProductDetails.product.status,'PRODUCT_STATUS'));
			 $('#seProductViewForm #productName').text(seProductDetails.product.productName);
			 $('#seProductViewForm #consultingFeeRate').text(seProductDetails.product.consultingFeeRate);
			 $('#seProductViewForm #assessmentFeeRate').text(seProductDetails.product.assessmentFeeRate);
			 $('#seProductViewForm #manageFeeRate').text(seProductDetails.product.manageFeeRate);
			 $('#seProductViewForm #managePartRate').text(seProductDetails.product.managePartRate);
			 $('#seProductViewForm #overdueInterestRate').text(seProductDetails.product.overdueInterestRate);
			 $('#seProductViewForm #riskRate').text(seProductDetails.product.riskRate);
			 $('#seProductViewForm #rate').text(seProductDetails.product.rate);
			//product detail info
			 if(seProductDetails.term3){
				 $(":checkbox[value=3]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate3').text(seProductDetails.sumRate3);
				 $('#seProductViewForm #lowerLimit3').text(seProductDetails.lowerLimit3);
				 $('#seProductViewForm #upperLimit3').text(seProductDetails.upperLimit3);
			 }
			 if(seProductDetails.term6){
				 $(":checkbox[value=6]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate6').text(seProductDetails.sumRate6);
				 $('#seProductViewForm #lowerLimit6').text(seProductDetails.lowerLimit6);
				 $('#seProductViewForm #upperLimit6').text(seProductDetails.upperLimit6);
			 }
			 if(seProductDetails.term9){
				 $(":checkbox[value=9]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate9').text(seProductDetails.sumRate9);
				 $('#seProductViewForm #lowerLimit9').text(seProductDetails.lowerLimit9);
				 $('#seProductViewForm #upperLimit9').text(seProductDetails.upperLimit9);
			 }
			 if(seProductDetails.term12){
				 $(":checkbox[value=12]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate12').text(seProductDetails.sumRate12);
				 $('#seProductViewForm #lowerLimit12').text(seProductDetails.lowerLimit12);
				 $('#seProductViewForm #upperLimit12').text(seProductDetails.upperLimit12);
			 }
			 if(seProductDetails.term18){
				 $(":checkbox[value=18]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate18').text(seProductDetails.sumRate18);
				 $('#seProductViewForm #lowerLimit18').text(seProductDetails.lowerLimit18);
				 $('#seProductViewForm #upperLimit18').text(seProductDetails.upperLimit18);
			 }
			 if(seProductDetails.term24){
				 $(":checkbox[value=24]").attr("checked",'checked');
				 $('#seProductViewForm #sumRate24').text(seProductDetails.sumRate24);
				 $('#seProductViewForm #lowerLimit24').text(seProductDetails.lowerLimit24);
				 $('#seProductViewForm #upperLimit24').text(seProductDetails.upperLimit24);
			 }
			//city info
			 if(seProductDetails.baseAreaProductList){
				 for(var i =0;i<seProductDetails.baseAreaProductList.length;i++){
					 var baseAreaProduct = seProductDetails.baseAreaProductList[i];
					 $(":checkbox[value="+baseAreaProduct.areaId+"]").attr("checked",'checked');
				 }
			 }
		}
	});
}






