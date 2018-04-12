<#include "../../macros/constant_output_macro.ftl">
<#assign title="参数管理"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/sysParameter/sysParameterList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>属性代码：</span><input type="text" id="code" maxlength="30"  style="width: 150px" /> 
	     <span>属性名称：</span><input type="text" id="name" maxlength="30"  style="width: 150px" /> 
	     <!--<span>创建日期：</span><input  id="createdTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})""></input>  - 
      			  				<input  id="createdTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></br>
	     <span>修改日期：</span><input  id="modifiedTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>  - 
      			  				<input  id="modifiedTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
      			  				-->
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertSysParametersBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增参数</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增参数信息面板 -->
		<div id="addSysParameterPanel" class="easyui-window" title="新增参数"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addSysParameterForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span>属性代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性代码必须填写" style="width:180px;height:18px;" type="text" name="code" id = "code"></input>
                     </td>
                    <td >
                    	<span>属性名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性名称必须填写" style="width:180px;height:18px;" type="text" name="name" id = "name"></input>
                    </td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>属性值：</span>
							<textarea class="easyui-validatebox" required="true" missingMessage="属性值必须填写" id="parameterValue" name="parameterValue" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
                 <tr>
					<td >
                    	<span>版本：</span>
                    	<input class="easyui-textbox validateItem"  maxlength="10" style="width:180px;height:18px;" type="text" name="spmversion" id = "spmversion"></input>
                     </td>
                    <td >
                    	<span>是否有效：</span>
                    	<select  id="isDisabled"  editable="false" style="width:108px,height:5px" class="easyui-combobox">
			     				<option value="0" >有效</option>
			     				<option value="1" >无效</option>   
			            </select>  
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAddSysParam()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增参数信息面板 -->
    <!-- 修改参数信息面板 -->
		<div id="updateSysParameterPanel" class="easyui-window" title=" 修改参数"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateSysParameterForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                	<input name = "id" type = "hidden" id = "id" />
                    <td width="500px">
                    	<span>属性代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性代码必须填写" style="width:180px;height:18px;" type="text" name="code" id = "code"></input>
                     </td>
                    <td>
                    	<span>属性名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性名称必须填写" style="width:180px;height:18px;" type="text" name="name" id = "name"></input>
                    </td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>属性值：</span>
							<textarea class="easyui-validatebox" required="true" missingMessage="属性值必须填写" id="parameterValue" name="parameterValue" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
                 <tr>
                 	<td >
                    	<span>版本：</span>
                    	<input class="easyui-textbox validateItem" maxlength="10" style="width:180px;height:18px;" type="text" name="spmversion" id = "spmversion"></input>
                     </td>
					<td >
						<span>是否有效：</span>
						<input class="easyui-combobox" id="isDisabled" name="isDisabled" height="5px" data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '有效',
								value: '0'
							},{
								label: '无效',
								value: '1'
							}]" />
					</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitUpdateSysParam()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改参数信息面板 -->
    <!-- 参数详情信息面板 -->
		<div id="detailSysParameterPanel" class="easyui-window" title=" 参数详情"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="detailSysParameterForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<td width="500px">
                    	<span>属性代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性代码必须填写" style="width:180px;height:18px;" readonly="readonly" type="text" name="code" id = "code"></input>
                     </td>
                    <td>
                    	<span>属性名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="属性名称必须填写" style="width:180px;height:18px;" readonly="readonly" type="text" name="name" id = "name"></input>
                    </td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>属 性 值：</span>
							<textarea class="easyui-validatebox" required="true" missingMessage="属性值必须填写" id="parameterValue" name="parameterValue" rows="3" cols="100" readonly="readonly" maxlength="300"></textarea>
						</td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>备  注  ：</span>
							<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300" readonly="readonly"></textarea>
						</td>
                </tr>
                 <tr>
                 	<td >
                    	<span>版  本  ：</span>
                    	<input class="easyui-textbox validateItem" maxlength="10" style="width:180px;height:18px;" readonly="readonly" type="text" name="spmversion" id = "spmversion"></input>
                     </td>
					<td >
						<span>是否有效：</span>
						<input class="easyui-textbox validateItem" id="isDisabled" readonly="readonly" type="text" name="isDisabled"/>
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
    <!--  参数详情信息面板 -->
	
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