<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/collectionTask/caseInfo.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<script type="text/javascript" charset="utf-8">
function exportExcel(){
	var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/findCountByParams?selectedProductId=';
	var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/exportExcel?selectedProductId=';
	var params='';
	var  personIdnum=null;
	var  personName=null;
	var  selectedProductId=null;
	var  status=null;
	var  contractConfirmStartDate=null;
	var  contractConfirmEndDate=null;
		personName = $('#personName').val();
		personIdnum = $('#personIdnum').val();
		contractConfirmStartDate = $('#contractConfirmStartDate').val();
		contractConfirmEndDate = $('#contractConfirmEndDate').val();
		var contractSrc =0;
		if($('#contractSrc').combobox('getValue')=="all"){
			 contractSrc = 0;
	    }else{
		   	 contractSrc = $('#contractSrc').combobox('getValue');
	    }
	   
		if($('#loanType').combobox('getValue')=="all"){
			selectedProductId =null;
	    }else{
	    	selectedProductId= $('#loanType').combobox('getValue');
	    	params+=selectedProductId;
	    }
		if($('#loanStatus').combobox('getValue')=="all"){
			status =0;
	    }else{
	    	status = $('#loanStatus').combobox('getValue');
	    }	    
		params+="&status="+status;
		if(personName!=''){
			//对汉字进行编码设置 ，服务端同样需要进行编码设置
			params+='&personName='+encodeURI(encodeURI(personName));
		}
		if(personIdnum!=''){
			params+='&personIdnum='+personIdnum;
		}
		if(contractSrc!=''||contractSrc==0){
			params+='&contractSrc='+contractSrc;
		}		
		if(contractConfirmStartDate!=''){
			params+='&contractConfirmStartDate='+contractConfirmStartDate;
		}
		if(contractConfirmEndDate!=''){
			params+='&contractConfirmEndDate='+contractConfirmEndDate;
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
  			$('#checkPageTb').datagrid('reload');
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
	<!--<table id="casesPageTb" toolbar="#toolbar"></table>-->
	<span  style="display:none;"> 
	<input type="hidden" id="optFlag" name="optFlag" value="${optFlag!""}"  />	
   <input type="hidden" id="personId" name="personId" value="${cc.personId!""}"  />	
   <input type="hidden" id="loanId" name="loanId" value="${cc.id}"  />	
    <input type="hidden" id="caseState" name="caseState" value="${cc.caseState}"  />	
    <input type="hidden" id="statue" name="statue" value="${cc.status}"  />
    <input type="hidden" id="id" name="id" value="${cc.caseId}"  />	
      <input type="hidden" id="tid" name="tid" value="${tid}"  />	
       <input type="hidden" id="taskState"   value="${task.taskState}"  />		
         <input type="hidden" id="userType"   value="${userType}"  />		
     </span> 
	 <div id="caseInfoTab" class="easyui-panel" title="作业基本信息" style="width:100;">
                    <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
                        <tr>
                            <td><label>案件编号:${info.caseNum!""} </label></td>
                            <td><label>案件创建日期:<#if info.createdTime??>${info.createdTime?string("yyyy/MM/dd")}</#if></label></td>
                           
                            <td><label><!--	案件状态:
                            			 <#if info.caseState??>
		  									<#if (info.caseState=="1")>
		  									客服催收中
		  									<#elseif (info.caseState=="2")>
		  									未分派
		  									<#elseif (info.caseState=="3")>
		  									部门催收中
		  									<#elseif (info.caseState=="4")>
		  									作业完成_未全部收回
		  									<#elseif (info.caseState=="5")>
		  									作业完成_全部收回 
		  									 <#elseif (info.caseState=="6")>
		  									结案_全部收回
		  									<#elseif (info.caseState=="7")>
		  									结案_坏账
		  									</#if>
		  								</#if>-->
		  								作业状态:
                            			 <#if task.taskState??>
		  									<#if (task.taskState==1)>
		  						 				进行中
		  									<#elseif (task.taskState==2)>
		  									正常移交
		  									<#elseif (task.taskState==3)>
		  									异常移交
		  									<#elseif (task.taskState==4)>
		  									作业完成_未全部收回
		  									<#elseif (task.taskState==5)>
		  									作业完成_全部收回 
		  									 
		  									</#if>
		  								</#if>
                            </label></td>
                          
                            <td><label>案件来源: 
                            		 <#if info.caseFrom??>
                            		 		<#if (info.caseFrom==1)>
		  									系统生成
		  									<#elseif (info.caseFrom==2)>
		  									手工创建
                            			  </#if>
                            		 </#if>
                            </label></td>
                          
                        </tr>
                       <tr>
                            <td><label>借款类型:${info.productName!""}</label></td>
                           
                            <td><label>借款日期:<#if info.requestDate??>${info.requestDate?string("yyyy/MM/dd")}</#if></label></td>
                           
                            <td><label>借款期限:${info.time!""}</label></td>
                           
                            <td><label>合同金额:<#if info.pactMoney??>${info.pactMoney?string('0.00')}</#if></label></td>
                           
                        </tr>
                         <tr>
                           <td><label>作业开始时间:<#if task.taskStartDate??>${task.taskStartDate?string("yyyy-MM-dd HH:mm:ss")} </#if></label></td>
                           <td><label>作业结束时间:<#if task.taskEndDate??>${task.taskEndDate?string("yyyy-MM-dd HH:mm:ss")} </#if></label></td>
                           <td><label>催收人员:${task.optName!""}</label></td>
                           <td><label>作业类型: 
                            			<#if task.taskType??>
                            				
                            		 		<#if (task.taskType==1)>
		  									客服催收
		  									<#elseif (task.taskType==2)>
		  									部门催收
		  								 
                            				</#if>
                            		 </#if>
                            </label></td>
                        </tr>
                        
                    </table>
         </div>
          <div id="loanInfoTab" class="easyui-panel" title="欠款信息" style="width:100;">
                    <table style="font-size:15px; width:100%; text-align:left;" cellspacing="4">
                        <tr  >
                            <td><label>逾期起始日:
                            
                            	<#if rd??>	<#if rd.overdueStartDate??>${rd.overdueStartDate} </#if>	 </#if></label></td>
                        
                            <td><label>逾期本息和:<#if rd??>	<#if rd.overduePrincipalInterestSum??>${rd.overduePrincipalInterestSum?string('0.00')}<#else>0.00</#if><#else>0.00 </#if></label></td>
                          
                            <td><label>罚息金额 :<#if rd??><#if rd.fine??>${rd.fine?string('0.00')} <#else>0.00</#if><#else>0.00</#if></label></td>
                           
                            <td><label>欠款总额:<#if (cc.status==140)>
                            			   <#if rd.repayAmount??>${rd.repayAmount?string('0.00')} </#if>	
                            			   <#else>
                            			    <#if rd.repayAllAmount??>${rd.repayAllAmount?string('0.00')} </#if>	
                            		</#if></label></td>
                            
                        </tr>
                    </table>
         </div>
          <div   id="contactPersonTab" class="easyui-panel" title="相关人信息" style="width:100;"></div>
          	
                <table id="personPageTb"  ></table>
                 <div   style="text-align:center;"  > 
      	  	 		 <a class="easyui-linkbutton"    id="contracterSaveBt" iconCls="icon-ok"  onclick="toAddContracterUI();" >新增联系人</a>
    		</div>   
          <div   id="telTab" class="easyui-panel" title="电催记录" style="width:100;"></div>
                <table id="telPageTb"  ></table>   
                 <div   style="text-align:center;"  > 
      	  	 		 <a class="easyui-linkbutton"    id="personSaveBt" iconCls="icon-ok"  onclick="toRecordUi(1);" >新增电催</a>
    		</div>	
          <div   id="visitTab" class="easyui-panel" title="外访记录          <a onclick='toImgShow();' >附件</a>" style="width:100;"></div>
          		<table id="visitTb"  ></table>   
          		  <div   style="text-align:center;"  > 
          		 <a class="easyui-linkbutton"   id="personSaveBt" iconCls="icon-ok"  onclick="toRecordUi(2);" >新增外访</a>  
          </div>			
          		
          		   
      	  <div   id="caseOptTab" class="easyui-panel" title="作业操作信息" style="width:100;">
      	 		 
                        <form id="taskTable">
                          <span  style="display:none;"> 
                        	<input type="hidden" id="caseId" name="caseId" value="${cc.caseId}"  />	
                           <input type="hidden" id="tid1" name="id" value="${tid}"  />	
                         </span>
                        <table style="font-size:15px; width:100%; text-align:left;" cellspacing="0">
      	 			 		 <tr  >
                            <td style="  width:15%; "><label>客户承诺还款金额
                            		
                            </label></td> 
                              <td><label>
                             <input name="promisedRepayMent" id="promisedRepayMent" type="text"  precision="2"  value="${task.promisedRepayMent!""}" class="easyui-numberbox"  maxlength="10" size="10" style="text-align:right;" />
                               </label></td>
                             <td style="  width:5%; "><label>客户实际还款金额
                             		
                             </label></td>
                            
                            <td><label>  
                            	<input name="factRepayment" id="factRepayment" type="text" value="${task.factRepayment!""}"  precision="2" class="easyui-numberbox"  maxlength="10" size="10" style="text-align:right;" />
                               
                               	 
                                </label></td>
                       </tr>
      	 			 		<tr>
                            <td  style="  width:5%; "><label> 作业备注</label ></td>
                            <td align="left"  colspan="10"> 
                            	 <textarea  name="taskMemo" id="teskMemo" rows="5"  cols="115" class="text" maxlength="1000">${task.taskMemo!""}</textarea>
                            </td>
                            
                   		  </tr>
                   		 
                  </table > 
                  </form>
                 <div align=center>	
                 		 <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        				<a href="javascript:void(0)" id="saveBt" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存作业信息</a> 
	        			 <label>
               	              催收结果： <select  id="closedType" name="closedType" editable="false" class="easyui-combobox" data-options="width:140,valueField: 'value',textField: 'text' "> 
               	             
            	 					 
            					 </select>
                               </label>
	        				<a href="javascript:void(0)" id="closedBt" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交催收结果</a> 
						</div>	
                  </div>
      	 </div>
      	 <div id="contactPersonDlg" style="top: 20px;height:600px;width:900px;"></div>   
      	   <div id="recordDlg" style="top: 20px;height:600px;width:900px;"></div>  
      	   <div id="addContactPersonDlg" style="top: 20px;height:600px;width:900px;"></div>    
<script>	
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                
               
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
                 //IE也能用textarea 
$("textarea[maxlength]").keyup(function(){ 
var area=$(this); 
var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
if(max>0){ 
if(area.val().length>max){ //textarea的文本长度大于maxlength 
area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
} 
} 
}); 
//复制的字符处理问题 
$("textarea[maxlength]").blur(function(){ 
var area=$(this); 
var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值 
if(max>0){ 
if(area.val().length>max){ //textarea的文本长度大于maxlength 
area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值 
} 
} 
}); 
               
            })
</script>
</body>
</html>