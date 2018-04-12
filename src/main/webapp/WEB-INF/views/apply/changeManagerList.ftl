<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/changeManager.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>	
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
	<table id="manageServicePageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>姓名：</span><input id="personName" type = "text" maxlength="25"  style="width: 90px" />
			<span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" />
			<span>借款子状态：</span>
			<select  id="productSubType"  editable="false" class="easyui-combobox">  
				<option value="0">全部</option>
				<option value="1">移交类</option>
				<option value="2">流通类</option>
			</select>
			<span>管理客服：</span><input id="managerList" class="easyui-combobox" editable="false" data-options="width:100" />
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" id="changeBtn" >变更</a> 
	        <a class="easyui-linkbutton" id="searchBtn">确定</a>
		</div>
	</div>
	<!-- 变更弹出窗 -->
	<div id="changeDLG" class="easyui-dialog" style="top: 150px;height:250px;width: 300px;" modal="true" closed="true">
		<div style="text-align:center;margin-top:40px">
			<span>变更管理客服为&nbsp;</span><input id="changeManagerList" class="easyui-combobox" editable="false" data-options="width:100" />
		</div>
		<div style="margin-top:80px">
			<a   style="margin-left:50px"  class="easyui-linkbutton" iconCls="icon-ok" id="change" plain="true" onclick="submitChange()">确认</a>
			<a   style="margin-left:30px"  class="easyui-linkbutton" iconCls="icon-ok" id="cancel" plain="true" onclick="cancelChange()" >取消</a>
		</div>
	</div>
</body>
</html>