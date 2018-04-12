<#include "../../macros/constant_output_macro.ftl">
<#assign title="用户地区管理"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/userAreaManager/userAreaManagerList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/userAreaManager/chooseUserList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/userAreaManager/userAreaList.js"></script>
	
</head>
<body>
<!--新增地区用户-->
<div id="dlg_add" class="easyui-dialog" style="top:150px;height:250px;width:400px;" closed="true">
	<form id="userAreaForm" method="post" enctype="multipart/form-data">
		<table  style="margin:0 auto;">
		 <hidden  id="userAreaManagerId"/>
			 <tr>
		        <td>
		         <span>用户:</span>
		        </td>
	            <td>
		          <input class="easyui-textbox validateItem" style="width:140px;height:18px;" name="userName" id = "userNameAdd" readOnly="readOnly"></input>
		         <hidden name="userId" id="userId"/>
		         <button  name="chooseUserData">...</button>
		        </td>
		     </tr>
		     <tr>
		       <td>	
	            <span>所属网点:</span>
	           </td>
	            <td>
	             <input name="areaId" type="hidden" id="areaId"/>
	            <input class="easyui-textbox validateItem" style="width:140px;height:18px;" name="fullName"  id ='fullName'readOnly="readOnly"></input>
	            <button name="chooseBranch"">...</button>        	
	           </td>          	          
		     </tr>
		</table>
	</form>	
	<div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="saveUserBaseArea()">提交</a>
            <a class="easyui-linkbutton" onclick="closeUserBaseArea()">关闭</a>
     </div>
</div>
 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>用户名称：</span><input type="text" id="userName" maxlength="30"  style="width: 200px" /> 
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addData();">新增</a> 
	  </div>
</div>
<table id="userAreaMa_result" toolbar="#toolbar"></table>


<!-- 选择用户窗口面板 -->
	<div id="window_chooseUser" class="easyui-window" title="选择用户" style="width:900px;top:160px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>工号：</span><input type="text" id = "userSearchCode" style="width: 190px" />
			<span>姓名：</span><input type="text" id = "userSearchName" style="width: 190px" />
			<a id="searchUser"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" id="clearChooseUser" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>
		</div>
		<div id="chooseUserList"></div>
	</div>
   
   <!-- 选择营业网点窗口面板 -->
	<div id="chooseBaseAreaPanel" class="easyui-window" title="选择营业网点" style="width:1000px;top:60px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>地区<span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="areaCombo">&nbsp;&nbsp;&nbsp;
			<span>城市<span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="cityCombo">&nbsp;&nbsp;&nbsp;
			<span>部门/营业部<span> &nbsp;&nbsp;<input class="easyui-combobox"  style="width:300px;"  id="branchCombo">&nbsp;&nbsp;&nbsp;
			<br/><br/>
			<span>网点名称：</span><input type="text" id="baseAreaName" style="width: 190px" />
			<a id="searchAreaBaseBut"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <a href="javascript:void(0)" id="clear" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a> 
		</div>
		<div style="padding:10px 20px 20px 20px">
			<table id="userAreaGrid"></table>
		</div>
	</div>
    <!-- 选择营业网点窗口面板 -->
</body>