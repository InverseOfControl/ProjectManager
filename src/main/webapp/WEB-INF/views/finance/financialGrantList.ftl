<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript">var userType='${userType}';</script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/finance/financialGrant.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<script type="text/javascript" charset="utf-8">
function exportExcel(){
	var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'financialGrant/checkExportNum?productId=';
	var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'financialGrant/export?productId=';
	
	var params='';
	var  productId=null;
	var  status=null;
	var personName = $('#personName').val();
	var personIdnum = $('#personIdnum').val();
	var financeGrantTimeStart = $('#financeGrantTimeStart').val();
	var financeGrantTimeEnd = $('#financeGrantTimeEnd').val();
		var contractSrc =0;
		if($('#contractSrcComb').combobox('getValue')=="all"){
			 contractSrc = 0;
	    }else{
		   	 contractSrc = $('#contractSrcComb').combobox('getValue');
	    }

		if($('#productComb').combobox('getValue')=="全部"){
			productId =null;
	    }else{
	    	productId= $('#productComb').combobox('getValue');
	    	params+=productId;
	    }

		if($('#status').combobox('getValue')=="all"){
			status =0;
	    }else{
	    	status = $('#status').combobox('getValue');
	    }	    
		params+="&status="+status;
		if(personName!=''){
			//对汉字进行编码设置 ，服务端同样需要进行编码设置
			params+='&personName='+encodeURI(encodeURI(personName));
		}
		if(personIdnum!=''){
			params+='&personIdnum='+personIdnum;
		}
		if(financeGrantTimeStart!=''){
			params+='&financeGrantTimeStart='+financeGrantTimeStart;
		}
		if(financeGrantTimeEnd!=''){
			params+='&financeGrantTimeEnd='+financeGrantTimeEnd;
		}
		url+=params;
		url2+=params;

		$.ajax({
	  	 url : url,	  
	  	 type:"POST",
	  	 success : function(result){
		 	  if(result=="success"){
		 	 	 self.location.href=url2;			
		 	  }else{
				 $.messager.show({
						title : '提示',
						msg : result
				});
		  	 }		    			
  			$('#list_result').datagrid('reload');
	   }
	});	
}
 </script>
<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#refuseReasonForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #refuseReasonForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:left;
            
            
        }
        #refuseReasonForm table tr td:nth-child(even){
            background: #FFFFFF;
        }
</style>
</head>
<body>
	<table id="list_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<span>姓名：</span><input type="text" id="personName" maxlength="25"  style="width: 90px" />  
		<span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" style="width: 200px" />  
		<span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:120"/>  
     	<span>状态：</span>
     	<select  id="status"  editable="false" style="width:108px" class="easyui-combobox">
        			<option value="all">全部</option>
     				<option value="110" selected = "selected">财务放款</option>
     				<option value="120">财务放款退回</option>   
        			<option value="130">正常</option>
          </select>     	 
     	 <span>财务放款日期：</span>
     	 <input id="financeGrantTimeStart" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"/>
  		 <span>到：</span>
     	 <input id="financeGrantTimeEnd" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"/>
     	 <span>合同来源：</span>
     	 <select id="contractSrcComb"  editable="false" style="width:108px" class="easyui-combobox">
     	 			<option value="all">全部</option>  
        			<option value="1">证大P2P</option>
        			<option value="2">证大爱特</option>
         </select>     	     
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a href="javascript:void(0)" id="exportExcelBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出</a>
			<#--<#if userType ==8 >
				<a href="javascript:void(0)" id="batchMakeLoansBt" class="easyui-linkbutton" data-options="iconCls:'icon-add'">批量放款</a>
			</#if>-->
		</div>		
	</div>
	<div id="dlg" class="easyui-dialog" style="top: 150px;width: 750px;" closed="true">  	 	
		<form id="refuseReasonForm" method="post">	
		 		 <input type="hidden" id ="loanId" name="loanId" />  
				<table id="refuseTb" style="font-size:12px; width:100%;text-align:left; "> 
					<tr>
	    				<td>
							<label style="margin-right:50px">退回门店</label>
						</td>
				    </tr>
				    <tr>
				    	<td>	
		    				<label>一级原因</label>
		    				<input id="reason1" name="reason1" class="easyui-combobox" editable="false" required="true" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
		    			    <label>二级原因</label>
		    			    <input id="reason2" name="reason2"  class="easyui-combobox" editable="false" required="true" data-options="width:200" />
		    				
	    				</td>
    				</tr>
    				<tr>
	    				<td><label style="margin-right:50px">备注</label></td>
    				</tr>
    				<tr>
	    				<td ><textarea id="reason" name="reason" rows="10" cols="100" required="true" maxlength="390"/></textarea></td>
    				</tr>
    				
    				<tr>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>	
	    					<a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			
		</form>
	</div>
	<!-- 财务放款日志对话框 -->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>
<script>	
           $(function(){
           		
           		
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>
</body>
</html>