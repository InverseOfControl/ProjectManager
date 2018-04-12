<#include "../../macros/constant_output_macro.ftl">
<#assign title="拒绝原因管理"/>
<@htmlCommonHead/>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/refusedReason/refusedReasonList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>拒绝原因：</span><input type="text" id="refusedReason" maxlength="255"  style="width: 250px" /> 
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertReasonBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增银行信息面板 -->
	<div id="addReasonPanel" class="easyui-window" title="新增拒绝原因"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addReasonForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span><font color="red">*</font>拒绝原因：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="拒绝原因必须填写" style="width:280px;height:18px;" type="text" name="reason" id = 'reason'></input>
                     </td>
                    <td>
						<span><font color="red">*</font>父原因：</span>
						<input id="parentId" name = "parentId" class="easyui-combobox" required="true" missingMessage="父原因必须填写" editable="false" data-options="width:220" style="width:108px"/>
					</td>
                </tr>
                <tr>
					<td>
						<span>排序：</span>
						<input class="easyui-validatebox" data-options="editable:false" validType="checkNum" maxlength="5" style="width:180px;" name="levelOrder"  id="levelOrder"/>
					</td>
                    <td>
                    	<span><font color="red">*</font>限制时间（天）：</span>
                    	<input class="easyui-validatebox" required="true" validType="checkNum" missingMessage="限制时间必须填写" style="width:180px;height:18px;" maxlength="5" type="text" name="canRequestDays" id = 'canRequestDays'></input>
                    </td>
                </tr>
                <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAddReason()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
    	</div>
    </div>
    <!-- 新增原因信息面板 -->
    <!-- 修改原因信息面板 -->
		<div id="updateReasonPanel" class="easyui-window" title=" 修改拒绝原因信息"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateReasonForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
                <tr>
                	<input name = "id" type = "hidden" id = "id" />
                	<input name = "version" type = "hidden" id = "version" />
                	<input name = "isDeleted" type = "hidden" id = "isDeleted" />
                    <td width="500px">
                    	<span><font color="red">*</font>拒绝原因：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="拒绝原因必须填写" style="width:180px;height:18px;" type="text" name="reason" id = 'reason'></input>
                     </td>
                    <td>
						<span><font color="red">*</font>父原因：</span>
						<input id="parentId" name = "parentId" class="easyui-combobox" required="true" missingMessage="父原因必须填写" editable="false" data-options="width:220" style="width:108px"/>
					</td>
                </tr>
                <tr>
					<td>
						<span>排序：</span>
						<input class="easyui-validatebox" style="width:180px;" validType="checkNum"  type="text" maxlength="5" name="levelOrder"  id="levelOrder"/>
					</td>
                    <td>
                    	<span><font color="red">*</font>限制时间（天）：</span>
                    	<input name="canRequestDays" id = "canRequestDays" required="true"  validType="checkNum"  missingMessage="限制时间必须填写" maxlength="5" class="easyui-validatebox" style="width:180px;height:18px;" type="text"/>
                    </td>
                </tr>
                <tr>
					<td colspan="2">
						<span>备注：</span>
						<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
					</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitUpdateReason()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改原因信息面板 -->

    <!-- 查询原因详情 -->
		<div id="reasonDetailPanel" class="easyui-window" title=" 修改拒绝原因信息"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="reasonDetailForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
                <tr>
                	<input name = "id" type = "hidden" id = "id" />
                	<input name = "version" type = "hidden" id = "version" />
                	<input name = "isDeleted" type = "hidden" id = "isDeleted" />
                    <td width="500px">
                    	<span><font color="red">*</font>拒绝原因：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="拒绝原因必须填写" style="width:180px;height:18px;" type="text" name="reason" id = 'reason' readonly="readonly"></input>
                     </td>
                    <td>
						<span><font color="red">*</font>父原因：</span>
						<input id="parentId" name = "parentId" class="easyui-combobox" required="true" missingMessage="父原因必须填写" editable="false" data-options="width:220" style="width:108px" readonly="readonly"/>
					</td>
                </tr>
                <tr>
					<td>
						<span>排序：</span>
						<input class="easyui-validatebox" style="width:180px;" type="text" maxlength="20" name="levelOrder"  id="levelOrder" readonly="readonly"/>
					</td>
                    <td>
                    	<span><font color="red">*</font>限制时间（天）：</span>
                    	<input name="canRequestDays" id = "canRequestDays" required="true" missingMessage="限制时间必须填写" class="easyui-validatebox" style="width:180px;height:18px;" type="text" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
					<td colspan="2">
						<span>备注：</span>
						<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300" readonly="readonly"></textarea>
					</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 查看原因详情 -->
	
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