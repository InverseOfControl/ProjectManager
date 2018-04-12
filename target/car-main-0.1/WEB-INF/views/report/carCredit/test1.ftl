
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
        $.ajax({
            type: "POST",
            url: "/report/car1",
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
        LODOP.ADD_PRINT_TEXT(118, 564, 170, 12, data[0]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        //年
        LODOP.ADD_PRINT_TEXT(180,216,41,12,data[1]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //月
        LODOP.ADD_PRINT_TEXT(180,265,41,12,data[2]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //日
        LODOP.ADD_PRINT_TEXT(180,317,41,12,data[3]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //某某市
        LODOP.ADD_PRINT_TEXT(180,384,41,12,data[4]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //某某区
        LODOP.ADD_PRINT_TEXT(180,433,41,12,data[5]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


        //甲方借款人
        LODOP.ADD_PRINT_TEXT(219,154,192,12,data[6]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //身份证号码
        LODOP.ADD_PRINT_TEXT(240,136,207,12,data[7]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",3);

        //现住址
        LODOP.ADD_PRINT_TEXT(258,168,230,12,data[8]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

        //电子邮箱
        LODOP.ADD_PRINT_TEXT(279,178,222,12,data[9]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

        //丙方抵押人
        LODOP.ADD_PRINT_TEXT(312,676,68,12,data[10]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        //丙方地址
        LODOP.ADD_PRINT_TEXT(332,539,230,12,data[11]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

        //邮编
        LODOP.ADD_PRINT_TEXT(354,483,230,12,data[12]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);

        LODOP.ADD_PRINT_TEXT(670,369,75,12,data[13]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",3);

        LODOP.NEWPAGE();

        LODOP.ADD_PRINT_TEXT(495,203,580,12,data[14]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

        LODOP.ADD_PRINT_TEXT(526,203,520,420,data[15]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",1);

        LODOP.NEWPAGE();

        LODOP.ADD_PRINT_TEXT(321,226,514,12,data[16]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


        LODOP.ADD_PRINT_TEXT(340,306,434,12,data[17]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


        LODOP.ADD_PRINT_TEXT(361,125,612,12,data[18]);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Alignment",2);


    };

</script>