function modifyPwd() {
	 var oldpwd =  $('#modifyPwbForm').find("#original").val();
	 var newpwd =  $('#modifyPwbForm').find("#reset").val();
	 var confirmpwd =  $('#modifyPwbForm').find("#confirm").val();
		
	  if (oldpwd ==''||newpwd=='' ||confirmpwd=='' ||newpwd!= confirmpwd) {
	        return false;
     }
	   $.messager.confirm('确认对话框', '您确认要更改密码吗？', function (r) {
           if (r) {
               doModifyPwd();
           } else {
               return false;
           }
       });
}
function doModifyPwd() {
	$.ajax({
	    type : "POST",
        dataType:"JSON",
		url : 'system/modifyPwd',
		data : $('#modifyPwbForm').serialize(),
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '操作成功！'
				});
			}  
		},
		 error:function(data){   
	 		 $.messager.show({
					title: 'warning',
					msg: data.responseText
				});
		}
	
	
	});
}