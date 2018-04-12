;var currentAuditData = {curAuditDialog:'',loanDetails:'',formId:''};
;var approveCLFormFirstInto = true;
;var seloanAudit_plugin = function  ($) {

	function _reload(pageData,self){
		$(self).find('.newSeloanBrowse').hide();
		$(self).find('.oldSeloanBrowse').show();
	};
	function validate(requestMoney,agreeMoney,passed,productId){
		//同城贷签批金额判断
		if(productId==5 || productId==6)
		{
			if(agreeMoney<1 || agreeMoney >1000000){
				 $.messager.show({
		             title:'审批金额不合法',
		             msg:'贷款金额必须位于在区间[1,1000000]中',
		             showType:'slide'
		         });
				 return false;
			}
		}	
		//小企业贷签批金额判断
		else if(productId==1)
		{
			if(agreeMoney<100000 || agreeMoney >500000){
				 $.messager.show({
		             title:'审批金额不合法',
		             msg:'贷款金额必须位于在区间[100000,500000]中',
		             showType:'slide'
		         });
				 return false;
			}
			if(agreeMoney%1000 != 0) {
				 $.messager.show({
		          title:'审批金额不合法',
		          msg:'审批金额必须是1000的倍数！',
		          showType:'slide'
		      });
				 return false;
			}
		}
		if(requestMoney < agreeMoney) {
			 $.messager.show({
	             title:'审批金额不合法',
	             msg:'审批金额不能大于申请金额！',
	             showType:'slide'
	         });
			 return false;
		}
		
		return passed;
		
	}

	var methods = {
        init: function (pageData, nav) {           
			$(this).find('.apprWSLoan').hide();
			// $(this).find('.apprWSLoan').attr("disabled","disabled");
			
			$(this).find('.apprHasHouse').removeAttr("disabled");
			// $(this).find('.seConApprGua').removeAttr("disabled");

			$(this).find('.apprHasHouse').show();
			$(this).find('.seConApprGua').show();

			$(this).find('.newSeloanBrowse').hide();
			$(this).find('.oldSeloanBrowse').show();
			// renderOtherField(pageData,'auditLoanBasicInfo');

			
            return this;
        },
        reload:function  (pageData) {
     	   	_reload(pageData,this);
        	return this;
        },
        validateAgreeComponents:function (){
			var dialogId = currentAuditData.curAuditDialog;
			var passed = true;
			if(!$('#'+dialogId+' #approveForm #agreeMoneyInput').valid())
				passed= false;	
			var agreeMoney= $('#'+dialogId+' #approveForm #agreeMoneyInput').val();
			var requestMoney = parseInt($('#'+dialogId+' #approveForm #requestMoney').text());
			var productId= $('#'+dialogId+' #approveForm #productId').val();
			
			passed =  validate(requestMoney,agreeMoney,passed,productId);
			return passed; 
		},
		validateConditionAgreeComponents:function (){
			var dialogId = currentAuditData.curAuditDialog;
			var formId = currentAuditData.formId;
			var passed = true;
			if(!$('#'+dialogId+' #conditionalAgreeMoneyInput').valid())
				 passed= false;	
			var conditionalAgreeMoney = $('#'+dialogId+' #'+formId+' #conditionalAgreeMoneyInput').val();
			var requestMoney = parseInt($('#'+dialogId+' #'+formId+' #requestMoney').text());
//			var agreeMoney= $('#'+dialogId+' #'+formId+' #agreeMoneyInput').val();
			var productId= $('#'+dialogId+' #'+formId+' #productId').val();
			
			passed =  validate(requestMoney,conditionalAgreeMoney,passed,productId);
			return passed;
		}
    };
	
	$.fn.seLoanAudit = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance_detail' );
        }
    };
	// $.fn.validateAgreeComponents =  
	// $.fn.validateConditionAgreeComponents = 

};
seloanAudit_plugin(jQuery);



;var wsLoanAudit_plugin = function  ($) {

	function initialize (pageData,self) {
		currentAuditData.curAuditDialog = $(self).attr('id');
		currentAuditData.loanDetails = pageData;

		$(self).find('.newSeloanBrowse').show();
		$(self).find('.oldSeloanBrowse').hide();
		renderOtherField(pageData,'auditLoanBasicInfo');
		

		$(self).find('.apprHasHouse').attr("disabled","disabled");
		// $(self).find('.seConApprGua').attr("disabled","disabled");
		$(self).find('.apprHasHouse').hide();
		$(self).find('.seConApprGua').hide();

		$(self).find('.apprWSLoan').show();
		// $(self).find('.apprWSLoan').removeAttr("disabled");
		// $(self).find('.wsConApprGua').show();
		// $('#nn').numberbox({min:null,max:null});

		
		renderConApprGua('wsGuaNatr','wsGuaLeg');

		$('.apprWSLoan').each(function(){
			$(this).find('#memberType').is('select')?$(this).find('#memberType').combobox('select', pageData.company.memberType):'';
		}); 
		
		$("#approveConfirmDlg #approveConfirmSubmitBt").unbind().bind('click', function (){
			var apprMemberType = $('#wsLoanApprTr').find('#memberType').combobox('getValue'); 
			var apprData = {auditMemberType:apprMemberType};

			$('#approveConfirmDlg').dialog('close');
			submitAgree(apprData);
		});


		$("#conditionalApproveConfirmDlg #conditionalApproveConfirmSubmitBt").unbind().bind('click', function (){
			var conApprMemberType = $('#wsLoanConTr').find('#memberType').combobox('getValue'); 
			var conApprData = {auditMemberType:conApprMemberType};
			$('#conditionalApproveConfirmDlg').dialog('close');
			submitConditionalAgree(conApprData);
		});


		// try{
		// 	renderOtherField(pageData,'approveForm');
		// }catch(e){
		// }
	};

	function _reload(pageData,self){
		$(self).find('.newSeloanBrowse').show();
		$(self).find('.oldSeloanBrowse').hide();
		renderOtherField(pageData,'loanBrowseTab');
	};
	function validate(apprMemberType,requestMoney,agreeMoney,passed){
//		var passed = true;
		if(requestMoney < agreeMoney) {
			 $.messager.show({
	             title:'审批金额不合法',
	             msg:'审批金额不能大于申请金额！',
	             showType:'slide'
	         });
			 return false;
		}
		if (apprMemberType == 1 ) {
			if(300000 < agreeMoney || agreeMoney<50000){
				 $.messager.show({
		             title:'审批金额不合法',
		             msg:'审批金额不能小于5万或大于30万！',
		             showType:'slide'
		         });
				 return false;
				
			}
		};
		if (apprMemberType == 2 ) {
			if(500000 < agreeMoney || agreeMoney<100000){
				 $.messager.show({
		             title:'审批金额不合法',
		             msg:'审批金额不能小于10万或大于50万！',
		             showType:'slide'
		         });
				 return false;

			}
		};
		if (apprMemberType == 3 ) {
			if(800000 < agreeMoney ||agreeMoney<100000){
				 $.messager.show({
		             title:'审批金额不合法',
		             msg:'审批金额不能小于10万或大于80万！',
		             showType:'slide'
		         });
				 return false;

			}
		};
		if(agreeMoney%10000 != 0) {
				 $.messager.show({
		          title:'审批金额不合法',
		          msg:'审批金额必须是10000的倍数！',
		          showType:'slide'
		      });
				 return false;
			};     
  		
		return passed;
		
		
	};

	var methods = {
        init: function (pageData, nav) {           
            initialize(pageData,this);
            return this;
        },
        reload:function  (pageData) {
     	   	_reload(pageData,this);
        	return this;
        }, 
        validateAgreeComponents:function(){
	    	var dialogId = currentAuditData.curAuditDialog;
	    	var passed = true;
			if(!$('#'+dialogId+' #approveForm #agreeMoneyInput').valid())
				passed= false;	
			var agreeMoney= $('#'+dialogId+' #approveForm #agreeMoneyInput').val();
			var requestMoney = parseInt($('#'+dialogId+' #approveForm #requestMoney').text());
			var productId= $('#'+dialogId+' #approveForm #productId').val();
			var apprMemberType = $('#wsLoanApprTr').find('#memberType').combobox('getValue'); 
			passed = validate(apprMemberType,requestMoney,agreeMoney,passed);
			return passed;
			
	    },
	    validateConditionAgreeComponents :function (){
			var dialogId = currentAuditData.curAuditDialog;
			var formId = currentAuditData.formId;
			var passed = true;
			if(!$('#'+dialogId+' #conditionalAgreeMoneyInput').valid())
				 passed= false;	
			var conditionalAgreeMoney = $('#'+dialogId+' #'+formId+' #conditionalAgreeMoneyInput').val();
			var requestMoney = parseInt($('#'+dialogId+' #'+formId+' #requestMoney').text());
			var agreeMoney= $('#'+dialogId+' #'+formId+' #agreeMoneyInput').val();

			var productId= $('#'+dialogId+' #'+formId+' #productId').val();
			var apprMemberType = $('#wsLoanConTr').find('#memberType').combobox('getValue'); 
			
			passed = validate(apprMemberType,requestMoney,conditionalAgreeMoney,passed);
			return passed;
	    }
    };

    $.fn.wsLoanAudit = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance_detail' );
        }
    };
   
   
	
};
wsLoanAudit_plugin(jQuery);

$(function() {

	$('#cityComb').combobox({     
	    valueField:'id',
	    textField:'name',
	    onChange:function(value){ 
	    	$('#salesDeptComb').combobox({     
	    		  url:'audit/getSalesDeptFrCityId?cityId='+value,
	    		    valueField:'id',
	    		    textField:'name',
	    		    onLoadSuccess:function(){
	    	        	var data = $(this).combobox('getData');
	    	        	if(data.length>0)
	    	        		$(this).combobox('select', data[0].id);
	    	        }
	    	  }); 
	    }
	  
	});
	
	
	//车贷拒绝一级二级原因加载
	$('#approveCLForm').find('#refuseFirstReason').combobox({     
	    valueField:'id',
	    textField:'reason',
	    onChange:function(reason1){ 
	    	$('#approveCLForm').find('#refuseSecondReason').combobox({     
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
	
	//车贷展期拒绝一级二级原因加载
	$('#approveExtensionCLForm').find('#refuseFirstReason').combobox({     
	    valueField:'id',
	    textField:'reason',
	    onChange:function(reason1){ 
	    	$('#approveExtensionCLForm').find('#refuseSecondReason').combobox({     
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
	

	//借款类型
	$('#toolbar #productComb').combobox({
	    url:'apply/getProductType',
	    valueField:'id',
	    textField:'productName',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length==1)
        		$(this).combobox('select', data[0].productType);
        }
	});
	//营业网点
	$('#salesDeptComb').combobox({
        url:'audit/getAllSalesDepts',
        valueField:'id',  
        textField:'name',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length==1)
        		$(this).combobox('select', data[0].id);
        }
	});
	//城市
	$('#cityComb').combobox({
        url:'audit/getAllCitys',
        valueField:'id',  
        textField:'name',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length==1)
        		$(this).combobox('select', data[0].id);
        }
	});
	// 借款状态
    $('#toolbar #loanStatusComb').combobox({
    	url:'audit/getLoanStatusList',
    	valueField:'value' ,
    	textField:'name',
    	onLoadSuccess:function() {
    		//var data = $(this).combobox('getData');
    		$(this).combobox('select',30);
    		var isJump = $.trim($("#isJump").val());
    		if (isJump == 1 ) {
    			$(this).combobox('select',0);
    			}
    	}
    });
	
    $('#toolbar #extensionTimeComb').combobox({
        url:'apply/getExtensionTimeList',
        valueField:'value',
        textField:'name',
        onLoadSuccess:function(){
             var data = $(this).combobox('getData');
                $(this).combobox('select', data[0].value);
            
        }
    });
	// 查询按钮
	$('#searchBt').bind('click', search);
	$('#toolbar > #productComb').combobox({
	    url:'apply/getLoanType',
	    valueField:'id',
	    textField:'productName'
	});
	// 回车键搜索
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	//列表
	$('#loanPageTb').datagrid({
		url : 'audit/getLoanPage',
		fitColumns : true,
        border : false,
        singleSelect:true,
        fit:true,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        queryParams: {
        	personIdnum :  $('#personIdnumTxt').val(),
        	flag: $('#isJump').val(),
    	},
		columns : [[ 
			{field : 'personNo',title : '客户编号', formatter:formatPeronNoCell,width : 140},
			{field : 'personName',title : '姓名',formatter:link,width : 70},
			{field : 'personIdnum',title : '身份证号',width : 150},
			{field : 'productId',title : '产品类型',formatter: showExtensionLoanLedger
			,width : 80},
			{field : 'requestMoney',title : '申请金额(元)',width : 80},
			{field : 'auditMoney',title : '审批金额(元)',width : 80},
			
			{
				field : 'extensionTime',
				title : '展期期次',
				formatter : function(value, row, index) {
					if(value == 0) {
						return "无";
					} else {
						return value;
					}
				},
				width : 60
			},
			{field : 'serviceName',title : '客服人员',width : 80},
			{field : 'managerName',title : '客户经理',width : 80},
			{field : 'assessorName',title : '复核人员',width : 80},
			{field : 'salesDeptName',title : '营业网点',width : 150},
			{field : 'requestDate',title : '申请日期', formatter:formatRequestDate, width : 140},
			{field : 'submitDate',title : '提交时间',width : 150},
			{field : 'status',title : '状态',formatter: function(value, row, index){
				return  formatEnumName(value,'LOAN_STATUS');
			},width : 50},
			{field : 'operation',title : '操作', formatter:formatOperationsCell,width : 120},
			
		]]
	});

	function formatRequestDate(value,row,index){
		 return getYMD(value);
	}

	function getYMD(datetime){
		if(datetime==''||typeof(datetime) =="undefined"){
			return '';
		}
		return datetime.substr(0, 10);
	}
	
	
	// 确定按钮
	$("#approveConfirmDlg #approveConfirmSubmitBt").bind('click', function (){
		$('#approveConfirmDlg').dialog('close');
		submitAgree();
	});
	// 取消按钮
	$("#approveConfirmDlg #approveConfirmCancelBt").bind('click',function (){
		$('#approveConfirmDlg').dialog('close');
	});
	//  END
	
	$("#approveForm #agreeCancelBt").bind('click', closeApproveDlg);
	
	$("#approveForm #conditionalAgreeTb").hide();
	// $("#approveForm #conditionalAgreeSubmitBt").bind('click', conditionalApproveConfirm);

	// 确定按钮
	$("#conditionalApproveConfirmDlg #conditionalApproveConfirmSubmitBt").bind('click', function (){
		$('#conditionalApproveConfirmDlg').dialog('close');
		submitConditionalAgree();
	});
	// 取消按钮
	$("#conditionalApproveConfirmDlg #conditionalApproveConfirmCancelBt").bind('click',function (){
		$('#conditionalApproveConfirmDlg').dialog('close');
	});
	//  END
	//指定担保人
	$("#approveForm #addGuaInputBt1").bind('click', addGuaInput1);
	$("#approveForm #addGuaInputBt2").bind('click', addGuaInput2);
	$("#conditionalAgreeGuaranteeInput1").attr("readonly","true"); 
	$("#conditionalAgreeGuaranteeInput2").attr("readonly","true"); 
	$("#conditionalAgreeGuaranteeInput3").attr("readonly","true"); 
	$("#conditionalAgreeGuaranteeInput4").attr("readonly","true"); 
	$("#conditionalAgreeGuaranteeInput2").hide();
	$("#conditionalAgreeGuaranteeInput4").hide();
	
	 $("#guaranteeCheck1").bind("click", function () {
		 if($('#guaranteeCheck1').attr("checked")=="checked"){ 
			 $('#conditionalAgreeGuaranteeInput1').removeAttr("readonly");
			 $('#conditionalAgreeGuaranteeInput2').removeAttr("readonly");
		 }else{
			 $('#conditionalAgreeGuaranteeInput1').val('');
			 $('#conditionalAgreeGuaranteeInput2').val('');
			 $('#conditionalAgreeGuaranteeInput1').attr("readonly","true"); 
			 $('#conditionalAgreeGuaranteeInput2').attr("readonly","true"); 			 
		 }
		  
	  });
	 $("#guaranteeCheck2").bind("click", function () {
		 if($('#guaranteeCheck2').attr("checked")=="checked"){ 
			 $('#conditionalAgreeGuaranteeInput3').removeAttr("readonly");
			 $('#conditionalAgreeGuaranteeInput4').removeAttr("readonly");
		 }else{
			 $('#conditionalAgreeGuaranteeInput3').val('');
			 $('#conditionalAgreeGuaranteeInput4').val('');
			 $('#conditionalAgreeGuaranteeInput3').attr("readonly","true"); 
			 $('#conditionalAgreeGuaranteeInput4').attr("readonly","true"); 			 
		 }
		  
	  });
	
	
	$("#approveForm #refuseTb").hide();
	$("#approveForm #refuseSubmitBt").bind('click', refuseApproveConfirm);
	$("#approveForm #refuseSubmitBt2").bind('click', returnApproveConfirm);
	// 	START 小企业贷拒绝签批
	function refuseApproveConfirm() {
		$('#refuseApproveConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
		
		$('#refuseApproveConfirmDlg #approveResult').val('拒绝');
	}
// 	START  小企业贷退回门店
	function returnApproveConfirm() {	
		var dialogId = currentAuditData.curAuditDialog;
		var  contractMatters =$('#'+dialogId+' #approveForm #returnContractMatters').val();	
		if(contractMatters!=''){			
			$('#returnApproveConfirmDlg #contractMatters2').val("退回门店");
		}else{
			 $.messager.show({
					title: '提示',
					msg: '请填写退回原因'
				});
			return false;
		}		
		$('#returnApproveConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', '退回门店');
	}
	//小企业贷退回门店提交后的确定按钮
	$("#returnApproveConfirmDlg #returnApproveConfirmSubmitBt").bind('click', function (){
		$('#returnApproveConfirmDlg').dialog('close');
		businessReturn();
	});
	// 小企业贷 退回门店提交后的取消按钮
	$("#returnApproveConfirmDlg #returnApproveConfirmCancelBt").bind('click', function (){
		$('#returnApproveConfirmDlg').dialog('close');		
	});
	// 确定按钮
	$("#refuseApproveConfirmDlg #refuseApproveConfirmSubmitBt").bind('click', function (){
		$('#refuseApproveConfirmDlg').dialog('close');
		submitRefuse();
	});
	// 取消按钮
	$("#refuseApproveConfirmDlg #refuseApproveConfirmCancelBt").bind('click',function (){
		$('#refuseApproveConfirmDlg').dialog('close');
	});
	//  END
	
	$("#approveForm #refuseCancelBt").bind('click', closeApproveDlg);
	$("#approveForm #refuseCancelBt2").bind('click', closeApproveDlg);

	$("#approveCLPanel #agreeSubmitBt").bind('click', submitApproveCLAgree);
	// 	START 车贷同意签批
	function submitApproveCLAgree() {
		//alert('1');
		//添加校验判断等级和结果是否存在
		var zonganGrade=$('#zonganGrade').html();
		var zonganResult=$('#zonganResult').html();
		if(zonganGrade==null||zonganGrade==''||zonganResult==null||zonganResult==''){
			$.messager.show({
                title:'结果',
                msg:'捞财宝未返回风控结果，请稍后！',
                showType:'slide'
            });
			return;
		}
		//对于同意的单子，要判断合同金额是否大于20W
		var name=$('#approveCLDlg #personName').html();
		var idNo=$('#approveCLDlg #personIdnum').html();
		var loanId=$('#approveCLDlg #loanId').val();
		var agreeMoneyInput=$('#approveCLDlg #agreeMoneyInput').val();
		var agreeTimeComb=$('#approveCLDlg #agreeTimeComb').combobox('getValue');
		 $.ajax({
			  type: "POST",
			  url: 'audit/contractManeyOverProof',
			  dataType: "json",
			  data: {
				  name:name,
				  idNo:idNo,
				  loanId:loanId,
				  agreeMoneyInput:agreeMoneyInput,
				  agreeTimeComb:agreeTimeComb
			  },
			  success: function(message){
				  if(message.SUMMONEY==true){
					  $.messager.show({
							title:'提示',
							msg:'合同金额过高，请降额或者修改期限',
							showType:'slide'
					  });
					  return;
				  }else{
					  $('#approveCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
						var requestMoney = $('#approveCLForm #agreeMoneyInput').val();
						var requestTime = $('#approveCLForm #agreeTimeComb').combobox('getValue');
						
						$('#approveCLConfirmDlg #approveResult').val('同意');
						$('#approveCLConfirmDlg #requestMoney').val(requestMoney+"元");
						$('#approveCLConfirmDlg #requestTime').val(requestTime+"个月");
				  }
			  },
			  error:function(){
				  $.messager.show({
						title:'提示',
						msg:'校验合同金额是否超过20W失败！',
						showType:'slide'
				  });
				  return;
			  }
			});	
		
		
		
		
		
	}
	// 确定按钮
	$("#approveCLConfirmDlg #approveCLConfirmSubmitBt").bind('click', function (){
		$('#approveCLConfirmDlg').dialog('close');
		submitCLAgree();
	});
	// 取消按钮
	$("#approveCLConfirmDlg #approveCLConfirmCancelBt").bind('click',function (){
		$('#approveCLConfirmDlg').dialog('close');
	});
	//  END
	$("#approveCLPanel #agreeCancelBt").bind('click', closeApproveCLDlg);
	
	$("#approveCLPanel #conditionalAgreeTb").hide();
	
	$("#approveCLPanel #refuseTb").hide();
	//车贷退回
	$("#approveCLPanel #refuseSubmitBt2").bind('click', returnApproveCLConfirm);
	$("#approveCLPanel #refuseSubmitBt").bind('click', refuseApproveCLConfirm);
	// 	START  车贷拒绝签批
	function refuseApproveCLConfirm() {
		//添加校验判断等级和结果是否存在
//		var zonganGrade=$('#zonganGrade').html();
//		var zonganResult=$('#zonganResult').html();
//		if(zonganGrade==null||zonganGrade==''||zonganResult==null||zonganResult==''){
//			$.messager.show({
//                title:'结果',
//                msg:'捞财宝未返回风控结果，请稍后！',
//                showType:'slide'
//            });
//			return;
//		}
		
		
		$('#refuseCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
		
		$('#refuseCLConfirmDlg #approveResult').val('拒绝');
	}
// 	START  弹出退回门店提示框
	function returnApproveCLConfirm() {	
		//添加校验判断等级和结果是否存在
//				var zonganGrade=$('#zonganGrade').html();
//				var zonganResult=$('#zonganResult').html();
//				if(zonganGrade==null||zonganGrade==''||zonganResult==null||zonganResult==''){
//					$.messager.show({
//		                title:'结果',
//		                msg:'捞财宝未返回风控结果，请稍后！',
//		                showType:'slide'
//		            });
//					return;
//				}
				
		
		
		var  contractMatters =$('#approveCLForm #returnContractMatters').val();	
		if(contractMatters!=''){
			$('#returnApproveCLConfirmDlg #carContractMatters').val("退回门店");
		}else{
			 $.messager.show({
					title: '提示',
					msg: '请填写退回原因'
				});
			return false;
		}		
		$('#returnApproveCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', '退回门店');
	}
	//车贷退回门店提交后的确定按钮
	$("#returnApproveCLConfirmDlg #returnApproveCLConfirmSubmitBt").bind('click', function (){
		$('#returnApproveCLConfirmDlg').dialog('close');
		carReturn(); 
	});
	// 车贷退回门店提交后的取消按钮
	$("#returnApproveCLConfirmDlg #returnApproveCLConfirmCancelBt").bind('click', function (){
		$('#returnApproveCLConfirmDlg').dialog('close');		
	});
	// 确定按钮
	$("#refuseCLConfirmDlg #refuseApproveConfirmSubmitBt").bind('click', function (){
		$('#refuseCLConfirmDlg').dialog('close');
		submitCLRefuse();
	});
	// 取消按钮
	$("#refuseCLConfirmDlg #refuseApproveConfirmCancelBt").bind('click',function (){
		$('#refuseCLConfirmDlg').dialog('close');
	});
	$("#approveCLPanel #refuseCancelBt2").bind('click', closeApproveCLDlg);
	$("#approveCLPanel #refuseCancelBt").bind('click', closeApproveCLDlg);
	
	

	
	
	
	
	$("#approveExtensionCLPanel #agreeSubmitBt").bind('click', submitApproveExtensionCLAgree);
	// 	START 车贷展期同意签批
	function submitApproveExtensionCLAgree() {
		$('#approveExtensionCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
		var requestMoney = $('#approveExtensionCLForm #agreeMoneyInput').val();
		var requestTime = $('#approveExtensionCLForm #agreeTimeComb').combobox('getValue');
		
		$('#approveExtensionCLConfirmDlg #approveResult').val('同意');
		$('#approveExtensionCLConfirmDlg #requestMoney').val(requestMoney+"元");
		$('#approveExtensionCLConfirmDlg #requestTime').val(requestTime+"个月");
	}
	// 确定按钮
	$("#approveExtensionCLConfirmDlg #approveCLConfirmSubmitBt").bind('click', function (){
		$('#approveExtensionCLConfirmDlg').dialog('close');
		submitExtensionCLAgree();
	});
	// 取消按钮
	$("#approveExtensionCLConfirmDlg #approveCLConfirmCancelBt").bind('click',function (){
		$('#approveExtensionCLConfirmDlg').dialog('close');
	});
	//  END
	$("#approveExtensionCLPanel #agreeCancelBt").bind('click', closeApproveExtensionCLDlg);
	
	$("#approveExtensionCLPanel #conditionalAgreeTb").hide();
	
	$("#approveExtensionCLPanel #refuseTb").hide();
	//车贷退回
	$("#approveExtensionCLPanel #refuseSubmitBt2").bind('click', returnApproveExtensionCLConfirm);
	$("#approveExtensionCLPanel #refuseSubmitBt").bind('click', refuseApproveExtensionCLConfirm);
	// 	START  车贷拒绝签批
	function refuseApproveExtensionCLConfirm() {
		$('#refuseExtensionCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
		
		$('#refuseExtensionCLConfirmDlg #approveResult').val('拒绝');
	}
// 	START  弹出退回门店提示框
	function returnApproveExtensionCLConfirm() {		
		var  contractMatters =$('#approveExtensionCLForm #returnContractMatters').val();	
		if(contractMatters!=''){
			$('#returnApproveExtensionCLConfirmDlg #carContractMatters').val("退回门店");
		}else{
			 $.messager.show({
					title: '提示',
					msg: '请填写退回原因'
				});
			return false;
		}		
		$('#returnApproveExtensionCLConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', '退回门店');
	}
	//车贷退回门店提交后的确定按钮
	$("#returnApproveExtensionCLConfirmDlg #returnApproveCLConfirmSubmitBt").bind('click', function (){
		$('#returnApproveExtensionCLConfirmDlg').dialog('close');
		carExtensionReturn(); 
	});
	// 车贷退回门店提交后的取消按钮
	$("#returnApproveExtensionCLConfirmDlg #returnApproveCLConfirmCancelBt").bind('click', function (){
		$('#returnApproveCLConfirmDlg').dialog('close');		
	});
	// 确定按钮
	$("#refuseExtensionCLConfirmDlg #refuseApproveConfirmSubmitBt").bind('click', function (){
		$('#refuseExtensionCLConfirmDlg').dialog('close');
		submitExtensionCLRefuse();
	});
	// 取消按钮
	$("#refuseExtensionCLConfirmDlg #refuseApproveConfirmCancelBt").bind('click',function (){
		$('#refuseExtensionCLConfirmDlg').dialog('close');
	});
	$("#approveExtensionCLPanel #refuseCancelBt2").bind('click', closeApproveExtensionCLDlg);
	$("#approveExtensionCLPanel #refuseCancelBt").bind('click', closeApproveExtensionCLDlg);
	
	
	
	
	
	
	// //车贷选择签批类型
	// $("#approveCLPanel > input:radio").change(function() {    //id 为season行内radio值变化函数  
	// 	var approveResult = $("#approveCLPanel input[name='agreementRD']:checked").val();	
	// 	if(approveResult == "10"){
	// 		$("#approveCLPanel #agreeTb").show();
	// 		$("#approveCLPanel #conditionalAgreeTb").hide();
	// 		$("#approveCLPanel #refuseTb").hide();
	// 		$("#approveCLPanel #returnTable").hide();
	// 	}else if(approveResult == "20") {
	// 		$('#'+approveDialogId+" #approvePanel #conditionalAgreeTb").show();
	// 		$('#'+approveDialogId+" #approvePanel #agreeTb").hide();
	// 		$('#'+approveDialogId+" #approvePanel #refuseTb").hide();
	// 		$('#'+approveDialogId+" #approvePanel #returnTable").hide();
	// 	}  else if(approveResult == "30"){
	// 		$("#approveCLPanel #refuseTb").show();
	// 		$("#approveCLPanel #conditionalAgreeTb").hide();
	// 		$("#approveCLPanel #agreeTb").hide();
	// 		$("#approveCLPanel #returnTable").hide();
	// 	}else if(approveResult == "31"){//退回
	// 		$("#approveCLPanel #returnTable").show();
	// 		$("#approveCLPanel #refuseTb").hide();
	// 		$("#approveCLPanel #conditionalAgreeTb").hide();
	// 		$("#approveCLPanel #agreeTb").hide();
	// 	}
	// });
	
	
	//车贷展期选择签批类型
	$("#approveExtensionCLPanel > input:radio").change(function() {    //id 为season行内radio值变化函数  
		var approveResult = $("#approveExtensionCLPanel input[name='agreementRD']:checked").val();	
		if(approveResult == "10"){
			$("#approveExtensionCLPanel #agreeTb").show();
			$("#approveExtensionCLPanel #conditionalAgreeTb").hide();
			$("#approveExtensionCLPanel #refuseTb").hide();
			$("#approveExtensionCLPanel #returnTable").hide();
		} else if(approveResult == "30"){
			$("#approveExtensionCLPanel #refuseTb").show();
			$("#approveExtensionCLPanel #conditionalAgreeTb").hide();
			$("#approveExtensionCLPanel #agreeTb").hide();
			$("#approveExtensionCLPanel #returnTable").hide();
		}else if(approveResult == "31"){//退回
			$("#approveExtensionCLPanel #returnTable").show();
			$("#approveExtensionCLPanel #refuseTb").hide();
			$("#approveExtensionCLPanel #conditionalAgreeTb").hide();
			$("#approveExtensionCLPanel #agreeTb").hide();
		}
	});

});

/** 操作 */
function formatOperationsCell(value,row,index){
	var operation = row.operations,productCode;
	
	if(row.productCode)
		productCode = row.productCode.slice(row.productCode.lastIndexOf('/')+1)+'Audit'+'';
	var formattedOperation="";
	if(operation == "签批") {
		formattedOperation = '<a href="javascript:void(0)" onclick="approveShadow('+row.id+ ','+row.productId+ ',\''+ productCode + '\')">签批</a>';
		
	}
	if(operation == "展期签批") {
		formattedOperation = '<a href="javascript:void(0)" onclick="extensionApprove('+row.id+ ',\'' + row.productId  + '\')">展期签批</a>';
	}
	 var log = "<a href='javascript:businessLogPage(" + row.id + ");'>日志</a>";
	 
	 var attachment =  "<a href='javascript:showAttachmentDlg(" + row.id +","+ row.extensionTime+ ");'>附件</a>";
	 formattedOperation = formattedOperation + "  " + log+ "  " + attachment;
	
	return formattedOperation;
};
function showAttachmentDlg(loanId){
	
	 window.open (rayUseUrl+"audit/imageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
function showAttachmentDlg(loanId,extensionTime){
	if(extensionTime==0)
	{
		window.open (rayUseUrl+"audit/imageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}
	else
	{
		window.open (rayUseUrl+"audit/extensionImageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");

	}
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


function formatPeronNoCell(value,row,index){
	return '<b>' + row.personNo + '</b>';
}

function link(value,row,index){
	var productCode;
	if(row.productCode)
		productCode = row.productCode.slice(row.productCode.lastIndexOf('/')+1)+'Audit'+'';
	if(row.extensionTime==0)
	{
		return '<a  href="javascript:void(0)" onclick="browse('+row.id+ ',\'' + row.productId  + '\',\''+ productCode + '\')">' + row.personName + '</a>';
	}
	else
	{
		return '<a  href="javascript:void(0)" onclick="browseExtension('+row.id+ ')">' + row.personName + '</a>';
	}
}

function browse(loanId, productId,productCode){
    if(productId ==1){
        doBrowse(loanId,productCode);
    }else if(productId == 2){
        doBrowseCL(loanId);
    }
};
function browseExtension(loanId){
	doBrowseCLExtension(loanId);
};
/**查看小企业贷*/
function doBrowse(loanId,productCode){
	var flag='audit';
    var strData = getSeLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height() ;
    var dialogId = 'browseDlg';
    var productId = loanDetails.product.id;

    if (5 == productId || productId == 6)
    	dialogId = 'browseCityWideSeLoan';

    $('#' + dialogId).dialog({modal:true,height:h*(0.8)}).dialog('open').dialog('setTitle', '查看小企业贷');

    
    'browseCityWideSeLoan' == dialogId ? doBrowseCityWideLoan(loanDetails,'browseForm',dialogId) : loadSeLoanData(loanDetails);
    if(productCode && typeof($('#'+dialogId)[productCode] ) === 'function'){
		$('#'+dialogId)[productCode]('reload',loanDetails);
	};
    

  

};
/*查看同城贷*/
function doBrowseCityWideLoan(loanDetails,form,dialogId){
	var customizeEleId = {dialogId:'browseCityWideSeLoan',
						contacterBrowseId:'cityWideContacterBrowseTab',contacterTempletId:'cityWideContacterBrowsePanelTemplate',
						guaranteeBrowseTab:'cityWideGuaranteeBrowseTab',guaranteeBrowsePanelTemplate:'cityWideGuaranteeBrowsePanelTemplate',
						approveResultBrowseTab:'cityWideApproveResultBrowseTab',approveResultBrowsePanelTemplate:'cityWideApproveResultBrowsePanelTemplate'};
		loadSeLoanData(loanDetails,customizeEleId);
		loadCityWideLoanData(loanDetails,form);
						

};
/*小企业贷-同城贷数据加载*/
function loadCityWideLoanData(loanDetails,form){
	
    
    var productId = loanDetails.product.id;  
    

    $('#'+form+' #productNames').text(loanDetails.product == undefined?loanDetails.productName:loanDetails.product.productName);
    
    if (loanDetails.company) {
        if (productId == 5){
        	$('#'+form+' #cityWideSeloanOwn').hide();
    	 	$('#'+form+' #cityWidePOSLoanOwn').show();
            $('#'+form+' #posOpenDate').is('label') ? $(posOpenDate).text(getYMD(loanDetails.company.posOpenDate)) : $(posOpenDate).datebox('setValue',getYMD(loanDetails.company.posOpenDate));
            $('#'+form+' #posAcquirer').val(loanDetails.company.posAcquirer).text(loanDetails.company.posAcquirer);
            $('#'+form+' #posCapitavolume').val(loanDetails.company.posCapitavolume).text(loanDetails.company.posCapitavolume);
        }else {
        	$('#'+form+' #cityWideSeloanOwn').show();
    	 	$('#'+form+' #cityWidePOSLoanOwn').hide();
        	$('#'+form+' #companyDebtBalance').val(loanDetails.company.companyDebtBalance).text(loanDetails.company.companyDebtBalance);
        	
        }
            
        $('#'+form+' #monthTurnOver').val(loanDetails.company.monthTurnOver).text(loanDetails.company.monthTurnOver);
        
    }

    if (loanDetails.loan) 
    $('#'+form+' #sourceOfRepay').val(loanDetails.loan.sourceOfRepay).text(loanDetails.loan.sourceOfRepay);   
    
    if (loanDetails.person) {
        $('#'+form+' #placeDomicile').val(loanDetails.person.placeDomicile).text(loanDetails.person.placeDomicile);
        $('#'+form+' #ratioOfInvestments').val(loanDetails.person.ratioOfInvestments).text(loanDetails.person.ratioOfInvestments);
        $('#'+form+' #monthOfProfit').val(loanDetails.person.monthOfProfit).text(loanDetails.person.monthOfProfit);
        $('#'+form+' #monthRepayAmount').val(loanDetails.person.monthRepayAmount).text(loanDetails.person.monthRepayAmount);
        $('#'+form+' #personDebtBalance').val(loanDetails.person.personDebtBalance).text(loanDetails.person.personDebtBalance);
        
    };
    

}
/*小企业贷数据加载*/
function loadSeLoanData(loanDetails,customizeEleId){
	var elementId = {dialogId:'browseDlg',contacterBrowseId:'contacterBrowseTab',
					contacterTempletId:'contacterBrowsePanelTemplate',guaranteeBrowseTab:'guaranteeBrowseTab',
					guaranteeBrowsePanelTemplate:'guaranteeBrowsePanelTemplate2',approveResultBrowseTab:'approveResultBrowseTab2',
					approveResultBrowsePanelTemplate:'approveResultBrowsePanelTemplate2'};
	// var elementId= {} ;
	if(customizeEleId)
		elementId = $.extend(true,elementId,customizeEleId);
	var dialogId = elementId.dialogId;
	var contacterBrowseId = elementId.contacterBrowseId;
	var contacterTempletId = elementId.contacterTempletId;
	var guaranteeBrowseTab = elementId.guaranteeBrowseTab;
	var guaranteeBrowsePanelTemplate = elementId.guaranteeBrowsePanelTemplate;
	var approveResultBrowseTab = elementId.approveResultBrowseTab;
	var approveResultBrowsePanelTemplate = elementId.approveResultBrowsePanelTemplate;

	if(loanDetails.product) {
        $('#'+dialogId+' #browseForm #productName').text(loanDetails.product.productName);
    }
    if(loanDetails.loan) {
        $('#'+dialogId+' #browseForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
        $('#'+dialogId+' #browseForm #requestTime').text(loanDetails.loan.requestTime + "期");
        $('#'+dialogId+' #browseForm #purpose').text(loanDetails.loan.purpose);
    }
    if(loanDetails.person) {
        $('#'+dialogId+' #browseForm #personName').text(loanDetails.person.name);
        $('#'+dialogId+' #browseForm #personSex').text(formatSex(loanDetails.person.sex));
        $('#'+dialogId+' #browseForm #personIdnum').text(loanDetails.person.idnum);
        $('#'+dialogId+' #browseForm #personMarried').text(formatMarried(loanDetails.person.married));
        $('#'+dialogId+' #browseForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
        $('#'+dialogId+' #browseForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
        $('#'+dialogId+' #browseForm #personZipCode').text(loanDetails.person.zipCode);
        $('#'+dialogId+' #browseForm #personAddress').text(loanDetails.person.address);
        $('#'+dialogId+' #browseForm #personMobilePhone').text(loanDetails.person.mobilePhone);
        $('#'+dialogId+' #browseForm #personEmail').text(loanDetails.person.email);
        $('#'+dialogId+' #browseForm #personHomePhone').text(transferUndefined(loanDetails.person.homePhone));
        if(loanDetails.person.professionType){
            $('#'+dialogId+' #browseForm').find('#professionType').text(loanDetails.person.professionType);//职业类型
        }
        // 根据房产类型判断租金和房贷显示与否
        // 规则，如果房产类型是商品房、经济适用房、自建房则显示房贷
        // 如果是租用 则显示每月租金
        // 如果是亲戚住房则租金和房贷均没有
        $('#'+dialogId+' #browseForm #personHouseEstateType').text(loanDetails.person.houseEstateType);
        var personHouseTR = $('#'+dialogId+' #browseForm #personHouseEstateType').parent().parent();
        if(loanDetails.person.houseEstateType == '商品房' || 
        		loanDetails.person.houseEstateType == '经济适用房' || 
        		loanDetails.person.houseEstateType == '自建房'){
        	personHouseTR.find(':nth-child(3)').hide();
        	personHouseTR.find(':nth-child(4)').hide();
        	personHouseTR.find(':nth-child(5)').show();
        	personHouseTR.find(':nth-child(6)').show();
        	
        	$('#'+dialogId+' #browseForm #personHasHouseLoan').text(formatHave(loanDetails.person.hasHouseLoan));
        }
        if(loanDetails.person.houseEstateType == '租用'){
        	personHouseTR.find(':nth-child(3)').show();
        	personHouseTR.find(':nth-child(4)').show();
        	personHouseTR.find(':nth-child(5)').hide();
        	personHouseTR.find(':nth-child(6)').hide();

        	$('#'+dialogId+' #browseForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        }
        if(loanDetails.person.houseEstateType == '亲戚住房'){
        	personHouseTR.find(':nth-child(3)').hide();
        	personHouseTR.find(':nth-child(4)').hide();
        	personHouseTR.find(':nth-child(5)').hide();
        	personHouseTR.find(':nth-child(6)').hide();
        }
        
        $('#'+dialogId+' #browseForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
        if(loanDetails.person.incomePerMonth){
        	$('#'+dialogId+' #browseForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth +"万元/月");
        }
        
    }
    if(loanDetails.company) {
        $('#'+dialogId+' #browseForm #companyName').text(loanDetails.company.name);
        $('#'+dialogId+' #browseForm #companyIndustryInvolved').text(loanDetails.company.industryInvolved);
        $('#'+dialogId+' #browseForm #companyLegalRepresentative').text(loanDetails.company.legalRepresentative);
        $('#'+dialogId+' #browseForm #companyLegalRepresentativeId').text(loanDetails.company.legalRepresentativeId);
        $('#'+dialogId+' #browseForm #companyIncomePerMonth').text(loanDetails.company.incomePerMonth + "万元/月");
        $('#'+dialogId+' #browseForm #companyFoundedDate').text(getYMD(loanDetails.company.foundedDate));
        $('#'+dialogId+' #browseForm #companyCategory').text(formatCompanyCategory(loanDetails.company.category));
        $('#'+dialogId+' #browseForm #companyAddress').text(loanDetails.company.address);
        $('#'+dialogId+' #browseForm #companyAvgProfitPerYear').text(loanDetails.company.avgProfitPerYear + "万元/年");
        $('#'+dialogId+' #browseForm #companyPhone').text(transferUndefined(transferUndefined(loanDetails.company.phone)));
        $('#'+dialogId+' #browseForm #companyZipCode').text(loanDetails.company.zipCode);
        $('#'+dialogId+' #browseForm #companyOperationSite').text(loanDetails.company.operationSite);
        $('#'+dialogId+' #browseForm #companyMajorBusiness').text(loanDetails.company.majorBusiness);
        $('#'+dialogId+' #browseForm #companyEmployeesNumber').text(loanDetails.company.employeesNumber);
        $('#'+dialogId+' #browseForm #companyEmployeesWagesPerMonth').text(loanDetails.company.employeesWagesPerMonth + "万元/月");
    }
    if(loanDetails.service) {
        $('#'+dialogId+' #browseForm #serviceName').text(loanDetails.service.name);
    }
    if(loanDetails.loan) {
        $('#'+dialogId+' #browseForm #customerSource').text(loanDetails.loan.customerSource);
        $('#'+dialogId+' #browseForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
    }
    if(loanDetails.crm) {
        $('#'+dialogId+' #browseForm #crmCode').text(loanDetails.crm.code);
        $('#'+dialogId+' #browseForm #crmName').text(loanDetails.crm.name);
    }
    if(loanDetails.salesDept) {
        $('#'+dialogId+' #browseForm #salesDeptName').text(loanDetails.salesDept.name);
    }
    if(loanDetails.assessor) {
        $('#'+dialogId+' #browseForm #assessorName').text(loanDetails.assessor.name);
    }
    if(loanDetails.loan.remark) {
        $('#'+dialogId+' #browseForm #remark').text(loanDetails.loan.remark);
    }
    // 清空联系人列表（除了模板）
    
    $('#'+dialogId+' #' +contacterBrowseId+' >'+' #'+contacterTempletId+'  ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#'+contacterTempletId).clone().show().addClass('easyui-panel');
            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
            contacterBrowsePanel.attr("title","联系人"+(i+1));

            contacterBrowsePanel.find('#contacterName').text(contacter.name);
            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);
            contacterBrowsePanel.find('#title').text(contacter.title);

            contacterBrowsePanel.appendTo($('#'+ contacterBrowseId));
        }
        $.parser.parse('#'+ contacterBrowseId);
    }

    renderGuaView(guaranteeBrowseTab,guaranteeBrowsePanelTemplate,loanDetails);
    // $('#'+dialogId+' #'+guaranteeBrowseTab+' >'+' #'+guaranteeBrowsePanelTemplate+' ~ div').remove();
    // if(loanDetails.guaranteeList) {
    //     for(var i =0;i<loanDetails.guaranteeList.length;i++){
    //         var guarantee = loanDetails.guaranteeList[i];
    //         var guaranteeBrowsePanel =  $('#'+guaranteeBrowsePanelTemplate).clone().show().addClass('easyui-panel');
    //         var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;
    //         guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
    //         guaranteeBrowsePanel.attr("title","担保人"+(i+1));
    //         if(guarantee.flag){
    //         	guaranteeBrowsePanel.find('#flag').text("该担保人为指定担保人");            	
    //         }
    //         if(guarantee.guaranteeType==0){//自然人
    //         	 guaranteeBrowsePanel.find('#detailtr7').hide();
    //              guaranteeBrowsePanel.find('#detailtr8').hide();
    //              guaranteeBrowsePanel.find('#guaranteeName').text(guarantee.name);
    //             guaranteeBrowsePanel.find('#guaranteeType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
    //             guaranteeBrowsePanel.find('#guaranteeIdnum').text(transferUndefined(guarantee.idnum));
    //             guaranteeBrowsePanel.find('#guaranteeSex').text(transferUndefined(formatSex(guarantee.sex)));
    //             guaranteeBrowsePanel.find('#guaranteeMarried').text(transferUndefined(formatMarried(guarantee.married)));
    //             guaranteeBrowsePanel.find('#guaranteeEducationLevel').text(transferUndefined(guarantee.educationLevel));
    //             guaranteeBrowsePanel.find('#guaranteeHasChildren').text(transferUndefined(formatYes(guarantee.hasChildren)));
    //             guaranteeBrowsePanel.find('#guaranteeAddress').text(transferUndefined(guarantee.address));
    //             guaranteeBrowsePanel.find('#guaranteeMobilePhone').text(transferUndefined(guarantee.mobilePhone));
    //             guaranteeBrowsePanel.find('#guaranteeEmail').text(transferUndefined(guarantee.email));
    //             guaranteeBrowsePanel.find('#personHomePhone').text(transferUndefined(guarantee.homePhone));
    //             guaranteeBrowsePanel.find('#guaranteeCompanyFullName').text(transferUndefined(guarantee.companyFullName));
    //             guaranteeBrowsePanel.find('#guaranteeZipCode').text(transferUndefined(guarantee.zipCode));
    //             guaranteeBrowsePanel.find('#guaranteeCompanyAddress').text(transferUndefined(guarantee.companyAddress));
    //             guaranteeBrowsePanel.find('#guaranteeCompanyPhone').text(transferUndefined(guarantee.companyPhone));
               
              
              
    //        }else if(guarantee.guaranteeType==1){//法人
    //        	 guaranteeBrowsePanel.find('#detailtr1').hide();
    //             guaranteeBrowsePanel.find('#detailtr2').hide();
    //             guaranteeBrowsePanel.find('#detailtr3').hide();
    //             guaranteeBrowsePanel.find('#detailtr4').hide();
    //             guaranteeBrowsePanel.find('#detailtr5').hide();
    //             guaranteeBrowsePanel.find('#detailtr6').hide();                      
    //        	 	guaranteeBrowsePanel.find('#detailtr7').show();
    //             guaranteeBrowsePanel.find('#detailtr8').show(); 
    //             guaranteeBrowsePanel.find('#guaType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
    //             guaranteeBrowsePanel.find('#guaCompanyFullName').text(transferUndefined(guarantee.companyFullName));
    //             guaranteeBrowsePanel.find('#guaZipCode').text(transferUndefined(guarantee.zipCode));
    //             guaranteeBrowsePanel.find('#guaCompanyAddress').text(transferUndefined(guarantee.companyAddress));
    //             guaranteeBrowsePanel.find('#guaCompanyPhone').text(transferUndefined(guarantee.companyPhone));
           	
    //        }
          
           
    //         guaranteeBrowsePanel.appendTo($('#'+guaranteeBrowseTab));
    //     }
    //     $.parser.parse($('#'+guaranteeBrowseTab).parent());
       
    // } 
    
    if(loanDetails.approveResultList.length>0){ 
    	 $('#'+approveResultBrowseTab).show();
    	 $('#'+approveResultBrowsePanelTemplate).show();
    	$('#'+approveResultBrowseTab).html("");
    	var managefeeRatesTd;
    	dialogId=='browseCityWideSeLoan'?managefeeRatesTd = '':managefeeRatesTd = '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>管理费率</td>';
    	if(loanDetails.hasHouseString){}
    	else if(dialogId != 'browseCityWideSeLoan') 
    		{managefeeRatesTd = '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>会员类型</td>';}

    	var head=
    		 ' <div style='+'margin:10px auto;text-align:center'+' id='+approveResultBrowsePanelTemplate +'>'+
	          ' <table style='+'position:relative;margin-center:200px;border-top:solid 2px #ebebeb'+'   cellspacing= '+'10'+'>'+
	     '  <tr>'+ 
	     ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签批意见</td>'+ 
	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>申请金额</td>' +  
	       managefeeRatesTd +  
	      ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>期限</td>'+
	      '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签约事项</td>'+
	      '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>备注</td>'+	 
	      '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>补充证件</td>'+ 
	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>拒绝/退回原因</td>'+  
	      ' </tr>';
    	var html="";
    	for(var i =0;i<loanDetails.approveResultList.length;i++){    		
    		 var requestMoney=transferUndefined(loanDetails.approveResultList[i].requestMoney);
             var term=transferUndefined(loanDetails.approveResultList[i].term);
             var state=transferUndefined(loanDetails.approveResultList[i].state);
             var reason=transferUndefined(loanDetails.approveResultList[i].reason);
             var guaranteeName=transferUndefined(loanDetails.loan.guaranteeName);
             var reason1=transferUndefined(loanDetails.approveResultList[i].reason1);
             var reason2=transferUndefined(loanDetails.approveResultList[i].reason2);
             var certificates1=transferUndefined(loanDetails.approveResultList[i].certificates1);
             var certificates2=transferUndefined(loanDetails.approveResultList[i].certificates2);
             var certificates3=transferUndefined(loanDetails.approveResultList[i].certificates3); 
             var contractMatters=transferUndefined(loanDetails.approveResultList[i].contractMatters);
             var hasHouse = transferUndefined(loanDetails.hasHouseString);
             if(managefeeRatesTd){
             	if(hasHouse)
             		hasHouseTd =  '<td>'+'<label >'+hasHouse +'</label>';
             	else hasHouseTd = '<td>'+'<label >'+loanDetails.loan.auditMemberTypeText +'</label>';
             	
             } else {hasHouseTd = '';}

             if(term!=''){
            	 term=term+'个月' ;
             }
             if(requestMoney!=''){
            	 requestMoney=requestMoney+'元' ;
             }
    		html+=	'<tr>'+
    		'<td style='+'background:#fff;text-align:center'+'>'+'<label >'+ formatEnumName(state,'APPROVE_RESULT_STATE')+'</label>'+
    		'</td>'+
    		'<td>'+'<label >'+requestMoney +'</label>'+
    		'</td>'+
    		hasHouseTd+
    		'</td>'+
    		'<td style='+'background:#fff;text-align:center'+'>'+
    				'<label >'+term+'</label>'+'</td>';
    		  		
            if(loanDetails.approveResultList[i].state==30){
            	html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';  
            	html+='<td>'+'<label >'+reason+"<br>"+'</label>'+
        		'</td>';
        		html+='<td>'+'</td>';
        		html+='<td>'+'<label >'+"一级原因:"+reason1+"<br>"+"二级原因:"+reason2 +'</label>'+
        		'</td>';
           }else if(loanDetails.approveResultList[i].state==31){
           		html+='<td> </td>';
           		html+='<td>'+'<label >'+reason +"<br>"+'</label>'+'<br>'+'<label >'+
          		'</td>';
        		html+='<td>'+'</td>';
        		html+='<td>'+'<label >'+ contractMatters +'</label>'+
        		'</td>';


           } else{
           	html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';  
        	   html+='<td>'+'<label >'+reason+'</label>'+
       		'</td>';
       		 html+='<td>'+'<label >'+certificates1+"<br> "+certificates2+"<br> "  +certificates3 +'</label>'+
       		 '</td>';
           }
            html+='</tr>';
           
    	}
    	var end=
    	'</table>'+
        '  </div>';   
    	$("#"+approveResultBrowseTab).append(head+html+end);    	
    	 $.parser.parse($("#"+approveResultBrowseTab).parent());
    }else{    	
    	 $("#"+approveResultBrowseTab).hide();
    	 $('#'+approveResultBrowsePanelTemplate).hide();
    };
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

function getCarExtensionLoanDetails(loanId,flag){	
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

/**查看车贷*/
function doBrowseCL(loanId){
	var isf="0";
	 $.ajax({
        url : 'RefusalEntry/isRefusal?loanId='+loanId,
        data : $("#addCarLoanForm").serialize(),
        type:"POST",
        async: false,
        success : function(result){
            if(result=="1"){
            	isf="1";
            	  
            }
        }
 });
	 if(isf=="1"){
	 
		 doCarLoan(loanId,isf);
		 
	 }else{
	
	
	var flag='audit';
    var strData = getCarLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();
    $('#browseCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '查看车贷');
    if(loanDetails.product) {
        $('#browseCLForm').find('#productName').text(loanDetails.product.productName);
    }
    
  //加载新增加的五个参数
	if(loanDetails.person) {
		if(loanDetails.person.topEducation==1){
			 $('#topEducation').text('硕士及以上');
		}else if(loanDetails.person.topEducation==2){
			 $('#topEducation').text('本科');
		}else if(loanDetails.person.topEducation==3){
			 $('#topEducation').text('大专');
		}else if(loanDetails.person.topEducation==4){
			 $('#topEducation').text('中专');
		}else if(loanDetails.person.topEducation==5){
			 $('#topEducation').text('高中');
		}else if(loanDetails.person.topEducation==6){
			 $('#topEducation').text('初中及以下');
		}else{
			$('#topEducation').text('');
		}
       
		
		if(loanDetails.person.homeCondition==1){
			 $('#homeCondition').text('还款中');
		}else if(loanDetails.person.homeCondition==2){
			 $('#homeCondition').text('全款购');
		}else if(loanDetails.person.homeCondition==3){
			 $('#homeCondition').text('已结清');
		}else if(loanDetails.person.homeCondition==4){
			 $('#homeCondition').text('无');
		}else{
			$('#homeCondition').text('');
		}
       
		
		if(loanDetails.person.isHaveCarLoan==1){
			 $('#isHaveCarLoan').text('是');
		}else if(loanDetails.person.isHaveCarLoan==2){
			 $('#isHaveCarLoan').text('否');
		}else{
			 $('#isHaveCarLoan').text('');
		}
       
        $('#monthIncome').text(loanDetails.person.monthIncome);
        $('#loanSize').text(loanDetails.person.loanSize);
        
        
        if(loanDetails.person.unitIndustryCategory==1){
			 $('#unitIndustryCategory').text('农、林、牧、渔业');
        }else if(loanDetails.person.unitIndustryCategory==2){
        	$('#unitIndustryCategory').text('能源、采矿业');
        }else if(loanDetails.person.unitIndustryCategory==3){
        	$('#unitIndustryCategory').text('食品、药品、工业原料、服装、日用品等制造业');
        }else if(loanDetails.person.unitIndustryCategory==4){
        	$('#unitIndustryCategory').text('电力、热力、燃气及水生产和供应业');
        }else if(loanDetails.person.unitIndustryCategory==5){
        	$('#unitIndustryCategory').text('建筑业');
        }else if(loanDetails.person.unitIndustryCategory==6){
        	$('#unitIndustryCategory').text('批发和零售业');
        }else if(loanDetails.person.unitIndustryCategory==7){
        	$('#unitIndustryCategory').text('交通运输、仓储和邮政业');
        }else if(loanDetails.person.unitIndustryCategory==8){
        	$('#unitIndustryCategory').text('住宿、旅游、餐饮业');
        }else if(loanDetails.person.unitIndustryCategory==9){
        	$('#unitIndustryCategory').text('信息传输、软件和信息技术服务业');
        }else if(loanDetails.person.unitIndustryCategory==10){
        	$('#unitIndustryCategory').text('金融业');
        }else if(loanDetails.person.unitIndustryCategory==11){
        	$('#unitIndustryCategory').text('房地产业');
        }else if(loanDetails.person.unitIndustryCategory==12){
        	$('#unitIndustryCategory').text('租凭和商务服务业');
        }else if(loanDetails.person.unitIndustryCategory==13){
        	$('#unitIndustryCategory').text('科学研究、技术服务业');
        }else if(loanDetails.person.unitIndustryCategory==14){
        	$('#unitIndustryCategory').text('水利、环境和公共设施管理业');
        }else if(loanDetails.person.unitIndustryCategory==15){
        	$('#unitIndustryCategory').text('居民服务、修理和其他服务业');
        }else if(loanDetails.person.unitIndustryCategory==16){
        	$('#unitIndustryCategory').text('教育、培训');
        }else if(loanDetails.person.unitIndustryCategory==17){
        	$('#unitIndustryCategory').text('卫生、医疗、社会保障、社会福利');
        }else if(loanDetails.person.unitIndustryCategory==18){
        	$('#unitIndustryCategory').text('文化、体育和娱乐业');
        }else if(loanDetails.person.unitIndustryCategory==19){
        	$('#unitIndustryCategory').text('政府、非盈利机构和社会组织');
        }else if(loanDetails.person.unitIndustryCategory==20){
        	$('#unitIndustryCategory').text('警察、消防、军人');
        }else if(loanDetails.person.unitIndustryCategory==21){
        	$('#unitIndustryCategory').text('其他');
        }else{
        	$('#unitIndustryCategory').text('');
        }
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
   	        $('#browseCLForm #foundedDate').text(loanDetails.person.foundedDate);
   	        $('#browseCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
   	        $('#browseCLForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees) +'人');
   	        $('#browseCLForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments) +'%');
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
        	  $('#browseCLForm #childrenSchool').text(transferUndefined(loanDetails.person.childrenSchool));
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
    }
    if(loanDetails.person) {
        $('#browseCLForm #personDeptName').text(loanDetails.person.deptName);
        $('#browseCLForm #personJob').text(loanDetails.person.job);
        $('#browseCLForm #personExt').text(loanDetails.person.ext);
        if(loanDetails.person.incomePerMonth){
        	  $('#browseCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");
        }
        if(loanDetails.person.payDate){
        	  $('#browseCLForm #personPayDay').text(loanDetails.person.payDate + "号");
        }
		$('#browseCLForm #personOtherIncome').text(' ');
        if(loanDetails.person.otherIncome){
            $('#browseCLForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }
        $('#browseCLForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
      
        $('#browseCLForm #personWitness').text(loanDetails.person.witness);
        $('#browseCLForm #personWorkThatDept').text(loanDetails.person.workThatDept);
        $('#browseCLForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
        $('#browseCLForm #personWorkThatTell').text(loanDetails.person.workThatTell);
      
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
    if(loanDetails.salesDept) {
        $('#browseCLForm #salesDeptName').text(loanDetails.salesDept.name);
    }
    if(loanDetails.assessor) {
        $('#browseCLForm #assessorName').text(loanDetails.assessor.name);
    }
    if(loanDetails.loan.remark) {
        $('#browseCLForm #remark').text(loanDetails.loan.remark);
    }
    
    $('#carContacterBrowseTab > #carContacterBrowsePanelTemplate ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#carContacterBrowsePanelTemplate').clone().show().addClass('easyui-panel');
            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
            contacterBrowsePanel.attr("title","联系人"+(i+1));

            contacterBrowsePanel.find('#contacterName').text(contacter.name);
            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);
            contacterBrowsePanel.appendTo($('#carContacterBrowseTab'));
        }
        $.parser.parse('#carContacterBrowseTab');
    }
    if(loanDetails.approveResultList.length>0){ 
    	 	$('#approveResultBrowseTab4').show();
    	 	$('#approveResultBrowsePanelTemplate4').show();
    	    	$("#approveResultBrowseTab4").html("");
    	    	var head=
    	       ' <div style='+'margin:10px auto;text-align:center'+' id='+'approveResultBrowsePanelTemplate4' +'>'+
    	          ' <table style='+'position:relative;margin-left:200px;border-top:solid 2px #ebebeb'+'   cellspacing= '+'10'+'>'+
    	     '  <tr>'+ 
    	     ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签批意见</td>'+ 
    	       '<td style='+'font-weight:bolder'+'>申请金额</td>' +    
    	      ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>期限</td>'+
    	      '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签约事项</td>'+
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>备注</td>'+
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>补充证件</td>'+
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>拒绝/退回原因</td>'+
    	      ' </tr>';
    	    	var html="";
    	    	for(var i =0;i<loanDetails.approveResultList.length;i++){    		
    	    		 var requestMoney=transferUndefined(loanDetails.approveResultList[i].requestMoney);
    	             var term=transferUndefined(loanDetails.approveResultList[i].term);
    	             var state=transferUndefined(loanDetails.approveResultList[i].state);
    	             var reason=transferUndefined(loanDetails.approveResultList[i].reason);
    	             var reason1=transferUndefined(loanDetails.approveResultList[i].reason1);
    	             var reason2=transferUndefined(loanDetails.approveResultList[i].reason2); 
    	             var contractMatters=transferUndefined(loanDetails.approveResultList[i].contractMatters);

    	             var certificates1=transferUndefined(loanDetails.approveResultList[i].certificates1);
		             var certificates2=transferUndefined(loanDetails.approveResultList[i].certificates2);
		             var certificates3=transferUndefined(loanDetails.approveResultList[i].certificates3); 
    	             if(term!=''){
    	            	 term=term+'个月' ;
    	             }
    	             if(requestMoney!=''){
    	            	 requestMoney=requestMoney+'元' ;
    	             }
    	    		html+=	'<tr>'+
    	    		'<td style='+'background:#fff;text-align:center'+'>'+'<label >'+ formatEnumName(state,'APPROVE_RESULT_STATE')+'</label>'+
    	    		'</td>'+
    	    		'<td>'+'<label >'+requestMoney +'</label>'+
    	    		'</td>'+
    	    		'<td style='+'background:#fff;text-align:center'+'>'+    	    		 
    	    		'<label >'+term+'</label>'+'</td>'; 
    	           if(state==30){
    	           		html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	            	html+='<td>'+'<label >'+reason+'</label>'+
    	        		'</td>';
    	        		html+='<td>'+
    	        		'</td>';
    	        		html+='<td>'+'<label >'+"一级原因:"+reason1+"<br>"+"二级原因"+reason2 +'</label>'+
    	        		'</td>';
    	           }else if(state==20){
    	           		html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	           	 	html+='<td>'+'<label >'+reason +"<br>"+'</label>'+'<br>'+'<label >'+
    	          		'</td>';
    	          		html+='<td>'+certificates1+"<br> "+certificates2+"<br> " +certificates3 +'</label>'+'</td>';
    	           }else if(state==31){
    	           		html+='<td> </td>';
    	           		html+='<td>'+'<label >'+reason +"<br>"+'</label>'+'<br>'+'<label >'+
    	          		'</td>';
		        		html+='<td>'+'</td>';
		        		html+='<td>'+'<label >'+ contractMatters +'</label>'+
		        		'</td>';

    	           }else{
    	           	html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	        	   html+='<td>'+'<label >'+reason +"<br>"+'</label>'+
    	          		'</td>';	          
    	           }
    	            html+='</tr>';
    	           
    	    	}
    	    	var end=
    	    	'</table>'+
    	        '  </div>';
    	    	$("#approveResultBrowseTab4").append(head+html+end);   
    	 $.parser.parse($('#approveResultBrowseTab4').parent());
    }else{    	
    	 $('#approveResultBrowseTab4').hide();
    	 $('#approveResultBrowsePanelTemplate4').hide();
    }
	 }
};


/**查看车贷展期*/
function doBrowseCLExtension(loanId){
	
	var flag='audit';
    var strData = getCarExtensionLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();
    $('#browseCLExtensionDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '查看车贷展期');
    if(loanDetails.product) {
        $('#browseCLExtensionForm').find('#productName').text(loanDetails.product.productName);
    }
    if(loanDetails.loan) {
        $('#browseCLExtensionForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
        $('#browseCLExtensionForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
        $('#browseCLExtensionForm #requestTime').text(loanDetails.loan.requestTime + "期");
        $('#browseCLExtensionForm #purpose').text(loanDetails.loan.purpose);
    }
    if(loanDetails.person) {
    	$('#browseCLExtensionForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
      	$('#browseCLExtensionForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
      	if(loanDetails.person.professionType=='自营'){
      		$('.enterpprise1').css('display','table-row');
      		$('.enterpprise2').css('display','table-row');
      		$('#browseCLExtensionForm #privateEnterpriseType').text(transferUndefined(loanDetails.person.privateEnterpriseType));
   	        $('#browseCLExtensionForm #foundedDate').text(loanDetails.person.foundedDate);
   	        $('#browseCLExtensionForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
   	        $('#browseCLExtensionForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees) +'人');
   	        $('#browseCLExtensionForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments) +'%');
   	        $('#browseCLExtensionForm #monthOfProfit').text(transferUndefined(loanDetails.person.monthOfProfit)+'万元/月');
      	}else{
      		$('.enterpprise1').css('display','none');
      		$('.enterpprise2').css('display','none');
      	}
        $('#browseCLExtensionForm #personName').text(loanDetails.person.name);
        $('#browseCLExtensionForm #personSex').text(formatSex(loanDetails.person.sex));
        $('#browseCLExtensionForm #personIdnum').text(loanDetails.person.idnum);
        $('#browseCLExtensionForm #personMarried').text(formatMarried(loanDetails.person.married));
        $('#browseCLExtensionForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
        $('#browseCLExtensionForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
        if(loanDetails.person.hasChildren==1){
        	  $('#browseCLExtensionForm #childrenSchool').text(transferUndefined(loanDetails.person.childrenSchool));
        }      
        $('#browseCLExtensionForm #personMobilePhone').text(loanDetails.person.mobilePhone);
        $('#browseCLExtensionForm #personEmail').text(loanDetails.person.email);
        $('#browseCLExtensionForm #personHomePhone').text(loanDetails.person.homePhone);
        $('#browseCLExtensionForm #personPlaceDomicile').text(loanDetails.person.placeDomicile);
        $('#browseCLExtensionForm #personHouseholdZipCode').text(loanDetails.person.householdZipCode);
        $('#browseCLExtensionForm #personAddress').text(loanDetails.person.address);
        // 根据居住类型，决定每月租金和每月房贷是否显示，规则
        // 如果居住类型是按揭房，则显示每月房贷
        // 如果居住类型是租赁，则显示每月租金
        // 如果其他的，则不显示每月租金和每月房贷
        $('#browseCLExtensionForm #personLiveType').text(loanDetails.person.liveType);
        var liveType = loanDetails.person.liveType;
        var liveTypeTR =  $('#browseCLExtensionForm #personLiveType').parent().parent();
        if(liveType=='按揭房'){
        	liveTypeTR.find(':nth-child(3)').text('每月房贷').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#browseCLExtensionForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#browseCLExtensionForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        	
        } else if(liveType == '租赁'){
        	liveTypeTR.find(':nth-child(3)').text('每月租金').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#browseCLExtensionForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#browseCLExtensionForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        } else{
        	liveTypeTR.find(':nth-child(3)').hide();
        	liveTypeTR.find(':nth-child(4)').hide();
        }

        $('#browseCLExtensionForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
        $('#browseCLExtensionForm #personZipCode').text(loanDetails.person.zipCode);
    }
    if(loanDetails.vehicle) {
        $('#browseCLExtensionForm #vehicleBrand').text(loanDetails.vehicle.brand);
        $('#browseCLExtensionForm #vehicleModel').text(loanDetails.vehicle.model);
        $('#browseCLExtensionForm #vehicleCoty').text(loanDetails.vehicle.coty+"年");
        $('#browseCLExtensionForm #vehicleMileage').text(loanDetails.vehicle.mileage+"公里");
        $('#browseCLExtensionForm #vehiclePlateNumber').text(loanDetails.vehicle.plateNumber);
        $('#browseCLExtensionForm #vehicleFrameNumber').text(loanDetails.vehicle.frameNumber);
    }
    if(loanDetails.company) {
        $('#browseCLExtensionForm #companyName').text(loanDetails.company.name);
        $('#browseCLExtensionForm #companyAddress').text(loanDetails.company.address);
    }
    if(loanDetails.person) {
        $('#browseCLExtensionForm #personDeptName').text(loanDetails.person.deptName);
        $('#browseCLExtensionForm #personJob').text(loanDetails.person.job);
        $('#browseCLExtensionForm #personExt').text(loanDetails.person.ext);
        if(loanDetails.person.incomePerMonth){
        	  $('#browseCLExtensionForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");
        }
        if(loanDetails.person.payDate){
        	  $('#browseCLExtensionForm #personPayDay').text(loanDetails.person.payDate + "号");
        }

		$('#browseCLExtensionForm #personOtherIncome').text(' ');
		if(loanDetails.person.otherIncome){
			$('#browseCLExtensionForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }
        $('#browseCLExtensionForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
      
        $('#browseCLExtensionForm #personWitness').text(loanDetails.person.witness);
        $('#browseCLExtensionForm #personWorkThatDept').text(loanDetails.person.workThatDept);
        $('#browseCLExtensionForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
        $('#browseCLExtensionForm #personWorkThatTell').text(loanDetails.person.workThatTell);
      
    }
    if(loanDetails.creditHistory) {
        $('#browseCLExtensionForm #creditHistoryHasCreditCard').text(formatHave(loanDetails.creditHistory.hasCreditCard));
        if(loanDetails.creditHistory.hasCreditCard==1){
	        $('#browseCLExtensionForm #creditHistoryCardNum').text(transferUndefinedAndZero(loanDetails.creditHistory.cardNum));
	        $('#browseCLExtensionForm #creditHistoryTotalAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.totalAmount) + "元");
	        $('#browseCLExtensionForm #creditHistoryOverdrawAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.overdrawAmount) + "元");
    	}else{
    		 $('#browseCLExtensionForm #creditHistoryCardNum').text('');
 	        $('#browseCLExtensionForm #creditHistoryTotalAmount').text('');
 	        $('#browseCLExtensionForm #creditHistoryOverdrawAmount').text('');
    	}
    }
    if(loanDetails.service) {
        $('#browseCLExtensionForm #serviceName').text(loanDetails.service.name);
    }
    if(loanDetails.loan) {
        $('#browseCLExtensionForm #customerSource').text(loanDetails.loan.customerSource);
        $('#browseCLExtensionForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
    }
    if(loanDetails.crm) {
        $('#browseCLExtensionForm #crmCode').text(loanDetails.crm.code);
        $('#browseCLExtensionForm #crmName').text(loanDetails.crm.name);
    }
    //业务主任
    if(loanDetails.director) {
        $('#browseCLExtensionForm #directorCode').text(loanDetails.director.code);
        $('#browseCLExtensionForm #directorName').text(loanDetails.director.name);
    }
    if(loanDetails.salesDept) {
        $('#browseCLExtensionForm #salesDeptName').text(loanDetails.salesDept.name);
    }
    if(loanDetails.assessor) {
        $('#browseCLExtensionForm #assessorName').text(loanDetails.assessor.name);
    }
    if(loanDetails.loan.remark) {
        $('#browseCLExtensionForm #remark').text(loanDetails.loan.remark);
    }
    
    $('#carExtensionContacterBrowseTab > #carExtensionContacterBrowsePanelTemplate ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#carExtensionContacterBrowsePanelTemplate').clone().show().addClass('easyui-panel');
            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
            contacterBrowsePanel.attr("title","联系人"+(i+1));

            contacterBrowsePanel.find('#contacterName').text(contacter.name);
            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);

            contacterBrowsePanel.appendTo($('#carExtensionContacterBrowseTab'));
        }
        $.parser.parse('#carExtensionContacterBrowseTab');
    }
    if(loanDetails.approveResultList.length>0){ 
    	 	$('#approveExtensionResultBrowseTab4').show();
    	 	$('#approveExtensionResultBrowsePanelTemplate4').show();
    	    	$("#approveExtensionResultBrowseTab4").html("");
    	    	var head=
    	       ' <div style='+'margin:10px auto;text-align:center'+' id='+'approveResultBrowsePanelTemplate4' +'>'+
    	          ' <table style='+'position:relative;margin-left:200px;border-top:solid 2px #ebebeb'+'   cellspacing= '+'10'+'>'+
    	     '  <tr>'+ 
    	     ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签批意见</td>'+ 
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>申请金额</td>' +    
    	      ' <td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>期限</td>'+
    	      '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>签约事项</td>'+
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>备注</td>'+
    	       '<td style='+'font-weight:bolder;background:#fff;text-align:center;font-weight:bolder'+'>拒绝/退回原因</td>'+
    	      ' </tr>';
    	    	var html="";
    	    	for(var i =0;i<loanDetails.approveResultList.length;i++){    		
    	    		 var requestMoney=transferUndefined(loanDetails.approveResultList[i].requestMoney);
    	             var term=transferUndefined(loanDetails.approveResultList[i].term);
    	             var state=transferUndefined(loanDetails.approveResultList[i].state);
    	             var reason=transferUndefined(loanDetails.approveResultList[i].reason);
    	             var reason1=transferUndefined(loanDetails.approveResultList[i].reason1);
    	             var reason2=transferUndefined(loanDetails.approveResultList[i].reason2); 
    	             var contractMatters=transferUndefined(loanDetails.approveResultList[i].contractMatters);

    	             var certificates1=transferUndefined(loanDetails.approveResultList[i].certificates1);
		             var certificates2=transferUndefined(loanDetails.approveResultList[i].certificates2);
		             var certificates3=transferUndefined(loanDetails.approveResultList[i].certificates3); 
		    	             if(term!=''){
    	            	 term=term+'个月' ;
    	             }
    	             if(requestMoney!=''){
    	            	 requestMoney=requestMoney+'元' ;
    	             }
    	    		html+=	'<tr>'+
    	    		'<td style='+'background:#fff;text-align:center'+'>'+'<label >'+ formatEnumName(state,'APPROVE_RESULT_STATE')+'</label>'+
    	    		'</td>'+
    	    		'<td>'+'<label >'+requestMoney +'</label>'+
    	    		'</td>'+
    	    		'<td style='+'background:#fff;text-align:center'+'>'+    	    		 
    	    		'<label >'+term+'</label>'+'</td>'; 
    	            if(state==30){
    	            	html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	            	html+='<td>'+'<label >'+reason+'</label>'+
    	        		'</td>';
    	        		
    	        		html+='<td>'+'<label >'+"一级原因:"+reason1+"<br>"+"二级原因"+reason2 +'</label>'+
    	        		'</td>';
    	           }else if(state == 31){
    	           		html+='<td></td>';
    	            	html+='<td>'+'<label >'+reason+'</label>'+
    	        		'</td>';
    	        		
    	           		html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	        		

    	           }else{
    	           		html+='<td>'+'<label >'+contractMatters +'</label>'+'</td>';
    	        	   html+='<td>'+'<label >'+reason +"<br>"+'</label>'+
    	          		'</td>';	          
    	           }
    	            html+='</tr>';
    	           
    	    	}
    	    	var end=
    	    	'</table>'+
    	        '  </div>';
    	    	$("#approveExtensionResultBrowseTab4").append(head+html+end);   
    	 $.parser.parse($('#approveExtensionResultBrowseTab4').parent());
    }else{    	
    	 $('#approveExtensionResultBrowseTab4').hide();
    	 $('#approveExtensionResultBrowsePanelTemplate4').hide();
    }
	
};


function search(){
	var queryParams = $('#loanPageTb').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personNameTxt').val();
	queryParams.personIdnum = $('#toolbar #personIdnumTxt').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.salesDeptId = $('#toolbar #salesDeptComb').combobox('getValue');
	queryParams.status = $('#toolbar #loanStatusComb').combobox('getValue');
    queryParams.extensionTime = $('#toolbar #extensionTimeComb').combobox('getValue');
    queryParams.flag = null;
    setFirstPage("#loanPageTb");
	$('#loanPageTb').datagrid('options').queryParams = queryParams;
	$("#loanPageTb").datagrid('reload');
};
window.onload = search;
// 	START  小企业贷同意签批
function submitConfirm() {
	$('#approveConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' ');
	var requestMoney = $('#'+currentAuditData.curAuditDialog+' #approveForm #agreeMoneyInput').val();
	var requestTime = $('#'+currentAuditData.curAuditDialog+' #approveForm #agreeTimeComb').combobox('getValue');
	
	$('#approveConfirmDlg #approveResult').val("同意");
	$('#approveConfirmDlg #requestMoney').val(requestMoney+"元");
	$('#approveConfirmDlg #requestTime').val(requestTime+"个月");
}
//小企业贷-所有产品页面渲染
function renderSeLoanApproveDialog(approveDialogId,loanDetails){
		currentAuditData.curAuditDialog = approveDialogId;
		currentAuditData.loanDetails = loanDetails;
		currentAuditData.formId = 'approveForm';
		$('#'+approveDialogId+' #approveForm'+ ' #agreeSubmitBt').bind('click', submitConfirm);
		// $("#"+currentAuditData.formId +" #conditionalAgreeSubmitBt").bind('click', conditionalApproveConfirm);
		//小企业贷拒绝一级二级原因加载
		$('#'+approveDialogId+' #approveForm').find('#refuseFirstReason').combobox({     
		    valueField:'id',
		    textField:'reason',
		    onChange:function(reason1){ 
		    	$('#'+approveDialogId+' #approveForm').find('#refuseSecondReason').combobox({     
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

		
		renderCondApprRadio('approvePanel');
		
		loadApproveTermOfBorrowing();

}

function renderCondApprRadio(approvePanel){
	var approveDialogId = currentAuditData.curAuditDialog;
	//小企业贷款选择签批类型  
		$('#'+approveDialogId+" #"+approvePanel+" > input:radio").change(function() {    //id 为season行内radio值变化函数  
				var approveResult = $('#'+approveDialogId+" #"+approvePanel+" input[name='agreementRD']:checked").val();
				if(approveResult == "10"){
					$('#'+approveDialogId+" #"+approvePanel+" #agreeTb").show();
					$('#'+approveDialogId+" #"+approvePanel+" #conditionalAgreeTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #refuseTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #returnTable").hide();
				} else if(approveResult == "20") {
					$('#'+approveDialogId+" #"+approvePanel+" #conditionalAgreeTb").show();
					$('#'+approveDialogId+" #"+approvePanel+" #agreeTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #refuseTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #returnTable").hide();
				} else if(approveResult == "30"){
					$('#'+approveDialogId+" #"+approvePanel+" #refuseTb").show();
					$('#'+approveDialogId+" #"+approvePanel+" #conditionalAgreeTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #agreeTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #returnTable").hide();
				} else if(approveResult == "31"){//退回 
					$('#'+approveDialogId+" #"+approvePanel+" #returnTable").show();
					$('#'+approveDialogId+" #"+approvePanel+" #refuseTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #conditionalAgreeTb").hide();
					$('#'+approveDialogId+" #"+approvePanel+" #agreeTb").hide();
				}
		});	
	$('#'+approveDialogId+' #certificates1Input').val('');
	$('#'+approveDialogId+'  #certificates2Input').hide().val('');
	$('#'+approveDialogId+'  #certificates3Input').hide().val('');
	
	$('#'+approveDialogId+' textarea').each(function(){
		$(this).val('');
	});
	$("#"+currentAuditData.formId+" #conditionalAgreeSubmitBt").bind('click', conditionalApproveConfirm);
	$("#"+approveDialogId+" #approveForm"+" #conditionalAgreeCancelBt").bind('click', closeApproveDlg);
	$("#"+approveDialogId+" #approveCLForm"+" #conditionalAgreeCancelBt").bind('click', closeApproveCLDlg);
	$("#"+approveDialogId+" #approveExtensionCLPanel"+" #conditionalAgreeCancelBt").bind('click', closeApproveExtensionCLDlg);

	$("#"+approveDialogId+" #addNeedCardInputBt").bind('click', addNeedCardInput);
	
	$("#"+approveDialogId+" #certificates2Input").hide();
	$("#"+approveDialogId+" #certificates3Input").hide();
}

function renderConApprGua(natrueGuaId,legalGuaId) {
	var dialogId = currentAuditData.curAuditDialog;


	var natrueGuadiv = $('#'+dialogId+' #'+natrueGuaId).empty();
	var legalGuaDiv = $('#'+dialogId+' #'+legalGuaId).empty();
	if (currentAuditData.loanDetails.guaranteeList) {
		
		$.grep(currentAuditData.loanDetails.guaranteeList,function(e,i) {
			var div ;
			e.guaranteeType == 0 ?div = $(natrueGuadiv):div = $(legalGuaDiv);
			div
			// .append($('<tr/>')
				.append($('<input  type="checkbox" ></input>').val(e.name).attr('guaID',e.id))
				.append($('<label></label>').text(e.name));			
		});
	};
	 // loadCWConApprGua();
}

function getCWApprFormData(){
	var dialogId = currentAuditData.curAuditDialog;
	var data = {guarantee:{natural:{},legal:{}}};
	$.grep(['CWconAgrNaGuaLi','CWconAgrLeGuaLi'],function(e,i){
		var divId = e;
		$('#'+dialogId+' #'+e+' input:checked').each(function(i,e){
			
			var index =i+1;
			// divId == 'CWconAgrNaGuaLi' ? index =i+1 : index=i+3;
			var attr = 'guaName'+index;

			divId == 'CWconAgrNaGuaLi'?data.guarantee.natural[attr] = $(e).val():data.guarantee.legal[attr] = $(e).val();
			// data.guarantee[attr] = $(e).val();
			
		});
		
	});

	return data;

};
function getAllCheckdGua (data,guaIds) {
	var dialogId = currentAuditData.curAuditDialog;
	var guaranteeName = '';
	var naturalIndex = 1,legalIndex = 1;
	$('#'+dialogId+' .guaLiCheckBox').each(function(i,e){
		var guaEle = $(this);

			$(this).find('input:checked').each(function(i,e){
				
				guaranteeName += $(e).val()+',';
				if(guaIds){
					guaIds.push($(e).attr('guaID'));
				}
				
				if(data){
					if($(guaEle).hasClass('natural')){
						var attr = 'guaName'+naturalIndex;
						data.guarantee.natural[attr]= $(e).val();
						naturalIndex++;

					}else if($(guaEle).hasClass('legal')){
						var attr = 'guaName'+legalIndex;
						data.guarantee.legal[attr]= $(e).val();
						legalIndex++;
						
					}
				}
			});
			
		});
	return guaranteeName;
}
function loadApproveTermOfBorrowing() {
	var agreeTimeCombList = ['agreeTimeComb','conditionalAgreeTimeComb'];
	$.grep(agreeTimeCombList,function(e,i) {
		var value = currentAuditData.loanDetails.loan.loanType
		//新增页面，移交类1，流通类2，
        //产品配置页面，流通类1，移交类2，
        //不知道那个大神做的。，为了不影响代码，做转换
        if(value == 1){
        	value = 2;
        } else if(value == 2){
        	value = 1;
        }
		$('#'+currentAuditData.curAuditDialog).find('#'+e).combobox({
//			url:'apply/getProductTermsByProductId?productId='+currentAuditData.loanDetails.product.id,
	    url:'apply/findListByVO?productId='+currentAuditData.loanDetails.product.id+'&carProductType='+value,
	    valueField:'term',
	    textField:'termName',
	    onLoadSuccess:function(){
        	var data = $('#'+currentAuditData.curAuditDialog).find('#'+e).combobox('getData');
        	if(data.length==1)
        		$('#'+currentAuditData.curAuditDialog).find('#'+e).combobox('select', data[0].term || data[0].id);
        }
	 });
	});
}

//签批之前先调用这个方法之后调用签批的方法
function approveShadow(loanId,productType,productCode){
	$.ajax({
		  type: "POST",
		  url: "audit/getZongAnReturnData",
		  dataType: "json",
		  data: {
			  loanId:loanId
		  },
		  success: function(message){
			  //一开始先给他RADIO设置成全部可以选择(要不然如果先签批结果未通过的信息，在签批结果通过的信息RADIO有问题)
			  	 var reRadio1=$('#approveCLPanel input[name="agreementRD"]').eq(0);	
				 reRadio1.attr('disabled',false);
				 var reRadio2=$('#approveCLPanel input[name="agreementRD"]').eq(1);	
				 reRadio2.attr('disabled',false);
				 var reRadio3=$('#approveCLPanel input[name="agreementRD"]').eq(3);	
				 reRadio3.attr('disabled',false);
				 
			 if(message!=null){
				 if(message.RISKGRADE!=null&&message.RISKGRADE!=''){
					  $('#zonganGrade').html(message.RISKGRADE);
				  }
				 if(message.AUDITRESULT!=null&&message.AUDITRESULT!=''){
					 if(message.AUDITRESULT=='0'){
						 $('#zonganResult').html('审核通过');
						 
					 }else if(message.AUDITRESULT=='1'){
						 $('#zonganResult').html('身份信息未通过');
						 
					 }else if(message.AUDITRESULT=='2'){
						 $('#zonganResult').html('黑名单未通过');
						 
					 }else{
						 $('#zonganResult').html('逾期未通过');
						 
					 }
					 
				 }
			 }
			 //执行原本的代码
			 approve(loanId,productType,productCode);
			 
			//如果众安反欺诈有值，并且结果！=审核通过，在最后给他选好拒绝（radio）
			if(message!=null&&message.AUDITRESULT!='0'){
				
				 var reRadio=$('#approveCLPanel input[name="agreementRD"]').eq(2);	
				 reRadio.attr('checked','checked');
				 //显示拒绝的TABLE页面
				 $("#approveCLDlg #approveCLPanel #refuseTb").show();
				 $("#approveCLDlg #approveCLPanel #conditionalAgreeTb").hide();
				 $("#approveCLDlg #approveCLPanel #agreeTb").hide();
				 $("#approveCLDlg #approveCLPanel #returnTable").hide();
				 var reRadio1=$('#approveCLPanel input[name="agreementRD"]').eq(0);	
				 reRadio1.attr('disabled',true);
				 var reRadio2=$('#approveCLPanel input[name="agreementRD"]').eq(1);	
				 reRadio2.attr('disabled',true);
				 var reRadio3=$('#approveCLPanel input[name="agreementRD"]').eq(3);	
				 reRadio3.attr('disabled',true);
			}
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批回显等级和结果失败！',
					showType:'slide'
			  });
		  }
		});
	
	
}

//签批
function approve(loanId,productType,productCode){
	if(productType == 1){

			doApprove(loanId,productCode);			
		
	}else if(productType == 2){
		auditRefuse(productType);
		doCLApprove(loanId);
	}
}

//展期签批
function extensionApprove(loanId, productId){
	auditExtensionRefuse(productId);
	doCLExtensionApprove(loanId);
}
//审贷会决议拒绝
function  auditRefuse(productId,approveDialogId){
	if(productId == 1){
	     $('#'+approveDialogId+' #approveForm').find('#refuseFirstReason').combobox('clear');
	     $('#'+approveDialogId+' #approveForm').find('#refuseSecondReason').combobox('clear');
	 	$('#'+approveDialogId+' #approveForm').find('#refuseFirstReason').combobox({     
	 		 url:'master/rejectReason/getRefuseFirstReason',
			    valueField:'id',
			    textField:'reason',
			    onBeforeLoad: function(param){                    		    	
						param.productId = productId;								
					}
		});
	}
	else if(productId == 2){
		 $('#approveCLForm').find('#refuseFirstReason').combobox('clear');
		 $('#approveCLForm').find('#refuseSecondReason').combobox('clear');
		 $('#approveCLForm').find('#refuseFirstReason').combobox({     
	 		 url:'master/rejectReason/getRefuseFirstReason',
			    valueField:'id',
			    textField:'reason',
			    onBeforeLoad: function(param){                    		    	
						param.productId = productId;								
					}
		});
	}
}

//展期审贷会决议拒绝
function  auditExtensionRefuse(productId){

     $('#approveExtensionCLForm').find('#refuseFirstReason').combobox('clear');
     $('#approveExtensionCLForm').find('#refuseSecondReason').combobox('clear');
 	 $('#approveExtensionCLForm').find('#refuseFirstReason').combobox({     
 		 url:'master/rejectReason/getRefuseFirstReason',
		    valueField:'id',
		    textField:'reason',
		    onBeforeLoad: function(param){                    		    	
					param.productId = productId;								
				}
	});
	
}
//小企业贷同意
function submitAgree(customizeData){
	var dialogId = currentAuditData.curAuditDialog;
	var productCode =currentAuditData.loanDetails.product.productCode||'seLoan/seLoan';
	productCode = productCode.slice(productCode.lastIndexOf('/')+1)+'Audit'+'';
	 if(!$('#approveForm')[productCode]('validateAgreeComponents'))
		 return;
	 var loanId = $('#'+dialogId+' #approveForm #loanId').val();
	 var state = $('#'+dialogId+" #approveForm input[name='agreementRD']:checked").val();
	 var auditMoney = $('#'+dialogId+' #approveForm #agreeMoneyInput').val();
	 var auditTime = $('#'+dialogId+' #approveForm #agreeTimeComb').combobox('getValue');
	 var reason =$('#'+dialogId+' #approveForm #agreeReason').val();
	 var requestMoney = $('#'+dialogId+' #approveForm #agreeMoneyInput').val();
	 var term =$('#'+dialogId+' #approveForm #agreeTimeComb').combobox('getValue');
	 var hasHouse;
	 if($('#'+dialogId+' #approveForm #hasHouse').length > 0 && $($('#'+dialogId+' .apprHasHouse')[0]).attr('disabled') !='disabled')
	 	 hasHouse =$('#'+dialogId+' #agreeTb #hasHouse').combobox('getValue');
	 var contractMatters =$('#'+dialogId+' #approveForm #contractMatters').val();	
	 var data = {
			  loanId:loanId,
			  state:state,
			  auditMoney:auditMoney,
			  reason:reason,
			  term:auditTime,
			  requestMoney:requestMoney,
			  term:term,
			  hasHouse:hasHouse,
			  contractMatters:contractMatters
		  };
	data  = $.extend(true,{},data,customizeData);
	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: data,
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#'+dialogId).dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});
};



function validateRefuseComponents(){
	var dialogId = currentAuditData.curAuditDialog;
	var passed = true;
	var  refuseFirstReasonId= $('#'+dialogId+' #approveForm #refuseFirstReason').combobox('getValue');
	var  refuseSecondReasonId= $('#'+dialogId+' #approveForm #refuseSecondReason').combobox('getValue');
	if(refuseFirstReasonId=='' ||refuseSecondReasonId=='')
	{
		passed= false;
		$.messager.show({
	          title:'拒绝原因',
	          msg:'请选择退回门店的一二级原因！',
	          showType:'slide'
	      });
	}
	return passed;
}
function validateAgreeCLAndAgreeExtensionAmount(loanType,agreeMoney,requestMoney,passed){
	
	if(requestMoney < agreeMoney) {
		 $.messager.show({
             title:'审批金额不合法',
             msg:'审批金额不能大于申请金额！',
             showType:'slide'
         });
		 passed= false;
	}
	if(agreeMoney%1000 != 0) {
		 $.messager.show({
          title:'审批金额不合法',
          msg:'审批金额必须是1000的倍数！',
          showType:'slide'
      });
		 passed = false;
	}
	if('流通类' === loanType || '移交类' === loanType ){
		if( agreeMoney < 1000 ){
			$.messager.show({
		        title:'审批金额不合法',
		        msg:loanType+'贷款金额必须至少1000',
		        showType:'slide'
	    	});
	 		passed= false;	
		} 
	}

	return passed;
}
function validateAgreeCLComponents(){
	var passed = true;
	
	if(! $('#approveCLForm #agreeMoneyInput').valid()){
		passed = false;
	}
	
	var agreeMoney= $('#approveCLForm #agreeMoneyInput').val();
	var requestMoney = parseInt($('#approveCLForm #requestMoney').text());
	
	var loanType = $('#approveCLForm #loanType').text();
	return validateAgreeCLAndAgreeExtensionAmount(loanType,agreeMoney,requestMoney,passed);

	// var auditTime = $('#approveCLForm #agreeTimeComb').combobox('getValue');
}


function validateAgreeExtensionCLComponents(){
	var passed = true;
	
	if(! $('#approveExtensionCLForm #agreeMoneyInput').valid()){
		passed = false;
	}
	
	var agreeMoney= $('#approveExtensionCLForm #agreeMoneyInput').val();
	var requestMoney = parseInt($('#approveExtensionCLForm #requestMoney').text());
	
	var loanType = $('#approveExtensionCLForm #loanType').text();
	// var auditTime = $('#approveCLForm #agreeTimeComb').combobox('getValue');
	return validateAgreeCLAndAgreeExtensionAmount(loanType,agreeMoney,requestMoney,passed);
}

function validateRefuseExtensionCLComponents(){
	var passed = true;
	var  refuseFirstReasonId= $('#approveExtensionCLForm #refuseFirstReason').combobox('getValue');
	var  refuseSecondReasonId= $('#approveExtensionCLForm #refuseSecondReason').combobox('getValue');
	if(refuseFirstReasonId=='' ||refuseSecondReasonId=='')
	{
		passed= false;
		$.messager.show({
	          title:'拒绝原因',
	          msg:'请选择退回门店的一二级原因！',
	          showType:'slide'
	      });
	}
	return passed;
}
function validateRefuseCLComponents(){
	var passed = true;
	var  refuseFirstReasonId= $('#approveCLForm #refuseFirstReason').combobox('getValue');
	var  refuseSecondReasonId= $('#approveCLForm #refuseSecondReason').combobox('getValue');
	if(refuseFirstReasonId=='' ||refuseSecondReasonId=='')
	{
		passed= false;
		$.messager.show({
	          title:'拒绝原因',
	          msg:'请选择退回门店的一二级原因！',
	          showType:'slide'
	      });
	}
	return passed;
}
function closeApproveDlg(){	
	var dialogId = currentAuditData.curAuditDialog || 'approveDlg';
	$('#'+dialogId).dialog('close');
}
function closeApproveCLDlg(){
	$('#approveCLDlg').dialog('close');
}
function closeApproveExtensionCLDlg(){
	$('#approveExtensionCLDlg').dialog('close');
}
function checkCLinput() {
	var flag = true;
	for(var i = 0;i < arguments.length; i++) { 
        
        if(arguments[i].length >200)
        	flag =  false;
    } 
    return flag;
}
//车贷同意签批
function submitCLAgree(){
	 if(!validateAgreeCLComponents())
		 return;

	 var loanId = $('#approveCLForm #loanId').val();
	 var state = $("#approveCLForm input[name='agreementRD']:checked").val();
	 var auditMoney = $('#approveCLForm #agreeMoneyInput').val();
	 var auditTime = $('#approveCLForm #agreeTimeComb').combobox('getValue');
	 var reason =$('#approveCLForm #agreeReason').val();
	 var requestMoney = $('#approveCLForm #agreeMoneyInput').val();
	 var term =$('#approveCLForm #agreeTimeComb').combobox('getValue');
	 var contractMatters =$('#approveCLForm #contractMatters').val();
	 var loanType = $("#approveCLForm input[name='loanType']").val();
	 var productId = $("#approveCLForm input[name='productType']").val();
	 if(!productId){
		 productId =  currentAuditData.loanDetails.product.id;
	 }
	 if(checkCLinput(contractMatters ) != true){
	 	$.messager.show({
					
					msg:'签约事项输入信息过长!',
					showType:'slide'
			  });
	 	return false;
	 };

	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  auditMoney:auditMoney,
			  reason:reason,
			  term:auditTime,
			  requestMoney:requestMoney,
			  term:term,
			  contractMatters:contractMatters,
			  loanType:loanType,
			  productId:productId
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});
};
//车贷展期同意签批
function submitExtensionCLAgree(){
	 if(!validateAgreeExtensionCLComponents())
		 return;

	 var loanId = $('#approveExtensionCLForm #loanId').val();
	 var state = $("#approveExtensionCLForm input[name='agreementRD']:checked").val();
	 var auditMoney = $('#approveExtensionCLForm #agreeMoneyInput').val();
	 var auditTime = $('#approveExtensionCLForm #agreeTimeComb').combobox('getValue');
	 var reason =$('#approveExtensionCLForm #agreeReason').val();
	 var requestMoney = $('#approveExtensionCLForm #agreeMoneyInput').val();
	 var term =$('#approveExtensionCLForm #agreeTimeComb').combobox('getValue');
	 var contractMatters =$('#approveExtensionCLForm #contractMatters').val();	
	 if(checkCLinput(contractMatters ) != true){
	 	$.messager.show({
					
					msg:'签约事项输入信息过长!',
					showType:'slide'
			  });
	 	return false;
	 };
	 $.ajax({
		  type: "POST",
		  url: "audit/extensionApprove",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  auditMoney:auditMoney,
			  reason:reason,
			  term:auditTime,
			  requestMoney:requestMoney,
			  term:term,
			  contractMatters:contractMatters
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveExtensionCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});
};
//车贷退回门店
function carReturn(){
	 var loanId = $('#approveCLForm #loanId').val();
	 var state = $("#approveCLForm input[name='agreementRD']:checked").val();
	 var contractMatters =$('#approveCLForm #returnContractMatters').val();
	 var reason =$('#approveCLForm #returnReason').val();
	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  contractMatters:contractMatters
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});

}
//车贷展期退回门店
function carExtensionReturn(){
	 var loanId = $('#approveExtensionCLForm #loanId').val();
	 var state = $("#approveExtensionCLForm input[name='agreementRD']:checked").val();
	 var contractMatters =$('#approveExtensionCLForm #returnContractMatters').val();
	 var reason =$('#approveExtensionCLForm #returnReason').val();
	 $.ajax({
		  type: "POST",
		  url: "audit/extensionApprove",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  contractMatters:contractMatters
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveExtensionCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});

}
//小企业贷款退回门店
function businessReturn(){
	 var dialogId = currentAuditData.curAuditDialog;
	 var loanId = $('#'+dialogId+' #approveForm #loanId').val();
	 var state = $('#'+dialogId+" #approveForm input[name='agreementRD']:checked").val();
	 var  contractMatters =null;
	 var  reason =null;
	 if($('#'+dialogId+' #approveForm #returnContractMatters').val()!=''){
		 contractMatters =  $('#'+dialogId+' #approveForm #returnContractMatters').val();
	 }
	 if($('#'+dialogId+' #approveForm #returnReason').val()!=''){			
		reason = $('#'+dialogId+' #approveForm #returnReason').val();
	 }
	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  contractMatters:contractMatters
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#'+dialogId).dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});

}

	// START 小企业贷有条件同意签批
function conditionalApproveConfirm() {
	//alert('2');
	//添加校验判断等级和结果是否存在
	var zonganGrade=$('#zonganGrade').html();
	var zonganResult=$('#zonganResult').html();
	if(zonganGrade==null||zonganGrade==''||zonganResult==null||zonganResult==''){
		$.messager.show({
            title:'结果',
            msg:'捞财宝未返回风控结果，请稍后！',
            showType:'slide'
        });
		return;
	}
	
	
	
	 
	  
	  //对于同意的单子，要判断合同金额是否大于20W
			var name=$('#approveCLDlg #personName').html();
			var idNo=$('#approveCLDlg #personIdnum').html();
			var loanId=$('#approveCLDlg #loanId').val();
			var agreeMoneyInput=$('#approveCLDlg #conditionalAgreeMoneyInput').val();
			var agreeTimeComb=$('#approveCLDlg #conditionalAgreeTimeComb').combobox('getValue');
			 $.ajax({
				  type: "POST",
				  url: 'audit/contractManeyOverProof',
				  dataType: "json",
				  data: {
					  name:name,
					  idNo:idNo,
					  loanId:loanId,
					  agreeMoneyInput:agreeMoneyInput,
					  agreeTimeComb:agreeTimeComb
				  },
				  success: function(message){
					  if(message.SUMMONEY==true){
						  $.messager.show({
								title:'提示',
								msg:'合同金额过高，请降额或者修改期限',
								showType:'slide'
						  });
						  return;
					  }else{
						  var tempData= {guarantee:{natural:{},legal:{}}};
							var data = getCWApprFormData();
							getAllCheckdGua(tempData);
							data  = $.extend(true,data,tempData);
							 // var guaranteeName1=  data.guarantee.guaName1 || $('#conditionalAgreeGuaranteeInput1').val();
							 // var guaranteeName2 = data.guarantee.guaName2 || $('#conditionalAgreeGuaranteeInput2').val();
							 // var guaranteeName3 = data.guarantee.guaName3 || $('#conditionalAgreeGuaranteeInput3').val();
							 // var guaranteeName4 = data.guarantee.guaName4 || $('#conditionalAgreeGuaranteeInput4').val();
							 var auditMoney = $('#'+currentAuditData.curAuditDialog+' #'+currentAuditData.formId+' #conditionalAgreeMoneyInput').val();
							 var auditTime = $('#'+currentAuditData.curAuditDialog+' #'+currentAuditData.formId+' #conditionalAgreeTimeComb').combobox('getValue');
							// var guaList= [];
							// // if (data){
							// 	guaList[1] = data.guarantee.natural.guaName1;
							// 	guaList[2] = data.guarantee.natural.guaName2;
							// 	guaList[3] = data.guarantee.legal.guaName1;
							// 	guaList[4] = data.guarantee.legal.guaName2;
							// // }
							
							$('#conditionalApproveConfirmDlg #approveResult').val('有条件同意');
							$('#conditionalApproveConfirmDlg #requestMoney').val(auditMoney+"元");
							$('#conditionalApproveConfirmDlg #requestTime').val(auditTime+"个月");

							$('#conditionalApproveConfirmDlg #guaContent').empty();
							var natural = $('<ul></ul>').append($('<li><label>自然人担保 :</label></li>'));
							var legal = $('<ul></ul>').append($('<li><label>法人担保 :</label></li>'));
							 $.each(data.guarantee.natural,function(name,val){
								var li = '<li>'+val+'</li>';
								$(li).appendTo(natural);

							 });
							 $.each(data.guarantee.legal,function(name,val){
								var li = '<li>'+val+'</li>';
								$(li).appendTo(legal);

							 });
							 
							 $(natural)[0].childNodes.length>1?$(natural).appendTo($('#conditionalApproveConfirmDlg #guaContent')):'';
							 $(legal)[0].childNodes.length>1?$(legal).appendTo($('#conditionalApproveConfirmDlg #guaContent')):'';
							// $.grep(data.guarantee.natural,function(e){

							// });
							// $.grep(data.guarantee.legal,function(e){
							// 	var li = '<li>'+e+'</li>';
							// 	$(li).appendTo(legal).appendTo($('#conditionalApproveConfirmDlg #content'));

							// });
							// for(var i = 1; i < 5; i++){
							// 	var  guaType,guaName;
							// 	i<3?guaType = '自然人担保':guaType = '法人担保';
							// 	guaName = guaList[i] || $('#conditionalAgreeGuaranteeInput'+i).val();

							// 		var guaText = guaType+" ：  "  + guaName;
							// 		var guaID = 'guarantee'+i;
							// 			$('#conditionalApproveConfirmDlg #guarantee'+i)[0].outerHTML= '<label '+' id ='+guaID+'>'+'</label>';
							// 			guaName != ''?$('#conditionalApproveConfirmDlg #guarantee'+i)[0].outerHTML= '<label '+' id ='+guaID+'>'+guaText+'</label>':'';
							// }

							// }


							
							// for (var i = 1; i < 5; i++) {
							// 	var guaNameKey = 'guaName'+i;
							// 	var guaType;
							// 	var guaName = data.guarantee[guaNameKey] || $('#conditionalAgreeGuaranteeInput'+i).val();
							// 	i>2?guaType = '法人担保':guaType = '自然人担保';
							// 	$('#conditionalApproveConfirmDlg #guarantee'+i).text('');

							// 	if(guaName!=''){
							// 		$('#conditionalApproveConfirmDlg #guarantee'+i).text(guaType+" ：  "  + guaName); 
							// 	}
							// };
								if(currentAuditData.loanDetails.product.id == 1){
									if(data.guarantee.natural.guaName3 || data.guarantee.legal.guaName3){
										 $.messager.show({
								             title:'指定担保人不合规',
								             msg:'最多可指定两个法人或者两个自然人担保',
								             showType:'slide'
								         });
										 return false;
										
									}
										
								}
									
								$('#conditionalApproveConfirmDlg').dialog({modal:true}).dialog('open').dialog('setTitle', ' '); 
								
							
							// if(guaranteeName1!=''){
							// 	$('#conditionalApproveConfirmDlg #guarantee1').text("自然人担保 ：  "  + guaranteeName1); 
							// }
							// if(guaranteeName2!=''){
							// 	$('#conditionalApproveConfirmDlg #guarantee2').text("自然人担保 ：  "  + guaranteeName2); 
							// }
							// if(guaranteeName3!=''){
							// 	$('#conditionalApproveConfirmDlg #guarantee3').text("法人担保 ：  "  + guaranteeName3); 
							// }
							// if(guaranteeName4!=''){
							// 	$('#conditionalApproveConfirmDlg #guarantee4').text("法人担保 ：  "  + guaranteeName4); 
							// }
							
					  }
				  },
				  error:function(){
					  $.messager.show({
							title:'提示',
							msg:'校验合同金额是否超过20W失败！',
							showType:'slide'
					  });
					  return;
				  }
				});	
	
	
}
//提交有条件同意
	function submitConditionalAgree(customizeData){
	 	var dialogId = currentAuditData.curAuditDialog;
//		var data  = getCWApprFormData();
		var productCode =currentAuditData.loanDetails.product.productCode||'seLoan/seLoan';
		productCode = productCode.slice(productCode.lastIndexOf('/')+1)+'Audit'+'';
		if(currentAuditData.loanDetails.product.productType == 2 ){
			productCode = 'seLoanAudit';
		}
		if( !$('#'+dialogId+' #approveForm')[productCode]('validateConditionAgreeComponents') )
			 return;
		 
		 var loanId = $('#'+dialogId+' #'+currentAuditData.formId+' #loanId').val();
		 var state = $('#'+dialogId+" #"+currentAuditData.formId+" input[name='agreementRD']:checked").val();
		 var naturalGuaranteeName1 =   $('#'+dialogId+' #conditionalAgreeGuaranteeInput1').val();
		 var naturalGuaranteeName2 =  $('#'+dialogId+' #conditionalAgreeGuaranteeInput2').val();
		 var legalGuaranteeCname1 =   $('#'+dialogId+' #conditionalAgreeGuaranteeInput3').val();
		 var legalGuaranteeCname2 =  $('#'+dialogId+' #conditionalAgreeGuaranteeInput4').val();
		 // var guaranteeName = $('#'+dialogId+' #'+currentAuditData.formId+' #conditionalAgreeGuaranteeInput').val();
		 var guaIds=new Array();
		 var guaranteeName = getAllCheckdGua(null,guaIds);
		 guaIds = guaIds.join(",");
		 var reason = $('#'+dialogId+' #'+currentAuditData.formId+' #conditionalAgreeRemarkTA').val();
		 var auditMoney = $('#'+dialogId+' #'+currentAuditData.formId+' #conditionalAgreeMoneyInput').val();
		 var auditTime = $('#'+dialogId+' #'+currentAuditData.formId+' #conditionalAgreeTimeComb').combobox('getValue');
		 
		 var certificates1 = $('#'+dialogId+' #'+currentAuditData.formId+' #certificates1Input').val();
		 var certificates2 = $('#'+dialogId+' #'+currentAuditData.formId+' #certificates2Input').val();
		 var certificates3 = $('#'+dialogId+' #'+currentAuditData.formId+' #certificates3Input').val();
		 var requestMoney = $('#'+dialogId+' #conditionalAgreeMoneyInput').val();
		 var term =$('#'+dialogId+' #'+currentAuditData.formId+' #conditionalAgreeTimeComb').combobox('getValue');
		  var hasHouse;
		 // if ($('#'+dialogId+' #hasHouse').length>0 && $('#'+dialogId+' .apprHasHouse').attr('disabled') !='disabled')
		 //    hasHouse =$('#'+dialogId+' #hasHouse').combobox('getValue');

		if($('#'+dialogId+' #approveForm #hasHouse').length > 0 && $($('#'+dialogId+' .apprHasHouse')[0]).attr('disabled') !='disabled')
	 	   hasHouse =$('#'+dialogId+' #conditionalAgreeTb #hasHouse').combobox('getValue');

		
		 var contractMatters =$('#'+dialogId+' #contractMattersInput').val();	

		 var data = {
				  loanId:loanId,
				  state:state,
				  guaranteeName:guaranteeName,
				  naturalGuaranteeName1:naturalGuaranteeName1,
				  naturalGuaranteeName2:naturalGuaranteeName2,
				  legalGuaranteeCname1:legalGuaranteeCname1,
				  legalGuaranteeCname2:legalGuaranteeCname2,
				  reason:reason,
				  auditMoney:auditMoney,
				  term:auditTime,
				  certificates1:certificates1,
				  certificates2:certificates2,
				  certificates3:certificates3,
				  requestMoney:requestMoney,
				  term:term,
				  hasHouse:hasHouse,
				  contractMatters:contractMatters,
				  guaIds:guaIds
				  };
		data  = $.extend(true,{},data,customizeData);
		



		 // if(certificates1 & certificates1.length >100){
			//  $.messager.show({
		 //  			title: '提示',
		 //  			msg: '需补充证件字符数限制在100位之内'
		 //  	  }); 
			//  return false;
		 // }
		 // if(certificates2 & certificates2.length >100){
			//  $.messager.show({
			// 	 title: '提示',
			// 	 msg: '需补充证件字符数限制在100位之内'
			//  }); 
			//  return false;
		 // }
		 // if(certificates3 & certificates3.length >100){
			//  $.messager.show({
			// 	 title: '提示',
			// 	 msg: '需补充证件字符数限制在100位之内'
			//  }); 
			//  return false;
		 // }
		 if(guaranteeName == ''&&naturalGuaranteeName1==''&&naturalGuaranteeName1==''&&legalGuaranteeCname1==''&&legalGuaranteeCname2==''&&certificates1==''&&certificates2==''&&certificates3==''){
			 $.messager.show({
			  			title: '提示',
			  			msg: '需补充证件和指定担保人至少填写一项'
			  	  }); 
				 return false;
		 }
		 var url;
		 dialogId == 'approveExtensionCLDlg'?url = 'audit/extensionApprove':url = 'audit/approve'
		 $.ajax({
			  type: "POST",
			  url: url,
			  dataType: "json",
			  data: data,
			  success: function(){
				  $.messager.show({
						title:'签批',
						msg:'签批成功！',
						showType:'slide'
				  });
				  $('#'+dialogId).dialog('close');
				  $("#loanPageTb").datagrid('reload');
			  },
			  error:function(){
				  $.messager.show({
						title:'签批',
						msg:'签批失败！',
						showType:'slide'
				  });
			  }
			});	
	};
 function addGuaInput1(){
		 $("#conditionalAgreeGuaranteeInput2").show();
 };
 function addGuaInput2(){
		 $("#conditionalAgreeGuaranteeInput4").show();
 };
 function addNeedCardInput(){
 	var dialogId = currentAuditData.curAuditDialog;
 	var formId = currentAuditData.formId;
	 if($('#'+dialogId+' #'+formId+' #certificates2Input').is(":hidden")){
		 $('#'+dialogId+' #'+formId+' #certificates2Input').show();
		 return;
	 }
	 if($("#"+dialogId+" #"+formId+" #certificates3Input").is(":hidden")){
		 $('#'+dialogId+" #"+formId+" #certificates3Input").show();
		 return;
	 }

	 $.messager.show({
			title:'添加证件',
			msg:'最多只能添加三个证件',
			showType:'slide'
	  });
 };
 
 function submitRefuse(){
 	var dialogId = currentAuditData.curAuditDialog;
	 if(!validateRefuseComponents())
		 return;
	 var loanId = $('#'+dialogId+' #approveForm #loanId').val();
	 var state = $('#'+dialogId+" #approveForm input[name='agreementRD']:checked").val();
	 var reason = $('#'+dialogId+' #approveForm #refuseRemarkTA').val();
	 var refuseSecondReasonId= $('#'+dialogId+' #approveForm #refuseSecondReason').combobox('getValue');
		
	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  refuseSecondReasonId:refuseSecondReasonId
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#'+dialogId).dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});	
 } 

 function submitCLRefuse(){
	 if(!validateRefuseCLComponents())
		 return;
	 
	 var loanId = $('#approveCLForm #loanId').val();
	 var state = $("#approveCLForm input[name='agreementRD']:checked").val();
	 var reason = $('#approveCLForm #refuseRemarkTA').val();
	 var refuseSecondReasonId= $('#approveCLForm #refuseSecondReason').combobox('getValue');
	 
	 $.ajax({
		  type: "POST",
		  url: "audit/approve",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  refuseSecondReasonId:refuseSecondReasonId
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});	
 }

 function submitExtensionCLRefuse(){
	 if(!validateRefuseExtensionCLComponents())
		 return;
	 
	 var loanId = $('#approveExtensionCLForm #loanId').val();
	 var state = $("#approveExtensionCLForm input[name='agreementRD']:checked").val();
	 var reason = $('#approveExtensionCLForm #refuseRemarkTA').val();
	 var refuseSecondReasonId= $('#approveExtensionCLForm #refuseSecondReason').combobox('getValue');
	 
	 $.ajax({
		  type: "POST",
		  url: "audit/extensionApprove",
		  dataType: "json",
		  data: {
			  loanId:loanId,
			  state:state,
			  reason:reason,
			  refuseSecondReasonId:refuseSecondReasonId
		  },
		  success: function(){
			  $.messager.show({
					title:'签批',
					msg:'签批成功！',
					showType:'slide'
			  });
			  $('#approveExtensionCLDlg').dialog('close');
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'签批',
					msg:'签批失败！',
					showType:'slide'
			  });
		  }
		});	
 }



function formatRequestDate(value,row,index){
	return getYMD(value);
}

function getYMD(datetime){
	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	return datetime.substr(0, 10);
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

/**签批小企业贷*/
function doApprove(loanId,productCode){
//	$('#approveForm :input').val('');  
	var flag='audit';
	//flag表示是签批的详细信息
	var strData = getSeLoanDetails(loanId,flag);
	var loanDetails = $.parseJSON(strData);
	var productId = loanDetails.product.id;
	var dialogId = 'approveDlg';
    if (5 == productId || productId == 6)
    	dialogId = 'cityWideApproveDlg';
    auditRefuse(loanDetails.product.productType,dialogId);
    renderSeLoanApproveDialog(dialogId,loanDetails);
	var h = $(window).height();
	$('#'+dialogId).dialog({modal:true,height:h}).dialog('open').dialog('setTitle', '签批小企业贷');
    
    if (5 == productId || productId == 6)	
    	doApproveCityWideLoan(loanDetails)
    else loadApproveSeloanData(loanDetails,dialogId);

	if(productCode && typeof($('#'+dialogId)[productCode] ) === 'function'){
		

		$('#'+dialogId)[productCode](loanDetails);
	}
};
/*签批小企业贷-小企业贷*/
function loadApproveSeloanData(loanDetails,customizeEleId){
	var elementId = {dialogId:'approveDlg',contacterBrowseId:'contactTab',
					contacterTempletId:'contacterBrowsePanelTemplate',guaranteeBrowseTab:'guraTab',
					guaranteeBrowsePanelTemplate:'guaranteeBrowsePanelTemplate'};
	if(customizeEleId)
		elementId = $.extend(true,elementId,customizeEleId);

	var dialogId = elementId.dialogId;
	var contacterBrowseId = elementId.contacterBrowseId;
	var contacterTempletId = elementId.contacterTempletId;
	var guaranteeBrowseTab = elementId.guaranteeBrowseTab;
	var guaranteeBrowsePanelTemplate = elementId.guaranteeBrowsePanelTemplate;

	// 基本清空
	$('#'+dialogId+' #approveForm #agreeMoneyInput').val(loanDetails.loan.requestMoney);
	$('#'+dialogId+' #approveForm #agreeTimeComb').combobox("select", loanDetails.loan.requestTime);
	$('#'+dialogId+' #approveForm #agreeReason').val('');
	
	if(loanDetails.loan.hasHouse!=null) {
		$('#'+dialogId+' #approveForm #hasHouse').combobox("select", loanDetails.loan.hasHouse);
	}else{
		$('#'+dialogId+' #approveForm #hasHouse').combobox("select", 0);
	}
	$('#'+dialogId+' #approveForm #contractMatters').val('');
	$('#'+dialogId+' #approveForm #contractMattersInput').val('');
	
	$('#'+dialogId+' #approveForm #conditionalAgreeMoneyInput').val(loanDetails.loan.requestMoney);
	$('#'+dialogId+' #approveForm #conditionalAgreeGuaranteeInput').val('');
	$('#'+dialogId+' #approveForm #conditionalAgreeTimeComb').combobox("select", loanDetails.loan.requestTime);
	$('#'+dialogId+' #approveForm #certificates1Input').val('');
	$('#'+dialogId+' #approveForm #certificates2Input').hide().val('');
	$('#'+dialogId+' #approveForm #certificates3Input').hide().val('');
	$('#'+dialogId+' #approveForm #conditionalAgreeRemarkTA').val('');
	
	$('#'+dialogId+' #approveForm #refuseFirstReasonInput').val('');
	$('#'+dialogId+' #approveForm #refuseSecondReasonInput').val('');
	$('#'+dialogId+' #approveForm #refuseRemarkTA').val('');
	
	if(loanDetails.product) {
		$('#'+dialogId+' #approveForm #productName').text(loanDetails.product.productName);
	}
	if(loanDetails.loan) {
		$('#'+dialogId+' #approveForm #requestMoney').text(loanDetails.loan.requestMoney);
		$('#'+dialogId+' #approveForm #requestTime').text(loanDetails.loan.requestTime + "期");
		$('#'+dialogId+' #approveForm #purpose').text(loanDetails.loan.purpose);
	}
	
	if(loanDetails.person) {
		$('#'+dialogId+' #approveForm #personName').text(loanDetails.person.name);
		$('#'+dialogId+' #approveForm #personSex').text(formatSex(loanDetails.person.sex));
		$('#'+dialogId+' #approveForm #personIdnum').text(loanDetails.person.idnum);
		$('#'+dialogId+' #approveForm #personMarried').text(formatMarried(loanDetails.person.married));
		$('#'+dialogId+' #approveForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
		$('#'+dialogId+' #approveForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
		$('#'+dialogId+' #approveForm #personZipCode').text(loanDetails.person.zipCode);
		$('#'+dialogId+' #approveForm #personAddress').text(loanDetails.person.address);
		$('#'+dialogId+' #approveForm #personMobilePhone').text(loanDetails.person.mobilePhone);
		$('#'+dialogId+' #approveForm #personEmail').text(loanDetails.person.email);
		$('#'+dialogId+' #approveForm #personHomePhone').text(transferUndefined(loanDetails.person.homePhone));
		$('#'+dialogId+' #approveForm').find('#professionType').text(transferUndefined(loanDetails.person.professionType));//职业类型
	    
		// 根据房产类型判断租金和房贷显示与否
        // 规则，如果房产类型是商品房、经济适用房、自建房则显示房贷
        // 如果是租用 则显示每月租金
        // 如果是亲戚住房则租金和房贷均没有
		$('#'+dialogId+' #approveForm #personHouseEstateType').text(loanDetails.person.houseEstateType);
        var personHouseTR = $('#'+dialogId+' #approveForm #personHouseEstateType').parent().parent();
        if(loanDetails.person.houseEstateType == '商品房' || 
        		loanDetails.person.houseEstateType == '经济适用房' || 
        		loanDetails.person.houseEstateType == '自建房'){
        	personHouseTR.find(':nth-child(3)').hide();
        	personHouseTR.find(':nth-child(4)').hide();
        	personHouseTR.find(':nth-child(5)').show();
        	personHouseTR.find(':nth-child(6)').show();
        	
        	$('#'+dialogId+' #approveForm #personHasHouseLoan').text(formatHave(loanDetails.person.hasHouseLoan));
        }
        if(loanDetails.person.houseEstateType == '租用'){
        	personHouseTR.find(':nth-child(3)').show();
        	personHouseTR.find(':nth-child(4)').show();
        	personHouseTR.find(':nth-child(5)').hide();
        	personHouseTR.find(':nth-child(6)').hide();

        	$('#'+dialogId+' #approveForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        }
        if(loanDetails.person.houseEstateType == '亲戚住房'){
        	personHouseTR.find(':nth-child(3)').hide();
        	personHouseTR.find(':nth-child(4)').hide();
        	personHouseTR.find(':nth-child(5)').hide();
        	personHouseTR.find(':nth-child(6)').hide();
        }
		
		$('#'+dialogId+' #approveForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
		if(loanDetails.person.incomePerMonth){
			$('#'+dialogId+' #approveForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "万元/月");
		}
		
	}
	
	if(loanDetails.company) {
		$('#'+dialogId+' #approveForm #companyName').text(loanDetails.company.name);
		$('#'+dialogId+' #approveForm #companyIndustryInvolved').text(loanDetails.company.industryInvolved);
		$('#'+dialogId+' #approveForm #companyLegalRepresentative').text(loanDetails.company.legalRepresentative);
		$('#'+dialogId+' #approveForm #companyLegalRepresentativeId').text(loanDetails.company.legalRepresentativeId);
		$('#'+dialogId+' #approveForm #companyIncomePerMonth').text(transferUndefined(loanDetails.company.incomePerMonth) + "万元/月");
		$('#'+dialogId+' #approveForm #companyFoundedDate').text(getYMD(loanDetails.company.foundedDate));
		$('#'+dialogId+' #approveForm #companyCategory').text(formatCompanyCategory(loanDetails.company.category));
		$('#'+dialogId+' #approveForm #companyAddress').text(loanDetails.company.address);
		$('#'+dialogId+' #approveForm #companyAvgProfitPerYear').text(transferUndefined(loanDetails.company.avgProfitPerYear) + "万元/年");
		$('#'+dialogId+' #approveForm #companyPhone').text(transferUndefined(loanDetails.company.phone));
		$('#'+dialogId+' #approveForm #companyZipCode').text(loanDetails.company.zipCode);
		$('#'+dialogId+' #approveForm #companyOperationSite').text(loanDetails.company.operationSite);
		$('#'+dialogId+' #approveForm #companyMajorBusiness').text(loanDetails.company.majorBusiness);
		$('#'+dialogId+' #approveForm #companyEmployeesNumber').text(loanDetails.company.employeesNumber);
		$('#'+dialogId+' #approveForm #companyEmployeesWagesPerMonth').text(transferUndefined(loanDetails.company.employeesWagesPerMonth) + "万元/月");
	}
	
	if(loanDetails.service) {
		$('#'+dialogId+' #approveForm #serviceName').text(loanDetails.service.name);
	}
	if(loanDetails.loan) {
		$('#'+dialogId+' #approveForm #customerSource').text(loanDetails.loan.customerSource);
		$('#'+dialogId+' #approveForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
	}
	if(loanDetails.crm) {
		$('#'+dialogId+' #approveForm #crmCode').text(loanDetails.crm.code);
		$('#'+dialogId+' #approveForm #crmName').text(loanDetails.crm.name);
	}
	if(loanDetails.salesDept) {
		$('#'+dialogId+' #approveForm #salesDeptName').text(loanDetails.salesDept.name);
	}
	if(loanDetails.assessor) {
		$('#'+dialogId+' #approveForm #assessorName').text(loanDetails.assessor.name);
	}
	if(loanDetails.loan.remark) {
	        $('#'+dialogId+' #approveForm #remark').text(loanDetails.loan.remark);
	}

	if(loanDetails.attachmentList && loanDetails.attachmentList.length >0 ) {
    	$('#'+dialogId+' #approveForm #downloadAttachmentZip').show();
    	$('#'+dialogId+' #approveForm #downloadAttachmentZip').attr("href", "apply/downloadAttachmentZip?loanId="+ loanDetails.loan.id);
    } else 
    	$('#'+dialogId+' #approveForm #downloadAttachmentZip').hide();
	
	$('#'+dialogId+' #approveForm #loanId').val(loanDetails.loan.loanId);
	$('#'+dialogId+' #approveForm #productId').val(loanDetails.loan.productId);
	 $('#'+dialogId+' #' +contacterBrowseId+' >'+' #'+contacterTempletId+'  ~ div').remove();
	    if(loanDetails.contacterList) {
	        for(var i =0;i<loanDetails.contacterList.length;i++){
	            var contacter = loanDetails.contacterList[i];
	            var contacterBrowsePanel =  $('#'+contacterTempletId).clone().show().addClass('easyui-panel');
	            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
	            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
	            contacterBrowsePanel.attr("title","联系人"+(i+1));

	            contacterBrowsePanel.find('#contacterName').text(contacter.name);
	            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
	            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
	            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
	            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
	            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
	            contacterBrowsePanel.find('#address').text(contacter.address);
           		contacterBrowsePanel.find('#title').text(contacter.title);

	            contacterBrowsePanel.appendTo($('#'+contacterBrowseId));
	        }
	        $.parser.parse('#'+contacterBrowseId);
	}

	    // $('#guraTab > #guaranteeBrowsePanelTemplate ~ div').remove();
	     $('#'+dialogId+' #'+guaranteeBrowseTab+' >'+' #'+guaranteeBrowsePanelTemplate+' ~ div').remove();
	    if(loanDetails.guaranteeList) {
	        for(var i =0;i<loanDetails.guaranteeList.length;i++){
	            var guarantee = loanDetails.guaranteeList[i];
	            var guaranteeBrowsePanel =  $('#'+guaranteeBrowsePanelTemplate).clone().show().addClass('easyui-panel');
	            var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;
	            guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
	            guaranteeBrowsePanel.attr("title","担保人"+(i+1));

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
              

	            guaranteeBrowsePanel.appendTo($('#'+guaranteeBrowseTab));
	        }
	        $.parser.parse($('#'+guaranteeBrowseTab).parent());
	    }
}
/*签批小企业贷-同城贷*/
function doApproveCityWideLoan(loanDetails){
	var customizeEleId = {dialogId:'cityWideApproveDlg',
						contacterBrowseId:'cityWidecontactTab',contacterTempletId:'cityWideApprContacterBrowsePanelTemplate',
						guaranteeBrowseTab:'cityWideGuraTab',guaranteeBrowsePanelTemplate:'cityWideApprguaranteeBrowsePanelTemplate'};
	loadApproveSeloanData(loanDetails,customizeEleId);
	loadCityWideLoanData(loanDetails,'approveForm');
	renderConApprGua('CWconAgrNaGuaLi','CWconAgrLeGuaLi');
}
/**签批车贷*/
function doCLApprove(loanId){
	//借款类型
//	$('#approveCLForm :input').val('');  
	var flag='audit';
	var strData = getCarLoanDetails(loanId,flag);
	var loanDetails = $.parseJSON(strData);
	
	
	
	//加载新增加的五个参数
	if(loanDetails.person) {
		if(loanDetails.person.topEducation==1){
			 $('#approveCLDlg #topEducation').text('硕士及以上');
		}else if(loanDetails.person.topEducation==2){
			 $('#approveCLDlg #topEducation').text('本科');
		}else if(loanDetails.person.topEducation==3){
			 $('#approveCLDlg #topEducation').text('大专');
		}else if(loanDetails.person.topEducation==4){
			 $('#approveCLDlg #topEducation').text('中专');
		}else if(loanDetails.person.topEducation==5){
			 $('#approveCLDlg #topEducation').text('高中');
		}else if(loanDetails.person.topEducation==6){
			 $('#approveCLDlg #topEducation').text('初中及以下');
		}else{
			$('#approveCLDlg #topEducation').text('');
		}
       
		
		if(loanDetails.person.homeCondition==1){
			 $('#approveCLDlg #homeCondition').text('还款中');
		}else if(loanDetails.person.homeCondition==2){
			 $('#approveCLDlg #homeCondition').text('全款购');
		}else if(loanDetails.person.homeCondition==3){
			 $('#approveCLDlg #homeCondition').text('已结清');
		}else if(loanDetails.person.homeCondition==4){
			 $('#approveCLDlg #homeCondition').text('无');
		}else{
			 $('#approveCLDlg #homeCondition').text('');
		}
       
		
		if(loanDetails.person.isHaveCarLoan==1){
			 $('#approveCLDlg #isHaveCarLoan').text('是');
		}else if(loanDetails.person.isHaveCarLoan==2){
			 $('#approveCLDlg #isHaveCarLoan').text('否');
		}else{
			 $('#approveCLDlg #isHaveCarLoan').text('');
		}
       
        $('#approveCLDlg #monthIncome').text(loanDetails.person.monthIncome);
        $('#approveCLDlg #loanSize').text(loanDetails.person.loanSize);
        
        
       if(loanDetails.person.unitIndustryCategory==1){
		$('#approveCLDlg #unitIndustryCategory').text('农、林、牧、渔业');
       }else if(loanDetails.person.unitIndustryCategory==2){
       	$('#approveCLDlg #unitIndustryCategory').text('能源、采矿业');
       }else if(loanDetails.person.unitIndustryCategory==3){
       	$('#approveCLDlg #unitIndustryCategory').text('食品、药品、工业原料、服装、日用品等制造业');
       }else if(loanDetails.person.unitIndustryCategory==4){
       	$('#approveCLDlg #unitIndustryCategory').text('电力、热力、燃气及水生产和供应业');
       }else if(loanDetails.person.unitIndustryCategory==5){
       	$('#approveCLDlg #unitIndustryCategory').text('建筑业');
       }else if(loanDetails.person.unitIndustryCategory==6){
       	$('#approveCLDlg #unitIndustryCategory').text('批发和零售业');
       }else if(loanDetails.person.unitIndustryCategory==7){
       	$('#approveCLDlg #unitIndustryCategory').text('交通运输、仓储和邮政业');
       }else if(loanDetails.person.unitIndustryCategory==8){
       	$('#approveCLDlg #unitIndustryCategory').text('住宿、旅游、餐饮业');
       }else if(loanDetails.person.unitIndustryCategory==9){
       	$('#approveCLDlg #unitIndustryCategory').text('信息传输、软件和信息技术服务业');
       }else if(loanDetails.person.unitIndustryCategory==10){
       	$('#approveCLDlg #unitIndustryCategory').text('金融业');
       }else if(loanDetails.person.unitIndustryCategory==11){
       	$('#approveCLDlg #unitIndustryCategory').text('房地产业');
       }else if(loanDetails.person.unitIndustryCategory==12){
       	$('#approveCLDlg #unitIndustryCategory').text('租凭和商务服务业');
       }else if(loanDetails.person.unitIndustryCategory==13){
       	$('#approveCLDlg #unitIndustryCategory').text('科学研究、技术服务业');
       }else if(loanDetails.person.unitIndustryCategory==14){
       	$('#approveCLDlg #unitIndustryCategory').text('水利、环境和公共设施管理业');
       }else if(loanDetails.person.unitIndustryCategory==15){
       	$('#approveCLDlg #unitIndustryCategory').text('居民服务、修理和其他服务业');
       }else if(loanDetails.person.unitIndustryCategory==16){
       	$('#approveCLDlg #unitIndustryCategory').text('教育、培训');
       }else if(loanDetails.person.unitIndustryCategory==17){
       	$('#approveCLDlg #unitIndustryCategory').text('卫生、医疗、社会保障、社会福利');
       }else if(loanDetails.person.unitIndustryCategory==18){
       	$('#approveCLDlg #unitIndustryCategory').text('文化、体育和娱乐业');
       }else if(loanDetails.person.unitIndustryCategory==19){
       	$('#approveCLDlg #unitIndustryCategory').text('政府、非盈利机构和社会组织');
       }else if(loanDetails.person.unitIndustryCategory==20){
       	$('#approveCLDlg #unitIndustryCategory').text('警察、消防、军人');
       }else if(loanDetails.person.unitIndustryCategory==21){
       	$('#approveCLDlg #unitIndustryCategory').text('其他');
       }else{
       	$('#approveCLDlg #unitIndustryCategory').text('');
       }
    }
	
	
	
	currentAuditData.curAuditDialog = 'approveCLDlg';
	currentAuditData.loanDetails = loanDetails;
	currentAuditData.formId = 'approveCLForm';
	loadApproveTermOfBorrowing();
	$('#approveCLForm #agreeMoneyInput').val(loanDetails.loan.requestMoney);

	$('#approveCLDlg #agreeTimeComb').combobox("select", loanDetails.loan.requestTime);		
	$('#approveCLDlg #conditionalAgreeTimeComb').combobox("select", loanDetails.loan.requestTime);

	//$('#approveCLDlg #productId').combobox("select", loanDetails.loan.productId);
	$('#approveCLForm #agreeReason').val('');
	
	$('#approveCLForm #refuseFirstReasonInput').val('');
	$('#approveCLForm #refuseSecondReasonInput').val('');
	$('#approveCLForm #refuseRemarkTA').val('');
	renderCondApprRadio('approveCLPanel');
	
	var h = $(window).height();
	$('#approveCLDlg').dialog({modal:true,height:h}).dialog('open').dialog('setTitle', '签批车贷');
	if(loanDetails.product) {
		$('#approveCLForm #productName').text(loanDetails.product.productName);
	}
	if(loanDetails.loan) {
		$('#approveCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
		$('#approveCLForm #requestMoney').text(loanDetails.loan.requestMoney);
		$('#approveCLForm #requestTime').text(loanDetails.loan.requestTime);
		$('#approveCLForm #purpose').text(loanDetails.loan.purpose);
		$('#approveCLForm #conditionalAgreeMoneyInput').val(loanDetails.loan.requestMoney);	
	}
	if(loanDetails.person) {
		$('#approveCLForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
      	$('#approveCLForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
      	if(loanDetails.person.professionType=='自营'){
      		$('.enterpprise3').css('display','table-row');
      		$('.enterpprise4').css('display','table-row');
      		$('#approveCLForm #privateEnterpriseType').text(transferUndefined(loanDetails.person.privateEnterpriseType));
   	        $('#approveCLForm #foundedDate').text(loanDetails.person.foundedDate);
   	        $('#approveCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
   	        $('#approveCLForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees) +'人');
   	        $('#approveCLForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments) +'%');
   	        $('#approveCLForm #monthOfProfit').text(transferUndefined(loanDetails.person.monthOfProfit) +'万元/月');
      	}else{
      		$('.enterpprise3').css('display','none');
      		$('.enterpprise4').css('display','none');
      	}
		$('#approveCLForm #personName').text(loanDetails.person.name);
		$('#approveCLForm #personSex').text(formatSex(loanDetails.person.sex));
		$('#approveCLForm #personIdnum').text(loanDetails.person.idnum);
		$('#approveCLForm #personMarried').text(formatMarried(loanDetails.person.married));
		$('#approveCLForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
		$('#approveCLForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
		if(loanDetails.person.hasChildren==1){
			$('#approveCLForm #childrenSchool').text(transferUndefined(loanDetails.person.childrenSchool));
		}		
		$('#approveCLForm #personMobilePhone').text(loanDetails.person.mobilePhone);
		$('#approveCLForm #personEmail').text(loanDetails.person.email);
		$('#approveCLForm #personHomePhone').text(loanDetails.person.homePhone);
		$('#approveCLForm #personPlaceDomicile').text(loanDetails.person.placeDomicile);
		$('#approveCLForm #personHouseholdZipCode').text(loanDetails.person.householdZipCode);
		// 根据居住类型，决定每月租金和每月房贷是否显示，规则
        // 如果居住类型是按揭房，则显示每月房贷
        // 如果居住类型是租赁，则显示每月租金
        // 如果其他的，则不显示每月租金和每月房贷
		$('#approveCLForm #personLiveType').text(loanDetails.person.liveType);
        var liveType = loanDetails.person.liveType;
        var liveTypeTR =  $('#approveCLForm #personLiveType').parent().parent();
        if(liveType=='按揭房'){
        	liveTypeTR.find(':nth-child(3)').text('每月房贷').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#approveCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#approveCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        	
        } else if(liveType == '租赁'){
        	liveTypeTR.find(':nth-child(3)').text('每月租金').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#approveCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#approveCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        } else{
        	liveTypeTR.find(':nth-child(3)').hide();
        	liveTypeTR.find(':nth-child(4)').hide();
        }
		
		$('#approveCLForm #personAddress').text(loanDetails.person.address);
		$('#approveCLForm #personZipCode').text(loanDetails.person.zipCode);
	}
	
	if(loanDetails.vehicle) {
		$('#approveCLForm #vehicleBrand').text(loanDetails.vehicle.brand);
		$('#approveCLForm #vehicleModel').text(loanDetails.vehicle.model);
		$('#approveCLForm #vehicleCoty').text(loanDetails.vehicle.coty+"年");
		$('#approveCLForm #vehicleMileage').text(loanDetails.vehicle.mileage+"公里");
		$('#approveCLForm #vehiclePlateNumber').text(loanDetails.vehicle.plateNumber);
		$('#approveCLForm #vehicleFrameNumber').text(loanDetails.vehicle.frameNumber);
	}
	
	if(loanDetails.company) {
		$('#approveCLForm #companyName').text(loanDetails.company.name);
		$('#approveCLForm #companyAddress').text(loanDetails.company.address);
	}
	
	if(loanDetails.person) {
		$('#approveCLForm #personDeptName').text(loanDetails.person.deptName);
		$('#approveCLForm #personJob').text(loanDetails.person.job);
		$('#approveCLForm #personExt').text(loanDetails.person.ext);
		$('#approveCLForm #personIncomePerPonth').text(loanDetails.person.incomePerMonth + "元");
		$('#approveCLForm #personPayDate').text(loanDetails.person.payDate + "号");
		$('#approveCLForm #personOtherIncome').text(loanDetails.person.otherIncome||'' + "元");
		$('#approveCLForm #personWitness').text(loanDetails.person.witness);
		$('#approveCLForm #personWorkThatDept').text(loanDetails.person.workThatDept);
		$('#approveCLForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
		$('#approveCLForm #personWorkThatTell').text(loanDetails.person.workThatTell);
		$('#approveCLForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
	}
	
	if(loanDetails.creditHistory) {
		$('#approveCLForm #creditHistoryHasCreditCard').text(formatHave(loanDetails.creditHistory.hasCreditCard));
		if(loanDetails.creditHistory.hasCreditCard==1){
			$('#approveCLForm #creditHistoryCardNum').text(transferUndefinedAndZero(loanDetails.creditHistory.cardNum));
			$('#approveCLForm #creditHistoryTotalAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.totalAmount) + "元");
			$('#approveCLForm #creditHistoryOverdrawAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.overdrawAmount) + "元");
		}else{
   		    $('#approveCLForm #creditHistoryCardNum').text('');
	        $('#approveCLForm #creditHistoryTotalAmount').text('');
	        $('#approveCLForm #creditHistoryOverdrawAmount').text('');
		}		
	}
	
	if(loanDetails.service) {
		$('#approveCLForm #serviceName').text(loanDetails.service.name);
	}
	if(loanDetails.loan) {
		$('#approveCLForm #customerSource').text(loanDetails.loan.customerSource);
		$('#approveCLForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
	}
	if(loanDetails.crm) {
		$('#approveCLForm #crmCode').text(loanDetails.crm.code);
		$('#approveCLForm #crmName').text(loanDetails.crm.name);
	}
	if(loanDetails.salesDept) {
		$('#approveCLForm #salesDeptName').text(loanDetails.salesDept.name);
	}
	if(loanDetails.assessor) {
		$('#approveCLForm #assessorName').text(loanDetails.assessor.name);
	}
	 if(loanDetails.loan.remark) {
	    $('#approveCLForm #remark').text(loanDetails.loan.remark);
	 }
	if(loanDetails.attachmentList && loanDetails.attachmentList.length >0 ) {
    	$('#approveCLForm #downloadAttachmentZip').show();
    	$('#approveCLForm #downloadAttachmentZip').attr("href", "apply/downloadAttachmentZip?loanId="+ loanDetails.loan.id);
    } else 
    	$('#approveCLForm #downloadAttachmentZip').hide();
    $('#carContacterBrowseTab2 > #carContacterBrowsePanelTemplate2 ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#carContacterBrowsePanelTemplate2').clone().show().addClass('easyui-panel');
            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
            contacterBrowsePanel.attr("title","联系人"+(i+1));

            contacterBrowsePanel.find('#contacterName').text(contacter.name);
            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);
            contacterBrowsePanel.appendTo($('#carContacterBrowseTab2'));
        }
        $.parser.parse('#carContacterBrowseTab2');
    }
    
    approveCLFormFirstInto = true;
    
	//贷款类型
    var loanTypes = loanDetails.loan.loanType;
//    $("#approveCLForm #loanTypeApprove").append("<option value='1'>移交类</option>");
//    $("#approveCLForm #loanTypeApprove").append("<option value='2'>流通类</option>");
    $("#approveCLForm #loanTypeApprove").combobox({
    	data:[{
    	    "value":1,
    	    "name":"移交类"
    	},{
    	    "value":2,
    	    "name":"流通类"
    	}],
    	valueField:'value',
  	    textField:'name',
  	    onLoadSuccess: function(){
	    	$("#approveCLForm #loanTypeApprove").combobox('select',loanTypes);
	    },
	    onSelect: function(record) {
    		if(approveCLFormFirstInto){
    			return;
    		}
    		var productValue = $('#approveCLForm #productType').combobox('getValue');
    		//新增页面，移交类1，流通类2，
	        //产品配置页面，流通类1，移交类2，
	        //不知道那个大神做的。，为了不影响代码，做转换
    		var value=0;
	        if(record.value == 1){
	        	value = 2;
	        } else if(record.value == 2){
	        	value = 1;
	        }
    		//onchange的时候下拉框的width不断的缩小，无法找到原因，只能指定了
    		$("#approveCLForm #agreeTimeComb").combobox({
    			url:'apply/findListByVO?productId='+productValue+'&carProductType='+value
    		 });
    	}
    });
    //产品类型
    var proudctId = loanDetails.product.id;
//    $("#approveCLForm #productType").append("<option value='2'>车贷</option>");
//    $("#approveCLForm #productType").append("<option value='4'>车贷新产品</option>");
    $("#approveCLForm #productType").combobox({
    	data:[{
    	    "value":2,
    	    "name":"车贷"
    	},{
    	    "value":4,
    	    "name":"车贷新产品"
    	}],
    	valueField:'value',
  	    textField:'name',
	    onLoadSuccess: function(){
	    	$("#approveCLForm #productType").combobox('select',proudctId);
	    },
	    onSelect: function(record) {
	  		if(approveCLFormFirstInto){
    			return;
    		}
	  		var value = $("#approveCLForm #loanTypeApprove").combobox('getValue');
	  		//新增页面，移交类1，流通类2，
	        //产品配置页面，流通类1，移交类2，
	        //不知道那个大神做的。，为了不影响代码，做转换
	        if(value == 1){
	        	value = 2;
	        } else if(value == 2){
	        	value = 1;
	        }
	  		//onchange的时候下拉框的width不断的缩小，无法找到原因，只能指定了
	  		$("#approveCLForm #agreeTimeComb").combobox({
	  			url:'apply/findListByVO?productId='+record.value+'&carProductType='+value
	  		 });
	  	}
    });
    
    //新增页面，移交类1，流通类2，
    //产品配置页面，流通类1，移交类2，
    //不知道那个大神做的。，为了不影响代码，做转换
    var changeLoanTypes = 0
    if(loanTypes == 1){
    	changeLoanTypes = 2;
    } else if(loanTypes == 2){
    	changeLoanTypes = 1;
    }
    
    $("#approveCLForm #agreeTimeComb").combobox({
		url:'apply/findListByVO?productId='+proudctId+'&carProductType='+changeLoanTypes,
	    valueField:'term',
	    textField:'termName',
	    width:120,
	    onLoadSuccess:function(){
	    	var requestTime = loanDetails.loan.requestTime;
	    	if(approveCLFormFirstInto){
	    		approveCLFormFirstInto=false
	    		$("#approveCLForm #agreeTimeComb").combobox('select', requestTime);
    			return;
    		}
	    	var data = $("#approveCLForm #agreeTimeComb").combobox('getData');
        	var productValue = $('#approveCLForm #productType').combobox('getValue');
        	var checkInit = false;
        	for (var int = 0; int < data.length; int++) {
				var row = data[int];
				if(row.term == requestTime){
					checkInit = true;
					break;
				}
			}
        	if(checkInit){
        		$("#approveCLForm #agreeTimeComb").combobox('select', requestTime);
        	} else {
        		if(productValue == 4){
        			$("#approveCLForm #agreeTimeComb").combobox('select', currentAuditData.loanDetails.loan.requestTime == 3 ? 6 : currentAuditData.loanDetails.loan.requestTime);
        		}else {
        			$("#approveCLForm #agreeTimeComb").combobox('select', 3);
        		}
        	}
        	
        	
        }
	 });

	$('#approveCLForm #loanId').val(loanId);
};



/**签批展期车贷*/
function doCLExtensionApprove(loanId){
//	$('#approveCLForm :input').val('');  
	var flag='audit';
	var strData = getCarExtensionLoanDetails(loanId,flag);
	var loanDetails = $.parseJSON(strData);

	currentAuditData.curAuditDialog = 'approveExtensionCLDlg';
	currentAuditData.loanDetails = loanDetails;
	currentAuditData.formId = 'approveExtensionCLForm';
	loadApproveTermOfBorrowing();

	renderCondApprRadio('approveExtensionCLPanel');
	
	$('#approveExtensionCLForm #agreeMoneyInput').val(loanDetails.loan.requestMoney);
	$('#approveExtensionCLForm #conditionalAgreeMoneyInput').val(loanDetails.loan.requestMoney);
	$('#approveExtensionCLForm #agreeTimeComb').combobox("select", loanDetails.loan.requestTime);
	$('#approveExtensionCLForm #conditionalAgreeTimeComb').combobox("select", loanDetails.loan.requestTime);
	
	$('#approveExtensionCLForm #agreeReason').val('');
	
	$('#approveExtensionCLForm #refuseFirstReasonInput').val('');
	$('#approveExtensionCLForm #refuseSecondReasonInput').val('');
	$('#approveExtensionCLForm #refuseRemarkTA').val('');

	var h = $(window).height();
	$('#approveExtensionCLDlg').dialog({modal:true,height:h}).dialog('open').dialog('setTitle', '签批展期车贷');
	if(loanDetails.product) {
		$('#approveExtensionCLForm #productName').text(loanDetails.product.productName);
	}
	if(loanDetails.loan) {
		$('#approveExtensionCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
		$('#approveExtensionCLForm #requestMoney').text(loanDetails.loan.requestMoney);
		$('#approveExtensionCLForm #requestTime').text(loanDetails.loan.requestTime);
		$('#approveExtensionCLForm #purpose').text(loanDetails.loan.purpose);
	}
	
	if(loanDetails.person) {
		$('#approveExtensionCLForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
      	$('#approveExtensionCLForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
      	if(loanDetails.person.professionType=='自营'){
      		$('.enterpprise3').css('display','table-row');
      		$('.enterpprise4').css('display','table-row');
      		$('#approveExtensionCLForm #privateEnterpriseType').text(transferUndefined(loanDetails.person.privateEnterpriseType));
   	        $('#approveExtensionCLForm #foundedDate').text(loanDetails.person.foundedDate);
   	        $('#approveExtensionCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
   	        $('#approveExtensionCLForm #totalEmployees').text(transferUndefined(loanDetails.person.totalEmployees) +'人');
   	        $('#approveExtensionCLForm #ratioOfInvestments').text(transferUndefined(loanDetails.person.ratioOfInvestments) +'%');
   	        $('#approveExtensionCLForm #monthOfProfit').text(transferUndefined(loanDetails.person.monthOfProfit) +'万元/月');
      	}else{
      		$('.enterpprise3').css('display','none');
      		$('.enterpprise4').css('display','none');
      	}
		$('#approveExtensionCLForm #personName').text(loanDetails.person.name);
		$('#approveExtensionCLForm #personSex').text(formatSex(loanDetails.person.sex));
		$('#approveExtensionCLForm #personIdnum').text(loanDetails.person.idnum);
		$('#approveExtensionCLForm #personMarried').text(formatMarried(loanDetails.person.married));
		$('#approveExtensionCLForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
		$('#approveExtensionCLForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
		if(loanDetails.person.hasChildren==1){
			$('#approveExtensionCLForm #childrenSchool').text(transferUndefined(loanDetails.person.childrenSchool));
		}		
		$('#approveExtensionCLForm #personMobilePhone').text(loanDetails.person.mobilePhone);
		$('#approveExtensionCLForm #personEmail').text(loanDetails.person.email);
		$('#approveExtensionCLForm #personHomePhone').text(loanDetails.person.homePhone);
		$('#approveExtensionCLForm #personPlaceDomicile').text(loanDetails.person.placeDomicile);
		$('#approveExtensionCLForm #personHouseholdZipCode').text(loanDetails.person.householdZipCode);
		// 根据居住类型，决定每月租金和每月房贷是否显示，规则
        // 如果居住类型是按揭房，则显示每月房贷
        // 如果居住类型是租赁，则显示每月租金
        // 如果其他的，则不显示每月租金和每月房贷
		$('#approveExtensionCLForm #personLiveType').text(loanDetails.person.liveType);
        var liveType = loanDetails.person.liveType;
        var liveTypeTR =  $('#approveExtensionCLForm #personLiveType').parent().parent();
        if(liveType=='按揭房'){
        	liveTypeTR.find(':nth-child(3)').text('每月房贷').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#approveExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#approveExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        	
        } else if(liveType == '租赁'){
        	liveTypeTR.find(':nth-child(3)').text('每月租金').show();
        	liveTypeTR.find(':nth-child(4)').show();
        	if(loanDetails.person.rentPerMonth){
        		$('#approveExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}else if(loanDetails.person.rentPerMonth==0){
        		$('#approveExtensionCLForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
        	}
        } else{
        	liveTypeTR.find(':nth-child(3)').hide();
        	liveTypeTR.find(':nth-child(4)').hide();
        }
		
		$('#approveExtensionCLForm #personAddress').text(loanDetails.person.address);
		$('#approveExtensionCLForm #personZipCode').text(loanDetails.person.zipCode);
	}
	
	if(loanDetails.vehicle) {
		$('#approveExtensionCLForm #vehicleBrand').text(loanDetails.vehicle.brand);
		$('#approveExtensionCLForm #vehicleModel').text(loanDetails.vehicle.model);
		$('#approveExtensionCLForm #vehicleCoty').text(loanDetails.vehicle.coty+"年");
		$('#approveExtensionCLForm #vehicleMileage').text(loanDetails.vehicle.mileage+"公里");
		$('#approveExtensionCLForm #vehiclePlateNumber').text(loanDetails.vehicle.plateNumber);
		$('#approveExtensionCLForm #vehicleFrameNumber').text(loanDetails.vehicle.frameNumber);
	}
	
	if(loanDetails.company) {
		$('#approveExtensionCLForm #companyName').text(loanDetails.company.name);
		$('#approveExtensionCLForm #companyAddress').text(loanDetails.company.address);
	}
	
	if(loanDetails.person) {
		$('#approveExtensionCLForm #personDeptName').text(loanDetails.person.deptName);
		$('#approveExtensionCLForm #personJob').text(loanDetails.person.job);
		$('#approveExtensionCLForm #personExt').text(loanDetails.person.ext);
		$('#approveExtensionCLForm #personIncomePerPonth').text(loanDetails.person.incomePerMonth + "元");
		$('#approveExtensionCLForm #personPayDate').text(loanDetails.person.payDate + "号");
		$('#approveExtensionCLForm #personOtherIncome').text(loanDetails.person.otherIncome||'' + "元");
		$('#approveExtensionCLForm #personWitness').text(loanDetails.person.witness);
		$('#approveExtensionCLForm #personWorkThatDept').text(loanDetails.person.workThatDept);
		$('#approveExtensionCLForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
		$('#approveExtensionCLForm #personWorkThatTell').text(loanDetails.person.workThatTell);
		$('#approveExtensionCLForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
	}
	
	if(loanDetails.creditHistory) {
		$('#approveExtensionCLForm #creditHistoryHasCreditCard').text(formatHave(loanDetails.creditHistory.hasCreditCard));
		if(loanDetails.creditHistory.hasCreditCard==1){
			$('#approveExtensionCLForm #creditHistoryCardNum').text(transferUndefinedAndZero(loanDetails.creditHistory.cardNum));
			$('#approveExtensionCLForm #creditHistoryTotalAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.totalAmount) + "元");
			$('#approveExtensionCLForm #creditHistoryOverdrawAmount').text(transferUndefinedAndZero(loanDetails.creditHistory.overdrawAmount) + "元");
		}else{
   		    $('#approveExtensionCLForm #creditHistoryCardNum').text('');
	        $('#approveExtensionCLForm #creditHistoryTotalAmount').text('');
	        $('#approveExtensionCLForm #creditHistoryOverdrawAmount').text('');
		}		
	}
	
	if(loanDetails.service) {
		$('#approveExtensionCLForm #serviceName').text(loanDetails.service.name);
	}
	if(loanDetails.loan) {
		$('#approveExtensionCLForm #customerSource').text(loanDetails.loan.customerSource);
		$('#approveExtensionCLForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
	}
	if(loanDetails.crm) {
		$('#approveExtensionCLForm #crmCode').text(loanDetails.crm.code);
		$('#approveExtensionCLForm #crmName').text(loanDetails.crm.name);
	}
	if(loanDetails.salesDept) {
		$('#approveExtensionCLForm #salesDeptName').text(loanDetails.salesDept.name);
	}
	 if(loanDetails.loan.remark) {
	    $('#approveExtensionCLForm #remark').text(loanDetails.loan.remark);
	 }
	
    $('#carContacterBrowseTab3 > #carContacterBrowsePanelTemplate3 ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#carContacterBrowsePanelTemplate3').clone().show().addClass('easyui-panel');
            var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
            contacterBrowsePanel.attr("id",contacterBrowsePanelId);
            contacterBrowsePanel.attr("title","联系人"+(i+1));

            contacterBrowsePanel.find('#contacterName').text(contacter.name);
            contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
            contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
            contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);

            contacterBrowsePanel.appendTo($('#carContacterBrowseTab3'));
        }
        $.parser.parse('#carContacterBrowseTab3');
    }
  
	
	$('#approveExtensionCLForm #loanId').val(loanId);
};
function formatSex(sex){
    if(sex==1)
        return "男";
    else if(sex==0)
        return "女";
    else 
    	return sex;
}
function formatHave(have){
    if(have==1)
        return "有";
    else if(have==0)
        return "无";
    else 
    	return have;
}
function formatYes(yes){
    if(yes==1)
        return "是";
    else if(yes== 0)
        return "否";
    else 
    	return yes;
}

function formatProductCarType(loanType){
    if(loanType == 1)
        return "移交类";
    else if(loanType==2)
        return "流通类";
    else 
    	return loanType;
}

function formatEducationLevel(educationLevel){
    if(educationLevel == 0)
        return "初中及以下";
    else if(educationLevel==1)
        return "高中";
    else if(educationLevel==2)
        return "中专";
    else if(educationLevel==3)
        return "大专";
    else if(educationLevel==4)
        return "本科";
    else if(educationLevel==5)
        return "硕士及以上";
    else 
    	return educationLevel;
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
function formatCompanyCategory(category){
	if(category == 0)
        return "个体";
    else if(category==1)
        return "私营独资";
    else if(category==2)
        return "私营合伙";
    else if(category==3)
        return "私营有限责任";
    else if(category==4)
        return "私营股份有限";
    else if(category==5)
        return "其他";
    else 
        return category;
}

function formatGuaranteeType(guaranteeType){
    if(guaranteeType == 0)
        return "自然人";
    else if(guaranteeType==1)
        return "法人";
    else 
        return category;
}


function formatCompanyType(companyType){
	 if(companyType == 0)
	        return "政府机构";
	    else if(companyType==1)
	        return "事业";
	    else if(companyType==2)
	        return "国企";
	    else if(companyType==3)
	        return "外资";
	    else if(companyType==4)
	        return "民营";
	    else if(companyType==5)
	        return "私营";
	    else if(companyType==6)
	        return "其他";
	    else if(companyType==7)
	        return "合资";
	    else 
	    	return companyType;
}

function formatMarried(married){
	 if(married == 0)
       return "未婚";
    else if(married==1)
       return "已婚";
    else if(married==2)
       return "离异";
    else if(married==3)
       return "再婚";
    else if(married==4)
       return "丧偶";
    else if(married==5)
       return "其他";
    else 
   	return married;
}

/**获取贷款详细信息*/
function getLoanDetails(loanId){
	var response = $.ajax({
		  type: "POST",
		  url: "apply/getLoanDetails",
		  dataType: "json",
		  async:false,
		  data: {
			  loanId:loanId
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

/**编辑*/
function edit(loanId){

};
/**提交*/
function submit(loanId){
	$.ajax({
		  type: "POST",
		  url: "apply/submit",
		  dataType: "json",
		  data: {
			  id:loanId
		  },
		  success: function(){
			  $.messager.show({
					title:'提交',
					msg:'提交成功！',
					showType:'slide'
			  });
			  $("#loanPageTb").datagrid('reload');
		  },
		  error:function(){
			  $.messager.show({
					title:'提交',
					msg:'提交失败！',
					showType:'slide'
			  });
		  }
		});
};




// 提交操作时间时间
function formatDate(val) {
	return $.mFuc.dateD(val);
};
// 操作
function formatAction(val,row,index) {
	var url="vehicleListEdit.html";
	return '<img src="/resources/static2/css/icons/cut.png" onclick="assignRoles()" title="分配角色" />&nbsp;<img src="/resources/static2/css/icons/pencil.png" onclick="window.open(\''+url+'\',\'\',\'scrollbars=yes,width=800,height=600,left='+($.mFuc.winW()-800)/2+',top='+($.mFuc.winH()-700)/2+'\')" title="编辑" />&nbsp;<img src="/resources/static2/css/icons/cancel.png" onclick="removeRecord('+index+')" title="删除" />';
};

// 弹出借款类型窗口
function addRecord() {	
	$('#dlg').dialog('open').dialog('setTitle', '　');
	
};

//加载数据到页面上
function loadData(applyVo){


};

//取消
function cancel() {	
	$('#dlg').dialog('close').dialog();
	
};
//关闭页面
function cloae() {	
	$('#alg').dialog('close').dialog();
	
};
//打开申请贷款页面
function addVehicle() {
	$('#newLoanForm').submit();
}
// 批量删除
function batchRemoveRecord() {
	var rows = $('#list_result').datagrid('getChecked');
	var user = "";
	for(var i=0; i<rows.length; i++) {
		user = user+rows[i].name+" ";
	};
	if (rows.length > 0) {
		parent.$.messager.confirm('确认','确认要删除 '+user+'吗？', function(r) {
			if(r) {
				parent.$.messager.show({
					title : '提示',
					msg : '删除成功！'
				});
			};
		});
	}else{
		parent.$.messager.show({
			title : '提示',
			msg : '请勾选要删除的记录！'
		});
	};
};
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
// 删除记录
function removeRecord(index) {
	$('#addRecord_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('checkRow', index);
	batchRemoveRecord();
	$('#addRecord_datagrid').datagrid('uncheckAll').datagrid('unselectAll');
};
// 操作
function formatAction1(val, row, index) {
	var url = 'userInfoPerson.html';
	var html = [];
	html.push('<img title="编辑" src="/resources/css/icons/pencil.png" onclick="window.open(\''+url+'\',\'\',\'scrollbars=yes,width=800,height=600,left='+($.mFuc.winW()-800)/2+',top='+($.mFuc.winH()-700)/2+'\')" />');
	html.push('&nbsp;<img title="删除" src="/resources/css/icons/cancel.png" onclick="removeRecord('+index+')" />');
	htmlString = html.join('');
	return htmlString;
};
// 清空搜索
function clearSearch() {
	$('#list_result').datagrid('load',{});
	$('#list_search').searchbox('setValue','');
};

