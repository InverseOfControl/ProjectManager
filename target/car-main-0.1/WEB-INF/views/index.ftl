<#include "macros/constant_output_macro_noBaseHref.ftl">
<#assign title="证大车贷管理系统"/>
<@htmlCommonHead/>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px; padding:12px 5px 0; background:#E4EDFE; text-align:right; line-height:1.5em;">
		<img src="resources/css/m_images/last.png" class="fl"/>
		<span>【<#if username??>${username}</#if>】，欢迎您。 ${today}</span>
		<br />
		<a href="system/loginout" class="easyui-linkbutton fr" data-options="plain:true,iconCls:'icon-no'">退出</a>
		<a id="modifyPwd" class="easyui-linkbutton fr" data-options="plain:true,iconCls:'icon-m-set'">修改密码</a>
	</div>
	<div data-options="region:'west',title:'菜单',split:true,tools:'#tt'" style="width:150px;">
		<ul id="sidebar" style="padding:5px;">
	</div>
	<input id="loanApplyLinkFlag" type="hidden" value="true"></input>
	<input id="loanConfirmLinkFlag" type="hidden" value="true"></input>
	<input id="auditResolutionLinkFlag" type="hidden" value="true"></input>
	<input id="contractGenLinkFlag" type="hidden" value="true"></input>
	<input id="contractConfirmLinkFlag" type="hidden" value="true"></input>
	<input id="financeAuditLinkFlag" type="hidden" value="true"></input>
	<input id="financialGrantLinkFlag" type="hidden" value="true"></input>
	<div data-options="region:'center'" style="overflow:hidden;">
		 <div class="easyui-layout" data-options="fit:true">
			<!--<div class="right_header" title="快捷选项" data-options="region:'north',split:true">
				<ul>
					<li><a id="loanApplyLink"><img src="resources/css/m_images/loanapply.png" alt="" permissionCode="loanApply"/></a><p>借贷申请</p></li>
					<li><a id="loanConfirmLink"><img src="resources/css/m_images/loanconfirm.png" alt="" permissionCode="loanConfirm"/></a><p>借款提交</p></li>
					<li><img src="resources/css/m_images/check.png"  alt="" style="cursor:none"/><p>实地考察</p></li>
					<li><a id="auditResolutionLink"><img src="resources/css/m_images/audit.png" alt="" permissionCode="auditResolution"/></a><p>审贷签批</p></li>
					<li><a id="contractGenLink"><img src="resources/css/m_images/contractgenerate.png" alt="" permissionCode="contractGen"/></a><p>合同生成</p></li>
					<li><a id="contractConfirmLink"><img src="resources/css/m_images/contractconfirm.png" alt="" permissionCode="contractConfirm"/></a><p>合同确认</p></li>
					<li><a id="financeAuditLink"><img src="resources/css/m_images/financeaudit.png" alt="" permissionCode="financeAudit"/></a><p>财务审核</p></li>
					<li><a id="financialGrantLink"><img src="resources/css/m_images/financeloan.png" alt="" permissionCode="financialGrant"/></a><p>财务放款</p></li>
				</ul>
			</div> -->
			<div id="centerTabs" class="easyui-tabs" data-options="region:'center',border:false,tools:'#tab-tools'">
				<div title="首页"><iframe src="home.html" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:30px; line-height:30px; text-align:center; background:#e4edfe;">COPYRIGHT BY 证大财富 系统版本号<#if tagVersion??>${tagVersion}</#if></div>
	<!-- 左侧菜单栏tools -->
	<div id="tt">
		<a href="javascript:void(0)" class="icon-reload" onclick="reload()"></a> 
		<a href="javascript:void(0)" class="icon-redo" onclick="expandAll()"></a>  
		<a href="javascript:void(0)" class="icon-undo" onclick="collapseAll()"></a>
	</div>
	<!-- 选项卡tools -->
	<div id="tab-tools">  
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="refreshTab()"></a>  
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" onclick="removeTab()"></a>  
	</div>
	<!-- 选项卡右键event -->
	<div id="centerTabsMenu" style="width:120px;display:none;">
		<div type="refresh">刷新</div>
		<div class="menu-sep"></div>
		<div type="close">关闭</div>
		<div type="closeOther">关闭其他</div>
		<div type="closeAll">关闭所有</div>
	</div>
	<script type="text/javascript" charset="UTF-8">
		$(function(){
		 	$("#modifyPwd").click(function(){       
                    	addTab({
							src : 'system/modifyPwdInfo',
							title : '修改密码'
							
						});
             });
             $("#loanApplyLink").bind('click',function(){     
             			if($("#loanApplyLinkFlag").val()=="true")
             			{
	                      	addTab({
								src : 'apply/applyMain',
								title : '借款申请'
							});
						}
             });
            
             $("#loanConfirmLink").click(function(){    
            			if($("#loanConfirmLinkFlag").val()=="true")
             			{  
	                      	addTab({
								src : 'apply/applyMain',
								title : '借款提交'
							});
						}
             });
            $("#auditResolutionLink").click(function(){ 
            			if($("#auditResolutionLinkFlag").val()=="true")
             			{       
	                      	addTab({
								src : 'audit/auditMain',
								title : '审贷会决议'
							});
						}
             });
            $("#contractGenLink").click(function(){ 
            			if($("#contractGenLinkFlag").val()=="true")
             			{       
	                      	addTab({
								src : 'audit/contract/list',
								title : '合同生成'
							});
						}
             });
             $("#contractConfirmLink").click(function(){  
            			if($("#contractConfirmLinkFlag").val()=="true")
             			{   
             		
	                      	addTab({
								src : 'audit/contractConfirm/list',
								title : '合同确认'
							});
						}
             });
               $("#financeAuditLink").click(function(){ 
            			if($("#financeAuditLinkFlag").val()=="true")
             			{       
	                      	addTab({
								src : 'finance/financialAudit/list',
								title : '财务审核'
							});
						}
             });
               $("#financialGrantLink").click(function(){ 
            			if($("#financialGrantLinkFlag").val()=="true")
             			{       
	                      	addTab({
								src : 'financialGrant/list',
								title : '财务放款'
							});
						}
             });
			$('#sidebar').tree({
				url : 'json/west.json',
				lines : true,
				onClick : function(node){
					if(node.attributes && node.attributes.src){
						addTab({
							src : node.attributes.src,
							title : node.text
						});
					};
				}
			});
			$('#centerTabsMenu').menu({
				onClick : function(item){
					var curTabTitle = $(this).data('tabTitle');
					var type = $(item.target).attr('type');
					if (type === 'refresh') {
						refreshTab(curTabTitle);
						return;
					};
					if (type === 'close') {
						removeTab(curTabTitle);
						return;
					};
					var allTabs = $('#centerTabs').tabs('tabs');
					var closeTabsTitle = [];
					$.each(allTabs, function(){
						var opt = $(this).panel('options');
						if (opt.closable && opt.title != curTabTitle && type === 'closeOther'){
							closeTabsTitle.push(opt.title);
						}else if (opt.closable && type === 'closeAll'){
							closeTabsTitle.push(opt.title);
						}
					});
					for(var i = 0; i < closeTabsTitle.length; i++){
						$('#centerTabs').tabs('close',closeTabsTitle[i]);
					}
				}
			});
			$('#centerTabs').tabs({
				onContextMenu : function(e,title){
					e.preventDefault();
					$("#centerTabsMenu").menu('show',{
						left : e.pageX,
						top : e.pageY
					}).data('tabTitle', title);
				}
			});
			//左侧菜单和tab的title同步
			$('.tabs-header','#centerTabs').find("li").live("click",function(){
				var title = $(this).find(".tabs-title").html();
				setTimeout(function(){
					$('.tree-node','#sidebar').each(function(){
						if($(this).children(".tree-title").html()==title){
							collapseAll();
							$(this).click();
							var node = $('#sidebar').tree('getSelected');
							var parentnode = $('#sidebar').tree('getParent',node.target);
							var parentClose = function(){
								if(parentnode){
									$('#sidebar').tree('expand',parentnode.target);
								}else{
									return;
								};
								parentnode = $('#sidebar').tree('getParent',parentnode.target);
								parentClose();
							};
							parentClose();
						};
					});
				},0);
			});
		});
		function reload(){  
			$('#sidebar').tree('reload');		
		};
		function expandAll(){  
			$('#sidebar').tree('expandAll');
		};
		function collapseAll(){ 
			var node = $('#sidebar').tree('getRoot');
			$('#sidebar').tree('collapseAll').tree('expand',node.target);
		};
		function addTab(opts) {
			var src=opts.src;
			var subChar=src.substring(src.length-1,src.length);
			if(subChar=="_"){
				src=src.split("_")[0];
				src=src.replace("${WebConstants.contextPath}","${WebConstants.contextReportPath}");
				 
				var cookie=getCookie();
				
				opts.src="${WebConstants.webReportUrl}"+src+"?isExternal=true&cookie="+cookie;
				var ul="${WebConstants.webReportUrl}"+src+"?isExternal=true&cookie="+cookie;
			}
			var options = $.extend({
				content : '<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>',
				closable : true
			}, opts);
			if ($('#centerTabs').tabs('exists', options.title)) {
				$('#centerTabs').tabs('select', options.title);
			}else{
				$('#centerTabs').tabs('add', options);			
			};
		};
		function refreshTab(title){
			if(title){
				$('#centerTabs').tabs('select',title);
			};
			var tab	= $('#centerTabs').tabs('getSelected');
			var index = $('#centerTabs').tabs('getTabIndex',tab);
			var src = tab.panel('panel').find('iframe').eq(0).attr('src');
			$('#centerTabs').tabs('update',{
				tab:tab,
				options:{
					content:'<iframe src="' + src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>'
				}
			});
		};
		function removeTab(title){
			if(title){
				$('#centerTabs').tabs('select',title);
			};
			var tab	= $('#centerTabs').tabs('getSelected');
			var index = $('#centerTabs').tabs('getTabIndex',tab);
			if(tab.panel('options').closable){
				$('#centerTabs').tabs('close',index);
			}else{
				$.messager.alert('提示', '<strong>[' + tab.panel('options').title + ']</strong>不可以被关闭', 'error');
			};
		};
		function getCookie(){
			var cookieName="${WebConstants.cookieKey}";
			var acookie=document.cookie.split(";");
				for(var i=0;i<acookie.length;i++){
					var arr=acookie[i].split("="); 
					if(cookieName==$.trim(arr[0])){
						if(arr.length>1) {
							var str=arr[1]
							return str;
							 
						}else{ 
							return ""; 
							}
					}
				
				}
		}
		function setPermission(){
		if(document.readyState == "complete") {
			clearTimeout(startPermission);//执行成功，清除监听
			var permissionStr    =",${ApplicationContext.getUser().getPermissionStr()},";
			var permissionResource = $("[permissionCode]");
			if(permissionResource){
				for(var i = 0; i < permissionResource.length; i++){
					var resourceCode = $(permissionResource[i]).attr("permissionCode");
					//若存在code，则判断code是否在权限code中，若不存在则处理
					if(resourceCode){
						//若在权限码中不存在buttonCode，则资源不显示
						if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="loanApply"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/noloanapply.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#loanApplyLinkFlag").val("false");
								$("[permissionCode='loanConfirm']").attr("src","resources/css/m_images/noloanconfirm.png");
								$("[permissionCode='loanConfirm']").attr("style","cursor:none");
								$("#loanConfirmLinkFlag").val("false");
						}
						else if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="auditResolution"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/noaudit.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#auditResolutionLinkFlag").val("false");
						}
						else if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="contractGen"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/nocontractgenerate.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#contractGenLinkFlag").val("false");
						}
						else if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="contractConfirm"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/nocontractconfirm.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#contractConfirmLinkFlag").val("false");
						}
						else if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="financeAudit"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/nofinanceaudit.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#financeAuditLinkFlag").val("false");
						}
						else if(permissionStr.indexOf(","+resourceCode+",") == -1 && resourceCode=="financialGrant"){
								$("[permissionCode='"+resourceCode+"']").attr("src","resources/css/m_images/nofinanceloan.png");
								$("[permissionCode='"+resourceCode+"']").attr("style","cursor:none");
								$("#financialGrantLinkFlag").val("false");
						}
					}
				}
			}
		}else{
			startPermission = setTimeout("setPermission()", 5);
		}
	}
	setPermission();
	</script>
</body>
</html>