$(function (){
	// 查询按钮
	 $('#matchingBt').click(function(){
		 window.open (appname+"intrenalMatching/init?loanId="+loanId, "newwindowinter","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
		});
	 $('#honorBt').click(function(){
		var loanStatus = $('#loanStatus').val();
		var personName=$('#personName').val();
		personName=encodeURI(personName);
		personName=encodeURI(personName);
		var toUrl;
		if(loanStatus>=130){
			  toUrl="NFCS/creditReportAfter/userName/"+ personName +"/idnum/"+ $('#idnum').val()+"/charSet/utf-8";
		}else{
			  toUrl="NFCS/creditReport/userName/"+ personName +"/idnum/"+ $('#idnum').val()+"/charSet/utf-8";
		}
	 	
		 window.open(honorUrl+toUrl, "honorurlWindow","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
		});
	 
	 
	var personId=$('#personId').val();
	var loanId2=$('#loanId').val();
	 $('#loanHistoryTab').datagrid({
	        url: 'intrenalMatching/loanHistoryList/'+personId+'?loanId='+loanId2,
	    	fitColumns : true,
	        border : false,
	        pagination : true,
	       
	        striped: true,
	        pageSize:10,     
	        rownumbers : true,
	        columns : [ [       
	                     {
	             			field : 'id',
	             			title : 'id',
	             			width : 90,
	             			hidden:true,
	             			formatter: function(value, row, index){
	             	          	return value;
	             	        	}
	                     }, 
	                     {
	              			field : 'personName',
	              			title : '姓名',
	              			width : 90,
	              			formatter: function(value, row, index){
	              				return '<a style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="browse('+row.id+ ',' + row.productType + ', '+row.productId+' )" >' + value + '</a>';
	              	        	}
	                      }, 
	                      {
	          				field : 'productName',
	          				title : '产品类型',
	          				width : 90,
	          				formatter: showExtensionLoanLedgerApply1
	          			},
	         {
				field : 'idNum',
				title : '身份证号',
				width : 90,
				formatter: function(value, row, index){
		          	return value;
		        	}
	        }, 
			  
			{
				field : 'companyName',
				title : '企业名称',
				width : 90,
				formatter: function(value, row, index){
		          	//return  value;
		        	return value;
		        	}
			}, {
				field : 'guaranteeName',
				title : '担保人',
				width : 90,
				formatter: function(value, row, index){
						return value;
		          }
			}, {
				field : 'organName',
				width : 90,
				title : '收单机构',	formatter: function(value, row, index){
					 
						return value;
				 
		          }
			}, {
				field : 'curDebtbalancePerson',
				title : '个人负债余额',
				width : 90,
				formatter: function(value, row, index){
					 
		          }
			}, {
				field : 'curDebtbalanceCompany',
				width : 90,
				title : '企业负债余额',formatter: function(value, row, index){
						return value;
	        	}
			} ,{
				field : 'status',
				width : 90,
				title : '状态',formatter: function(value, row, index){
					return   formatEnumName(value,'LOAN_STATUS');
				}	
			} ,
			 {
				field : 'approvalConclusion',
				width : 180,
				title : '审批结论',formatter: function(value, row, index){
						return value;
	        	}
			} ,
			 {
				field : 'requestDate',
				width : 90,
				title : '申请日期',formatter:formatRequestDate
			} ,
			 {
				field : 'salesDept',
				width : 90,
				title : '网点',formatter: function(value, row, index){
						return value;
	        	}
			} ,
			{	
				field : 'option',
				title : '信息查看',
				width : 90,
				formatter : function(value, row, rowIndex) {	
					var link = "<a href='javascript:businessLogPage(" + row.id + ");'>日志</a>";
					 
					return  link;
				},width : 90
			}
			] ]
		});
	 $('#matchingTab').datagrid({
	        url: 'intrenalMatching/matchingList?personId='+personId+'&loanId='+loanId2,
	    	fitColumns : true,
	        border : false,
	        pagination : true,
	    
	        striped: true,
	        pageSize:10,     
	        rownumbers : true,
	        frozenColumns: [[    
	                         /*{
	 	             			field : 'loanId',
	 	             			width : 180,
	 	             			hidden:true,
	 	             			formatter: function(value, row, index){
	 	             	          	return value;
	 	             	        	}
	 	                     },*/
                             { title: '匹配号码',field : 'matchIngPhone',  width: 100, sortable: true,
	 	                    	formatter: function(value, row, index){
	 	             	          	return value;
	 	                    	}}    
                         ]],   
	        columns : [ [
	                      {
	                    	  	 
	                    	  	title : '申请人信息',
	                    	  	colspan:2,
	                    	   
	                        }, 
	                        {
	                    	  	 
	                    	  	title : '被匹配人信息',
	                    	  	colspan:7,
	                    	 
	                    	  
	                        },
	                        {	field : 'option',
	            				title : '信息查看',
	            				width : 90,
	            				rowspan:2,
	            				formatter : function(value, row, rowIndex) {	
	            					var link = "";
	            					if(row.productId==8){
	            						link += "<a href='javascript:openWindos(\""+row.loanId+"\")' >查看</a>";	
	            					}else {
	            						link += "<a href='javascript:openWindoz(\""+row.idNum+"\")' >查看</a>";	
	            						}
	             				return  link;
	            				}
	            			}
	                        ],
	                     
	                 [ 
	                    {
	              			field : 'requestDataOption',
	              			title : '数据项',
	              			rowspan:1,
	              			width : 90,
	              			formatter: function(value, row, index){
 	             	          	return value;
 	                    	}
	                      }, 
	                      {
	          				field : 'requestCorrespondingMsg',
	          				title : '对应信息',
	          				width : 90,
	          				rowspan:1,
	          				formatter: function(value, row, index){
	          		         	return  value;
	          		          }
	          			},
	         {
				field : 'matchingDataOption',
				title : '数据项',
				width : 90,
				rowspan:1,
				formatter: function(value, row, index){
		          	return value;
		        	}
	        }, 
			  
			{
				field : 'matchingCorrespondingMsg',
				title : '对应信息',
				width : 90,
				rowspan:1,
				formatter: function(value, row, index){
		          	//return  value;
		        	return value;
		        	}
			}, {
				field : 'matchingPerson',
				title : '被匹配客户',
				width : 90,
				rowspan:1,
				formatter: function(value, row, index){
					return '<a style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="browse('+row.loanId+ ',' + row.productType + ', '+row.productId+' )" >' + value + '</a>';
		          }
			}, {
				field : 'matchingRequestDate',
				width : 90,
				rowspan:1,
				title : '被匹配客户申请时间',	formatter: formatRequestDate
			}, {
				field : 'status',
				title : '申请状态',
				width : 90,
				rowspan:1,
				formatter: function(value, row, index){
						   
							   return   formatEnumName(value,'LOAN_STATUS');
						  
				}	
			}, {
				field : 'approvalConclusion',
				width : 180,
				rowspan:1,
				title : '原因',formatter: function(value, row, index){
					if(row.status!=40){
						if(row.requestMoney!=null){
						 return row.requestMoney.toFixed(2);
						}
					   }else{
							return value;
					   }
					
	        	}
			} ,{
				field : 'productName',
				width : 90,
				title : '贷款产品',
				rowspan:1,
				formatter: function(value, row, index){
					return value;
        	}
			} 
			  
			
			] ]
		});
	    // 设置分页控件
	   var p = $('#loanHistoryTab').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
	    var p = $('#matchingTab').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
	    
});

 
	 
	function browse(loanId,productType, productId){
	    if(productType == 1){
	       if(5 ==  productId ||  productId == 6){
	        //seLoanCityWideLoanDetail;
	          doSeLoan(loanId,'apply/seLoanCityWideLoanDetail',loadExistedCityWideLoan);
	        }else{
	          doSeLoan(loanId);
	        }
	    }else if(productType == 2){
	    	var isf="0";
	    	 $.ajax({
	 	        url : 'RefusalEntry/isRefusal?loanId='+loanId,
	 	        data : $("#addCarLoanForm").serialize(),
	 	        type:"POST",
	 	        async: false,
	 	        success : function(result){
	 	            if(result=="1"){
	 	            	isf="1";
	 	            	  
	 	            }
	 	        }
	 	 });
	    	doCarLoan(loanId,isf);
	    }
	};
 
	function showExtensionLoanLedgerApply1(value,row,index){
		 
		 
			return '<a style="font-weight:bolder;color:blue;" href="javascript:void(0)" onclick="showLoanLedger('+row.id+')">'+row.productName+'</a>';
		 
		 
	}
	function showLoanLedger(id){
		 $('#showLoanLedgerDlg').dialog({
				title : '借款台账',
				inline: true,
				closed : false,
				cache : false,
				maximizable: true,
		 });
			var url = 'repaymentPlan/repaymentPlanLedgerList/' + id;
			$('#loanLedgerList').datagrid({
				url : url,
				fitColumns : false,
				border : false,
				pagination : false,
				 
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
	function businessLogPage(id) {
	    $('#businessLogDlg').dialog({
	        title: '借款日志',
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
	        pageSize: 10,
	        striped: true,
	    
	        rownumbers: true,
	        nowrap:false,
	        columns: [
	            [
	                {
	                    field: 'operator',
	                    title: '操作者',
	                    width: 50
	                },
	                {
	                    field: 'flowStatusView',
	                    title: '环节',
	                    width: 60
	                },
	                {
	                    field: 'createDate',
	                    title: '操作时间',
	                    width: 100
	                },
	                {
	                    field: 'message',
	                    title: '日志内容',
	                    width: 300 
	                }
	            ]
	        ]
	    });
	    // 设置分页控件
	    var p = $('#business_log_result').datagrid('getPager');
	    $(p).pagination({
	        pageList: [ 10, 20, 50 ]
	    });
	}
	function openWindos(id){
		window.open (rayUseUrl+"audit/eduLoan/eduCreditAuditDetails/"+id,"newwindowinter2","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}
	
	function openWindoz(idNum){
		window.open (rayUseUrl+"audit/auditMain?idnum="+idNum,"newwindowinter2","toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=3");
	}