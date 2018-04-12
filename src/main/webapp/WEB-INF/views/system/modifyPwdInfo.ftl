<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/system.js"></script>
<style>
		body{background:#f5fdfe!important;background:rgba(228,237,254,0.3)}
		#modifyPwbForm{margin-top:50px}
		table tr th,table tr td{padding:10px;}
		#modifyPwbForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:40px;
            line-height:18px;
            padding:5px;
            padding-right:35px;
        }
        #modifyPwbForm table tr:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            text-indent:1em;
        }
        #modifyPwbForm table tr:nth-child(even){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            text-indent:1em;
        }
		input{
			height:27px;
		}
</style>
</head>
<body>
<div id="add" align="center" style="padding: 5px;">
	<form id="modifyPwbForm" method="post">
		<div>
			<table class="tableForm">
				<tr>
					<th>原密码</th>
					<td><input id="original" name="original" class="easyui-validatebox"  type="password"  data-options="required:true,missingMessage:'请填写原密码'" /></td>	、
				</tr>
				<tr>
					<th>新密码</th>
					<td> <input id="reset" name="reset" class="easyui-validatebox"  type="password"  data-options="required:true,missingMessage:'请填写新密码'" /></td>		
				</tr>
				<tr>			
					<th>确认密码</th>
					<td><input  id="confirm" name="confirm" class="easyui-validatebox"  type="password"  data-options="required:true,missingMessage:'请填写确认密码'" /></td>	
				</tr>
				
				<tr >
					<td colspan="2" align="left" style="padding-left:60px;border:none" >
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="modifyPwd();">确定</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
	    </div>
	</form>
</div>
<script>
           $(function(){
                $('table tr').css("background","#f1f5f9");
                $('body').css("background","rgba(228,237,254,0.3)");
              
            })
</script> 
</body>
</html>