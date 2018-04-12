$(function () {
	// 查询按钮
	/*if($('#acceptAuditValue').val()=="1"){
		document.getElementById("acceptAuditSpan").innerHTML ="暂停接单";
	}else{
		document.getElementById("acceptAuditSpan").innerHTML ="开启接单";
	};
	 $('#acceptAudit').bind('click', acceptAudit);*/
});
function acceptAudit(){
	var msg=null;
	var status=null;
	if($('#acceptAuditValue').val()=="1"){
		msg="确定暂停接单？";
		  status="0";
	} else{
		msg="确定开启接单？"; 
		  status="1";
	}
	 $.messager.confirm("操作提示", msg, function (data) {
		  if (data) {
	  $.ajax({
        type: 'GET',
        url: 'approvePhone/approvePhoneData/updateAcceptAudit/'+status,
        dataType: "json",
        success:function(data){
        	if(data.status=="1"){
        		$('#acceptAuditValue').val("1");
        		document.getElementById("acceptAuditSpan").innerHTML ="暂停接单";
        	}else{
        		$('#acceptAuditValue').val("0") ;
        		document.getElementById("acceptAuditSpan").innerHTML ="开启接单";
        	}
        }
    });
		  }
	  });
	 
}