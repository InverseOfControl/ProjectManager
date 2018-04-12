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
                        <td align="left"><label id="">助学贷</label></td>
                        <td><label>申请金额</label></td>
                        <td align="left"><label id="requestMoney"></label></td>
                        <td><label>申请期限</label></td>
                        <td align="left"><label id="requestTime"></label></td>
                    </tr>
                    <tr>
                        <td><label>所属机构</label></td>
                        <td align="left"><label id='organName'> </label></td>
                        <td><label>机构内部编号</label></td>
                        <td align="left"><label id="organCode"></label></td>
                        <td><label>借款方案</label></td>
                        <td align="left"><label id="channelPlanName" ></label></td>
                    </tr>
                    <tr>
                        <td><label>借款用途</label></td>
                        <td align="left"><label id="purpose"></label></td>

                   
                    </tr>
                    <tr>
                        <td><label>还款来源</label></td>
                        <td align="left"><label id="sourceOfRepay"></label></td>

                   
                    </tr>
                     <tr>
                        <td><label>借款状态</label></td>
                        <td align="left"><label id="status"></label></td>
                          <td><label>放款银行</label></td>
                        <td align="left"><label id="grantAccount"></label></td>            
                        <td><label>还款银行</label></td>
                        <td align="left"><label id="repayAccount"></label></td>
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
                            <td><label>教育水平</label></td>
                            <td align="left"><label id="personEducationLevel"></label></td>
                            <td><label>户口所在地</label></td>
                            <td align="left"><label id="placeDomicile"></label></td>
                        </tr>
                        <tr>
                            <!-- <td><label>邮政编码</label></td>
                            <td align="left"><label id="personZipCode"></label></td> -->
                            <td ><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="personAddress"></label></td>

                              <td ><label>子女数目</label></td>
                            <td colspan="3" align="left"><label id="childrenNum"></label></td>

                            
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
                          
                            <td><label>每月租金</label></td>
                            <td align="left"><label id="rentPerMonth"></label>元</td>

                             <td><label>居住类型</label></td>
                            <td align="left"><label id="houseEstateType"></label></td>
                          <!--   <td><label>房 贷</label></td>
                            <td align="left"><label id="personHasHouseLoan"></label></td>
                            <td><label>职业类型</label></td>
                            <td align="left"><label id="professionType"></label></td> -->
                        </tr>
                        <tr>
                             <td><label>与谁同住</label></td>
                            <td align="left" width="200px">
                                <label id="livingWithType"></label>
                                 <label id="livingWithOtherText"></label>
                                <label>共</label>
                                <label id="livingWithNum"></label>人
                            </td>
                           


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
                <div id="companyBrowsePanel" class="easyui-panel browsePanel" title="学校/公司信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">                       
                    
                        <tr>
                            <td><label>学校/企业信息</label></td>
                            <td align="left"><label id="companyName"></label></td>
                            <!-- <td><label>所属行业</label></td>
                            <td align="left"><label id="companyIndustryInvolved"></label>
                            <td><label>法人代表</label></td>
                            <td align="left"><label id="companyLegalRepresentative"></label> -->
                        </tr>
                        <tr>
                             <td><label>固话及分机</label></td>
                            <td align="left"><label id="companyPhone"></label></td>
                          <!--   <td><label>法人代表身份证号</label></td>
                            <td align="left"><label id="companyLegalRepresentativeId"></label>
                            <td><label>近年营业额</label></td>
                            <td align="left"><label id="companyIncomePerMonth"></label> -->
                            <td><label>专业/部门</label></td>
                            <td align="left"><label id="deptName"></label></td>
                        </tr>
                        <tr>
                         <!--    <td><label>企业类型</label></td>
                            <td align="left"><label id="companyCategory"></label> -->
                            <td><label>学校/企业地址</label></td>
                            <td colspan="3" align="left"><label id="companyAddress"></label></td>
                            <td><label>职务</label></td>
                            <td align="left"><label id="job"></label></td> 
                            <td><label>入职时间/入学时间</label></td>
                            <td align="left"><label id="entranceDate"></label> </td>

                        </tr>

   						<tr >
                            <td><label>主要收入来源</label></td>
                            <td align="left" colspan="3">
                            <span>
                                 工资收入:<label id="salaryIncome"></label>元/月
                                 房屋出租:<label id="rentIncome"></label>元/月
                                          其他收入:<label id="otherIncomeAmount"></label>元/月

                            </span>
                             	 
                            </td>
                        </tr>

                   
                    </table>
                </div>     
                <div id="companyBrowsePanel" class="easyui-panel browsePanel" title="培训信息" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">

                        
                        <tr>
                            <td><label>培训课程</label></td>
                            <td align="left"><label id="course"></label></td>

                            <td><label>培训期长</label></td>
                            <td align="left"><label id="period"></label>个月</td>
                           
                            <td><label>校区</label></td>
                            <td align="left"><label id="schoolDistrict"></label></td>
                        </tr>
                        
                

                   
                    </table>
                </div>
                  <div id="creditInfo" class="easyui-panel browsePanel" title="信贷历史" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;height: 100%">
                                         
                            <tr>
                                <td><label>渠道</label></td>
                                <td align="left"><label id="historyLoanChannel"></label>

                                <td><label>金额</label></td>
                                <td align="left"><label id="historyAmount"></label>
                           
                                <td><label>放款日期</label></td>
                                <td align="left"><label id="historyGrantDate"></label>
                            </tr>
                            <tr>
                                 <td><label>月还款额</label></td>
                                <td align="left"><label id="historyMonPay"></label>
                                
                                <td><label>是否有逾期</label></td>
                                <td align="left"><label id="historyOverdue"></label>
                           
                                <td><label>信用卡</label></td>
                                <td align="left"><label id="hasCreditCardText"></label>

                            </tr>
                             <tr>
                                 <td><label>总张数</label></td>
                                <td align="left"><label id="cardNum"></label>
                                
                                <td><label>总额度</label></td>
                                <td align="left"><label id="totalAmount"></label>
                           
                                <td><label>已透支</label></td>
                                <td align="left"><label id="overdrawAmount"></label>

                            </tr>
                    </table>





                </div>
                <div id="otherBrowsePanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                           
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