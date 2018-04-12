var url;
$(function () {
	// 查询按钮
	$('#searchBt', parent.document).bind('click', search);
	
	// 更改按钮
	$('#changeToFirstBt', parent.document).bind('click', changeToFirst);
	
    
    $('#approvePage').datagrid({
        url: 'approvePhone/approvePhoneData/listData',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        fit:true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        columns : [ [       
                     {
             			field : 'id',
             			title : 'id',
             			width : 180,
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     }, 
                     {
               			field : 'productId',
               			title : '产品ID',
               			width : 180,
               			hidden:true,
               			formatter: function(value, row, index){
               	          	return value;
               	        	}
                       }, 
                       {
                  			field : 'productType',
                  			title : '产品类型',
                  			width : 180,
                  			hidden:true,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          },     
         {
			field : 'areaName',
			title : '营业部',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        }, 
		  
		{
			field : 'personName',
			title : '借款人',
			width : 180,
			formatter: function(value, row, index){
	          //	return  value;
	          	return '<a style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="browse('+row.id+ ',' + row.productType + ', '+row.productId+' )" >' + value + '</a>';
	        	}
		},{
			field : 'productName',
			title : '产品类型',
			width : 180,
			formatter: function(value, row, index){
				return  '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.loanId+')">'+value+'</a>';
	          }
		}, {
			field : 'idNum',
			title : '身份证号',
			width : 180,
			formatter: function(value, row, index){
					return value;
	          }
		}, {
			field : 'requestMoney',
			width : 180,
			title : '申请金额',	formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	          }
		}, {
			field : 'auditMoney',
			title : '审核金额',
			width : 180,
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return row.requestMoney.toFixed(2);
				}
	          }
		}, {
			field : 'requestTime',
			width : 180,
			title : '借款期数',formatter: function(value, row, index){
					return value;
        	}
		},{
			field : 'requestDate',
			width : 180,
			title : '申请日期',formatter: formatRequestDate
		} ,{
			field : 'status',
			width : 180,
			title : '状态',formatter: function(value, row, index){
				return   formatEnumName(value,'LOAN_STATUS');
		}
		} ,
		{	
			field : 'option',
			title : '操作',
			width : 180,
			formatter : function(value, row, rowIndex) {	
				var link = "";
			
							link += "<a href='javascript:openWindos2(\""+row.id+"\")' >查看</a>";	
				 
				 	
				return  link;
			},width : 180
		}
		] ]
	});
    // 设置分页控件
   var p = $('#approvePage').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
 // 确定按钮
	$("#refuseSubmitBt").bind('click',submitRefuse);
	 // 导出按钮
	$("#exportExcelBt").bind('click',exportExcel);
	 //退回窗口点击取消
	$("#refuseCancelBt").bind('click',cencalRefuse);
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	 
    
});
function formatRequestDate(value,row,index){
	 return getYMD(value);
}

function propertyChange(value){
	var queryParams = $('#approvePage').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName', parent.document).val();
	 queryParams.personIdnum =$('#personIdnum', parent.document).val() ;
	 queryParams.startDate = $('#startDate', parent.document).val()  ;
	 queryParams.endDate = $('#endDate', parent.document).val() ;
	 if($('#auditStartDate', parent.document).val() != '' && $('#auditStartDate', parent.document).val() != null){
		 queryParams.auditDateStartEdu = $('#auditStartDate', parent.document).val()  ;
	 }else{
		 queryParams.auditDateStartEdu = null;
	 }
	 if($('#auditEndDate', parent.document).val() != '' && $('#auditEndDate', parent.document).val() != null){
		 queryParams.auditDateEndEdu = $('#auditEndDate', parent.document).val() ;
	 }else{
		 queryParams.auditDateEndEdu = null;
	 }
	if($('#stateValue', parent.document).val()==""){
  	  
  		  queryParams.status=null;
  		 queryParams.searchFlag=null;
  }else if( $('#stateValue', parent.document).val()=="all" ){
	  queryParams.status=0;
	  queryParams.searchFlag="true";
  }else{
	    queryParams.status = $('#stateValue', parent.document).val();
	    queryParams.searchFlag="true";
	    
  }
	 

	setFirstPage("#approvePage");
	 $("#approvePage").datagrid('options').queryParams = queryParams;
	 $("#approvePage").datagrid('reload'); 
};
function inputChange(value){
	var queryParams = $('#approvePage').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName', parent.document).val();
	 queryParams.personIdnum =$('#personIdnum', parent.document).val() ;
	 queryParams.startDate = $('#startDate', parent.document).val()  ;
	 queryParams.endDate = $('#endDate', parent.document).val() ;
	 if($('#auditStartDate', parent.document).val() != '' && $('#auditStartDate', parent.document).val() != null){
		 queryParams.auditDateStartEdu = $('#auditStartDate', parent.document).val()  ;
	 }else{
		 queryParams.auditDateStartEdu = null;
	 }
	 if($('#auditEndDate', parent.document).val() != '' && $('#auditEndDate', parent.document).val() != null){
		 queryParams.auditDateEndEdu = $('#auditEndDate', parent.document).val() ;
	 }else{
		 queryParams.auditDateEndEdu = null;
	 }
	if($('#stateValue', parent.document).val()==""){
  	  
  		  queryParams.status=null;
  		 queryParams.searchFlag=null;
  }else if( $('#stateValue', parent.document).val()=="all" ){
	  queryParams.status=0;
	  queryParams.searchFlag="true";
  }else{
	    queryParams.status = $('#stateValue', parent.document).val();
	    queryParams.searchFlag="true";
	    
  }
	 

	setFirstPage("#approvePage");
	 $("#approvePage").datagrid('options').queryParams = queryParams;
	 $("#approvePage").datagrid('reload'); 
};
function openWindos2(id){
	 window.open (rayUseUrl+"/approvePhone/approvePhoneData/approvePhoneDetailsScan/"+id+"&2","newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}

function getYMD(datetime){
	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	return datetime.substr(0, 10);
}
function browse(loanId,productType, productId){
	
    if(productType == 1){
       if(5 ==  productId ||  productId == 6){
        //seLoanCityWideLoanDetail;
          doSeLoan(loanId,'apply/seLoanCityWideLoanDetail',loadExistedCityWideLoan);
        }else{
          doSeLoan(loanId);
        }
    }else if(productType == 2){
    	doCarLoan(loanId);
    }
};
//显示附件
function showAttachmentDlg(loanId){
	 window.open (rayUseUrl+"finance/financialAudit/imageUploadView/"+loanId,"newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
//业务日志
function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '财务审核日志',
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

// 财务审核
function financialAudit(loanId){
	parent.$.messager.confirm('确认','审核确认', function(r) {
		if(r) {
			$.post('finance/financialAudit/financialAudit',{loanId:loanId},function(result){
				 if (result=='success'){
					parent.$.messager.show({
						title : '提示',
						msg : '审核确认成功！'
					});
					$('#list_result').datagrid('reload'); // reload the user data
				 } else {
					 parent.$.messager.show({ // show error message
						 title: 'Error',
						 msg: result.errorMsg
					 });
				 }	
			});
		}
	});
}
function clearRefuse(){
	 $("#dlg").find("#reason").val('');
	 $("#dlg").find("#reason1").combobox('clear');
	 $("#dlg").find("#reason2").combobox('clear');
}
function formatEnumName(value,type){
	   var enumJsondata = eval("("+enumJson+")");   
	   try{
			var typeList = enumJsondata.dicData[type];
			if(typeList){
				for(var i = 0; i < typeList.length; i++){
					if(value == typeList[i].enumCode){
						return typeList[i].enumValue;
					}
				}
				return "";
			}else{
				return "";
			}
		}catch(e){
			alert("不存在数据字典对象!");
		}
	}
function replyPlan(id){
	 
	/* $("#dlg").dialog("open").dialog('setTitle', '财务审核退回');*/
 
	 $('#seReplyEditDlg').dialog({
			title : '批复方案',
			closed : false,
			cache : false,
			href : "reply/replyData/seReplyEdit/"+id,
			modal : true,
	 });
	 
}
/** 编辑产品保存**/
function replyEditSave() {
 
	var checkStatus=$('input[name="approverState"]:checked ').val();
	var sendBackMemo=$('#sendBackMemo').val();
	if(checkStatus==4){
	  if(sendBackMemo==null || sendBackMemo==''){
		  $.messager.show({
				title: '提示',
				msg: '请输入备注信息！'
			});
		   return false;
	  }
	}

	$.messager.confirm('确认对话框', '确定要提交吗？', function(r) {
		if (r) {
			doSaveEditReply();
		} else {
			return false;
		}
	});
}


function doSaveEditReply() {
//	alert( $('#productSeEditForm').serialize());
	$.ajax({
		type : 'post',
		url : 'reply/replyData/replyEditSave',
		data : $('#replySeEditForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '提交成功！'
				});
				$('#seReplyEditDlg').dialog('close');
				var queryParams = $('#checkPageTb').datagrid('options').queryParams;
				queryParams.name = $('#name').val();
				queryParams.orgName = $('#orgName').val();
				queryParams.orgNum = $('#orgNum').val();
				if($('#approverState').combobox('getValue')=="all"){
			    	 queryParams.approverState =null;
			    }else{
				    queryParams.approverState = $('#approverState').combobox('getValue');
			    }
				setFirstPage("#checkPageTb");
				$('#checkPageTb').datagrid('options').queryParams = queryParams;
				$("#checkPageTb").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}
//点击取消
function  cencalRefuse(){
	$('#seReplyEditDlg').dialog('close');	
}
function getApproverStateVale(){
}
//财务退回提交
function submitRefuse() {
	// 验证FORM	
	 if(!$("#refuseReasonForm").form('validate')){
	        return false;
	    }
	 if($("#reason").val()==''){
		 	$.messager.show({
				title: '提示',
				msg: '请输入备注信息！'
			});
		   return false;
	 }
	 $("#reason1").combobox('setValue',$("#reason1").combobox('getText'));
	 $("#reason2").combobox('setValue',$("#reason2").combobox('getText'));	
	 parent.$.messager.confirm('确定','确认退回？', function(r) {
		 if(r) {	
		    $.ajax({
		   url : 'finance/financialAudit/financialReturn',
		   data : $("#refuseReasonForm").serialize(),
		   type:"POST",
		   success : function(result){
		   		if(result=='success'){
		   			$.messager.show({
						title : '提示',
						msg : '保存成功！'
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
		 }
	 });
}


function search(){
	var queryParams = $('#approvePage').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName', parent.document).val();
	 queryParams.personIdnum =$('#personIdnum', parent.document).val() ;
	 queryParams.startDate = $('#startDate', parent.document).val()  ;
	 queryParams.endDate = $('#endDate', parent.document).val() ;
	 if($('#auditStartDate', parent.document).val() != '' && $('#auditStartDate', parent.document).val() != null){
		 queryParams.auditDateStartEdu = $('#auditStartDate', parent.document).val()  ;
	 }else{
		 queryParams.auditDateStartEdu = null;
	 }
	 if($('#auditEndDate', parent.document).val() != '' && $('#auditEndDate', parent.document).val() != null){
		 queryParams.auditDateEndEdu = $('#auditEndDate', parent.document).val() ;
	 }else{
		 queryParams.auditDateEndEdu = null;
	 }
	if($('#stateValue', parent.document).val()==""){
		  queryParams.status=null;
	      queryParams.searchFlag=null;
   		
   }else if( $('#stateValue', parent.document).val()=="all" ){
		  queryParams.status=0;
		  queryParams.searchFlag="true";
	  }else{
	    queryParams.status = $('#stateValue', parent.document).val();
	    queryParams.searchFlag="true";
	    
   }
	setFirstPage("#approvePage");
	 $("#approvePage").datagrid('options').queryParams = queryParams;
	 $("#approvePage").datagrid('reload'); 
}

function changeToFirst(){
	var  loanChangeId;
	var row = $('#approvePage').datagrid('getSelections');
	if (row) {	
		if(row.length>1){		
			parent.$.messager.show({
				title: '提示',
				msg: "只能选择一行数据"
			});
			return false;
		}
		if(row.length==0){
			parent.$.messager.show({
				title: '提示',
				msg: "请选择一行数据"
			});
			return false;
		}
		if(row[0].status!=200&&row[0].status!=34&&row[0].status!=35&&row[0].status!=53&&row[0].status!=40){
			parent.$.messager.show({
				title: '提示',
				msg: "请选择系统自动取消状态或拒绝的数据"
			});
			return false;
		}else{
		    $.ajax({
				   url : 'approvePhone/approvePhoneData/approvePhoneCheck',
				   data : {
					   loanId: row[0].loanId
				   }
					,
				   type:"POST",
				   success : function(result){
				   		if(result.success){
				   			if(result.businessLog.flowStatus==200||result.businessLog.flowStatus==34||result.businessLog.flowStatus==35){
					   			loanChangeId=row[0].loanId;
					   			$('#loanIdChange').val(loanChangeId);
					   			changeToFirstDlg();
				   			}else{
				   				parent.$.messager.show({
									title: '提示',
									msg: "请选择系统自动取消状态或拒绝的数据"
								});
								return false;
				   			}
				   		}else{
							parent.$.messager.show({
								title: '提示',
								msg: "请选择系统自动取消状态或拒绝的数据"
							});
							return false;
				   		}
				   }
			 });
		}
	}else{
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
	}
}




