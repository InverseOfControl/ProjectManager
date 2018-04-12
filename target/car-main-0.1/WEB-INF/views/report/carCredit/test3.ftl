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
            url: "/report/car3",
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

        //年
        LODOP.ADD_PRINT_TEXT(156, 279, 41, 12, data[1]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //月
        LODOP.ADD_PRINT_TEXT(156, 331, 41, 12, data[2]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //日
        LODOP.ADD_PRINT_TEXT(156, 384, 41, 12, data[3]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //某某市
        LODOP.ADD_PRINT_TEXT(156, 448, 41, 12, data[4]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //某某区
        LODOP.ADD_PRINT_TEXT(156, 501, 41, 12, data[5]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);


        //甲方借款人
        LODOP.ADD_PRINT_TEXT(178, 211, 204, 12, data[6]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        //身份证号码
        LODOP.ADD_PRINT_TEXT(202, 196, 219, 12, data[7]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        //现住址
        LODOP.ADD_PRINT_TEXT(224, 220, 283, 12, data[8]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        //电子邮箱
        LODOP.ADD_PRINT_TEXT(248, 242, 230, 12, data[9]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        //前期风险资金
        LODOP.ADD_PRINT_TEXT(449, 306, 415, 12, data[10]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        //中期风险基金
        LODOP.ADD_PRINT_TEXT(474, 302, 415, 84, data[11]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);
    }

</script>