var _step=1;
$(function() {
	//编辑车贷信用卡
	$('#editCarLoanForm').find('#hasCreditCard').combobox({
    onSelect: function () {
        var value = $('#editCarLoanForm').find('#hasCreditCard').combobox('getValue');
        if(value==1){//有信用卡可填写额度等信息
            $('#editCarLoanForm').find('#cardNum').removeAttr("disabled");
            $('#editCarLoanForm').find('#totalAmount').removeAttr("disabled");
            $('#editCarLoanForm').find('#overdrawAmount').removeAttr("disabled");
        }else{
            $('#editCarLoanForm').find('#cardNum').val("");
            $('#editCarLoanForm').find('#totalAmount').val("");
            $('#editCarLoanForm').find('#overdrawAmount').val("");
            $('#editCarLoanForm').find('#cardNum').attr("disabled","disabled");
            $('#editCarLoanForm').find('#totalAmount').attr("disabled","disabled");
            $('#editCarLoanForm').find('#overdrawAmount').attr("disabled","disabled");
        }
    }
});
	//新增 车贷信用卡
	$('#addCarLoanForm').find('#hasCreditCard').combobox({
	    onSelect: function () {
	        var value = $('#addCarLoanForm').find('#hasCreditCard').combobox('getValue');
	        if(value==1){//有信用卡可填写额度等信息
	            $('#addCarLoanForm').find('#cardNum').removeAttr("disabled");
	            $('#addCarLoanForm').find('#totalAmount').removeAttr("disabled");
	            $('#addCarLoanForm').find('#overdrawAmount').removeAttr("disabled");
	        }else{
	            $('#addCarLoanForm').find('#cardNum').val("");
	            $('#addCarLoanForm').find('#totalAmount').val("");
	            $('#addCarLoanForm').find('#overdrawAmount').val("");
	            $('#addCarLoanForm').find('#cardNum').attr("disabled","disabled");
	            $('#addCarLoanForm').find('#totalAmount').attr("disabled","disabled");
	            $('#addCarLoanForm').find('#overdrawAmount').attr("disabled","disabled");
	        }
	    }
	});
	    //编辑车贷选择有无子女为时候输入框可输入
	$('#editCarLoanForm').find('#carPersonHasChildren').combobox({
	        onSelect: function () {        
	            var value = $('#editCarLoanForm').find('#carPersonHasChildren').combobox('getValue');
	            if(value==1){
	            	$('#editCarLoanForm').find('#childrenSchool').removeAttr("disabled");
	            }else{
	            	$('#editCarLoanForm').find('#childrenSchool').val("");
	            	$('#editCarLoanForm').find('#childrenSchool').attr("disabled","disabled");
	            }
	        }
	    });
	    //新增车贷选择有无子女为时候输入框可输入
	    $('#addCarLoanForm').find('#carPersonHasChildren').combobox({
	        onSelect: function () {        
	            var value = $('#addCarLoanForm').find('#carPersonHasChildren').combobox('getValue');
	            if(value==1){
	            	$('#addCarLoanForm').find('#childrenSchool').removeAttr("disabled");
	            }else{
	            	$('#addCarLoanForm').find('#childrenSchool').val("");
	            	$('#addCarLoanForm').find('#childrenSchool').attr("disabled","disabled");
	            }
	        }
	    });
	  
	   
	    //编辑车贷 勾选同户籍地址后，地址文本框自动填充
	    $('#editCarLoanForm').find("#checkCarAddress").bind("click", function () {
	    	
	    	if( $('#editCarLoanForm').find("#checkCarAddress").attr("checked")=="checked"){ 
	    	//	$('input[name=houseEstateAddress]').attr("readonly","readonly")
	    		 $('#editCarLoanForm').find('#editCarLoanForm').find("#carPersonAddress").attr("readonly","true");
	    		
	    		 $('#editCarLoanForm').find("#carPersonAddress").val($('#editCarLoanForm').find("#placeDomicile").val());
	    		
	    		 $('#editCarLoanForm').find('#editCarLoanForm').find("#carPersonZipCode").attr("readonly","true");
	    		
	    		 $('#editCarLoanForm').find("#carPersonZipCode").val($('#editCarLoanForm').find("#householdZipCode").val());
	    	}else{
	    		 $('#editCarLoanForm').find("#carPersonAddress").removeAttr("readonly");
	    		 $('#editCarLoanForm').find("#carPersonZipCode").removeAttr("readonly");
	    	}

	    });
	    //新增车贷 勾选同户籍地址后，地址文本框自动填充
	    $('#addCarLoanForm').find("#checkCarAddress").bind("click", function () {
	    	
	    	if( $('#addCarLoanForm').find("#checkCarAddress").attr("checked")=="checked"){ 
	    	//	$('input[name=houseEstateAddress]').attr("readonly","readonly")
	    		 $('#addCarLoanForm').find("#carPersonAddress").attr("readonly","true");
	    		
	    		 $('#addCarLoanForm').find("#carPersonAddress").val($('#addCarLoanForm').find("#placeDomicile").val());
	    		
	    		 $('#addCarLoanForm').find("#carPersonZipCode").attr("readonly","true");
	    		
	    		 $('#addCarLoanForm').find("#carPersonZipCode").val($('#addCarLoanForm').find("#householdZipCode").val());
	    	}else{
	    		 $('#addCarLoanForm').find("#carPersonAddress").removeAttr("readonly");
	    		 $('#addCarLoanForm').find("#carPersonZipCode").removeAttr("readonly");
	    	}

	    });
		//  车贷 居住类型
	    $('#editCarLoanForm').find('#liveType').combobox({
	        onSelect: function () {
	        	 var value = $('#editCarLoanForm').find('#liveType').combobox('getValue');
	             if(value=='按揭房'){ 	
	            	 $('#editCarLoanForm').find('#carRentPerMonth').removeAttr("disabled");      
	            	 $('#editCarLoanForm').find('#rentPerMonthLabel').text('每月房贷');
	             }else if(value=='租赁'){
	            	 $('#editCarLoanForm').find('#carRentPerMonth').removeAttr("disabled");
	            	 $('#editCarLoanForm').find('#rentPerMonthLabel').text('每月租金');        	  
	             } else{
	            	 $('#editCarLoanForm').find('#carRentPerMonth').val("");
	            	 $('#editCarLoanForm').find('#carRentPerMonth').attr("disabled","disabled"); 
	             }
	             
	          }
	    });
	    
	//  车贷 居住类型
	    $('#addCarLoanForm').find('#liveType').combobox({
	        onSelect: function () {
	        	 var value = $('#addCarLoanForm').find('#liveType').combobox('getValue');
	             if(value=='按揭房'){ 	
	            	 $('#addCarLoanForm').find('#carRentPerMonth').removeAttr("disabled");      
	            	 $('#addCarLoanForm').find('#rentPerMonthLabel').text('每月房贷');
	             }else if(value=='租赁'){
	            	 $('#addCarLoanForm').find('#carRentPerMonth').removeAttr("disabled");
	            	 $('#addCarLoanForm').find('#rentPerMonthLabel').text('每月租金');        	  
	             } else{
	            	 $('#addCarLoanForm').find('#carRentPerMonth').val("");
	            	 $('#addCarLoanForm').find('#carRentPerMonth').attr("disabled","disabled"); 
	             }
	             
	          }
	    });
	   /* $(".enterpriseType1").css("display","none");
	    $(".enterpriseType2").css("display","none");
	    $(".enterpriseType3").css("display","none");
	    $(".enterpriseType4").css("display","none");*/
		// 新增 车贷 私营企业类型显示隐藏
	    $('#addCarLoanForm').find('#professionType').combobox({
	        onSelect: function () {
	        	 var value = $('#addCarLoanForm').find('#professionType').combobox('getValue');
	             if(value=='自营'){ 	
	            	 $(".enterpriseType1").css("display","table-row");
	            	 $(".enterpriseType2").css("display","table-row");
	             }else{
	            	 $(".enterpriseType1").css("display","none");
	            	 $(".enterpriseType2").css("display","none");
	             }
	             
	          }
	    });
	 // 编辑车贷 私营企业类型显示隐藏
	    $('#editCarLoanForm').find('#professionType').combobox({
	        onSelect: function () {
	        	 var value = $('#editCarLoanForm').find('#professionType').combobox('getValue');	        	
	             if(value=='自营'){ 	
	            	 $(".enterpriseType3").css("display","table-row");
	            	 $(".enterpriseType4").css("display","table-row");
	             }else{
	            	 $(".enterpriseType3").css("display","none");
	            	 $(".enterpriseType4").css("display","none");
	             }
	             
	          }
	    });
	  //上一步 车贷
	    $("#prevCarStepBtn").click(function(){
	        $("#addCarTable"+ _step).hide();
	        _step -= 1;
	        $("#addCarTable"+ _step).show();
	        $("#nextCarStepBtn").show();
	        if(_step <= 1){
	            $(this).hide();
	        }else{
	            $(this).show();
	        }
	        if(_step >= 2){
	            $("#addCarLoanBtn").show();
	        }else{
	            $("#addCarLoanBtn").hide();
	        }
	    });
	    //下一步 车贷
	    $("#nextCarStepBtn").click(function(){	    	
	        if(!$("#addCarTable"+ _step).form('validate')){
	            return false;
	        }  
	        // 验证table
	        $("#addCarTable"+ _step).hide();
	        _step += 1;
	        $("#addCarTable"+ _step).show();
	        $("#prevCarStepBtn").show();
	        if(_step >= 2){
	            $(this).hide();
	            $("#addCarLoanBtn").show();
	        }else{
	            $(this).show();
	            $("#addCarLoanBtn").hide();
	        }
	    });

	    //新增上一步 车贷
	    $("#prevCarStepBtnNew").click(function(){
	        $("#addCarTableNew"+ _step).hide();
	        _step -= 1;
	        $("#addCarTableNew"+ _step).show();
	        $("#nextCarStepBtnNew").show();
	        if(_step <= 1){
	            $(this).hide();
	        }else{
	            $(this).show();
	        }
	        if(_step >= 2){
	            $("#addCarLoanBtnNew").show();
	        }else{
	            $("#addCarLoanBtnNew").hide();
	        }
	    });
	    //新增下一步 车贷
	    $("#nextCarStepBtnNew").click(function(){	
	    	  // 验证table
	        if(!$("#addCarTableNew"+ _step).form('validate')){
	            return false;
	        } 
	        $("#addCarTableNew"+ _step).hide();
	        _step += 1;
	        $("#addCarTableNew"+ _step).show();
	        $("#addCarLoanBtnNew").show();
	        if(_step >= 2){
	            $(this).hide();
	            $("#prevCarStepBtnNew").show();
	        }else{
	            $(this).show();
	            $("#nextCarStepBtnNew").hide();
	        }
	    });

    //车贷选择信用卡
    $('#browseForm').form({
        url:'apply/toCarLoanDetail'
    });
});


function showUploadAttachmentDlg(){
$('#uploadDlg').dialog('open').dialog('setTitle', '　');
$('#uploadDlg #attachmentTitleInput').val('');
$('#uploadDlg #uploadInput').uploadify('cancel');
}

function transferUndefinedAndZero(data){
	if(data){
		return data;
	}else{
		return "";
	}
}

function transferUndefined(data){
	if(data){
		return data;
	}else if(data==0){
		return data;
	}else{
		return "";
	}
}
//车贷-经营场所
function furmatBusinessPlace(businessPlace){
	if(businessPlace==1){
		return  '租用';
	}else if(businessPlace==2){
		return ' 自有房产';
	}else{
		return '';
	}
}
/**查看车贷*/
function doCarLoanBrowse (loanId){
	var flag='apply';
    var strData = getCarLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();  
    
    //查看车贷对话框
    var url = 'RefusalEntry/carLoanDetail';
    $('#carLoanDetail').dialog({
        title: '查看车贷',
        width: 1100,
        height:800,
        closed: false,  
        cache: false,
        href: url,
        modal: true,
        onLoad : function(){
        	//  $('#browseCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '　');
        	 
        	    if(loanDetails.product) {
        	        $('#browseCLForm').find('#productName').text(loanDetails.product.productName);
        	    }
        	    if(loanDetails.loan) {
        	       // $('#browseCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
        	        $('#browseCLForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
        	        $('#browseCLForm #requestTime').text(loanDetails.loan.requestTime + "期");
        	        $('#browseCLForm #purpose').text(loanDetails.loan.purpose);
        	    }
        	    if(loanDetails.person) {
        	    	$('#browseCLForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
        	    	$('#browseCLForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
        	    	if(loanDetails.person.professionType=='自营'){
        	    		$('.enterpprise1').css('display','table-row');
        	    		$('.enterpprise2').css('display','table-row');
        	    		$('#browseCLForm #privateEnterpriseType').text(transferUndefined(loanDetails.person.privateEnterpriseType));
             	        $('#browseCLForm #foundedDate').text(getYMD(loanDetails.person.foundedDate));//成立时间
             	        $('#browseCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
             	        $('#browseCLForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees)+'人');
             	        $('#browseCLForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments)+'%');
             	        $('#browseCLForm #monthOfProfit').text(transferUndefined(loanDetails.person.monthOfProfit)+'万元/月');
        	    	}else{
        	    		$('.enterpprise1').css('display','none');
        	    		$('.enterpprise2').css('display','none');
        	    	}
        	        $('#browseCLForm #personName').text(loanDetails.person.name);
        	        $('#browseCLForm #personSex').text(formatSex(loanDetails.person.sex));
        	        $('#browseCLForm #personIdnum').text(loanDetails.person.idnum);
        	        $('#browseCLForm #personMarried').text(formatMarried(loanDetails.person.married));
        	        $('#browseCLForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
        	        $('#browseCLForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
        	        if(loanDetails.person.hasChildren==1){
        	        	$('#browseCLForm #childrenSchool').text(loanDetails.person.childrenSchool);
        	        }else{
        	        	$('#browseCLForm #school').text('');//不显示子女在读学校        	        	
        	        }
        	        $('#browseCLForm #personMobilePhone').text(loanDetails.person.mobilePhone);
        	        $('#browseCLForm #personEmail').text(loanDetails.person.email);
        	        $('#browseCLForm #personHomePhone').text(loanDetails.person.homePhone);
        	        $('#browseCLForm #personPlaceDomicile').text(loanDetails.person.placeDomicile);
        	        $('#browseCLForm #personHouseholdZipCode').text(loanDetails.person.householdZipCode);
        	        $('#browseCLForm #personAddress').text(loanDetails.person.address);
        	        // 根据居住类型，决定每月租金和每月房贷是否显示，规则
        	        // 如果居住类型是按揭房，则显示每月房贷
        	        // 如果居住类型是租赁，则显示每月租金
        	        // 如果其他的，则不显示每月租金和每月房贷
        	       
        	        $('#browseCLForm #personLiveType').text(loanDetails.person.liveType);
        	        var liveType = loanDetails.person.liveType;
        	        var liveTypeTR =  $('#browseCLForm #personLiveType').parent().parent();
        	        if(liveType=='按揭房'){
        	        	liveTypeTR.find(':nth-child(3)').text('每月房贷').show();
        	        	liveTypeTR.find(':nth-child(4)').show();
        	        	if(loanDetails.person.rentPerMonth){
        	        		$('#browseCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}else if(loanDetails.person.rentPerMonth==0){
        	        		$('#browseCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}
        	        	
        	        } else if(liveType == '租赁'){
        	        	liveTypeTR.find(':nth-child(3)').text('每月租金').show();
        	        	liveTypeTR.find(':nth-child(4)').show();
        	        	if(loanDetails.person.rentPerMonth){
        	        		$('#browseCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}else if(loanDetails.person.rentPerMonth==0){
        	        		$('#browseCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}
        	        } else{
        	        	liveTypeTR.find(':nth-child(3)').hide();
                		liveTypeTR.find(':nth-child(4)').hide();
                	}
        	        $('#browseCLForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
        	        $('#browseCLForm #personZipCode').text(loanDetails.person.zipCode);
        	    }
        	   
        	    if(loanDetails.vehicle) {
        	        $('#browseCLForm #vehicleBrand').text(loanDetails.vehicle.brand);
        	        $('#browseCLForm #vehicleModel').text(loanDetails.vehicle.model);
        	        $('#browseCLForm #vehicleCoty').text(loanDetails.vehicle.coty+"年");
        	        $('#browseCLForm #vehicleMileage').text(loanDetails.vehicle.mileage+"公里");
        	        $('#browseCLForm #vehiclePlateNumber').text(loanDetails.vehicle.plateNumber);
        	        $('#browseCLForm #vehicleFrameNumber').text(loanDetails.vehicle.frameNumber);
        	    }
        	    if(loanDetails.company) {
        	        $('#browseCLForm #companyName').text(loanDetails.company.name);
        	        $('#browseCLForm #companyAddress').text(loanDetails.company.address);
        	        $('#browseCLForm #personCompanyType').text(formatCompanyType(loanDetails.company.companyType));
        	    }
        	    if(loanDetails.person) {
        	        $('#browseCLForm #personDeptName').text(loanDetails.person.deptName);
        	        $('#browseCLForm #personJob').text(loanDetails.person.job);
        	        $('#browseCLForm #personExt').text(loanDetails.person.ext);
        	        $('#browseCLForm #personWorkNature').text(loanDetails.person.workNature);
        	        $('#browseCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");
        	        $('#browseCLForm #personPayDay').text(loanDetails.person.payDate + "号");
        	        if(loanDetails.person.otherIncome){
        	        	$('#browseCLForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        	        }
        	        $('#browseCLForm #personWitness').text(loanDetails.person.witness);
        	        $('#browseCLForm #personWorkThatDept').text(loanDetails.person.workThatDept);
        	        $('#browseCLForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
        	        $('#browseCLForm #personWorkThatTell').text(loanDetails.person.workThatTell);
        	        $('#browseCLForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
        	    }
        	    if(loanDetails.creditHistory) {
        	        $('#browseCLForm #creditHistoryHasCreditCard').text(formatHave(loanDetails.creditHistory.hasCreditCard));
        	        if(loanDetails.creditHistory.hasCreditCard==1){
    		        	$('#browseCLForm #creditHistoryCardNum').text(transferUndefinedAndZero(loanDetails.creditHistory.cardNum));
    		        	$('#browseCLForm #creditHistoryTotalAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.totalAmount) + "元");
    		        	$('#browseCLForm #creditHistoryOverdrawAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.overdrawAmount) + "元");
        	    	}else{
        	    		 $('#browseCLForm #creditHistoryCardNum').text('');
         		        $('#browseCLForm #creditHistoryTotalAmount').text('');
         		        $('#browseCLForm #creditHistoryOverdrawAmount').text('');
        	    	}
        	    }
        	    if(loanDetails.service) {
        	        $('#browseCLForm #serviceName').text(loanDetails.service.name);
        	    }
        	    if(loanDetails.loan) {
        	        $('#browseCLForm #customerSource').text(loanDetails.loan.customerSource);
        	        $('#browseCLForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
        	    }
        	    if(loanDetails.crm) {
        	        $('#browseCLForm #crmCode').text(loanDetails.crm.code);
        	        $('#browseCLForm #crmName').text(loanDetails.crm.name);
        	    }
        	    //业务主任
        	    if(loanDetails.director) {
        	        $('#browseCLForm #directorCode').text(loanDetails.director.code);
        	        $('#browseCLForm #directorName').text(loanDetails.director.name);
        	    }
        	    if(loanDetails.salesDept) {
        	        $('#browseCLForm #salesDeptName').text(loanDetails.salesDept.name);
        	    }
        	    if(loanDetails.assessor) {
        	        $('#browseCLForm #assessorName').text(loanDetails.assessor.name);
        	    }
        	    if(loanDetails.loan.remark) {
        		    $('#browseCLForm #remark').text(loanDetails.loan.remark);
        		 }
        	    
        	    $('#contacterCLBrowseTab > #contacterQTLXRBrowsePanelTemplate ~ div').remove();
        	    if(loanDetails.contacterList) {
        	        var otherContacter = 0;
        	        var haveZXQS = false;
        	        var haveXDWLXR = false;
        	        for(var i =0;i<loanDetails.contacterList.length;i++){
        	            var contacter = loanDetails.contacterList[i];
        	            if(contacter.relationship == '直系亲属' && !haveZXQS) {
        	            	haveZXQS= true;
        	                var contacterZXQSBrowsePanel = $('#contacterZXQSBrowsePanel').show().addClass('easyui-panel');
        	                contacterZXQSBrowsePanel.attr("title", "直系亲属");
        	                contacterZXQSBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterZXQSBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterZXQSBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
        	                contacterZXQSBrowsePanel.find('#contacterAddress').text(contacter.address);

        	                contacterZXQSBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterZXQSBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
        	            }else if(contacter.relationship == '现单位同事' && !haveXDWLXR){
        	            	haveXDWLXR = true;
        	                var contacterXDWLXRBrowsePanel = $('#contacterXDWLXRBrowsePanel').show().addClass('easyui-panel');
        	                contacterXDWLXRBrowsePanel.attr("title", "现单位同事");
        	                contacterXDWLXRBrowsePanel.show();
        	                contacterXDWLXRBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterXDWLXRBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterXDWLXRBrowsePanel.find('#contacterAddress').text(contacter.address);
        	                contacterXDWLXRBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);;
        	                contacterXDWLXRBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterXDWLXRBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
        	            }else {
        	                otherContacter += 1;

        	                var contacterCLBrowsePanel =  $('#contacterQTLXRBrowsePanelTemplate').clone().show().addClass('easyui-panel');
        	                var contacterCLBrowsePanelId = "contacterCLBrowsePanel_" + i;
        	                contacterCLBrowsePanel.attr("id",contacterCLBrowsePanelId);
        	                contacterCLBrowsePanel.attr("title", "其他联系人"+(otherContacter));

        	                contacterCLBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterCLBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterCLBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
        	                contacterCLBrowsePanel.find('#contacterAddress').text(contacter.address);
        	                contacterCLBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterCLBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));

        	                contacterCLBrowsePanel.appendTo($('#contacterCLBrowseTab'));
        	            }
        	        }
        	       
        	    }		
        }
	
        });
  
    $.parser.parse('#contacterCLBrowseTab');
};


/**查看展期车贷*/
function doCarLoanExtension(loanId){
	var flag='apply';
    var strData = getCarLoanExtensionDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();  
    
    //查看车贷对话框
    var url = 'apply/carLoanExtensionDetail';
    $('#carLoanExtensionDetail').dialog({
        title: '查看车贷展期',
        width: 1100,
        height:800,
        closed: false,  
        cache: false,
        href: url,
        modal: true,
        onLoad : function(){
        	//  $('#browseCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '　');
        	 
        	    if(loanDetails.product) {
        	        $('#browseExtensionCLForm').find('#productName').text(loanDetails.product.productName);
        	    }
        	    if(loanDetails.loan) {
        	      //  $('#browseExtensionCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
        	        $('#browseExtensionCLForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
        	        $('#browseExtensionCLForm #requestTime').text(loanDetails.loan.requestTime + "期");
        	        $('#browseExtensionCLForm #purpose').text(loanDetails.loan.purpose);
        	    }
        	    if(loanDetails.person) {
        	    	$('#browseExtensionCLForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
        	    	$('#browseExtensionCLForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
        	    	if(loanDetails.person.professionType=='自营'){
        	    		$('.enterpprise1').css('display','table-row');
        	    		$('.enterpprise2').css('display','table-row');
        	    		$('#browseExtensionCLForm #privateEnterpriseType').text(transferUndefined(loanDetails.person.privateEnterpriseType));
             	        $('#browseExtensionCLForm #foundedDate').text(getYMD(loanDetails.person.foundedDate));//成立时间
             	        $('#browseExtensionCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
             	        $('#browseExtensionCLForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees)+'人');
             	        $('#browseExtensionCLForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments)+'%');
             	        $('#browseExtensionCLForm #monthOfProfit').text(transferUndefined(loanDetails.person.monthOfProfit)+'万元/月');
        	    	}else{
        	    		$('.enterpprise1').css('display','none');
        	    		$('.enterpprise2').css('display','none');
        	    	}
        	        $('#browseExtensionCLForm #personName').text(loanDetails.person.name);
        	        $('#browseExtensionCLForm #personSex').text(formatSex(loanDetails.person.sex));
        	        $('#browseExtensionCLForm #personIdnum').text(loanDetails.person.idnum);
        	        $('#browseExtensionCLForm #personMarried').text(formatMarried(loanDetails.person.married));
        	        $('#browseExtensionCLForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
        	        $('#browseExtensionCLForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
        	        if(loanDetails.person.hasChildren==1){
        	        	$('#browseExtensionCLForm #childrenSchool').text(loanDetails.person.childrenSchool);
        	        }else{
        	        	$('#browseExtensionCLForm #school').text('');//不显示子女在读学校        	        	
        	        }
        	        $('#browseExtensionCLForm #personMobilePhone').text(loanDetails.person.mobilePhone);
        	        $('#browseExtensionCLForm #personEmail').text(loanDetails.person.email);
        	        $('#browseExtensionCLForm #personHomePhone').text(loanDetails.person.homePhone);
        	        $('#browseExtensionCLForm #personPlaceDomicile').text(loanDetails.person.placeDomicile);
        	        $('#browseExtensionCLForm #personHouseholdZipCode').text(loanDetails.person.householdZipCode);
        	        $('#browseExtensionCLForm #personAddress').text(loanDetails.person.address);
        	        // 根据居住类型，决定每月租金和每月房贷是否显示，规则
        	        // 如果居住类型是按揭房，则显示每月房贷
        	        // 如果居住类型是租赁，则显示每月租金
        	        // 如果其他的，则不显示每月租金和每月房贷
        	       
        	        $('#browseExtensionCLForm #personLiveType').text(loanDetails.person.liveType);
        	        var liveType = loanDetails.person.liveType;
        	        var liveTypeTR =  $('#browseExtensionCLForm #personLiveType').parent().parent();
        	        if(liveType=='按揭房'){
        	        	liveTypeTR.find(':nth-child(3)').text('每月房贷').show();
        	        	liveTypeTR.find(':nth-child(4)').show();
        	        	if(loanDetails.person.rentPerMonth){
        	        		$('#browseExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}else if(loanDetails.person.rentPerMonth==0){
        	        		$('#browseExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}
        	        	
        	        } else if(liveType == '租赁'){
        	        	liveTypeTR.find(':nth-child(3)').text('每月租金').show();
        	        	liveTypeTR.find(':nth-child(4)').show();
        	        	if(loanDetails.person.rentPerMonth){
        	        		$('#browseExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}else if(loanDetails.person.rentPerMonth==0){
        	        		$('#browseExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	        	}
        	        } else{
        	        	liveTypeTR.find(':nth-child(3)').hide();
                		liveTypeTR.find(':nth-child(4)').hide();
                	}
        	        $('#browseExtensionCLForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
        	        $('#browseExtensionCLForm #personZipCode').text(loanDetails.person.zipCode);
        	    }
        	   
        	    if(loanDetails.vehicle) {
        	        $('#browseExtensionCLForm #vehicleBrand').text(loanDetails.vehicle.brand);
        	        $('#browseExtensionCLForm #vehicleModel').text(loanDetails.vehicle.model);
        	        $('#browseExtensionCLForm #vehicleCoty').text(loanDetails.vehicle.coty+"年");
        	        $('#browseExtensionCLForm #vehicleMileage').text(loanDetails.vehicle.mileage+"公里");
        	        $('#browseExtensionCLForm #vehiclePlateNumber').text(loanDetails.vehicle.plateNumber);
        	        $('#browseExtensionCLForm #vehicleFrameNumber').text(loanDetails.vehicle.frameNumber);
        	    }
        	    if(loanDetails.company) {
        	        $('#browseExtensionCLForm #companyName').text(loanDetails.company.name);
        	        $('#browseExtensionCLForm #companyAddress').text(loanDetails.company.address);
        	        $('#browseExtensionCLForm #personCompanyType').text(formatCompanyType(loanDetails.company.companyType));
        	    }
        	    if(loanDetails.person) {
        	        $('#browseExtensionCLForm #personDeptName').text(loanDetails.person.deptName);
        	        $('#browseExtensionCLForm #personJob').text(loanDetails.person.job);
        	        $('#browseExtensionCLForm #personExt').text(loanDetails.person.ext);
        	        $('#browseExtensionCLForm #personWorkNature').text(loanDetails.person.workNature);
        	        $('#browseExtensionCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");
        	        $('#browseExtensionCLForm #personPayDay').text(loanDetails.person.payDate + "号");

        	        if(loanDetails.person.otherIncome){
        	        $('#browseExtensionCLForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        	        }
        	        $('#browseExtensionCLForm #personWitness').text(loanDetails.person.witness);
        	        $('#browseExtensionCLForm #personWorkThatDept').text(loanDetails.person.workThatDept);
        	        $('#browseExtensionCLForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
        	        $('#browseExtensionCLForm #personWorkThatTell').text(loanDetails.person.workThatTell);
        	        $('#browseExtensionCLForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
        	    }
        	    if(loanDetails.creditHistory) {
        	        $('#browseExtensionCLForm #creditHistoryHasCreditCard').text(formatHave(loanDetails.creditHistory.hasCreditCard));
        	        if(loanDetails.creditHistory.hasCreditCard==1){
    	        		$('#browseExtensionCLForm #creditHistoryCardNum').text(transferUndefinedAndZero(loanDetails.creditHistory.cardNum));
    	        		$('#browseExtensionCLForm #creditHistoryTotalAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.totalAmount) + "元");
    	        		$('#browseExtensionCLForm #creditHistoryOverdrawAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.overdrawAmount) + "元");
        	    	}else{
        	    		$('#browseExtensionCLForm #creditHistoryCardNum').text('');
         		        $('#browseExtensionCLForm #creditHistoryTotalAmount').text('');
         		        $('#browseExtensionCLForm #creditHistoryOverdrawAmount').text('');
        	    	}
        	    }
        	    if(loanDetails.service) {
        	        $('#browseExtensionCLForm #serviceName').text(loanDetails.service.name);
        	    }
        	    if(loanDetails.loan) {
        	        $('#browseExtensionCLForm #customerSource').text(loanDetails.loan.customerSource);
        	        $('#browseExtensionCLForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
        	    }
        	    if(loanDetails.crm) {
        	        $('#browseExtensionCLForm #crmCode').text(loanDetails.crm.code);
        	        $('#browseExtensionCLForm #crmName').text(loanDetails.crm.name);
        	    }
        	       //业务主任
        	    if(loanDetails.director) {
        	        $('#browseExtensionCLForm #directorCode').text(loanDetails.director.code);
        	        $('#browseExtensionCLForm #directorName').text(loanDetails.director.name);
        	    }
        	    if(loanDetails.salesDept) {
        	        $('#browseExtensionCLForm #salesDeptName').text(loanDetails.salesDept.name);
        	    }
        	    if(loanDetails.assessor) {
        	        $('#browseExtensionCLForm #assessorName').text(loanDetails.assessor.name);
        	    }
        	    if(loanDetails.loan.remark) {
        		    $('#browseExtensionCLForm #remark').text(loanDetails.loan.remark);
        		 }
        	    
        	    $('#contacterCLBrowseTab > #contacterQTLXRBrowsePanelTemplate ~ div').remove();
        	    if(loanDetails.contacterList) {
        	        var otherContacter = 0;
        	        var haveZXQS = false;
        	        var haveXDWLXR = false;
        	        for(var i =0;i<loanDetails.contacterList.length;i++){
        	            var contacter = loanDetails.contacterList[i];
        	            if(contacter.relationship == '直系亲属' && !haveZXQS) {
        	            	haveZXQS= true;
        	                var contacterZXQSBrowsePanel = $('#contacterZXQSBrowsePanel').show().addClass('easyui-panel');
        	                contacterZXQSBrowsePanel.attr("title", "直系亲属");
        	                contacterZXQSBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterZXQSBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterZXQSBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
        	                contacterZXQSBrowsePanel.find('#contacterAddress').text(contacter.address);

        	                contacterZXQSBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterZXQSBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
        	            }else if(contacter.relationship == '现单位同事' && !haveXDWLXR){
        	            	haveXDWLXR = true;
        	                var contacterXDWLXRBrowsePanel = $('#contacterXDWLXRBrowsePanel').show().addClass('easyui-panel');
        	                contacterXDWLXRBrowsePanel.attr("title", "现单位同事");
        	                contacterXDWLXRBrowsePanel.show();
        	                contacterXDWLXRBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterXDWLXRBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterXDWLXRBrowsePanel.find('#contacterAddress').text(contacter.address);
        	                contacterXDWLXRBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);;
        	                contacterXDWLXRBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterXDWLXRBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
        	            }else {
        	                otherContacter += 1;

        	                var contacterCLBrowsePanel =  $('#contacterQTLXRBrowsePanelTemplate').clone().show().addClass('easyui-panel');
        	                var contacterCLBrowsePanelId = "contacterCLBrowsePanel_" + i;
        	                contacterCLBrowsePanel.attr("id",contacterCLBrowsePanelId);
        	                contacterCLBrowsePanel.attr("title", "其他联系人"+(otherContacter));

        	                contacterCLBrowsePanel.find('#contacterName').text(contacter.name);
        	                contacterCLBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
        	                contacterCLBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
        	                contacterCLBrowsePanel.find('#contacterAddress').text(contacter.address);
        	                contacterCLBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
        	                contacterCLBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));

        	                contacterCLBrowsePanel.appendTo($('#contacterCLBrowseTab'));
        	            }
        	        }
        	       
        	    }		
        }
	
        });
  
    $.parser.parse('#contacterCLBrowseTab');
};


/**获取贷款详细信息*/
function getCarLoanDetails(loanId,flag){
    var response = $.ajax({
        type: "POST",
        url: "RefusalEntry/toCarLoanDetail",
        dataType: "json",
        async:false,
        data: {
            loanId:loanId,
            flag:flag
        },
        error:function(){
            $.messager.show({
                title:'加载贷款信息',
                msg:'加载车贷贷款信息失败！',
                showType:'slide'
            });
        }
    });
    return response.responseText;
}

/**获取贷款详细信息*/
function getCarLoanExtensionDetails(loanId,flag){
    var response = $.ajax({
        type: "POST",
        url: "apply/toCarExtensionLoanDetail",
        dataType: "json",
        async:false,
        data: {
            loanId:loanId,
            flag:flag
        },
        error:function(){
            $.messager.show({
                title:'加载贷款信息',
                msg:'加载车贷贷款信息失败！',
                showType:'slide'
            });
        }
    });
    return response.responseText;
}
// 提交操作时间时间
function formatDate(val) {
    return $.mFuc.dateD(val);
};


//清空车贷页面上的数据
function clearCarLoanDate(){
	$('#editCarLoanForm :input').val('');
}

//加载新增车贷数据
function loadAddCarLoanData(loanDetailsVo){//车贷贷款信息
	
	//产品期数
	$('#addCarLoanForm').find('#requestTime').combobox({
	    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
        	var data = $('#addCarLoanForm').find('#requestTime').combobox('getData');
        	if(data.length==1)
        		$('#addCarLoanForm').find('#requestTime').combobox('select', data[0].id);
        }
	 });
	
	$('#addCarLoanForm').find('#carPersonIdnumName').text(loanDetailsVo.idnum);
	 $('#carPersonIdnum').val(loanDetailsVo.idnum);
	    //其它
	 $('#addCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	 $('#addCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);	
	
	// $('#addCarLoanForm').find('#salesDeptId').val(loanDetailsVo.salesDept.id);
	 //$('#addCarLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
	//贷款类型默认显示为移交类，性别默认显示为男
 
	 
	 $('#addCarLoanForm').find('#productName').val(loanDetailsVo.loanType);
	 $('#addCarLoanForm').find('#productId').val(loanDetailsVo.productId);
	 $('#addCarLoanForm').find('#productTypeId').val(loanDetailsVo.productTypeId);
	 


}
//加载车贷老客户页面数据
function loadOldCarLoanData(loanDetailsVo){
	
		//产品期数
		$('#addCarLoanForm').find('#requestTime').combobox({
		    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
		    valueField:'term',
		    textField:'termName',
		    onLoadSuccess:function(){
	        	$('#addCarLoanForm').find('#requestTime').combobox('getData');
	        }
		 });
		
	    $('#addCarLoanForm').find('#carPersonIdnumName').text(loanDetailsVo.idnum);
	    $('#addCarLoanForm').find('#carPersonIdnum').val(loanDetailsVo.idnum);
		$('#addCarLoanForm').find('#productId').val(loanDetailsVo.productId);
		$('#addCarLoanForm').find('#productTypeId').val(loanDetailsVo.productTypeId);
	    $('#addCarLoanForm').find('#personId').val(loanDetailsVo.person.id);

	   $('#addCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	   $('#addCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	   $('#addCarLoanForm').find('#companyId').val(loanDetailsVo.companyId);
	   $('#addCarLoanForm').find('#vehicleId').val(loanDetailsVo.vehicleId);
	   $('#addCarLoanForm').find('#creditHistoryId').val(loanDetailsVo.creditHistoryId);
	 //贷款信息
	    //person
	    $('#addCarLoanForm').find('#carPersonName').val(loanDetailsVo.person.name);
	    $('#addCarLoanForm').find('#carPersonSex').combobox('select',loanDetailsVo.person.sex);
	   
	    $('#addCarLoanForm').find('#carPersonMarried').combobox('select',loanDetailsVo.person.married);
	   
	    $('#addCarLoanForm').find('#carPersonEducationLevel').combobox('select',loanDetailsVo.person.educationLevel);
	    $('#addCarLoanForm').find('#carPersonHasChildren').combobox('select',loanDetailsVo.person.hasChildren);
	    //子女数目
	    if(loanDetailsVo.person.hasChildren==0){
	    	 $('#addCarLoanForm').find('#childrenNum').removeAttr("disabled");
	    }	   
	    $('#addCarLoanForm').find('#childrenNum').val(loanDetailsVo.person.childrenNum);
	    $('#addCarLoanForm').find('#carPersonZipCode').val(loanDetailsVo.person.zipCode);
	    $('#addCarLoanForm').find('#carPersonAddress').val(loanDetailsVo.person.address);
	    $('#addCarLoanForm').find('#carPersonMobilePhone').val(loanDetailsVo.person.mobilePhone);
	    
	    $('#addCarLoanForm').find('#deptName').val(loanDetailsVo.person.deptName);
	    $('#addCarLoanForm').find('#job').val(loanDetailsVo.person.job);
	    $('#addCarLoanForm').find('#ext').val(loanDetailsVo.person.ext);
	    $('#addCarLoanForm').find('#workNature').val(loanDetailsVo.person.workNature);
	    $('#addCarLoanForm').find('#payDate').val(loanDetailsVo.person.payDate);
	    $('#addCarLoanForm').find('#otherIncome').val(loanDetailsVo.person.otherIncome);
	    
	    $('#addCarLoanForm').find('#carPersoneEmail').val(loanDetailsVo.person.email);
	    $('#addCarLoanForm').find('#carPersonHomePhone').val(loanDetailsVo.person.homePhone);

	     $('#addCarLoanForm').find('#professionType').combobox('select',loanDetailsVo.person.professionType);
	    if(loanDetailsVo.person.professionType=='自营'){//如果为私营企业 	
	    	  $(".enterpriseType1").css("display","table-row");
	       	  $(".enterpriseType2").css("display","table-row");
	    	  $('#addCarLoanForm').find('#privateEnterpriseType').combobox('select',loanDetailsVo.person.privateEnterpriseType);
	    	  $('#addCarLoanForm').find('#businessPlace').combobox('select',loanDetailsVo.person.businessPlace);
	    	  $('#addCarLoanForm').find('#totalEmployees').val(loanDetailsVo.person.totalEmployees);
	    	  $('#addCarLoanForm').find('#ratioOfInvestments').val(loanDetailsVo.person.ratioOfInvestments);
	    	  $('#addCarLoanForm').find('#monthOfProfit').val(loanDetailsVo.person.monthOfProfit);
	    	  $('#addCarLoanForm').find('#carFoundedDate').datebox('setValue',getYMD(loanDetailsVo.person.foundedDate || loanDetailsVo.person.carFoundedDate));
	    }else{
	    	$(".enterpriseType1").css("display","none");
	    	$(".enterpriseType2").css("display","none");
        }
	    
	    //户籍地址
	    $('#addCarLoanForm').find('#placeDomicile').val(loanDetailsVo.person.placeDomicile);
	    $('#addCarLoanForm').find('#houseEstateType').combobox('select',loanDetailsVo.person.houseEstateType);
	    $('#addCarLoanForm').find('#carRentPerMonth').val(loanDetailsVo.person.rentPerMonth);
	    if(loanDetailsVo.person.hasHouseLoan==0){
	        $('#addCarLoanForm').find('#hasHouseLoan').combobox('select','0');
	    }else{
	        $('#addCarLoanForm').find('#hasHouseLoan').combobox('select','1');
	    }
	    $('#addCarLoanForm').find('#houseEstateAddress').val(loanDetailsVo.person.houseEstateAddress);
	    $('#addCarLoanForm').find('#carIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
	    $('#addCarLoanForm').find('#liveType').combobox('select',loanDetailsVo.person.liveType);
	    //工作性质

	    $('#addCarLoanForm').find('#workNature').combobox('select',loanDetailsVo.person.workNature);
	    
	    //工作证明人
	    $('#addCarLoanForm').find('#witness').val(loanDetailsVo.person.witness);
	    $('#addCarLoanForm').find('#workThatDept').val(loanDetailsVo.person.workThatDept);
	    $('#addCarLoanForm').find('#workThatPosition').val(loanDetailsVo.person.workThatPosition);
	    $('#addCarLoanForm').find('#workThatTell').val(loanDetailsVo.person.workThatTell);

	    $('#addCarLoanForm').find('#ratioOfInvestments').val(loanDetailsVo.person.ratioOfInvestments);
	    $('#addCarLoanForm').find('#totalEmployees').val(loanDetailsVo.person.totalEmployees);
	    $('#addCarLoanForm').find('#monthOfProfit').val(loanDetailsVo.person.monthOfProfit);	


	    //车辆信息
	    if(loanDetailsVo.vehicle){
	    	$('#addCarLoanForm').find('#vehicleId').val(loanDetailsVo.vehicle.id);
	    	$('#addCarLoanForm').find('#brand').val(loanDetailsVo.vehicle.brand);
	  	    $('#addCarLoanForm').find('#model').val(loanDetailsVo.vehicle.model);
	  	    $('#addCarLoanForm').find('#coty').val(loanDetailsVo.vehicle.coty);
	  	    $('#addCarLoanForm').find('#plateNumber').val(loanDetailsVo.vehicle.plateNumber);
	  	    $('#addCarLoanForm').find('#frameNumber').val(loanDetailsVo.vehicle.frameNumber);
	  	    $('#addCarLoanForm').find('#mileage').val(loanDetailsVo.vehicle.mileage);
	    }
	    // 信贷历史    
	    // 信贷历史    
	    if(loanDetailsVo.creditHistory){
		    $('#addCarLoanForm').find('#creditHistoryId').val(loanDetailsVo.creditHistory.id);
		    $('#addCarLoanForm').find('#hasCreditCard').combobox('select',loanDetailsVo.creditHistory.hasCreditCard);
		    if(loanDetailsVo.creditHistory.hasCreditCard==1){
	    		$('#addCarLoanForm').find('#cardNum').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.cardNum));
	    		$('#addCarLoanForm').find('#totalAmount').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.totalAmount));
	    		$('#addCarLoanForm').find('#overdrawAmount').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.overdrawAmount));
		    }else{
		    	$('#addCarLoanForm').find('#cardNum').val("");
			 	$('#addCarLoanForm').find('#totalAmount').val("");
			 	$('#addCarLoanForm').find('#overdrawAmount').val("");		 	    
		    }
	    }
	    //	企业信息 $("#addCarLoanForm").form('load',loanDetailsVo.company);companyName
	    $('#addCarLoanForm').find('#carCompanyType').combobox('select',loanDetailsVo.person.companyType);
	    $('#addCarLoanForm').find('#carCompanyName').val(loanDetailsVo.company.name);
	    $('#addCarLoanForm').find('#carCompanyAddress').val(loanDetailsVo.company.address);
	    $('#addCarLoanForm').find('#householdZipCode').val(loanDetailsVo.person.householdZipCode);
	//    $('#addCarLoanForm').find('#customerSource').combobox('select',loanDetailsVo.loan.customerSource);
	    //其它
	    $('#addCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	    $('#addCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	    //$('#addCarLoanForm').find('#personNo').combobox('setValue',loanDetailsVo.crm.code);
	    $('#addCarLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
	    $('#addCarLoanForm').find('#bizDirectorId').val(loanDetailsVo.director.id);
	   // $('#addCarLoanForm').find('#salesDeptId').val(loanDetailsVo.salesDept.id);
	   // $('#addCarLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
	    $('#addCarLoanForm').find('#carIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
	    
	    //联系人
	    if(loanDetailsVo.contacterList){
	        for(var i=0;i<4;i++){       
		    	if(loanDetailsVo.contacterList[i]){
		    		$('#addCarLoanForm').find("#contacterId"+i).val(loanDetailsVo.contacterList[i].id);
		    		$('#addCarLoanForm').find("#contacterName"+i).val(loanDetailsVo.contacterList[i].name);
		    		$('#addCarLoanForm').find("#relationship"+i).val(loanDetailsVo.contacterList[i].relationship);
		    		$('#addCarLoanForm').find("#mobilePhone"+i).val(loanDetailsVo.contacterList[i].mobilePhone);
		    		$('#addCarLoanForm').find("#homePhone"+i).val(loanDetailsVo.contacterList[i].homePhone);
		    		$('#addCarLoanForm').find("#workUnit"+i).val(loanDetailsVo.contacterList[i].workUnit);
		    		$('#addCarLoanForm').find("#address"+i).val(loanDetailsVo.contacterList[i].address);
		    		$('#addCarLoanForm').find('#contacterHadKnown'+i).combobox('select',loanDetailsVo.contacterList[i].hadKnown);  
		    	}
		          
		    }
	    }
}
//加载车贷编辑页面数据
function loadEditCar1(loanDetailsVo){
	 
	//产品期数
		$('#editCarLoanForm').find('#requestTime').combobox({
		    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
		    valueField:'term',
		    textField:'termName',
		    onLoadSuccess:function(){
	        	$('#editCarLoanForm').find('#requestTime').combobox('getData');
	        }
		 });
	    $('#editCarLoanForm').find('#loanId').val(loanDetailsVo.loanId);
	    $('#editCarLoanForm').find('#carPersonIdnumName').text(loanDetailsVo.idnum);
	    $('#editCarLoanForm').find('#carPersonIdnum').val(loanDetailsVo.idnum);
	    $('#editCarLoanForm').find('#personId').val(loanDetailsVo.person.id);
	    $('#editCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	    $('#editCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	    $('#editCarLoanForm').find('#loanTypes').combobox('select',loanDetailsVo.loan.loanType); 
	  //贷款信息
	    $('#editCarLoanForm').find("#requestMoney").val(loanDetailsVo.loan.requestMoney);
	    $('#editCarLoanForm').find("#requestTime").combobox('select',loanDetailsVo.loan.requestTime);
	    $('#editCarLoanForm').find("#productId").val(loanDetailsVo.loan.productId);
	    //person
	    $('#editCarLoanForm').find('#carPersonName').val(loanDetailsVo.person.name);
	    $('#editCarLoanForm').find('#deptName').val(loanDetailsVo.person.deptName);
	  //  $("#dd").datebox("setValue", "2012-01-01");
	    $('#editCarLoanForm').find('#carRequestDate').datebox("setValue",getYMD(loanDetailsVo.loan.requestDate));
	    //其它
	    $('#editCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	    $('#editCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	    if(loanDetailsVo.loan.remark) {
		    $('#editCarLoanForm').find('#remark').val(loanDetailsVo.loan.remark);
		}
	    $('#editCarLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
	    $('#editCarLoanForm').find('#bizDirectorId').val(loanDetailsVo.director.id);
}  

//取消
function cancel() {
    $('#dlg').dialog('close');

};

//关闭页面
function cloae() {
    $('#alg').dialog('close');

};
//打开申请贷款页面
function addVehicle() {
    $('#newLoanForm').submit();
}
// 分配角色
function assignRoles() {
    parent.$('<div/>').dialog({
        href : 'assignRoles.html',
        width : 500,
        height : 370,
        modal : true,
        title : '分配角色',
        buttons : [{
            text : '分配',
            iconCls : 'icon-ok',
            handler : function() {
                var d = parent.$(this).closest('.window-body');
                d.dialog('destroy');
                parent.$.messager.show({
                    title : '提示',
                    msg : '分配成功！'
                });
            }
        },{
            text : '取消',
            iconCls : 'icon-cancel',
            handler : function() {
                var d = parent.$(this).closest('.window-body');
                d.dialog('destroy');
            }
        }],
        onClose : function() {
            parent.$(this).dialog('destroy');
        }
    });
};
//编辑页面职业类型的校验
function checkEditProfessionType(){
	//私营企业类型
	var privateEnterpriseType = $('#editCarLoanForm').find("#privateEnterpriseType").combobox('getValue');
	if(privateEnterpriseType==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择私营企业类型'
			});
		   return false;
	}
	//成立时间
	var carFoundedDate = $('#editCarLoanForm').find("#carFoundedDate").datebox('getValue');
	if(carFoundedDate==''){	
		 $.messager.show({
				title: 'warning',
				msg: '请选择成立时间'
			});
		   return false;
	}
	//经营场所
	var businessPlace = $('#editCarLoanForm').find("#businessPlace").combobox('getValue');
	if(businessPlace==''){	
		 $.messager.show({
				title: 'warning',
				msg: '请选择经营场所'
			});
		   return false;
	}
	//员工人数
	var totalEmployees = $('#editCarLoanForm').find("#totalEmployees").val();
	if(totalEmployees==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写员工人数'
			});
		   return false;
	}
	//占股比例
	var ratioOfInvestments = $('#editCarLoanForm').find("#ratioOfInvestments").val();
	if(ratioOfInvestments=='' ||ratioOfInvestments>100){
		 $.messager.show({
				title: 'warning',
				msg: '请填写占股比例,且小于100'
			});
		   return false;
	}
	//月净利润额
	var monthOfProfit = $('#editCarLoanForm').find("#monthOfProfit").val();
	if(monthOfProfit==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写月净利润额'
			});
		   return false;
	}
	
}
//新增页面职业类型的校验
function checkAddProfessionType(){
	//私营企业类型
	var privateEnterpriseType = $('#addCarLoanForm').find("#privateEnterpriseType").combobox('getValue');
	if(privateEnterpriseType==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择私营企业类型'
			});
		   return false;
	}
	//成立时间
	var carFoundedDate = $('#addCarLoanForm').find("#carFoundedDate").datebox('getValue');
	if(carFoundedDate==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择成立时间'
			});
		   return false;
	}
	//经营场所
	var businessPlace = $('#addCarLoanForm').find("#businessPlace").combobox('getValue');
	if(businessPlace==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择经营场所'
			});
		   return false;
	}
	//员工人数
	var totalEmployees = $('#addCarLoanForm').find("#totalEmployees").val();
	if(totalEmployees==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写员工人数'
			});
		   return false;
	}
	//占股比例
	var ratioOfInvestments = $('#addCarLoanForm').find("#ratioOfInvestments").val();
	if(ratioOfInvestments=='' || ratioOfInvestments >100){
		 $.messager.show({
				title: 'warning',
				msg: '请填写占股比例,且小于100'
			});
		   return false;
	}
	//月净利润额
	var monthOfProfit = $('#addCarLoanForm').find("#monthOfProfit").val();
	if(monthOfProfit==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写月净利润额'
			});
		   return false;
	}
	
}
//新增车贷
function addCarLoan(){
	// var carPersonHomePhone = $('#addCarLoanForm').find("#carPersonHomePhone").val();
	// var ext = $('#addCarLoanForm').find("#ext").val();
	
	// if(carPersonHomePhone=='' && ext==''){
	// 	$.messager.show({
	// 		title: 'warning',
	// 		msg: '住宅电话或固话分机至少填写一个'
	// 	});	
	// 	return false;
	// }
  

	var managerName=$('#addCarLoanForm').find("#managerName").combobox('getValue');
 
	var bizName=$('#addCarLoanForm').find("#bizName").combobox('getValue');
 
	//验证贷款类型
	if(managerName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户经理'
	    });	
		return false;
	}

	if(bizName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择业务主任'
	    });
		return false;
	}
	///
     
    // 验证FORM
    if(!$("#addCarLoanForm").form('validate')){
        return false;
    }else{
    	$('#addCarLoanBtnNew').linkbutton({disabled:true});
    	$('#cancelCarLoanBtn').linkbutton({disabled:true});
    }
    $.ajax({
        url : 'RefusalEntry/addCarLoan',
        data : $("#addCarLoanForm").serialize(),
        type:"POST",
        success : function(result){
        	$('#addCarLoanBtnNew').linkbutton({disabled:false});
        	$('#cancelCarLoanBtn').linkbutton({disabled:false});
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '保存成功！'
                });
                //发送数据到ECIF
              //清空车贷页面上的数据
           //    clearCarLoanDate();	
                clearCarLoanDate();
                $('#carLoanAdd').dialog('close');
                $('#loanPageTb').datagrid('reload');
            }else{
                parent.$.messager.show({
                    title: 'Error',
                    msg: result
                });
            }
        }
    });
}
//取消 编辑车贷
function cancelModifyCarLoan() {
//	clearCarLoanDate();
	 $('#carLoanModify').dialog('close');	

};
//取消新增车贷
function cancelCarLoan() {
	 $('#carLoanAdd').dialog('close');
};
//取消编辑车贷
function cancelCarLoanModify() {
	 $('#carLoanModify').dialog('close');
};
//编辑车贷
function editCarLoan(){
	var managerName=$('#editCarLoanForm').find("#managerName").combobox('getValue');
	var salesDeptId=$('#editCarLoanForm').find("#salesDeptId").combobox('getValue');
	
	var bizName=$('#editCarLoanForm').find("#bizNameEdit").combobox('getValue');
 
	//验证贷款类型
	if(managerName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户经理'
	    });	
		return false;
	}

	if(bizName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择业务主任'
	    });
		return false;
	}
	///
     
    // 验证FORM
    if(!$("#editCarLoanForm").form('validate')){
        return false;
    }else{
    	$('#updateCarLoanBtnNew').linkbutton({disabled:true});
    	$('#cancelCarLoanBtn').linkbutton({disabled:true});
    }
    $.ajax({
        url : 'RefusalEntry/modifyCarLoan',
        data : $("#editCarLoanForm").serialize(),
        type:"POST",
        success : function(result){
        	$('#updateCarLoanBtnNew').linkbutton({disabled:false});
        	$('#cancelCarLoanBtn').linkbutton({disabled:false});
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '保存成功！'
                });
                //发送数据到ECIF
              //清空车贷页面上的数据
           //    clearCarLoanDate();	
                clearCarLoanDate();
                $('#carLoanModify').dialog('close');
                $('#loanPageTb').datagrid('reload');
            }else{
                parent.$.messager.show({
                    title: 'Error',
                    msg: result
                });
            }
        }
    });
	 
   
   
}

