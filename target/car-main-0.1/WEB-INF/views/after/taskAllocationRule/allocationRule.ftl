
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
    <form id="allocationRuleTb" name="allocationRuleTb" method="post" >
    	
    <div id="productTableDiv"   closed="true" data-options="resizable:true">
	     <input  name="id" id="id"  type="hidden"   value="${user.id}"/> 
	     <input  name="ruleStrId" id="ruleId"  type="hidden"   value="${ruleId!""}"/> 
	      <table style="font-size:12px; width:100%; text-align:left;border-collapse: collapse;"   cellspacing="4">
                        <tr style="height:30px" >
                            <td><label>姓名</label></td>
                            <td align="left"><label  >${user.name!""}</label></td>
                           
                        </tr>
                        <tr style="height:30px;border-bottom:1px solid blue" >
                     	 <td><label>分配数</label></td>
                            <td align="left"><label  >
                     	  <input type="text"   name="num" id="num"   onkeydown="if(event.keyCode==13) return false;" /></label></td>
                     	
                        </tr>
       
      	  
       
                        
	    </table> 
	     
      	</div>	   		   
  
                    
         <div id="addCarlg-buttons" style="text-align:center;"  > 
      	  <a class="easyui-linkbutton" id="saveCasesButton" iconCls="icon-ok" plain="true" onclick="addAllocationRule();" >保存</a>
      	   </div>  
    </form>
   			 
      	
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
		 
            });
         
</script>		
 