/**
 * 
 */
$(function() {
	// 获取所有客户经理
	$("#toolbar #crmList").combobox({
		url:'after/abrogateOffer/getAllCrmList',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){    
	  		  var data = $(this).combobox('getData');    		
	  	       $(this).combobox('select',data[0].id);
		}
	});
	
	$("#abrogateOfferGenePageTb").datagrid({
		url : 'after/abrogateOffer/getAbrogateGenePage',
		fitColumns : true,
        border : false,
        singleSelect:true,
        pagination : true,
        striped: true,
        pageSize:10,
        rownumbers : true,
        fit:true,
        columns : [[
                    {
        			field : 'person.name',
        			title : '姓名',
        			formatter : function(value, rowData, rowIndex) {		
        				return rowData.person.name;
//        				return '<a style="font-weight:bolder" href="javascript:void(0)" onclick="browse('+rowData.id+ ',' + rowData.productId  + ',' + rowData.productType + ',' + rowData.extensionTime +')">' + rowData.person.name + '</a>';
        			}
                }, 
        		  {
        			field : 'productName',
        			title : '产品类型',
        			formatter: function(value, row, index){
        	         	return  row.product.productTypeName;
                  }
        		},
        		{
        			field : 'person.idnum',
        			title : '身份证号',
        				formatter: function(value, row, index){
            	         	return  row.person.idnum;
                      }
        		}, 
        		{
        			field : 'extensionTime',
        			title : '展期期次',
        			formatter : function(value, row, index) {
        				if(value == 0) {
        					return "无";
        				} else {
        					return value;
        				}
        			}
        		},
        		
        		{
        			field : 'service.name',
        			title : '客服',
        			formatter: function(value, row, index){
        	         	return  row.service.name;
                  }
        		},  {
        			field : 'auditMoney',
        			title : '审批金额(元)'
        		}, {
        			field : 'auditTime',
        			title : '审批期限'
        		}, {
        			field : 'requestDate',
        			title : '申请日期',
        			formatter:formatRequestDate
        		}, {
        			field : 'submitDate',
        			title : '提交时间'
        		},
        		 {
        			field : 'auditDate',
        			title : '签批日期'
        		},{
        			field : 'assessor.name',
        			title : '复核人员',
        			formatter: function(value, row, index){
        	         	return  row.assessor.name;
                  }
        		}, {
        			field : 'status',
        			title : '状态',
        			formatter: function(value, row, index){
        	          	return  formatEnumName(value,'LOAN_STATUS');
        			}		
        		}
                   ]]
	});
	
	// 设置分页控件
    var p = $('#abrogateOfferGenePageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
	

	// 点击【查询】按钮
	$('#searchBtn').bind('click', search);
	
	// 点击【查询】按钮
	$('#generateBtn').bind('click', checkValidAmount);
	
	$(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
	
});

function search() {
	var queryParams = $('#abrogateOfferGenePageTb').datagrid('options').queryParams;
	queryParams.personName = $('#toolbar #personName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
//	queryParams.mobilePhone = $('#toolbar #mobilePhone').val();
	 setFirstPage("#abrogateOfferGenePageTb");
	$('#abrogateOfferGenePageTb').datagrid('options').queryParams = queryParams;
	$("#abrogateOfferGenePageTb").datagrid('reload');
}

function checkValidAmount() {
	var rows = $('#abrogateOfferGenePageTb').datagrid('getSelected');
	var loanId;
	if (rows != null) {
			loanId = rows.id;
	} else {
		$.messager.show({
			title : '提示',
			msg : '请选择一条数据！'
		});
		return false;
	}
	$.ajax({
		type:"POST",
		url:"after/abrogateOffer/checkValidOffer",
		data:{
			loanId:loanId
		},
		success: function(message){
			if(message.result == "error") {
				$.messager.alert("操作提示", message.message);
			}else if(message.result == "success") {
				submitAbrogateOffer(loanId);
			}
		}
	});
}

function submitAbrogateOffer(loanId){
	$.ajax({
		type:"POST",
		url:"after/abrogateOffer/submitAbrogateGene",
		data:{
			loanId : loanId,
		},
		success: function(message){
            if(message.result=="success"){
                $.messager.show({
                    title:'提示',
                    msg:'生成成功！',
                    showType:'slide'
                });
                $("#abrogateOfferGenePageTb").datagrid('reload');
        	} else {
        		 $.messager.show({
                        title:'提示',
                        msg:message.message,
                        showType:'slide'
                    });
        	}
        },
        error:function(){
            $.messager.show({
                title:'提交',
                msg:'申请失败！',
                showType:'slide'
            });
        }
	});
}

function formatRequestDate(value,row,index){	
	 return getYMD(value);
}

function getYMD(datetime){	

	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	return datetime.substr(0, 10);
}