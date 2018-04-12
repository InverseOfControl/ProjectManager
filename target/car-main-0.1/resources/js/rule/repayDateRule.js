$(function() {
	 $('#loanType').combobox({		 
		    onSelect: function () {		    	
	    	var loanType = $('#loanType').combobox('getValue');
	    	if(loanType==2){//车贷
	    		 $('#loanTypeChild').combobox({
	    			 disabled:false
	    		 });
	    		 $('#loanTypeChild').combobox('select',1);
	    	}else{
	    		$('#loanTypeChild').combobox('clear');
	    		 $('#loanTypeChild').combobox({
	    			 disabled:true
	    		 });
	    		}
		    }
	 });
	$('#loanTypeChild').combobox('clear');
	 // 新增
	 $('#loanType2').combobox({		 
		    onSelect: function () {			    	
	    	var loanType = $('#loanType2').combobox('getValue');
	    	if(loanType==2){//车贷
	    		 $('#productSubtype').combobox({
	    			 disabled:false
	    		 });
	    	}else{
	    		 $('#productSubtype').combobox({
	    			 disabled:true
	    		 });
	    		}
		    }
	 });
   //初始化列表
    $('#loanPageTb').datagrid({
        url : 'repayDateRule/getRepayDateRulePage',
        fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        fit:true,
        pageSize:10,
        rownumbers : true,
        columns : [[
                    {field : 'ck',checkbox : true},
                    {field : 'name',title : '规则名称',formatter:link},
                    {field : 'productType',title : '借款类型',formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_TYPE');
                    },width : 10},
                    {field : 'productSubtype',title : '子类型',formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
                    },width : 10},
                    {field : 'contractSrc',title : '合同来源',formatter: function(value, row, index){
                    	return  formatEnumName(value,'CONTRACT_SRC');
                    },width : 10},
                    {field : 'repaydateRule',title : '当前规则',formatter: function(value, row, index){
                    	return  formatEnumName(value,'REPAY_DATE_RULE');
                    },width : 10},
                    {field : 'modifiedTime',title : '上次修改时间',width : 10},
                    {field : 'creator',title : '操作人',width : 10},
                    {field : 'isExecuted',title : '是否生效',formatter: function(value, row, index){
                    	return  formatEnumName(value,'IS_EXECUTED');
                    },width : 10},
                    {field : 'executeTime',title : '设置时间',width : 10},
                    ]]
    });
    // 查询按钮
    $('#searchBt').bind('click', search);   
    //  新建按钮
    $('#newButton').bind('click', AddRule);  
    //  新建按钮
    $('#addCancel').bind('click', addCancel);
    //  新增规则
    $('#addRule').bind('click', submitRule);  
    //   编辑按钮
    $('#editButton').bind('click', openEditRule);   
    //  编辑
    $('#editRule').bind('click', editRule); 
    //  编辑取消
    $('#editCancel').bind('click', editCancel); 
    //  删除
    $('#removeButton').bind('click', removeRule);
    //  设置
    $('#setRuleButton').bind('click', configRule);
    
    $(document).keydown(function(e) {
    	if(e.which == 13) {
    		$('#searchBt').click();
    	}
    });
    
});
function link(value,row,index){
	return '<a  href="javascript:void(0)" onclick="linkEdit('+row.id  + ')">' + row.name+'</a>';
}
//查询按钮
function search(){
	  var queryParams = $('#loanPageTb').datagrid('options').queryParams;
	    if($('#toolbar #loanType').combobox('getValue')=="all"){
	    	 queryParams.productType =null;
	    }else{
		    queryParams.productType = $('#toolbar #loanType').combobox('getValue');
	    }
	    if($('#toolbar #loanType').combobox('getValue')==2){
	    	if($('#toolbar #loanTypeChild').combobox('getValue')=="all"){
	    		queryParams.productSubtype = null;
	    	}else{
		    	queryParams.productSubtype = $('#toolbar #loanTypeChild').combobox('getValue');
	    	}
	    }else{
	    	queryParams.productSubtype = null;
	    }		    
	    if($('#toolbar #contractSource').combobox('getValue')=="all"){
	    	 queryParams.contractSrc =null;
	    }else{
		    queryParams.contractSrc = $('#toolbar #contractSource').combobox('getValue');
	    }	    
	    if($('#toolbar #repaydateRule').combobox('getValue')=="all"){
	    	 queryParams.repaydateRule =null;
	    }else{
		    queryParams.repaydateRule = $('#toolbar #repaydateRule').combobox('getValue');
	    }
	    
	    $('#loanPageTb').datagrid('options').queryParams = queryParams;
	    $("#loanPageTb").datagrid('reload');
}
//新建
function AddRule(){

	 $('#addDLG').dialog().dialog('open').dialog('setTitle', '新增还款规则');
		clearAdd();
}
function addCancel(){
	 $('#addDLG').dialog('close');
}
//保存规则
function submitRule(){
	var productType = $("#loanType2").combobox('getValue');
	var productSubtype = $("#productSubtype").combobox('getValue');
	var contractSrc =$("#contractSrc").combobox('getValue');
	var repaydateRule = $("#repaydateRule2").combobox('getValue');
	var name=$("#name").val();
	if(productType==1){
		productSubtype=null;
	}	
    $.ajax({
        url : 'repayDateRule/addRepayDateRule',
        type:"POST",
        dataType: "json",
        async:false,
        data: {
        	productType:productType,
            productSubtype:productSubtype,
            contractSrc:contractSrc,
            repaydateRule:repaydateRule,
            name:name
        },
        success : function(result){
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '保存成功！'
                }); 
                $('#addDLG').dialog('close');
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
//点击当前规则打开编辑页面 
function linkEdit(id){
	var ruleId=id;
	$('#editDLG').dialog('open').dialog('setTitle','编辑还款规则');
	$.ajax({	        
        url: "repayDateRule/toEditRepayDateRule",
        type : "POST",
        dataType:"json",
        data: {	  
        	ruleId:ruleId	            
        },
        success:function(rule){
        	clearEdit();
        	loadEditRule(rule);
        },
        error:function(data){
	 		 $.messager.show({
					title: 'warning',
					msg: data.responseText
				});
		}
    });
}
//打开编辑页面
function openEditRule(id){
	var  ruleId;
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
		ruleId=row[0].id;
		
	}else{
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
	}
	
	$('#editDLG').dialog('open').dialog('setTitle','编辑还款规则');
	$.ajax({	        
        url: "repayDateRule/toEditRepayDateRule",
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
}

function loadEditRule(rule){
	$('#ruleId').val(rule.id);
	$('#productType').text(formatEnumName(rule.productType,'PRODUCT_TYPE'));
	$('#productName').val(rule.name);
	if(rule.productType==2){
		$('#childDisplay').text(formatEnumName(rule.productSubtype,'PRODUCT_SUB_TYPE'));
	}
	$('#editContractSource').text(formatEnumName(rule.contractSrc,'CONTRACT_SRC'));
	$('#dueDateRule').combobox('select',rule.repaydateRule);
}
function  editRule(){
	var id = $("#ruleId").val();
	var repaydateRule = $("#dueDateRule").combobox('getValue');
	var	name  =$("#productName").val();	
    $.ajax({
        url : 'repayDateRule/updateRepayDateRule',
        type:"POST",
        dataType: "json",
        async:false,
        data: {
        	id:id,
        	repaydateRule:repaydateRule,
        	name:name,        	
        },
        success : function(result){
            if(result=='success'){
                parent.$.messager.show({
                    title : '提示',
                    msg : '更新成功！'
                });       
                $('#editDLG').dialog('close');
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
//编辑-取消
function editCancel(){
	$('#editDLG').dialog('close');
}
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
		parent.$.messager.confirm('确认','确认要删除所选记录吗？', function(r) {
			if(r) {
				$.post('repayDateRule/deleteRepayDateRule',{idList:ids},function(result){
					 if (result=='success'){
						parent.$.messager.show({
							title : '提示',
							msg : '删除成功！'
						});
						$('#loanPageTb').datagrid('reload'); // reload the user data
					 } else {
						 parent.$.messager.show({ // show error message
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
//设置
function configRule(){
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
		var id=row[0].id;
		var isExecuted=row[0].isExecuted;	
		var name=row[0].name;
		var valid =null;
		if(isExecuted==0){
			valid='有效';
			isExecuted=1;
		}else{
			valid='无效';
			isExecuted=0;
		}
		parent.$.messager.confirm('确认','确认要当前规则设为'+valid+'?', function(r) {
			if(!r){
				return false;
			}
			$.ajax({	        
		        url: "repayDateRule/configRepayDateRule",
		        type : "POST",
		        dataType:"json",
		        data: {	  
		        	id:id,
		        	isExecuted:isExecuted,
		        	name:name
		        },
		        success:function(){		        	
		        	parent.$.messager.show({
						title : '提示',
						msg : '设置成功！'
					});
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
function clearAdd(){
	$('#name').val("");
	$('#loanType2').combobox('clear');
	$('#productSubtype').combobox('clear');
	$('#contractSrc').combobox('clear');
	$('#repaydateRule2').combobox('clear');
}
function clearEdit(){
	$('#productName').val("");
	$('#productType').text("");
	$('#childDisplay').text("");
	$('#editContractSource').text("");
	$('#dueDateRule').combobox('clear');
}



