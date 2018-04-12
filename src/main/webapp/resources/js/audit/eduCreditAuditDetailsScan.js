$().ready(function() {
	//IE也能用textarea 
	$("textarea[maxlength]").keyup(function(){ 
	var area=$(this); 
	var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
	if(max>0){ 
	if(area.val().length>max){ //textarea的文本长度大于maxlength 
	area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
	} 
	} 
	}); 
	//复制的字符处理问题 
	$("textarea[maxlength]").blur(function(){ 
	var area=$(this); 
	var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
	if(max>0){ 
	if(area.val().length>max){ //textarea的文本长度大于maxlength 
	area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
	} 
	} 
	}); 
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
	
	$('#hangUpPanel').window('close');
	$('#agreePanel').window('close');
	$('#sendBackPanel').window('close');
	$('#refusePanel').window('close');
	
	
	$('#refusePanel').find('#refuseFirstReason').combobox({     
	    valueField:'id',
	    textField:'reason',
	    onChange:function(reason1){ 
	    	$('#refusePanel').find('#refuseSecondReason').combobox({     
	    		  url:'master/rejectReason/getRefuseSecondReason?parentId='+reason1,
	    		    valueField:'id',
	    		    textField:'reason',
	    		    onLoadSuccess:function(){
	    	        	var data = $(this).combobox('getData');
	    	        	if(data.length>0)
	    	        		$(this).combobox('select', data[0].id);
	    	        }
	    	  }); 
	    }
  
	});
	
	
	$('#hangUpBut').click(function(){
		$('#hangUpPanel').form('clear');
		$('#hangUpPanel').window({
			modal:true
		});
	});
	
	$('#agreeBut').click(function(){
		$('#agreePanel').form('clear');
		$('#agreePanel').window({
			modal:true
		});
		$('#agreePanel #agreeMoneyInput').val(requestMoney);
		$('#agreePanel #agreeTimeComb').val(requestTime);
	});
	
	$('#sendBackBut').click(function(){
		$('#sendBackPanel').form('clear');
		$('#sendBackPanel').window({
			modal:true
		});
	});
	
	$('#refuseBut').click(function(){
		$('#refusePanel').form('clear');
		$('#refusePanel').window({
			modal:true
		});
		auditRefuse();
	});
	
	$('#logBut').click(function(){
	 	var loanIdLog=loanId;
		businessLogPage(loanIdLog);
	});
	
	$('#checkMsgBut').click(function(){
		openWindows(loanId);
	}
	);
	
	$('#checkMsgLBut').click(function(){
		openWindowsL(loanId);
	}
	);
	
	
	$('#customerButScan').click(function(){
		
		
		$.ajax({	        
		        url: appname+"apply/viewEdit",
		        type : "POST",
		        dataType:"json",
		        data: {	  
		        	 loanId:loanId,
		             productId:8,
		             personType:1,
		             idnum:personIdum
		        },
		        success:function(loanDetailsVo){
		        		_step=1;
							
							
						//隐藏上一步，提交按钮
						$("#addTable1").show();
						$("#addTable2").hide();
						$("#prevStepBtn").hide();
						$("#submitEditBtn").hide();
		        		$("#nextStepBtn").show();
						var h = $(window).height() ;
						clrearLoanDate();            
						commonApplyDataModule_plugin.prototype.loanDetails = loanDetailsVo;
	              				
	              	var url = appname+'audit/eduLoan/toEditCustomerInfoScan';
					$('#seLoanModify').dialog({
						title: '编辑客户信息',
						closed: false,  
						inline:true,
						cache: false,
						href: url,
						onLoad : function(){
              					 // $('#editLoanForm').commonApplyDataModule(loanDetailsVo);
	   							
	   								loadLoanDate(loanDetailsVo);
	   								  // 营业网点及相应客户经理加载
							        $('#editLoanForm').find('#salesDeptId').combobox({
							              // url:'apply/getSalesDeptsInCurCity',
							              data:[loanDetailsVo.salesDept],
							              valueField:'id',
							              textField:'name',
							              onLoadSuccess:function(){
							                  var data = $(this).combobox('getData');
							                  if(data.length==1)
							                      $(this).combobox('select', data[0].id);
							              },
							              onSelect:function(newVal, oldVal){ 
							               $('#editLoanForm').find( '#managerName').combobox({     
							                  url:appname+'apply/getCrmsInSalesDeptByProductIdAndSalesDeptId',

							                    valueField:'id',
							                    textField:'name',
							                    onBeforeLoad: function(param){                              
							                      param.productId = loanDetailsVo.productId;  
							                      param.salesDeptId = newVal.id;
							                      if( $('#editLoanForm').find('#organID').length>0){
							                        param.organID =  $('#editLoanForm').find('#organID').combobox('getValue');

							                      }
							                    },
							                    onLoadSuccess:function(){
							                        if(loanDetailsVo.loanId){
							                             $(this).combobox('select', loanDetailsVo.crm.id);
							                         }else{
							                            var data = $(this).combobox('getData');
							                            if(data.length>0)
							                              $(this).combobox('select', data[0].id);

							                         }
							                      },
							                      onSelect:function(newVal, oldVal){
							                          $('#editLoanForm').find('#crmId').val(newVal.id);
							                      }
							                }); 
							            }
							          });
										//复核人员
						 		    // $('#editLoanForm').find('#assessorName').combobox(
			   						 // 		  		{
			   						 // 		    	valueField: 'value',
												 // 	textField: 'textField',
			   						 // 		  			data:[{
			   						 // 		  				value:loanDetailsVo.assessor.id,
			   						 // 		  				textField:loanDetailsVo.assessor.name
			   						 // 		  			}]
			   						 // 		  		});
			   						 // $('#editLoanForm').find('#assessorName').combobox('select',loanDetailsVo.assessor.id);
									   $('#editLoanForm').find('#assessorName').combobox({
									          url:appname+'apply/getServicesInCurSalesDeptByProductId',
									          valueField:'id',
									          textField:'name',
									          editable:false ,
									          onBeforeLoad: function(param){                  
									            param.productId = loanDetailsVo.productId;  
									            param.userId =    loanDetailsVo.service.id;
									           
									          },
									          onLoadSuccess:function(){
										          	if(loanDetailsVo.assessor){
										          		 $(this).combobox('select', loanDetailsVo.assessor.id);
										          	}else{
										          		
											            var data = $(this).combobox('getData');
								                        if(data.length>0)
								                          $(this).combobox('select', data[0].id);
										          	}
									          	},
										          onSelect:function(newVal, oldVal) {

										        	  // if(newVal.id ==  $('#editLoanForm').find('#serviceId').val()){
										           //        $.messager.show({
										           //             title:'提示',
										           //             msg:'复核人员和客服不可以是同一人',
										           //             showType:'slide'
										           //         });
										           //        $(this).combobox('unselect', newVal.id);
										           //        return false;
										                 
										           //     }

										            	$('#editLoanForm').find('#assessorId').val(newVal.id);
										          }
									        });  
										// $('#editLoanForm').find('#salesDeptName').val(loanDetailsVo.salesDept.name);
	   								  

	   						// 		  $('#editLoanForm').find('#managerName').combobox(

	   						// 		  		{
	   						// 		  			valueField: 'value',
										// 		textField: 'textField',
	   						// 		  			data:[{
	   						// 		  				value:loanDetailsVo.crm.id,
	   						// 		  				textField:loanDetailsVo.crm.name
	   						// 		  			}]
	   						// 		  		});

	   								  	
	   								  // $('#editLoanForm').find('#crmId').val(loanDetailsVo.crm.id);
	   						// 		   $('#editLoanForm').find('#managerName').combobox('select',loanDetailsVo.crm.id);
	   						// 		  // $('#editLoanForm').find('#assessorName').val( 'setValue', loanDetailsVo.assessor.name);
	   						// 		    $('#editLoanForm').find('#assessorName').combobox(
	   						// 		  		{
	   						// 		    	valueField: 'value',
										// 	textField: 'textField',
	   						// 		  			data:[{
	   						// 		  				value:loanDetailsVo.assessor.id,
	   						// 		  				textField:loanDetailsVo.assessor.name
	   						// 		  			}]
	   						// 		  		});
	   						// 		     $('#editLoanForm').find('#assessorName').combobox('select',loanDetailsVo.assessor.id);

	   						// 		  $('#editLoanForm').find('#assessorId').val(loanDetailsVo.assessor.id);

	   									
	   								 
							
		//         				$('#editLoanForm').commonApplyDataModule(loanDetailsVo);
					
							// loadLoanDate(loanDetailsVo);	
						}
				
					});		

				

							
					
					
		        },
		        error:function(data){
			 		 $.messager.show({
							title: 'warning',
							msg: data.responseText
						});
				}
		    });
			 // edit(loanId,8,personIdnum);
				
		
	});
	
});


function openWindowsL(id){
	 window.open(appname+"audit/eduLoanList/eduCreditAuditListScan/"+id+"&"+inType,"newwindow3","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");

}


//审贷会决议拒绝
function  auditRefuse(){
		var productId=1;
	     $('#refusePanel').find('#refuseFirstReason').combobox('clear');
	     $('#refusePanel').find('#refuseSecondReason').combobox('clear');
	 	$('#refusePanel').find('#refuseFirstReason').combobox({     
	 		 url:'master/rejectReason/getRefuseFirstReason',
			    valueField:'id',
			    textField:'reason',
			    onBeforeLoad: function(param){                    		    	
						param.productId = productId;								
					}
		});
}

function doHangUpLoan(){
	var isok =false;
	if($('#hangUp1').val()==null || $('#hangUp1').val()==""){
		$.messager.alert('操作提示', "请填写挂起原因");
		return false;};
		$.ajax({
			url : 'audit/eduLoan/isHangUp',
			data : {
				id : loanId
				 
				
			},
			type:'POST',
			success : function(result){
				if (result.isFlag) {
					isok =true;
				 
					$.messager.alert('操作提示',"挂起失败，不可再次挂起！");
					 
				}else{
					
					$.messager.confirm("提示","是否确认挂起",function(r){
						if(r){
							$.ajax({
								url : 'audit/eduLoan/hangUp',
								data : {
									id : loanId,
									remark : $('#hangUp1').val()
								},
								type:'POST',
								success : function(result){
									var isErr = '';
									if (result.isSuccess) {
										
									} else {
										isErr = 'error';
									}
									$.messager.alert('操作提示', result.msg,isErr);
									$('#searchBut').trigger('click');
									if (!(result.msg == '')) {
										window.opener.document.getElementById('reason12').value = '刷新';
										window.opener.document.getElementById('reason12').value = '';
										var div=	window.opener.document.getElementById('flshDiv');
										div.focus();
										$('#hangUpPanel').window('close');
										window.close();
									}
								},
								error:function(data){
									$.messager.alert('操作提示', 'error','error');
								}
							});
						}
					});
				}
			},
		});

}

function businessLogPage(id) {
    $('#businessLogDlg').dialog({
        title: '审贷会决议日志',
        width: 900,
        height: 300,
        closed: false,
        cache: false,
        modal: true
    });
    var url = 'audit/businessLog/detail.json/' + id;
    $('#business_log_result').datagrid({
        url: url,
        fitColumns: true,
        border: false,
        singleSelect:true,
        pagination: true,
        fit:true,
        pageSize: 10,
        striped: true,
        rownumbers: true,
        nowrap:false,
        columns: [
            [
                {
                    field: 'operator',
                    title: '操作者',
                    width: 50
                },
                {
                    field: 'flowStatusView',
                    title: '环节',
                    width: 60
                },
                {
                    field: 'createDate',
                    title: '操作时间',
                    width: 100
                },
                {
                    field: 'message',
                    title: '日志内容',
                    width: 300 
                }
            ]
        ]
    });
    // 设置分页控件
    var p = $('#business_log_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
}

//同意
function agreeSubmit(){
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					state : 10,
					auditMoney : $('#agreeMoneyInput').val(),
					requestMoney : requestMoney,
					contractMatters : $('#contractMatters').val(),
					reason : $('#agreeReason').val() ,
					term : $('#agreeTimeComb').val()
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#agreePanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//拒绝
function refuseSubmit(){
	if($('#refuseSecondReason').combobox("getValue")==null||$('#refuseSecondReason').combobox("getValue")==''){
		$.messager.alert('操作提示', '请填写拒贷原因','error');
		return;
	}
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					reason : $('#refuseRemarkTA').val() ,
					state : 30,
					refuseSecondReasonId : $('#refuseSecondReason').combobox("getValue")
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#refusePanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//退回
function sendBackSubmit(){
	if( $('#returnContractMatters').val()==null||$('#returnContractMatters').val()==''){
		$.messager.alert('操作提示', '请填写退回原因','error');
		return;
	}
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					state : 31,
					status : '3',
					contractMatters : $('#returnContractMatters').val(),
					reason : $('#returnReason').val() 
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#sendBackPanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//同意
function agreeLApproveSubmit(){
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					state : 10,
					auditMoney : $('#agreeMoneyInput').val(),
					requestMoney : requestMoney,
					contractMatters : $('#contractMatters').val(),
					reason : $('#agreeReason').val() ,
					term : $('#agreeTimeComb').val()
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#agreePanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//拒绝
function refuseLApproveSubmit(){
	if($('#refuseSecondReason').combobox("getValue")==null||$('#refuseSecondReason').combobox("getValue")==''){
		$.messager.alert('操作提示', '请填写拒贷原因','error');
		return;
	}
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					status : '3',
					reason : $('#refuseRemarkTA').val() ,
					state : 30,
					refuseSecondReasonId : $('#refuseSecondReason').combobox("getValue")
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#refusePanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//终审退回门店
function sendBackToLApproveSubmit(){
	if( $('#returnContractMatters').val()==null||$('#returnContractMatters').val()==''){
		$.messager.alert('操作提示', '请填写退回原因','error');
		return;
	}
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					state : 31,
					status : '1',
					contractMatters : $('#returnContractMatters').val(),
					reason : $('#returnReason').val() 
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#sendBackPanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//退回初审
function sendBackToFApproveSubmit(){
	if( $('#returnContractMatters').val()==null||$('#returnContractMatters').val()==''){
		$.messager.alert('操作提示', '请填写退回原因','error');
		return;
	}
	$.messager.confirm("提示","是否确认提交",function(r){
		if(r){
			$.ajax({
				url : 'audit/eduLoan/approve',
				data : {
					loanId : loanId,
					state : 31,
					status : '2',
					contractMatters : $('#returnContractMatters').val(),
					reason : $('#returnReason').val() 
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					if (!(result.msg == '')) {
						window.opener.document.getElementById('reason12').value = '刷新';
						window.opener.document.getElementById('reason12').value = '';
						var div=	window.opener.document.getElementById('flshDiv');
						div.focus();
						$('#sendBackPanel').window('close');
						window.close();
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

function editCustomerSubmit(){

		 var entranceDate=$('#editLoanForm').find("#entranceDate").val();

	    
	    if(entranceDate==''){
	         $.messager.show({
	                title: 'warning',
	                msg: ' 请填写入学时间'
	          });
	        return false;
	    };    
	   var assessorName=$('#editLoanForm').find("#assessorName").combobox('getValue');	
		if(isNaN(assessorName)){
			$('#editLoanForm').find("#assessorId").val(null);
			$('#editLoanForm').find("#assessorName").combobox('setValue',null);		
		}else{
			$('#editLoanForm').find("#assessorId").val(assessorName);
		};

        var  houseEstateType = $('#editLoanForm').find('#houseEstateType').combobox('getValue');
            //提示填写每月租金
            if(houseEstateType=='租赁'&& $('#editLoanForm').find('#rentPerMonth').val()==""){
                 $.messager.show({
                        title: 'warning',
                        msg: '请填写每月租金'
                  });
                 return false;
            }  

             var  personMarried = $('#editLoanForm').find('#personMarried').combobox('getValue');
         if(personMarried=='1' && $('#editLoanForm').find('#childrenNum').val()==""){
             $.messager.show({
                    title: 'warning',
                    msg: '请填写子女数目'
              });
             return false;
        }    
	   $.ajax({
		   url : appname+'apply/modifySELoan',
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
