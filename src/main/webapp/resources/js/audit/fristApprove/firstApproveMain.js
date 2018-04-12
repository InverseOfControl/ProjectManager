$(function () {
	// 查询按钮
	if($('#acceptAuditValue').val()=="1"){
		document.getElementById("acceptAuditSpan").innerHTML ="暂停接单";
	}else{
		document.getElementById("acceptAuditSpan").innerHTML ="开启接单";
	};
	 $('#acceptAudit').bind('click', acceptAudit);
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
        url: 'FirstApprove/FirstApproveData/updateAcceptAudit/'+status,
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

function  openDiv(){
	var td = document.getElementById('td1').childNodes.length;
	var loginName=$('#loginName').val() ;
	for(var i=0;i<td;i++){
		$('#div_').empty().remove();
	}
    //加载未处理的借款
    $.ajax({  
        type: "POST",  
        url: 'letterTrial/loanStatusList',
        cache: false,  
        dataType : "json",  
        success: function(data){  
        	if(data != null){
        		var td1 = document.getElementById('td1');
        		
        		for(var i=0;i<data.length;i++){
        			
        			if(loginName==data[i].name){
        			var div = document.createElement('div');
        			div.style.width = '200px';
        			div.style.float = 'center';
        			div.innerHTML = data[i].name+'&nbsp;&nbsp;&nbsp;&nbsp;'+data[i].settled+'/'+data[i].untreated+'';
        			div.id="div_";
        			td1.appendChild(div); 
        			}
        		}
        	}
        }  
     });  
	$('#p').panel('expand',true);
}
