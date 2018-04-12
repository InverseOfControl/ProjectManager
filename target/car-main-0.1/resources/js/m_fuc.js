jQuery.mFuc = {
	backpic : function(img, wd, hg) {
		if (wd && hg) {
			return '<img src="css/m_images/' + img + '" width="' + wd + '" height="' + hg + '" />';
		} else {
			return '<img src="css/m_images/' + img + '" />';
		}
		;
	},
	backa : function(url, tgt, val) {
		return '<a href="' + url + '" class="text" target="' + tgt + '">' + val + '</a>';
	},
	winW : function() {
		return window.screen.width;
	},
	winH : function() {
		return window.screen.height;
	},
	dateS : function(val) {
		if (val == undefined || val == '') {
			return '';
		}
		var date = new Date(val);
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
		var mints = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
		var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
		return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + mints + ":" + sec;
	},
	dateM : function(val) {
		var _dateM = $.mFuc.dateS(val);
		return _dateM.substring(0, _dateM.lastIndexOf(":"));
	},
	dateD : function(val) {
		var _dateD = $.mFuc.dateS(val);
		return _dateD.substring(0, _dateD.indexOf(" "));
	},
	newW : function(url, val) {
		return '<a href="#" class="text" onclick="window.open(\'' + url + '\',\'\',\'scrollbars=yes,width=800,height=600,left=' + ($.mFuc.winW() - 800) / 2 + ',top=' + ($.mFuc.winH() - 700) / 2 + '\')">' + val + '</a>';
	},
	backUrl : function(url) {
		window.location.href = url;
	},
	/**
	 * 表单校验 fo 获取表单 param1 为true时，绑定焦点进入离开，可为空。 param2 为true时，绑定提交按钮，可为空。 submit
	 * 获取按钮，绑定焦点及提交按钮，可为空。
	 */
	mValidate : function(fo, param1, param2, submit) {
		// 获取name
		var name = {
			loginname : "loginname",
			realname : "realname",
			chinese : "chinese",
			letter : "letter",
			number4 : "number4",
			number9 : "number9",
			idcard : "idcard",
			mobile : "mobile",
			rar : "rar",
			qq : "qq",
			picture : "picture",
			zipcode : "zipcode",
			money : "money",
			email : "email",
			education : "education",
			password : "password",
			rpassword : "rpassword",
			chars20 : "chars20",
			chars50 : "chars50",
			chars100 : "chars100",
			chars300 : "chars300",
			chars1000 : "chars1000",
			phone1 : "phone1",
			phone2 : "phone2",
			url : "url"
		};
		// 提示语句
		var message = {
			loginname : {
				a : "4-20个字符，不可使用中文(必填)", // required
				b : "4-20个字符，不可使用中文", // no required
				c : "不能使用中文", // error 1
				d : "字符数控制在4-20" // error 2
			},
			realname : {
				a : "必填项,不能为空",
				b : "",
				c : "不能使用非中文",
				d : "字数控制在1-10"
			},
			chinese : "必填项",
			letter : "必填项",
			number : {
				a : "必填项,不能为空",
				b : "",
				c : "数字输入有误",
				d1 : "不能超过4位数",
				d2 : "不能超过9位数"
			},
			idcard : {
				a : "必填项,不能为空",
				b : "",
				c : "身份证号码输入有误"
			},
			mobile : {
				a : "必填项,不能为空",
				b : "",
				c : "手机号码输入有误"
			},
			rar : "必填项",
			qq : {
				a : "必填项,不能为空",
				b : "",
				c : "QQ号码输入有误"
			},
			picture : "必填项",
			zipcode : {
				a : "必填项,不能为空",
				b : "",
				c : "邮编输入有误"
			},
			money : {
				a : "请不要输入逗号和空格,正确的格式为123456.78(必填)",
				b : "请不要输入逗号和空格,正确的格式为123456.78",
				c : "金额输入有误",
				d : "金额不能超过20个位",
				e : "金额最多保留小数点后两位"
			},
			email : {
				a : "必填项,不能为空",
				b : "",
				c : "邮箱输入有误"
			},
			education : {
				a : "必填项,不能为空",
				b : "",
				c : "验证码输入有误"
			},
			password : {
				a : "6-16个字符(必填)",
				b : "6-16个字符",
				c : "请输入6-16个字符"
			},
			rpassword : {
				a : "必填项,不能为空",
				b : "",
				c : "两次输入密码不一致"
			},
			chars : {
				a : "必填项,不能为空",
				b : "",
				c20 : "不能超过20个字符",
				c50 : "不能超过50个字符",
				c100 : "不能超过100个字符",
				c300 : "不能超过150个汉字",
				c1000 : "不能超过500个汉字"
			},
			phone : {
				a1 : "区号不能为空",
				a2 : "电话号码不能为空",
				b1 : "",
				b2 : "",
				c : "区号输入有误",
				d : "电话号码输入有误"
			},
			url : {
				a : "必填项,不能为空",
				b : "",
				c : "网址输入有误",
				d : "网址不超过80个字符"
			}
		};
		// 验证规则
		var rules = {
			required : function(val) {
				return $.trim(val) != "";
			},
			loginname : {
				c : function(val) {
					return /^[^\u4E00-\u9FA5]*$/.test($.trim(val));
				},
				d : function(val) {
					return /^[^\u4E00-\u9FA5]{4,20}$/.test($.trim(val));
				}
			},
			realname : {
				c : function(val) {
					return /^[\u4E00-\u9FA5•·]*$/.test($.trim(val));
				},
				d : function(val) {
					return /^[\u4E00-\u9FA5•·]{2,10}$/.test($.trim(val));
				}
			},
			chinese : "必填项",
			letter : "必填项",
			number : {
				c : function(val) {
					return /^[1-9]\d*$/.test($.trim(val));
				},
				d1 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 4;
				},
				d2 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 9;
				}
			},
			rar : "必填项",
			qq : {
				c : function(val) {
					return /^[1-9]\d{4,10}$/.test($.trim(val));
				}
			},
			idcard : {
				c : function(val) {
					return /^(\d{15}|(\d{17}(\d|x|X)))$/.test($.trim(val));
				}
			},
			mobile : {
				c : function(val) {
					return /^0?(13|15|18)[0-9]{9}$/.test($.trim(val));
				}
			},
			picture : "必填项",
			zipcode : {
				c : function(val) {
					return /^\d{6}$/.test($.trim(val));
				}
			},
			money : {
				c : function(val) {
					return /^([1-9]\d*)(\.\d{1,})?$/.test($.trim(val));
				},
				d : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 20;
				},
				e : function(val) {
					return /^([1-9]\d*)(\.\d{1,2})?$/.test($.trim(val));
				}
			},
			email : {
				c : function(val) {
					return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test($.trim(val));
				}
			},
			education : {
				c : function(val) {
					return /^\d{12}$/.test($.trim(val));
				}
			},
			password : {
				c : function(val) {
					return /^[A-Za-z0-9-_\~\!\@\#\$\%\^\&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]{6,16}$/.test(val);
				}
			},
			rpassword : {
				c : function(val1, val2) {
					return val1 == val2;
				}
			},
			chars : {
				c20 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 20;
				},
				c50 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 50;
				},
				c100 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 100;
				},
				c300 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 300;
				},
				c1000 : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 1000;
				}
			},
			phone : {
				c : function(val) {
					return /^0\d{2,3}$/.test($.trim(val));
				},
				d : function(val) {
					return /^\d{7,8}$/.test($.trim(val));
				}
			},
			url : {
				c : function(val) {
					return /^(http[s]?:\/\/)?([\w-]+\.)+[\w-]+([\w-\.\/\?\%\&\#\=]*)?$/.test($.trim(val));
				},
				d : function(val) {
					return val.replace(/[^\x00-\xff]/g, "xx").length <= 80;
				}
			}
		};
		// 显示
		var showTip = function(obj, msg) {
			var tipBox = $('<div class="mvalidatetip"><span class="mvalidatetip-c">' + msg + '</span><span class="mvalidatetip-p"></span></div>');
			var body = obj.parents("body");
			var position = function() {
				tipBox.css({
					"left" : obj.offset().left + obj.outerWidth(),
					"top" : obj.offset().top
				});
				setTimeout(position, 200);
			};
			if (msg !== "") {
				tipBox.appendTo(body);
				position();
			}
			;
		};
		// 焦点进入离开方法
		var fobl = function(name, msg0_re, msg0_no, msg1, rules1, msg2, rules2, msg3, rules3) {
			$("." + name, fo).bind("focus blur mouseenter mouseleave", function(event) {
				var obj = $(this);
				var val = obj.val();
				var val_psd = $(".password", fo).val();
				var ru0 = rules.required(val);
				var ru1, ru2, ru3;
				if (rules1) {
					if (obj.hasClass("rpassword")) {
						ru1 = rules1(val, val_psd);
					} else {
						ru1 = rules1(val);
					}
					;
				}
				;
				if (rules2) {
					ru2 = rules2(val);
				}
				;
				if (rules3) {
					ru3 = rules3(val);
				}
				;
				$(".mvalidatetip", fo.parents("body")).remove();
				if (!ru0 && obj.hasClass("required") && (event.type == "blur" || event.type == "mouseleave")) {
					obj.addClass("mvalidatewarn");
				} else if (msg1 && ru0 && ru1 != undefined && !ru1) {
					if (event.type == "blur" || event.type == "mouseleave") {
						obj.addClass("mvalidatewarn");
					} else {
						showTip(obj, msg1);
					}
				} else if (msg2 && ru0 && ru2 != undefined && !ru2) {
					if (event.type == "blur" || event.type == "mouseleave") {
						obj.addClass("mvalidatewarn");
					} else {
						showTip(obj, msg2);
					}
				} else if (msg3 && ru0 && ru3 != undefined && !ru3) {
					if (event.type == "blur" || event.type == "mouseleave") {
						obj.addClass("mvalidatewarn");
					} else {
						showTip(obj, msg3);
					}
				} else {
					if (event.type == "blur" || event.type == "mouseleave") {
						obj.removeClass("mvalidatewarn");
					} else {
						if (msg0_re && obj.hasClass("required")) {
							showTip(obj, msg0_re);
						} else if (msg0_no) {
							showTip(obj, msg0_no);
						}
						;
					}
					;
				}
				;
			});
		};
		// 表单提交按钮临时变量
		var flag;
		// 表单提交方法
		var sumt = function(name, rules1, rules2, rules3) {
			$("." + name, fo).each(function() {
				var obj = $(this);
				var val = obj.val();
				var val_psd = $(".password", fo).val();
				var ru0 = rules.required(val);
				var ru1, ru2, ru3;
				if (rules1) {
					if (obj.hasClass("rpassword")) {
						ru1 = rules1(val, val_psd);
					} else {
						ru1 = rules1(val);
					}
					;
				}
				;
				if (rules2) {
					ru2 = rules2(val);
				}
				;
				if (rules3) {
					ru3 = rules3(val);
				}
				;
				if (!ru0 && obj.hasClass("required")) {
					obj.addClass("mvalidatewarn");
					flag = false;
				} else if (ru0 && ru1 != undefined && !ru1) {
					obj.addClass("mvalidatewarn");
					flag = false;
				} else if (ru0 && ru2 != undefined && !ru2) {
					obj.addClass("mvalidatewarn");
					flag = false;
				} else if (ru0 && ru3 != undefined && !ru3) {
					obj.addClass("mvalidatewarn");
					flag = false;
				}
				;
			});
		};
		// 聚合所有验证
		var foblall = function() {
			fobl(name.loginname, message.loginname.a, message.loginname.b, message.loginname.c, rules.loginname.c, message.loginname.d, rules.loginname.d);
			fobl(name.email, message.email.a, message.email.b, message.email.c, rules.email.c);
			fobl(name.password, message.password.a, message.password.b, message.password.c, rules.password.c);
			fobl(name.rpassword, message.rpassword.a, message.rpassword.b, message.rpassword.c, rules.rpassword.c);
			fobl(name.realname, message.realname.a, message.realname.b, message.realname.c, rules.realname.c, message.realname.d, rules.realname.d);
			fobl(name.mobile, message.mobile.a, message.mobile.b, message.mobile.c, rules.mobile.c);
			fobl(name.idcard, message.idcard.a, message.idcard.b, message.idcard.c, rules.idcard.c);
			fobl(name.chars20, message.chars.a, message.chars.b, message.chars.c20, rules.chars.c20);
			fobl(name.chars50, message.chars.a, message.chars.b, message.chars.c50, rules.chars.c50);
			fobl(name.chars100, message.chars.a, message.chars.b, message.chars.c100, rules.chars.c100);
			fobl(name.chars300, message.chars.a, message.chars.b, message.chars.c300, rules.chars.c300);
			fobl(name.chars1000, message.chars.a, message.chars.b, message.chars.c1000, rules.chars.c1000);
			fobl(name.money, message.money.a, message.money.b, message.money.c, rules.money.c, message.money.d, rules.money.d, message.money.e, rules.money.e);
			fobl(name.education, message.education.a, message.education.b, message.education.c, rules.education.c);
			fobl(name.zipcode, message.zipcode.a, message.zipcode.b, message.zipcode.c, rules.zipcode.c);
			fobl(name.number4, message.number.a, message.number.b, message.number.c, rules.number.c, message.number.d1, rules.number.d1);
			fobl(name.number9, message.number.a, message.number.b, message.number.c, rules.number.c, message.number.d2, rules.number.d2);
			fobl(name.phone1, message.phone.a1, message.phone.b1, message.phone.c, rules.phone.c);
			fobl(name.phone2, message.phone.a2, message.phone.b2, message.phone.d, rules.phone.d);
			fobl(name.qq, message.qq.a, message.qq.b, message.qq.c, rules.qq.c);
			fobl(name.url, message.url.a, message.url.b, message.url.c, rules.url.c, message.url.d, rules.url.d);
		};
		var sumtall = function() {
			flag = true;
			sumt(name.loginname, rules.loginname.c, rules.loginname.d);
			sumt(name.email, rules.email.c);
			sumt(name.password, rules.password.c);
			sumt(name.rpassword, rules.rpassword.c);
			sumt(name.realname, rules.realname.c, rules.realname.d);
			sumt(name.mobile, rules.mobile.c);
			sumt(name.idcard, rules.idcard.c);
			sumt(name.chars20, rules.chars.c20);
			sumt(name.chars50, rules.chars.c50);
			sumt(name.chars100, rules.chars.c100);
			sumt(name.chars300, rules.chars.c300);
			sumt(name.chars1000, rules.chars.c1000);
			sumt(name.money, rules.money.c, rules.money.d, rules.money.e);
			sumt(name.education, rules.education.c);
			sumt(name.zipcode, rules.zipcode.c);
			sumt(name.number4, rules.number.c, rules.number.d1);
			sumt(name.number9, rules.number.c, rules.number.d2);
			sumt(name.phone1, rules.phone.c);
			sumt(name.phone2, rules.phone.d);
			sumt(name.qq, rules.qq.c);
			sumt(name.url, rules.url.c, rules.url.d);
			$(".mvalidatewarn", fo).eq(0).focus();
			return flag;
		};
		// 执行;
		if (param1) { // 焦点绑定
			foblall();
		} else if (param2) { // 提交绑定并反会值
			sumtall();
			return flag;
		} else { // 焦点绑定,提交绑定并反会值
			foblall();
			if (submit) {
				submit.bind("click", sumtall);
			} else {
				$(".validateSubmit", fo).bind("click", sumtall);
			}
			;
		}
		;
	}
};