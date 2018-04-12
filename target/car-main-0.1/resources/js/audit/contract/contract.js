var url;
$(function () {	
	
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
	
	//营业网点
	$('#toolbar #salesDeptComb').combobox({
		url:'apply/getCurSalesDept',
	        valueField:'id',  
	        textField:'name',
		    onLoadSuccess:function(){
	        	var data = $(this).combobox('getData');
	        	if(data.length==1)
	        		$(this).combobox('select', data[0].id);
	        }
	});
    // 借款状态
    $('#toolbar #loanStatusContractComb').combobox({
    	url:'audit/contract/getLoanStatusList',
    	valueField:'value' ,
    	textField:'name',
    	 onLoadSuccess:function(){
    		  var data = $(this).combobox('getData');    		
    	       $(this).combobox('select',data[1].value);
    	 }
    });
    
    // 合同来源
	$('#ff #contractSrcComb').combobox({
		url:'audit/contract/getContractSrcList',
	        valueField:'enumCode',  
	        textField:'enumValue',
		    onLoadSuccess:function(){
	        	var data = $(this).combobox('getData');
	        	if(data.length>0){
	        		$(this).combobox('select', data[0].enumCode);
	        	}
	        }
	});
	  //展期开户行默认值设定
	$('#ff #bankComb').combobox({
		url:'bank/getBankListIn.json',
        valueField:'id',  
        textField:'bankName',
	    onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length>0){
        		$(this).combobox('select', bank);
        	}
        }
	});
	
	//设置开始时间为15天前 0时0分0秒
    var date=new Date();
    var startDate=new Date((+date)-15*24*3600*1000);    
    var start = startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+ startDate.getDate();
    $('#toolbar #auditDateStartDate').val(start);
    
    //设置开始时间为7天前 
    $('#exportLogTab #operatorStartTime').val(CurentTimeStand(-7));  
    $('#exportLogTab #operatorEndTime').val(CurentTime(0));  
    
	// 查询按钮
	$('#searchContractBt').bind('click', search);
	
	// 导出按钮
	$('#exportExcelLogBt').bind('click', exportExcelLogDlg);	
	 //校验
    $.extend($.fn.validatebox.defaults.rules, { 
    integerCheck:{
        validator:function(value){
            return /^[+]?[0-9]\d*$/.test(value);
        },
        message: '请输入整数'
    }  
      });
    
    /***
     * 		loanListList.add(new LoanList(loan.getId(),loan.getLoanId(), loan.getPerson().getName(), loan.getPerson().getIdnum(), loan.getService().getName(), loan.getCrm().getName(), loan.getAuditMoney(), loan
				.getAuditTime(), loan.getContractNo(), guaranteeList, loan.getProductType().longValue(), loan.getStatus(), loan.getSubmitDate(), loan.getAssessor().getName(), loan.getRequestDate(),
				loan.getAuditDate(), operations.toString(), loan.getHasHouse(),loan.getExtensionTime())); 
     * */
    
    // 列表
    $('#list_result').datagrid({
        url: 'audit/contract/list.json',
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
				return '<a style="font-weight:bolder" href="javascript:void(0)" onclick="browse('+rowData.id+ ',' + rowData.productId  + ',' + rowData.productType + ',' + rowData.extensionTime +')">' + rowData.name + '</a>';
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
		}, 
		{
			field : 'extensionTime',
			title : '展期期次',
			formatter : function(value, row, index) {
				if(value == 0) {
					return "无";
				} else {
					return value;
				}
			}
		},
		
		{
			field : 'serviceName',
			title : '客服'
		}, {
			field : 'crmName',
			title : '客户经理'
		}, {
			field : 'auditMoney',
			title : '审批金额(元)'
		}, {
			field : 'time',
			title : '审批期限'
		}, {
			field : 'hasHouse',
			title : '费率',
			formatter: function(value, row, index){
            	return  formatEnumName(value,'HAVE_HOUSE_STATUS');
            }
		},{
			field : 'requestDate',
			title : '申请日期',
			formatter:formatRequestDate
		}, {
			field : 'submitDate',
			title : '提交时间'
		},
		 {
			field : 'auditDate',
			title : '签批日期'
		},{
			field : 'assessorName',
			title : '复核人员'
		}, {
			field : 'status',
			title : '状态',
			formatter: function(value, row, index){
	          	return  formatEnumName(value,'LOAN_STATUS');
			}		
		}, {
			field : 'id',
			title : '合同名称',
			formatter : function(value, rowData, rowIndex) {
			
				var link = "";
				// 状态为"合同签订"、"合同确认退回"、"财务审核退回"、"财务放款退回"的借款，可以生成合同
				if(rowData.status == 60 || rowData.status == 80 || rowData.status == 100 || rowData.status == 120){
					link += "<a href='javascript:generateContractPage(" + rowData.id + ");'>生成</a>&nbsp;&nbsp;&nbsp;";
				}
				// 状态为"展期签订"、"展期合同确认退回"的借款，可以生成合同
				if(rowData.status == 61 || rowData.status == 81){
					link += "<a href='javascript:generateExtensionContractPage(" + rowData.id + ");'>生成</a>&nbsp;&nbsp;&nbsp;";
				}
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
				// 小企业贷
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
				// 车贷
				if((rowData.productId ==2 || rowData.productId ==4)  && rowData.contractNo!=null&&rowData.extensionTime==0){
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
					link += "<a href='javascript:previewLoanExtensionAgreement(\""+rowData.id+"\")' >借款抵押</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='javascript:previewFundRiskAgreement(\""+rowData.id+"\")' >风险基金</a>&nbsp;&nbsp;&nbsp;";
					link += "<a href='report/carRepaymentNotice?extensionId="+rowData.id+ "' target='_blank'>还款</a>&nbsp;&nbsp;&nbsp;"; 
					link += "<a href='report/carDeductionNotice?extensionId="+rowData.id+"' target='_blank'>委托扣款</a>";
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
    
    $(document).keydown(function(e) {
    	if(e.which == 13) {    
    		//如果打开了生成合同页面,按下Enter键，调用生成合同的function
    		if($("#generateContractDlg").is(":visible")==true){
    			generateContract();//生成合同
			}else{
				$('#searchContractBt').click();
			}
    	}
    });
});

/** 操作 */
function formatOperationsCell(value,row,index){
var operations = row.operations.split("|");

var formattedOperations="";
for(var i = 0; i < operations.length; i++) {
	var operation = operations[i];
	if (operation == "日志") {
		operation = '<a href="javascript:void(0)" onclick="businessLogPage('+row.id+')">日志</a>';
	} else if(operation == "合同签约" && row.extensionTime == 0) {
		operation = '<a href="javascript:void(0)" onclick="lcbBaseFn.lcbSignName('+row.id+')">合同签约</a>';
	} else if(operation == "合同签约" && row.extensionTime > 0) {
		operation = '<a href="javascript:void(0)" onclick="extensionContractSign('+row.id+')">合同签约</a>';
	}else if(operation == "提交" && row.extensionTime == 0) {
		operation = '<a href="javascript:void(0)" onclick="contractSubmit('+row.id+')">提&nbsp;&nbsp交</a>';
	}else if(operation == "提交" && row.extensionTime > 0) {
		operation = '<a href="javascript:void(0)" onclick="extensionContractSubmit('+row.id+')">提&nbsp;&nbsp交</a>';
	} else if(operation == "附件") {
		operation = '<a href="javascript:void(0)" onclick="showAttachmentDlg('+row.id+','+  row.extensionTime  +')">附件</a>';
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

// 合同签订
function contractSign(loanId){
	$.ajax({	        
        url: "audit/contract/signContractByStatus",
        type : "POST",
        dataType:"json",
        data: {	  
        	loanId:loanId
        },
        success:function(data){
        	if(data.isSuccess) {
        		parent.$.messager.show({
					title : '提示',
					msg : '设置成功！'
				});
        	}else {
        		$.messager.show({
					title: '提示',
					msg: '该借款正处于' + data.loanStatus + '状态,不可再次进行签订'
				});
        	}
        	$('#list_result').datagrid('reload');
        },
        error:function(data){
	 		 $.messager.show({
					title: 'warning',
					msg: data.responseText
				});
		}
    });
}
function extensionContractSign(loanId){
	parent.$.messager.confirm('确认','签订合同？', function(r) {
		if(!r){
			return false;
		}
		$.ajax({	        
	        url: "audit/contract/signExtensionContractByStatus",
	        type : "POST",
	        dataType:"json",
	        data: {	  
	        	loanId:loanId
	        },
	        success:function(data){		        	
	        	if(data.isSuccess) {
	        		parent.$.messager.show({
						title : '提示',
						msg : '设置成功！'
					});
	        	}else {
	        		$.messager.show({
						title: '提示',
						msg: '该借款正处于' + data.extensionStatus + '状态,不可再次进行签订'
					});
	        	}
	        	$('#list_result').datagrid('reload');
	        },
	        error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText
					});
			}
	    });
	});
}

// 合同提交
function  contractSubmit(loanId){	
	parent.$.messager.confirm('确认','提交合同？', function(r) {
		if(!r){
			return false;
		}
		$.ajax({	        
	        url: "audit/contract/submitContract",
	        type : "POST",
	        dataType:"json",
	        data: {	  
	        	loanId:loanId
	        },
	        success:function(){		        	
	        	parent.$.messager.show({
					title : '提示',
					msg : '设置成功！'
				});
	        	$('#list_result').datagrid('reload');
	        },
	        error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText
					});
			}
	    });
	});
}


//展期合同提交
function  extensionContractSubmit(loanId){	
	parent.$.messager.confirm('确认','提交合同？', function(r) {
		if(!r){
			return false;
		}
		$.ajax({	        
	        url: "audit/contract/submitExtensionContract",
	        type : "POST",
	        dataType:"json",
	        data: {	  
	        	loanId:loanId
	        },
	        success:function(){		        	
	        	parent.$.messager.show({
					title : '提示',
					msg : '设置成功！'
				});
	        	$('#list_result').datagrid('reload');
	        },
	        error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText
					});
			}
	    });
	});
}
// 附件操作：打开附件对话框
function showAttachmentDlg(loanId,extensionTime){
	if(extensionTime==0)
	{
		window.open (rayUseUrl+"audit/contract/imageUploadView/"+loanId,"newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}
	else
	{
		window.open (rayUseUrl+"audit/contract/extensionImageUploadView/"+loanId,"newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}
}

function formatProductCarType(loanType){
    if(loanType == 1)
        return "移交类";
    else if(loanType==2)
        return "流通类";
    else 
    	return loanType;
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

//弹出生成合同窗口
function generateContractPage(id) {
	
    var url = 'audit/contract/initGenerateContract/' + id;
    $('#generateContractDlg').dialog({
        title: '生成合同',
        width: 400,
        height: 350,
        closed: false,
        cache: false,
        href: url,
        modal: true
    });   
    $('#generateContractDlg').show();
}

//弹出生成展期合同窗口
function generateExtensionContractPage(id) {
	
    var url = 'audit/contract/initGenerateExtensionContract/' + id;
    
    if(!validationGenerateExtension(id)){
    	return;
    }
    $('#generateExtensionContractDlg').dialog({
        title: '生成合同',
        width: 400,
        height: 240,
        closed: false,
        cache: false,
        href: url,
        modal: true
    });   
    $('#generateExtensionContractDlg').show();
}

//验证能否生成展期合同
function validationGenerateExtension(id) {
	var url = 'audit/contract/validationGenerateExtension';
	var flag = false;
	$.ajax({
		  type: "POST",
		  url: url,
		  dataType: "json",
		  async: false,
		  data: {
			  extensionId:id
		  },
		  success:function(data){
				if (data.success != 0) {
					alert(data.msg);
					flag = false;
				}else{
					flag = true;
				}
		}
	 });
	return flag;
}

//弹出生成合同窗口
function exportExcelLogDlg() {
	
    $('#exportLogDlg').dialog({
        title: '合同日志',
        width: 980,
        height: 220,
        closed: false,
        cache: false,
        href: url,
        modal: true
    });   
    $('#exportLogDlg').show();
}


function submitLog(){
	var url1=rayUseUrl+"loanChangeLog/checkExportNum";
	var url2=rayUseUrl+"loanChangeLog/exportExcel";
	var startDate = $('#exportLogTab #operatorStartTime').val();
	var endDate = $('#exportLogTab #operatorEndTime').val();
	$.ajax({
 	 url : url1,	
 	 data: $("#exportLogForm").serialize(),
 	 dataType: 'json',
 	 type:"POST",
 	 success : function(result){
	 	  if(result=="success"){
	 		 url2=url2+"/"+startDate+"/"+endDate;
	 		 self.location.href=url2;	
	 	  }else{ 
			 $.messager.show({
					title : '提示',
					msg : result
			});
	  	 }		    			
 	 }
	});	
}



function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '合同日志',
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
		pageSize: 100,
		striped: true,
		rownumbers: true,
		fit:true,
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

function search(){
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personNameTxt').val();
	queryParams.personIdnum = $('#toolbar #personIdnumTxt').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.salesDeptId = $('#toolbar #salesDeptComb').combobox('getValue');
	queryParams.status = $('#toolbar #loanStatusContractComb').combobox('getValue');
	queryParams.auditDateStart = $('#toolbar #auditDateStartDate').val();
	queryParams.auditDateEnd = $('#toolbar #auditDateEndDate').val();
	queryParams.extensionTime = $('#toolbar #extensionTimeComb').combobox('getValue');
	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}

function loadData(id, brand, model) {
    $('#id').val(id);
    $('#brand').val(brand);
    $('#model').val(model);

}

formatterDate = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};

/*加载同城贷数据*/
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

     

};
/*加载小企业贷数据*/

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

function browse(loanId, productId,productType, extensionTime){
	if(productType ==1){ //小企业贷
	        doBrowse(loanId);
    }else if(productType == 2){ //车贷
    	if(extensionTime == 0) {
    		doBrowseCL(loanId);
    	} else {
    		// 浏览车贷展期详情
    		doBrowseExtensionCL(loanId);
    	}
    }
};

function transferUndefinedAndZero(data){
	if(data){
		return data;
	}else{
		return "";
	}
}

function doBrowseCityWideLoan(loanDetails,form,dialogId){
	var customizeEleId = {dialogId:'browseCityWideSeLoan',
						contacterBrowseId:'cityWideContacterBrowseTab',contacterTempletId:'cityWideContacterBrowsePanelTemplate',
						guaranteeBrowseTab:'cityWideGuaranteeBrowseTab',guaranteeBrowsePanelTemplate:'cityWideGuaranteeBrowsePanelTemplate'};
		loadSeLoanData(loanDetails,customizeEleId);
		loadCityWideLoanData(loanDetails,form);
						

};
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
	var flag='contract';
    var strData = getSeLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);

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

	    var h = $(window).height() ;
	    $('#'+dialogId).dialog({modal:true,height:h*(0.8)}).dialog('open').dialog('setTitle', '查看小企业贷');

	    'browseCityWideSeLoan' == dialogId ? doBrowseCityWideLoan(loanDetails,'browseForm',dialogId) : loadSeLoanData(loanDetails);

    	
    }

    
    // if(loanDetails.product) {
    //     $('#browseForm #productName').text(loanDetails.product.productName);
    // }
    // if(loanDetails.loan) {
    //     $('#browseForm #requestMoney').text(loanDetails.loan.requestMoney + "元");
    //     $('#browseForm #requestTime').text(loanDetails.loan.requestTime + "期");
    //     $('#browseForm #purpose').text(loanDetails.loan.purpose);
    // }
    // if(loanDetails.person) {
    //     $('#browseForm #personName').text(loanDetails.person.name);
    //     $('#browseForm #personSex').text(formatSex(loanDetails.person.sex));
    //     $('#browseForm #personIdnum').text(loanDetails.person.idnum);
    //     $('#browseForm #personMarried').text(formatMarried(loanDetails.person.married));
    //     $('#browseForm #personEducationLevel').text(formatEducationLevel(loanDetails.person.educationLevel));
    //     $('#browseForm #personHasChildren').text(formatHave(loanDetails.person.hasChildren));
    //     $('#browseForm #personZipCode').text(loanDetails.person.zipCode);
    //     $('#browseForm #personAddress').text(loanDetails.person.address);
    //     $('#browseForm #personMobilePhone').text(loanDetails.person.mobilePhone);
    //     $('#browseForm #personEmail').text(loanDetails.person.email);
    //     $('#browseForm #personHomePhone').text(transferUndefined(loanDetails.person.homePhone));
    //     if(loanDetails.person.professionType){
    //         $('#browseForm').find('#professionType').text(loanDetails.person.professionType);//职业类型
    //     }
    //     // 根据房产类型判断租金和房贷显示与否
    //     // 规则，如果房产类型是商品房、经济适用房、自建房则显示房贷
    //     // 如果是租用 则显示每月租金
    //     // 如果是亲戚住房则租金和房贷均没有
    //     $('#browseForm #personHouseEstateType').text(loanDetails.person.houseEstateType);
    //     var personHouseTR = $('#browseForm #personHouseEstateType').parent().parent();
    //     if(loanDetails.person.houseEstateType == '商品房' || 
    //     		loanDetails.person.houseEstateType == '经济适用房' || 
    //     		loanDetails.person.houseEstateType == '自建房'){
    //     	personHouseTR.find(':nth-child(3)').hide();
    //     	personHouseTR.find(':nth-child(4)').hide();
    //     	personHouseTR.find(':nth-child(5)').show();
    //     	personHouseTR.find(':nth-child(6)').show();
        	
    //     	$('#browseForm #personHasHouseLoan').text(formatHave(loanDetails.person.hasHouseLoan));
    //     }
    //     if(loanDetails.person.houseEstateType == '租用'){
    //     	personHouseTR.find(':nth-child(3)').show();
    //     	personHouseTR.find(':nth-child(4)').show();
    //     	personHouseTR.find(':nth-child(5)').hide();
    //     	personHouseTR.find(':nth-child(6)').hide();

    //     	$('#browseForm #personRentPerMonth').text(loanDetails.person.rentPerMonth + "元");
    //     }
    //     if(loanDetails.person.houseEstateType == '亲戚住房'){
    //     	personHouseTR.find(':nth-child(3)').hide();
    //     	personHouseTR.find(':nth-child(4)').hide();
    //     	personHouseTR.find(':nth-child(5)').hide();
    //     	personHouseTR.find(':nth-child(6)').hide();
    //     }
        
    //     $('#browseForm #personHouseEstateAddress').text(loanDetails.person.houseEstateAddress);
    //     if(loanDetails.person.incomePerMonth){
    //     	$('#browseForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth +"万元/月");
    //     }
        
    // }
    // if(loanDetails.company) {
    //     $('#browseForm #companyName').text(loanDetails.company.name);
    //     $('#browseForm #companyIndustryInvolved').text(loanDetails.company.industryInvolved);
    //     $('#browseForm #companyLegalRepresentative').text(loanDetails.company.legalRepresentative);
    //     $('#browseForm #companyLegalRepresentativeId').text(loanDetails.company.legalRepresentativeId);
    //     $('#browseForm #companyIncomePerMonth').text(loanDetails.company.incomePerMonth + "万元/月");
    //     $('#browseForm #companyFoundedDate').text(getYMD(loanDetails.company.foundedDate));
    //     $('#browseForm #companyCategory').text(formatCompanyCategory(loanDetails.company.category));
    //     $('#browseForm #companyAddress').text(loanDetails.company.address);
    //     $('#browseForm #companyAvgProfitPerYear').text(loanDetails.company.avgProfitPerYear + "万元/年");
    //     $('#browseForm #companyPhone').text(transferUndefined(transferUndefined(loanDetails.company.phone)));
    //     $('#browseForm #companyZipCode').text(loanDetails.company.zipCode);
    //     $('#browseForm #companyOperationSite').text(loanDetails.company.operationSite);
    //     $('#browseForm #companyMajorBusiness').text(loanDetails.company.majorBusiness);
    //     $('#browseForm #companyEmployeesNumber').text(loanDetails.company.employeesNumber);
    //     $('#browseForm #companyEmployeesWagesPerMonth').text(loanDetails.company.employeesWagesPerMonth + "万元/月");
    // }
    // if(loanDetails.service) {
    //     $('#browseForm #serviceName').text(loanDetails.service.name);
    // }
    // if(loanDetails.loan) {
    //     $('#browseForm #customerSource').text(loanDetails.loan.customerSource);
    //     $('#browseForm #requestDate').text(getYMD(loanDetails.loan.requestDate));
    // }
    // if(loanDetails.crm) {
    //     $('#browseForm #crmCode').text(loanDetails.crm.code);
    //     $('#browseForm #crmName').text(loanDetails.crm.name);
    // }
    // if(loanDetails.salesDept) {
    //     $('#browseForm #salesDeptName').text(loanDetails.salesDept.name);
    // }
    // if(loanDetails.assessor) {
    //     $('#browseForm #assessorName').text(loanDetails.assessor.name);
    // }
    // if(loanDetails.loan.remark) {
    //     $('#browseForm #remark').text(loanDetails.loan.remark);
    // }
    // // 清空联系人列表（除了模板）
    
    // $('#contacterBrowseTab > #contacterBrowsePanelTemplate ~ div').remove();
    // if(loanDetails.contacterList) {
    //     for(var i =0;i<loanDetails.contacterList.length;i++){
    //         var contacter = loanDetails.contacterList[i];
    //         var contacterBrowsePanel =  $('#contacterBrowsePanelTemplate').clone().show().addClass('easyui-panel');
    //         var contacterBrowsePanelId = "contacterBrowsePanel_" + i;
    //         contacterBrowsePanel.attr("id",contacterBrowsePanelId);
    //         contacterBrowsePanel.attr("title","联系人"+(i+1));

    //         contacterBrowsePanel.find('#contacterName').text(contacter.name);
    //         contacterBrowsePanel.find('#contacterRelationship').text(contacter.relationship);
    //         contacterBrowsePanel.find('#contacterMobilePhone').text(contacter.mobilePhone);
    //         contacterBrowsePanel.find('#contacterHomePhone').text(transferUndefined(contacter.homePhone));
    //         contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
    //         contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));

    //         contacterBrowsePanel.appendTo($('#contacterBrowseTab'));
    //     }
    //     $.parser.parse('#contacterBrowseTab');
    // }

    // $('#guaranteeBrowseTab > #guaranteeBrowsePanelTemplate ~ div').remove();
    // if(loanDetails.guaranteeList) {
    //     for(var i =0;i<loanDetails.guaranteeList.length;i++){
    //         var guarantee = loanDetails.guaranteeList[i];
    //         var guaranteeBrowsePanel =  $('#guaranteeBrowsePanelTemplate').clone().show().addClass('easyui-panel');
    //         var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;
    //         guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
    //         guaranteeBrowsePanel.attr("title","担保人"+(i+1));
    //         if(loanDetails.guaranteeList[i].flag){
    //        	 guaranteeBrowsePanel.find('#flag').text("该担保人为指定担保人");
    //         }
    //         if(guarantee.guaranteeType==0){//自然人
    //        	 guaranteeBrowsePanel.find('#tr7').hide();
    //             guaranteeBrowsePanel.find('#tr8').hide();
    //       	 guaranteeBrowsePanel.find('#guaranteeName').text(guarantee.name);
    //            guaranteeBrowsePanel.find('#guaranteeType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
    //            guaranteeBrowsePanel.find('#guaranteeIdnum').text(transferUndefined(guarantee.idnum));
    //            guaranteeBrowsePanel.find('#guaranteeSex').text(transferUndefined(formatSex(guarantee.sex)));
    //            guaranteeBrowsePanel.find('#guaranteeMarried').text(transferUndefined(formatMarried(guarantee.married)));
    //            guaranteeBrowsePanel.find('#guaranteeEducationLevel').text(transferUndefined(guarantee.educationLevel));
    //            guaranteeBrowsePanel.find('#guaranteeHasChildren').text(transferUndefined(formatYes(guarantee.hasChildren)));
    //            guaranteeBrowsePanel.find('#guaranteeAddress').text(transferUndefined(guarantee.address));
    //            guaranteeBrowsePanel.find('#guaranteeMobilePhone').text(transferUndefined(guarantee.mobilePhone));
    //            guaranteeBrowsePanel.find('#guaranteeEmail').text(transferUndefined(guarantee.email));
    //            guaranteeBrowsePanel.find('#personHomePhone').text(transferUndefined(guarantee.homePhone));
    //            guaranteeBrowsePanel.find('#guaranteeCompanyFullName').text(transferUndefined(guarantee.companyFullName));
    //            guaranteeBrowsePanel.find('#guaranteeZipCode').text(transferUndefined(guarantee.zipCode));
    //            guaranteeBrowsePanel.find('#guaranteeCompanyAddress').text(transferUndefined(guarantee.companyAddress));
    //            guaranteeBrowsePanel.find('#guaranteeCompanyPhone').text(transferUndefined(guarantee.companyPhone));
              
             
             
    //       }else if(guarantee.guaranteeType==1){//法人
    //       	 guaranteeBrowsePanel.find('#tr1').hide();
    //            guaranteeBrowsePanel.find('#tr2').hide();
    //            guaranteeBrowsePanel.find('#tr3').hide();
    //            guaranteeBrowsePanel.find('#tr4').hide();
    //            guaranteeBrowsePanel.find('#tr5').hide();
    //            guaranteeBrowsePanel.find('#tr6').hide();                      
    //       	 guaranteeBrowsePanel.find('#tr7').show();
    //            guaranteeBrowsePanel.find('#tr8').show(); 
    //            guaranteeBrowsePanel.find('#guaType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
    //            guaranteeBrowsePanel.find('#guaCompanyFullName').text(transferUndefined(guarantee.companyFullName));
    //            guaranteeBrowsePanel.find('#guaZipCode').text(transferUndefined(guarantee.zipCode));
    //            guaranteeBrowsePanel.find('#guaCompanyAddress').text(transferUndefined(guarantee.companyAddress));
    //            guaranteeBrowsePanel.find('#guaCompanyPhone').text(transferUndefined(guarantee.companyPhone));
          	
    //       }
           
    //         guaranteeBrowsePanel.appendTo($('#guaranteeBrowseTab'));
    //     }
    //     $.parser.parse($('#guaranteeBrowseTab').parent());
       
    // } 
};

/**查看车贷*/
function doBrowseCL(loanId){
	
	var flag='contract';
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
    	$('#browseCLForm #professionType').text(transferUndefined(loanDetails.person.professionType));// 职业类型
    	if(loanDetails.person.professionType=='自营'){
    		$('.enterpprise1').css('display','table-row');
    		$('.enterpprise2').css('display','table-row');
    		$('#browseCLForm #privateEnterpriseType').text(loanDetails.person.privateEnterpriseType);
 	        $('#browseCLForm #foundedDate').text(loanDetails.person.foundedDate);
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
            contacterBrowsePanel.find('#contacterHomePhone').text(contacter.homePhone||'');
            contacterBrowsePanel.find('#contacterWorkUnit').text(contacter.workUnit);
            contacterBrowsePanel.find('#contacterHadKnown').text(formatYes(contacter.hadKnown));
            contacterBrowsePanel.find('#address').text(contacter.address);

            contacterBrowsePanel.appendTo($('#carContacterBrowseTab'));
        }
        $.parser.parse('#carContacterBrowseTab');
    }

};




/**查看车贷展期*/
function doBrowseExtensionCL(loanId){
	
	var flag='contract';
    var strData = getCarExtensionLoanDetails(loanId,flag);
    var loanDetails = $.parseJSON(strData);
    var h = $(window).height();
    $('#browseExtensionCLDlg').dialog({modal:true,height:h*(0.9)}).dialog('open').dialog('setTitle', '查看车贷展期');
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
    	$('#browseExtensionCLForm #professionType').text(loanDetails.person.professionType);// 职业类型
    	if(loanDetails.person.professionType=='自营'){
    		$('.enterpprise1').css('display','table-row');
    		$('.enterpprise2').css('display','table-row');
    		$('#browseExtensionCLForm #privateEnterpriseType').text(loanDetails.person.privateEnterpriseType);
 	        $('#browseExtensionCLForm #foundedDate').text(loanDetails.person.foundedDate);
 	        $('#browseExtensionCLForm #businessPlace').text(furmatBusinessPlace(loanDetails.person.businessPlace));
 	        $('#browseExtensionCLForm #totalEmployees').text(loanDetails.person.totalEmployees+'人');
 	        $('#browseExtensionCLForm #ratioOfInvestments').text(loanDetails.person.ratioOfInvestments+'%');
 	        $('#browseExtensionCLForm #monthOfProfit').text(loanDetails.person.monthOfProfit+'万元/月');
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
        var liveTypeTR =  $('#browseCLForm #personLiveType').parent().parent();
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
    }
    if(loanDetails.person) {
        $('#browseExtensionCLForm #personDeptName').text(loanDetails.person.deptName);
        $('#browseExtensionCLForm #personJob').text(loanDetails.person.job);
        $('#browseExtensionCLForm #personExt').text(loanDetails.person.ext);
        if(loanDetails.person.incomePerMonth){
        	  $('#browseExtensionCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");
        }else if(loanDetails.person.incomePerMonth==0){
      	  $('#browseExtensionCLForm #personIncomePerMonth').text(loanDetails.person.incomePerMonth + "元");

        }
        if(loanDetails.person.payDate){
        	  $('#browseExtensionCLForm #personPayDay').text(loanDetails.person.payDate + "号");
        }
        if(loanDetails.person.otherIncome){
            $('#browseExtensionCLForm #personOtherIncome').text(loanDetails.person.otherIncome + "元");
        }else if(loanDetails.person.otherIncome==0){
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
    if(loanDetails.salesDept) {
        $('#browseExtensionCLForm #salesDeptName').text(loanDetails.salesDept.name);
    }
    if(loanDetails.assessor) {
        $('#browseExtensionCLForm #assessorName').text(loanDetails.assessor.name);
    }
    if(loanDetails.loan.remark) {
        $('#browseExtensionCLForm #remark').text(loanDetails.loan.remark);
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
            contacterBrowsePanel.find('#address').text(formatYes(contacter.hadKnown));

            contacterBrowsePanel.appendTo($('#carExtensionContacterBrowseTab'));
        }
        $.parser.parse('#carExtensionContacterBrowseTab');
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


function generateContract() {
    var isValidate = $('#ff').form('validate');
    if($('#bankAccount').val() == 0){
    	parent.$.messager.show({
			title : '提示',
			msg : '请输入银行卡号'
		});
    	return false;
    }
    if(isverificationBank ==1){
  //验证银行卡是否是10-20位数字
    var reg = /^\d{10,20}$/g;
    if(!reg.test($('#bankAccount').val())) 
    { 
    	parent.$.messager.show({
			title : '提示',
			msg : '银行卡号格式错误，应该是10-20位数字！'
		});
    	return false;
    }
    
    }
    if($('#bank').combobox('getValue') == 0){
       	parent.$.messager.show({
    			title : '提示',
    			msg : '请选择开户银行'
    		});
       	return false;
    }
   if($('#bankBranch').val() == 0){
	    	parent.$.messager.show({
				title : '提示',
				msg : '请输入开户网点'
			});
	    	return false;
   }
   
   if($('#organBankName4').val() != null ){
	   if($('#organBankAccount4').combobox('getValue') == 0){
		   parent.$.messager.show({
				title : '提示',
				msg : '请选择放款银行'
			});
	  	return false;
	   }
   }
   
   if($('#organBankName5').val() != null ){
	   if($('#organBankAccount5').combobox('getValue') == 0){
		   parent.$.messager.show({
				title : '提示',
				msg : '请选择还款银行'
			});
	  	return false;
	   }
   }
   
   //担保合同生成校验
   
   //自然人担保
   if($('#naturalGuaranteeName1').val() !=null && $('#naturalGuaranteeName1').val().length != 0){
			   if($('#naturalGuaranteeBankAccount1').val() == 0){
			   	parent.$.messager.show({
						title : '提示',
						msg : '请输入银行卡号'
					});
			   	return false;
			   }
			   if($('#naturalGuaranteeBank1').combobox('getValue') == 0){
			      	parent.$.messager.show({
			   			title : '提示',
			   			msg : '请选择开户银行'
			   		});
			      	return false;
			   }
			  if($('#naturalGuaranteeBankBranch1').val() == 0){
				    	parent.$.messager.show({
							title : '提示',
							msg : '请输入开户网点'
						});
				    	return false;
			  }
   }  
 
   if($('#naturalGuaranteeName2').val() !=null && $('#naturalGuaranteeName2').val().length != 0){
	      if($('#naturalGuaranteeBankAccount2').val() == 0){
		   	parent.$.messager.show({
					title : '提示',
					msg : '请输入银行卡号'
				});
		   	return false;
		   }
		   if($('#naturalGuaranteeBank2').combobox('getValue') == 0){
		      	parent.$.messager.show({
		   			title : '提示',
		   			msg : '请选择开户银行'
		   		});
		      	return false;
		   }
		  if($('#naturalGuaranteeBankBranch2').val() == 0){
			    	parent.$.messager.show({
						title : '提示',
						msg : '请输入开户网点'
					});
			    	return false;
		  }
   }
   //法人担保
   if($('#legalGuaranteeName1').val() !=null && $('#legalGuaranteeName1').val().length != 0){
	   if($('#legalGuaranteeBankAccount1').val() == 0){
	   	parent.$.messager.show({
				title : '提示',
				msg : '请输入银行卡号'
			});
	   	return false;
	   }
	   if($('#legalGuaranteeBank1').combobox('getValue') == 0){
	      	parent.$.messager.show({
	   			title : '提示',
	   			msg : '请选择开户银行'
	   		});
	      	return false;
	   }
	   
	   
	  if($('#legalGuaranteeBankBranch1').val() == 0){
		    	parent.$.messager.show({
					title : '提示',
					msg : '请输入开户网点'
				});
		    	return false;
	  }
   	}  
   if($('#legalGuaranteeName2').val() !=null && $('#legalGuaranteeName2').val().length != 0){
	   	if($('#legalGuaranteeBankAccount2').val() == 0){
		   	parent.$.messager.show({
					title : '提示',
					msg : '请输入银行卡号'
				});
		   	return false;
	   	}
	   if($('#legalGuaranteeBank2').combobox('getValue') == 0){
	      	parent.$.messager.show({
	   			title : '提示',
	   			msg : '请选择开户银行'
	   		});
	      	return false;
	   }
	  if($('#legalGuaranteeBankBranch2').val() == 0){
		    	parent.$.messager.show({
					title : '提示',
					msg : '请输入开户网点'
				});
		    	return false;
	  }
   }
    if (!isValidate) {
        return false;
    }
    if ($('#isSign').val() == 0) {
        $.messager.confirm('确认对话框', '您确认要生成合同吗？', function (r) {
            if (r) {
            	doGenerateContractPre();
            } else {
                return false;
            }
        });
    } else {
        $.messager.confirm('确认对话框', '合同已经生成，您确认要覆盖之前的合同吗？', function (r) {
            if (r) {
            	doGenerateContractPre();
            } else {
                return false;
            }
        });
    }
}

function doGenerateContract() {
	$.ajax({
		type : 'post',
		url : 'audit/contract/generateContract',
		data : $('#ff').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '操作成功！'
				});
				$('#generateContractDlg').dialog('close');
				$("#list_result").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

function generateExtensionContract() {
    var isValidate = $('#ff').form('validate');

  
    if($('#bankAccount').val() == 0){
    	parent.$.messager.show({
			title : '提示',
			msg : '请输入银行卡号'
		});
    	return false;
    }
    if(isverificationBank ==1){
    //验证银行卡是否是10-20位数字
    var reg = /^\d{10,20}$/g;
    if(!reg.test($('#bankAccount').val())) 
    { 
    	parent.$.messager.show({
			title : '提示',
			msg : '银行卡号格式错误，应该是10-20位数字！'
		});
    	return false;
    }
    }
   
    
    
   if($('#bankBranch').val() == 0){
	    	parent.$.messager.show({
				title : '提示',
				msg : '请输入开户网点'
			});
	    	return false;
   }
   if (!isValidate) {
       return false;
   }
    if ($('#isSign').val() == 0) {
        $.messager.confirm('确认对话框', '您确认要生成合同吗？', function (r) {
            if (r) {
                doGenerateExtensionContract();
            } else {
                return false;
            }
        });
    } else {
        $.messager.confirm('确认对话框', '合同已经生成，您确认要覆盖之前的合同吗？', function (r) {
            if (r) {
            	doGenerateExtensionContract();
            } else {
                return false;
            }
        });
    }
}

function doGenerateExtensionContract() {
	$.ajax({
		type : 'post',
		url : 'audit/contract/generateExtensionContract',
		data : $('#ff').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '操作成功！'
				});
				$('#generateExtensionContractDlg').dialog('close');
				$("#list_result").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

// /////////////////////zwl add start
function Preview1(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/reportOne",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBill1(data);
            LODOP.PREVIEW();
        }
    });
};

var LODOP; //声明为全局变量
function CreateFullBill1(data) {
    LODOP = getLodop();
    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    //合同编号
    LODOP.ADD_PRINT_TEXT(78, 604, 150, 12, data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //年
    LODOP.ADD_PRINT_TEXT(122, 163, 56, 12, data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(122, 213, 50, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(122, 253, 50, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //市
    LODOP.ADD_PRINT_TEXT(122, 316, 72, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //区
    LODOP.ADD_PRINT_TEXT(122, 388, 72, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //甲方 借款人
    LODOP.ADD_PRINT_TEXT(157, 148, 280, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(157, 512, 210, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //住址
    LODOP.ADD_PRINT_TEXT(180, 148, 280, 24, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //email
    LODOP.ADD_PRINT_TEXT(180, 512, 210, 12, data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //大写金额
    LODOP.ADD_PRINT_TEXT(335, 410, 125, 12, data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

    //小写金额
    LODOP.ADD_PRINT_TEXT(335, 570, 100, 12, data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

};


function Preview2(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/reportTwo",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            //alert();
            CreateFullBill2(data);
            LODOP.PREVIEW();
        }
    });

};


function CreateFullBill2(data) {

    LODOP = getLodop();
    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    //合同编号
    LODOP.ADD_PRINT_TEXT(72, 600, 150, 12, data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //年
    LODOP.ADD_PRINT_TEXT(106, 261, 56, 12, data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(106, 326, 36, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(106, 364, 36, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //市
    LODOP.ADD_PRINT_TEXT(106, 416, 65, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //区
    LODOP.ADD_PRINT_TEXT(106, 478, 65, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //甲方 借款人
    LODOP.ADD_PRINT_TEXT(143, 148, 280, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //身份证
    LODOP.ADD_PRINT_TEXT(167, 148, 280, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //现住址
    LODOP.ADD_PRINT_TEXT(189, 148, 370, 12, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //邮编
    LODOP.ADD_PRINT_TEXT(212, 148, 280, 12, data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //借款用途
    LODOP.ADD_PRINT_TEXT(290, 188, 310, 12, data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //借款金额
    LODOP.ADD_PRINT_TEXT(318, 283, 226, 12, data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //十
    LODOP.ADD_PRINT_TEXT(318, 518, 4, 12, data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //万
    LODOP.ADD_PRINT_TEXT(318, 546, 4, 12, data[13]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //千
    LODOP.ADD_PRINT_TEXT(318, 573, 4, 12, data[14]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //百
    LODOP.ADD_PRINT_TEXT(318, 601, 4, 12, data[15]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //十
    LODOP.ADD_PRINT_TEXT(318, 629, 4, 12, data[16]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //元
    LODOP.ADD_PRINT_TEXT(318, 657, 4, 12, data[17]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //角
    LODOP.ADD_PRINT_TEXT(318, 682, 4, 12, data[18]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //分
    LODOP.ADD_PRINT_TEXT(318, 712, 4, 12, data[19]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月还本息
    LODOP.ADD_PRINT_TEXT(344, 283, 226, 12, data[20]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);


    //十
    LODOP.ADD_PRINT_TEXT(344, 512, 4, 12, data[21]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //万
    LODOP.ADD_PRINT_TEXT(344, 546, 4, 12, data[22]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //千
    LODOP.ADD_PRINT_TEXT(344, 573, 4, 12, data[23]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //百
    LODOP.ADD_PRINT_TEXT(344, 601, 4, 12, data[24]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //十
    LODOP.ADD_PRINT_TEXT(344, 629, 4, 12, data[25]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //元
    LODOP.ADD_PRINT_TEXT(344, 657, 4, 12, data[26]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //角
    LODOP.ADD_PRINT_TEXT(344, 682, 4, 12, data[27]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //分
    LODOP.ADD_PRINT_TEXT(344, 712, 4, 12, data[28]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);


    //期数
    LODOP.ADD_PRINT_TEXT(372, 248, 56, 12, data[29]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //还款日
    LODOP.ADD_PRINT_TEXT(372, 528, 36, 12, data[30]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //年
    LODOP.ADD_PRINT_TEXT(398, 204, 36, 12, data[31]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(398, 242, 24, 12, data[32]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(398, 274, 24, 12, data[33]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //年
    LODOP.ADD_PRINT_TEXT(398, 318, 36, 12, data[34]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(398, 363, 24, 12, data[35]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(398, 393, 24, 12, data[36]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //户名
    LODOP.ADD_PRINT_TEXT(436, 222, 80, 36, data[37]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //户名
    LODOP.ADD_PRINT_TEXT(436, 316, 200, 36,  data[38]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //户名
    LODOP.ADD_PRINT_TEXT(436, 542, 180, 36,  data[39]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
};


function Preview3(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/reportThree",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBill3(data);
            LODOP.PREVIEW();
        }
    });

};



function CreateFullBill3(data) {
    LODOP = getLodop();
    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");


    //合同编号
    LODOP.ADD_PRINT_TEXT(76, 608, 150, 12, data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //年
    LODOP.ADD_PRINT_TEXT(120, 167, 56, 12, data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(120, 217, 50, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(120, 257, 50, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //市
    LODOP.ADD_PRINT_TEXT(120, 325, 72, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //区
    LODOP.ADD_PRINT_TEXT(120, 398, 72, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //甲方 借款人
    LODOP.ADD_PRINT_TEXT(153, 156, 280, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(153, 516, 210, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //住址
    LODOP.ADD_PRINT_TEXT(176, 156, 280, 24, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //email
    LODOP.ADD_PRINT_TEXT(176, 516, 210, 12, data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //金额
    LODOP.ADD_PRINT_TEXT(542, 408, 100, 12, data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);


    //换页
    LODOP.NEWPAGE();

    //金额大写
    LODOP.ADD_PRINT_TEXT(200, 335, 82, 12, data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额小写
    LODOP.ADD_PRINT_TEXT(200, 452, 100, 12, data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额大写
    LODOP.ADD_PRINT_TEXT(220, 266, 82, 12, data[13]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额小写
    LODOP.ADD_PRINT_TEXT(220, 379, 100, 12, data[14]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额大写
    LODOP.ADD_PRINT_TEXT(310, 395, 82, 12, data[15]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额小写
    LODOP.ADD_PRINT_TEXT(310, 510, 100, 12, data[16]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额大写
    LODOP.ADD_PRINT_TEXT(330, 408, 82, 12, data[17]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //金额小写
    LODOP.ADD_PRINT_TEXT(330, 524, 100, 12, data[18]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //户名
    LODOP.ADD_PRINT_TEXT(618, 360, 360, 12, data[19]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //开户银行
    LODOP.ADD_PRINT_TEXT(647, 360, 360, 12, data[20]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //账号
    LODOP.ADD_PRINT_TEXT(679, 360, 360, 12, data[21]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
};

///////////////////////zwl add end


////////////////////////////////////////////////wangjl add start
function PreviewCar1(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/car1",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBillCar1(data);
            LODOP.PREVIEW();
        }
    });
}

function CreateFullBillCar1(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    //合同编号
    LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
        //年
    LODOP.ADD_PRINT_TEXT(143, 161, 41, 12,data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //月
    LODOP.ADD_PRINT_TEXT(143, 222, 41, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(143, 280, 41, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某市
    LODOP.ADD_PRINT_TEXT(143, 341, 99, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某区
    LODOP.ADD_PRINT_TEXT(143, 430, 99, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    
       //甲方借款人
    LODOP.ADD_PRINT_TEXT(185, 140, 210, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(206, 130, 207, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //现住址
    LODOP.ADD_PRINT_TEXT(226, 100, 260, 12, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);


    //借款金额
    LODOP.ADD_PRINT_TEXT(611,324,95,12, data[10]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",3);
    
    LODOP.NEWPAGE();
    
    
    if(data[11].length>0){
    	for(var i=0;i<data[11].length;i++){
    		if(i==0){
    			 LODOP.ADD_PRINT_TEXT(408, 153, 100, 12, data[11][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(408, 250, 80, 12, data[11][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(408, 330, 80, 12, data[11][i][2]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(408, 430, 80, 12, data[11][i][3]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(408, 540, 80, 12, data[11][i][4]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(408, 640, 80, 12, data[11][i][5]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    		}else{
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 153, 100, 12, data[11][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 250, 80, 12, data[11][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 330, 80, 12, data[11][i][2]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 430, 80, 12, data[11][i][3]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 540, 80, 12, data[11][i][4]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    			 
    			 LODOP.ADD_PRINT_TEXT(428+(i-1)*12, 640, 80, 12, data[11][i][5]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    		}
    	}
    }
    
    LODOP.ADD_PRINT_TEXT(920, 240, 514, 12, data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);


    LODOP.ADD_PRINT_TEXT(940, 300, 434, 12, data[13]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);


    LODOP.ADD_PRINT_TEXT(960, 100, 660, 12, data[14]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
}

function PreviewCar2(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/car2",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBillCar2(data);
            LODOP.PREVIEW();
        }
    });
}


function CreateFullBillCar2(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

     //合同编号
    LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
     //年
    LODOP.ADD_PRINT_TEXT(135, 260, 41, 12, data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //月
    LODOP.ADD_PRINT_TEXT(135, 314, 41, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(135, 368, 41, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某市
    LODOP.ADD_PRINT_TEXT(135, 516, 61, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某区
    LODOP.ADD_PRINT_TEXT(135, 582, 61, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //甲方借款人
    LODOP.ADD_PRINT_TEXT(159, 220, 180, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(183, 150, 238, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //现住址
    LODOP.ADD_PRINT_TEXT(207, 118, 290, 12, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //联系电话
    LODOP.ADD_PRINT_TEXT(232, 128, 108, 12, data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //电子邮箱
    LODOP.ADD_PRINT_TEXT(232, 280, 118, 12,  data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //借款详细用途
    LODOP.ADD_PRINT_TEXT(406, 194, 280, 24, data[14]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    //借款本金数额
    LODOP.ADD_PRINT_TEXT(430, 288, 180, 24, data[15]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    
    LODOP.ADD_PRINT_TEXT(430, 610, 180, 24, data[16]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//    //百
//    LODOP.ADD_PRINT_TEXT(428, 594, 30, 12,data[16]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //拾
//    LODOP.ADD_PRINT_TEXT(428, 602, 30, 12, data[17]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //万
//    LODOP.ADD_PRINT_TEXT(428, 610, 30, 12, data[18]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //千
//    LODOP.ADD_PRINT_TEXT(428, 618, 30, 12, data[19]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //百
//    LODOP.ADD_PRINT_TEXT(428, 626, 30, 12, data[20]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //拾
//    LODOP.ADD_PRINT_TEXT(428, 634, 30, 12, data[21]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //元
//    LODOP.ADD_PRINT_TEXT(428, 642, 30, 12, data[22]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //角
//    LODOP.ADD_PRINT_TEXT(428, 650, 30, 12, data[23]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //分
//    LODOP.ADD_PRINT_TEXT(428, 658, 30, 12, data[24]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    
    //总利息
    LODOP.ADD_PRINT_TEXT(454, 288, 180, 24, data[17]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    
    LODOP.ADD_PRINT_TEXT(454, 610, 180, 24, data[18]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//     //百
//    LODOP.ADD_PRINT_TEXT(452, 594, 30, 12,data[26]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //拾
//    LODOP.ADD_PRINT_TEXT(452, 602, 30, 12, data[27]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //万
//    LODOP.ADD_PRINT_TEXT(452, 610, 30, 12, data[28]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //千
//    LODOP.ADD_PRINT_TEXT(452, 618, 30, 12, data[29]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //百
//    LODOP.ADD_PRINT_TEXT(452, 626, 30, 12, data[30]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //拾
//    LODOP.ADD_PRINT_TEXT(452, 634, 30, 12, data[31]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //元
//    LODOP.ADD_PRINT_TEXT(452, 642, 30, 12, data[32]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //角
//    LODOP.ADD_PRINT_TEXT(452, 650, 30, 12, data[33]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
//
//    //分
//    LODOP.ADD_PRINT_TEXT(452, 658, 30, 12, data[34]);
//    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
//    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    //实际到账金额
    LODOP.ADD_PRINT_TEXT(478, 288, 180, 24, data[19]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    LODOP.ADD_PRINT_TEXT(478, 610, 180, 24, data[20]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 214, 41, 12, data[21]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //借款期限年
    LODOP.ADD_PRINT_TEXT(504, 294, 41, 12, data[22]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 366, 41, 12, data[23]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 426, 41, 12, data[24]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 510, 41, 12, data[25]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 590, 41, 12, data[26]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(504, 654, 41, 12, data[27]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
     //甲方专用账户:户名
    LODOP.ADD_PRINT_TEXT(546, 244, 96, 24, data[28]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    //甲方专用账号:账号
    LODOP.ADD_PRINT_TEXT(528, 398, 176, 24, data[29]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //甲方专用账户:开户行
    LODOP.ADD_PRINT_TEXT(552, 398, 176, 24, data[30]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
     if(data[31].length>0){
    	for(var i=0;i<data[31].length;i++){
    		 	 LODOP.ADD_PRINT_TEXT(774+(i-1)*12, 108, 100, 12, data[31][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(774+(i-1)*12, 278, 100, 12, data[31][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(774+(i-1)*12, 428, 100, 12, data[31][i][2]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(774+(i-1)*12, 600, 100, 12, data[31][i][3]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    	}
     }
}

function PreviewCar3(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/car3",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBillCar3(data);
            LODOP.PREVIEW();
        }
    });
}


function CreateFullBillCar3(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    //合同编号
    LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //年
    LODOP.ADD_PRINT_TEXT(134, 231, 41, 12,data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //月
    LODOP.ADD_PRINT_TEXT(134, 283, 41, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //日
    LODOP.ADD_PRINT_TEXT(134, 336, 41, 12, data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某市
    LODOP.ADD_PRINT_TEXT(134, 401, 75, 12, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //某某区
    LODOP.ADD_PRINT_TEXT(134, 481, 75, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //甲方借款人
    LODOP.ADD_PRINT_TEXT(158, 207, 214, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(182, 198, 204, 12, data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //现住址
    LODOP.ADD_PRINT_TEXT(204, 157, 283, 12, data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

    //电子邮箱
    LODOP.ADD_PRINT_TEXT(228, 167, 260, 12, data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    

    
    
    
        if(data[10].length>0){
    	for(var i=0;i<data[10].length;i++){
    		if(i==0){
    			 LODOP.ADD_PRINT_TEXT(433, 294, 100, 12, data[10][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(433, 514, 100, 12, data[10][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    		}else{
    			 LODOP.ADD_PRINT_TEXT(462+(i-1)*12, 294, 100, 12, data[10][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(462+(i-1)*12, 514, 100, 12, data[10][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    		}
    	}
    }
    
    
     //前期风险资金
    //LODOP.ADD_PRINT_TEXT(429, 302, 415, 12, data[10]);
    //LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
   //LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
      
    //中期风险基金
    //LODOP.ADD_PRINT_TEXT(454, 302, 415, 84, data[11]);
    //LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    //LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
}


function PreviewCar4(contractNo) {
    $.ajax({
        type: "POST",
        url: "report/car4",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: contractNo},
        dataType: 'json',
        success: function (data) {
            CreateFullBillCar4(data);
            LODOP.PREVIEW();
        }
    });
}


function CreateFullBillCar4(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    //合同编号
    LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
      //委托人
    LODOP.ADD_PRINT_TEXT(178, 179, 260, 12, data[1]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //身份中编号
    LODOP.ADD_PRINT_TEXT(198, 205, 220, 12, data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //委托人（财产共有人）
   // LODOP.ADD_PRINT_TEXT(219, 269, 156, 12, data[1]);
   // LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
   // LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

    //身份证编号
   // LODOP.ADD_PRINT_TEXT(237, 204, 220, 12, data[2]);
   // LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
   // LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
        //是否结婚
    if(parseInt(data[3])==0){
        LODOP.ADD_PRINT_TEXT(279, 270, 12, 12, "√");
    }else if(parseInt(data[3])==1){
        LODOP.ADD_PRINT_TEXT(279, 219, 12, 12, "√");
    }
    
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //汽车品牌
    LODOP.ADD_PRINT_TEXT(279, 418, 240, 12,  data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //车牌号
    LODOP.ADD_PRINT_TEXT(303, 150, 160, 12, data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //车架号
    LODOP.ADD_PRINT_TEXT(303, 433, 200, 12, data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
    //自然人出借人
    LODOP.ADD_PRINT_TEXT(346, 200, 90, 12, "戴卫新");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    
     //编号
    LODOP.ADD_PRINT_TEXT(346, 420, 180, 12, data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
}
////////////////////////////////////////////////wamgjl add end


function CurentTime(num)
{ 
	var preDate = new Date();
	var  now= new Date(preDate.getTime() +num*24*60*60*1000);
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   var hour= now.getHours(); //获取系统时，
   var mins=  now.getMinutes(); //分
   var sec=  now.getSeconds(); //秒
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
    
    if(hour < 10)
        clock += "0";
       
    clock += hour + ":";
    
    if(mins < 10)
        clock += "0";
       
    clock += mins + ":";
    
    if(sec < 10)
        clock += "0";
       
    clock += sec;
    

    return(clock); 
} 


function CurentTimeStand(num)
{ 
	var preDate = new Date();
	var  now= new Date(preDate.getTime() +num*24*60*60*1000);
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   var hour= "00"; //获取系统时，
   var mins=  "00"; //分
   var sec=   "00"; //秒
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
    
       
    clock += hour + ":";
    
       
    clock += mins + ":";
    
       
    clock += sec;
    

    return(clock); 
} 