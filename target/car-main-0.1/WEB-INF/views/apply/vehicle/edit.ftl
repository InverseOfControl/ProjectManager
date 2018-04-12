<div id="edit" align="center" style="padding: 5px;">
	<form id="editVehicleForm" method="post" action="/vehicle/editVehicle">
	<input type="hidden" name="id" />
		<table class="tableForm">
			<tr>
				<th>品牌</th>
				<td><input name="brand" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写品牌名称'" /></td>
			</tr>
			<tr>
				<th>型号</th>
				<td><input name="model" class="easyui-validatebox" data-options="required:true,missingMessage:'请填写型号名称'" /></td>
			</tr>
		</table>
	</form>
</div>