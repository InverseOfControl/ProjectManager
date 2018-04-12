/**
 * 
 */
$(function() {
	   $.extend($.fn.validatebox.defaults.rules, {	    	
	    //金额校验
	    moneyCheck:{
	        validator: function (value) {
	            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
	         },
	         message:'请输入正确的金额'
	    } 
	  });
	
	// 查询按钮
	$('#searchBt').bind('click', search);
	$('#uploadBtn').bind('click', openUploadFileDLG);
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
        url: 'after/repayEntry/list.json',
        fitColumns: true,
        border: false,
        singleSelect:true,
        pagination: true,
        pageSize: 10,
        fit:true,
        striped: true,
        rownumbers: true,	
        columns : [ [ {
			field : 'salesDeptName',
			title : '营业部'
		},{
			field : 'personName',
			title : '借款人'
		},
		{
			field : 'productId',title : '产品类型',formatter: function(value, row, index){
	          	return  formatEnumName(value,'PRODUCT_ID');
        }
		},
		{field : 'personIdnum' ,title : '身份证号' , formatter : function (value, row, index) {
        	var idnum = row.personIdnum;	
        	var idnumStr = "****" + idnum.substr(idnum.length-4, 4);
        	return idnumStr;
        	}
		},
		{
			field : 'pactMoney' ,title : '合同金额' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'repayPeriod',
			title : '期限'
		}, {
			field : 'loanStatus' ,title : '还款状态' , formatter: function(value, row, index){
        	return  formatEnumName(value,'LOAN_STATUS');
        }},{
			field : 'isOneTimeRepayment',
			title : '一次性结清'
		},
		{
			field : 'reliefOfFine' ,title : '减免金额' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
        {field : 'operation',title : '操作', formatter:formatOperationsCell,width : 600}] ]
	});
    // 设置分页控件
    var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
    //按Enter键查询
  	$(document).keydown(function(e) {
  		if (e.which == 13){	
  			$('#searchBt').click();
  		}
  	});
});

//打开上传文件窗口
function openUploadFileDLG() {
	$("#fileName").val('');
	$('#fileUploadDialog').dialog('open').dialog('setTitle', '批量导入');
	$('#fileUploadForm').form('clear');
}
/** 操作 */
function formatOperationsCell(value,row,index){
	return '<a  href="javascript:void(0)" onclick="repayInfo('+row.loanId+')">' + row.operations + '</a>';

}
function repayInfo(loanId){
	$.ajax({	        
        url: "after/repayEntry/repayEdit",
        type : "POST",
        dataType:"json",
        data: {	  
        	loanId:loanId
        },
        success:function(repayEntryDetailsVO){
        	   
        	var url = 'after/repayEntry/repayEntryModify';
        	   
        	  $('#repayModify').dialog({
                  title: '还款信息',
                  closed: false,  
                  cache: false,
                  href: url,
                  modal: true,
                  onLoad : function(){
                	  loadEditRepayEntry(repayEntryDetailsVO);	
          		}		
				
			});
        	  
        },
        error:function(data){
	 		 $.messager.show({
					title: 'warning',
					msg: data.responseText
				});
		}
    });
}
function ajaxFileUpload(saveBtn) {
    $.ajaxFileUpload({
            url: 'after/repayEntry/upload', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
            	$.messager.show({
					title: 'warning',
					msg: data.responseText
				});
            	
 	        },  
 	       error:function(data){
		 		 $.messager.show({
						title: 'warning',
						msg: data.responseText,
						timeout :0
					});
		   }
      });
    $('#fileUploadDialog').dialog('close');
}
function search(){
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.personFuzzyName = $('#toolbar #personFuzzyName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}