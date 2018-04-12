
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />

    <div id="productTableDiv"   closed="true" data-options="resizable:true">
    
    	  <form id="recordTb" name="recordTb"> 
    	   <#if (type=="1")>
	   	   <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
				<tr  >
                            <td style="  width:7%; "><label>电催对象 
                            		
                            </label></td> 
                              <td><label>
                             <span  style="display:none;"> 
                             <input type="hidden"   name="recordType"  value="1" />
                             <input type="hidden"  name="taskId" id="taskId1"	/>
                             <input type="hidden"  id="recordName" name="recordName"  value="${re.recordName!""}"/>
                             <input type="hidden"    name="id" id="recordId"  value='${re.id!""}'/>
                              </span>
                          	 <select  id="recordName1"  name="recordName1"  editable="false" class="easyui-combobox" data-options="width:120,valueField: 'text',textField: 'text',
                          	 		onSelect: function(rec){
                          	 			 $('#recordName').val(rec.name);
                          	 			 var url='collectionTask/Main/getTelList?id='+ rec.id+'&part='+rec.part+'&subordinate='+rec.subordinate+'&type='+1
                          	 		      $('#recordTel').combobox('clear');
                          	 		     $('#recordTel').combobox({
	       											 url: url,
	       											 valueField:'mobilePhone',
	       											 textField:'mobilePhone',
	       											 onLoadSuccess:function(){
	           											 var data = $(this).combobox('getData');
	           												 if(data.length > 0){
	              												   $(this).combobox('select', data[0].mobilePhone);
	  														}
	       															 }
	       										  });
                          	 		}
                          	 
                          	 
                          	 " >  
                           			
                           
                              </select>
                             <span style="display:none;"> 
                               <select     id="ff" name="ff" editable="false" class="easyui-combobox" data-options="width:120, 
                           					onLoadSuccess: function(){
                           					  var personId=$('#personId').val();
											  var loanId=$('#loanId').val();
                           					  var url='collectionTask/Main/getPersonCombobox?personId='+ personId+'&loanId='+loanId
                           							 $('#recordName1').combobox({
	       											 url: url,
	       											 valueField:'name',
	       											 textField:'name',
	       											 value:'${re.recordName!""}',
	       										  });

	       										
	       										 	 $('#taskId1').val(	 $('#tid').val());
	  	
                           					}
                           				" >
                           			         </select>
                           			        </span 	>
                               </label></td>
                             <td style="  width:7%; "><label>拨打电话
                             		
                             </label></td>
                            
                            <td><label>  
                             	 <select  id="recordTel"  name="recordTel"  editable="false" class="easyui-combobox" data-options="width:120,valueField: 'mobilePhone',textField: 'mobilePhone', value:'${re.recordTel!""}'">
                           		   </select> 
                                </label></td>
                               
                               <td style="  width:7%; "><label>起止时间
                             </label></td>
                            
                            <td><label>  
                           		<input  id="recordStartDateStr" name="recordStartDateStr" style="width: 180px" class="Wdate easyui-validatebox" value='${re.recordStartDateStr!""}' type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>-<input  id="recordEndDateStr"  name="recordEndDateStr" style="width: 180px" class="Wdate easyui-validatebox"  value='${re.recordEndDateStr!""}' type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                </label></td> 
                       </tr>
      	 			 		<tr>
                            <td  style="  width:7%; "><label>电催内容 </label ></td>
                            <td align="left"  colspan="10"> 
                            	 <textarea  name="recordContent" id="recordContent" rows="5"  cols="115" class="text"  maxlength="1000">${re.recordContent!""}</textarea>
                            </td>
                            
                   		  </tr>	    			
	    			
	    			
	      </table>
	    </#if>
	     <#if (type=="2")>
	    		 <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
				<tr  >
                            <td style="  width:7%; "><label>外访对象 
                            		
                            </label></td> 
                              <td><label>
                              <span  style="display:none;"> 
                             <input type="hidden"  name="recordType"  value="2"/>
                             <input type="hidden"  name="taskId" id="taskId1"	/>
                              <input type="hidden"    name="id" id="recordId"  value='${re.id!""}'/>
                               <input type="hidden"  id="recordName" name="recordName"  value="${re.recordName!""}"/>
                                   <input type="hidden"    name="id" id="id"  value='${re.id!""}'/>
                                    </span>
                          	 <select  id="recordName1"  name="recordName1"  editable="false" class="easyui-combobox" data-options="width:120, 
                          	 		onSelect: function(rec){
                          	 			 $('#recordName').val(rec.name);
                          	 		  var url='collectionTask/Main/getTelList?id='+ rec.id+'&part='+rec.part+'&subordinate='+rec.subordinate+'&type='+2
                          	 		    $('#recordAddress').combobox('clear');
                          	 		     $('#recordAddress').combobox({
	       											 url: url,
	       											 valueField:'address',
	       											 textField:'address',
	       											 onLoadSuccess:function(){
	           											 var data = $(this).combobox('getData');
	           												 if(data.length > 0)
	              												   $(this).combobox('select', data[0].address);
	  
	       															 }
	       										  });
                          	 		}
                          	 " >  
                           			
                           
                              </select>
                             <span style="display:none;"> 
                               <select     id="ff" name="ff" editable="false" class="easyui-combobox" data-options="width:120,valueField: 'value',textField: 'text' ,
                           					onLoadSuccess: function(){
                           					  var personId=$('#personId').val();
											  var loanId=$('#loanId').val();
                           					  var url='collectionTask/Main/getPersonCombobox?personId='+ personId+'&loanId='+loanId
                           							 $('#recordName1').combobox({
	       											 url: url,
	       											 valueField:'name',
	       											 textField:'name',
	       											  value:'${re.recordName!""}',
	       										  });
	       										 	 $('#taskId1').val(	 $('#tid').val());
	  	
                           					}
                           				" >
                           			         </select>
                           			        </span 	>
                               </label></td>
                             <td style="  width:7%; "><label>外访地址
                             		
                             </label></td>
                            
                            <td><label>  
                            	   <select  id="recordAddress"  name="recordAddress"  editable="false" class="easyui-combobox" data-options="width:120,valueField: 'address',textField: 'address', value:'${re.recordAddress!""}'">
                           		   </select> 
                            	  
                                </label></td>
                               
                               <td style="  width:7%; "><label>起止时间
                             </label></td>
                            
                            <td><label>  
                           		<input  id="recordStartDateStr" name="recordStartDateStr" value='${re.recordStartDateStr!""}'  style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> 
      						  - 
      			  				<input  id="recordEndDateStr"  name="recordEndDateStr" value='${re.recordEndDateStr!""}' style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                </label></td> 
                       </tr>
      	 			 		<tr>
                            <td  style="  width:7%; "><label>外访内容 </label ></td>
                            <td align="left"  colspan="10"> 
                            	 <textarea  name="recordContent" id="recordContent" rows="5"  cols="115" class="text" maxlength="1000">${re.recordContent!""}</textarea>
                            </td>
                            
                   		  </tr>	    			
	    			
	    			
	      </table>
	      </#if>
	      </form>
      		 <div id="addCarlg-buttons" style="text-align:center;"  > 
      	  	  <a class="easyui-linkbutton"    id="recordSaveBt" iconCls="icon-ok"  onclick="saveRecord();" >保存</a>
    		</div>	  
    </div> 
	
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
 