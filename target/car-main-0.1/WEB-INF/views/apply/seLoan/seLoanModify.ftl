<html>
 <head>
</head>
<body>
 <div   style="text-align:right;"  >
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/seLoan/seLoanApply.js"></script>
  
     <form id="editLoanForm" method="post"  style="padding: 15px;">
            <table cellpadding="5" id="addTable1" class="addTable" cellspacing="10" style='text-align:left;'>
                <tr>
                    <td >
                        <label style="font-weight:bold">借款类型</label>
                        <span id="productNames">小企业贷  </span>
                        <input type="hidden" id ="loanId" name="loanId" />
                        <input type="hidden" id ="productId" name="productId" />
                        <input type="hidden" id ="status" name="status" />
                        <input type="hidden" id ="productName" name="productName" />
                        
                    </td>
                    <td ><font  style="color: blue">申请金额10万~50万元</font> </td>
                 </tr>
                 <tr>
					<td>
						 <span class="pre_span"><font color="red">*</font></span>
                    	 <label>申请金额</label>
					     <input id="requestMoney" class="easyui-numberbox" name="requestMoney" validType="requestMoneyCheck"  min="100000" max="500000" maxlength="6" required="true"  style="width: 90px" />
					     <span>元</span>
					</td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label>申请期限</label>
                       
                        <!--<select  id="requestTime" name="requestTime"  required="true"  editable="false"  class="easyui-combobox">                          
                            <option value="6">6期</option>
                            <option value="9">9期</option>	
                            <option value="12">12期</option>
                            <option value="18">18期</option>
                            <option value="24">24期</option>
                        </select>-->
                        <input id="requestTime"  name="requestTime" class="easyui-combobox" required="true" editable="false"  data-options="width:120"/>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label>借款用途</label>
                        <input  class="easyui-validatebox" name="purpose" id="purpose" style="width: 90px"  data-options="required:true,validType:'length[1,4]'"/>
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
                            <option  selected="selected" value="1">男</option>
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
                    	<label> 婚姻状态</label>
                        <select  id="personMarried" name="personMarried" editable="false"  class="easyui-combobox"  >
                            <option value="0">未婚</option>
                            <option value="1">已婚</option>
                            <option value="2">离异</option>
                            <option value="5">其它</option>
                             <option selected="selected" value></option>
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
                        </select>
                    </td>
                    <td> 
						<span class="pre_span"><font color="red">*</font></span>
                    	<label> 有无子女</label>
                        <select id="personHasChildren" name="personHasChildren" required="true"  editable="false"  class="easyui-combobox">
                            <option value="1"  select="selected" >有</option>
                            <option value="0">无</option>
                        </select>
                    </td>                    
                </tr>
                <tr>
                    <td >
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 住宅地址</label>
                        <input id="personAddress" name="personAddress" maxlength="150"  class="easyui-validatebox" required="true"/>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 邮政编码</label>
                    	<input id="personZipCode" name="personZipCode" maxlength="6"  validType="zipCheck" class="easyui-validatebox" />
                    </td>
                    <td>
        				<span class="pre_span"><font color="red">*</font></span>
        				<label>职业类型</label>
            			<select name="professionType" id="professionType"  class="easyui-combobox">
              			  	 <option value="自营">自营</option>
               			 	 <option value="工薪">工薪</option>
                			 <option value="白领">白领</option>
               				 <option value="学生">学生</option>               
                			 <option >其它(可输入)</option>
            			</select>
        			</td>
                </tr>
                
                <tr>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 手机号码</label>
                    	<input id="personMobilePhone" name="personMobilePhone" validType="mobileCheck"   maxlength="11" class="easyui-validatebox" required="true" />
                    </td>
                    <td >
                    <span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 常用邮箱</label>
                        <input id="personeEmail" name="personeEmail" class="easyui-validatebox"  maxlength="50"  validType="emailCheck" />                       
                    </td>
                    <td>
                   		 <span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 住宅电话</label>
                        <input id="personHomePhone" name="personHomePhone" class="easyui-validatebox" maxlength="20" validType="telCheck" style="width: 125px" placeholder="021-88888888"/>
                    	
                    </td>
                </tr>
                <tr >
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 房产类型</label>
                        <select id="houseEstateType" name="houseEstateType"  editable=false class="easyui-combobox" >
                            <option value="商品房">商品房</option>
                            <option value="经济适用房">经适房/动迁房/房改房</option>
                            <option value="自建房">自建房</option>
                            <option value="租用">租用</option>
                            <option value="亲戚住房">亲戚住房</option>
                        </select>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red"></font></span>
                    	<label> 每月租金</label>
                        <input id="rentPerMonth" name="rentPerMonth" disabled="disabled"  maxlength="15"  class="easyui-validatebox"  />
                        <span>元</span>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 房&nbsp;&nbsp;&nbsp;&nbsp;贷</label>
                        <select id="hasHouseLoan" name="hasHouseLoan"   class="easyui-combobox"  maxlength="5"   editable="false" style="width:140px" >
                            <option selected="selected" value="1">有</option>
                            <option value="0">无</option>
                        </select>
                    </td>
                </tr>
                <tr class="bottomLineTr">
                    <td>
                       	<span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 房产地址</label>
                        <input type="checkbox" id="checkAddress" />
                        <span>同住宅地址</span>
                    </td>
                    <td>
                    	
                        <input style="width:200px;margin-left:20px" id="houseEstateAddress" name="houseEstateAddress"  maxlength="150"  class="easyui-validatebox"  />
                    </td>
                    <td colspan="2">
                    	<span class="pre_span"><font color="red">&nbsp;</font></span>
                    	<label> 月均收入</label>
                        <input  id="personIncomePerMonth" name="personIncomePerMonth" maxlength="15"  validType="integerCheck"  class="easyui-validatebox"  /><label>
                        <span>万元/月</span>
                    </td>
                </tr>
                <tr>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 企业全称</label>
                        <input  id="companyId" name="companyId" type="hidden" />
                        <input  id="companyName" name="companyName" class="easyui-validatebox" maxlength="150"  required="true" />
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 所属行业</label>
                        <input  id="industryInvolved" name="industryInvolved"  maxlength="15" class="easyui-validatebox" required="true" />
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label>  法人代表</label>
                      
                        <input  id="legalRepresentative" name="legalRepresentative" maxlength="15" class="easyui-validatebox" required="true" />
                    </td>
                </tr>
                <tr>
                    <td style="margin-left:0px">
						<span class="pre_span"><font color="red">*</font></span>
                    	<label style="width:120px">法人代表身份证号</label>
                       
                        <input  id="legalRepresentativeId" name="legalRepresentativeId"  validType="idCheck"   maxlength="18" class="easyui-validatebox" required="true" />
                    </td>
                 </tr>
                 
                <tr>
                    <td style="width:310px">
						<span class="pre_span"><font color="red">*</font></span>
                    	<label> 近年营业额</label>
                        
                        <input  id="incomePerMonth" name="incomePerMonth" required="true" validType="integerCheck"  maxlength="15" class="easyui-validatebox" />
                        <span>万元/月</span>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 成立时间</label>
                        <input  id="foundedDate" name="foundedDate"  editable="false"  required="true" class="easyui-datebox" />
                    </td>
                </tr>
                <tr>
                    <td>
						<span class="pre_span"><font color="red">*</font></span>
                    	<label> 企业类型</label>
                        <select name="category"  id="category"  class="easyui-combobox" editable="false" required="true" style="width: 140px;margin-right: 70px" >
                            <option value="0">个体</option>
                            <option value="1">私营独资</option>
                            <option value="2"> 私营合伙</option>
                            <option value="3"> 私营有限责任</option>
                            <option value="4"> 私营股份有限</option>
                            <option value="5"> 其他</option>
                        </select>
                    </td>
                    <td colspan="3">
                    
                    
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 企业地址</label>
                        <input  id="companyAddress" name="companyAddress" class="easyui-validatebox"  maxlength="150"  required="true"  style="width: 330px"/>
                   		<!--label style="color: blue">例:上海市浦东新区东方路800号</label-->
                    </td>
                </tr>
                <tr>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 年均利润</label>
                       
                        <input  id="avgProfitPerYear" name="avgProfitPerYear"  maxlength="15" required="true" validType="integerCheck"  class="easyui-validatebox"  /><span>万元/年</span>
                    </td>
                    <td>
                   		 <span class="pre_span"><font color="red">*</font></span>   
                    	<label> 企业电话</label>
                     	<input  id="phone" name="phone" class="easyui-validatebox"  validType="telCheck" required="true" maxlength="20" style="width: 118px"/>
                    </td>
                    <td>        
                    	<span class="pre_span"><font color="red">&nbsp;</font></span>            
                    	<label> 邮政编码</label>                       
                        <input  id="companyZipCode" name="companyZipCode" validType="zipCheck" maxlength="6"    class="easyui-validatebox" style="width: 80px;" />
                    </td>
                </tr>
                <tr class="bottomLineTr">
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 经营场所</label>
                       
                        <select   id="operationSite" name="operationSite" required="true" editable="false"  class="easyui-combobox" >
                           <option value="租用">租用</option>
                            <option value="自有房产">自有房产</option> 
                         </select>                              
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 主营业务</label>
                        
                        <input  id="majorBusiness" name="majorBusiness" class="easyui-validatebox"  maxlength="150"  required="true" style="width: 114px;"/>
                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 员工人数</label>
                        
                        <input  id="employeesNumber" name="employeesNumber" maxlength="6"  required="true" validType="integerCheck"  class="easyui-validatebox"  style="width: 80px;"/>人
                    </td>
                </tr>
                </tr>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label style="width:80px"> 员工工资支出</label>
                        
                        <input  id="employeesWagesPerMonth" name="employeesWagesPerMonth" maxlength="15" validType="integerCheck"  required="true"  class="easyui-validatebox"  style="width: 72px" /><span>万元/月</span>
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
                        </select>
                    </td>
                    <td colspan="2">
		                    <span class="pre_span"><font color="red">*</font></span>
                    		<label>  申请日期</label>
		                    <input  id="requestDate" name="requestDate"   editable="false"  required="true" class="easyui-datebox" />
                    </td>
                </tr>
                <tr>
                  
                    <td>
                        <span class="pre_span"><font color="red">*</font></span>
                        <label>  营业网点</label>
                        <input id="salesDeptId" name="salesDeptId" class="easyui-combobox"  editable="false" style="width:145px"/>

                    </td>
                    <td>
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label>  客户经理</label>
                        <input type="hidden" id="crmId" name="crmId" />
                       
                        <input id="managerName" name="managerName" class="easyui-combobox" style="width:140px"/>
                    </td>
                    <td style="height:30px"> 
                    	<span class="pre_span"><font color="red">*</font></span>
                    	<label> 复核人员</label>
                        <input type="hidden" id="assessorId" name="assessorId" />
                        <input id="assessorName" name="assessorName" editable="false" required="true" style="width: 108px;" class="easyui-combobox"  />
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
                        	<span class="pre_span"><font color="red">&nbsp;</font></span>
                           		<label>   联系人${i+1}</label>	
                            <input type="hidden" id="contacterId${i}" name=contacterList[${i}].id />
                        </td>
                    <tr>
                    </tr>
                        <td>
                        	<span class="pre_span"><font color="red">*</font></span>
                   			<label>    姓　　名</label>
                            <input style="width:130px" id="contacterName${i}"  name=contacterList[${i}].name  maxlength="15" class="easyui-validatebox" required="true" />
                        </td>
                        <td>
                        	<span class="pre_span"><font color="red">*</font></span>
                   			<label>    关            系</label>
                            <input id="relationship${i}" name=contacterList[${i}].relationship  maxlength="15" class="easyui-validatebox"  required="true"/>
                        </td>
                        <td>
							<span class="pre_span"><font color="red">&nbsp;</font></span>
                   			<label>    手机号码</label>
                            <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11" class="easyui-validatebox" validType="mobileCheck"   />
                        </td>
                    </tr>
                    <tr class="bottomLineTr">
            			<td>
                   			<label>    固定电话</label>
                            <input style="width:130px" id="homePhone${i}" name=contacterList[${i}].homePhone  maxlength="20" validType="telCheck"  class="easyui-validatebox" validType="telCheck" />
                        </td>
                        <td>
                        	<span class="pre_span"><font color="red">*</font></span>
                   			<label>    工作单位</label>
                            <input style="width:130px" id="workUnit${i}" name=contacterList[${i}].workUnit maxlength="150"   class="easyui-validatebox" />
                        </td>
                        <td colspan="1">
                        	<span class="pre_span"><font color="red">*</font></span>
                   			<label >    知晓贷款</label>
                            <select  id="hadKnown${i}" name=contacterList[${i}].hadKnown  editable="false" required="true" class="easyui-combobox" >
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
                    </tr>
                </#list>  
               </table> 
            <table id="addTable3" class="addTable" style="display: none;">            
                <input type="hidden" id="guaranteeName" />
                <input type="hidden" id="naturalGuaranteeName1" />
                <input type="hidden" id="naturalGuaranteeName2" />
                <input type="hidden" id="legalGuaranteeCname1" />
                <input type="hidden" id="legalGuaranteeCname2" />
                <#list 0..3 as i>
                    <tr>
                        <td>
                        	<span class="pre_span"><font color="red">&nbsp;</font></span>
                        	<label>    担保人${i+1}</label>
                             <label id="guaranteeChange${i}" style="color: blue"></label>
                            <input type="hidden" id="guaranteeId${i}" name=guaranteeList[${i}].id  />
                            <input type="hidden" id="guaPersonId${i}" name=guaranteeList[${i}].personId  />
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
                            <select  id="guaType${i}" name=guaranteeList[${i}].guaranteeType editable="false"  class="easyui-combobox" >
                                <option selected="selected" value="0">自然人 </option>
                                <option value="1">法人</option>
                            </select>
                        </td>
                      
                    </tr>
                    <tr id="nametr${i}">
                        <td >
                          <span class="pre_span"><font color="red"> </font></span>
                         <label >       姓　　名</label>                  
                         			<input id="guaName${i}" name="guaranteeList[${i}].name" class="easyui-validatebox"  maxlength="15"/>
                        </td>
                        <td>
                        <span class="pre_span"><font color="red"> </font></span>
                            <label>  身份证号</label>
                            <input id="guaIdnum${i}"  name=guaranteeList[${i}].idnum  validType="idCheck" class="easyui-validatebox"  maxlength="18"/>
                        </td>
                    </tr>
                    <tr id="sextr${i}">
                        <td>
                        	 <span class="pre_span"><font color="red"> </font></span>
                            <label>  性别</label>
                            <select  id="guaSex${i}" name=guaranteeList[${i}].sex   editable="false"  class="easyui-combobox" >
                                <option selected="selected" value="1">男 </option>
                                <option value="0">女</option>
                            </select>
                        </td>
                        <td>
                        	 <span class="pre_span"><font color="red"> </font></span>
                        	<label>  婚姻状况</label>
                            
                            <select id="guaMarried${i}"  name=guaranteeList[${i}].married   editable="false"  class="easyui-combobox" style="margin-left: 30px">
                                <option value="0">未婚</option>
                                <option value="1">已婚</option>
                                <option value="2">离异</option>
                                <option value="5">其它</option>
                            </select>
                        </td>
                        <td  id="edutr${i}" >
                        	 <span class="pre_span"><font color="red"> </font></span>
                        	<label>  最高学历</label>
                            
                            <select id="guaEducationLevel${i}"  name=guaranteeList[${i}].educationLevel   editable="false" class="easyui-combobox"style="margin-left:30px">
                                    <option value="硕士及以上">硕士及以上</option>
		                            <option value="本科">本科</option>
		                            <option value="大专">大专</option>
		                            <option value="高中">高中</option>
		                            <option value="中专">中专</option>
		                            <option value="初中及以下">初中及以下</option>
                            </select>
                        </td>
                    </tr>
                    <tr  id="childtr${i}">
                        <td>
                        	 <span class="pre_span"><font color="red"> </font></span>
                            <label>  子女</label>
                            <select id="guaHasChildren${i}"  name=guaranteeList[${i}].hasChildren  editable="false" class="easyui-combobox" style="margin-left: 60px">
                                <option selected="selected" value="1">有</option>
                                <option value="0">无</option>
                            </select>
                        </td>
                        
                        <td>
                        	 <span class="pre_span"><font color="red"> </font></span>
                        	<label>  住宅地址</label>
                            <input id="guaAddress${i}" name=guaranteeList[${i}].address   maxlength="150"   class="easyui-validatebox"  />
                          </td>
                        
                        <td>
                        <span class="pre_span"><font color="red"> </font></span>
                        <label>邮政编码</label>
                        <input id="guaZipCode${i}" name=guaranteeList[${i}].zipCode  maxlength="6"    validType="zipCheck" class="easyui-validatebox" >
                        </td>
                        
                    </tr>
                    <tr id="mobiletr${i}">
                        <td>
                          <span class="pre_span"><font color="red"> </font></span>
                         <label>手机号码</label>
                            <input id="guaMobilePhone${i}" name=guaranteeList[${i}].mobilePhone  maxlength="11"  class="easyui-validatebox"  validType="mobileCheck" />
                        </td>
                        <td >
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>常用邮箱</label>
                        <input id="guaEmail${i}" name=guaranteeList[${i}].email   class="easyui-validatebox"  maxlength="50"  validType="emailCheck" />
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>住宅电话</label>
                         <input id="guaHomePhone${i}" name=guaranteeList[${i}].homePhone   maxlength="20" validType="telCheck"  class="easyui-validatebox" />
                        </td>
                    </tr>
                    <tr id="compantr${i}"  class="bottomLineTr">
                        <td>
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>企业全称</label>
                        <input id="guaCompanyFullName${i}" name=guaranteeList[${i}].companyFullName  maxlength="150"   class="easyui-validatebox" />
                        </td>
                       
                        <td  >
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>企业地址</label>
                            <input id="guaCompanyAddress${i}" name=guaranteeList[${i}].companyAddress  maxlength="150" class="easyui-validatebox"  />
                        </td>
                        
                        <td>
                         <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业电话</label>
                        <input id="guaPhone${i}" name=guaranteeList[${i}].companyPhone    validType="telCheck" class="easyui-validatebox" maxlength="20"/>
                        </td>
                    </tr>
                     <tr id="tr${i}">
                       <td>
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>公司全称</label>
                        <input id="guaranteeCompanyFullName${i}"   maxlength="150"   class="easyui-validatebox" />
                        </td>
                        <td>
                        <span class="pre_span"><font color="red">&nbsp;</font></span>
                        <label>企业电话</label>
                        <input id="guaranteePhone${i}"    validType="telCheck" class="easyui-validatebox" maxlength="20"/>
                        </td>
                    </tr>
                    <tr id="t_r${i}" class="bottomLineTr">
                        <td  >
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>企业地址</label>
                            <input id="guaranteeCompanyAddress${i}"   maxlength="150" class="easyui-validatebox"  />
                        </td>
                        <td>
                         <span class="pre_span"><font color="red"> </font></span>
                        <label>邮政编码</label>
                        <input id="guaranteeZipCode${i}"   maxlength="6"    validType="zipCheck" class="easyui-validatebox" >
                        </td>
                    </tr>
                </#list>
            </table>
        </form>
         <div id="dlg-buttons">
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="prevStepBtn" style="display: none;">上一步</a>
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="nextStepBtn">下一步</a>
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="submitEditBtn"   style="display: none;">保存</a>
            <a  class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="closeLoanModifyBtn"  >取消</a>
        </div>
    
    </div>
<script>
			$(function(){
				
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");

                 $('#submitEditBtn').bind('click',submitEdit);
                $('#closeLoanModifyBtn').bind('click',closeLoanModify);
            })
</script>
</body>
</html>