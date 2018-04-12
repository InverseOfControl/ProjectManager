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
function doCarLoan(loanId,isfusal){
	debugger;
	var flag='apply';
	 var url = 'apply/carLoanDetail';
	 var strData;
	            if(isfusal=="1"){
	            	   url = 'RefusalEntry/carLoanDetail';
	            	   strData = getCarLoanDetails1(loanId,flag);
	            }else{
	            	  strData = getCarLoanDetails(loanId,flag);
	            }
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();  
    
    //查看车贷对话框
   //alert('3');
    $('#carLoanDetail').dialog({
        title: '查看车贷',
        width: 1100,
        height:h*0.8,
        closed: false,  
        cache: false,
        href: url,
        modal: true,
        onLoad : function(){
        	
        	
        	//  $('#browseCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '　');
        	    if(loanDetails.product) {
        	    	  $('#browseCLForm #productName').text(loanDetails.product.productName);
        	    }
        	    if(loanDetails.loan) {
        	        $('#browseCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
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
        	    
        	  //加载新增加的6个参数
            	if(loanDetails.person) {
            		if(loanDetails.person.topEducation==1){
            			 $('#browseCLForm #topEducation').text('硕士及以上');
            		}else if(loanDetails.person.topEducation==2){
            			 $('#browseCLForm #topEducation').text('本科');
            		}else if(loanDetails.person.topEducation==3){
            			 $('#browseCLForm #topEducation').text('大专');
            		}else if(loanDetails.person.topEducation==4){
            			 $('#browseCLForm #topEducation').text('中专');
            		}else if(loanDetails.person.topEducation==5){
            			 $('#browseCLForm #topEducation').text('高中');
            		}else if(loanDetails.person.topEducation==6){
            			 $('#browseCLForm #topEducation').text('初中及以下');
            		}else{
            			$('#browseCLForm #topEducation').text('');
            		}
        	       
            		
            		if(loanDetails.person.homeCondition==1){
            			 $('#browseCLForm #homeCondition').text('还款中');
            		}else if(loanDetails.person.homeCondition==2){
            			 $('#browseCLForm #homeCondition').text('全款购');
            		}else if(loanDetails.person.homeCondition==3){
            			 $('#browseCLForm #homeCondition').text('已结清');
            		}else if(loanDetails.person.homeCondition==4){
            			 $('#browseCLForm #homeCondition').text('无');
            		}else{
            			$('#browseCLForm #homeCondition').text('');
            		}
        	       
            		
            		if(loanDetails.person.isHaveCarLoan==1){
            			 $('#browseCLForm #isHaveCarLoan').text('是');
            		}else if(loanDetails.person.isHaveCarLoan==2){
            			 $('#browseCLForm #isHaveCarLoan').text('否');
            		}else{
            			 $('#browseCLForm #isHaveCarLoan').text('');
            		}
        	       
        	        $('#browseCLForm #monthIncome').text(loanDetails.person.monthIncome);
        	        $('#browseCLForm #loanSize').text(loanDetails.person.loanSize);
        	        
        	        
        	        if(loanDetails.person.unitIndustryCategory==1){
           			 	$('#browseCLForm #unitIndustryCategory').text('农、林、牧、渔业');
        	        }else if(loanDetails.person.unitIndustryCategory==2){
        	        	$('#browseCLForm #unitIndustryCategory').text('能源、采矿业');
        	        }else if(loanDetails.person.unitIndustryCategory==3){
        	        	$('#browseCLForm #unitIndustryCategory').text('食品、药品、工业原料、服装、日用品等制造业');
        	        }else if(loanDetails.person.unitIndustryCategory==4){
        	        	$('#browseCLForm #unitIndustryCategory').text('电力、热力、燃气及水生产和供应业');
        	        }else if(loanDetails.person.unitIndustryCategory==5){
        	        	$('#browseCLForm #unitIndustryCategory').text('建筑业');
        	        }else if(loanDetails.person.unitIndustryCategory==6){
        	        	$('#browseCLForm #unitIndustryCategory').text('批发和零售业');
        	        }else if(loanDetails.person.unitIndustryCategory==7){
        	        	$('#browseCLForm #unitIndustryCategory').text('交通运输、仓储和邮政业');
        	        }else if(loanDetails.person.unitIndustryCategory==8){
        	        	$('#browseCLForm #unitIndustryCategory').text('住宿、旅游、餐饮业');
        	        }else if(loanDetails.person.unitIndustryCategory==9){
        	        	$('#browseCLForm #unitIndustryCategory').text('信息传输、软件和信息技术服务业');
        	        }else if(loanDetails.person.unitIndustryCategory==10){
        	        	$('#browseCLForm #unitIndustryCategory').text('金融业');
        	        }else if(loanDetails.person.unitIndustryCategory==11){
        	        	$('#browseCLForm #unitIndustryCategory').text('房地产业');
        	        }else if(loanDetails.person.unitIndustryCategory==12){
        	        	$('#browseCLForm #unitIndustryCategory').text('租凭和商务服务业');
        	        }else if(loanDetails.person.unitIndustryCategory==13){
        	        	$('#browseCLForm #unitIndustryCategory').text('科学研究、技术服务业');
        	        }else if(loanDetails.person.unitIndustryCategory==14){
        	        	$('#browseCLForm #unitIndustryCategory').text('水利、环境和公共设施管理业');
        	        }else if(loanDetails.person.unitIndustryCategory==15){
        	        	$('#browseCLForm #unitIndustryCategory').text('居民服务、修理和其他服务业');
        	        }else if(loanDetails.person.unitIndustryCategory==16){
        	        	$('#browseCLForm #unitIndustryCategory').text('教育、培训');
        	        }else if(loanDetails.person.unitIndustryCategory==17){
        	        	$('#browseCLForm #unitIndustryCategory').text('卫生、医疗、社会保障、社会福利');
        	        }else if(loanDetails.person.unitIndustryCategory==18){
        	        	$('#browseCLForm #unitIndustryCategory').text('文化、体育和娱乐业');
        	        }else if(loanDetails.person.unitIndustryCategory==19){
        	        	$('#browseCLForm #unitIndustryCategory').text('政府、非盈利机构和社会组织');
        	        }else if(loanDetails.person.unitIndustryCategory==20){
        	        	$('#browseCLForm #unitIndustryCategory').text('警察、消防、军人');
        	        }else if(loanDetails.person.unitIndustryCategory==21){
        	        	$('#browseCLForm #unitIndustryCategory').text('其他');
        	        }else{
        	        	$('#browseCLForm #unitIndustryCategory').text('');
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
        height:h*0.8,
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
        	        $('#browseExtensionCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
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
        url: "apply/toCarLoanDetail",
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
function getCarLoanDetails1(loanId,flag){
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
	//新增贷款类型
	$('#addCarLoanForm').find('#loanTypes').combobox({
		onSelect: function (record) {
	        var value = record.value;
	        //新增页面，移交类1，流通类2，
	        //产品配置页面，流通类1，移交类2，
	        //不知道那个大神做的。，为了不影响代码，做转换
	        if(value == 1){
	        	value = 2;
	        } else if(value == 2){
	        	value = 1;
	        }
	        var productId = loanDetailsVo.productId;
	        var url='apply/findListByVO';
	        var param ={};
	        param.productId=productId;
	        param.carProductType=value;
	        $.ajax({
	 		   url : url,
	 		   data : param,
//		 		   type:"POST",
	 		   async:false,  // 设置同步方式
	 	       cache:false,
	 		   success : function(result){ 
	 			   //产品期数
	 			   $('#addCarLoanForm').find('#requestTime').combobox({
	 				   data:result,
	 				   valueField:'term',
	 				   textField:'termName',
	 				   onLoadSuccess:function(){
	 					  var data = $('#addCarLoanForm').find('#requestTime').combobox('getData');
	 			        	if(data.length==1)
	 			        		$('#addCarLoanForm').find('#requestTime').combobox('select', data[0].term);
	 				   }
	 			   });
	 		   },
	 		   error:function(data){
	 		 		 $.messager.show({
	 						title: '提示',
	 						msg: data.responseText
	 					});
	 		   }
	        });	
	    }
	});
	$('#addCarLoanForm').find('#loanTypes').combobox("select","1");
	
	//产品期数
	/*$('#addCarLoanForm').find('#requestTime').combobox({
		url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
        	var data = $('#addCarLoanForm').find('#requestTime').combobox('getData');
        	if(data.length==1)
        		$('#addCarLoanForm').find('#requestTime').combobox('select', data[0].id);
        }
	 });*/
	
	$('#addCarLoanForm').find('#carPersonIdnumName').text(loanDetailsVo.idnum);
	 $('#carPersonIdnum').val(loanDetailsVo.idnum);
	    //其它
	 $('#addCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	 $('#addCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);	
	
	// $('#addCarLoanForm').find('#salesDeptId').val(loanDetailsVo.salesDept.id);
	 //$('#addCarLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
	//贷款类型默认显示为移交类，性别默认显示为男
	 $('#').find('#loanTypes').combobox('select','1');
	 $('#addCarLoanForm').find('#carPersonSex').combobox('select','1');
	 $('#addCarLoanForm').find('#productName').val(loanDetailsVo.loanType);
	 $('#addCarLoanForm').find('#productId').val(loanDetailsVo.productId);
	 $('#addCarLoanForm').find('#productTypeId').val(loanDetailsVo.productTypeId);
	 $('#addCarLoanForm').find("#relationship0").val('直系亲属');
	 $('#addCarLoanForm').find("#relationship1").val('现单位同事');
	 $('#addCarLoanForm').find("#relationship2").val('其他联系人');

}
//加载车贷老客户页面数据
function loadOldCarLoanData(loanDetailsVo){
	

				//加载新增加的五个参数
	            	if(loanDetailsVo.person) {
	            		
	        	       
	            		$('#addCarLoanForm #topEducation').combobox('setValue',loanDetailsVo.person.topEducation);
	            		 $('#addCarLoanForm #homeCondition').combobox('setValue',loanDetailsVo.person.homeCondition);
	            		 $('#addCarLoanForm #isHaveCarLoan').combobox('setValue',loanDetailsVo.person.isHaveCarLoan);
	        	        $('#addCarLoanForm #monthIncome').val(loanDetailsVo.person.monthIncome);
	        	        $('#addCarLoanForm #loanSize').val(loanDetailsVo.person.loanSize);
	        	        $('#addCarLoanForm #unitIndustryCategory').combobox('setValue',loanDetailsVo.person.unitIndustryCategory);
	        	    }
	            	
	            	
	            	
	            	
	 //新增贷款类型
	$('#addCarLoanForm').find('#loanTypes').combobox({
		onSelect: function (record) {
	        var value = record.value;
	        //新增页面，移交类1，流通类2，
	        //产品配置页面，流通类1，移交类2，
	        //不知道那个大神做的。，为了不影响代码，做转换
	        if(value == 1){
	        	value = 2;
	        } else if(value == 2){
	        	value = 1;
	        }
	        var productId = loanDetailsVo.productId;
	        var url='apply/findListByVO';
	        var param ={};
	        param.productId=productId;
	        param.carProductType=value;
	        $.ajax({
	 		   url : url,
	 		   data : param,
//		 		   type:"POST",
	 		   async:false,  // 设置同步方式
	 	       cache:false,
	 		   success : function(result){ 
	 			   //产品期数
	 			   $('#addCarLoanForm').find('#requestTime').combobox({
	 				   data:result,
	 				   valueField:'term',
	 				   textField:'termName',
	 				   onLoadSuccess:function(){
	 					  $('#addCarLoanForm').find('#requestTime').combobox('getData');
	 				   }
	 			   });
	 		   },
	 		   error:function(data){
	 		 		 $.messager.show({
	 						title: '提示',
	 						msg: data.responseText
	 					});
	 		   }
	        });	
	    }
	});
	$('#addCarLoanForm').find('#loanTypes').combobox("select","1");
		//产品期数
		/*$('#addCarLoanForm').find('#requestTime').combobox({
		    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
		    valueField:'term',
		    textField:'termName',
		    onLoadSuccess:function(){
	        	$('#addCarLoanForm').find('#requestTime').combobox('getData');
	        }
		 });*/
		
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
function loadEditCar(loanDetailsVo){
	 //贷款类型
	$('#editCarLoanForm').find('#loanTypes').combobox({
		onSelect: function (record) {
	        var value = record.value;
	        //新增页面，移交类1，流通类2，
	        //产品配置页面，流通类1，移交类2，
	        //不知道那个大神做的。，为了不影响代码，做转换
	        if(value == 1){
	        	value = 2;
	        } else if(value == 2){
	        	value = 1;
	        }
	        var productId = loanDetailsVo.productId;
	        var url='apply/findListByVO';
	        var param ={};
	        param.productId=productId;
	        param.carProductType=value;
	        $.ajax({
	 		   url : url,
	 		   data : param,
//		 		   type:"POST",
	 		   async:false,  // 设置同步方式
	 	       cache:false,
	 		   success : function(result){ 
	 			   //产品期数
	 			   $('#editCarLoanForm').find('#requestTime').combobox({
	 				   data:result,
	 				   valueField:'term',
	 				   textField:'termName',
	 				   onLoadSuccess:function(){
	 					  $('#editCarLoanForm').find('#requestTime').combobox('getData');
	 				   }
	 			   });
	 		   },
	 		   error:function(data){
	 		 		 $.messager.show({
	 						title: '提示',
	 						msg: data.responseText
	 					});
	 		   }
	        });	
	    }
	});
	//产品期数
		/*$('#editCarLoanForm').find('#requestTime').combobox({
		    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
		    valueField:'term',
		    textField:'termName',
		    onLoadSuccess:function(){
	        	$('#editCarLoanForm').find('#requestTime').combobox('getData');
	        }
		 });*/
	    $('#editCarLoanForm').find('#loanId').val(loanDetailsVo.loanId);
	    $('#editCarLoanForm').find('#carPersonIdnumName').text(loanDetailsVo.idnum);
	    $('#editCarLoanForm').find('#carPersonIdnum').val(loanDetailsVo.idnum);
	 
	    $('#editCarLoanForm').find('#personId').val(loanDetailsVo.person.id);

	    $('#editCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	    $('#editCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	    $('#editCarLoanForm').find('#loanTypes').combobox('select',loanDetailsVo.loan.loanType); 
	    $('#editCarLoanForm').find('#companyId').val(loanDetailsVo.companyId);
	    $('#editCarLoanForm').find('#vehicleId').val(loanDetailsVo.vehicleId);
	    $('#editCarLoanForm').find('#creditHistoryId').val(loanDetailsVo.creditHistoryId);
	  //贷款信息
	    $('#editCarLoanForm').find("#requestMoney").val(loanDetailsVo.loan.requestMoney);
	    $('#editCarLoanForm').find("#requestTime").combobox('select',loanDetailsVo.loan.requestTime);
	    $('#editCarLoanForm').find("#purpose").combobox('select',loanDetailsVo.loan.purpose);
	    $('#editCarLoanForm').find("#productId").val(loanDetailsVo.loan.productId);
	    //person
	    $('#editCarLoanForm').find('#carPersonName').val(loanDetailsVo.person.name);
	    $('#editCarLoanForm').find('#carPersonSex').combobox('select',loanDetailsVo.person.sex);
	   
	    $('#editCarLoanForm').find('#carPersonMarried').combobox('select',loanDetailsVo.person.married);
	    $('#editCarLoanForm').find('#carPersonEducationLevel').combobox('select',loanDetailsVo.person.educationLevel);
	    $('#editCarLoanForm').find('#carPersonHasChildren').combobox('select',loanDetailsVo.person.hasChildren);
	   if(loanDetailsVo.person.hasChildren==1){
		   $('#editCarLoanForm').find('#childrenSchool').val(loanDetailsVo.person.childrenSchool);
	   	}
	    $('#editCarLoanForm').find('#carPersonZipCode').val(loanDetailsVo.person.zipCode);
	    $('#editCarLoanForm').find('#carPersonAddress').val(loanDetailsVo.person.address);
	    $('#editCarLoanForm').find('#carPersonMobilePhone').val(loanDetailsVo.person.mobilePhone);
	    
	    $('#editCarLoanForm').find('#deptName').val(loanDetailsVo.person.deptName);
	    $('#editCarLoanForm').find('#job').val(loanDetailsVo.person.job);
	    $('#editCarLoanForm').find('#ext').val(loanDetailsVo.person.ext);
	    $('#editCarLoanForm').find('#payDate').val(loanDetailsVo.person.payDate);
	    $('#editCarLoanForm').find('#otherIncome').val(loanDetailsVo.person.otherIncome);
	    $('#editCarLoanForm').find('#carPersoneEmail').val(loanDetailsVo.person.email);
	    $('#editCarLoanForm').find('#carPersonHomePhone').val(loanDetailsVo.person.homePhone);
	    //户籍地址
	    $('#editCarLoanForm').find('#placeDomicile').val(loanDetailsVo.person.placeDomicile);
	    $('#editCarLoanForm').find('#houseEstateType').combobox('select',loanDetailsVo.person.houseEstateType);
	    $('#editCarLoanForm').find('#carRentPerMonth').val(loanDetailsVo.person.rentPerMonth);
	    $('#editCarLoanForm').find('#maxRepayAmount').val(transferUndefined(loanDetailsVo.person.maxRepayAmount));//可接受的最高月还款额
	    if(loanDetailsVo.person.hasHouseLoan==0){
	        $('#editCarLoanForm').find('#hasHouseLoan').combobox('select','0');
	    }else{
	        $('#editCarLoanForm').find('#hasHouseLoan').combobox('select','1');
	    }
	    $('#editCarLoanForm').find('#houseEstateAddress').val(loanDetailsVo.person.houseEstateAddress);
	    $('#editCarLoanForm').find('#carIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
	    $('#editCarLoanForm').find('#liveType').combobox('select',loanDetailsVo.person.liveType);
	    
	    
	    //工作证明人
	    $('#editCarLoanForm').find('#witness').val(loanDetailsVo.person.witness);
	    $('#editCarLoanForm').find('#workThatDept').val(loanDetailsVo.person.workThatDept);
	    $('#editCarLoanForm').find('#workThatPosition').val(loanDetailsVo.person.workThatPosition);
	    $('#editCarLoanForm').find('#workThatTell').val(loanDetailsVo.person.workThatTell);
	    //车辆信息
	    if(loanDetailsVo.vehicle){
	   	$('#editCarLoanForm').find('#vehicleId').val(loanDetailsVo.vehicle.id);
	    $('#editCarLoanForm').find('#brand').val(loanDetailsVo.vehicle.brand);
	    $('#editCarLoanForm').find('#model').val(loanDetailsVo.vehicle.model);
	    $('#editCarLoanForm').find('#coty').val(loanDetailsVo.vehicle.coty);
	    $('#editCarLoanForm').find('#plateNumber').val(loanDetailsVo.vehicle.plateNumber);
	    $('#editCarLoanForm').find('#frameNumber').val(loanDetailsVo.vehicle.frameNumber);
	    $('#editCarLoanForm').find('#mileage').val(loanDetailsVo.vehicle.mileage);
	    }
	    // 信贷历史    
	    if(loanDetailsVo.creditHistory){
		    $('#editCarLoanForm').find('#creditHistoryId').val(loanDetailsVo.creditHistory.id);
	    	 $('#editCarLoanForm').find('#hasCreditCard').combobox('select',loanDetailsVo.creditHistory.hasCreditCard);
	 	    if(loanDetailsVo.creditHistory.hasCreditCard==1){
 	    		$('#editCarLoanForm').find('#cardNum').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.cardNum));
 	    		$('#editCarLoanForm').find('#totalAmount').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.totalAmount));
 	    		$('#editCarLoanForm').find('#overdrawAmount').val(transferUndefinedAndZero(loanDetailsVo.creditHistory.overdrawAmount));
	 	    }else{
	 	    	 $('#editCarLoanForm').find('#cardNum').val("");
	 		 	 $('#editCarLoanForm').find('#totalAmount').val("");
	 		 	 $('#editCarLoanForm').find('#overdrawAmount').val("");		 	    
	 	    }
	    }
	    //	企业信息 $("#editCarLoanForm").form('load',loanDetailsVo.company);companyName
	    $('#editCarLoanForm').find('#carCompanyType').combobox('select',loanDetailsVo.person.companyType);
	    $('#editCarLoanForm').find('#professionType').combobox('select',loanDetailsVo.person.professionType);
	    if(loanDetailsVo.person.professionType=='自营'){//如果为私营企业 	
	    	  $(".enterpriseType3").css("display","table-row");
	       	  $(".enterpriseType4").css("display","table-row");
	    	  $('#editCarLoanForm').find('#privateEnterpriseType').combobox('select',loanDetailsVo.person.privateEnterpriseType);	    	  
	    	  $('#editCarLoanForm').find('#carFoundedDate').datebox('setValue',getYMD(loanDetailsVo.person.foundedDate));
	    	  $('#editCarLoanForm').find('#businessPlace').combobox('select',loanDetailsVo.person.businessPlace);
	    	  $('#editCarLoanForm').find('#totalEmployees').val(loanDetailsVo.person.totalEmployees);
	    	  $('#editCarLoanForm').find('#ratioOfInvestments').val(loanDetailsVo.person.ratioOfInvestments);
	    	  $('#editCarLoanForm').find('#monthOfProfit').val(loanDetailsVo.person.monthOfProfit);	    	  
	    }else{
	    	$(".enterpriseType3").css("display","none");
	    	$(".enterpriseType4").css("display","none");
        }
	    $('#editCarLoanForm').find('#carCompanyName').val(loanDetailsVo.company.name);
	    $('#editCarLoanForm').find('#carCompanyAddress').val(loanDetailsVo.company.address);
	    $('#editCarLoanForm').find('#householdZipCode').val(loanDetailsVo.person.householdZipCode);
	    $('#editCarLoanForm').find('#customerSource').combobox('select',loanDetailsVo.loan.customerSource);
	    $('#editCarLoanForm').find('#carRequestDate').val(getYMD(loanDetailsVo.loan.requestDate));
	    //其它
	    $('#editCarLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
	    $('#editCarLoanForm').find('#customerName').text(loanDetailsVo.service.name);
	    if(loanDetailsVo.userId==loanDetailsVo.assessor.id){//如果当前用户是复核人员
	    	// $('#editCarLoanForm').find("#assessorName").combobox({disabled:true});	    
	    	 $('#editCarLoanForm').find('#assessorName').combobox('setValue',loanDetailsVo.assessor.name);
	    }else{
	    	$('#editCarLoanForm').find('#assessorName').combobox('select',loanDetailsVo.assessor.id);
	    }
	    if(loanDetailsVo.loan.remark) {
		    $('#editCarLoanForm').find('#remark').val(loanDetailsVo.loan.remark);
		}
	    $('#editCarLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
	    $('#editCarLoanForm').find('#carIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
	    //联系人
	    for(var i=0;i<4;i++){    
	    	if(loanDetailsVo.contacterList[i]){
	    		$('#editCarLoanForm').find("#contacterId"+i).val(loanDetailsVo.contacterList[i].id);
	    		$('#editCarLoanForm').find("#contacterName"+i).val(loanDetailsVo.contacterList[i].name);
	    		$('#editCarLoanForm').find("#relationship"+i).val(loanDetailsVo.contacterList[i].relationship);
	    		$('#editCarLoanForm').find("#mobilePhone"+i).val(loanDetailsVo.contacterList[i].mobilePhone);
	    		$('#editCarLoanForm').find("#homePhone"+i).val(loanDetailsVo.contacterList[i].homePhone);
	    		$('#editCarLoanForm').find("#workUnit"+i).val(loanDetailsVo.contacterList[i].workUnit);
	    		$('#editCarLoanForm').find("#address"+i).val(loanDetailsVo.contacterList[i].address);
	    		$('#editCarLoanForm').find('#contacterHadKnown'+i).combobox('select',loanDetailsVo.contacterList[i].hadKnown);  
	    	}
	          
	    }
	    
	    //填充新增的两个备份数据（姓名,手机）
	    $('#carPersonNameBackups').val(loanDetailsVo.person.name);
	    $('#carPersonMobilePhoneBackups').val(loanDetailsVo.person.mobilePhone);
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
	var topEducation=$('#topEducation').combobox('getValue');
	if(topEducation==null||topEducation==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择最高学历'
			});
		   return false;
		
	}
	var homeCondition=$('#homeCondition').combobox('getValue');
	if(homeCondition==null||homeCondition==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择房贷情况'
			});
		   return false;
		
	}
	var monthIncome=$('#monthIncome').val();
	if(monthIncome==null||monthIncome==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写月收入'
			});
		   return false;
		
	}
	var isHaveCarLoan=$('#isHaveCarLoan').combobox('getValue');
	if(isHaveCarLoan==null||isHaveCarLoan==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择是否有车贷'
			});
		   return false;
		
	}
	var loanSize=$('#loanSize').val();
	if(loanSize==null||loanSize==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写贷款笔数'
			});
		   return false;
		
	}
	
	var unitIndustryCategory=$('#unitIndustryCategory').combobox('getValue');
	if(unitIndustryCategory==null||unitIndustryCategory==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择单位行业类别'
			});
		   return false;
		
	}
	// var carPersonHomePhone = $('#addCarLoanForm').find("#carPersonHomePhone").val();
	// var ext = $('#addCarLoanForm').find("#ext").val();
	
	// if(carPersonHomePhone=='' && ext==''){
	// 	$.messager.show({
	// 		title: 'warning',
	// 		msg: '住宅电话或固话分机至少填写一个'
	// 	});	
	// 	return false;
	// }
	
	 /*var customerSource=$('#addCarLoanForm').find("#customerSource").combobox('getValue');
		if(customerSource==''){
			 $.messager.show({
					title: 'warning',
					msg: '请选择客户来源'
				});
			   return false;
		}*/
	var loanTypes=$('#addCarLoanForm').find("#loanTypes").combobox('getValue');
	if(loanTypes==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择贷款类型'
			});
		   return false;
		
	}

	var requestTime=$('#addCarLoanForm').find("#requestTime").combobox('getValue');
	//var purpose=$('#addCarLoanForm').find("#purpose").combobox('getValue');	
	var carPersonSex=$('#addCarLoanForm').find("#carPersonSex").combobox('getValue');
	var managerName=$('#addCarLoanForm').find("#managerName").combobox('getValue');
	//var carPersonEducationLevel=$('#addCarLoanForm').find("#carPersonEducationLevel").combobox('getValue');
	var carPersonMarried=$('#addCarLoanForm').find("#carPersonMarried").combobox('getValue');
	var carPersonHasChildren=$('#addCarLoanForm').find("#carPersonHasChildren").combobox('getValue');
	var carCompanyType=$('#addCarLoanForm').find("#carCompanyType").combobox('getValue');
	var hasCreditCard=$('#addCarLoanForm').find("#hasCreditCard").combobox('getValue');
	//var professionType=$('#addCarLoanForm').find("#professionType").combobox('getValue');

	var assessorName=$('#addCarLoanForm').find("#assessorName").combobox('getValue');
	//验证职业类型
	/*if(professionType==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择职业类型'
		});
	     return false;

	}else if(professionType=='自营'){
		//新增页面职业类型的校验
		if (checkAddProfessionType() == false)
			return false;
	}*/
	//验证贷款类型
	if(loanTypes==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择贷款类型'
		});
	     return false;

	}
	//验证婚姻状况
	if(carPersonMarried==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择婚姻状态'
		});	
		return false;
	}
	if(requestTime==''){		
		 $.messager.show({
				title: 'warning',
				msg: '请选择申请期限'
		});	
		return false;
	}
//	if(purpose==''){	
//		 $.messager.show({
//				title: 'warning',
//				msg: '请选择借款用途'
//		});
//		return false;
//	}	
	if(carPersonSex==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择性别'
		});		
		return false;
	}
	/*if(carPersonEducationLevel==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择最高学历'
		});	
		return false;
	}*/
	if(carPersonHasChildren==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择有无子女'
	    });	
		return false;
	}	
	if(carCompanyType==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择单位性质'
	    });	
		return false;
	}
	if(hasCreditCard==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择是否持有信用卡'
	    });	
		return false;
	}
	/*if(customerSource==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户来源'
	    });	
		return false;
	}*/
	if(managerName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户经理'
	    });	
		return false;
	}

	if(assessorName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择复核人员'
	    });
		return false;
	}
	///
     if($("#addCarLoanForm").find("#requestMoney").val()<1000){
    	 $.messager.show({
 			title: 'warning',
 			msg: '申请金额需大于等于1000'
 	    });    	 
    	 return false;
     }
     if($("#addCarLoanForm").find("#requestMoney").val() % 1000 != 0){
    	 $.messager.show({
  			title: 'warning',
  			msg: '申请金额需大于等于1000，且为1000的倍数'
  	    }); 
    	 return false;
     }
    // 验证FORM
    if(!$("#addCarLoanForm").form('validate')){
        return false;
    }else{
    	$('#addCarLoanBtnNew').linkbutton({disabled:true});
    	$('#cancelCarLoanBtn').linkbutton({disabled:true});
    }
    $.ajax({
        url : 'apply/addCarLoan',
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
                    ecifTrans($("#addCarLoanForm").serialize());
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
	
	
	var topEducation=$('#topEducation').combobox('getValue');
	if(topEducation==null||topEducation==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择最高学历'
			});
		   return false;
		
	}
	var homeCondition=$('#homeCondition').combobox('getValue');
	if(homeCondition==null||homeCondition==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择房贷情况'
			});
		   return false;
		
	}
	var monthIncome=$('#monthIncome').val();
	if(monthIncome==null||monthIncome==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写月收入'
			});
		   return false;
		
	}
	var isHaveCarLoan=$('#isHaveCarLoan').combobox('getValue');
	if(isHaveCarLoan==null||isHaveCarLoan==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择是否有车贷'
			});
		   return false;
		
	}
	var loanSize=$('#loanSize').val();
	if(loanSize==null||loanSize==''){
		 $.messager.show({
				title: 'warning',
				msg: '请填写贷款笔数'
			});
		   return false;
		
	}
	
	var unitIndustryCategory=$('#unitIndustryCategory').combobox('getValue');
	if(unitIndustryCategory==null||unitIndustryCategory==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择单位行业类别'
			});
		   return false;
		
	}
	
	// var carPersonHomePhone = $('#editCarLoanForm').find("#carPersonHomePhone").val();
	// var ext = $('#editCarLoanForm').find("#ext").val();

	// if(carPersonHomePhone=='' && ext==''){
	// 	$.messager.show({
	// 		title: 'warning',
	// 		msg: '住宅电话或固话分机至少填写一个'
	// 	});	
	// 	return false;
	// }
	
	 /*var customerSource=$('#editCarLoanForm').find("#customerSource").combobox('getValue');
		if(customerSource==''){
			 $.messager.show({
					title: 'warning',
					msg: '请选择客户来源'
				});
			   return false;
		}*/
	var loanTypes=$('#editCarLoanForm').find("#loanTypes").combobox('getValue');
	if(loanTypes==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择贷款类型'
			});
		   return false;
		
	}
	 var carRequestDate=$('#editCarLoanForm').find("#carRequestDate").val();
	 if(carRequestDate==''){
		   $.messager.show({
				title: 'warning',
				msg: '请选择申请日期'
			});
		   return false;
	 } 
	var requestTime=$('#editCarLoanForm').find("#requestTime").combobox('getValue');
	//var purpose=$('#editCarLoanForm').find("#purpose").combobox('getValue');	
	var carPersonSex=$('#editCarLoanForm').find("#carPersonSex").combobox('getValue');
	var managerName=$('#editCarLoanForm').find("#managerName").combobox('getValue');
	//var carPersonEducationLevel=$('#editCarLoanForm').find("#carPersonEducationLevel").combobox('getValue');
	var carPersonMarried=$('#editCarLoanForm').find("#carPersonMarried").combobox('getValue');
	var carPersonHasChildren=$('#editCarLoanForm').find("#carPersonHasChildren").combobox('getValue');
	var carCompanyType=$('#editCarLoanForm').find("#carCompanyType").combobox('getValue');
	var assessorName=$('#editCarLoanForm').find("#assessorName").combobox('getValue');
	var serviceId=$('#editCarLoanForm').find("#serviceId").val();
	//var professionType=$('#editCarLoanForm').find("#professionType").combobox('getValue');
	//验证职业类型
	/*if(professionType==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择职业类型'
		});
	     return false;

	}else if(professionType=='自营'){
		//编辑页面职业类型验证
		if (checkEditProfessionType() == false)
			return false;
	}*/
	//验证婚姻状况
	if(loanTypes==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择贷款类型'
		});
	     return false;

	}
	//验证婚姻状况
	if(carPersonMarried==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择婚姻状态'
		});	
		return false;
	}
	if(requestTime==''){		
		 $.messager.show({
				title: 'warning',
				msg: '请选择申请期限'
		});	
		return false;
	}
/*	if(purpose==''){	
		 $.messager.show({
				title: 'warning',
				msg: '请选择借款用途'
		});
		return false;
	}	*/
	if(carPersonSex==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择性别'
		});		
		return false;
	}
	/*if(carPersonEducationLevel==''){
		 $.messager.show({
				title: 'warning',
				msg: '请选择最高学历'
		});	
		return false;
	}*/
	if(carPersonHasChildren==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择有无子女'
	    });	
		return false;
	}	
	if(carCompanyType==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择单位性质'
	    });	
		return false;
	}
	/*if(customerSource==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户来源'
	    });	
		return false;
	}*/
	if(managerName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择客户经理'
	    });	
		return false;
	}

	if(assessorName==''){
		$.messager.show({
			title: 'warning',
			msg: '请选择复核人员'
	    });
		return false;
	}
	/*if(assessorName==serviceId){
		$.messager.show({
			title: 'warning',
			msg: '复核人员和客服不可以是同一人'
	    });
		return false;
	}*/
	///
     if($("#editCarLoanForm").find("#requestMoney").val()<1000){
    	 $.messager.show({
 			title: 'warning',
 			msg: '申请金额应在1000元以上'
 	    });    	 
    	 return false;
     }
     if($("#editCarLoanForm").find("#requestMoney").val() % 1000 != 0){
    	 $.messager.show({
  			title: 'warning',
  			msg: '申请金额需大于等于1000，且为1000的倍数'
  	    }); 
    	 return false;
     }
     var assessorName=$('#editCarLoanForm').find("#assessorName").combobox('getValue');	
 	if(isNaN(assessorName)){	
 		$('#editCarLoanForm').find("#assessorId").val(null);
 		$('#editCarLoanForm').find("#assessorName").combobox('setValue',null);
 	}else{
 		 // 验证FORM
 	    if(!$("#editCarLoanForm").form('validate')){
 	        return false;
 	    }
 	}
 	//验证姓名手机，和备份的姓名和手机是否一致，（看是否被修改过）
 	var oldName=$('#carPersonNameBackups').val();
 	var newName=$('#carPersonName').val();
 	var oldPhone=$('#carPersonMobilePhoneBackups').val();
 	var newPhone=$('#carPersonMobilePhone').val();
 	if(oldName==newName&&oldPhone==newPhone){//没有变化
 		$('#isModify').val(0);
 	}else{//变化了
 		$('#isModify').val(1);
 	}
 	
    $.ajax({
        url : 'apply/modifyCarLoan',
        data : $("#editCarLoanForm").serialize(),
        type:"POST",
        success : function(result){
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '保存成功！'
                });
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


function loanNewEditCar(loanDetailsVo){
	$('#editCarLoanForm').find('#topEducation').combobox('select',loanDetailsVo.person.topEducation);
	$('#editCarLoanForm').find('#homeCondition').combobox('select',loanDetailsVo.person.homeCondition);
	$('#editCarLoanForm').find('#monthIncome').val(loanDetailsVo.person.monthIncome);
	$('#editCarLoanForm').find('#isHaveCarLoan').combobox('select',loanDetailsVo.person.isHaveCarLoan);
	$('#editCarLoanForm').find('#loanSize').val(loanDetailsVo.person.loanSize);
	$('#editCarLoanForm').find('#unitIndustryCategory').combobox('select',loanDetailsVo.person.unitIndustryCategory);
}
