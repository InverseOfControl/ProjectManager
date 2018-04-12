<#include "../../macros/constant_output_macro.ftl">
<#assign title="产品详情管理"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/product/productDetailList.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>产品类型：</span><input id="productId" class="easyui-combobox"  editable="false"  data-options="width:120"/> 
	     <span>贷款产品类型：</span>
		     <select name='carProductType' id='carProductType' class="easyui-combobox">
	    		<option value=''>全部</option>
	    		<option value='1'>流通类</option>
	    		<option value='2'>移交类</option>
	    	</select>
	     <!--<span>创建日期：</span><input  id="createdTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})""></input>  - 
      			  				<input  id="createdTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></br>
	     <span>修改日期：</span><input  id="modifiedTimeStart" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>  - 
      			  				<input  id="modifiedTimeEnd" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
      			  				-->
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
         <a id="insertProductBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
    <!-- 产品详情信息面板 -->
		<div id="detailProductDetailPanel" class="easyui-window" title=" 产品详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="detailProductDetailForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                    <td width="500px">
                    	<span>产品名称：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="productName" id = "productName" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>贷款产品类型：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="carProductType" id = "carProductType" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>综合费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="sumRate" id = "sumRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>借款期限：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="term" id = "term" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>借款金额下限：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="lowerLimit" id = "lowerLimit" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>借款金额上限：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="upperLimit" id = "upperLimit" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>状态：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="status" id = "status" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>版本：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="version" id = "version" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>年利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="yearRate" id = "yearRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>会员类型：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="memberType" id = "memberType" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="riskRate" id = "riskRate" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="monthRate" id = "monthRate" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<span>第三方费率：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="thirdFeeRate" id = "thirdFeeRate" readonly="readonly"></input>
                     </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="remark" id = "remark" readonly="readonly"></input>
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
    
    
    <!-- 产品详情新增面板 -->
		<div id="addProductDetailPanel" class="easyui-window" title="新增产品详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addProductDetailForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                    <td width="500px">
                    	<span>产品名称：</span>
                    	<input id="addProductId" name='productId' class="easyui-combobox"  editable="false"  data-options="width:120"/>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>贷款产品类型：</span>
                    	<select name='carProductType' class="easyui-combobox" id='carProductType'>
				    		<option value='0'>全部</option>
				    		<option value='1'>流通类</option>
				    		<option value='2'>移交类</option>
				    	</select>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>综合费率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="sumRate"></input>
                     </td>
                    <td >
                    	<span>借款期限：</span>
                    	<input class="easyui-numberbox" min="3" max="24" required="true" missingMessage="借款期限必须填写"  style="width:180px;height:18px;" type="text" name="term"></input>
                   		<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>借款金额下限：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="借款金额下限必须填写" style="width:180px;height:18px;" type="text" name="lowerLimit"></input>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>借款金额上限：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="借款金额上限必须填写" style="width:180px;height:18px;" type="text" name="upperLimit"></input>
                    	<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>状态：</span>
                    	<select name='status' editable="false" style="width:108px" class="easyui-combobox">
		     				<option value="1" >可用</option>
		     				<option value="0" >不可用</option>   
			            </select>
			            <font color='red'>*</font>
                     </td>
                    <td>
                    	<span>第三方费率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="thirdFeeRate"></input>
                     </td>
                </tr>
                <tr>
                    <td >
                    	<span>年利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="yearRate" ></input>
                     </td>
                    <td >
                    	<span>会员类型：</span>
                		<select name='memberType'>
                    		<option value='0'>--请选择--</option>
                    		<option value='1'>免费会员</option>
                    		<option value='2'>付费会员（半年以下）</option>
                    		<option value='3'>付费会员（半年以上）</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="riskRate"></input>
                     </td>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="monthRate"></input>
                    </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="remark"></input>
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
        	<a class="easyui-linkbutton" onclick="submitAddproductDetail()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  产品详情新增面板 -->
	
	
	
	
	<!-- 产品详情修改面板 -->
		<div id="updateProductDetailPanel" class="easyui-window" title="修改产品详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateProductDetailForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            <input class="easyui-validatebox"  type="hidden" id='id' name="id" ></input>
            	<tr>
                    <td width="500px">
                    	<span>产品名称：</span>
                    	<input class="easyui-validatebox" value='' style="width:180px;height:18px;" type="text" id='updateProductId' name="productId" ></input>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>贷款产品类型：</span>
                    	<select name='carProductType' class="easyui-combobox" id='carProductType'>
				    		<option value='0'>全部</option>
				    		<option value='1'>流通类</option>
				    		<option value='2'>移交类</option>
				    	</select>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>综合费率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="sumRate"></input>
                     </td>
                    <td >
                    	<span>借款期限：</span>
                    	<input class="easyui-numberbox" min="3" max="24" required="true" missingMessage="借款期限必须填写"  style="width:180px;height:18px;" type="text" name="term"></input>
                    	<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>借款金额下限：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="借款金额下限必须填写" style="width:180px;height:18px;" type="text" name="lowerLimit"></input>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>借款金额上限：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="借款金额上限必须填写" style="width:180px;height:18px;" type="text" name="upperLimit" ></input>
                    	<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>状态：</span>
                    	<select name='status' editable="false" style="width:108px" class="easyui-combobox">
		     				<option value="1" >可用</option>
		     				<option value="0" >不可用</option>   
			            </select>
                     </td>
                    <td >
                    	<span>版本：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="version"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>年利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="yearRate"></input>
                     </td>
                    <td >
                    	<span>会员类型：</span>
                		<select name='memberType'>
                    		<option value='0'>--请选择--</option>
                    		<option value='1'>免费会员</option>
                    		<option value='2'>付费会员（半年以下）</option>
                    		<option value='3'>付费会员（半年以上）</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>风险金利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="riskRate"></input>
                     </td>
                    <td >
                    	<span>月利率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5'  style="width:180px;height:18px;" type="text" name="monthRate"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<span>第三方费率：</span>
                    	<input class="easyui-numberbox" min="0" max="1" precision='5' style="width:180px;height:18px;" type="text" name="thirdFeeRate"></input>
                     </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" name="remark"></input>
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
        	<a class="easyui-linkbutton" onclick="submitUpdateproduct()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!--  产品详情新增面板 -->	
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