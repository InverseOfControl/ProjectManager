;var eduLoanApply_plugin = function ($) {
    var dataTag = 'eduLoanApply';




  

    function initialize(self) {

        

    	var loanDetails = commonApplyDataModule_plugin.prototype.loanDetails;
        $(self).find('#organID').combobox({
            
            valueField: 'id',
            textField: 'name',
            data: loanDetails.organList,
            onSelect:function(value){
             
                 $(self).find('#schemeID').combobox({
                    url:'channelPlan/getSearchJsonAll?approverState=3&organizationId='+value.id,
                    valueField: 'id',
                    textField: 'name',
                    onSelect:function(value){
                         $(self).find('#requestTimeValue').val(value.time);
                         $(self).find('#requestTimeText').text(value.time);
                         $(self).find('#requestMoney').val(value.requestMoney);
                         $(self).find('#requestMoneyText').text(value.requestMoney);
                            var oper = $('<a></a>');
                            $(oper).attr('href','javascript:void(0);');
                            $(oper).css({'color':'blue'});
                            $(oper).text('查看方案信息');
                            $(oper).bind('click',value.id,loadChannelPlanInfoToWindow);
                             $(self).find('#browseScheme').empty().append(oper);
                        
                    }
                }); 
				
			     $(self).find('#organCode').text(value.code);
                 if($(self).find( '#salesDeptId').hasClass('easyui-combobox')){

                    $(self).find( '#salesDeptId').combobox('select',$(self).find('#salesDeptId').combobox('getValue'));
                 }
                 


            }
         	});  

	         $(self).find('.zxIncomeSource.easyui-numberbox').numberbox({
	            onChange : function(value){
	                var valueEleId = $(this).attr('valueEle');
	
	                  $(self).find('#'+valueEleId).val(value);
	                
	            }
	
	        });

	         $(self).find('#personMarried').combobox({
                onSelect:function(value){
                    if(value.value == '1'){
                         $(self).find('#childrenNum').removeAttr("disabled");  
                    }else{
                         $(self).find('#childrenNum').val("");
                         $(self).find('#childrenNum').attr("disabled","true");
                    }

                }


             });
	         $(self).find('#hasCreditCard').combobox({
                onSelect:function(value){
                    if(value.value == '1'){
                    	  $(self).find('#creditCardInfoTr').show();

                    }else{
                          $(self).find('#creditCardInfoTr').find('.easyui-numberbox').each(function() {
                                $(this).numberbox('clear');
                          });
                    	  $(self).find('#creditCardInfoTr').hide();

                         
                    }

                }

             });

	         $(self).find('#livingWithType').combobox({
                onSelect:function(value){
                    if(value.value == '其他'){
                    	  $(self).find('#livingWithOtherText').show();
                        // var livingWithOtherText = $(' <input id="livingWithOtherText" name="livingWithOtherText"  style="width: 30px"/>');
                        // $('#livingWithOther').append(livingWithOtherText);
                    }else{
                       //  var value = $(self).find('#livingWithNum').numberbox('getValue');
                    	  // $(self).find('#livingWithNum').numberbox('setValue', value);
                    	  $(self).find('#livingWithOtherText').hide();
                    }

                }

             });


	        if(loanDetails.loan ){
	            if(loanDetails.loan .requestMoney){
	                        $(self).find('#requestMoney').val(loanDetails.loan.requestMoney);
	                       $(self).find('#requestMoneyText').text(loanDetails.loan.requestMoney);
	                
	            }

	            if( loanDetails.loan.requestTime){
	                        $(self).find('#requestTimeText').text(loanDetails.loan.requestTime);
	                       $(self).find('#requestTimeValue').val(loanDetails.loan.requestTime);

	            }
                if(loanDetails.loan.status && loanDetails.loan.status >10){
                    $('#editLoanForm .loanInfo :input').attr("readonly","readonly"); 
                    $('#editLoanForm #organID').combobox({disabled:true});
                    $('#editLoanForm #schemeID').combobox({disabled:true});

                }
	       };
           if (loanDetails.creditHistory) {
                var historyLoanChannel = loanDetails.creditHistory.historyLoanChannel;
                if (historyLoanChannel == '银行'){
                     $(self).find('#histBank').attr("checked",'checked');
                }
                else if (historyLoanChannel == '个人'){
                    $(self).find('#histPerson').attr("checked",'checked');  
                }
                else if (historyLoanChannel == '金融机构'){
                     $(self).find('#histOrg').attr("checked",'checked');
                }
                else if (historyLoanChannel == '其他'){
                     $(self).find('#histOther').attr("checked",'checked');
                }


           };

           if(loanDetails.salaryIncome ){
                if(loanDetails.salaryIncome != " "){
                     $(self).find("#salaryIncomeValue").numberbox('setValue', loanDetails.salaryIncome);
                     $(self).find('#salaryIncomeType').attr("checked",'checked').val(loanDetails.salaryIncome);               

                }

           }
            if(loanDetails.rentIncome){

                if(loanDetails.rentIncome != " "){

                    $(self).find("#rentIncomeValue").numberbox('setValue', loanDetails.rentIncome);
                    $(self).find('#rentIncomeType').attr("checked",'checked').val(loanDetails.rentIncome);               
                }

           }
            if(loanDetails.otherIncomeAmount){
                    if(loanDetails.otherIncomeAmount !=" "){

                         $(self).find("#otherIncomeValue").numberbox('setValue', loanDetails.otherIncomeAmount);
                         $(self).find('#otherIncomeType').attr("checked",'checked').val(loanDetails.otherIncomeAmount);               
                    }


           }


           if (loanDetails.livingWithOtherText) {
        	   $(self).find('#livingWithType').combobox('select','其他');

           };

            if (loanDetails.personMarried) {
            	$(self).find('#personMarried').combobox('select','1');

           };



	        

        }
 
     


    var methods = {
        init: function (pageData, nav) {
            this.data(dataTag, {});
            
            initialize(this);
            return this;
        }
    };

    $.fn.eduLoanApply = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance ' );
        }
    };
}
eduLoanApply_plugin.prototype = {};
eduLoanApply_plugin(jQuery);

  function loadChannelPlanInfoToWindow (channel) {

       
        $.ajax({
                url : 'channelPlan/loadChannelPlanInfo',
                data : {
                    id : channel.data                      
                },
                type:'POST',
                success : function(result){
                        // $('#channelPlanEditPanel').window('open');
                    if (result.isSuccess) {
                        $('#channelPlanEditPanel').window({
        //                  width:300,
                            modal:true,
                            closed:false
                        });
                        
                        $('#returneterm12').val('');
                        $('#channelPlanEditPanel').form('load', result.channelPlan);
                        $('#channelPlanEditForm').find('#startDate2').val(result.channelPlan.startDate.substring(0,10));
                        $('#channelPlanEditForm').find('#endDate2').val(result.channelPlan.endDate.substring(0,10));
                        $('#channelPlanEditForm').find('#organName2').val(result.channelPlan.orgName);
                        $('#channelPlanEditForm').find('input').attr("disabled",true);
                        $('#channelPlanEditForm').find('textarea').attr("disabled",true);
                        // $('#returnType2').combobox('disable');
                        // $('#orgFeeState2').combobox('disable');
                    } else {
                        $.messager.alert('操作提示', result.msg,'error');
                    }
                },
                error:function(data){
                    $.messager.alert('操作提示', 'error','error');
                }
            });
    }