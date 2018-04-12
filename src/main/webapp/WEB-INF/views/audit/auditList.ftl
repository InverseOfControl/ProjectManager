<#include "../macros/constant_output_macro.ftl">
<#include "../macros/multiSearchMacro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/audit/audit.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
 <script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
 
<style type="text/css">
		.datagrid-toolbar {
			height: 56px;
		}
		
		.browsePanel {
		    margin-bottom: 10px;
		    padding: 10px;
		}
		#approveForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:40px;
            line-height:18px;
            width:110px
        }
        #approveForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            text-indent:1em;
        }
        #approveForm table tr td:nth-child(even){
            background: #FFFFFF;
            padding-left:10px;
            text-align:left;
            text-indent:1em;
        }
        
        #approveForm1 table td{
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:40px;
            line-height:18px;
            width:110px
        }
        #approveForm1 table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:middle;
            text-indent:1em;
        }
        #approveForm1 table tr td:nth-child(even){
            background: #f1f5f9;
            padding-left:10px;
            text-align:middle;
            text-indent:1em;
        }
        #conditionalAgreeTb table tr td:nth-child(odd){text-align:left;}
        
         #carContacterBrowseTab2 table td,#browseForm table td,#browseCLForm table td,#approveCLForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:40px;
            line-height:18px;
            width:110px
        }
        #browseForm table tr td:nth-child(odd),#browseCLForm table tr td:nth-child(odd),#approveCLForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            text-indent:1em;
        }
        #browseFormtable tr td:nth-child(even),#browseCLForm table tr td:nth-child(even),#approveCLForm table tr td:nth-child(even){
            background: #FFFFFF;
            padding-left:10px;
            text-align:left;
            text-indent:1em;
        }
       
       
        #carContacterBrowseTab3 table td,#browseForm table td,#browseCLExtensionForm table td,#approveExtensionCLForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:40px;
            line-height:18px;
            width:110px
        }
        #browseForm table tr td:nth-child(odd),#browseCLExtensionForm table tr td:nth-child(odd),#approveExtensionCLForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:right;
            text-indent:1em;
        }
        #browseFormtable tr td:nth-child(even),#browseCLExtensionForm table tr td:nth-child(even),#approveExtensionCLForm table tr td:nth-child(even){
            background: #FFFFFF;
            padding-left:10px;
            text-align:left;
            text-indent:1em;
        }
        
        
        #approveForm #conditionalAgreeTb tr td{text-align:left;}
        
        #approveForm1 #conditionalAgreeTb tr td{text-align:middle;}
       
        #title h3{
        	margin-left:100px;
        }
        #content{
        	margin-top:30px;
        	
        }
         #content ul{
          float:left;          
        }
        #content ul li{
        	list-style-type:none;
        	float:left;
        	margin-left:25px;
        }
       
        #submit_btn a{
        	margin-left:80px;
        }
        
        #content input{
        	border-style:none;
        	background-color:transparent;
        	width:50px;
        }
</style>
</head>
<body>
	<table id="loanPageTb" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:92px;padding-top:8px;">
		<div style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
     	<@htmlMultiSearch/>
     	<span>借款状态：</span><input id="loanStatusComb" class="easyui-combobox" editable="false" data-options="width:100" />
       </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
	</div>
	   <span  style="display:none;"> 
 			  <input type="hidden" id="isJump"   value="${isJump!""}"  />	
     	 </span>
	<!--小企业贷之"同意"签批确认对话框-->
	<div id="approveConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul >
  									<li><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  									<li><lable>申请金额：</lable> <input type="text" style="width:90px;" name="requestMoney" id="requestMoney" readonly="true"/></li>
  									<li><lable>期限：</lable> <input type="text" name="requestTime" id="requestTime" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
            <span>  
              <a style="" href="javascript:void(0)" id="approveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
              <a style="" href="javascript:void(0)" id="approveConfirmCancelBt" class="easyui-linkbutton" >取消</a>

          </span>
				
		  </div>
	</div>
	
	<!--小企业贷之"有条件同意"确认对话框-->
	<div id="conditionalApproveConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul >
  									<li><lable>意见：</lable> <input type="text" style= "width:100px;" name="approveResult" id="approveResult" readonly="true"/></li>
  									<li><lable>申请金额：</lable> <input type="text" style="width:90px;" name="requestMoney" id="requestMoney" readonly="true"/></li>
  									<li><lable>期限：</lable> <input type="text" name="requestTime" id="requestTime" readonly="true"/></li>
  							</ul>
  							<ul>
  									<li><lable id="guarantee1" ></lable> </li>
  									<li><lable id="guarantee2" ></lable> </li>
  									<li><lable id="guarantee3" ></lable> </li>
  									<li><lable id="guarantee4"></lable> </li>
  							</ul>
                <div id='guaContent'>
                  
                </div>
  					</div>
  			</div>
  			<div id='submit_btn' style="float: left;">
  					
  					<br>           
              <span>
                
    					   <a style="" href="javascript:void(0)" id="conditionalApproveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>            
                
    					   <a style="" href="javascript:void(0)" id="conditionalApproveConfirmCancelBt" class="easyui-linkbutton" >取消</a>
                
              </span>

            
              
           
		  </div>
	</div>
	
	<!--小企业贷之"拒绝"确认对话框-->
	<div id="refuseApproveConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	<!--小企业贷退回确认对话框-->
	<div id="returnApproveConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable><input type="text" id="contractMatters2" readonly="true"></input></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="returnApproveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="returnApproveConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	
	<!--车贷之"同意"签批确认对话框-->
	<div id="approveCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul >
  									<li><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  									<li><lable>申请金额：</lable> <input type="text" style="width:90px;" name="requestMoney" id="requestMoney" readonly="true"/></li>
  									<li><lable>期限：</lable> <input type="text" name="requestTime" id="requestTime" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="approveCLConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="approveCLConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	<!--车贷之"同意"展期签批确认对话框-->
	<div id="approveExtensionCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul >
  									<li><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  									<li><lable>申请金额：</lable> <input type="text" style="width:90px;" name="requestMoney" id="requestMoney" readonly="true"/></li>
  									<li><lable>期限：</lable> <input type="text" name="requestTime" id="requestTime" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="approveCLConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="approveCLConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	
	
	
	<!--车贷之"拒绝"签批确认对话框-->
	<div id="refuseCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	
	
	<!--车贷之"拒绝"展期签批确认对话框-->
	<div id="refuseExtensionCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable> <input type="text" name="approveResult" id="approveResult" readonly="true"/></li>
  							</ul>
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="refuseApproveConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	<!--车贷"退回"确认对话框-->
	<div id="returnApproveCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable><input type="text" id="carContractMatters" readonly="true"></input> </li>
  							</ul>  							
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="returnApproveCLConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="returnApproveCLConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	
	<!--车贷"退回"展期确认对话框-->
	<div id="returnApproveExtensionCLConfirmDlg" class="easyui-dialog" style="width:400px;height:300px" closed="true" data-options="resizable:true">
		<div   style='padding-top:20px;padding-left:30px;'>
  					<div id='title'>
  							<h3>是否确认提交</h3>
  					</div>
  					<div id='content'>
  							<ul>
  									<li style='margin-left:110px;'><lable>意见：</lable><input type="text" id="carContractMatters" readonly="true"></input> </li>
  							</ul>  							
  					</div>
  			</div>
  			<div id='submit_btn'>
  					<br>
  					<br>
  					<br>
  					<br>
					<a style="" href="javascript:void(0)" id="returnApproveCLConfirmSubmitBt" class="easyui-linkbutton" >确定</a>
					<a style="" href="javascript:void(0)" id="returnApproveCLConfirmCancelBt" class="easyui-linkbutton" >取消</a>
		  </div>
	</div>
	<!-- 小企业贷签批对话框  -->
	<div id="approveDlg" class="easyui-dialog approveSeloanDialog" style="width: 980px;height:320px;overflow-y:scroll" closed="true" data-options="resizable:true" >
		  <div id="browseTabs" class="easyui-tabs">
		    <div id=" " title="借款信息" style="padding:20px">
      		<form id="approveForm" method="post" style="padding:20px">
      		<div id="auditLoanBasicInfo">
          	<table style="font-size:12px; width:100%; text-align:left;">
          		<tr>
      					<td><label>借款类型</label></td><td align="left"><label id="productName" ></label></td>
      					<td><label>申请金额</label></td><td align="left"><label id="requestMoney" ></label></td>
      					<td><label>申请期限</label></td><td align="left"><label id="requestTime" ></label></td>
      					<td><label>借款用途</label></td><td align="left"><label id="purpose" ></label></td>
      		    </tr>
               <tr class="newSeloanBrowse">
                       <td><label>还款来源</label></td>
                        <td align="left"><label id="sourceOfRepay"></label></td>
                </tr>
          	</table>
          	<div id="personApprovePanel" class="easyui-panel browsePanel" title="个人信息"  style="width:910px;">
      				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
      					<tr>
      	    				<td><label>姓       名</label></td><td align="left"><label id="personName" ></label></td>
      	    				<td><label>性       别</label></td><td align="left"><label id="personSex" ></label></td>
      	    				<td><label>身份证号</label></td><td align="left"><label id="personIdnum" ></label></td>
          				</tr>
          				<tr>
      	    				<td><label>婚姻状况</label></td><td align="left"><label id="personMarried" ></label></td>
      	    				<td><label>最高学历</label></td><td align="left"><label id="personEducationLevel" ></label></td>
      	    				<td class="oldSeloanBrowse"><label>是否有子女</label></td><td align="left" class="oldSeloanBrowse"><label id="personHasChildren" ></label></td>
          				</tr>
          				<tr>
      	    				<td class="oldSeloanBrowse"><label>邮政编码</label></td><td align="left" class="oldSeloanBrowse"><label id="personZipCode" ></label></td>
      	    				<td><label>住宅地址</label></td><td colspan="3" align="left" style="width:150px"><label id="personAddress" ></label></td>
                    <td class="newSeloanBrowse"><label>户口所在地</label></td>
                    <td align="left" class="newSeloanBrowse"><label id="placeDomicile"></label></td>
          				</tr>
          				<tr>
      	    				<td><label>手机号码</label></td><td align="left"><label id="personMobilePhone" ></label></td>
      	    				<td class="oldSeloanBrowse"><label>常用邮箱</label></td><td align="left" class="oldSeloanBrowse"><label id="personEmail" ></label></td>
      	    				<td><label>住宅电话</label></td><td align="left"><label id="personHomePhone" ></label></td>
          				</tr>
          				<tr>
          					<td><label>房产类型</label></td><td align="left"><label id="personHouseEstateType" ></label></td>
          					<td class="oldSeloanBrowse"><label>每月租金</label></td><td align="left" class="oldSeloanBrowse"><label id="personRentPerMonth" ></label></td>
          					<td class="oldSeloanBrowse"><label>房       贷</label></td><td align="left" class="oldSeloanBrowse"><label id="personHasHouseLoan" ></label></td>
          				    <td class="oldSeloanBrowse"><label>职业类型</label></td><td align="left" class="oldSeloanBrowse"><label id="professionType"></label></td>
          				
          				</tr>
          				<tr class="oldSeloanBrowse">
          					<td <label>房产地址</label></td><td colspan="5" align="left" style="width:150px"  ><label id="personHouseEstateAddress" ></label></td>
          				<tr>
          				<tr class="oldSeloanBrowse">
          					<td><label>月平均收入</label></td><td align="left"><label id="personIncomePerMonth" ></label></td>
          				</tr>
      				</table>
      	    </div>
      			<div id="companyApprovePanel" class="easyui-panel browsePanel" title="公司信息" style="width:910px;">

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
      	    				<td><label>企业全称</label></td><td align="left"><label id="companyName" ></label>
      	    				<td><label>所属行业</label></td><td align="left"><label id="companyIndustryInvolved" ></label>
      	    				<td><label>法人代表</label></td><td align="left"><label id="companyLegalRepresentative" ></label>
          				</tr>
          				<tr>
      	    				<td><label>法人代表身份证号</label></td><td align="left"><label id="companyLegalRepresentativeId" ></label>
      	    				<td><label>近年营业额</label></td><td align="left"><label id="companyIncomePerMonth" ></label>
      	    				<td><label>成立时间</label></td><td align="left"><label id="companyFoundedDate" ></label>
          				</tr>
          				<tr>
      	    				<td><label>企业类型</label></td><td align="left"><label id="companyCategory" ></label>
      	    				<td ><label>企业地址</label></td><td colspan="3" align="left" style="width:150px"><label id="companyAddress" ></label>
          				</tr>
          				<tr>
      	    				<td><label>平均年利润</label></td><td align="left"><label id="companyAvgProfitPerYear" ></label>
      	    				<td><label>企业电话</label></td><td align="left"><label id="companyPhone" ></label>
      	    				<td><label>邮政编码</label></td><td align="left"><label id="companyZipCode" ></label>
          				</tr>
          				<tr>
      	    				<td><label>经营场所</label></td><td align="left"><label id="companyOperationSite" ></label>
      	    				<td><label>主营业务</label></td><td align="left"><label id="companyMajorBusiness" ></label>
      	    				<td><label>员工人数</label></td><td align="left"><label id="companyEmployeesNumber" ></label>
          				</tr>
          				<tr>
      						<td><label>员工工资支出</label></td><td align="left"><label id="companyEmployeesWagesPerMonth" ></label>		    					
          				</tr>
                  
      				</table>
      			</div>
            </div>
      			<div id="otherPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
      				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
      					<tr>
      						<td><label>客服姓名</label></td><td align="left"><label id="serviceName" ></label></td>
      	    				<td><label>客户来源</label></td><td align="left"><label id="customerSource" ></label></td>
      	    				<td><label>申请日期</label></td><td align="left"><label id="requestDate" ></label></td>
          				</tr>
          				<tr>
      	    				<td><label>客户经理工号</label></td><td align="left"><label id="crmCode" ></label></td>
      	    				<td><label>客户经理</label></td><td align="left"><label id="crmName" ></label></td>
      	    				<td><label>营业网点</label></td><td align="left"><label id="salesDeptName" ></label></td>
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
      			<div id="approvePanel" class="easyui-panel" title="签批结果" style="width:910px;padding: 10px;">
      				<input type="hidden" id="loanId" />
      				<input type="hidden" id="productId" />
      				<!--<input type="hidden" id="personId" />-->
      			 	<input type="radio" name="agreementRD" value="10" checked><span>同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			 	<input type="radio" name="agreementRD" value="20"><span>有条件同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			 	<input type="radio" name="agreementRD" value="30"><span>拒绝</span></input>&nbsp;&nbsp;&nbsp;&nbsp;
      				<input type="radio" name="agreementRD" value="31"><span>退回门店</span></input>
      				<table id="agreeTb" style="font-size:12px; width:100%; text-align:left;margin-top: 5px;" cellspacing="5">
      					<tr>

      						<td>
      							<label style='float:left;margin-left:0'>申请金额</label><input style='float:left;margin-left:10px' id="agreeMoneyInput" type="text" required=true  digits=true  min=1  max=1000000 />
      							<span style='float:left;margin-left:0'>元</span>
      							<label>期       限</label>
      	    					<select id="agreeTimeComb" class="easyui-combobox">
      							    <option value="6">6期</option>
      							    <option value="9">9期</option>
      							    <option value="12">12期</option>
      							    <option value="18">18期</option>
      							    <option value="24">24期</option>
      							</select>
                    </td>
                  </tr>
                  <tr class='apprHasHouse'>
                    <td>
        							<label>管理费率</label>
        	    					<select id="hasHouse" class="easyui-combobox">
        							    <option value="0">无房</option>
        							    <option value="1">有房</option>							    
        							</select>
                      
                    </td>
                  </tr>
                  <tr  id = 'wsLoanApprTr' class= 'apprWSLoan' style="display: none;">
                  <td>
                    <label>  会员类型</label>
                    <select name="memberType" id="memberType"  required="true"  editable="false"  class="easyui-combobox">
                        <option value="1">免费</option>
                        <option value="2">付费（半年以下）</option>
                        <option value="3">付费（半年及以上）</option>

                    </select>
                  </td>                 
                </tr>
          				<tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMatters" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>
          				<tr>
      	    				<td><label style="">备注</label><textarea id="agreeReason" rows="5" cols="100"  maxlength="1000" /></textarea></td>
      	    				<td>
      	    					<a style='' href="javascript:void(0)" id="agreeSubmitBt" class="easyui-linkbutton" >提交</a>
      	    				</td>
      	    				<td id="xqd3">
      	    					<a href="javascript:void(0)" id="agreeCancelBt" class="easyui-linkbutton" >取消</a>
      	    				</td>
          				</tr>
          			</table>
          			<table id="conditionalAgreeTb" style="font-size:12px; width:100%; text-align:left;display: none;" cellspacing="5">
      					<tr>
      						<td>
      							<label style=''>申请金额&nbsp;&nbsp;&nbsp;&nbsp;</label><input style='' id="conditionalAgreeMoneyInput" type="text" required=true digits=true min=1 max=1000000/>
      							<span style=''>元</span>
      							<label style="">期       限</label>
      							<select  id="conditionalAgreeTimeComb" class="easyui-combobox">
      							    <option value="6">6期</option>
      							    <option value="9">9期</option>
      							    <option value="12">12期</option>
      							    <option value="18">18期</option>
      							    <option value="24">24期</option>
      							</select>
      							
      						</td>
      					</tr>
                <tr class='apprHasHouse'>
                    <td>
                      <label>管理费率</label>
                        <select id="hasHouse" class="easyui-combobox">
                          <option value="0">无房</option>
                          <option value="1">有房</option>                 
                      </select>
                      
                    </td>
                  </tr>
                <tr id = 'wsLoanConTr' class= 'apprWSLoan' style="display: none;">
                  <td>
                    <label>  会员类型</label>
                    <select name="memberType" id="memberType"  required="true"  editable="false"  class="easyui-combobox">
                        <option value="1">免费</option>
                        <option value="2">付费（半年以下）</option>
                        <option value="3">付费（半年及以上）</option>

                    </select>
                  </td>                 
                </tr>
                <tr class="apprWSLoan" style="display: none;">
                    <td>  
                    <label style="float:left">自然人担保: </label>
                    <div id = 'wsGuaNatr' class="guaLiCheckBox natural" style="text-align:left;"></div>

                    </td>
                  </tr>
                  <tr class="apprWSLoan" style="display: none;"> 
                    <td>  
                    <label style="float:left">法人担保: </label>
                    <div id = 'wsGuaLeg' class="guaLiCheckBox legal" style="text-align:left;"></div>
                    </td>
                  </tr>
      					<tr class="seConApprGua">
      						<td>	
      						<input  type="checkbox" id="guaranteeCheck1"/>
      						<label >自然人担保</label>
      						<input  id="conditionalAgreeGuaranteeInput1" type="text" />
      						<input  id="conditionalAgreeGuaranteeInput2" type="text" />
      						<a style="" id="addGuaInputBt1" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
      						</td>
      					</tr>
      					<tr class="seConApprGua">
      						<td>	
      						<input  type="checkbox" id="guaranteeCheck2"/>
      						<label >法人担保</label>
      						<input  id="conditionalAgreeGuaranteeInput3" type="text" />
      						<input  id="conditionalAgreeGuaranteeInput4" type="text" />
      						<a style="" id="addGuaInputBt2" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
      						</td>
      					</tr>
      					<tr>
      						<td>
      							<label style="">需补充证件</label>
      							
      							<input style="" id="certificates1Input" type="text"  maxlength="100" required=true />
      							<input style="" id="certificates2Input" type="text" maxlength="100"  />
      							<input style="" id="certificates3Input" type="text" maxlength="100"  />
      							<a style="" id="addNeedCardInputBt" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
      						</td>
      					</tr>
      					 <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMattersInput" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>					
      					<tr>
      						<td><label style="">备注</label><textarea id="conditionalAgreeRemarkTA" rows="5" cols="100"  maxlength="1000" /></textarea></td>
      						<td >
      							<a style="" href="javascript:void(0)" id="conditionalAgreeSubmitBt" class="easyui-linkbutton" >提交</a>
      						</td>
      						<td id="xqd2">
      							<a style="" href="javascript:void(0)" id="conditionalAgreeCancelBt" class="easyui-linkbutton" >取消</a>
      						</td>
      					</tr>
      				
      				</table>
          			<table id="refuseTb" style="font-size:12px; width:100%; text-align:left;display: none;" >
      					<tr>
      						<td>
      						<label style="margin-right:50px">拒绝原因</label>&nbsp;&nbsp;&nbsp;&nbsp;
      						<label>一级原因</label>
      						<input id="refuseFirstReason" class="easyui-combobox" editable="false" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
      						<label>二级原因</label><input id="refuseSecondReason" class="easyui-combobox" editable="false" data-options="width:200" />
      						
      						</td>
      					</tr>
          				<tr>
      	    				<td><textarea id="refuseRemarkTA" rows="10" cols="100" required=true maxlength="290"></textarea></td>
      	    				<td>
      	    					<a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>
      	    				</td>
      	    				<td id="xqd1">
      	    					<a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
      	    				</td>
          				</tr>
          			</table>
          			<table id="returnTable" style="font-size:12px; width:100%; text-align:left;display: none;" >
      					<tr ><td ><label>退回原因</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
      					<tr>
      						<td>						
      	    				<textarea id="returnContractMatters" rows="10" cols="100" required=true maxlength="800"></textarea>
      						</td>
      					</tr>
      					<tr><td><label >备注</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>    	
          				<tr>
      	    				<td>	    								
      	    				<textarea id="returnReason" rows="10" cols="100" required=true maxlength="290"></textarea>
      	    				</td>
      	    				<td>
      	    					<a href="javascript:void(0)" id="refuseSubmitBt2" class="easyui-linkbutton" >提交</a>
      	    				</td>
      	    				<td id="xqd4">
      	    					<a href="javascript:void(0)" id="refuseCancelBt2" class="easyui-linkbutton" >取消</a>
      	    				</td>
          				</tr>
          			</table>
      			</div>
      		</form>
		    </div>
    		<div id="contactTab" title="联系人信息" style="padding:20px">
      		<div id="contacterBrowsePanelTemplate"
                           style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
            <form id="approveForm" method="post" style="padding:20px">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="oldSeloanBrowse">
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
                            <td><label>联系地址</label></td>
                            <td><label id="address"></label></td>      
                            <td><label>固定电话</label></td>
                            <td><label id="contacterHomePhone"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="newSeloanBrowse">
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
             </form>
          </div>
    		</div>
  		  <div id="guraTab" title="担保人信息" style="padding:20px">
      		<div id="guaranteeBrowsePanelTemplate"
                           style="padding:10px;width:910px;display:none;margin-bottom: 10px;">
            <form id="approveForm" method="post" style="padding:20px">
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
                                <td><label id="guaranteeSex"></label></td>
                                <td><label>婚姻状况</label></td>
                                <td><label id="guaranteeMarried"></label></td>
                                <td><label>最高学历</label></td>
                                <td><label id="guaranteeEducationLevel"></label></td>
                            <tr id="tr3">
                                <td><label>是否有子女</label></td>
                                <td><label id="guaranteeHasChildren"></label></td>
                                <td><label>住宅地址</label></td>
                                <td colspan="3" align="left"><label id="guaranteeAddress"></label></td>
                            </tr>
                            <tr id="tr4">
                                <td><label>手机号码</label></td>
                                <td><label id="guaranteeMobilePhone"></label></td>
                                <td><label>常用邮箱</label></td>
                                <td><label id="guaranteeEmail"></label></td>
                                <td><label>住宅电话</label></td>
                                <td><label id="personHomePhone"></label></td>
                            </tr>
                            <tr id="tr5">
                                <td><label>企业全称</label></td>
                                <td><label id="guaranteeCompanyFullName"></label></td>
                                <td><label>邮政编码</label></td>
                                <td><label id="guaranteeZipCode"></label></td>
                                <td><label>企业电话</label></td>
                                <td><label id="guaranteeCompanyPhone"></label></td>
                            </tr>
                            <tr id="tr6">
                                <td><label>企业地址</label></td>
                                <td colspan="3" align="left"><label id="guaranteeCompanyAddress"></label></td>
                            </tr>
                            <tr id="tr7">
                                <td><label>类 型</label></td>
                                <td><label id="guaType"></label></td>     
                                <td><label>企业全称</label></td>
                                <td><label id="guaCompanyFullName"></label></td>                            
                                <td><label>企业电话</label></td>
                                <td><label id="guaCompanyPhone"></label></td>
                            </tr>
                            <tr id="tr8">
                                <td><label>企业地址</label></td>
                                <td colspan="3" align="left"><label id="guaCompanyAddress"></label></td>
                                <td><label>邮政编码</label></td>
                                <td><label id="guaZipCode"></label></td>
                            </tr>
                        </table>
            </form>
          </div>
		    </div>
      </div>
	</div>
<!-- 同城贷签批 -->
<div id="cityWideApproveDlg" class="easyui-dialog approveSeloanDialog" style="width: 980px;height:320px;overflow-y:scroll" closed="true" data-options="resizable:true" >
      <div id="browseTabs" class="easyui-tabs">
        <div id="" title="借款信息" style="padding:20px">
          <form id="approveForm" method="post" style="padding:20px">
          
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
            <div id="personApprovePanel" class="easyui-panel browsePanel" title="个人信息"  style="width:910px;">
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
            <div id="companyApprovePanel" class="easyui-panel browsePanel" title="公司信息" style="width:910px;">
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
            <div id="otherPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
              <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                <tr>
                  <td><label>客服姓名</label></td><td align="left"><label id="serviceName" ></label></td>
                    <td><label>客户来源</label></td><td align="left"><label id="customerSource" ></label></td>
                    <td><label>申请日期</label></td><td align="left"><label id="requestDate" ></label></td>
                  </tr>
                  <tr>
                    <td><label>客户经理工号</label></td><td align="left"><label id="crmCode" ></label></td>
                    <td><label>客户经理</label></td><td align="left"><label id="crmName" ></label></td>
                    <td><label>营业网点</label></td><td align="left"><label id="salesDeptName" ></label></td>
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
            <div id="approvePanel" class="easyui-panel" title="签批结果" style="width:910px;padding: 10px;">
              <input type="hidden" id="loanId" />
              <input type="hidden" id="productId" />
              <!--<input type="hidden" id="personId" />-->
              <input type="radio" name="agreementRD" value="10" checked><span>同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="radio" name="agreementRD" value="20"><span>有条件同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="radio" name="agreementRD" value="30"><span>拒绝</span></input>&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="radio" name="agreementRD" value="31"><span>退回门店</span></input>
              <table id="agreeTb" style="font-size:12px; width:100%; text-align:left;margin-top: 5px;" cellspacing="5">
                <tr>
                  <td>
                    <label style='float:left;margin-left:0'>申请金额</label><input style='float:left;margin-left:10px' id="agreeMoneyInput" type="text" required=true  digits=true  min=1  max=1000000 />
                    <span style='float:left;margin-left:0'>元</span>
                    <label>期       限</label>
                      <select id="agreeTimeComb" class="easyui-combobox">
                        <option value="6">6期</option>
                        <option value="9">9期</option>
                        <option value="12">12期</option>
                        <option value="18">18期</option>
                        <option value="24">24期</option>
                    </select>
                
                    </td>
                  </tr>
                  <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMatters" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>
                  <tr>
                    <td><label style="">备注</label><textarea id="agreeReason" rows="5" cols="100"  maxlength="1000" /></textarea></td>
                    <td>
                      <a style='' href="javascript:void(0)" id="agreeSubmitBt" class="easyui-linkbutton" >提交</a>
                    </td>
                    <td id="xqd3">
                      <a href="javascript:void(0)" id="agreeCancelBt" class="easyui-linkbutton" >取消</a>
                    </td>
                  </tr>
                </table>
                <table id="conditionalAgreeTb" style="font-size:12px; width:100%; text-align:left;display: none;" cellspacing="5">
                  <tr>
                    <td>
                      <label style=''>申请金额&nbsp;&nbsp;&nbsp;&nbsp;</label><input style='' id="conditionalAgreeMoneyInput" type="text" required=true digits=true min=1 max=1000000/>
                      <span style=''>元</span>
                      <label style="">期       限</label>
                      <select  id="conditionalAgreeTimeComb" class="easyui-combobox">
                          <option value="6">6期</option>
                          <option value="9">9期</option>
                          <option value="12">12期</option>
                          <option value="18">18期</option>
                          <option value="24">24期</option>
                      </select>
                     
                    </td>
                  </tr>
                  <tr>
                    <td>  
                    <label style="float:left">自然人担保: </label>
                    <div id = 'CWconAgrNaGuaLi' class="natural guaLiCheckBox" style="text-align:left;"></div>

                    </td>
                  </tr>
                  <tr>
                    <td>  
                    <label style="float:left">法人担保: </label>
                    <div id = 'CWconAgrLeGuaLi' class="legal guaLiCheckBox" style="text-align:left;"></div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <label style="float:left">需补充证件</label>
                      
                      <input style="float:left" id="certificates1Input" type="text" maxlength="100" required=true  />
                      <input style="float:left" id="certificates2Input" type="text" maxlength="100" />
                      <input style="float:left" id="certificates3Input" type="text" maxlength="100" />
                      <a style="float:left" id="addNeedCardInputBt" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a>
                    </td>
                  </tr>
                   <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMattersInput" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>         
                  <tr>
                    <td><label style="">备注</label><textarea id="conditionalAgreeRemarkTA" rows="5" cols="100"  maxlength="1000" /></textarea></td>  
                     <td >
                      <a style="" href="javascript:void(0)" id="conditionalAgreeSubmitBt" class="easyui-linkbutton" >提交</a>
                    </td>
                    <td id="xqd2">
                      <a style="" href="javascript:void(0)" id="conditionalAgreeCancelBt" class="easyui-linkbutton" >取消</a>
                    </td>
                                      
                  </tr>
                 
              
                </table>
                <table id="refuseTb" style="font-size:12px; width:100%; text-align:left;display: none;" >
                <tr>
                  <td>
                  <label style="margin-right:50px">拒绝原因</label>&nbsp;&nbsp;&nbsp;&nbsp;
                  <label>一级原因</label>
                  <input id="refuseFirstReason" class="easyui-combobox" editable="false" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
                  <label>二级原因</label><input id="refuseSecondReason" class="easyui-combobox" editable="false" data-options="width:200" />
                  
                  </td>
                </tr>
                  <tr>
                    <td><textarea id="refuseRemarkTA" rows="10" cols="100" required=true maxlength="290"></textarea></td>
                    <td>
                      <a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>
                    </td>
                    <td id="xqd1">
                      <a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
                    </td>
                  </tr>
                </table>
                <table id="returnTable" style="font-size:12px; width:100%; text-align:left;display: none;" >
                <tr ><td ><label>退回原因</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                <tr>
                  <td>            
                    <textarea id="returnContractMatters" rows="10" cols="100" required=true maxlength="800"></textarea>
                  </td>
                </tr>
                <tr><td><label >备注</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>      
                  <tr>
                    <td>                      
                    <textarea id="returnReason" rows="10" cols="100" required=true maxlength="290"></textarea>
                    </td>
                    <td>
                      <a href="javascript:void(0)" id="refuseSubmitBt2" class="easyui-linkbutton" >提交</a>
                    </td>
                    <td id="xqd4">
                      <a href="javascript:void(0)" id="refuseCancelBt2" class="easyui-linkbutton" >取消</a>
                    </td>
                  </tr>
                </table>
            </div>
          </form>
        </div>
        <div id="cityWidecontactTab" title="联系人信息" style="padding:20px">
          <div id="cityWideApprContacterBrowsePanelTemplate"
                           style="padding:10px;width:910px;margin-bottom: 10px;display:none;">
            <form id="approveForm" method="post" style="padding:20px">
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
             </form>
          </div>
        </div>
        <div id="cityWideGuraTab" title="担保人信息" style="padding:20px">
          <div id="cityWideApprguaranteeBrowsePanelTemplate"
                           style="padding:10px;width:910px;display:none;margin-bottom: 10px;">
            <form id="approveForm" method="post" style="padding:20px">
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
                                <td><label id="guaranteeSex"></label></td>
                                <td><label>婚姻状况</label></td>
                                <td><label id="guaranteeMarried"></label></td>
                                <td><label>最高学历</label></td>
                                <td><label id="guaranteeEducationLevel"></label></td>
                            <tr id="tr3">
                                <td><label>是否有子女</label></td>
                                <td><label id="guaranteeHasChildren"></label></td>
                                <td><label>住宅地址</label></td>
                                <td colspan="3" align="left"><label id="guaranteeAddress"></label></td>
                            </tr>
                            <tr id="tr4">
                                <td><label>手机号码</label></td>
                                <td><label id="guaranteeMobilePhone"></label></td>
                                <td><label>常用邮箱</label></td>
                                <td><label id="guaranteeEmail"></label></td>
                                <td><label>住宅电话</label></td>
                                <td><label id="personHomePhone"></label></td>
                            </tr>
                            <tr id="tr5">
                                <td><label>企业全称</label></td>
                                <td><label id="guaranteeCompanyFullName"></label></td>
                                <td><label>邮政编码</label></td>
                                <td><label id="guaranteeZipCode"></label></td>
                                <td><label>企业电话</label></td>
                                <td><label id="guaranteeCompanyPhone"></label></td>
                            </tr>
                            <tr id="tr6">
                                <td><label>企业地址</label></td>
                                <td colspan="3" align="left"><label id="guaranteeCompanyAddress"></label></td>
                            </tr>
                            <tr id="tr7">
                                <td><label>类 型</label></td>
                                <td><label id="guaType"></label></td>     
                                <td><label>企业全称</label></td>
                                <td><label id="guaCompanyFullName"></label></td>                            
                                <td><label>企业电话</label></td>
                                <td><label id="guaCompanyPhone"></label></td>
                            </tr>
                            <tr id="tr8">
                                <td><label>企业地址</label></td>
                                <td colspan="3" align="left"><label id="guaCompanyAddress"></label></td>
                                <td><label>邮政编码</label></td>
                                <td><label id="guaZipCode"></label></td>
                            </tr>
                        </table>
            </form>
          </div>
        </div>
      </div>
  </div>
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
                            <td class="oldSeloanBrowse"><label>是否有子女</label></td>
                            <td align="left" class="oldSeloanBrowse"><label id="personHasChildren"></label></td>
                        </tr>
                        <tr>
                            <td class="oldSeloanBrowse"><label>邮政编码</label></td>
                            <td align="left" class="oldSeloanBrowse"><label id="personZipCode"></label></td>
                            <td><label>住宅地址</label></td>
                            <td  colspan="3" align="left"><label id="personAddress"></label></td>
                             <td class="newSeloanBrowse"><label>户口所在地</label></td>
                            <td align="left" class="newSeloanBrowse"><label id="placeDomicile"></label></td>
                        </tr>
                        <tr>
                            <td><label>手机号码</label></td>
                            <td align="left"><label id="personMobilePhone"></label></td>
                            <td class="oldSeloanBrowse"><label>常用邮箱</label></td>
                            <td align="left" class="oldSeloanBrowse"><label id="personEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td align="left"><label id="personHomePhone"></label></td>
                        </tr>
                        <tr>
                            <td><label>房产类型</label></td>
                            <td align="left"><label id="personHouseEstateType"></label></td>
                            <td class="oldSeloanBrowse"><label>每月租金</label></td>
                            <td align="left" class="oldSeloanBrowse"><label id="personRentPerMonth"></label></td>
                            <td class="oldSeloanBrowse"><label>房 贷</label></td>
                            <td align="left" class="oldSeloanBrowse"><label id="personHasHouseLoan"></label></td>
                            <td class="oldSeloanBrowse"><label>职业类型</label></td><td align="left" class="oldSeloanBrowse"><label id="professionType"></label></td>
                            
                        </tr>
                        <tr class="oldSeloanBrowse">
                            <td><label>房产地址</label></td>
                            <td colspan="5" align="left"><label id="personHouseEstateAddress"></label></td>
                        <tr>
                        <tr class="oldSeloanBrowse">
                            <td  ><label>月平均收入</label></td>
                            <td align="left"  ><label id="personIncomePerMonth"></label></td>
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
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="oldSeloanBrowse">
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
                            <td><label>联系地址</label></td>
                            <td><label id="address"></label></td>      
                            <td><label>固定电话</label></td>
                            <td><label id="contacterHomePhone"></label></td>
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3" class="newSeloanBrowse">
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
            <div id="guaranteeBrowseTab" title="担保人信息" style="padding:20px">
                <div id="guaranteeBrowsePanelTemplate2"
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
                            <td><label id="guaranteeSex"></label></td>
                            <td><label>婚姻状况</label></td>
                            <td><label id="guaranteeMarried"></label></td>
                            <td><label>最高学历</label></td>
                            <td><label id="guaranteeEducationLevel"></label></td>
                        <tr id="tr3">
                            <td><label>是否有子女</label></td>
                            <td><label id="guaranteeHasChildren"></label></td>
                            <td><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeAddress"></label></td>
                        </tr>
                        <tr id="tr4">
                            <td><label>手机号码</label></td>
                            <td><label id="guaranteeMobilePhone"></label></td>
                            <td><label>常用邮箱</label></td>
                            <td><label id="guaranteeEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td><label id="personHomePhone"></label></td>
                        </tr>
                        <tr id="tr5">
                            <td><label>企业全称</label></td>
                            <td><label id="guaranteeCompanyFullName"></label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaranteeZipCode"></label></td>
                            <td><label>企业电话</label></td>
                            <td><label id="guaranteeCompanyPhone"></label></td>
                        </tr>
                        <tr id="tr6">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeCompanyAddress"></label></td>
                        </tr>
                        <tr id="tr7">
                            <td><label>类 型</label></td>
                            <td><label id="guaType"></label></td>     
                            <td><label>企业全称</label></td>
                            <td><label id="guaCompanyFullName"></label></td>                            
                            <td><label>企业电话</label></td>
                            <td><label id="guaCompanyPhone"></label></td>
                        </tr>
                        <tr id="tr8">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaCompanyAddress"></label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaZipCode"></label></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="approveResultBrowseTab2" title="审核意见" style="padding:20px">            
            </div>
        </div>
    </form>
</div>
	
<!-- 查看小企业贷-同城贷 -->

<div id="browseCityWideSeLoan" class="easyui-dialog cityWideLoanDialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true" data-options="resizable:true">
    <form id="browseForm" class="cityWideLoanForm" method="post">
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
                        <tr>
                          <div style="line-height:28px;font-weight:bold;color:blue"><label id="flag" ></label></div>
                        </tr>
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
                            <td><label id="guaranteeSex"></label></td>
                            <td><label>婚姻状况</label></td>
                            <td><label id="guaranteeMarried"></label></td>
                            <td><label>最高学历</label></td>
                            <td><label id="guaranteeEducationLevel"></label></td>
                        <tr id="tr3">
                            <td><label>是否有子女</label></td>
                            <td><label id="guaranteeHasChildren"></label></td>
                            <td><label>住宅地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeAddress"></label></td>
                        </tr>
                        <tr id="tr4">
                            <td><label>手机号码</label></td>
                            <td><label id="guaranteeMobilePhone"></label></td>
                            <td><label>常用邮箱</label></td>
                            <td><label id="guaranteeEmail"></label></td>
                            <td><label>住宅电话</label></td>
                            <td><label id="personHomePhone"></label></td>
                        </tr>
                        <tr id="tr5">
                            <td><label>企业全称</label></td>
                            <td><label id="guaranteeCompanyFullName"></label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaranteeZipCode"></label></td>
                            <td><label>企业电话</label></td>
                            <td><label id="guaranteeCompanyPhone"></label></td>
                        </tr>
                        <tr id="tr6">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaranteeCompanyAddress"></label></td>
                        </tr>
                        <tr id="tr7">
                            <td><label>类 型</label></td>
                            <td><label id="guaType"></label></td>     
                            <td><label>企业全称</label></td>
                            <td><label id="guaCompanyFullName"></label></td>                            
                            <td><label>企业电话</label></td>
                            <td><label id="guaCompanyPhone"></label></td>
                        </tr>
                        <tr id="tr8">
                            <td><label>企业地址</label></td>
                            <td colspan="3" align="left"><label id="guaCompanyAddress"></label></td>
                            <td><label>邮政编码</label></td>
                            <td><label id="guaZipCode"></label></td>
                        </tr>
                       
                    </table>
                </div>
            </div>

            <div id="cityWideApproveResultBrowseTab" title="审核意见" style="padding:20px">            
            </div>
        </div>
    </form>
  
</div>

<!-- 查看车贷  -->
<div id="browseCLDlg" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true"
     data-options="resizable:true">
     
    <form id="browseCLForm" method="post">
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
                            <td><label>子女在读学校</label></td>
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
                            <td ><label>房贷情况</label></td>
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
                        </tr>
                        <tr>
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
                            <td  align="left"><label id="companyAddress"></label></td>
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
                            <td ><label id="address"></label></td>                       
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>		
				 <div id="approveResultBrowseTab4" title="审核意见" style="padding:20px">                   
		         </div>
        </div>
    </form>
</div>




<!-- 查看车贷展期  -->
<div id="browseCLExtensionDlg" class="easyui-dialog" style="width: 980px;height: 320px;overflow-y:scroll" closed="true"
     data-options="resizable:true">
     
    <form id="browseCLExtensionForm" method="post">
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
                            <td><label>子女在读学校</label></td>
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
                            <td colspan="5" align="left"><label id="companyAddress"></label></td>
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
                            <td><label>业务主任工号</label></td>
                            <td align="left"><label id="directorCode"></label></td>
                            <td><label>业务主任</label></td>
                            <td align="left"><label id="directorName"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
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
                            <td><label id="address"></label></td>                                
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>		
				 <div id="approveExtensionResultBrowseTab4" title="审核意见" style="padding:20px">                   
		         </div>
        </div>
    </form>
</div>



<!-- 车贷签批对话框  -->
	<div id="approveCLDlg" class="easyui-dialog" style="width: 1200px;height:320px;overflow-y:scroll" closed="true" data-options="resizable:true" >
		 <div id="browseTabs" class="easyui-tabs">
		   <div id="" title="借款信息" style="padding:20px">
		<form id="approveCLForm" method="post" style="padding: 20px;">
        	<table style="font-size:12px; width:100%; text-align:left;">
        		<tr>
					<td><label>借款类型</label></td><td align="left"><label id="productName" ></label></td>
					<td><label>贷款类型</label></td><td align="left"><label id="loanType" ></label></td>
					<td><label>申请金额</label></td><td align="left"><label id="requestMoney" ></label></td>
					<td><label>申请期限</label></td><td align="left"><label id="requestTime" ></label></td>
					<!--<td><label>借款用途</label></td><td align="left"><label id="purpose" ></label></td>
					-->
				</tr>
        	</table>
        	<div id="personApproveCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>姓       名</label></td><td align="left"><label id="personName" ></label></td>
	    				<td><label>性       别</label></td><td align="left"><label id="personSex" ></label></td>
	    				<td><label>身份证号</label></td><td align="left"><label id="personIdnum" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>婚姻状况</label></td><td align="left"><label id="personMarried" ></label></td>
	    				<!--<td><label>最高学历</label></td><td align="left"><label id="personEducationLevel" ></label></td>
	    				-->
	    				 <td><label>最高学历</label></td>
                            <td align="left"><label id="topEducation"></label></td>
	    				<td><label>是否有子女</label></td><td align="left"><label id="personHasChildren" ></label></td>
	    				<td><label>子女在读学校</label></td><td align="left"><label id="childrenSchool"></label></td>
    				</tr>
    				<tr>
    					<td><label>手机号码</label></td><td align="left"><label id="personMobilePhone" ></label></td>
	    				<td><label>常用邮箱</label></td><td align="left"><label id="personEmail" ></label></td>
	    				<td><label>住宅电话</label></td><td align="left"><label id="personHomePhone" ></label></td>
    				</tr>
    				<tr>
    					<td><label>户籍地址</label></td><td colspan="3" align="left" style="width:150px"><label id="personPlaceDomicile" ></label></td>
    					
    					<td><label>邮政编码</label></td><td align="left" ><label id="personHouseholdZipCode" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>现居住地址</label></td><td colspan="3" align="left" style="width:150px"><label id="personAddress" ></label></td>
	    				
	    				<td><label>邮政编码</label></td><td align="left"><label id="personZipCode" ></label></td>
    				</tr>
    				<tr>
    					<td><label>居住类型</label></td><td align="left"><label id="personLiveType" ></label></td>
    					<td><label>每月租金</label></td><td align="left"><label id="personRentPerMonth" ></label></td>
    					<td ><label>房贷情况</label></td>
                            <td align="left"><label id="homeCondition"></label></td>
    					<td><label>可接受的最高月还款额</label></td><td align="left"><label id="maxRepayAmount"></label></td>
    				</tr>
    				<tr>
                            <td><label>月收入/元</label></td>
                            <td align="left"><label id="monthIncome"></label></td>
                            
                        </tr>
				</table>
			</div>
			<div id="personApproveCLPanel" class="easyui-panel browsePanel" title="车辆信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>车辆品牌</label></td><td align="left"><label id="vehicleBrand" ></label></td>
	    				<td><label>车       型</label></td><td align="left"><label id="vehicleModel" ></label></td>
	    				<td><label>车       龄</label></td><td align="left"><label id="vehicleCoty" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>行驶里程</label></td><td align="left"><label id="vehicleMileage" ></label></td>
	    				<td><label>车  牌  号</label></td><td align="left"><label id="vehiclePlateNumber" ></label></td>
	    				<td><label>车  架  号</label></td><td align="left"><label id="vehicleFrameNumber" ></label></td>
    				</tr>
    				<tr>
    					<td><label>是否有车贷</label></td>
                            <td align="left"><label id="isHaveCarLoan"></label></td>
    				</tr>
				</table>
			</div>
			<div id="personApproveCLPanel" class="easyui-panel browsePanel" title="职业信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>单位全称</label></td><td align="left"><label id="companyName" ></label></td>
	    				<td><label>单位地址</label></td><td align="left" style="width:150px"><label id="companyAddress" ></label></td>
	    				<td><label>单位行业类别</label></td><td align="left"><label id="unitIndustryCategory" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>单位性质</label></td><td align="left"><label id="personCompanyType" ></label></td>
	    				<td><label>部       门</label></td><td align="left"><label id="personDeptName" ></label></td>
	    				<td><label>职       务</label></td><td align="left"><label id="personJob" ></label></td>
	    				<td><label>固话分机</label></td><td align="left"><label id="personExt" ></label></td>
    				</tr>
    				<!--<tr>
	    				<td><label>职业类型</label></td><td align="left"><label id="professionType"></label></td>                            
	    				<td><label>月基本薪金</label></td><td align="left"><label id="personIncomePerPonth" ></label></td>
	    				<td><label>月发薪日</label></td><td align="left"><label id="personPayDate" ></label></td>
	    				<td><label>其他收入</label></td><td align="left"><label id="personOtherIncome" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>工作证明人</label></td><td align="left"><label id="personWitness" ></label></td>
	    				<td><label>部       门</label></td><td align="left"><label id="personWorkThatDept" ></label></td>
	    				<td><label>职       务</label></td><td align="left"><label id="personWorkThatPosition" ></label></td>
	    				<td><label>联系电话</label></td><td align="left"><label id="personWorkThatTell" ></label></td>
    				</tr>
    				<tr class="enterpprise3" style="dispaly:none">
                            <td><label>私营企业类型</label></td>
                            <td align="left"><label id="privateEnterpriseType"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="foundedDate"></label></td>
                            <td><label>经营场所</label></td> 
                            <td align="left"><label id="businessPlace"></label></td>                           
                        </tr>
                        <tr class="enterpprise4" style="dispaly:none">
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
			<div id="personApproveCLPanel" class="easyui-panel browsePanel" title="信贷历史" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>信用卡</label></td><td align="left"><label id="creditHistoryHasCreditCard" ></label></td>
	    				<td><label>总张数</label></td><td align="left"><label id="creditHistoryCardNum" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>总  额  度</label></td><td align="left"><label id="creditHistoryTotalAmount" >TODO</label></td>
	    				<td><label>已  透  支</label></td><td align="left"><label id="creditHistoryOverdrawAmount" ></label></td>
    				</tr>
    				<tr>
    					<td><label>贷款笔数</label></td>
                            <td align="left"><label id="loanSize"></label></td>
    				</tr>
				</table>
			</div>
			<div id="otherApproveCLPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
						<td><label>客服姓名</label></td><td align="left"><label id="serviceName" ></label></td>
	    				<!--<td><label>客户来源</label></td><td align="left"><label id="customerSource" ></label></td>
	    				-->
	    				<td><label>申请日期</label></td><td align="left"><label id="requestDate" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>客户经理工号</label></td><td align="left"><label id="crmCode" ></label></td>
	    				<td><label>客户经理</label></td><td align="left"><label id="crmName" ></label></td>
	    				<td><label>营业网点</label></td><td align="left"><label id="salesDeptName" ></label></td>
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
			<div id="approveCLPanel" class="easyui-panel browsePanel" title="签批结果" style="width:1200px;">
			<div>
				<div style=" float:left;">
						<label>众安反欺诈等级：</label>
						<span id="zonganGrade"></span>
				</div>
				<div style=" float:left;margin-left: 20px">
						<label>捞财宝风控结果：</label>
						<span id="zonganResult"></span>
				</div>
			</div></br></br>
				<input type="hidden" id="loanId" />
			 	<input type="radio" name="agreementRD" value="10" checked><span>同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" name="agreementRD" value="20"><span>有条件同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 	<input type="radio" name="agreementRD" value="30"><span>拒绝</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="agreementRD" value="31"><span>退回门店</span></input>
				<table id="agreeTb" style="font-size:12px; width:100%; text-align:left;">
  					<tr>
  						<td>
  							<label style='float:left;margin-left:10px'>申请金额</label><input style='float:left;margin-left:10px' id="agreeMoneyInput" type="text" required=true digits=true />
  	    					<span style='float:left;margin-left:10px'>元</span>
  	    					<label style='margin-right:30px'>期       限</label>
  	    					<select style='float:left;margin-left:30px' id="agreeTimeComb" name="agreeTimeComb" class="easyui-combobox">
  							  <!--   <option value="6">6期</option>
                    				<option value="9">9期</option>
                    				<option value="12">12期</option>
                   				<option value="18">18期</option>
                    				<option value="24">24期</option> -->
  							</select>
  	    				</td>
      				</tr>
      				<tr>
      				   <td>
  	    				    <label>贷款类型 </label>
  	    				    
  	    				    <!--<label style="width:140px" id="loanTypeApprove" name="loanType"></label>-->
  	    				    <input style="width:140px" id="loanTypeApprove" name="loanType"></input>
  	    				</td>
      				</tr>
      				<tr>
      				     <td>
      				         <label>产品类型 </label>
                            <!-- <label style="width:140px" id="productType" name="productType" /></label>-->
                            <input style="width:140px" id="productType" name="productType"></input>
      				     </td>
      				</tr>
              <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMatters" rows="5" cols="100"  maxlength="200" /></textarea></td></tr>
      				<tr>
                        
  	    				<td><label style="margin-left:0px">备注</label><textarea id="agreeReason" rows="10" cols="100" maxlength="290"/></textarea></td>
  	    				<td >
  	    					<a href="javascript:void(0)" id="agreeSubmitBt" class="easyui-linkbutton" >提交</a>
  	    				</td>
  	    				<td id='spe1'>
  	    					<a href="javascript:void(0)" id="agreeCancelBt" class="easyui-linkbutton" >取消</a>
  	    				</td>
      				</tr>
    		</table>
    		<table id="refuseTb" style="font-size:12px; width:100%;text-align:left; display:none;"> 
					<tr>
	    				<td>
							<label style="margin-right:50px">拒绝原因</label>&nbsp;&nbsp;&nbsp;&nbsp;
		    				<label>一级原因</label>
		    				<input id="refuseFirstReason" class="easyui-combobox" editable="false" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
		    			    <label>二级原因</label><input id="refuseSecondReason" class="easyui-combobox" editable="false" data-options="width:200" />
		    				
	    				</td>
    				</tr>
    				<tr>
	    				<td><textarea id="refuseRemarkTA" rows="10" cols="100" maxlength="390"/></textarea></td>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>
	    				</td>
	    				<td id='spe2'>
	    					<a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			<table id="returnTable" style="font-size:12px; width:100%;text-align:left; display:none;"> 
    			<tr><td ><label>退回原因</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
					<tr>
	    				<td >							
	    				<textarea id="returnContractMatters" rows="10" cols="100" required=true maxlength="800"></textarea>
	    				</td>
    				</tr>
    				<tr><td><label >备注</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>    				
    				<tr>
	    				<td >	    				 				
	    				<textarea id="returnReason" rows="10" cols="100" required=true maxlength="290"></textarea></td>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt2" class="easyui-linkbutton" >提交</a>
	    				</td>
	    				<td id='spe3'>
	    					<a href="javascript:void(0)" id="refuseCancelBt2" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    		</table>
        <table id="conditionalAgreeTb" style="font-size:12px; width:100%; text-align:left;display: none;" cellspacing="5">
          <tr>
            <td>
              <label style=''>申请金额&nbsp;&nbsp;&nbsp;&nbsp;</label><input style='' id="conditionalAgreeMoneyInput" type="text" required=true digits=true />
              <span style=''>元</span>
              <label style="">期       限</label>
              <select  id="conditionalAgreeTimeComb" class="easyui-combobox">
                 <!--  <option value="6">6期</option>
                  <option value="9">9期</option>
                  <option value="12">12期</option>
                  <option value="18">18期</option>
                  <option value="24">24期</option> -->
              </select>
             <!--  <label>管理费率</label>
                <select id="hasHouseInput" class="easyui-combobox">
                  <option value="0">无房</option>
                  <option value="1">有房</option>                 
              </select> -->
      		</td>
          </tr>
 
          <tr>
            <td>
              <label style="">需补充证件</label>
              
              <input style="" id="certificates1Input" type="text"  maxlength="100" required=true />
              <input style="" id="certificates2Input" type="text" maxlength="100" />
              <input style="" id="certificates3Input" type="text" maxlength="100" />
              <a style="" id="addNeedCardInputBt" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a> 
            </td>
          </tr>
           <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMattersInput" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>         
          <tr>
            <td><label style="">备注</label><textarea id="conditionalAgreeRemarkTA" rows="5" cols="100"  maxlength="1000" /></textarea></td>
            <td >
              <a style="" href="javascript:void(0)" id="conditionalAgreeSubmitBt" class="easyui-linkbutton" >提交</a>
            </td>
            <td id="">
              <a style="" href="javascript:void(0)" id="conditionalAgreeCancelBt" class="easyui-linkbutton" >取消</a>
            </td>
          </tr>
              
        </table>
			</div>
		</form>
		</div>
		<div id="carContacterBrowseTab2" title="联系人信息" style="padding:20px">
                <div id="carContacterBrowsePanelTemplate2"
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
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>			
		</div>
	</div>
  </div> 
  
  
  <!-- 车贷展期签批对话框  -->
	<div id="approveExtensionCLDlg" class="easyui-dialog" style="width: 980px;height:320px;overflow-y:scroll" closed="true" data-options="resizable:true" >
		 <div id="browseTabs" class="easyui-tabs">
		   <div id="" title="借款信息" style="padding:20px">
		<form id="approveExtensionCLForm" method="post" style="padding: 20px;">
        	<table style="font-size:12px; width:100%; text-align:left;">
        		<tr>
					<td><label>借款类型</label></td><td align="left"><label id="productName" ></label></td>
					<td><label>贷款类型</label></td><td align="left"><label id="loanType" ></label></td>
					<td><label>申请金额</label></td><td align="left"><label id="requestMoney" ></label></td>
					<td><label>申请期限</label></td><td align="left"><label id="requestTime" ></label></td>
					<!--<td><label>借款用途</label></td><td align="left"><label id="purpose" ></label></td>
					-->
				</tr>
        	</table>
        	<div id="personApproveExtensionCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>姓       名</label></td><td align="left"><label id="personName" ></label></td>
	    				<td><label>性       别</label></td><td align="left"><label id="personSex" ></label></td>
	    				<td><label>身份证号</label></td><td align="left"><label id="personIdnum" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>婚姻状况</label></td><td align="left"><label id="personMarried" ></label></td>
	    				<!--<td><label>最高学历</label></td><td align="left"><label id="personEducationLevel" ></label></td>
	    				-->
	    				<td><label>是否有子女</label></td><td align="left"><label id="personHasChildren" ></label></td>
	    				<td><label>子女在读学校</label></td><td align="left"><label id="childrenSchool"></label></td>
    				</tr>
    				<tr>
    					<td><label>手机号码</label></td><td align="left"><label id="personMobilePhone" ></label></td>
	    				<td><label>常用邮箱</label></td><td align="left"><label id="personEmail" ></label></td>
	    				<td><label>住宅电话</label></td><td align="left"><label id="personHomePhone" ></label></td>
    				</tr>
    				<tr>
    					<td><label>户籍地址</label></td><td colspan="3" align="left" style="width:150px"><label id="personPlaceDomicile" ></label></td>
    					<td><label>邮政编码</label></td><td align="left" ><label id="personHouseholdZipCode" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>现居住地址</label></td><td colspan="3" align="left" style="width:150px"><label id="personAddress" ></label></td>
	    				<td><label>邮政编码</label></td><td align="left"><label id="personZipCode" ></label></td>
    				</tr>
    				<tr>
    					<td><label>居住类型</label></td><td align="left"><label id="personLiveType" ></label></td>
    					<td><label>每月租金</label></td><td align="left"><label id="personRentPerMonth" ></label></td>
    					<td><label>可接受的最高月还款额</label></td><td align="left"><label id="maxRepayAmount"></label></td>
    				</tr>
				</table>
			</div>
			<div id="personApproveExtensionCLPanel" class="easyui-panel browsePanel" title="车辆信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>车辆品牌</label></td><td align="left"><label id="vehicleBrand" ></label></td>
	    				<td><label>车       型</label></td><td align="left"><label id="vehicleModel" ></label></td>
	    				<td><label>车       龄</label></td><td align="left"><label id="vehicleCoty" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>行驶里程</label></td><td align="left"><label id="vehicleMileage" ></label></td>
	    				<td><label>车  牌  号</label></td><td align="left"><label id="vehiclePlateNumber" ></label></td>
	    				<td><label>车  架  号</label></td><td align="left"><label id="vehicleFrameNumber" ></label></td>
    				</tr>
				</table>
			</div>
			<div id="personApproveExtensionCLPanel" class="easyui-panel browsePanel" title="职业信息" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>单位全称</label></td><td align="left"><label id="companyName" ></label></td>
	    				<td><label>单位地址</label></td><td colspan="5" align="left" style="width:150px"><label id="companyAddress" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>单位性质</label></td><td align="left"><label id="personCompanyType" ></label></td>
	    				<td><label>部       门</label></td><td align="left"><label id="personDeptName" ></label></td>
	    				<td><label>职       务</label></td><td align="left"><label id="personJob" ></label></td>
	    				<td><label>固话分机</label></td><td align="left"><label id="personExt" ></label></td>
    				</tr>
    				<!--<tr>
	    				<td><label>职业类型</label></td><td align="left"><label id="professionType"></label></td>                            
	    				<td><label>月基本薪金</label></td><td align="left"><label id="personIncomePerPonth" ></label></td>
	    				<td><label>月发薪日</label></td><td align="left"><label id="personPayDate" ></label></td>
	    				<td><label>其他收入</label></td><td align="left"><label id="personOtherIncome" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>工作证明人</label></td><td align="left"><label id="personWitness" ></label></td>
	    				<td><label>部       门</label></td><td align="left"><label id="personWorkThatDept" ></label></td>
	    				<td><label>职       务</label></td><td align="left"><label id="personWorkThatPosition" ></label></td>
	    				<td><label>联系电话</label></td><td align="left"><label id="personWorkThatTell" ></label></td>
    				</tr>
    				<tr class="enterpprise3" style="dispaly:none">
                            <td><label>私营企业类型</label></td>
                            <td align="left"><label id="privateEnterpriseType"></label></td>
                            <td><label>成立时间</label></td>
                            <td align="left"><label id="foundedDate"></label></td>
                            <td><label>经营场所</label></td> 
                            <td align="left"><label id="businessPlace"></label></td>                           
                        </tr>
                        <tr class="enterpprise4" style="dispaly:none">
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
			<div id="personApproveExtensionCLPanel" class="easyui-panel browsePanel" title="信贷历史" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
	    				<td><label>信用卡</label></td><td align="left"><label id="creditHistoryHasCreditCard" ></label></td>
	    				<td><label>总张数</label></td><td align="left"><label id="creditHistoryCardNum" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>总  额  度</label></td><td align="left"><label id="creditHistoryTotalAmount" >TODO</label></td>
	    				<td><label>已  透  支</label></td><td align="left"><label id="creditHistoryOverdrawAmount" ></label></td>
    				</tr>
				</table>
			</div>
			<div id="otherApproveExtensionCLPanel" class="easyui-panel browsePanel" title="其他" style="width:910px;">
				<table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
					<tr>
						<td><label>客服姓名</label></td><td align="left"><label id="serviceName" ></label></td>
	    				<!--<td><label>客户来源</label></td><td align="left"><label id="customerSource" ></label></td>
	    				-->
	    				<td><label>申请日期</label></td><td align="left"><label id="requestDate" ></label></td>
    				</tr>
    				<tr>
	    				<td><label>客户经理工号</label></td><td align="left"><label id="crmCode" ></label></td>
	    				<td><label>客户经理</label></td><td align="left"><label id="crmName" ></label></td>
	    				<td><label>营业网点</label></td><td align="left"><label id="salesDeptName" ></label></td>
    				</tr>
                    <tr>
                       <td><label>备注</label></td>
                       <td align="left" colspan="5" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                    </tr>
    			</table>
			</div>
			<div id="approveExtensionCLPanel" class="easyui-panel browsePanel" title="签批结果" style="width:910px;">
				<input type="hidden" id="loanId" />
			 	<input type="radio" name="agreementRD" value="10" checked><span>同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!-- <input type="radio" name="agreementRD" value="20"><span>有条件同意</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
			 	<input type="radio" name="agreementRD" value="30"><span>拒绝</span></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="agreementRD" value="31"><span>退回门店</span></input>
				<table id="agreeTb" style="font-size:12px; width:100%; text-align:left;">
					<tr>
						<td >
							<label style='float:left;margin-left:10px'>申请金额</label><input style='float:left;margin-left:10px' id="agreeMoneyInput" type="text" required=true digits=true min=1 max=1000000/>
	    					<span style='float:left;margin-left:10px'>元</span>
	    					<label style='margin-right:30px'>期       限</label>
	    					<select style='float:left;margin-left:30px' id="agreeTimeComb" class="easyui-combobox">
							    <option value="3">3期</option>
							</select>
	    				</td>
    				</tr>
            <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMatters" rows="5" cols="100"  maxlength="200" /></textarea></td></tr>
    				<tr>
                      
	    				<td> <label style="margin-left:0px">备注</label><textarea id="agreeReason" rows="10" cols="100" maxlength="290"/></textarea></td>
	    				<td >
	    					<a href="javascript:void(0)" id="agreeSubmitBt" class="easyui-linkbutton" >提交</a>
	    				</td>
	    				<td id='spe1'>
	    					<a href="javascript:void(0)" id="agreeCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			<table id="refuseTb" style="font-size:12px; width:100%;text-align:left; display:none;"> 
					<tr>
	    				<td>
							<label style="margin-right:50px">拒绝原因</label>&nbsp;&nbsp;&nbsp;&nbsp;
		    				<label>一级原因</label>
		    				<input id="refuseFirstReason" class="easyui-combobox" editable="false" data-options="width:150" />&nbsp;&nbsp;&nbsp;&nbsp;
		    			    <label>二级原因</label><input id="refuseSecondReason" class="easyui-combobox" editable="false" data-options="width:200" />
		    				
	    				</td>
    				</tr>
    				<tr>
	    				<td><textarea id="refuseRemarkTA" rows="10" cols="100" maxlength="390"/></textarea></td>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt" class="easyui-linkbutton" >提交</a>
	    				</td>
	    				<td id='spe2'>
	    					<a href="javascript:void(0)" id="refuseCancelBt" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
    			<table id="returnTable" style="font-size:12px; width:100%;text-align:left; display:none;"> 
    			<tr><td ><label>退回原因</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
					<tr>
	    				<td >							
	    				<textarea id="returnContractMatters" rows="10" cols="100" required=true maxlength="800"></textarea>
	    				</td>
    				</tr>
    				<tr><td><label >备注</label>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>    				
    				<tr>
	    				<td >	    				 				
	    				<textarea id="returnReason" rows="10" cols="100" required=true maxlength="290"></textarea></td>
	    				<td>
	    					<a href="javascript:void(0)" id="refuseSubmitBt2" class="easyui-linkbutton" >提交</a>
	    				</td>
	    				<td id='spe3'>
	    					<a href="javascript:void(0)" id="refuseCancelBt2" class="easyui-linkbutton" >取消</a>
	    				</td>
    				</tr>
    			</table>
      <!--     <table id="conditionalAgreeTb" style="font-size:12px; width:100%; text-align:left;display: none;" cellspacing="5">
            <tr>
              <td>
                <label style=''>申请金额&nbsp;&nbsp;&nbsp;&nbsp;</label><input style='' id="conditionalAgreeMoneyInput" type="text" required=true digits=true min=1 max=1000000/>
                <span style=''>元</span>
                <label style="">期       限</label>
                <select  id="conditionalAgreeTimeComb" class="easyui-combobox">
                  
                </select>
              
              </td>
            </tr>
   
            <tr>
              <td>
                <label style="">需补充证件</label>
                
                <input style="" id="certificates1Input" type="text"  maxlength="100" required=true />
                <input style="" id="certificates2Input" type="text" maxlength="100" />
                <input style="" id="certificates3Input" type="text" maxlength="100" />
                <a style="" id="addNeedCardInputBt" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a> 
              </td>
            </tr>
             <tr><td><label style="margin-left:0px">签约事项</label><textarea id="contractMattersInput" rows="5" cols="100"  maxlength="1000" /></textarea></td></tr>         
            <tr>
              <td><label style="">备注</label><textarea id="conditionalAgreeRemarkTA" rows="5" cols="100"  maxlength="1000" /></textarea></td>
              <td >
                <a style="" href="javascript:void(0)" id="conditionalAgreeSubmitBt" class="easyui-linkbutton" >提交</a>
              </td>
              <td id="">
                <a style="" href="javascript:void(0)" id="conditionalAgreeCancelBt" class="easyui-linkbutton" >取消</a>
              </td>
            </tr>
              
          </table> -->
			</div>
		</form>
		</div>
		<div id="carContacterBrowseTab3" title="联系人信息" style="padding:20px">
                <div id="carContacterBrowsePanelTemplate3"
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
                            <td><label>工作单位</label></td>
                            <td><label id="contacterWorkUnit"></label></td>
                            <td><label>知晓贷款</label></td>
                            <td><label id="contacterHadKnown"></label></td>
                        </tr>
                    </table>
                </div>
            </div>			
		</div>
	</div>
  </div> 
  <div id="carLoanDetail" style="top: 20px;height:600px;width:1000px;"></div>
  <!--借款日志对话框-->
	<div id="businessLogDlg" buttons="#businessLogDlg-buttons">
		<table id="business_log_result"></table>
	</div>     
		<!--台账对话框-->
	<div id="showLoanLedgerDlg" style="top: 20px;height:600px;width:1000px;">
		<table id="loanLedgerList"></table>
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
                $('#spe1').css("background","#ffffff");
                $('#spe2').css("background","#ffffff");
                $('#spe3').css("background","#ffffff");
                $('#xqd1').css("background","#ffffff");
                $('#xqd2').css("background","#ffffff");
                $('#xqd3').css("background","#ffffff");
                $('#xqd4').css("background","#ffffff");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0"); 
                          
            })
</script> 
</body>
</html>