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
            url: "/report/car4",
            cache: false,
            async: false,
            data: {r: Math.random, contractNo: "ZDB12014011044429"},
            dataType: 'json',
            success: function (data) {
                CreateFullBill(data);
                LODOP.PREVIEW();
            }
        });
    }


    var LODOP; //声明为全局变量
    function CreateFullBill(data) {
        LODOP = getLodop();

        //设置纸张为A4（按操作系统定义的A4尺寸），纵向打印
        LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");

        //合同编号
        LODOP.ADD_PRINT_TEXT(118, 564, 170, 12, data[0]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        //委托人
        LODOP.ADD_PRINT_TEXT(175, 189, 219, 12, data[1]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //身份中编号
        LODOP.ADD_PRINT_TEXT(195, 247, 192, 12, data[2]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //委托人（财产共有人）
        LODOP.ADD_PRINT_TEXT(216, 290, 136, 12, data[1]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //身份证编号
        LODOP.ADD_PRINT_TEXT(235, 275, 196, 12, data[2]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //汽车品牌
        LODOP.ADD_PRINT_TEXT(276, 423, 151, 12, data[4]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        //车牌号
        LODOP.ADD_PRINT_TEXT(295, 188, 94, 12, data[5]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        //车架号
        LODOP.ADD_PRINT_TEXT(295, 355, 120, 12, data[6]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        //自然人出借人
        LODOP.ADD_PRINT_TEXT(316, 375, 90, 12, data[7]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //编号
        LODOP.ADD_PRINT_TEXT(316, 549, 90, 12, data[8]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    }
</script>