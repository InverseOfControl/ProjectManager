<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/sms/smsConfigration.js"></script>
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
	<table id="smsConfigration" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>城市：</span><input id="cityId"  class="easyui-combobox"  editable="false" data-options="valueField:'cityId',textField:'cityName',editable:false,
                    	url:'after/smsConfiguration/getCityList'"/>
			&nbsp;&nbsp;&nbsp;<span>电话号码：</span><input  id="servicePhone"  maxlength="25"  style="width: 150px" class="easyui-validatebox"/>  
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" onclick='searchCityPhone()' id="searchBtn">查询</a>
	        <a class="easyui-linkbutton" onclick='tanCityPhone()' id="plUpdCityPhoneBtn">(单条or批量)修改</a>
		</div>
		<!--电话配置弹出层-->
		<div id="editForm" class="easyui-window" title="电话配置"
		data-options="modal:true,closed:true"
		style="width: 400px; height: 200px; padding: 12px;">
			<table cellpadding="3">
				<tr>
					<td width="15%">电话号码:</td>				
					<td width="55%"><input id="servicePhone"
						name="servicePhone"  class='easyui-textbox' value="" required="true"  type="text"/></td>
				</tr>
			</table>
			<div style="margin-top: 5px; text-align: center;">
					 <a id="btnEp"
					class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"
					onclick="plUpdCityPhone()">确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)" onclick="closeForm()">取消</a>	
					<input type="hidden" id="cityIds"/>		
			</div>
	</div>
</body>
</html>