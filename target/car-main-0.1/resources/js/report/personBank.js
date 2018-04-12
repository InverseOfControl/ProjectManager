$(function() {
 // 查询按钮
	/*$('#searchBtn').bind('click', searchPersonBank);	*/
	$("#personBank").datagrid({
		url : 'report/personBank/getPersonBankList',
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
                    {field : 'personId' ,title : '客户ID' ,width : 100},
                    {field : 'bankAccountId' ,title : '银行账户ID' , width : 100},
                    {field : 'loanId' ,title : '借款ID' , width : 150},
                    {field : 'version' ,title : '版本' , width : 100}                                
                   ]]
                 
	});
});

function searchPersonBank(){
	//查询数据
    var queryParams = $('#personBank').datagrid('options').queryParams;
	queryParams.loanId = $('#toolbar #loanId').val();
	setFirstPage("#personBank");
	$('#personBank').datagrid('options').queryParams = queryParams;		
	$("#personBank").datagrid('reload');	
}