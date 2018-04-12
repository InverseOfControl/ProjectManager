<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/productList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/carProductView.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/seProductView.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/carProductEdit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/seProductEdit.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
<script type="text/javascript">
var enumJson='${gridEnumJson}';
</script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>借款类型：</span><input id="productComb" class="easyui-combobox"  editable="false"  data-options="width:80"/>  
	     <span>网点：</span><input id="cityComb" class="easyui-combobox"  editable="false" data-options="width:150"/>  
      </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	       <!--  <a href="javascript:void(0)" id="createBt" class="easyui-linkbutton" iconCls="icon-add" plain="true" >新增</a>-->
	        <a id="createBt" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="productAdd()">新建</a>
		 </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
 	 <!-- 新增产品对话框 -->
    <div id="productAddDlg"  style="top: 20px;height:600px;width:1000px;"></div>  
     <!-- 查看车贷产品对话框 -->
	<div id="carProductViewDlg" style="top: 20px;height:600px;width:1000px;"></div>   
     <!-- 查看小企业贷产品对话框 -->
	<div id="seProductViewDlg" style="top: 20px;height:600px;width:1000px;"></div>   
     <!-- 编辑车贷产品对话框 -->
	<div id="carProductEditDlg" style="top: 20px;height:600px;width:1000px;"></div>   
     <!-- 编辑小企业贷产品对话框 -->
	<div id="seProductEditDlg" style="top: 20px;height:600px;width:1000px;"></div>   
     
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