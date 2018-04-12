<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
</head>
<body class="easyui-layout">
	 
	 
	<div data-options="region:'center'" style="overflow:hidden;height:120px; ">
		<div class="easyui-layout" data-options="fit:true">
	
			<div id="centerTabs" class="easyui-tabs" data-options="region:'center',border:false,tools:'#tab-tools'">
				<div title="借款信息">	<iframe id="iframe1" src="comprehensiveSearch/loanDetails?loanId=${loanId}&extenId=${extenId}&extensionTime=${extensionTime}" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
				<div title="还款汇总信息"><iframe id="iframe2" src="comprehensiveSearch/repaymentSummaryInfo?loanId=${loanId}&extenId=${extenId}&extensionTime=${extensionTime}" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
				<div title="还款详细信息"><iframe id="iframe3" src="comprehensiveSearch/repaymentPLanListUi?loanId=${loanId}&extenId=${extenId}&extensionTime=${extensionTime}" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
				<div title="帐卡信息">	<iframe id="iframe4" src="comprehensiveSearch/accountCardMainUi?loanId=${loanId}&extenId=${extenId}&extensionTime=${extensionTime}" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
			</div>
		</div>
	</div>
	 
	  
</body>
</html>