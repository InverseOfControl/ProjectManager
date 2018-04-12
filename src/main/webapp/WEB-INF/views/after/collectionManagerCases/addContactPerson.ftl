
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
   <span style="display:none;" >
   	 </span>
    <div id="productTableDiv"   closed="true" data-options="resizable:true">
                <form id="collectionContracterFrom" name="collectionContracterFrom"> 
                <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4"> 
                 	  <tr style="height:30px" >
                        	 <td style="background:#f1f5f9"><label>所属人</label></td>
                              <td align="left"><label  >${person.name!""}
                               <span style="display:none;" >
                              		   <input type="text"  hidden="true" value='${person.id!""}' id="personId" name="personId"   />
                              	  </span>	   
                              </label></td>
                        </tr>
                        <tr style="height:30px" >
                            <td style="background:#f1f5f9"><label>姓名</label></td>
                            <td align="left"><label  >
                            			 <input type="text"    name="name"   id="personContracterName"   >
                            </label></td>
                            <td style="background:#f1f5f9"><label>性别</label></td>
                            <td align="left"><label> <select  id="sex"    name="sex"  editable="false"  class="easyui-combobox" data-options="width:120"> 
            								   <option value=0>女</option>
                          					   <option value=1>男</option>
                           				  	   
           						 </select></label></td>
                            <td style="background:#f1f5f9"><label>关系</label></td>
                            <td align="left"><label> <select  id="relationShip"    name="relationShip"  editable="false"  class="easyui-combobox" data-options="width:120"> 
                            					   
            								   <option value="同事">同事</option>
                          					   <option value="配偶">配偶</option>
                           				  	   <option value="父亲">父亲</option>
                           					   <option value="母亲">母亲</option>
                         					   <option value="朋友">朋友</option>
                         					   <option value="同学">同学</option>
                         					   <option value="子女">子女</option>
                         					   <option value="其他亲属">其他亲属</option>
                         					   <option value="其他">其他</option
           						 </select></label></td>
                             		
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                            <td><label>手机</label></td>
                            <td align="left"><label  >
                            		 	 <input type="text" maxlength="11"   name="mobilePhone" id="mobilePhone"   />
                           		</label></td>
                            <td><label>家庭电话</label></td>
                            <td align="left"><label > 
                            		 
  										 
  					  <input type="text"   name="homePhone" id="homePhone"   />
  					 				 
                            </label></td>
                            <td style="background:#f1f5f9"><label>身份证</label></td>
                            <td align="left"><label  >	 
                            			  <input type="text"   name="idnum"   />
                            </label></td>
                        </tr>
                        <tr style="height:30px;border-bottom:1px  " >
                             <td style="background:#f1f5f9"><label>现居地址</label></td>
                            <td align="left"><label  >
  										 
  										  <input type="text"   name="address"   />
                           		</label></td>
                            <td style="background:#f1f5f9"><label>所在公司</label></td>
                            <td align="left"><label > 
                            			 <input type="text"   name="workUnit"   /> 
  										
                            </label></td>
                            <td style="background:#f1f5f9"><label>部门</label></td>
                            <td align="left"><label  > 
                            		  <input type="text"    name="deptName"   />
                             </label></td>
                        </tr>
                        
                         <tr style="height:30px;border-bottom:1px  " >
                            <td style="background:#f1f5f9"><label>职务</label></td>
                            <td align="left"><label  >
  										 <input type="text"   name="job"   />
                           		</label></td>
                            <td style="background:#f1f5f9"><label>公司电话</label></td>
                            <td align="left"><label > 
                            	 	 <input type="text"   name="companyPhone" id="companyPhone"   />
                            </label></td>
                          
                        </tr>
	   
	      </table>
	      </form>
      		 <div id="addCarlg-buttons" style="text-align:center;"  > 
      	  	  <a class="easyui-linkbutton"   id="saveContactPersonBt" iconCls="icon-ok"  onclick="addContactPerson();" >保存</a>
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
            
         
            })
</script>		
 