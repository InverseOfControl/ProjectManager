/**
 * 特殊签单设置
 */
$(function() {
	 $('#loanType').combobox({
		    onSelect: function () {
	    	var loanType = $('#loanType').combobox('getValue');
	    	if(loanType==2){
	    		 $('#loanTypeChild').combobox({
	    			 disabled:false
	    		 });
	    	}else{
	    		 $('#loanTypeChild').combobox({
	    			 disabled:true
	    		 });
	    		}
		    }
	 });
	 
	 //根据日期，判断是否可进行新建、编辑、设置
//	 $.ajax({
//		 url : "signLoanRule/checkDate",
//		 success : function (result) {
//			 if(!result) {
//				 $('#newButton').linkbutton('disable');
//				 $('#editButton').linkbutton('disable');
//				 $('#setRuleButton').linkbutton('disable');
//			}
//		 }
//	 });
	 
	 //判断当前日期是否适合操作
	 var flag = false;
	 // 当前日期
	 var date = new Date();
	 // 当前日(1~31)
	 var day = date.getDate();
	 // 当月天数
	 var daysOfMonth = new Date(date.getFullYear(),date.getMonth()+1,0).getDate();
	 // 月中情况
	 if(parseInt(day) >=13 && parseInt(day) <= 15) {
		 flag = true;
	 }
	 // 月末情况
	 else if((parseInt(daysOfMonth) - parseInt(day)) < 3) {
		 flag = true;
	 }
	 
	 // 当前日期不适合操作
	 if(!flag) {
		 $('#newButton').linkbutton('disable');
		 $('#editButton').linkbutton('disable');
		 $('#setRuleButton').linkbutton('disable');
	 }

   //初始化列表
    $('#loanPageTb').datagrid({
        url : 'signLoanRule/getSignLoanRulePage',
        fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        columns : [[
                    {field : 'ck',checkbox : true},
                    {field : 'productType',title : '借款类型',formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_TYPE');
                    },width : 10 },
                    {field : 'productSubtype',title : '子类型',formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
                    },width : 10},
                    {field : 'createdTime',title : '新建时间',width : 10},
                    {field : 'modifiedTime',title : '修改时间',width : 10},
                    {field : 'modifier',title : '操作人',width : 10},
                    {field : 'overdueDate',title : '失效时间',width : 10},
                    {field : 'isExecuted',title : '是否生效',formatter: function(value, row, index){
                    	return  formatEnumName(value,'IS_EXECUTED');
                    },width : 10},
                    {field : 'executeTime',title : '设置时间',width : 10}
                    ]]
    });
    // 查询按钮
    $('#searchBt').bind('click', search);
    
    $(document).keydown(function(e) {
    	if(e.which == 13) {
    		$('#searchBt').click();
    	}
    });
});

function search () {
	 var queryParams = $('#loanPageTb').datagrid('options').queryParams;
	    //产品类型选择全部
	    if($('#toolbar #loanType').combobox('getValue') == 0) {
	    	queryParams.productType = null;
	    }else {
	    	queryParams.productType = $('#toolbar #loanType').combobox('getValue');
	    }
	    
	    // 车贷
	    if($('#toolbar #loanType').combobox('getValue')==2){
	    	// 全部(流通类和移交类)
	    	if($('#toolbar #loanTypeChild').combobox('getValue')==0){
	    		queryParams.productSubtype = null;
	    	}else{
		    	queryParams.productSubtype = $('#toolbar #loanTypeChild').combobox('getValue');
	    	}
	    }else{
	    	queryParams.productSubtype = null;
	    }
	    queryParams.overdueDateStartDate = $('#toolbar #overdueDateStartDate').datebox('getValue');
	    queryParams.overdueDateEndDate = $('#toolbar #overdueDateEndDate').datebox('getValue');
	    $('#loanPageTb').datagrid('options').queryParams = queryParams;
	    $("#loanPageTb").datagrid('reload');
}

//  弹出"特殊签单规则"之【新建】窗口
function addSignLoanRule () {
	$("#addDlg").dialog().dialog('open').dialog('setTitle', '新建规则');
	$("#loanType1").combobox({
			onSelect : function () {
				var loanType = $("#loanType1").combobox('getValue');
				if(loanType==2){
		    		 $('#loanTypeChild1').combobox({
		    			 disabled:false
		    		 });
		    	}else{
		    		 $('#loanTypeChild1').combobox({
		    			 disabled:true
		    		 });
		    	}
			}
	});
	$("#loanType1").combobox('select','1');
	$("#loanTypeChild1").combobox('select','1');
}

// 提交【新建】窗口表单数据
function submitAdd () {
	// 验证form
	if(!$("#newRuleForm").form('validate')) {
		return false;
	}
	
	$.ajax({
			url : "signLoanRule/addSignLoanRule" ,
			async : false ,
			type : "POST",
			data: $("#newRuleForm").serialize() ,
			success : function(result){	
			   		if(result=='success'){
			   			$.messager.show({
							title : '提示',
							msg : '保存成功！'
						});
			   			$('#addDlg').dialog('close');
			   			$('#loanPageTb').datagrid('reload');
			   		}else{
			   			$.messager.show({
							title: 'Error',
							msg: result
						});
			   		}
			   }
	});
}

// 关闭【新建】窗口
function cancelAdd () {
	$("#addDlg").dialog().dialog('close');
}

// 弹出"特殊签单规则"之【编辑】窗口
function editSignLoanRule () {
	var row = $('#loanPageTb').datagrid('getSelections');
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
		var ruleId=row[0].id;
		// 当前日期
		var day = new Date();
		// 将要编辑的规则的失效日期
		var overdueDateStr = row[0].overdueDate;
		var overdueDate = new Date(Date.parse(overdueDateStr.replace(/-/g,"/")));
		// 当前为月中
		if(13 <= day.getDate() && day.getDate() <= 15) {
			var overdueDate16Str = day.getFullYear() + "/" + (day.getMonth()+1) + "/" + "16 00:00:00";
			var overdueDate16 = new Date(Date.parse(overdueDate16Str));
			if(overdueDate > overdueDate16 || overdueDate < overdueDate16) {
				$.messager.show({
					title: '提示',
					msg: '只能编辑当月月中签单'
				});
				return false;
			}
		}
		//获取当月天数
		var dayOfMonth = new Date(day.getFullYear(), day.getMonth() + 1, 0).getDate();
		// 当前为月末
		if(dayOfMonth - day.getDate() <= 3) {
			var overdueDate1Str = day.getFullYear() + "/" +(day.getMonth() + 2) + "/" + "01 00:00:00";
			var overdueDate1 = new Date(Date.parse(overdueDate1Str));
			if(overdueDate > overdueDate1 || overdueDate < overdueDate1) {
				$.messager.show({
					title: '提示',
					msg: '只能编辑当月月末签单'
				});
				return false;
			}
		}
		$("#editDlg").dialog().dialog('open').dialog('setTitle', '编辑规则');

		$.ajax({	        
			url: "signLoanRule/toEditSignLoanRule",
			type : "POST",
			dataType:"json",
			data: {	  
				ruleId:ruleId	            
			},
			success:function(rule){
				loadEditRule(rule);
			},
			error:function(data){
				$.messager.show({
					title: 'warning',
					msg: data.responseText
				});
			}
		});

		$("#loanType2").combobox({
			onSelect : function () {
				var loanType = $("#loanType2").combobox('getValue');
				if(loanType==2){
		    		 $('#loanTypeChild2').combobox({
		    			 disabled:false
		    		 });
		    	}else{
		    		 $('#loanTypeChild2').combobox({
		    			 disabled:true
		    		 });
		    	}
			}
		});
	}else{
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
	}
}

// 填充编辑页面
function loadEditRule(rule){
	$('#ruleId').val(rule.id);
	$('#loanType2').combobox('select', rule.productType);
	if(rule.productType==2){
		$('#loanTypeChild2').combobox('select', rule.productSubtype);
	} else {
		$('#loanTypeChild2').combobox({
			 disabled:true
		 });
	}
	$('#overDueDate').val(rule.overdueDate);
	$('#ruleType').val(rule.ruleType);
	$('#isExecuted').val(rule.isExecuted);
}

// 提交编辑页面
function  submitEdit(){
	var id = $("#ruleId").val();
	var productType = $("#loanType2").combobox('getValue');
	var productSubtype = $("#loanTypeChild2").combobox('getValue');
	var overdueDate = $("#overDueDate").val();
	var ruleType = $("#ruleType").val();
	var isExecuted = $("#isExecuted").val();
	if(productType == 1) {
		productSubtype == null;
	}
    $.ajax({
        url : 'signLoanRule/editSignLoanRule',
        type:"POST",
        dataType: "json",
        async:false,
        data: {
        	id:id,
        	productType:productType,
        	productSubtype:productSubtype,
        	overdueDate:overdueDate,
        	ruleType:ruleType,
        	isExecuted:isExecuted
        },
        success : function(result){
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '更新成功！'
                });       
                $('#editDlg').dialog('close');
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

// 关闭【编辑】窗口
function cancelEdit () {
	$("#editDlg").dialog().dialog('close');
}

// 弹出特殊签单规则之【删除】窗口
function removeRule(){
	var rows = $('#loanPageTb').datagrid('getChecked');
	if (rows.length > 0) {
		var ids = "";
		$.each(rows,function(i,n){
			if(i != 0){
				ids += "," ;
			}
			ids += (n.id) ;
		});
		parent.$.messager.confirm('','确定删除本条规则？', function(r) {
			if(r) {
				$.post('signLoanRule/deleteSignLoanRule',{idList:ids},function(result){
					 if (result == 'success'){
						parent.$.messager.show({
							title : '提示',
							msg : '删除成功！'
						});
						$('#loanPageTb').datagrid('reload'); 
					 } else {
						 parent.$.messager.show({ 
							 title: 'Error',
							 msg: result.errorMsg
						 });
					 }	
				});
			}
		});
	}else{
		parent.$.messager.show({
			title : '提示',
			msg : '请勾选要删除的记录！'
		});
	};
}


//设置特殊签单规则
function setRule(){
	var row = $('#loanPageTb').datagrid('getSelections');
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
		// 当前日期
		var day = new Date();
		// 将要编辑的规则的失效日期
		var overdueDateStr = row[0].overdueDate;
		var overdueDate = new Date(Date.parse(overdueDateStr.replace(/-/g,"/")));
		// 当前为月中
		if(13 <= day.getDate() && day.getDate() <= 15) {
			var overdueDate16Str = day.getFullYear() + "/" + (day.getMonth()+1) + "/" + "16 00:00:00";
			var overdueDate16 = new Date(Date.parse(overdueDate16Str));
			if(overdueDate > overdueDate16 || overdueDate < overdueDate16) {
				$.messager.show({
					title: '提示',
					msg: '只能设置当月月中签单'
				});
				return false;
			}
		}
		//获取当月天数
		var dayOfMonth = new Date(day.getFullYear(), day.getMonth() + 1, 0).getDate();
		// 当前为月末
		if(dayOfMonth - day.getDate() <= 3) {
			var overdueDate1Str = day.getFullYear() + "/" +(day.getMonth() + 2) + "/" + "01 00:00:00";
			var overdueDate1 = new Date(Date.parse(overdueDate1Str));
			if(overdueDate > overdueDate1 || overdueDate < overdueDate1) {
				$.messager.show({
					title: '提示',
					msg: '只能设置当月月末签单'
				});
				return false;
			}
		}
		var id=row[0].id;
		var productType = row[0].productType;
		var productSubtype = row[0].productSubtype;
		var ruleType = row[0].ruleType;
		var overdueDate = row[0].overdueDate;
		var isExecuted=row[0].isExecuted;	
		var valid =null;
		if(isExecuted==0){
			valid='有效';
			isExecuted=1;
		}else{
			valid='无效';
			isExecuted=0;
		}		
		parent.$.messager.confirm('确认','确认将当期规则设为'+valid+'?', function(r) {
			if(!r){
				return false;
			}
			$.ajax({	        
		        url: "signLoanRule/setSignLoanRule",
		        type : "POST",
		        dataType:"json",
		        data: {	  
		        	id:id,
		        	productType:productType,
		        	productSubtype:productSubtype,
		        	ruleType:ruleType,
		        	overdueDate:overdueDate,
		        	isExecuted:isExecuted
		        },
		        success:function(result ){
		        	if(result=="success"){
		        		$.messager.show({
			        		title: '提示',
			        		msg: "成功设置为" + valid
			        	});
		        	}else{
		        		$.messager.show({
			        		title: '提示',
			        		msg: result
			        	});
		        	}
		        	
		        	$('#loanPageTb').datagrid('reload');
		        },
		        error:function(data){
			 		 $.messager.show({
							title: 'warning',
							msg: data.responseText
						});
				}
		    });
		});
	}else{
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
	}
}
