/**
 * 
 */

$(function() {
	
	// 获取该营业店所有客户经理列表
	$("#toolbar #crmList").combobox({
		url : 'after/repayInAdvance/getCrmList',
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function() {
			var data = $(this).combobox('getData');
			$(this).combobox('select', data[0].id);
		}
	});
	// 产品类型
	$('#toolbar #productComb').combobox({
		url : 'apply/getProductType',
		valueField : 'id',
		textField : 'productName',
		onLoadSuccess : function() {
			var data = $(this).combobox('getData');
		}
	});

	$("#plRepayInAdvancePageTb")
			.datagrid(
					{
						url : 'after/repayInAdvance/getRepayInAdvancePlPage',
						fitColumns : true,
						border : false,
						singleSelect : true,
						pagination : true,
						fit : true,
						striped : true,
						pageSize : 10,
						rownumbers : true,
						checkbox : true,
						checkOnSelect : true,
						selectOnCheck : false,
						// rowStyler:function(index,row){
						// if(row.isFinanceExamine==1){
						// return
						// 'background-color:#6293BB;color:#fff;font-weight:bold;';
						// }
						// },
						onLoadSuccess : function(data) {// 加载完毕后获取所有的checkbox遍历
							if (data != null) {
								if (data.rows.length > 0) {
									// 循环判断操作为新增的不能选择
									for (var j = 0; j < data.rows.length; j++) {

										var operations = data.rows[j].operations
												.split("|");

										var formattedOperations = "";

										for (var i = 0; i < operations.length; i++) {
											var operation = operations[i];
											/* alert(operation); */
											if (operation != null
													|| '' != operation) {
												if (operation == "提前扣款取消") {
													/*alert(operation);*/
													$("input[type='checkbox']")[j + 1].disabled = true;
												} else if (operation == "一次性结清取消") {
													$("input[type='checkbox']")[j + 1].disabled = true;
												} else if (operation == "不可操作") {
													$("input[type='checkbox']")[j + 1].disabled = true;
												}
											}
										}
									}
								}
							}
						},
						onClickRow : function(rowIndex, rowData) {
							// 加载完毕后获取所有的checkbox遍历
							$("input[type='checkbox']").each(
									function(index, el) {
										// 如果当前的复选框不可选，则不让其选中
										if (el.disabled == true) {
											$('#plRepayInAdvancePageTb')
													.datagrid('unselectRow',
															index - 1);
										}
									})
						},
						onCheckAll : function(rows) {
							$("input[type='checkbox']").each(
									function(index, el) {
										/* console.log(el.disabled) */
										if (el.disabled) {
											$("#plRepayInAdvancePageTb")
													.datagrid('uncheckRow',
															index - 1);
										}
									})
						},
						columns : [ [
								{
									field : 'loanId',
									checkbox : true
								},
								{
									field : 'name',
									title : '姓名',
									width : 50
								},
								{
									field : 'idNum',
									title : '身份证号',
									width : 80
								},
								{
									field : 'productId',
									title : '产品类型',
									formatter : function(value, row, index) {
										return formatEnumName(value,
												'PRODUCT_ID');
									},
									width : 50
								},
								{
									field : 'productSubType',
									title : '借款子类型',
									formatter : function(value, row, index) {
										return formatEnumName(value,
												'PRODUCT_SUB_TYPE');
									},
									width : 50
								},
								{
									field : 'pactMoney',
									title : '合同金额',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);// 保留两位小数
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'currRepayAmount',
									title : '当期应还',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'overdueAllAmount',
									title : '逾期应还',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'fine',
									title : '逾期违约金',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'oneTimeRepayment',
									title : '一次性还款金额',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'accAmount',
									title : '期末预收',
									formatter : function(value, row, index) {
										if (value != null) {
											return value.toFixed(2);
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'time',
									title : '期数',
									width : 30
								},
								{
									field : 'extensionTime',
									title : '展期期次',
									formatter : function(value, row, index) {
										if (value == 0) {
											return "无";
										} else {
											return value;
										}
									},
									width : 60
								},
								{
									field : 'status',
									title : '借款状态',
									formatter : function(value, row, index) {
										return formatEnumName(value,
												'LOAN_STATUS');
									},
									width : 50
								}, {
									field : 'operations',
									title : '操作',
									formatter : formatOperationsCell,
									width : 100
								} ] ]
					});

	function formatOperationsCell(value, row, index) {
		var operations = row.operations.split("|");
		var formattedOperations = "";

		for (var i = 0; i < operations.length; i++) {
			var operation = operations[i];

			if (operation == "提前扣款取消") {
				operation = '<a href="javascript:void(0)" onclick="cancelRepayInAdvance('
						+ row.loanId + ')">提前扣款取消</a>';
			} else if (operation == "一次性结清取消") {
				operation = '<a href="javascript:void(0)" onclick="cancelRepayOneTime('
						+ row.loanId + ')">一次性结清取消</a>';
			} else if (operation == "提前扣款") {
				operation = '<a href="javascript:void(0)" onclick="repayInAdvance('
						+ row.loanId + ')">提前扣款</a>';
			} else if (operation == "一次性结清") {
				operation = '<a href="javascript:void(0)" onclick="repayOneTime('
						+ row.loanId + ')">一次性结清</a>';
			} else if (operation == "不可操作") {
				operation = '&nbsp;';
			}

			if (formattedOperations == "") {
				formattedOperations = operation;
			} else {
				formattedOperations = formattedOperations
						+ "&nbsp;&nbsp;&nbsp;&nbsp;" + operation;
			}
		}
		return formattedOperations;
	}

	// 设置分页控件
	var p = $('#plRepayInAdvancePageTb').datagrid('getPager');
	$(p).pagination({
		pageList : [ 10, 20, 50 ]
	});

	// 点击【查询】按钮
	$('#searchBtn').bind('click', search);

	$(document).keydown(function(e) {
		if (e.which == 13) {
			$('#searchBtn').click();
		}
	});
	// 点击【批量提前还款】按钮
	$('#plRepayInAdvance').bind('click', plRepayInAdvance);
	// 点击【批量一次性结清】按钮
	$('#plRepayOneTime').bind('click', plRepayOneTime);
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

function search() {
	 /*var opts = $("#plRepayInAdvancePageTb").datagrid("options");*/
	   
	var queryParams = $('#plRepayInAdvancePageTb').datagrid('options').queryParams;
	// 姓名模糊查询
	/*queryParams.personFuzzyName = $('#toolbar #personName').val();
	queryParams.productId = $('#toolbar #productComb').combobox('getValue');
	queryParams.personIdnum = $('#toolbar #personIdnum').val();
	queryParams.personMobilePhone = $('#toolbar #mobilePhone').val();
	queryParams.crmId = $('#toolbar #crmList').combobox('getValue');*/
	

	queryParams.repayDay = $('#repayDay').val(); 
	
	//获取城市Id
	queryParams.cityId = $('#toolbar #cityComb').combobox('getValue');
	/*if(queryParams.repayDay!=''||queryParams.cityId!=''){
		 opts.url = "after/repayInAdvance/getRepayInAdvancePage";
	}*/
	setFirstPage("#plRepayInAdvancePageTb");
	$('#plRepayInAdvancePageTb').datagrid('options').queryParams = queryParams;
	$("#plRepayInAdvancePageTb").datagrid('reload');
}

// 提前扣款取消
function cancelRepayInAdvance(loanId) {
	$.messager.confirm("Confirm", "您确定要取消吗？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : "after/repayInAdvance/cancelRepayInAdvance",
				dataType : "json",
				data : {
					loanId : loanId
				},
				success : function(message) {
					if (message == "success") {
						$.messager.show({
							title : '提示',
							msg : '取消成功！',
							showType : 'slide'
						});
						$("#plRepayInAdvancePageTb").datagrid('reload');
					} else {
						$.messager.show({
							title : '提示',
							msg : message,
							showType : 'slide'
						});
						$(
						"#plRepayInAdvancePageTb")
						.datagrid(
								'reload');
					}
				},
				error : function() {
					$.messager.show({
						title : '提交',
						msg : '取消失败！',
						showType : 'slide'
					});
				}
			});
		}
	});
}

// 一次性还款取消
function cancelRepayOneTime(loanId) {
	$.messager.confirm("Confirm", "您确定要取消吗？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : "after/repayInAdvance/cancelRepayOneTime",
				dataType : "json",
				data : {
					loanId : loanId
				},
				success : function(message) {
					if (message == "success") {
						$.messager.show({
							title : '提示',
							msg : '取消成功！',
							showType : 'slide'
						});
						$("#plRepayInAdvancePageTb").datagrid('reload');
					} else {
						$.messager.show({
							title : '提示',
							msg : message,
							showType : 'slide'
						});
						$(
						"#plRepayInAdvancePageTb")
						.datagrid(
								'reload');
					}
				},
				error : function() {
					$.messager.show({
						title : '提交',
						msg : '取消失败！',
						showType : 'slide'
					});
				}
			});
		}
	});
}

// 提前扣款
function repayInAdvance(loanId) {
	// 判断loanId是否有展期
	$
			.ajax({
				type : "POST",
				url : "after/repayInAdvance/hasExtension",
				dataType : "json",
				data : {
					loanId : loanId
				},
				success : function(message) {
					var waring = "您确定要提交吗？";
					if (message == true) {
						waring = "该笔借款有展期，如果申请，将会取消其展期，您确定要提交吗？";
					}
					$.messager
							.confirm(
									"Confirm",
									waring,
									function(r) {
										if (r) {
											$
													.ajax({
														type : "POST",
														url : "after/repayInAdvance/plSubmitRepayInAdvance",
														dataType : "json",
														data : {
															loanId : loanId
														},
														success : function(
																message) {
															if (message == "提交成功!") {
																$.messager
																		.show({
																			title : '提示',
																			msg : '提交成功！',
																			showType : 'slide'
																		});
																$(
																		"#plRepayInAdvancePageTb")
																		.datagrid(
																				'reload');
															} else {
																$.messager
																		.show({
																			title : '提示',
																			msg : message,
																			showType : 'slide'
																		});
																$(
																"#plRepayInAdvancePageTb")
																.datagrid(
																		'reload');
															}
														},
														error : function() {
															$.messager
																	.show({
																		title : '提交',
																		msg : '提交失败！',
																		showType : 'slide'
																	});
														}
													});
										}
									});
				}
			});
}


// 一次性还款
function repayOneTime(loanId) {
	// 判断loanId是否有展期
	$
			.ajax({
				type : "POST",
				url : "after/repayInAdvance/hasExtension",
				dataType : "json",
				data : {
					loanId : loanId
				},
				success : function(message) {
					var waring = "您确定要提交吗？";
					if (message == true) {
						waring = "该笔借款有展期，如果申请，将会取消其展期，您确定要提交吗？";
					}
					$.messager
							.confirm(
									"Confirm",
									waring,
									function(r) {
										if (r) {
											$
													.ajax({
														type : "POST",
														url : "after/repayInAdvance/plSubmitRepayOneTime",
														dataType : "json",
														data : {
															loanId : loanId
														},
														success : function(
																message) {
															if (message == "提交成功!") {
																$.messager
																		.show({
																			title : '提示',
																			msg : '提交成功！',
																			showType : 'slide'
																		});
																$(
																		"#plRepayInAdvancePageTb")
																		.datagrid(
																				'reload');
															} else {
																$.messager
																		.show({
																			title : '提示',
																			msg : message,
																			showType : 'slide'
																		});
																$(
																"#plRepayInAdvancePageTb")
																.datagrid(
																		'reload');
															}
														},
														error : function() {
															$.messager
																	.show({
																		title : '提交',
																		msg : '提交失败！',
																		showType : 'slide'
																	});
														}
													});
										}
									});
				}
			});
}

var lines="";
function handleData(result) {
    lines += result;
   /* $("table tr:eq(0) td:eq(9)").text(result);*/
    return lines;
}
/**
 * 批量提前还款
 */

function plRepayInAdvance() {
	lines="";
	var plInfo="";
	var plMessenge="";
	var waring="";
	var zhanqi=0;
	var id;
	var rows = $('#plRepayInAdvancePageTb').datagrid('getChecked');
	
	if (rows.length > 0) {
		//判断借款是否为展期
		
		
		for (var i = 0; i < rows.length; i++) {
		$.ajax({
			type : "POST",
			url : "after/repayInAdvance/hasExtension",
			dataType : "json",
			data : {
				loanId : rows[i].loanId
			},
			success : function(message) {
				 waring = "您确定要提交吗？";
				if (message == true) {
					waring = "选中借款中有展期，如果申请，将会取消其展期，您确定要提交吗？";
					zhanqi++;
				}
			}
		});
		}
		if(zhanqi<=0){
		$.messager
				.confirm(
						'确认',
						'您确认想要申请选中的' + (rows.length) + '条借款信息的提前扣款吗？',
						function(r) {
							if (r) {
								for (var i = 0; i < rows.length; i++) {
									/*alert('Item ID:' + rows[i].loanId);*/
									/*
									 * $.messager.confirm("Confirm", waring,
									 * function (r) {
									 */id=rows[i].loanId;
									if (r) {
										$
												.ajax({
													type : "POST",
													url : "after/repayInAdvance/plSubmitRepayInAdvance",
													dataType : "json",
													data : {
														loanId : rows[i].loanId
													},
													 async:false,
													 complete:function(message){
														 /*  var ss=hid+result;
														   var button="<input id='Info' type='button' value='"+ss+"'/>"; 
												   		    $("#plMessage").append(button);*/
													 /* alert(lines);*/
														   $.messager.show({
																title : '提示',
																msg : lines,
																showType:'slide',  
															    height:'100%'
															});
														   $("#plRepayInAdvancePageTb").datagrid('reload');
														  /* $('#plList_result').datagrid('reload');*/
													   
													  },
													success : function(message) {
														var result="["+rows[i].name+"]"+message;
														handleData(result);
														if (message == "success") {
															plInfo="编号:["+id+"]提交成功!\n";
															/*plMessenge.append('编号:['+id+']提交成功!\n');*/
															/*$.messager.show({
															title : '提交',
															msg : plMessenge,
															showType : 'slide'
														});*/
														} else {
															/*$.messager
																	.show({
																		title : '提示',
																		msg : message,
																		showType : 'slide'
																	});*/
															plMessenge="编号:["+rows[i].loanId+"]"+message+"\n";
														}
													},
													error : function() {
														/*$.messager.show({
															title : '提交',
															msg : '提交失败！',
															showType : 'slide'
														});*/
														plInfo=+"编号:["+rows[i].loanId+"]提交失败!\n";
													}
												});
									}
									/* }); */
								}
								/*$.messager
								.show({
									title : '提示',
									msg : '批量提交结束！',
									showType : 'slide'
								});*/
								/*$("#plRepayInAdvancePageTb").datagrid('reload');*/
							}
						});
		}else{
			$.messager
			.confirm(
					'确认',
					'选中借款中有展期，如果申请，将会取消其展期，您确定要提交吗？',
					function(r) {
						if (r) {
							for (var i = 0; i < rows.length; i++) {
							/*	alert('Item ID:' + rows[i].loanId);*/
								/*
								 * $.messager.confirm("Confirm", waring,
								 * function (r) {
								 */
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "after/repayInAdvance/plSubmitRepayInAdvance",
												dataType : "json",
												data : {
													loanId : rows[i].loanId
												},
												 async:false,
												 complete:function(message){
													 /*  var ss=hid+result;
													   var button="<input id='Info' type='button' value='"+ss+"'/>"; 
											   		    $("#plMessage").append(button);*/
												 /* alert(lines);*/
													   $.messager.show({
															title : '提示',
															msg : lines,
															showType:'slide',  
														    height:'100%'
														});
													   $("#plRepayInAdvancePageTb").datagrid('reload');
													  /* $('#plList_result').datagrid('reload');*/
												   
												  },
												success : function(message) {
													var result="["+rows[i].name+"]"+message;
													handleData(result);
													if (message == "success") {
														/*$.messager
																.show({
																	title : '提示',
																	msg : '提交成功！',
																	showType : 'slide'
																});*/
														plMessenge+="编号:["+rows[i].loanId+"]提交成功!/n";

													} else {
														/*$.messager
																.show({
																	title : '提示',
																	msg : message,
																	showType : 'slide'
																});
																*/		
														plMessenge+="编号:["+rows[i].loanId+"]"+message+"/n";
														}
												},
												error : function() {
													/*$.messager.show({
														title : '提交',
														msg : '提交失败！',
														showType : 'slide'
													});*/
													plMessenge+="编号:["+rows[i].loanId+"]提交失败!/n";
												}
											});
								}
								/* }); */
							}
							/*$.messager
							.show({
								title : '提示',
								msg : '批量提交结束！',
								showType : 'slide'
							});*/
							/*$("#plRepayInAdvancePageTb").datagrid('reload');*/
						}
					});
			
		}
	} else {
		$.messager.alert("操作提示", "请至少选择一条数据！");
	}
}

/**
 * 批量一次性结清
 */
var jum = 0;
function plRepayOneTime() {
	lines="";
	var rows = $('#plRepayInAdvancePageTb').datagrid('getChecked');
	/*alert(rows);*/

	if (rows.length > 0) {
		//判断借款是否为展期
		var zhanqi=0;
		var waring='';
		for (var i = 0; i < rows.length; i++) {
		$.ajax({
			type : "POST",
			url : "after/repayInAdvance/hasExtension",
			dataType : "json",
			data : {
				loanId : rows[i].loanId
			},
			success : function(message) {
				 waring = "您确定要提交吗？";
				if (message == true) {
					waring = "选中借款中有展期，如果申请，将会取消其展期，您确定要提交吗？";
					zhanqi++;
				}
			}
		});
		}
		if(zhanqi<=0){
		$.messager
				.confirm(
						'确认',
						'您确认想要申请选中的' + (rows.length) + '条借款信息的一次性结清吗？',
						function(r) {
							if (r) {
								for (var i = 0; i < rows.length; i++) {
									/*alert('Item ID:' + rows[i].loanId);*/
									/*
									 * $.messager.confirm("Confirm", waring,
									 * function (r) {
									 */
									if (r) {
										$
												.ajax({
													type : "POST",
													url : "after/repayInAdvance/plSubmitRepayOneTime",
													dataType : "json",
													data : {
														loanId : rows[i].loanId
													},
													 async:false,
													 complete:function(message){
														 /*  var ss=hid+result;
														   var button="<input id='Info' type='button' value='"+ss+"'/>"; 
												   		    $("#plMessage").append(button);*/
													 /* alert(lines);*/
														   $.messager.show({
																title : '提示',
																msg : lines,
																showType:'slide',  
															    height:'100%'
															});
														   $("#plRepayInAdvancePageTb").datagrid('reload');
														  /* $('#plList_result').datagrid('reload');*/
													   
													  },
													success : function(message) {
														var result="["+rows[i].name+"]"+message;
														handleData(result);
														if (message == "success") {
															/*$.messager
																	.show({
																		title : '提示',
																		msg : '提交成功！',
																		showType : 'slide'
																	});
															$(
																	"#plRepayInAdvancePageTb")
																	.datagrid(
																			'reload');*/
														} else {
															/*$.messager
																	.show({
																		title : '提示',
																		msg : message,
																		showType : 'slide'
																	});*/
														}
													},
													error : function() {
														/*$.messager.show({
															title : '提交',
															msg : '提交失败！',
															showType : 'slide'
														});*/
													}
												});
									}
									/* }); */
								}
								/*$.messager
								.show({
									title : '提示',
									msg : '批量一次性结清结束！',
									showType : 'slide'
								});
								$("#plRepayInAdvancePageTb").datagrid('reload');*/
							}
						});
		}else{
			$.messager
			.confirm(
					'确认',
					'选中借款中有展期，如果申请，将会取消其展期，您确定要提交吗？',
					function(r) {
						if (r) {
							for (var i = 0; i < rows.length; i++) {
							/*	alert('Item ID:' + rows[i].loanId);*/
								/*
								 * $.messager.confirm("Confirm", waring,
								 * function (r) {
								 */
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "after/repayInAdvance/plSubmitRepayOneTime",
												dataType : "json",
												data : {
													loanId : rows[i].loanId
												},
												async:false,
												 complete:function(message){
													 /*  var ss=hid+result;
													   var button="<input id='Info' type='button' value='"+ss+"'/>"; 
											   		    $("#plMessage").append(button);*/
												 /* alert(lines);*/
													   $.messager.show({
															title : '提示',
															msg : lines,
															showType:'slide',  
														    height:'100%'
														});
													   $("#plRepayInAdvancePageTb").datagrid('reload');
													  /* $('#plList_result').datagrid('reload');*/
												   
												  },
												success : function(message) {
													var result="["+rows[i].name+"]"+message;
													handleData(result);
													if (message == "success") {
														/*$.messager
																.show({
																	title : '提示',
																	msg : '提交成功！',
																	showType : 'slide'
																});
														$(
																"#plRepayInAdvancePageTb")
																.datagrid(
																		'reload');*/
													} else {
														/*$.messager
																.show({
																	title : '提示',
																	msg : message,
																	showType : 'slide'
																});*/
													}
												},
												error : function() {
													/*$.messager.show({
														title : '提交',
														msg : '提交失败！',
														showType : 'slide'
													});*/
												}
											});
								}
								/* }); */
							}
							/*$.messager
							.show({
								title : '提示',
								msg : '批量一次性结清结束！',
								showType : 'slide'
							});
							$("#plRepayInAdvancePageTb").datagrid('reload');*/
						}
					});
			
		}
	} else {
		$.messager.alert("操作提示", "请至少选择一条数据！");
	}
}