$(function (){
	// 查询按钮
/*	 $('#telCheckBut').bind('click', telCheck);*/
	 $('#telCheckBut').click(function(){
		 var type=	 $('#type').val();
		 $('#phoneVerificationDlg').dialog({
				title : '电核',
				inline: true,
				closed : false,
				cache : false,
				maximizable: true,
				href : "phoneVerification/phoneVerificationMain/init/"+loanId+"/"+type
			
		 });
		});
	 $('#telCheckButScan').click(function(){
		 var type=	 $('#type').val();
		 $('#phoneVerificationDlg').dialog({
				title : '电核',
				inline: true,
				closed : false,
				cache : false,
				maximizable: true,
				href : "phoneVerification/phoneVerificationMain/initScan/"+loanId+"/"+type
			
		 });
		});
});
