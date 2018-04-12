<html>
<head>
</head>
<body>
 <div>


 <link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
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
                            <!-- <td><label>邮政编码</label></td>
                            <td align="left"><label id="personZipCode"></label></td> -->
                            <td ><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>
                            
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <!-- <td><label>常用邮箱</label></td>
                            <td align="left"><label id="personEmail"></label></td> -->
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>住址类型</label></td>
                            <td align="left"><label id="personHouseEstateType"></label></td>
                          <!--   <td><label>每月租金</label></td>
                            <td align="left"><label id="personRentPerMonth"></label></td> -->
                          <!--   <td><label>房 贷</label></td>
                            <td align="left"><label id="personHasHouseLoan"></label></td>
                            <td><label>职业类型</label></td>
                            <td align="left"><label id="professionType"></label></td> -->
                        </tr>
                       <!--  <tr>
                            <td><label>房产地址</label></td>
                            <td colspan="5" align="left"><label id="personHouseEstateAddress"></label></td>
                        <tr>
                        <tr>
                            <td><label>月平均收入</label></td>
                            <td align="left"><label id="personIncomePerMonth"></label></td>
                        </tr> -->
                    </table>
                </div>
                <div id="companyBrowsePanel" class="easyui-panel browsePanel" title="公司信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>企业全称</label></td>
                            <td align="left"><label id="companyName"></label></td>
                            <!-- <td><label>所属行业</label></td>
                            <td align="left"><label id="companyIndustryInvolved"></label>
                            <td><label>法人代表</label></td>
                            <td align="left"><label id="companyLegalRepresentative"></label> -->
                        </tr>
                        <tr>
                             <td><label>企业电话</label></td>
                            <td align="left"><label id="companyPhone"></label></td>
                          <!--   <td><label>法人代表身份证号</label></td>
                            <td align="left"><label id="companyLegalRepresentativeId"></label>
                            <td><label>近年营业额</label></td>
                            <td align="left"><label id="companyIncomePerMonth"></label> -->
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="companyFoundedDate"></label></td>
                        </tr>
                        <tr>
                         <!--    <td><label>企业类型</label></td>
                            <td align="left"><label id="companyCategory"></label> -->
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
                

                     <!--    <tr>
                            <td><label>经营场所</label></td>
                            <td align="left"><label id="companyOperationSite"></label>
                            <td><label>主营业务</label></td>
                            <td align="left"><label id="companyMajorBusiness"></label>
                        </tr>
                        <tr>
                            <td><label>员工工资支出</label></td>
                            <td align="left"><label id="companyEmployeesWagesPerMonth"></label>
                        </tr> -->
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
                       <!--  <td><label>备注</label></td>
                        <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td> -->
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
                        <tr id="tr3">
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
                            <td colspan="3" align="left"><label id="guaranteeCompanyAddress">test</label></td>
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
            <!--<div id="attachmentBrowseTab" title="附件" style="padding:15px">
				<a href="javascript:void(0)" id="downloadAttachmentZip" class="easyui-linkbutton">附件包</a>
            </div>-->
        </div>
    </form>
</div>

<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
            })
</script>		
</body>
</html>