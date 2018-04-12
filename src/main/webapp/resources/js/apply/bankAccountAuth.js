/**
 * 银行卡验证
 */

$(function (){
	$("#bankAccountAuthPageTb").datagrid({
		url : 'apply/authBankAccount/getBankAccountAuthPage',
		fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        fit:true,
        columns : [[
                    {field : 'personName' ,title : '客户姓名' ,width : 50},
                    {field : 'personIdnum' ,title : '身份证号' , width : 100},
                    {field : 'bankName' ,title : '银行名称' , width : 100},
                    {field : 'branchName' ,title : '分行名称' , width : 100},
                    {field : 'account' ,title : '银行卡号' , width : 100},
                    {field : 'accountAuthType' ,title : '验证结果' , 
                    	formatter : function(value, row, index) {
                    		if(value == 1){
            				return '已验证';
                    		}else{
                    			return '未验证';	
                    		}
                    	},
                    	width : 50},
                    {field : 'productType' ,title : '借款类型' , 
                    	formatter : function(value, row, index) {
            				return formatEnumName(value, 'PRODUCT_TYPE');
            			},
            			width : 50},
            		
                    {field : 'pactMoney' ,title : '合同金额' , width : 50},
                    {field : 'loanStatus' ,title : '借款状态' , 
                    	formatter : function(value, row, index) {
            				return formatEnumName(value, 'LOAN_STATUS');
            			},
                    	width : 50},
                    {field : 'operation' ,title : '操作' , 
                    	formatter : function (value, rowData, rowIndex) {
                    		if(rowData.accountAuthType== 0){
                    		return "<a href='javascript:authBankAccount(" + rowData.id + ");'>认证</a>&nbsp;&nbsp;&nbsp;";
                    	}else{
                    			return '';
                    	}
                    	}, width : 50}
                    ]]
	});
	
	
	// 设置分页控件
    var p = $('#bankAccountAuthPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
    $('#searchBtn').bind('click', search);
    // 回车键搜索
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
});


function search() {
	var queryParams = $('#bankAccountAuthPageTb').datagrid('options').queryParams;
    queryParams.personName = $('#toolbar #personName').val();
    queryParams.personIdnum = $('#toolbar #personIdnum').val();
    setFirstPage("#bankAccountAuthPageTb");
    $('#bankAccountAuthPageTb').datagrid('options').queryParams = queryParams;
    $("#bankAccountAuthPageTb").datagrid('reload');
}

function authBankAccount(id) {
        $.messager.confirm('确认对话框', '您确认要更改吗？', function (r) {
            if (r) {
            	$.ajax({
            		type : 'post',
            		url : 'apply/authBankAccount/doAuthBankAccount',
            		data : {
            			id:id
            		},
            		async : false,
            		success : function(result) {
            			if (result.success=='true') {
            				parent.$.messager.show({
            					title : '提示',
            					msg : result.message
            				});
            				search();
            			} else {
            				parent.$.messager.show({
            					title : 'Error',
            					msg : result.message
            				});
            			}
            		}
            	});
            } else {
                return false;
            }
        });
}

