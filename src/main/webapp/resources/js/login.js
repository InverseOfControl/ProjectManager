$(function() {
	if (!placeholderSupport()) { // 判断浏览器是否支持 placeholder
		$('[placeholder]').focus(function() {
			var input = $(this);
			if (input.val() == input.attr('placeholder')) {
				input.val('');
				input.css("color", "#000000");
				input.removeClass('placeholder');
			}
		}).blur(function() {
			var input = $(this);
			if (input.val() == '' || input.val() == input.attr('placeholder')) {
				input.addClass('placeholder');
				input.css("color", "#bbbbbb");
				input.val(input.attr('placeholder'));
			}
		}).blur();
	}
	;
	$('#username').focus();
	$("#username").keypress(function(e) {
		var key = e.which;
		if (key == 13) {
			$('#password').focus();
		}
	});
	$("#password").keydown(function(e) {
		var key = e.which;
		if (key == 13) {
			$('#submitLogin').click();
		}
	});
	$('#submitLogin').click(function() {
		$('#submitLogin').val('登录中...');
		$('#submitLogin').prop('disabled', true);
		$.post('system/login', {
			username : $('#username').val(),
			password : $('#password').val()
		}, function(data) {
			$('#submitLogin').prop('disabled', false);
			$('#submitLogin').val('登    录');
			if (data == '' || data == undefined) {
				$('#beError').show();
				$('#beError').html('出现系统错误，请联系管理员。');
				return;
			}
			if (data.success != 1) {
				$('#beError').show();
				$('#beError').html(data.message);
				return;
			}
			$('#beError').hide();
			gotoPage('index');
		});
	});
});
function placeholderSupport() {
	return 'placeholder' in document.createElement('input');
}
function outOfFrame(){
	if (window.top.location != window.self.location) {  
		window.top.location.reload();
	}  
}