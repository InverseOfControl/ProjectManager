var url;
$(function () {

	getTextResource([10]);
	$('#searchBt').linkbutton({    
		text: '查询'   
	});  
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
        url: 'TextResource/main/searchPage',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        fit:true,
        queryParams: {
        	
    	},
        columns : [ [
                     {
             			field : 'text',
             			title : '文字',
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                    		}, 
                    		{
                    			field : 'code',
                    			title : '唯一标识符',formatter: function(value, row, index){
                    				return value;}
                    		}, {
                    			field : 'type',
                    			title : '类型',formatter: function(value, row, index){
                    						 
                    				  return formatEnumName(value, 'TEXT_RESOURCES_TYPE');}
                    		},
                    		{
                    			field : 'status',
                    			title : '状态',formatter: function(value, row, index){
                    				if(value==1){
                    					return "可用";
                    				}else if(value==0){
                    					return "不可用";
                    				}
                    				}
                    		
                    		},
                    		{
                    			field : 'language',
                    			title : '语言',formatter: function(value, row, index){
                    				
                    				
                    				return formatEnumName(value, 'TEXT_RESOURCES_LANGUAGE');}
                    		},
                    		{
                    			field : 'creator',
                    			title : '操作',formatter: function(value, row, index){
                    				//('+row.id +','+  row.productType+','+  row.extensionTime  +')
                    				return  '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="refreshCache('+row.type+')">刷新</a>'
                    				+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="editData('+row.id +')">修改</a>';
                    				 ;}
                    		}
			
		  ] ]	});
    // 设置分页控件
   var p = $('#overduePageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
  
   
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	 
  
	

});
 
 

function bformatRequestDate(value,row,index){
	 return bgetYMD(value);
}

function bgetYMD(datetime){
	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	 var date=datetime.substr(0, 10);
 
	return 	date.replace(/-/g,'/');
}
 
  
 
function search(){
	var queryParams = $('#overduePageTb').datagrid('options').queryParams;
	 
	queryParams.text=    $('#text').val();
	 queryParams.type =$('#type').combobox('getValue') ;
	setFirstPage("#overduePageTb");
	$('#overduePageTb').datagrid('options').queryParams = queryParams;
	$("#overduePageTb").datagrid('reload');
}
/**
 * 新增函数操作
 */
function addData(){
	//将id置为空
	$("#resourceId").val("");
	 //清除掉所有选中的行
	$('#dlg_add').dialog('open').dialog('setTitle', '新建文字资源');
	//清空原来输入的数据
	$("#resourceText").val("");
	$("#resourceType").val("");
	// 文字类型下拉框
  $('#dlg_add #resourceTypeComb').combobox({
        url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_TYPE',
        width:185,
        height:30,
        editable: false,
        valueField:'enumCode',
        textField:'enumValue',
        onLoadSuccess:function(){
        	var data = $(this).combobox('getData');
        	if(data.length>0)
        		$(this).combobox('select', data[0].enumCode);
        }
     
    });
  //文字状态下拉框（是否可用）
  $('#dlg_add #resourceStateCom').combobox({
      url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_ISAVALIABILITY',
      width:185,
      height:30,
      editable: false,
      valueField:'enumCode',
      textField:'enumValue',
      onLoadSuccess:function(){
      	var data = $(this).combobox('getData');
      	if(data.length>0)
      		$(this).combobox('select', data[0].enumCode);
      }
   
  });
  //文字语言下拉框
  $('#dlg_add #resourceLanguage').combobox({
      url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_LANGUAGE',
      width:185,
      height:30,
      editable: false,
      valueField:'enumCode',
      textField:'enumValue',
      onLoadSuccess:function(){
      	var data = $(this).combobox('getData');
      	if(data.length>0)
      		$(this).combobox('select', data[0].enumCode);
      }
   
  });

}

/**
 * 修改函数操作
 */
function editData(id){
	//先将ID置为空后赋值
	$("#resourceId").val("");
	$("#resourceId").val(id);
	$.ajax({
		type : 'post',
		dataType:"JSON",
		url : 'TextResource/main/findTextResourcesById',
		data : {
			id :id
		},
		success : function(rowInfo) {
			if(rowInfo != null){
				$('#dlg_add').dialog('open').dialog('setTitle', '修改文字资源');
				//清空原来输入的数据
				$("#resourceText").val(rowInfo.text);
				$("#resourceType").val(rowInfo.code);
				// 文字类型下拉框
			  $('#dlg_add #resourceTypeComb').combobox({
			        url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_TYPE',
			        width:185,
			        height:30,
			        editable: false,
			        valueField:'enumCode',
			        textField:'enumValue',
			        onLoadSuccess:function(){
			        	var data = $(this).combobox('getData');
			        	if(data.length>0)
			        		$(this).combobox('select', rowInfo.type);
			        }
			     
			    });
			  //文字状态下拉框（是否可用）
			  $('#dlg_add #resourceStateCom').combobox({
			      url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_ISAVALIABILITY',
			      width:185,
			      height:30,
			      editable: false,
			      valueField:'enumCode',
			      textField:'enumValue',
			      onLoadSuccess:function(){
			      	var data = $(this).combobox('getData');
			      	if(data.length>0)
			      		$(this).combobox('select', rowInfo.status);
			      }
			   
			  });
			  //文字语言下拉框
			  $('#dlg_add #resourceLanguage').combobox({
			      url: 'TextResource/main/findResourcesData?type=TEXT_RESOURCES_LANGUAGE',
			      width:185,
			      height:30,
			      editable: false,
			      valueField:'enumCode',
			      textField:'enumValue',
			      onLoadSuccess:function(){
			      	var data = $(this).combobox('getData');
			      	if(data.length>0)
			      		$(this).combobox('select', rowInfo.language);
			      }
			   
			  });
			
			}
		}
	});
}

//新增/修改取消方法
function cancel() {
    $('#dlg_add').dialog('close');
    //清除掉所有选中的行
    $('#overduePageTb').datagrid('clearSelections');
    
    

};
//数据校验
function valiateData(){
	var  text = $("#resourceText").val();
	var code = $("#resourceType").val();
	var type = $("#resourceTypeComb").combobox('getValue');
	var status = $("#resourceStateCom").combobox('getValue');
	var language = $("#resourceLanguage").combobox('getValue');
	//文字不为空
	if(text==''||$.trim(text)==''||text =='undefined'||text ==null){
		$.messager.alert('提示','请输入文字！');
		return false;
	}
	//唯一标识符不为空
	if(code==''||$.trim(code)==''||code =='undefined'||code ==null){
		$.messager.alert('提示','请输入唯一标识符！');
		return false;
	}
	//类型不为空
	if(type==''||$.trim(type)==''||type =='undefined'||type ==null){
		$.messager.alert('提示','请选择类型！');
		return false;
	}
	//是否可用不为空
	if(status==''||$.trim(status)==''||status =='undefined'||status ==null){
		$.messager.alert('提示','请选择是否可用！');
		return false;
	}
	//语言不为空
	if(language==''||$.trim(language)==''||language =='undefined'||language ==null){
		$.messager.alert('提示','请选择语言！');
		return false;
	}
	return true;
}
//保存或者修改
function onSave(){
	//调用验证，通过进行保存
	if(valiateData()){
		//判断文字、唯一标识符联合唯一
		//先判断是否有ID
		var resourceId = $("#resourceId").val();
		if(resourceId==''||$.trim(resourceId)==''||resourceId =='undefined'||resourceId ==null){
			//没有选中行，表明是新增
			$.ajax({
				type : 'post',
				dataType:"JSON",
				url : 'TextResource/main/findTexResourceByCondition',
				data : {
					code :$("#resourceType").val(),
					type:$("#resourceTypeComb").combobox('getValue')
				},
				success : function(result) {
					//是true,则证明数据库中没有文字、唯一标示符联合唯一的数据，可以保存
					if(result.msg){
						$.ajax({
							type : 'post',
							dataType:"JSON",
							url : 'TextResource/main/insertTextResource',
							data : {
								text:$("#resourceText").val(),
								code :$("#resourceType").val(),
								type:$("#resourceTypeComb").combobox('getValue'),
								status:$("#resourceStateCom").combobox('getValue'),
								language:$("#resourceLanguage").combobox('getValue')
							},
							success : function(result) {
								$('#dlg_add').dialog('close');
								$("#overduePageTb").datagrid('reload');
								$.messager.show({
									title:'提示',
									msg:result.msg,
									showType:'slide'
								}); 
							}
						});
					}else if(result.msg==false){
						$.messager.alert('提示','唯一标识符+类型已经存在，请重新输入！');
						return false;
					}
				}
			});
		}
		//id有值，表明是修改
		if(resourceId !=''&& $.trim(resourceId) !=''&& resourceId !='undefined'&&resourceId !=null){
			
			$.ajax({
				type : 'post',
				dataType:"JSON",
				url : 'TextResource/main/findTextResourcesById',
				data : {
					id :resourceId
				},
				success : function(rowInfo) {
					//先判断选择的类型和唯一标识符是否是原来的，如果是则不判断联合唯一
					var codeTemp = rowInfo.code;
					var typeTemp = rowInfo.type;
					if(typeTemp==$("#resourceTypeComb").combobox('getValue')&&codeTemp==$("#resourceType").val()){
						$.ajax({
							type : 'post',
							dataType:"JSON",
							url : 'TextResource/main/updateTextResource',
							data : {
								id :resourceId,
								text:$("#resourceText").val(),
								code :$("#resourceType").val(),
								type:$("#resourceTypeComb").combobox('getValue'),
								status:$("#resourceStateCom").combobox('getValue'),
								language:$("#resourceLanguage").combobox('getValue')
							},
							success : function(result) {
								$('#dlg_add').dialog('close');
								 //清除掉所有选中的行
							    $('#overduePageTb').datagrid('clearSelections');
								$("#overduePageTb").datagrid('reload');
								
								$.messager.show({
									title:'提示',
									msg:result.msg,
									showType:'slide'
								}); 
							}
						});
					
					}else {
						$.ajax({
							type : 'post',
							dataType:"JSON",
							url : 'TextResource/main/findTexResourceByCondition',
							data : {
								code :$("#resourceType").val(),
								type:$("#resourceTypeComb").combobox('getValue')
							},
							success : function(result) {
								//是true,则证明数据库中没有唯一标识符类型联合唯一的数据，可以保存
								if(result.msg){
									$.ajax({
										type : 'post',
										dataType:"JSON",
										url : 'TextResource/main/updateTextResource',
										data : {
											id :resourceId,
											text:$("#resourceText").val(),
											code :$("#resourceType").val(),
											type:$("#resourceTypeComb").combobox('getValue'),
											status:$("#resourceStateCom").combobox('getValue'),
											language:$("#resourceLanguage").combobox('getValue')
										},
										success : function(result) {
											$('#dlg_add').dialog('close');
											 //清除掉所有选中的行
										    $('#overduePageTb').datagrid('clearSelections');
											$("#overduePageTb").datagrid('reload');
											$.messager.show({
												title:'提示',
												msg:result.msg,
												showType:'slide'
											}); 
										}
									});
								}else if(result.msg==false){
									$.messager.alert('提示','唯一标识符+类型已经存在，请重新输入！');
									return false;
								}
							}
						});
					}
					
				}});
		}
		
	}
	
}
  
function refreshCache(type){
	 
	$.ajax({
		type : 'post',
		url : 'TextResource/main/refresh/'+type,
		async : false,
		success : function(result) {
			 $('#overduePageTb').datagrid('clearSelections');
			$("#overduePageTb").datagrid('reload');
			 $.messager.show({
                 title:'提示',
                 msg:'提交成功！',
                 showType:'slide'
             }); 
		}
	});
}
  

function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day;
    return(clock); 
} 

