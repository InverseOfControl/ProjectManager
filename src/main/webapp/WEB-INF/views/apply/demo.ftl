<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
</head>
<body>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
      	<span>借款状态：</span><input id="loanStatusComb" class="easyui-combobox" editable="false" data-options="width:100" />
      	<span>手机号：</span><input type="text" id="personMobilePhoneTxt" maxlength="25"  style="width: 90px" />  
        <span>车牌号：</span><input type="text" id="plateNumberTxt" maxlength="25"  style="width: 90px" />    
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <a href="javascript:void(0)" id="ceshiBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">测试</a>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#ceshiBt").on("click",function(){
				$.ajax({
					type: "POST",
			        url: "demo/execute",
			        dataType: "json",
			        success: function(data){
			        	console.log(data);
			        }
				})
			})
		})
	</script>
</body>
</html>