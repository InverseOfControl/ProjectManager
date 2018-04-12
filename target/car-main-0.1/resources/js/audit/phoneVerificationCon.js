$(function () {
	// 查询按钮
	// $('#ClickMe').bind('click', ClickMe);
	$('#replySeEditForm').find('input').attr("readonly","readonly");
	$('#replySeEditForm').find('textarea').attr("readonly","readonly");
	 
});
function updatePerson(obj,type){
	var info=$(obj).val();
	if(info==null || info==""){
		return false;
	}
	var id;
	if(type==1){
		  id=$("#personId").val();
	}else{
		  id=$("#companyId").val();
	}
	 $.ajax({
			url:'phoneVerification/phoneVerificationMain/updatePerson',
			data:{"info":info,"type":type,"id":id},
			dataType:"JSON",
			type:"POST",
			async:true,
			success:function(data){
				 
			},
			error:function(data){
				 
			}
		});	
}
function ClickMe(type){
    var row = document.getElementById("trHidden");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable").append("<tr><td style='display:none'></td><td style='width:100px'><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert(this,0)' readonly='true'></textarea></td><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;' name='name2'  onblur='dataInsert(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)' value='编辑 '><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
function ClickMe2(type){
    var row = document.getElementById("trHidden2");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable2").append("<tr><td style='display:none'></td><td><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert2(this,0)' readonly='true'></textarea></td><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;'  name='name2' onblur='dataInsert2(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)'   value='编辑 '><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
function ClickMe3(type){
    var row = document.getElementById("trHidden3");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable3").append("<tr><td style='display:none'></td><td><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert3(this,0)' readonly='true'></textarea></td><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;'  name='name2' onblur='dataInsert3(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)'   value='编辑 '><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
function ClickMe4(type){
    var row = document.getElementById("trHidden4");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable4").append("<tr><td style='display:none'></td><td><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert4(this,0)' readonly='true'></textarea></td><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;'  name='name2' onblur='dataInsert4(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)'  value='编辑 ' /><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
function ClickMe5(type){
    var row = document.getElementById("trHidden5");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable5").append("<tr><td style='display:none'></td><td><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert5(this,0)' readonly='true'></textarea><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;'  name='name2' onblur='dataInsert5(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)'  value='编辑 '><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
function ClickMe6(type){
    var row = document.getElementById("trHidden6");  
	if(type==0){
		 row.style.display = (document.all ? "block" : "table-row");  
	}
	var nowTime=CurentTime();
	$("#mytable6").append("<tr><td style='display:none'></td><td><textarea  style='width:100px;height:200px;'  name='name1' onblur='dataInsert6(this,0)' readonly='true'></textarea></td><td>"+nowTime+"</td><td>" +
			"<textarea  style='width:300px;height:200px;'  name='name1' onblur='dataInsert6(this,1)' readonly='true'></textarea></td>" +
			"<td><input type='button' id='edit' onclick='editRow(this)'  value='编辑 '><input type='button' value='删除' onclick='delRow(this)' /></td></tr>");
}
	//删除行
function delRow(obj){
		var tr = this.getRowObj(obj);
	if(tr != null){
		 var isId= tr.cells[0].innerText;
		 if(isId==null ||  isId==""){
			 tr.parentNode.removeChild(tr);
		 }else{
			 $.ajax({
					url:'phoneVerification/phoneVerificationMain/telDel',
					data:{"id":isId},
					dataType:"JSON",
					type:"POST",
					async:true,
					success:function(data){
						tr.parentNode.removeChild(tr);
					},
					error:function(data){
						 
					}
				});	
		 }
	
	}else{
		throw new Error("the given object is not contained by the table");
	}
 
};
//编辑行
function editRow(obj){
	var tr = this.getRowObj(obj);
	/*tr.find("input[type = 'text']").readonly(false);*/
	$(tr).find("textarea").attr("readonly",false);
	
};
function dataInsert(obj,num){
		//得到行对象
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#phoneInput").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=1;
    		 var relation="";
    		 var content="";
    		 
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
    							 
    								$(tr).find("textarea[name='name1']").attr("readonly",true);
    								 
    			    		 }
    			    		 if(num==1){
    			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
    			    		 }
    						
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
 							   $(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    				$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	function dataInsert2(obj,num){
		//得到行对象 
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#homePhone").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=2;
    		 var relation="";
    		 var content="";
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
 								$(tr).find("textarea[name='name1']").attr("readonly",true);
 			    		 }
    						 if(num==1){
 			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
    						 }
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
								$(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	function dataInsert3(obj,num){
		//得到行对象
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#parentPhone").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=3;
    		 var relation="";
    		 var content="";
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
 								$(tr).find("textarea[name='name1']").attr("readonly",true);
 			    		 }
    						 if(num==1){
 			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
    						 }
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
								$(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	function dataInsert4(obj,num){
		//得到行对象
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#colleaguePhone").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=4;
    		 var relation="";
    		 var content="";
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
 								$(tr).find("textarea[name='name1']").attr("readonly",true);
 			    		 }
 			    		 if(num==1){
 			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
 			    		 }
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
								$(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	function dataInsert5(obj,num){
		//得到行对象
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#colleaguePhone").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=5;
    		 var relation="";
    		 var content="";
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
 								$(tr).find("textarea[name='name1']").attr("readonly",true);
 			    		 }
 			    		 if(num==1){
 			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
 			    		 }
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
								$(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	function dataInsert6(obj,num){
		//得到行对象
		var tr = this.getRowObj(obj);
		if(tr != null){
    		 var nameInput =$("#nameInput").val();
    		 var companyInput =$("#companyInput").val();
    		 var loanIdInput =$("#loanIdInput").val();
    		 var phoneInput =$("#colleaguePhone").val();
    		 var inquiryTime=tr.cells[2].innerText;
    		 var telType=6;
    		 var relation="";
    		 var content="";
    		 if(num==0){
    			 relation=tr.getElementsByTagName('textarea')[num].value;
    			 if(relation==null || relation==""){
    				 return false;
    			 }
    		 }
    		 if(num==1){
    			 content=tr.getElementsByTagName('textarea')[num].value;
    			 if(content==null || content==""){
    				 return false;
    			 }
    		 }
    		 var isId= tr.cells[0].innerText;
    		 if(isId==null ||  isId==""){
    			 $.ajax({
    					url:'phoneVerification/phoneVerificationMain/telSave',
    					data:{"name":nameInput,"tel":phoneInput,"company":companyInput,"loanId":loanIdInput,"telType":telType,"relation":relation,"content":content,"inquiryTime":inquiryTime},
    					dataType:"JSON",
    					type:"POST",
    					async:true,
    					success:function(data){
    						var obj = eval("("+ data+ ")");
    						 tr.cells[0].innerText=obj.id;
    						 if(num==0){
 								$(tr).find("textarea[name='name1']").attr("readonly",true);
 			    		 }
 			    		 if(num==1){
 			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
 			    		 }
    					},
    					error:function(data){
    						 
    					}
    				});	
    		 }else {
    			 $.ajax({
 					url:'phoneVerification/phoneVerificationMain/telUpdate',
 					data:{"id":isId,"relation":relation,"content":content},
 					dataType:"JSON",
 					type:"POST",
 					async:true,
 					success:function(data){
 						 if(num==0){
								$(tr).find("textarea[name='name1']").attr("readonly",true);
			    		 }
			    		 if(num==1){
			    			 	$(tr).find("textarea[name='name2']").attr("readonly",true);
			    		 }
 					},
 					error:function(data){
 						 
 					}
 				});	
    		 }
			
			
		}
	};
	//得到行对象
	function getRowObj(obj)
	{
		var i = 0;
		while(obj.tagName.toLowerCase() != "tr"){
			obj = obj.parentNode;
			if(obj.tagName.toLowerCase() == "table")return null;
		}
		return obj;
	}
	//根据得到的行对象得到所在的行数
	function getRowNo(obj){
			var trObj = getRowObj(obj);
			var trArr = trObj.parentNode.children;
			for(var trNo= 0; trNo < trArr.length; trNo++){
				if(trObj == trObj.parentNode.children[trNo]){
					alert(trNo+1);
				}
			}
	};

	function CurentTime()
	{ 
		var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds();
	    return currentdate;
	} 

