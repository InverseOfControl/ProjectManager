$(function() {
	
	
	//设置生成报盘开始时间为当天 0时0分0秒到24点  结束时间为当天23:59:59
	$('#createdTimeStart').val(startDate);
	$('#createdTimeEnd').val(endDate);
	// 查询按钮
	$('#searchBt').bind('click', search);
	// 查询按钮
	$('#generateBt').bind('click', generateCurrentOffer);	
	
	// 列表
	$('#list_result').datagrid({
		url : 'after/deductionsManagement/getDeductionsImmediatelyPage',
		fitColumns : true,
		border : false,
		singleSelect : false,
		pagination : true,
		striped : true,
		fit:true,
		pageSize : 10,
		rownumbers : true,
		checkOnSelect : true,
		columns : [ [ {
			field : 'id',
			title : '借款编号'

		}, {
			field : 'personName',
			title : '姓名',
			formatter : link2
		}, {
			field : 'personIdnum',
			title : '身份证'
		}, {
			field : 'productId',
			title : '产品类型',
			formatter :showExtensionLoanLedgerApplyDec
		}, {
			field : 'pactMoney',
			title : '合同金额'

		}, {
			field : 'auditMoney',
			title : '审批金额'
		}, 
		 {
			field : 'time',
			title : '借款期限'
		}, 
		
		 {
			field : 'serviceName',
			title : '客服人员'
			
		},{
			field : 'extensionTime',
			title : '展期期次'
		},{
			field : 'status',
			title : '状态',
			formatter : function(value, row, index) {
				return formatEnumName(value,'LOAN_STATUS');
			}
		} ] ]
	});
	// 设置分页控件
	var p = $('#list_result').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});

	$(document).keydown(function(e) {
		if (e.which == 13) {
			$('#searchBt').click();
		}
	});

});

function search() {
	var queryParams = $('#list_result').datagrid('options').queryParams;
	
	if ($('#personName').val() != '') {
		queryParams.personName = $('#personName').val();
	}else{
		queryParams.personName = null;
	}
	if ($('#personIdnum').val() != '') {
		queryParams.personIdnum = $('#personIdnum').val();
	} else{
		queryParams.personIdnum = null;
	}
	setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}

function showExtensionLoanLedgerApplyDec(value,row,index){
	if( row.extensionTime==0)
	{
		return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.id+')">'+row.procutName+'</a>';
	}
	else
	{
		
		return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanExtensionLedger('+row.id+')">'+row.procutName+'</a>';
	}
}

function link2(value,row,index){
	  var fontStyle = 'font-weight:bolder';
	  if(row.status==51 || row.status== 36 || row.status == 38){
	    fontStyle = ' font-weight:bolder;color:red; ';

	  }

		if(row.extensionTime==0)
		{
			return '<a style='+fontStyle+' href="javascript:void(0)" onclick="browse('+row.id+ ',' + row.productType + ', '+row.productId+' )" >' + row.person.name + '</a>';
		}
		else
		{
			return '<a style='+fontStyle+' href="javascript:void(0)" onclick="browseExtension('+row.id+ ')" >' + row.person.name + '</a>';
		}
	} 



function generateCurrentOffer(){
	var row = $('#list_result').datagrid('getSelections');
	if (row) {	
		if(row.length>1){		
			parent.$.messager.show({
				title: '提示',
				msg: "只能选择一行数据"
			});
			return false;
		}
		if(row.length==0){
			parent.$.messager.show({
				title: '提示',
				msg: "请选择一行数据"
			});
			return false;
		}
		$.messager.confirm('确定','确认生成该笔交易的实时划扣？', function(r) {
			 if(r) {	
				  $.ajax({
					   url : 'after/deductionsManagement/toCurrentOffer',
					   data : {
						   loanId:row[0].id
					   },
					   type:"POST",
					   success : function(result){
					   		if(result=='success'){
					   		 $.ajax({
								   url : 'after/deductionsManagement/addCurrentOffer',
								   data : {
									   loanId:row[0].id,
									   personId:row[0].personId,
									   personName:row[0].personName
								   },
								   type:"POST",
								   success : function(result){
								   		if(result=='success'){
								   			$.messager.show({
												title : '提示',
												msg : '保存成功！'
											});
								   			parent.$('#list_result').datagrid('reload');
								   		}else{
								   			parent.$.messager.show({
												title: '提示',
												msg: result
								   			});
							   			}
							   		}
							 });
					   		}else{
					   			parent.$.messager.show({
									title: '提示',
									msg: result
					   			});
					   			}
					   		}
						 });	 
			 }
		});
	}
};



//---------------------------界面生成

function browse(loanId,productType, productId){
    if(productType == 1){
       if(5 ==  productId ||  productId == 6){
        //seLoanCityWideLoanDetail;
          doSeLoan(loanId,'apply/seLoanCityWideLoanDetail',loadExistedCityWideLoan);
        }else{
          doSeLoan(loanId);
        }
    }else if(productType == 2){
    	var isf="0";
    	 $.ajax({
 	        url : 'RefusalEntry/isRefusal?loanId='+loanId,
 	        data : $("#addCarLoanForm").serialize(),
 	        type:"POST",
 	        async: false,
 	        success : function(result){
 	            if(result=="1"){
 	            	isf="1";
 	            	  
 	            }
 	        }
 	 });
    	doCarLoan(loanId,isf);
    }
};
function browseExtension(loanId){
    	doCarLoanExtension(loanId);
};
function formatRequestDate(value,row,index){
	 return getYMD(value);
}
function formatSex(sex){
    if(sex==1)
        return "男";
    else if(sex==0)
        return "女";
    else 
    	return sex;
}
function formatHave(have){
    if(have==1)
        return "有";
    else if(have==0)
        return "无";
    else 
    	return have;
}
function formatYes(yes){
    if(yes==1)
        return "是";
    else if(yes== 0)
        return "否";
    else 
    	return yes;
}

function formatProductCarType(loanType){
    if(loanType == 1)
        return "移交类";
    else if(loanType==2)
        return "流通类";
    else 
    	return loanType;
}

function formatEducationLevel(educationLevel){
    if(educationLevel == 0)
        return "初中及以下";
    else if(educationLevel==1)
        return "高中";
    else if(educationLevel==2)
        return "中专";
    else if(educationLevel==3)
        return "大专";
    else if(educationLevel==4)
        return "本科";
    else if(educationLevel==5)
        return "硕士及以上";
    else 
    	return educationLevel;
}

function formatCompanyCategory(category){
    if(category == 0)
        return "个体";
    else if(category==1)
        return "私营独资";
    else if(category==2)
        return "私营合伙";
    else if(category==3)
        return "私营有限责任";
    else if(category==4)
        return "私营股份有限";
    else if(category==5)
        return "其他";
    else 
        return category;
}

function formatGuaranteeType(guaranteeType){
    if(guaranteeType == 0)
        return "自然人";
    else if(guaranteeType==1)
        return "法人";
    else 
        return category;
}

function formatCompanyType(companyType){
	 if(companyType == 0)
	        return "政府机构";
	    else if(companyType==1)
	        return "事业";
	    else if(companyType==2)
	        return "国企";
	    else if(companyType==3)
	        return "外资/合资";
	    else if(companyType==4)
	        return "民营";
	    else if(companyType==5)
	        return "私营";
	    else if(companyType==6)
	        return "其他";
	    else 
	    	return companyType;
}

function formatMarried(married){
	 if(married == 0)
        return "未婚";
     else if(married==1)
        return "已婚";
     else if(married==2)
        return "离异";
     else if(married==3)
        return "再婚";
     else if(married==4)
        return "丧偶";
     else if(married==5)
        return "其他";
     else 
    	return married;
}

function transferUndefined(data){
	if(data)
		return data;
	else 
		return "";
}

