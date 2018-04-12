var rowArr =  new Array();
$(function () {
var url;
	



	// 查询按钮
	$('#searchBt').bind('click', search);
	 // 列表
    $('#list_result').datagrid({
        url: 'ecif/getEcifTransList',
    	fitColumns : true,
        border : false,
        singleSelect:false,
        pagination : true,
        fit:true,
        striped: true,
        pageSize:20,
        checkOnSelect: false,
        selectOnCheck: false,
        rownumbers : true,
        columns :[[
                   {
           			field : 'personIdnum',
           			title : '证件号码'
           		},
           	    {
           			field : 'personName',
           			title : '客户姓名'
           		},
           	  {
                	field : 'productId',title : '产品类型',
                	formatter: function(value, row, index){
                  			return  formatEnumName(value,'PRODUCT_ID');
                		}
                },
                {
                	field : 'productType',title : '借款类型',
                	formatter: function(value, row, index){
                  			return  formatEnumName(value,'PRODUCT_TYPE');
                		}
                },
                {
           			field : 'statusMessage',
           			title : '状态'
           		},
           	 {
           			field : 'interfaceType',
           			title : '接口类型'
           		},
           	  {
           			field : 'ecifReq',
           			title : '请求信息',
                formatter:function  (value, row, index) {
                        rowArr.push(row);
                         var a = '<a  href="javascript:void(0)" onclick="openXML('+row.id+',\''+ 'ecifReq' + '\')">查看</a>';
                      return a;
                }
           		},
           	  {
           			field : 'ecifRes',
           			title : '返回信息',
                formatter:function  (value, row, index) {
                        rowArr.push(row);
                         var a = '<a  href="javascript:void(0)" onclick="openXML('+row.id+',\''+ 'ecifRes' + '\')">查看</a>';
                      return a;
                }
           		},
           	 
           	  {
           			field : 'createdTime',
           			title : '发送日期',
                formatter: function(value, row, index){
                        return  getYMD(value);
                    }
           		},
           	  {
           			field : 'modifiedTime',
           			title : '修改日期',
                formatter: function(value, row, index){
                        return  getYMD(value);
                    }
           		}
           	
                   
                   
                   ]]
    
    
    
	});
    // 设置分页控件
   var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
 //      //确定按钮
	// $("#refuseSubmitBt").bind('click',submitRefuse);
	
	
	//  //批量放款按钮
	// $("#batchMakeLoansBt").bind('click',batchMakeLoans);
	
	//退回窗口点击取消
	$("#refuseCancelBt").bind('click',cencalRefuse);
	
	 $(document).keydown(function(e) {
	    	if(e.which == 13) {
	    		$('#searchBt').click();
	    	}
	    });
    
});


//点击取消
function  cencalRefuse(){
	$('#dlg').dialog('close');	
}

function search(){
	var queryParams = $('#list_result').datagrid('options').queryParams;
	

	if($('#toolbar #status').combobox('getValue')=="all"){
    	 queryParams.status =null;
    }else{
	    queryParams.status = $('#toolbar #status').combobox('getValue');
    }


	
   
	
	queryParams.createdTimeAfter = $('#toolbar #createdTimeAfter').val();
	queryParams.createdTimeBefore = $('#toolbar #createdTimeBefore').val();


	queryParams.personName = $('#toolbar #personName').val();
	queryParams.personIdnum = $('#toolbar #personIdnum').val();



	 setFirstPage("#list_result");
	$('#list_result').datagrid('options').queryParams = queryParams;
	$("#list_result").datagrid('reload');
}


function openXML (id,type) {
    var str = '';
  $(rowArr).each(function(index, el) {
    if(el.id === id){
      str = el[type];
    }
  });
    var xml = '<xmp>'+str+'</xmp>';
    $('#dlg').dialog('open').dialog('setTitle', '查看XML信息');
    $('#dlg #content').empty().append(xml);
}
