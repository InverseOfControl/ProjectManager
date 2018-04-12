$(function () {
	// 初始化营业网点表格
	$('#userAreaGrid').datagrid({
		url : 'baseArea/getSearchJson',
		singleSelect:true,
		fitColumns : true,
		pagination : true,
		striped : true,
		pageSize :20,
		//fit:true,
		rownumbers : true,
		columns : [ [ {
			field : 'code',
			title : '网点编号',
			formatter : function(value, row, index) {
				return value;
			},
			width : 70
		}, {
			field : 'fullName',
			title : '网点全称',
			width : 170
		}, 
		{
			field : 'id',
			title : '网点id',
			hidden : true,
			width : 170
		},{
			field : 'name',
			title : '网点名称',
			width : 60,
			formatter : function(value, row, index) {
				return value;
			}
		}, {
			field : 'identifier',
			title : '网点类型',
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
			},
			width : 40
		} ,{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = '';
				oper += '<a href="javascript:void(0)" onclick="selectedBaseArea({\'id\':' + row.id + ',\'name\':\'' + row.name + '\'});">选择</a>';
				return oper;
			},
			width : 50
		} ] ]
	});
	
	
	
	$('#searchAreaBaseBut').click(function() {
		var queryParams = $('#userAreaGrid').datagrid('options').queryParams;
		//网点名称
		queryParams.fullName = $('#baseAreaName').val();
		
		//地区ID
		var areaId = $('#areaCombo').combobox('getValue');
		//城市ID
		var cityId = $('#cityCombo').combobox('getValue');
		//部门/营业部ID
		var branchId = $('#branchCombo').combobox('getValue');
		
		if (branchId) {
			areaId = '';
			cityId = '';
		} else if (cityId) {
			areaId = '';
		}
		queryParams.areaId = areaId;
		queryParams.cityId = cityId;
		queryParams.salesDeptId = branchId;
		$('#userAreaGrid').datagrid('options').queryParams = queryParams;
	    $("#userAreaGrid").datagrid('reload');
	})
	

    //清空查询条件
    $('#clear').click(function(){
    	loadComBoBox();
    	$('#baseAreaName').val("");
    	$('#userAreaGrid').datagrid('options').queryParams = {};
    	 $("#userAreaGrid").datagrid('reload');
    });
	loadComBoBox();
	function  loadComBoBox(){

		
		//填充地区下拉框数据项
		var emptyData = [{"id":"","name":"所有"}];
		 $.ajax({
			            type: "POST",  
			            url: 'baseArea/getSearchJson1?companyId=2',  
			            cache: false,  
			            dataType : "json",  
			            success: function(data){
			            	data.unshift(emptyData[0]);
			            $("#areaCombo").combobox("loadData",data);  
			                     }  
			                });       
		
		$('#areaCombo').combobox({   
			editable:false, //不可编辑状态  
			cache: false,  
			panelHeight: '150',  
			valueField:'id',     
			textField:'name',
			onSelect: function(){  
				 $("#cityCombo").combobox("setValue",'');
				 $("#branchCombo").combobox("setValue",'');
				 $("#cityCombo").combobox("loadData",emptyData);  
				 $("#branchCombo").combobox("loadData",emptyData);  
				 var areaId = $(this).combobox('getValue');
		              if (areaId) {
			          $.ajax({  
			            type: "POST",  
			            url: 'baseArea/getSearchJson1?areaId=' + areaId,  
			            cache: false,  
			            dataType : "json",  
			            success: function(data){
			            			data.unshift(emptyData[0]);
			            		$("#cityCombo").combobox("loadData",data);  
			                     }  
			                });       
			} 
			},
			onLoadSuccess : function() {
				
				
				
				
			}
		})
		$('#cityCombo').combobox({   
			editable:false, //不可编辑状态  
			cache: false,   
			panelHeight: '150',  
			valueField:'id',     
			textField:'name',
			onSelect: function(){  
				$("#branchCombo").combobox("setValue",'');  
				 $("#branchCombo").combobox("loadData",emptyData);
				var cityId = $(this).combobox('getValue');
				if(cityId) {
				
				$.ajax({  
		            type: "POST",  
		            url: 'baseArea/getSearchDepts?cityId=' + cityId,  
		            cache: false,  
		            dataType : "json",  
		            success: function(data){  
		            	data.unshift(emptyData[0]);
		            $("#branchCombo").combobox("loadData",data);  
		                     }  
		                });       
				}
			}
		})
		
		$('#branchCombo').combobox({   
			editable:false, //不可编辑状态  
			cache: false,  
			panelHeight: '150',  
			valueField:'id',     
			textField:'name',
			onHidePanel: function(){  
				
			}
		})
		
		//初始化城市和部门下拉框数据项

		$('#cityCombo').combobox('loadData',emptyData);
		$('#branchCombo').combobox('loadData',emptyData);
		
	}
})

//选中营业网点赋值操作
function selectedBaseArea(baseArea) {
	var id = baseArea.id;
	var name = baseArea.name;
	$('#dlg_add').find('input[name="areaId"]').val(id);
	$('#dlg_add').find('input[name="fullName"]').val(name);
	$('#chooseBaseAreaPanel').window('close');
}



