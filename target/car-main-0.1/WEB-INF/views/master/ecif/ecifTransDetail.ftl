<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>

<script type="text/javascript">var userType='${userType}';</script> 
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/master/ecif/ecifTrans.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<script type="text/javascript" charset="utf-8">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
 </script>


<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
	
</style>
</head>
<body>
	<table id="list_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">

	

   		<span>姓名：</span><input type="text" id="personName" maxlength="25"  style="width: 90px" />  
		<span>身份证号：</span><input type="text" id="personIdnum" maxlength="18" style="width: 200px" />  
        <span>发送日期：</span>
       <input id="createdTimeAfter" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' })"/>
       <span>到：</span>
       <input id="createdTimeBefore" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' })"/>
		
        
    	<span>状态：</span>
     	<select  id="status"  editable="false" style="width:108px" class="easyui-combobox">


        			<option value="">全部</option>
              <option value="AAAAAAAA">成功</option>
            <option value="NNNNNNNN" >无权访问</option>
            <option value="ZZZZZZZZ">正在执行批处理</option>   
              <option value="NNNNUSER">用户不存在</option>
              <option value="E0000000">ECIF内部错误</option>
              <option value="E0000001">发送日期过期</option>
              <option value="E0000002">请求字段校验失败</option>
              <option value="E0000003">请求流水号重复</option>
              <option value="E0000004">新增客户重复</option>
          </select>
   
        </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		</div>		
    	
	</div>

  <div id="dlg" class="easyui-dialog" style="top: 150px;height:150px;width: 550px;" closed="true";resizable="true">
      <div id="content">
        
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
            });
</script>
</body>
</html>