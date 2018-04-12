$(function() {
	
	  //设置开始时间为1天前 
    $('#sendStartDate').val(CurentTimeStand(-30));  
    $('#sendEndDate').val(CurentTime(0));  
    $('#buildStartDate').val(CurentTimeStand(-30));  
    $('#buildEndDate').val(CurentTime(0));  
    
	// 查询按钮
	$('#searchBt').bind('click', search);
	
	
	$('#repaymentMsgLog_result').datagrid({
		url : 'repaymentMsgLog/repaymentMsgLogList',
		fitColumns : true,
		border : false,
		pagination : true,
		fit:true,
		pageSize : 20,
		striped : true,
		rownumbers : true,
		queryParams:{
        	sendStartDate : CurentTimeStand(-30),
        	sendEndDate : CurentTime(0),
        	buildStartDate : CurentTimeStand(-30),
        	buildEndDate : CurentTime(0)
        },
		columns : [ [ 
		 {
			field : 'loanId',
			title : '借款ID',
			width : 100
		},{
			field : 'repaymentId',
			title : '还款ID',
			width : 100
		},{
			field : 'name',
			title : '姓名',
			width : 100
		},{
			field : 'idNum',
			title : '身份证',
			width : 150
		}, {
			field : 'mobile',
			title : '手机号',
			width : 100
		}, {
			field : 'templetId',
			title : '模板ID',
			width : 50
		}, {
			field : 'repayDay',
			title : '还款日',
			width : 100,
			formatter : function(value,row,index){
				return value.substr(0,10);
			}
		}, {
			field : 'curTime',
			title : '当期期数',
			width : 100
		}, {
			field : 'repayAmount',
			title : '当期还款金额',
			width : 100,
			formatter : function(value,row,index){
				return value.toFixed(2);
			}
		}, {
			field : 'productId',
			title : '借款类型',
			width : 100,
			formatter : function(value,row,index){
				return  formatEnumName(value,'PRODUCT_ID');
			}
		}, {
			field : 'productType',
			title : '产品类型',
			width : 100,
			formatter : function(value,row,index){
				return  formatEnumName(value,'PRODUCT_TYPE');
			}
		}, {
			field : 'sendtimes',
			title : '发送次数',
			width : 100
		}, {
			field : 'status',
			title : '发送状态',
			width : 100,
			formatter : function(value,row,index){
				return  formatEnumName(value,'SEND_STATUS');
			}
		},{
			field : 'sendDetails',
			title : '发送信息',
			width : 200
		}, 
		{
			field : 'msg',
			title : '返回信息',
			width : 200
		}, {
			field : 'buildDate',
			title : '流水数据生成时间',
			width : 200
		}, {
			field : 'sendDate',
			title : '发送时间',
			width : 200
		}, {
			field : 'returnDate',
			title : '结果返回时间',
			width : 200
		}
		] ]
	});
	 // 设置分页控件
	   var p = $('#repaymentMsgLog_result').datagrid('getPager');
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
	var queryParams = $('#repaymentMsgLog_result').datagrid('options').queryParams;
	
	if($('#statusValue').val()==''){
		  queryParams.status=null;
	}else{
		 queryParams.status = $('#statusValue').val();
	}
	if($('#toolbar #personName').val()==''){
		  queryParams.name=null;
	}else{
		queryParams.name=$('#toolbar #personName').val();
	}
	if($('#toolbar #personIdnum').val()==''){
		  queryParams.idNum=null;
	}else{
		queryParams.idNum=$('#toolbar #personIdnum').val();
	}
	
	queryParams.sendStartDate = $('#toolbar #sendStartDate').val();
	queryParams.sendEndDate = $('#toolbar #sendEndDate').val();
	queryParams.buildStartDate = $('#toolbar #buildStartDate').val();
	queryParams.buildEndDate = $('#toolbar #buildEndDate').val();
	 setFirstPage("#repaymentMsgLog_result");
	$('#repaymentMsgLog_result').datagrid('options').queryParams = queryParams;
	$("#repaymentMsgLog_result").datagrid('reload');
}