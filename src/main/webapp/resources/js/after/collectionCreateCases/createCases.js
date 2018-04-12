var url;
$(function () {
	// 查询按钮
	$('#searchBt').bind('click', search);
	
    $('#casesPageTb').datagrid({
        url: 'CollectionCreateCases/Main/collectionList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        fit:true,
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
                  			field : 'personName',
                  			title : '借款人',
                  			width : 180,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          },     
         {
			field : 'idNum',
			title : '身份证号',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        }, 
		{
			field : 'time',
			title : '借款期限',
			width : 180,
			formatter: function(value, row, index){
				return value;
			
	          }
		}, {
			field : 'productType',
			title : '借款类型',
			width : 180,
			formatter: function(value, row, index){
					return formatEnumName(value,'PRODUCT_TYPE');
	          }
		}, {
			field : 'startRepayDate',
			width : 180,
			title : '首次还款日',	formatter: formatRequestDate
		}, {
			field : 'signDate',
			title : '签约日期',
			width : 180,
			formatter: formatRequestDate
		}, {
			field : 'status',
			width : 180,
			title : '借款状态',formatter: function(value, row, index){
				return   formatEnumName(value,'LOAN_STATUS');
        	}
		}   ,
		{	
			field : 'caseState',
			title : ' ',
			width : 180,
			formatter : function(value, row, rowIndex) {	
				var link = "";
				/* if(row.caseState==1 ){
					 	return "客服催收中";
				 }else if(row.caseState==2){
					 link = "未分派 ";
				 }else if(row.caseState==3){
					 link = "部门催收中";
				 }else if(row.caseState==4){
					 link= "作业完成_未全部收回";
				 }else if(row.caseState==5){
					 link = "作业完成_全部收回";
				 }else if(row.caseState==6){
					   link = "结案_全部收回";
				 }else if(row.caseState==7){
					 	link = "结案_坏账";
					 	
				  } */
				  if(row.status==140){
					  	link = "催收案件进行中";	
				  }
				  if(row.status==130){
					  	link = "<a href='javascript:addCasesDlg(\""+row.id+"\")' >生成催收案件</a>";	
				  }
				  if(row.status==160){
				  		link = "<a href='javascript:addCasesDlg(\""+row.id+"\")' >生成催收案件</a>";	
			  }
				return link; 	
			},width : 180
		}
		] ]
	});
    // 设置分页控件
   var p = $('#casesPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
 // 确定按钮
	$("#refuseSubmitBt").bind('click',submitRefuse);
	 // 导出按钮
	$("#exportExcelBt").bind('click',exportExcel);
	 //退回窗口点击取消
 
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	 
    
});
function saveCases(){
	$.ajax({
		type : 'post',
		url : 'CollectionCreateCases/Main/casesAdd',
		data : $('#addCasesForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				if(result.flag){
					parent.$.messager.alert('提示','提交成功！');
				}else{
					parent.$.messager.alert('提示','提交成功！');
				}
				
				$('#addCasesDlg').dialog('close');
				var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
				 queryParams.personName = $('#personName').val();
				 queryParams.idNum =$('#idNum').val() ;
			 
				 
				setFirstPage("#casesPageTb");
				 $("#casesPageTb").datagrid('options').queryParams = queryParams;
				 $("#casesPageTb").datagrid('reload'); 
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
	
}
function  cencalRefuse(){
	$('#addCasesDlg').dialog('close');	
}


function selectOnChange(obj,value){
	if(value==1){
		$('#memo').text("A:连续3天未能联系到客户本人，且家人无代偿意愿的； "); 
	}else if(value==2){
		$('#memo').text("B:连续跟进15天，客户无还款记录亦无承诺还款的；"); 
	}else if(value==3){
		$('#memo').text("C:客户连续3次违背付款承诺，未依约定日期、金额还款的；"); 
	}else if(value==4){
		$('#memo').text("D:客户发生骗贷、逃匿、死亡、入狱、被执行、冒办、盗办等情形的；"); 
	}else if(value==5){
		$('#memo').text("E:未逾期案件（风险案件）提前移交 "); 
	} else if(value==6){
		$('#memo').text("F:(保障金)保障金催收（助学贷逾期案件或其他涉及保障金垫付的案件）;"); 
	}  
};
function addCasesDlg(loanId){
	 
	/* $("#dlg").dialog("open").dialog('setTitle', '财务审核退回');*/
 
	 $('#addCasesDlg').dialog({
			title : '手动生成催收案件',
			closed : false,
			cache : false,
			href : "CollectionCreateCases/Main/searchLoan/"+loanId,
			modal : true,
	 });
	 
}
function formatRequestDate(value,row,index){
	 return getYMD(value);
}

function propertyChange(value){
	var queryParams = $('#approvePage').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName', parent.document).val();
	 queryParams.personIdnum =$('#personIdnum', parent.document).val() ;
	 queryParams.startDate = $('#startDate', parent.document).val()  ;
	 queryParams.endDate = $('#endDate', parent.document).val() ;

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
function openWindos(id){
	 window.open (rayUseUrl+"audit/eduLoan/eduCreditAuditDetailsLApprove/"+id,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
function openWindos2(id){
	 window.open (rayUseUrl+"audit/eduLoan/eduCreditAuditDetailsScan/"+id+"&2","newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
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
		pageSize: 100,
		striped: true,
		rownumbers: true,
		nowrap:false,	
		fit:true,
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
	var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
 
	 
	setFirstPage("#casesPageTb");
	 $("#casesPageTb").datagrid('options').queryParams = queryParams;
	 $("#casesPageTb").datagrid('reload'); 
}

