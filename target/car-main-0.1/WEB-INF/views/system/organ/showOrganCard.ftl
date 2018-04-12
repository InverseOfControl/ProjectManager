<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/system/organ/showOrganCard.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
</head>
<body>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	      <span>交易开始日期: </span>
	      <input id="tradeDateStart" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input> 
	      <span>交易结束日期: </span>
	      <input id="tradeDateEnd" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
	      <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	    </div>
	    <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
			  <tr>
			    <td>
			        <label>机构名称: </label>
			        <label id="organName">${organName}</label>
			    </td>
			    <td>
				    <label>机构代码: </label>
				    <label id="organCode">${organCode}</label>
			    </td>
			    <td>
			    	<label>机构总收入: </label>
			    	<label id="organTotalIncome"></label>
			    </td>
			    <td>
			    	<label>机构总支出: </label>
			    	<label id="organTotalExpense"></label>
			    </td>
			    <td>
			    	<label>挂账金额: </label>
			    	<label id="organCash"></label>
			    </td>
			  </tr>
			</table>
		</div>
	</div>
	<table id="list_result" toolbar="#toolbar"></table>
	
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