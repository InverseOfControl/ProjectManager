var url;
$(function () {
	// 查询按钮
	$('#searchBt').bind('click', search);
	
	$('#toolbar #salesDeptId').combobox({     
		  url:'CollectionCreateCases/Main/getDepts',
		    valueField:'id',
		    textField:'name',
		    onLoadSuccess:function(){
	        	var data = $(this).combobox('getData');
	        	if(data.length>0)
	        		$(this).combobox('select', data[0].id);
	        }
	}); 
	
    $('#toolbar #loanType').combobox({
        url:'apply/getLoanType',
        valueField:'productType',
        textField:'productTypeName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length==1){
                $(this).combobox('select', data[0].productType);
            }else if(data.length>0){
            	$(this).combobox('select', data[0].productType);
            }
          //  userProductType = data[0].productType;
        }
    });
    $('#toolbar #productTypeComb').combobox({
        url:'apply/getProductTypeRemovemallBusiness',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length>0)
        		$(this).combobox('select', data[0].id);
        }
    }); 
	
    $('#casesPageTb').datagrid({
        url: 'CollectionManagerCases/Main/collectionList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        fit:true,
        checkOnSelect:true,
        queryParams: {
        	caseState : 8,
        	productType:1
    	},

        columns : [ [ 
                     { 
              			field : 'ck',
              			checkbox:'true'
                      },
                     {
             			field : 'id',
             			title : 'id',
             			width : 180,
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     }, 
                     {
              			field : 'loanId',
              			title : 'lId',
              			width : 180,
              			hidden:true,
              			formatter: function(value, row, index){
              	          	return value;
              	        	}
                      }, 
                      {
                			field : 'status',
                			title : 'lstatus',
                			width : 180,
                			hidden:true,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                      {
                			field : 'contractNo',
                			title : '合同编号',
                			width : 180,
                			formatter: function(value, row, index){
                				if(value==null || value==undefined){
                					value="";
                				}
                				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="addCasesDlg('+row.id+ ',' +1 + ', '+row.loanId+' )" >' + value + '</a>';
                	        	}
                        }, 
                        {
                			field : 'caseState',
                			title : '案件状态',
                			width : 180,
                			formatter: function(value, row, index){
                				 if(row.caseState==1 ){
             					 	return "客服催收中";
             				 }else if(row.caseState==2){
             						return "未分派 ";
             				 }else if(row.caseState==3){
             						return "部门催收中";
             				 }else if(row.caseState==4){
             						return "作业完成_未全部收回";
             				 }else if(row.caseState==5){
             						return "作业完成_全部收回";
             				 }else if(row.caseState==6){
             						return "结案_全部收回";
             				 }else if(row.caseState==7){
             						return "结案_坏账";		
             						 }
                	        	}
                        }
//                        , {
//                			field : 'caseType',
//                			title : '案件分类',
//                			width : 180,
//                			formatter: function(value, row, index){
//                				 if(row.caseType==1 ){
//             					 	return "NONE";
//             				 }else if(row.caseType==2){
//             						return "CLOSE ";
//             				 } 
//                	        	}
//                        }
                        ,{
                			field : 'transferCode',
                			title : '移交类型',
                			width : 180,
                			formatter: function(value, row, index){
                				 if(row.transferCode==1 ){
             					 	return "A";
             				 }else if(row.transferCode==2){
             						return "B ";
             				 } else if(row.transferCode==3){
             						return "C ";
             				 } else if(row.transferCode==4){
             						return "D ";
             				 } else if(row.transferCode==5){
             						return "E ";
             				 } else if(row.transferCode==6){
             						return "F";
             				 } 
                	        	}
                        },
                        {
                			field : 'transferDateStr',
                			title : '移交日期',
                			width : 180,
                			formatter: formatRequestDate
                		},
                		  {
                  			field : 'deptName',
                  			title : '管理营业部',
                  			width : 180,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          },   
                          {
                    			field : 'deptName2',
                    			title : '放款营业部',
                    			width : 180,
                    			formatter: function(value, row, index){
                    	          	return row.deptName;
                    	        	}
                            }, 
                            {
                    			field : 'productType',
                    			title : '借款类型',
                    			width : 180,
                    			formatter: function(value, row, index){
                    					return formatEnumName(value,'PRODUCT_TYPE');
                    	          }
                    		}, {
                    			field : 'productName',
                    			title : '产品类型',
                    			width : 180,
                    			formatter: function(value, row, index){
                    					return  value;
                    	          }
                    		}, 
                       {
                  			field : 'personName',
                  			title : '借款人',
                  			width : 180,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          },     
         {
			field : 'idNum',
			title : '身份证号',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        } 
		 
		] ]
	});
    // 设置分页控件
   var p = $('#casesPageTb').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
 
	 
	 $('#collectionUser').combobox({
	        url:'CollectionManagerCases/Main/getFirstTrials',
	        valueField:'id',
	        textField:'name',
	        onLoadSuccess:function(){
	            var data = $(this).combobox('getData');
	            if(data.length > 1)
	                 $(this).combobox('select', data[0].id);
	  
	        }
	  	  });
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	  
 	$('#dispatchBt').click(		function() {
 	 
 		if($('#optFlag').val()=="0"){
 			$.messager.alert("提示","无操作权限！");
	    		return false;
 		};
 		var checkedItems = $('#casesPageTb').datagrid('getChecked');
 	    var names = [];
 	    var flag=false;
 	  
 	     $.each(checkedItems, function(index, item){
 	    	 
 	    	 if(item.caseState==2||item.caseState==4){
 	    		  names.push(item.id);
 	    	 }else{
 	    		 flag=true;
 	    		$.messager.alert("提示","请勾选案件状态为“未分派”、“作业完成（未全部收回）的案件！");
 	    		return false;
 	    	 };
 	       
 	    });
 	    if(names.length<=0){
 	    	$.messager.alert("提示","请勾选案件状态为“未分派”、“作业完成（未全部收回）的案件！");
	    	return false;
 	    } 
 	   var collectionUser= $('#collectionUser').combobox('getValue');
 	   if(collectionUser==null || collectionUser=="" ){
 		 	$.messager.alert("提示","请选择催收员！");
	    	return false;
 	   }

 	 
 	  $.ajax({
          type: 'GET',
          url: 'CollectionManagerCases/Main/casesDispatch/'+names+"/"+collectionUser,
          data: names,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("提示","分派完成！");
        	  search();  
          }
      });
 	   
 	  
 	});
    //导出
	$('#excelBt').click(		function() {
	 	 
 		/*if($('#optFlag').val()=="0"){
 			$.messager.alert("提示","无操作权限！");
	    		return false;
 		};*/
 		var checkedItems = $('#casesPageTb').datagrid('getChecked');
 	    var names = [];
 	   var flag=false;
  	  
	     $.each(checkedItems, function(index, item){
	    	if(item.caseState==4){
	    		  names.push(item.loanId);
	    	 }else{
	    		 flag=true;
	    		$.messager.alert("提示","请勾选案件状态为“作业完成（未全部收回）的案件！");
	    		return false;
	    	 };
	       
	    });
	     if(flag){return false;}
	    if(names.length<=0){
	    	$.messager.alert("提示","请勾选案件状态为“作业完成（未全部收回）的案件！");
	    	return false;
	    } 
	 
 	   window.open(rayUseUrl+'CollectionManagerCases/Main/exportExcel/'+names, "_blank");
 	 
 	   return 
 	  
 	});
 	
 	
	$('#closedBt').click(		function() {
 		var checkedItems = $('#casesPageTb').datagrid('getChecked');
 	    var names = [];
 	    var flag=false;
 	   var closedType= $('#closedType').combobox('getValue');
 	     $.each(checkedItems, function(index, item){
 	    	 if(closedType==6){
 	    	 if(item.caseState==2||item.caseState==5){
 	    		 if(item.statue==150|| item.statue==130){
 	    			 names.push(item.id);
 	    		 }else{
 	    			flag=true;
 	 	    		$.messager.alert("提示","欠款尚未收回，不可做全部收回结案！");
 	 	    		return false;
 	    		 }
 	    		 
 	    	 }else{
 	    		 flag=true;
 	    		$.messager.alert("提示","请勾选案件状态为“未分派”、“作业完成（全部收回）的案件！");
 	    		return false;
 	    	 };
 	    	 }else if(closedType==7){
 	    		if(item.caseState==4){
 	 	    		  names.push(item.id);
 	 	    	 }else{
 	 	    		 flag=true;
 	 	    		$.messager.alert("提示","请勾选案件状态为“作业完成（未全部收回）”的案件！");
 	 	    		return false;
 	 	    	 }; 
 	    	 }
 	    });
 	     if(flag){return false;}
 	    if(closedType==6){ 
 	    	if(names.length<=0){
 	    		$.messager.alert("提示","请勾选案件状态为“未分派”、“作业完成（全部收回）的案件！");
 	    		return false;
 	    	} 
 	    } else if(closedType==7){
 	    	if(names.length<=0){
 	    		$.messager.alert("提示","“作业完成（未全部收回）的案件！");
 	    		return false;
 	    	} 
 	    }
 	   
 	  $.ajax({
          type: 'GET',
          url: 'CollectionManagerCases/Main/casesClosed/'+names+"/"+closedType,
          dataType: "json",
          success:function(data){
        	  $.messager.alert("提示","结案完成！");
        	  var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
        		 queryParams.personName = $('#personName').val();
        		 queryParams.idNum =$('#idNum').val() ;
        		 queryParams.productType =$('#loanType').combobox('getValue') ;
        		 queryParams.caseState =$('#caseState').combobox('getValue') ;
        		 queryParams.casesStartDate =$('#casesStartDate').val() ;
        		 queryParams.casesEndDate =$('#casesEndDate').val() ;
        		 queryParams.caseStateList =null ;
        		 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
        		 setFirstPage("#casesPageTb");
        		 $("#casesPageTb").datagrid('options').queryParams = queryParams;
        		 $("#casesPageTb").datagrid('reload'); 
          }
      });
 	   
 	  
 	});
    
 	
 	
});
function saveCases(){
	$.ajax({
		type : 'post',
		url : 'CollectionCreateCases/Main/casesAdd',
		data : $('#addCasesForm').serialize(),
		async : false,
		success : function(result) {
			if (result.success) {
				if(result.flag){
					parent.$.messager.alert('提示','提交成功！');
				}else{
					parent.$.messager.alert('提示','提交成功！');
				}
				
				$('#addCasesDlg').dialog('close');
				var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
				 queryParams.personName = $('#personName').val();
				 queryParams.idNum =$('#idNum').val() ;
			 
				 
				setFirstPage("#casesPageTb");
				 $("#casesPageTb").datagrid('options').queryParams = queryParams;
				 $("#casesPageTb").datagrid('reload'); 
			} else {
				$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
	
}
function  cencalRefuse(){
	$('#addCasesDlg').dialog('close');	
}


function selectOnChange(obj,value){
	if(value==1){
		$('#memo').text("A:连续3天未能联系到客户本人，且家人无代偿意愿的； "); 
	}else if(value==2){
		$('#memo').text("B:连续跟进15天，客户无还款记录亦无承诺还款的；"); 
	}else if(value==3){
		$('#memo').text("C:客户连续3次违背付款承诺，未依约定日期、金额还款的；"); 
	}else if(value==4){
		$('#memo').text("D:客户发生骗贷、逃匿、死亡、入狱、被执行、冒办、盗办等情形的；"); 
	}else if(value==5){
		$('#memo').text("E:未逾期案件（风险案件）提前移交 "); 
	} else if(value==6){
		$('#memo').text("F:(保障金保障金催收（助学贷逾期案件或其他涉及保障金垫付的案件）;"); 
	}  
};
function addCasesDlg(casesId,contractNo,loanId){
	var url="CollectionManagerCases/Main/casesManager?casesId="+casesId+"&loanId="+loanId;
	 window.open (rayUseUrl+url,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	 
	 
}
function formatRequestDate(value,row,index){
	 return getYMD(value);
}

  
function openWindos(id){
	 window.open (rayUseUrl+"audit/eduLoan/eduCreditAuditDetailsLApprove/"+id,"newwindow9","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
}
 

function getYMD(datetime){
	if(datetime==''||typeof(datetime) =="undefined"){
		return '';
	}
	return datetime.substr(0, 10);
}
function browse(loanId,productType, productId){
	
    if(productType == 1){
       if(5 ==  productId ||  productId == 6){
        //seLoanCityWideLoanDetail;
          doSeLoan(loanId,'apply/seLoanCityWideLoanDetail',loadExistedCityWideLoan);
        }else{
          doSeLoan(loanId);
        }
    }else if(productType == 2){
    	doCarLoan(loanId);
    }
};
//显示附件
 
//业务日志
function businessLogPage(id) {
	$('#businessLogDlg').dialog({
		title: '财务审核日志',
		width: 900,
		height: 300,
		closed: false,
		cache: false,
		modal: true
	});
	var url = 'audit/businessLog/detail.json/' + id;
	$('#business_log_result').datagrid({
		url: url,
		fitColumns: true,
		border: false,
		singleSelect:true,
		pagination: true,
		pageSize: 100,
		striped: true,
		rownumbers: true,
		nowrap:false,	
		columns: [[
		           {field: 'operator', title: '操作者', width: 50},
		           {field: 'flowStatusView', title: '环节', width: 60},
		           {field: 'createDate', title: '操作时间', width: 100},
		           {field: 'message', title: '日志内容', width: 300}
		]]
    });
	 // 设置分页控件
	 var p = $('#business_log_result').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
}
 
 
function formatEnumName(value,type){
	   var enumJsondata = eval("("+enumJson+")");   
	   try{
			var typeList = enumJsondata.dicData[type];
			if(typeList){
				for(var i = 0; i < typeList.length; i++){
					if(value == typeList[i].enumCode){
						return typeList[i].enumValue;
					}
				}
				return "";
			}else{
				return "";
			}
		}catch(e){
			alert("不存在数据字典对象!");
		}
	}
 
 
	 
 
function propertyChange(value){
	var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
	 queryParams.productType =$('#loanType').combobox('getValue') ;
	 queryParams.caseState =$('#caseState').combobox('getValue') ;
	 queryParams.casesStartDate =$('#casesStartDate').val() ;
	 queryParams.casesEndDate =$('#casesEndDate').val() ;
	 queryParams.caseStateList =null ;
	 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
	 setFirstPage("#casesPageTb");
	 $("#casesPageTb").datagrid('options').queryParams = queryParams;
	 $("#casesPageTb").datagrid('reload'); 
};
function inputChange(value){
	var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
	 queryParams.productType =$('#loanType').combobox('getValue') ;
	 queryParams.caseState =$('#caseState').combobox('getValue') ;
	 queryParams.casesStartDate =$('#casesStartDate').val() ;
	 queryParams.casesEndDate =$('#casesEndDate').val() ;
	 queryParams.caseStateList =null ;
	 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
	 setFirstPage("#casesPageTb");
	 $("#casesPageTb").datagrid('options').queryParams = queryParams;
	 $("#casesPageTb").datagrid('reload'); 
};
function search(){
	var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
	 queryParams.productType =$('#loanType').combobox('getValue') ;
	 queryParams.productId =$('#productTypeComb').combobox('getValue') ;
	 queryParams.caseState =$('#caseState').combobox('getValue') ;
	 queryParams.casesStartDate =$('#casesStartDate').val() ;
	 queryParams.casesEndDate =$('#casesEndDate').val() ;
	 queryParams.caseStateList =null ;
	 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
	 setFirstPage("#casesPageTb");
	 $("#casesPageTb").datagrid('options').queryParams = queryParams;
	 $("#casesPageTb").datagrid('reload'); 
}

