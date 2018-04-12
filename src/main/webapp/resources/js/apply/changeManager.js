/**
 * 
 */

$(function() {
	
	// 判断用户productId，对"借款子类型"进行相应设置
	 $.ajax({
		 url : "changeManager/checkProductType",
		 success : function (productType) {
			 // 如果为小企业贷，则借款子类型下拉框不显示(灰掉)
			 if(productType == "seLoan") {
				 $("#productSubType").combobox({
	    			 disabled:true
	    		 });
			}
		 }
	 });
	
	$("#toolbar #managerList").combobox({
		url:'changeManager/getManagerList?flage=all',
		valueField:'id',
        textField:'name',
        onLoadSuccess:function(){    
  		  var data = $(this).combobox('getData');    		
  	       $(this).combobox('select',data[0].id);
  	 }
	});
	
	$("#manageServicePageTb").datagrid({
		url : 'changeManager/getManagerPage',
		fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        fit:true,
        columns : [[
                    {field : 'ck',checkbox : true},
                    {field : 'service.name' ,title : '签约客服' , formatter : function (value, row, index) {
                    	return  row.service.name;
                    }, width : 100},
                    {field : 'manageService.name' ,title : '管理客服' , formatter : function (value, row, index) {
                    	return row.manageService.name;
                    }, width : 100},
                    {field : 'person.name' ,title : '借款人' , formatter : function (value, row, index) {
                    	return row.person.name;
                    }, width : 100},
                    {field : 'person.idnum' ,title : '身份证号' , formatter : function (value, row, index) {
                    	var idnum = row.person.idnum;
                    	var idnumStr = "****" + idnum.substr(idnum.length-4, 4);
                    	return idnumStr;
                    }, width : 120},
                    {field : 'loanType' ,title : '借款子类型' , formatter: function(value, row, index){
                    	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
                    }, width : 80},
                    {field : 'pactMoney' ,title : '合同金额' , formatter : function (value, row, index) {
                    	if(value!=null){
       					 return  value.toFixed(2);//保留两位小数
                    	}else{
                    		return value;
                    	}
                    }, width : 120},
                    {
            			field : 'extensionTime',
            			title : '展期期次',
            			formatter : function(value, row, index) {
            				if(value == 0) {
            					return "无";
            				} else {
            					return value;
            				}
            			},
            			width : 60
            		}, 
                    {field : 'auditTime' ,title : '期数' , width : 50}
                    ]]
	});
	
	 // 设置分页控件
    var p = $('#manageServicePageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
	
	// 点击【确定】按钮
	$('#searchBtn').bind('click', search);
	// 点击【变更】按钮
	$('#changeBtn').bind('click', openChange);
	// 确认变更
	// $('#change').bind('click', submitChange);
	// 回车事件
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
});

function search() {
	var queryParams = $('#manageServicePageTb').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	queryParams.loanType = $('#toolbar #productSubType').combobox('getValue');
	queryParams.managerId = $('#toolbar #managerList').combobox('getValue');
	  setFirstPage("#manageServicePageTb");
	$('#manageServicePageTb').datagrid('options').queryParams = queryParams;
	$("#manageServicePageTb").datagrid('reload');
}

function openChange() {
	var rows = $('#manageServicePageTb').datagrid('getChecked');
	if(rows.length ==0) {
		parent.$.messager.show({
			title: '提示',
			msg: "请选择一行数据"
		});
		return false;
	}
	
		$('#changeDLG').dialog('open').dialog('setTitle','变更管理客服');
		$("#changeDLG #changeManagerList").combobox({			
			url:'changeManager/getManagerList?flage=notAll',
			valueField:'id',
			textField:'name',
			onLoadSuccess:function(){    
				var data = $(this).combobox('getData');    		
				$(this).combobox('select',data[0].id);
			}
		});
}

//【变更】弹窗确定按钮
function submitChange() {
	var rows = $('#manageServicePageTb').datagrid('getChecked');
	if (rows.length > 0) {
		var ids = "";
		$.each(rows,function(i,n){
			if(i != 0){
				ids += "," ;
			}
			ids += (n.id) ;
		});
		var manageId = $('#changeDLG #changeManagerList').combobox('getValue');
		$.post('changeManager/doChange',{idList:ids,managerId:manageId},function(result){
			 if (result=='success'){
				parent.$.messager.show({
					title : '提示',
					msg : '变更成功！'
				});
				$('#changeDLG').dialog().dialog('close');
				$('#manageServicePageTb').datagrid('reload'); // reload the user data
			 } else {
				 parent.$.messager.show({ // show error message
					 title: 'Error',
					 msg: result.errorMsg
				 });
			 }	
		});
		
		
	}
}

// 【变更】弹窗取消按钮时间
function cancelChange() {
	$('#changeDLG').dialog().dialog('close');
}