$(function() {
	
    //用户列表
    $('#userComb').combobox({
        url:'systemLog/findAllUser',
        valueField:'id',
        textField:'name'
       
    });
    //操作类型
    var optType = $('#optType').combobox({       
         valueField:'enumCode',
         textField:'enumValue'
        
     });
    //操作模块
    $('#optModule').combobox({
        url:'systemLog/findSysEnumerateListByType',
        valueField:'enumCode',
        textField:'enumValue',
        onSelect: function(record){
        	optType.combobox({
                disabled: false,
                url: 'systemLog/findOptionTypeListByOptionModule?parentIdStr='+record.id,
                valueField: 'enumCode',
                textField: 'enumValue'
            });
    	}

    });   
   //设置开始时间为七天前 0时0分0秒
    var date=new Date();
    var startDate=new Date((+date)-7*24*3600*1000);    
    var start = startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+ startDate.getDate()+"  00:00:00";
    $('#startTime').datetimebox('setText', start);  
    var endTime=new Date();        
    var end = endTime.getFullYear()+"-"+(endTime.getMonth()+1)+"-"+ endTime.getDate()+" "+endTime.getHours()+":"+endTime.getMinutes()+":"+endTime.getSeconds();
    $('#endTime').datetimebox('setText', end);  
  //显示系统日志列表
    $('#sysLogPageTb').datagrid({
        url : 'systemLog/getSysLogPage',
        fitColumns : true,
        border : false,
        singleSelect:true,
        pagination : true,
        striped: true,
        fit:true,
        pageSize:10,
        rownumbers : true,
        columns : [[           
            {field : 'creator',title : '用户名 ',width : 60},
            {field : 'optModule',title : '操作模块',formatter: function(value, row, index){
            	return  formatEnumName(value,'OPTION_MODULE');
            },width : 60},
            {field : 'optType',title : '操作类型',formatter: function(value, row, index){
            	return  formatEnumName(value,'OPTION_TYPE');
            },width : 60},
            {field : 'ipAddress',title : 'IP地址',width : 80},          
            {field : 'createdTime',title : '创建时间 ',width : 80},
            {field : 'remark',title : '备注',width : 280},
        ]]
    });
    // 搜索日志
    $('#searchBt').bind('click', search);
   
    $(document).keydown(function(e) {
    	if(e.which == 13) {
    		$('#searchBt').click();
    	}
    });
});

function search(){	
    var queryParams = $('#sysLogPageTb').datagrid('options').queryParams;
    var creatorId = $('#userComb').combobox('getValue');
    queryParams.creatorId = creatorId;
    queryParams.optType = $('#optType').combobox('getValue');
    queryParams.optModule = $('#optModule').combobox('getValue');
    queryParams.ipAddress = $('#ipAddress').val();
    //备注
    queryParams.remark = $('#remark').val();
    
    if($('#endTime').datetimebox('getText')==''){
    	var endTime=new Date();        
    	var end = endTime.getFullYear()+"-"+(endTime.getMonth()+1)+"-"+ endTime.getDate()+" "+endTime.getHours()+":"+endTime.getMinutes()+":"+endTime.getSeconds();
    	queryParams.endTime = end;
    }else{
    	  queryParams.endTime = $('#endTime').datetimebox('getText');
    }
    if($('#startTime').datetimebox('getText')==''){
    	 var date=new Date();
    	 var startDate=new Date((+date)-7*24*3600*1000);    
    	 var start = startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+ startDate.getDate()+"  00:00:00";
    	queryParams.startTime = start;
    }else{
    	  queryParams.startTime = $('#startTime').datetimebox('getText');
    }     
 
    $('#sysLogPageTb').datagrid('options').queryParams = queryParams;
    $("#sysLogPageTb").datagrid('reload');
};

