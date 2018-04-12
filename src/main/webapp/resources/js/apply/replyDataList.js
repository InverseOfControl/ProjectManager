var url;
$(function () {
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
    	$('#startDate3').val(CurentTime());
    	$('#endDate3').val("2017-01-01");
	   
	});
	// 查询按钮
	$('#searchBt').bind('click', search);
	
	$('#approverState').combobox('select', '1');


	 // 列表
    $('#checkPageTb').datagrid({
        url: 'reply/replyData/getreplyDataPage',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        fit:true,
        queryParams: {
        	approverState: '1'
    		 
    	},

        columns : [ [       
                     {
             			field : 'id',
             			title : 'id',
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     },       
         {
			field : 'orgName',
			title : '机构名称',
			formatter: function(value, row, index){
	          	return value;
	        	}
        }, 
		  
		{
			field : 'name',
			title : '方案名称',
			formatter: function(value, row, index){
				var oper = '';
				oper = '<a style="color:blue" href="javascript:void(0)" onclick="loadChannelPlanInfoToWindow(' + row.id + ');">'+ row.name +'</a>'
				return oper;
	        	}
		},{
			field : 'requestMoney',
			title : '借款金额',
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	          }
		}, {
			field : 'pactMoney',
			title : '合同金额',
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	          }
		}, {
			field : 'time',
			title : '借款期数',formatter: function(value, row, index){
	          	return   value;
	          }
		}, {
			field : 'toTerm1',
			title : '分段期限1',
			formatter: function(value, row, index){
				return value;
	        }
		}, {
			field : 'returneterm1',
			title : '分段还款金额1',formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
        	}
		},{
			field : 'toTerm2',
			title : '分段期限2',formatter: function(value, row, index){
				return value;
		}
		},{
			field : 'returneterm2',
			title : '分段还款金额2',formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
		}
		}, {
			field : 'margin',
			title : '保证金',formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
		}
		}, {
			field : 'rateSum',
			title : '服务费合计',formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
			
		}
		}, {
			field : 'approverState',
			title : '方案状态',formatter: function(value, row, index){
						  if(value=='1'){
							return "待批复";
						  }else if(value=='2'){
								return "方案重提";
							}	else if(value=='3'){
							return "生效";
						 }else if(value=='4'){
							 return "拒绝";
						 } 
					 
				 
			
		}
		}, {
			field : 'startDate',
			title : '开售日期',formatter: formatRequestDate
		},{
			field : 'endDate',
			title : '停售日期',formatter: formatRequestDate
		},   {	
			field : 'planId',
			title : '操作',
			formatter : function(value, row, rowIndex) {	
				var link = "";
				if(row.approverState==0 || row.approverState==1 || row.approverState==2  ){
					link += "<a href='javascript:replyPlan(\""+row.id+"\")' >批复</a>";	
				}	
				return  link;
			},width : 160
		}] ]
	});
    // 设置分页控件
   var p = $('#checkPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
 // 确定按钮
	$("#refuseSubmitBt").bind('click',submitRefuse);
	 // 导出按钮
	$("#exportExcelBt").bind('click',exportExcel);
	 //退回窗口点击取消
	$("#refuseCancelBt").bind('click',cencalRefuse);
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	 
	 

    
});
function radioChange(){
	 
	 var termind =$('input:radio[name="approverState"]:checked').val(); 
	 	if(termind=="3"){ 
	 		document.getElementById("spantext").innerHTML ="备注";
	 	}else{ 
	 		document.getElementById("spantext").innerHTML ="退回原因";
	 	} 
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
//显示附件
function showAttachmentDlg(loanId){
	 window.open (rayUseUrl+"finance/financialAudit/imageUploadView/"+loanId,"newwindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
//业务日志
function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '财务审核日志',
		width: 900,
		height: 300,
		closed: false,
		cache: false,
		modal: true
	});
	var url = 'audit/businessLog/detail.json/' + id;
	$('#business_log_result').datagrid({
		url: url,
		fitColumns: true,
		border: false,
		singleSelect:true,
		pagination: true,
		pageSize: 100,
		striped: true,
		rownumbers: true,
		fit:true,
		nowrap:false,	
		columns: [[
		           {field: 'operator', title: '操作者', width: 50},
		           {field: 'flowStatusView', title: '环节', width: 60},
		           {field: 'createDate', title: '操作时间', width: 100},
		           {field: 'message', title: '日志内容', width: 300}
		]]
    });
	 // 设置分页控件
	 var p = $('#business_log_result').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
}

// 财务审核
function financialAudit(loanId){
	parent.$.messager.confirm('确认','审核确认', function(r) {
		if(r) {
			$.post('finance/financialAudit/financialAudit',{loanId:loanId},function(result){
				 if (result=='success'){
					parent.$.messager.show({
						title : '提示',
						msg : '审核确认成功！'
					});
					$('#list_result').datagrid('reload'); // reload the user data
				 } else {
					 parent.$.messager.show({ // show error message
						 title: 'Error',
						 msg: result.errorMsg
					 });
				 }	
			});
		}
	});
}
function clearRefuse(){
	 $("#dlg").find("#reason").val('');
	 $("#dlg").find("#reason1").combobox('clear');
	 $("#dlg").find("#reason2").combobox('clear');
}

function replyPlan(id){
	 
	/* $("#dlg").dialog("open").dialog('setTitle', '财务审核退回');*/
 
	 $('#seReplyEditDlg').dialog({
			title : '批复方案',
			closed : false,
			cache : false,
			href : "reply/replyData/seReplyEdit/"+id,
			modal : true,
	 });
	 
}
/** 编辑产品保存**/
/*function replyEditSave() {
	 
	var checkStatus=$('input[name="approverState"]:checked ').val();
	var sendBackMemo=$('#sendBackMemo').val();
	if(checkStatus==4){
	  if(sendBackMemo==null || sendBackMemo==''){
		  $.messager.show({
				title: '提示',
				msg: '请输入退回原因！'
			});
		   return false;
	  }
	}
	 
	var text=$("#spanStartDate").html();
	 
	 $.messager.confirm('确认对话框', '确定要提交吗？提交后该方案启用，开售时间为'+text, function(r) {
		if (r) {
			doSaveEditReply();
		} else {
			return false;
		}
	}); 
}
*/

function doSaveEditReply() {
//	alert( $('#productSeEditForm').serialize());
	$.ajax({
		type : 'post',
		url : 'reply/replyData/replyEditSave',
		data : $('#replySeEditForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				if(result.flag){
					parent.$.messager.alert('提示','提交成功！');
				}else{
					parent.$.messager.alert('提示','提交成功！');
				}
				
				$('#seReplyEditDlg').dialog('close');
				var queryParams = $('#checkPageTb').datagrid('options').queryParams;
				queryParams.name = $('#name').val();
				queryParams.orgName = $('#orgName').val();
				queryParams.orgNum = $('#orgNum').val();
				if($('#approverState').combobox('getValue')=="all"){
			    	 queryParams.approverState =null;
			    }else{
				    queryParams.approverState = $('#approverState').combobox('getValue');
			    }
				setFirstPage("#checkPageTb");
				$('#checkPageTb').datagrid('options').queryParams = queryParams;
				$("#checkPageTb").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

//点击取消
function  cencalRefuse(){
	$('#seReplyEditDlg').dialog('close');	
}

//财务退回提交
function submitRefuse() {
	// 验证FORM	
	 if(!$("#refuseReasonForm").form('validate')){
	        return false;
	    }
	 if($("#reason").val()==''){
		 	$.messager.show({
				title: '提示',
				msg: '请输入退回原因！'
			});
		   return false;
	 }
	 $("#reason1").combobox('setValue',$("#reason1").combobox('getText'));
	 $("#reason2").combobox('setValue',$("#reason2").combobox('getText'));	
	 parent.$.messager.confirm('确定','确认退回？', function(r) {
		 if(r) {	
		    $.ajax({
		   url : 'finance/financialAudit/financialReturn',
		   data : $("#refuseReasonForm").serialize(),
		   type:"POST",
		   success : function(result){
		   		if(result=='success'){
		   			$.messager.show({
						title : '提示',
						msg : '保存成功！'
					});
		   			$('#dlg').dialog('close');
		   			$('#list_result').datagrid('reload');
		   		}else{
		   			parent.$.messager.show({
						title: 'Error',
						msg: result
		   			});
		   			}
		   		}
			 });	
		 }
	 });
}
function search(){
	var queryParams = $('#checkPageTb').datagrid('options').queryParams;
	queryParams.name = $('#name').val();
	queryParams.orgName = $('#orgName').val();
	queryParams.orgNum = $('#orgNum').val();
	if($('#approverState').combobox('getValue')=="all"){
    	 queryParams.approverState =null;
    }else{
	    queryParams.approverState = $('#approverState').combobox('getValue');
    }
	setFirstPage("#checkPageTb");
	$('#checkPageTb').datagrid('options').queryParams = queryParams;
	$("#checkPageTb").datagrid('reload');
}




//加载某方案信息填充到表单
function loadChannelPlanInfoToWindow (channelId) {
	 var h = $(window).height() ;

	$.ajax({
		url : 'channelPlan/loadChannelPlanInfo',
		data : {
			id : channelId						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#channelPlanEditPanel').window({
//					width:300,
					height: h*0.8,
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
				code : $('#code3').val()						
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
	//修改方案信息
		var approverState=$('#approverState2').val();
		var nowDate = CurentTime();
		nowDate=new Date(nowDate.replace(/-/g,   "/"));
		var startDate2=$('#startDate2').val();
		var startDate=new Date(startDate2.replace(/-/g,   "/"));  
		var endDate2=$('#endDate2').val();
		var endDate=new Date(endDate2.replace(/-/g,   "/"));  
		if(approverState!='3'){
			if(startDate<nowDate){
				var isErr = '';
				$.messager.alert('操作提示', "开售时间不能早于当天",isErr);
				return;
			}
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