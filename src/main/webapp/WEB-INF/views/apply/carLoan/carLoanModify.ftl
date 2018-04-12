<html>
 <head>
</head>
<body>
 <div   style="text-align:right;"  >
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>
   <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
    <form id="editCarLoanForm" method="post" style="padding: 5px;">
    <table cellpadding="5" id="addCarTable1" class="chedai"  cellspacing="10" style='text-align:left;'>
    	<input  type="hidden" id="isModify" name="isModify"/>
    <tr>
        <td>
        	<label style="font-weight:bold">贷款类型</label>
            <span id="productNames">车贷</span>
            <input  type="hidden" id="loanId" name="loanId"/>
            <input type="hidden" id="productId" name="productId"/>
            <input type="hidden" id="productName" name="productName"/>
         </td>
         <td><font  style="color: blue">申请金额1000元以上 </font></td>
    </tr>
    <tr>
        <td> 
        	 <span class="pre_span"><font color="red">*</font></span>
        	 <label>贷款类型 </label> 
             <select id="loanTypes" name="loanTypes" editable="false" class="easyui-combobox">
                <option value="1">移交类</option>
                <option value="2">流通类</option>               
            </select>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>申请金额 </label> 
            <input id="requestMoney" name="requestMoney" class="easyui-numberbox" min="1000" required="true" validType="requestMoneyCheck" />元
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>申请期限</label> 
            <input id="requestTime" name="requestTime" class="easyui-combobox" required="true" editable="false"  data-options="width:120"/>
        </td>
        <!--<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 借款用途</label> 
            <select id="purpose" name="purpose" editable="false" class="easyui-combobox">                       
                <option value="资金周转">资金周转</option>
                <option value="扩大经营">扩大经营</option>
                <option value="购买设备">购买设备</option>
                 <option value="教育支出">教育支出</option>
                  <option value="装修家居">装修家居</option>
                  <option value="医疗">医疗</option>
                  <option value="购车">购车</option>
                  <option value="其他">其他</option>
                   <option ></option>
            </select>
        </td>
        -->
    </tr>
    <tr>
        <td>
        	<input type="hidden"  id="carPersonNameBackups" name="carPersonNameBackups"/>
            <input type="hidden" id="personId" name="personId"/>
            <span class="pre_span"><font color="red">*</font></span>
        	<label> 姓　　名</label> 
            <input id="carPersonName" name="carPersonName" class="easyui-validatebox" maxlength="25" required="true" />
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label style='text-indent:'> 性　　别</label> 
            <select id="carPersonSex" name="carPersonSex" class="easyui-combobox"  editable="false"  >
                <option value="1">男</option>
                <option value="0">女</option>
            </select>
        </td>
        <td >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 身份证号&nbsp;&nbsp;</label> 
        	<label name="carPersonIdnumName" id="carPersonIdnumName" style="margin-left: 10px;color: blue"></label>
         	 <input type="hidden" id="carPersonIdnum" name="carPersonIdnum" />
        </td>
        <td colspan="3" >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 最高学历</label> 
            <select id="topEducation" name="topEducation" class="easyui-combobox"  editable="false" required="true"  style="maxwidth:60px">
             	<option value=""> </option>
                <option value="1">硕士及以上</option>
                <option value="2">本科</option>
                <option value="3">大专</option>
                <option value="4">中专</option>
                <option value="5">高中</option>
                <option value="6">初中及以下</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  婚姻状况</label> 
            <select id="carPersonMarried" name="carPersonMarried" editable="false"  class="easyui-combobox">
                <option value="0">未婚</option>
                 <option value="1">已婚</option>
                 <option value="2">离异</option> 
                 <option value="5">其它</option>
            </select>
        </td>
        <!--<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  最高学历</label> 
            <select id="carPersonEducationLevel" name="carPersonEducationLevel"  editable="false"  class="easyui-combobox">
                <option value="5">硕士及以上</option>
                <option value="4">本科</option>
                <option value="3">大专</option>
                <option value="2">中专</option>
                <option value="1">高中</option>
                <option value="0">初中及以下</option>
            </select>
        </td>
        -->
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  有无子女</label> 
            <select id="carPersonHasChildren" name="carPersonHasChildren"    editable="false"  class="easyui-combobox">
                <option value="1">有</option>
                <option selected="selected" value="0">无</option>
            </select>
        </td>
         <td>
        	<span class="pre_span">
        	<label>  子女在读学校 </label> 
        	<input id="childrenSchool" name="childrenSchool" disabled="disbaled" class="easyui-validatebox"  maxlength="50"/>
        </td> 
    </tr>
    <tr>
        <td>
        	<input type="hidden" id="carPersonMobilePhoneBackups" name="carPersonMobilePhoneBackups"/>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  手机号码</label> 
        	<input id="carPersonMobilePhone" name="carPersonMobilePhone" required="true" class="easyui-validatebox"  maxlength="11" validType="mobileCheck" />
        </td>
        <td>
        	<label>  常用邮箱</label> 
            <input id="carPersoneEmail" name="carPersoneEmail" class="easyui-validatebox" maxlength="50"  validType="emailCheck"/>
        </td>
        <td colspan="2">
        	<label>  住宅电话</label>
            <input id="carPersonHomePhone" name="carPersonHomePhone" class="easyui-validatebox" validType="telCheck" maxlength="20" style="width: 125px"/>
            <span style="color: blue;margin-left:5px;">例:021-88888888</span>
        </td>
    </tr>
     <tr>
        <td colspan="3">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  户籍地址</label>
            <input id="placeDomicile" name="placeDomicile"    maxlength="75"  class="easyui-validatebox"  required="true" 
                   style="width: 360px;overflow: hidden"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  邮政编码</label>
            <input id="householdZipCode" name="householdZipCode" validType="zipCheck" maxlength="6"    class="easyui-validatebox"  />
        </td>
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  居住地址</label>
            <input type="checkbox" id="checkCarAddress" />
        	<span>同户籍地址</span>    
        </td>
        <td colspan="2">
            <input id="carPersonAddress" name="carPersonAddress"   maxlength="150" class="easyui-validatebox"  required="true" 
                   style="width: 360px;overflow: hidden"/>
        </td>
        <td colspan="2">
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  邮政编码</label>
            <input id="carPersonZipCode" name="carPersonZipCode" validType="zipCheck" maxlength="6"    class="easyui-validatebox" />
        </td>
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  居住类型</label>
            <select name="liveType" id="liveType" class="easyui-combobox">
                <option value="按揭房" selected="selected">按揭房</option>
                <option value="非按揭房">非按揭房</option>
                <option value="自建房">自建房</option>
                <option value="亲属房">亲属房</option>
                <option value="单位房">单位房</option>
                <option value="租赁">租赁</option>
                <option value="" > </option>
            </select>
        </td>
        <td >
        	<span class="pre_span"><font color="red"></font></span>
        	<label id="rentPerMonthLabel" >  每月房贷</label>
            <input id="carRentPerMonth" style="width:100px" name="carRentPerMonth"   disabled="disabled"  maxlength="6"  class="easyui-validatebox"/>
            <span>元</span>
        </td>
        <td colspan="3">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 房贷情况</label> 
            <select id="homeCondition" name="homeCondition" class="easyui-combobox"  editable="false" required="true"  style="maxwidth:60px">
             	<option value=""> </option>
                <option value="1">还款中</option>
                <option value="2">全款购</option>
                <option value="3">已结清</option>
                <option value="4">无</option>
            </select>
        </td>
        
    </tr>
    <tr>
    	
   		 <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 月收入/元</label> 
            <input id="monthIncome" style="width:100px" name="monthIncome"    maxlength="8"  required="true"    class="easyui-numberbox"  data-options="min:0,precision:2"/>
        </td>
    	<td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label >可接受最高月还款额</label>
            <input id="maxRepayAmount" style="width:100px" name="maxRepayAmount" validType="integerCheck"   maxlength="6"  class="easyui-validatebox"/>
			<span>元/月</span>
        </td>
    </tr>
    <tr>
        <td>
        	<input type="hidden" id="vehicleId" name="vehicleId" />
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  车辆品牌</label>
      		 <input id="brand" name="brand"  maxlength="15"  required="true" class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  车　　型</label>
            <input id="model" name="model" required="true" maxlength="15"  class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  车　　龄</label>
            <input id="coty" name="coty"  required="true"  maxlength="3"  style="width:80px" validType="integerCheck" class="easyui-validatebox"/>
        	<span>年</span>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  行驶里程</label>
            <input id="mileage" name="mileage"  required="true"  maxlength="10"  style="width:80px"  validType="integerCheck" class="easyui-validatebox"/>
        <span>公里</span>
        </td>
    </tr>
     <tr>       
        <td>
			<span class="pre_span"><font color="red">*</font></span>
        	<label>  车牌号&nbsp;&nbsp;</label>
          	<input id="plateNumber" name="plateNumber" required="true" maxlength="20"   class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  车架号&nbsp;&nbsp;</label>
           <input id="frameNumber" name="frameNumber" required="true" maxlength="20"   class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 是否有车贷</label> 
            <select id="isHaveCarLoan" name="isHaveCarLoan" class="easyui-combobox"  editable="false" required="true"  style="maxwidth:60px">
             	<option value=""> </option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
       	 </td>
    </tr>
     <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>   单位全称</label>
            <input  id="companyId" name="companyId" type="hidden" />
            <input id="carCompanyName" name="carCompanyName"   maxlength="150" required="true"  class="easyui-validatebox"/>
        </td>
        <td >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>   单位地址</label>
            <input id="carCompanyAddress" name="carCompanyAddress"   maxlength="150" required="true" class="easyui-validatebox"/>
            <span style="color: blue">例:上海市浦东新区东方路800号</span>
        </td>
        <td colspan="3" >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 单位行业类别</label> 
            <select id="unitIndustryCategory" name="unitIndustryCategory" class="easyui-combobox"  editable="false" required="true"  style="maxwidth:60px">
             	<option value=""> </option>
                <option value="1">农、林、牧、渔业</option>
                <option value="2">能源、采矿业</option>
                <option value="3">食品、药品、工业原料、服装、日用品等制造业</option>
                <option value="4">电力、热力、燃气及水生产和供应业</option>
                <option value="5">建筑业</option>
                <option value="6">批发和零售业</option>
                <option value="7">交通运输、仓储和邮政业</option>
                <option value="8">住宿、旅游、餐饮业</option>
                <option value="9">信息传输、软件和信息技术服务业</option>
                <option value="10">金融业</option>
                <option value="11">房地产业</option>
                <option value="12">租凭和商务服务业</option>
                <option value="13">科学研究、技术服务业</option>
                <option value="14">水利、环境和公共设施管理业</option>
                <option value="15">居民服务、修理和其他服务业</option>
                <option value="16">教育、培训</option>
                <option value="17">卫生、医疗、社会保障、社会福利</option>
                <option value="18">文化、体育和娱乐业</option>
                <option value="19">政府、非盈利机构和社会组织</option>
                <option value="20">警察、消防、军人</option>
                <option value="21">其他</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>   单位性质</label>
            <select name="carCompanyType" id="carCompanyType" editable="false"  class="easyui-combobox">
                <option value="0">政府机构</option>
                <option value="1">事业</option>
                <option value="2">国企</option>
                <option value="3">外资</option>
                <option value="4">民营</option>
                <option value="5">私营</option>
                <option value="6">其它</option>
                <option value="7">合资</option>
                 <option  >  </option>
            </select>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>   部　　门</label>
            <input id="deptName" name="deptName" maxlength="15" required="true" class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>   职　　务</label>
            <input id="job" name="job"  maxlength="15" required="true" class="easyui-validatebox"/>
        </td>
        <td>
            <span class="pre_span"><font color="red"></font></span>
        	<label>   固话分机</label>
            <input id="ext" name="ext"   maxlength="20" validType="telCheck"  class="easyui-validatebox"/>
        </td>
    </tr>
    <!--<tr>
    	<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>职业类型</label>
            <select name="professionType" id="professionType"  class="easyui-combobox">
                <option value="自营">自营</option>
                <option value="工薪">工薪</option>
                <option value="白领">白领</option>
                <option value="学生">学生</option>               
                <option >其它(可输入)</option>
                <option>  </option>
            </select>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  月基本薪金</label>
        	<input id="carIncomePerMonth" name="carIncomePerMonth" class="easyui-validatebox" maxlength="15"  required="true" style="width:90px;"/><span>元</span>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  月发薪日</label>
        	<input id="payDate" name="payDate" class="easyui-numberbox" maxlength="2" max="31" required="true" style="width:90px;"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  其他收入</label>
        	<input id="otherIncome" style="width:90px" name="otherIncome"   maxlength="15" class="easyui-validatebox" /><span>元</span>
        </td>
    </tr>
    --> 
    <!--<tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 工作证明人</label>
            <input id="witness"  name="witness" required="true" maxlength="15" class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  部　　门</label>
        	<input id="workThatDept" name="workThatDept" required="true" maxlength="15" class="easyui-validatebox"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  职　　务</label>
        	<input id="workThatPosition" name="workThatPosition" required="true"  maxlength="15"  class="easyui-validatebox"/>
        </td>
      
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  联系电话</label>
        	<input id="workThatTell" name="workThatTell" required="true" maxlength="11" validType="phoneCheck"   class="easyui-validatebox"/>
        </td>
    </tr>
    -->
    <!--<tr class="enterpriseType3">   
    	<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>私营企业类型</label>
            <select name="privateEnterpriseType" id="privateEnterpriseType"   class="easyui-combobox">
                <option value="个体">个体</option>
                <option value="私营独资">私营独资</option>
                <option value="私营合伙">私营合伙</option>
                <option value="私营有限责任">私营有限责任</option>  
                 <option value="私营股份有限">私营股份有限</option>
                <option value="其它(可输入)">其它(可输入)</option> 
            </select>
        </td>  
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 成立时间</label>
			<input id="carFoundedDate" name="carFoundedDate" editable="false"  class="easyui-datebox"/>       
			 </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  经营场所</label>
        	<select   id="businessPlace" name="businessPlace" editable="false"  class="easyui-combobox" >
                           <option value="1">租用</option>
                            <option value="2">自有房产</option> 
             </select>  
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 员工人数 </label>
        	<input id="totalEmployees" style="width:90px" name="totalEmployees"   maxlength="15" class="easyui-validatebox"  validType="integerCheck"  /><span>人</span>
        </td>
    </tr>
    -->
    <!--<tr class="enterpriseType4">   
    <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  占股比例</label>
             <input id="ratioOfInvestments" name="ratioOfInvestments" class="easyui-numberbox" min="0" max="100"  maxlength="3"  validType="integerCheck"   style="width:90px;"/><span>%</span>

        </td>  
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>月净利润额</label>
        	<input id="monthOfProfit" name="monthOfProfit"  class="easyui-validatebox" maxlength="15"  validType="integerCheck"   style="width:90px;"/><span>万元</span>
        </td>
    </tr>
    -->
    <tr>
        <td>
			<span class="pre_span"><font color="red"></font></span>
        	<label>  信用卡&nbsp;&nbsp;</label>
        	<input type="hidden" id="creditHistoryId" name="creditHistoryId" />
            <select id="hasCreditCard" name="hasCreditCard"  editable="false" class="easyui-combobox" style="width:140px">
                <option value="1"> 有</option>
                <option selected="selected" value="0">无</option>
            </select>
        </td>
        <td >
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  总张数&nbsp;&nbsp;</label>
            <input id="cardNum" style="" name="cardNum" class="easyui-validatebox" maxlength="2"  disabled="disbaled"/><span> 张</span>
        </td>
        <td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  总额度&nbsp;&nbsp;</label>
            <input id="totalAmount" name="totalAmount" class="easyui-validatebox" maxlength="15"   disabled="disbaled"/><span> 元</span>
        </td>
        <td>
        	<span class="pre_span"><font color="red"></font></span>
        	<label>  已透支&nbsp;&nbsp;</label>
            <input id="overdrawAmount" name="overdrawAmount" class="easyui-validatebox" maxlength="15"  disabled="disbaled"/><span> 元</span>
        </td>
    </tr>
    <tr>
    	<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  贷款笔数</label>
            <input id="loanSize" name="loanSize" required="true"  maxlength="3"   validType="integerCheck" class="easyui-validatebox"/>
            
        </td>
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  客服姓名</label>
            <input type="hidden" id="serviceId" name="serviceId"/>
            <label id="customerName" name="customerName" style="margin-left: 10px;color: blue"/>
        </td>
        
        <!--<td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  客户来源</label>
            <select id="customerSource" name="customerSource" class="easyui-combobox" style="width:145px;">  
                <option value="报纸">报纸</option>
                <option value="宣传单">宣传单</option>
                <option value="电销">电销</option>                
                <option value="短信">短信</option>
                 <option value="网络">网络</option>
                 <option value="陌拜">陌拜</option>
                 <option value="市场/小区推广">市场/小区推广</option>
                 <option value="转介绍">转介绍</option>
                 <option value="同业渠道">同业渠道</option>
                 <option value="促销活动">促销活动</option>
                 <option value="其他（可文本输入）">其他（可文本输入）</option>
            </select>
        </td>
        -->
        
        <td colspan="2">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  申请日期</label>
        	<input id="carRequestDate" name="carRequestDate" class="easyui-validatebox" readonly="true"  style="width:150px"/>
        </td>
    </tr>
    <tr>
        <td>
            <span class="pre_span"><font color="red">*</font></span>
            <label>  营业网点</label>
            <input id="salesDeptId" name="salesDeptId" class="easyui-combobox"  editable="false" style="width:140px"/>
        </td>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  客户经理</label>
            <input type="hidden" id="crmId" name="crmId"/>
            <input id="managerName" name="managerName" class="easyui-combobox" style="width:140px"/>
        </td>
        <td colspan="2">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  复核人员</label>
            <input type="hidden" id="assessorId" name="assessorId"/>
            <input id="assessorName" name="assessorName" class="easyui-combobox"  style="width:140px" />
        </td>
    </tr>
    <tr>
        <td colspan="3">
        <label> 备注</label></br>
        <textarea id="remark" name="remark" rows="5" cols="120"  maxlength="1000" /></textarea>
        </td>
    </tr>
    </table>
    <table cellpadding="5" cellspacing="5" id="addCarTable2" class="addCarTable" style="display: none;height: 360px;text-align:left" >
    <#list 0..3 as i>
        <tr>
            <td>
                联系人${i+1}
           <input type="hidden" id="contacterId${i}" name=contacterList[${i}].id />
            </td>
            <td>
              <#if i==3>
             姓　　名   
                <input id="contacterName${i}"  name=contacterList[${i}].name  maxlength="15"  class="easyui-validatebox" />
            <#else>
            <font color="red">*</font>
             姓　　名   
                <input id="contacterName${i}"  name=contacterList[${i}].name  maxlength="15"  required="true"  class="easyui-validatebox" />
            </#if>
            </td>
            
            <td>
            
            <#if i<3>
             <font color="red">*</font>
            </#if>
               
                <#if i==0>
                关　　系
                <label>直系亲属</label>
                 <input type="hidden" value='直系亲属'  id="relationship${i}"   name=contacterList[${i}].relationship     />   
               <#elseif i==1 >
               关　　系
               <label>现单位同事</label>
                    <input type="hidden"  value='现单位同事' id="relationship${i}"   name=contacterList[${i}].relationship      />  
                <#elseif  i==2>
                关　　系
                     <label>其他联系人</label>
 					<input type="hidden"  value='其他联系人'  id="relationship${i}"  name=contacterList[${i}].relationship    />
                 <#else>
                 关　　系
 				    <select  id="relationship${i}" name=contacterList[${i}].relationship  class="easyui-combobox" >
	 				    <option  value="配偶">配偶</option>
	 				    <option value="直系亲属">直系亲属</option>
	 				    <option  value="前单位同事">前单位同事</option>
	 				    <option  value="现单位同事">现单位同事</option>
	 				    <option  value="其他联系人">其他联系人</option>
 				    </select>
 				</#if>
            </td>
            <td>
            <#if i==3>
             手机号码
                <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11"  class="easyui-validatebox"  validType="mobileCheck"  />
            
            <#else>
            <font color="red">*</font>
             手机号码
                <input id="mobilePhone${i}" name=contacterList[${i}].mobilePhone maxlength="11"  class="easyui-validatebox" required="true"  validType="mobileCheck"  />
            
            </#if>
            </td>
        </tr>
        <tr>
            <td>
            <#if i==3>
               联系地址
                <input id="address${i}" name=contacterList[${i}].address maxlength="50"  class="easyui-validatebox"  />
            
            <#else>
            <font color="red">*</font>
               联系地址
                <input id="address${i}" name=contacterList[${i}].address maxlength="50" required="true"  class="easyui-validatebox"  />
            
            </#if>
                    </td>
            <td>
            <#if i==3>
                 工作单位
                <input id="workUnit${i}" name=contacterList[${i}].workUnit  axlength="150"   class="easyui-validatebox" />
            
            <#else>
            <font color="red">*</font>
                 工作单位
                <input id="workUnit${i}" name=contacterList[${i}].workUnit required="true" axlength="150"   class="easyui-validatebox" />
            
            </#if>
                       </td>
            <td colspan="2">
            <#if i==3>
              知晓贷款
                <select  id="contacterHadKnown${i}" name=contacterList[${i}].hadKnown  editable="false" class="easyui-combobox" >
                    <option value="1">是</option>
                    <option value="0" selected>否 </option>
                </select>
            
            <#else>
            <font color="red">*</font>
              知晓贷款
                <select  id="contacterHadKnown${i}" name=contacterList[${i}].hadKnown  required="true"  editable="false" class="easyui-combobox" >
                    <option value="1">是</option>
                    <option value="0" selected>否 </option>
                </select>
            
            </#if>
            </td>
        </tr>
    </#list>
    </table>
    </form>
    <div id="addCarlg-buttons">
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="prevCarStepBtn" style="display: none;">上一步</a>
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="nextCarStepBtn">下一步</a>
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="addCarLoanBtn"  style="display: none;">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="cancelCarLoanModifyBtn" >取消</a>
    </div>
    </div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");

                 
                $('#addCarLoanBtn').bind('click',editCarLoan); 
                $('#cancelCarLoanModifyBtn').bind('click',cancelCarLoanModify);
            })
</script>		
</body>
</html>