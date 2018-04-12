<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<title>审核工作台 </title>
<base href="${WebConstants.webUrl}${WebConstants.contextPath}/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" charset="UTF-8" src="resources/js/swfupload/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/swfupload/UploadPanel.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/swfupload/UploadPanel.js"></script>
<script type="text/javascript" src="resources/js/swfupload/swfupload.js"></script>
<link rel="stylesheet" href="resources/css/icon.css" type="text/css">
<link rel="stylesheet" href="resources/css/m_style.css" type="text/css">
<link rel="stylesheet" href="resources/css/easyui.css" type="text/css">
<style type="text/css">
    table .tdleft 
    {
        width:16%;
    }
    table .tdright
    {
    	width:40%;
    } 
</style>

<script type="text/javascript" src="resources/js/jquery-1.8.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/m_common.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/m_fuc.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/swfupload/CJL.0.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/swfupload/ImageTrans.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/master/attachment.js" charset="utf-8"></script>


<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/eduLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>

<link rel="stylesheet" type="text/css" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/apply/seLoan.css" />

<script type="text/javascript" charset="utf-8">
    var loanId='${loanId}';
    var productId='${productId}';
    var personId='${personId}';
    var optModule='${optModule}';
    var uploadWindow = null;
    var uploadPanel = null;
    var appname='${WebConstants.webUrl}${WebConstants.contextPath}/';
     var honorUrl='${WebConstants.honorUrl}/';
    var fileSizeLimit='${fileSizeLimit}';
    var fileQueueLimit='${fileQueueLimit}';
    var requestMoney='${loan.requestMoney}';
    var requestTime='${loan.requestTime}';
    var comboTypeSub = null;
    var comboType_data = null;
    var toolBarForm = null;
    var comboType = null;
    var comboTextField = null;
    var comboTypeSubData = [];
     var personIdum = '${personIdum}';
    $(function () {
        //上传图片
        $("idUpload").onclick = function () {
            Ext.QuickTips.init();
            Ext.onReady(function () {
                if (uploadPanel == null) {
                    uploadPanel = Ext.create('Ext.ux.uploadPanel.UploadPanel', {
                        anchor: '100% 88%',
                        addFileBtnText: '选择文件...',
                        uploadBtnText: '上传',
                        removeBtnText: '移除所有',
                        cancelBtnText: '取消上传',
                        width: 1100,
                        file_size_limit: fileSizeLimit,//MB
                        file_queue_limit: fileQueueLimit,
                        upload_url: appname+'fileUpload/imageUpload?loanId=' + loanId+'&personId='+personId+'&optModule='+optModule+'&productId='+productId,
                        border: false
                    });
               		 comboTypeSub = new Ext.form.ComboBox({
                        id: 'subCombo_type',
                        store: new Ext.data.SimpleStore({
                           fields: ["id","name"],
                           data:[  
						     [101,'基本申请资料'],
						     [102,'对公流水'],
						     [103,'对私流水'],
						     [104,'补件'],
						     [105,'其他']
						  ]
                        }),
                        valueField:"id",
                        displayField: "name",
                        forceSelection: true,
                        blankText: '请选择资料二级分类',
                        emptyText: '请选择资料二级分类',
                        editable: false,
                        triggerAction: 'all',
                        allowBlank: true,
                        fieldLabel: '资料二级分类',
                        width: 220,
                        labelWidth: 80
                    });
                    
					 comboType = new Ext.form.ComboBox({
                        id: 'combo_type',
                        store: new Ext.data.SimpleStore({
                           fields: ["id","name"],
                           data:[  
						     [1,'信审资料']
						  ]
                        }),
                        valueField:"id",
                        displayField: "name",
                        forceSelection: true,
                        blankText: '请选择资料分类',
                        emptyText: '请选择资料分类',
                        editable: false,
                        triggerAction: 'all',
                        allowBlank: true,
                        fieldLabel: '资料分类',
                        width: 220,
                        labelWidth: 80
                    });
                    
                    
					    var typeForm = new Ext.form.Panel(
                            {
                                border: false,
                                items: [
                                    new Ext.Toolbar(
                                            {
                                                items: [
                                                    comboType, comboTypeSub 
                                                ]
                                            }
                                    )
                                ]
                            }
                    	); 
                    	
                    uploadWindow = new Ext.Window({
                        title: "批量上传",
                        frame: true,
                        plain: true,
                        resizable: true,
                        layout: 'anchor',
                        closeAction: "hide",
                        width: 1110,
                        height: 370,
                        closable: true,
                        bodyStyle: "padding:4px",
                        listeners: {
                            'hide': function () {
                                location.reload();
                                //$('#showTree').tree('reload');
                            }
                        },
                        items: [
                            typeForm,
                            uploadPanel
                        ]
                    });
                    uploadWindow.show();              
                } 
                else 
                {
                }
            });
        };
    });
</script>
<script type="text/javascript" src="resources/js/audit/eduCreditAuditDetails.js"></script>
<script type="text/javascript" src="resources/js/audit/phoneVerification.js"></script>
<script type="text/javascript" src="resources/js/audit/internalMatching/internalMatching.js"></script>
</head>

<body oncontextmenu="return false">
<div class="easyui-panel" title="附件管理 &nbsp;&nbsp;&nbsp; 借款人: &nbsp;${personName}" fit="true">
	<input id="type" type="hidden" value="2">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'west'" style="width:200px;background: #f1fcfe;">
            <ul id="showTree"></ul>
        </div>

        <div data-options="region:'center'">
            <div id="idContainer" style="height:100%; width:100%;background-color: #f1fcfe;"></div>
        </div>

        <div data-options="region:'south',border:false"
             style="height:30px; line-height:30px; text-align:center; background: #f1fcfe;">
            <input id="idLeft" type="button" value="左转">
            <input id="idRight" type="button" value="右转">
            <input id="idReset" type="button" value="重置">
            <input id="invert" type="button" value="反色">
            <input id="idPrev" type="button" value="上一张">
            <input id="idNext" type="button" value="下一张">
            <input id="idPrint" type="button" value="打印">
        </div>
        <div data-options="region:'east'" style="width:600px;background: #f1fcfe;">
	        <div style="margin-top:20px; height:30px; line-height:30px; text-align:center; background: #f1fcfe;">
	        <input id="honorBt" style="width:10%;" type="button" value="征信信息">
	        <input id="matchingBt" style="width:10%;" type="button" value="内部匹配">
	        <input id="customerBut" style="width:10%;"type="button" value="客户信息">
	        <input id="telCheckBut" style="width:10%;" type="button" value="电核记录">
	        <input id="logBut" style="width:10%;" type="button" value="日志备注">
	        <input id="checkMsgLBut" style="width:15%;" type="button" value="审批意见表">
	        </div>
	        <div  >
	        <span  style="display:none;"> 
 			  <input type="hidden" id="personName" name="personName" value="${personName!""}"  />	
 			  <input type="hidden" id="idnum" name="idnum" value="${personDetails.idnum!""}"  />	
 			  <input type="hidden" id="loanStatus"   value="${loan.status!""}"  />
     	 </span>
	        <table  align="left" style="margin-left:50px;margin-top:5%;">
	        	<tr>
	        	<td class="tdleft"><label>借款人：</label></td><td class="tdright"><label>${personName!}</label></td>
	        	<td class="tdleft"><label>身份证号：</label></td><td class="tdright"><label>${personDetails.idnum!}</label></td>
	        	</tr>
	        	<tr>
	        	<td class="tdleft"><label>产品类型：</label></td><td class="tdright"><label>助学贷</label></td>
	        	<td class="tdleft"><label>月综合费率：</label></td><td class="tdright"><label>${actualRate?string('0.0000')}</label></td>
	        	</tr>
	        	<tr>
	        	<td class="tdleft"><label>申请金额：</label></td><td class="tdright"><label>${loan.requestMoney!}元</label></td>
	        	<td class="tdleft"><label>申请期限：</label></td><td class="tdright"><label>${loan.requestTime!}期</label></td>
	        	</tr>
	        	<tr>
	        	<td class="tdleft"><label>用途支出：</label></td><td class="tdright"><label>${loan.purpose!}</label></td>
	        	<td class="tdleft"><label>营业部：</label></td><td class="tdright"><label>${salesDeptName!}</label></td>
	        	</tr>
	        	<tr>
	        	<td class="tdleft"><label>客户经理：</label></td><td class="tdright"><label>${crmName!}</label></td>
	        	<td class="tdleft"><label>是否结清再贷：</label></td><td class="tdright"><label>${settleType}</label></td>
	        	</tr>
	        	<tr>
	        	<td colspan="2" class="tdleft"><label>备注：</label></td>
	        	<td colspan="2" class="tdright"><label>${remark!}</label></td>
	        	</tr>
	        </table>
	       	 </div>
	       	 <div style="margin-top:65%;  line-height:30px; text-align:center; background: #f1fcfe;">
	       	 <br/>
		       <span><input id="agreeBut" style="width:10%;" type="button" value="同意"></span>
		        <span><input id="sendBackBut" style="width:10%;"type="button" value="退回"></span>
		       <span><input id="refuseBut" style="width:10%;" type="button" value="拒贷"></span>
		        <br/>
	       	 </div>
        </div>
    </div>
</div>

<div id="mm" class="easyui-menu" style="width:120px;">
    <div onclick="downloadAttach()" data-options="iconCls:'icon-save'">下载</div>
     <div onclick="deleteAttach()" data-options="iconCls:'icon-cancel'">删除</div>
</div>

<div id="mmParent" class="easyui-menu" style="width:120px;">
     <div onclick="batchDownloadAttach()" data-options="iconCls:'icon-save'">批量下载</div>
     <div onclick="batchDeleteAttach()" data-options="iconCls:'icon-cancel'">批量删除</div>
</div>

	<div id="agreePanel" class="easyui-window" title="同意审核" style="width:600px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="agreeForm"  method="post" enctype="multipart/form-dat">
   				<table id="agreeTb" style="font-size:12px; width:100%; height:100%;text-align:left;">
					<tr>
						<td >
							<label style='float:left;margin-left:10px'>借款金额</label><input style='float:left;margin-left:10px' id="agreeMoneyInput" type="text" required=true digits=true min=1 max=1000000 readonly/>
	    					<span style='float:left;margin-left:10px'>元</span>
	    					<label style='float:left;margin-left:10px'>期限</label>
	    					<input style='float:left;margin-left:10px' id="agreeTimeComb" type="text" required=true digits=true min=1 max=24 readonly/>
	    				</td>
    				</tr>
            <tr><td ><label style="margin-left:10px">签约事项</label></td></tr>
            <tr>
            <td> <textarea id="contractMatters" rows="5" cols="70"  maxlength="200" /></textarea></td>
            </tr>
    				<tr>
	    				<td> <label style='float:left;margin-left:10px'>备注</label></td>
	    				</tr>
	    				<tr>
			            <td> <textarea id="agreeReason" rows="10" cols="70" maxlength="290"/></textarea></td>
			            </tr>
	    				<tr>
	    				<td >
	    					<a href="javascript:void(0)" id="agreeSubmitBt"  onclick="agreeLApproveSubmit()">提交</a>
	    					<a href="javascript:void(0)"  style="margin-left:10px" id="agreeCancelBt" class="commonCloseBut" >取消</a>
	    				</td>
    				</tr>
    			</table>
        </form>
        </div>
    </div>
    
    <div id="sendBackPanel" class="easyui-window" title="退回原因" style="width:600px" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
         <form id="returnForm"  method="post" enctype="multipart/form-dat">
        			<table id="returnTable" style="font-size:12px; width:100%;text-align:left; "> 
    			<tr><td ><label>退回原因</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
					<tr>
	    				<td >							
	    				<textarea id="returnContractMatters" rows="10" cols="70" required=true maxlength="700"></textarea>
	    				</td>
    				</tr>
    				<tr><td ><label >备注</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>    				
    				<tr>
	    				<td >	    				 				
	    				<textarea id="returnReason" rows="10" cols="70" required=true maxlength="290"></textarea></td>
	    				</tr>
	    				<tr>
	    				<td>
	    					<a href="javascript:void(0)" id="returnCancelBt"  onclick="sendBackToLApproveSubmit()">退回到门店</a>
	    					<a href="javascript:void(0)" id="returnCancelBt2"  onclick="sendBackToFApproveSubmit()">退回到初审</a>
	    					<a href="javascript:void(0)" style="margin-left:10px" id="returnCancel" class=" commonCloseBut" >取消</a>
	    				</td>
    				</tr>
    			</table>
    		</form>
        </div>
    </div>
    
    <div id="refusePanel" class="easyui-window" title="拒贷原因" style="width:600px" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="refuseForm"  method="post" enctype="multipart/form-dat">
       			<table id="refuseTb" style="font-size:12px; width:100%;text-align:left; "> 
       			    <tr>
	    				<td ><label>拒贷原因:</label></td>
	    			</tr>
					<tr>
	    				<td>
		    				<label style="margin-right:10px">一级原因</label>
		    				<input id="refuseFirstReason" style="margin-right:10px" class="easyui-combobox" editable="false" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
		    			    <label style="margin-right:10px" >二级原因</label><input id="refuseSecondReason" style="margin-right:10px" class="easyui-combobox" editable="false" data-options="width:140" />
		    				
	    				</td>
    				</tr>
    				<tr>
	    				<td ><textarea id="refuseRemarkTA" rows="10" cols="70" maxlength="390"/></textarea></td>
	    				</tr>
	    			<tr>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt"  onclick="refuseLApproveSubmit()">提交</a>
	    					<a href="javascript:void(0)" style="margin-right:10px" id="refuseCancelBt" class=" commonCloseBut" >取消</a>
	    				</td>
    				</tr>
    			</table>
        </form>
        </div>
    </div>
    
    <div id="hangUpPanel" class="easyui-window" title="挂起" style="width:600px" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
        <div style="padding:10px 10px 10px 10px">
        <form id="hangUpForm"  method="post" enctype="multipart/form-dat">
        <div><span><label>挂起原因:</label></span>
        <br/>
        <span>
            <textarea id="hangUp1" name="hangUp" rows="3" cols="80"  ></textarea>
        </span>
        </div>
        </form>
         <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class=" editBtn" onclick="doHangUpLoan()">确定</a>
            <a href="javascript:void(0)" class=" commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    
    <!--借款日志对话框-->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>  
 <!--电核对话框-->
	<div id="phoneVerificationDlg" style="top: 20px;height:600px;width:1000px;"></div>   
<div id="seLoanModify" style="top: 20px;height:600px;width:1000px;"></div>  

<img id="printImg" style="display:none;" />
</body>
</html>