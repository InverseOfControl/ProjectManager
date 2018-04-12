<#include "../../macros/constant_output_macro.ftl">
<#assign title="枚举管理"/>
<@htmlCommonHead/>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/sysEnumerate/sysEnumerateList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>数据类型：</span><input type="text" id="enumType" maxlength="30"  style="width: 150px" /> 
	     <span>数据代码：</span><input type="text" id="enumCode" maxlength="30"  style="width: 150px" /> 
	     <span>数 据 值：</span><input type="text" id="enumValue" maxlength="30"  style="width: 150px" /> 
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertEnumBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增枚举</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增枚举信息面板 -->
		<div id="addEnumPanel" class="easyui-window" title="新增枚举"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addEnumForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span>数据类型：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="数据类型必须填写" style="width:180px;height:18px;" type="text" name="enumType" id = 'enumType'></input>
                     </td>
                    <td>
                    	<span>数据代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="数据代码必须填写" style="width:180px;height:18px;" maxlength="11" type="text" name="enumCode" id = 'enumCode'></input>
                    </td>
                </tr>
                 <tr>
						<td>
							<span>数据值：</span>
							<input class="easyui-validatebox" required="true" missingMessage="属性值必须填写" data-options="editable:false" style="width:180px;" name="enumValue"  id="enumValue"/>
						</td>
						<td>
							<span>版  本：</span>
							<input class="easyui-textbox" data-options="editable:false" style="width:180px;" maxlength="10" name="enumversion"  id="enumversion"/>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAddEnum()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增枚举信息面板 -->
    <!-- 修改枚举信息面板 -->
		<div id="updateEnumPanel" class="easyui-window" title=" 修改枚举信息"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateEnumForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                	<input name = "id" type = "hidden" id = "id" />
                    <td width="500px">
                    	<span>数据类型：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="数据类型必须填写" style="width:180px;height:18px;" type="text" name="enumType" id = 'enumType'></input>
                     </td>
                    <td>
                    	<span>数据代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="数据代码必须填写" style="width:180px;height:18px;" maxlength="11" type="text" name="enumCode" id = 'enumCode'></input>
                    </td>
                </tr>
                 <tr>
						<td>
							<span>数据值：</span>
							<input class="easyui-validatebox" required="true" missingMessage="数据值必须填写" data-options="editable:false" style="width:180px;" name="enumValue"  id="enumValue"/>
						</td>
						<td>
							<span>版  本：</span>
							<input class="easyui-textbox" data-options="editable:false" style="width:180px;" maxlength="10" name="enumversion"  id="enumversion"/>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitUpdateEnum()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改枚举信息面板 -->
	
	<script>
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>