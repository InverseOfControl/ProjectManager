$(function() {
	//划扣失败按钮隐藏
	$('#exportFail').hide();
	$('#exportExcelFailBt').linkbutton("disable");
	
	$('#guaranteeButton').linkbutton("disable");
	$('#toEditMoney').linkbutton("disable");
	$('#exportExcelBt').linkbutton("disable");
	$('#export').hide();
	$('#editMoney').hide();
	$('#guarantee').hide();
	$('#changeTpp').hide();
	if (userType == 6) {//小企业贷款可以担保代扣 修改金额
		$('#editMoney').show();
		$('#guarantee').show();
		$('#guaranteeButton').linkbutton("enable");	
		$('#toEditMoney').linkbutton("enable");
		// 担保代扣  修改金额
		$("#toEditMoney").bind('click', toEditMoney);
		$("#guaranteeButton").bind('click', toEditGuaratee);
	}else if(userType==3){//车贷客服经理副理权限		
		$('#editMoney').show();
		// 可修改金额
		$('#toEditMoney').linkbutton("enable");
		$("#toEditMoney").bind('click', toEditMoney);
	}else if(userType==8){//财务
		$('#export').show();
		$('#exportExcelBt').linkbutton("enable");
		// 导出按钮
		$("#exportExcelBt").bind('click', exportExcel);
		
	}else if(hasIfEditCarLoan){
		$('#editMoney').show();
		// 可修改金额
		$('#toEditMoney').linkbutton("enable");
		$("#toEditMoney").bind('click', toEditMoney);
		$('#changeTpp').show();
		// 可修改金额
		$('#changeTppBtn').linkbutton("enable");
		$("#changeTppBtn").bind('click', toChangeTpp);
		
	}
	//****************划扣失败导出权限***********************
	//判断是否是admin 用户 给予 划扣结果导出和划扣失败导出权限
	if(userType == 1 && "code1" == code){
		//划扣结果导出
		$('#export').show();
		$('#exportExcelBt').linkbutton("enable");
		$("#exportExcelBt").bind('click', exportExcel);
		//划扣失败按钮显示
		$('#exportFail').show();
		//划扣失败按钮添加点击事件
		$('#exportExcelFailBt').linkbutton("enable");
		$("#exportExcelFailBt").bind('click', exportExcelFail);
	}
	
	$('#deductionsStatus').combobox('setValue', 10);
	$('#guarateeName')
			.combobox(
					{
						valueField : 'bankAccountId',
						textField : 'name',
						onChange : function(value) {
							$
									.ajax({
										url : 'after/deductionsManagement/toGuarateeBankAccount',
										type : "POST",
										data : {
											bankAccountId : value
										},
										success : function(result) {
											if (result != '') {
												var data = $('#guarateeName')
														.combobox('getData');
												//选择了最后一个，就是借款人本人		   			
												if (data[data.length - 1].bankAccountId == result.id) {
													$('#isGuarantee').val(2);//设为无担保
												} else {
													$('#isGuarantee').val(1);//设为有担保
												}
												$('#guaranteeName')
														.val(
																$(
																		'#guarateeName')
																		.combobox(
																				'getText'));
												$('#guarateeBankNameInput')
														.val(result.bankName);
												$('#guarateeAccountInput').val(
														result.account);
												$('#guarateeBankName').text(
														result.bankName);
												$('#guarateeAccount').text(
														result.account);

											} else {
												$.messager.show({
													title : '提示',
													msg : '当前修改操作，不在允许时间范围内！'
												});
												$('#list_result').datagrid(
														'reload');
											}
										}
									});
						}

					});
	//产品类型
    $('#toolbar #productComb').combobox({
        url:'apply/getProductType',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1){
                $(this).combobox('select', data[0].productType);
            }else if(data.length>0){
            	$(this).combobox('select', data[0].productType);
            }
            userProductType = data[0].productType;
        }
    }); 
	
	
	//设置生成报盘开始时间为当天 0时0分0秒到24点  结束时间为当天23:59:59
	$('#createdTimeStart').val(startDate);
	$('#createdTimeEnd').val(endDate);
	
	
	//王丽园所在组、朱雪娇所在组查询条件优化
	//财务审核划扣组(王丽园所在组)
	if(financeGroup==13){
		//设置报盘时间为当天 0时0分0秒到24点  结束时间为当天23:59:59
		$('#sendDateStart').val(startDate);
		$('#sendDateEnd').val(endDate);
		//划扣结果为默认（默认包含成功和部分还款）
		$('#returnCode').combobox('setValue', 'default');
		//生成时间置为空
		$('#createdTimeStart').val("");
		$('#createdTimeEnd').val("");
		//划扣状态，默认已回盘
		$('#deductionsStatus').combobox('setValue', 30);
	}else if(financeGroup==16){
		//设置报盘时间为当天 0时0分0秒到24点  结束时间为当天23:59:59
		$('#sendDateStart').val(startDate);
		$('#sendDateEnd').val(endDate);
		//生成时间置为空
		$('#createdTimeStart').val("");
		$('#createdTimeEnd').val("");
	}
	
	
	
	
	
	
	// 查询按钮
	$('#searchBt').bind('click', search);
	$('#list_result').datagrid({
		url : 'after/deductionsManagement/getDeductionsManagementPage',
		fitColumns : true,
		//将报盘时间、生成时间、划扣结果、状态传入
		queryParams: {
			startCreatStrDate:$('#createdTimeStart').val(),
			endCreatStrDate:$('#createdTimeEnd').val(),
			startStrDate: $('#sendDateStart').val(),
			endStrDate: $('#sendDateEnd').val(),
			returnCode:$('#returnCode').combobox('getValue'),
			status:$('#deductionsStatus').combobox('getValue'),
			tppType:$('#tppType').combobox('getValue')	
		},
 
		border : false,
		singleSelect : false,
		pagination : true,
		striped : true,
		pageSize : 10,
		fit:true,
		rownumbers : true,
		checkOnSelect : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'personName',
			title : '姓名'
		}, {
			field : 'bankName',
			title : '开户行'
		}, {
			field : 'bankAccount',
			title : '账号'
		}, {
			field : 'receivableAmount',
			title : '应报金额'

		}, {
			field : 'offerAmount',
			title : '实报金额'
		},{
			field : 'successAmount',
			title : '回盘金额'
		},
		{
			field : 'offerType',
			title : '报盘类型',
			formatter : function(value, row, index) {
				return row.offerType==1?'实时报盘':'定时报盘';
			}
		}, 
		{
			field : 'productId',
			title : '产品类型',
			formatter : function(value, row, index) {
				return formatEnumName(value,'PRODUCT_ID');
			}
		},
		 {
			field : 'createdTime',
			title : '生成时间'
		},{
			field : 'sendDate',
			title : '报盘时间'
		},
		
		{
			field : 'handleTime',
			title : '回盘时间',
			formatter : function(value, row, index) {
				return row.tppCallBackData.handleTime;
			}
		},{
			field : 'tppType',
			title : '划扣渠道',
			formatter : function(value, row, index) {
				return formatEnumName(value,'TPP_TYPE');
			}
		}, {
			field : 'status',
			title : '划扣状态',
			formatter : function(value, row, index) {
				return formatEnumName(value,'OFFER_STATE');
			}
		}, {
			field : 'returnCode',
			title : '划扣结果',
			formatter : function(value, row, index) {				
				return convertCode(row.tppCallBackData.returnCode);
			}
		} ,{
			field : 'businessCompany.shortName',
			title : '公司名称',
			formatter : function(value, row, index) {
				return row.businessCompany.shortName;
			}
		} ,{
			field : 'tppCallBackData.returnInfo',
			title : '备注',
			formatter : function(value, row, index) {
				return row.tppCallBackData.returnInfo;
			}
		},{
			field : 'salesDeptName',
			title : '放款营业部',
		},{
			field : 'tppCallBackData.failReason',
			title : '失败原因',
			formatter : function(value, row, index) {
				return row.tppCallBackData.failReason;
			}
		}, {
			field : 'isGuarantee',
			title : '有无担保人',formatter : function(value, row, index) {				
				return formatEnumName(value,'HAS_GUARANTEE');
			}

		}, {
			field : 'hasGuarantee',
			title : '担保代扣',formatter : function(value, row, index) {				
				return formatEnumName(value,'HAS_GUARANTEE');
			}

		}, {
			field : 'guaranteeName',
			title : '担保人'
		}, {
			field : 'guaranteeBankName',
			title : '担保人开户行'
		}, {
			field : 'guaranteeBankAccount',
			title : '担保人账号',width : 160
		}, {
			field : 'operator',
			title : '操作',width : 160,
			formatter : function(value, row, index) {	
				if(row.offerType==1&&row.status==10&&hasIfEditCarLoan){
					var param='';
					param+='<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="sendOffer('+row.id+')">划扣&nbsp;&nbsp;</a>';
					param+='<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="cannelOffer('+row.id+')">取消&nbsp;&nbsp;</a>';
					return param;
				}else{
					return '';
				}
			}
		}
		] ]
	});
	// 设置分页控件
	var p = $('#list_result').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});

	
	// 修改金额  提交按钮
	$("#offerAmountSubmitBt").bind('click', editMoney);
	// 修改金额 取消按钮
	$("#offerAmountCancelBt").bind('click', cencalMoneyDialog);
	// 修改渠道  提交按钮
	$("#changeTppSubmitBt").bind('click', changeOfferTpp);
	// 修改渠道  取消按钮
	$("#changeTppCancelBt").bind('click', cancelTppDialog);
	// 担保代扣提交按钮
	$("#guarateeSubmitBt").bind('click', editGuaratee);
	//退回窗口点击取消
	$("#guarateeCancelBt").bind('click', cencalGuaratee);
	//   界面生成
	$("#immediateBt").bind('click', windowOpen);
	// 实时划扣  提交按钮
	$("#sendOfferBt").bind('click', sendOffer);	

	$(document).keydown(function(e) {
		if (e.which == 13) {
			$('#searchBt').click();
		}
	});
	//城市
    $('#toolbar #cityComb').combobox({
        url:'apply/getCurCity',
        valueField:'id',
        textField:'name',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].id);
        }
    });

});
function convertCode(state){
	if(state=='000000'){
		return  '成功';
	}else if(state=='111111'){
		return  '失败';
	}else if(state=='444444'){
		return  '部分还款';
	}
}
//修改金额点击取消
function cencalMoneyDialog() {
	$('#moneyDialog').dialog('close');
}
function cencalGuaratee() {
	$('#guarateeDialog').dialog('close');
}
//修改金额点击取消
function cancelTppDialog() {
	$('#changeTppDialog').dialog('close');
}
//是否可修改金额
function toEditMoney() {	
	$('#offerAmount').val('');
	var rows = $('#list_result').datagrid('getChecked');
	var id = '';
	if (rows.length == 1) {
		if (rows[0].status == 10) {// 未报盘
			id = rows[0].id;
		} else {//
			$.messager.show({
				title : '提示',
				msg : '只能选择未报盘数据！'
			});
			return false;
		}

	} else {
		$.messager.show({
			title : '提示',
			msg : '请选择且只能选择一条数据！'
		});
		return false;
	}
	$.ajax({
		url : 'after/deductionsManagement/toEditMoney',
		type : "POST",
		data : {
			id : id
		},
		success : function(result) {
			if (result != '') {
				$('#offerAmount').val('');
				$('#loanId').val('');
				$('#id').val(result.id);
				$('#loanId').val(result.loanId);
				$('#name').text(result.personName);
				$('#receivableAmount').text(result.receivableAmount);
				$('#moneyDialog').dialog('open').dialog('setTitle', '修改金额');
			} else {
				$.messager.show({
					title : '提示',
					msg : '当前修改操作，不在允许时间范围内！'
				});
				$('#list_result').datagrid('reload');
			}
		}
	});

}
//修改金额
function editMoney() {
    var id = $('#id').val();
	var offerAmount = $('#offerAmount').val();	
	var loanId = $('#loanId').val();	
	if (offerAmount == '' || offerAmount < 0|| isNaN(offerAmount)==true) {
		$.messager.show({
			title : '提示',
			msg : '请输入正确的金额！'
		});
		return false;
	}
	parent.$.messager.confirm('确认', '确定提交?', function(r) {
		if (r) {
			$.ajax({
				url : 'after/deductionsManagement/editMoney',
				type : "POST",
				data : {
					id : id,
					loanId:loanId,
					offerAmount : offerAmount
				},
				success : function(result) {
					if (result == 'success') {
						$.messager.show({
							title : '提示',
							msg : '修改成功！'
						});
						$('#moneyDialog').dialog('close');
						$('#list_result').datagrid('reload');
					} else {
						$.messager.show({
							title : '提示',
							msg : '修改失败！'
						});
						$('#moneyDialog').dialog('close');
						$('#list_result').datagrid('onUncheckAll');
						$('#list_result').datagrid('reload');
					}
				}
			});
		}
	});
}
//是否可修改担保代扣  逾期
function toEditGuaratee() {
	$('#guaranteeName').val();
	$('#guarateeBankNameInput').val();
	$('#guarateeAccountInput').val();
	$('#guarateeBankName').text();
	$('#guarateeAccount').text();
	if(userType != 6){//如果不是小企业贷款，不可使用担保代扣
		return false;
	}
	var rows = $('#list_result').datagrid('getChecked');
	var id = '';
	var loanId = '';
	var personId = '';
	var personName = '';
	if (rows.length == 1) {
		if (rows[0].loan.status == 140) {// 逾期
			id = rows[0].id;
			loanId = rows[0].loanId;
			personId = rows[0].loan.personId;
			personName = rows[0].personName;

		} else {//
			$.messager.show({
				title : '提示',
				msg : '只能选择逾期借款！'
			});
			return false;
		}
		if (rows[0].isGuarantee == 2) {//无担保			
			$.messager.show({
				title : '提示',
				msg : '只能选择有担保的借款！'
			});
			return false;
		}

	} else {
		$.messager.show({
			title : '提示',
			msg : '请选择且只能选择一条数据！'
		});
		return false;
	}
	$.ajax({
		url : 'after/deductionsManagement/toEditGuaratee',
		type : "POST",
		data : {
			id : id
		},
		success : function(result) {
			if (result != '') {
				$('#offerAmount').val('');
				$('#id').val(result.id);
				$('#personName2').text(result.personName);
				$('#personBankName').text(result.bankName);
				$('#personBankAccount').text(result.bankAccount);
				$('#guarateeDialog').dialog('open').dialog('setTitle', '担保代扣');
				//担保人
				$('#guarateeName').combobox({
					url : 'after/deductionsManagement/toGuaratee',
					valueField : 'bankAccountId',
					textField : 'name',
					editable : false,
					onBeforeLoad : function(param) {
						param.loanId = loanId;
						param.personId = personId;
						param.personName = personName;
					}
				});
			} else {
				$.messager.show({
					title : '提示',
					msg : '当前修改操作，不在允许时间范围内！'
				});
				$('#list_result').datagrid('reload');
			}
		}
	});

}
//提交担保代扣 
function editGuaratee() {
	var id = $('#id').val();
	var hasGuarantee = $('#isGuarantee').val();//有无担保	
	var guaranteeId = $('#guarateeName').combobox('getValue');
	var guaranteeName = $('#guaranteeName').val();
	var guaranteeBankName = $('#guarateeBankNameInput').val();
	var guaranteeBankAccount = $('#guarateeAccountInput').val();
	if (hasGuarantee == 2) {
		guaranteeId = null;
		guaranteeName = null;
		guaranteeBankName = null;
		guaranteeBankAccount = null;
	}
	parent.$.messager.confirm('确认', '确定提交?', function(r) {
		if (r) {
			$.ajax({
				url : 'after/deductionsManagement/editGuaratee',
				type : "POST",
				data : {
					id : id,
					guaranteeId : guaranteeId,
					hasGuarantee : hasGuarantee,
					guaranteeName : guaranteeName,
					guaranteeBankName : guaranteeBankName,
					guaranteeBankAccount : guaranteeBankAccount
				},
				success : function(result) {
					if (result == 'success') {
						$.messager.show({
							title : '提示',
							msg : '提交成功！'
						});
						$('#guarateeDialog').dialog('close');
						$('#list_result').datagrid('reload');
					} else {
						$.messager.show({
							title : '提示',
							msg : '提交失败！'
						});
						$('#list_result').datagrid('reload');
					}
				}
			});
		}
	});
}
function search() {
	var queryParams = $('#list_result').datagrid('options').queryParams;
	//先把datagrid queryParams 置为空
	queryParams = {};
	
	//初始化时间为空
	repay=new Object();		
	queryParams.sendDateStart = repay;
	queryParams.sendDateEnd = repay;
	queryParams.createdTimeStart =  repay;
	queryParams.createdTimeEnd = repay;	
	
	queryParams.personName = $('#personName').val();
	queryParams.personIdnum = $('#personIdnum').val();
	//获取城市Id
	queryParams.cityId = $('#toolbar #cityComb').combobox('getValue');
	if ($('#returnCode').combobox('getValue') == "all") {//划扣结果
		queryParams.returnCode = null;
	} else {
		queryParams.returnCode = $('#returnCode').combobox(
				'getValue');
	}
	 queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	 if($('#toolbar #productComb').combobox('getValue')==''||$('#toolbar #productComb').combobox('getValue')==null){
		 queryParams.productId = 0;
	 }
		if ($('#offerType').combobox('getValue') == "all") {//报盘类型
			queryParams.offerType = null;
		} else {
			queryParams.offerType = $('#offerType').combobox(
					'getValue');
		}
		
	 
	 queryParams.bankName = $('#bankName').val(); 
	 queryParams.failReasonType = $('#failReasonType').val(); 
	 
	
	if ($('#hasGuarantee').combobox('getValue') == "all") {//是否担保
		queryParams.hasGuarantee = 0;
	} else {
		queryParams.hasGuarantee = $('#hasGuarantee').combobox('getValue');
	}
	if ($('#deductionsStatus').combobox('getValue') == "all") {//划扣状态
		queryParams.status = 0;
	} else {
		queryParams.status = $('#deductionsStatus').combobox('getValue');
	}
	if ($('#tppType').combobox('getValue') == "all") {//划扣通道
		queryParams.tppType = 0;
	}else {
		queryParams.tppType = $('#tppType').combobox('getValue');
	}
	//开始结束时间为当天 0时0分0秒到24点 	
	//报盘时间
	if ($('#sendDateStart').val() != '') {
		queryParams.sendDateStart = $('#sendDateStart').val();
	}
	if ($('#sendDateEnd').val() != '') {
		queryParams.sendDateEnd = $('#sendDateEnd').val();
	} 
	//生成时间
	if ($('#createdTimeStart').val() != '') {
		queryParams.createdTimeStart = $('#createdTimeStart').val();
	} 
	if ($('#createdTimeEnd').val() != '') {
		queryParams.createdTimeEnd = $('#createdTimeEnd').val();
	} 
	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}


function windowOpen(){
	 window.open(rayUseUrl+"after/deductionsManagement/listImmediately" 
			 ,"newwindow2","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");

}

//提交实时报盘
function sendOffer(id) {
		parent.$.messager.confirm('确认', '确定发送实时报盘?', function(r) {
			if (r) {
				$.ajax({
					url : 'after/deductionsManagement/toSendTpp',
					type : "POST",
					data : {
						offerId : id,
					},
					success : function(result) {
						if (result == 'success') {
							$.messager.show({
								title : '提示',
								msg : '提交成功！'
							});
							$('#list_result').datagrid('reload');
						} else {
							$.messager.show({
								title : '提示',
								msg: result
							});
							$('#list_result').datagrid('reload');
						}
					}
				});
			}
		});
}
//取消实时报盘
function cannelOffer(id) {
		parent.$.messager.confirm('确认', '确定取消实时报盘?', function(r) {
			if (r) {
				$.ajax({
					url : 'after/deductionsManagement/cannelCurrentOffer',
					type : "POST",
					data : {
						id : id,
					},
					success : function(result) {
						if (result == 'success') {
							$.messager.show({
								title : '提示',
								msg : '提交成功！'
							});
							$('#list_result').datagrid('reload');
						} else {
							$.messager.show({
								title : '提示',
								msg: result
							});
							$('#list_result').datagrid('reload');
						}
					}
				});
			}
		});

}



//是否可修改渠道
function toChangeTpp() {	
	var rows = $('#list_result').datagrid('getChecked');
	var length=rows.length;
	//产品类型
    $('#tppTypeChange').combobox({
        url:'after/deductionsManagement/getTppType',
        valueField:'enumCode',
        textField:'enumValue',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1){
                $(this).combobox('select', data[0].enumCode);
            }else if(data.length>0){
            	$(this).combobox('select', data[0].enumCode);
            }
        }
    }); 
    var test = true;
	if (length > 0) {		
		$.each(rows,function(i,n){
			if(n.status != 10){
				parent.$.messager.show({
					title : '提示',
					msg : '只能选择未报盘数据！'
				});
				test= false;
				return false;
			}
		});
		if(test){
			$('#changeTppDialog').dialog('open').dialog('setTitle', '修改划扣渠道');
		}
	}else {
		$.messager.show({
			title : '提示',
			msg : '请勾选要修改的报盘交易！'
		});
		return false;
	}

}

function changeOfferTpp(){
	debugger;
	var rows = $('#list_result').datagrid('getChecked');
	//选择的行数
	var length=rows.length;
	var ids = "";
	var tppType=$('#tppTypeChange').combobox('getValue');
	if(tppType==null || tppType==''){
		parent.$.messager.show({
			title : '提示',
			msg : '请选择渠道！'
		});
		return false;
	}
	if (length > 0) {		
		$.each(rows,function(i,n){
			if(n.status != 10){
				parent.$.messager.show({
					title : '提示',
					msg : '只能选择未报盘数据！'
				});
				return false;
			}
			if(i != 0){
				ids += "," ;
			}
			ids += (n.id);
		});
		parent.$.messager.confirm('确认','确认要修改所选中报盘交易吗？', function(r) {
			if(!r){
				return false;
			}
			$.ajax({
			   url : 'after/deductionsManagement/changeOfferTpp',
			   data : {
				   idList:ids,
				   tppType:tppType
			   },
			   type:"POST",
				success : function(result) {
					if (result == 'success') {
						parent.$.messager.show({
							title : '提示',
							msg : '修改成功！'
						});
						$('#changeTppDialog').dialog('close');
						$('#list_result').datagrid('reload');
						$('#list_result').datagrid('unselectAll');
					} else {
						parent.$.messager.show({
							title : '错误',
							msg : result
						});
					}
				}
		});	
		});
	}else{
		parent.$.messager.show({
			title : '提示',
			msg : '请勾选要修改的报盘交易！'
		});
	};
	
}

