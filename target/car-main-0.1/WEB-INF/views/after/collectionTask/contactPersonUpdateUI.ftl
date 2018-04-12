
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
   <span style="display:none;" >
   	<input type="text" hidden="true" value='${part!""}' id="part"   >
   	 </span>
    <div id="productTableDiv"   closed="true" data-options="resizable:true">
    	  <form id="personTb" name="personTb"> 
	   	   <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
	    			  <span style="display:none;" >
	      				     <input type="text"  hidden="true" value='${cp.id!""}'  name="id"   disabled/>
	      				    <input type="text"  hidden="true"  value='${cp.id!""}' id="additionalId"   disabled/>
	      				<input type="text"  value='${cp.subordinate!""}' name="subordinate"  id="subordinate"  hidden="true" >
	      				   
	      			   </span>
                        <tr style="height:30px" >
                        	 <td style="background:#f1f5f9;width:100px"><label>所属人:</label></td>
                             <td align="left"  style=" width:200px"><label>${person.name !""}</label></td>
                        </tr>
                        <tr style="height:30px" >
                            <td style="background:#f1f5f9;width:100px"><label>姓名</label></td>
                            <td align="left"><label  > 
                            		<input type="text" id="collectionContactName"  value='${cp.name!""}' name="name"   disabled>
                            		</label></td>
                         <td style="background:#f1f5f9;width:100px"><label>性别</label></td>
                            <td align="left" style=" width:200px"><label><select  id="sex"   disabled  name="sex"  editable="false"  class="easyui-combobox" data-options="width:120,
                            		 onLoadSuccess: function () { 
                        								$('#sex').combobox('select', '${cp.sex!""}');
                        									}  "> 
            								   <option value=0>女</option>
                          					   <option value=1>男</option>
                           				  	   
           						 </select></label></td>
                            <td style="background:#f1f5f9;width:100px"><label>关系</label></td>
                            <td align="left" style=" width:200px"><label>
                             <#if (cp.relationShip=="本人")>
                            			 		 本人 <span style="display:none;" >
	      													<input type="text"  value='本人' name="relationShip"     hidden="true" >
	      				 
	      										   </span>
	      										   <#else>
                            			 <select  id="relationShip" disabled  name="relationShip"  editable="false" class="easyui-combobox" data-options="width:120,
                            					  onLoadSuccess: function () { 
                        									$('#relationShip').combobox('select', '${cp.relationShip!""}');
                        									}  "> 
            								   <option value="同事">同事</option>
                          					   <option value="配偶">配偶</option>
                           				  	   <option value="父亲">父亲</option>
                           					   <option value="母亲">母亲</option>
                         					   <option value="朋友">朋友</option>
                         					   <option value="同学">同学</option>
                         					   <option value="子女">子女</option>
                         					   <option value="其他亲属">其他亲属</option>
                         					   <option value="其他">其他</option
           						 </select>
           						   </#if>
                            </label></td>
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                         	 <td style="background:#f1f5f9;width:100px"><label>手机</label></td>
                            <td align="left"><label  >
                            		
                            		 	<input type="text"  value='${cp.mobilePhone!""}' name="mobilePhone" id="mobilePhone"  maxlength=11 disabled>
                           		</label></td>
                             <td style="background:#f1f5f9"><label>家庭电话</label></td>
                            <td align="left"><label > 
  									
  											<input type="text"  value='${cp.homePhone!""}' id="homePhone" name="homePhone"  disabled>
                            </label></td>
                             <td style="background:#f1f5f9"><label>身份证</label></td>
                         	  <td align="left"><label  >	 
                            			  <input type="text"    name="idnum"   value='${cp.idnum!""}'  disabled/>
                            </label></td>
                            
                            
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                          	 <td style="background:#f1f5f9;width:100px"><label>现居地址</label></td>
                            <td align="left"><label  >
                            		 
  									 
  					 <input type="text"  value='${cp.address!""}' name="address"  disabled>
  					 			 
                           		</label></td>
                            <td style="background:#f1f5f9"><label>所在公司</label></td>
                            <td align="left"><label > 
                            		 
  										 
  					 	 <input type="text"  value='${cp.workUnit!""}' name="workUnit"  disabled>
                             </label></td>
                          	 <td style="background:#f1f5f9"><label>部门</label></td>
                             <td align="left"><label  >  <input type="text"    value='${cp.deptName!""}' name="deptName"  disabled/> </label></td>
                        </tr>
                        
                         <tr style="height:30px;border-bottom:1px  " >
                           	 <td style="background:#f1f5f9;width:100px"><label>职务</label></td>
                            <td align="left"><label  >
                            		 
  					  	 <input type="text"  value='${cp.job!""}' name="job"  disabled>
  					 				 
                           		</label></td>
                            <td style="background:#f1f5f9"><label>公司电话</label></td>
                            <td align="left"> <input type="text"   name="companyPhone"   value='${cp.companyPhone!""}'    disabled/><label > 
                            	 
                            </label></td>
                          
                        </tr>
                     </table>   
                   </form > 
              
	  
      		 <div id="addCarlg-buttons" style="text-align:center;"  > 
      		  <a class="easyui-linkbutton" id="editBt" iconCls="icon-ok" plain="true" onclick="contactPerson1();" >编辑</a>
      	  	  <a class="easyui-linkbutton" style="display:none;"  id="personSaveBt1" iconCls="icon-ok"  onclick="saveContactPerson1();" >保存</a>
    		</div>	  
    </div> 
   			 	<table id="personContacterTb"  ></table>  
   			 	 <div  style="text-align:center;"  > 
   			 		 <a class="easyui-linkbutton"    id="addAdditionalBt" iconCls="icon-ok"  onclick="addAdditional();" >添加</a>
   			 	</div>	 
   			 	<div id="additionalDlg" class="easyui-dialog" style="width: 300px;height: 300px;" closed="true" data-options="resizable:true">
   			 	 	 <form id="additionalFrom" name="additionalFrom"> 
   			 	 	 	 <span style="display:none;" >
   			 	 	 		<input type="text" hidden="true" value='${part!""}' name="additionalType"   >
   			 	 	 		<input type="text" hidden="true"  id="relationId" name="relationId"   >
   			 	 	 	 </span>	
   			 	 	 		  <table>
   			 	 	 		   <tr style="height:30px;border-bottom:1px  " >
                          		  <td style="background:#f1f5f9"><label>额外电话</label></td>
                            		<td align="left"><label  >
  										 <input type="text" style="width:300px;"  value="" id="additionalTel"  name="additionalTel"  />
                           		</label></td>
                          		  </tr> 
                          <tr style="height:30px;border-bottom:1px  " >
                          		  <td style="background:#f1f5f9"><label>额外地址</label></td>
                            		<td align="left"><label  >
  										 <input type="text"  style="width:300px;"  value="" id="additionalAddress"  name="additionalAddress"  />
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
          var relationId=  $('#relationId').val();
           
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
	        	relationId :relationId,
	        	innerCollection :1
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
 