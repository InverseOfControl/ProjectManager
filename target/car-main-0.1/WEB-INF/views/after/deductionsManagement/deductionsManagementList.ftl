<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script> 
  <script type="text/javascript">var userType='${userType}';
  var code='${code}';
  var financeGroup = '${financeGroup}'
  </script> 
  <script type="text/javascript">var startDate='${startDate}';</script> 
  <script type="text/javascript">var endDate='${endDate}';</script> 
 <script type="text/javascript" charset="utf-8">
 var hasIfEditCarLoan=${ifEditCarLoan};
function exportExcel(){
	
		var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'after/deductionsManagement/findCountByParams?';
		var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'after/deductionsManagement/exportExcel?';
		var params='';
		
		var  sendDateStart=null;
		var  sendDateEnd=null;
		var  personName= $('#personName').val();
		var  personIdnum = $('#personIdnum').val();
		var hasGuarantee =null;
		var returnCode=null;
		var status=0;
		var bankName = $('#bankName').val(); 
		var failReasonType = $('#failReasonType').val(); 
		if ($('#hasGuarantee').combobox('getValue') == "all") {//是否担保
			hasGuarantee = 0;
		} else {
			hasGuarantee = $('#hasGuarantee').combobox('getValue');
		}
		if ($('#deductionsStatus').combobox('getValue') == "all") {//划扣状态
			status = 0;
		} else {
			status = $('#deductionsStatus').combobox('getValue');
		}
		if ($('#returnCode').combobox('getValue') == "all") {//划扣结果
			returnCode = 'all';
		}else if($('#returnCode').combobox('getValue') == "default"){
			returnCode = 'default';
		}else {
			returnCode = $('#returnCode').combobox('getValue');
		}
		var productId = $('#toolbar #productComb').combobox('getValue');
		 if($('#toolbar #productComb').combobox('getValue')==''||$('#toolbar #productComb').combobox('getValue')==null){
			productId = 0;
		 }
		 var offerType=null;
		if ($('#offerType').combobox('getValue') == "all") {//报盘类型
			offerType = 99;
		} else {
			offerType = $('#offerType').combobox(
					'getValue');
		}
	
	   //生成时间
		var createdTimeStart = null;
		var createdTimeEnd = null;
		createdTimeStart = $('#createdTimeStart').val();
		createdTimeEnd = $('#createdTimeEnd').val();
		sendDateStart=$('#sendDateStart').val();
		sendDateEnd=$('#sendDateEnd').val();
		params+="personName="+encodeURI(encodeURI(personName))+"&personIdnum="+personIdnum+"&hasGuarantee="+hasGuarantee
		+"&status="+status+"&returnCode="+returnCode+"&productId="+productId+"&bankName="+encodeURI(encodeURI(bankName))
		+"&failReasonType="+encodeURI(encodeURI(failReasonType))+"&offerType="+offerType
		;
		if(sendDateStart!=''){
			params+="&sendDateStart="+sendDateStart
			if(sendDateEnd!=''){
			params+="&sendDateEnd="+sendDateEnd;
			}
		}
		if(createdTimeStart!=''){
		  params+="&createdTimeStart="+createdTimeStart;
		}
		if(createdTimeEnd!=''){
		  params+="&createdTimeEnd="+createdTimeEnd;
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
	   },  
	    error:function(data){	   
	 		 $.messager.show({
				title: 'warning',
				msg: data.responseText
			});
	   }
	});	
}


function exportExcelFail(){
	var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'after/deductionsManagement/findFailCountByParams?';
		var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'after/deductionsManagement/exportFailExcel?';
		var params='returnCode=111111';
		  //生成时间
		var createdTimeStart = null;
		var createdTimeEnd = null;
		createdTimeStart = $('#createdTimeStart').val();
		createdTimeEnd = $('#createdTimeEnd').val();
		if(createdTimeStart!=''){
		  params+="&createdTimeStart="+createdTimeStart;
		}
		if(createdTimeEnd!=''){
		  params+="&createdTimeEnd="+createdTimeEnd;
		}
		url +=params;
		url2 +=params;
		
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
	   },  
	    error:function(data){	   
	 		 $.messager.show({
				title: 'warning',
				msg: data.responseText
			});
	   }
	});	
};
 </script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/deductionsManagement/deductionsManagement.js"></script>
<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#offerAmountForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #offerAmountForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            
            
        }
        #offerAmountForm table tr td:nth-child(even){
            background: #FFFFFF;
        }	
        #guarateeAmountForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #guarateeAmountForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            
            
        }
        #guarateeAmountForm table tr td:nth-child(even){
            background: #FFFFFF;
        }	
</style>
</head>
<body>
	<table id="list_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:auto;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<span>姓名：</span><input  id="personName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
		<span>身份证号：</span><input  id="personIdnum" maxlength="18" style="width: 180px" class="easyui-validatebox" />  
		<span>开户行：</span><input  id="bankName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/> 		  	 
     	 <span>生成时间：</span>
     	 <input type="text" id="createdTimeStart" onfocus="WdatePicker()" class="Wdate" style="width:140px"/>
     	 		 &nbsp;—&nbsp;
     	 <input type="text" id="createdTimeEnd" onfocus="WdatePicker()" class="Wdate" style="width:140px"/>
     	 <span>报盘时间：</span>
     	 <input type="text" id="sendDateStart" onfocus="WdatePicker()" class="Wdate" style="width:140px"/>
     	 		 &nbsp;—&nbsp;
     	 <input type="text" id="sendDateEnd" onfocus="WdatePicker()" class="Wdate" style="width:140px"/>
     	   </br> 
     	  <span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:140"/>
     	 
     	 <span>划扣结果：</span>
		<select  id="returnCode"  editable="false" class="easyui-combobox" data-options="width:80">  
		 		<option value="all">全部</option>	
		 		<option value="default">默认</option>	
        		<option value="000000">成功</option>
                <option value="111111">失败</option>	     
                <option value="444444">部分还款</option>	            
        </select>
         <span>划扣渠道：</span>
		<select  id="tppType"  editable="false" class="easyui-combobox" data-options="width:80">  
		        <option value="0">全部</option>     
		 		<option value="10">通联</option>     				        			
				<option value="20">富友</option> 
				<option value="30">银联</option> 
				<option value="40">快捷通</option> 	         
        </select>
     	<span>担保代扣：</span>
     	<select  id="hasGuarantee"  editable="false" style="width:108px" class="easyui-combobox">
                    <option value="all">全部</option>	
        			<option value="1" >有担保</option>
        			<option value="2">无担保</option>                   
          </select>  
          <span>划扣状态：</span>
     	<select  id="deductionsStatus"  editable="false" style="width:108px" class="easyui-combobox">
     				<option value="all">全部</option>
     				<option value="10">未报盘</option>     				        			
        			<option value="20">已报盘</option> 
        			<option value="30">已回盘</option> 	
        			<option value="40">失败</option> 
          </select>   
       <span>报盘类型：</span>
     	<select  id="offerType"  editable="false" style="width:108px" class="easyui-combobox">
     				<option value="all">全部</option>
     				<option value="0">定时报盘</option>     				        			
        			<option value="1">实时报盘</option> 
          </select>   
          <span>失败原因：</span><input  id="failReasonType" maxlength="28" style="width: 180px" class="easyui-validatebox" />  
          <span>城市：</span><input id="cityComb" class="easyui-combobox"  editable="false" data-options="width:100"/>  
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			 <#if ifEditCarLoan> 
			<a href="javascript:void(0)" id="immediateBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">实时划扣</a> 
			</#if>
			<span id="export">
			<a href="javascript:void(0)" id="exportExcelBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">划扣结果导出</a> 
			</span>
			
			<span id="exportFail">
			<a href="javascript:void(0)" id="exportExcelFailBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">划扣失败导出</a> 
			</span>
			
			<span id="changeTpp">
			<a href="javascript:void(0)" id="changeTppBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'">变更划扣渠道</a> 
			</span>
			<span id="editMoney">
			<a href="javascript:void(0)" id="toEditMoney" class="easyui-linkbutton" data-options="iconCls:'icon-save'">修改金额</a> 
			</span>
			<span id="guarantee">
			<a href="javascript:void(0)" id="guaranteeButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'">担保代扣</a> 
			</span>

		</div>		
	</div>
	<!-- 修改金额弹出 窗口-->
	<div id="moneyDialog" class="easyui-dialog" style="top: 150px;width: 350px;height:250px" closed="true">  	 	
		<form id="offerAmountForm" method="post">	
		 		 <input type="hidden" id ="id" name="id" />  
		 		 <input type="hidden" id ="loanId" name="loanId" />  		 		 
				<table id="refuseTb" style="font-size:12px; width:100%;"> 
					<tr>
	    				<td>
							<label id="">姓名</label>
						</td>
						<td>
							<label id="name"></label>
						</td>
				    </tr>
				    <tr>
	    				<td>
							<label id="">应报金额</label>
						</td>
						<td>
							<label id="receivableAmount"></label>
						</td>
				    </tr>
    				<tr>
	    				<td>
							<label id="">实报金额</label>
						</td>
						<td>
							<input id="offerAmount" required="true" maxlength="10"  class="easyui-validatebox"></input>
						</td>
				    </tr>
    				
    				<tr>
    				<td></td>
	    				<td>
	    					<a href="javascript:void(0)" id="offerAmountSubmitBt" class="easyui-linkbutton" >提交</a>	
	    					<a href="javascript:void(0)" id="offerAmountCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			
		</form>
	</div>
		<!-- 修改金额弹出 窗口-->
	<div id="changeTppDialog" class="easyui-dialog" style="top: 150px;width: 350px;height:100px" closed="true">  	 	
		<form id="changeTppForm" method="post">	
				<table id="changeTppTb" style="font-size:12px; width:100%;"> 
    				<tr align="center">
	    				<td>
							<label id="">划扣渠道</label>
						</td>
						<td>
							<select  id="tppTypeChange"  editable="false" style="width:108px" class="easyui-combobox">
				     				<option value="10">通联</option>     				        			
				        			<option value="20">富友</option> 
				        			<option value="30">银联</option> 
				        			<option value="40">快捷通</option> 	
				          </select>   
						</td>
				    </tr>
    				
    				<tr>
	    				<td colspan=2 align="center">
	    					<a href="javascript:void(0)" id="changeTppSubmitBt" class="easyui-linkbutton" >提交</a>	
	    					<a href="javascript:void(0)" id="changeTppCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			
		</form>
	</div>
	<!-- 担保代扣弹出 窗口-->
	<div id="guarateeDialog" class="easyui-dialog" style="top: 150px;width: 350px;height:350px" closed="true">  	 	
		<form id="guarateeAmountForm" method="post">	
		 		 <input type="hidden" id ="id" name="id" /> 
		 		  <input type="hidden" id ="isGuarantee" name="isGuarantee" /> 
		 		  <input type="hidden" id ="guaranteeName" name="guaranteeName" /> 
		 		  <input type="hidden" id="guarateeBankNameInput" ></input>
		 		  <input type="hidden" id="guarateeAccountInput"  ></input> 
				<table id="refuseTb" style="font-size:12px; width:100%;"> 
					<tr>
	    				<td>
							<label id="">姓名</label>
						</td>
						<td>
							<label id="personName2"></label>
						</td>
				    </tr>
				    <tr>
	    				<td>
							<label id="">开户行</label>
						</td>
						<td>
							<label id="personBankName"></label>
						</td>
				    </tr>
				     <tr>
	    				<td>
							<label id="">账号</label>
						</td>
						<td>
							<label id="personBankAccount"></label>
						</td>
				    </tr>
    				<tr>
	    				<td>
							<label id="">变更为:</label>
						</td>
						<td>							
						</td>
				    </tr>
    				<tr>
	    				<td>
							<label id="">姓名</label>
						</td>
						<td>
							<input id="guarateeName"  class="easyui-combobox"  style="width:140px"></input>
						</td>
				    </tr>
				    <tr>
	    				<td>
							<label id="">开户行</label>
						</td>
						<td>
							<label id="guarateeBankName"></label>
						</td>
				    </tr>
				    <tr>
	    				<td>
							<label id="">账号</label>
						</td>
						<td>
						   <label id="guarateeAccount"></label>							
						</td>
				    </tr>
    				<tr>
    				    <td></td>
	    				<td>
	    					<a href="javascript:void(0)" id="guarateeSubmitBt" class="easyui-linkbutton" >提交</a>	
	    					<a href="javascript:void(0)" id="guarateeCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			
		</form>
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