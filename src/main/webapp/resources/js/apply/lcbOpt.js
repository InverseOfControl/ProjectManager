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
    $("<div class='datagrid-mask-msg' style='z-index:100003'></div>").html("正在处理，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
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
			//打开弹框后，关闭遮罩
			closeLoading();
		},
		//捞财宝登录弹出框
		lcbLoginDialog : function() {
			$('#lcbLoginDlg').dialog({
				title: '捞财宝注册登录',
				width: 350,
				height: 200,
				closed: false,
				cache: true,
				href: 'audit/contract/toLcbLoginPage',
				modal: true
			});   
			$('#lcbLoginDlg').show();
			//打开弹框后，关闭遮罩
			closeLoading();
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
			//打开弹框后，关闭遮罩
			closeLoading();
		}
}

/**
 * 对接捞财宝-ajax请求
 */
function lcbAjax(lcbStatusNode){
	var defer = $.Deferred();
	
	$('#ff').find("input[name='lcbStatusNode']").remove();
	if(lcbStatusNode == "1" || lcbStatusNode == "2"){
		$('#ff').find("input[name='lcbVerifyCode']").remove(); 
	}
	
	$('#ff').append('<input type="hidden" name="lcbStatusNode" value="'+lcbStatusNode+'" />');
	if(lcbStatusNode == "1" || lcbStatusNode == "2"){
		var $form = (lcbStatusNode == "1") ? $("#lcbRegisterForm") : $("#lcbLoginForm");
		var lcbRegisterVerifyCode = $form.find("input[name='lcbVerifyCode']").val();
		$('#ff').append('<input type="hidden" name="lcbVerifyCode" value="'+lcbRegisterVerifyCode+'" />');
	}
	$.ajax({
		type : 'post',
		url : 'audit/contract/generateContractPre',
		data : $('#ff').serialize(),
		async : true,
		success : function(result){
			var msg = result.repCode == "000000" ? lcbBaseFn.lcbTipMsg(lcbStatusNode) : result.repMsg;
			var bool = lcbBaseFn.lcbTip(result.repCode,msg);
			
			var arr = [];
			arr.push(bool);
			arr.push(result.repCode);
			defer.resolve(arr);
		}
	});
	return defer.promise();
}

/**
 * 对接捞财宝-业务方法
 */
var lcbBaseFn = {
	//验证码倒计时
	countDown : function(formType){
		loading();
		$.when(lcbAjax("9")).done(function(resp){
			if(!resp[0]) return false;
			$("#"+formType).find("tr:eq(0) td:eq(2) span:eq(0)").hide();
			lcbBaseFn.setTime(formType,60);
			closeLoading();
		});
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
		if(code == "000000"){
			return true;
		}else{
			parent.$.messager.show({
				title : '提示',
				msg : tip,
				showType:'fade'
			});
			
			closeLoading();
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
	 * 捞财宝推标和生成合同
	 */
	lcbBidPush : function(){
		$.when(lcbAjax("10")).done(function(resp){
			//生成合同
			if(resp[0]) {
				doGenerateContract();
				$('#lcbRegisterDlg').dialog('close');
				$('#lcbLoginDlg').dialog('close');
			}
		})
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
				success : function(result) {
					if(result.repCode == "000000"){
						lcbDialogFn.eleSignCallbackDialog(loanId);
					}
				},
				beforeSend : function(){
					loading();
				}
			});
		});
	},
	//实名、绑卡、推标、生成合同
	lcbRealNameBindCard : function(){
		$.when(lcbAjax("3")).done(function(resp){
			if(resp[1] == "300001"){
				lcbDialogFn.lcbLoginDialog();
				return false;
			}
			
			if(resp[0]){ //实名成功之后绑卡
				lcbAjax("4").done(function(resp){
					if(resp[0]){ //绑卡成功之后推标
						lcbBaseFn.lcbBidPush();
					}else{
						if(resp[1] == "300001") lcbDialogFn.lcbLoginDialog();
					}
				})
			}
		})
	},
	/**
	 * 捞财宝注册、登录页面确定按钮操作
	 */
	lcbOk : function(type){
		loading();
		$.when(lcbAjax(type)).done(function(resp){
			if(!resp[0]) return false
			lcbBaseFn.lcbRealNameBindCard();
		});
	}
}

/**
 * 生成合同前置校验
 * @param type 0-手机校验接口；1-注册接口；2-登录接口；3-实名认证接口；4-绑卡接口；8-银行卡校验；9-获取验证码接口；10-推标；11-电子签章
 */
function doGenerateContractPre() {
	loading();
	$.when(lcbAjax("8")).done(function(resp){
		if(!resp[0]) return false;
		
		lcbAjax("0").done(function(resp){
			if(!resp[0]){
				return false;
			}
			var lcbStatus = lcbBaseFn.getLcbStatus();
			var lcbAuthStatus = parseInt(lcbStatus.lcbAuthStatus);
			
			//判断有没有注册，如果没有注册，弹出注册登录框
			if(lcbAuthStatus < 20){
				lcbDialogFn.lcbRegisterDialog();
			}else{
				lcbBaseFn.lcbRealNameBindCard();
			}
		});
	});
}