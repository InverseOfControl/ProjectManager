$(function(){
	initDwr();
})

function initDwr(){
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true);
}

/**
 * 电子签章回调js
 * @param msg
 */
function jsEleSignCallback(loanId){
	$('#eleSignCallbackPage').find("iframe").attr("src","audit/contract/openLcbEleSignPage?loanId="+loanId);
}

/**
 * 捞财宝电子签章回调js
 * 当捞财宝电子签章成功之后，调用我们的生成合同方法
 * @param msg
 */
function jsLcbEleSignCallback(loanId){
	contractSign(loanId);
	$('#eleSignCallbackPage').dialog("close");
}

function loading(msg){  
    $("<div class='datagrid-mask' style='z-index:100001'></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class='datagrid-mask-msg' style='z-index:100002'></div>").html("正在处理，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}  

function closeLoading(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}

/**
 * 对接捞财宝-弹框
 */
var lcbDialogFn = {
		//打开注册协议
		openRegisterAgreement : function(){
			window.open("https://www.laocaibao.com/credit/contract/registerContract");
		},	
		//捞财宝注册弹出框
		lcbRegisterDialog : function(){
			$('#lcbRegisterDlg').dialog({
				title: '捞财宝注册登录',
				width: 350,
				height: 200,
				closed: false,
				cache: true,
				href: 'audit/contract/toLcbRegisterPage',
				modal: true
			});   
			$('#lcbRegisterDlg').show();
		},
		//捞财宝登录弹出框
		lcbLoginDialog : function() {
			$('#lcbLoginDlg').dialog({
				title: '捞财宝注册登录',
				width: 350,
				height: 200,
				closed: false,
				cache: false,
				href: 'audit/contract/toLcbLoginPage',
				modal: true
			});   
			$('#lcbLoginDlg').show();
		},
		//电子签章回调页面
		eleSignCallbackDialog : function(loanId) {
			$('#eleSignCallbackPage').dialog({
				title: '电子签章',
				width: 1000,
				height: 750,
				closed: false,
				cache: false,
				content:"<iframe scrolling='auto' frameborder='0' src='audit/contract/openEleSignPage?loanId="+loanId+"' style='width:100%; height:90%; display:block;'></iframe>",
				modal: true
			});   
			$('#eleSignCallbackPage').show();
		}
}

/**
 * 对接捞财宝-ajax请求
 */
function lcbAjax(lcbStatusNode){
	
	alert();
	$('#ff').append('<input type="hidden" name="lcbStatusNode" value="'+lcbStatusNode+'" />');
	if(lcbStatusNode == "1" || lcbStatusNode == "2"){
		var $form = (lcbStatusNode == "1") ? $("#lcbRegisterForm") : $("#lcbLoginForm");
		var lcbRegisterVerifyCode = $form.find("input[name='lcbVerifyCode']").val();
		$('#ff').append('<input type="hidden" name="lcbVerifyCode" value="'+lcbRegisterVerifyCode+'" />');
	}
	var ret = null;
	/*$.ajax({
		type : 'post',
		url : 'audit/contract/generateContractPre',
		data : $('#ff').serialize(),
		async : false,
		success : function(result) {
			ret = result;
		},
		beforeSend : function(){
			loading();
			alert();
			console.log("====================");
			return false;
		},
		complete : function(){
			$('#ff').find("input[name='lcbStatusNode']").remove();
			if(lcbStatusNode == "1" || lcbStatusNode == "2"){
				$('#ff').find("input[name='lcbVerifyCode']").remove(); 
			}
			closeLoading();
		}
	});*/
	return ret;
}
/*var lcbAjaxFn = {
	lcbAjax : function(lcbStatusNode){
		alert();
		$('#ff').append('<input type="hidden" name="lcbStatusNode" value="'+lcbStatusNode+'" />');
		if(lcbStatusNode == "1" || lcbStatusNode == "2"){
			var $form = (lcbStatusNode == "1") ? $("#lcbRegisterForm") : $("#lcbLoginForm");
			var lcbRegisterVerifyCode = $form.find("input[name='lcbVerifyCode']").val();
			$('#ff').append('<input type="hidden" name="lcbVerifyCode" value="'+lcbRegisterVerifyCode+'" />');
		}
		var ret = null;
		$.ajax({
			type : 'post',
			url : 'audit/contract/generateContractPre',
			data : $('#ff').serialize(),
			async : false,
			success : function(result) {
				ret = result;
			},
			beforeSend : function(){
				loading();
				alert();
				console.log("====================");
				return false;
			},
			complete : function(){
				$('#ff').find("input[name='lcbStatusNode']").remove();
				if(lcbStatusNode == "1" || lcbStatusNode == "2"){
					$('#ff').find("input[name='lcbVerifyCode']").remove(); 
				}
				closeLoading();
			}
		});
		return ret;
	}
}
*/
/**
 * 对接捞财宝-业务方法
 */
var lcbBaseFn = {
	//验证码倒计时
	countDown : function(formType){
		if(!lcbBaseFn.lcbExecute('9')){
			return false;
		}
		$("#"+formType).find("tr:eq(0) td:eq(2) span:eq(0)").hide();
		lcbBaseFn.setTime(formType,60);
	},	
	setTime : function(formType,val){
		val = parseInt(val);
		if(val == 0){
			$("#"+formType).find("tr:eq(0) td:eq(2) span:eq(1)").text('');
			$("#"+formType).find("tr:eq(0) td:eq(2) span:eq(0)").show();
			return;
		}else{
			val--;
			$("#"+formType).find("tr:eq(0) td:eq(2) span:eq(1)").text(val+"s");
		}
		setTimeout(function() { 
			lcbBaseFn.setTime(formType,val) 
		},1000)
	},
	//获取捞财宝认证状态
	getLcbStatus : function(){
		var ret = null;
		$.ajax({
			type : 'post',
			url : 'audit/contract/getLcbStatus',
			data : $('#ff').serialize(),
			async : false,
			success : function(result) {
				ret = result;
			}
		});
		return ret;
	},
	//公共提示
	lcbTip : function(code,tip){
		parent.$.messager.show({
			title : '提示',
			msg : tip,
			showType:'fade'
		});
		if(code == "000000"){
			return true;
		}else{
			return false;
		}
	},
	lcbTipMsg : function(code){
		var msg;
		if(code == "0") msg = "手机一致性校验成功";
		if(code == "1") msg = "注册成功";
		if(code == "2") msg = "登录成功";
		if(code == "3") msg = "实名认证成功";
		if(code == "4") msg = "绑卡成功";
		if(code == "8") msg = "银行卡校验成功";
		if(code == "9") msg = "验证码获取成功";
		if(code == "10") msg = "推标成功";
		if(code == "11") msg = "电子签章成功";
		return msg;
	},
	/**
	 * @param type 0-手机校验接口；1-注册接口；2-登录接口；3-实名认证接口；4-绑卡接口；8-银行卡校验；9-获取验证码接口；10-推标；11-电子签章
	 */
	lcbExecute : function(type,callback){
		var ret = lcbAjax(type);
		var msg = ret.repCode == "000000" ? lcbBaseFn.lcbTipMsg(type) : ret.repMsg;
		if(typeof callback == "function"){
			callback(ret.repCode);
		} 
		return lcbBaseFn.lcbTip(ret.repCode,msg);
	},
	/**
	 * 捞财宝实名、绑卡 
	 * 如果捞财宝返回登录超时，弹出登录框，获取验证码重新登录捞财宝
	 */
	lcbRealNameBindCard : function(){
		var bool = lcbBaseFn.lcbExecute("3",function(repCode){
			if(repCode == "300001") lcbDialogFn.lcbLoginDialog();
		});
		if(!bool) return false;
		
		var bool = lcbBaseFn.lcbExecute("4",function(repCode){
			if(repCode == "300001") lcbDialogFn.lcbLoginDialog();
		});
		if(!bool) return false;
		
		return true;
	},
	/**
	 * 捞财宝推标和生成合同
	 */
	lcbBidPush : function(){
		if(!lcbBaseFn.lcbExecute("10")){
			return false;
		}
		//生成合同
		doGenerateContract();
	},
	/**
	 * 电子签章 
	 * 1.先推送待签约合同信息到电子签章系统
	 * 2.免登陆打开签章页面进行签名
	 */
	lcbSignName : function(loanId){
		parent.$.messager.confirm('确认','签订合同？', function(r) {
			if(!r){
				return false;
			}
			
			$.ajax({
				type : 'post',
				url : 'audit/contract/signContractPre',
				data: {"loanId":loanId},
				async : false,
				success : function(result) {
					if(result.repCode == "000000"){
						lcbDialogFn.eleSignCallbackDialog(loanId);
					}
					lcbBaseFn.lcbTip(result.repCode,result.repMsg);
				},
				beforeSend : function(){
					loading();
				},
				complete : function(){
					closeLoading();
				}
			});
		});
	},
	/**
	 * 捞财宝注册、登录页面确定按钮操作
	 */
	lcbOk : function(type){
		if(!lcbBaseFn.lcbExecute(type)){
			return false;
		};
		if(!lcbBaseFn.lcbRealNameBindCard()){
			return false;
		}
		if(!lcbBaseFn.lcbBidPush()){
			return false;
		}
		//关闭弹框
		if(type == 1) $('#lcbRegisterDlg').dialog('close');
		if(type == 2) $('#lcbLoginDlg').dialog('close');
	}
}


/**
 * 生成合同前置校验
 */
function doGenerateContractPre() {
	//银行卡是否支持放款
	if(!lcbBaseFn.lcbExecute("8")){
		return false;
	}
	
	//捞财宝手机号一致性校验
	if(!lcbBaseFn.lcbExecute("0")){
		return false;
	}
	
	var lcbStatus = lcbBaseFn.getLcbStatus();
	var isLcbRegister = lcbStatus.isLcbRegister;
	//判断有没有注册，如果没有注册，弹出注册登录框
	if(isLcbRegister != "0"){
		lcbDialogFn.lcbRegisterDialog();
		return false;
	}
	
	//实名、绑卡
	if(!lcbBaseFn.lcbRealNameBindCard()){
		return false;
	}
	
	//推标
	if(!lcbBaseFn.lcbBidPush()){
		return false;
	}
}