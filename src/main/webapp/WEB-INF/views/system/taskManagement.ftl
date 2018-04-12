<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
	var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/system/taskManagement.js"></script> 
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;margin-top:5px">
			<table>
				<tr>
					<td  width="250px">
						<span>任务名称：</span>
						<input id="taskName" type="text"/>
		  			</td>
		  		</tr>
			</table>
		</div>
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;margin-top:5px">
			<a id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a id="insertTaskManagementBut" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		</div>	
	</div>
	 <table id="list_result" toolbar="#toolbar""></table>
	 <!--任务详情对话框-->	
	<div id="showLoanLedgerDlg" style="top: 100px;height:500px;width:900px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;margin-top:5px">
			<table>
				<tr>
					<td>
						<span>处理结果状态：</span>
		  			</td>
	  			    <td>
                          　　				<select id="state" data-options="required:true,width:180" missingMessage="该输入项为必输项" ></select>
						<div id="sp">
							<div style="color:#99BBE8;background:#fafafa;padding:5px;"></div>
							<input type="checkbox" name="resultState" id="resultState" value="0"><span>全部</span><br/>
							<input type="checkbox" name="resultState" id="resultState" value="1"><span>成功</span><br/>
							<input type="checkbox" name="resultState" id="resultState" value="2"><span>部分成功</span><br/>
							<input type="checkbox" name="resultState" id="resultState" value="3" checked><span>失败</span><br/>
						</div>
                   </td>
		  		</tr>
	  		</table>
  		</div>
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;margin-top:5px">
			<a id="searchSysJobLogBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		</div>	
		<div style="padding:10px 20px 20px 20px">
			<table id="loanLedgerList" toolbar=""></table>
		</div>
	</div>
	
	<!-- 新增、修改定时任务面板 -->
	<div id="taskManagementPanel" class="easyui-window" title="员工信息维护"  align = "center" style="width:800px;top:150px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:700px;">
        <form id="taskManagementForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="40px" align="center">
	            <tr>
	            	<input name="id" type="hidden" id = "id" />
	            	<td align="right">
	                	<span>任务名称 ：</span>
	            	</td>
	            	<td>
	                	<input class="easyui-validatebox" data-options="required:true" style="width:180px;height:18px;" type="text" name="panelTaskName" id = 'panelTaskName'></input>
	            	</td>
	                <td align="right">
	                	<span>类名 ：</span>
	            	</td>
	            	<td>
	                	<input class="easyui-validatebox" data-options="required:true" style="width:180px;height:18px;" type="text" name="className" id = 'className'></input>
	                </td>
	        	</tr>
	            <tr>
	            	<td align="right">
	                	<span>日期  ：</span>
                	</td>
	            	<td>
	                	<input class="easyui-validatebox" data-options="required:true" style="width:180px;height:18px;" type="text" name="executionDate" id = 'executionDate'></input>
	            	</td>
	                <td align="right">
	                	<span>时间：</span>
                	</td>
	            	<td>
	                	<input class="easyui-timespinner" data-options="formatter:formatterdate,min:'00:00:00',showSeconds:true,required:true" missingMessage="该输入项为必输项" style="width:180px" name="executionTime" id = 'executionTime'>
	                </td>
	            </tr>
	            <tr>
	            	<td align="right">
	                	<span>任务详述  ：</span>
                	</td>
	            	<td colspan="3">
	            		<textarea  class="easyui-validatebox textarea" style="margin-left: 0px; margin-right: 0px; height: 58px; width: 500px;" name="taskDetailed" id = 'taskDetailed'></textarea>
	            		<!-- <textarea  class="easyui-validatebox textarea" data-options="required:true" style="margin-left: 0px; margin-right: 0px; height: 58px; width: 500px;" name="taskDetailed" id = 'taskDetailed'></textarea>-->
	            	</td>
	            </tr>
   	 		</table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="doModifyTaskManagement()">确认</a>
            <a class="easyui-linkbutton commonCloseBut">取消</a>
        </div>
        </div>
    </div>
    <!-- 新增、修改定时任务面板 -->
</body>
</html>	