	<#include "../macros/constant_output_macro.ftl">
<#assign title="员工管理"/>
<@htmlCommonHead/>
<script type="text/javascript">var userType='${userType}';</script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/system/sysUserList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/baseAreaList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript">
	var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
	
	<!-- 查询条件 -->
	<div id="toolbar" style="height:90px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>工号：</span><input type="text" id="code" maxlength="20"  style="width: 90px" />&nbsp;&nbsp;&nbsp;&nbsp;
			<span>姓名：</span><input type="text" id="name" maxlength="60"  style="width: 90px" />&nbsp;&nbsp;&nbsp;&nbsp;
			<span>员工类型：</span><input id="userTypecbo" class="easyui-combobox"  editable="false"  data-options="width:120"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<span>所属网点：</span><input type="text" id="fullNameSearch" maxlength="60"  style="width: 350px" />
			<br/><br/>
			<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a id="insertSysUserBut" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增员工</a>

		</div>
	</div>
	

	
	<!--  员工列表数据 -->
	<table id="sysUserGrid" toolbar="#toolbar"></table>
	
	<!-- 新增、修改员工信息面板 -->
	<div id="sysUserPanel" class="easyui-window" title="员工信息维护"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="sysUserForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="10px">
                <tr>
                	<input name="id" type="hidden" id = "id" />
                	<input name="areaId" type="hidden" id="areaId"/>
                 
                    <td width="500px">
                    	<span>工号</span>
                    	<input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="code" id = 'userCode'></input>
                     </td>
                    <td>
                    	<span>姓名：</span>
                    	<input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" id = 'userName'></input>
                    </td>
                </tr>
                
                <tr>
                    <td width="500px">
                    	<span>类型:</span>
                    	<input class="easyui-combobox" data-options="editable:false" style="width:180px;" name="userType"  id="userType"/>
                    </td>
                    <td>	
                    	<span>所属网点</span>
                    	<input class="easyui-textbox validateItem" style="width:140px;height:18px;" name="fullName"  id = 'fullName'readOnly="readOnly"></input>
                    	<button name="chooseBranch">...</button>
                    </td>
                </tr>
                 <tr>
                    <td colspan ="2" align="left">
                   			<ul id="productTree2"></ul>
                   			<ul id="groupTree2"></ul>
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="doModifySysUserInfo()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增、修改员工信息面板 -->
	
	<!-- 重置密码窗口面板 -->
	<div id="changePwdPanel" class="easyui-window" title="重置密码" style="width:400px" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
		<div style="padding:10px 20px 20px 20px">
			<form id="changePwdForm" method="post">
				<input name="userId" type="hidden"/>
				<table cellspacing="10">
					<tr>
						<td>密码:</td>
						<td><input type="password" style="height:18px;" class="easyui-textbox validateItem" name="pwd" /></td>
					</tr>
					<tr>
						<td>确认密码:</td>
						<td><input type="password" style="height:18px;" name="confirmPwd" class="easyui-textbox validateItem"/></td>
					</tr>
				</table>
			</form>
			<br/>
			<div style="text-align:center;padding:5px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doModifyPwd()">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
	        </div>  
		</div>
	</div>
    <!-- 重置密码窗口面板 -->
    
	<!-- 选择营业网点窗口面板 -->
	<div id="chooseBaseAreaPanel" class="easyui-window" title="选择营业网点" style="width:1000px;top:60px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>地区<span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="areaCombo">&nbsp;&nbsp;&nbsp;
			<span>城市<span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="cityCombo">&nbsp;&nbsp;&nbsp;
			<span>部门/营业部<span> &nbsp;&nbsp;<input class="easyui-combobox"  style="width:300px;"  id="branchCombo">&nbsp;&nbsp;&nbsp;
			<br/><br/>
			<span>网点名称：</span><input type="text" id="baseAreaName" style="width: 190px" />
			<a id="searchAreaBaseBut"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <a href="javascript:void(0)" id="clear" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">清空</a> 
		</div>
		<div style="padding:10px 20px 20px 20px">
			<table id="baseAreaGrid" toolbar=""></table>
		</div>
	</div>
    <!-- 选择营业网点窗口面板 -->
</body>
</html>