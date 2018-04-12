var url;
$(function () {
	$('#dispatchBt').click(	function() {
 		var caseState = $('#caseState').val();
 	    var names = [];
 	    var flag=false;
 	    	 if(caseState==2||caseState==4){
 	    		  names.push( $('#id').val());
 	    	 }else{
 	    		 flag=true;
 	    		$.messager.alert("提示"," 案件状态为不是“未分派”、“作业完成（未全部收回）的案件不能分派！");
 	    		return false;
 	    	 };
 	       
 	   
 	     if(flag){return false;}
 	    if(names.length<=0){
 	   	$.messager.alert("提示"," 案件状态为不是“未分派”、“作业完成（未全部收回）的案件不能分派！");
	    	return false;
 	    } 
 	   var collectionUser= $('#collectionUser').combobox('getValue');
 	   if(collectionUser==null || collectionUser=="" ){
 		 	$.messager.alert("提示","请选择催收员！");
	    	return false;
 	   }
 	  $.ajax({
          type: 'GET',
          url: 'collectionTask/Main/casesDispatch/'+names+"/"+collectionUser,
          data: names,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("提示","分派完成！");
        	  location.reload(true) ;
          }
      });
 	   
 	  
 	});	
	$('#closedBt').click(		function() {
		if( $('#optFlag').val()=="0"){
			  $.messager.alert("提示","无操作权限！");
		  		 return false;
		  }
 	    var names = [];
 	    var flag=false;
 	    var closedType= $('#closedType').combobox('getValue');
 	    var statue= $('#statue').val();
 	    var tid= $('#tid').val();
 	    if(closedType==null || closedType==""){
 	    	$.messager.alert("提示","请选择催收结果！");
 	    	return false;
 	    }
 	 
 	    	 if(closedType=="6"){
 	    		 if( statue==150||  statue==130){
 	    			 names.push( $('#id').val());
 	    		 }else{
 	    			flag=true;
 	 	    		$.messager.alert("提示","尚存在逾期的还款，不可做全部收回结案！");
 	 	    		return false;
 	    		 }
 	    	 }else if(closedType=="2"){
 	    	 
 	    			 names.push( $('#id').val());
 	    	 }
 	   
 	  
 	    if(closedType=="5"){ 
 	    	 if( statue==150||  statue==130){
	    			 names.push( $('#id').val());
	    		 }else{
	    			flag=true;
	 	    		$.messager.alert("提示","尚存在逾期的还款，不可做全部收回结案！");
	 	    		return false;
	    		 }
 	     
 	    } else if(closedType=="4"){
 	    		names.push( $('#id').val());
 	    }
 	  if($('#taskState').val()=="1"){
 		 if( $('#userType').val()=="7"){
 			  flag=true;
 	 		  $.messager.alert("提示","催收主管不可操作！");
 	 		 return false;
 	 	   }
 	  }else{
 		 if( $('#userType').val()=="6"){
			  flag=true;
	 		  $.messager.alert("提示","客服不可操作！");
	 		 return false;
	 	   }
 	  }
 	  
 	 /* if( $('#taskState').val()!="1"){
 		  $.messager.alert("提示","案件状态为进行中才可操作！");
 		 return false;
 	   }
 	  */
 	  
 	 if(flag){return false;}
 	  $.ajax({
          type: 'GET',
          url: 'collectionTask/Main/casesClosed/'+names+"/"+closedType+"?tid="+tid,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("操作提示", "操作成功！", "info", function () {  
	        		 location.reload(true) ;
	             });
          }
      });
 	   
 	  
 	});
	  $('#saveBt').click(function() {
		  if( $('#optFlag').val()=="0"){
			  $.messager.alert("提示","无操作权限！");
		  		 return false;
		  }
		  	if(  $('#promisedRepayMent').val()==""){
		  		 $.messager.alert("提示","请输入客户承诺还款金额！");
		  		 return false;
		  	}if(  $('#factRepayment').val()==""){
		  		 $.messager.alert("提示","请输入客户实际还款金额！");
		  		 return false;
		  	}if(  $('#teskMemo').val()==""){
		  		 $.messager.alert("提示","请输入作业备注！");
		  		 return false;
		  	}
		  
	 		  $.ajax({
	 	          type: 'post',
	 	          url: 'collectionTask/Main/taskSave',
	 	          data : $('#taskTable').serialize(),
	 	          async : false,
	 	          success:function(data){
	 	        	 $.messager.alert("操作提示", "保存成功！", "info", function () {  
		        		 location.reload(true) ;
		             });  
	 	        	  
	 	          }
	 	      });
	 		
	 	}); 
	 $('#collectionUser').combobox({
	        url:'collectionTask/Main/getFirstTrials',
	        valueField:'id',
	        textField:'name',
	        onLoadSuccess:function(){
	            var data = $(this).combobox('getData');
	            if(data.length > 1)
	                 $(this).combobox('select', data[0].id);
	  
	        }
	  	  });
	
    
    $('#telPageTb').datagrid({
        url: 'collectionTask/Main/collectionRecordList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        nowrap:false,
        rownumbers : true,
      
        checkOnSelect:true,
        queryParams: {
        	caseId :  $('#id').val(),
        	//taskId :  $('#tid').val(),
        	recordType :  1
        	
    	},
        columns : [ [ 
 
                     {
             			field : 'id',
             			title : 'id',
             			width : 90,
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     }, 
                     
                      {
              			field : 'recordStartDate',
              			title : '开始时间',
              			width : 90,
              			formatter: formatRequestDate1
              		}, {
              			field : 'recordEndDate',
              			title : '结束时间',
              			width : 90,
              			formatter: formatRequestDate1
              		},
                      {
                			field : 'recordName',
                			title : '电催对象',
                			width : 90,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                        {
                			field : 'recordTel',
                			title : '拨打电话',
                			width : 90,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                     
                          
                			{field : 'recordContent',
                			title : '电催内容',
                			width : 400,
                			styler: function(value,row,index){
                				 
                					return 'table-layout:word-wrap:break-word;word-break:break-all';
                			},

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
                          }, 
                        {
                 			field : 'cz',
                 			title : '操作',
                 			width : 50,
                 			formatter: function(value, row, index){
                 				
                 				
                 				return  '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="toUpdateRecordUi('+row.id+ ','+1+'  )" >' + "修改" + '</a>' +"&nbsp"+ '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="deleteRecord('+row.id+ ')" >' + "删除" + '</a>' ;
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
        nowrap:false,
        rownumbers : true,
        checkOnSelect:true,
        queryParams: {
        	caseId :  $('#id').val(),
        	//taskId :  $('#tid').val(),
        	recordType :  2
        	
    	},
    	 
        columns : [ [ 
                      
                     {
              			field : 'id',
              			title : 'id',
              			width : 90,
              			hidden:true,
              			formatter: function(value, row, index){
              	          	return value;
              	        	}
                      }, 
                      
                       {
               			field : 'recordStartDate',
               			title : '开始时间',
               			width : 90,
               			formatter: formatRequestDate1
               		}, {
               			field : 'recordEndDate',
               			title : '结束时间',
               			width : 90,
               			formatter: formatRequestDate1
               		},
                       {
                 			field : 'recordName',
                 			title : '外访对象',
                 			width : 90,
                 			formatter: function(value, row, index){
                 	          	return value;
                 	        	}
                         }, 
                         {
                 			field : 'recordAddress',
                 			title : '外访地址',
                 			width : 90,
                 			styler: function(value,row,index){
                  				 
            					return 'table-layout:word-wrap:break-word;word-break:break-all';
            			},
                 			formatter: function(value, row, index){
                 	          	return value;
                 	        	}
                         }, 
                      
                           
                 			{field : 'recordContent',
                 			title : '外访内容',
                 			width : 400,
                 			styler: function(value,row,index){
               				 
            					return 'table-layout:word-wrap:break-word;word-break:break-all';
            			},
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
                 			width : 50,
                 			formatter: function(value, row, index){
                 				
                 				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="toUpdateRecordUi('+row.id+ ','+2+'  )" >' + "修改" + '</a>'+"&nbsp"+ '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="deleteRecord('+row.id+ ')" >' + "删除" + '</a>' ;
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
    
    //相关联系人table
    $('#personPageTb').datagrid({
        url: 'collectionTask/Main/collectionPersonList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        queryParams: {
        	personId : $('#personId').val(),
        	loanId : $('#loanId').val()
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
               			field : 'subordinate',
               			title : 'subordinate',
               			width : 180,
               			hidden:true,
               			formatter: function(value, row, index){
               	          	return value;
               	        	}
                       }, 
                     {
              			field : 'part',
              			title : '角色',
              			width : 180,
              			formatter: function(value, row, index){
              				if(value==1){
              					return "借款人";	
              				}else if(value==2){
              					return "联系人";
              				}
              			}
                      },
                      {
              			field : 'name',
              			title : '姓名',
              			width : 180,
              			formatter: function(value, row, index){
              	          	 
              			   return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="searchContactPerson('+row.id+ ','+row.personId+ ','+row.part+ ','+row.subordinate+ ')" >' + value + '</a>';
              	        	}
                      }, 
                      {
                			field : 'sex',
                			title : '性别',
                			width : 180,
                			formatter: function(value, row, index){
                				 if(value==0){
                  					return "女";	
                  				}else if(value==1){
                  					return "男";
                  				}
                	        	}
                        }, 
                        {
                  			field : 'relationShip',
                  			title : '本人关系',
                  			width : 180,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          }, 
                          {
                    			field : 'idnum',
                    			title : '身份证号',
                    			width : 180,
                    			formatter: function(value, row, index){
                    	          	return value;
                    	        	}
                            }
		 
		] ]
	});
    var p = $('#personPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10]
    });
    // 设置分页控件
  
    var caseState= $('#caseState').val();
 
    	var data;
 	if(caseState=="1"){
 		  data = [{ "value": "6", "text": "结案（全部收回）" }, { "value": "2", "text": "异常移交" }];
 	}else if(caseState=="3"){
 		  data = [{ "value": "5", "text": "作业完成（全部收回）" }, { "value": "4", "text": "作业完成（未全部收回）" }];
 	}else if(caseState=="4"){
 		  data = [{ "value": "5", "text": "作业完成（全部收回）" }, { "value": "4", "text": "作业完成（未全部收回）" }];
 	}
 
 	$('#closedType').combobox({
 		data: data
 	});

   
});
function toRecordUi( type){
	if( $('#optFlag').val()=="0"){
		  $.messager.alert("提示","无操作权限！");
	  		 return false;
	  }
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
function toUpdateRecordUi(rid, type,optype){
	 
	if( $('#optFlag').val()=="0"){
		  $.messager.alert("提示","无操作权限！");
	  		 return false;
	  }
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
function deleteRecord(id){
	if( $('#optFlag').val()=="0"){
		  $.messager.alert("提示","无操作权限！");
	  		 return false;
	  }
	$.ajax({
		type : 'post',
		url : 'collectionTask/Main/deleteRecord/'+id,
		async : false,
		success : function(result) {
			location.reload(true); 
			 
		}
	});
}
function saveContactPerson(){
	if( $('#optFlag').val()=="0"){
		  $.messager.alert("提示","无操作权限！");
	  		 return false;
	  }
	var name= $('#collectionContactName').val();
	if(name==null || name=="" ){
		 $.messager.alert("操作提示","请输入姓名!");
		 return false;
	}
var mobilePhone=$('#mobilePhone').val();
var homePhone=$('#homePhone').val();
var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
var regHome = /^((0\d{2,4})-)(\d{7,8})?$/;

if(mobilePhone==null || mobilePhone==""){
	
}else{
	if (!reg.test(mobilePhone)) {
		 $.messager.alert("操作提示","手机格式有误!");
		 return false;
	}
}
if(homePhone==null || homePhone==""){
	
}else{
	if (!regHome.test(homePhone)) {
		 $.messager.alert("操作提示","固定电话格式有误!");
		 return false;
	}
}
	var part=$('#part').val();
	if(part==2){
		$.ajax({
			type : 'post',
			url : 'collectionTask/Main/updatePersonContacter',
			data : $('#contactTb').serialize(),
			async : false,
			success : function(result) {
				 $.messager.alert("操作提示", "修改成功！", "info", function () {  
	       		  location.reload(true) ;
	            });
				 
			}
		});
		}else if(part==1){
			$.ajax({
				type : 'post',
				url : 'collectionTask/Main/updatePersonContacter',
				data : $('#personTb').serialize(),
				async : false,
				success : function(result) {
					  $.messager.alert("操作提示", "修改成功！", "info", function () {  
		        		  location.reload(true) ;
		             });
					 
				}
			});
		
	}
}
function saveContactPerson1(){
	if( $('#optFlag').val()=="0"){
		  $.messager.alert("提示","无操作权限！");
	  		 return false;
	  }
	var name= $('#collectionContactName').val();
	if(name==null || name=="" ){
		 $.messager.alert("操作提示","请输入姓名!");
		 return false;
	}
var mobilePhone=$('#mobilePhone').val();
var homePhone=$('#homePhone').val();
var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
var regHome = /^((0\d{2,4})-)(\d{7,8})?$/;

if(mobilePhone==null || mobilePhone==""){
	
}else{
	if (!reg.test(mobilePhone)) {
		 $.messager.alert("操作提示","手机格式有误!");
		 return false;
	}
}
if(homePhone==null || homePhone==""){
	
}else{
	if (!regHome.test(homePhone)) {
		 $.messager.alert("操作提示","固定电话格式有误!");
		 return false;
	}
}
	 
		 
			$.ajax({
				type : 'post',
				url : 'collectionTask/Main/updatePersonContacter',
				data : $('#personTb').serialize(),
				async : false,
				success : function(result) {
					  $.messager.alert("操作提示", "修改成功！", "info", function () {  
		        		  location.reload(true) ;
		             });
					 
				}
			});
		
	 
}
function saveRecord(){
	var recordId= $('#recordId').val();
	var type=null;
	if(recordId==null || recordId==""||typeof(recordId) =="undefined"){
		type=1;
	}
	$.ajax({
		type : 'post',
		url : 'collectionTask/Main/saveRecord?type='+type,
		data : $('#recordTb').serialize(),
		async : false,
		success : function(result) {
			  $.messager.alert("操作提示", "保存成功！", "info", function () {  
	        		 location.reload(true) ;
	             });                 
			 
			 
		}
	});
}
function toAddContracterUI(){
	var personId =$('#personId').val();
	$('#addContactPersonDlg').dialog({
		title: '新增联系人',
		width: 900,
		height: 600,
		href : "collectionTask/Main/addContactPersonUI/"+personId,
		closed: false,
		cache: false,
		modal: true
	});
}
function addContactPerson(){
	var name= $('#personContracterName').val();
	if(name==null || name=="" ){
		 $.messager.alert("操作提示","请输入姓名!");
		 return false;
	}
	var companyPhone=$('#companyPhone').val();
	var homePhone=$('#homePhone').val();
	var mobilePhone=$('#mobilePhone').val();
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var regHome = /^((0\d{2,4})-)(\d{7,8})?$/;
	if(mobilePhone==null || mobilePhone==""){
	}else{
		if (!(reg.test(mobilePhone))) {
			 $.messager.alert("操作提示","手机格式有误!");
			 return false;
		}
	}	if(homePhone==null || homePhone==""){
		 
	}else{
		if (!(regHome.test(homePhone))) {
			 $.messager.alert("操作提示","家庭电话格式有误!");
			 return false;
		}
	}if(companyPhone==null || companyPhone==""){
		 
	}else{
		if (!(regHome.test(companyPhone))) {
			 $.messager.alert("操作提示","公司电话格式有误!");
			 return false;
		}
	}
	  $.ajax({
          type: 'post',
          url: 'collectionTask/Main/addContactPerson',
          data : $('#collectionContracterFrom').serialize(),
          async : false,
          success:function(data){
        	  $.messager.alert("操作提示", "添加成功！", "info", function () {  
        		  location.reload(true) ;
             });
        	  
          }
      });
 
}
function contactPerson(){
	 var editBt = document.getElementById("editBt");
	// editBt.style.display = "none";  
	 $(editBt).hide();
	 	 var personSaveBt = document.getElementById("personSaveBt1");
	 	//personSaveBt.style.display = "block";
	 	 $(personSaveBt).show();
	var part=$('#part').val();
	 if(part==2){
		$('input,select',$('form[name="contactTb"]')).attr('disabled',false);
		$('#relationShip').combobox('enable');
		$('#sex').combobox('enable');
	}else if(part==1){
		$('input,select',$('form[name="personTb"]')).attr('disabled',false);
		$('#relationShip').combobox('enable');
		$('#sex').combobox('enable');
	}  

	//$('#contactTb').find('input').attr("readonly",false);
	 
/*	$('input,select',$('form[id="contactTb"]')).prop('readonly',false);
	var form =$('#contactTb');
	$(form).find("input").prop("readonly",'false');
	$(form).find("input").attr("readonly",false);
	$('#contactTb').find('input').attr("disabled",false);
	var mm= $('#name');
	var txtN = document.getElementById("contactTb").getElementsByTagName("input"); 
	alert(txtN.length);
	for(i=0;i<txtN.length;i++){ 
		if(txtN[i].type="text"){ 
				txtN[i].readOnly=false; 
			} 
		}*/
	
}
function contactPerson1(){
	 var editBt = document.getElementById("editBt");
	// editBt.style.display = "none";  
	 $(editBt).hide();
	 	 var personSaveBt = document.getElementById("personSaveBt1");
	 	//personSaveBt.style.display = "block";
	 	 $(personSaveBt).show();
		$('input,select',$('form[name="personTb"]')).attr('disabled',false);
		$('#relationShip').combobox('enable');
		$('#sex').combobox('enable');
	 
	}
function searchTask(taskId,caseId){
	 
	var personId=$('#personId').val();
	var loanId=$('#loanId').val();
	 
	 window.open (rayUseUrl+"collectionTask/Main/casesRecordUI/"+taskId+"?personId="+personId+"&loanId="+loanId,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
function formatRequestDate(value,row,index){
	 return getYMD(value);
}
function searchContactPerson(cid,personId,part,subordinate){
	$('#contactPersonDlg').dialog({
		title: '查看联系人',
		width: 900,
		height: 600,
		href : "collectionTask/Main/contactPersonUI/"+cid+"/"+part+"/"+subordinate+"?personId="+personId,
		closed: false,
		cache: false,
		modal: true
	});
}

 
function formatRequestDate1(value,row,index){	
	 return getYMD1(value);
}

function getYMD1(datetime){	

	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	} 
	 
	return datetime ;  
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
 
function  cencalRefuse(){
	$('#contactPersonDlg').dialog('close');	
}

	 
function  cencalRefuseDlg(){
	$('#recordDlg').dialog('close');	
}
function deleteAdditional(id){
	$.ajax({
		type : 'post',
		url : 'CollectionManagerCases/Main/deletePersonContacterAdditional/'+id,
		async : false,
		success : function(result) {
			  $("#personContacterTb").datagrid('reload');
    		  $('#additionalDlg').dialog('close');	
			 
		}
	});
}
function  addAdditional(){
$('#additionalDlg').dialog({
    title: '新增额外电话地址',
    width: 400,
    height: 300,
    closed: false,
    cache: false,
    modal: true
	});
}
function  additionalSaveClick(){
	var additionalTel=$('#additionalTel').val();
	var additionalAddress=$('#additionalAddress').val();
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var regHome = /^((0\d{2,4})-)(\d{7,8})?$/;
	 var pId=  $('#additionalId').val();
     $('#relationId').val(pId);
	if(additionalTel==null || additionalTel==""){
		 
	}else{
		if (!(reg.test(additionalTel)||regHome.test(additionalTel))) {
			 $.messager.alert("操作提示","额外电话有误!");
			 return false;
		}
	}
	if((additionalAddress==null || additionalAddress=="")&&(additionalTel==null || additionalTel=="")){
		 $.messager.alert("操作提示","请选填一项!");
		 return false;
	}
	  $.ajax({
	          type: 'post',
	          url: 'CollectionManagerCases/Main/saveAdditional',
	          data : $('#additionalFrom').serialize(),
	          async : false,
	          success:function(data){
	        	  $.messager.alert("操作提示", "保存成功！", "info", function () {  
	        		  $('#additionalTel').val(null);
	        		  $('#additionalAddress').val(null);
	        		  $("#personContacterTb").datagrid('reload');
	        		  $('#additionalDlg').dialog('close');	
	             });
	        	  
	          }
	      });
	}

//显示附件
function toImgShow(){
  var loanId=   $('#loanId').val();
	 window.open (rayUseUrl+"CollectionManagerCases/Main/seImageUploadView/"+loanId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
 
