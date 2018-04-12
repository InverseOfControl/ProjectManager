/**
 * 展期合同打印
 */
//008-抵押借款展期协议
function previewLoanExtensionAgreement(extensionId) {
    $.ajax({
        type: "POST",
        url: "report/loanExtensionAgreement",
        cache: false,
        async: false,
        data: {r: Math.random, extensionId: extensionId},
        dataType: 'json',
        success: function (data) {
            createLoanExtensionAgreement(data);
            LODOP.PREVIEW();
        }
    });
}

function createLoanExtensionAgreement(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    var contractNo = generateExtensionNo(data[0],data[1],8); 
    
     //合同编号
    LODOP.ADD_PRINT_TEXT(70,531,173,25,contractNo);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
     //年
    LODOP.ADD_PRINT_TEXT(126,274,39,20,data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //月
    LODOP.ADD_PRINT_TEXT(126,323,34,18,data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //日
    LODOP.ADD_PRINT_TEXT(126,367,34,18,data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //某市
    LODOP.ADD_PRINT_TEXT(126,515,33,20,data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //某区
    LODOP.ADD_PRINT_TEXT(126,564,60,20,data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //甲方借款人
    LODOP.ADD_PRINT_TEXT(161,200,160,20,data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //身份证号码
    LODOP.ADD_PRINT_TEXT(182,120,240,20,data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //现住址
    LODOP.ADD_PRINT_TEXT(202,120,240,20,data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-年
    LODOP.ADD_PRINT_TEXT(349,213,34,20, data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-月
    LODOP.ADD_PRINT_TEXT(349,263,34,20,data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-日
    LODOP.ADD_PRINT_TEXT(349,307,34,20,data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原合同编号
    LODOP.ADD_PRINT_TEXT(349,443,160,20,data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-年
    LODOP.ADD_PRINT_TEXT(387,420,40,20,data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-月
    LODOP.ADD_PRINT_TEXT(387,465,34,20,data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-日
    LODOP.ADD_PRINT_TEXT(387,500,34,20,data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原合同编号
    LODOP.ADD_PRINT_TEXT(387,560,160,20,data[0]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原合同金额-大写
    LODOP.ADD_PRINT_TEXT(484,298,100,20,data[13]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原合同金额-小写
    LODOP.ADD_PRINT_TEXT(484,412,73,20,data[14]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-年
    LODOP.ADD_PRINT_TEXT(484,559,43,20,data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-月
    LODOP.ADD_PRINT_TEXT(484,615,43,20,data[11]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-日
    LODOP.ADD_PRINT_TEXT(484,663,43,20,data[12]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原最后还款-年
    LODOP.ADD_PRINT_TEXT(506,83,44,20,data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-月
    LODOP.ADD_PRINT_TEXT(506,140,44,20,data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //原来签约-日
    LODOP.ADD_PRINT_TEXT(506,197,44,20, data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //借款本金数额
    LODOP.ADD_PRINT_TEXT(565,281,221,20,data[15]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    LODOP.ADD_PRINT_TEXT(565,568,122,20,data[16]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //总利息
    LODOP.ADD_PRINT_TEXT(585,281,220,20,data[17]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
    LODOP.ADD_PRINT_TEXT(585,568,122,20,data[18]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限年
    LODOP.ADD_PRINT_TEXT(606,220,45,20,data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期月
    LODOP.ADD_PRINT_TEXT(606,290,45,20,data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期日
    LODOP.ADD_PRINT_TEXT(606,354,45,20,data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期年	
    LODOP.ADD_PRINT_TEXT(606,422,45,20,data[19]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期月
    LODOP.ADD_PRINT_TEXT(606,497,45,20,data[20]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //借款期限期日
    LODOP.ADD_PRINT_TEXT(606,565,45,20,data[21]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
     //甲方专用账户:户名
    LODOP.ADD_PRINT_TEXT(630,230,98,20,data[22]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //甲方专用账号:账号
    LODOP.ADD_PRINT_TEXT(630,390,160,20,data[23]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //甲方专用账户:开户行
    LODOP.ADD_PRINT_TEXT(630,600,160,20,data[24]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    
     if(data[25].length>0){
    	for(var i=0;i<data[25].length;i++){
    		 	 LODOP.ADD_PRINT_TEXT(800+(i-1)*16,158,112,20,data[25][i][0]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(800+(i-1)*16,287,120,20,data[25][i][1]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(800+(i-1)*16,394,120,20,data[25][i][2]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    			 
    			 LODOP.ADD_PRINT_TEXT(800+(i-1)*16,514,120,20,data[25][i][3]);
    			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    	}
     }
     
     //new page-second page
     LODOP.NEWPAGE();
     //展期-合同编号
     LODOP.ADD_PRINT_TEXT(668,465,193,20,contractNo);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 1); 
     //原合同编号
     LODOP.ADD_PRINT_TEXT(692,197,126,18,data[0]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 1); 
     //年
     LODOP.ADD_PRINT_TEXT(713,110,37,20,data[2]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //月
     LODOP.ADD_PRINT_TEXT(713,165,30,20,data[3]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //日
     LODOP.ADD_PRINT_TEXT(713,217,30,20,data[4]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //某市
     LODOP.ADD_PRINT_TEXT(713,328,40,20,data[5]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //某区
     LODOP.ADD_PRINT_TEXT(713,380,70,20,data[6]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //甲方借款人
     LODOP.ADD_PRINT_TEXT(741,130,140,18,data[7]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //身份证号码
     LODOP.ADD_PRINT_TEXT(761,130,200,18,data[8]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //现住址
     LODOP.ADD_PRINT_TEXT(779,130,200,18,data[9]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     //原合同编号
     LODOP.ADD_PRINT_TEXT(923,374,160,20,data[0]);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 1); 
     //展期-合同编号
     LODOP.ADD_PRINT_TEXT(944,180,180,20,contractNo);
     LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     LODOP.SET_PRINT_STYLEA(0, "Alignment", 1); 
     
     LODOP.NEWPAGE();//new page
     
     if(data[26].length>0){
     	for(var i=0;i<data[25].length;i++){
     		    if(i == 0){
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,152,100,20,data[26][i][0]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,266,73,20,data[26][i][1]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,342,78,20,data[26][i][2]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,426,73,20,data[26][i][3]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,522,73,20,data[26][i][4]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(186+(i-1)*16,604,130,20,data[26][i][5]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    }else{
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,152,100,20,data[26][i][0]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,266,73,20,data[26][i][1]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,342,78,20,data[26][i][2]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,426,73,20,data[26][i][3]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,522,73,20,data[26][i][4]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    	LODOP.ADD_PRINT_TEXT(214+(i-1)*16,604,130,20,data[26][i][5]);
     		    	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
     		    	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
     		    	
     		    }
     	}
      }
}

function generateExtensionNo(no,agreeNum,type) {
	//展期合同编号--现有代码只支持展23次
    var seq = type+4*(agreeNum-1);
    seq = "00"+seq;
    if(seq.length>3){
    	seq = seq.substring(1,4);
    }
    var contractNo = no+"-"+seq;
	return contractNo;
}

//009 个人借款咨询服务风险基金协议(展期)-TBD-duplicated
function previewFundRiskAgreement(extensionId) {
    $.ajax({
        type: "POST",
        url: "report/loanFundRiskAgreement",
        cache: false,
        async: false,
        data: {r: Math.random,  extensionId: extensionId},
        dataType: 'json',
        success: function (data) {
            createFundRiskAgreement(data);
            LODOP.PREVIEW();
        }
    });
}

function createFundRiskAgreement(data) {
    LODOP = getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

    var contractNo = generateExtensionNo(data[0],data[1],9);
    //合同编号
    LODOP.ADD_PRINT_TEXT(100,540,165,22,contractNo);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    //年
    LODOP.ADD_PRINT_TEXT(128,215,40,20,data[2]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //月
    LODOP.ADD_PRINT_TEXT(128,285,40,20,data[3]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //日
    LODOP.ADD_PRINT_TEXT(128,336,40,20,data[4]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //某某市
    LODOP.ADD_PRINT_TEXT(128,390,39,20,data[5]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //某某区
    LODOP.ADD_PRINT_TEXT(128,431,90,20,data[6]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //甲方借款人
    LODOP.ADD_PRINT_TEXT(150,161,151,20,data[7]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //身份证号码
    LODOP.ADD_PRINT_TEXT(176,150,202,20,data[8]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //现住址
    LODOP.ADD_PRINT_TEXT(200,100,300,20,data[9]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //电子邮箱
    LODOP.ADD_PRINT_TEXT(222,150,105,20,data[10]);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //风险基金
    if(data[11].length>0){
	for(var i=0;i<data[11].length;i++){
		if(i==0){
			 LODOP.ADD_PRINT_TEXT(425,271,111,20, data[11][i][0]);
			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
			 
			 LODOP.ADD_PRINT_TEXT(425,480,100,20,data[11][i][1]);
			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
		}else{
			 LODOP.ADD_PRINT_TEXT(485+(i-1)*16,271,110,20,data[11][i][0]);
			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
			 
			 LODOP.ADD_PRINT_TEXT(485+(i-1)*16,480,105,20,data[11][i][1]);
			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
		}
	}
}
}
/* 010 还款温馨提示函(展期)
function previewRepaymentAgreement(extensionId) {
	$.ajax({
		type: "POST",
		url: "report/loanRepaymentAgreement",
		cache: false,
		async: false,
		data: {r: Math.random,  extensionId: extensionId},
		dataType: 'json',
		success: function (data) {
			createRepaymentAgreement(data);
			LODOP.PREVIEW();
		}
	});
}

function createRepaymentAgreement(data) {
	LODOP = getLodop();
	
	//设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	var contractNo = generateExtensionNo(data[0],data[1],10);
	//合同编号
	LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+contractNo);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
	//借款人
	LODOP.ADD_PRINT_TEXT(128, 30, 40, 12, data[2]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//网点电话
	LODOP.ADD_PRINT_TEXT(228, 180, 100, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//还款明细
	if(data[4].length>0){
		for(var i=0;i<data[4].length;i++){
			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 40, 90, 12, data[4][i][0]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
 			 
 			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 130, 90, 12, data[4][i][1]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
 			 
 			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 220, 90, 12, data[4][i][2]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
 			 
 			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 310, 90, 12, data[4][i][3]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
 			 
 			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 400, 90, 12, data[4][i][4]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
 			 
 			 LODOP.ADD_PRINT_TEXT(274+(i-1)*12, 490, 90, 12, data[4][i][5]);
 			 LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
 			 LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
		}
	}
	//还款日
	LODOP.ADD_PRINT_TEXT(528, 80, 40, 12, data[5]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//网点电话
	LODOP.ADD_PRINT_TEXT(568, 390, 100, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	
	LODOP.NEWPAGE();//new page
	//逾期费用1天
	LODOP.ADD_PRINT_TEXT(88, 200, 40, 12, data[6]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//逾期费用15天
	LODOP.ADD_PRINT_TEXT(138, 200, 40, 12, data[7]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//网点电话
	LODOP.ADD_PRINT_TEXT(188, 160, 100, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//网点电话
	LODOP.ADD_PRINT_TEXT(268, 180, 100, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//网点电话
	LODOP.ADD_PRINT_TEXT(300, 150, 100, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	
}
// 011 委托扣款授权书（无风险基金的描述)
function previewDeductionAgreement(extensionId) {
	$.ajax({
		type: "POST",
		url: "report/loanDeductionAgreement",
		cache: false,
		async: false,
		data: {r: Math.random,  extensionId: extensionId},
		dataType: 'json',
		success: function (data) {
			createDeductionAgreement(data);
			LODOP.PREVIEW();
		}
	});
}

function createDeductionAgreement(data) {
	LODOP = getLodop();
	
	//设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	var contractNo = generateExtensionNo(data[0],data[1],11);
	//合同编号
	LODOP.ADD_PRINT_TEXT(58, 554, 200, 12, "编号:"+contractNo);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
	//借款人
	LODOP.ADD_PRINT_TEXT(188, 30, 40, 12, data[2]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
	//借款人ID
	LODOP.ADD_PRINT_TEXT(188, 80, 200, 12, data[3]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
	//年
	LODOP.ADD_PRINT_TEXT(188, 361, 41, 12,data[4]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//月
	LODOP.ADD_PRINT_TEXT(188, 400, 41, 12, data[5]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//日
	LODOP.ADD_PRINT_TEXT(188, 440, 41, 12, data[6]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//原合同编号
	LODOP.ADD_PRINT_TEXT(238, 268, 80, 12, data[0]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//年
	LODOP.ADD_PRINT_TEXT(238, 400, 41, 12,data[7]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//月
	LODOP.ADD_PRINT_TEXT(258, 20, 41, 12,data[8]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//日
	LODOP.ADD_PRINT_TEXT(258, 80, 41, 12,data[9]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//借款人
	LODOP.ADD_PRINT_TEXT(278, 60, 41, 12,data[2]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//银行
	LODOP.ADD_PRINT_TEXT(298, 60, 120, 12,data[10]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//支行
	LODOP.ADD_PRINT_TEXT(298, 180, 41, 12,data[11]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//账号
	LODOP.ADD_PRINT_TEXT(318, 60, 80, 12,data[12]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	//联系方式
	LODOP.ADD_PRINT_TEXT(338, 60, 80, 12,data[13]);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
	
}*/
//loan extension add end