var url;
$(function () {
	//借款类型
	$('#toolbar #productComb').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].productType);
        }
    });
    // 借款状态
    $('#toolbar #loanStatusContractConfirmComb').combobox({
    	url:'audit/contractConfirm/getLoanStatusList',
    	valueField:'value' ,
    	textField:'name',
    	 onLoadSuccess:function(){
    		  var data = $(this).combobox('getData');    		
    	       $(this).combobox('select',data[0].value);
    	 }
    });
    $('#toolbar #extensionTimeComb').combobox({
        url:'apply/getExtensionTimeList',
        valueField:'value',
        textField:'name',
        onLoadSuccess:function(){
             var data = $(this).combobox('getData');
                $(this).combobox('select', data[0].value);
            
        }
    });
    //设置还款日期
    var repay = today;
  
    $('#toolbar #repayDate').val(repay);
    
	// 查询按钮
	$('#searchBt').bind('click', search);
	 //校验
    $.extend($.fn.validatebox.defaults.rules, { 
    integerCheck:{
        validator:function(value){
            return /^[+]?[0-9]\d*$/.test(value);
        },
        message: '请输入整数'
    }  
      });
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
        url: 'after/repayTrial/list.json',
        fitColumns: true,
        border: false,
        singleSelect:true,
        pagination: true,
        pageSize: 10,
        striped: true,
        fit:true,
        rownumbers: true,	
        columns : [ [ {
			field : 'manageServiceName',
			title : '管理客服'
		},{
			field : 'personName',
			title : '客户姓名'
		},
			{field : 'personIdnum' ,title : '身份证号' , formatter : function (value, row, index) {
        	var idnum = row.personIdnum;	
        	var idnumStr = "****" + idnum.substr(idnum.length-4, 4);
        	return idnumStr;
        	}
		},
		{
			field : 'personMobilePhone',
			title : '手机号码'
		}
		, 
		{
			field : 'productId',title : '产品类型',formatter: showExtensionLoanLedgerRepay
		},{
			field : 'loanType',title : '子类型',formatter: function(value, row, index){
	          	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
        }
		},
		{
			field : 'bankAccount' ,title : '银行账号' , formatter : function (value, row, index) {
			if(value!=null){
	        	var bankAccount = row.bankAccount;	
	        	var bankAccountStr = "****" + bankAccount.substr(bankAccount.length-4, 4);
	        	return bankAccountStr;
			}
			else{
	          	return value;
	          	}
        }
		},{
			field : 'bankName',
			title : '开户行'
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
		},{
			field : 'currTermText',
			title : '当前期数'
		},{
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
			field : 'repayAllAmount' ,title : '应还总额' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'currAmount' ,title : '当期应还' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'overdueAllAmount' ,title : '逾期应还' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'fine' ,title : '罚息' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'penalty' ,title : '提还违约金' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          }
		},
		{
			field : 'accAmount' ,title : '期末预收' , formatter : function (value, row, index) {
          	if(value!=null){
					 return  value.toFixed(2);
          	}else{
          	return value;
          	}
          },width : 160
		}
		] ]
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
function search(){
	var queryParams = $('#list_result').datagrid('options').queryParams;
	queryParams.personFuzzyName = $('#toolbar #personFuzzyName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.repayDate = $('#toolbar #repayDate').val();
	queryParams.repaymentType = $('#toolbar #repaymentType').combobox('getValue');
	queryParams.personMobilePhone = $('#toolbar #personMobilePhone').val();
	queryParams.extensionTime = $('#toolbar #extensionTimeComb').combobox('getValue');
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
	if($('#toolbar #repaymentType').combobox('getValue')=="1"){
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
		        url: 'after/repayTrial/list.json',
		        fitColumns: true,
		        border: false,
		        singleSelect:true,
		        pagination: true,
		        pageSize: 10,
		        striped: true,
		        rownumbers: true,	
		        columns : [ [ {
					field : 'manageServiceName',
					title : '管理客服'
				},{
					field : 'personName',
					title : '客户姓名'
				},
					{field : 'personIdnum' ,title : '身份证号' , formatter : function (value, row, index) {
		        	var idnum = row.personIdnum;	
		        	var idnumStr = "****" + idnum.substr(idnum.length-4, 4);
		        	return idnumStr;
		        	}
				},
				{
					field : 'personMobilePhone',
					title : '手机号码'
				}
				, 
				{
					field : 'productId',title : '产品类型',formatter: showExtensionLoanLedgerRepay
				},{
					field : 'loanType',title : '子类型',formatter: function(value, row, index){
			          	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
		        }
				},
				{
					field : 'bankAccount' ,title : '银行账号' , formatter : function (value, row, index) {
						if(value!=null){
				        	var bankAccount = row.bankAccount;	
				        	var bankAccountStr = "****" + bankAccount.substr(bankAccount.length-4, 4);
				        	return bankAccountStr;
						}
						else{
				          	return value;
				          	}
		        }
				},{
					field : 'bankName',
					title : '开户行'
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
				},{
					field : 'currTermText',
					title : '当前期数'
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
					field : 'repayAllAmount' ,title : '应还总额' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'currAmount' ,title : '当期应还' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'overdueAllAmount' ,title : '逾期应还' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'fine' ,title : '罚息' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'penalty' ,title : '提还违约金' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'accAmount' ,title : '期末预收' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				}] ]
			});
			
	}else if($('#toolbar #repaymentType').combobox('getValue')=="2"){
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
		        url: 'after/repayTrial/list.json',
		        fitColumns: true,
		        border: false,
		        singleSelect:true,
		        pagination: true,
		        pageSize: 10,
		        striped: true,
		        rownumbers: true,	
		        columns : [ [ {
					field : 'manageServiceName',
					title : '管理客服'
				},{
					field : 'personName',
					title : '客户姓名'
				},
					{field : 'personIdnum' ,title : '身份证号' , formatter : function (value, row, index) {
		        	var idnum = row.personIdnum;	
		        	var idnumStr = "****" + idnum.substr(idnum.length-4, 4);
		        	return idnumStr;
		        	}
				},
				{
					field : 'personMobilePhone',
					title : '手机号码'
				}
				, 
				{
					field : 'productType',title : '产品类型',formatter: showExtensionLoanLedgerRepay
				},{
					field : 'loanType',title : '子类型',formatter: function(value, row, index){
			          	return  formatEnumName(value,'PRODUCT_SUB_TYPE');
		        }
				},
				{
					field : 'bankAccount' ,title : '银行账号' , formatter : function (value, row, index) {
						if(value!=null){
				        	var bankAccount = row.bankAccount;	
				        	var bankAccountStr = "****" + bankAccount.substr(bankAccount.length-4, 4);
				        	return bankAccountStr;
						}
						else{
				          	return value;
				          	}
		        }
				},{
					field : 'bankName',
					title : '开户行'
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
				},{
					field : 'currTermText',
					title : '当前期数'
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
					field : 'repayAllAmount' ,title : '应还总额' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'oneTimeRepayment' ,title : '一次性结清' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'overdueAllAmount' ,title : '逾期应还' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'fine' ,title : '罚息' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'penalty' ,title : '提还违约金' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				},
				{
					field : 'accAmount' ,title : '期末预收' , formatter : function (value, row, index) {
		          	if(value!=null){
							 return  value.toFixed(2);
		          	}else{
		          	return value;
		          	}
		          }
				}] ]
			});
	}
	
}



