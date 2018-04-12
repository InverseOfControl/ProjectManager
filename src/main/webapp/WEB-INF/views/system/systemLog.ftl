<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/systemLog.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
 	<table id="sysLogPageTb" toolbar="#toolbar"></table>
 	<div id="toolbar" style="height:92px;padding-top:8px;">
	   <div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>用户名：</span><input  id="userComb" class="easyui-combobox"  style="width:120px"/>
        <span>操作模块：</span><input  editable="false"  id="optModule" class="easyui-combobox" style="width:120px"/>        						  
        <span>操作类型：</span><input  editable="false"  id="optType" class="easyui-combobox" style="width:120px"/> 
        <span>操作IP：</span><input   id="ipAddress" class="easyui-validatebox" style="width:120px"/>        						
        <span>开始时间：</span><input  id="startTime" name="startTime"    class="easyui-datetimebox" style="width:120px"/> 
        <span>结束时间：</span><input  id="endTime" name="endTime"    class="easyui-datetimebox" style="width:120px"/> 
        <span>备注：</span><input id="remark" class="easyui-validatebox" style="width: 120px" />   
       </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        
		</div>
	</div>
</body>
</html>