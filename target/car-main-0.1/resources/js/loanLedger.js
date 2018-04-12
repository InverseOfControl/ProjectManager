function showLoanLedger(id){
	 var h = $(window).height() ;
	 $('#showLoanLedgerDlg').dialog({
			title : '借款台账',
			inline: true,
			closed : false,
			cache : false,
			maximizable: true,
			height: h*0.8
	 });
		var url = 'repaymentPlan/repaymentPlanLedgerList/' + id;
		$('#loanLedgerList').datagrid({
			url : url,
			fitColumns : false,
			border : false,
			fit:true,
			pagination : false,
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

function showLoanExtensionLedger(id){
	 var h = $(window).height() ;
	 $('#showLoanLedgerDlg').dialog({
			title : '借款台账',
			inline: true,
			closed : false,
			cache : false,
			maximizable: true,
			height: h*0.8
	 });
		var url = 'repaymentPlan/repaymentPlanLedgerExtensionList/' + id;
		$('#loanLedgerList').datagrid({
			url : url,
			fitColumns : false,
			border : false,
			fit:true,
			pagination : false,
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

function showExtensionLoanLedger(value,row,index){
		return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.id+')">'+row.procutName+'</a>';
}

function showExtensionLoanLedgerApply(value,row,index){
	if( row.extensionTime==0)
	{
		return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.id+')">'+row.product.productTypeName+'</a>';
	}
	else
	{
		return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanExtensionLedger('+row.id+')">'+row.product.productTypeName+'</a>';
	}
}

function showExtensionLoanLedgerRepay(value,row,index){
	var val=formatEnumName(value,'PRODUCT_ID');
	return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.loanId+')">'+val+'</a>';
}


