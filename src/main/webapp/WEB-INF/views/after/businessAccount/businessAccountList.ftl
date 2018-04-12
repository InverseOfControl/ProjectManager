<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/ajaxfileupload.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/after/businessAccount/businessAccount.js"></script>
<script type="text/javascript">var enumJson='${gridEnumJson}';</script> 
<script type="text/javascript">var userType='${userType}';</script> 
<script type="text/javascript">var loginUserId='${loginUserId}';</script> 
<script type="text/javascript">var productType='${productType}';</script> 
<script type="text/javascript">var salesDept='${salesDept}';</script> 

<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		
</style>
</head>
<body>
	<table id="list_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<span>交易日期：</span> 
     	 <input id="repayDateInput" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
		<span>还款金额：</span><input  id="amount" maxlength="18" style="width: 140px" class="easyui-validatebox" />  		  	 
     	 <span>对方单位：</span> <input  id="secondCompanyInput" maxlength="18" style="width: 180px" class="easyui-validatebox" /> 
     	 <span>状态：</span>
		<select  id="status"  editable="false" class="easyui-combobox" data-options="width:120">  
		 		<option value="all">全部</option>	
        		<option value="20">未认领</option>
                <option value="10">已认领</option>	 
                <option value="30">已导出</option>
                <option value="40">无需认领</option>	
                <option value="50">个贷门店认领</option>	
                <option value="60">个贷渠道认领</option>
                  <option value="70">证方门店认领</option>	
                <option value="80">证方渠道认领</option>
        </select>
     	<span>认领时间：</span>
     	<input type="text" id="recTimeStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:140px"/>
  		 &nbsp;—&nbsp;
  		<input type="text" id="recTimeEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:140px"/>
          <span>认领者工号：</span>
     	<input  id="recOperatorNo" maxlength="18" style="width: 180px" class="easyui-validatebox" /> 
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<span id="export">
			<a href="javascript:void(0)" id="exportExcelBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">批量导入</a> 
			<a href="javascript:void(0)" id="exportReceiveResultBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">认领结果导出</a> 
			</span>
			<span id="recTimeBt">
			<a href="javascript:void(0)" id="changeRecBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">修改领取时间</a> 
			</span>
		</div>		
	</div>
	<!--   领取列表 -->
		<div id="receiveGrid" class="easyui-dialog" style="top: 150px;width: 1250px;height:550px" modal="false"closed="true">  	 	
		<table id="receive_result" toolbar="#receivebar" ></table>
		<div id="receivebar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
		<label id="repayDateLabel"></label> &nbsp;&nbsp;&nbsp;&nbsp;<label id="amoutLabel"></label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label id="statusLabel"></label>
		 <input type="hidden" id ="repayDate" />  	
		  <input type="hidden" id ="repayTime" />  	
		   <input type="hidden" id ="businessAccountId"  />  	
		    <input type="hidden" id ="version"  />  	
		     <input type="hidden" id ="secondCompany"/>  
		     <input type="hidden" id ="amountInput"/>  	
		</br>
		</br>
     	<span>姓名：</span> <input id="personName"class="easyui-validatebox" style="width:140px" />
		<span>手机号码：</span><input  id="personMobilePhone" maxlength="18" style="width: 180px" class="easyui-validatebox" />  		  	 
     	 <span>身份证号：</span> <input  id="personIdnum" maxlength="18" style="width: 180px" class="easyui-validatebox" />  
     	 <span>展期期次：</span>
		<select  id="extensionTimeComb"  editable="false" class="easyui-combobox" data-options="width:60">  
				<option value="-1">所有</option>
				<option value="0">无</option>
		        <option value="1">1</option>
		        <option value="2">2</option>	 
		        <option value="3">3</option>	 	 	
		 </select>
		 <span>产品类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:120"/>  
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="receiveSearchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a href="javascript:void(0)" id="backBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">返回</a> 
		</div>
		</div>
		</div>
	</div>
	
	<!-- 批量导入弹出窗 -->
		<div id="fileUploadDialog" class="easyui-dialog" style="top: 50px; width: 750px; padding: 10px 20px" closed="true" buttons="#fileUploadDlg-buttons">
			<form id="fileUploadForm" method="post" enctype="multipart/form-data">
				<table style="font-size:12px; width:100%; text-align:right;">
					<tr>
		        		<td><label>上传文件:</label></td>
		        		<td align="left"><input type="file" name="file" id="file" required="true"/></td>		
					</tr>
				</table>
			</form>
		</div>
		<div id="fileUploadDlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="ajaxFileUpload(this)">导入</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fileUploadDialog').dialog('close')">取消</a>
		</div>
	
		<!--   日志列表 -->
		<div id="logGrid" class="easyui-dialog" style="top: 150px;width: 550px;height:350px" modal="false"closed="true">  	 	
		<table id="log_result"></table>
		</div>	
		<!-- 查看小企业贷对话框 -->
     	<div id="seLoanDetail" style="top: 20px;height:600px;width:1000px;"></div>    
     	<div id="browseEduDlg" style="top: 20px;height:600px;width:1000px;"></div> 
		<!-- 查看车贷对话框 -->
		<div id="carLoanDetail" style="top: 20px;height:600px;width:1000px;"></div>
		 <!-- 查看车贷展期对话框 -->
		<div id="carLoanExtensionDetail" style="top: 20px;height:600px;width:1000px;"></div>
		
		
	<!-- 修改对公还款信息面板 -->
	<div id="businessAccountPanel" class="easyui-window channel" title="修改对公还款" style="width:800px;top:50%;left:50%" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="businessAccountEditForm"  method="post" enctype="multipart/form-data">
            <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left" >
           		 <input id="idEdit" type="hidden" name="id" class="easyui-validatebox validateItem" style="width: 140px" />
            	<tr>
            	<td>
            	交易日期
            	</td>
            	    <td>
            	    <input class="Wdate easyui-validatebox validatebox-text validateItem" id="repayDateEdit" name="repayDate" style="width: 140px;" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})" type="text" >
            	    </td>
            	<td>
            	交易时间
            	</td>
            	    <td>
            	    <input class="Wdate easyui-validatebox validatebox-text validateItem" id="repayTimeEdit" name="repayTime" style="width: 140px;" onfocus="WdatePicker({dateFmt: 'HH:mm:ss', minDate: '00:00:00', maxDate: '23:59:59'} )" type="text" >
            	    </td>
            	</tr>
            	<tr>
            	<td>
            	对方账号
            	</td>
            	    <td><input id="secondAccountEdit" type="text" name="secondAccount" class="easyui-validatebox validateItem" style="width: 140px" /></td>
            	<td>
            	还款金额
            	</td>
               		<td><input id="amountEdit" type="text" name="amount" class="easyui-validatebox validateItem" style="width: 140px" /></td>
            	</tr>
            	<tr>
            	<td>
            	对方单位
            	</td>
               		<td><input id="secondCompanyEdit" type="text" name="secondCompany" class="easyui-validatebox validateItem" style="width: 140px" /></td>
            	<td>
            	状态
            	</td>
            		 <td>
            	<select  id="statusEdit"  name='status' editable="false" class="easyui-combobox" data-options="width:140">  
	        		<option value="20">未认领</option>
	                <option value="10">已认领</option>	 
	                <option value="30">已导出</option>
	                <option value="40">无需认领</option>	
	                <option value="50">个贷门店认领</option>	
	                <option value="60">个贷渠道认领</option>
	                 <option value="70">证方门店认领</option>	
             	   <option value="80">证方渠道认领</option>
       			</select>
            		</td>
            	</tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doSavebusinessAccountInfo()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    
    	<!-- 修改领取时间面板 -->
	<div id="recTimePanel" class="easyui-window channel" title="修改领取时间" style="width:800px;top:50%;left:50%" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="recTimeEditForm"  method="post" enctype="multipart/form-data">
            <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left" >
           		 <input id="idEdit" type="hidden" name="id" class="easyui-validatebox validateItem" style="width: 140px" />
            	<tr>
            	<td>
            	领取开始日期
            	</td>
            	    <td>
            	    <input class="Wdate easyui-validatebox validatebox-text validateItem" id="recStartTime" name="recStartTime" style="width: 140px;" onfocus="WdatePicker({dateFmt: 'HH:mm', minDate: '00:00', maxDate: '23:59'})" type="text" >
            	    </td>
            	<td>
            	领取结束时间
            	</td>
            	    <td>
            	    <input class="Wdate easyui-validatebox validatebox-text validateItem" id="recEndTime" name="recEndTime" style="width: 140px;" onfocus="WdatePicker({dateFmt: 'HH:mm', minDate: '00:00', maxDate: '23:59'} )" type="text" >
            	    </td>
            	</tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doSaveRecTime()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    
    
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