	 
//弹出生成合同窗口
function changeToFirstDlg() {
	
    $('#changeToFirstDlg').dialog({
        title: '变更初审员',
        width: 500,
        height: 120,
        closed: false,
        cache: false,
        href: url,
        modal: true
    });   
    $('#changeToFirstDlg').show();
    
    $('#changeToFirstDlg #firstTrialId3').combobox({
 		 url:'letterTrial/getFirstTrials3',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){
//		   var data = $(this).combobox('getData');
			  $.ajax({
				   url : 'apply/getLoanOne',
				   data :{
					   id:$('#loanIdChange').val()
				   },
				   type:"POST",
				   success : function(result){
					   $('#changeToFirstDlg #firstTrialId3').combobox('select', result.firstTrialId);
				   }
			  });
		      
		}
    });  
}


function auditSubmitBt(){
	$.messager.confirm('确认','初审变更确认', function(r) {
		if(r) {
			  $.ajax({
		        type: 'POST',
		        url: 'finalApprove/finalApproveData/changeToFirst',
		        dataType: "json",
				data : $('#changeToFirstForm').serialize(),
				async : false,
		        success:function(data){
		        	if (data.success) {
						$.messager.show({
							title : '提示',
							msg : '提交成功！'
						});
						$('#changeToFirstDlg').dialog('close');
						$("#approvePage").datagrid('reload'); 
					} else {
						$.messager.show({
							title : 'Error',
								msg : data.msg
							});
					}
				   }
				});	
			}
	});
}