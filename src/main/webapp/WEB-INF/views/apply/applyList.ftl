<#include "../macros/constant_output_macro.ftl">
<#include "../macros/multiSearchMacro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript">var groupId='${groupId}'; var userType ='${userType}';</script> 
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/eduLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />

 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
 	<table id="loanPageTb" toolbar="#toolbar"></table>
 	
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
      	<@htmlMultiSearch/>
      	<span>借款状态：</span><input id="loanStatusComb" class="easyui-combobox" editable="false" data-options="width:100" />
      	<span>手机号：</span><input type="text" id="personMobilePhoneTxt" maxlength="25"  style="width: 90px" />  
        <span>车牌号：</span><input type="text" id="plateNumberTxt" maxlength="25"  style="width: 90px" />    
      	<span>客户经理 <span><input id="managerName" name="managerName" class="easyui-combobox" style="width:140px"/>
      	 <!-- <span>机构名称：</span><input type="text" id="organName"  style="width: 150px" /> -->
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	      
	        <#if userType == 4 || userType == 5 || groupId == 55> 
		 
			<#else>
				 <a id="add" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRecord()">新建</a>
			</#if>
		</div>
	</div>

<!-- 弹出层 -->

	<div id="dlg" class="easyui-dialog" style="top: 150px;height:200px;width: 550px;" closed="true">
		<form id="newLoanForm" method="post">	
			<div style="font-size:12px;  text-align:right;margin-right:150px;margin-top:18px">
				<div style="margin-right:-50px;">
					<label style="">借款类型   </label>
					<input type="hidden"  name="loanType"   /> 
					<input id="productComb2" name="productTypeId" class="easyui-combobox" editable="false" style="width: 100px;"/>
					<label style="">产品类型   </label>
					<input id="productType" name="productId" class="easyui-combobox" editable="false" style="width: 150px;"/>
				</div >
				<div style="margin-top:20px">
					<label style="">请输入身份证号码   </label>
					<input id="idnum"  validType="idCheck"    class="easyui-validatebox" name="idnum"  maxlength="18"   style="width: 180px;"  />					
				</div>
				
				<div style="margin-top:20px">
					 <a   style="margin-right:50px"  class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="next()">确认</a>
					 <a   style="margin-right:0"  class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="cancel()">取消</a>
				</div>
			</div>		
			
		</form>
	</div>
	
	<div id="alg" class="easyui-dialog" style="top: 150px;height:150px;width: 560px; padding: 10px 20px";" closed="true">
		<form id="addLoanForm" method="post">		
			<div style="font-size:12px;  text-align:center;margin-top:18px">	
			<div>
				<label> 该客户信息已存在，是否继续？ </label>
				<br><label id="refuseReason" > </label>
				<!-- 产品L类型 -->
				<input type="hidden"  id="productId" name="productId"  value=""  />
				<input type="hidden"  id="loanType" name="loanType"  value=""  />
				<input type="hidden"  id="personType" name="personType"  value=""  />
				<input  type="hidden"   id="idnum" name="idnum" value=""  />
				<!-- 借款类型 -->
				<input type= "hidden"	id="productTypeId"	name="productTypeId"	value=""	/>
			</div>	
			<div style="margin-top:20px">
				<a  style="margin-right:50px"  class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="nexts();">是</a>	&nbsp&nbsp&nbsp&nbsp			 
				 <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAlg()">否 </a>	
			</div>
	    </div>
		</form>
	
</div>

<div id="attachmentDlg" class="easyui-dialog" style="width: 960px;padding-left:8px;overflow-y:scroll;" closed="true" data-options="resizable:true">
	<input id="loanId" type="hidden"  />
	<input id="personId" type="hidden"  />
	<!--上传弹出框按钮-->
	<div style="with:110px;height:40px;padding:4px 8px;float:left;margin-top:15px;">
		<a href="javascript:void(0)" id="uploadAttachmentBtn" class="easyui-linkbutton">上传附件 </a>
	</div>
	<div style="with:110px;height:40px;padding:4px 8px;float:left;margin-top:15px;">
		<a href="javascript:void(0)" id="downloadAttachmentZip" class="easyui-linkbutton">下载附件包</a>
	</div>
	<div class="easyui-panel browsePanel" title="附件列表" style="width:910px;">
	    <table id="attachmentTab" style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
	    </table>
	</div>
	<div  id="uploadDlg" class="easyui-dialog"  closed="true" style="width:300px;height:200px;">
		<table>
			<tr>
				<td>上传附件</td>
				<td><input id="uploadInput" type="file" /></td>
			</tr>
			<tr>
				<td>标题</td>
				<td><input id="attachmentTitleInput" type="text" required="true" /></td>
			</tr>
			<tr>
				<td><a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="uploadAttachmentSubmitBtn" >提交</a></td>
				<td><a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="uploadAttachmentCloseBtn" >关闭 </a></td>
			</tr>
		</table>
</div>

	
    </div>
      <!-- 新增小企业贷对话框 -->
     	<div id="seLoanAdd" class="windowType" style="top: 20px;height:600px;width:1000px;"></div>  
     	<!-- 查看车贷对话框 -->
	<div id="seLoanDetail" class="windowType" style="top: 20px;height:600px;width:1000px;"></div>   
     <!-- 编辑小企业贷对话框 -->
     	<div id="seLoanModify" class="windowType" style="top: 20px;height:600px;width:1000px;"></div>    
    <!-- 新增车贷对话框 -->
	<div id="carLoanAdd" class="windowType" style="top: 20px;height:600px;width:1200px;"></div>
	<!-- 查看车贷对话框 -->
	<div id="carLoanDetail" class="windowType" style="top: 20px;height:600px;width:1000px;"></div>
		<!-- 查看车贷对话框 -->
	<div id="carLoanExtensionDetail" class="windowType" style="top: 20px;height:600px;width:1000px;"></div>
	<!-- 编辑车贷对话框 -->
	<div id="carLoanModify" class="windowType" style="top: 20px;height:600px;width:1200px;"></div>
	<!--借款日志对话框-->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>
	<!--台账对话框-->
	<div id="showLoanLedgerDlg" style="top: 20px;height:600px;width:1000px;">
		<table id="loanLedgerList"></table>
	</div>
	<!--借款取消弹出选择取消的DIALOG-->
	<div id="showLoanCancelSelect" class="easyui-dialog" title="借款取消" style="width:320px;height:230px;"
    data-options="iconCls:'icon-save',resizable:true,closable:false,closed:true">
    	<div style="margin-left:10px;margin-top:10px;">
	    	<label> <span style="color:red">*</span>取消原因: </label>
	    	<input class="easyui-combobox" id="LoanCancelSelect" data-options="
			valueField: 'label',
			textField: 'value',
			editable:false,
			data: [{
				label: '客户资金已到位，取消申请',
				value: '客户资金已到位，取消申请'
			},{
				label: '放款前发现客户高风险，取消审批',
				value: '放款前发现客户高风险，取消审批'
			}]" />
    	</div>
    	<div style="margin-left:10px;margin-top:10px;">
    		<label>备注: </label>
    		<textarea id="textareabeizhu" rows="4" cols="30"  style="resize:none" >

			</textarea>
    	</div>
    	<div style="margin-top:20px;text-align:center;">
    		 <a id="canCelSaveButton" class="easyui-linkbutton" iconCls="icon-save"  onclick="canCelSaveButton()">提交</a>
    		 <a id="canCelCanCelButton" class="easyui-linkbutton" iconCls="icon-cancel"  onclick="canCelCanCelButton()">取消</a>
    		 <input id="displayLoanId" name="displayLoanId" type="hidden"/>
    	</div>
    	
	</div>
	<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
                var height1 = $(window).height()-20;  
                $('.windowType').css("height",height1);
            })
</script>		
</body>
</html>