<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/replyDataList.js"></script>
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
	<table id="checkPageTb" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<span>机构名称：</span><input  id="orgName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
		<span>方案名称：</span><input  id="name" maxlength="25" style="width: 180px" class="easyui-validatebox" />  
		<span>方案状态：</span>
		<select  id="approverState"  editable="false" class="easyui-combobox" data-options="width:80">  
		 		<option value="all">全部</option>	
                <option value="1">待批复</option>	
                <option value="2">方案重提</option>  
                <option value="3">生效</option>	 
                <option value="4">拒绝</option>	 
        </select>
     	 
     	 
        </div>
          <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	           
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
                    <td><input id="name2" type="text" name="name" class="easyui-validatebox validateItem" style="width: 140px" /></td>
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
            <a href="javascript:void(0)" class="easyui-linkbutton editBtn" onclick="toEditChannelPlanInfo()">编辑</a>
            <a href="javascript:void(0)" class="easyui-linkbutton editBtn" onclick="doDeleteChannelPlanInfo()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton saveBtn" onclick="doEditChannelPlanInfo()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>

	<!-- 审核日志对话框 -->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>
	<!-- 审核方案 -->
	<div id="seReplyEditDlg" style="top: 20px;height:600px;width:1000px;"></div>   
<script>	
           $(function(){
           	 
           		
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>
</body>
</html>