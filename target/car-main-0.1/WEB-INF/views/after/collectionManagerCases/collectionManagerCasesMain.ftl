<#include "../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript">
var rayUseUrl='${WebConstants.webUrl}${WebConstants.contextPath}/';
</script>
<script type="text/javascript" charset="UTF-8" src="resources/js/credit.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/after/collectionManagerCases/managerCases.js"></script>
<script type="text/javascript" src="resources/js/My97DatePicker/WdatePicker.js"></script> 
 <script type="text/javascript">var enumJson='${gridEnumJson}';</script>
<script type="text/javascript" charset="utf-8">
function exportExcel(){
	var url='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/findCountByParams?selectedProductId=';
	var url2='${WebConstants.webUrl}${WebConstants.contextPath}/'+'finance/financialAudit/exportExcel?selectedProductId=';
	var params='';
	var  personIdnum=null;
	var  personName=null;
	var  selectedProductId=null;
	var  status=null;
	var  contractConfirmStartDate=null;
	var  contractConfirmEndDate=null;
		personName = $('#personName').val();
		personIdnum = $('#personIdnum').val();
		contractConfirmStartDate = $('#contractConfirmStartDate').val();
		contractConfirmEndDate = $('#contractConfirmEndDate').val();
		var contractSrc =0;
		if($('#contractSrc').combobox('getValue')=="all"){
			 contractSrc = 0;
	    }else{
		   	 contractSrc = $('#contractSrc').combobox('getValue');
	    }
	   
		if($('#loanType').combobox('getValue')=="all"){
			selectedProductId =null;
	    }else{
	    	selectedProductId= $('#loanType').combobox('getValue');
	    	params+=selectedProductId;
	    }
		if($('#loanStatus').combobox('getValue')=="all"){
			status =0;
	    }else{
	    	status = $('#loanStatus').combobox('getValue');
	    }	    
		params+="&status="+status;
		if(personName!=''){
			//对汉字进行编码设置 ，服务端同样需要进行编码设置
			params+='&personName='+encodeURI(encodeURI(personName));
		}
		if(personIdnum!=''){
			params+='&personIdnum='+personIdnum;
		}
		if(contractSrc!=''||contractSrc==0){
			params+='&contractSrc='+contractSrc;
		}		
		if(contractConfirmStartDate!=''){
			params+='&contractConfirmStartDate='+contractConfirmStartDate;
		}
		if(contractConfirmEndDate!=''){
			params+='&contractConfirmEndDate='+contractConfirmEndDate;
		}
		url+=params;
		url2+=params;
		$.ajax({
	  	 url : url,	  
	  	 type:"POST",
	  	 success : function(result){
		 	  if(result=="success"){
		 	 	 self.location.href=url2;			
		 	  }else{
				 $.messager.show({
						title : '提示',
						msg : result
				});
		  	 }		    			
  			$('#checkPageTb').datagrid('reload');
	   }
	});	
}
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
	<table id="casesPageTb" toolbar="#toolbar"></table>
	<!-- toolbar  -->
	<span  style="display:none;"> 
	<input id="reason" name="reason" type="hidden" oninput="inputChange(this.value);" onpropertyChange="propertyChange(this.value);">
	<input id="optFlag" name="optFlag" type="hidden" value="${optFlag}">
	</span>
	<div  id="flshDiv" tabindex="-1" onfocus="inputChange('focus');"  > </div>
	<form id="casesForm"   method="post" >
	<div id="toolbar" style="height:120px;padding-top:8px;">
		<div  style="padding:8px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">
			<span>借款人:</span>
			&nbsp;&nbsp;&nbsp;<input id="personName" name="personName" type="text"  /> 
			<span>身份证号：</span>
			<input id="idNum" name="idNum" type="text"  />
			<span>营业部：</span>
            <select  id="salesDeptId" name="salesDeptId" editable="false" class="easyui-combobox" data-options="width:200"> 
            </select>
		    <span>借款类型：</span>
            <select  id="loanType" name="loanType"  editable="false" class="easyui-combobox" data-options="width:80">   </select>
     		 <span>产品类型：</span>
            <select  id="productTypeComb" name="productType"  editable="false" class="easyui-combobox" data-options="width:80">   </select>
     		<span>案件状态：</span>
            <select  id="caseState"  name="caseState"  editable="false" class="easyui-combobox" data-options="width:120"> 
            	<option value="">全部</option>
            	<option value=8  selected>默认</option>
            	<option value=1>客服催收中</option>
            	<option value=2>未分派 </option>
            	<option value=3>部门催收中 </option>
            	<option value=4>作业完成_未全部收回 </option>
            	<option value=5>作业完成_全部收回</option>
            	<option value=6>结案_全部收回</option>
            	<option value=7>结案_坏账</option>
            </select>
     		<span>案件创建日期：</span><input  id="casesStartDate" name="casesStartDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"></input>
      						  - 
      		<input  id="casesEndDate"  name="casesEndDate" style="width: 180px" class="Wdate easyui-validatebox"  type="text"  onfocus="WdatePicker()"/>

        	</div>
        
          <div style="margin: 7px 0 0px 0px;padding:5px;padding-left:5px;border:solid 1px #99CCFF;background:#ededed;border-top-left-radius:5px;">			
	        <a href="javascript:void(0)" id="searchBt" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
	        <a href="javascript:void(0)" id="excelBt" class="easyui-linkbutton"  data-options="iconCls:'icon-save'" >导出</a>
 
				 <select  id="collectionUser" name="collectionUser" editable="false" class="easyui-combobox" data-options="width:140"> </select>
            	  <a href="javascript:void(0)" id="dispatchBt" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">批量分派</a> 
			 
		</div>	
	</div>
	<form>
			
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