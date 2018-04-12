<html>
<head>
	<script src="/resources/js/report/LodopFuncs.js"></script>
	<script src="/resources/js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<p><a href="javascript:Preview()">打印预览</a>表格js去掉</p>  
</body>
</html>

<script language="javascript" type="text/javascript">
 function Preview() {
 	$.ajax({
					 type: "POST",
					 url: "/report/reportOne",
					 cache:false,
					 async: false,
					 data:   {r:Math.random,contractNo:"ZDB12014011044428"},
					 dataType:'json',
					 success: function(data){
					 	CreateFullBill(data);
						LODOP.PREVIEW();	
					 }
		   }) 		
		
 };	
	
 var LODOP; //声明为全局变量   
 function CreateFullBill(data) {
		LODOP=getLodop();  
		//设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
		
		//合同编号
		LODOP.ADD_PRINT_TEXT(96,604,136,12,data[0]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//年 
		LODOP.ADD_PRINT_TEXT(140,179,56,12,data[1]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月 
		LODOP.ADD_PRINT_TEXT(140,229,50,12,data[2]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//日 
		LODOP.ADD_PRINT_TEXT(140,269,50,12,data[3]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//市
		LODOP.ADD_PRINT_TEXT(140,330,72,12,data[4]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//区
		LODOP.ADD_PRINT_TEXT(140,402,72,12,data[5]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//甲方 借款人
		LODOP.ADD_PRINT_TEXT(173,172,280,12,data[6]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//身份证号码
		LODOP.ADD_PRINT_TEXT(173,520,210,12,data[7]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//住址
		LODOP.ADD_PRINT_TEXT(196,172,280,24,data[8]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//email
		LODOP.ADD_PRINT_TEXT(196,520,210,12,data[9]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//大写金额
		LODOP.ADD_PRINT_TEXT(345,461,125,12,data[10]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",3);  
		
		//小写金额
		LODOP.ADD_PRINT_TEXT(345,610,100,12,data[11]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",3);
	
};	
</script>