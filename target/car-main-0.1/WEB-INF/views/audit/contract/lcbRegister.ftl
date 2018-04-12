<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
</head>

<body>
	<script type="text/javascript" src="resources/js/audit/contract/contract.js"></script>
	<form method="post" id="lcbRegisterForm">
		<table style="margin-top: 20px;">
			<tr style="height:35px;">
				<td style="width:110px;padding-left:10px;"><label>请输入验证码</label></td>
				<td style="width:110px" >
					<input style="width:105px;" id="lcbVerifyCode" name="lcbVerifyCode"/>
				</td>
				<td style="width:100px;">
					<label style="color:red;cursor:pointer;margin-left:15px;">
						<span onclick="lcbBaseFn.countDown('lcbRegisterForm')">获取验证码</span>
						<span style="font-size:14px;padding-left:20px;"></span>
					</label>
				</td>
			</tr>
			<tr style="height:35px;">
				<td>
					<span style="padding-left:10px;"><input type="checkbox" checked="true" style="width:13px;height:13px;"/></span>
					<span>我已阅读并同意</span>
				</td>
				<td colspan="2"><span style="color:blue;cursor:pointer;margin-left:-5px;" onclick="lcbDialogFn.openRegisterAgreement()">《捞财宝注册协议》</span></td>
			</tr>
		</table>
		<div style='padding-top: 10px; padding-bottom: 10px'>
			<a style='margin-left: 50px' id="ok" class="easyui-linkbutton" iconCls="icon-ok" onclick="lcbBaseFn.lcbOk('1')">确定</a> 
			<a style='margin-left: 50px' class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#lcbRegisterDlg').dialog('close')">返回</a>
		</div>
	</form>
</body>
</html>
