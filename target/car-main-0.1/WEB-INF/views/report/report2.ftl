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
					 url: "/report/reportTwo",
					 cache:false,
					 async: false,
					 data:   {r:Math.random,contractNo:"ZDB12014011044428"},
					 dataType:'json',
					 success: function(data){
					 	//alert();
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
		LODOP.ADD_PRINT_TEXT(129,273,56,12,data[1]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月 
		LODOP.ADD_PRINT_TEXT(129,338,36,12,data[2]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//日
		LODOP.ADD_PRINT_TEXT(129,376,36,12,data[3]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
	    //市
		LODOP.ADD_PRINT_TEXT(129,426,65,12,data[4]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//区
		LODOP.ADD_PRINT_TEXT(129,488,65,12,data[5]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//甲方 借款人
		LODOP.ADD_PRINT_TEXT(163,168,280,12,data[6]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//身份证
		LODOP.ADD_PRINT_TEXT(187,168,280,12,data[7]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//现住址
		LODOP.ADD_PRINT_TEXT(209,168,370,12,data[8]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//邮编
		LODOP.ADD_PRINT_TEXT(232,168,280,12,data[9]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//借款用途
		LODOP.ADD_PRINT_TEXT(304,205,310,12,data[10]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//借款金额
		LODOP.ADD_PRINT_TEXT(332,295,226,12,data[11]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//十
		LODOP.ADD_PRINT_TEXT(332,520,4,12,data[12]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//万
		LODOP.ADD_PRINT_TEXT(332,546,4,12,data[13]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//千
		LODOP.ADD_PRINT_TEXT(332,573,4,12,data[14]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//百
		LODOP.ADD_PRINT_TEXT(332,599,4,12,data[15]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//十
		LODOP.ADD_PRINT_TEXT(332,625,4,12,data[16]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//元
		LODOP.ADD_PRINT_TEXT(332,651,4,12,data[17]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//角
		LODOP.ADD_PRINT_TEXT(332,677,4,12,data[18]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//分
		LODOP.ADD_PRINT_TEXT(332,703,4,12,data[19]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月还本息
		LODOP.ADD_PRINT_TEXT(358,295,226,12,data[20]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		
		
		//十
		LODOP.ADD_PRINT_TEXT(358,520,4,12,data[21]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//万
		LODOP.ADD_PRINT_TEXT(358,546,4,12,data[22]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//千
		LODOP.ADD_PRINT_TEXT(358,573,4,12,data[23]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//百
		LODOP.ADD_PRINT_TEXT(358,599,4,12,data[24]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//十
		LODOP.ADD_PRINT_TEXT(358,625,4,12,data[25]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//元
		LODOP.ADD_PRINT_TEXT(358,651,4,12,data[26]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//角
		LODOP.ADD_PRINT_TEXT(358,677,4,12,data[27]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//分
		LODOP.ADD_PRINT_TEXT(358,703,4,12,data[28]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		
		
		//期数
		LODOP.ADD_PRINT_TEXT(382,240,56,12,data[29]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//年 
		LODOP.ADD_PRINT_TEXT(408,220,36,12,data[30]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月 
		LODOP.ADD_PRINT_TEXT(408,260,24,12,data[31]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//日
		LODOP.ADD_PRINT_TEXT(408,290,24,12,data[32]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//年 
		LODOP.ADD_PRINT_TEXT(408,330,36,12,data[33]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月 
		LODOP.ADD_PRINT_TEXT(408,375,24,12,data[34]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//月 
		LODOP.ADD_PRINT_TEXT(408,405,24,12,data[35]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		
		//户名
		LODOP.ADD_PRINT_TEXT(427,236,80,36,data[36]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//户名
		LODOP.ADD_PRINT_TEXT(427,326,200,36,data[37]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		
		//户名
		LODOP.ADD_PRINT_TEXT(427,544,180,36,data[38]);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9); 
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
};	
</script>