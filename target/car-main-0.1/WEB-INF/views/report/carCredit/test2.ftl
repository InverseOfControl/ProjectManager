
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script src="/resources/js/report/LodopFuncs.js"></script>
    <script src="/resources/js/jquery-1.8.0.min.js"></script>
</head>
<body>
<p><a href="javascript:Preview()">打印预览</a>表格js</p>
</body>
</html>
<script language="javascript" type="text/javascript">

function Preview() {
    CreateFullBill(data);
    LODOP.PREVIEW();
};


function Preview() {
    $.ajax({
        type: "POST",
        url: "/report/car2",
        cache: false,
        async: false,
        data: {r: Math.random, contractNo: "ZDB12014011044429"},
        dataType: 'json',
        success: function (data) {
            CreateFullBill(data);
            LODOP.PREVIEW();                             t
        }
    });
}

var LODOP; //声明为全局变量
function CreateFullBill(data) {
    LODOP=getLodop();

    //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
    LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");

    //合同编号
    /* 	LODOP.ADD_PRINT_TEXT(118,564,170,12,"aaa11111111111122222222222");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1); */

    //年
    LODOP.ADD_PRINT_TEXT(175,336,41,12,data[1]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

    //月
    LODOP.ADD_PRINT_TEXT(175,396,41,12,data[2]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

    //日
    LODOP.ADD_PRINT_TEXT(175,440,41,12,data[3]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

    //某某市
    LODOP.ADD_PRINT_TEXT(175,587,41,12,data[4]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

    //某某区
    LODOP.ADD_PRINT_TEXT(175 ,636,41,12,data[5]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


    //甲方借款人
    LODOP.ADD_PRINT_TEXT(223,260,159,12,data[6]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

    //身份证号码
    LODOP.ADD_PRINT_TEXT(247,159,238,12,data[7]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",3);

    //现住址
    LODOP.ADD_PRINT_TEXT(268,206,283,12,data[8]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //联系电话
    LODOP.ADD_PRINT_TEXT(295,221,98,12,data[9]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //电子邮箱
    LODOP.ADD_PRINT_TEXT(295,367,138,12,data[10]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",6);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //丙方抵押人
    LODOP.ADD_PRINT_TEXT(330,382,57,12,data[11]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //丙方地址
    LODOP.ADD_PRINT_TEXT(358,220,280,12,data[12]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //丙方电话
    LODOP.ADD_PRINT_TEXT(380,132,280,12,data[13]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


    //借款详细用途
    LODOP.ADD_PRINT_TEXT(453,244,257,36,data[14]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //借款本金数额
    LODOP.ADD_PRINT_TEXT(528,347,162,36,data[15]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);


    //百
    LODOP.ADD_PRINT_TEXT(531,508,30,12,data[16]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //拾
    LODOP.ADD_PRINT_TEXT(531,538,30,12,data[17]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //万
    LODOP.ADD_PRINT_TEXT(531,568,30,12,data[18]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //千
    LODOP.ADD_PRINT_TEXT(531,598,30,12,data[19]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //百
    LODOP.ADD_PRINT_TEXT(531,628,30,12,data[20]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //拾
    LODOP.ADD_PRINT_TEXT(531,658,30,12,data[21]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //元
    LODOP.ADD_PRINT_TEXT(531,688,30,12,data[22]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //角
    LODOP.ADD_PRINT_TEXT(531,718,30,12,data[23]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //分
    LODOP.ADD_PRINT_TEXT(531,748,30,12,data[24]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);


    //借款本金数额
    LODOP.ADD_PRINT_TEXT(588,347,162,36,data[25]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);


    //百
    LODOP.ADD_PRINT_TEXT(598,508,30,12,data[26]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //拾
    LODOP.ADD_PRINT_TEXT(598,538,30,12,data[27]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //万
    LODOP.ADD_PRINT_TEXT(598,568,30,12,data[28]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //千
    LODOP.ADD_PRINT_TEXT(598,598,30,12,data[29]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //百
    LODOP.ADD_PRINT_TEXT(598,628,30,12,data[30]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //拾
    LODOP.ADD_PRINT_TEXT(598,658,30,12,data[31]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //元
    LODOP.ADD_PRINT_TEXT(598,688,30,12,data[32]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //角
    LODOP.ADD_PRINT_TEXT(598,718,30,12,data[33]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //分
    LODOP.ADD_PRINT_TEXT(598,748,30,12,data[34]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,260,41,12,data[35]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //借款期限年
    LODOP.ADD_PRINT_TEXT(662,324,41,12,data[36]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,376,41,12,data[37]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,428,41,12,data[38]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,491,41,12,data[39]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,543,41,12,data[40]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
    //借款期限期
    LODOP.ADD_PRINT_TEXT(662,592,41,12,data[41]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //甲方专用账户:户名
    LODOP.ADD_PRINT_TEXT(722,302,87,36,data[42]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //甲方专用账号:账号
    LODOP.ADD_PRINT_TEXT(722,431,151,36,data[43]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

    //甲方专用账户:开户行
    LODOP.ADD_PRINT_TEXT(722,630,147,36,data[44]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);


    LODOP.NEWPAGE();

    //借款详细用途
    LODOP.ADD_PRINT_TEXT(152,181,529,380,data[45]);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

};

</script>