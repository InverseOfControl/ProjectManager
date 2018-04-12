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
    var inType='${inType}';
    var productId='${productId}';
    var personId='${personId}';
    var optModule='${optModule}';
    var uploadWindow = null;
    var uploadPanel = null;
    var appname='${WebConstants.webUrl}${WebConstants.contextPath}/';
    var fileSizeLimit='${fileSizeLimit}';
    var fileQueueLimit='${fileQueueLimit}';
    var requestMoney='${loan.requestMoney}';
    var requestTime='${loan.requestTime}';
    var honorUrl='${WebConstants.honorUrl}/';
   
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
<script type="text/javascript" src="resources/js/audit/eduCreditAuditDetailsScan.js"></script>
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
	        <input id="telCheckButScan" style="width:10%;" type="button" value="电核记录">
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