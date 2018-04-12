<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/bankAccountAuth.js"></script>
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
<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#ff table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            width:110px
        }
        #ff table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:left;
            text-indent:1em;
             width:120px
        }
        #ff table tr td:nth-child(even){
            background: #FFFFFF;
        }
</style>
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
	<table id="bankAccountAuthPageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>姓名：</span><input id="personName" type = "text" maxlength="25"  style="width: 90px" />
			<span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" />&nbsp;&nbsp;
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" id="searchBtn" data-options="iconCls:'icon-search'">查询</a>
		</div>
	</div>
	
</body>
</html>