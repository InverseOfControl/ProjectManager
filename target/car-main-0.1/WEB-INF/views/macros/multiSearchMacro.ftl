<#setting url_escaping_charset='UTF-8'>
<#macro htmlMultiSearch>
<span>姓名：</span><input type="text" id="personNameTxt" maxlength="25"  style="width: 90px" />
<span>身份证号：</span><input type="text" id="personIdnumTxt" maxlength="18"  value="${idnum!""}"/>  
<span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:120"/>  
<span>城市：</span><input id="cityComb" class="easyui-combobox"  editable="false" data-options="width:100"/>   
<span>营业网点：</span><input id="salesDeptComb" class="easyui-combobox"  editable="false" data-options="width:150"/>
<span>展期期次：</span>
<select  id="extensionTimeComb"  editable="false" class="easyui-combobox" data-options="width:60">  
 </select>
</#macro>
 