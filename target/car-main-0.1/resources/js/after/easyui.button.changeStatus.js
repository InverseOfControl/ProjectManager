var timer;
var winOpen;
function IfWindowClosed(d) { 
	if (winOpen.closed == true) { 
	  var d= document.getElementById(d);
		 setButtonState( d, false,"导出");
	window.clearInterval(timer) ;
	} 
	} 
function setButtonState(domElem, disabled,text) {                    // 设置按钮状态
	$(domElem).linkbutton({   
		      text:text  
		   });
    var data = $.data(domElem, "linkbutton");                   // 获取对象的数据
    if (disabled) {                                             // 禁用按钮
        data.options.disabled = true;
        var href = $(domElem).attr("href");                     // 获取超级连接
        if (href) {
            data.href = href;                                   // 保存原来的超级链接
            $(domElem).attr("href", "javascript:void(0)");      // 重新设置
        }
        if (domElem.onclick) {                                  // 是否有点击事件处理
            data.onclick = domElem.onclick;
            domElem.onclick = null;                             // 取消掉
        }
        var eventData = $(domElem).data("events") || $._data(domElem, 'events');
        if (eventData && eventData["click"]) {
            var clickHandlerObjects = eventData["click"];
            data.savedHandlers = [];
            for (var i = 0; i < clickHandlerObjects.length; i++) {
                if (clickHandlerObjects[i].namespace != "menu") {
                    var handler = clickHandlerObjects[i]["handler"];
                    $(domElem).unbind('click', handler);
                    data.savedHandlers.push(handler);
                }
            }
        }

        $(domElem).addClass("l-btn-disabled");                  // 使用样式
    } else {
        data.options.disabled = false;                          // 启用按钮
        if (data.href) {                                        // 恢复原来的超级链接
            $(domElem).attr("href", data.href);
        }
        if (data.onclick) {                                     // 恢复原来的点击事件处理
            domElem.onclick = data.onclick;
        }
        if (data.savedHandlers) {
            for (var i = 0; i < data.savedHandlers.length; i++) {
                $(domElem).click(data.savedHandlers[i]);
            }
        }

        $(domElem).removeClass("l-btn-disabled");
    }
}