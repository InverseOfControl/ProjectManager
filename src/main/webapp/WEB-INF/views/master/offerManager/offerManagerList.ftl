<#include "../../macros/constant_output_macro.ftl">
<#assign title="报盘管理"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/offerManager/offerManager.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
	     	<span>报盘日期：</span><input id="offerDay" style="width: 180px" type="text" class='easyui-numberbox' min="1" max="31"/>
      	</div>
      	<div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         	<a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
         	<a id="insertBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增</a>
	  	</div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
    <!-- 报盘详情面板 -->
		<div id="detailOfferPanel" class="easyui-window" title=" 报盘详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="detailOfferForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                    <td width="500px">
                    	<span>报盘日期：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "day" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>前几天：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "beforeDay" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>后几天：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "afterDay" readonly="readonly"></input>
                     </td>
                    <td >
                    	<span>生成报盘时间：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "generateTime" readonly="readonly"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>发送报盘时间：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "sendTime" readonly="readonly"></input>
                     </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<input class="easyui-validatebox"  style="width:180px;height:18px;" type="text" id = "remark" readonly="readonly"></input>
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
    <!--  报盘详情面板 -->
    
    
    <!-- 新增面板 -->
		<div id="addOfferPanel" class="easyui-window" title="新增报盘"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="addOfferForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<tr>
                    <td width="500px">
                    	<span>报盘日期：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="报盘日期必须填写" min="1" max="31" style="width:180px;height:18px;" type="text" name="day" id = "day"></input>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>前几天：</span>
                    	<input  class="easyui-numberbox" min="0" max="31" style="width:180px;height:18px;" type="text" name="beforeDay" id = "beforeDay"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>后几天：</span>
                    	<input  class="easyui-numberbox" min="0" max="31" style="width:180px;height:18px;" type="text" name="afterDay" id = "afterDay"></input>
                     </td>
                     <td >
                    	<span>生成报盘时间：</span>
                    	<input  id="generateTime" name='generateTime' style="width: 180px" class="Wdate easyui-validatebox"  
                    		type="text"  onclick="WdatePicker({dateFmt:'HH:mm'})" missingMessage="请选择生成报盘时间" readonly="readonly" data-options="required:true"/>
                    	<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>发送报盘时间：</span>
                    	<input  id="sendTime" name='sendTime' style="width: 180px" class="Wdate easyui-validatebox"  
                    		type="text"  onclick="WdatePicker({dateFmt:'HH:mm'})" missingMessage="请选择发送报盘时间" readonly="readonly" required="true"/>
                    	<font color='red'>*</font>
                     </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<textarea class="easyui-validatebox"  style="width:380px;height:30px;" type="text" name="remark" id = "remark"></textarea>
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
        	<a class="easyui-linkbutton" onclick="submitAddOffer()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 新增面板 -->
	
	
	
	
	<!-- 修改面板 -->
		<div id="updateOfferPanel" class="easyui-window" title="修改产品详情"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1190px;">
        <form id="updateOfferForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
            	<input class="easyui-validatebox"  type="hidden" id='id' name="id" ></input>
            	<input class="easyui-validatebox"  type="hidden" id='status' name="status" ></input>
            	<tr>
                    <td width="500px">
                    	<span>报盘日期：</span>
                    	<input class="easyui-numberbox" required="true" missingMessage="报盘日期必须填写" min="1" max="31" style="width:180px;height:18px;" type="text" name="day" id = "day"></input>
                    	<font color='red'>*</font>
                     </td>
                    <td >
                    	<span>前几天：</span>
                    	<input class="easyui-numberbox validatebox-text" min="0" max="31" style="width:180px;height:18px;" type="text" id='beforeDay' name="beforeDay"></input>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>后几天：</span>
                    	<input class="easyui-numberbox" min="0" max="31" style="width:180px;height:18px;" type="text" id='afterDay' name="afterDay"></input>
                     </td>
                     <td >
                    	<span>生成报盘时间：</span>
                    	<input  id="generateTime" name='generateTime' style="width: 180px" class="Wdate easyui-validatebox"  
                    		type="text"  onclick="WdatePicker({dateFmt:'HH:mm'})" missingMessage="请选择生成报盘时间" readonly="readonly" data-options="required:true"/>
                    	<font color='red'>*</font>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>发送报盘时间：</span>
                		<input  id="sendTime" name='sendTime' style="width: 180px" class="Wdate easyui-validatebox"  
                    		type="text"  onclick="WdatePicker({dateFmt:'HH:mm'})" missingMessage="请选择发送报盘时间" readonly="readonly" required="true"/>
                    	<font color='red'>*</font>
                     </td>
                </tr>
                 <tr>
                    <td colspan="2">
                    	<span>备注：</span>
                    	<textarea class="easyui-validatebox"  style="width:380px;height:30px;" type="text" id='remark' name="remark"></textarea>
                    </td>
                </tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
        	<a class="easyui-linkbutton" onclick="submitUpdateOffer()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 修改面板 -->	
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