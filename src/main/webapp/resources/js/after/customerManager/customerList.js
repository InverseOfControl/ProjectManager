var url;

$(function() {
	// 点击【查询】按钮
	$('#searchBtn').bind('click', search);
	
	$(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBtn').click();
	    	}
	    });
	
	//默认关闭提现弹出框
	$('#showTiXianDlg').window('close');
	
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
	//校验提现金额
    $.extend($.fn.validatebox.defaults.rules, {
	//金额校验
	    moneyCheck:{
	        validator: function (value) {
	            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
	         },
	         message:'请输入正确的金额'
	    }
      });
});

//提现
function showTiXian(personName,idNum,contractNo,cash,loanId,extenId){
	var h = $(window).height() ;
	$('#showTiXianDlg').dialog({
			title : '提现',
			inline: true,
			closed : false,
			cache : false,
			maximizable: true,
			height: h*0.4
	});
	$('#showTiXianDlg').form('clear');
	$('#tiXianForm').find('input[name="personName"]').val(personName);
	$('#tiXianForm').find('input[name="idNum"]').val(idNum);
	$('#tiXianForm').find('input[name="cash"]').val(cash.toFixed(2));
	$('#tiXianForm').find('input[name="contractNo"]').val(contractNo); 
	$('#tiXianForm').find('input[name="loanId"]').val(loanId); 
	$('#tiXianForm').find('input[name="extenId"]').val(extenId); 
}

function search() {
	var personName = $('#toolbar #personName').val();
	var idNum = $('#toolbar #idnum').val();
	var contractNo = $('#toolbar #contractNo').val();
//	alert(personName+"||"+idNum+"||"+contractNo);
	if(personName.length == 0 && idNum.length == 0 && contractNo.length == 0){
		$.messager.alert('操作提示', '请至少输入一个查询条件', true);
		return;
	}
	
	$("#customerTb").datagrid({
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
		url : 'after/customerManager/getCustomerList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
	    
        queryParams: {
        	personName : $('#toolbar #personName').val(),
        	idNum : $('#toolbar #idnum').val(),
        	contractNo : $('#toolbar #contractNo').val(),
		},
        columns : [[
                    {field : 'personName' ,title : '客户姓名' ,width : 50,
            			formatter: function(value, row, index){
              	          	return '<a style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="openTab(\''+value+'\','+row.loanId+','+row.personId+','+row.extenId+','+row.extensionTime+')" >' + value + '</a>';
              	        }
                    },
                    {field : 'idNum' ,title : '身份证号' ,width : 80},
                    {
                    	field : 'productName' ,
                    	title : '产品类型' ,
                    	width : 50,
            			formatter: function(value, row, index){
            				return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger2('+row.loanId+','+row.extenId+','+row.extensionTime+')">'+row.productName+'</a>';
            			}
                    },
                    {
                    	field : 'extensionTime' ,
                    	title : '展期期次' ,
                    	width : 50,
            			formatter: function(value, row, index){
            				if(value ==null){
            					return "无";
            				}else{
            					return value;
            				}
            			}
                    },
                    {
                    	field : 'cash' ,
                    	title : '账户余额（元）' , 
                        width : 50,
                    	formatter : function (value, row, index) {
	                    	if(value!=null){
	       					 	return  value.toFixed(2);//保留两位小数
	                    	}else{
	                    		return value;
	                    	}
                    	}
                    },
                    {field : 'contractNo' ,title : '合同编号' , width : 80},
                    {field : 'operation' ,title : '操作' , width : 50,
            			formatter: function(value, row, index){
            				var cash = row.cash;
//            				var cash2 = row.cash;
//            				cash2 = cash2.toFixed(2);//保留两位小数
//            				alert(cash2);
            				if(cash != null && cash > 0){
                				return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showTiXian(\''+row.personName+'\','+'\''+row.idNum+'\','+'\''+row.contractNo+'\','+row.cash+','+row.loanId+','+row.extenId+')">提现</a>';
            				}
              	        }
                    }
                    ]]
	});
	
	// 设置分页控件
	var p = $('#customerTb').datagrid('getPager');
		$(p).pagination({
			pageList : [ 10, 20, 50 ]
	});
	 setFirstPage("#customerTb");
}

function showLoanLedger2(id,extenId,extensionTime){
	var h = $(window).height() ;
	$('#showLoanLedgerDlg').dialog({
			title : '借款台账',
			inline: true,
			closed : false,
			cache : false,
			maximizable: true,
			height: h*0.8
	});
	var url = 'repaymentPlan/repaymentPlanLedgerExtensionList/' + extenId;
	if(extensionTime==0)
	{
		url = 'repaymentPlan/repaymentPlanLedgerList/' + id;
	}
	 
	
	$('#loanLedgerList').datagrid({
		url : url,
		fitColumns : false,
		border : false,
		pagination : false,
		fit:true,
		striped : true,
		rownumbers : true,
		columns : [ [ 
		{
			field : 'curNum',
			title : '期数',
			width : 50
		}, {
			field : 'repayDay',
			title : '应还款日',
			width : 80,
			formatter : function(value, row, index) {
				if(value != null){
					return value.substring(0,10);
				}else{
					return value;
				}
			}
		}, {
			field : 'factReturnDate',
			title : '实际还款日',
			width : 80,
			formatter : function(value, row, index) {
				if(value != null){
					return value.substring(0,10);
				}else{
					return value;
				}
			}
		}, {
			field : 'repayAmount',
			title : '还款金额',
			width : 100,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'oneTimeRepaymentAmount',
			title : '当期一次性还款金额',
			width : 150,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'status',
			title : '当期还款状态',
			width : 100,
			formatter : function(value, row, index) {
					return formatEnumName(value,'REPAYMENT_PLAN_STATE');
			}
		} ,
		{
			field : 'deficit',
			title : '当期剩余还款',
			width : 150,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'name',
			title : '还款方',
			width : 200,
			formatter : function(value, row, index) {
				if(row.orgRepayTerm!=null){
					if(row.curNum<=row.orgRepayTerm ){
						return row.accountName;
					}else{
						return value;
					}
				}else{
					return value;
				}
			}
		} 		
		] ]
	});
}

function openTab(personName,loanId,personId,extenId,extensionTime){
	var title=personName+" - 借款详细信息";
	var jq = top.jQuery;    
	if (typeof(extenId) == "undefined") { 
		extenId=null;
		}
	var content ='<iframe src="after/customerManager/openTabMain?loanId='+loanId+'&extenId='+extenId+'&extensionTime='+extensionTime+'" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
	if (jq("#centerTabs").tabs('exists', title)) {
		jq("#centerTabs").tabs('select', title);
	}else{
		jq("#centerTabs").tabs('add',{
		    title:personName+" - 借款详细信息",
		    content:content ,
		    closable:true
		   });
	};
}

function openWindos(id){
	 window.open (rayUseUrl+"audit/eduLoan/eduCreditAuditDetailsLApprove/"+id,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}

//提现按钮，后台新增一条记录
function tiXianMethod() {
	var formObj = $('#tiXianForm');
	var loanId = $('#tiXianForm #loanId').val();
	var tiXianMoney = $('#tiXianForm #tiXianMoney').val();
	var cash = $('#tiXianForm #cash').val();
	var remark  = $('#tiXianForm #remark').val();
	var sysParameterData = {accountId:loanId,tradeAmount:tiXianMoney,remark:remark,cash:cash};
	if (formObj.form("validate")) {
		if(tiXianMoney != null && cash != null){
			if(eval(tiXianMoney) > eval(cash)){
				$.messager.alert('操作提示', '提现金额不能大于账户余额', true);
				return;
			}
			if(tiXianMoney == "" || tiXianMoney == null){
				$.messager.alert('操作提示', '提现金额为必填项', true);
				return;
			}
			if(tiXianMoney == 0 || tiXianMoney == 0.00){
				$.messager.alert('操作提示', '提现金额不能为零', true);
				return;
			}
			if(remark == "" || remark == null){
				$.messager.alert('操作提示', '备注为必输项', true);
				return;
			}
		}
		$.post('after/customerManager/tiXianMethod', sysParameterData, function(data) {
			var isErr = '';
			if (data.isSuccess) {
			} else {
				isErr = 'error';
			}
			$.messager.alert('操作提示', data.msg, isErr);
			$('#searchBtn').trigger('click');
			if (!(data.msg == '新增失败')) {
				$('#showTiXianDlg').window('close');
			}
		});
	}
}
