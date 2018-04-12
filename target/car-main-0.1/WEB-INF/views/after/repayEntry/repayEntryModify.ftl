<html>
<head>
</head>
<body>
   <!-- 查看还款录入  -->
 <div>
 
<script type="text/javascript" charset="UTF-8" src="resources/js/after/repayEntry/repayEntryModify.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/repayEntry/repayEntry.js"></script>

 <link rel="stylesheet" type="text/css" href="resources/css/apply/seLoan.css" />
    
     <form id="browseForm" method="post">
     <input type="hidden" id ="loanId" name="loanId" />
 	 <div id="personBrowsePanel" class="easyui-panel browsePanel" title="还款详情" style="width:1000px;">
            <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                <tr>
                    <td><label>姓 名</label></td>
                    <td align="left"><label id="personName"></label></td>
                    <td><label>身份证号</label></td>
                    <td align="left"><label id="personIdnum"></label></td>
                    <td><label>手机</label></td>
                    <td align="left"><label id="personMobilePhone"></label></td>
                </tr>
                <tr>
                    <td><label>逾期起始日</label></td>
                    <td align="left"><label id="overdueStartDate"></label></td>
                    <td><label>逾期总数</label></td>
                    <td align="left"><label id="overdueTerm"></label></td>
                    <td><label>逾期应还总额</label></td>
                    <td align="left"><label id="overdueAmount"></label></td>
                </tr>
                <tr>
                    <td><label>罚息起算日</label></td>
                    <td align="left"><label id="fineDate"></label></td>
                    <td><label>罚息天数</label></td>
                    <td align="left"><label id="fineDay"></label></td>
                    <td><label>罚息金额</label></td>
                    <td align="left"><label id="fine"></label></td>
                </tr>
                <tr>
                    <td><label>当期还款日</label></td>
                    <td align="left"><label id="curRepayDate"></label></td>
                    <td><label>当前期数</label></td>
                    <td align="left"><label id="currTerm"></label></td>
                    <td><label><label id="currAmountLabel"></label></td>
                    <td align="left"><label id="currAmount"></label></td>
                </tr>
                  <tr>
                    <td><label>期末预收</label></td>
                    <td align="left"><label id="accAmount"></label></td>
                    <td><label>减免金额</label></td>
                    <td align="left"><label id="reliefOfFine"></label></td>
                 
                </tr>
               <tr>
                    <td align="left"><label>应还总额（不含当期）</label></td>
                    <td><label id="repayAmount"></label></td>
                    <td align="left"><label>应还总额（包含当期）</label></td>
                    <td><label id="repayAllAmount"></label></td>
                 
                </tr>
             </table>
        </div>
        <div id="otherBrowsePanel" class="easyui-panel browsePanel" title="还款录入" style="width:1000px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>还款日期</label></td>
                            <td align="left"><label id="nowDate"></label></td>
                            <td><label>还款金额</label></td>
                            <td align="left"><input id="tradeAmount" class="easyui-validatebox" validType="moneyCheck" name="tradeAmount" maxlength="10" required="true" precision="2"  style="width: 90px" /></td>
                            <td><label>还款途径</label></td>
                            <td align="left"><select  id="payType" name="payType"  required="true"  editable="false"  class="easyui-combobox">                          
	                            <option value="1">现金</option>
	                            <option value="2">转账</option>	
	                        	</select></td>
                        </tr>
                        <tr>
	                        <td><label>备注</label></td>
	                        <td align="left" colspan="5" style="width:83%; word-break:break-all">
	                        	<textarea id="remark" name="remark" rows="5" cols="100"  maxlength="1000" /></textarea>
	                        </td>
                        </tr>
                    </table>
                </div>
                 <div id="dlg-buttons">
            <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="submitEditBtn"  >保存</a>
        </div>
      </form>
</div>

<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");

                $('#submitEditBtn').bind('click',submitEdit);
            })
</script>		
</body>
</html>