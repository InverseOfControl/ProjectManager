var organizationId=$('#organViewForm #organId', parent.document).val();
var organizationName=$('#organViewForm #organName', parent.document).val();

$(function() {
	//开启表单验证
    $('input.validateItem').validatebox({
        required:true
    });
    //开启表单验证
    $('select.validateItem').combo({
        required:true
        //multiple:true
    }); 
   
    
    $('#channelPlanPanel').window('close');
    $('#channelPlanEditPanel').window('close');
    
	$('.commonCloseBut').click(function(){
		$(this).parents('div .easyui-window').window('close');
	});
    
    $('#insertChannelPlanBut').click(function() {
    	$('#channelPlanPanel').form('clear');
		$('#channelPlanPanel').window({
			modal:true
		});
		$("#returneterm13").val("");
		$("#returneterm23").val("");
		$("#organName3").html(organizationName);
    	$("#startDate3").val(CurentTime());
    	$("#endDate3").val("2017-01-01");
	});
    
	$('#searchBut').click(function() {
	    $("#channelPlanGrid").datagrid('reload');
	});
	
	// 初始化列表
	$('#channelPlanGrid').datagrid({
		url : 'channelPlan/getSearchJson',
		queryParams:{
			organizationId:organizationId
		},
		fitColumns : true,
		border : false,
		singleSelect : true,
		pagination : true,
		striped : true,
		pageSize : 10,
		rownumbers : true,
		nowrap:false,
		columns :
		[
		   [ {
			field : 'code',
			title : '方案编号',
			formatter : function(value, row, index) {
				return value;
			},
			width : 100
		}, {
			field : 'name',
			title : '方案名称',
			formatter : function(value, row, index) {
				var oper = '';
				oper = '<a style="color:blue" href="javascript:void(0)" onclick="loadChannelPlanInfoToWindow(' + row.id + ');">'+ row.name +'</a>';
				return oper;
			},
			width : 100
		}, {
			field : 'startDate',
			title : '开售时间',
			width : 80,
			formatter : function(value, row, index) {
				return value.substring(0,10);
			}
		}, {
			field : 'endDate',
			title : '停售时间',
			formatter : function(value, row, index) {
				return value.substring(0,10);
			},
			width : 80
		} ,{
			field : 'requestMoney',
			title : '申请金额',
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			},
			width : 100
		} ,{
			field : 'time',
			title : '借款期限',
			formatter : function(value, row, index) {
				return row.time;
			},
			width : 80
		}  ,{
			field : 'orgRepayTerm',
			title : '机构还款期数',
			formatter : function(value, row, index) {
				return row.orgRepayTerm;
			},
			width : 100
		}  ,{
			field : 'toTerm1',
			title : '分段期限1',
			formatter : function(value, row, index) {
				return row.toTerm1;
			},
			width : 80
		} 
		 ,{
				field : 'returneterm1',
				title : '分段还款金额1',
				formatter : function(value, row, index) {
					if(value != null){
						return value.toFixed(2);
						}else{
							return value;
						}
				},
				width : 100
			} 
		 ,{
				field : 'toTerm2',
				title : '分段期限2',
				formatter : function(value, row, index) {
					return row.toTerm2;
				},
				width : 80
			} 
		 ,{
				field : 'returneterm2',
				title : '分段还款金额2',
				formatter : function(value, row, index) {
					if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
				},
				width : 100
			} 
		,{
			field : 'approverState',
			title : '方案状态',
			formatter : function(value, row, index) {
				var statusName = '';
				if (row.approverState == '1') {
					statusName = '待批复';
				}else if (row.approverState == '2') {
					statusName = '方案重提';
				}else if (row.approverState == '3') {
					statusName = '生效';
				}else if(row.approverState == '4'){
					statusName = '拒绝';
				}
				return statusName;
			},
			width : 80
		},{
			field : 'sendBackMemo',
			title : '拒绝原因',
			formatter:
				function(value, row, index){
					return value;
			},
			width : 160
		} 
		] 
		],
		onBeforeLoad : function(param) {
			
		},
		onLoadSuccess:function(param) {
			
		},
		onLoadError : function(e) {
			
		},
		onClickCell : function(rowIndex, field, value) {
			
		}
	});
	
});



//加载某方案信息填充到表单
function loadChannelPlanInfoToWindow (channelId) {
	$.ajax({
		url : 'channelPlan/loadChannelPlanInfo',
		data : {
			id : channelId						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#channelPlanEditPanel').window({
					modal:true
				});
				$(".editBtn").css("display", "");
				$(".saveBtn").css("display", "none");
				$('#channelPlanEditForm').find('input').attr("disabled",false);
				$('#channelPlanEditForm').find('textarea').attr("disabled",false);
				$('#returneterm12').val('');
				$('#returneterm22').val('');
				$('#toTerm22').val('');
				$('#margin2').val('');
				$('#channelPlanEditPanel').form('load', result.channelPlan);
				$('#channelPlanEditForm').find('#startDate2').val(result.channelPlan.startDate.substring(0,10));
				$('#channelPlanEditForm').find('#endDate2').val(result.channelPlan.endDate.substring(0,10));
				$('#channelPlanEditForm').find('#organName2').html(result.channelPlan.orgName);
				$('#returnType2').combobox('disable');
				$('#orgFeeState2').combobox('disable');
				$('#channelPlanEditForm').find('input').attr("disabled",true);
				$('#channelPlanEditForm').find('textarea').attr("disabled",true);
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

function toEditChannelPlanInfo(){
	

	if($('#approverState2').val()=='3'){
			$(".editBtn").css("display", "none");
			$(".saveBtn").css("display", "");
			$('#channelPlanEditForm').find('#endDate2').attr("disabled",false);
			$('#channelPlanEditForm').find('#checkId2').attr("disabled",false);
			$('#channelPlanEditForm').find('#planId2').attr("disabled",false);
			$('#channelPlanEditForm').find('#approverState2').attr("disabled",false);
	}else{
	$(".editBtn").css("display", "none");
	$(".saveBtn").css("display", "");
	$('#channelPlanEditForm').find('input').attr("disabled",false);
	$('#channelPlanEditForm').find('#code2').attr("disabled",true);
	$('#channelPlanEditForm').find('#name2').attr("disabled",true);
	$('#returnType2').combobox('enable');
	$('#orgFeeState2').combobox('enable');
	var returnType=$('#returnType2').combobox('getValue');
	if(returnType=='1'){
		  $('#toTerm22').attr("disabled",true);
		  $('#toTerm22').val('');
		  $('#toTerm22').removeClass("validateItem");
		  $('#toTerm22').removeClass("validatebox-invalid");
		  $('#toTerm22').removeClass("validatebox-text");
		  $('#returneterm22').attr("disabled",true);
		  $('#returneterm22').val('');
		  $('#returneterm22').removeClass("validateItem");
		  $('#returneterm22').removeClass("validatebox-invalid");
		  $('#returneterm22').removeClass("validatebox-text");
	}else{
		   $('#toTerm22').addClass("validateItem");
		   $('#toTerm22').attr("disabled",false);
		   $('#returneterm22').addClass("validateItem");
		   $('#returneterm22').attr("disabled",false);
	}
	$('#channelPlanEditForm').find('textarea').attr("disabled",false);
	}
}

//删除方案信息
function doDeleteChannelPlanInfo(){
	if($('#approverState2').val()=='3'){
		$.messager.alert('操作提示', '方案不可删除','error');
		return;
	}
	var deleteId=$('#checkId2').val();
	$.messager.confirm("提示","是否确认删除方案",function(r){
		if(r){
			$.ajax({
				url : 'channelPlan/deleteChannelPlanInfo',
				data : {
					id : deleteId						
				},
				type:'POST',
				success : function(result){
					var isErr = '';
					if (result.isSuccess) {
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', result.msg,isErr);
					$('#searchBut').trigger('click');
					if (!(result.msg == '')) {
						$('#channelPlanEditPanel').window('close');
					}
				},
				error:function(data){
					$.messager.alert('操作提示', 'error','error');
				}
			});
		}
	});
}

//新增方案信息
function doSaveChannelPlanInfo() {
	var nowDate = CurentTime();
	nowDate=new Date(nowDate.replace(/-/g,   "/"));
	var startDate3=$('#startDate3').val();
	var startDate=new Date(startDate3.replace(/-/g,   "/"));  
	var endDate3=$('#endDate3').val();
	var endDate=new Date(endDate3.replace(/-/g,   "/"));  
//	if(startDate<nowDate){
//		var isErr = '';
//		$.messager.alert('操作提示', "开售时间不能早于当天",isErr);
//		return;
//	}
	if(endDate<startDate||endDate<nowDate){
		var isErr = '';
		$.messager.alert('操作提示', "停售时间不能早于开售日期",isErr);
		return;
	}	
	var time=$('#time3').val();
	if(time<0||60<time){
		var isErr = '';
		$.messager.alert('操作提示', "借款日期不能超过60个月",isErr);
		return;
	}
	var requestMoney=$('#requestMoney3').val();
	if(requestMoney<1){
		var isErr = '';
		$.messager.alert('操作提示', "申请金额不能小于1元",isErr);
		return;
	}
	var pactMoney=$('#pactMoney3').val();
	if(parseInt(pactMoney)<parseInt(requestMoney)){
		var isErr = '';
		$.messager.alert('操作提示', "合同金额小于申请金额",isErr);
		return;
	}
	var orgRepayTerm=$('#orgRepayTerm3').val();
	var toTerm1=$('#toTerm13').val();
	var returnType=$('#returnType3').combobox('getValue');
	if(returnType=='1'){
		if(orgRepayTerm!=0){
			var isErr = '';
			$.messager.alert('操作提示', "机构还款期限应为0",isErr);
			return;
		}
	}else if(returnType=='0'){
		if(orgRepayTerm!=toTerm1){
			if(orgRepayTerm!=0){
				var isErr = '';
				$.messager.alert('操作提示', "机构还款期限应为0或者第一段还款期数",isErr);
				return;
			}
		}
	}
	var actualRate=$('#actualRate3').val();
	if(actualRate<0||0.02<actualRate){
		var isErr = '';
		$.messager.alert('操作提示', "月综合费率必须在0-0.02之间",isErr);
		return;
	}	
	if(returnType=='0'){
		if(toTerm1>time/2){
			var isErr = '';
			$.messager.alert('操作提示', "第一段还款期数不能多于借款期限的一半",isErr);
			return;
		}
		if(0==toTerm1){
			var isErr = '';
			$.messager.alert('操作提示', "前低后高情况第一段还款期数不能为0",isErr);
			return;
		}
	}
	var timecheck=Number($('#toTerm13').val())+Number($('#toTerm23').val());
	if(Number($('#time3').val())!=timecheck){
		var isErr = '';
		$.messager.alert('操作提示', "期限与期数不符",isErr);
		return;
	}
	$('#organizationId3').val(organizationId);
	var formObj = $('#channelPlanForm');
	if (formObj.form("validate")) {
		$.ajax({
			url : 'channelPlan/checkChannelPlanInfo',
			data : {
				code : $('#code3').val(),
				name : $('#name3').val()
			},
			type:'POST',
			success : function(result){
				var isErr = '';
				if (result.isSuccess) {
					$.post('channelPlan/saveChannelPlanInfo', formObj.serialize(), function (data) {
						var isErr = '';
						if(data.isSuccess){
							
						} else {
							isErr = 'error';
						}
						$.messager.alert('操作提示', data.msg,isErr);
						$('#searchBut').trigger('click');
						if (!(data.msg == '')) {
							$('#channelPlanPanel').window('close');
						}
					});
				} else {
					isErr = 'error';
					$.messager.alert('操作提示', result.msg,isErr);
				}
			},
			error:function(data){
				$.messager.alert('操作提示','error','error');
			}
		});
		
	}
}

//修改方案信息
function doEditChannelPlanInfo() {
	var approverState=$('#approverState2').val();
	
	var nowDate = CurentTime();
	nowDate=new Date(nowDate.replace(/-/g,   "/"));
	var startDate2=$('#startDate2').val();
	var startDate=new Date(startDate2.replace(/-/g,   "/"));  
	var endDate2=$('#endDate2').val();
	var endDate=new Date(endDate2.replace(/-/g,   "/"));  
	if(approverState!='3'){
//		if(startDate<nowDate){
//			var isErr = '';
//			$.messager.alert('操作提示', "开售时间不能早于当天",isErr);
//			return;
//		}
	if(endDate<startDate){
		var isErr = '';
		$.messager.alert('操作提示', "停售时间不能早于开售日期",isErr);
		return;
	}	
	if(endDate<nowDate){
		var isErr = '';
		$.messager.alert('操作提示', "停售时间不能早于当天日期",isErr);
		return;
	}
	var time=$('#time2').val();
	if(time<0||60<time){
		var isErr = '';
		$.messager.alert('操作提示', "借款日期不能超过60个月",isErr);
		return;
	}
	var requestMoney=$('#requestMoney2').val();
	if(requestMoney<1){
		var isErr = '';
		$.messager.alert('操作提示', "申请金额不能小于1元",isErr);
		return;
	}
	var pactMoney=$('#pactMoney2').val();
	if(parseInt(pactMoney)<parseInt(requestMoney)){
		var isErr = '';
		$.messager.alert('操作提示', "合同金额小于申请金额",isErr);
		return;
	}
	var returnType=$('#returnType2').combobox('getValue');
	var orgRepayTerm=$('#orgRepayTerm2').val();
	var toTerm1=$('#toTerm12').val();
	if(returnType=='1'){
		if(orgRepayTerm!=0){
			var isErr = '';
			$.messager.alert('操作提示', "机构还款期限应为0",isErr);
			return;
		}
	}else if(returnType=='0'){
		if(orgRepayTerm!=toTerm1){
			if(orgRepayTerm!=0){
				var isErr = '';
				$.messager.alert('操作提示', "机构还款期限应为0或者第一段还款期数",isErr);
				return;
			}
		}
	}
	var actualRate=$('#actualRate2').val();
	if(actualRate<0||0.02<actualRate){
		var isErr = '';
		$.messager.alert('操作提示', "月综合费率必须在0-0.02之间",isErr);
		return;
	}
	if(returnType=='0'){
		if(toTerm1>time/2){
			var isErr = '';
			$.messager.alert('操作提示', "第一段还款期数不能多于借款期限的一半",isErr);
			return;
		}
		if(0==toTerm1){
			var isErr = '';
			$.messager.alert('操作提示', "前低后高情况第一段还款期数不能为0",isErr);
			return;
		}
	}
	var timecheck=Number($('#toTerm12').val())+Number($('#toTerm22').val());
	if(Number($('#time2').val())!=timecheck){
		var isErr = '';
		$.messager.alert('操作提示', "借款期限与总期数不符",isErr);
		return;
	}
	}else{
		if(endDate<startDate){
			var isErr = '';
			$.messager.alert('操作提示', "停售时间不能早于开售日期",isErr);
			return;
		}	
		if(endDate<nowDate){
			var isErr = '';
			$.messager.alert('操作提示', "停售时间不能早于当天日期",isErr);
			return;
		}
	}
	$.messager.confirm("提示","是否确认编辑方案",function(r){
		if(r){
			var formObj = $('#channelPlanEditForm');
			if (formObj.form("validate")) {
				$.post('channelPlan/editChannelPlanInfo', formObj.serialize(), function (data) {
					var isErr = '';
					if(data.isSuccess){
						
					} else {
						isErr = 'error';
					}
					$.messager.alert('操作提示', data.msg,isErr);
					$('#searchBut').trigger('click');
					if (!(data.msg == '')) {
						$('#channelPlanEditPanel').window('close');
					}
					
				});
			}
		}
	});
}


function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day;
    return(clock); 
} 

function saveCal(){
	var returnType=$('#returnType3').combobox('getValue');
	var orgFeeState=$('#orgFeeState3').combobox('getValue');
	
	if(returnType=='0'){
		var rate=0.08/12;
		var term=$('#time3').val();
		var oldterm=$('#toTerm13').val();
		var pv=$('#requestMoney3').val();
		var oldamt=$('#returneterm13').val();
		var monthRate=$('#actualRate3').val();
		var newterm=term-oldterm;
		if(oldterm!=null&&oldterm!=''&&oldamt!=null&&oldamt!=''&&monthRate!=null&&monthRate!=''&&pv!=null&&pv!=''&&term!=null&&term!=''){
			if(orgFeeState == '01'){
				   $('#toTerm23').val(newterm);
				   $('#returneterm23').val(calSecTermNoOrgan(monthRate, oldamt,  oldterm ,  newterm , pv, term));
			}else{
				   $('#toTerm23').val(newterm);
				   $('#returneterm23').val(calSecTermOrgan(rate, newterm,oldterm,oldamt,pv));
			}
		}
	}
}

function editCal(){
	var returnType=$('#returnType2').combobox('getValue');
	var orgFeeState=$('#orgFeeState2').combobox('getValue');
	if(returnType=='0'){
		var rate=0.08/12;
		var term=$('#time2').val();
		var oldterm=$('#toTerm12').val();
		var pv=$('#requestMoney2').val();
		var oldamt=$('#returneterm12').val();
		var monthRate=$('#actualRate2').val();
		var newterm=term-oldterm;
		if(oldterm!=null&&oldterm!=''&&oldamt!=null&&oldamt!=''&&monthRate!=null&&monthRate!=''&&pv!=null&&pv!=''&&term!=null&&term!=''){
			if(orgFeeState == '01'){
				   $('#toTerm22').val(newterm);
				   $('#returneterm22').val(calSecTermNoOrgan(monthRate, oldamt,  oldterm ,  newterm , pv, term));
			}else{
				   $('#toTerm22').val(newterm);
				   $('#returneterm22').val(calSecTermOrgan(rate, newterm,oldterm,oldamt,pv));
			}
		}
	}
}

function calSecTermNoOrgan(monthRate, oldamt,  oldterm ,  newterm , pv, term){
	return calStuAmountNE(monthRate, oldamt,  oldterm ,  newterm , pv, term).toFixed(2);
}


// 计算学生承担费用 前低后高
function calStuAmountNE(rate, oldamt,  oldterm ,  newterm , pv, term) {
      var pi=pv*(1+(term*rate));
      var pn=oldamt*oldterm;
      var result = (pi-pn)/newterm;
      return result;
  }



function calSecTermOrgan(rate, newterm,oldterm,oldamt,pv){
	var financeAmount=Math.abs(FV(rate,oldterm,oldamt,pv));
	return (PMT(rate, newterm,financeAmount)).toFixed(2);
}

//计算FV 有机构
function FV(rate, nper, pmt, pv) {
    var v = (1 + rate);
    var pi=pv*Math.pow(v, nper);
    var pn=-pmt*(Math.pow(v, nper)-1)/rate;
    var result = -(pi+pn);
    return result;
}

// 计算月供 有机构

function PMT(rate, term,financeAmount) {
    var v = (1 + rate);
    var t = (-(term / 12) * 12);
    var result = (financeAmount * rate) / (1 - Math.pow(v, t));
    return result;
}