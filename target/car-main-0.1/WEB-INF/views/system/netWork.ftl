<#include "../macros/constant_output_macro.ftl">
<#assign title="员工管理"/>
<@htmlCommonHead/>
<script type="text/javascript">var userType='${userType}';</script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/system/netWork.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/baseAreaList.js"></script>
<script type="text/javascript">
	var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
</head>
<body>		
	<!-- 查询条件 -->
	<div id="toolbar" style="height:90px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>地区</span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="areaCombo">&nbsp;&nbsp;&nbsp;
			<span>城市</span> &nbsp;&nbsp;<input class="easyui-combobox"  style=""  id="cityCombo">&nbsp;&nbsp;&nbsp;
			<span>部门/营业部</span> &nbsp;&nbsp;<input class="easyui-combobox"  style="width:200px"  id="branchCombo">&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" id="clear" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">清空</a> 
			<br/><br/>
			<span>网点名称：</span><input type="text" id="fullName" style="width: 455px" />&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> &nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
		<table id="netWorkGrid"  toolbar="#toolbar"></table>
		<!-- 查询条件 -->
		<!-- 新增地区 -->
    	<div id="addAreaPanel" class="easyui-window" title="新增地区" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="addAreaForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
          	<input name="id" type="hidden"/>
          	<input name="companyId" type="hidden"/>
            <table cellspacing="0px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	       		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveArea();">提交</a>
				<a  class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addAreaPanel').window('close')">取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 新增地区-->
		<!-- 修改公司信息 -->
    	<div id="updateCompanyPanel" class="easyui-window" title="修改公司信息" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="updateCompanyForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
          	<input name="id" type="hidden"/>
            <table cellspacing="0px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	       		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="updateCompany();">提交</a>
				<a  class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateCompanyPanel').window('close')">取消</a>
	        </div>
       	  </div>
   		</div>
   			<!-- 修改公司信息 -->
   			
   			
		<!-- 修改地区 -->
    	<div id="updateAreaPanel" class="easyui-window" title="修改地区" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="updateAreaForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
          	<input name="id" type="hidden"/>
            <table cellspacing="0px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:updateArea();" class="easyui-linkbutton" iconCls="icon-ok" >提交</a>
	            <a onclick="javascript:$('#updateAreaPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 修改地区 -->
   		
   		
   		
		<!-- 新增城市 -->
    	<div id="addCityPanel" class="easyui-window" title="新增城市" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="addCityForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
           <input name="areaId" type="hidden"/>
            <table cellspacing="0px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:saveCity();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	        	<a onclick="javascript:$('#addCityPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 新增城市 -->
   		
   		<!-- 新增城市 -->
    	<div id="updateCityPanel" class="easyui-window" title="新增城市" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="updateCityForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
           <input name="id" type="hidden"/>
            <table cellspacing="0px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:updateCity();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	        	<a onclick="javascript:$('#updateCityPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 新增城市 -->
   		
   		
   		<!--新增营业部 -->
    	<div id="addDeptPanel" class="easyui-window"  title="新增营业部" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="addDeptForm"  method="post" enctype="multipart/form-data">
          	<input name="cityId" type="hidden"/>
            <table border="0px" cellspacing="20px">
            	<tr>
                    <td width="100px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:380px;height:18px;" type="text" name="name"/></td>		
                    <td >&nbsp;</td>	
                </tr>
            	<tr>
                    <td width="100px">网点类型:</td>
                    <td>
                    		<select name="deptType" class="easyui-combobox validateItem"  style="width:380px;height:18px;">
                    			<option value = "1"> 小企业贷 </option>
                    			<option value = "2"> 车贷 </option>
                    		</select>
                    </td>		
                    <td >&nbsp;</td>	
                </tr>
            	<tr>
                    <td>
                    		办公地点:
                     		<input  type="hidden" name="workPlaceInfoId" />
                    </td>
                     <td>
                     		 <textarea  style="width:380px;"  name="site"></textarea>
                    </td>	
                   <td>
                   		<input onclick="javascript:reloadChooseWorkPlace();" type="button" value="选择办公地点"/>
                   </td>
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:saveSalesDepartment();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	            <a onclick="javascript:$('#addDeptPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	        </div>
       	  </div>
   		</div>
   	  <!-- 新增营业部-->	
   	  
   	   <!--修改营业部 -->
    	<div id="updateDeptPanel" class="easyui-window"  title="修改营业部" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="updateDeptForm"  method="post" enctype="multipart/form-data">
          	<input name="id" type="hidden"/>
            <table border="0px" cellspacing="20px">
            	<tr>
                    <td width="100px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:380px;height:18px;" type="text" name="name"/></td>		
                    <td >&nbsp;</td>	
                </tr>
                <tr>
                    <td width="100px">网点类型:</td>
                    <td>
                    		<select name="deptType" class="easyui-combobox"  style="width:380px;height:18px;">
                    			<option value = "1"> 小企业贷 </option>
                    			<option value = "2"> 车贷 </option>
                    		</select>
                    </td>		
                    <td >&nbsp;</td>	
                </tr>
            	<tr>
                    <td>
                    		办公地点:
                     		<input  type="hidden" name="workPlaceInfoId" />
                    </td>
                     <td>
                     		 <textarea  style="width:380px;"  name="site"></textarea>
                    </td>	
                   <td>
                   		<input onclick="javascript:reloadChooseWorkPlace();" type="button" value="选择办公地点"/>
                   </td>
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:updateDept();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	            <a onclick="javascript:$('#updateDeptPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	        </div>
       	  </div>
   		</div>
   		<!--修改营业部 -->
   		   	   
   	  <!-- 新增团队-->	
    	<div id="addTeamPanel" class="easyui-window" title="新增团队" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="addTeamPanelForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
          	<input name="salesDeptId" type="hidden"/>
            <table cellspacing="20px" border=0>
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
                <tr>
                    <td width="100px">网点类型:</td>
                    <td>
                    		<select name="deptType" class="easyui-combobox"  style="width:180px;height:18px;">
                    			<option value = "1"> 小企业贷 </option>
                    			<option value = "2"> 车贷 </option>
                    		</select>
                    </td>		
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:saveSalesTeam();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	            <a onclick="javascript:$('#addTeamPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 新增团队 -->
   		
   		   	  <!-- 修改团队-->	
    	<div id="updateTeamPanel" class="easyui-window" title="修改团队" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="updateTeamForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF; padding-left:10px;text-align:left;">
          	<input name="id" type="hidden"/>
             <input name="salesDeptId" type="hidden"/>
            <table cellspacing="20px" border=0 style="line-height:20px">
            	<tr>
                    <td width="60px">网点名称:</td>
                    <td><input class="easyui-textbox validateItem" style="width:180px;height:18px;" type="text" name="name" ></input></td>			
                </tr>
               <tr>
                    <td width="100px">网点类型:</td>
                    <td>
                    		<select name="deptType" class="easyui-combobox"  style="width:180px;height:18px;">
                    			<option value = "1"> 小企业贷 </option>
                    			<option value = "2"> 车贷 </option>
                    		</select>
                    </td>		
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:updateTeam();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	            <a onclick="javascript:$('#updateTeamPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	        </div>
       	  </div>
   		</div>
   		<!-- 修改团队 -->
   		
   		
   	  <!-- 选择办公地点面板 -->
	<div id="chooseWorkPlaceInfoPanel" class="easyui-window" title="选择办公地点" style="width:1500px;top:60px;height:700px" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>办公电话</span> &nbsp;&nbsp;&nbsp;&nbsp;<input  id="tel"/>&nbsp;&nbsp;
			<span>城市编号</span> &nbsp;&nbsp;<input id="cityNo"/>&nbsp;&nbsp;&nbsp;
			 <a id="searchWorkPlaceInfoBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <a id="addWorkPlaceInfoBut" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增办公地点</a>
			<br/><br/>
			<span>办公地点名称：</span><input id="site" style="width:575px;"/>
		</div>
		<div style="height:500px">
		     <table id="WorkPlaceInfoGrid" toolbar="#toolbar"></table>
		</div>
	</div>
    <!-- 选择办公地点面板  -->
    
	<!--新增办公地点面板 -->
    	<div id="addWorkPlaceInfoPanel" class="easyui-window" width="600px"  title="办公地点信息维护" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
      	  <div style="padding:10px 10px 10px 10px">
            <form id="addWorkPlaceInfoForm"  method="post" enctype="multipart/form-data" style="background: #FFFFFF;">
            <input name="id" type="hidden"/>
            <table cellspacing="20px" >
            	<tr>
                    <td width="60px">服务电话:</td>
                    <td><input  name="tel"   class="easyui-textbox" style="width:380px;height:18px;"/></td>			
                </tr>
            	<tr>
                    <td width="60px">城市编号:</td>
                    <td><input  class="easyui-textbox validateItem" style="width:380px;height:18px;" equired="true" type="text" name="cityNo" /></td>	
                     <td>
                     </td>			
                </tr>
            	<tr>
                    <td width="100px">
                    		办公地址:
                     		<input  type="hidden" name="workPlaceInfoId" />
                    </td>
                     <td>
                     		 <textarea  style="width:380px;"  name="site" equired="true"></textarea>
                    </td>	
                   <td width="60px">
                   	
                   </td>
                </tr>
                 <tr>
                    <td width="60px">区号:</td>
                    <td><input   class="easyui-textbox validateItem" style="width:380px;height:18px;"  type="text" name="zoneCode" /></td>		
                     <td></td>		
                </tr>
             </table>          
       		 </form>
        	<br/>
	        <div style="text-align:center;padding:5px">
	            <a href="javascript:addWorkPlaceInfo();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
	            <a onclick="javascript:$('#addWorkPlaceInfoPanel').window('close');"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	        </div>
       	  </div>
   		</div>
   	  <!-- 新增办公地点面板 -->	

</body>
</html>