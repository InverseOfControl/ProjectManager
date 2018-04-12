var url;
$(function () {
	$('#reason1').combobox({     
	    valueField:'id',
	    textField:'reason',
	    onChange:function(reason1){ 
	    	$('#reason2').combobox({     
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
	
    $('#toolbar #productComb').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].productType);
            userProductType = data[0].productType;
        }
    });
    //设置开始时间为15天前 
    var date=new Date();
    var startDate=new Date((+date)-15*24*3600*1000);    
    var start = startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+ startDate.getDate();  
    $('#contractConfirmStartDate').val(start);    
	// 查询按钮
	$('#searchBt').bind('click', search);
	 // 列表
    $('#list_result').datagrid({
        url: 'finance/financialAudit/getFinacialAuditPage',
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
         {	field:'ck',
        	checkbox:true
        },
        {
			field : 'name',
			title : '姓名',
			formatter: function(value, row, index){
	          	return  row.person.name;
	        	}
        },
        {field : 'productId',title : '产品类型',formatter: function(value, row, index){
        	var val=formatEnumName(value,'PRODUCT_ID');
			return  '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.loanId+')">'+val+'</a>';
        	}
        }, 
		  {field : 'productType',title : '借款类型',formatter: function(value, row, index){
	          	return  formatEnumName(value,'PRODUCT_TYPE');
          }
		},
		{
			field : 'idnum',
			title : '身份证号',
			formatter: function(value, row, index){
	          	return  row.person.idnum;
	        	}
		}, {
			field : 'pactMoney',
			title : '合同金额',
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	          }
				
		}, {
			field : 'contractSrc',
			title : '合同来源',formatter: function(value, row, index){
	          	return  formatEnumName(value,'CONTRACT_SRC');
	          }
		}, {
			field : 'grantMoney',
			title : '放款金额',
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	        }
		}, {
			field : 'grantAccount.bankName',
			title : '放款银行',formatter: function(value, row, index){
				if(row.grantAccount!=null&&row.grantAccount.bankName!=null){
					 return   row.grantAccount.bankName;//保留两位小数
				}else{
					return '';
				}
        	}
			
		},{
			field : 'grantAccount.account',
			title : '放款账户',formatter: function(value, row, index){
				if(row.grantAccount!=null&&row.grantAccount.account!=null){
					 return   row.grantAccount.account;//保留两位小数
				}else{
					return '';
				}
        	}
			
		}, {
			field : 'contractConfirmDate',
			title : '合同确认日期'
		},  {
			field : 'status',
			title : '状态',
			formatter: function(value, row, index){
          	return  formatEnumName(value,'LOAN_STATUS');
        	}
		},{		
			field : 'id',
			title : '查看',
			formatter : function(value, rowData, rowIndex) {
				var link = "";
				link += "<a href='javascript:businessLogPage(\""+rowData.id+"\")' >日志</a>&nbsp;&nbsp;&nbsp;";
				link += "<a href='javascript:showAttachmentDlg(\""+rowData.id+"\")' >附件</a>&nbsp;&nbsp;&nbsp;";
				return link;
			}
		
		},{	
			field : 'loanId',
			title : '操作',
			formatter : function(value, row, rowIndex) {	
				var link = "";
				if(row.status==90){
					/*link += "<a href='javascript:financialAudit(\""+row.id+"\")' >审核确认</a>&nbsp;&nbsp;&nbsp;";	*/
					link += '<a  href="javascript:void(0)"  onclick="financialReturn('+row.productType + ',' +row.id  + ')">' + "退回"+ '</a>';
				}	
				return  link;
			},width : 160
		}] ]
	});
    // 设置分页控件
   var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
	
	 //批量放款按钮
	$("#batchMakeLoansBt").bind('click',batchMakeLoans);
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
//弹出财务审核退回窗口
function financialReturn(productType,loanId){
	 $("#dlg").find("#loanId").val(loanId);
	 $("#dlg").dialog("open").dialog('setTitle', '财务审核退回');
	 $('#reason1').combobox('clear');
	 $('#reason2').combobox('clear');
	 $('#reason1').combobox({     
		    url:'master/rejectReason/getRefuseFirstReason',
		    valueField:'id',
		    textField:'reason',
		    onBeforeLoad: function(param){                    		    	
					param.productType = productType;								
				}
		  
		});
}
//点击取消
function  cencalRefuse(){
	$('#dlg').dialog('close');	
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
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.personName = $('#personName').val();
	queryParams.personIdnum = $('#personIdnum').val();

	if($('#productComb').combobox('getValue')=="all"){
    	 queryParams.selectedProductId =null;
    }else{
	    queryParams.selectedProductId = $('#productComb').combobox('getValue');
    }

	 queryParams.productId = $('#productComb').combobox('getValue');
	if($('#loanStatus').combobox('getValue')=="all"){
		queryParams.status =0;
    }else{
	    queryParams.status = $('#loanStatus').combobox('getValue');
    }
	if($('#contractSrc').combobox('getValue')=="all"){
		queryParams.contractSrc = 0;
    }else{
	    queryParams.contractSrc = $('#contractSrc').combobox('getValue');
    }
	queryParams.contractConfirmStartDate = $('#contractConfirmStartDate').val();
	queryParams.contractConfirmEndDate = $('#contractConfirmEndDate').val();
	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}

function batchMakeLoans(){
	var rows = $('#list_result').datagrid('getChecked');
	//选择的行数
	var length=rows.length;
	var ids = "";
	if (length > 0) {		
		$.each(rows,function(i,n){
			if(i != 0){
				ids += "," ;
			}
			ids += (n.id);
		});
		parent.$.messager.confirm('确认','确认要批量审核所选中借款吗？', function(r) {
			if(!r){
				return false;
			}
			$.ajax({
			   url : 'finance/financialAudit/financialAuditLoans',
			   data : {
				   idList:ids
			   },
			   type:"POST",
				success : function(result) {
					if (result.success) {
						parent.$.messager.show({
							title : '提示',
							msg : '审核成功！'
						});
						$('#list_result').datagrid('reload');
						$('#list_result').datagrid('unselectAll');
					} else {
						parent.$.messager.show({
							title : '错误',
							msg : result.msg
						});
					}
				}
		});	
		});
	}else{
		parent.$.messager.show({
			title : '提示',
			msg : '请勾选要审核的贷款！'
		});
	};
	
}

