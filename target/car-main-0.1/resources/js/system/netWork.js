$()
        .ready(
                function() {

	                // 开启表单验证
	                $('input.validateItem').validatebox({
		                required : true
	                });
	                // 开启表单验证
	                $('select.validateItem').combo({
		                required : true
	                // multiple:true
	                });
	                // 初始化办公地点表格
	                $('#WorkPlaceInfoGrid')
	                        .datagrid(
	                                {
	                                    url : 'workPleaceInfo/getWorkPlaceInfoJson',
	                                    fitColumns : true,
	                                    border : false,
	                                    singleSelect : true,
	                                    pagination : true,
	                                    striped : true,
	                                    pageSize : 20,
	                                    fit:true,
	                                    rownumbers : true,
	                                    
	                                    columns :
	                                    [
	                                    [
	                                            {
	                                                field : 'id',
	                                                title : '网点编号',
	                                                width : 60
	                                            },
	                                            {
	                                                field : 'tel',
	                                                title : '服务电话',
	                                                width : 100
	                                            },
	                                            {
	                                                field : 'cityNo',
	                                                title : '城市编号',
	                                                width : 60
	                                            },
	                                            {
	                                                field : 'zoneCode',
	                                                title : '区号',
	                                                width : 60
	                                            },
	                                            {
	                                                field : 'site',
	                                                title : '办公地点名称',
	                                                width : 500
	                                            },
	                                            {
	                                                field : 'oper',
	                                                title : '操作',
	                                                width : 80,
	                                                formatter : function(value,
	                                                        row, index) {
		                                                var oper = '';
		                                                oper += '<a href="javascript:void(0)" onclick="selectWorkPlaceInfo({\'id\':'
		                                                        + row.id
		                                                        + ',\'site\':\''
		                                                        + row.site
		                                                        + '\'});">选择</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		                                                oper += '<a href="javascript:void(0)" onclick="loadWorkPlaceInfo('
		                                                        + row.id
		                                                        + ');">修改</a>';

		                                                return oper;
	                                                },
	                                                width : 100
	                                            } ] ]
	                                });
	                // 办公网点条件查询
	                $('#searchWorkPlaceInfoBut')
	                        .click(
	                                function() {
		                                var queryParams = $(
		                                        '#WorkPlaceInfoGrid').datagrid(
		                                        'options').queryParams;
		                                // 电话
		                                queryParams.tel =  $('#chooseWorkPlaceInfoPanel').find('input[id="tel"]').val();
		                                queryParams.cityNo = $('#chooseWorkPlaceInfoPanel').find('input[id="cityNo"]').val();
		                                queryParams.site = $('#chooseWorkPlaceInfoPanel').find('input[id="site"]').val();
		                                $('#WorkPlaceInfoGrid').datagrid(
		                                        'options').queryParams = queryParams;
		                                $("#WorkPlaceInfoGrid").datagrid('reload');
	                                });
	                // 窗口默认为，关闭状态。
	                $('#addAreaPanel').window('close');
	                $('#addCityPanel').window('close');
	                $('#addDeptPanel').window('close');
	                $('#addTeamPanel').window('close');
	                $('#updateCompanyPanel').window('close');
	                $('#updateAreaPanel').window('close');
	                $('#updateCityPanel').window('close');
	                $('#updateDeptPanel').window('close');
	                $('#updateTeamPanel').window('close');
	                $('#chooseWorkPlaceInfoPanel').window('close');
	                $('#addWorkPlaceInfoPanel').window('close');
	                // 网点条件查询
	                $('#searchBut')
	                        .click(
	                                function() {
		                                var queryParams = $('#netWorkGrid')
		                                        .datagrid('options').queryParams;
		                                // 网点名称
		                                queryParams.fullName = $('#fullName')
		                                        .val();

		                                // 地区ID
		                                var areaId = $('#areaCombo').combobox(
		                                        'getValue');
		                                // 城市ID
		                                var cityId = $('#cityCombo').combobox(
		                                        'getValue');
		                                // 部门/营业部ID
		                                var branchId = $('#branchCombo')
		                                        .combobox('getValue');

		                                if (branchId) {
			                                areaId = '';
			                                cityId = '';
		                                } else if (cityId) {
			                                areaId = '';
		                                }
		                                queryParams.areaId = areaId;
		                                queryParams.cityId = cityId;
		                                queryParams.salesDeptId = branchId;
		                                $('#netWorkGrid').datagrid('options').queryParams = queryParams;
		                                $("#netWorkGrid").datagrid('reload');
	                                })

	                // 初始化营业网点表格
	                $('#netWorkGrid')
	                        .datagrid(
	                                {
	                                    url : 'baseArea/getSearchJson',
	                            		fitColumns : true,
	                            		border : false,
	                            		singleSelect : true,
	                            		pagination : true,
	                            		striped : true,
	                            		fit:true,
	                            		pageSize : 20,
	                            		rownumbers : true,
	                                    columns :
	                                    [
	                                    [
	                                            {
	                                                field : 'code',
	                                                title : '网点编号',
	                                                formatter : function(value,
	                                                        row, index) {
		                                                return value;
	                                                },
	                                                width : 70
	                                            },
	                                            {
	                                                field : 'fullName',
	                                                title : '网点全称',
	                                                width : 140
	                                            },
	                                            {
	                                                field : 'name',
	                                                title : '网点名称',
	                                                width : 60,
	                                                formatter : function(value,
	                                                        row, index) {
		                                                return value;
	                                                }
	                                            },
	                                            {
	                                                field : 'identifier',
	                                                title : '网点类型',
	                                                formatter : function(value,
	                                                        row, index) {
		                                                var identifierName = '';
		                                                if (value == 'zdsys.Company') {
			                                                identifierName = '公司';
		                                                } else if (value == 'zdsys.Area') {
			                                                identifierName = '地区';
		                                                } else if (value == 'zdsys.City') {
			                                                identifierName = '城市';
		                                                } else if (value == 'zdsys.SalesDepartment') {
			                                                identifierName = '营业部';
		                                                } else if (value == 'zdsys.SalesTeam') {
			                                                identifierName = '团队';
		                                                }
		                                                return identifierName;
	                                                },
	                                                width : 40
	                                            },
	                                            {
	                                                field : 'oper',
	                                                title : '操作',
	                                                formatter : function(value,
	                                                        row, index) {
		                                                var oper = '';
		                                                var identifier = row.identifier;
		                                				if(userType == 1){
		                                					oper += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deletedBaseArea(' + row.id + ');">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		                                				}
		                                                if (row.identifier == 'zdsys.Company') {
			                                                oper += oper = '<a onclick="loadNetWorkInfoToWindow('
			                                                        + row.id
			                                                        + ');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			                                                oper += '<a onclick="addArea('
			                                                        + row.id
			                                                        + ');">新增地区</a>';
		                                                }
		                                                if (row.identifier == 'zdsys.Area') {
			                                                oper += '<a onclick="loadNetWorkInfoToWindow('
			                                                        + row.id
			                                                        + ');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			                                                oper += '<a onclick="addCity('
			                                                        + row.id
			                                                        + ')">新增城市</a>';
		                                                }
		                                                if (row.identifier == 'zdsys.City') {
			                                                oper += '<a onclick="loadNetWorkInfoToWindow('
			                                                        + row.id
			                                                        + ');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			                                                oper += '<a onclick="addDept('
			                                                        + row.id
			                                                        + ')">新增营业部</a>';
		                                                }
		                                                if (row.identifier == 'zdsys.SalesDepartment') {
			                                                oper += '<a onclick="loadNetWorkInfoToWindow('
			                                                        + row.id
			                                                        + ');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			                                                oper += '<a onclick="addTeam('
			                                                        + row.id
			                                                        + ')">新增团队</a>';
		                                                }
		                                                if (row.identifier == 'zdsys.SalesTeam') {
			                                                oper += '<a onclick="loadNetWorkInfoToWindow('
			                                                        + row.id
			                                                        + ');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';

		                                                }

		                                                return oper;
	                                                },
	                                                width : 50
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
	                // 显示新增办公地点面板
	                $('#addWorkPlaceInfoBut').click(function() {
		                $('#addWorkPlaceInfoPanel').form('clear');
		                $('#addWorkPlaceInfoPanel').window({
			                modal : true
		                });

	                });
                })
/** *************************************************************************************** */
function loadWorkPlaceInfo(id) {
	$.ajax({
	    url : 'workPleaceInfo/loadworkPlaceInfo',
	    data : {
		    id : id
	    },
	    type : 'POST',
	    success : function(result) {
		    if (result.isSuccess) {
			    $('#addWorkPlaceInfoPanel').find('input[name="id"]').val(
			            result.workPlaceInfo.id);
			    $('#addWorkPlaceInfoPanel').find('textarea[name="site"]').val(
			            result.workPlaceInfo.site);
			    $('#addWorkPlaceInfoPanel').find('input[name="cityNo"]').val(
			            result.workPlaceInfo.cityNo);
			    $('#addWorkPlaceInfoPanel').find('input[name="zoneCode"]').val(
			            result.workPlaceInfo.zoneCode);
			    $('#addWorkPlaceInfoPanel').find('input[name="tel"]').val(
			            result.workPlaceInfo.tel);
			    $('#addWorkPlaceInfoPanel').window({
				    modal : true
			    });
		    } else {
			    parent.$.messager.show({
			        title : '提示',
			        msg : result.msg
			    });
		    }
	    },
	    error : function(data) {
		    parent.$.messager.show({
		        title : '提示',
		        msg : 'error'
		    });
	    }
	});
}
// 选中办公地点赋值操作
function selectWorkPlaceInfo(workPlaceInfo) {
	var id = workPlaceInfo.id;
	var site = workPlaceInfo.site;
	$('#addDeptPanel').find('input[name="workPlaceInfoId"]').val(id);
	$('#addDeptPanel').find('textarea[name="site"]').val(site);
	$('#updateDeptPanel').find('input[name="workPlaceInfoId"]').val(id);
	$('#updateDeptPanel').find('textarea[name="site"]').val(site);
	$('#chooseWorkPlaceInfoPanel').window('close');
}
// 加载某网点信息填充到表单
function loadNetWorkInfoToWindow(id) {
	$
	        .ajax({
	            url : 'baseArea/loadNetWorkInfo',
	            data : {
		            id : id
	            },
	            type : 'POST',
	            success : function(result) {
		            if (result.isSuccess) {
			            // 加载公司名
			            if (result.baseArea.identifier == 'zdsys.Company') {
				            $('#updateCompanyPanel').window({
					            modal : true
				            });
				            $('#updateCompanyPanel').form('clear');
				            $('#updateCompanyPanel').form('load', result.baseArea);
				            $('#updateCompanyPanel').find('input[name="name"]').val( result.baseArea.name);
				            $('#updateCompanyPanel').find('input[name="id"]').val(id);
			            }
			            // 加载地区名
			            if (result.baseArea.identifier == 'zdsys.Area') {
				            $('#updateAreaPanel').window({
					            modal : true
				            });
				            $('#updateAreaPanel').form('clear');
				            $('#updateAreaPanel').form('load', result.baseArea);
				            $('#updateAreaPanel').find('input[name="name"]').val(result.baseArea.name);
				            $('#updateAreaPanel').find('input[name="id"]').val(id);
			            }
			            // 加载城市名
			            if (result.baseArea.identifier == 'zdsys.City') {
				            $('#updateCityPanel').window({
					            modal : true
				            });
				            $('#updateCityPanel').form('clear');
				            $('#updateCityPanel').form('load', result.baseArea);
				            $('#updateCityPanel').find('input[name="name"]').val(result.baseArea.name);
				            $('#updateCityPanel').find('input[name="id"]').val(id);
			            }
			            // 加载营业部信息
			            if (result.baseArea.identifier == 'zdsys.SalesDepartment') {
				            $('#updateDeptPanel').window({
					            modal : true
				            });
				            $('#updateDeptPanel').form('load',result.baseArea);
				            $('#updateDeptPanel').find('input[name="name"]').val(result.baseArea.name);
				            $('#updateDeptPanel').find('input[name="id"]').val(id);
				            $
				                    .ajax({
				                        url : 'workPleaceInfo/loadworkPlaceInfo',
				                        data : {
					                        id : result.baseArea.workPlaceInfoId
				                        },
				                        type : 'POST',
				                        success : function(result) {
					                        if (result.isSuccess) {

						                        $('#updateDeptPanel').find('input[name="workPlaceInfoId"]').val(result.workPlaceInfo.id);
						                        $('#updateDeptPanel').find('textarea[name="site"]').val(result.workPlaceInfo.site);
					                        } else {
						                        parent.$.messager.show({
						                            title : '提示',
						                            msg : result.msg
						                        });
					                        }
				                        },
				                        error : function(data) {
					                        parent.$.messager.show({
					                            title : '提示',
					                            msg : 'error'
					                        });
				                        }
				                    });
			            }
			            // 加载团队名
			            if (result.baseArea.identifier == 'zdsys.SalesTeam') {
				            $('#updateTeamPanel').window({
					            modal : true
				            });
				            $('#updateTeamPanel').form('clear');
				            $('#updateTeamPanel').form('load', result.baseArea);
				            $('#updateTeamPanel').find('input[name="name"]').val(result.baseArea.name);
				            $('#updateTeamPanel').find('input[name="salesDeptId"]').val(result.baseArea.salesDeptId);
				            $('#updateTeamPanel').find('input[name="id"]').val(id);
			            }
		            } else {

			            parent.$.messager.show({
			                title : '提示',
			                msg : result.msg
			            });
		            }
	            },
	            error : function(data) {
		            parent.$.messager.show({
		                title : '提示',
		                msg : 'error'
		            });
	            }
	        });
}
// 打开新增网点窗口
function addArea(companyId) {
		$('#addAreaPanel').window({
			modal : true
		});
		$('#addAreaPanel').form('clear');
		$('#addAreaPanel').find('input[name="companyId"]').val(companyId);
}
function addCity(areaId) {
		$('#addCityPanel').window({
			modal : true
		});
		$('#addCityPanel').form('clear');
		$('#addCityPanel').find('input[name="areaId"]').val(areaId);
}
function addDept(cityId){
		$('#addDeptPanel').window({
			modal : true
		});
		$('#addDeptPanel').form('clear');
		$('#addDeptPanel').find('input[name="cityId"]').val(cityId);
}
function addTeam(salesDeptId){
	$('#addTeamPanel').window({
		modal : true
	});
	$('#addTeamPanel').form('clear');
	$('#addTeamPanel').find('input[name="salesDeptId"]').val(salesDeptId);
}


// 修改公司信息
function updateCompany(){
	var formObj = $('#updateCompanyForm');
	if (formObj.form("validate")) {
		$.post('baseArea/updateCompany', formObj.serialize(), function(data) {
			var isErr = '';

			if (data.isSuccess) {

			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#updateCompanyPanel').window('close');
			}
		})
	}
}
// 修改地区
function updateArea(){
	var formObj = $('#updateAreaForm');
	if (formObj.form("validate")) {
		$.post('baseArea/updateArea', formObj.serialize(), function(data) {
			var isErr = '';
			
			if (data.isSuccess) {
				
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#updateAreaPanel').window('close');
			}
		})
	}
}
// 修改城市
function updateCity(){
	var formObj = $('#updateCityForm');
	if (formObj.form("validate")) {
		$.post('baseArea/updateCity', formObj.serialize(), function(data) {
			var isErr = '';
			
			if (data.isSuccess) {
				
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#updateCityPanel').window('close');
			}
		})
	}
}
// 修改营业部
function updateDept(){
	var formObj = $('#updateDeptForm');
	if (formObj.form("validate")) {
		$.post('baseArea/updateDept', formObj.serialize(), function(data) {
			var isErr = '';
			
			if (data.isSuccess) {
				
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#updateDeptPanel').window('close');
			}
		})
	}
}
// 修改团队
function updateTeam(){
	var formObj = $('#updateTeamForm');
	if (formObj.form("validate")) {
		$.post('baseArea/updateTeam', formObj.serialize(), function(data) {
			var isErr = '';
			
			if (data.isSuccess) {
				
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#updateTeamPanel').window('close');
			}
		})
	}
}

// 保存地区
function saveArea() {
	var formObj = $('#addAreaForm');
	if (formObj.form("validate")) {
		$.post('baseArea/addArea', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
				//添加地区成功刷新区域下拉框
				$('#areaCombo').combobox('reload','baseArea/getSearchJson1?companyId=2');
				//刷新列表
                $("#WorkPlaceInfoGrid").datagrid('reload') ;
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#addAreaPanel').window('close');
			}
		})
	}
}

// 保存城市
function saveCity() {
	var formObj = $('#addCityForm');
	if (formObj.form("validate")) {
		$.post('baseArea/addCity', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#addCityPanel').window('close');
			}
		})
	}
}
// 保存营业部
function saveSalesDepartment() {
	var formObj = $('#addDeptForm');
	if (formObj.form("validate")) {
		$.post('baseArea/addDept', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#addDeptPanel').window('close');
			}
		})
	}
}
// 保存团队信息
function saveSalesTeam() {
	var formObj = $('#addTeamPanelForm');
	if (formObj.form("validate")) {
		$.post('baseArea/addTeam', formObj.serialize(), function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBut').trigger('click');
			if (!(data.msg == '编码重复')) {
				$('#addTeamPanel').window('close');
			}
		})
	}
}
// 保存办公地点
function addWorkPlaceInfo() {
	var formObj = $('#addWorkPlaceInfoForm');
	if (formObj.form("validate")) {
		$.post('workPleaceInfo/addWorkPlaceInfo', formObj.serialize(),
		        function(data) {
			        var isErr = '';
			        if (data.isSuccess) {
			        } else {
				        isErr = 'error';
			        }
			        $.messager.alert('操作提示', data.msg, isErr);
			        $('#searchWorkPlaceInfoBut').trigger('click');
			        if (!(data.msg == '办公地点重复') && !(data.msg == '新增办公地点缺少市或区县字样信息') && !(data.msg == '新增办公地点格式不正确')) {
				        $('#addWorkPlaceInfoPanel').window('close');
			        }
		        })
	}
}

// 自定义 验证规则validType="selectValueRequired"
$.extend($.fn.validatebox.defaults.rules, {
	selectValueRequired : {
	    validator : function(value, param) {
		    if (value == "") {
			    return false;
		    } else {
			    return true;
		    }
	    },
	    message : '该下拉框为必选项'
	}
});

//删除网点
function deletedBaseArea(id) {	
	if (!id) {return;}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){   
	    	$.ajax({
	    		url : 'baseArea/deletedBaseArea',
	    		data : {
	    			id : id						
	    		},
	    		type:'POST',
	    		success : function(result){
	    			if (result.isSuccess) {
	    				$.messager.alert('操作提示', '操作成功');
	    				 $("#netWorkGrid").datagrid('reload');
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
function reloadChooseWorkPlace() {
	 $('#chooseWorkPlaceInfoPanel').find('input[id="tel"]').val("");
    $('#chooseWorkPlaceInfoPanel').find('input[id="cityNo"]').val("");
    $('#chooseWorkPlaceInfoPanel').find('input[id="site"]').val("");
    var queryParams = $(
    '#WorkPlaceInfoGrid').datagrid(
    'options').queryParams;
   // 电话
   queryParams.tel =  $('#chooseWorkPlaceInfoPanel').find('input[id="tel"]').val();
   queryParams.cityNo = $('#chooseWorkPlaceInfoPanel').find('input[id="cityNo"]').val();
   queryParams.site = $('#chooseWorkPlaceInfoPanel').find('input[id="site"]').val();
$('#WorkPlaceInfoGrid').datagrid(
    'options').queryParams = queryParams;
$("#WorkPlaceInfoGrid").datagrid('reload');
    $('#chooseWorkPlaceInfoPanel').window({
			modal : true
	});
}