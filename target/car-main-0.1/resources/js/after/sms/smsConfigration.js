$(function() {
	$("#smsConfigration").datagrid({
		url : 'after/smsConfiguration/getSmsConfigrationList',
		fitColumns : false,
        border : false,
        singleSelect:true,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        checkOnSelect: false, selectOnCheck: false,
        columns : [[
                    {field : 'cityId' ,checkbox : true},
                    {field : 'cityName' ,title : '城市' ,width : 50},
                    {field : 'servicePhone' ,title : '电话号码' , width : 120}
                   /* {field : 'operations' ,title : '操作' ,formatter : formatOperationsCell, width :80}*/
                   ]]
                  /* onLoadSuccess:function (data){
                	   var checkedItems = $('#smsConfigration').datagrid('getChecked');               	  
                	   var ids = [];               	   
                	   $.each(checkedItems, function(index, rows){            	   
                		   ids.push(rows.cityId);               	   
                	   });                            	  
                	   alert(ids.join(","));
                	   $('#cityIds').val(ids.join(","));
                   }*/
	});
});


function searchCityPhone(){
		//查询数据
	    var queryParams = $('#smsConfigration').datagrid('options').queryParams;
		queryParams.cityId = $('#toolbar #cityId').combobox('getValue');
		queryParams.servicePhone = $('#toolbar #servicePhone').val();
		 setFirstPage("#smsConfigration");
		$('#smsConfigration').datagrid('options').queryParams = queryParams;		
		$("#smsConfigration").datagrid('reload');	
}
//关闭表单
function closeForm()
{
$('#form1').form('clear');
$('#editForm').window('close');
}


//电话配置弹出层
function tanCityPhone(){
	  var checkedItems = $('#smsConfigration').datagrid('getChecked');               	  
	  var ids = [];               	   
	  $.each(checkedItems, function(index, rows){            	   
		   ids.push(rows.cityId);               	   
	 });                            	  	 
	if(ids==''){
		$.messager.show({
			title : '提示',
			msg : '请选择至少一个城市！',
			showType : 'slide'
		});
		return;
	}
	$('#cityIds').val(ids);
	$('#editForm').window('open');	
	$('#editForm #servicePhone').val("");
}

function checkTel(tel)
{
   var mobile = /^1[3|5|8]\d{9}$/ , phone = /^0\d{2,3}-?\d{7,8}$/;
   return mobile.test(tel) || phone.test(tel);
}
//批量修改城市服务电话
function plUpdCityPhone(){
	var servicePhone=$('#editForm #servicePhone').val();
	if(!checkTel(servicePhone) && !checkTel(servicePhone)){
		$.messager.show({
			title : '提示',
			msg : '请输入正确格式的电话号码！',
			showType : 'slide'
		});
		return;
	}
	var cityIds=$('#cityIds').val();
	$.post('after/smsConfiguration/plUpdCityPhone',{'ids':cityIds,'servicePhone':servicePhone},
			function callback(data){
		if(data.flag==0){
			$.messager.show({
				title : '提示',
				msg : '城市或电话号码为空!',
				showType : 'slide'
			});
			closeForm();
			$('#smsConfigration').datagrid('reload');
			return;
		}
		if(data.flag==-1){
			$.messager.show({
				title : '提示',
				msg : '数据库操作异常',
				showType : 'slide'
			});
			closeForm();
			$('#smsConfigration').datagrid('reload');
			return;
		}
		if(data.flag==1){
			$.messager.show({
				title : '提示',
				msg : '更新成功笔数:'+data.susCount,
				showType : 'slide'
			});
			closeForm();
			$('#smsConfigration').datagrid('reload');
			return;
		}
	})
}
