var url;

$(function () {
	// 列表
    $('#list_result').datagrid({
    	  onLoadSuccess:function(data){ 
    		  if(data.total==0)
    		  {
    		    $.messager.show({
                    title:'结果',
                    msg:'没有数据！',
                    showType:'slide'
                });
    		  }
    	  },
        url: 'organ/management/organCardList',
        fitColumns: true,
        border: false,
        singleSelect:true,
        fit:true,
        pagination: true,
        pageSize: 10,
        striped: true,
        rownumbers: true,	
        columns : [ [ 
		{
			field : 'name',
			title : '姓名'
		},                    
        {
			field : 'tradeDate',
			title : '交易日期'
		},
	    {
	    	field : 'tradeKind',
	    	title : '交易方式'
	    },
	    {
	    	field : 'tradeType',
	    	title : '交易类型'
	    },
	    {
	    	field : 'income',
	    	title : '收入'
	    },
	    {
	    	field : 'expense',
	    	title : '支出'
	    }
		] ]
	});
    // 设置分页控件
    var p = $('#list_result').datagrid('getPager');
    $(p).pagination({
        pageList: [ 10, 20, 50 ]
    });
    
	// 查询按钮
//	$('#searchBt').bind('click', search);
    
   //按Enter键查询
	$(document).keydown(function(e) {
		if (e.which == 13){	
			$('#searchBt').click();
		}
	});

});

function search(){	//TBD
};


