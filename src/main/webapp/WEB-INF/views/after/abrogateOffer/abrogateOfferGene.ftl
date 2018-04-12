<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/abrogateOffer/abrogateOfferGene.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/ajaxfileupload.js"></script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 

<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
<table id="abrogateOfferGenePageTb" toolbar="#toolbar"></table>
<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>姓名：</span><input id="personName" type = "text" maxlength="25"  style="width: 90px" />
			<span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" />
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" id="searchBtn" data-options="iconCls:'icon-search'">查询</a>
	        <a class="easyui-linkbutton" id="generateBtn" data-options="iconCls:'icon-search'">生成停止报盘</a>
	        
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
		
			<!--台账对话框-->
		<div id="showLoanLedgerDlg" style="top: 20px;height:600px;width:1000px;">
		<table id="loanLedgerList"></table>
		</div>
</body>