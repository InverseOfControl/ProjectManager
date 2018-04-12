var artChanged ;
var isselect ;
var newValues;
$(function () {
	
	//营业网点
    $('#departmentTableDiv #salesDeptComb').combobox({
        url:'organ/management/getAllSalesDept',
        valueField:'id',
        textField:'name',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1)
                $(this).combobox('select', data[0].id);
        }
    });
    
    //绑定add page上面BUTTON
    $('#organAddCancel').bind('click', organCancel);
    $('#organAddSave').bind('click', organSave);
    $('#bankCardAdd').bind('click', bankCardAdd);
    $('#salesDepartmentAdd').bind('click', salesDepartmentAdd);
    $('#salesManagerAdd').bind('click', salesManagerAdd);
    
    //绑定view page上面BUTTON
    $('#organViewEdit').bind('click', organEdit);
    $('#organViewDelete').bind('click', organDelete);
    //绑定modify page上面BUTTON
    $('#organModifyCancel').bind('click', organModifyCancel);
    $('#organModifySave').bind('click', organEditSave);
    $('#bankCardModify').bind('click', bankCardModify);
    $('#salesDepartmentModify').bind('click', salesDepartmentModify);
    $('#salesManagerModify').bind('click', salesManagerModify);
});

/**新建页面-撤销**/
function organCancel() {
	$.messager.confirm('确认对话框', '所录信息不再保存!', function(r) {
		if (r) {
		   $('#organAddDlg').dialog('close');
		   $("#list_result").datagrid('reload');
		} else {
			return false;
		}
	});
}

/**新建页面-保存**/
function organSave() {
	var isValidate = $('#organAddForm').form('validate');
	if (!isValidate) {
		return false;
	}
	
	$.ajax({
		type : 'post',
		url : 'organ/management/organSave',
		data : $('#organAddForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '新建成功!'
				});
				$('#organAddDlg').dialog('close');
				$("#list_result").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

/**修改页面-撤销**/
function organModifyCancel() {
	$.messager.confirm('确认对话框', '所录信息不再保存!', function(r) {
		if (r) {
			$('#organEditDlg').dialog('close');
			$("#list_result").datagrid('reload');
		} else {
			return false;
		}
	});
}

/**修改页面-保存**/
function organEditSave() {
	var isValidate = $('#organModifyForm').form('validate');
	if (!isValidate) {
		return false;
	}
	
	$.ajax({
		type : 'post',
		url : 'organ/management/organEditSave',
		data : $('#organModifyForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				parent.$.messager.show({
					title : '提示',
					msg : '编辑成功!'
				});
				$('#organEditDlg').dialog('close');
				$("#list_result").datagrid('reload');
			} else {
				parent.$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

/** 查看机构页面**/
function loadOrganDetail(id) {
	 var h = $(window).height() ;
	 var w = $(window).width() ;
	var strData = getOrganDetails(id);
	var organDetails = $.parseJSON(strData);
	$('#organViewDlg').dialog({
        title: '查看机构',
        width:  w*0.8,
		height: h*0.8,
        closed: false,  
        cache: false,
        href: 'organ/management/organViewDetail',
        modal: true,
        onLoad : function(){
        	renderOtherField(organDetails,'organViewForm');
        	$('#organViewForm #organId').val(id); 
        	$('#organViewForm #organName').val(organDetails.name); 
        	$('#organViewForm #signedDate').html(formatSignDateView(organDetails.signedDate));
        	  if(organDetails.bankAccountList) {//银行信息
        		  for(var i =0;i<organDetails.bankAccountList.length;i++){
                     var bankAcct = organDetails.bankAccountList[i];
                 	 var  i =$("#organViewForm #bankAccountTable tr").length;  
                 	 var row = "<tr id=bankAccountTr"+i+"> <td><label>卡"+(i+1)+" :户名：</label>";
                 	 row +="<label>"+bankAcct.accountName+"</label></td>";
                 	 row+=" <td><label>卡类别： </label>"+"<label>"+formatEnumName(bankAcct.cardType,'CARD_TYPE')+"</label></td>";
                 	 row+=" <td><label>开户银行：</label> <label>"+bankAcct.bankName+"</label></td>";
                 	 row+="<td><label>银行卡号： </label> <label>"+bankAcct.account+"</label></td>";
                 	 row+="<td><label>支行信息： </label> <label>"+bankAcct.branchName+"</label></td></tr>";
                 	 $("#organViewForm #bankAccountTable").append(row);
                 	 $("#organViewForm #bankAccountTable").css('display','block');
        		  }
              }
        	  if(organDetails.salesDepartmentList) {//网点信息
        		  for(var i =0;i<organDetails.salesDepartmentList.length;i++){
        			  var salesDepart = organDetails.salesDepartmentList[i];
        			  var  i =$("#organViewForm #salesDepartmentTable tr").length;  
        			  var row = "<tr id=salesManagerTableTr"+i+"><td><label>"+salesDepart.name+"</label></td></tr>";
        			  $("#organViewForm #salesDepartmentTable").append(row);
        		  }
        	  }
        	  if(organDetails.salesManagerList) {//客户经理信息
        		  for(var i =0;i<organDetails.salesManagerList.length;i++){
        			  var organSalesManager = organDetails.salesManagerList[i];
        			  var row = "<tr id=salesManagerTableTr"+i+">";
        			  row+=" <td>"+organSalesManager.salesManager+","+organSalesManager.code+"</td></tr>";
        			  $("#organViewForm #salesManagerTable").append(row);
        		  }
        	  }
        }
    });
}


/**获取详细信息*/
function getOrganDetails(organId){
    var response = $.ajax({
        type: "POST",
        url: "organ/management/getOrganDetail",
        dataType: "json",
        async:false,
        data: {organId:organId},
        error:function(){
            $.messager.show({
                title:'加载机构详细信息',
                msg:'加载机构详细信息失败！',
                showType:'slide'
            });
        }
    });
    return response.responseText;
}
function dataInsert(obj){
	alert("s");
	alert($(obj).combobox('getValue'));
}
function organEdit(){
	var organId = $('#organViewForm #organId').val();

/*		if(!validationOrgan(organId)){
			return;
		}*/
	var strData = getOrganDetails(organId);
	var organDetails = $.parseJSON(strData);
	 var h = $(window).height() ;
	 var w = $(window).width() ;
	$('#organEditDlg').dialog({
        title: '查看机构',
        width:  w*0.8,
		height: h*0.8,
        closed: false,  
        cache: false,
        href: 'organ/management/organModify',
        modal: true,
        onLoad : function(){
        	$('#organViewDlg').dialog('close');
        	renderOtherField(organDetails,'organModifyForm');
        	$('#organModifyForm #organId').val(organId); 
        	$('#organModifyForm #code').attr("readonly","readonly");
        	$('#organModifyForm #signedDate').val(formatSignDateView(organDetails.signedDate));
        	if(organDetails.bankAccountList) {//银行信息
        		  for(var i =0;i<organDetails.bankAccountList.length;i++){
                     var bankAcct = organDetails.bankAccountList[i];
                 	 var row = "<tr id=bankAccountTr"+i+"> <td> <span class='pre_span'><font color='red'>*</font></span><label>户名：</label>";
                	 row +="<input id=accountName"+i+ " name=bankAccountList["+i+"].accountName" +"  class='easyui-validatebox' maxlength='30'  style='width: 80px'/></td>";
                	 
                	 row+=" <td> <span class='pre_span'><font color='red'>*</font></span><label>卡类别： </label> <select style='width: 50px;' " +
                	 		"id=cardType"+i+" name=bankAccountList["+i+"].cardType "+" editable='false' class='easyui-combobox'>" +
                	 				"<option value='1'>对公</option><option value='2'>对私</option></select></td>";
                	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>开户银行：</label><input    id=bankName"+i+" name=bankAccountList["+i+"].bankName "
                	 + " class='easyui-combobox' required='true' data-options='width:125," +
		" onChange: function(newValue, oldValue){selectOnChange(this,newValue)}," +
		" onHidePanel: function () {selectOnHidePanel(this)},"+
		" onSelect: function () {selectOnSelect(this)},"+
		" onBeforeLoad: function () {seOnLoadSuccess(this)},"+
		" '></td>";
           

                	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>银行卡号： </label> <input id=account"+i+" name=bankAccountList["+i+"].account required='true'  validType='integerCheck' class='easyui-validatebox' maxlength='30'  style='width: 130px'/></td>";
                	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>支行信息： </label> <input class='easyui-validatebox' type='text' id=branchName"+i+"  name=bankAccountList["+i+"].branchName   />" +
                	 		" &nbsp; <a herf='#' class='organModifyBankAccountClass' style='cursor:pointer'>删除</a> </td></tr>";
                	 
                	 $("#organModifyForm #bankAccountTable").append(row);
                	 //开户银行
                	 $("#organModifyForm #bankAccountTable #bankName"+i).combobox({
                	       url:'bank/getBankListIn.json',
                	       valueField:'bankName',
                	       textField:'bankName',
                	       onLoadSuccess:function(){
                	           var data = $(this).combobox('getData');
                	           if(data.length==1)
                	               $(this).combobox('select', data[0].id);
                	       }
                	  });
                	 $('#organModifyForm #accountName'+i).val(bankAcct.accountName);
                	 $('#organModifyForm #cardType'+i).val(bankAcct.cardType);
                	 $('#organModifyForm #bankName'+i).combobox('select', bankAcct.bankName);
                	 $('#organModifyForm #account'+i).val(bankAcct.account);
                	 $('#organModifyForm #branchName'+i).val(bankAcct.branchName);
                	 
                	 $('#organModifyForm #accountName'+i).validatebox({required:true });
                	 $('#organModifyForm #account'+i).validatebox({required:true });
                	 $('#organModifyForm #branchName'+i).validatebox({required:true });
                	 //动态绑定删除函数-FOR IE VERSION
                	 $('#organModifyForm .organModifyBankAccountClass').unbind().bind('click', deleteBankAccountModify);
        		  }
              }
        	  if(organDetails.salesDepartmentList) {//网点信息
        		  for(var i =0;i<organDetails.salesDepartmentList.length;i++){
        			  var salesDepart = organDetails.salesDepartmentList[i];
        			  var row = "<tr id=salesDepartmentTr"+i+"><td>"+salesDepart.name+
        			  		"&nbsp; <a herf='#' class='organModifySalesDeptClass' style='cursor:pointer'>删除</a> " +
        			  				"<input name='salesDepartmentList["+i+"].id' value="+salesDepart.id+" type='hidden'> </td></td></tr>";
        			  $("#organModifyForm #salesDepartmentTable").append(row);
        			  //动态绑定删除函数-FOR IE VERSION
        			  $('#organModifyForm .organModifySalesDeptClass').unbind().bind('click', deleteSalesDeptModify); 
        		  }
        	  }
        	  if(organDetails.salesManagerList) {//客户经理信息
        		  for(var i =0;i<organDetails.salesManagerList.length;i++){
        			  var organSalesManager = organDetails.salesManagerList[i];
        			  if(i==0){ //第一行
        				  $("#organModifyForm #saleManagerName0").val(organSalesManager.salesManager);
        				  $("#organModifyForm #saleManagerCode0").val(organSalesManager.code);
        				  continue;
        			  }
    				 var row = "<tr id=salesManagerTableTr"+i+"> <td>" +
    					 "<span class='pre_span'><font color='red'>*</font></span><label>客户经理：</label>"+
    					 "<input id=saleManagerName"+i+" name=salesManagerList["+i+"].salesManager " +
    					 		" value="+organSalesManager.salesManager+" class='easyui-validatebox' maxlength='30' style='width: 80px'/></td>";
    				 row += "<td><span class='pre_span'><font color='red'>*</font></span><label>工号： </label>"+
    			     	 "<input id=saleManagerCode"+i+" name=salesManagerList["+i+"].code class='easyui-validatebox' value="+organSalesManager.code+" maxlength='30' style='width: 80px'/>"
    			     	 +"&nbsp;<a herf='#' class='organModifySalesManagerClass' style='cursor:pointer' class='easyui-linkbutton' iconCls='icon-add'>删除</a></td></tr>";
    				 
    				 $("#organModifyForm #salesManagerTable").append(row);
    				 
    				 $('#organModifyForm #saleManagerName'+i).validatebox({required:true });
    				 $('#organModifyForm #saleManagerCode'+i).validatebox({required:true });
    				 //动态绑定删除函数-FOR IE VERSION
    			     $('#organModifyForm .organModifySalesManagerClass').unbind().bind('click', deleteSalesManagerModify);
        		  }
        	  }
        }
    });
}
function organDelete(){
	$.messager.confirm('确认对话框', '该机构信息将全部删除，确定删除？', function(r) {
		if (r) {
			var organId = $('#organViewForm #organId').val();
			if(!validationOrgan(organId)){
				return;
			}
			$.ajax({
				type: "POST",
				url: "organ/management/doOrganDelele",
				dataType: "json",
				async:false,
				data: {organId:organId},
				success: function(message){
					$.messager.show({
						title:'提示',
						msg:message,
						showType:'slide'
					});
					$('#organViewDlg').dialog('close');
					$("#list_result").datagrid('reload');
				},
				error:function(message){
					$.messager.show({
						title:'删除',
						msg:message,
						showType:'slide'
					});
				}  
			});
		} else {
			return false;
		}
	});
}

/**验证机构是否可以编辑删除**/
function validationOrgan(organId){
	var url = 'organ/management/validationOrgan';
	var flag = false;
	$.ajax({
		  type: "POST",
		  url: url,
		  dataType: "json",
		  async: false,
		  data: {
			  organId:organId
		  },
		  success:function(data){
				if (data.success != 0) {
					$.messager.show({
			                msg:data.msg,
			                showType:'slide'
			         });
					flag = false;
				}else{
					flag = true;
				}
		 },
		  error:function(message){
              $.messager.show({
                  title:'删除',
                  msg:'删除失败',
                  showType:'slide'
              });
          }
	 });
	return flag;
}
function selectOnChange(obj,newValue1){
	 newValues= newValue1;
	 
}
function selectOnHidePanel(obj){
	 if(isselect==newValues){
		}else{
			 $(obj).combobox('setValue', '');
		}
	
}
function selectOnSelect(obj){
	//console.info("1");
	//console.info($(obj).combobox('getValue'));
	isselect=$(obj).combobox('getValue');
	
}
function seOnLoadSuccess(obj){
/*	$(obj).combobox('panel').panel({   
	    onClose:function(){   
	    	selectOnHidePanel(obj); 
	    }   
	}) ; */
	
	$(obj).next().children(":text").blur(function() {
	    // do something
		selectOnHidePanel(obj); 
	});
}

/** add page bank add start**/
function bankCardAdd() {
	 var  i =$("#organAddForm #bankAccountTable tr").length;  
	
	 var row = "<tr id=bankAccountTr"+i+"> <td> <span class='pre_span'><font color='red'>*</font></span><label>户名：</label>";
	 row +="<input id=accountName"+i+ " name=bankAccountList["+i+"].accountName" +" class='easyui-validatebox'  maxlength='30'  style='width: 80px'/></td>";
	 
	 row+=" <td> <span class='pre_span'><font color='red'>*</font></span><label>卡类别： </label> <select style='width: 50px;' " +
	 		"id=cardType"+i+" name=bankAccountList["+i+"].cardType "+" editable='false' class='easyui-combobox'>" +
	 				"<option value='1'>对公</option><option value='2'>对私</option></select></td>";
	 
	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>开户银行：</label><input   id=bankName"+i+" name=bankAccountList["+i+"].bankName "
	 + " class='easyui-combobox' required='true' data-options='width:125," +
		" onChange: function(newValue, oldValue){selectOnChange(this,newValue)}," +
		" onHidePanel: function () {selectOnHidePanel(this)},"+
		" onSelect: function () {selectOnSelect(this)},"+
		" onBeforeLoad: function () {seOnLoadSuccess(this)},"+
		" '></td>";
										            
										            
										
										          
										             
											

	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>银行卡号： </label> <input id=account"+i+" name=bankAccountList["+i+"].account  class='easyui-validatebox' validType='integerCheck' maxlength='30'  style='width: 130px'/></td>";
	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>支行信息： </label> <input class='easyui-validatebox' type='text' id=branchName"+i+"  name=bankAccountList["+i+"].branchName  />" +
	 		" &nbsp; <a herf='#' class='organAddBankAccountClass' style='cursor:pointer'>删除</a> </td></tr>";
	
	 $("#organAddForm #bankAccountTable").append(row);
	
	 $('#organAddForm #accountName'+i).validatebox({required:true });
	 $('#organAddForm #account'+i).validatebox({required:true });
	 $('#organAddForm #branchName'+i).validatebox({required:true });
	 //动态绑定删除函数-FOR IE VERSION
	 $('#organAddForm .organAddBankAccountClass').unbind().bind('click', deleteBankAccount);
	    
	//开户银行
    $("#organAddForm #bankAccountTable #bankName"+i).combobox({
       url:'bank/getBankListIn.json',
       valueField:'bankName',
       textField:'bankName',
       onLoadSuccess:function(){
           var data = $(this).combobox('getData');
           if(data.length==1)
               $(this).combobox('select', data[0].id);
       }
    });
	$("#organAddForm #bankAccountTable").css('display','block');
}

function salesDepartmentAdd(){
	var  i =$("#organAddForm #salesDepartmentTable tr").length;
	var value = $("#organAddForm #salesDeptComb").combobox('getValue');
	var text = $("#organAddForm #salesDeptComb").combobox('getText');
	if(value ==''){
		$.messager.show({
			title : 'Error',
			msg : "请选择签约门店!"
		});
		return;
	}
	 //是否存在判断
	 var existedFlag = false;
	 $("#organAddForm #salesDepartmentTable tr td").each(function () {
		   var existed = $(this).find("input").val();
		   if(value == existed){
			   $.messager.show({
					title : 'Error',
					msg : "添加了已经存在的门店!"
				});
			   existedFlag = true;
			   return false; //跳出循环
		   }
	 });
	 if(existedFlag){ 
		 return;
	 }
	 
	var row ="<tr id=salesDepartmentTr"+i+"><td>"+text+
	         "&nbsp; <a herf='#' class='organAddSalesDeptClass' style='cursor:pointer'>删除</a> " +
	        		"<input name='salesDepartmentList["+i+"].id' value="+value+" type='hidden'> </td></tr>";
	$("#organAddForm #salesDepartmentTable").append(row);
	 //动态绑定删除函数-FOR IE VERSION
	 $('#organAddForm .organAddSalesDeptClass').unbind().bind('click', deleteSalesDepartment);
}

function salesManagerAdd() {
	 var  i =$("#organAddForm #salesManagerTable tr").length; 
	 var row = "<tr id=salesManagerTableTr"+i+"> <td>" +
		 "<span class='pre_span'><font color='red'>*</font></span> <label>客户经理：</label>"+
		 "<input id=saleManagerName"+i+" name=salesManagerList["+i+"].salesManager required='true' class='easyui-validatebox' maxlength='30' style='width: 80px'/>"
	     +"</td>";
	 row += "<td><span class='pre_span'><font color='red'>*</font></span>" +
	 		"<label>工号： </label>"+
     	 "<input id=saleManagerCode"+i+" name=salesManagerList["+i+"].code  required='true' class='easyui-validatebox' maxlength='30' style='width: 80px'/>"+
	        "&nbsp;<a herf='#' class='organAddSalesManagerClass' style='cursor:pointer' class='easyui-linkbutton' iconCls='icon-add'>删除</a></td></tr>";
	 
	 $("#organAddForm #salesManagerTable").append(row);
	 $('#organAddForm #saleManagerName'+i).validatebox({required:true });
	 $('#organAddForm #saleManagerCode'+i).validatebox({required:true });
	 //动态绑定删除函数-FOR IE VERSION
	 $('#organAddForm .organAddSalesManagerClass').unbind().bind('click', deleteSalesManager);
}


function deleteSalesDepartment(){
	$(this).parent().parent().remove();
}

function deleteBankAccount(){
	$(this).parent().parent().remove();
}

function deleteSalesManager(){
	$(this).parent().parent().remove();
	//$("#organAddForm #salesManagerTableTr"+index).remove();  
}
/**add page end **/


/** modify page bank add start**/
function bankCardModify() {
	 var  i =$("#organModifyForm #bankAccountTable tr").length;  
	
	 var row = "<tr id=bankAccountTr"+i+"> <td> <span class='pre_span'><font color='red'>*</font></span><label>户名：</label>";
	 row +="<input id=accountName"+i+ " name=bankAccountList["+i+"].accountName" +"  class='easyui-validatebox' maxlength='30' style='width: 80px'/></td>";
	 
	 row+=" <td> <span class='pre_span'><font color='red'>*</font></span><label>卡类别： </label> <select style='width: 60px;' " +
	 		"id=cardType"+i+" name=bankAccountList["+i+"].cardType "+" editable='false' class='easyui-combobox'>" +
	 				"<option value='1'>对公</option><option value='2'>对私</option></select></td>";
	 
	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>开户银行：</label><input onblur='dataInsert(this)'  id=bankName"+i+" name=bankAccountList["+i+"].bankName "
	 + " class='easyui-combobox' required='true' data-options='width:130," +
		" onChange: function(newValue, oldValue){selectOnChange(this,newValue)}," +
		" onHidePanel: function () {selectOnHidePanel(this)},"+
		" onSelect: function () {selectOnSelect(this)},"+
		" onBeforeLoad: function () {seOnLoadSuccess(this)},"+
		" '></td>";
	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>银行卡号： </label> <input id=account"+i+" name=bankAccountList["+i+"].account  class='easyui-validatebox' validType='integerCheck' maxlength='30'  style='width: 130px'/></td>";
	 row+="<td><span class='pre_span'><font color='red'>*</font></span><label>支行信息： </label> <input class='easyui-validatebox' type='text' id=branchName"+i+"  name=bankAccountList["+i+"].branchName  />" +
	 		" &nbsp; <a herf='#' class='organModifyBankAccountClass' style='cursor:pointer'>删除</a> </td></tr>";
	
	$("#organModifyForm #bankAccountTable").append(row);
	$('#organModifyForm #accountName'+i).validatebox({required:true });
	$('#organModifyForm #account'+i).validatebox({required:true });
	$('#organModifyForm #branchName'+i).validatebox({required:true });
	 //动态绑定删除函数-FOR IE VERSION
	$('#organModifyForm .organModifyBankAccountClass').unbind().bind('click', deleteBankAccountModify);
	//开户银行
	  $("#organModifyForm #bankAccountTable #bankName"+i).combobox({
	      url:'bank/getBankListIn.json',
	      valueField:'bankName',
	      textField:'bankName',
	      onLoadSuccess:function(){
	          var data = $(this).combobox('getData');
	          if(data.length==1)
	              $(this).combobox('select', data[0].id);
	      }
	  });
	$("#organModifyForm #bankAccountTable").css('display','block');
}

function salesDepartmentModify(){
	var  i =$("#organModifyForm #salesDepartmentTable tr").length;
	var value = $("#organModifyForm #salesDeptComb").combobox('getValue');
	var text = $("#organModifyForm #salesDeptComb").combobox('getText');
	if(value ==''){
		$.messager.show({
			title : 'Error',
			msg : "请选择签约门店!"
		});
		return;
	}
	 //是否存在判断
	 var existedFlag = false;
	 $("#organModifyForm #salesDepartmentTable tr td").each(function () {
		   var existed = $(this).find("input").val();
		   if(value == existed){
			   $.messager.show({
					title : 'Error',
					msg : "添加了已经存在的门店!"
				});
			   existedFlag = true;
			   return false; //跳出循环
		   }
	 });
	 if(existedFlag){ 
		 return;
	 }
	var row ="<tr id=salesDepartmentTr"+i+"><td>"+text+
	         "&nbsp; <a herf='#' class='organModifySalesDeptClass' style='cursor:pointer'>删除</a> " +
	        		"<input name='salesDepartmentList["+i+"].id' value="+value+" type='hidden'> </td></tr>";
	$("#organModifyForm #salesDepartmentTable").append(row);
	//动态绑定删除函数-FOR IE VERSION
	$('#organModifyForm .organModifySalesDeptClass').unbind().bind('click', deleteSalesDeptModify);
}

function salesManagerModify() {
	 var  i =$("#organModifyForm #salesManagerTable tr").length; 
	 var row = "<tr id=salesManagerTableTr"+i+"> <td>" +
		 "<span class='pre_span'><font color='red'>*</font></span><label>客户经理：</label>"+
		 "<input id=saleManagerName"+i+" name=salesManagerList["+i+"].salesManager class='easyui-validatebox' maxlength='30' style='width: 80px'/>"
	     +"</td>";
	 row += "<td><span class='pre_span'><font color='red'>*</font></span><label>工号： </label>"+
    	 "<input id=saleManagerCode"+i+" name=salesManagerList["+i+"].code  class='easyui-validatebox' maxlength='30' style='width: 80px'/>"+
	        "&nbsp;<a herf='#' class='organModifySalesManagerClass' style='cursor:pointer' class='easyui-linkbutton'" +
	        " iconCls='icon-add'>删除</a></td></tr>";
	 
	 $("#organModifyForm #salesManagerTable").append(row);
	 $('#organModifyForm #saleManagerName'+i).validatebox({required:true });
	 $('#organModifyForm #saleManagerCode'+i).validatebox({required:true });
	 //动态绑定删除函数-FOR IE VERSION
     $('#organModifyForm .organModifySalesManagerClass').unbind().bind('click', deleteSalesManagerModify);
}

function deleteSalesDeptModify(){
	$(this).parent().parent().remove();
}

function deleteBankAccountModify(){
	$(this).parent().parent().remove();
}

function deleteSalesManagerModify(){
	$(this).parent().parent().remove();
}
/**page sales department add end**/

function formatSignDateView(value){
	if(value && value.length>=10){
		return value.substr(0, 10);
	}else{
		return "";
	}
}
$.extend($.fn.validatebox.defaults.rules, {
    phoneTelCheck:{
        validator:function(value,param){
        	
            return (/^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$/.test(value));
        },
        message:'联系电话不正确'
    }
	
});