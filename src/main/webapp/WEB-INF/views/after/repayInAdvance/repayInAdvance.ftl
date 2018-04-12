<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/repayInAdvance/repayInAdvance.js"></script>
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
	<table id="repayInAdvancePageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>姓名：</span><input id="personName" class="easyui-validatebox"  maxlength="25"  style="width: 90px" />
			<span>身份证号：</span><input class="easyui-validatebox"  id="personIdnum" maxlength="18" />
			<span>手机号码：</span><input class="easyui-validatebox" id="mobilePhone" maxlength="11" />
			<span>客户经理：</span><input id="crmList" class="easyui-combobox" editable="false"  style="width:100" />
			<span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:120"/>  
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" id="searchBtn">查询</a>
		</div>
	</div>
</body>
</html>