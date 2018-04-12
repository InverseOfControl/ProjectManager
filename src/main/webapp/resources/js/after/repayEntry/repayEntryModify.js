function loadEditRepayEntry(repayEntryDetailsVO){
	
	 $('#browseForm').find('#personName').text(repayEntryDetailsVO.personName);	
	 $('#browseForm').find('#personIdnum').text(repayEntryDetailsVO.personIdnum);	
	 $('#browseForm').find('#personMobilePhone').text(repayEntryDetailsVO.personMobilePhone);	
	 $('#browseForm').find('#overdueStartDate').text(repayEntryDetailsVO.overdueStartDate);	
	 $('#browseForm').find('#overdueTerm').text(repayEntryDetailsVO.overdueTerm);	
	 $('#browseForm').find('#overdueAmount').text(repayEntryDetailsVO.overdueAmount.toFixed(2));	
	 $('#browseForm').find('#fineDate').text(repayEntryDetailsVO.fineDate);	
	 $('#browseForm').find('#fineDay').text(repayEntryDetailsVO.fineDay);	
	 $('#browseForm').find('#fine').text(repayEntryDetailsVO.fine.toFixed(2));	
	 $('#browseForm').find('#curRepayDate').text(repayEntryDetailsVO.curRepayDate);	
	 $('#browseForm').find('#currTerm').text(repayEntryDetailsVO.currTerm);	
	 $('#browseForm').find('#currAmountLabel').text(repayEntryDetailsVO.currAmountLabel);	
	 $('#browseForm').find('#currAmount').text(repayEntryDetailsVO.currAmount.toFixed(2));	
	 $('#browseForm').find('#accAmount').text(repayEntryDetailsVO.accAmount.toFixed(2));	
	 $('#browseForm').find('#reliefOfFine').text(repayEntryDetailsVO.reliefOfFine.toFixed(2));	
	 $('#browseForm').find('#repayAllAmount').text(repayEntryDetailsVO.repayAllAmount.toFixed(2));	
	 $('#browseForm').find('#repayAmount').text(repayEntryDetailsVO.repayAmount.toFixed(2));	
	 $('#browseForm').find('#nowDate').text(repayEntryDetailsVO.nowDate);	
	 $('#browseForm').find('#loanIdValue').text(repayEntryDetailsVO.loanIdValue);	
					
	  $('#browseForm').find('#loanId').val(repayEntryDetailsVO.loanId);
	  $('#browseForm').find('#nowDateValue').val(repayEntryDetailsVO.nowDateValue);
}


function submitEdit(){
	$('#submitEditBtn').linkbutton({disabled:true});
	  $.ajax({
		   url : 'after/repayEntry/saveRepay',
		   data : $("#browseForm").serialize(),
		   type:"POST",
		   async:false,  // 设置同步方式
	       cache:false,
		   success : function(result){
			   $('#submitEditBtn').linkbutton({disabled:false});
			   $.messager.progress('close');
		   		if(result=='success'){
		   			$.messager.show({
						title : '提示',
						msg : '保存成功！'
					});
		   			$('#repayModify').dialog('close');
		   			$('#list_result').datagrid('reload');
		   		}else{
		   			parent.$.messager.show({
						title: 'Error',
						msg: result
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