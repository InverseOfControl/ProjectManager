<!-- 通过type和code找到枚举值 -->
<#macro enumValue type code>
	<#t>${stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumValue('${type}', ${code})")}<#t>
</#macro>

<!-- 通过type找到枚举下拉框 -->
<#macro enumList type>
	<#local list = stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumListByType('"+type+"')") />
	<option value=''>请选择</option>
	<#list list as item>
		<option value='${item.enumCode}'>${item.enumValue}</option>
	</#list>
</#macro>

<#macro enumListAll type>
	<#local list = stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumListByType('"+type+"')") />
	<#list list as item>
		<option value='${item.enumCode}'>${item.enumValue}</option>
	</#list>
</#macro>

<!-- 通过type找到枚举多选框 -->
<#macro enumCheckBoxList type fieldName value="">
	<#local list = stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumListByType('"+type+"')") />
	<#local valueList = value?replace(" ","")?split(",") />
	<#list list as item>
		<input type="checkbox" name="${fieldName}" value="${item.enumCode}" <#if valueList?seq_contains("${item.enumCode}")>checked</#if>>${item.enumValue}
	</#list>
</#macro>

<#macro enumListByIncludeCode type includeCode>
	<#local list = stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumListByIncludeCode('"+type+"','"+includeCode+"')") />
	<option value=''>请选择</option>
	<#list list as item>
		<option value='${item.enumCode}'>${item.enumValue}</option>
	</#list>
</#macro>

<#macro enumListByExclusionCode type exclusionCode>
	<#local list = stack.findValue("@com.ezendai.credit2.master.enumerate.EnumUtil@findEnumListByExclusionCode('"+type+"','"+exclusionCode+"')") />
	<option value=''>请选择</option>
	<#list list as item>
		<option value='${item.enumCode}'>${item.enumValue}</option>
	</#list>
</#macro>