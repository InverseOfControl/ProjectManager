var _step=1;
$(function() { 
    if($('#addSeLoanForm').length > 0){
        $('#addSeLoanForm').seLoanApply();
        
    }else if($('#editLoanForm').length > 0){
        $('#editLoanForm').seLoanApply();
    }   
    
   
     
});//initEnd
;var seLoanApply_plugin = function ($) {
    var dataTag = 'seLoanApply';
    function bindSeptBtn(){     
              //新增页面上一步 小企业贷
        $("#prevStepBtnNew").click(function(){      
             $("#addTableNew"+ _step).hide();
            _step -= 1;
            $("#addTableNew"+ _step).show();
            $("#nextStepBtnNew").show();
            if(_step <= 1){
                $(this).hide();
            }else{
                $(this).show();
            }
            if(_step >= 3){
                $("#submitEditBtnNew").show();
            }else{
                $("#submitEditBtnNew").hide();
            }
        });
        //新增页面下一步 小企业贷
       $("#nextStepBtnNew").click(function(){    
            $("#addTableNew"+ _step).hide();      
              // 验证table
            if(!$("#addTableNew"+ _step).form('validate')){
                $("#addTableNew"+ _step).show();       
                return false;
            }  
            _step += 1;
            $("#addTableNew"+ _step).show();
            $("#addTableNew"+ _step).css("text-align","left");
            $("#prevStepBtnNew").show();
            if(_step >= 3){
                $(this).hide();
                $("#submitEditBtnNew").show();
            }else{
                $(this).show();
                $("#submitEditBtnNew").hide();
            }
        });   
        //修改页面上一步 小企业贷
        $("#prevStepBtn").click(function(){
            $("#addTable"+ _step).hide();
            _step -= 1;
            $("#addTable"+ _step).show();
            $("#nextStepBtn").show();
            if(_step <= 1){
                $(this).hide();
            }else{
                $(this).show();
            }
            if(_step >= 3){
                $("#submitEditBtn").show();
            }else{
                $("#submitEditBtn").hide();
            }
        });
        //修改页面下一步 小企业贷
        $("#nextStepBtn").click(function(){
             $("#addTable"+ _step).hide();      
              // 验证table
            if(!$("#addTable"+ _step).form('validate')){
                 $("#addTable"+ _step).show();       
                return false;
            }  
            _step += 1;
            $("#addTable"+ _step).show();
            $("#addTable"+ _step).css("text-align","left");
            $("#prevStepBtn").show();
            if(_step >= 3){
                $(this).hide();
                $("#submitEditBtn").show();
            }else{
                $(this).show();
                $("#submitEditBtn").hide();
            }
        }); 
    }
    function renderPersonForm (self) {
         //小企业贷选择房产类型后租金显示隐藏
        $(self).find('#houseEstateType').combobox({
            onSelect: function () {
                var value = $(self).find('#houseEstateType').combobox('getValue');
               if(value=="租用" || value=="租赁"){//如果为租用，可以填写每月租金
                    $(self).find('#rentPerMonth').removeAttr("disabled");              
                }else{
                    $(self).find('#rentPerMonth').val("");
                    $(self).find('#rentPerMonth').attr("disabled","true");
                }      //如果不是租用或者亲戚住房，需选择有无房贷     
                if(value=="租用"||value=="亲戚住房"){
                    $(self).find('#hasHouseLoan').combobox({  
                         disabled:true  
                     });  
                }else{
                    $(self).find('#hasHouseLoan').combobox({  
                        disabled:false  
                    }); 
                }
            }
        }); 
         // 勾选同住宅地址后，地址文本框自动填充
        $(self).find("#checkAddress").bind("click", function () {
            
            if($(self).find("#checkAddress").attr("checked")=="checked"){ 
            //  $('input[name=houseEstateAddress]').attr("readonly","readonly")
                $(self).find("#houseEstateAddress").attr("readonly","true");
                
                $(self).find("#houseEstateAddress").val($(self).find("#personAddress").val());
            }else{
                $(self).find("#houseEstateAddress").removeAttr("readonly");
            }

        });


    }

    function renderGuaForm (self) {

        //新增页面 选择担保人类型
        for (var i = 0; i < 4; i++) {

            $(self).find("#guaranteeCheck"+i).bind("click", function () {
                var i = $(this).attr('id').charAt($(this).attr('id').length-1);
                if( $(this).attr("checked")=="checked"){  
                        $(self).find("#guaranteeFlag"+i).val(1);
                }else{
                     $(self).find("#guaranteeFlag"+i).val(null);
                }
            });

            $(self).find("#guaType"+i).combobox({
                onSelect: function () {
                    var i = $(this).attr('id').charAt($(this).attr('id').length-1);
                    if($(this).combobox("getValue")==0){ //自然人
                        $('#addNametr'+i).show();  
                        $('#addSextr'+i).show(); 
                        $('#addEdutr'+i).show();  
                        $('#addChildtr'+i).show(); 
                        $('#addMobiletr'+i).show();  
                        $('#addCompantr'+i).show(); 
                        $('#addtr'+i).hide();  
                        $('#addt_r'+i).hide(); 

                        $('#nametr'+i).show();  
                        $('#sextr'+i).show(); 
                        $('#edutr'+i).show();  
                        $('#childtr'+i).show(); 
                        $('#mobiletr'+i).show(); 
                        $('#compantr'+i).show();
                        $('#tr'+i).hide();  
                        $('#t_r'+i).hide();                
                    }else if($(this).combobox("getValue")==1){//法人
                        $('#addNametr'+i).hide();  
                        $('#addSextr'+i).hide(); 
                        $('#addEdutr'+i).hide();  
                        $('#addChildtr'+i).hide(); 
                        $('#addMobiletr'+i).hide();  
                        $('#addCompantr'+i).hide(); 
                        $('#addtr'+i).show();  
                        $('#addt_r'+i).show(); 

                        $('#nametr'+i).hide();  
                        $('#sextr'+i).hide(); 
                        $('#edutr'+i).hide();  
                        $('#childtr'+i).hide(); 
                        $('#mobiletr'+i).hide();  
                        $('#compantr'+i).hide(); 
                        $('#tr'+i).show();  
                        $('#t_r'+i).show(); 
                    
                    }
              }
           });
        };
    };
    function renderCompanyForm (self) {
        var formID = $(self).attr('id');
        if($(self).find('#memberType').length>0){
            $(self).find('#memberType').combobox({onSelect:function () {
                var value = $(this).combobox('getValue');
                if(value=="1"){//如果为租用，可以填写每月租金
                    $('#'+formID+' #amountLimit').text('申请金额5-30万元');              
                    $('#'+formID+' #requestMoney').numberbox({min:50000,max:300000});           
                }else if(value=="2"){
                    $('#'+formID+' #amountLimit').text('申请金额10-50万元');              
                    $('#'+formID+' #requestMoney').numberbox({min:100000,max:500000});           
                }else if(value=='3'){
                    $('#'+formID+' #amountLimit').text('申请金额10-80万元');              
                    $('#'+formID+' #requestMoney').numberbox({min:100000,max:800000});           

                }     
            }});
            
        }


    }
    function initialize(self) {

        bindSeptBtn();
        renderPersonForm(self);
        renderCompanyForm(self);
        renderGuaForm(self);

      
    };
    var methods = {
        init: function (pageData, nav) {
            this.data(dataTag, {});
            
            initialize(this);
            return this;
        }
    };

    $.fn.seLoanApply = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance_detail' );
        }
    };
}
seLoanApply_plugin.prototype = {};
seLoanApply_plugin(jQuery);


//编辑小企业数据
function loadLoanDate(loanDetailsVo){
     renderOtherField(loanDetailsVo,'editLoanForm');
	
	//产品期数
	$('#editLoanForm').find('#requestTime').combobox({
	    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
        	var data = $('#editLoanForm').find('#requestTime').combobox('getData');
        	if(data.length==1)
        		$('#editLoanForm').find('#requestTime').combobox('select', data[0].id);
        }
	 });
    //贷款信息
    $('#editLoanForm').find('#loanId').val(loanDetailsVo.loanId);
    $('#editLoanForm').find('#status').val(loanDetailsVo.loan.status);
    $('#editLoanForm').find('#loanType').text(loanDetailsVo.loanType);
    $('#editLoanForm').find("#requestMoney").val(loanDetailsVo.loan.requestMoney);
    $('#editLoanForm').find("#requestTime").combobox('select',loanDetailsVo.loan.requestTime);
    $('#editLoanForm').find("#purpose").val(loanDetailsVo.loan.purpose);
    
    $('#editLoanForm').find("#productId").val(loanDetailsVo.loan.productId);

    //person
    $('#editLoanForm').find("#personId").val(loanDetailsVo.person.id);
    $('#editLoanForm').find('#personName').val(loanDetailsVo.person.name);
    $('#editLoanForm').find('#personSex').combobox('select',loanDetailsVo.person.sex);
    $('#editLoanForm').find('#personIdnum').val(loanDetailsVo.person.idnum);
    $('#editLoanForm').find('#personIdnumName').text(loanDetailsVo.person.idnum);


    if(loanDetailsVo.person.married==0){
        $('#editLoanForm').find('#personMarried').combobox('select',loanDetailsVo.person.married);
    }else{
        $('#editLoanForm').find('#personMarried').combobox('select',loanDetailsVo.person.married);
    }
    $('#editLoanForm').find('#personEducationLevel').combobox('select',loanDetailsVo.person.educationLevel);
    $('#editLoanForm').find('#personHasChildren').combobox('select',loanDetailsVo.person.hasChildren);

    $('#editLoanForm').find('#personChildrenNum').val(loanDetailsVo.person.childrenNum);
    $('#editLoanForm').find('#personZipCode').val(loanDetailsVo.person.zipCode);
    $('#editLoanForm').find('#personAddress').val(loanDetailsVo.person.address);
    $('#editLoanForm').find('#personMobilePhone').val(loanDetailsVo.person.mobilePhone);
    if(loanDetailsVo.person.professionType){
        $('#editLoanForm').find('#professionType').val(loanDetailsVo.person.professionType);//职业类型
    }
   //Email前缀
   /* var personEmail = emailPrefix(loanDetailsVo.person.email);
    //Email后缀
   var mail = emailSuffix(loanDetailsVo.person.email);   
  */
    $('#editLoanForm').find('#personeEmail').val(loanDetailsVo.person.email);
//    $('#editLoanForm').find('#personeEmails').combobox('select',mail);
    
    $('#editLoanForm').find('#personHomePhone').val(loanDetailsVo.person.homePhone);
   
    $('#editLoanForm').find('#houseEstateType').combobox('select',loanDetailsVo.person.houseEstateType);
    $('#editLoanForm').find('#rentPerMonth').val(loanDetailsVo.person.rentPerMonth);
    if(loanDetailsVo.person.hasHouseLoan==0){
        $('#editLoanForm').find('#hasHouseLoan').combobox('select','0');
    }else{
        $('#editLoanForm').find('#hasHouseLoan').combobox('select','1');
    }
    $('#editLoanForm').find('#houseEstateAddress').val(loanDetailsVo.person.houseEstateAddress);
    $('#editLoanForm').find('#personIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
    //	企业信息 $("#editLoanForm").form('load',loanDetailsVo.company);companyName
    $('#editLoanForm').find('#companyId').val(loanDetailsVo.company.id);
    $('#editLoanForm').find('#companyName').val(loanDetailsVo.company.name);
    $('#editLoanForm').find('#industryInvolved').val(loanDetailsVo.company.industryInvolved);
    $('#editLoanForm').find('#legalRepresentative').val(loanDetailsVo.company.legalRepresentative);
    $('#editLoanForm').find('#legalRepresentativeId').val(loanDetailsVo.company.legalRepresentativeId);
    $('#editLoanForm').find('#incomePerMonth').val(loanDetailsVo.company.incomePerMonth);
    $('#editLoanForm').find('#foundedDate').datebox('setValue',loanDetailsVo.company.foundedDate);
    $('#editLoanForm').find('#category').combobox('select',loanDetailsVo.company.category);
    $('#editLoanForm').find('#companyAddress').val(loanDetailsVo.company.address);
    $('#editLoanForm').find('#avgProfitPerYear').val(loanDetailsVo.company.avgProfitPerYear);
    $('#editLoanForm').find('#phone').val(loanDetailsVo.company.phone);
   // $('#editLoanForm').find('#zipCode').val(loanDetailsVo.company.zipCode);
    
    $('#editLoanForm').find('#companyZipCode').val(loanDetailsVo.company.zipCode);
    
    
    
    $('#editLoanForm').find('#operationSite').combobox('select',loanDetailsVo.company.operationSite);
    $('#editLoanForm').find('#majorBusiness').val(loanDetailsVo.company.majorBusiness);
    $('#editLoanForm').find('#employeesNumber').val(loanDetailsVo.company.employeesNumber);
    $('#editLoanForm').find('#employeesWagesPerMonth').val(loanDetailsVo.company.employeesWagesPerMonth);
    
   
    
    $('#editLoanForm').find('#foundedDate').datebox("setValue",  getYMD(loanDetailsVo.company.foundedDate));
    $('#editLoanForm').find('#requestDate').datebox("setValue", getYMD(loanDetailsVo.loan.requestDate));
    
    //其它

    //其它
    $('#editLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
    $('#editLoanForm').find('#customerName').text(loanDetailsVo.service.name);
 //   $('#editLoanForm').find('#personNo').combobox('setValue',loanDetailsVo.crm.code);
  //  $('#editLoanForm').find('#managerName').combobox('select',loanDetailsVo.crm.id);
    $('#editLoanForm').find('#assessorName').combobox('select',loanDetailsVo.assessor.id);   
    $('#editLoanForm').find('#assessorId').val(loanDetailsVo.assessor.id);
    $('#editLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
    //var salesDeptId = $("#editLoanForm #salesDeptId").combobox('getValue');
    //$('#editLoanForm').find('#salesDeptId').val(salesDeptId);
    //$('#editLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
    if(loanDetailsVo.userId==loanDetailsVo.assessor.id){//如果当前用户是复核人员
    	 $('#editLoanForm').find("#assessorName").combobox({
   		  disabled:true
   	  });
       $('#editLoanForm').find('#assessorName').combobox('setValue',loanDetailsVo.assessor.name);
    }else{
    	$('#editLoanForm').find('#assessorName').combobox('select',loanDetailsVo.assessor.id);
    }
   
    $('#editLoanForm').find('#customerSource').combobox('select',loanDetailsVo.loan.customerSource);
    if(loanDetailsVo.loan.remark){
    	 $('#editLoanForm').find('#remark').val(loanDetailsVo.loan.remark);	
    } 
    //联系人

    for(var i=0;i<loanDetailsVo.contacterList.length;i++){       
    	if(loanDetailsVo.contacterList[i]){
    		$('#editLoanForm').find("#contacterId"+i).val(loanDetailsVo.contacterList[i].id);
    		$('#editLoanForm').find("#contacterName"+i).val(loanDetailsVo.contacterList[i].name);
    		
    		$('#editLoanForm').find("#mobilePhone"+i).val(loanDetailsVo.contacterList[i].mobilePhone);
    		$('#editLoanForm').find("#homePhone"+i).val(loanDetailsVo.contacterList[i].homePhone);
    		$('#editLoanForm').find("#workUnit"+i).val(loanDetailsVo.contacterList[i].workUnit);
            $('#editLoanForm').find('#hadKnown'+i).combobox('select',loanDetailsVo.contacterList[i].hadKnown);   
            $('#editLoanForm').find('#title'+i).val(loanDetailsVo.contacterList[i].title);   
    		$('#editLoanForm').find('#address'+i).val(loanDetailsVo.contacterList[i].address); 
                     
            if($('#editLoanForm').find('#relationship'+i).is('select')){
                $('#editLoanForm').find("#relationship"+i).combobox('select',loanDetailsVo.contacterList[i].relationship);
                // $('#editLoanForm').find("#relationship"+i).combobox('setValue',loanDetailsVo.contacterList[i].relationship);
            }else{
                $('#editLoanForm').find("#relationship"+i).val(loanDetailsVo.contacterList[i].relationship);
            }
            
    	}
          
    }
	for(var i=0;i<4;i++){   
		 $('#editLoanForm').find("#naturalGuaranteeName"+i).removeAttr("readonly"); 
		 $('#editLoanForm').find("#guaranteeCheck"+i).attr("checked",false);
  		$('#editLoanForm').find("#guaranteeFlag"+i).val(null); 
	}
    if(loanDetailsVo.guaranteeList!=''){
		for(var i=0;i<loanDetailsVo.guaranteeList.length;i++){   
			if(loanDetailsVo.guaranteeList[i].flag){//该担保人为指定的担保人
	     		$('#editLoanForm').find("#guaranteeCheck"+i).attr("checked",'0');
	     		$('#editLoanForm').find("#guaranteeFlag"+i).val(1); 
	     	}else{
	     		$('#editLoanForm').find("#guaranteeCheck"+i).attr("checked",false);
	     		$('#editLoanForm').find("#guaranteeFlag"+i).val(null); 
	     	}
	     	
			}
	}
    
    var loanStatus= loanDetailsVo.loan.status;  
    if(loanStatus==50){  
    	
    	//$('#editLoanForm  :checkbox').attr("disabled","disabled");
    	$('#editLoanForm :input').attr("readonly","readonly");    	
   	     $('#editLoanForm').find('select:not(.conRelationship)').each(function(){
   		 	$(this).combobox({
   		 		disabled:true
   		 	});   		 
   	    });
   	  $('#editLoanForm').find("#requestDate").datebox({
   		  disabled:true
   	  });
   	 $('#editLoanForm').find("#foundedDate").datebox({
  		  disabled:true
  	  });
   	 $('#editLoanForm').find("#managerName").combobox({
 		  disabled:true
 	  });
   	 //有条件同意时指定的担保人
    // var naturalGuaranteeName1 = $('#editLoanForm').find("#naturalGuaranteeName1").val();
    // var naturalGuaranteeName2 = $('#editLoanForm').find("#naturalGuaranteeName2").val();
    // var legalGuaranteeCname1 = $('#editLoanForm').find("#legalGuaranteeCname1").val();
    // var legalGuaranteeCname2 = $('#editLoanForm').find("#legalGuaranteeCname2").val();
    // var appointedGuar = [naturalGuaranteeName1,naturalGuaranteeName2,legalGuaranteeCname1,legalGuaranteeCname2];
    var appointedGuar =  new Array();
   	 if(loanDetailsVo.loan.guaranteeName){
   		 $('#editLoanForm').find('#guaranteeName').val(loanDetailsVo.loan.guaranteeName);
         var arr = loanDetailsVo.loan.guaranteeName.split(',');   
         appointedGuar = arr.slice(0,arr.length-1);
     }
     if(loanDetailsVo.loan.naturalGuaranteeName1){
    	 $('#editLoanForm').find("#naturalGuaranteeName1").removeAttr("readonly"); 
  		 $('#editLoanForm').find('#naturalGuaranteeName1').val(loanDetailsVo.loan.naturalGuaranteeName1);

         appointedGuar.push(loanDetailsVo.loan.naturalGuaranteeName1);
  	 }if(loanDetailsVo.loan.naturalGuaranteeName2){
  		 $('#editLoanForm').find("#naturalGuaranteeName2").removeAttr("readonly"); 
   		 $('#editLoanForm').find('#naturalGuaranteeName2').val(loanDetailsVo.loan.naturalGuaranteeName2);
         appointedGuar.push(loanDetailsVo.loan.naturalGuaranteeName2);
   	 }if(loanDetailsVo.loan.legalGuaranteeCname1){
   		 $('#editLoanForm').find("#legalGuaranteeCname1").removeAttr("readonly"); 
   		 $('#editLoanForm').find('#legalGuaranteeCname1').val(loanDetailsVo.loan.legalGuaranteeCname1);
         appointedGuar.push(loanDetailsVo.loan.legalGuaranteeCname1);
   	 }if(loanDetailsVo.loan.legalGuaranteeCname2){
   		 $('#editLoanForm').find("#legalGuaranteeCname2").removeAttr("readonly"); 
   		 $('#editLoanForm').find('#legalGuaranteeCname2').val(loanDetailsVo.loan.legalGuaranteeCname2);
         appointedGuar.push(loanDetailsVo.loan.legalGuaranteeCname2);   
   	 }
     //$('#editLoanForm').find('#managerName').combobox('select',loanDetailsVo.crm.id);
     // 担保人       
     for(var i=0;i<4;i++){   
        if(loanDetailsVo.guaranteeList[i]){
            $('#editLoanForm').find("#guaranteeCheck"+i).removeAttr('checked');
            if ($.inArray(loanDetailsVo.guaranteeList[i].name, appointedGuar)>-1) {
                $('#editLoanForm').find("#guaranteeCheck"+i).attr('checked','checked');
                $('#editLoanForm').find("#guaranteeFlag"+i).val('1');             

                 $('#editLoanForm').find('#guaranteeChange'+i).text("有条件签约时选择指定的担保人");
       
            };
            $('#editLoanForm').find("#guaranteeCheck"+i).attr("disabled","true");
            
            
            if(loanDetailsVo.guaranteeList[i].guaranteeType==0){//自然人担保
                
            
                $('#nametr'+i).show();  
                $('#sextr'+i).show(); 
                $('#edutr'+i).show();  
                $('#childtr'+i).show(); 
                $('#mobiletr'+i).show(); 
                $('#compantr'+i).show();
                $('#tr'+i).hide();  
                $('#t_r'+i).hide();
                
                
                $('#editLoanForm').find("#guaranteeId"+i).removeAttr("readonly");  
                $('#editLoanForm').find("#guaPersonId"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
                $('#editLoanForm').find("#guaName"+i).val(loanDetailsVo.guaranteeList[i].name);
                $('#editLoanForm').find("#guaType"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaType"+i).combobox('select',loanDetailsVo.guaranteeList[i].guaranteeType);
                $('#editLoanForm').find("#guaIdnum"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaIdnum"+i).val(loanDetailsVo.guaranteeList[i].idnum);
                $('#editLoanForm').find("#guaSex"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaSex"+i).combobox('select',loanDetailsVo.guaranteeList[i].sex);
                
                $('#editLoanForm').find("#guaMarried"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaMarried"+i).combobox('select',loanDetailsVo.guaranteeList[i].married);
                $('#editLoanForm').find("#guaEducationLevel"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaEducationLevel"+i).combobox('select',loanDetailsVo.guaranteeList[i].educationLevel);
                $('#editLoanForm').find("#guaHasChildren"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaHasChildren"+i).combobox('select',loanDetailsVo.guaranteeList[i].hasChildren);
                $('#editLoanForm').find("#guaMobilePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaHomePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaCompanyFullName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaZipCode"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaAddress"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaCompanyAddress"+i).removeAttr("readonly");
                    
                $('#editLoanForm').find("#guaPhone"+i).removeAttr("readonly");            
                $('#editLoanForm').find("#guaEmail"+i).removeAttr("readonly");
            
                $('#editLoanForm').find("#guaranteeCompanyFullName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeZipCode"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeCompanyAddress"+i).removeAttr("readonly");   
                
                
                $('#editLoanForm').find("#guaMobilePhone"+i).val(loanDetailsVo.guaranteeList[i].mobilePhone);
                $('#editLoanForm').find("#guaHomePhone"+i).val(loanDetailsVo.guaranteeList[i].homePhone);
                $('#editLoanForm').find("#guaCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
                $('#editLoanForm').find("#guaZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
                $('#editLoanForm').find("#guaAddress"+i).val(loanDetailsVo.guaranteeList[i].address);
                $('#editLoanForm').find("#guaCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);
                $('#editLoanForm').find("#guaPhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
                $('#editLoanForm').find("#guaEmail"+i).val(loanDetailsVo.guaranteeList[i].email);
                if ($.inArray(loanDetailsVo.guaranteeList[i].name, appointedGuar)>-1){
                	$('#editLoanForm').find("#guaName"+i).attr("readonly","true");
                    // $('#editLoanForm').find("#guaType"+i).combobox({ disabled:true});
                	
                	
                }
            }else if(loanDetailsVo.guaranteeList[i].guaranteeType==1){//法人担保
                $('#nametr'+i).hide();  
                $('#sextr'+i).hide(); 
                $('#edutr'+i).hide();  
                $('#childtr'+i).hide(); 
                $('#mobiletr'+i).hide();  
                $('#compantr'+i).hide(); 
                $('#tr'+i).show();  
                $('#t_r'+i).show();
                $('#editLoanForm').find("#guaranteeId"+i).removeAttr("readonly");  
                $('#editLoanForm').find("#guaPersonId"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
                $('#editLoanForm').find("#guaName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaIdnum"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaSex"+i).combobox({  disabled:false });
                
                $('#editLoanForm').find("#guaMarried"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaEducationLevel"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaHasChildren"+i).combobox({  disabled:false });
                $('#editLoanForm').find("#guaMobilePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaHomePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaCompanyFullName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaZipCode"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaAddress"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaCompanyAddress"+i).removeAttr("readonly");
                    
                $('#editLoanForm').find("#guaPhone"+i).removeAttr("readonly");            
                $('#editLoanForm').find("#guaEmail"+i).removeAttr("readonly");
                
                $('#editLoanForm').find("#guaType"+i).combobox({  disabled:false });
                // $('#editLoanForm').find("#guaranteeCompanyFullName"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeZipCode"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteePhone"+i).removeAttr("readonly");
                $('#editLoanForm').find("#guaranteeCompanyAddress"+i).removeAttr("readonly");               
                
                $('#editLoanForm').find("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
                $('#editLoanForm').find("#guaPersonId"+i).val(loanDetailsVo.guaranteeList[i].personId);  
                $('#editLoanForm').find("#guaType"+i).combobox('select',loanDetailsVo.guaranteeList[i].guaranteeType);
                $('#editLoanForm').find("#guaranteeCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
                $('#editLoanForm').find("#guaranteeZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
                $('#editLoanForm').find("#guaranteePhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
                $('#editLoanForm').find("#guaranteeCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);
                
                if ($.inArray(loanDetailsVo.guaranteeList[i].name, appointedGuar)>-1){
                      $('#editLoanForm').find("#guaranteeCompanyFullName"+i).attr("readonly","true");                     
                	   // $('#editLoanForm').find("#guaType"+i).combobox({ disabled:true});
                	
                }
     			
     		}	

            
     	}else{
            $('#editLoanForm').find("#guaranteeCheck"+i).attr("disabled","disabled");
     		$('#nametr'+i).show();  
    		$('#sextr'+i).show(); 
    		$('#edutr'+i).show();  
    		$('#childtr'+i).show(); 
    		$('#mobiletr'+i).show(); 
    		$('#compantr'+i).show();
    		$('#tr'+i).hide();  
    		$('#t_r'+i).hide(); 
//     		$('#editLoanForm').find("#guaranteeId"+i);
//     		$('#editLoanForm').find("#guaPersonId"+i);
//     		$('#editLoanForm').find("#guaName"+i);
//     		$('#editLoanForm').find("#guaType"+i);
//     		$('#editLoanForm').find("#guaIdnum"+i);
//     		$('#editLoanForm').find("#guaSex"+i);
//     		
//     		$('#editLoanForm').find("#guaMarried"+i);
//     		$('#editLoanForm').find("#guaEducationLevel"+i);
//     		$('#editLoanForm').find("#guaHasChildren"+i);
//     		$('#editLoanForm').find("#guaMobilePhone"+i);
//     		$('#editLoanForm').find("#guaHomePhone"+i);
//     		$('#editLoanForm').find("#guaCompanyFullName"+i);
//     		$('#editLoanForm').find("#guaZipCode"+i);
//     		$('#editLoanForm').find("#guaAddress"+i);
//     		$('#editLoanForm').find("#guaCompanyAddress"+i);
//     	        
//     		$('#editLoanForm').find("#guaPhone"+i);     	      
//     		$('#editLoanForm').find("#guaEmail"+i);
//     		
//     		$('#editLoanForm').find("#guaranteeCompanyFullName"+i);
//    		$('#editLoanForm').find("#guaranteeZipCode"+i);
//    		$('#editLoanForm').find("#guaranteePhone"+i);
//    		$('#editLoanForm').find("#guaranteeCompanyAddress"+i);	 
     	}
        
     }

    
    	
    }else{
    	  // 担保人   
        for(var i=0;i<4;i++){ 
        	$('#editLoanForm').find("#guaranteeCheck"+i).attr("disabled","disabled");
        	if(loanDetailsVo.guaranteeList[i]){
        		/*if(loanDetailsVo.guaranteeList[i].flag){	
        			$('#editLoanForm').find("#guaranteeCheck"+i).attr("checked",true); 
        			$('#editLoanForm').find("#guaranteeFlag"+i).val(1);
        		}*/
                if(loanDetailsVo.guaranteeList[i].guaranteeType==0){//自然人担保
        			$('#nametr'+i).show();  
    	    		$('#sextr'+i).show(); 
    	    		$('#edutr'+i).show();  
    	    		$('#childtr'+i).show(); 
    	    		$('#mobiletr'+i).show(); 
    	    		$('#compantr'+i).show();
    	    		$('#tr'+i).hide();  
    	    		$('#t_r'+i).hide();
    	    		
        			$('#editLoanForm').find("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
            		$('#editLoanForm').find("#guaPersonId"+i).val(loanDetailsVo.guaranteeList[i].personId);
            		$('#editLoanForm').find("#guaName"+i).val(loanDetailsVo.guaranteeList[i].name);
            		$('#editLoanForm').find("#guaType"+i).combobox('select',loanDetailsVo.guaranteeList[i].guaranteeType);
            		$('#editLoanForm').find("#guaIdnum"+i).val(loanDetailsVo.guaranteeList[i].idnum);
            		$('#editLoanForm').find("#guaSex"+i).combobox('select',loanDetailsVo.guaranteeList[i].sex);

            		$('#editLoanForm').find("#guaMarried"+i).combobox('select',loanDetailsVo.guaranteeList[i].married);
            		$('#editLoanForm').find("#guaEducationLevel"+i).combobox('select',loanDetailsVo.guaranteeList[i].educationLevel);
            		$('#editLoanForm').find("#guaHasChildren"+i).combobox('select',loanDetailsVo.guaranteeList[i].hasChildren);
            		$('#editLoanForm').find("#guaMobilePhone"+i).val(loanDetailsVo.guaranteeList[i].mobilePhone);
            		$('#editLoanForm').find("#guaHomePhone"+i).val(loanDetailsVo.guaranteeList[i].homePhone);
            		$('#editLoanForm').find("#guaCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
            		$('#editLoanForm').find("#guaZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
            		$('#editLoanForm').find("#guaAddress"+i).val(loanDetailsVo.guaranteeList[i].address);
            		$('#editLoanForm').find("#guaCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);
            	        
            		$('#editLoanForm').find("#guaPhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
            	      
            		$('#editLoanForm').find("#guaEmail"+i).val(loanDetailsVo.guaranteeList[i].email);
        		}else if(loanDetailsVo.guaranteeList[i].guaranteeType==1){//法人                    		
        			$('#nametr'+i).hide();  
    	    		$('#sextr'+i).hide(); 
    	    		$('#edutr'+i).hide();  
    	    		$('#childtr'+i).hide(); 
    	    		$('#mobiletr'+i).hide();  
    	    		$('#compantr'+i).hide(); 
    	    		$('#tr'+i).show();  
    	    		$('#t_r'+i).show(); 	
        			$('#editLoanForm').find("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
            		$('#editLoanForm').find("#guaPersonId"+i).val(loanDetailsVo.guaranteeList[i].personId);  
            		$('#editLoanForm').find("#guaType"+i).combobox('select',loanDetailsVo.guaranteeList[i].guaranteeType);
            		$('#editLoanForm').find("#guaranteeCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
            		$('#editLoanForm').find("#guaranteeZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
            		$('#editLoanForm').find("#guaranteePhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
            		$('#editLoanForm').find("#guaranteeCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);
        		}
        		 
        	}else{//默认显示    	
        		
    	    		$('#nametr'+i).show();  
    	    		$('#sextr'+i).show(); 
    	    		$('#edutr'+i).show();  
    	    		$('#childtr'+i).show(); 
    	    		$('#mobiletr'+i).show(); 
    	    		$('#compantr'+i).show();
    	    		$('#tr'+i).hide();  
    	    		$('#t_r'+i).hide(); 
    	    	
        	}
           
        }
    	
    }
   
}

//加载老客户小企业贷数据
function  loadOldData(loanDetailsVo){
    renderOtherField(loanDetailsVo,'addSeLoanForm');	
	//产品期数
	$('#addSeLoanForm').find('#requestTime').combobox({
	    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
        	var data = $('#addSeLoanForm').find('#requestTime').combobox('getData');
        	if(data.length==1)
        		$('#addSeLoanForm').find('#requestTime').combobox('select', data[0].id);
        }
	 });
	
	$('#addSeLoanForm').find("#guaranteeCheck0").attr("disabled","disabled");
	$('#addSeLoanForm').find("#guaranteeCheck1").attr("disabled","disabled");		
    //person
    $('#addSeLoanForm').find("#personId").val(loanDetailsVo.person.id);
    $('#addSeLoanForm').find('#productId').val(loanDetailsVo.productId);
    $('#addSeLoanForm').find('#productTypeId').val(loanDetailsVo.productTypeId);
    $('#addSeLoanForm').find('#personName').val(loanDetailsVo.person.name);
    $('#addSeLoanForm').find('#personSex').combobox('select',loanDetailsVo.person.sex);
    $('#addSeLoanForm').find('#personIdnumName').text(loanDetailsVo.person.idnum);
    $('#addSeLoanForm').find('#personIdnum').val(loanDetailsVo.person.idnum);

    if(loanDetailsVo.person.married==0){
        $('#addSeLoanForm').find('#personMarried').combobox('select',loanDetailsVo.person.married);
    }else{
        $('#addSeLoanForm').find('#personMarried').combobox('select',loanDetailsVo.person.married);
    }
   $('#addSeLoanForm').find('#personEducationLevel').combobox('select',loanDetailsVo.person.educationLevel);
    $('#addSeLoanForm').find('#personHasChildren').combobox('select',loanDetailsVo.person.hasChildren);

    $('#addSeLoanForm').find('#personChildrenNum').val(loanDetailsVo.person.childrenNum);
    $('#addSeLoanForm').find('#personZipCode').val(loanDetailsVo.person.zipCode);
    $('#addSeLoanForm').find('#personAddress').val(loanDetailsVo.person.address);
    $('#addSeLoanForm').find('#personMobilePhone').val(loanDetailsVo.person.mobilePhone);
    $('#addSeLoanForm').find('#personeEmail').val(loanDetailsVo.person.email);
    $('#addSeLoanForm').find('#personHomePhone').val(loanDetailsVo.person.homePhone);
    $('#addSeLoanForm').find('#houseEstateType').combobox('select',loanDetailsVo.person.houseEstateType);
    $('#addSeLoanForm').find('#rentPerMonth').val(loanDetailsVo.person.rentPerMonth);
    if(loanDetailsVo.person.hasHouseLoan==0){
        $('#addSeLoanForm').find('#hasHouseLoan').combobox('select','0');
    }else{
        $('#addSeLoanForm').find('#hasHouseLoan').combobox('select','1');
    }
    $('#addSeLoanForm').find('#houseEstateAddress').val(loanDetailsVo.person.houseEstateAddress);
    $('#addSeLoanForm').find('#personIncomePerMonth').val(loanDetailsVo.person.incomePerMonth);
    //	企业信息 $("#addSeLoanForm").form('load',loanDetailsVo.company);companyName
    $('#addSeLoanForm').find('#companyId').val(loanDetailsVo.company.id);
    $('#addSeLoanForm').find('#companyName').val(loanDetailsVo.company.name);
    $('#addSeLoanForm').find('#industryInvolved').val(loanDetailsVo.company.industryInvolved);
    $('#addSeLoanForm').find('#legalRepresentative').val(loanDetailsVo.company.legalRepresentative);
    $('#addSeLoanForm').find('#legalRepresentativeId').val(loanDetailsVo.company.legalRepresentativeId);
    $('#addSeLoanForm').find('#incomePerMonth').val(loanDetailsVo.company.incomePerMonth);
    $('#addSeLoanForm').find('#foundedDate').val(loanDetailsVo.company.foundedDate);
    $('#addSeLoanForm').find('#category').val(loanDetailsVo.company.category);
    $('#addSeLoanForm').find('#address').val(loanDetailsVo.company.address);
    $('#addSeLoanForm').find('#avgProfitPerYear').val(loanDetailsVo.company.avgProfitPerYear);
    $('#addSeLoanForm').find('#phone').val(loanDetailsVo.company.phone);
    $('#addSeLoanForm').find('#zipCode').val(loanDetailsVo.company.zipCode);
    $('#addSeLoanForm').find('#operationSite').val(loanDetailsVo.company.operationSite);
    $('#addSeLoanForm').find('#majorBusiness').val(loanDetailsVo.company.majorBusiness);
    $('#addSeLoanForm').find('#employeesNumber').val(loanDetailsVo.company.employeesNumber);
    $('#addSeLoanForm').find('#employeesWagesPerMonth').val(loanDetailsVo.company.employeesWagesPerMonth);
    $('#addSeLoanForm').find('#companyAddress').val(loanDetailsVo.company.address);

    if(loanDetailsVo.company.foundedDate){
    	 $('#addSeLoanForm').find('#foundedDate').datebox("setValue",  getYMD(loanDetailsVo.company.foundedDate));
    }
    //其它

    //其它
    $('#addSeLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
    $('#addSeLoanForm').find('#customerName').text(loanDetailsVo.service.name);
//    $('#addSeLoanForm').find('#personNo').combobox('setValue',loanDetailsVo.crm.code);
 //   $('#addSeLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
//    $('#addSeLoanForm').find('#salesDeptId').val(loanDetailsVo.salesDept.id);
//    $('#addSeLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
    
   
    //联系人
    if (loanDetailsVo.contacterList){
        for(var i=0;i<4;i++){       
        	if(loanDetailsVo.contacterList[i]){
        		 $("#contacterId"+i).val(loanDetailsVo.contacterList[i].id);
                 $("#contacterName"+i).val(loanDetailsVo.contacterList[i].name);
                 // $("#relationship"+i).val(loanDetailsVo.contacterList[i].relationship);
                 $("#mobilePhone"+i).val(loanDetailsVo.contacterList[i].mobilePhone);
                 $("#homePhone"+i).val(loanDetailsVo.contacterList[i].homePhone);
                 $("#workUnit"+i).val(loanDetailsVo.contacterList[i].workUnit);
                 $('#hadKnown'+i).combobox('select',loanDetailsVo.contacterList[i].hadKnown);

                $('#title'+i).val(loanDetailsVo.contacterList[i].title);   
                $('#address'+i).val(loanDetailsVo.contacterList[i].address); 

                if( $("#relationship"+i).is('select')){
                    $("#relationship"+i).combobox('select',loanDetailsVo.contacterList[i].relationship);
                    // $('#editLoanForm').find("#relationship"+i).combobox('setValue',loanDetailsVo.contacterList[i].relationship);
                }else{
                    $("#relationship"+i).val(loanDetailsVo.contacterList[i].relationship);
                }   
        		
        	}
              
        };
        
    };
    // 担保人   
    if(loanDetailsVo.guaranteeList ){
        for(var i=0;i<loanDetailsVo.guaranteeList.length;i++){   
                
            $("#guaType"+i).combobox('select',loanDetailsVo.guaranteeList[i].guaranteeType);
            $("#guaranteeId"+i).val(loanDetailsVo.guaranteeList[i].id);
            $("#guaPersonId"+i).val(loanDetailsVo.guaranteeList[i].personId);
            $("#guaName"+i).val(loanDetailsVo.guaranteeList[i].name);
            $("#guaIdnum"+i).val(loanDetailsVo.guaranteeList[i].idnum);
            $("#guaSex"+i).combobox('select',loanDetailsVo.guaranteeList[i].sex);

            $("#guaMarried"+i).combobox('select',loanDetailsVo.guaranteeList[i].married);
            $("#guaEducationLevel"+i).combobox('select',loanDetailsVo.guaranteeList[i].educationLevel);
            $("#guaHasChildren"+i).combobox('select',loanDetailsVo.guaranteeList[i].hasChildren);
            $("#guaAddress"+i).val(loanDetailsVo.guaranteeList[i].address);
            $("#guaMobilePhone"+i).val(loanDetailsVo.guaranteeList[i].mobilePhone);
            $("#guaEmail"+i).val(loanDetailsVo.guaranteeList[i].email);
            $("#guaHomePhone"+i).val(loanDetailsVo.guaranteeList[i].homePhone);
            $("#guaPhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
            $("#guaZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
            $("#guaCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
            $("#guaCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);


            $("#guaranteeCompanyFullName"+i).val(loanDetailsVo.guaranteeList[i].companyFullName);
            $("#guaranteePhone"+i).val(loanDetailsVo.guaranteeList[i].companyPhone);
            $("#guaranteeCompanyAddress"+i).val(loanDetailsVo.guaranteeList[i].companyAddress);
            $("#guaranteeZipCode"+i).val(loanDetailsVo.guaranteeList[i].zipCode);
        }
        for(var i=loanDetailsVo.guaranteeList.length; i< 4 ;i++){
             $("#addtr"+i).hide();
             $("#addt_r"+i).hide();
        }
       
    }
}

//加载小企业数据到新增页面上
function loadAddData(loanDetailsVo){
	
	//产品期数
	$('#addSeLoanForm').find('#requestTime').combobox({
	    url:'apply/getProductTermsByProductId?productId='+loanDetailsVo.productId,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
            var data = $('#addSeLoanForm').find('#requestTime').combobox('getData');
        	if(data.length==1) 
        		$('#addSeLoanForm').find('#requestTime').combobox('select', data[0].id);
        }
	 });
	
	$('#addSeLoanForm').find("#guaranteeCheck0").attr("disabled","disabled");
	$('#addSeLoanForm').find("#guaranteeCheck1").attr("disabled","disabled");	
	$('#addSeLoanForm').find("#guaranteeCheck2").attr("disabled","disabled");
	$('#addSeLoanForm').find("#guaranteeCheck3").attr("disabled","disabled");	
	
	//贷款信息
    $('#addSeLoanForm').find('#productNames').text(loanDetailsVo.loanType);
    $('#addSeLoanForm').find('#productName').val(loanDetailsVo.loanType);
    $('#addSeLoanForm').find('#productId').val(loanDetailsVo.productId);
    $('#addSeLoanForm').find('#productTypeId').val(loanDetailsVo.productTypeId);
    $('#addSeLoanForm').find('#personIdnumName').text(loanDetailsVo.idnum);
    $('#addSeLoanForm').find('#personIdnum').val(loanDetailsVo.idnum);
    //其它
    $('#addSeLoanForm').find('#serviceId').val(loanDetailsVo.service.id);
    $('#addSeLoanForm').find('#customerName').text(loanDetailsVo.service.name);
    $('#addSeLoanForm').find('#crmId').val(loanDetailsVo.crm.id);   
//    $('#addSeLoanForm').find('#salesDeptId').val(loanDetailsVo.salesDept.id);
//    $('#addSeLoanForm').find('#deptArea').text(loanDetailsVo.salesDept.name);
    for(var i=0;i<=3;i++){  
        $("#hadKnown"+i).combobox('select','0');
        $("#hadKnown"+i).combobox('select','0');
        $("#hadKnown"+i).combobox('select','0');
        $("#hadKnown"+i).combobox('select','0');
    }
    for(var i=0;i<=4;i++){  
        $("#guaType"+i).combobox('select','0');
        $("#guaSex"+i).combobox('select','1');
        $("#guaHasChildren"+i).combobox('select','1');
    }
    
    for(var i=0;i<4;i++){//担保人初始页面
    	$('#addNametr'+i).show();  
    	$('#addSextr'+i).show(); 
    	$('#addEdutr'+i).show();  
    	$('#addChildtr'+i).show(); 
    	$('#addMobiletr'+i).show(); 
    	$('#addCompantr'+i).show(); 
    	$('#addtr'+i).hide();  
    	$('#addt_r'+i).hide(); 
    }
    
        
};
//把新增小企业贷数据提交到后台
function submitAdd() {
	var personMarried=$('#addSeLoanForm').find("#personMarried").combobox('getValue');
	var requestTime=$('#addSeLoanForm').find("#requestTime").combobox('getValue');
	var personSex=$('#addSeLoanForm').find("#personSex").combobox('getValue');
	var personEducationLevel=$('#addSeLoanForm').find("#personEducationLevel").combobox('getValue');
	var personHasChildren=$('#addSeLoanForm').find("#personHasChildren").combobox('getValue');
	var houseEstateType=$('#addSeLoanForm').find("#houseEstateType").combobox('getValue');
	var customerSource=$('#addSeLoanForm').find("#customerSource").combobox('getValue');
	
	var category=$('#addSeLoanForm').find("#category").combobox('getValue');
	var operationSite=$('#addSeLoanForm').find("#operationSite").combobox('getValue');
	var managerName=$('#addSeLoanForm').find("#managerName").combobox('getValue');
	var professionType =$('#addSeLoanForm').find("#professionType").combobox('getValue');

	var assessorName=$('#addSeLoanForm').find("#assessorName").combobox('getValue');
	if(professionType==''){
		$.messager.show({
  			title: 'warning',
  			msg: '请选择职业类型'
  	  }); 
	return false;
	}
	//验证婚姻状况
	if(personMarried==''){
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
	if(personSex==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择性别'
	  	  });
		return false;
	}
	if(personEducationLevel==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择最高学历'
	  	  });
		return false;
	}
	if(personHasChildren==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择有无子女'
	  	  });
		return false;
	}
	if(houseEstateType==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择房产类型'
	  	  });
		return false;
	}
	if(customerSource==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择客户来源'
	  	  });
		return false;
	}
	if(managerName==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择客户经理'
	  	  });
		return false;
	}
	if(operationSite==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择经营场所'
	  	  });
		return false;
	}
	if(assessorName==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择复核人员'
	  	  });
		return false;
	}
	if(category==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择企业类型'
	  	  });
		return false;
	}
	//成立时间
	var foundedDate=$('#addSeLoanForm').find("#foundedDate").datebox('getValue');
	   if(foundedDate==''){
		   $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择成立时间'
	  	  });
		   return false;
	   }
	//申请日期
	   var requestDate=$('#addSeLoanForm').find("#requestDate").datebox('getValue');
	   if(requestDate==''){
		   $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择申请日期'
	  	  });		   
		   return false;
	   } 
	   //贷款状态为退回时，必须选择担保人
	   if($('#addSeLoanForm').find("#status").val()==50){		  
		   var flag = $('#addSeLoanForm').find("#guaranteeFlag0").val();
		   var flag2 = $('#addSeLoanForm').find("#guaranteeFlag1").val();	
		   if(flag!=1&&flag2!=1){
			   $.messager.show({
		  			title: 'warning',
		  			msg: ' 请选择担保人'
		  	  });
			   return false;
		   }
		   if(flag==1&&$('#addSeLoanForm').find("#guaName0").val()==''){
			   $.messager.show({
		  			title: 'warning',
		  			msg: '请填写担保人姓名'
		  	  });
			   return false;
		   }
		   if(flag2==1&&$('#addSeLoanForm').find("#guaName1").val()==''){
			   $.messager.show({
		  			title: 'warning',
		  			msg: '请填写担保人姓名'
		  	  });
			   return false;
		   }
		   
	   }
	var crmId=$('#addSeLoanForm').find("#managerName").combobox('getValue');
	$('#addSeLoanForm').find('#crmId').val(crmId);
	var assessorId=$('#addSeLoanForm').find("#assessorName").combobox('getValue');
	$('#addSeLoanForm').find('#assessorId').val(assessorId);
	
	var  houseEstateType = $('#addSeLoanForm').find('#houseEstateType').combobox('getValue');
	//提示填写每月租金
	if(houseEstateType=='租用'&&$('#addSeLoanForm').find('#rentPerMonth').val()==""){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请填写每月租金'
	  	  });
		 return false;
	}	
	//新增页面联系人人信息校验	
	if(checkAddContacter()==false){
		return false;
	}

    submitLoan();
	
	 
	

}
function submitLoan(){
    var checkContacterIntegrityResult = checkContacterIntegrity('addSeLoanForm');
    if(checkContacterIntegrityResult.status == false){
        $.messager.show({
                title: 'warning',
                msg: checkContacterIntegrityResult.msg
          });
        return false;
    } 
    //新增页面担保人信息校验
    if(checkAddGuaratee() ==false){
        return false;
    }
    //新增页面担保人信息填充
    addtGuarantee();    
    // 验证FORM   
     if(!$("#addSeLoanForm").form('validate')){
            return false;
    }else{
        $('#submitEditBtnNew').linkbutton({disabled:true});
        $('#closeLoanBtn').linkbutton({disabled:true});
    }
    
    $.ajax({
           url : 'apply/addSELoan',
           data : $("#addSeLoanForm").serialize(),
           type:"POST",
           success : function(result){
               $('#submitEditBtnNew').linkbutton({disabled:false});
                $('#closeLoanBtn').linkbutton({disabled:false});
                if(result=='success'){
                    $.messager.show({
                        title : '提示',
                        msg : '保存成功！'
                    });
                    //发送数据到ECIF
                    ecifTrans($("#addSeLoanForm").serialize());
                    //清空小企业贷页面上的数据
                    clrearLoanDate();   
                    $('#seLoanAdd').dialog('close');
                    $('#loanPageTb').datagrid('reload');
                    
                }else{
                    parent.$.messager.show({
                        title: 'Error',
                        msg: result
                    });
                }
           },
           error:function(data){
                 $.messager.show({
                        title: 'warning',
                        msg: data.responseText
                    });
           }
    }); 

}

function submitAddCityWideLoan(){
    
    var personMarried=$('#addSeLoanForm').find("#personMarried").combobox('getValue');
    var requestTime=$('#addSeLoanForm').find("#requestTime").combobox('getValue');
    var personSex=$('#addSeLoanForm').find("#personSex").combobox('getValue');
    var personEducationLevel=$('#addSeLoanForm').find("#personEducationLevel").combobox('getValue');
    // var personHasChildren=$('#addSeLoanForm').find("#personHasChildren").combobox('getValue');
    var houseEstateType=$('#addSeLoanForm').find("#houseEstateType").combobox('getValue');
    var customerSource=$('#addSeLoanForm').find("#customerSource").combobox('getValue');
    
    // var category=$('#addSeLoanForm').find("#category").combobox('getValue');
    // var operationSite=$('#addSeLoanForm').find("#operationSite").combobox('getValue');
    var managerName=$('#addSeLoanForm').find("#managerName").combobox('getValue');
    // var professionType =$('#addSeLoanForm').find("#professionType").combobox('getValue');

    var assessorName=$('#addSeLoanForm').find("#assessorName").combobox('getValue');
    
       //贷款状态为退回时，必须选择担保人
       if($('#addSeLoanForm').find("#status").val()==50){         
           var flag = $('#addSeLoanForm').find("#guaranteeFlag0").val();
           var flag2 = $('#addSeLoanForm').find("#guaranteeFlag1").val();   
           if(flag!=1&&flag2!=1){
               $.messager.show({
                    title: 'warning',
                    msg: ' 请选择担保人'
              });
               return false;
           }
           if(flag==1&&$('#addSeLoanForm').find("#guaName0").val()==''){
               $.messager.show({
                    title: 'warning',
                    msg: '请填写担保人姓名'
              });
               return false;
           }
           if(flag2==1&&$('#addSeLoanForm').find("#guaName1").val()==''){
               $.messager.show({
                    title: 'warning',
                    msg: '请填写担保人姓名'
              });
               return false;
           }
           
       }
    var crmId=$('#addSeLoanForm').find("#managerName").combobox('getValue');
    $('#addSeLoanForm').find('#crmId').val(crmId);
    var assessorId=$('#addSeLoanForm').find("#assessorName").combobox('getValue');
    $('#addSeLoanForm').find('#assessorId').val(assessorId);

    var checkContacterIntegrityResult = checkContacterIntegrity('addSeLoanForm');
    if(checkContacterIntegrityResult.status == false){
        $.messager.show({
                title: 'warning',
                msg: checkContacterIntegrityResult.msg
          });
        return false;
    } 
    // var  houseEstateType = $('#addSeLoanForm').find('#houseEstateType').combobox('getValue');
    // //提示填写每月租金
    // if(houseEstateType=='租用'&&$('#addSeLoanForm').find('#rentPerMonth').val()==""){
    //      $.messager.show({
    //             title: 'warning',
    //             msg: '请填写每月租金'
    //       });
    //      return false;
    // }   
    //新增页面联系人人信息校验  
    // if(checkAddContacter()==false){
    //     return false;
    // }
    //新增页面担保人信息校验
    if(checkAddGuaratee() ==false){
        return false;
    }
    // //新增页面担保人信息填充
    addtGuarantee();    
     // 验证FORM   
      if(!$("#addSeLoanForm").form('validate')){
             return false;
     }else{
         $('#submitEditBtnNew').linkbutton({disabled:true});
         $('#closeLoanBtn').linkbutton({disabled:true});
     }
    
     
    $.ajax({
           url : 'apply/addSELoan',
           data : $("#addSeLoanForm").serialize(),
           type:"POST",
           success : function(result){
               $('#submitEditBtnNew').linkbutton({disabled:false});
                $('#closeLoanBtn').linkbutton({disabled:false});
                if(result=='success'){
                    $.messager.show({
                        title : '提示',
                        msg : '保存成功！'
                    });
                    //发送数据到ECIF
                    ecifTrans($("#addSeLoanForm").serialize());
                    //清空小企业贷页面上的数据
                    clrearLoanDate();   
                    $('#seLoanAdd').dialog('close');
                    $('#loanPageTb').datagrid('reload');
                    
                }else{
                    parent.$.messager.show({
                        title: 'Error',
                        msg: result
                    });
                }
           },
           error:function(data){
                 $.messager.show({
                        title: 'warning',
                        msg: data.responseText
                    });
           }
    }); 


    
}

function submitEduLoan () {

    var assessorName=$('#addSeLoanForm').find("#assessorName").combobox('getValue');
   
    var managerName=$('#addSeLoanForm').find("#managerName").combobox('getValue');
    // var salesDeptId=$('#addSeLoanForm').find("#salesDeptId").combobox('getValue');
    var entranceDate=$('#addSeLoanForm').find("#entranceDate").val();

    
    if(entranceDate==''){
         $.messager.show({
                title: 'warning',
                msg: ' 请填写入学时间'
          });
        return false;
    };    

    if(assessorName==''){
         $.messager.show({
                title: 'warning',
                msg: ' 请选择复核人员'
          });
        return false;
    };

    //申请日期
       var requestDate=$('#addSeLoanForm').find("#requestDate").datebox('getValue');
       if(requestDate==''){
           $.messager.show({
                title: 'warning',
                msg: ' 请选择申请日期'
          });          
           return false;
       } ;

       if(managerName==''){
         $.messager.show({
                title: 'warning',
                msg: ' 请选择客户经理'
          });
            return false;
        };
        var  houseEstateType = $('#addSeLoanForm').find('#houseEstateType').combobox('getValue');
        //提示填写每月租金
        if(houseEstateType=='租赁'&&$('#addSeLoanForm').find('#rentPerMonth').val()==""){
             $.messager.show({
                    title: 'warning',
                    msg: '请填写每月租金'
              });
             return false;
        }   

        var  personMarried = $('#addSeLoanForm').find('#personMarried').combobox('getValue');
         if(personMarried=='1'&&$('#addSeLoanForm').find('#childrenNum').val()==""){
             $.messager.show({
                    title: 'warning',
                    msg: '请填写子女数目'
              });
             return false;
        }   

         // 验证FORM   
         if(!$("#addSeLoanForm").form('validate')){
                return false;
        }else{
            $('#submitEditBtnNew').linkbutton({disabled:true});
            $('#closeLoanBtn').linkbutton({disabled:true});
        }

      $.ajax({
           url : 'apply/addEduLoan',
           data : $("#addSeLoanForm").serialize(),
           type:"POST",
           success : function(result){
               $('#submitEditBtnNew').linkbutton({disabled:false});
                $('#closeLoanBtn').linkbutton({disabled:false});
                if(result=='success'){
                    $.messager.show({
                        title : '提示',
                        msg : '保存成功！'
                    });
                    //发送数据到ECIF
                    ecifTrans($("#addSeLoanForm").serialize());
                    //清空小企业贷页面上的数据
                    clrearLoanDate();   
                    $('#seLoanAdd').dialog('close');
                    $('#loanPageTb').datagrid('reload');
                    
                }else{
                    parent.$.messager.show({
                        title: 'Error',
                        msg: result
                    });
                }
           },
           error:function(data){
                 $.messager.show({
                        title: 'warning',
                        msg: data.responseText
                    });
           }
    }); 
}
//新增页面担保人信息填充
function addtGuarantee(){
	for(var i=0;i<4;i++){
		var guaType = $('#addSeLoanForm').find("#guaType"+i).combobox('getValue');
		if(guaType!=''&&guaType==0){//自然人
			$('#addSeLoanForm').find("#guaranteeCompanyFullName"+i).val(null);
			$('#addSeLoanForm').find("#guaranteePhone"+i).val(null);
			$('#addSeLoanForm').find("#guaranteeCompanyAddress"+i).val(null);
			$('#addSeLoanForm').find("#guaranteeZipCode"+i).val(null);
			if($('#addSeLoanForm').find("#guaName"+i).val()==''){//如果名字为空
				$('#addSeLoanForm').find("#guaType"+i).combobox('setValue',null);
				$('#addSeLoanForm').find("#guaSex"+i).combobox('setValue',null);
				$('#addSeLoanForm').find("#guaMarried"+i).combobox('setValue',null);
				$('#addSeLoanForm').find("#guaEducationLevel"+i).combobox('setValue',null);
				$('#addSeLoanForm').find("#guaHasChildren"+i).combobox('setValue',null);
			}
			
		}else if(guaType!=''&&guaType==1){//法人	
			$('#addSeLoanForm').find("#guaName"+i).val($('#addSeLoanForm').find("#guaranteeCompanyFullName"+i).val());
			$('#addSeLoanForm').find("#guaCompanyFullName"+i).val($('#addSeLoanForm').find("#guaranteeCompanyFullName"+i).val());
			$('#addSeLoanForm').find("#guaPhone"+i).val($('#addSeLoanForm').find("#guaranteePhone"+i).val());
			$('#addSeLoanForm').find("#guaCompanyAddress"+i).val($('#addSeLoanForm').find("#guaranteeCompanyAddress"+i).val());
			$('#addSeLoanForm').find("#guaZipCode"+i).val($('#addSeLoanForm').find("#guaranteeZipCode"+i).val());			
			
			if($('#addSeLoanForm').find("#guaranteeCompanyFullName"+i).val()==''){//如果没有填写企业全称
				$('#addSeLoanForm').find("#guaType"+i).combobox('setValue',null);
			}
			//设置自然人为空			
			$('#addSeLoanForm').find("#guaIdnum"+i).val(null);
			$('#addSeLoanForm').find("#guaAddress"+i).val(null);
			$('#addSeLoanForm').find("#guaMobilePhone"+i).val(null);
			$('#addSeLoanForm').find("#guaEmail"+i).val(null);
			$('#addSeLoanForm').find("#guaHomePhone"+i).val(null);
			
			$('#addSeLoanForm').find("#guaSex"+i).combobox('setValue',null);
			$('#addSeLoanForm').find("#guaMarried"+i).combobox('setValue',null);
			$('#addSeLoanForm').find("#guaEducationLevel"+i).combobox('setValue',null);
			$('#addSeLoanForm').find("#guaHasChildren"+i).combobox('setValue',null);
		}
		
	}
	
}

// 新增同城贷联系人信息完整性校验
function checkContacterIntegrity(formID) {
        var contacterAttr = {contacterName:'',relationship:'',mobilePhone:'',workUnit:'',title:'',address:''};
        var result = {status:true,msg:''};
        var errorContacter = '';
    for(var i=0;i<4;i++){
    	for(var field in contacterAttr){
        	if($('#'+formID+' #'+field+i).val()){
                var reloationShip = $('#'+formID+' #relationship'+i).is('select')?$('#'+formID+' #relationship'+i).combobox('getValue'):$('#'+formID+' #relationship'+i).val();
    			if($('#'+formID+' #contacterName'+i).val() == '' || reloationShip == ''){
    				var index = 1+i;
                  errorContacter+='联系人'+index+',';
                  result.status = false;
                  result.msg = errorContacter+'姓名和关系字段必填';                  
                  break;
    			}
    		}
    	};
    }
       
    return result;
}
//新增联系人验证
function checkAddContacter(){
	for(var i=0;i<4;i++){
		if($('#addSeLoanForm').find("#contacterName"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人姓名'
		  	  }); 
			return false;
		}
		if($('#addSeLoanForm').find("#relationship"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人关系'
		  	  }); 
			return false;
		}
		if($('#addSeLoanForm').find("#mobilePhone"+i).val()==''&&$('#addSeLoanForm').find("#homePhone"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '联系人手机号码和固定电话至少要填写一项'
		  	  }); 
			return false;
		}
		if($('#addSeLoanForm').find("#workUnit"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人工作单位'
		  	  }); 
			return false;
		}
	}
}
//编辑联系人验证
function checkEditContacter(){
	for(var i=0;i<4;i++){
		if($('#editLoanForm').find("#contacterName"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人姓名'
		  	  }); 
			return false;
		}
		if($('#editLoanForm').find("#relationship"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人关系'
		  	  }); 
			return false;
		}
		if($('#editLoanForm').find("#mobilePhone"+i).val()==''&&$('#editLoanForm').find("#homePhone"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '联系人手机号码和固定电话至少要填写一项'
		  	  }); 
			return false;
		}
		if($('#editLoanForm').find("#workUnit"+i).val()==''){
			 $.messager.show({
		  			title: 'warning',
		  			msg: '请填写联系人工作单位'
		  	  }); 
			return false;
		}
	}
}
//编辑页面担保人信息填充
function editGuarantee(){
	for(var i=0;i<4;i++){
		$('#editLoanForm').find("#guaranteeId"+i).val($('#editLoanForm').find("#guaranteeId"+i).val());		
		$('#editLoanForm').find("#guaranteeCheck"+i).val($('#editLoanForm').find("#guaranteeCheck"+i).val());	
		var  guaType=$('#editLoanForm').find("#guaType"+i).combobox('getValue');
		if(guaType!=''&&guaType==0){//自然人
			$('#editLoanForm').find("#guaranteeCompanyFullName"+i).val(null);
			$('#editLoanForm').find("#guaranteePhone"+i).val(null);
			$('#editLoanForm').find("#guaranteeCompanyAddress"+i).val(null);
			$('#editLoanForm').find("#guaranteeZipCode"+i).val(null);			
			if($('#editLoanForm').find("#guaName"+i).val()==''){//如果名字为空
				$('#editLoanForm').find("#guaType"+i).combobox('setValue',null);
				$('#editLoanForm').find("#guaSex"+i).combobox('setValue',null);
				$('#editLoanForm').find("#guaMarried"+i).combobox('setValue',null);
				$('#editLoanForm').find("#guaEducationLevel"+i).combobox('setValue',null);
				$('#editLoanForm').find("#guaHasChildren"+i).combobox('setValue',null);
			}			
		}else if(guaType!=''&&guaType==1){//法人	
			$('#editLoanForm').find("#guaName"+i).val($('#editLoanForm').find("#guaranteeCompanyFullName"+i).val());
			$('#editLoanForm').find("#guaCompanyFullName"+i).val($('#editLoanForm').find("#guaranteeCompanyFullName"+i).val());
			$('#editLoanForm').find("#guaPhone"+i).val($('#editLoanForm').find("#guaranteePhone"+i).val());
			$('#editLoanForm').find("#guaCompanyAddress"+i).val($('#editLoanForm').find("#guaranteeCompanyAddress"+i).val());
			$('#editLoanForm').find("#guaZipCode"+i).val($('#editLoanForm').find("#guaranteeZipCode"+i).val());			
			
			if($('#editLoanForm').find("#guaranteeCompanyFullName"+i).val()==''){//如果没有填写企业全称
				$('#editLoanForm').find("#guaType"+i).combobox('setValue',null);
			}
			//设置自然人为空
			
			$('#editLoanForm').find("#guaIdnum"+i).val(null);
			$('#editLoanForm').find("#guaAddress"+i).val(null);
			$('#editLoanForm').find("#guaMobilePhone"+i).val(null);
			$('#editLoanForm').find("#guaEmail"+i).val(null);
			$('#editLoanForm').find("#guaHomePhone"+i).val(null);
			
			$('#editLoanForm').find("#guaSex"+i).combobox('setValue',null);
			$('#editLoanForm').find("#guaMarried"+i).combobox('setValue',null);
			$('#editLoanForm').find("#guaEducationLevel"+i).combobox('setValue',null);
			$('#editLoanForm').find("#guaHasChildren"+i).combobox('setValue',null);
		}
		
	}
	
}
//验证编辑页面
function checkEdit(){
	
	
	var personMarried=$('#editLoanForm').find("#personMarried").combobox('getValue');
	var requestTime=$('#editLoanForm').find("#requestTime").combobox('getValue');
	var personSex=$('#editLoanForm').find("#personSex").combobox('getValue');
	var personEducationLevel=$('#editLoanForm').find("#personEducationLevel").combobox('getValue');
	var personHasChildren=$('#editLoanForm').find("#personHasChildren").combobox('getValue');
	var houseEstateType=$('#editLoanForm').find("#houseEstateType").combobox('getValue');
	var customerSource=$('#editLoanForm').find("#customerSource").combobox('getValue');
	
	var category=$('#editLoanForm').find("#category").combobox('getValue');
	var operationSite=$('#editLoanForm').find("#operationSite").combobox('getValue');
	var managerName=$('#editLoanForm').find("#managerName").combobox('getValue');
	
	var professionType =$('#editLoanForm').find("#professionType").combobox('getValue');

	var assessorName=$('#editLoanForm').find("#assessorName").combobox('getValue');
	
	if(!isNaN(assessorName)){
		
		assessorName=null;
	}
	var serviceId=$('#editLoanForm').find("#serviceId").val();
	//验证职业类型
	if(professionType==''){
	 	 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择职业类型'
	  	  }); 
		return false;
	}
	//验证婚姻状况
	if(personMarried==''){
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
	if(personSex==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择性别'
	  	  });
		return false;
	}
	if(personEducationLevel==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择最高学历'
	  	  });
		return false;
	}
	if(personHasChildren==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择有无子女'
	  	  });
		return false;
	}
	if(houseEstateType==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请选择房产类型'
	  	  });
		return false;
	}
	if(customerSource==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择客户来源'
	  	  });
		return false;
	}
	if(managerName==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择客户经理'
	  	  });
		return false;
	}
	if(operationSite==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择经营场所'
	  	  });
		return false;
	}
	if(assessorName==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择复核人员'
	  	  });
		return false;
	}
	if(assessorName==serviceId){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 复核人员和客服不可以是同一人'
	  	  });
		return false;
	}
	if(category==''){
		 $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择企业类型'
	  	  });
		return false;
	}
	
	//成立时间
	var foundedDate=$('#editLoanForm').find("#foundedDate").datebox('getValue');
	   if(foundedDate==''){
		   $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择成立时间'
	  	  });
		   return false;
	   }
	//申请日期
	   var requestDate=$('#editLoanForm').find("#requestDate").datebox('getValue');
	   if(requestDate==''){
		   $.messager.show({
	  			title: 'warning',
	  			msg: ' 请选择申请日期'
	  	  });		   
		   return false;
	   } 
	var crmId=$('#editLoanForm').find("#managerName").combobox('getValue');
	$('#editLoanForm').find('#crmId').val(crmId);
	var assessorId=$('#editLoanForm').find("#assessorName").combobox('getValue');
	$('#editLoanForm').find('#assessorId').val(assessorId);
	
	var  houseEstateType = $('#editLoanForm').find('#houseEstateType').combobox('getValue');
	//提示填写每月租金
	if(houseEstateType=='租用'&&$('#editLoanForm').find('#rentPerMonth').val()==""){
		 $.messager.show({
	  			title: 'warning',
	  			msg: '请填写每月租金'
	  	  });
		 return false;
	}	
	
	//担保的信息校验
	checkGuaratee();
}
//新建页面担保人的校验
function checkAddGuaratee(){
	var sum =0;//选中的个数
	for(var i=0;i<4;i++){
		//是否选中 flag==1 为选中
		
		var flag =  $('#addSeLoanForm').find("#guaranteeFlag"+i).val();
		//自然人
		var guaName = $('#addSeLoanForm').find("#guaName"+i).val();
		var guaIdnum = $('#addSeLoanForm').find("#guaIdnum"+i).val();	
		var guaSex = $('#addSeLoanForm').find("#guaSex"+i).combobox('getValue');	
		var guaMarried = $('#addSeLoanForm').find("#guaMarried"+i).combobox('getValue');	
		var guaEducationLevel = $('#addSeLoanForm').find("#guaEducationLevel"+i).combobox('getValue');	
		var guaHasChildren = $('#addSeLoanForm').find("#guaHasChildren"+i).combobox('getValue');	
		var guaAddress = $('#addSeLoanForm').find("#guaAddress"+i).val();		
		var guaZipCode = $('#addSeLoanForm').find("#guaZipCode"+i).val();	
		var guaMobilePhone = $('#addSeLoanForm').find("#guaMobilePhone"+i).val();
		var guaEmail = $('#addSeLoanForm').find("#guaEmail"+i).val();	
		var guaCompanyFullName = $('#addSeLoanForm').find("#guaCompanyFullName"+i).val();
		var guaCompanyAddress = $('#addSeLoanForm').find("#guaCompanyAddress"+i).val();
		//法人		
		var guaranteeCompanyFullName = $('#addSeLoanForm').find("#guaranteeCompanyFullName"+i).val();
		var guaranteeCompanyAddress = $('#addSeLoanForm').find("#guaranteeCompanyAddress"+i).val();
		var guaranteeZipCode = $('#addSeLoanForm').find("#guaranteeZipCode"+i).val();
		
		if(flag==1){//选中
			sum+=1;
			var guaType = $('#addSeLoanForm').find("#guaType"+i).combobox('getValue');	
			
			if(guaType!=''&&guaType==0){//自然人	
				
				if(guaName==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人姓名'
			  	  });
					return  false;
				}
				if(guaIdnum==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人身份证号'
			  	  });
					return  false;
				}
				if(guaSex==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请选择担保人性别'
			  	  });
					return  false;
				}
				if(guaMarried==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: '请选择担保人婚姻状况'
			  	  });
					return  false;
				}
				if(guaEducationLevel==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: '请选择担保人学历'
			  	  });
					return  false;
				}
				if(guaHasChildren==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: '请选择担保人子女'
			  	  });
					return  false;
				}
				if(guaAddress==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人住宅地址'
			  	  });
					return  false;
				}
				if(guaZipCode==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人邮政编码'
			  	  });
					return  false;
				}
				if(guaMobilePhone==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人手机号码'
			  	  });
					return  false;
				}
				if(guaEmail==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人常用邮箱'
			  	  });
					return  false;
				}
				if(guaCompanyFullName==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人企业全称'
			  	  });
					return  false;
				}
				if(guaCompanyAddress==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人企业地址'
			  	  });
					return  false;
				}
			
				}else if(guaType!=''&&guaType==1){//法人
					
					if(guaranteeCompanyFullName==''){
						$.messager.show({
				  			title: 'warning',
				  			msg: ' 请填写担保人企业全称'
				  	  });
						return false;
					}
					if(guaranteeCompanyAddress==''){				
						$.messager.show({
				  			title: '提示',
				  			msg: ' 请填写担保人企业地址'
				  	  });
						return  false;
					}
					if(guaranteeZipCode==''){				
						$.messager.show({
				  			title: '提示',
				  			msg: ' 请填写担保人邮政编码'
				  	  });
						return  false;
					}
				}
				
			}
		}

}
//有条件同意下担保人的校验
function checkGuaratee(){

	var sum =0;//选中的个数
	//选中的自然人个数
	var naturalGuaranteeCount = 0;
	//选中的法人个数
	var legalGuaranteeCount = 0;
	//指定的自然人个数
	var naturalCount = 0;
	//指定的法人个数
	var legalCount = 0;
	var checkNameResult =null;
	var naturalGuaranteeName1 = $('#editLoanForm').find("#naturalGuaranteeName1").val();
	var naturalGuaranteeName2 = $('#editLoanForm').find("#naturalGuaranteeName2").val();
	var legalGuaranteeCname1 = $('#editLoanForm').find("#legalGuaranteeCname1").val();
	var legalGuaranteeCname2 = $('#editLoanForm').find("#legalGuaranteeCname2").val();
    // var appointedGuar = [naturalGuaranteeName1,naturalGuaranteeName2,legalGuaranteeCname1,legalGuaranteeCname2];
    
	if(naturalGuaranteeName1!=''){
		naturalCount+=1;
	}
	if(naturalGuaranteeName2!=''){
		naturalCount+=1;
	}
	if(legalGuaranteeCname1!=''){
		legalCount+=1;
	}
	if(legalGuaranteeCname2!=''){
		legalCount+=1;
	}
	for(var i=0;i<4;i++){
        
        
    
        //自然人
        var guaName = $('#editLoanForm').find("#guaName"+i).val();
        var guaIdnum = $('#editLoanForm').find("#guaIdnum"+i).val();    
        var guaSex = $('#editLoanForm').find("#guaSex"+i).combobox('getValue'); 
        var guaMarried = $('#editLoanForm').find("#guaMarried"+i).combobox('getValue'); 
        var guaEducationLevel = $('#editLoanForm').find("#guaEducationLevel"+i).combobox('getValue');   
        var guaHasChildren = $('#editLoanForm').find("#guaHasChildren"+i).combobox('getValue'); 
        var guaAddress = $('#editLoanForm').find("#guaAddress"+i).val();        
        var guaZipCode = $('#editLoanForm').find("#guaZipCode"+i).val();    
        var guaMobilePhone = $('#editLoanForm').find("#guaMobilePhone"+i).val();
        var guaEmail = $('#editLoanForm').find("#guaEmail"+i).val();    
        var guaCompanyFullName = $('#editLoanForm').find("#guaCompanyFullName"+i).val();
        var guaCompanyAddress = $('#editLoanForm').find("#guaCompanyAddress"+i).val();
        //法人        
        var guaranteeCompanyFullName = $('#editLoanForm').find("#guaranteeCompanyFullName"+i).val();
        var guaranteeCompanyAddress = $('#editLoanForm').find("#guaranteeCompanyAddress"+i).val();
        var guaranteeZipCode = $('#editLoanForm').find("#guaranteeZipCode"+i).val();

       
        
		//是否选中 flag==1 为选中
		var flag =  $('#editLoanForm').find("#guaranteeFlag"+i).val();
        if(flag==1){//选中            
            sum+=1;
            var guaType = $('#editLoanForm').find("#guaType"+i).combobox('getValue');   
            
            if(guaType!=''&&guaType==0){//自然人   
                naturalGuaranteeCount+=1;
                if(guaName==''){
				  $.messager.show({
						title: '提示',
			  			msg: ' 请填写担保人姓名'
			  	  });
					return  false;
				}
				if(guaIdnum==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人身份证号'
			  	  });
					return  false;
				}
				// if(guaSex==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请选择担保人性别'
			 //  	  });
				// 	return  false;
				// }
				// if(guaMarried==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: '请选择担保人婚姻状况'
			 //  	  });
				// 	return  false;
				// }
				// if(guaEducationLevel==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: '请选择担保人学历'
			 //  	  });
				// 	return  false;
				// }
				// if(guaHasChildren==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: '请选择担保人子女'
			 //  	  });
				// 	return  false;
				// }
				// if(guaAddress==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请填写担保人住宅地址'
			 //  	  });
				// 	return  false;
				// }
				// if(guaZipCode==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请填写担保人邮政编码'
			 //  	  });
				// 	return  false;
				// }
				if(guaMobilePhone==''){				
					$.messager.show({
			  			title: '提示',
			  			msg: ' 请填写担保人手机号码'
			  	  });
					return  false;
				}
				// if(guaEmail==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请填写担保人常用邮箱'
			 //  	  });
				// 	return  false;
				// }
				// if(guaCompanyFullName==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请填写担保人企业全称'
			 //  	  });
				// 	return  false;
				// }
				// if(guaCompanyAddress==''){				
				// 	$.messager.show({
			 //  			title: '提示',
			 //  			msg: ' 请填写担保人企业地址'
			 //  	  });
				// 	return  false;
				// }
				
				if(guaName!=naturalGuaranteeName1 && guaName!=naturalGuaranteeName2){
							  checkNameResult='填写的担保人和审批人指定的担保人不一致';
				  }		  
				}else if(guaType!=''&&guaType==1){//法人
					legalGuaranteeCount+=1;					
					if(guaranteeCompanyFullName==''){
						$.messager.show({
				  			title: 'warning',
				  			msg: ' 请填写担保人企业全称'
				  	  });
						return false;
					}
					// if(guaranteeCompanyAddress==''){				
					// 	$.messager.show({
				 //  			title: '提示',
				 //  			msg: ' 请填写担保人企业地址'
				 //  	  });
					// 	return  false;
					// }
					// if(guaranteeZipCode==''){				
					// 	$.messager.show({
				 //  			title: '提示',
				 //  			msg: ' 请填写担保人邮政编码'
				 //  	  });
					// 	return  false;
					// }
					if(guaranteeCompanyFullName!=legalGuaranteeCname1&&guaranteeCompanyFullName!=legalGuaranteeCname2){
						checkNameResult='填写的担保人和审批人指定的担保人不一致';
					}
				
			}
		}
		
	}

    	if(legalGuaranteeCount>2||naturalGuaranteeCount>2){
    		$.messager.show({
      			title: '提示',
      			msg: ' 最多可指定两个法人或者两个自然人担保'
      	  });
    	   return false;
    	}
	/*
	if(legalGuaranteeCount!=legalCount||naturalGuaranteeCount!=naturalCount){
		$.messager.show({
  			title: '提示',
  			msg: ' 指定的法人或者自然人担保和选中的数量不一致'
  	  });
	   return false;		
	}	*/
	// if($('#editLoanForm').find("#status").val()==50&&sum==0){//有条件同意下没有选择担保
	// 	$.messager.show({
 //  			title: '提示',
 //  			msg: ' 请至少选择一个担保'
 //  	  });
	//    return false;
	// }
	if(checkNameResult!=null||legalGuaranteeCount!=legalCount||naturalGuaranteeCount!=naturalCount){
		  $.messager.confirm('提示','填写的担保人和审批人指定的担保不一致',function(r){					 
			  if(r){//点击确认
				//提交编辑页面
				//   editGuarantee();
				// editSubmit();  
                return  false;
			  }else{//点击取消
				  return  false;
			  }
		   });
	   }else{
		   //提交编辑页面
		   editGuarantee();			 
		   editSubmit();
	   
	   }

}
//把编辑小企业贷数据提交到后台
function submitEdit() {
    
    if(checkEditContacter()==false){
        return false;
    }
    //校验
    if($('#editLoanForm').find("#status").val()==50){
        //担保的信息校验
        checkGuaratee();
    }else{
        if(!$("#editLoanForm").form('validate')){
            return false;
        }
        //编辑页面担保人信息填充
        checkEdit();
    }
};
function submitMultiGuaEdit(){
     //校验
    if($('#editLoanForm').find("#status").val()==50){
        $('#editLoanForm').find("#memberType").combobox('setValue',null);  
    }
        //担保的信息校验
        checkMultiGua();
    // }else{
        //  if(!$("#editLoanForm").form('validate')){
        //     return false;
        // }
        //编辑页面担保人信息填充
        // checkEdit();
    // }

}
function checkMultiGua(){

    for(var i=0;i<4;i++){  
    
        //自然人
        var guaName = $('#editLoanForm').find("#guaName"+i).val();
        var guaIdnum = $('#editLoanForm').find("#guaIdnum"+i).val();    
       
        var guaMobilePhone = $('#editLoanForm').find("#guaMobilePhone"+i).val();      
       
        
        //是否选中 flag==1 为选中
        var flag =  $('#editLoanForm').find("#guaranteeFlag"+i).val();
        if(flag==1){//选中            
            // sum+=1;
            // $('#editLoanForm').find("#guaType"+i).combobox({  disabled:false });
            var guaType = $('#editLoanForm').find("#guaType"+i).combobox('getValue');              
            if(guaType!=''&&guaType==0){//自然人   
                
                if(guaIdnum==''){               
                    $.messager.show({
                        title: '提示',
                        msg: ' 请填写担保人身份证号'
                  });
                    return  false;
                }
               
                if(guaMobilePhone==''){             
                    $.messager.show({
                        title: '提示',
                        msg: ' 请填写担保人手机号码'
                  });
                    return  false;
                }         
                
            }               
        }
        
    };
     editGuarantee();            
        editSubmit();
}

function submitEduEdit(){
    //  var entranceDate=$('#editLoanForm').find("#entranceDate").val();

    
    // if(entranceDate==''){
    //      $.messager.show({
    //             title: 'warning',
    //             msg: ' 请填写入学时间'
    //       });
    //     return false;
    // };    

    editSubmit();

}
//编辑页面提交
function editSubmit(){
	   var assessorName=$('#editLoanForm').find("#assessorName").combobox('getValue');	
		if(isNaN(assessorName)){
			$('#editLoanForm').find("#assessorId").val(null);
			$('#editLoanForm').find("#assessorName").combobox('setValue',null);		
		}else{
			$('#editLoanForm').find("#assessorId").val(assessorName);
		};

        var crmId=$('#editLoanForm').find("#managerName").combobox('getValue');
        $('#editLoanForm').find('#crmId').val(crmId);
        var assessorId=$('#editLoanForm').find("#assessorName").combobox('getValue');
        $('#editLoanForm').find('#assessorId').val(assessorId);

        var  houseEstateType = $('#editLoanForm').find('#houseEstateType').combobox('getValue');
            //提示填写每月租金
            if(houseEstateType=='租用' || houseEstateType=='租赁'){
                if ($('#editLoanForm').find('#rentPerMonth').val()=="") {


                     $.messager.show({
                            title: 'warning',
                            msg: '请填写每月租金'
                      });
                     return false;

                };
            }

        var  personMarried = $('#editLoanForm').find('#personMarried').combobox('getValue');
         if(personMarried=='1'&&$('#editLoanForm').find('#childrenNum').val()==""){
             $.messager.show({
                    title: 'warning',
                    msg: '请填写子女数目'
              });
             return false;
        }   

                
	   $.ajax({
		   url : 'apply/modifySELoan',
		   data : $("#editLoanForm").serialize(),
		   type:"POST",
		   async:false,  // 设置同步方式
	       cache:false,
		   success : function(result){
			   $.messager.progress('close');
		   		if(result=='success'){
		   			$.messager.show({
						title : '提示',
						msg : '保存成功！'
					});
		   			//清空小企业贷页面上的数据
		   		    clrearLoanDate();	
		   			$('#seLoanModify').dialog('close');
		   			$('#loanPageTb').datagrid('reload');
		   		}else{
		   			parent.$.messager.show({
						title: 'Error',
						msg: result
					});
		   		}
		   },
		   error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText
					});
		   }
	});	 

}
/**获取贷款详细信息*/
function getSeLoanDetails(loanId,flag){
    var response = $.ajax({
        type: "POST",
        url: "apply/toSELoanDetail",
        dataType: "json",
        async:false,
        data: {
            loanId:loanId,
            flag:flag
        },
        error:function(){
            $.messager.show({
                title:'加载贷款信息',
                msg:'加载贷款信息失败！',
                showType:'slide'
            });
        }
    });
    return response.responseText;
}
/**查看小企业贷*/
function doSeLoan(loanId,customizeURL,loadCustomizeData){
	var flag='apply';
    var strData = getSeLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height() ;
 //   $('#browseDlg').dialog({modal:true,height:h*(0.8)}).dialog('open').dialog('setTitle', '　');
    var url; 
    var url = 'apply/getHtml?'+'productCode='+loanDetails.product.productCode+'&handler='+'Detail';   
    if(typeof customizeURL === 'string') url = customizeURL;
    $('#seLoanDetail').dialog({
        title: '查看小企业贷',
        width: 1100,
        height:h*0.8,
        closed: false,  
        cache: false,
        href: url,
        modal: true,
        onLoad : function(){
            renderOtherField(loanDetails,'loanBrowseTab');           
            

         
            
        	if(loanDetails.product) {
                $('#browseForm #productName').text(loanDetails.product.productName);
            }
            if(loanDetails.loan) {
                $('#browseForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
                $('#browseForm #requestTime').text(loanDetails.loan.requestTime + "期");
                $('#browseForm #purpose').text(loanDetails.loan.purpose);
            }
            if(loanDetails.person) {
                $('#browseForm #personName').text(loanDetails.person.name);
                $('#browseForm #personSex').text(formatSex(loanDetails.person.sex));
                $('#browseForm #personIdnum').text(loanDetails.person.idnum);
                $('#browseForm #personMarried').text(formatMarried(loanDetails.person.married));
                $('#browseForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
                $('#browseForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));              
                $('#browseForm #personZipCode').text(loanDetails.person.zipCode);
                $('#browseForm #personAddress').text(loanDetails.person.address);
                $('#browseForm #personMobilePhone').text(loanDetails.person.mobilePhone);
                $('#browseForm #personEmail').text(loanDetails.person.email);
                $('#browseForm #personHomePhone').text(transferUndefined(loanDetails.person.homePhone));
               if(loanDetails.person.professionType){
                    $('#browseForm').find('#professionType').val(loanDetails.person.professionType);//职业类型
                }
                // 根据房产类型判断租金和房贷显示与否
                // 规则，如果房产类型是商品房、经济适用房、自建房则显示房贷
                // 如果是租用 则显示每月租金
                // 如果是亲戚住房则租金和房贷均没有
                $('#browseForm #personHouseEstateType').text(loanDetails.person.houseEstateType);
                var personHouseTR = $('#browseForm #personHouseEstateType').parent().parent();
                if(loanDetails.person.houseEstateType == '商品房' || 
                		loanDetails.person.houseEstateType == '经济适用房' || 
                		loanDetails.person.houseEstateType == '自建房'){
                	personHouseTR.find(':nth-child(3)').hide();
                	personHouseTR.find(':nth-child(4)').hide();
                	personHouseTR.find(':nth-child(5)').show();
                	personHouseTR.find(':nth-child(6)').show();
                	
                	$('#browseForm #personHasHouseLoan').text(formatHave(loanDetails.person.hasHouseLoan));
                }
                if(loanDetails.person.houseEstateType == '租用'){
                	personHouseTR.find(':nth-child(3)').show();
                	personHouseTR.find(':nth-child(4)').show();
                	personHouseTR.find(':nth-child(5)').hide();
                	personHouseTR.find(':nth-child(6)').hide();

                	$('#browseForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
                }
                if(loanDetails.person.houseEstateType == '亲戚住房'){
                	personHouseTR.find(':nth-child(3)').hide();
                	personHouseTR.find(':nth-child(4)').hide();
                	personHouseTR.find(':nth-child(5)').hide();
                	personHouseTR.find(':nth-child(6)').hide();
                }
                
                $('#browseForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress); 
                if(loanDetails.person.incomePerMonth){
                    $('#browseForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth +"万元/月");

                }
               //职业类型
                $('#browseForm #professionType').text(loanDetails.person.professionType);
               
            }
            if(loanDetails.company) {
                $('#browseForm #companyName').text(loanDetails.company.name);
                $('#browseForm #companyIndustryInvolved').text(loanDetails.company.industryInvolved);
                $('#browseForm #companyLegalRepresentative').text(loanDetails.company.legalRepresentative);
                $('#browseForm #companyLegalRepresentativeId').text(loanDetails.company.legalRepresentativeId);
                $('#browseForm #companyIncomePerMonth').text(loanDetails.company.incomePerMonth + "万元/月");
                $('#browseForm #companyFoundedDate').text(getYMD(loanDetails.company.foundedDate));
                loanDetails.company.category==3;
                $('#browseForm #companyCategory').text(formatCompanyCategory(loanDetails.company.category));
                $('#browseForm #companyAddress').text(loanDetails.company.address);
                $('#browseForm #companyAvgProfitPerYear').text(loanDetails.company.avgProfitPerYear + "万元/年");
                $('#browseForm #companyPhone').text(transferUndefined(transferUndefined(loanDetails.company.phone)));
                $('#browseForm #companyZipCode').text(loanDetails.company.zipCode);
                $('#browseForm #companyOperationSite').text(loanDetails.company.operationSite);
                $('#browseForm #companyMajorBusiness').text(loanDetails.company.majorBusiness);
                $('#browseForm #companyEmployeesNumber').text(loanDetails.company.employeesNumber);
                $('#browseForm #companyEmployeesWagesPerMonth').text(loanDetails.company.employeesWagesPerMonth + "万元/月");
            }
            if(loanDetails.service) {
                $('#browseForm #serviceName').text(loanDetails.service.name);
            }
            if(loanDetails.loan) {
                $('#browseForm #customerSource').text(loanDetails.loan.customerSource);
                $('#browseForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
            }
            if(loanDetails.crm) {
                $('#browseForm #crmCode').text(loanDetails.crm.code);
                $('#browseForm #crmName').text(loanDetails.crm.name);
            }
            if(loanDetails.salesDept) {
                $('#browseForm #salesDeptName').text(loanDetails.salesDept.name);
            }
            if(loanDetails.assessor) {
                $('#browseForm #assessorName').text(loanDetails.assessor.name);
            }
            if(loanDetails.loan.remark) {
                $('#browseForm #remark').text(loanDetails.loan.remark);
            }
            // 清空联系人列表（除了模板）
            
            $('#contacterBrowseTab > #contacterBrowsePanelTemplate ~ div').remove();
            if(loanDetails.contacterList) {
                for(var i =0;i<loanDetails.contacterList.length;i++){
                    var contacter = loanDetails.contacterList[i];
                    var contacterBrowsePanel =  $('#contacterBrowsePanelTemplate').clone().show().addClass('easyui-panel');
                    var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
                    contacterBrowsePanel.attr("id",contacterBrowsePanelId);
                    contacterBrowsePanel.attr("title","联系人"+(i+1));

                    contacterBrowsePanel.find('#contacterName').text(contacter.name);
                    contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
                    contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
                    contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
                    contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
                    contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
                    contacterBrowsePanel.find('#title').text(contacter.title);
                    contacterBrowsePanel.find('#address').text(contacter.address);


                    contacterBrowsePanel.appendTo($('#contacterBrowseTab'));
                }
                $.parser.parse('#contacterBrowseTab');
            }

            $('#guaranteeBrowseTab > #guaranteeBrowsePanelTemplate ~ div').remove();
            if(loanDetails.guaranteeList) {
                for(var i =0;i<loanDetails.guaranteeList.length;i++){
                    var guarantee = loanDetails.guaranteeList[i];
                    var guaranteeBrowsePanel =  $('#guaranteeBrowsePanelTemplate').clone().show().addClass('easyui-panel');
                    var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;                  
                    guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
                    guaranteeBrowsePanel.attr("title","担保人"+(i+1));
                    if(loanDetails.guaranteeList[i].flag){
                    	 guaranteeBrowsePanel.find('#flag').text("该担保人为指定担保人");
                	}
                    if(guarantee.guaranteeType==0){//自然人
                    	 guaranteeBrowsePanel.find('#guaranteeName').text(guarantee.name);
                         guaranteeBrowsePanel.find('#guaranteeType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
                         guaranteeBrowsePanel.find('#guaranteeIdnum').text(transferUndefined(guarantee.idnum));
                         guaranteeBrowsePanel.find('#guaranteeSex').text(transferUndefined(formatSex(guarantee.sex)));
                         guaranteeBrowsePanel.find('#guaranteeMarried').text(transferUndefined(formatMarried(guarantee.married)));
                         guaranteeBrowsePanel.find('#guaranteeEducationLevel').text(transferUndefined(guarantee.educationLevel));
                         guaranteeBrowsePanel.find('#guaranteeHasChildren').text(transferUndefined(formatYes(guarantee.hasChildren)));
                         guaranteeBrowsePanel.find('#guaranteeAddress').text(transferUndefined(guarantee.address));
                         guaranteeBrowsePanel.find('#guaranteeMobilePhone').text(transferUndefined(guarantee.mobilePhone));
                         guaranteeBrowsePanel.find('#guaranteeEmail').text(transferUndefined(guarantee.email));
                         guaranteeBrowsePanel.find('#personHomePhone').text(transferUndefined(guarantee.homePhone));
                         guaranteeBrowsePanel.find('#guaranteeCompanyFullName').text(transferUndefined(guarantee.companyFullName));
                         guaranteeBrowsePanel.find('#guaranteeZipCode').text(transferUndefined(guarantee.zipCode));
                         guaranteeBrowsePanel.find('#guaranteeCompanyAddress').text(transferUndefined(guarantee.companyAddress));
                         guaranteeBrowsePanel.find('#guaranteeCompanyPhone').text(transferUndefined(guarantee.companyPhone));
                        
                        guaranteeBrowsePanel.find('#tr7').hide();
                        guaranteeBrowsePanel.find('#tr8').hide();
                       
                    }else if(guarantee.guaranteeType==1){//法人
                    	 guaranteeBrowsePanel.find('#tr1').hide();
                         guaranteeBrowsePanel.find('#tr2').hide();
                         guaranteeBrowsePanel.find('#tr3').hide();
                         guaranteeBrowsePanel.find('#tr4').hide();
                         guaranteeBrowsePanel.find('#tr5').hide();
                         guaranteeBrowsePanel.find('#tr6').hide();                      
                    	 guaranteeBrowsePanel.find('#tr7').show();
                         guaranteeBrowsePanel.find('#tr8').show(); 
                         guaranteeBrowsePanel.find('#guaType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
                         guaranteeBrowsePanel.find('#guaCompanyFullName').text(transferUndefined(guarantee.companyFullName));
                         guaranteeBrowsePanel.find('#guaZipCode').text(transferUndefined(guarantee.zipCode));
                         guaranteeBrowsePanel.find('#guaCompanyAddress').text(transferUndefined(guarantee.companyAddress));
                         guaranteeBrowsePanel.find('#guaCompanyPhone').text(transferUndefined(guarantee.companyPhone));
                    	
                    }
                   

                    guaranteeBrowsePanel.appendTo($('#guaranteeBrowseTab'));
                }
            }
            if(typeof loadCustomizeData === 'function') loadCustomizeData(loanDetails,'browseForm');
           
        }
    });
    
        $.parser.parse($('#guaranteeBrowseTab').parent());
    
};
//取消新增 小企业贷
function closeLoanAdd() {
    $('#seLoanAdd').dialog('close');
    _step=1;

};
//取消编辑小企业贷
function closeLoanModify() {
    $('#seLoanModify').dialog('close');
    _step=1;

};
//同城贷特有数据加载
function loadExistedCityWideLoan(loanDetailsVo,form){
    var productId;
    var posOpenDate;

    if (loanDetailsVo.product != undefined)  
      productId = loanDetailsVo.product.id  
    else  productId = loanDetailsVo.productId ;     
    productId == 5?$('#'+form+' #cityWideSeloanOwn').remove() : $('#'+form+' #cityWidePOSLoanOwn').remove()  ;
    
    posOpenDate =  $('#'+form+' #posOpenDate');

    $('#'+form+' #productNames').text(loanDetailsVo.product == undefined?loanDetailsVo.productName:loanDetailsVo.product.productName);
    var attName = form.indexOf('browse')>-1?  'text': 'val';
    if (loanDetailsVo.company) {
        if ($(posOpenDate).length > 0){
            $(posOpenDate).is('label') ? $(posOpenDate).text(getYMD(loanDetailsVo.company.posOpenDate)) : $(posOpenDate).datebox('setValue',getYMD(loanDetailsVo.company.posOpenDate));
            $('#'+form+' #posAcquirer')[attName](loanDetailsVo.company.posAcquirer);

            $('#'+form+' #posCapitavolume')[attName](loanDetailsVo.company.posCapitavolume);

        }else 
            $('#'+form+' #companyDebtBalance')[attName](loanDetailsVo.company.companyDebtBalance); 

            
        $('#'+form+' #monthTurnOver')[attName](loanDetailsVo.company.monthTurnOver);

    }

    if (loanDetailsVo.loan) 
    $('#'+form+' #sourceOfRepay')[attName](loanDetailsVo.loan.sourceOfRepay);   

    
    if (loanDetailsVo.person) {
        $('#'+form+' #placeDomicile')[attName](loanDetailsVo.person.placeDomicile);
        $('#'+form+' #ratioOfInvestments')[attName](loanDetailsVo.person.ratioOfInvestments);
        $('#'+form+' #monthOfProfit')[attName](loanDetailsVo.person.monthOfProfit);
        $('#'+form+' #monthRepayAmount')[attName](loanDetailsVo.person.monthRepayAmount);
        $('#'+form+' #personDebtBalance')[attName](loanDetailsVo.person.personDebtBalance);

        
    };  

    

};
function submitCityWideLoanEdit(){

    // if(checkEditContacter()==false){
    //     return false;
    // }
   
    //校验
    if($('#editLoanForm').find("#status").val()==50){
        //担保的信息校验
       
        // checkGuaratee();
         // checkCityWideLoanEdit();
         checkMultiGua();
    }else{
        if(!$("#editLoanForm").form('validate')){
            return false;
        }
        checkCityWideLoanEdit();
    }
};

function checkCityWideLoanEdit(){


    // var personMarried=$('#editLoanForm').find("#personMarried").combobox('getValue');
    // var requestTime=$('#editLoanForm').find("#requestTime").combobox('getValue');
    // var personSex=$('#editLoanForm').find("#personSex").combobox('getValue');
    // var personEducationLevel=$('#editLoanForm').find("#personEducationLevel").combobox('getValue');
    // var houseEstateType=$('#editLoanForm').find("#houseEstateType").combobox('getValue');
    // var customerSource=$('#editLoanForm').find("#customerSource").combobox('getValue');
    
    var managerName=$('#editLoanForm').find("#managerName").combobox('getValue');

    var assessorName=$('#editLoanForm').find("#assessorName").combobox('getValue');
    
    if(!isNaN(assessorName)){
        
        assessorName=null;
    }
    var serviceId=$('#editLoanForm').find("#serviceId").val();
 
   
    if(assessorName==serviceId){
         $.messager.show({
                title: 'warning',
                msg: ' 复核人员和客服不可以是同一人'
          });
        return false;
    }
    var checkContacterIntegrityResult = checkContacterIntegrity('editLoanForm');
    if(checkContacterIntegrityResult.status == false){
        $.messager.show({
                title: 'warning',
                msg: checkContacterIntegrityResult.msg
          });
        return false;
    } 
    var crmId=$('#editLoanForm').find("#managerName").combobox('getValue');
    $('#editLoanForm').find('#crmId').val(crmId);
    var assessorId=$('#editLoanForm').find("#assessorName").combobox('getValue');
    $('#editLoanForm').find('#assessorId').val(assessorId);
    
    
    checkGuaratee();

};
function seLoanCityWideLoan(url,title,loanDetailsVo,dialogID,formID){
              $('#seLoan'+dialogID).dialog({
                title: title,
                closed: false,  
                cache: false,
                href: url,
                modal: true,
                onLoad : function(){
                // 营业网点及相应客户经理加载
                      $('#'+formID+' #salesDeptId').combobox({
                            url:'apply/getSalesDeptsInCurCity',
                            valueField:'id',
                            textField:'name',
                            onLoadSuccess:function(){
                                var data = $(this).combobox('getData');
                                if(data.length==1)
                                    $(this).combobox('select', data[0].id);
                            },
                            onChange:function(newVal, oldVal){ 
                            $('#'+formID+' #managerName').combobox({     
                                url:'apply/getCrmsInSalesDeptByProductIdAndSalesDeptId',
                                  valueField:'id',
                                  textField:'name',
                                  onBeforeLoad: function(param){                              
                                    param.productId = loanDetailsVo.productId;  
                                    param.salesDeptId = newVal;
                                  },
                                  onLoadSuccess:function(){
                                	  if(loanDetailsVo.loanId){
                                          $(this).combobox('select', loanDetailsVo.crm.id);
                                      }else{
                                         var data = $(this).combobox('getData');
                                         if(data.length>0)
                                           $(this).combobox('select', data[0].id);

                                      }
                                  }
                                     
                                           
                                    
                              }); 
                          }
                        });
                  
                        //复核人员
                        $('#'+formID+' #assessorName').combobox({
                        url:'apply/getServicesInCurSalesDeptByProductId',
                        valueField:'id',
                        textField:'name',
                        editable:false ,
                        onBeforeLoad: function(param){                  
                        param.productId = loanDetailsVo.productId; 
                                       
                            if(loanDetailsVo.service){
                               param.userId =  loanDetailsVo.service.id;
                            }                    
                          },
                          onSelect:function(newVal, oldVal) {
                              
                              if(newVal.id == $('#'+formID+' #serviceId').val()){
                                  $.messager.show({
                                       title:'提示',
                                       msg:'复核人员和客服不可以是同一人',
                                       showType:'slide'
                                   });
                                  $(this).combobox('unselect', newVal.id);
                                  return false;
                                 
                               }

                            $('#'+formID+' #assessorId').val(newVal.id);
                          }


                      }); 
                   


                    if ('Add' == dialogID )
                        "新客户" == loanDetailsVo.personType ? loadAddData(loanDetailsVo) : loadOldData(loanDetailsVo)                      
                    else loadLoanDate(loanDetailsVo);
                    loadExistedCityWideLoan(loanDetailsVo,formID);                
            
              }});   
};




