var url;
$(function () {
	 
	
	 
	
    
    $('#list_result').datagrid({
        url: 'comprehensiveSearch/repaymentPLanList?loanId='+ $('#loanId').val()+'&extenId='+$('#extenId').val(),
        fitColumns : true,
        border : false,
        striped: true,
        fit:true,
        rownumbers : true,
        columns : [ [ 
                     
                      {
                			field : 'curNum',
                			title : '期数',
                			width : 180,
                			formatter: function(value, row, index){
                	          	return value;
                	        	}
                        }, 
                      {
                			field : 'repayDay',
                			title : '应还款日',
                			width : 180,
                			formatter: formatRequestDate

                				 
                        }, 
                        {
                			field : 'factReturnDate',
                			title : '实际还款日',
                			width : 180,
                			formatter: formatRequestDate
                        }, 
                        {
                			field : 'repayAmount',
                			title : '还款金额',
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
                  			field : 'oneTimeRepaymentAmount',
                  			title : '当期一次性还款金额',
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
                    			field : 'status',
                    			title : '当期还款状态',
                    			width : 100,
                    			formatter: function(value, row, index){
                    			 
                    				 			
                    					return formatEnumName(value,'REPAYMENT_PLAN_STATE');
                    				 
                   				 
                      	        	}
                    			 
                            }, 
                            {
                    			field : 'deficit',
                    			title : '当期剩余欠款',
                    			width : 180,
                    			formatter: function(value, row, index){
                    				if(value!=null){
                   					 return  value.toFixed(2);//保留两位小数
                   				}else{
                   					return value;
                   				}
                      	        	}
                    		} 
                    		 
		 
		] ]
	});
   
 
 
 	
});
 
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
 
 
	 
   

