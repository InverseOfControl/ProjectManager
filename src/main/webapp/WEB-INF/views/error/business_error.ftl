<#include "../macros/constant_output_macro.ftl">
<@htmlCommonHead/>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <style>
        *{
            font-family:'宋体',"Helvetica Neue",Helvetica,Arial,sans-serif;
        }
        #error{
            width: 96%;
            margin: 0 auto;
        }
        dl dt{
            width:200px ;
            height: 28px;
            line-height: 28px;
            background: #99CCFF;
            border-radius: 5px;
            text-align: left;
            text-indent: .5em;
            font-weight: bold;
            color: #ffffff;
            margin: 8px 0 8px 0;
        }
        dl dt:hover{
            background: #b7dbff;
        }
        dl dd{
            display: none;
            background: #dedede;
            line-height: 28px;
            text-indent: 1em;
            margin: 8px 0 8px 0;
            padding: 5px;
            border: solid 1px #99CCFF ;
            border-top-left-radius: 5px;
        }

    </style>
</head>
<body>
        <div id="error">
            <dl>
                 <dt id="one">业务错误代码:</dt>
                <dd><p><#if errorCode??>${errorCode}</#if></p></dd>
                <dt id="two">业务错误信息:</dt>
                <dd><p><#if errorMsg??>${errorMsg}</#if> </p></dd>
                <dt id="three">异常堆栈:</dt>
                <dd><p><#if stackTraceMsg??>${stackTraceMsg}</#if> </p></dd>
            </dl>
        </div>
        <script>
                $(function(){
                    $("dl dt").click(function(){
//                        $("dl dt+dd").toggle();
                        if($(this).next().css("display")=="none"){
                            $(this).next().show(1000);
                        }else{
                            $(this).next().hide(1000);
                        }

                    });
                })
        </script>
</body>
</html>