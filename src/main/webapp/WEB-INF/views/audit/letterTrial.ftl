<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/lettertrial.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body width="100%">
	<!--查询条件-->
	<div id="toolbar" style="height:90px;padding-top:8px;">
		<table style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;width:100%"	border="0px">
			<tr width="100%">
				<td width="20%">借款人姓名：<input id="personNameTxt" /></td>
				<td width="20%">审核员：<input id="assessorNameComb"  class="easyui-combobox"  style="width:150px;"/></td>
				<td width="20%">
					分派状态：
					<select class="easyui-combobox" data-options="editable:false" style="width:180px;" id="loanStatus" style="width:150px;">
								<option value="0">全部</option>
								<option value="23">初审待分配</option>
								<option value="25">初审中</option>
								<option value="45">门店重提</option>
								
					</select>
			</td>
				<td  width="40%">
					申请日期 ：     	
					<input type="text" id="requestStartDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:140px"/>
	  					 &nbsp;—&nbsp;
	  				<input type="text" id="requestEndDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:140px"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" width="100%">
					<a class="easyui-linkbutton" iconCls="icon-" onclick="searchBg()">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" iconCls="icon-" onclick="openWindow()">分派</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>		
	</div>
	<!--查询条件-->
	<!--审核员工作量-->
	<div class="easyui-panel" style="padding:5px;margin-top:10px;">
		<a class="easyui-linkbutton" onclick=" openDiv()" type="button">点击查看审核员当天的工作量</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" onclick="javascript:$('#p').panel('collapse',true)">收起</a>
		<div id="p" align="center" class="easyui-panel" title="" style="margin-top:10px;" data-options="iconCls:'icon-save',collapsible:false,minimizable:true,maximizable:true,closable:true">
		 	<table style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;width:50%">
		 		<tr align="center">
		 			<td>初审员当天工作量&nbsp;&nbsp;&nbsp;&nbsp;已处理/未处理</td>
		 		</tr>
		 		<tr align="center">
		 			<td id="td1"></td>
		 		</tr>
		 	</table>
		</div>
		
	</div>
	<!--审核员工作量-->
	<!--借款列表-->
		<table id="bg" ></table>
	<!--分派彈窗-->
	<div id="fenpai" class="easyui-window" title="信审分单" align="center" style="width:400px;height:400px;margin-top:30px;">
			<div style="width:400px;height:70%;margin-top:30px;">
				分派给：<input id="assessorNameComb2"  class="easyui-combobox"/>
			</div>
			<div>
				<a class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fenpai').window('close');">取消</a>
			</div>
	</div>
	
	<!--分派彈窗-->
</body>
</html>