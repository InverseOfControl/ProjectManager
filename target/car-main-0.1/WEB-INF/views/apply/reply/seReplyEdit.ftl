 
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/replyDataList.js"></script>
 
    <form id="replySeEditForm" name="replySeEditForm" method="post" >
    <div id="productTableDiv" style=" closed="true" data-options="resizable:true">
	    <table style="font-size:15px; width:100%; text-align:right;height: 100%;padding:20px">
	    <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>方案编号 ：</label> 
	        </td>
	        <td align="left"> 
	        
	        		${check.code!""}
        		 
	        </td>
	        <td> 
        		 <label>方案名称 ：</label> 
	        </td>
	        <td align="left"> 
        		 <label> ${check.name!""}</label> 
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>借款类型 ：</label> 
        		 
	        </td>
	        <td align="left"> 
        		 
        	<#if check.planType??>    
        		 <#if check.planType = "00">   
 					助学贷 
				</#if>   
	
        	</#if> 	 
	        </td>
	        <td> 
        		 <label>所属机构 ：</label> 
	        </td>
	        <td align="left"> 
        		 <label> ${check.orgName!""}</label> 
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>开售日期 ：</label> 
	        </td>
	        <td align="left"> 
	        	<span id="spanStartDate">
        		 <#if check.startDate??> 
						${check.startDate?string("yyyy-MM-dd") }
				</#if>
				</span>        
	        </td>
	        <td> 
        		 <label>停售日期 ：</label> 
	        </td>
	        <td align="left">
	        	<#if check.endDate??> 
						${check.endDate?string("yyyy-MM-dd") }
				</#if>          		 
	        </td>
	    </tr>
	       <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>申请金额 ：</label> 
	        </td>
	        <td align="left"> 
        	 
        		 <label><#if check.requestMoney??> ${check.requestMoney?string('0.00')}</#if></label> 
        		 
	        </td>
	        <td> 
        		 <label>借款期数：</label> 
	        </td>
	        <td align="left"> 
        		 <label> ${check.time!""}</label> 
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>合同金额 ：</label> 
	        </td>
	        <td align="left"> 
        		  <label><#if check.pactMoney??> ${check.pactMoney?string('0.00')}</#if></label> 
	        </td>
	        <td> 
        		 <label>保证金金额：</label> 
	        </td>
	        <td align="left"> 
        		  <label><#if check.margin??> ${check.margin?string('0.00')}</#if></label> 
        		 
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>机构承担服务费 ：</label> 
	        </td>
	        <td align="left"> 
	      		  <#if check.orgFeeState??>
	      		  		 <#if check.orgFeeState = "00">   
 							承担
						  </#if>  
						   <#if check.orgFeeState = "01">   
 							不承担
						  </#if>   
	      		  	
	      		  </#if>
        		 </label> 
	        </td>
	        <td> 
        		 <label>机构还款期数：</label> 
	        </td>
	        <td align="left"> 
        		 <label> ${check.orgRepayTerm!""}</label> 
	        </td>
	    </tr>
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>月综合费率 ：</label> 
	        </td>
	        <td align="left"> 
        		 
        		 <label> 
        		 	 <#if check.actualRate??>
        		  		 ${check.actualRate?c}
        		  	 </#if>
        		</label> 
	        </td>
	        <td> 
        		 <label>还款方式：</label> 
	        </td>
	        <td align="left"> 
	      	  <#if check.returnType??>    
        		 <#if check.returnType = 0>   
 					前低后高
				</#if>   
				 <#if check.returnType = 1>   
 					等额本息
				</#if>   
        	</#if> 
        		 
	        </td>
	    </tr>
	       <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>分段还款明细：</label> 
	        	</td>
	     		 <td> 
	     		 <table>
	     		 	<tr style="width:200px;text-align:center">
	     		 		<td style="width:100px">还款段次</td>
	     		 		<td style="width:100px">期数</td>
	     		 		<td style="width:100px">金额</td>
	     		 	</tr>
	     		 	<tr  style="width:200px;text-align:center">
	     		 		<td>1</td>
	     		 		<td>${check.toTerm1!""}</td>
	     		 		<td> <#if check.returneterm1??> ${check.returneterm1?string('0.00')}</#if></td>
	     		 	</tr>
	     		 	<tr  style="width:200px;text-align:center">
	     		 		<td>2</td>
	     		 		<td>${check.toTerm2!""}</td>
	     		 		<td>   <#if check.returneterm2??> ${check.returneterm2?string('0.00')}</#if></td>
	     		 	</tr>
	     		 </table>
	     		  </td>
	     </tr>
	       <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>备注 ：</label> 
	        </td>
	        <td align="left"> 
        		 <label> ${check.memo!""}</label> 
	        </td>
	     </tr>  
	      <tr style="height:40px" >
	        <td style="width:200px" > 
        		 <label>批复意见：</label> 
	        </td>
	        <td align="left"> 
        		 <label>
        		 	
        		 	<label><input   name="approverState" type="radio" value="3"   />同意 </label> 
					 <label><input   name="approverState" type="radio" value="4"    checked/>退回</label>
        		 </label> 
	        </td>
	     </tr>
	        <tr style="height:40px" >
	         
	         <td style="width:200px" > 
        		 <label><span id="spantext">退回原因</span></label> 
	        </td>
	        <td align="left"> 
        		 <label>
        		  <textarea  name="sendBackMemo" id="sendBackMemo" rows="5" cols="30" class="text" maxlenght="85"></textarea>
        		 </label> 
	        </td>
	     </tr>
	     
	    <!--other rates -->
	      
	    </table>
    </div>
    <input id="id" name="id" value="${check.id}"   type="hidden"> 
    </form>
    <div id="addCarlg-buttons" style="text-align:center;"  > 
      	  <a class="easyui-linkbutton" id="saveButton" iconCls="icon-ok" plain="true"  >保存</a>
      	  <a class="easyui-linkbutton" id="refuseCancelBt" iconCls="icon-cancel" plain="true" onclick="cencalRefuse();" >返回</a>
    </div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
               
                 $("#refuseCancelBt").bind('click',cencalRefuse);
  
                 
                $('#saveButton').click(
					function() {
				var checkStatus=$('input[name="approverState"]:checked ').val();
				var sendBackMemo=$('#sendBackMemo').val();
	if(checkStatus==4){
	  if(sendBackMemo==null || sendBackMemo==''){
		  $.messager.show({
				title: '提示',
				msg: '请输入退回原因！'
			});
		   return false;
	  }
	}
	 
	 var text=$("#spanStartDate").html();
	 var termind =$('input:radio[name="approverState"]:checked').val(); 
	 var str;
	 str="";
	 if(termind=="3"){ 
	 		 str="提交后该方案启用，开售时间为"+text;
	 	}else{
	 		 str="";
	 	}
	 $.messager.confirm('确认对话框', '确定要提交吗？'+str, function(r) {
		if (r) {
			doSaveEditReply();
		} else {
			return false;
		}
	}); 
					});
			$("input:radio[name='approverState']").change(function (){
					var termind =$('input:radio[name="approverState"]:checked').val(); 
	 	if(termind=="3"){ 
	 		document.getElementById("spantext").innerHTML ="备注";
	 	}else{ 
	 		document.getElementById("spantext").innerHTML ="退回原因";
	 	} 
				});
					
		 
            });
         
</script>		
 