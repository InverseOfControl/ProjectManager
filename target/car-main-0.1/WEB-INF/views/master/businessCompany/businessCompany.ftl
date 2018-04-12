<#include "../../macros/constant_output_macro.ftl">
<#assign title="本方账户配置"/>
<@htmlCommonHead/>
	<script type="text/javascript">
		var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
	</script>
	<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" charset="UTF-8" src="resources/js/master/businessCompany/businessCompany.js"></script>
	<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
</head>
<body>
	 <div id="toolbar" style="height:92px;padding-top:8px;">
	 	<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <span>账户名：</span><input type="text" id="name" maxlength="30"  style="width: 150px" /> 
      </div>
      <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
         <a id="searchBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		 <a id="insertBut" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openWindows()">新增本方账户</a>
	  </div>
	</div>
 	<table id="list_result" toolbar="#toolbar"></table>
     
  <!-- 新增本方账号面板 -->
		<div id="addPanel" class="easyui-window" title="新增本方账户"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:false,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1000px;">
        <form id="addForm"  method="post" enctype="multipart/form-data">
            <table cellspacing="20px">
                <tr>
                    <td width="500px">
                    	<span>账户名称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="账户名称必填" style="width:180px;height:18px;" type="text" name="name" id = "name"></input>
                     	<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>账户简称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="账户简称必填" style="width:180px;height:18px;" type="text" name="shortName" id = "shortName"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>本方账户：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="本方账户必填" style="width:180px;height:18px;" type="text" name="account" id = "account"></input>
                   		<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>TPP配置的业务代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="TPP配置的业务代码必填" style="width:180px;height:18px;" type="text" name="businessCode" id = "businessCode"></input>
                 		<span><font color="red">*</font></span>
                    </td>
                </tr>
            </table>
        </form>
        <br/>
        <div style="text-align:center;padding:5px">
            <a class="easyui-linkbutton" onclick="submitAdd()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
    <!-- 修改本方账号面板 -->
		<div id="updatePanel" class="easyui-window" title="修改本方账户"  align = "center" style="height:400px;width:1100px;" data-options="collapsible:true,minimizable:false,maximizable:false, resizable:true">
        <div style="padding:10px 10px 10px 10px;width:1000px;">
        <form id="updateForm"  method="post" enctype="multipart/form-data">
            <table  cellspacing="20px" >
                <tr>
                    <td width="500px">
                    	<span>账户名称：</span>
                    	<input  type="hidden" name="id" id = "id"></input>
                    	<input class="easyui-validatebox" required="true" missingMessage="账户名称必填" style="width:180px;height:18px;" type="text" name="name" id = "name"></input>
                     	<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>账户简称：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="账户简称必填" style="width:180px;height:18px;" type="text" name="shortName" id = "shortName"></input>
                    	<span><font color="red">*</font></span>
                    </td>
                </tr>
                <tr>
                    <td >
                    	<span>本方账户：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="本方账户必填" style="width:180px;height:18px;" type="text" name="account" id = "account"></input>
                   		<span><font color="red">*</font></span>
                     </td>
                    <td >
                    	<span>TPP配置的业务代码：</span>
                    	<input class="easyui-validatebox" required="true" missingMessage="TPP配置的业务代码必填" style="width:180px;height:18px;" type="text" name="businessCode" id = "businessCode"></input>
                 		<span><font color="red">*</font></span>
                    </td>
                </tr>
            </table>

        </form>
        <br/>
        <div style="text-align:center;padding:5px">
           <a class="easyui-linkbutton" onclick="submitUpdateManager()">提交</a>
            <a class="easyui-linkbutton commonCloseBut">关闭</a>
        </div>
        </div>
    </div>
	
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