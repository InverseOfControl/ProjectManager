<#include "../../macros/constant_output_macro.ftl">
<#assign title="银行管理"/>
<@htmlCommonHead/>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/bank/bankList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>银行名称：</span><input type="text" id="bankName" maxlength="30"  style="width: 150px" /> 
	     <span>银行代码：</span><input type="text" id="bankCode" maxlength="30"  style="width: 150px" /> 
	     <span>TPP类型：</span>
	     <select  id="tppType"  editable="false" style="width:108px" class="easyui-combobox">
        			<option value="">全部</option>
     				<option value="10" >通联划扣</option>
     				<option value="20" >富有划扣</option>   
     				<option value="30" >银联划扣</option>   
          </select>  
	     <span>银行类型：</span>
	     <select  id="bankType"  editable="false" style="width:108px" class="easyui-combobox">
        			<option value="">全部</option>
     				<option value="10" >国内银行</option>
     				<option value="1" >国外银行</option>   
          </select>  
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertBankBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增银行</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增银行信息面板 -->
		<div id="addBankPanel" class="easyui-window" title="新增银行"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addBankForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span>银行名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="银行名称必须填写" style="width:180px;height:18px;" type="text" name="bankName" id = 'bankName'></input>
                     </td>
                    <td>
                    	<span>银行代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="银行代码必须填写" style="width:180px;height:18px;" maxlength="20" type="text" name="bankCode" id = 'bankCode'></input>
                    </td>
                </tr>
                 <tr>
						<td>
							<span>TPP银行代码：</span>
							<input class="easyui-validatebox" required="true" missingMessage="TPP银行代码必须填写" data-options="editable:false"  maxlength="20" style="width:180px;" name="tppBankCode"  id="tppBankCode"/>
						</td>
						<td>
							<span>TPP类型：</span>
							<select  id="tppType"  editable="false" style="width:108px" class="easyui-combobox">
				     				<option value="10" >通联划扣</option>
				     				<option value="20" >富有划扣</option>   
				     				<option value="30" >银联划扣</option>  
				            </select>
						</td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>银行类型：</span>
							<select  id="bankType"  editable="false" style="width:108px" class="easyui-combobox">
				     				<option value="10" >国内银行</option>
				     				<option value="1" >国外银行</option>   
				            </select>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAddBank()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增银行信息面板 -->
    <!-- 修改银行信息面板 -->
		<div id="updateBankPanel" class="easyui-window" title=" 修改银行信息"  align = "center" style="width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateBankForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                	<input name = "id" type = "hidden" id = "id" />
                    <td >
                    	<span>银行名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="银行名称必须填写"  type="text" name="bankName" id = 'bankName'/>
                     </td>
                    <td>
                    	<span>银行代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="银行代码必须填写" svalidtype="integerCheck" maxlength="20" tyle="width:180px;height:18px;" type="text" name="bankCode" id = 'bankCode'/>
                    </td>
                </tr>
                 <tr>
					<td>
						<span>TPP银行代码：</span>
						<input class="easyui-validatebox" required="true" missingMessage="TPP银行代码必须填写" data-options="editable:false" maxlength="20" style="width:180px;" name="tppBankCode"  id="tppBankCode"/>
					</td>
					<td>
						<span>TPP类型：</span>
				     	<input class="easyui-combobox" id="tppType" name="tppType" data-options="
								valueField: 'value',
								textField: 'label',
								data: [{
									label: '通联划扣',
									value: '10'
								},{
									label: '富有划扣',
									value: '20'
								},{
									label: '银联划扣',
									value: '30'
								}
								]" />
					</td>
                </tr>
                 <tr>
					<td colspan="2">
						<span>银行类型：</span>
						<input class="easyui-combobox" id="bankType" name="bankType" data-options="
							valueField: 'value',
							textField: 'label',
							data: [{
								label: '国内银行',
								value: '0'
							},{
								label: '国外银行',
								value: '1'
							}]" />
					</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitUpdateBank()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改银行信息面板 -->
	
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