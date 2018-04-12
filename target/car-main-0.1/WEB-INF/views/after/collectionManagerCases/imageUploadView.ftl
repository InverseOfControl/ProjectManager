<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<title>图片上传 </title>
<base href="${WebConstants.webUrl}${WebConstants.contextPath}/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="resources/css/swfupload/ext-all.css" />
<link rel="stylesheet" type="text/css" href="resources/css/swfupload/ext-path.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/swfupload/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/swfupload/UploadPanel.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/swfupload/UploadPanel.js"></script>
<script type="text/javascript" src="resources/js/swfupload/swfupload.js"></script>
<link rel="stylesheet" href="resources/css/swfupload/easyui.css" type="text/css">
<link rel="stylesheet" href="resources/css/swfupload/icon.css" type="text/css">
<link rel="stylesheet" href="resources/css/swfupload/m_style.css" type="text/css">

<script type="text/javascript" src="resources/js/jquery-1.8.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/m_common.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/m_fuc.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/swfupload/CJL.0.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/swfupload/ImageTrans.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/master/attachment.js" charset="utf-8"></script>

<script type="text/javascript" charset="utf-8">
    var loanId='${loanId}';
    var productId='${productId}';
    var personId='${personId}';
    var optModule=30;
    var uploadWindow = null;
    var uploadPanel = null;
    var appname='${WebConstants.webUrl}${WebConstants.contextPath}/';
    var fileSizeLimit='${fileSizeLimit}';
    var fileQueueLimit='${fileQueueLimit}';
    var comboTypeSub = null;
    var comboType_data = null;
    var toolBarForm = null;
    var comboType = null;
    var comboTextField = null;
    var comboTypeSubData = [];
    
    $(function () {
      
        //上传图片
        $$("idUpload").onclick = function () {
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
                        upload_url: appname+'fileUpload/imageUpload?loanId=' + loanId+'&personId='+personId+'&optModule='+optModule,
                        border: false
                    });
               		 comboType = new Ext.form.ComboBox({
                        id: 'combo_type',
                        store: new Ext.data.SimpleStore({
                           fields: ["id","name"],
                           data:[  
						     [6,'外访资料']
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
                                                    comboType
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
     //批量删除
function batchDeleteAttach11() {
     var node = $('#showTree').tree('getSelected');
     if (node == null || typeof(node.attributes) == 'object') {
          Ext.Msg.alert('提示' ,'批量删除请选中上级目录菜单才可进行操作');
     }
     else
     {
     	if(node.text!="外访资料"){
	 	 Ext.Msg.alert("操作提示", "不能删除！" );
	 	 return  false;
		}
   		Ext.MessageBox.confirm('提示', '是否批量删除文件菜单['+node.text+']下所有文件', batchDeleteAttachResult);    
     }
}
    //删除附件
function deleteAttachResult1(btn) {
    if(btn == 'yes'){
	 var node = $('#showTree').tree('getSelected');
	 var parentNode=$('showTree').tree('getParent',node.target);
	 if(parentNode.text!="外访资料"){
	 	 Ext.Msg.alert("操作提示", "不能删除！" );
	 	 return  false;
		}
	 
     $.ajax({
            type: "POST",
            url: appname+'fileUpload/deleteAttachmentDetail',
            data: {attachmentDetailId: node.attributes.attachmentDetailId,optModule:optModule},
            success: function (response) {
             	location.reload();
                $('#showTree').tree('reload');
            },
            error: function (msg) {
                alert('删除失败!');
            }
        });
    }
}

function deleteAttach1() {
	  var node = $('#showTree').tree('getSelected');
    //$('#showTree').tree('remove', node.target);
    Ext.MessageBox.confirm('提示', '是否删除附件['+node.text+']', deleteAttachResult1);    
}
//下载附件
function downloadAttach1() {
    var node = $('#showTree').tree('getSelected');
    var fileDownLoadPath;
    window.open(appname+'fileUpload/downloadAttachmentDetail?attachmentDetailId=' + node.attributes.attachmentDetailId+'&optModule='+optModule, "_blank");
    return;
}
</script>

</head>

<body oncontextmenu="return false">
<div class="easyui-panel" title="附件管理 &nbsp;&nbsp;&nbsp; 借款人: &nbsp;${personName}" fit="true">
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
            <!--客户经理,客服,复核客服,副理,经理 -->
	        
            <input id="idUpload" type="button" value="批量上传">
            <input onclick="deleteAttach1()" type="button" value="删除">
            <input onclick="downloadAttach1()"  type="button" value="下载"> 
             
            <input id="idPrint" type="button" value="打印">
	  
        </div>
    </div>
</div>  <!--

  <div id="mm" class="easyui-menu" style="width:120px;">
    <div onclick="downloadAttach()" data-options="iconCls:'icon-save'">下载</div>
      <!--客户经理,客服,复核客服,副理,经理 -->
	  <#if !(userType == 2 || userType == 3 || userType == 5 || userType == 6)>
      <div onclick="deleteAttach()" data-options="iconCls:'icon-cancel'">删除</div>
      </#if>
</div>

<div id="mmParent" class="easyui-menu" style="width:120px;">
     <div onclick="batchDownloadAttach()" data-options="iconCls:'icon-save'">批量下载</div>
     <!--客户经理,客服,复核客服,副理,经理 -->
	 <#if !(userType == 2 || userType == 3 || userType == 5 || userType == 6)>
     <div onclick="batchDeleteAttach11()" data-options="iconCls:'icon-cancel'">批量删除</div>
     </#if>
</div>
<img id="printImg" style="display:none;" />
</body>
</html>