<html>
<head>
</head>
<body>
<!-- 新增小企业贷 -->

 <div    style="text-align:right;" >
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>

 <link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 <form id="addSeLoanForm" method="post" style="padding: 5px;">
            <table cellpadding="5" id="addTableNew1"   cellspacing="10" style="text-align:left">
                <tr>
                    <td >
                        <label style="font-weight:bold">借款类型</label>
                        <span id="productNames"></span>
                        <input type="hidden" id ="loanId" name="loanId" />
                        <input type="hidden" id ="productId" name="productId" />
                        <input type="hidden" id ="status" name="status" />
                        <input type="hidden" id ="productName" name="productName" />
                        <input type="hidden" id ="productTypeId" name="productTypeId" /> 

                        <!-- <input type="hidden" id ="personHasChildren" name="personHasChildren" value="" /> -->
                    </td>
                    <td ><font  style="color: blue">申请金额1-100万元</font> </td>
                 </tr>
                 <tr>
                    <td>
                         <span class="pre_span"><font color="red">*</font></span>
                         <label>申请金额</label>
                         <input id="requestMoney" class="easyui-numberbox" name="requestMoney" min="1" max="1000000" maxlength="7" required="true"  style="width: 90px" />
                         <span>元</span>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>申请期限</label>
                       
                       <!-- <select  id="requestTime" name="requestTime"  required="true"  editable="false"  class="easyui-combobox">                          
                            <option value="6">6期</option>
                            <option value="9">9期</option>   
                            <option value="12">12期</option>
                            <option value="18">18期</option>
                            <option value="24">24期</option>
                        </select>
                        -->
                        <input id="requestTime"  name="requestTime" class="easyui-combobox" required="true" editable="false"  data-options="width:120"/>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>借款用途</label>
                        <input  class="easyui-validatebox" name="purpose" id="purpose" style="width: 90px" data-options="required:true,validType:'length[1,4]'"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>还款来源</label>
                        <input class="easyui-validatebox" name="sourceOfRepay" id="sourceOfRepay"   style="width: 90px" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" id ="personId" name="personId" />
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>姓　　名</label>
                       <input id="personName" name="personName" class="easyui-validatebox"  maxlength="25"  required="true" style="width: 90px" />
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>性　　别</label>
                        <select id="personSex" name="personSex" class="easyui-combobox" required="true" editable="false"  style="maxwidth:60px">
                            <option selected="selected"  value="1">男</option>
                            <option value="0">女</option>

                        </select>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 身份证号</label>
                        <label name="personIdnumName" id="personIdnumName" style="margin-left: 10px;color: blue"></label>
                         <input type="hidden" id ="personIdnum" name="personIdnum" />
                    </td>
                    
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 婚姻状况</label>
                        <select  id="personMarried" name="personMarried" editable="false" required="true"  class="easyui-combobox"  >
                            <option value="0">未婚</option>
                            <option value="1">已婚</option>
                            <option value="2">离异</option>
                            <option value="5">其它</option>
                             <option selected="selected" value=""></option>
                        </select>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 最高学历</label>    
                        <select id="personEducationLevel" name="personEducationLevel" required="true"  editable="false"  class="easyui-combobox" >
                            <option value="硕士及以上">硕士及以上</option>
                            <option value="本科">本科</option>
                            <option value="大专">大专</option>
                            <option value="高中">高中</option>
                            <option value="中专">中专</option>
                            <option value="初中及以下">初中及以下</option>
                            <option selected="selected" value="" ></option>
                        </select>
                    </td>
                    <td> 
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>户口所在地</label>
                        <input  class="easyui-validatebox" name="placeDomicile" id="placeDomicile" maxlength="150"  required="true" />
                        
                    </td>                    
                </tr>
                <tr>
                    <td >
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 住宅地址</label>
                        <input id="personAddress" name="personAddress" maxlength="150"  class="easyui-validatebox" required="true"/>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 手机号码</label>
                        <input id="personMobilePhone" name="personMobilePhone" validType="mobileCheck"   maxlength="11" class="easyui-validatebox" required="true" />                    
                        <!-- <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 邮政编码</label>
                        <input id="personZipCode" name="personZipCode" maxlength="6"  validType="zipCheck" class="easyui-validatebox" /> -->
                    </td>
                     <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 住宅电话</label>
                        <input id="personHomePhone" name="personHomePhone" class="easyui-validatebox" maxlength="20" validType="telCheck" style="width: 125px" placeholder="021-88888888"/>

                        <!-- <span class="pre_span"><font color="red">*</font></span>
                        <label>职业类型</label>
                        <select name="professionType" id="professionType"  class="easyui-combobox">
                             <option value="自营">自营</option>
                             <option value="工薪">工薪</option>
                             <option value="白领">白领</option>
                             <option value="学生">学生</option>               
                             <option >其它(可输入)</option>
                        </select> -->
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <!-- <span class="pre_span"><font color="red">*</font></span>
                        <label> 手机号码</label>
                        <input id="personMobilePhone" name="personMobilePhone" validType="mobileCheck"   maxlength="11" class="easyui-validatebox" required="true" /> -->

                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 住址类型</label>
                        <select id="houseEstateType" name="houseEstateType"  editable=false required="true"  class="easyui-combobox" >
                            <option value="自有">自有</option>
                            <option value="租赁">租赁</option>
                            <option value="亲属">亲属</option>
                            
                            <option selected="selected" value=""></option>
                        </select>
                    </td>
                    <td >
                   <!--  <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 常用邮箱</label>
                        <input id="personeEmail" name="personeEmail" class="easyui-validatebox"  maxlength="50"  validType="emailCheck" />      -->                  
                    </td>
                    <td>
                        <!--  <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 住宅电话</label>
                        <input id="personHomePhone" name="personHomePhone" class="easyui-validatebox" maxlength="20" validType="telCheck" style="width: 125px" placeholder="021-88888888"/> -->
                        
                    </td>
                </tr>
                <tr >
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 企业全称</label>
                        <input  id="companyId" name="companyId" type="hidden" />
                        <input  id="companyName" name="companyName" class="easyui-validatebox" maxlength="150"  required="true" />
                    </td>
                    <td>
                        <!-- <span class="pre_span"><font color="red">*</font></span>
                        <label> 每月租金</label>
                        <input id="rentPerMonth" name="rentPerMonth" disabled="disabled"  validType="integerCheck" maxlength="15"  class="easyui-validatebox"  />
                        <span>元</span> -->
                         <span class="pre_span"><font color="red"> </font></span>   
                        <label> 企业电话</label>
                        <input  id="phone" name="phone" class="easyui-validatebox"  validType="telCheck"   maxlength="20" style="width: 118px"/>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 成立时间</label>
                        <input  id="foundedDate" name="foundedDate"  editable="false"  required="true"   class="easyui-datebox" />
                    </td>
                </tr>
                <tr class="bottomLineTr">
                    <td>
                        <!-- <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 房产地址</label>
                        <input type="checkbox" id="checkAddress" />
                        <span>同住宅地址</span> -->
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 企业地址</label>
                        <input  id="companyAddress" name="companyAddress" class="easyui-validatebox"  maxlength="150"  required="true"  />
                    </td>
                    <td>
                        
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 员工人数</label>
                        
                        <input  id="employeesNumber" name="employeesNumber" maxlength="6" validType="integerCheck"  required="true"  class="easyui-validatebox"  style="width: 80px;"/>人
                    </td>
                    <td colspan="2">
                        <!-- <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 月均收入</label>
                        <input  id="personIncomePerMonth" name="personIncomePerMonth" maxlength="15"  validType="integerCheck" class="easyui-validatebox"  /><label>
                        <span>万元/月</span> -->
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 月营业额</label>
                        <input  id="monthTurnOver" name="monthTurnOver" maxlength="15"  required="true" validType="integerCheck" class="easyui-validatebox"  /><label>
                        <span>万元/月</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 所属行业</label>
                        <input  id="industryInvolved" name="industryInvolved"  maxlength="10" class="easyui-validatebox" required="true" />
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>  申请人占股</label>
                        <input id="ratioOfInvestments" name="ratioOfInvestments" required="true" class="easyui-numberbox" min="0" max="100"  maxlength="3"  validType="integerCheck"   style="width:90px;"/><span>%</span>

                    </td>
                    <td>
                    
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>月净利润</label>
                        <input id="monthOfProfit" name="monthOfProfit" class="easyui-validatebox" maxlength="10" required="true" validType="integerCheck"  style="width:90px;"/><span>万元</span>
                    </td>
                </tr>
                <tr id="cityWidePOSLoanOwn" >
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 收单机构</label>
                        <input  id="posAcquirer" name="posAcquirer"  maxlength="100"   class="easyui-validatebox" required="true" />
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 月均交易量</label>
                        <input id="posCapitavolume" name="posCapitavolume" class="easyui-validatebox" maxlength="15" required="true"  validType="integerCheck"  style="width:90px;"/>
                        <span>元</span>

                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>  入网时间（年/月）</label>
                        <input  id="posOpenDate" name="posOpenDate"  required="true" editable="false" class="easyui-datebox" />

                    </td>

                </tr>
                
                <tr id="cityWideSeloanOwn" >
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 企业负债余额</label>
                        <input  id="companyDebtBalance" name="companyDebtBalance"  validType="integerCheck" maxlength="15" class="easyui-validatebox" required="true" />
                        <span>元</span>
                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 个人负债余额</label>
                        <input id="personDebtBalance" name="personDebtBalance" class="easyui-validatebox" maxlength="15"  validType="integerCheck" required="true"   style="width:90px;"/>
                        <span>元</span>

                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>  月均还款额</label>
                        <input  id="monthRepayAmount" name="monthRepayAmount" validType="integerCheck"  required="true"  class="easyui-validatebox" />
                        <span>元</span>
                    </td>

                </tr>

                <tr>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 客服姓名</label>
                          <input type="hidden" id="serviceId" name="serviceId" />
                        <label id="customerName" name="customerName" style="margin-left: 10px;color: blue"></label>
                    <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>  客户来源</label>
                     
                        <select id="customerSource"   name="customerSource" class="easyui-combobox" editable="false" style="width:145px;">
                            <option value="主动拜访">主动拜访</option>
                            <option value="宣传单">宣传单</option>
                            <option value="电销">电销</option>
                            <option value="短信">短信</option>
                            <option value="网络">网络</option>
                            <option value="报纸">报纸</option>
                            <option value="市场推广">市场推广</option>
                            <option value="转介绍">转介绍</option>
                            <option value="朋友介绍">朋友介绍</option>
                            <option value="其他">其他</option>
                            <option selected="selected"  value=""></option>
                        </select>
                    </td>
                    <td colspan="2">
                            <span class="pre_span"><font color="red">*</font></span>
                            <label>  申请日期</label>
                            <input  id="requestDate" name="requestDate" editable="false"   required="true"  class="easyui-datebox" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label> 营业部</label>
                        <input id="salesDeptId" name="salesDeptId" class="easyui-combobox"  editable="false" style="width:145px"/>

                    </td>
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                       <label>  客户经理</label>
                        <input type="hidden" id="crmId" name="crmId" />
                        <input  id="managerName" name="managerName" editable="false"   style="width:145px;" class="easyui-combobox" />
                    </td>
                    <td style="height:30px"> 
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 复核人员</label>
                        <input type="hidden" id="assessorId" name="assessorId" />
                        <input id="assessorName" name="assessorName" editable="false" required="true" style="width: 108px;" class="easyui-combobox"  />
                    </td>
                </tr>
               <!--  <tr>
                <td colspan="3">
                <label> 备注</label></br>
                <textarea id="remark" name="remark" rows="5" cols="120"  maxlength="1000" /></textarea>
                </td>
                </tr> -->
            </table>

            <table id="addTableNew2" class="addTable" style="display: none;">
                <#list 0..3 as i>
                    <tr>
                        <td>
                            联系人${i+1}
                            <input type="hidden" id="contacterId${i}" name=contacterList[${i}].id />
                           
                        </td>
                    </tr>
                            

                    <tr>
                        <td><font color="red"> </font>
                            姓　　名
                            <input id="contacterName${i}"  name=contacterList[${i}].name  maxlength="8" class="easyui-validatebox"   />
                        </td>
                        <td><font color="red"> </font>
                            关　　系
                            <!-- <input id="relationship${i}" name=contacterList[${i}].relationship  maxlength="8" class="easyui-validatebox"  required="true"/> -->
                            <select id="relationship${i}"   name=contacterList[${i}].relationship class="easyui-combobox" editable="false"  style="width:145px;">
                                <option value="同事">同事</option>
                                <option value="配偶">配偶</option>
                                <option value="父亲">父亲</option>
                                <option value="母亲">母亲</option>
                                <option value="朋友">朋友</option>
                                <option value="同学">同学</option>
                                <option value="其他亲属">其他亲属</option>
                                <option value="其他">其他</option>
                                
                                <option selected="selected" value=""></option>
                            </select>
                        </td>
                        <td><font color="red"> </font>
                            手机号码
                            <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11"   class="easyui-validatebox" validType="mobileCheck"   />
                        </td>
                    </tr>
                    <tr class="bottomLineTr">
                    
                        <td>
                            <font color="red"> </font>
                            工作单位
                            <input id="workUnit${i}" name=contacterList[${i}].workUnit maxlength="150" style="width: 90px"    class="easyui-validatebox" />
                        </td>
                        <td colspan="1">
                            <font color="red"> </font>
                            职务
                            <input id="title${i}" name=contacterList[${i}].title  maxlength="15"    class="easyui-validatebox"  />
                        </td>
                        <td>
                            <font color="red"> </font>
                            详细地址
                            <input id="address${i}" name=contacterList[${i}].address maxlength="150"  style="width: 90px"  class="easyui-validatebox"  />

                        </td>
                    </tr>
                </#list>
                  </table>
           <table id="addTableNew3" class="addTable" style="display: none;">
                <#list 0..3 as i>
                    <tr>
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>    担保人${i+1}</label>
                        </td>
                    </tr>
                     <tr>
                         <td>
                         <input  type="hidden" id="guaranteeFlag${i}" name=guaranteeList[${i}].check  />
                         <input  type="checkbox" name="guaranteeCheck" id="guaranteeCheck${i}"  />                         
                         <label>担保合同</label> 
                         </td>
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>担保类型</label>                            
                            <select id="guaType${i}"  name=guaranteeList[${i}].guaranteeType      class="easyui-combobox" >
                                
                                <option selected="selected" value="0">自然人 </option>
                                <option value="1">法人</option>
                            </select>
                        </td>
                      
                    </tr>
                    <tr id="addNametr${i}">
                        <td >
                         
                         <label >       姓　　名</label>                  
                            <input id="guaName${i}" name="guaranteeList[${i}].name" class="easyui-validatebox"  maxlength="15"/>
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  身份证号</label>
                            <input id="guaIdnum${i}"  name=guaranteeList[${i}].idnum  validType="idCheck" class="easyui-validatebox"  maxlength="18"/>
                        </td>
                    </tr>
                    <tr id="addSextr${i}">
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  性别</label>
                            <select  id="guaSex${i}" name=guaranteeList[${i}].sex   class="easyui-combobox" >
                                
                                <option selected="selected"  value="1">男 </option>
                                <option value="0">女</option>
                            </select>
                        </td>
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  婚姻状况</label>
                            
                            <select id="guaMarried${i}"  name=guaranteeList[${i}].married    class="easyui-combobox" style="margin-left: 30px">
                                <option value="0">未婚</option>
                                <option value="1">已婚</option>
                                <option value="2">离异</option>
                                <option value="5">其它</option>
                                <option selected="selected" value=""></option>
                            </select>
                        </td>
                        <td  id="addEdutr${i}" >
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  最高学历</label>
                            
                            <select id="guaEducationLevel${i}"  name=guaranteeList[${i}].educationLevel   class="easyui-combobox"style="margin-left:30px">
                                    <option value="硕士及以上">硕士及以上</option>
                                    <option value="本科">本科</option>
                                    <option value="大专">大专</option>
                                    <option value="高中">高中</option>
                                    <option value="中专">中专</option>
                                    <option value="初中及以下">初中及以下</option>
                                    <option selected="selected" value=""></option>
                            </select>
                        </td>
                    </tr>
                    <tr  id="addChildtr${i}">
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  子女</label>
                            <select id="guaHasChildren${i}"  name=guaranteeList[${i}].hasChildren  class="easyui-combobox" style="margin-left: 60px">
                                
                                <option selected="selected" value="1">有</option>
                                <option value="0">无</option>
                            </select>
                        </td>
                        
                        <td>
                            <span class="pre_span"><font color="red">&nbsp;</font></span>
                            <label>  住宅地址</label>
                            <input id="guaAddress${i}" name=guaranteeList[${i}].address   maxlength="150"   class="easyui-validatebox"  />
                          </td>
                        
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>邮政编码</label>
                        <input id="guaZipCode${i}" name=guaranteeList[${i}].zipCode  maxlength="6"    validType="zipCheck" class="easyui-validatebox" >
                        </td>
                        
                    </tr>
                    <tr id="addMobiletr${i}">
                        <td>
                         <span class="pre_span"><font color="red">&nbsp;</font></span>
                         <label>手机号码</label>
                            <input id="guaMobilePhone${i}" name=guaranteeList[${i}].mobilePhone  maxlength="11"  class="easyui-validatebox"  validType="mobileCheck" />
                        </td>
                        <td >
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>常用邮箱</label>
                        <input id="guaEmail${i}" name=guaranteeList[${i}].email   class="easyui-validatebox"  maxlength="50"  validType="emailCheck" />
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>住宅电话</label>
                         <input id="guaHomePhone${i}" name=guaranteeList[${i}].homePhone   maxlength="20" validType="telCheck"  class="easyui-validatebox" />
                        </td>
                    </tr>
                    <tr id="addCompantr${i}"  class="bottomLineTr">
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业全称</label>
                        <input id="guaCompanyFullName${i}" name=guaranteeList[${i}].companyFullName  maxlength="150"   class="easyui-validatebox" />
                        </td>
                       
                        <td  >
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业地址</label>
                            <input id="guaCompanyAddress${i}" name=guaranteeList[${i}].companyAddress  maxlength="150" class="easyui-validatebox"  />
                        </td>
                        
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业电话</label>
                        <input id="guaPhone${i}" name=guaranteeList[${i}].companyPhone    validType="telCheck" class="easyui-validatebox" maxlength="20"/>
                        </td>
                    </tr>
                     <tr id="addtr${i}">
                       <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>公司全称</label>
                        <input id="guaranteeCompanyFullName${i}"   maxlength="150"   class="easyui-validatebox" />
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业电话</label>
                        <input id="guaranteePhone${i}"    validType="telCheck" class="easyui-validatebox" maxlength="20"/>
                        </td>
                    </tr>
                    <tr id="addt_r${i}" class="bottomLineTr">
                        <td  >
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业地址</label>
                            <input id="guaranteeCompanyAddress${i}"   maxlength="150" class="easyui-validatebox"  />
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>邮政编码</label>
                        <input id="guaranteeZipCode${i}"   maxlength="6"    validType="zipCheck" class="easyui-validatebox" >
                        </td>
                    </tr>
                </#list>
            </table>
        </form>
        <div id="dlg-buttons">
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="prevStepBtnNew" style="display: none;">上一步</a>
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="nextStepBtnNew">下一步</a>
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="submitEditBtnNew"  style="display: none;">保存</a>
            <a  class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="closeLoanBtn" >取消</a>
        </div>
</div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");

                  $('#submitEditBtnNew').bind('click',submitAddCityWideLoan);

                $('#closeLoanBtn').bind('click',closeLoanAdd);
            })
</script>
</body>
</html>