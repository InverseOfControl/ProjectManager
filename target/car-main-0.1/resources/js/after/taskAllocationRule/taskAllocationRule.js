var url;
$(function () {
 
	//开启表单验证
    $('input.validateItem').validatebox({
        required:true
    });
    //开启表单验证
    $('select.validateItem').combo({
        required:true
        //multiple:true
    }); 
   
	// 查询按钮
	$('#searchBt').bind('click', search);
	
	 
 


	 // 列表
    $('#overduePageTb').datagrid({
        url: 'TaskAllocationRule/Main/taskAllocationRuleList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        fit:true,
        checkOnSelect:true,
        

        columns : [ [ 
                     { 
               			field : 'ck',
               			checkbox:'true'
                       },
                 	{
            			field : 'userName',
            			title : '姓名',
            			width :180,
            			formatter: function(value, row, index){
            				 
            				return value;
            	        	}
            		}, 
            		{
            			field : 'ruleId',
            			title : 'ruleId',
            			width :180,
            			hidden:true,
            			formatter: function(value, row, index){
            				 
            				return value;
            	        	}
            		}, 
            		{
            			field : 'id',
            			title : 'id',
            			width :180,
            			hidden:true,
            			formatter: function(value, row, index){
            				 
            				return value;
            	        	}
            		}, 
            		
            		{
            			field : 'userLoginName',
            			title : '登录名',
            			width :180,
            			formatter: function(value, row, index){
            				 
            				return value;
            	        	}
            		},   
           {
			field : 'salesDeptName',
			title : '营业部',
			width :180,
			formatter: function(value, row, index){
	          	return value;
	        	}
       		},
       	 {
    			field : 'status',
    			title : '状态',
    			width :180,
    			formatter: function(value, row, index){
    				if(value==1){
    					return "已启用";
    				}else if(value==0){
    					return "已分配未启用";
    				}else{
    					return "未分配";
    				}
    	           
    	        	}
    		} ,
       	 {
			field : 'num',
			title : '分配数',
			width :180,
			formatter: function(value, row, index){
	          	return value;
	        	}
		} ,
		   {
			field : 'cz',
			title : '操作',
			width : 180,
			formatter: function(value, row, index){
				var operation='<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="allocationRule('+row.id+ ', '+row.ruleId+' )" >' + "分配" + '</a>';
				if(row.status==1){
					operation=operation+"     "+'<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="distribution('+row.ruleId+ ', '+row.status+' )" >' + "停用" + '</a>';
				}else if(row.status==0)
					operation=operation+"     "+'<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="distribution('+row.ruleId+ ', '+row.status+' )" >' + "启用" + '</a>';
				
				return   operation; 
	        	}
        }
		  ] ]
	});
    // 设置分页控件
   var p = $('#overduePageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
    
	  
 	$('#dispatchBt').click(		function() {
 		var checkedItems = $('#overduePageTb').datagrid('getChecked');
 	    var names = [];
 	    console.info(checkedItems);
 	  
 	     $.each(checkedItems, function(index, item){
 	    	 	  if(item.ruleId ==null){
 	    	 		  names.push(item.id);
 	    	 	  }
 	    		
 	    });
 	    
 	    if(names.length<=0){
 	    	$.messager.alert("提示","请选择未分配的客服！");
	    	return false;
 	    } 
 	  
 	 
 	   
 	  $.ajax({
          type: 'GET',
          url: 'TaskAllocationRule/Main/batchRule/'+names,
          data: names,
          dataType: "json",
          success:function(data){
        	  
        	  $.messager.alert("操作提示", "分配规则完成！", "info", function () {  
        		  search();  
	             });
        		
     	     
          }
      });
 	   
 	  
 	});
  
   
	
	/* $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });*/
	 //$('#excelBt').linkbutton('disable');
	 
		
				

});
function search(){
	 
	 
	var queryParams = $('#overduePageTb').datagrid('options').queryParams;
	 
	queryParams.name=    $('#name').val();
	 
	setFirstPage("#overduePageTb");
	$('#overduePageTb').datagrid('options').queryParams = queryParams;
	$("#overduePageTb").datagrid('reload');
}




  
function allocationRule(userId,ruleId){
	if (typeof(ruleId) == "undefined") { 
		ruleId=0;
		}  
	$('#allocationRuleDlg').dialog({
		title: ' ',
		width: 400,
		height: 200,
		href : "TaskAllocationRule/Main/allocationRuleUI/"+userId+"/"+ruleId ,
		closed: false,
		cache: false,
		modal: true
	});
}

function distribution(ruleId,status){
	console.info(ruleId);
	$.ajax({
		type : 'post',
		url : 'TaskAllocationRule/Main/distribution/'+ruleId+"/"+status ,
		success : function(result) {
			location.reload(true); 
			 
		}
	});
 
}

function addAllocationRule(){
	
	if($('#num').val()==""){
	    $.messager.alert('提示','请填入分配数！');
	    return false;
	}
	$.ajax({
		type : 'post',
		url : 'TaskAllocationRule/Main/allocationRule',
		data : $('#allocationRuleTb').serialize(),
		async : false,
		success : function(result) {
		    
			cencalRefuseDlg();
			location.reload(true); 
			 
		}
	});
}
function  cencalRefuseDlg(){
	$('#allocationRuleDlg').dialog('close');	
}
