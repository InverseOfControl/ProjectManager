<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/report/overdueMsgLogList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
</head>
<body>
	<table id="overdueMsg" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
        <span>姓名：</span><input  id="name"  maxlength="25"  style="width: 150px" class="easyui-validatebox"/>
       
        <span>身份证：</span><input  id="idNum"  maxlength="25"  style="width: 150px" class="easyui-validatebox"/> 
        <span>生成日期：</span>
     	<input id="buildStartDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input>
     	&nbsp;—&nbsp;
     	<input id="buildEndDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input>
     	
	    <span>发送日期：</span>
     	<input id="sendStartDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input>
     	&nbsp;—&nbsp;
     	<input id="sendEndDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input>
       	<span>发送状态:</span>
       		<select id="status" editable="false" class="easyui-combobox" data-options="width:80">  
        	<option value="0">全部</option>
            <option value="1">待发送</option>
            <option value="3">发送成功</option>		 
            <option value="4">发送失败</option>	 	
        </select>
        </div>
         <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" onclick='searchOverdueMsg()' id="searchBtn">查询</a>
		 </div>
	</div>
 </body>
</html>