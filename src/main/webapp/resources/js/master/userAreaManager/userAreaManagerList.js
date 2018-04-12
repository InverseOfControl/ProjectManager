var url;
$(function () {
	// 查询按钮
	$('#searchBut').bind('click', search);
	$('#addBut').bind('click', addData);
    $('#userAreaMa_result').datagrid({
    	onLoadSuccess:function(data){ 
  		  if(data.total==0)
  		  {
  		    $.messager.show({
                  title:'结果',
                  msg:'没查到符合条件的数据！',
                  showType:'slide'
              });
  		  }
    	  },
        url: 'userAreaManager/userAreaManagerList',
        fitColumns : true,
	    border : false,
	    fit:true,
	    singleSelect:true,
	    pagination : true,
	    striped: true,
	    pageSize:10,     
	    rownumbers : true,
        queryParams: {
	    	//userName : $('#userName').val()
	    	
	    	//createdTimeStart  : $('#createdTimeStart').val(),
	    	//createdTimeEnd  : $('#createdTimeEnd').val(),
	    	//modifiedTimeStart  : $('#modifiedTimeStart').val(),
	    	//modifiedTimeEnd  : $('#modifiedTimeEnd').val(),
		},
        columns : [ [ 
		{
			field : 'id',
			title : 'ID'
			//formatter : linkDetail
		},
		{
			field : 'userName',
			title : '用户'
			//formatter : linkDetail
		},
		{
			field : 'baseAreaName',
			title : '网点名称'
			//formatter : linkDetail
		}, 
        {
			field : 'baseAreaType',
			title : '网点类型' ,
			formatter : function(value, row, index) {
				var identifierName = '';
				if (value == 'zdsys.Company') {
					identifierName = '公司';
				}else if (value == 'zdsys.Area') {
					identifierName = '地区';
				} else if (value == 'zdsys.City') {
					identifierName = '城市';
				} else if (value == 'zdsys.SalesDepartment') {
					identifierName = '部门/营业部';
				} else if (value == 'zdsys.SalesTeam') {
					identifierName = '团队';
				}
				return identifierName;
			}
			
		},
		{
			field : 'baseAreaId',
			title : '区域id',
			hidden : true
			//formatter : linkDetail
		}, 
	    {
	    	field : 'isDeleted',
	    	title : '是否有效',
	    	formatter: function(value, row, index){
				if(value == 0){
					return '有效';
				}else if(value == 1){
					return '无效';
				}else{
					return '';
				}
	        }
	    },
	    {
	    	field : 'operation',
	    	title : '操作'  ,
    		formatter : function(value, row, index) {
    			var isDeleted = row.isDeleted;
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="updateUserBaseArea(' + row.id + ');">修改</a>' ;
				if(isDeleted==0){
					oper += ' | <a href="javascript:void(0)" onclick="updateDisableState(' + row.id + ');">禁用</a>' ;
				}else if(isDeleted==1){
					oper += ' | <a href="javascript:void(0)" onclick="updateEnableState(' + row.id + ');">启用</a>';
				}
//				oper += ' | <a href="javascript:void(0)" onclick="sysParamFlushWindow(' + row.id + ');">刷新</a>';
				//oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="lockSysUser(' + row.id + ',' + (row.status == 0?1:0) + ');">' + (row.status == 0?'锁定':'恢复') + '</a>';
//				if(userType == 1){
//					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedUser(' + row.id + ');">删除</a>';
//				}
				return oper;
			},
			width : 150
	    }
		] ]
	});

    // 设置分页控件
   /*var p = $('#userAreaMa_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });*/
    
   
    
    $('button[name="chooseUserData"]').click(function() {
    	$('#window_chooseUser').window({
    		height:$(window).height()/2+50,
			modal:true
		});
    	return false;
    })
    
    $('button[name="chooseBranch"]').click(function() {
    	$('#chooseBaseAreaPanel').window({
    		height:$(window).height()/2+50,
			modal:true
		});
    	return false;
    })
     $('#window_chooseUser').window('close');
    $('#chooseBaseAreaPanel').window('close');
    
});

//查询数据
function search(){	
	var queryParams = $('#userAreaMa_result').datagrid('options').queryParams;
	queryParams.userName = $('#toolbar #userName').val();
	
	setFirstPage("#userAreaMa_result");
	$('#userAreaMa_result').datagrid('options').queryParams = queryParams;
	$("#userAreaMa_result").datagrid('reload');
};
/**
 * 新增函数操作
 */
function addData(){
	//先清除掉form中的数据
	setNull();
	
	$('#dlg_add').dialog('open').dialog('setTitle', '新增用户地区');
	//清除掉用户原来选中的行
	 $('#chooseUserList').datagrid('clearSelections');
	//清除掉网点原来选中的行
	 $('#userAreaGrid').datagrid('clearSelections');
}
/**
 * 取消新增/修改页面
 */
function closeUserBaseArea(){
	$('#dlg_add').dialog('close');
}

//数据校验
function valiateData(){
	var  userName = $("#userNameAdd").val();
	var areaName = $("#fullName").val();
	//用户名称不为空
	if(userName==''||$.trim(userName)==''||userName =='undefined'||userName ==null){
		$.messager.alert('提示','请选择一个用户！');
		return false;
	}
	//区域名称不为空
	if(areaName==''||$.trim(areaName)==''||areaName =='undefined'||areaName ==null){
		$.messager.alert('提示','请选择区域！');
		return false;
	}
	
	return true;
}

//保存数据
function saveUserBaseArea(){
	if(valiateData()){
		//根据ID判断是否是修改
	 var userBaseAreaId = $("#userAreaManagerId").val();
	 if(userBaseAreaId!=''&&$.trim(userBaseAreaId)!=''&&userBaseAreaId !='undefined'&&userBaseAreaId !=null){
		 $.ajax({
				type : 'post',
				dataType:"JSON",
				url : 'userAreaManager/updateUserAreaManager',
				data : {
					userId:$("#userId").val(),
					baseAreaId :$("#areaId").val(),
					id :userBaseAreaId
				},
				success : function(result) {
					//关闭新增页面
					$('#dlg_add').dialog('close');
					$("#userAreaMa_result").datagrid('reload');
					$.messager.show({
						title:'提示',
						msg:result.msg,
						showType:'slide'
					}); 
				}
			});
	 }else{
		 $.ajax({
				type : 'post',
				dataType:"JSON",
				url : 'userAreaManager/insertUserAreaManager',
				data : {
					userId:$("#userId").val(),
					baseAreaId :$("#areaId").val()
				},
				success : function(result) {
					//关闭新增页面
					$('#dlg_add').dialog('close');
					$("#userAreaMa_result").datagrid('reload');
					$.messager.show({
						title:'提示',
						msg:result.msg,
						showType:'slide'
					}); 
				}
			});
	 }
	}
}
//修改数据
function updateUserBaseArea(id){
	//先将数据置为空后赋值
	setNull();
	$("#userAreaManagerId").val(id);
	$.ajax({
		type : 'post',
		dataType:"JSON",
		url : 'userAreaManager/findUserAreaManagerById',
		data : {
			id :id
		},
		success : function(rowInfo) {
			if(rowInfo != null){
				$("#userId").val(rowInfo.userId);
				$("#areaId").val(rowInfo.baseAreaId);
				$("#userNameAdd").val(rowInfo.userName);
				$("#fullName").val(rowInfo.baseAreaName);
				$('#dlg_add').dialog('open').dialog('setTitle', '修改用户地区');
				
			}
		}
	});
}
//禁用数据
function updateDisableState(id){
	$.ajax({
		type : 'post',
		dataType:"JSON",
		url : 'userAreaManager/updateUserAreaManager',
		data : {
			id :id,
			isDeleted:1
		},
		success : function(result) {
			//关闭新增页面
			$('#dlg_add').dialog('close');
			$("#userAreaMa_result").datagrid('reload');
			$.messager.show({
				title:'提示',
				msg:result.msg,
				showType:'slide'
			}); 
		}
			
		});
}

//启用数据
function updateEnableState(id){
	$.ajax({
		type : 'post',
		dataType:"JSON",
		url : 'userAreaManager/updateUserAreaManager',
		data : {
			id :id,
			isDeleted:0
		},
		success : function(result) {
			//关闭新增页面
			$('#dlg_add').dialog('close');
			$("#userAreaMa_result").datagrid('reload');
			$.messager.show({
				title:'提示',
				msg:result.msg,
				showType:'slide'
			}); 
		}
		});
}


//将新增修改页面中的数据置为空
function setNull(){
	$("#userAreaManagerId").val("");
	$("#userId").val("");
	$("#areaId").val("");
	$("#userNameAdd").val("");
	$("#fullName").val("");
}



/**
 * 选择网点名称列表页面
 */
//function chooseBaseArea(){
//	$('#dlg_chooseBaseArea').dialog('open').dialog('setTitle', '选择营业网点');
//}


