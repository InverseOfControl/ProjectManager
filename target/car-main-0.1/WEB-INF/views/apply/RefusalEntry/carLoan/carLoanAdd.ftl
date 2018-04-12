<html>
<head>
</head>
<body>
 <div>                
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/refusalEntry/carLoanApply/carLoanApply.js"></script>
 <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
    <form id="addCarLoanForm" method="post" style="padding: 5px;">
    <table cellpadding="5" id="addCarTableNew1" class="chedai"  cellspacing="10" style="text-align:left">
    <tr>
        <td>
            <input type="hidden" id="personId" name="personId"/>
            <span class="pre_span"><font color="red">*</font></span>
        	<label> 姓　　名</label> 
            <input id="carPersonName" name="carPersonName" class="easyui-validatebox" maxlength="25" required="true" />
        </td>
        <td  colspan="2" >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 身份证号&nbsp;&nbsp;</label> 
        	<label name="carPersonIdnumName" id="carPersonIdnumName" style="margin-left: 10px;color: blue"></label>
        		<input type="hidden" id="carPersonIdnum" name="carPersonIdnum" />
         	  <input type="hidden" id="loanId" name="loanId"/>
            <!-- 产品信息ID -->
            <input type="hidden" id="productId" name="productId"/>
             <!-- 借款类型ID -->
            <input type="hidden" id="productTypeId" name="productTypeId"/>
            <input type="hidden" id="productName" name="productName"/>
        </td>
           <td colspan="2">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label> 拒单日期</label>
        	<input id="carRequestDate"  name="carRequestDate" style="width: 180px"  editable="false" value='${carRequestDate}'  class="easyui-datebox"/>           
        </td>
    </tr>  
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  客服姓名</label>
            <input type="hidden" id="serviceId" name="serviceId"/>
            <label id="customerName" name="customerName" style="margin-left: 10px;color: blue"/>
        </td>
          <td  colspan="2" >
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  客户经理</label>
            <input type="hidden" id="crmId" name="crmId"/>
            <input id="managerName" name="managerName" class="easyui-combobox" style="width:140px"/>
        </td>
        <td colspan="2">
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  业务主任</label>
             <input type="hidden" id="bizDirectorId" name="bizDirectorId"/>
            <input id="bizName" name="bizName" class="easyui-combobox"  style="width:140px" />
        </td>
     
    </tr>
    <tr>
        <td>
        	<span class="pre_span"><font color="red">*</font></span>
        	<label>  营业网点</label>
        	<input id="salesDeptId" name="salesDeptId" class="easyui-combobox"  editable="false" style="width:140px"/>
        </td>
       
    </tr>
    <tr>
         <td colspan="3">
         <label> 备注</label></br>
         <textarea id="remark" name="remark" rows="5" cols="120"  maxlength="1000" /></textarea>
         </td>
    </tr>
    </table>
    </form>
    <div id="addCarlg-buttons" style="text-align:center;padding-right:100px"  > 
        <a class="easyui-linkbutton" iconCls="icon-ok" plain="true" id="addCarLoanBtnNew"      >保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="cancelCarLoanBtn"  >取消</a>
    </div>
    </div>
<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
                $('#addCarLoanBtnNew').bind('click',addCarLoan);
                $('#cancelCarLoanBtn').bind('click',cancelCarLoan);  
            })
</script>		
</body>
</html>