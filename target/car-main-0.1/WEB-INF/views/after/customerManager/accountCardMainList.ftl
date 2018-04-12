<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/customerManager/accountCardMainList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/ajaxfileupload.js"></script> 
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>

 	<table id="list_result"  ></table>
	 
	<div id="accountCardDetailsDlg" style="top: 20px;height:400px;width:700px;">
		<table id="accountCardDetailsList"></table>
	</div>
	<span  style="display:none;"> 
   <input type="hidden" id="loanId"  value="${loanId}"  />	
    <input type="hidden" id="extensionTime"  value="${extensionTime}"  />	
   </span>
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