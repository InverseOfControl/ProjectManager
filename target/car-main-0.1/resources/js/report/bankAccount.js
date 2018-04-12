$(function() {
 // 查询按钮
	/*$('#searchBtn').bind('click', searchPersonBank);	*/
	$("#bankAccount").datagrid({
		url : 'report/bankAccount/getBankAccountList',
		fitColumns : false,
        border : false,
        singleSelect:true,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        columns : [[
                    {field : 'id' ,title :'ID', width : 100},
                    {field : 'bankId' ,title : '银行ID' ,width : 100},
                    {field : 'account' ,title : '账户' , width : 100},
                    {field : 'status' ,title : '状态' , formatter:function(value){
						var str;
						if(value=="1"){
							str="启用";
						}
						if(value=="0"){
							str="禁用";
						}   		
							return str;
						}
					,width : 100},
                    {field : 'branchName' ,title : '银行名称' , width : 100},
                    {field : 'bankName' ,title : '总行名称' , width : 100},
                    
                    {field : 'creatorId' ,title : '创建用户ID' , width : 100},
                    {field : 'creator' ,title : '创建用户' , width : 100},
                    {field : 'modifierId' ,title : '更新用户ID' , width : 100},
                    {field : 'modifier' ,title : '更新用户' , width : 100},
                    {field : 'modifiedTime' ,title : '更新时间' , width : 100},
                    {field : 'version' ,title : '版本' , width : 100},
                    {field : 'cardType' ,title : '机构状态' ,formatter:function(value){
						var str;
						if(value=="1"){
							str="对私";
						}
						if(value=="2"){
							str="对公";
						}   		
							return str;
						}
					,width : 100},
					{field : 'accountName' ,title : '用户' , width : 100},
					{field : 'accountAuthType' ,title : '是否实名认证' ,formatter:function(value){
						var str;
						if(value=="0"){
							str="未认证";
						}
						if(value=="1"){
							str="认证";
						}   		
							return str;
						}
					,width : 100}
                   ]]
                 
	});
});

function searchBankAccount(){
	//查询数据
    var queryParams = $('#bankAccount').datagrid('options').queryParams;
	queryParams.id = $('#toolbar #id').val();
	setFirstPage("#bankAccount");
	$('#bankAccount').datagrid('options').queryParams = queryParams;		
	$("#bankAccount").datagrid('reload');	
}