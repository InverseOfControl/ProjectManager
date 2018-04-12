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
        		$(this).combobox('select', data[0].id);
        }
	});
	
    //营业网点
    $('#toolbar #salesDeptComb').combobox({
        url:'repayment/report/getAllSalesDept',
        valueField:'id',
        textField:'name',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].id);
        }
    });
    
	$('#list_result_repayment_div').hide();
	$('#list_result_Norepayment_div').hide();
	
	//设置默认时间
    $('#toolbar #repayDateStart').val(repayDateStart);
    $('#toolbar #repayDateEnd').val(repayDateEnd);
    
	// 查询按钮
	$('#searchBt').bind('click', search);
	// 导出按钮
	$('#exportExcelBt').bind('click', exportExcel);
	
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});
	
});

function search(){
	var productType = $('#toolbar #productComb').combobox('getValue');
	var repayDateStart = $('#toolbar #repayDateStart').val();
	var repayDateEnd = $('#toolbar #repayDateEnd').val();
	var salesDeptId = $('#toolbar #salesDeptComb').combobox('getValue');
	var repaymentType = $('#toolbar #repaymentType').combobox('getValue');
	
	if($('#toolbar #repaymentType').combobox('getValue')=="1"){ //查询有还款记录
		$("#list_result_repayment_div").css('display','block');
		$("#list_result_NoRepayment_div").css('display','none'); 
		  $('#list_result_repayment').datagrid({
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
		        url: 'repayment/report/list.json?repaymentType='+repaymentType+'&productType='+
		        productType+'&repayDateStart='+repayDateStart+'&repayDateEnd='+repayDateEnd+'&salesDeptId='+salesDeptId,
		        fitColumns: true,
		        border: false,
		        striped: true,
				pagination : true,
				pageSize : 10,
		        rownumbers: true,	
		        columns : [ [ {
					field : 'salesDeptName',
					title : '营业部'
				},{
					field : 'personName',
					title : '客户姓名'
				},
					{field : 'idnum' ,title : '身份证号' , formatter : function (value, row, index) {
		        	var idnum = row.idnum;	
		        	var idnumStr = "****" + idnum.substr(idnum.length-6);
		        	return idnumStr;
		        	}
				},
				{
					field : 'productId',title : '产品类型',formatter: function(value, row, index){
			          	return  formatEnumName(value,'PRODUCT_ID');
		           }
				},
				{
					field : 'pactMoney' ,
					title : '合同金额'
				 },
				 {
						field : 'time',
						title : '期限'
				 },
				 {
						field : 'curNum',
						title : '当前期数'
				  },
					{
						field : 'repayDay',
						title : '应还款日',
						formatter : function (value, row, index) {
				        	return formatDate(row.repayDay);
				        }
					},
					{
						field : 'repayAmount',
						title : '本期应还总额'
					},
					{
						field : 'principalAmt',
						title : '本期应还本金'
					},
					{
						field : 'interestAmt',
						title : '本期应还利息'
					},
					{
						field : 'evalRate',
						title : '本期应还评估费'
					},
					{
						field : 'referRate',
						title : '本期应还咨询费'
					},
					{
						field : 'managePart0Fee',
						title : '本期应还乙方管理费'
					},
					{
						field : 'managePart1Fee',
						title : '本期应还丙方管理费'
					},
					{
						field : 'risk',
						title : '本期应还风险金'
					},
					{
						field : 'tradeTime',
						title : '实际还款日',
						formatter : function (value, row, index) {
				        	return formatDate(row.tradeTime);
				        }
					},
					{
						field : 'tradeAmount',
						title : '实际还款总额'
					},
					{
						field : 'tradeCode', //convert
						title : '还款性质',formatter: function(value, row, index){
				          	 if(value == '1001'){
				          		 return '正常还款';
				          	 }else if(value == '3001'){
				          		 return '一次性(提前还款)';
				          	 }else{ 
				          		 return '';
				          	 }
				        }
					},
					{
						field : 'payType', //convert
						title : '还款方式',formatter: function(value, row, index){
				          	return  formatEnumName(value,'TRADE_TYPE');
				        }
					},
					{
						field : 'tradeAmountBegin', 
						title : '期初预收'
					},
					{
						field : 'reliefAmount', 
						title : '减免金额'
					},
					{
						field : 'penaltyInterestAmt', 
						title : '罚息'
					},
					{
						field : 'overdueInterestAmt', 
						title : '逾期利息'
					},
					{
						field : 'overduePrincipal', 
						title : '逾期本金'
					},
					{
						field : 'overdueCurManagePart0Fee', 
						title : '逾期乙方管理费'
					},
					{
						field : 'overdueCurManagePart1Fee', 
						title : '逾期丙方管理费'
					},
					{
						field : 'overdueCurReferRate', 
						title : '逾期咨询费'
					},
					{
						field : 'overdueCurEvalRate', 
						title : '逾期评估费'
					},
					{
						field : 'overdueCurRisk', 
						title : '逾期风险金'
					},
					{
						field : 'curInterestAmt', 
						title : '当期利息'
					},
					{
						field : 'curPrincipal', 
						title : '当期本金'
					},
					{
						field : 'curManagePart0Fee', 
						title : '当期乙方管理费'
					},
					{
						field : 'curManagePart1Fee', 
						title : '当期丙方管理费'
					},
					{
						field : 'curReferRate', 
						title : '当期咨询费'
					},
					{
						field : 'curEvalRate', 
						title : '当期评估费'
					},
					{
						field : 'curRisk', 
						title : '当期风险金'
					},
					{
						field : 'penalty', 
						title : '提还违约金'
					},
					{
						field : 'curRefundPart0Fee', 
						title : '退费(乙方管理费)'
					},
					{
						field : 'tradeAmountEnd', 
						title : '期末预收'
					},
					{
						field : 'constractSrc', 
						title : '合同来源',
						formatter : function(value, row, index) {				
							return '证大P2P';
						}
					},
					{
						field : 'remark', 
						title : '备注',
					}
				] ]
			});
		// 设置分页控件
			var p = $('#list_result_repayment').datagrid('getPager');
				p = $('#list_result_repayment').datagrid('getPager');
				$(p).pagination({
					pageList : [ 10, 20, 50 ]
			});
		 setFirstPage("#list_result_repayment");
	}else if($('#toolbar #repaymentType').combobox('getValue')=="0"){ //无还款记录
		$("#list_result_NoRepayment_div").css('display','block'); 
		$("#list_result_repayment_div").css('display','none');

		$('#list_result_NoRepayment').datagrid({
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
	    	    url: 'repayment/report/list.json?repaymentType='+repaymentType+'&productType='+
	    	    productType+'&repayDateStart='+repayDateStart+'&repayDateEnd='+repayDateEnd+'&salesDeptId='+salesDeptId,
		        fitColumns: true,
		        border: false,
		        pagination: true,
		        pageSize: 10,
		        striped: true,
		        rownumbers: true,	
		        columns : [ [ 
					{
						field : 'salesDeptName',
						title : '营业部'
					},{
						field : 'personName',
						title : '客户姓名'
					},
						{field : 'idnum' ,title : '身份证号' , formatter : function (value, row, index) {
						var idnum = row.idnum;	
						var idnumStr = "****" + idnum.substr(idnum.length-6);
						return idnumStr;
						}
					},
					{
						field : 'productId',title : '产品类型',formatter: function(value, row, index){
					      	return  formatEnumName(value,'PRODUCT_ID');
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
							field : 'time',
							title : '期限'
					 },
					 {
							field : 'curNum',
							title : '当前期数'
					  },
					  {
							field : 'repayDay',
							title : '应还款日',		
							formatter : function (value, row, index) {
					        	return formatDate(row.repayDay);
					        }
						},
						{
							field : 'repayAmount',
							title : '本期应还总额'
						},
						{
							field : 'principalAmt',
							title : '本期应还本金'
						},
						{
							field : 'interestAmt',
							title : '本期应还利息'
						},
						{
							field : 'evalRate',
							title : '本期应还评估费'
						},
						{
							field : 'referRate',
							title : '本期应还咨询费'
						},
						{
							field : 'managePart0Fee',
							title : '本期应还乙方管理费'
						},
						{
							field : 'managePart1Fee',
							title : '本期应还丙方管理费'
						},
						{
							field : 'risk',
							title : '本期应还风险金'
						},
						{
							field : 'constractSrc', 
							title : '合同来源',
							formatter : function(value, row, index) {				
								return '证大P2P';
							}
						}
				] ]
			});
		// 设置分页控件
		var p = $('#list_result_NoRepayment').datagrid('getPager');
		$(p).pagination({
			pageList : [ 10, 20, 50 ]
		});
	   setFirstPage("#list_result_NoRepayment");
	}
}

function formatDate(value){
	if(value && value.length>=10){
		return value.substr(0, 10);
	}else{
		return "";
	}
}

 
