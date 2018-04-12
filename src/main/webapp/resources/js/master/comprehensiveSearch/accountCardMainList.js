var url;
$(function () {
    $('#list_result').datagrid({
        url: 'comprehensiveSearch/accountCardMainList?loanId='+ $('#loanId').val()+'&extensionTime='+ $('#extensionTime').val() + '&extenId='+ $('#extenId').val(),
        fitColumns : true,
        border : false,
        striped: true,
        fit:true,
        rownumbers : true,
        columns : [ [ 
                      {
                			field : 'tradeTime',
                			title : '交易日期',
                			width : 100,
                			formatter:formatRequestDate
                        }, 
                      {
                			field : 'payType',
                			title : '交易方式',
                			width : 100,
                			formatter: function(value, row, index){
                				return formatEnumName(value,'TRADE_TYPE');
              	        	}
                       }, 
                        {
                			field : 'tradeKind',
                			title : '交易类型',
                			width : 100,
                			formatter: function(value, row, index){
                   			 		if(row.tradeCode =="4001" || row.tradeCode =="4002" ){
                   			 			return "账号放款";
                   			 		}else{
                   			 			return "账号还款";
                   			 		}
              	        	}
                        }, 
                        {
                			field : 'initialBalance',
                			title : '期初余额（元）',
                			width : 100,
                			formatter: function(value, row, index){
                				if(value!=null){
                 					 return  value.toFixed(2);//保留两位小数
                 				}else{
                 					return value;
                 				}
                  	        }
                		},
                		{
                  			field : 'income',
                  			title : '收入（元）',
                  			width : 100,
                  			formatter: function(value, row, index){
                  				if(value!=null){
                					 return  value.toFixed(2);//保留两位小数
                				}else{
                					return value;
                				}
                  	        	}
                  			 
                          },   
                          {
                    			field : 'expenditure',
                    			title : '支出（元）',
                    			width : 100,
                    			formatter: function(value, row, index){
                    				if(value!=null){
                   					 return  value.toFixed(2);//保留两位小数
                   				}else{
                   					return value;
                   				}
                      	        	}
                    			 
                            }, 
                            {
                    			field : 'endingBalance',
                    			title : '期末余额（元）',
                    			width : 100,
                    			formatter: function(value, row, index){
                    				if(value!=null){
                   					 return  value.toFixed(2);//保留两位小数
                   				}else{
                   					return value;
                   				}
                      	        	}
                    		} ,
                    		   {
                    			field : 'remark',
                    			title : '备注',
                    			width : 180,
                    			formatter: function(value, row, index){
                    				 
                   					return value;
                   			 
                      	        	}
                    		} ,   {
                    			field : 'accountId',
                    			width : 180,
                    			formatter : accountCardDetailsFormat
                    		} 
                    		 
		 
		] ]
	});

    
    
    
    
    
 	
});
 


function accountCardDetailsFormat(value,row,index){
	 
	 
	return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="accountCardDetails(\''+row.tradeNo+'\','+row.accountId+','+row.payType+')" >' + "查看明细" + '</a>';
 
 
}
function accountCardDetails(tradeNo,id,payType){
 $('#accountCardDetailsDlg').dialog({
		title : '帐卡详细信息',
		inline: true,
		closed : false,
		cache : false,
		maximizable: true,
 });
	var url = 'comprehensiveSearch/accountCardFlow?loanId='+id+'&tradeNo='+tradeNo+'&payType='+payType;
	$('#accountCardDetailsList').datagrid({
		url : url,
		fitColumns : false,
		border : false,
		pagination : false,
		striped : true,
		fit:true,
		rownumbers : true,
		columns : [ [ 
		{
			field : 'tradeTime',
			title : '明细生成日期',
			width : 160,
			formatter:formatRequestDate
		}, {
			field : 'accountTitle',
			title : '明细项目',
			width : 160,
			formatter : function(value, row, index) {
				return formatEnumNameAgain(value,'ACCOUNT_TITLE',row.remark);
			}
		}, {
			field : 'term',
			title : '期数',
			width : 160,
			formatter : function(value, row, index) {
					return value;
				 
			}
		}, {
			field : 'tradeAmount',
			title : '金额',
			width : 160,
			formatter : function(value, row, index) {
				if(value != null){
					return value.toFixed(2);
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
 
function formatEnumNameAgain(value, type, remark) {
	var enumJsondata = eval("(" + enumJson + ")");
	try {
		var typeList = enumJsondata.dicData[type];
		if (typeList) {
			for (var i = 0; i < typeList.length; i++) {
				if (value == typeList[i].enumCode) {
					if (remark!=null && remark.indexOf("罚息减免") >= 0) {
						return "罚息减免";
					} else {
						return typeList[i].enumValue;
					}
				}
			}
			return "";
		} else {
			return "";
		}
	} catch (e) {
		alert("不存在数据字典对象!");
	}
}
	 
   

