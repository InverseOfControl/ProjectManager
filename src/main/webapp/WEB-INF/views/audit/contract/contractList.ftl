<#include "../../macros/constant_output_macro.ftl">
<#include "../../macros/multiSearchMacro.ftl">
<@htmlCommonHead/>


<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/report/LodopFuncs.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/contract/contract.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/contract/contractPrint.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

<script type="text/javascript" charset="UTF-8" src="resources/js/dwr/engine.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/dwr/util.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/dwr/dwr.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/lcbOpt.js"></script>


<script type="text/javascript">
	var enumJson='${gridEnumJson}';
	
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true);
</script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#ff table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            width:110px
        }
        #ff table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:left;
            text-indent:1em;
             width:120px
        }
        #ff table tr td:nth-child(even){
            background: #FFFFFF;
        }
</style>
</head>
<body>
	<table id="list_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<@htmlMultiSearch/>
     	<span>借款状态：</span><input id="loanStatusContractComb" class="easyui-combobox" editable="false" data-options="width:120" />
     	 <span>签批日期：</span>
     	 <input id="auditDateStartDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
  		 &nbsp;&nbsp;&nbsp;
     	 <input id="auditDateEndDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			
	        <a href="javascript:void(0)" id="searchContractBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        	<#if userType==10>
	        	<a href="javascript:void(0)" id="exportExcelLogBt" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出生成合同日志</a> 
				</#if>
		</div>
	</div>
	<!-- 生成合同对话框 -->
	<div id="generateContractDlg" class="easyui-dialog" closed="true"></div>
	<!-- 捞财宝注册弹出框 -->
	<div id="lcbRegisterDlg" class="easyui-dialog" closed="true"></div>
	<!-- 捞财宝注册弹出框 -->
	<div id="lcbLoginDlg" class="easyui-dialog" closed="true"></div>
	<!-- 电子签章回调页面 --> 
	<div id="eleSignCallbackPage" class="easyui-dialog" closed="true"></div>
	<!-- 生成展期合同对话框 -->
	<div id="generateExtensionContractDlg" class="easyui-dialog" closed="true"></div>
	<!-- 审核日志对话框 -->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>
	<!-- 导出合同日志对话框 -->
	<div id="exportLogDlg" class="easyui-dialog" style="width: 980px;height: 220px;overflow-y:scroll" closed="true" data-options="resizable:true">
		 <form id="exportLogForm" method="post">
		 	<div id="exportLogTab" title="合同日志" style="padding:20px">
		 	  <table style="font-size:12px; width:100%; text-align:left;height: 100%">
                    <tr>
                        <td><label>起始时间</label></td>
                        <td align="left"> <input id="operatorStartTime" name="operatorStartTime" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input></td>
                        <td><label>结束时间</label></td>
                        <td align="left"> <input id="operatorEndTime" name="operatorEndTime" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input></td>
                    </tr>
                    <tr class="newSeloanBrowse">
                       <td></td> <td><a class="easyui-linkbutton" iconCls="icon-save" onclick="submitLog()">导出</a></td>
                       <td></td> <td><a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#exportLogDlg').dialog('close')">关闭</a></td>
                    </tr>
                </table>
            </div>
            </form>
        </div>
	<!--查看助学贷 -->
<div id="browseEduDlg"></div>
	<!-- 查看小企业贷  -->
<div id="browseDlg" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true" data-options="resizable:true">
    <form id="browseForm" method="post">
        <div id="browseTabs" class="easyui-tabs">
            <div id="loanBrowseTab" title="借款信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;height: 100%">
                    <tr>
                        <td><label>产品类型</label></td>
                        <td align="left"><label id="productName"></label></td>
                        <td><label>申请金额</label></td>
                        <td align="left"><label id="requestMoney"></label></td>
                        <td><label>申请期限</label></td>
                        <td align="left"><label id="requestTime"></label></td>
                        <td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>
                    </tr>
                    <tr class="newSeloanBrowse">
                       <td><label>还款来源</label></td>
                        <td align="left"><label id="sourceOfRepay"></label></td>
                    </tr>
                </table>
                <div id="personBrowsePanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
                     <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="newSeloanBrowse">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td align="left"><label id="personName"></label></td>
                            <td><label>性 别</label></td>
                            <td align="left"><label id="personSex"></label></td>
                            <td><label>身份证号</label></td>
                            <td align="left"><label id="personIdnum"></label></td>
                        </tr>
                        <tr>
                            <td><label>婚姻状况</label></td>
                            <td align="left"><label id="personMarried"></label></td>
                            <td><label>最高学历</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            <td><label>户口所在地</label></td>
                            <td align="left"><label id="placeDomicile"></label></td>
                        </tr>
                        <tr>
                            <td ><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>
                            
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>住址类型</label></td>
                            <td align="left"><label id="personHouseEstateType"></label></td>
                        </tr>
                </table>
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="oldSeloanBrowse">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td align="left"><label id="personName"></label></td>
                            <td><label>性 别</label></td>
                            <td align="left"><label id="personSex"></label></td>
                            <td><label>身份证号</label></td>
                            <td align="left"><label id="personIdnum"></label></td>
                        </tr>
                        <tr>
                            <td><label>婚姻状况</label></td>
                            <td align="left"><label id="personMarried"></label></td>
                            <td><label>最高学历</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            <td><label>是否有子女</label></td>
                            <td align="left"><label id="personHasChildren"></label></td>
                        </tr>
                        <tr>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="personZipCode"></label></td>
                            <td><label>住宅地址</label></td>
                            <td  colspan="3" align="left"><label id="personAddress"></label></td>
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td><label>常用邮箱</label></td>
                            <td align="left"><label id="personEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>房产类型</label></td>
                            <td align="left"><label id="personHouseEstateType"></label></td>
                            <td><label>每月租金</label></td>
                            <td align="left"><label id="personRentPerMonth"></label></td>
                            <td><label>房 贷</label></td>
                            <td align="left"><label id="personHasHouseLoan"></label></td>
                            <td><label>职业类型</label></td><td align="left"><label id="professionType"></label></td>
                            
                        </tr>
                        <tr>
                            <td><label>房产地址</label></td>
                            <td colspan="5" align="left"><label id="personHouseEstateAddress"></label></td>
                        <tr>
                        <tr>
                            <td><label>月平均收入</label></td>
                            <td align="left"><label id="personIncomePerMonth"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="companyBrowsePanel" class="easyui-panel browsePanel" title="公司信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="newSeloanBrowse">
                        <tr>
                            <td><label>企业全称</label></td>
                            <td align="left"><label id="companyName"></label></td>
                        </tr>
                        <tr>
                            <td><label>企业电话</label></td>
                            <td align="left"><label id="companyPhone"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="companyFoundedDate"></label></td>
                        </tr>
                        <tr>
                         
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label></td>
                            <td><label>员工人数</label></td>
                            <td align="left"><label id="companyEmployeesNumber"></label>人</td> 
                            <td><label>月营业额</label></td>
                            <td align="left"><label id="monthTurnOver"></label>万元/月</td>

                        </tr>
                        <tr>
                            <td><label>所属行业</label></td>
                            <td align="left"><label id="companyIndustryInvolved"></label></td>

                            <td><label>申请人占股</label></td>
                            <td align="left"><label id="ratioOfInvestments"></label>%</td>
                           
                            <td><label>月净利润</label></td>
                            <td align="left"><label id="monthOfProfit"></label>万元</td>
                        </tr>   
                        <tr >
                            <td><label>所属平台</label></td>
                            <td align="left"><label id="platform"></label></td>
                            <td><label>注册时间</label></td>
                            <td align="left"><label id="regDate"></label></td>
                            <td><label>会员类型</label></td>
                            <td align="left"><label id="memberTypeText"></label></td>

                        </tr>
                          

                    </table>
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="oldSeloanBrowse">
                        <tr>
                            <td><label>企业全称</label></td>
                            <td align="left"><label id="companyName"></label>
                            <td><label>所属行业</label></td>
                            <td align="left"><label id="companyIndustryInvolved"></label>
                            <td><label>法人代表</label></td>
                            <td align="left"><label id="companyLegalRepresentative"></label>
                        </tr>
                        <tr>
                            <td><label>法人代表身份证号</label></td>
                            <td align="left"><label id="companyLegalRepresentativeId"></label>
                            <td><label>近年营业额</label></td>
                            <td align="left"><label id="companyIncomePerMonth"></label>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="companyFoundedDate"></label>
                        </tr>
                        <tr>
                            <td><label>企业类型</label></td>
                            <td align="left"><label id="companyCategory"></label>
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label>
                        </tr>
                        <tr>
                            <td><label>平均年利润</label></td>
                            <td align="left"><label id="companyAvgProfitPerYear"></label>
                            <td><label>企业电话</label></td>
                            <td align="left"><label id="companyPhone"></label>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="companyZipCode"></label>
                        </tr>
                        <tr>
                            <td><label>经营场所</label></td>
                            <td align="left"><label id="companyOperationSite"></label>
                            <td><label>主营业务</label></td>
                            <td align="left"><label id="companyMajorBusiness"></label>
                            <td><label>员工人数</label></td>
                            <td align="left"><label id="companyEmployeesNumber"></label>
                        </tr>
                        <tr>
                            <td><label>员工工资支出</label></td>
                            <td align="left"><label id="companyEmployeesWagesPerMonth"></label>
                        </tr>
                    </table>
                </div>
                <div id="otherBrowsePanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                            <td><label>客户来源</label></td>
                            <td align="left"><label id="customerSource"></label></td>
                            <td><label>申请日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                        </tr>
                        <tr>
                            <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                        </tr>
                        <tr>
                        	<td><label>复核人员</label></td>
                        	<td align="left" colspan="5"><label id="assessorName"></label></td>
                        </tr>
                        <tr>
                       		 <td><label>备注</label></td>
                        	 <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="contacterBrowseTab" title="联系人信息" style="padding:20px">
                <div id="contacterBrowsePanelTemplate"
                     style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td><label id="contacterName"></label></td>
                            <td><label>关 系</label></td>
                            <td><label id="contacterRelationship"></label></td>
                            <td><label>手机号码</label></td>
                            <td><label id="contacterMobilePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>联系地址</label></td>
                            <td><label id="address"></label></td>
                            <!-- <td><label>固定电话</label></td>
                            <td><label id="contacterHomePhone"></label></td> -->
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td  class="oldSeloanBrowse"><label>知晓贷款</label></td>
                            <td  class="oldSeloanBrowse"><label id="contacterHadKnown"></label></td>
                             <td  class="newSeloanBrowse"><label>职务</label></td>
                            <td  class="newSeloanBrowse"><label id="title"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="guaranteeBrowseTab" title="担保人信息" style="padding:20px">
                <div id="guaranteeBrowsePanelTemplate"
                     style="padding:10px;width:910px;display:none;margin-bottom: 10px;">
                    <table style="font-size:12px; width:100%; text-align:left;">
                    <tr><div style="line-height:28px;font-weight:bold;color:blue"><label id="flag" ></label></div></tr>                    
                        <tr id="tr1">
                            <td><label>姓 名</label></td>
                            <td><label id="guaranteeName"></label></td>
                            <td><label>类 型</label></td>
                            <td><label id="guaranteeType"></label></td>
                            <td><label>身份证号</label></td>
                            <td><label id="guaranteeIdnum"></label></td>
                        </tr>
                        <tr id="tr2"> 
                            <td><label>性 别</label></td>
                            <td><label id="guaranteeSex">test</label></td>
                            <td><label>婚姻状况</label></td>
                            <td><label id="guaranteeMarried">test</label></td>
                            <td><label>最高学历</label></td>
                            <td><label id="guaranteeEducationLevel">test</label></td>
                        <tr  id="tr3">
                            <td><label>是否有子女</label></td>
                            <td><label id="guaranteeHasChildren">test</label></td>
                            <td><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeAddress">test</label></td>
                        </tr>
                        <tr id="tr4">
                            <td><label>手机号码</label></td>
                            <td><label id="guaranteeMobilePhone">test</label></td>
                            <td><label>常用邮箱</label></td>
                            <td><label id="guaranteeEmail">test</label></td>
                            <td><label>住宅电话</label></td>
                            <td><label id="personHomePhone">test</label></td>
                        </tr>
                        <tr id="tr5">
                            <td><label>企业全称</label></td>
                            <td><label id="guaranteeCompanyFullName">test</label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaranteeZipCode">test</label></td>
                            <td><label>企业电话</label></td>
                            <td><label id="guaranteeCompanyPhone">test</label></td>
                        </tr>
                        <tr id="tr6">
                            <td><label>企业地址</label></td>
                            <td colspan="5" align="left"><label id="guaranteeCompanyAddress">test</label></td>
                        </tr>
                           <tr id="tr7">
                            <td><label>类 型</label></td>
                            <td><label id="guaType">test</label></td>     
                            <td><label>企业全称</label></td>
                            <td><label id="guaCompanyFullName">test</label></td>                            
                            <td><label>企业电话</label></td>
                            <td><label id="guaCompanyPhone">test</label></td>
                        </tr>
                        <tr id="tr8">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaCompanyAddress">test</label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaZipCode">test</label></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </form>
</div>
<!--  查看小企业贷-同城贷 -->
<div id="browseCityWideSeLoan" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true" data-options="resizable:true">
    <form id="browseForm" method="post">
        <div id="browseTabs" class="easyui-tabs">
            <div id="loanBrowseTab" title="借款信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;height: 100%">
                    <tr>
                        <td><label>产品类型</label></td>
                        <td align="left"><label id="productName"></label></td>
                        <td><label>申请金额</label></td>
                        <td align="left"><label id="requestMoney"></label></td>
                        <td><label>申请期限</label></td>
                        <td align="left"><label id="requestTime"></label></td>
                    </tr>
                    <tr>
                        <td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>

                        <td><label>还款来源</label></td>
                        <td align="left"><label id="sourceOfRepay"></label></td>
                    </tr>
                </table>
               <div id="personBrowsePanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td align="left"><label id="personName"></label></td>
                            <td><label>性 别</label></td>
                            <td align="left"><label id="personSex"></label></td>
                            <td><label>身份证号</label></td>
                            <td align="left"><label id="personIdnum"></label></td>
                        </tr>
                        <tr>
                            <td><label>婚姻状况</label></td>
                            <td align="left"><label id="personMarried"></label></td>
                            <td><label>最高学历</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            <td><label>户口所在地</label></td>
                            <td align="left"><label id="placeDomicile"></label></td>
                        </tr>
                        <tr>
                            <td ><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>
                            
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>住址类型</label></td>
                            <td align="left"><label id="personHouseEstateType"></label></td>
                        </tr>
                    </table>
                </div>
              <div id="companyBrowsePanel" class="easyui-panel browsePanel" title="公司信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>企业全称</label></td>
                            <td align="left"><label id="companyName"></label></td>
                        </tr>
                        <tr>
                            <td><label>企业电话</label></td>
                            <td align="left"><label id="companyPhone"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="companyFoundedDate"></label></td>
                        </tr>
                        <tr>
                         
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label></td>
                            <td><label>员工人数</label></td>
                            <td align="left"><label id="companyEmployeesNumber"></label>人</td> 
                            <td><label>月营业额</label></td>
                            <td align="left"><label id="monthTurnOver"></label>万元/月</td>

                        </tr>
                        <tr>
                            <td><label>所属行业</label></td>
                            <td align="left"><label id="companyIndustryInvolved"></label></td>

                            <td><label>申请人占股</label></td>
                            <td align="left"><label id="ratioOfInvestments"></label>%</td>
                           
                            <td><label>月净利润</label></td>
                            <td align="left"><label id="monthOfProfit"></label>万元</td>
                        </tr>
                        <tr id="cityWidePOSLoanOwn" >
                            <td><label>收单机构</label></td>
                            <td align="left"><label id="posAcquirer"></label></td>
                            <td><label>月均交易量</label></td>
                            <td align="left"><label id="posCapitavolume"></label><label>元</label></td>
                            <td><label>入网时间（年/月）</label></td>
                            <td align="left"><label id="posOpenDate"></label></td>

                        </tr>
                
                        <tr id="cityWideSeloanOwn" >
                            <td><label>企业负债余额</label></td>
                            <td align="left"><label id="companyDebtBalance"></label><label>元</label></td>
                            <td><label>个人负债余额</label></td>
                            <td align="left"><label id="personDebtBalance"></label><label>元</label></td>
                            <td><label>月均还款额</label></td>
                            <td align="left"><label id="monthRepayAmount"></label><label>元</label></td>
                        </tr>

                  
                    </table>
                </div>
                <div id="otherBrowsePanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                            <td><label>客户来源</label></td>
                            <td align="left"><label id="customerSource"></label></td>
                            <td><label>申请日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                        </tr>
                        <tr>
                            <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                        </tr>
                        <tr>
                            <td><label>复核人员</label></td>
                            <td align="left" colspan="5"><label id="assessorName"></label></td>
                        </tr>
                        <tr>
                             <td><label>备注</label></td>
                             <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="cityWideContacterBrowseTab" title="联系人信息" style="padding:20px">
                <div id="cityWideContacterBrowsePanelTemplate"
                     style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                      <tr><div style="line-height:28px;font-weight:bold;color:blue"><label id="flag" ></label></div></tr>
                       <tr>
                            <td><label>姓 名</label></td>
                            <td><label id="contacterName"></label></td>
                            <td><label>关 系</label></td>
                            <td><label id="contacterRelationship"></label></td>
                            <td><label>手机号码</label></td>
                            <td><label id="contacterMobilePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>详细地址</label></td>
                            <td><label id="address"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>职务</label></td>
                            <td><label id="title"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="cityWideGuaranteeBrowseTab" title="担保人信息" style="padding:20px">
                <div id="cityWideGuaranteeBrowsePanelTemplate"
                     style="padding:10px;width:910px;display:none;margin-bottom: 10px;">
                    <table style="font-size:12px; width:100%; text-align:left;">
                    <tr><div style="line-height:28px;font-weight:bold;color:blue"><label id="flag" ></label></div></tr>                    
                        <tr id="tr1">
                            <td><label>姓 名</label></td>
                            <td><label id="guaranteeName"></label></td>
                            <td><label>类 型</label></td>
                            <td><label id="guaranteeType"></label></td>
                            <td><label>身份证号</label></td>
                            <td><label id="guaranteeIdnum"></label></td>
                        </tr>
                        <tr id="tr2"> 
                            <td><label>性 别</label></td>
                            <td><label id="guaranteeSex">test</label></td>
                            <td><label>婚姻状况</label></td>
                            <td><label id="guaranteeMarried">test</label></td>
                            <td><label>最高学历</label></td>
                            <td><label id="guaranteeEducationLevel">test</label></td>
                        <tr  id="tr3">
                            <td><label>是否有子女</label></td>
                            <td><label id="guaranteeHasChildren">test</label></td>
                            <td><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeAddress">test</label></td>
                        </tr>
                        <tr id="tr4">
                            <td><label>手机号码</label></td>
                            <td><label id="guaranteeMobilePhone">test</label></td>
                            <td><label>常用邮箱</label></td>
                            <td><label id="guaranteeEmail">test</label></td>
                            <td><label>住宅电话</label></td>
                            <td><label id="personHomePhone">test</label></td>
                        </tr>
                        <tr id="tr5">
                            <td><label>企业全称</label></td>
                            <td><label id="guaranteeCompanyFullName">test</label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaranteeZipCode">test</label></td>
                            <td><label>企业电话</label></td>
                            <td><label id="guaranteeCompanyPhone">test</label></td>
                        </tr>
                        <tr id="tr6">
                            <td><label>企业地址</label></td>
                            <td colspan="5" align="left"><label id="guaranteeCompanyAddress">test</label></td>
                        </tr>
                           <tr id="tr7">
                            <td><label>类 型</label></td>
                            <td><label id="guaType">test</label></td>     
                            <td><label>企业全称</label></td>
                            <td><label id="guaCompanyFullName">test</label></td>                            
                            <td><label>企业电话</label></td>
                            <td><label id="guaCompanyPhone">test</label></td>
                        </tr>
                        <tr id="tr8">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaCompanyAddress">test</label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaZipCode">test</label></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- 查看车贷  -->
<div id="browseCLDlg" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true"
     data-options="resizable:true">
     
    <form id="browseCLForm" method="post">
        <div id="browseCLTabs" class="easyui-tabs">
            <div id="loanBrowseCLTab" title="产品信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;">
                    <tr>
                        <td><label>产品类型</label></td>
                        <td align="left"><label id="productName"></label></td>
                        <td><label>贷款类型</label></td>
                        <td align="left"><label id="loanType"></label></td>
                        <td><label>申请金额</label></td>
                        <td align="left"><label id="requestMoney"></label></td>
                        <td><label>申请期限</label></td>
                        <td align="left"><label id="requestTime"></label></td>
                        <!--<td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>
                        -->
                    </tr>
                </table>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td align="left"><label id="personName"></label></td>
                            <td><label>性 别</label></td>
                            <td align="left"><label id="personSex"></label></td>
                            <td><label>身份证号</label></td>
                            <td align="left"><label id="personIdnum"></label></td>
                        </tr>
                        <tr>
                            <td><label>婚姻状况</label></td>
                            <td align="left"><label id="personMarried"></label></td>
                            <!--<td><label>最高学历</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            -->
                            <td><label>最高学历</label></td>
                            <td align="left"><label id="topEducation"></label></td>
                            <td><label>是否有子女</label></td>
                            <td align="left"><label id="personHasChildren"></label></td>
                            <td><label id="school">子女在读学校</label></td>
                            <td align="left"><label id="childrenSchool"></label></td>
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td><label>常用邮箱</label></td>
                            <td align="left"><label id="personEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>户籍地址</label></td>
                            <td colspan="3" align="left"><label id="personPlaceDomicile"></label></td>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="personHouseholdZipCode"></label></td>
                        </tr>
                        <tr>
                            <td><label>现居住地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="personZipCode"></label></td>
                        </tr>
                        <tr>
                            <td><label>居住类型</label></td>
                            <td align="left"><label id="personLiveType"></label></td>
                            <td><label>每月租金</label></td>
                            <td align="left"><label id="personRentPerMonth"></label></td>
                            <td><label>房贷情况</label></td>
                            <td align="left"><label id="homeCondition"></label></td>
                            <td><label>可接受的最高月还款额</label></td>
                            <td align="left"><label id="maxRepayAmount"></label></td>
                        </tr>
                        <tr>
                            <td><label>月收入/元</label></td>
                            <td align="left"><label id="monthIncome"></label></td>
                            
                        </tr>
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="车辆信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>车辆品牌</label></td>
                            <td align="left"><label id="vehicleBrand"></label></td>
                            <td><label>车 型</label></td>
                            <td align="left"><label id="vehicleModel"></label></td>
                            <td><label>车 龄</label></td>
                            <td align="left"><label id="vehicleCoty"></label></td>
                        </tr>
                        <tr>
                            <td><label>行驶里程</label></td>
                            <td align="left"><label id="vehicleMileage"></label></td>
                            <td><label>车 牌 号</label></td>
                            <td align="left"><label id="vehiclePlateNumber"></label></td>
                            <td><label>车 架 号</label></td>
                            <td align="left"><label id="vehicleFrameNumber"></label></td>
                               <td><label>是否有车贷</label></td>
                            <td align="left"><label id="isHaveCarLoan"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="职业信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>单位全称</label></td>
                            <td align="left"><label id="companyName"></label></td>
                            <td><label>单位地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label></td>
                             <td><label>单位行业类别</label></td>
                            <td align="left"><label id="unitIndustryCategory"></label></td>
                        </tr>
                        <tr>
                            <td><label>单位性质</label></td>
                            <td align="left"><label id="personCompanyType"></label></td>
                            <td><label>部 门</label></td>
                            <td align="left"><label id="personDeptName"></label></td>
                            <td><label>职 务</label></td>
                            <td align="left"><label id="personJob"></label></td>
                            <td><label>固话分机</label></td>
                            <td align="left"><label id="personExt"></label></td>
                        </tr>
                        <!--<tr>
                            <td><label>职业类型</label></td>
                            <td align="left"><label id="professionType"></label></td>
                            <td><label>月基本薪金</label></td>
                            <td align="left"><label id="personIncomePerMonth"></label></td>
                            <td><label>月发薪日</label></td>
                            <td align="left"><label id="personPayDay"></label></td>
                            <td><label>其他收入</label></td>
                            <td align="left"><label id="personOtherIncome"></label></td>
                        </tr>
                        -->
                        <!--<tr>
                            <td><label>工作证明人</label></td>
                            <td align="left"><label id="personWitness"></label></td>
                            <td><label>部 门</label></td>
                            <td align="left"><label id="personWorkThatDept"></label></td>
                            <td><label>职 务</label></td>
                            <td align="left"><label id="personWorkThatPosition"></label></td>
                            <td><label>联系电话</label></td>
                            <td align="left"><label id="personWorkThatTell"></label></td>
                        </tr>
                        -->
                        <!--<tr class="enterpprise1" style="dispaly:none">
                            <td><label>私营企业类型</label></td>
                            <td align="left"><label id="privateEnterpriseType"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="foundedDate"></label></td>
                            <td><label>经营场所</label></td> 
                            <td align="left"><label id="businessPlace"></label></td>                           
                        </tr>
                        <tr class="enterpprise2" style="dispaly:none">
                            <td><label>员工人数</label></td>
                            <td align="left"><label id="totalEmployees"></label></td>
                            <td><label>占股比例</label></td>
                            <td align="left"><label id="ratioOfInvestments"></label></td>
                            <td><label>月净利润额 </label></td>
                            <td align="left"><label id="monthOfProfit"></label></td>                            
                        </tr>
                        -->
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="信贷历史" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>信用卡</label></td>
                            <td align="left"><label id="creditHistoryHasCreditCard"></label></td>
                            <td><label>总张数</label></td>
                            <td align="left"><label id="creditHistoryCardNum"></label></td>
                        </tr>
                        <tr>
                            <td><label>总 额 度</label></td>
                            <td align="left"><label id="creditHistoryTotalAmount"></label></td>
                            <td><label>已 透 支</label></td>
                            <td align="left"><label id="creditHistoryOverdrawAmount"></label></td>
                        </tr>
                        <tr>
                        	<td><label>贷款笔数</label></td>
                            <td align="left"><label id="loanSize"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="otherBrowseCLPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                            <!--<td><label>客户来源</label></td>
                            <td align="left"><label id="customerSource"></label></td>
                            -->
                            <td><label>申请日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                        </tr>
                        <tr>
                            <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                        </tr>
                         <tr>
                        	<td><label>复核人员</label></td>
                        	<td align="left" colspan="5"><label id="assessorName"></label></td>
                        </tr>
                        <tr>
                       		 <td><label>备注</label></td>
                        	 <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
          <div id="carContacterBrowseTab" title="联系人信息" style="padding:20px">
                <div id="carContacterBrowsePanelTemplate"
                     style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td><label id="contacterName"></label></td>
                            <td><label>关 系</label></td>
                            <td><label id="contacterRelationship"></label></td>
                            <td><label>手机号码</label></td>
                            <td><label id="contacterMobilePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>联系地址</label></td>
                            <td><label id="address"></label></td> 
                            <!-- <td><label>固定电话</label></td>
                            <td><label id="contacterHomePhone"></label></td> -->
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>	
        </div>
    </form>
</div>


<!-- 查看车贷  -->
<div id="browseExtensionCLDlg" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true"
     data-options="resizable:true">
     
    <form id="browseExtensionCLForm" method="post">
        <div id="browseCLTabs" class="easyui-tabs">
            <div id="loanBrowseCLTab" title="借款信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;">
                    <tr>
                        <td><label>产品类型</label></td>
                        <td align="left"><label id="productName"></label></td>
                        <td><label>贷款类型</label></td>
                        <td align="left"><label id="loanType"></label></td>
                        <td><label>申请金额</label></td>
                        <td align="left"><label id="requestMoney"></label></td>
                        <td><label>申请期限</label></td>
                        <td align="left"><label id="requestTime"></label></td>
                        <td></td>
                        <!--<td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>
                        -->
                    </tr>
                </table>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td align="left"><label id="personName"></label></td>
                            <td><label>性 别</label></td>
                            <td align="left"><label id="personSex"></label></td>
                            <td><label>身份证号</label></td>
                            <td align="left"><label id="personIdnum"></label></td>
                        </tr>
                        <tr>
                            <td><label>婚姻状况</label></td>
                            <td align="left"><label id="personMarried"></label></td>
                            <!--<td><label>最高学历</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            -->
                            <td><label>是否有子女</label></td>
                            <td align="left"><label id="personHasChildren"></label></td>
                            <td><label id="school">子女在读学校</label></td>
                            <td align="left"><label id="childrenSchool"></label></td>
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td><label>常用邮箱</label></td>
                            <td align="left"><label id="personEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>户籍地址</label></td>
                            <td colspan="3" align="left"><label id="personPlaceDomicile"></label></td>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="personHouseholdZipCode"></label></td>
                        </tr>
                        <tr>
                            <td><label>现居住地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>
                            <td><label>邮政编码</label></td>
                            <td align="left"><label id="personZipCode"></label></td>
                        </tr>
                        <tr>
                            <td><label>居住类型</label></td>
                            <td align="left"><label id="personLiveType"></label></td>
                            <td><label>每月租金</label></td>
                            <td align="left"><label id="personRentPerMonth"></label></td>
                            <td><label>可接受的最高月还款额</label></td>
                            <td align="left"><label id="maxRepayAmount"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="车辆信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>车辆品牌</label></td>
                            <td align="left"><label id="vehicleBrand"></label></td>
                            <td><label>车 型</label></td>
                            <td align="left"><label id="vehicleModel"></label></td>
                            <td><label>车 龄</label></td>
                            <td align="left"><label id="vehicleCoty"></label></td>
                        </tr>
                        <tr>
                            <td><label>行驶里程</label></td>
                            <td align="left"><label id="vehicleMileage"></label></td>
                            <td><label>车 牌 号</label></td>
                            <td align="left"><label id="vehiclePlateNumber"></label></td>
                            <td><label>车 架 号</label></td>
                            <td align="left"><label id="vehicleFrameNumber"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="职业信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>单位全称</label></td>
                            <td align="left"><label id="companyName"></label></td>
                            <td><label>单位地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label></td>
                        </tr>
                        <tr>
                            <td><label>单位性质</label></td>
                            <td align="left"><label id="personCompanyType"></label></td>
                            <td><label>部 门</label></td>
                            <td align="left"><label id="personDeptName"></label></td>
                            <td><label>职 务</label></td>
                            <td align="left"><label id="personJob"></label></td>
                            <td><label>固话分机</label></td>
                            <td align="left"><label id="personExt"></label></td>
                        </tr>
                        <!--<tr>
                            <td><label>职业类型</label></td>
                            <td align="left"><label id="professionType"></label></td>
                            <td><label>月基本薪金</label></td>
                            <td align="left"><label id="personIncomePerMonth"></label></td>
                            <td><label>月发薪日</label></td>
                            <td align="left"><label id="personPayDay"></label></td>
                            <td><label>其他收入</label></td>
                            <td align="left"><label id="personOtherIncome"></label></td>
                        </tr>
                        <tr>
                            <td><label>工作证明人</label></td>
                            <td align="left"><label id="personWitness"></label></td>
                            <td><label>部 门</label></td>
                            <td align="left"><label id="personWorkThatDept"></label></td>
                            <td><label>职 务</label></td>
                            <td align="left"><label id="personWorkThatPosition"></label></td>
                            <td><label>联系电话</label></td>
                            <td align="left"><label id="personWorkThatTell"></label></td>
                        </tr>
                        <tr class="enterpprise1" style="dispaly:none">
                            <td><label>私营企业类型</label></td>
                            <td align="left"><label id="privateEnterpriseType"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="foundedDate"></label></td>
                            <td><label>经营场所</label></td> 
                            <td align="left"><label id="businessPlace"></label></td>                           
                        </tr>
                        <tr class="enterpprise2" style="dispaly:none">
                            <td><label>员工人数</label></td>
                            <td align="left"><label id="totalEmployees"></label></td>
                            <td><label>占股比例</label></td>
                            <td align="left"><label id="ratioOfInvestments"></label></td>
                            <td><label>月净利润额 </label></td>
                            <td align="left"><label id="monthOfProfit"></label></td>                            
                        </tr>
                        -->
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="信贷历史" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>信用卡</label></td>
                            <td align="left"><label id="creditHistoryHasCreditCard"></label></td>
                            <td><label>总张数</label></td>
                            <td align="left"><label id="creditHistoryCardNum"></label></td>
                        </tr>
                        <tr>
                            <td><label>总 额 度</label></td>
                            <td align="left"><label id="creditHistoryTotalAmount"></label></td>
                            <td><label>已 透 支</label></td>
                            <td align="left"><label id="creditHistoryOverdrawAmount"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="otherBrowseCLPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                            <!--<td><label>客户来源</label></td>
                            <td align="left"><label id="customerSource"></label></td>
                            -->
                            <td><label>申请日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                        </tr>
                        <tr>
                            <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                        </tr>
                        <tr>
                       		 <td><label>备注</label></td>
                        	 <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
          <div id="carExtensionContacterBrowseTab" title="联系人信息" style="padding:20px">
                <div id="carExtensionContacterBrowsePanelTemplate"
                     style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>姓 名</label></td>
                            <td><label id="contacterName"></label></td>
                            <td><label>关 系</label></td>
                            <td><label id="contacterRelationship"></label></td>
                            <td><label>手机号码</label></td>
                            <td><label id="contacterMobilePhone"></label></td>
                        </tr>
                        <tr>
                        	<td><label>联系地址</label></td>
                            <td ><label id="address"></label></td>  
                            <!-- <td><label>固定电话</label></td>
                            <td><label id="contacterHomePhone"></label></td> -->
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>	
        </div>
    </form>
</div>




<div id="businessLogDlg-buttons" class="none">
	<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#businessLogDlg').dialog('close')">关闭</a>
</div>
<script>
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                "text-align":"right",
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>
</body>
</html>