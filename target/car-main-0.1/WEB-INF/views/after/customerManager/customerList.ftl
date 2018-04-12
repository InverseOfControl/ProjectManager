<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/customerManager/customerList.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/loanLedger.js"></script> 

<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>
<link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
</head>
<body>

<div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>姓名：</span><input id="personName" type = "text" maxlength="25"  style="width: 90px" />
			<span>身份证号：</span><input type="text" id="idnum" maxlength="18" />
			<span>合同编号：</span><input type="text" id="contractNo" maxlength="50" />
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	        <a class="easyui-linkbutton" id="searchBtn" data-options="iconCls:'icon-search'">查询</a>
		</div>
</div>
<table id="customerTb" toolbar="#toolbar"></table>

<!--台账对话框-->
<div id="showLoanLedgerDlg" style="top: 20px;height:600px;width:1000px;">
	<table id="loanLedgerList"></table>
</div>	

<!--提现操作对话框-->
<div id="showTiXianDlg" class="easyui-window" title="提现"  align = "center" style="top:130px;height:300px;width:1200px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:false">
	<div >
       <form id="tiXianForm"  method="post" enctype="multipart/form-data">
          <table  style="font-size:15px; width:100%; text-align:left;" cellspacing="25">
	  		  <tr>
		  		        <input name = "loanId" type = "hidden" id = "loanId" />
	                	<input name = "extenId" type = "hidden" id = "extenId" />
                        <td><label>客户姓名：</label><input class="easyui-validatebox" readonly="readonly" style="width:170px;height:18px;border:none" type="text" name="personName" id = 'personName'></input></td>
                        <td><label>证件号码：</label><input class="easyui-validatebox" readonly="readonly" style="width:190px;height:18px;border:none" type="text" name="idNum" id = 'idNum'></input></td>
                        <td><label>账户余额（元）：</label><input class="easyui-validatebox" readonly="readonly" style="width:150px;height:18px;border:none" type="text" name="cash" id = 'cash'></input></td>
               </tr>
               <tr>
                    <td>
                    	<!--<label><span><font color="red">*</font></span>提现金额：<input  class="easyui-numberbox" required="true" type="text" style="width:140px;height:18px;" maxlength="22" name="tiXianMoney" id = 'tiXianMoney'></input> -->
                   		<label><span><font color="red">*</font></span>
                   		提现金额：<input class="easyui-validatebox" validType="moneyCheck"  required="true" missingMessage="提现金额必须填写" style="width:140px;height:18px;" maxlength="22" name="tiXianMoney" id = 'tiXianMoney'></input>
                    </td>
                    <td><label>合同编号: </label><input class="easyui-validatebox" readonly="readonly" style="width:180px;height:18px;border:none" type="text" name="contractNo" id = 'contractNo'></td>
                    <td></td>
               </tr>
               <tr>
					<td colspan="2">
						<span><font color="red">*</font>备注：</span>
						<textarea id="remark" name="remark" rows="3" cols="100" maxlength="300" ></textarea>
					</td>
					<td></td>
					<td></td>
               </tr>
	      </table>
	  </form>
	  <br/>
      <div style="text-align:center;padding:5px">
          <a class="easyui-linkbutton" onclick="tiXianMethod()">提现</a>
          <a class="easyui-linkbutton  commonCloseBut">取消</a>
      </div>
   </div>
</div>	
<!--提现操作对话框-->

</body>