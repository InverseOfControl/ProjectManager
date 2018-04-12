$(function() {
	
	  //设置开始时间为1天前 
    $('#sendStartDate').val(CurentTimeStand(-30));  
    $('#sendEndDate').val(CurentTime(0));  
    
	// 查询按钮
	$('#searchBt').bind('click', search);
	
	
	$('#accountAuthLogResult').datagrid({
		url : 'apply/accountAuthLog/accountAuthLogList',
		fitColumns : true,
		border : false,
		pagination : true,
		pageSize : 20,
		striped : true,
		rownumbers : true,
		fit:true,
//		queryParams:{
//        	sendStartDate : CurentTimeStand(-30),
//        	sendEndDate : CurentTime(0)
//        },
		columns : [ [ 
		 {
			field : 'name',
			title : '姓名',
			width : 100
		},{
			field : 'cardNo',
			title : '卡号',
			width : 100
		},{
			field : 'operatorId',
			title : '操作员',
			width : 100
		},{
			field : 'bankId',
			title : '银行ID',
			width : 150
		}, {
			field : 'loanId',
			title : '借款ID',
			width : 100
		}, {
			field : 'sendMsg',
			title : '发送内容',
			width : 100
		}, {
			field : 'returnCode',
			title : '返回码',
			width : 50
		}, {
			field : 'returnMsg',
			title : '返回信息',
			width : 100
		}, {
			field : 'sendTime',
			title : '发送时间',
			width : 100
		}
		] ]
	});
	 // 设置分页控件
	   var p = $('#accountAuthLogResult').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
	    
		
		 $(document).keydown(function(e) {
		    	if(e.which == 13) {
		    		$('#searchBt').click();
		    	}
		    });
});


function search(){
	var queryParams = $('#accountAuthLogResult').datagrid('options').queryParams;
	
	if($('#toolbar #personName').val()==''){
		  queryParams.name=null;
	}else{
		queryParams.name=$('#toolbar #personName').val();
	}
	
	queryParams.sendStartDate = $('#toolbar #sendStartDate').val();
	queryParams.sendEndDate = $('#toolbar #sendEndDate').val();
	 setFirstPage("#accountAuthLogResult");
	$('#accountAuthLogResult').datagrid('options').queryParams = queryParams;
	$("#accountAuthLogResult").datagrid('reload');
}