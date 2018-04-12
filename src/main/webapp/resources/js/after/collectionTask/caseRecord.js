var url;
$(function () {
	// 查询按钮
    $('#telPageTb').datagrid({
        url: 'collectionTask/Main/collectionRecordList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
       
        queryParams: {
        	//caseId :  $('#caseId').val(),
        	taskId :  $('#taskId').val(),
        	recordType :  1
        	
    	},
        columns : [ [ 
 
                     {
             			field : 'id',
             			title : 'id',
             			width : 180,
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     }, 
                     
                      {
              			field : 'recordStartDate',
              			title : '作业开始日期',
              			width : 180,
              			formatter: formatRequestDate
              		}, {
              			field : 'recordEndDate',
              			title : '作业完成日期',
              			width : 180,
              			formatter: formatRequestDate
              		},
                      {
                			field : 'recordName',
                			title : '电催对象',
                			width : 180,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                        {
                			field : 'recordTel',
                			title : '拨打电话',
                			width : 180,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                     
                          
                			{field : 'recordContent',
                			title : '电催内容',
                			width : 180,
                			formatter: function(value, row, index){
                				 
               					return value;
               				 
                	        	}
                        }, 
                        {
                  			field : 'optname',
                  			title : '操作人',
                  			width : 90,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          }, {
                 			field : 'cz',
                 			title : '操作',
                 			width : 180,
                 			formatter: function(value, row, index){
                 				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="toUpdateRecordUi('+row.id+ ','+1+'  )" >' + "修改" + '</a>';
                 	        	}
                         }   
                        
         
		 
		] ]
	});
    //相关联系人table
    $('#visitTb').datagrid({
        url: 'collectionTask/Main/collectionRecordList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
     
        queryParams: {
        	//caseId :  $('#caseId').val(),
        	taskId :  $('#taskId').val(),
        	recordType :  2
        	
    	},
    	 
        columns : [ [ 
                      
                     {
              			field : 'id',
              			title : 'id',
              			width : 180,
              			hidden:true,
              			formatter: function(value, row, index){
              	          	return value;
              	        	}
                      }, 
                      
                       {
               			field : 'recordStartDate',
               			title : '作业开始日期',
               			width : 180,
               			formatter: formatRequestDate
               		}, {
               			field : 'recordEndDate',
               			title : '作业完成日期',
               			width : 180,
               			formatter: formatRequestDate
               		},
                       {
                 			field : 'recordName',
                 			title : '外访对象',
                 			width : 180,
                 			formatter: function(value, row, index){
                 	          	return value;
                 	        	}
                         }, 
                         {
                 			field : 'recordAddress',
                 			title : '外访地址',
                 			width : 180,
                 			formatter: function(value, row, index){
                 	          	return value;
                 	        	}
                         }, 
                      
                           
                 			{field : 'recordContent',
                 			title : '外访内容',
                 			width : 180,
                 			formatter: function(value, row, index){
                 				 
                					return value;
                				 
                 	        	}
                         } , 
                         {
                   			field : 'optname',
                   			title : '操作人',
                   			width : 90,
                   			formatter: function(value, row, index){
                   	          	return value;
                   	        	}
                           }, 
                         {
                 			field : 'cz',
                 			title : '操作',
                 			width : 180,
                 			formatter: function(value, row, index){
                 				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="updateRecord('+row.id+ ','+2+'  )" >' + "修改" + '</a>';
                 	        	}
                         }  
                         
		 
		] ]
	});
    var p = $('#visitTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10]
    });
 
    
    // 设置分页控件
   var p = $('#telPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10 ]
    });
    
	 
 
 	
});
function toRecordUi( type){
	var personId=$('#personId').val();
	var loanId=$('#loanId').val();
	$('#recordDlg').dialog({
		title: '        ',
		width: 900,
		height: 600,
		href : "collectionTask/Main/addRecord?personId="+personId+"&loanId="+loanId+"&type="+type,
		closed: false,
		cache: false,
		modal: true
	});
}
function toUpdateRecordUi(rid, type){
 
	$('#recordDlg').dialog({
		title: '        ',
		width: 900,
		height: 600,
		href : "collectionTask/Main/updateRecordUI/"+rid+"?&type="+type,
		closed: false,
		cache: false,
		modal: true
	});
}
function saveRecord(){
	$.ajax({
		type : 'post',
		url : 'collectionTask/Main/saveRecord',
		data : $('#recordTb').serialize(),
		async : false,
		success : function(result) {
			$.messager.alert('提示','提交成功！');
			cencalRefuseDlg();
			  location.reload(true); 
			 
		}
	});
}

function formatRequestDate(value,row,index){
	 return getYMD(value);
}

  
 
 

function getYMD(datetime){
	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	return datetime.substr(0, 10);
}
 
//显示附件
 
//业务日志
function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '财务审核日志',
		width: 900,
		height: 300,
		closed: false,
		cache: false,
		modal: true
	});
	var url = 'audit/businessLog/detail.json/' + id;
	$('#business_log_result').datagrid({
		url: url,
		fitColumns: true,
		border: false,
		singleSelect:true,
		pagination: true,
		pageSize: 100,
		striped: true,
		rownumbers: true,
	 
		nowrap:false,	
		columns: [[
		           {field: 'operator', title: '操作者', width: 50},
		           {field: 'flowStatusView', title: '环节', width: 60},
		           {field: 'createDate', title: '操作时间', width: 100},
		           {field: 'message', title: '日志内容', width: 300}
		]]
    });
	 // 设置分页控件
	 var p = $('#business_log_result').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
}
 
 
function formatEnumName(value,type){
	   var enumJsondata = eval("("+enumJson+")");   
	   try{
			var typeList = enumJsondata.dicData[type];
			if(typeList){
				for(var i = 0; i < typeList.length; i++){
					if(value == typeList[i].enumCode){
						return typeList[i].enumValue;
					}
				}
				return "";
			}else{
				return "";
			}
		}catch(e){
			alert("不存在数据字典对象!");
		}
	}
function  cencalRefuseDlg(){
	$('#recordDlg').dialog('close');	
}
 
	 
 
 
