var url;
$(function () {
	$('#dispatchBt').click(	function() {
		if($('#optFlag').val()=="0"){
 			$.messager.alert("提示","无操作权限！");
	    		return false;
 		};
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
          url: 'CollectionManagerCases/Main/casesDispatch/'+names+"/"+collectionUser,
          data: names,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("操作提示", "分派完成！", "info", function () { 
        		    window.opener.document.getElementById('reason').value = '刷新';
					window.opener.document.getElementById('reason').value = '';
					 var div=	window.opener.document.getElementById('flshDiv');
					 div.focus();
	        		 location.reload(true) ;
	             });
          }
      });
 	   
 	  
 	});	
	$('#closedBt').click(		function() {
		if($('#optFlag').val()=="0"){
 			$.messager.alert("提示","无操作权限！");
	    		return false;
 		};
 	    var names = [];
 	    var flag=false;
 	    var closedType= $('#closedType').combobox('getValue');
 		var caseState = $('#caseState').val();
 	    var statue= $('#statue').val();
 	    	 if(closedType==6){
 	    	 if( caseState==2||caseState==5){
 	    		 if( statue==150||  statue==130){
 	    			 names.push( $('#id').val());
 	    		 }else{
 	    			flag=true;
 	 	    		$.messager.alert("提示","欠款尚未收回，不可做全部收回结案！");
 	 	    		return false;
 	    		 }
 	    		 
 	    	 }else{
 	    		 flag=true;
 	    		$.messager.alert("提示","案件状态不是“未分派”、“作业完成（全部收回）的案件！");
 	    		return false;
 	    	 };
 	    	 }else if(closedType==7){
 	    		if(caseState==4){
 	    			 names.push( $('#id').val());
 	 	    	 }else{
 	 	    		 flag=true;
 	 	    		$.messager.alert("提示","案件状态不是“作业完成（未全部收回）”的案件！");
 	 	    		return false;
 	 	    	 }; 
 	    	 }
 	   
 	     if(flag){return false;}
 	    if(closedType==6){ 
 	    	if(names.length<=0){
 	    		$.messager.alert("提示","案件状态不是“未分派”、“作业完成（全部收回）的案件！");
 	    		return false;
 	    	} 
 	    } else if(closedType==7){
 	    	if(names.length<=0){
 	    		$.messager.alert("提示","案件状态不是“作业完成（未全部收回）的案件！");
 	    		return false;
 	    	} 
 	    }
 	   
 	  $.ajax({
          type: 'GET',
          url: 'CollectionManagerCases/Main/casesClosed/'+names+"/"+closedType,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("提示","结案完成！");
        	  location.reload(true) ;
          }
      });
 	   
 	  
 	});
	  $('#saveBt').click(function() {
	 		  $.ajax({
	 	          type: 'post',
	 	          url: 'CollectionManagerCases/Main/updateCaseInfo',
	 	          data : $('#caseInfo').serialize(),
	 	          async : false,
	 	          success:function(data){
	 	        	 
	 	        	  $.messager.alert("操作提示", "保存成功！", "info", function () {  
	 	        		 location.reload(true) ;
	 	             });
	 	        	  
	 	          }
	 	      });
	 		
	 	}); 
	 $('#collectionUser').combobox({
	        url:'CollectionManagerCases/Main/getFirstTrials',
	        valueField:'id',
	        textField:'name',
	        onLoadSuccess:function(){
	            var data = $(this).combobox('getData');
	            if(data.length > 1)
	                 $(this).combobox('select', data[0].id);
	  
	        }
	  	  });
	
    $('#collectionTaskTb').datagrid({
        url: 'CollectionManagerCases/Main/collectionTaskList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
       
        queryParams: {
        	caseStateList : "1",
        	caseId :   $('#id').val()
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
              			field : 'caseId',
              			title : 'cId',
              			width : 180,
              			hidden:true,
              			formatter: function(value, row, index){
              	          	return value;
              	        	}
                      },
                      {
              			field : 'taskStartDate',
              			title : '作业开始时间',
              			width : 180,
              			formatter: formatRequestDate1
              		}, {
              			field : 'taskEndDate',
              			title : '作业完成时间',
              			width : 180,
              			formatter: formatRequestDate1
              		},
                      {
                			field : 'name',
                			title : '催收人员',
                			width : 180,
                			formatter: function(value, row, index){
                				return row.name;
                				 
                	        	}
                        }, 
                        {
                			field : 'taskType',
                			title : '催收类型',
                			width : 180,
                			formatter: function(value, row, index){
                				 if(row.taskType==1 ){
                					 return "客服催收";
                				 }else if(row.taskType==2){}
                				 	return "部门催收";
                	        	}
                        }, 
                     
                        {
                			field : 'taskState',
                			title : '作业结果',
                			width : 180,
                			formatter: function(value, row, index){
                				 if(row.taskState==1 ){
             					 	return "进行中";
             				 }else if(row.taskState==2){
             						return "正常移交 ";
             				 }else if(row.taskState==3){
             						return "异常移交";
             				 }else if(row.taskState==4){
             						return "完成_未全部收回";
             				 }else if(row.taskState==5){
             						return "完成_全部收回";
             				 } 
                	        	}
                        },  
                			{field : 'promisedRepayMent',
                			title : '客户承诺还款金额',
                			width : 180,
                			formatter: function(value, row, index){
                				if(value!=null){
               					 return  value.toFixed(2);//保留两位小数
               				}else{
               					return value;
               				}
                	        	}
                        },  {
                			field : 'factRepayment',
                			title : '客户实际还款金额',
                			width : 180,
                			formatter: function(value, row, index){
                				if(value!=null){
               					 return  value.toFixed(2);//保留两位小数
               				}else{
               					return value;
               				}
                	        	}
                        },	 
                        {
                			field : 'taskMemo',
                			title : '作业备注',
                			width : 180,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                        
                         {
                			field : 'cz',
                			title : '操作',
                			width : 180,
                			formatter: function(value, row, index){
                				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="searchTask('+row.id+ ', '+row.caseId+' )" >' + "查看" + '</a>';
                	        	}
                        }  
                        
         
		 
		] ]
	});
    //相关联系人table
    $('#personPageTb').datagrid({
        url: 'CollectionManagerCases/Main/collectionPersonList',
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
   var p = $('#collectionTaskTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
 	
});
function saveContactPerson(){
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
		url : 'CollectionManagerCases/Main/updatePersonContacter',
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
			url : 'CollectionManagerCases/Main/updatePersonContacter',
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
			url : 'CollectionManagerCases/Main/updatePersonContacter',
			data : $('#personTb').serialize(),
			async : false,
			success : function(result) {
				  $.messager.alert("操作提示", "修改成功！", "info", function () {  
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
		href : "CollectionManagerCases/Main/addContactPersonUI/"+personId,
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
          url: 'CollectionManagerCases/Main/addContactPerson',
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
	 	 var personSaveBt = document.getElementById("personSaveBt");
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
	if(part=="1"){
		
	}else if(part=="2"){
		
	}
	
}
function contactPerson1(){
	 var editBt = document.getElementById("editBt");
	// editBt.style.display = "none";  
	 $(editBt).hide();
	 	 var personSaveBt = document.getElementById("personSaveBt");
	 	//personSaveBt.style.display = "block";
	 	 $(personSaveBt).show();
		$('input,select',$('form[name="personTb"]')).attr('disabled',false);
		$('#relationShip').combobox('enable');
		$('#sex').combobox('enable');
	}
function searchTask(taskId,caseId){
	var loanId=$('#loanId').val();
	 window.open (rayUseUrl+"CollectionManagerCases/Main/casesRecordUI/"+taskId+"?loanId="+loanId,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
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

function formatRequestDate(value,row,index){
	 return getYMD(value);
}
function searchContactPerson(cid,personId,part,subordinate){

	$('#contactPersonDlg').dialog({
		title: '查看联系人',
		width: 900,
		height: 600,
		href : "CollectionManagerCases/Main/contactPersonUI/"+cid+"/"+part+"/"+subordinate+"?personId="+personId,
		closed: false,
		cache: false,
		modal: true
	});
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
function  cencalRefuse(){
	$('#contactPersonDlg').dialog('close');	
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
	if(additionalAddress==null || additionalAddress==""){
	 
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

 
 
