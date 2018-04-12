$().ready(function() {
	
    //用户类型
    $('#toolbar #userTypecbo').combobox({
        url:'sysUser/userTypes',
        valueField:'enumCode',
        textField:'enumValue',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length > 0){
            	$('#toolbar #userTypecbo').combobox('select',data[0].enumCode);
            }
        }
    });
    //用户类型
    $('#userType').combobox({
        url:'sysUser/userTypes',
        valueField:'enumCode',
        textField:'enumValue',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
        }
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
    
    $('button[name="chooseBranch"]').click(function() {
    	$('#chooseBaseAreaPanel').window({
    		height:$(window).height()/2+50,
			modal:true
		});
    	return false;
    })
    

    
	$('#sysUserPanel').window('close');
	$('#changePwdPanel').window('close');
	$('#chooseBaseAreaPanel').window('close');
	
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
	$('#searchBut').click(function() {
		var queryParams = $('#sysUserGrid').datagrid('options').queryParams;
		queryParams.name = $('#name').val();
		queryParams.code = $('#code').val();
		queryParams.userType = $('#userTypecbo').combobox('getValue');
		queryParams.fullName= $('#fullNameSearch').val();
		$('#sysUserGrid').datagrid('options').queryParams = queryParams;
	    $("#sysUserGrid").datagrid('reload');
	})
	
	$('#insertSysUserBut').click(function() {
		loadTerr();
		$('#sysUserPanel').window({
//			width:300,
			height:$(window).height()/2+50,
			modal:true
		});
		$('#sysUserPanel').form('clear');
		$('#sysUserForm').find('input[name="code"]').attr("readonly",false);
	})
	// 初始化列表
	$('#sysUserGrid').datagrid({
		url : 'sysUser/getSearchJson',
		fitColumns : true,
		border : false,
		singleSelect : true,
		pagination : true,
		striped : true,
		fit:true,
		pageSize : 20,
		rownumbers : true,
		columns : [ [ {
			field : 'code',
			title : '工号',
			formatter : function(value, row, index) {
				return value;
			},
			width : 60
		}, {
			field : 'name',
			title : '姓名',
			width : 80
		}, {
			field : 'userType',
			title : '员工类型',
			width : 60,
            formatter : function(value, row, index) {
                return formatEnumName(value, 'USER_TYPE');
            }
		}, {
			field : 'fullName',
			title : '所属网点',
			formatter : function(value, row, index) {
				if (row.baseArea) {
					return row.baseArea.fullName;
				} else {
					return '';
				}
			},
			width : 160
		} ,{
			field : 'lastLoginTime',
			title : '上次登陆时间',
			formatter : function(value, row, index) {
				return value;
			},
			width : 80
		} ,{
			field : 'status',
			title : '状态',
			formatter : function(value, row, index) {
				var statusName = '';
				if (value == 0) {
					statusName = '正常';
				} else if (value == 1) {
					statusName = '锁定';
				}
				return statusName;
			},
			width : 40
		} ,{
			field : 'a',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = '';
				oper = '<a href="javascript:void(0)" onclick="loadUserInfoToWindow(' + row.id + ');">修改</a>'
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="showChangePwdWIndow(' + row.id + ');">重置密码</a>';
				oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="lockSysUser(' + row.id + ',' + (row.status == 0?1:0) + ');">' + (row.status == 0?'锁定':'恢复') + '</a>';
				if(userType == 1 || userType == 7 || userType == 10){
					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedUser(' + row.id + ');">删除</a>';
				}
				return oper;
			},
			width : 150
		} ] ],
		onBeforeLoad : function(param) {
			
		},
		onLoadSuccess : function(data) {
			
		},
		onLoadError : function() {
			
		},
		onClickCell : function(rowIndex, field, value) {
			
		}
	});

})
//加载权限组和产品组
function loadTerr(userId){
    //加载权限组数据
    $('#groupTree2').tree({
    	url : 'sysGroup/getGroupJson',
    	method : 'post',
    	animate : true,
    	checkbox : true,
    	onlyLeafCheck : true,
    	onLoadSuccess:function(){  
    		//选中已有的权限组
    		if (!userId) {return;}
    		var treeObj = $('#productTree2');
    		var panelObj = $('#sysUserPanel');
    		panelObj.find('input[name="id"]').val(userId);
    		$.ajax({
    			type: 'POST',
    			url: 'sysUser/queryMyProductList' ,
    			data: {'userId':userId} ,
    		    dataType: 'json',
    		    success: function(data) {
    		    	var products = data.products;
    		    	if (products) {
    		    		for (var i=0;i<products.length;i++) {
    		    			var productId = products[i];
    		    			var node = treeObj.tree('find',productId);
    		    			if (node) {
    		    				treeObj.tree('check',node.target);
    		    			}
    		    		}
    		    	}
    		    } ,
    		    error : function() {
    		    	$.messager.alert('操作提示', '请求失败,请检查URL和参数!','error');
    		    }
    		});
    	}
    })

    /*加载权限组数据*/
    $('#productTree2').tree({
    	url : 'product/getProductJson',
    	method : 'post',
    	animate : true,
    	checkbox : true,
    	onlyLeafCheck : true,
    	onLoadSuccess:function(){  
    		//选中已添加的产品
    		if (!userId) {return;}
    		var treeObj = $('#groupTree2');

    		$.ajax({
    			type: 'POST',
    			url: 'sysUser/queryMyGroupList' ,
    			data: {'userId':userId} ,
    		    dataType: 'json',
    		    success: function(data) {
    		    	var groups = data.gorups;
    		    	if (groups) {
    		    		for (var i=0;i<groups.length;i++) {
    		    			var groupId = groups[i].groupId;
    		    			var node = treeObj.tree('find',groupId);
    		    			if (node) {
    		    				treeObj.tree('check',node.target);
    		    			}
    		    		}
    		    	}
    		    } ,
    		    error : function() {
    		    	$.messager.alert('操作提示', '请求失败,请检查URL和参数!','error');
    		    }
    		});
    	}
    })
}

//加载某员工信息填充到表单
function loadUserInfoToWindow (userId) {
	loadTerr(userId);
	if (!userId) {return;}
	$.ajax({
		url : 'sysUser/loadSysUserInfo',
		data : {
			userId : userId						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#sysUserPanel').window({
//					width:300,
					height:$(window).height()/2+50,
					modal:true
				});
				$('#sysUserPanel').form('clear');
				$('#sysUserPanel').form('load', result.sysUser);
				$('#sysUserPanel').find('input[name="fullName"]').val(result.sysUser.baseArea.name);
				$('#sysUserForm').find('input[name="code"]').attr("readonly",true);
				
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//新增、修改员工信息
function doModifySysUserInfo() {
	var formObj = $('#sysUserForm');
	if (formObj.form("validate")) {
		var  id = $('#id').val();
		id = (id == "" ? null  :  id);
		var  code = $('#userCode').val();
		var  name = $('#userName').val();
		var  userType = $('#userType').combobox('getValue');
		var  areaId = $('#areaId').val();
		var productList = [];
		 var checkNodes = $('#productTree2').tree('getChecked');//获取:checked的结点.
		 if(checkNodes.length < 1){
			 alert("请勾选产品");
			 return;
		 }
		 if (checkNodes) {
			 for (var i=0;i<checkNodes.length;i++) {
				 productList.push(checkNodes[i].id);
			 }
		 }
		 var groupList = [];
		 var checkNodes2 = $('#groupTree2').tree('getChecked');//获取:checked的结点.
		 if (checkNodes2) {
			 for (var i=0;i<checkNodes2.length;i++) {
				 groupList.push(checkNodes2[i].id);
			 }
		 }
			$.ajax({
				type: 'POST',
				url: 'sysUser/saveSysUserInfo' ,
				data: {'id':id,'code':code,'name':name,'userType':userType,'areaId':areaId,'productList':productList.join(','),'groupList':groupList.join(',')} ,
			    dataType: 'json',
			    success: function(data) {
					var isErr = '';
					if(data.isSuccess){
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', data.msg,isErr);
					$('#searchBut').trigger('click');
					if (!(data.msg == '工号重复')) {
						$('#sysUserPanel').window('close');
					}
			    }	
			});
	}
}


//显示重置密码窗口
function showChangePwdWIndow(userId) {
	if (!userId) {return;}
	$('#changePwdPanel').window({
		width:300,
		modal:true
	});
	$('#changePwdPanel').form('clear');
	$('#changePwdForm').find('input[name="userId"]').val(userId);
}

//提交重置密码请求
function doModifyPwd() {
	var userId = $('#changePwdForm > input[name="userId"]').val();
	if (!userId) {return;}
	
	if ($('#changePwdForm').form("validate")) {
		var pwd = $('#changePwdForm input[name="pwd"]').val();
		var confirmPwd = $('#changePwdForm input[name="confirmPwd"]').val();
		if (pwd != confirmPwd) {
			$.messager.alert('操作提示', '密码不一致','error');
			return;
		}
		
		$.post('sysUser/changePwd', $('#changePwdForm').serialize(), function (data) {
			if(data.isSuccess){
				$.messager.alert('操作提示', '修改成功');
			} else {
				$.messager.alert('操作提示', data.msg,'error');
			}
			$('#searchBut').trigger('click');
			$('#changePwdPanel').window('close');
		})
	}
}
//删除员工
function deletedUser(userId) {	
	if (!userId) {return;}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.ajax({
	    		url : 'sysUser/deletedUser',
	    		data : {
	    			userId : userId						
	    		},
	    		type:'POST',
	    		success : function(result){
	    			if (result.isSuccess) {
	    				$.messager.alert('操作提示', '操作成功');
	    				 $("#sysUserGrid").datagrid('reload');
	    			} else {
	    				$.messager.alert('操作提示', result.msg,'error');
	    			}
	    		},
	    		error:function(data){
	    			$.messager.alert('操作提示', 'error','error');
	    		}
	    	});
	    }    
	}); 

}

//锁定/恢复员工身份
function lockSysUser(userId,status) {
	if (!userId) {return;}
	$.messager.confirm('操作提示','您确定要执行' + (status == 0?'恢复':'锁定') + '操作吗？', function (data) {
		if (data) {
			//提交表单
			$.ajax({
				type: 'POST',
				url: 'sysUser/lockSysUser' ,
				data: {"userId":userId,"status":status} ,
			    dataType: 'json',
			    success: function() {
			    	$.messager.alert('操作提示', '操作成功');
					$('#searchBut').trigger('click');
			    } ,
			    error : function() {
			    	$.messager.alert('操作提示', '请求失败,请检查URL和参数!','error');
			    }
			});
		} else {
			return;
		}
	});
}

//自定义 验证规则validType="selectValueRequired"
$.extend($.fn.validatebox.defaults.rules, {   
    selectValueRequired: {   
        validator: function(value,param){  
             if (value == "") {   
                return false;  
             }else {  
                return true;  
             }    
        },   
        message: '该下拉框为必选项'   
    }   
});   


//清空查询条件
$('#clear').click(function(){
	 loadCombobox();
	$('#name').val("");
});

