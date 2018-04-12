$(function() {
	
    $('#sendStartDate').val(CurentTimeStand(-30));  
    $('#sendEndDate').val(CurentTime(0));  
    $('#buildStartDate').val(CurentTimeStand(-30));  
    $('#buildEndDate').val(CurentTime(0));  
 // 查询按钮
	$('#searchBtn').bind('click', searchOverdueMsg);	
	$("#overdueMsg").datagrid({
		url : 'report/overdueMsg/getOverdueMsgLogList',
		fitColumns : false,
        border : false,
        singleSelect:true,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        queryParams:{
        	sendStartDate : CurentTimeStand(-30),
        	sendEndDate : CurentTime(0),
        	buildStartDate : CurentTimeStand(-30),
        	buildEndDate : CurentTime(0)
        },
        columns : [[
                    {field : 'loanId' ,title :'借款ID', width : 50},
                    {field : 'repaymentId' ,title : '还款ID' ,width : 50},
                    {field : 'name' ,title : '姓名' , width : 100},
                    {field : 'idNum' ,title : '身份证' , width : 150},
                    {field : 'mobile' ,title : '手机号' , width : 100},
                    {field : 'templetId' ,title : '模板ID' , width : 120},
                    {field : 'repayDay' ,title : '还款日' , width : 200},
                    {field : 'curTime' ,title : '当前期数' , width : 80},
                    {field : 'productName' ,title : '借款产品' , width : 80,formatter:function(value){
						var str;
						if(value=="2"){
							str="车贷";
						}
						if(value=="4"){
							str="车贷新产品";
						}   		
							return str;
						}
					},
                    {field : 'productType' ,title : '产品类型' , width : 80,formatter:function(value){
						var str;
						if(value=="1"){
							str="移交类";
						}
						if(value=="2"){
							str="流通类";
						}   		
							return str;
						}
					},
                    {field : 'status' ,title : '发送状态' , width :80,formatter:function(value){
						var str;
						if(value=="1"){
							str="待发送";
						}else if(value=="3"){
							str="发送成功";
						}else{
							str="发送失败";
						}         		
							return str;
						}
					},
					 {field : 'sendDetails' ,title : '发送信息' , width : 300},
					 {field : 'buildDate' ,title : '生成时间' , width : 120},
					 {field : 'sendDate' ,title : '发送时间' , width : 120},
					 {field : 'returnDate' ,title : '结果返回时间' , width : 200}
                   ]]
                 
	});
});

function searchOverdueMsg(){
	//查询数据
    var queryParams = $('#overdueMsg').datagrid('options').queryParams;
	queryParams.buildStartDate = $('#toolbar #buildStartDate').val();
	queryParams.buildEndDate = $('#toolbar #buildEndDate').val();
	queryParams.sendStartDate = $('#toolbar #sendStartDate').val();
	queryParams.sendEndDate = $('#toolbar #sendEndDate').val();
	
	queryParams.name = $('#toolbar #name').val();
	
	queryParams.idNum = $('#toolbar #idNum').val();
	
	queryParams.status = $('#toolbar #status').combobox('getValue');
	
	 setFirstPage("#overdueMsg");
	$('#overdueMsg').datagrid('options').queryParams = queryParams;		
	$("#overdueMsg").datagrid('reload');	
}