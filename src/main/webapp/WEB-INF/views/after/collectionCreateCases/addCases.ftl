
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
    <form id="addCasesForm" name="addCasesForm" method="post" >
    	
    <div id="productTableDiv"   closed="true" data-options="resizable:true">
	     <input  name="loanId" id="loanId"  type="hidden"   value="${id}"/> 
	      <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
                        <tr style="height:30px" >
                            <td><label>借款人</label></td>
                            <td align="left"><label  >${loan.personName!""}</label></td>
                            <td><label>身份证号</label</td>
                            <td align="left"><label>${loan.idNum!""}</label></td>
                            <td><label>借款类型</label></td>
                            <td align="left"><label>${loan.productTypeName!""}</label></td>
                             <td><label>借款状态</label></td>
                            <td align="left"><label >${loan.statusName!""}</label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr style="height:30px;border-bottom:1px solid blue" >
                            <td><label>签约日期</label></td>
                            <td align="left"><label  >
                            		 <#if loan.signDate??>
  										${loan.signDate?string('yyyy-MM-dd')}
  					 
  					 				 </#if>	
                           		</label></td>
                            <td><label>放款日期</label></td>
                            <td align="left"><label > 
                            		<#if loan.grantDate??>
  										${loan.grantDate?string('yyyy-MM-dd')}
  					 
  					 				 </#if>
                            </label></td>
                            <td><label>借款期限</label></td>
                            <td align="left"><label  >${loan.time!""}</label></td>
                             <td><label>首次还款日</label></td>
                            <td align="left"><label >
                            			<#if loan.startRepayDate??>
  										${loan.startRepayDate?string('yyyy-MM-dd')}
  					 
  					 				 </#if>
                            </label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                       
                      
                        
	    </table> 
	    <div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	     <span>逾期日期:</span><input id="overdueDateStr" name="overdueDateStr" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
	   		<span >移交代码:</span><select  id="transferType"  name="transferType"	editable="false" class="easyui-combobox" data-options="width:80,onChange: function(newValue, oldValue){selectOnChange(this,newValue)}">  
               						 <option value=1>A</option>	
               						 <option value=2>B</option>  
                                     <option value=3>C</option>	 
                                     <option value=4>D</option>
                                     <option value=5>E</option>
                                     <option value=6>F</option>
              				  </select>
              <br/>
              <br/>
              <div  style="vertical-align:middle;height:76px;">    
                <span style="display: inline-block; height:76px; line-height:76px;">备注:&nbsp;&nbsp;
                		   <textarea  name="caseMemo" id="caseMemo" rows="5"  cols="100" class="text" maxlenght="85"></textarea>			
              </span>	
               
       		 
        </div>  
      		 <div id="addCarlg-buttons" style="text-align:center;"  > 
      	  <a class="easyui-linkbutton" id="saveCasesButton" iconCls="icon-ok" plain="true" onclick="saveCases();" >保存</a>
      	  <a class="easyui-linkbutton"   iconCls="icon-cancel" plain="true" onclick="cencalRefuse();" >返回</a>
    	</div>	  
    </div> 
    </form>
   			<span id="memo">A:连续3天未能联系到客户本人，且家人无代偿意愿的 </span>
      	
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
               
  
                 
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
 