
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
    <span  style="display:none;"> 
   	<input type="text" hidden="true" value='${part!""}' id="part"   >
   	  </span>
    <div id="productTableDiv"   closed="true" data-options="resizable:true">
    	  <form id="contactTb" name="contactTb"> 
	   	   <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
	    			 <#if (part=="2")>
	    			  <span  style="display:none;"> 
	      				<input type="text"  value='${contact.id!""}' name="id"  id="additionalId" hidden="true"  >
	      				<input type="text"  value='${person.id!""}'   name="personId" hidden="true" >
	      				<input type="text"  value='${subordinate!""}' name="subordinate"  id="subordinate"  hidden="true" >
	      			    <input type="text" hidden="true" name=part value='${part!""}' id="part"   >
	      				  </span>
                        <tr style="height:30px" >
                        	 <td style="background:#f1f5f9;width:100px"><label>所属人:</label></td>
                             <td align="left"  style=" width:200px"><label>${personName!""}</label></td>
                        </tr>
                        <tr style="height:30px" >
                            <td style="background:#f1f5f9;width:100px"><label>姓名</label></td>
                            <td align="left"><label  > 
                            		<input type="text" id="collectionContactName"  value='${contact.name!""}' name="name"   disabled>
                            		</label></td>
                             <td style="background:#f1f5f9;width:100px"><label>性别</label></td>
                            <td align="left" style=" width:200px"><label><select  id="sex"   disabled  name="sex"  editable="false"  class="easyui-combobox" data-options="width:120,
                            		 onLoadSuccess: function () { 
                        									$('#sex').combobox('setText', null);
                        									}  "> 
            								   <option value=0>女</option>
                          					   <option value=1>男</option>
                           				  	   
           						 </select></label></td>
                            <td style="background:#f1f5f9;width:100px"><label>关系</label></td>
                            <td align="left" style=" width:200px"><label>
                            			 <select  id="relationShip"   name="relationShip"  editable="false" class="easyui-combobox" data-options="width:120,
                            					  onLoadSuccess: function () { 
                        									$('#relationShip').combobox('select', '${contact.relationship!""}');
                        									}  "  disabled> 
            								   <option value="同事">同事</option>
                          					   <option value="配偶">配偶</option>
                           				  	   <option value="父亲">父亲</option>
                           					   <option value="母亲">母亲</option>
                         					   <option value="朋友">朋友</option>
                         					   <option value="同学">同学</option>
                         					   <option value="子女">子女</option>
                         					   <option value="其他亲属">其他亲属</option>
                         					   <option value="其他">其他</option>
           						 </select>
                            </label></td>
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                         	 <td style="background:#f1f5f9;width:100px"><label>手机</label></td>
                            <td align="left"><label  >
                            		
                            		 	<input type="text"  value='${contact.mobilePhone!""}' name="mobilePhone" id="mobilePhone"  maxlength=11 disabled>
                           		</label></td>
                             <td style="background:#f1f5f9"><label>家庭电话</label></td>
                            <td align="left"><label > 
  									
  											<input type="text" id="homePhone" value='${contact.homePhone!""}' name="homePhone"  disabled>
                            </label></td>
                             <td style="background:#f1f5f9"><label>身份证</label></td>
                            <td align="left"><label  > <input type="text"    name="idnum"  disabled/></label></td>
                            
                            
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                          	 <td style="background:#f1f5f9;width:100px"><label>现居地址</label></td>
                            <td align="left"> <label  >
                            		 
  									 
  					 <input type="text"  value='${contact.address!""}' name="address"  disabled>
  					 			 
                           		</label></td>
                            <td style="background:#f1f5f9"><label>所在公司</label></td>
                            <td align="left"><label > 
                            		 
  										 
  					 	 <input type="text"  value='${contact.workUnit!""}' name="workUnit"  disabled>
  					 				 
                            </label></td>
                           <td style="background:#f1f5f9"><label>部门</label></td>
                            <td align="left"><label  > <input type="text"    name="deptName"  disabled/> </label></td>
                            
                            
                        </tr>
                        
                         <tr style="height:30px;border-bottom:1px  " >
                           	 <td style="background:#f1f5f9;width:100px"><label>职务</label></td>
                            <td align="left"><label  >
                            		 
  					  	 <input type="text"  value='${contact.title!""}' name="job"  disabled>
  					 				 
                           		</label></td>
                            <td style="background:#f1f5f9"><label>公司电话</label></td>
                             <td align="left"> <input type="text"   name="companyPhone"  disabled/><label > 
                            	 
                            </label></td>
                          
                        </tr>
                    </#if>
                     </table>   
                   </form > 
                <form id="personTb" name="personTb"> 
                <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4"> 
                 <#if (part=="1")>
                 	  <tr style="height:30px" >
                        	 <td style="background:#f1f5f9"><label>所属人</label></td>
                              <td align="left"><label  >${person.name!""}
                               <span  style="display:none;"> 
                              		 <input type="text"  hidden="true" value='${person.id!""}' id="additionalId" name="id"  disabled/>
                              	 			<input type="text"  value='${subordinate!""}' name="subordinate"  id="subordinate"  hidden="true" >
	      				  					<input type="text" hidden="true" name=part value='${part!""}' id="part"   >
                              	 			<input type="text"  value='${cid!""}' name="personId"     hidden="true" >
                                </span>
                              </label></td>
                        </tr>
                        <tr style="height:30px" >
                            <td style="background:#f1f5f9"><label>姓名</label></td>
                            <td align="left"><label  >
                            			 <input type="text"  value='${person.name!""}' id="collectionContactName" name="name"  disabled>
                            </label></td>
                             <td style="background:#f1f5f9;width:100px"><label>性别</label></td>
                            <td align="left" style=" width:200px"><label><select  id="sex"   disabled name="sex"  editable="false"  class="easyui-combobox" data-options="width:120,
                              onLoadSuccess: function () { 
                        									$('#sex').combobox('select', '${person.sex!""}');
                        									} "> 
            								   <option value=0>女</option>
                          					   <option value=1>男</option>
                           				  	   
           						 </select></label></td>
                            <td style="background:#f1f5f9"><label>关系</label></td>
                            <td align="left"><label>本人 <span style="display:none;" >
	      													<input type="text"  value='本人' name="relationShip"     hidden="true" >
	      				 
	      										   </span></label></td>
                             		
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                            <td><label>手机</label></td>
                            <td align="left"><label  >
                            		 	 <input type="text" maxlength="11" value='${person.mobilePhone!""}' name="mobilePhone" id="mobilePhone" disabled/>
                           		</label></td>
                            <td><label>家庭电话</label></td>
                            <td align="left"><label > 
                            		 
  										 
  					  <input type="text"  value='${person.homePhone!""}' name="homePhone" id="homePhone"  disabled/>
  					 				 
                            </label></td>
                            <td style="background:#f1f5f9"><label>身份证</label></td>
                            <td align="left"><label  >	 
                            			  <input type="text"  value='${person.idnum!""}' name="idnum"  disabled/>
                            </label></td>
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                             <td style="background:#f1f5f9"><label>现居地址</label></td>
                            <td align="left"><label  >
  										 
  										  <input type="text"  value='${person.address!""}' name="address"  disabled/>
                           		</label></td>
                            <td style="background:#f1f5f9"><label>所在公司</label></td>
                            <td align="left"><label > 
                            			  <span  style="display:none;">  <input type="text"  hidden="true" value='${company.id!""}' name="companyId"  disabled/></span>
  										  <input type="text"  value='${company.name!""}' name="companyName"  disabled/>
                            </label></td>
                            <td style="background:#f1f5f9"><label>部门</label></td>
                            <td align="left"><label  > 
                            		  <input type="text"  value='${person.deptName!""}' name="deptName"  disabled/>
                             </label></td>
                        </tr>
                        
                         <tr style="height:30px;border-bottom:1px  " >
                            <td style="background:#f1f5f9"><label>职务</label></td>
                            <td align="left"><label  >
  										 <input type="text"  value='${person.job!""}' name="job"  disabled/>
                           		</label></td>
                            <td style="background:#f1f5f9"><label>公司电话</label></td>
                            <td align="left"><label > 
                            	 	 <input type="text"  value='${company.phone!""}' name="companyPhone"  disabled/>
                            </label></td>
                          
                        </tr>
                 </#if>       
	   
	      </table>
	      </form>
      		 <div id="addCarlg-buttons" style="text-align:center;"  > 
      		  <a class="easyui-linkbutton" id="editBt" iconCls="icon-ok" plain="true" onclick="contactPerson();" >编辑</a>
      	  	  <a class="easyui-linkbutton" style="display:none;"  id="personSaveBt1" iconCls="icon-ok"  onclick="saveContactPerson();" >保存</a>
    		</div>	  
    </div> 
     <table id="personContacterTb"  ></table>  
   			 	 <div  style="text-align:center;"  > 
   			 		 <a class="easyui-linkbutton"    id="addAdditionalBt" iconCls="icon-ok"  onclick="addAdditional();" >添加</a>
   			 	</div>	 
   			 	<div id="additionalDlg" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true" data-options="resizable:true">
   			 	 	 <form id="additionalFrom" name="additionalFrom"> 
   			 	 	 	 <span style="display:none;" >
   			 	 	 		<input type="text" hidden="true" value='${part!""}' name="additionalType"   >
   			 	 	 		<input type="text" hidden="true"  id="relationId" name="relationId"   >
   			 	 	 	 </span>	
   			 	 	 		  <table>
   			 	 	 		   <tr style="width: 300px;height:30px;border-bottom:1px  " >
                          		  <td style="background:#f1f5f9"><label>额外电话</label></td>
                            		<td align="left"><label  >
  										 <input type="text" style="width:300px;" id="additionalTel"  name="additionalTel"  />
                           		</label></td>
                          		  </tr> 
                          <tr style="width: 300px;height:30px;border-bottom:1px  " >
                          		  <td style="background:#f1f5f9"><label>额外地址</label></td>
                            		<td align="left"><label  >
  										 <input type="text" style="width:300px;"  id="additionalAddress"  name="additionalAddress"  />
                           		</label></td>
                          		  </tr> 
                       
   			 	 	 		  </table> 
   			 	 	 </form>
   			 	 	  <div  style="text-align:center;"  > 
      	  	  			<a class="easyui-linkbutton"    id="additionalSaveBt" iconCls="icon-ok"  onclick="additionalSaveClick();" >保存</a>
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
                 var pPart=  $('#part').val();  
            var pId=  $('#additionalId').val();  
            $('#relationId').val(pId);
          $('#personContacterTb').datagrid({
	        url: 'CollectionManagerCases/Main/personContacterList',
	    	fitColumns : true,
	        border : false,
	        singleSelect:false,
	        pagination : true,
	        striped: true,
	        pageSize:10,     
	        rownumbers : true,
	        checkOnSelect:true,
	        queryParams: {
	        	relationId :pId,
	        	additionalType :pPart
	    	},
	        columns : [ [ 
	                     {
	             			field : 'id',
	             			title : 'id',
	             			width : 180,
	             			hidden:true,
	             			formatter: function(value, row, index){
	             	          	return value;
	             	      }
	                     },{
	             			field : 'additionalTel',
	             			title : '额外电话',
	             			width : 100,
	             			formatter: function(value, row, index){
	             	          	return value;
	             	        	}
	                     },{
	             			field : 'additionalAddress',
	             			title : '额外地址',
	             			width : 400,
	             			styler: function(value,row,index){
                 				 
            					return 'table-layout:word-wrap:break-word;word-break:break-all';
            			},
	             			formatter: function(value, row, index){
	             	          	return value;
	             	        	}
	                     },  {
                			field : 'cz',
                			title : '操作',
                			width : 100,
                			formatter: function(value, row, index){
                				return    '<a  style="font-weight:bolder;color:blue;"  href="javascript:void(0)" onclick="deleteAdditional('+row.id+ ')" >' + "删除" + '</a>';
                	        	}
                        }  
                        
	                        
	         
			 
			] ]
		});
            })
</script>		
 