<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
</head>

<body>
	<form method="post" id="lcbLoginForm">
		<table style="margin-top: 20px;">
			<tr style="height:35px;">
				<td style="width:110px;padding-left:10px;"><label>请输入验证码</label></td>
				<td style="width:110px" >
					<input style="width:105px;" id="lcbVerifyCode" name="lcbVerifyCode"/>
				</td>
				<td style="width:100px;">
					<label style="color:red;cursor:pointer;margin-left:15px;">
						<span onclick="lcbBaseFn.countDown('lcbLoginForm')">获取验证码</span>
						<span style="font-size:14px;padding-left:20px;"></span>
					</label>
				</td>
			</tr>
		</table>
		<div style='padding-top: 10px; padding-bottom: 10px'>
			<a style='margin-left: 125px' class="easyui-linkbutton" iconCls="icon-ok" onclick="lcbBaseFn.lcbOk('2')">确定</a> 
		</div>
	</form>
</body>
</html>
