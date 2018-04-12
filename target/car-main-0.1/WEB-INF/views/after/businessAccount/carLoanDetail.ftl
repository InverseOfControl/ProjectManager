<html>
<head>
</head>
<body>
   <!-- 查看车贷  -->
 <div>
 <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />

    <form id="browseCLForm" method="post">
        <div id="browseCLTabs" class="easyui-tabs">
            <div id="loanBrowseCLTab" title="借款信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;">
                        <tr>
                        <td><label>借款类型</label></td>
                        <td align="left"><label id="productName"></label></td>
                        <td><label>审批金额</label></td>
                        <td align="left"><label id="auditMoney"></label></td>
                        <td><label>借款期限</label></td>
                        <td align="left"><label id="auditTime"></label></td>
                        <!--<td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>
                        -->
                    </tr>
                     <tr>
                        <td><label>借款状态</label></td>
                        <td align="left"><label id="status"></label></td>
                        <td><label>放款银行</label></td>
                        <td align="left"><label id="repayAccount"></label></td>
                        <td><label>还款银行</label></td>
                        <td align="left"><label id="grantAccount"></label></td>                      
                    </tr>
                </table>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:1020px;">
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
                            <td ><label>户籍地址</label></td>
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
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="车辆信息" style="width:1020px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>车辆品牌</label></td>
                            <td align="left"><label id="vehicleBrand"></label></td>
                            <td><label>车 型</label></td>
                            <td align="left"><label id="vehicleModel"></label></td>
                            <td><label>车 龄</label></td>
                            <td align="left"><label id="vehicleCoty"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr>
                            <td><label>行驶里程</label></td>
                            <td align="left"><label id="vehicleMileage"></label></td>
                            <td><label>车 牌 号</label></td>
                            <td align="left"><label id="vehiclePlateNumber"></label></td>
                            <td><label>车 架 号</label></td>
                            <td align="left"><label id="vehicleFrameNumber"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                    </table>
                </div>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="职业信息" style="width:1020px;">
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
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="信贷历史" style="width:1020px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>信用卡</label></td>
                            <td align="left"><label id="creditHistoryHasCreditCard"></label></td>
                            <td><label>总张数</label></td>
                            <td align="left"><label id="creditHistoryCardNum"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr>
                            <td><label>总 额 度</label></td>
                            <td align="left"><label id="creditHistoryTotalAmount"></label></td>
                            <td><label>已 透 支</label></td>
                            <td align="left"><label id="creditHistoryOverdrawAmount"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                    </table>
                </div>
                <div id="otherBrowseCLPanel" class="easyui-panel browsePanel" title="其他" style="width:1020px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                            <!--<td><label>客户来源</label></td>
                            <td align="left"><label id="customerSource"></label></td>
                            -->
                            <td><label>申请日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr>
                            <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr>
                        	<td><label>复核人员</label></td>
                        	<td align="left" colspan="7"><label id="assessorName"></label></td>
                        </tr>
                        <tr>
                       		 <td><label>备注</label></td>
                        	 <td align="left" colspan="7" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="contacterCLBrowseTab" title="联系人信息" style="padding:10px">
                <div id="contacterZXQSBrowsePanel" style="padding:10px;width:910px;display:none;">
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
                            <td colspan="3"><label id="contacterAddress"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                        </tr>
                        <tr>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="contacterXDWLXRBrowsePanel" style="padding:10px;width:910px;display:none;">
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
                            <td colspan="3"><label id="contacterAddress"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                        </tr>
                        <tr>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
                <div id="contacterQTLXRBrowsePanelTemplate" style="padding:10px;width:910px;display:none;">
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
                            <td colspan="3"><label id="contacterAddress"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                        </tr>
                        <tr>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
            <!--<div id="attachmentCLBrowseTab" title="附件" style="padding:10px">
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