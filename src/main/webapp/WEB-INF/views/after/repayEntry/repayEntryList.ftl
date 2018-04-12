<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/repayEntry/repayEntry.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/repayEntry/repayEntryModify.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/ajaxfileupload.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>
 	<table id="list_result" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:auto;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>姓名：</span><input type="text" id="personFuzzyName" maxlength="25"  style="width: 90px" />  
	    <span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" />  
       
         </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	          <a class="easyui-linkbutton" id="uploadBtn">批量导入</a>
	      <div id="result"></div>
	     
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
		
		
	
	<!-- 编辑车贷对话框 -->
	<div id="repayModify" style="top: 20px;height:550px;width:1020px;"></div>
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