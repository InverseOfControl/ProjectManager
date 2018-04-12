<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>

<script type="text/javascript" charset="UTF-8" src="resources/js/audit/finalApprove/finalApproveMain.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="easyui-layout">
	<input id="loanApplyLinkFlag" type="hidden" value="true"></input>
	<input id="loanConfirmLinkFlag" type="hidden" value="true"></input>
	<input id="auditResolutionLinkFlag" type="hidden" value="true"></input>
	<input id="contractGenLinkFlag" type="hidden" value="true"></input>
	<input id="contractConfirmLinkFlag" type="hidden" value="true"></input>
	<input id="financeAuditLinkFlag" type="hidden" value="true"></input>
	<input id="financialGrantLinkFlag" type="hidden" value="true"></input>
	<div data-options="region:'center'" style="overflow:hidden;height:120px; ">
		<div class="easyui-layout" data-options="fit:true">
			<div class="right_header" data-options="region:'north'" style="height:110px; ">
				 <div id="toolbar" style="height:120px; ">
				<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     						<span>姓名：</span><input  id="personName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
							<span>身份证号：</span><input  id="personIdnum" maxlength="25" style="width: 180px" class="easyui-validatebox" />  
							<span>状态：</span>
							<select  id="state"  editable="false"  class="easyui-combobox"  data-options="width:120,
								onSelect: function(rec){    
          						  
          						   $('#stateValue').val($('#state').combobox('getValue'));  
       						 }
							">  
							<option value="">默认</option>  
							<option value="all">全部</option>
							<option value="32">终审中</option>	 
							<option value="34">初审拒绝</option>	 
							<option value="35">终审拒绝</option>	 
							<option value="36">初审退回</option>	 
							<option value="38">终审退回门店</option>	 
							<option value="39">终审退回初审</option>	
							<option value="48">初审重提</option>	 
							<option value="60">合同签订</option>	 
							<option value="70">合同确认</option>	 
							<option value="80">合同确认退回</option>	 
							<option value="90">财务审核</option>	 
							<option value="100">财务审核退回</option>	 
							<option value="110">财务放款</option>	 
							<option value="120">财务放款退回</option>	 
							<option value="130">正常</option>	 
							<option value="140">逾期</option>	 
							<option value="141">预结清</option>	 
							<option value="150">结清</option>	 
							<option value="160">坏账</option>	 
							<option value="200">取消</option>	 
               				 
      			  	</select>
      			  	<span>申请日期：</span><input  id="startDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>  - 
      			  				<input  id="endDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"/>
						<span>审批日期：</span><input  id="auditStartDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>  - 
      			  				<input  id="auditEndDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"/> 	 
      			 	<input   id="stateValue" maxlength="25" type="hidden" value="" style="width:180px" class="easyui-validatebox" />
      			 	<input   id="acceptAuditValue" maxlength="25" type="hidden" value="${acceptAudit}" style="width:180px" class="easyui-validatebox" />
      			  
        </div>
          <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <#if userLoginName=="00239290">
	        <a href="javascript:void(0)" id="changeToFirstBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">恢复审核</a> 
	        </#if>
		</div>	
	</div>
			</div>
			<div id="centerTabs" class="easyui-tabs" data-options="region:'center',border:false,tools:'#tab-tools'">
				<div title="审核列表">	<iframe id="iframe1" src="finalApprove/finalApproveData/toApproveTab" frameborder="0" style="border:0;width:100%;height:100%;"></iframe></div>
			</div>
		</div>
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
              
              $("#financialGrantLink2").click(function(){ 
            			if($("#financeAuditLinkFlag").val()=="true")
             			{       
	                      	addTab({
								src : 'reply/replyData/list',
								title : '方案批复'
							});
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