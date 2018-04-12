var url;

$(function () {
	// 列表
    $('#list_result').datagrid({
    	  onLoadSuccess:function(data){ 
    		  if(data.total==0)
    		  {
    		    $.messager.show({
                    title:'结果',
                    msg:'没有数据！',
                    showType:'slide'
                });
    		  }
    	  },
        url: 'organ/management/list.json',
        fitColumns: true,
        border: false,
        singleSelect:true,
        pagination: true,
        fit:true,
        pageSize: 10,
        striped: true,
        rownumbers: true,	
        columns : [ [ 
		{
			field : 'code',
			title : '机构内部编号'
		},                    
        {
			field : 'name',
			title : '机构名称',  
			formatter : linkDetail
		},
	    {
	    	field : 'legalRepresentative',
	    	title : '法定代表人姓名'
	    },
	    {
	    	field : 'signedDate',
	    	title : '签约日期',formatter : function (value, row, index) {
	        	return formatSignDate(value);
	        }
	    },
	    {
	    	field : 'margin',
	    	title : '保证金'
	    },
	    {
	    	field : 'cash',
	    	title : '挂账金额'
	    },
	    {
	    	field : 'operations',
	    	title : '协议打印',
	    	formatter : formatOperationsCell
	    }	
		] ]
	});
    // 设置分页控件
    var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
	// 查询按钮
	$('#searchBt').bind('click', search);
    
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	
  $.extend($.fn.validatebox.defaults.rules, {
	//金额校验
	moneyCheck:{
	    validator: function (value) {
	        return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
	     },
	     message:'请输入正确的金额'
	} ,
	  });
});

function search(){	
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.code = $('#toolbar #code').val();
	queryParams.name = $('#toolbar #name').val();
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
};

/** 操作 */
function formatOperationsCell(value, row, index) {
	var operations = row.operations.split("|");
	var formattedOperations = "";
	for (var i = 0; i < operations.length; i++) {
		var operation = operations[i];
		if (operation == "attachment") {
			operation = '<a href="javascript:void(0)" onclick="showOrganAttachmentDlg('+row.id +')">合作协议</a>';
		} else if (operation == "acctInfo") {
			operation = '<a href="javascript:void(0)" onclick="showOrganCardInfo('+row.id +')">帐卡信息</a>';
		} 
		
		if(formattedOperations =="") {
			formattedOperations = operation;
		} else {
			formattedOperations = formattedOperations + "   " + operation;
		}
	}
	return formattedOperations;
};

function showOrganAttachmentDlg(id){
     var organId=id+10000000000000; //防止给借款LOAN_ID冲突+加上一个14为的前缀
	 window.open (rayUseUrl+"organ/management/organImageUploadView/"+organId, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}

function showOrganCardInfo(id){
	 window.open (rayUseUrl+"organ/management/showOrganCard/"+id, "newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=1");
}
/** 新建产品页面**/
function organAdd() {
	 var h = $(window).height() ;
	 var w = $(window).width() ;
	var url = 'organ/management/organAdd';
	$('#organAddDlg').dialog({
		title : '新增机构',
		closed : false,
		cache : false,
		href : url,
		modal : true,
		height: h*0.8,
		width:w*0.8
	});
}



/** 查看机构页面**/
function linkDetail(value,row,index) {
	var fontStyle = 'font-weight:bolder';
	return '<a style='+fontStyle+' href="javascript:void(0)" onclick="browseOrganDetail('+row.id+ ')">' + row.name + '</a>';
}

function browseOrganDetail(id){
	loadOrganDetail(id);
}

function formatSignDate(value){
	if(value && value.length>=10){
		return value.substr(0, 10);
	}else{
		return "";
	}
}
