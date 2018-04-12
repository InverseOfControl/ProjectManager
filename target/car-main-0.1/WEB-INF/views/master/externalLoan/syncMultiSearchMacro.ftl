<#setting url_escaping_charset='UTF-8'>
<span>债权编号：</span><input type="text" id="contractNo" maxlength="30" />  
<span>生成时间：</span>
<input id="buildDateStart" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"  style="width:140px"></input>
<span>-</span>
</span>
<input id="buildDateEnd" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"  style="width:140px"></input>
<span>请求时间：</span>
<input id="sendDateStart" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"  style="width:140px"></input>
<span>-</span>
<input id="sendDateEnd" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"  style="width:140px"></input>
<span>同步状态：</span>
<select  id="status"  editable="false" class="easyui-combobox" data-options="width:120">  
		<!--option value="60">所有</option-->
		<option value="">所有</option>
		<option value="0">未知</option>
        <option value="1">待同步</option>	 
        <option value="2">同步中</option>	
        <option value="3">同步成功</option>	
        <option value="4">拒绝同步</option>		
</select>