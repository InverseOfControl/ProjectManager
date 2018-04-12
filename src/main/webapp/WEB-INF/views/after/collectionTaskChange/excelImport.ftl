<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<div closed="true" data-options="resizable:true">
 
		<form id="excelImp" method="post" enctype="multipart/form-data" 
			action="${WebConstants.webUrl}${WebConstants.contextPath}/TaskAllocationRule/Main/ExcelImport">

			<table >
				<tr>
					<th   style="font-size: 12px"><label>导入文件:</label></th>
					<td> 
						<input id="filename" type="file" name="filename"    />
						 
					</td>
				
				</tr>
			</table>
		</form>
	 
</div>



 
   			 
      	
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
		 
            });
         
</script>		
 