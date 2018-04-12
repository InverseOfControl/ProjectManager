var url;
$(function () {
	// 查询按钮
	$('#searchBt').bind('click', search);
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
	        //    userProductType = data[0].productType;
	        }
	    });
	   
		  $('#collectionUser').combobox({
		        url:'collectionTask/Main/getFirstTrials',
		        valueField:'id',
		        textField:'name',
		        onLoadSuccess:function(){
		            var data = $(this).combobox('getData');
		            if(data.length > 1)
		                 $(this).combobox('select', data[0].id);
		  
		        }
		  	  }); 
    $('#casesPageTb').datagrid({
        url: 'collectionTaskChange/Main/collectionTaskManagerList',
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
              			field : 'tid',
              			title : 'tid',
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
                				return value;
                			/*	return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="addCasesDlg('+row.id+ ',' +1 + ', '+row.loanId+','+row.tid+ ' )" >' + value + '</a>';*/
                	        	}
                        }, 
                        {
                			field : 'taskStartDate',
                			title : '作业分派日期',
                			width : 180,
                			formatter: formatRequestDate
                        }, 
                       
                		  {
                  			field : 'deptName',
                  			title : '营业部',
                  			width : 180,
                  			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                          },   
                          {
                  			field : 'productName',
                  			title : '产品类型',
                  			width : 180,
                  			formatter: function(value, row, index){
            					return value;
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
			title : '证件号码',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        } , {
			field : 'pactMoney',
			title : '合同金额',
			width : 180,
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	        	}
        } ,{
			field : 'time',
			title : '借款期限',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        } ,
        {
			field : 'overdueStartDate',
			title : '逾期起始日',
			width : 180,
			formatter: function(value, row, index){
	          	return value;
	        	}
        } ,  {
			field : 'overduePrincipalInterestSum',
			title : '逾期应还本息',
			width : 180,
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	          
	        	}
        },
        {
			field : 'fine',
			title : '应还罚息',
			width : 180,
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	        	}
        } ,
        {
			field : 'repayAllAmount',
			title : '应还总额',
			width : 180,
			formatter: function(value, row, index){
				if(value!=null){
					 return  value.toFixed(2);//保留两位小数
				}else{
					return value;
				}
	        	}
        } ,   {
			field : 'optName',
			title : '催收员',
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
 
	 //退回窗口点击取消
 
	 
	 $('#operatorId').combobox({
	        url:'CollectionManagerCases/Main/getTrialsCombox',
	        valueField:'id',
	        textField:'name',
	        onLoadSuccess:function(){
	            var data = $(this).combobox('getData');
	            if(data.length > 1)
	                 $(this).combobox('select', data[0].id);
	  
	        }
	             
	    });
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
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	  
 	$('#dispatchBt').click(		function() {
 		var checkedItems = $('#casesPageTb').datagrid('getChecked');
 	    var names = [];
 	    
 	  
 	     $.each(checkedItems, function(index, item){
 	    		  names.push(item.id+"-"+item.tid);
 	    });
 	    
 	    if(names.length<=0){
 	    	$.messager.alert("提示","请勾作业！");
	    	return false;
 	    } 
 	   var collectionUser= $('#collectionUser').combobox('getValue');
 	   if(collectionUser==null || collectionUser=="" ){
 		 	$.messager.alert("提示","请选择催收员！");
	    	return false;
 	   }
 	   
 	  $.ajax({
          type: 'GET',
          url: 'collectionTaskChange/Main/taskChange/'+names+"/"+collectionUser,
          data: names,
          dataType: "json",
          success:function(data){
        	  
        	  $.messager.alert("操作提示", "作业变更完成！", "info", function () {  
        		  search();  
	             });
        		
     	     
          }
      });
 	   
 	  
 	});

 	 
	   var d= document.getElementById("excelBt");
		 $('#excelBt').click(function(){
			 setButtonState( d, true,"处理中");
				var url="TaskAllocationRule/Main/exportExcel";
				var urlaj="TaskAllocationRule/Main/checkExportNum";
				url=rayUseUrl+url;
					
			$.ajax({
				  	 url : urlaj,	  
				  	 type:"POST",
				  	async: false ,
				  	 success : function(result){
					 	  if(result=="success"){
					 		 document.getElementById("casesForm").action=url;
								//$("#detailsForm").submit();
					 		winOpen = window.open(url, "_blank");
					 		timer=window.setInterval("IfWindowClosed('excelBt')",500);
					 	  }else{
							 $.messager.show({
									title : '提示',
									msg : result
							});
							 setButtonState( d, false,"导出");
					  	 }		    			
			  			 
				   }
				});	 
					 
					 

		
			 	 
		
			 	
				}); 
			$('#impBt').click(function(){
				
				$('#excelDlg').dialog({
					title: ' ',
					width : 550,
					height : 400,
					href : "TaskAllocationRule/Main/exportExcelUI",
					closed: false,
					cache: false,
					modal: true,
					buttons : [
								{
									text : "导  入",
									handler : function(
											e) {
										$('#excelImp').form(																		
														{
															onSubmit : function() {
														 	 		$.messager.progress({
														 	 			text:'加载中...'
														 	 		 
														 	 			});
															},
															success : function(data) {
																var obj = eval("("+ data+ ")");
																if(obj.issu=="0"){
																	$.messager.progress('close');
																	$.messager.alert('提示',obj.msg);
																	$('#excelDlg').dialog('close');	
																}
																if(obj.issu=="1"){
																	$.messager.progress('close');
																	$.messager.alert("操作提示", obj.msg, "info", function () {  
														        		  search();  
															             });
														        		
																	$('#excelDlg').dialog('close');	
																}
																if(obj.issu=="2"){
																	$.messager.progress('close');
																	$.messager.alert('提示',"文件格式错误");
																	$('#excelDlg').dialog('close');	
																}
															}
														}); 
										 
									  	
										$('#excelImp').submit();
										 
									    	
									 
										 
									}
								},
								{
									text : "取 消",
									handler : function(
											e) {
										$('#excelDlg').dialog('close');	
									}
								} ]
				});
					
			});
 	
});
function saveCases(){
	$.ajax({
		type : 'post',
		url : 'collectionTask/Main/casesAdd',
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
function addCasesDlg(casesId,contractNo,loanId,tid){
	var url="collectionTask/Main/casesManager?casesId="+casesId+"&loanId="+loanId+"&tid="+tid;
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
		fit:true,
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
 
 
	 
 

function search(){
	var queryParams = $('#casesPageTb').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
	 queryParams.productType =$('#loanType').combobox('getValue') ;
	 queryParams.overdueStart =$('#overdueStart').val() ;
	 queryParams.overdueEnd =$('#overdueEnd').val() ;
	 queryParams.caseStateList =null ;operatorId
	 queryParams.operatorId =$('#operatorId').combobox('getValue') ; 
	 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
	 setFirstPage("#casesPageTb");
	 $("#casesPageTb").datagrid('options').queryParams = queryParams;
	 $("#casesPageTb").datagrid('reload'); 
}

