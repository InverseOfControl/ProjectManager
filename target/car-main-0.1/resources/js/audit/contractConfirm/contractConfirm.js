var url;
$(function () {
	$('#refuseFirstReason').combobox({     
	    valueField:'id',
	    textField:'reason',
	    onChange:function(reason1){ 
	    	$('#refuseSecondReason').combobox({     
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
	// 取消按钮
	$("#refuseReasonForm #refuseCancelBt").bind('click',function (){
		$('#dlg').dialog('close');
	});
	
	// 确定按钮
	$("#refuseReasonForm #refuseSubmitBt").bind('click', function (){
		submitRefuse();
	});
	//产品类型
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
	//根据所选的城市出现对应的营业部
	$('#toolbar #cityComb').combobox({     
	    valueField:'id',
	    textField:'name',
	    onChange:function(value){ 
	    	$('#toolbar #salesDeptComb').combobox({     
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
	
    //营业网点
    $('#toolbar #salesDeptComb').combobox({
        url:'apply/getCurSalesDept',
        valueField:'id',
        textField:'name',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            //if(data.length==1)
                $(this).combobox('select', data[0].id);
        }
    });
    //城市
    $('#toolbar #cityComb').combobox({
        url:'apply/getCurCity',
        valueField:'id',
        textField:'name',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].id);
        }
    });
    // 借款状态
    $('#toolbar #loanStatusContractConfirmComb').combobox({
    	url:'audit/contractConfirm/getLoanStatusList',
    	valueField:'value' ,
    	textField:'name',
    	 onLoadSuccess:function(){
    		  var data = $(this).combobox('getData');    		
    	       $(this).combobox('select',data[0].value);
    	 }
    });
    //设置开始时间为15天前 0时0分0秒
    var date=new Date();
    var startDate=new Date((+date)-15*24*3600*1000);    
    var start = startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+ startDate.getDate();
  
    $('#toolbar #signDateStartDate').val(start);
    
	// 查询按钮
	$('#searchBt').bind('click', search);
	 //校验
    $.extend($.fn.validatebox.defaults.rules, { 
    integerCheck:{
        validator:function(value){
            return /^[+]?[0-9]\d*$/.test(value);
        },
        message: '请输入整数'
    }  
      });
	 // 列表
    $('#list_result').datagrid({
        url: 'audit/contractConfirm/list.json',
        fitColumns: true,
        border: false,
        singleSelect:true,
        pagination: true,
        fit:true,
        pageSize: 10,
        striped: true,
        rownumbers: true,	
        columns : [ [ {
			field : 'name',
			title : '姓名',
			formatter : function(value, rowData, rowIndex) {
				return '<a style="font-weight:bolder" href="javascript:void(0)" onclick="browse('+rowData.loanId + ',' + rowData.productType + ','+ rowData.extensionTime +')">' + rowData.name + '</a>';
			}
        }, 
        {
			field : 'productName',
			title : '产品类型',
			formatter: function(value, row, index){
	         	return  row.product.productTypeName;
          }
		},
		{
			field : 'idnum',
			title : '身份证号'
		}, {
			field : 'serviceName',
			title : '客服'
		}, {
			field : 'crmName',
			title : '客户经理'
		}, {
			field : 'extensionTime',
			title : '展期期次',
			formatter : function(value, row, index) {
				if(value == 0) {
					return "无";
				} else {
					return value;
				}
			}
		}, {
			field : 'auditMoney',
			title : '审批金额(元)'
		}, {
			field : 'time',
			title : '审批期限'
		}, {
			field : 'assessorName',
			title : '复核人员'
		}, {
			field : 'repayDay',
			title : '还款日'
		},{
			field : 'id',
			title : '合同名称',
			formatter : function(value, rowData, rowIndex) {
				var link = "";
				
				//助学贷
				if(rowData.productId == 8  && rowData.contractNo != null){
					link += "<a href='studentLoanReport/printStudentLoanPersonLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>风险金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='studentLoanReport/printStudentLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>借款</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='studentLoanReport/printStudentLoanRepaymentContract?contractNo=" + rowData.contractNo + "' target='_blank'>服务</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='studentLoanReport/printStudentLoanRepaymentFundContract?contractNo=" + rowData.contractNo + "&productId="+rowData.productId +"' target='_blank'>提示函</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='studentLoanReport/printStudentLoanEntrustContract?contractNo=" + rowData.contractNo + "' target='_blank'>扣款</a>&nbsp;&nbsp;&nbsp;";
				}
				//网商贷
				if(rowData.productId ==7 && rowData.contractNo != null){
					link += "<a href='netBusinessReport/printNetBusinessPersonLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>风险金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='netBusinessReport/printNetBusinessLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>借款</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='netBusinessReport/printNetBusinessRepaymentContract?contractNo=" + rowData.contractNo + "' target='_blank'>服务</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='netBusinessReport/printNetBusinessRepaymentFundContract?contractNo=" + rowData.contractNo + "&productId="+rowData.productId +"' target='_blank'>提示函</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='netBusinessReport/printNetBusinessEntrustContract?contractNo=" + rowData.contractNo + "' target='_blank'>扣款</a>&nbsp;&nbsp;&nbsp;";
					if(rowData.guaranteeList != null){
						var j=0;
						for(var i=0;i<rowData.guaranteeList.length;i++){
							j=i+1;
							if (rowData.guaranteeList[i].guaranteeType == "1"){
								link += "<a href='netBusinessReport/printNetBusinessNaturalLegalContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
							if (rowData.guaranteeList[i].guaranteeType == "0") {
								link += "<a href='netBusinessReport/printNetBusinessNaturalGuaranteeContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
								link += "<a href='netBusinessReport/printNetBusinessEntrustGuaranteeContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保扣款"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
						}
					}	
				}
				//同城贷
				if((rowData.productId ==5 || rowData.productId ==6) && rowData.contractNo != null){
					link += "<a href='cityWideReport/printCityWidePersonLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>风险金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='cityWideReport/printCityWideLoanContract?contractNo=" + rowData.contractNo + "' target='_blank'>借款</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='cityWideReport/printCityWideRepaymentContract?contractNo=" + rowData.contractNo + "' target='_blank'>服务</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='cityWideReport/printCityWideRepaymentFundContract?contractNo=" + rowData.contractNo + "&productId="+rowData.productId +"' target='_blank'>提示函</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='cityWideReport/printCityWideEntrustContract?contractNo=" + rowData.contractNo + "' target='_blank'>扣款</a>&nbsp;&nbsp;&nbsp;";
					if(rowData.guaranteeList != null){
						var j=0;
						for(var i=0;i<rowData.guaranteeList.length;i++){
							j=i+1;
							if (rowData.guaranteeList[i].guaranteeType == "1"){
								link += "<a href='cityWideReport/printCityWideNaturalLegalContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
							if (rowData.guaranteeList[i].guaranteeType == "0") {
								link += "<a href='cityWideReport/printCityWideNaturalGuaranteeContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
								link += "<a href='cityWideReport/printCityWideEntrustGuaranteeContract?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保扣款"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
						}
					}	
				}
				
				if(rowData.productId ==1 && rowData.contractNo != null){
					link += "<a href='javascript:Preview1(\""+rowData.contractNo+"\")' >风险金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:Preview2(\""+rowData.contractNo+"\")' >借款</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:Preview3(\""+rowData.contractNo+"\")' >服务</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/hktxh?contractNo=" + rowData.contractNo + "' target='_blank'>提醒函</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/wtkksqs?contractNo=" + rowData.contractNo + "' target='_blank'>扣款</a>&nbsp;&nbsp;&nbsp;";
					if(rowData.guaranteeList != null){
						var j=0;
						for(var i=0;i<rowData.guaranteeList.length;i++){
							j=i+1;
							if (rowData.guaranteeList[i].guaranteeType == "1"){
								link += "<a href='report/dbhtsfr?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
							if (rowData.guaranteeList[i].guaranteeType == "0") {
								link += "<a href='report/dbhtszrr?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保"+ j +"</a>&nbsp;&nbsp;&nbsp;";
								link += "<a href='report/wtkksqsdbr?contractNo=" + rowData.contractNo + "&guaranteeId= " + rowData.guaranteeList[i].id + "' target='_blank'>担保扣款"+ j +"</a>&nbsp;&nbsp;&nbsp;";
							}
						}
					}
				}
				if((rowData.productId ==2 || rowData.productId ==4) && rowData.contractNo!=null&&rowData.extensionTime==0){
					link += "<a href='javascript:PreviewCar2(\""+rowData.contractNo+"\")' >借款抵押</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:PreviewCar3(\""+rowData.contractNo+"\")' >风险基金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:PreviewCar4(\""+rowData.contractNo+"\")' >车辆处置特</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:PreviewCar1(\""+rowData.contractNo+"\")' >借款咨询</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/car_wtkksqs?contractNo=" + rowData.contractNo + "' target='_blank'>委托扣款</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/car_csfymx?contractNo=" + rowData.contractNo + "&productId="+rowData.productId +"' target='_blank'>催收</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/car_hkwxtsh?contractNo=" + rowData.contractNo + "' target='_blank'>还款</a>";
					
				}
				//车贷展期
				if(rowData.productId ==2 && rowData.contractNo!=null&&rowData.extensionTime>0){
					link += "<a href='javascript:previewLoanExtensionAgreement(\""+rowData.loanId+"\")' >借款抵押</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:previewFundRiskAgreement(\""+rowData.loanId+"\")' >风险基金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/carRepaymentNotice?extensionId="+rowData.loanId+ "' target='_blank'>还款</a>&nbsp;&nbsp;&nbsp;"; 
					link += "<a href='report/carDeductionNotice?extensionId="+rowData.loanId+"' target='_blank'>委托扣款</a>";
				}
					return link;
			}
		}, {
			field : 'operations',
			title : '操作',
			formatter : formatOperationsCell,width : 160
		}] ]
	});
    // 设置分页控件
    var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
});


/** 操作 */
function formatOperationsCell(value,row,index){
var operations = row.operations.split("|");

var formattedOperations="";
for(var i = 0; i < operations.length; i++) {
	var operation = operations[i];
	if (operation == "日志") {
		operation = '<a href="javascript:void(0)" onclick="businessLogPage('+row.loanId+')">日志</a>';
	} else if(operation == "合同确认") {
		operation = '<a href="javascript:void(0)" onclick="contractConfirm('+row.loanId + ',' + row.extensionTime +')">合同确认</a>';
	} else if(operation == "退回") {
		operation = '<a href="javascript:void(0)"  onclick="contractConfirmRefuse('+row.productId + ',' +row.loanId+ ',' + row.extensionTime + ')">退回</a>';
	} else if(operation == "附件") {
		operation = '<a href="javascript:void(0)" onclick="showAttachmentDlg('+row.loanId+ ',' + row.extensionTime + ')">附件</a>';
		
	}
	if(formattedOperations =="") {
		formattedOperations = operation;
	} else {
		formattedOperations = formattedOperations + "   " + operation;
	}

}
	formattedOperations = formattedOperations;

return formattedOperations;
};


function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '合同确认日志',
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
		pageSize: 100,
		striped: true,
		rownumbers: true,
		nowrap:false,
		columns: [[
		           {field: 'operator', title: '操作者', width: 50},
		           {field: 'flowStatusView', title: '环节', width: 60},
		           {field: 'createDate', title: '操作时间', width: 100},
		           {field: 'message', title: '日志内容', width: 300}
		]]
    });
	 // 设置分页控件
    var p = $('#business_log_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
}

//合同确认拒绝
function  contractConfirmRefuse(productId,loanId,extensionTime){
	 $("#dlg").find("#contractConfirmRefuseLoanId").val(loanId);
	 $("#dlg").find("#contractConfirmRefuseextensionTime").val(extensionTime);
	 $("#dlg").dialog("open").dialog('setTitle', '合同确认退回');
	 $('#refuseFirstReason').combobox('clear');
	 $('#refuseSecondReason').combobox('clear');
	 $('#refuseFirstReason').combobox({     
		    url:'master/rejectReason/getRefuseFirstReason',
		    valueField:'id',
		    textField:'reason',
		    onBeforeLoad: function(param){                    		    	
					param.productId = productId;								
				}
		  
		});
}
//合同提交
function  contractConfirm(loanId, extensionTime){	
	   $("#alg").find("#contractConfirmLoanId").val(loanId);
	   $("#alg").find("#contractConfirmExtensionTime").val(extensionTime);
	   var status;
	   if(extensionTime == 0) {
		   status = 70;
	   } else {
		   status = 71; 
	   }
	   $.ajax({
	        url : "audit/findApproveResultByLoanIdAndState",
	        data: {loanId: loanId,state: status},
	        type : "POST",
	        dataType:"JSON",
	        success : function(approveResult){
                var certificates ="";
                if (approveResult) {
                    if (approveResult.certificates1) {
                        certificates = certificates + approveResult.certificates1;
                    }
                    if (approveResult.certificates2) {
                        certificates = certificates + approveResult.certificates2;
                    }
                    if (approveResult.certificates3) {
                        certificates = certificates + approveResult.certificates3;
                    }

                } else {
                    certificates = '暂无';
                }

	        	parent.$.messager.confirm("确认",
                    "请确定以下补充附件已上传!<br>"+certificates,
					function(r){
                    if(!r){
                        return false;
                    }
                    submitContractConfirm();

				} );

	        },
		    error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText
					});
			}
	    });
	 
}
function search(){
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personNameTxt').val();
	queryParams.personIdnum = $('#toolbar #personIdnumTxt').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.salesDeptId = $('#toolbar #salesDeptComb').combobox('getValue');
	queryParams.extensionTime = $('#toolbar #extensionTimeComb').combobox('getValue');
	queryParams.status = $('#toolbar #loanStatusContractConfirmComb').combobox('getValue');
	queryParams.signDateStart = $('#toolbar #signDateStartDate').val();
	queryParams.signDateEnd = $('#toolbar #signDateEndDate').val();
	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}

function submitContractConfirm() {
	var loanId= $('#contractConfirmLoanId').val();
	var extensionTime = $('#contractConfirmExtensionTime').val();
	$.ajax({
		   url : 'audit/contractConfirm/submitContractConfirm',
		   data :{loanId: loanId, extensionTime: extensionTime},
		   type:"POST",
		   success : function(result){	
			  
		   		if(result.repCode == '000000'){
		   			$.messager.show({
						title : '提示',
						msg : '保存成功！'
					});
		   			$('#alg').dialog('close');
		   			$('#list_result').datagrid('reload');
		   		}else{
		   			parent.$.messager.show({
						title: 'Error',
						msg: result.repMsg
					});
		   		}
		   }
	});	
}
function validateRefuseComponents(){
	var passed = true;
	var  refuseFirstReasonId= $('#refuseReasonForm #refuseFirstReason').combobox('getValue');
	var  refuseSecondReasonId= $('#refuseReasonForm #refuseSecondReason').combobox('getValue');
	 var reason = $('#refuseReasonForm #refuseRemark').val();
	if(refuseFirstReasonId=='' ||refuseSecondReasonId=='')
	{
		passed= false;
		$.messager.show({
	          title:'拒绝原因',
	          msg:'请选择退回门店的一二级原因！',
	          showType:'slide'
	      });
	}
	else if(reason=='' )
	{
		passed= false;
		$.messager.show({
	          title:'拒绝原因',
	          msg:'请输入备注信息！',
	          showType:'slide'
	      });
	}
	return passed;
}

function submitRefuse() {
	 if(!validateRefuseComponents())
		 return;
	 parent.$.messager.confirm('确认','确认合同退回吗？', function(r) {
		if(r) {
			var loanId= $('#contractConfirmRefuseLoanId').val();
			 var reason = $('#refuseReasonForm #refuseRemark').val();
			 var refuseSecondReasonId= $('#refuseReasonForm #refuseSecondReason').combobox('getValue');
			 var extensionTime = $('#contractConfirmRefuseextensionTime').val();
			$.ajax({
				   url : 'audit/contractConfirm/submitRefuse',
				   data :{loanId: loanId,reason: reason,refuseSecondReasonId: refuseSecondReasonId,extensionTime: extensionTime},
				   type:"POST",
				   success : function(result){	
					  
				   		if(result=='success'){
				   			$.messager.show({
								title : '提示',
								msg : '退回成功！'
							});
				   			$('#dlg').dialog('close');
				   			$('#list_result').datagrid('reload');
				   		}else{
				   			parent.$.messager.show({
								title: 'Error',
								msg: result
							});
				   		}
				   }
			});	
			
		};
	});
	 
	 
	
}
function showAttachmentDlg(loanId, extensionTime){
	if(extensionTime == 0) {
		window.open (rayUseUrl+"audit/contractConfirm/imageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	} else {
		window.open (rayUseUrl+"audit/contractConfirm/extensionImageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}
}

/*查看同城贷*/
function doBrowseCityWideLoan(loanDetails,form,dialogId){
	var customizeEleId = {dialogId:'browseCityWideSeLoan',
						contacterBrowseId:'cityWideContacterBrowseTab',contacterTempletId:'cityWideContacterBrowsePanelTemplate',
						guaranteeBrowseTab:'cityWideGuaranteeBrowseTab',guaranteeBrowsePanelTemplate:'cityWideGuaranteeBrowsePanelTemplate'};
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
					guaranteeBrowsePanelTemplate:'guaranteeBrowsePanelTemplate',approveResultBrowseTab:'approveResultBrowseTab2'};
	// var elementId= {} ;
	if(customizeEleId)
		elementId = $.extend(true,elementId,customizeEleId);
	var dialogId = elementId.dialogId;
	var contacterBrowseId = elementId.contacterBrowseId;
	var contacterTempletId = elementId.contacterTempletId;
	var guaranteeBrowseTab = elementId.guaranteeBrowseTab;
	var guaranteeBrowsePanelTemplate = elementId.guaranteeBrowsePanelTemplate;
	var approveResultBrowseTab = elementId.approveResultBrowseTab;

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
    	 $('#approveResultBrowseTab2').show();
    	 $('#approveResultBrowsePanelTemplate2').show();
    	$("#approveResultBrowseTab2").html("");
    	var head=
    		 ' <div style='+'margin:10px auto;text-align:center'+' id='+'approveResultBrowsePanelTemplate2' +'>'+
	          ' <table style='+'position:relative;margin-left:200px;border-top:solid 2px #ebebeb'+'   cellspacing= '+'10'+'>'+
	     '  <tr>'+ 
	     ' <td style='+'background:#fff;text-align:center;font-weight:bolder'+'>签批意见</td>'+ 
	       '<td style='+'font-weight:bolder'+'>借款金额</td>' +  
	       '<td style='+'font-weight:bolder'+'>管理费率</td>' +  
	      ' <td style='+'background:#fff;text-align:center;font-weight:bolder'+'>期限</td>'+
	      '<td style='+'font-weight:bolder'+'>签约事项</td>'+
	       '<td style='+'font-weight:bolder'+'>备注</td>'+	      
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
             var hasHouse=transferUndefined(loanDetails.hasHouseString);

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
    		'<td>'+'<label >'+hasHouse +'</label>'+
    		'</td>'+
    		'<td style='+'background:#fff;text-align:center'+'>'+
    		'<label >'+term+'</label>'+'</td>'+'<td>'+'<label >'+contractMatters +'</label>'+'</td>';    		
            if(loanDetails.approveResultList[i].state==40){
            	html+='<td>'+'<label >'+"备注："+reason+"<br>"+"一级原因:"+reason1+"<br>"+"二级原因:"+reason2 +'</label>'+
        		'</td>';
           }else if(loanDetails.approveResultList[i].state==50){
        	   html+='<td>'+'<label >'+"担保人:"+guaranteeName+"<br>"+"补充证件:"+certificates1+"<br> "+certificates2  +certificates3 +'</label>'+
       		'</td>';
           }else if(loanDetails.approveResultList[i].state==31){//退回门店        	  
        	   html+='<td>'+'<label >'+reason+"<br> "+'</label>'+
          		'</td>';
           }else{
        	   html+='</td>'+'<td>'+'<label >'+reason +'</label>'+
          		'</td>';	          
           }
            html+='</tr>';
           
    	}
    	var end=
    	'</table>'+
        '  </div>';   
    	$("#approveResultBrowseTab2").append(head+html+end);    	
    	 $.parser.parse($('#approveResultBrowseTab2').parent());
    }else{    	
    	 $('#approveResultBrowseTab2').hide();
    	 $('#approveResultBrowsePanelTemplate2').hide();
    };
}

function transferUndefinedAndZero(data){
	if(data){
		return data;
	}else{
		return "";
	}
}

function browse(loanId, productType, extensionTime){
	if(productType ==1){
	        doBrowse(loanId);
    }else if(productType == 2){
    	if(extensionTime == 0) {
    		doBrowseCL(loanId);
    	} else {
    		// 浏览车贷展期详情
    		doBrowseExtensionCL(loanId);
    	}
    }
};

/**查看小企业贷*/
// function doBrowse(loanId){
// 	var flag='contractConfirm';
//     var strData = getSeLoanDetails(loanId,flag);
//     var loanDetails = $.parseJSON(strData);
//     var h = $(window).height() ;
//     $('#browseDlg').dialog({modal:true,height:h*(0.8)}).dialog('open').dialog('setTitle', '查看小企业贷');
    
//     if(loanDetails.product) {
//         $('#browseForm #productName').text(loanDetails.product.productName);
//     }
//     if(loanDetails.loan) {
//         $('#browseForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
//         $('#browseForm #requestTime').text(loanDetails.loan.requestTime + "期");
//         $('#browseForm #purpose').text(loanDetails.loan.purpose);
//     }
//     if(loanDetails.person) {
//         $('#browseForm #personName').text(loanDetails.person.name);
//         $('#browseForm #personSex').text(formatSex(loanDetails.person.sex));
//         $('#browseForm #personIdnum').text(loanDetails.person.idnum);
//         $('#browseForm #personMarried').text(formatMarried(loanDetails.person.married));
//         $('#browseForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
//         $('#browseForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
//         $('#browseForm #personZipCode').text(loanDetails.person.zipCode);
//         $('#browseForm #personAddress').text(loanDetails.person.address);
//         $('#browseForm #personMobilePhone').text(loanDetails.person.mobilePhone);
//         $('#browseForm #personEmail').text(loanDetails.person.email);
//         $('#browseForm #personHomePhone').text(transferUndefined(loanDetails.person.homePhone));
//         if(loanDetails.person.professionType){
//             $('#browseForm').find('#professionType').text(loanDetails.person.professionType);//职业类型
//         }
//         // 根据房产类型判断租金和房贷显示与否
//         // 规则，如果房产类型是商品房、经济适用房、自建房则显示房贷
//         // 如果是租用 则显示每月租金
//         // 如果是亲戚住房则租金和房贷均没有
//         $('#browseForm #personHouseEstateType').text(loanDetails.person.houseEstateType);
//         var personHouseTR = $('#browseForm #personHouseEstateType').parent().parent();
//         if(loanDetails.person.houseEstateType == '商品房' || 
//         		loanDetails.person.houseEstateType == '经济适用房' || 
//         		loanDetails.person.houseEstateType == '自建房'){
//         	personHouseTR.find(':nth-child(3)').hide();
//         	personHouseTR.find(':nth-child(4)').hide();
//         	personHouseTR.find(':nth-child(5)').show();
//         	personHouseTR.find(':nth-child(6)').show();
        	
//         	$('#browseForm #personHasHouseLoan').text(formatHave(loanDetails.person.hasHouseLoan));
//         }
//         if(loanDetails.person.houseEstateType == '租用'){
//         	personHouseTR.find(':nth-child(3)').show();
//         	personHouseTR.find(':nth-child(4)').show();
//         	personHouseTR.find(':nth-child(5)').hide();
//         	personHouseTR.find(':nth-child(6)').hide();

//         	$('#browseForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
//         }
//         if(loanDetails.person.houseEstateType == '亲戚住房'){
//         	personHouseTR.find(':nth-child(3)').hide();
//         	personHouseTR.find(':nth-child(4)').hide();
//         	personHouseTR.find(':nth-child(5)').hide();
//         	personHouseTR.find(':nth-child(6)').hide();
//         }
        
//         $('#browseForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
//         if(loanDetails.person.incomePerMonth){
//         	$('#browseForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth +"万元/月");
//         }
        
//     }
//     if(loanDetails.company) {
//         $('#browseForm #companyName').text(loanDetails.company.name);
//         $('#browseForm #companyIndustryInvolved').text(loanDetails.company.industryInvolved);
//         $('#browseForm #companyLegalRepresentative').text(loanDetails.company.legalRepresentative);
//         $('#browseForm #companyLegalRepresentativeId').text(loanDetails.company.legalRepresentativeId);
//         $('#browseForm #companyIncomePerMonth').text(loanDetails.company.incomePerMonth + "万元/月");
//         $('#browseForm #companyFoundedDate').text(getYMD(loanDetails.company.foundedDate));
//         $('#browseForm #companyCategory').text(formatCompanyCategory(loanDetails.company.category));
//         $('#browseForm #companyAddress').text(loanDetails.company.address);
//         $('#browseForm #companyAvgProfitPerYear').text(loanDetails.company.avgProfitPerYear + "万元/年");
//         $('#browseForm #companyPhone').text(transferUndefined(transferUndefined(loanDetails.company.phone)));
//         $('#browseForm #companyZipCode').text(loanDetails.company.zipCode);
//         $('#browseForm #companyOperationSite').text(loanDetails.company.operationSite);
//         $('#browseForm #companyMajorBusiness').text(loanDetails.company.majorBusiness);
//         $('#browseForm #companyEmployeesNumber').text(loanDetails.company.employeesNumber);
//         $('#browseForm #companyEmployeesWagesPerMonth').text(loanDetails.company.employeesWagesPerMonth + "万元/月");
//     }
//     if(loanDetails.service) {
//         $('#browseForm #serviceName').text(loanDetails.service.name);
//     }
//     if(loanDetails.loan) {
//         $('#browseForm #customerSource').text(loanDetails.loan.customerSource);
//         $('#browseForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
//     }
//     if(loanDetails.crm) {
//         $('#browseForm #crmCode').text(loanDetails.crm.code);
//         $('#browseForm #crmName').text(loanDetails.crm.name);
//     }
//     if(loanDetails.salesDept) {
//         $('#browseForm #salesDeptName').text(loanDetails.salesDept.name);
//     }
//     if(loanDetails.assessor) {
//         $('#browseForm #assessorName').text(loanDetails.assessor.name);
//     }
//     if(loanDetails.loan.remark) {
//         $('#browseForm #remark').text(loanDetails.loan.remark);
//     }
//     // 清空联系人列表（除了模板）
    
//     $('#contacterBrowseTab > #contacterBrowsePanelTemplate ~ div').remove();
//     if(loanDetails.contacterList) {
//         for(var i =0;i<loanDetails.contacterList.length;i++){
//             var contacter = loanDetails.contacterList[i];
//             var contacterBrowsePanel =  $('#contacterBrowsePanelTemplate').clone().show().addClass('easyui-panel');
//             var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
//             contacterBrowsePanel.attr("id",contacterBrowsePanelId);
//             contacterBrowsePanel.attr("title","联系人"+(i+1));

//             contacterBrowsePanel.find('#contacterName').text(contacter.name);
//             contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
//             contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
//             contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
//             contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
//             contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));

//             contacterBrowsePanel.appendTo($('#contacterBrowseTab'));
//         }
//         $.parser.parse('#contacterBrowseTab');
//     }

//     $('#guaranteeBrowseTab > #guaranteeBrowsePanelTemplate ~ div').remove();
//     if(loanDetails.guaranteeList) {
//         for(var i =0;i<loanDetails.guaranteeList.length;i++){
//             var guarantee = loanDetails.guaranteeList[i];
//             var guaranteeBrowsePanel =  $('#guaranteeBrowsePanelTemplate').clone().show().addClass('easyui-panel');
//             var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;
//             guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
//             guaranteeBrowsePanel.attr("title","担保人"+(i+1));
//             if(loanDetails.guaranteeList[i].flag){
//               	 guaranteeBrowsePanel.find('#flag').text("该担保人为指定担保人");
//            }
//             if(guarantee.guaranteeType==0){//自然人
//            	 guaranteeBrowsePanel.find('#guaranteeName').text(guarantee.name);
//                 guaranteeBrowsePanel.find('#guaranteeType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
//                 guaranteeBrowsePanel.find('#guaranteeIdnum').text(transferUndefined(guarantee.idnum));
//                 guaranteeBrowsePanel.find('#guaranteeSex').text(transferUndefined(formatSex(guarantee.sex)));
//                 guaranteeBrowsePanel.find('#guaranteeMarried').text(transferUndefined(formatMarried(guarantee.married)));
//                 guaranteeBrowsePanel.find('#guaranteeEducationLevel').text(transferUndefined(guarantee.educationLevel));
//                 guaranteeBrowsePanel.find('#guaranteeHasChildren').text(transferUndefined(formatYes(guarantee.hasChildren)));
//                 guaranteeBrowsePanel.find('#guaranteeAddress').text(transferUndefined(guarantee.address));
//                 guaranteeBrowsePanel.find('#guaranteeMobilePhone').text(transferUndefined(guarantee.mobilePhone));
//                 guaranteeBrowsePanel.find('#guaranteeEmail').text(transferUndefined(guarantee.email));
//                 guaranteeBrowsePanel.find('#personHomePhone').text(transferUndefined(guarantee.homePhone));
//                 guaranteeBrowsePanel.find('#guaranteeCompanyFullName').text(transferUndefined(guarantee.companyFullName));
//                 guaranteeBrowsePanel.find('#guaranteeZipCode').text(transferUndefined(guarantee.zipCode));
//                 guaranteeBrowsePanel.find('#guaranteeCompanyAddress').text(transferUndefined(guarantee.companyAddress));
//                 guaranteeBrowsePanel.find('#guaranteeCompanyPhone').text(transferUndefined(guarantee.companyPhone));
               
//                guaranteeBrowsePanel.find('#tr7').hide();
//                guaranteeBrowsePanel.find('#tr8').hide();
              
//            }else if(guarantee.guaranteeType==1){//法人
//            	 guaranteeBrowsePanel.find('#tr1').hide();
//                 guaranteeBrowsePanel.find('#tr2').hide();
//                 guaranteeBrowsePanel.find('#tr3').hide();
//                 guaranteeBrowsePanel.find('#tr4').hide();
//                 guaranteeBrowsePanel.find('#tr5').hide();
//                 guaranteeBrowsePanel.find('#tr6').hide();                      
//            	 guaranteeBrowsePanel.find('#tr7').show();
//                 guaranteeBrowsePanel.find('#tr8').show(); 
//                 guaranteeBrowsePanel.find('#guaType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
//                 guaranteeBrowsePanel.find('#guaCompanyFullName').text(transferUndefined(guarantee.companyFullName));
//                 guaranteeBrowsePanel.find('#guaZipCode').text(transferUndefined(guarantee.zipCode));
//                 guaranteeBrowsePanel.find('#guaCompanyAddress').text(transferUndefined(guarantee.companyAddress));
//                 guaranteeBrowsePanel.find('#guaCompanyPhone').text(transferUndefined(guarantee.companyPhone));
           	
//            }
          
           
//             guaranteeBrowsePanel.appendTo($('#guaranteeBrowseTab'));
//         }
//         $.parser.parse($('#guaranteeBrowseTab').parent());
       
//     } 
// };



function renderContractView(dialogId,contacterBrowseId,contacterTempletId,loanDetails){
	$('#'+dialogId+' #' +contacterBrowseId+' >'+' #'+contacterTempletId+'  ~ div').remove();
    if(loanDetails.contacterList) {
        for(var i =0;i<loanDetails.contacterList.length;i++){
            var contacter = loanDetails.contacterList[i];
            var contacterBrowsePanel =  $('#'+dialogId+' #'+contacterTempletId).clone().show().addClass('easyui-panel');
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

            contacterBrowsePanel.appendTo($('#'+dialogId+' #' +contacterBrowseId));
        }
        $.parser.parse('#'+dialogId+' #' +contacterBrowseId);
    }
}

function renderCommonView(loanDetails,dialogId){
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
	
	
}
/**查看小企业贷*/
function doBrowse(loanId){
	var flag='audit';
    var strData = getSeLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height() ;
    var dialogId = 'browseDlg';
    var productId = loanDetails.product.id;
     var productCode = loanDetails.product.productCode;
    productCode? productCode = productCode.slice(productCode.lastIndexOf('/')+1)+'Contract'+'':'';


    if (5 == productId || productId == 6)
    	dialogId = 'browseCityWideSeLoan';
    
    if (8 == productId) {
	   	 $('#browseEduDlg').dialog({
	
	   	 	  title: '查看小企业贷',
			        width: 1100,
			        height:600,
			        closed: false,  
			        cache: false,
			        href: 'apply/getHtml?'+'productCode='+loanDetails.product.productCode+'&handler='+'Detail',
			        modal: true,
			        onLoad:function(){
			        	renderOtherField(loanDetails,'loanBrowseTab');
			        	renderContractView('browseEduDlg','contacterBrowseTab','contacterBrowsePanelTemplate',loanDetails);
			        	renderCommonView(loanDetails,'browseEduDlg');
			        	
			        }
	
	   	 })

   }else{
	   
	   if(productCode && typeof($('#'+dialogId)[productCode] ) === 'function'){
		   $('#'+dialogId)[productCode](loanDetails);
	   };
	   
	   $('#' + dialogId).dialog({modal:true,height:h*(0.8)}).dialog('open').dialog('setTitle', '查看小企业贷');
	   
	   
	   'browseCityWideSeLoan' == dialogId ? doBrowseCityWideLoan(loanDetails,'browseForm',dialogId) : loadSeLoanData(loanDetails);
	   
	   
   }

    

  

};
/**查看车贷*/
function doBrowseCL(loanId){
	
	var flag='contractConfirm';
    var strData = getCarLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();
    $('#browseCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '查看车贷');
    if(loanDetails.product) {
        $('#browseCLForm').find('#productName').text(loanDetails.product.productName);
    }
    if(loanDetails.loan) {
        $('#browseCLForm #loanType').text(formatProductCarType(loanDetails.loan.loanType));
        $('#browseCLForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
        $('#browseCLForm #requestTime').text(loanDetails.loan.requestTime + "期");
        $('#browseCLForm #purpose').text(loanDetails.loan.purpose);
    }
    if(loanDetails.person) {
    	$('#browseCLForm #maxRepayAmount').text(transferUndefined(loanDetails.person.maxRepayAmount)+'元/月');//可接受的最高月还款额
    	$('#browseCLForm #professionType').text(loanDetails.person.professionType);// 职业类型
    	if(loanDetails.person.professionType=='自营'){
    		$('.enterpprise1').css('display','table-row');
    		$('.enterpprise2').css('display','table-row');
    		$('#browseCLForm #privateEnterpriseType').text(loanDetails.person.privateEnterpriseType);
 	        $('#browseCLForm #foundedDate').text(getYMD(loanDetails.person.foundedDate));
 	        $('#browseCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
 	        $('#browseCLForm #totalEmployees').text(loanDetails.person.totalEmployees+'人');
 	        $('#browseCLForm #ratioOfInvestments').text(loanDetails.person.ratioOfInvestments+'%');
 	        $('#browseCLForm #monthOfProfit').text(loanDetails.person.monthOfProfit+'万元/月');
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
        } else{
        	 $('#browseCLForm #school').text('');
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
        }else if(loanDetails.person.incomePerMonth==0){
      	  $('#browseCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");

        }
        if(loanDetails.person.payDate){
        	  $('#browseCLForm #personPayDay').text(loanDetails.person.payDate + "号");
        }
        if(loanDetails.person.otherIncome){
            $('#browseCLForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }else if(loanDetails.person.otherIncome==0){
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

};


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

/**查看车贷展期*/
function doBrowseExtensionCL(loanId){
	var flag='contractConfirm';
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
    	$('#browseCLExtensionForm #professionType').text(loanDetails.person.professionType);// 职业类型
    	if(loanDetails.person.professionType=='自营'){
    		$('.enterpprise1').css('display','table-row');
    		$('.enterpprise2').css('display','table-row');
    		$('#browseCLExtensionForm #privateEnterpriseType').text(loanDetails.person.privateEnterpriseType);
 	        $('#browseCLExtensionForm #foundedDate').text(getYMD(loanDetails.person.foundedDate));
 	        $('#browseCLExtensionForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
 	        $('#browseCLExtensionForm #totalEmployees').text(loanDetails.person.totalEmployees+'人');
 	        $('#browseCLExtensionForm #ratioOfInvestments').text(loanDetails.person.ratioOfInvestments+'%');
 	        $('#browseCLExtensionForm #monthOfProfit').text(loanDetails.person.monthOfProfit+'万元/月');
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
        	 $('#browseCLExtensionForm #childrenSchool').text(loanDetails.person.childrenSchool);
        } else{
        	 $('#browseCLExtensionForm #school').text('');
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
        }else if(loanDetails.person.incomePerMonth==0){
      	  $('#browseCLExtensionForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");

        }
        if(loanDetails.person.payDate){
        	  $('#browseCLExtensionForm #personPayDay').text(loanDetails.person.payDate + "号");
        }
        if(loanDetails.person.otherIncome){
            $('#browseCLExtensionForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }else if(loanDetails.person.otherIncome==0){
            $('#browseCLExtensionForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }
        	

      
        $('#browseCLExtensionForm #personWitness').text(loanDetails.person.witness);
        $('#browseCLExtensionForm #personWorkThatDept').text(loanDetails.person.workThatDept);
        $('#browseCLExtensionForm #personWorkThatPosition').text(loanDetails.person.workThatPosition);
        $('#browseCLExtensionForm #personWorkThatTell').text(loanDetails.person.workThatTell);
        $('#browseCLExtensionForm #personCompanyType').text(formatCompanyType(loanDetails.person.companyType));
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
    if(loanDetails.salesDept) {
        $('#browseCLExtensionForm #salesDeptName').text(loanDetails.salesDept.name);
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
};