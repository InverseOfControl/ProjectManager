<#include "../../../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<script type="text/javascript" charset="UTF-8" src="resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/messages_zh.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/uploadify/jquery.uploadify.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/js/uploadify/uploadify.css" />
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/carLoanApply/carLoanApply.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/js/apply/apply.js"></script>

 <link rel="stylesheet" type="text/css" href="resources/css/apply/loan.css" />
 
</head>
<body>
   <!-- 查看车贷  -->
 <div>

    <form id="browseCLForm" method="post">
        <div id="browseCLTabs" class="easyui-tabs">
            <div id="loanBrowseCLTab" title="拒单信息" style="padding:20px">
                <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                    <tr>
                        <td style="font-size:12px; width:8%;"><label>产品类型</label></td>
                        <td align="left" style="font-size:12px; width:25%;"><label id="productName"></label></td>
                      <!--  <td style="font-size:12px; width:25%;"><label>贷款类型</label></td>
                        <td align="left" style="font-size:12px; width:25%;"><label id="loanType"></label></td>
                        -->
                    </tr>
                </table>
                <div id="personBrowseCLPanel" class="easyui-panel browsePanel" title="个人信息" style="width:1020px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td style="font-size:12px; width:25%;"><label>姓 名</label></td>
                            <td align="left" style="font-size:12px; width:26%;"><label id="personName"></label></td>
                            <td style="font-size:12px; width:25%;"><label>身份证号</label></td>
                            <td align="left" style="font-size:12px; width:25%;"><label id="personIdnum"></label></td>
                            
                        </tr>
                    </table>
                </div>
            
                <div id="otherBrowseCLPanel" class="easyui-panel browsePanel" title="其他" style="width:1020px;">
                    <table style="font-size:12px; width:100%; text-align:left;" cellspacing="3">
                        <tr>
                            <td><label>客服姓名</label></td>
                            <td align="left"><label id="serviceName"></label></td>
                               <td><label>客户经理工号</label></td>
                            <td align="left"><label id="crmCode"></label></td>
                            <td><label>客户经理</label></td>
                            <td align="left"><label id="crmName"></label></td>
                         
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                        <tr>
                          
                            <td><label>营业网点</label></td>
                            <td align="left"><label id="salesDeptName"></label></td>
                            <td><label>业务主任工号</label></td>
                            <td align="left"><label id="directorCode"></label></td>
                            <td><label>业务主任</label></td>
                            <td align="left"><label id="directorName"></label></td>
                            <td><label> </label></td>
                            <td align="left"><label> </label></td>
                        </tr>
                         <tr>
                             <td><label>拒单日期</label></td>
                            <td align="left"><label id="requestDate"></label></td>
                          
                        </tr>
                        
                        <tr>
                       		 <td><label>备注</label></td>
                        	 <td align="left" colspan="7" style="width:83%; word-break:break-all"><label id="remark"></label></td>
                        </tr>
                    </table>
                </div>
            </div>

            
            <!--<div id="attachmentCLBrowseTab" title="附件" style="padding:10px">
               <a href="javascript:void(0)" id="downloadAttachmentZip" class="easyui-linkbutton">附件包</a>
            </div>-->
        </div>
    </form>
</div>

<script>
            $(function(){
                $('table tr td:nth-child(odd)').css("background","#f1f5f9");
            })
</script>		
</body>
</html>