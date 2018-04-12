<#include "../macros/constant_output_macro.ftl">
<#assign title="权限管理"/>
<@htmlCommonHead/>
	<script type="text/javascript" charset="UTF-8" src="resources/js/system/sysPermissionList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>

	</head>
		<body>
			<!-- 查询条件 -->
			<div id="toolbar" style="height:90px;padding-top:8px;">
				<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px; padding-left:40px">
					<span>权限编号：</span><input type="text" id="code" maxlength="20"  style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;
					<span>权限名称：</span><input type="text" id="name" maxlength="60"  style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;
					<span>上级编号：</span><input type="text" id="parentId" maxlength="60"  style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;
					<br/><br/>
					<a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
					<a id="insertSysPermissionBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增权限</a>
				</div>
			</div>
				<!--权限列表数据 -->
			<table id="sysPermissionGrid" toolbar="#toolbar"></table>
		 <!-- 新增权限面板 -->
		<div id="sysPermissionPanel" class="easyui-window" title="新增权限"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="sysPermissionForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                	<input name = "id" type = "hidden" id = "id" />
                	<input name = "status" type = "hidden" id = "status" value = "0"/>
                	<input name = "type" type = "hidden" id = "type" value = "1"/>
                	<input name = "version" type = "hidden" id = "version" value = "1"/>
                 	<input name = "isDeleted" type = "hidden" id = "version" value = "0"/>
                    <td width="500px">
                    	<span>权限编号：</span>
                    	<input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="code" id = 'name'></input>
                     </td>
                    <td>
                    	<span>权限名称：</span>
                    	<input class="easyui-validatebox" data-options="required:true" style="width:180px;height:18px;" type="text" name="name" id = 'name'></input>
                    </td>
                </tr>
                 <tr>
						<td>
							<span>权限等级：</span>
							<input class="easyui-validatebox" data-options="required:true"   style="width:180px;" name="levels"  id="levels"/>
						</td>
						<td>
							<span>排&nbsp;&nbsp;&nbsp;&nbsp;序：</span>
							<input class="easyui-validatebox" data-options="required:true"  style="width:180px;" name="levelOrder"  id="levelOrder"/>
						</td>
                </tr>
                 <tr>
						<td>
							<span>URL&nbsp;地址：</span>
							<input class="easyui-textbox" data-options="editable:false" style="width:180px;" name="url"  id="url"/>
						</td>
						<td>
							<span>上级编号：</span>
							<input class="easyui-validatebox" data-options="required:true"  style="width:180px;" name="parentId"  id="parentId"/>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="insertSysPermission()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增权限面板 -->
    <!-- 修改权限面板 -->
		<div id="updatePanel" class="easyui-window" title=" 修改权限"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                	<input name = "id" type = "hidden" id = "id" />
                	<input name = "status" type = "hidden" id = "status" value = "0"/>
                	<input name = "type" type = "hidden" id = "type" value = "1"/>
                	<input name = "version" type = "hidden" id = "version" value = "1"/>
                 	<input name = "isDeleted" type = "hidden" id = "version" value = "0"/>
                    <td width="500px">
                    	<span>权限编号：</span>
                    	<input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="code" id = 'name'></input>
                     </td>
                    <td>
                    	<span>权限名称：</span>
                    	<input class="easyui-validatebox" data-options="required:true" style="width:180px;height:18px;" type="text" name="name" id = 'name'></input>
                    </td>
                </tr>
                 <tr>
						<td>
							<span>权限等级：</span>
							<input class="easyui-validatebox" data-options="required:true" style="width:180px;" name="levels"  id="levels"/>
						</td>
						<td>
							<span>排&nbsp;&nbsp;&nbsp;&nbsp;序：</span>
							<input class="easyui-validatebox" data-options="required:true" style="width:180px;" name="levelOrder"  id="levelOrder"/>
						</td>
                </tr>
                 <tr>
						<td>
							<span>URL&nbsp;地址：</span>
							<input class="easyui-textbox" data-options="editable:false" style="width:180px;" name="url"  id="url"/>
						</td>
						<td>
							<span>上级编号：</span>
							<input class="easyui-validatebox" data-options="required:true" style="width:180px;" name="parentId"  id="parentId"/>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="update()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改权限面板 -->
	</body>
</html>