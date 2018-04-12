var url;
$(function () {
	// 查询按钮
	$('#searchBt').bind('click', search);
	getTextResource([10,40]);
	$('#searchBt').linkbutton({    
		text: textResourceMap.get('btnSearch40') 
	}); 
	$('#toolbar #salesDeptId').combobox({     
		  url:'apply/getCurSalesDept',
		    valueField:'id',
		    textField:'name',
		    onLoadSuccess:function(){
	        	var data = $(this).combobox('getData');
	        	if(data.length>0)
	        		$(this).combobox('select', data[0].id);
	        }
	}); 
	
   
    $('#toolbar #productType').combobox({
        url:'apply/getProductTypeRemovemallBusiness',
        valueField:'id',
        textField:'productName',
        onLoadSuccess:function(){
            var data = $(this).combobox('getData');
            if(data.length>0)
        		$(this).combobox('select', data[0].id);
        }
    }); 
    //展期期次
    $('#toolbar #extensionTimeComb').combobox({
        url:'apply/getExtensionTimeList',
        valueField:'value',
        textField:'name',
        onLoadSuccess:function(){
             var data = $(this).combobox('getData');
                $(this).combobox('select', data[0].value);
            
        }
    });
    // 借款状态
    $('#toolbar #status').combobox({
    	url:'apply/getLoanStatusList',
    	valueField:'value' ,
    	textField:'name',
    	 onLoadSuccess:function(){
    		  var data = $(this).combobox('getData');    		
    	       $(this).combobox('select',data[0].value);
    	 }
    });
    // 获取所有客户经理
	 
	// 获取该营业店所有客户经理列表
	$("#toolbar #crmId").combobox({
		url:'after/repayInAdvance/getCrmList',
		valueField:'id',
		textField:'name',
		onLoadSuccess:function(){    
	  		  var data = $(this).combobox('getData');    		
	  	       $(this).combobox('select',data[0].id);
		}
	});
    $('#list_result').datagrid({
        url: 'comprehensiveSearch/getComprehensiveSearchList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:10,     
        rownumbers : true,
        checkOnSelect:true,
        columns : [ [ 
                     {
             			field : 'loanId',
             			title : 'loanId',
             			width : 180,
             			hidden:true,
             			formatter: function(value, row, index){
             	          	return value;
             	        	}
                     }, 
                      {
                			field : 'status',
                			title : 'status',
                			width : 180,
                			hidden:true,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                      {
                			field : 'personName',
                			title : textResourceMap.get('name10') ,
                			width : 180,
                			formatter: function(value, row, index){

                  	          	return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="openTab(\''+value+'\','+row.loanId+','+row.personId+','+row.extenId+','+row.extensionTime+')" >' + value + '</a>';
                  	        	}
                        }, 
                        {
                			field : 'productName',
                			title : '产品类型',
                			width : 180,
                			formatter: showExtensionLoanLedgerApply2
                        }, 
                        {
                			field : 'crmName',
                			title : '客户经理',
                			width : 180,
                			formatter: function(value, row, index){
                  	          	return value;
                  	        	}
                			 
                		},
                		  {
                  			field : 'serviceName',
                  			title : '客服',
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
                    			 
                            }, 
                            {
                    			field : 'professionType',
                    			title : '职业类型',
                    			width : 180,
                    			formatter: function(value, row, index){
                      	          	return value;
                      	        	}
                    		}, {
                    			field : 'purpose',
                    			title : '用途',
                    			width : 180,
                    			formatter: function(value, row, index){
                      	          	return value;
                      	        	}
                    			 
                    		}, 
                    		{
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
                          },     	{
                    			field : 'auditMoney',
                      			title : '审批金额',
                      			width : 180,
                      			formatter: function(value, row, index){
                      				if(value!=null){
                      					 return  value.toFixed(2);//保留两位小数
                      				}else{
                      					return value;
                      				}
                      	        	}
                              },   {
                        			field : 'time',
                          			title : '期限',
                          			width : 180,
                          			formatter: function(value, row, index){
                          	          	return value;
                          	        	}
                                  },{
                                	field : 'extensionTime',
                          			title : '展期期次',
                          			formatter : function(value, row, index) {
                          				if(value == 0) {
                          					return "无";
                          				} else {
                          					return value;
                          				}
                          			},
                          			width : 60
                                }, {
                        			field : 'statusStr',
                          			title : '状态',
                          			width : 180,
                          			formatter: function(value, row, index){
                          	          	return value;
                          	        	}
                                  }, 
                                  
                                  
                                  
        
		 
		] ]
	});
    // 设置分页控件
   var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
 
 
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
	 
	 $('#resetBt').click(function() {
		   $('#personName').val("");
		   $('#idNum').val("") ;
		   $('#tel').val("") ;
		   $('#status').combobox('select',0) ;
		   $('#crmId').combobox('select',0) ;
		   $('#productType').combobox('select',null) ;
		   $('#salesDeptId').combobox('select',null) ;
		 
	 });
	 
 
    
 	
 	
});
function showExtensionLoanLedgerApply2(value,row,index){
	
	return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.loanId+','+row.extenId+','+row.extensionTime+')">'+row.productName+'</a>';
	 
	
 
}
function showLoanLedger(id,extenId,extensionTime){
	 var h = $(window).height() ;
 $('#showLoanLedgerDlg').dialog({
		title : '借款台账',
		inline: true,
		closed : false,
		cache : false,
		maximizable: true,
		height: h*0.8
 });
 	var url = 'repaymentPlan/repaymentPlanLedgerExtensionList/' + extenId;
	if(extensionTime==0)
	{
		url = 'repaymentPlan/repaymentPlanLedgerList/' + id;
	}
	 
	
	$('#loanLedgerList').datagrid({
		url : url,
		fitColumns : false,
		border : false,
		pagination : false,
		fit:true,
		striped : true,
		rownumbers : true,
		columns : [ [ 
		{
			field : 'curNum',
			title : '期数',
			width : 50
		}, {
			field : 'repayDay',
			title : '应还款日',
			width : 80,
			formatter : function(value, row, index) {
				if(value != null){
					return value.substring(0,10);
				}else{
					return value;
				}
			}
		}, {
			field : 'factReturnDate',
			title : '实际还款日',
			width : 80,
			formatter : function(value, row, index) {
				if(value != null){
					return value.substring(0,10);
				}else{
					return value;
				}
			}
		}, {
			field : 'repayAmount',
			title : '还款金额',
			width : 100,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'oneTimeRepaymentAmount',
			title : '当期一次性还款金额',
			width : 150,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'status',
			title : '当期还款状态',
			width : 100,
			formatter : function(value, row, index) {
					return formatEnumName(value,'REPAYMENT_PLAN_STATE');
			}
		} ,
		{
			field : 'deficit',
			title : '当期剩余还款',
			width : 150,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
					}else{
						return value;
					}
			}
		} ,
		{
			field : 'name',
			title : '还款方',
			width : 200,
			formatter : function(value, row, index) {
				if(row.orgRepayTerm!=null){
					if(row.curNum<=row.orgRepayTerm ){
						return row.accountName;
					}else{
						return value;
					}
				}else{
					return value;
				}
			}
		} 		
		] ]
	});
}
function formatRequestDate(value,row,index){
	 return getYMD(value);
}
function openTab(personName,loanId,personId,extenId,extensionTime){
	var title=personName+" - 借款详细信息";
	var jq = top.jQuery;    
	if (typeof(extenId) == "undefined") { 
		extenId=null;
		}
	var content ='<iframe src="comprehensiveSearch/openTabMain?loanId='+loanId+'&extenId='+extenId+'&extensionTime='+extensionTime+'" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
	if (jq("#centerTabs").tabs('exists', title)) {
		jq("#centerTabs").tabs('select', title);
	}else{
		jq("#centerTabs").tabs('add',{
		    title:personName+" - 借款详细信息",
		    content:content ,
		    closable:true
		   });		
	};
	 
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
 
//显示附件
  
 
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
	if($.trim($('#personName').val())=="" && $.trim($('#idNum').val())==""){
		$.messager.alert("提示","请输入姓名或身份证号！");
		return false;
	}
	
	var queryParams = $('#list_result').datagrid('options').queryParams; 
	 queryParams.personName = $('#personName').val();
	 queryParams.idNum =$('#idNum').val() ;
	 queryParams.tel =$('#tel').val() ;
	 queryParams.status =$('#status').combobox('getValue') ;
	 queryParams.crmId =$('#crmId').combobox('getValue') ;
	 queryParams.productType =$('#productType').combobox('getValue') ;
	 queryParams.salesDeptId =$('#salesDeptId').combobox('getValue');
	 queryParams.extensionTime =$('#extensionTimeComb').combobox('getValue');
	 setFirstPage("#list_result");
	 $("#list_result").datagrid('options').queryParams = queryParams;
	 $("#list_result").datagrid('reload'); 
}

