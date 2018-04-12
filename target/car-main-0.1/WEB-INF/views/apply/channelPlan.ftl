
<html>
<head>
	<base href="${WebConstants.webUrl}${WebConstants.contextPath}/" />
	 <script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript">var groupId='${groupId}';</script> 
	<style type="text/css">
	    .easyui-window .channel
	    {
	        margin-top:60px;
	        margin-left:70px;
	    }
	    .chedai
	    {
	   	 font-size:14px;
	    }
	</style>
	<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/easyui.css" />
	<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/icon.css" />
	<link type="text/css" charset="UTF-8" rel="stylesheet" href="resources/css/m_style.css" />
	<script type="text/javascript" charset="UTF-8" src="resources/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.cookie.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/m_common.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/m_fuc.js"></script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
</head>
<body>
	<!-- 查询条件 -->
	<div id="toolbar" style="height:50px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton " data-options="iconCls:'icon-search'" style="display:none">查询</a> 
			<#if groupId != 55>
				<a href="javascript:void(0)" id="insertChannelPlanBut" class="easyui-linkbutton"  data-options="iconCls:'icon-add'" >新增方案</a>
			</#if>
		</div>
	</div>
	<!--  方案列表数据 -->
	<table id="channelPlanGrid" ></table>

	<!-- 新增方案信息面板 -->
	<div id="channelPlanPanel" class="easyui-window channel" title="新增方案" style="width:800px;top:50%;left:50%" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="channelPlanForm"  method="post" enctype="multipart/form-data">
            <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left" >
                <tr>
                    <td><div><label style="color:red" >*</label>方案编号：</div></td>
                    <td><input id="code3" type="text" name="code" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                    <td><div><label style="color:red" >*</label>方案名称：</div></td>
                    <td><input id="name3" type="text" name="name" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                </tr>
                <tr>
                    <td><label>借款类型：</label></td>
                    <td><input type="hidden" id="planType3" name="planType" /><label>助学贷</label></td>
                    <td><label>所属机构：</label></td>
                    <td><input type="hidden" id="organizationId3" name="organizationId" /><label><span id="organName3"></span></label></td>
                </tr>
                <tr>
                   	<td><div><label style="color:red" >*</label>开售日期：</div></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="startDate3" name="startDate" style="width: 140px;" onfocus="WdatePicker()" type="text" /></td>
                    <td><div><label style="color:red" >*</label>停售日期：</div></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="endDate3" name="endDate" style="width: 140px;" onfocus="WdatePicker()" type="text" /></td>
                </tr>
                <tr>
                    <td><div><label style="color:red" >*</label>申请金额：</div></td>
                    <td><input id="requestMoney3" name="requestMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><label>元</label></td>
                    <td><div><label style="color:red" >*</label>借款期限：</div></td>
                    <td><input validtype="integerCheck" id="time3" name="time" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                </tr>
                <tr>
                    <td><div><label style="color:red" >*</label>合同金额：</div></td>
                    <td><input id="pactMoney3" name="pactMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><label>元</label></td>
                    <td><label>保证金金额：</label></td>
                    <td><input validtype="moneyCheck" id="margin3" name="margin" class="easyui-validatebox" style="width: 140px" /><label>元</label></td>
                </tr>
                <tr>
                <td>
                	<div><label style="color:red" >*</label>机构承担服务费：</div>
                </td>
                <td>
	                <select  id="orgFeeState3"  name="orgFeeState" editable="false" class="easyui-combobox validateItem" data-options="width:140,panelWidth:140">  
					<option value="00">是</option>
					<option value="01">否</option>
				</select>
				</td>
				<td><div><label style="color:red" >*</label>机构还款期数：</div></td>
                 <td><input id="orgRepayTerm3" name="orgRepayTerm" class="easyui-validatebox validateItem" validtype="integerCheck" style="width: 140px" /></td>
                 </tr>
                 <tr>
                    <td><div><label style="color:red" >*</label>月综合费率：</div></td>
                    <td><input id="actualRate3" name="actualRate" validtype="percentCheck" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                    <td><div><label style="color:red" >*</label>还款类型：</div></td>
                    <td>
                     <select  id="returnType3"  name="returnType" editable="false" class="easyui-combobox validateItem" style="width:140px"
                     data-options="
                     width:140,
                     panelWidth:140,
                     onSelect:function(newValue){  
    		 				 if(newValue.value=='0'){
						    		   $('#toTerm23').addClass('validateItem');
						    		   $('#toTerm23').attr('disabled',false);
						    		   $('#returneterm23').addClass('validateItem');
						    		   $('#returneterm23').attr('disabled',false);
						    		   $('input.validateItem').validatebox({
						  		        required:true
						    		    });
				    		  }else if(newValue.value=='1'){
				    			  $('#toTerm23').attr('disabled',true);
				    			  $('#toTerm23').val('');
				    			  $('#toTerm23').removeClass('validateItem');
				    			  $('#toTerm23').removeClass('validatebox-invalid');
				    			  $('#toTerm23').removeClass('validatebox-text');
				    			  $('#returneterm23').attr('disabled',true);
				    			  $('#returneterm23').val('');
				    			  $('#returneterm23').removeClass('validateItem');
				    			  $('#returneterm23').removeClass('validatebox-invalid');
				    			  $('#returneterm23').removeClass('validatebox-text');
				    		  }
    	  				}
    	  				"
                     >  
						<option value="0">前低后高</option>
						<option value="1">等额本息</option>
					</select>
					</td>
                </tr>
                 <tr>
                <td colspan="4"> 
	        	 <div class="datagrid-toolbar"><label style="font-weight:bold">分期还款明细</label> </div>
	        	</td>
                </tr>
                <tr>
                <td colspan="4"> 
                	<table width="100%" cellpadding="5" id="termsTable23" class="chedai"  cellspacing="10" style="text-align:left" >
	        	 <tr>
	        	 <td>还款段次</td><td><label style="color:red" >*</label>期数</td><td><label style="color:red" >*</label>金额</td>
	        	 </tr>
	        	  <tr>
	        	 <td>1</td><td><input id="toTerm13" name="toTerm1" validtype="integerCheck" class="easyui-validatebox validateItem"  onblur="saveCal()"/></td><td><input id="returneterm13" name="returneterm1" validtype="moneyCheck" type="number" class="easyui-validatebox validateItem" onblur="saveCal()"/></td>
	        	 </tr>
	        	  <tr>
	        	 <td>2</td><td><input id="toTerm23" name="toTerm2" validtype="integerCheck" class="easyui-validatebox"  disabled="disabled" /></td><td><input id="returneterm23" name="returneterm2" validtype="moneyCheck" type="number" class="easyui-validatebox"   disabled="disabled"/></td>
	        	 </tr>
	        	 	</table>
	        	</td>
                </tr>
                <tr>
                <td colspan="4"><div class="datagrid-toolbar"><label style="font-weight:bold">备注</label> </div></td>
                </tr>
                <tr>
                <td colspan="4">
                	<textarea id="memo3" name="memo" rows="3" cols="100" maxlength="200"></textarea>
                </td>
                </tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doSaveChannelPlanInfo()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    
    
    	<!-- 修改，删除方案信息面板 -->
	<div id="channelPlanEditPanel" class="easyui-window channel" title="编辑方案" style="width:800px;top:50%;left:50%" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="channelPlanEditForm"  method="post" enctype="multipart/form-data">
            <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left" >
                <tr>
               		<input type="hidden" id="checkId2" name="id">
               		<input type="hidden" id="planId2" name="plan_id">
               		<input type="hidden" id="planState2" name="planState">
               		<input id='operatorId2' type="hidden" name="operatorId">
               		<input id='approverState2' type="hidden" name="approverState">
                    <td><div><label style="color:red" >*</label>方案编号：</div></td>
                    <td><input id="code2" type="text" name="code" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                    <td><div><label style="color:red" >*</label>方案名称：</div></td>
                    <td><input id="name2" type="text" name="name" class="easyui-validatebox validateItem" style="width: 140px" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><label>借款类型：</label></td>
                    <td><input id='planType2' type="hidden" name="planType"></input><label>助学贷</label></td>
                    <td><label>所属机构：</label></td>
                    <td><input id='organizationId2' type="hidden" name="organizationId"></input><label><span id="organName2"></span></label></td>
                </tr>
                <tr>
                   	<td><div><label style="color:red" >*</label>开售日期：</div></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="startDate2" name="startDate" style="width: 140px;" onfocus="WdatePicker()" type="text" /></td>
                    <td><div><label style="color:red" >*</label>停售日期：</div></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="endDate2" name="endDate" style="width: 140px;" onfocus="WdatePicker()" type="text" /></td>
                </tr>
                <tr>
                    <td><div><label style="color:red" >*</label>申请金额：</div></td>
                    <td><input id="requestMoney2" name="requestMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><label>元</label></td>
                    <td><div><label style="color:red" >*</label>借款期限：</div></td>
                    <td><input validtype="integerCheck" id="time2" name="time" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                </tr>
                <tr>
                    <td><div><label style="color:red" >*</label>合同金额：</div></td>
                    <td><input id="pactMoney2" name="pactMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><label>元</label></td>
                    <td><label>保证金金额：</label></td>
                    <td><input validtype="moneyCheck" id="margin2" name="margin" class="easyui-validatebox" style="width: 140px" /><label>元</label></td>
                </tr>
                <tr>
                <td>
                	<div><label style="color:red" >*</label>机构承担服务费：</div>
                </td>
                <td>
	                <select  id="orgFeeState2" name="orgFeeState" editable="false" class="easyui-combobox validateItem" data-options="width:140,panelWidth:140">  
					<option value="00">是</option>
					<option value="01">否</option>
				</select>
				</td>
				<td><div><label style="color:red" >*</label>机构还款期数：</div></td>
                 <td><input id="orgRepayTerm2" name="orgRepayTerm" class="easyui-validatebox validateItem" validtype="integerCheck" style="width: 140px" /></td>
                 </tr>
                 <tr>
                    <td><div><label style="color:red" >*</label>月综合费率：</div></td>
                    <td><input id="actualRate2" name="actualRate" validtype="percentCheck" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                    <td><div><label style="color:red" >*</label>还款类型：</div></td>
                    <td>
                    <select  id="returnType2"  name="returnType" editable="false" class="easyui-combobox validateItem"
                     data-options="
                     width:140,
                     panelWidth:140,
                     onSelect:function(newValue){  
    		 				 if(newValue.value=='0'){
						    		   $('#toTerm22').addClass('validateItem');
						    		   $('#toTerm22').attr('disabled',false);
						    		   $('#returneterm22').addClass('validateItem');
						    		   $('#returneterm22').attr('disabled',false);
						    		    $('input.validateItem').validatebox({
						  		        required:true
						    		    });
				    		  }else if(newValue.value=='1'){
				    			  $('#toTerm22').attr('disabled',true);
				    			  $('#toTerm22').val('');
				    			  $('#toTerm22').removeClass('validateItem');
				    			  $('#toTerm22').removeClass('validatebox-invalid');
				    			  $('#toTerm22').removeClass('validatebox-text');
				    			  $('#returneterm22').attr('disabled',true);
				    			  $('#returneterm22').val('');
				    			  $('#returneterm22').removeClass('validateItem');
				    			  $('#returneterm22').removeClass('validatebox-invalid');
				    			  $('#returneterm22').removeClass('validatebox-text');
				    		  }
    	  				}
                    	"
                     >  
					<option value="0">前低后高</option>
					<option value="1">等额本息</option>
					</select></td>
                </tr>
                 <tr>
                <td colspan="4"> 
	        	 <div class="datagrid-toolbar"><label style="font-weight:bold">分期还款明细</label> </div>
	        	</td>
                </tr>
                <tr>
                <td colspan="4"> 
                	<table width="100%" cellpadding="5" id="termsTable22" class="chedai"  cellspacing="10" style="text-align:left" >
			        	 <tr>
			        	 <td>还款段次</td><td><label style="color:red" >*</label>期数</td><td><label style="color:red" >*</label>金额</td>
			        	 </tr>
			        	  <tr>
			        	 <td>1</td><td><input id="toTerm12" name="toTerm1" validtype="integerCheck" class="easyui-validatebox validateItem"  onblur="editCal()"/></td><td><input id="returneterm12" name="returneterm1" type="number" class="easyui-validatebox validateItem" onblur="editCal()"/></td>
			        	 </tr>
			        	  <tr>
			        	 <td>2</td><td><input id="toTerm22" name="toTerm2" validtype="integerCheck" class="easyui-validatebox"  /></td><td><input id="returneterm22" name="returneterm2" type="number" class="easyui-validatebox"  /></td>
			        	 </tr>
	        	 	</table>
	        	</td>
                </tr>
                <tr>
                <td colspan="4"><div class="datagrid-toolbar"><label style="font-weight:bold">备注</label> </div></td>
                </tr>
                <tr>
                <td colspan="4">
                	<textarea id="memo2" name="memo" rows="3" cols="100"></textarea>
                </td>
                </tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
        <#if groupId != 55>
            <a href="javascript:void(0)" class="easyui-linkbutton editBtn" onclick="toEditChannelPlanInfo()">编辑</a>
            <a href="javascript:void(0)" class="easyui-linkbutton editBtn" onclick="doDeleteChannelPlanInfo()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton saveBtn" onclick="doEditChannelPlanInfo()">保存</a>
         </#if>   
            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
	<script type="text/javascript" charset="UTF-8" src="resources/js/apply/channelPlan.js"></script>

</body>

</html>