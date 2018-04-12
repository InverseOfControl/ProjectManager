<#include "../../macros/constant_output_macro.ftl">
<#assign staticPath="audit/contract" />
<#assign staticName="contract" />
<@htmlCommonHead/>
</head>
<body>
<input type="hidden" name="loanId" id="loanId" value="${loanId!''}" />
<table id="business_log_result"></table>
</body>
</html>