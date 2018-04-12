<#include "../../macros/constant_output_macro.ftl">
<#assign title="产品管理"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/product/productList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>产品编号：</span><input type="text" id="productCode" maxlength="30"  style="width: 150px" /> 
	     <span>产品名称：</span><input type="text" id="productName" maxlength="30"  style="width: 150px" /> 
	     <!--<span>创建日期：</span><input  id="createdTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})""></input>  - 
      			  				<input  id="createdTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></br>
	     <span>修改日期：</span><input  id="modifiedTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>  - 
      			  				<input  id="modifiedTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
      			  				-->
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertProductBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增产品</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增产品信息面板 -->
		<div id="addProductPanel" class="easyui-window" title="新增产品"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1000px;">
        <form id="addProductForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span>产品编号：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="产品编号必须填写" style="width:180px;height:18px;" type="text" name="productCode" id = "productCode"></input>
                     	<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>产品名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="产品名称必须填写" style="width:180px;height:18px;" type="text" name="productName" id = "productName"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>咨询费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="consultingFeeRate" id = "consultingFeeRate"></input>
                     </td>
                    <td >
                    	<span>丙方管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="managePartRate" id = "managePartRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="manageFeeRate" id = "manageFeeRate"></input>
                     </td>
                    <td >
                    	<span>逾期罚息费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="overdueInterestRate" id = "overdueInterestRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金比例：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="riskRate" id = "riskRate"></input>
                     </td>
                    <td >
                    	<span>评估费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="assessmentFeeRate" id = "assessmentFeeRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>平息利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="rate" id = "rate"></input>
                     </td>
                    <td >
                    	<span>状态：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="状态不能为空" style="width:180px;height:18px;" type="text" name="status" id = "status"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                 
                <tr>
                    <td >
                    	<span>版本：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="version" id = "version"></input>
                     </td>
                    <td >
                    	<span>产品类型：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="产品类型不能为空" style="width:180px;height:18px;" type="text" name="productType" id = "productType"></input>
                   		<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品类型名称：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="产品类型名称必须填写" style="width:180px;height:18px;" type="text" name="productTypeName" id = "productTypeName"></input>
                     </td>
                    <td >
                    	<span>同城费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="thirdFeeRate" id = "thirdFeeRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="monthRate" id = "monthRate"></input>
                     </td>
                    <td >
                    	<span>提前还款违约金费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="penaltyRate" id = "penaltyRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品渠道ID：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelId" id = "productChannelId"></input>
                     </td>
                    <td >
                    	<span>产品渠道名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelName" id = "productChannelName"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<span>年利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="yearRate" id = "yearRate"></input>
                     </td>
                </tr>
                <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea class="easyui-validatebox"  id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAddproduct()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增产品信息面板 -->
    <!-- 修改产品信息面板 -->
		<div id="updateProductPanel" class="easyui-window" title=" 修改产品"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1000px;">
        <form id="updateProductForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
            	<input name = "id" type = "hidden" id = "id" />
                    <td width="500px">
                    	<span>产品编号：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="产品编号必须填写" style="width:180px;height:18px;" type="text" name="productCode" id = "productCode"></input>
                     	<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>产品名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="产品名称必须填写" style="width:180px;height:18px;" type="text" name="productName" id = "productName"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>咨询费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="consultingFeeRate" id = "consultingFeeRate"></input>
                     </td>
                    <td >
                    	<span>丙方管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="managePartRate" id = "managePartRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="manageFeeRate" id = "manageFeeRate"></input>
                     </td>
                    <td >
                    	<span>逾期罚息费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="overdueInterestRate" id = "overdueInterestRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金比例：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="riskRate" id = "riskRate"></input>
                     </td>
                    <td >
                    	<span>评估费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="assessmentFeeRate" id = "assessmentFeeRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>平息利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="rate" id = "rate"></input>
                     </td>
                    <td >
                    	<span>状态：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="状态不能为空"  style="width:180px;height:18px;" type="text" name="status" id = "status"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                 
                <tr>
                    <td >
                    	<span>版本：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="version" id = "version"></input>
                     </td>
                    <td >
                    	<span>产品类型：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="产品类型不能为空"  style="width:180px;height:18px;" type="text" name="productType" id = "productType"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品类型名称：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="产品类型名称必须填写"  style="width:180px;height:18px;" type="text" name="productTypeName" id = "productTypeName"></input>
                     </td>
                    <td >
                    	<span>同城费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="thirdFeeRate" id = "thirdFeeRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="monthRate" id = "monthRate"></input>
                     </td>
                    <td >
                    	<span>提前还款违约金费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="penaltyRate" id = "penaltyRate"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品渠道ID：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelId" id = "productChannelId"></input>
                     </td>
                    <td >
                    	<span>产品渠道名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelName" id = "productChannelName"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<span>年利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="yearRate" id = "yearRate"></input>
                     </td>
                </tr>
                <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea class="easyui-validatebox"  id="remark" name="remark" rows="3" cols="100" maxlength="300"></textarea>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
           <a class="easyui-linkbutton" onclick="submitUpdateproductManager()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  修改产品信息面板 -->
    <!-- 产品详情信息面板 -->
		<div id="detailProductPanel" class="easyui-window" title=" 产品详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1000px;">
        <form id="detailProductForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                    <td width="500px">
                    	<span>产品编号：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productCode" id = "productCode" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>产品名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productName" id = "productName" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>咨询费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="consultingFeeRate" id = "consultingFeeRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>丙方管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="managePartRate" id = "managePartRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>管理费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="manageFeeRate" id = "manageFeeRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>逾期罚息费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="overdueInterestRate" id = "overdueInterestRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金比例：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="riskRate" id = "riskRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>评估费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="assessmentFeeRate" id = "assessmentFeeRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>平息利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="rate" id = "rate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>状态：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="状态不能为空" style="width:180px;height:18px;" type="text" name="status" id = "status" readonly="readonly"></input>
                    </td>
                </tr>
                
                <tr>
                    <td >
                    	<span>版本：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="version" id = "version" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>产品类型：</span>
                    	<input class="easyui-validatebox"  required="true" missingMessage="产品类型不能为空" style="width:180px;height:18px;" type="text" name="productType" id = "productType" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品类型名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productTypeName" id = "productTypeName" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>同城费费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="thirdFeeRate" id = "thirdFeeRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="monthRate" id = "monthRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>提前还款违约金费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="penaltyRate" id = "penaltyRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>产品渠道ID：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelId" id = "productChannelId"  readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>产品渠道名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productChannelName" id = "productChannelName"  readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<span>年利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="yearRate" id = "yearRate" readonly="readonly"></input>
                     </td>
                </tr>
                 <tr>
						<td colspan="2">
							<span>备注：</span>
							<textarea class="easyui-validatebox"  id="remark" name="remark" rows="3" cols="100" maxlength="300" readonly="readonly"></textarea>
						</td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  产品详情信息面板 -->
	
	<script>
           $(function(){
                $('table tr td:nth-child(odd)').css(
                {
                "background":"#f1f5f9",
                "padding-right":"10px"
                }
                );
               $('.datagrid-htable tr td:nth-child(odd)').css("padding-right","0");
            })
</script>