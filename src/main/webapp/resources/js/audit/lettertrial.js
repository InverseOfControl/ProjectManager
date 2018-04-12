/**
 * 信审分单
 */
$() .ready( function() {
	// 列表
    $('#bg').datagrid({
                url : 'letterTrial/loanList',
        		fitColumns : true,
        		border : false,
        		singleSelect : false,
        		pagination : true,
        		striped : true,
        		pageSize : 10,
        		rownumbers : true,
        		
        		checkOnSelect : true,
		columns : [ [{
			field : 'ck',
			checkbox : true,
			formatter : function(value, row, index) {
				return row.id;
			}
	    },{
			field : 'Name',
			title : '机构名称',
			formatter : function(value, row, index) {	
					return row.organ.name;
			},
			width : 100
				
		},   {
			field : 'personName',
			title : '姓名',
			formatter : function(value, row, index) {
				return row.person.name;
			},
			width : 100
		},
		{
			field : 'requestTime',
			title : '期限',
			width : 60
		},{
			field : 'productId',
			title : '产品类型',
			formatter : function(value, row, index) {
				return formatEnumName(value, 'PRODUCT_ID');
			},
			width : 80
			
		}, {
			field : 'requestMoney',
			title : '借款金额(元)',
			width : 80
		}, 
		{
			field : 'requestDate',
			title : '首次申请日期',
			formatter : formatRequestDate,
			width : 100
		},
	{
			field : 'status',
			title : '借款状态',
			formatter : function(value, row, index) {
				return formatEnumName(value, 'LOAN_STATUS');
			},
			width : 50
		},
		{
			field : 'salesDeptName',
			title : '营业部',
			formatter : function(value, row, index) {
				return row.salesDept.name;
			},
			width : 150
		},
		{
			field : 'firstTrialName',
			title : '审核员',
			formatter : function(value, row, index) {
				if( row.firstTrial == null ||  row.firstTrial.id == 0)
					return "未分配";
				else
					return row.firstTrial.name;
			},
			width : 80

		}] ]
	});
     
    //加载审核员
    $('#assessorNameComb').combobox({
        url:'letterTrial/getFirstTrials',
        valueField:'firstTrialId',
        textField:'firstTrialName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
        
                $(this).combobox('select', data[0].id);
        }
     }); 
    //加载审核员判断接单状态是否开启
    $.ajax({  
        type: "POST",  
        url: 'letterTrial/getFirstTrials2',
        cache: false,  
        dataType : "json",  
        success: function(data){  
        	$("#assessorNameComb2").combobox("loadData",data);  
        }  
     });    
    $('#assessorNameComb').combobox({   
    	editable:false, //不可编辑状态  
    	cache: false,  
    	panelHeight: '150',  
    	valueField:'id',     
    	textField:'name',
    	onHidePanel: function(){  
    		
    	}
    });
    $('#assessorNameComb2').combobox({   
    	editable:false, //不可编辑状态  
    	cache: false,  
    	panelHeight: '150',  
    	valueField:'id',     
    	textField:'name',  
    	onHidePanel: function(){  
    		
    	}
    });
    //弹窗默认为关闭状态
    $('#fenpai').window('close');
    
});     

////////////////////////////////////////////////////////////////////////////////////////

function  openDiv(){
	var td = document.getElementById('td1').childNodes.length;
	for(var i=0;i<td;i++){
		$('#div_').empty().remove();
	}
    //加载未处理的借款
    $.ajax({  
        type: "POST",  
        url: 'letterTrial/loanStatusList',
        cache: false,  
        dataType : "json",  
        success: function(data){  
        	if(data != null){
        		var td1 = document.getElementById('td1');
        	
        		for(var i=0;i<data.length;i++){
        			var div = document.createElement('div');
        			div.style.width = '200px';
        			div.style.float = 'left';
        			div.innerHTML = data[i].name+'&nbsp;&nbsp;&nbsp;&nbsp;'+data[i].settled+'/'+data[i].untreated+'';
        			div.id="div_";
        			td1.appendChild(div); 
        		}
        	}
        }  
     });  
	$('#p').panel('expand',true);
}


//查询按钮
function searchBg(){	
   var queryParams = $('#bg').datagrid('options').queryParams;
	repay=new Object();	
	queryParams.requestStartDate =  repay;
	queryParams.requestEndDate = repay;	
    queryParams.personName = $('#personNameTxt').val();
    queryParams.firstTrialId = $('#assessorNameComb').combobox('getValue');
    queryParams.status = $('#loanStatus').combobox('getValue');

	if ($('#requestStartDate').val() != '') {
		queryParams.requestStartDate = $('#requestStartDate').val();
	}
	if($('#requestEndDate').val()!=''){
		queryParams.requestEndDate = $('#requestEndDate').val();
	}
    setFirstPage("#bg");
    $('#bg').datagrid('options').queryParams = queryParams;
    $("#bg").datagrid('reload');

}

//显示分派窗口
function openWindow(){

	var rows=$('#bg').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.show({
			title : '提示',
			msg : '请选择需要分派的单子！'
		});
		return false;
	}
	$('#fenpai').window({
		modal:true
	});
}
//提交分派
function  save(){
	var assessorId = $('#assessorNameComb2').combobox('getValue');
	var rows=$('#bg').datagrid('getChecked');
	var datas=new Array();
	if(assessorId != ''){
		datas[0]=assessorId;
	}else{
		alert("请选审核人员！");
	}	
	for(var i=0;i<rows.length;i++){
		datas[i+1]=rows[i].id;	
	}
	$.ajax({
		url : 'letterTrial/reassign',
		type : "POST",
		data : {
			datas : datas
		},
		success : function(result) {
			$('#fenpai').window('close');
			if (result != '') {
				$.messager.show({
					title : '提示',
					msg : '分派成功！'
				});
				$('#bg').datagrid('reload');
			} else {
				$.messager.show({
					title : '提示',
					msg : '当前修改操作，不在允许时间范围内！'
				});
				$('#bg').datagrid('reload');
			}
		}
	})
}

                	
