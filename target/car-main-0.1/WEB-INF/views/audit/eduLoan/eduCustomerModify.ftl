
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="${WebConstants.webUrl}${WebConstants.contextPath}/resources/css/images/credit2_favicon.ico" type="image/x-icon" />
<html>
<head>

</head>
<body>

<div    style="text-align:right;" >
    

    <script type="text/javascript" charset="UTF-8"  >

         $('#editLoanForm').eduLoanApply();
        $('#editLoanForm').seLoanApply();
        $('table tr td:nth-child(odd)').css("background","#f1f5f9");        

        $('#submitEditBtn').bind('click',editCustomerSubmit);
        $('#closeLoanModifyBtn').bind('click',closeLoanModify);
        $('#checkSchemeBut').click(function(){
            $('#channelPlanEditPanel').window('close');
        });

       

    </script>
    <link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
    <form id="editLoanForm" method="post" style="padding: 5px;">
        <table cellpadding="5" id="addTable1"   cellspacing="10" style="text-align:left">
           
            <tr>
                <td >
                    <label style="font-weight:bold">产品类型</label>
                    <span id="">助学贷</span>
                    <input type="hidden" id ="loanId" name="loanId" />
                    <input type="hidden" id ="productId" name="productId" />
                    <input type="hidden" id ="status" name="status" />
                    <input type="hidden" id ="productName" name="productName" />
                    <input type="hidden" id ="productTypeId" name="productTypeId" />

                </td>
               
               

            </tr>
            <tr class="loanInfo">
                <td >
                    <label style="font-weight:bold">所属机构</label>                    
                    <input id="organID"  name="organID" class="easyui-combobox" editable="false"  required="true"  data-options="width:120"/>
                </td>
                 <td >
                    <label style="font-weight:bold">机构内部编号</label>   
                    <label  id="organCode"  name="organCode"> </label>                  
                    
                </td>
                 <td >
                    <label style="font-weight:bold">借款方案</label>                    
                    <input id="schemeID"  name="schemeID" class="easyui-combobox"  editable="false"  required="true" style="width: 80px"/>
                   <span id="browseScheme" >
                    
                    </span>
                        
                </td>
                
                    
               
            </tr>
            <tr class="loanInfo">
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>申请金额</label>
                    <label name="" id="requestMoneyText" style="margin-left: 10px;color: blue"></label>
                    <span>元</span>
                    <input id="requestMoney" type="hidden"  name="requestMoney" />

                </td>
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 申请期限</label>
                    <label name="" id="requestTimeText" style="margin-left: 10px;color: blue"></label>
                    <input type="hidden" id ="requestTimeValue" name="requestTimeValue" />
                  
                </td>
               
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>借款用途</label>
                    <input  class="easyui-validatebox" name="purpose" id="purpose" readonly="true"  style="width: 90px" value="教育支出" />
                </td>
            </tr>
            <tr class="loanInfo">
                 <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>还款来源</label>
                    <input class="easyui-validatebox" name="sourceOfRepay" id="sourceOfRepay"   required="true" style="width: 90px" />
                </td>
                
            </tr>
            
            <tr>
                <td>
                     <label style="font-weight:bold">  个人信息</label>
                    
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
                    <label> 教育水平</label>
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
                    <label> 现住址</label>
                    <input id="personAddress" name="personAddress" maxlength="150"  class="easyui-validatebox" required="true"/>
                </td>
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 手机号码</label>
                    <input id="personMobilePhone" name="personMobilePhone" validType="mobileCheck"   maxlength="11" class="easyui-validatebox" required="true" />
                    
                </td>
                <td>
                    <span class="pre_span"><font color="red">&nbsp;</font></span>
                    <label> 住宅电话</label>
                    <input id="personHomePhone" name="personHomePhone" class="easyui-validatebox" maxlength="20" validType="telCheck" style="width: 125px" placeholder="021-88888888"/>

                   
                </td>
            </tr>
            <tr>
                <td>
                     
                    <label>社交网络联系方式</label>
                    <input  class="easyui-validatebox" name="sns" id="sns" maxlength="100"    />
                </td>

            </tr>
            <tr>
                <td>
                    
                    <label> 子女数目</label>
                    <input id="childrenNum" name="childrenNum" disabled="disabled" class="easyui-numberbox"  />

                </td>
                <td>
                        
                        <label> 居住类型</label>
                        <select id="houseEstateType" name="houseEstateType"  editable="false" class="easyui-combobox" >
                            <option value="自有">自有</option>
                            <option value="亲属">亲属</option>
                            <option value="宿舍">宿舍</option>
                            <option value="租赁">租赁</option>
                            <option selected="selected" value=" "></option>
                        </select>
                    </td>
                    <td>
                         
                        <label> 每月租金</label>
                        <input id="rentPerMonth" name="rentPerMonth" disabled="disabled"  validType="integerCheck" maxlength="5"  class="easyui-numberbox"  />
                        <span>元</span>
                    </td>

           

            </tr>
            <tr>
                <td>
                    <label> 与谁同住</label>
                      <select id="livingWithType" name="livingWithType" editable="false"   class="easyui-combobox" >
                        <option value="独居">独居</option>
                        <option value="父母">父母</option>
                        <option value="配偶">配偶</option>
                        <option value="亲属">亲属</option>
                        <option value="同事">同事</option>
                        <option value="其他">其他</option>

                        <option selected="selected" value=""></option>
                    </select>
                     <span id="livingWithOther">
                          <input id="livingWithOtherText" name="livingWithOtherText" style="width: 30px;display: none;"/>
                     </span>
                     <label> 共</label>
                      <input id="livingWithNum" name="livingWithNum"  validType="integerCheck" style="width: 30px"/>
                     <label> 人</label>


                </td>
               <!--   <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label> 每月租金</label>
                        <input id="rentPerMonth" name="rentPerMonth"   validType="integerCheck" maxlength="15"  class="easyui-validatebox"  />
                        <span>元</span>
                </td> -->
            </tr>
          
             
            <tr>
                <td>
                     <label style="font-weight:bold">  学校/公司信息</label>
                    
                </td>
                
            </tr>
            <tr >
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 学校/企业全称</label>
                    <input  id="companyId" name="companyId" type="hidden" />
                    <input  id="companyName" name="companyName" class="easyui-validatebox" maxlength="150"  required="true" />
                </td>
                <td>
                   
                    <span class="pre_span"><font color="red"> </font></span>
                    <label> 固话及分机</label>
                    <input  id="phone" name="phone" class="easyui-validatebox"  validType="telCheck"    maxlength="20" style="width: 118px"/>
                </td>
              
              
            </tr>
             <tr>
                 <td colspan="2">
                     
                    <label>   专业/部门</label>
                    <input id="deptName" name="deptName"   class="easyui-validatebox"  style="width:450px;"/>
                </td>

            </tr>


            <tr class="bottomLineTr">
                <td>

                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 学校/企业地址</label>
                    <input  id="companyAddress" name="companyAddress" class="easyui-validatebox"  maxlength="150"  required="true"  />
                </td>
                 <td>
                    
                    <label>   职务</label>
                    <input id="job" name="job"  maxlength="15"   class="easyui-validatebox"/>
                </td>

              
            </tr>
            <tr>
                <td >
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>  入职时间/入学时间</label>
                    <input  id="entranceDate" name="entranceDate"   editable="false"   class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2000-1',maxDate:'2050-12'})"  />
                </td>
                 <td style="width:220px;">
                   
                    <label>  主要收入来源</label>
                    <!-- <input  id="incomeSource" name="incomeSource"  required="true"    class="easyui-validatebox" /> -->

                    <input  id="salaryIncomeType" name="salaryIncome"  type="checkbox"  />工资                   
                    <input  id="salaryIncomeValue" name=" "    class="easyui-numberbox zxIncomeSource" valueEle="salaryIncomeType" style="width:60px;" />元／月


                    <input  id="rentIncomeType" name="rentIncome"  type="checkbox"   />房屋出租                  
                    <input  id="rentIncomeValue" name=" "   class="easyui-numberbox zxIncomeSource" valueEle="rentIncomeType" style="width:60px;"  />元／月

                      <input  id="otherIncomeType" name="otherIncomeAmount"   type="checkbox" />其他                 
                    <input  id="otherIncomeValue" name=" "  class="easyui-numberbox zxIncomeSource"  valueEle="otherIncomeType"style="width:60px;"  />元 / 月

                </td>
            </tr>

            <tr>
                
                <td>
                     <label style="font-weight:bold">  培训信息</label>
                    
                </td>
            </tr>
            <tr>
                   <td >
                   
                    <label>  培训课程</label>
                    <input  id="course" name="course"    class="easyui-validatebox" />
                </td>
                <td>
                 
                    <label>  培训期长</label>
                    <input  id="period" name="period"    class="easyui-numberbox" /><label>  个月</label>

                </td>
                 <td>
                  
                    <label>  校区</label>
                    <input  id="schoolDistrict" name="schoolDistrict"      class="easyui-validatebox" />

                </td>

            </tr>

           
         
            <tr>
                <td>
                     <label style="font-weight:bold">  信贷历史</label>
                    
                </td>
            </tr>
            <tr>
                <td >
                  
                   
                    <input id="histBank" name="historyLoanChannel" type="radio" value="银行" /><label> 银行  </label>
                    <input id="histPerson" name="historyLoanChannel" type="radio" value="个人" /><label> 个人  </label>
                    <input id="histOrg" name="historyLoanChannel" type="radio" value="金融机构" /><label>  金融机构 </label>
                    <input id="histOther" name="historyLoanChannel" type="radio" value="其他" /><label> 其他  </label>
              <!--      
                     <select id="historyLoanChannel" name="historyLoanChannel"  required="true" editable="false" class="easyui-combobox">
                        <option value="银行">银行</option>
                        <option value="个人">个人</option>
                        <option value="金融机构">金融机构</option>
                        <option value="其他">其他</option>
                    </select> -->
                </td>
                <td>
                    
                    <label>  金额</label>
                    <input  id="historyAmount" name="historyAmount"    class="easyui-numberbox" />

                </td>
                 <td>
                    
                    <label>  放款日期</label>
                    <input  id="historyGrantDate" name="historyGrantDate"    editable="false"   class="easyui-datebox" />

                </td>

            </tr>
            <tr>
                <td >
                    
                    <label>  月还款额</label>
                    <input  id="historyMonPay" name="historyMonPay"     class="easyui-numberbox" />
                </td>
                <td>
                  
                    <label>  是否有逾期</label>
                    <!-- <input  id="historyOverdue" name="historyOverdue"  required="true"    class="easyui-validatebox" /> -->
                     <select id="historyOverdue" name="historyOverdue"   editable="false" class="easyui-combobox">
                        <option value="有">有</option>
                        <option selected="selected" value="无">无</option>
                    </select>

                </td>
                 <td>
               
                    <label>  信用卡</label>
                    <select id="hasCreditCard" name="hasCreditCard"   editable="false" class="easyui-combobox">
                        <option value="1">有</option>
                        <option selected="selected" value="0">无</option>
                    </select>

                </td>

            </tr>

              <tr id="creditCardInfoTr" style="display: none;">
                <td >
                  
                    <label>  总张数</label>
                    <input  id="cardNum" name="cardNum"    max="99"    class="easyui-numberbox" />
                </td>
                <td>
               
                    <label>  总额度</label>
                    <input  id="totalAmount" name="totalAmount"     class="easyui-numberbox" />

                </td>
                 <td>
            
                    <label>  已透支</label>
                    <input  id="overdrawAmount" name="overdrawAmount"  class="easyui-numberbox" />

                </td>

            </tr>
              <tr>
         <td colspan="3">
         <label> 备注</label></br>
         <textarea id="remark" name="remark" rows="5" cols="120"  maxlength="1000" /></textarea>
         </td>
    </tr>
           

        </table>

        <table id="addTable2" class="addTable" style="display: none;">
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
                    <#if i==3 >
                        <input id="contacterName${i}"  name=contacterList[${i}].name  maxlength="8" class="easyui-validatebox"   />
                         
                    <#else>
                        <font color="red">*</font>
                        <input id="contacterName${i}"  name=contacterList[${i}].name required="true"  requery maxlength="8" class="easyui-validatebox"   />
                    </#if>
                </td>
                <td><font color="red"> </font>
                    关　　系
                    <!-- <input id="relationship${i}" name=contacterList[${i}].relationship  maxlength="8" class="easyui-validatebox"  required="true"/> -->

                      <#if i==3 >
                        <select id="relationship${i}"   name=contacterList[${i}].relationship class="easyui-combobox" editable="false"  style="width:145px;">
                            <option value="同事">同事</option>
                            <option value="配偶">配偶</option>
                            <option value="父亲">父亲</option>
                            <option value="母亲">母亲</option>
                            <option value="朋友">朋友</option>
                            <option value="同学">同学</option>
                            <option value="子女">子女</option>
                            <option value="其他亲属">其他亲属</option>
                            <option value="其他">其他</option>

                            <option selected="selected" value=""></option>
                        </select>
                         
                    <#else>
                        <font color="red">*</font>
                       <select id="relationship${i}"   name=contacterList[${i}].relationship class="easyui-combobox" required="true"   editable="false"  style="width:145px;">
                            <option value="同事">同事</option>
                            <option value="配偶">配偶</option>
                            <option value="父亲">父亲</option>
                            <option value="母亲">母亲</option>
                            <option value="朋友">朋友</option>
                            <option value="同学">同学</option>
                            <option value="子女">子女</option>
                            <option value="其他亲属">其他亲属</option>
                            <option value="其他">其他</option>

                            <option selected="selected" value=""></option>
                    </select>
                    </#if>
                    
                </td>
                <td><font color="red"> </font>
                    手机号码
                    <#if i==3>
                        <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11"   class="easyui-validatebox" validType="mobileCheck"   />
                         
                    <#else>
                        <font color="red">*</font>
                       <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11"  required="true"   class="easyui-validatebox" validType="mobileCheck"   />
                    </#if>
                    
                </td>
            </tr>
            <tr class="bottomLineTr">

                <td>

                   
                    工作单位
                     <#if i==3 || i==2 || i==1>
                         <input id="workUnit${i}" name=contacterList[${i}].workUnit maxlength="150" style="width: 90px"    class="easyui-validatebox" />
                         
                    <#else>
                        <font color="red">*</font>
                        <input id="workUnit${i}" name=contacterList[${i}].workUnit maxlength="150" style="width: 90px" required="true" class="easyui-validatebox" />
                    </#if>
                   
                </td>
                <td colspan="1">
                   
                    职务
                    <#if i==3 || i==2 || i==1>
                         <input id="title${i}" name=contacterList[${i}].title  maxlength="15"    class="easyui-validatebox"  />
                    <#else>
                        <font color="red">*</font>
                       <input id="title${i}" name=contacterList[${i}].title  maxlength="15" required="true"   class="easyui-validatebox"  />
                    </#if>
                    
                </td>
                <td>
                    <font color="red"> </font>
                    详细地址
                    <#if i==3 || i==2 || i==1>
                        <input id="address${i}" name=contacterList[${i}].address maxlength="150"  style="width: 90px"  class="easyui-validatebox"  />
                    <#else>
                        <font color="red">*</font>
                      <input id="address${i}" name=contacterList[${i}].address maxlength="150"  style="width: 90px" required="true"   class="easyui-validatebox"  />
                    </#if>
                    

                </td>
            </tr>
        </#list>
        </table>
        <table id="addTable3" class="addTable" style="display: none;">
             <tr>
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 客服姓名</label>
                    <input type="hidden" id="serviceId" name="serviceId" />
                    <label id="customerName" name="customerName" style="margin-left: 10px;color: blue"></label>
               
                <td colspan="2">
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>  申请日期</label>
                    <input  id="requestDate" name="requestDate" editable="false"   required="true"  class="easyui-datebox" />
                </td>
            </tr>
             <tr>
                <td>
                    <span class="pre_span"><font color="red">&nbsp;</font></span>
                    <label> 营业网点</label>
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
                <td>
                    <span class="pre_span"><font color="red">&nbsp;</font></span>
                    <label> 营业网点</label>
                    <input  type="hidden" id="salesDeptId" name="salesDeptId"  editable="false"  style="width:145px"/>
                    <input   id="salesDeptName" name=" "  editable="false"  style="width:145px"/>

                </td>
                <td>
                    <span class="pre_span"><font color="red">*</font></span>
                    <label>  客户经理</label>
                    <input type="hidden" id="crmId" name="crmId" />
                    <input  id="managerName" name="managerName" class="easyui-combobox"    required="true"   style="width:145px;"  /> 
                </td>
                <td style="height:30px">
                    <span class="pre_span"><font color="red">*</font></span>
                    <label> 复核人员</label>
                    <input type="hidden" id="assessorId" name="assessorId" />
                    <input id="assessorName" name="assessorName" class="easyui-combobox"     required="true" style="width: 108px;"  /> 
                </td>
            </tr> -->
           
        </table>
    </form>
    <div id="dlg-buttons">
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="prevStepBtn" style="display: none;">上一步</a>
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="nextStepBtn">下一步</a>
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="submitEditBtn"  style="display: none;">保存</a>
        <a  class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="closeLoanModifyBtn" >取消</a>
    </div>
</div>
<div id="channelPlanEditPanel" class="easyui-window channel" title="查看方案" style="width:800px;top:50%;left:50%" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false" closed="true">
        <div style="padding:10px 10px 10px 10px">
        <form id="channelPlanEditForm"  method="post" enctype="multipart/form-data">
            <table width="100%" cellpadding="5" id="termsTable" class="chedai"  cellspacing="10" style="text-align:left" >
                <tr>
                    <input type="hidden" id="checkId2" name="id">
                    <input type="hidden" id="planId2" name="plan_id">
                    <input type="hidden" id="planState2" name="planState">
                    <input id='operatorId2' type="hidden" name="operatorId">
                    <input id='approverState2' type="hidden" name="approverState">
                    <td><span>方案编号：</span></td>
                    <td><input id="code2" type="text" name="code" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                    <td><span>方案名称：</span></td>
                    <td><input id="name2" type="text" name="name" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                </tr>
                <tr>
                    <td><span>借款类型：</span></td>
                    <td><input id='planType2' type="hidden" name="planType"></input><span>助学贷</span></td>
                    <td><span>所属机构：</span></td>
                    <td>
                        <input id='organizationId2' type="hidden" name="organizationId"></input>
                        <span>
                        <!-- <lable id="organName2" ></lable> -->
                        <input id="organName2" type="text" name="organName2"   style="width: 140px" />
                        </span>

                    </td>
                </tr>
                <tr>
                    <td><span>开售日期：</span></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="startDate2" name="startDate" style="width: 140px;" onfocus="WdatePicker()" type="text"/></input></td>
                    <td><span>停售日期：</span></td>
                    <td><input class="Wdate easyui-validatebox validatebox-text validateItem" id="endDate2" name="endDate" style="width: 140px;" onfocus="WdatePicker()" type="text"/></input></td>
                </tr>
                <tr>
                    <td><span>申请金额：</span></td>
                    <td><input id="requestMoney2" name="requestMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><span>元</span></td>
                    <td><span>借款期限：</span></td>
                    <td><input validtype="integerCheck" id="time2" name="time" class="easyui-validatebox validateItem" style="width: 140px" /></td>
                </tr>
                <tr>
                    <td><span>合同金额：</span></td>
                    <td><input id="pactMoney2" name="pactMoney" validtype="moneyCheck" class="easyui-validatebox validateItem" style="width: 140px" /><span>元</span></td>
                    <td><span>保证金金额：</span></td>
                    <td><input validtype="moneyCheck" id="margin2" name="margin" class="easyui-validatebox" style="width: 140px" /><span>元</span></td>
                </tr>
                <tr>
                <td>
                    <span>机构承担服务费：</span>
                </td>
                <td>
                    <select  id="orgFeeState2" name="orgFeeState" editable="false" class="easyui-combobox validateItem" data-options="width:140,panelWidth:140">  
                    <option value="00">是</option>
                    <option value="01">否</option>
                </select>
                </td>
                <td><span>机构还款期数：</span></td>
                 <td><input id="orgRepayTerm2" name="orgRepayTerm" class="easyui-validatebox validateItem" validtype="integerCheck" style="width: 140px" /></td>
                 </tr>
                 <tr>
                    <td><span>月综合费率：</span></td>
                    <td><input id="actualRate2" name="actualRate" validtype="percentCheck" class="easyui-validatebox validateItem" style="width: 140px" /><span></span></td>
                    <td><span>还款类型：</span></td>
                    <td>
                    <select  id="returnType2"  name="returnType" editable="false" class="easyui-combobox validateItem"
                     data-options="
                     width:140,
                     panelWidth:140,
                     onSelect:function(newValue){  
                             if(newValue.value=='0'){
                                       $('#toTerm22').addClass('validateItem');
                                       $('#toTerm22').attr('disabled',false);
                                       $('#returneterm22').addClass('validateItem');
                                       $('#returneterm22').attr('disabled',false);
                                        $('input.validateItem').validatebox({
                                        required:true
                                        });
                              }else if(newValue.value=='1'){
                                  $('#toTerm22').attr('disabled',true);
                                  $('#toTerm22').val('');
                                  $('#toTerm22').removeClass('validateItem');
                                  $('#toTerm22').removeClass('validatebox-invalid');
                                  $('#toTerm22').removeClass('validatebox-text');
                                  $('#returneterm22').attr('disabled',true);
                                  $('#returneterm22').val('');
                                  $('#returneterm22').removeClass('validateItem');
                                  $('#returneterm22').removeClass('validatebox-invalid');
                                  $('#returneterm22').removeClass('validatebox-text');
                              }
                        }
                        "
                     >  
                    <option value="0">前低后高</option>
                    <option value="1">等额本息</option>
                    </select></td>
                </tr>
                 <tr>
                <td colspan="4"> 
                 <div class="datagrid-toolbar"><label style="font-weight:bold">分期还款明细</label> </div>
                </td>
                </tr>
                <tr>
                <td colspan="4"> 
                    <table width="100%" cellpadding="5" id="termsTable22" class="chedai"  cellspacing="10" style="text-align:left" >
                 <tr>
                 <td>还款段次</td><td>期数</td><td>金额</td>
                 </tr>
                  <tr>
                 <td>1</td><td><input id="toTerm12" name="toTerm1" validtype="integerCheck" class="easyui-validatebox validateItem"  /></td><td><input id="returneterm12" name="returneterm1" type="number" class="easyui-validatebox validateItem" /></td>
                 </tr>
                  <tr>
                 <td>2</td><td><input id="toTerm22" name="toTerm2" validtype="integerCheck" class="easyui-validatebox"  /></td><td><input id="returneterm22" name="returneterm2" type="number" class="easyui-validatebox"  /></td>
                 </tr>
                    </table>
                </td>
                </tr>
                <tr>
                <td colspan="4"><div class="datagrid-toolbar"><label style="font-weight:bold">备注</label> </div></td>
                </tr>
                <tr>
                <td colspan="4">
                    <textarea id="memo2" name="memo" rows="3" cols="100"></textarea>
                </td>
                </tr>
            </table>
        </form>
        <br/>
         <div style="text-align:center;padding:5px">
         
            <a  id="checkSchemeBut" class="easyui-linkbutton">关闭</a>
        </div>
        </div>
    </div>
</body>
</html>