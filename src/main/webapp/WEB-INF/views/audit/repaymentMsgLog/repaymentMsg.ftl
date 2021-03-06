﻿<#include "/macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/audit/repaymentMsgLog/repaymentMsg.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 

<script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<script type="text/javascript" charset="utf-8">

 </script>
<style type="text/css">
.datagrid-toolbar {
	height: 56px;
}
		#refuseReasonForm table td{
            border-bottom:1px dashed #BFC5C5;
            border-left:#dfe4e5;
            border-right:#dfe4e5;
            height:36px;
            line-height:18px;
            padding-left:15px;
        }
        #refuseReasonForm table tr td:nth-child(odd){
            background: #f1f5f9;
            padding-right:10px;
            text-align:left;
            
            
        }
        #refuseReasonForm table tr td:nth-child(even){
            background: #FFFFFF;
        }
</style>
</head>
<body>
	<table id="repaymentMsg_result" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
		<span>姓名：</span><input  id="personName" maxlength="25"  style="width: 90px" class="easyui-validatebox"/>  
		<span>身份证号：</span><input  id="personIdnum" maxlength="25" style="width: 180px" class="easyui-validatebox" />  	
     	  <span>生成日期：</span>
     	 <input id="buildStartDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
  		 <span>到：</span>
     	 <input id="buildEndDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		 <span>发送日期：</span>
     	 <input id="sendStartDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
  		 <span>到：</span>
     	 <input id="sendEndDate" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
     	 <input   id="statusValue" maxlength="25" type="hidden" value="" style="width:180px" class="easyui-validatebox" />
			<select  id="status"  editable="false"  class="easyui-combobox"  data-options="width:120,
								onSelect: function(rec){    
          						   $('#statusValue').val($('#status').combobox('getValue'));  
       						 }
							">  
							<option value="">全部</option>  
							<option value="0">初始化</option>
							<option value="1">待发送</option>	 
							<option value="2">发送中</option>	 
							<option value="3">发送成功</option>	 
							<option value="4">发送失败</option>	 
      			  	</select>
		 </div>
        <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
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
</body>
</html>