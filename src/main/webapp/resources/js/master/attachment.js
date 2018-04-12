$(function () {
	
	   var container = $$("idContainer"),
       options = {
           onPreLoad: function () {
               container.style.backgroundImage = "url('resources/css/swfupload/images/loading.gif')";
           },
           onLoad: function () {
               container.style.backgroundImage = "";
           },
           mouseRotate: true
       },
       it = new ImageTrans(container, options);
		//左旋转
		$$("idLeft").onclick = function () {
		   it.left();
		};
		//右旋转
		$$("idRight").onclick = function () {
		   it.right();
		};
		
		//上一张
		$$("idPrev").onclick = function () {
		   $(".tree-node").each(function () {
		       var prevli = $(this).parent("li").prev("li");
		       if ($(this).attr("node-id") == $("#idContainer").find("img").attr("img-id") && prevli.length > 0) {
		           prevli.children(".tree-node").click();
		       }
		   });
		};
		//下一张
		$$("idNext").onclick = function () {
		   var treenode = $(".tree-node");
		   for (var i = treenode.length; i > 0; i--) {
		       var nextli = treenode.eq(i - 1).parent("li").next("li");
		       if (treenode.eq(i - 1).attr("node-id") == $("#idContainer").find("img").attr("img-id") && nextli.length > 0) {
		           nextli.children(".tree-node").click();
		       }
		   }
		};
		//禁止跳转
		$('a').click(function () {
		   return false;
		});
		
		//图片拖动
		var img  = $("#idContainer").find("img");
		var tempcolor = img.css('filter');//
		var isClick=false; //记录鼠标是否按下
		var defaultX; //按下鼠标时候的坐标
		var defaultY; //移动的时候的坐标
		var mouseX;	//移动的时候的X坐标
		var mouseY;	//移动的时候的Y坐标
		var imgTop;  //IMG离上边的距离
		var imgLeft; //IMG离左边的距离
		$("#idContainer").mousedown(function(e){ //鼠标按下
		   isClick=true;
		   defaultX=e.pageX;
		   defaultY=e.pageY;
		   imgTop=parseFloat(img.css("top"));
		   imgLeft=parseFloat(img.css("left"));
		   return false;
		}).mousemove(function(e) { //鼠标移动
		           mouseX=e.pageX;
		           mouseY=e.pageY;
		           if(isClick) {
		               var newTop=parseFloat(mouseY-defaultY);
		               var newLeft=parseFloat(mouseX-defaultX);
		               img.css({"top":newTop+imgTop});
		               img.css({"left":newLeft+imgLeft});
		           };
		           return false;
		       }).mouseup(function(){ //鼠标松开
		           isClick=false;
		       }).mouseleave(function(){ //鼠标离开
		           isClick=false;
		       });
		
		
		$('#showTree').tree({
		  url: appname+'fileUpload/showImgJson?loanId=' + loanId+'&productId='+productId,
		   onClick: function (node) {
		       if (node.attributes && node.attributes.src) {
		           it.load(node.attributes.src);
		           $("#printImg").attr({"img-id":node.id,"src":node.attributes.src}); //打印（新增）
		           if (typeof(node.attributes.filePath) != 'string') {
		               //$("#idContainer").find("img").css({'left':0 ,'top': 1000, 'position': 'absolute', 'height': 'auto', 'width': '100%'});
		               $("#idContainer").find("img").css({ 'left':0,top:'0' });
		           } else {
		               $("#idContainer").find("img").css({'position': 'absolute', 'border': '0px none', 'padding': '0px', 'margin': '0px', 'width': 'auto', 'height': 'auto', 'visibility': 'visible', 'top':$("#idContainer").parent().height()/2 , 'left':$("#idContainer").parent().width()/2 });
		           }
		           $("#idContainer").find("img").attr("img-id",node.id);
		           it.reset();
		
		       }
		
		   },
		   onLoadSuccess: function (node) {
		       //it.load("upfile/GangnamStyle.gif"); //初始图片路径(src)
		       $("#idContainer").find("img").attr("img-id", "2");//初始图片id(id)
		       $("#printImg").attr({"img-id":"2","src":"resources/css/swfupload/images/GangnamStyle.gif"}); //打印初始化img
		       $('.tree-node').each(function () {
		           if ($(this).attr("node-id") == "2") {
		               $(this).addClass("tree-node-selected"); //初始图片样式(id)
		           }
		       });
		   }
		});
		
		//右击菜单
		$('#showTree').tree({
		
		   onContextMenu: function (e, node) {
		       e.preventDefault();
		       // 选择节点
		       if (typeof(node.attributes) == 'object') {
		           $('#showTree').tree('select', node.target);
		           // 显示上下文菜单
		           $('#mm').menu('show', {
		               left: e.pageX,
		               top: e.pageY
		           });
		       }
		      	else 
		      	{
		      	  	// 显示上下文菜单
		           $('#mmParent').menu('show', {
		               left: e.pageX,
		               top: e.pageY
		           });
		      	}
		   }
		});
		
		// 反色
		$('#invert').click(function(){
		   img.css('filter',tempcolor+' invert');
		})
		
		//重置
		$$("idReset").onclick = function () {
		   img.css('filter',tempcolor)
		   it.reset();
		};
		
		// 打印调用
		$('#idPrint').click(function(){
		   $("#printImg").show().prev().hide();
		   window.print();
		   $("#printImg").hide().prev().show();
		});
	   
});//initEnd
//删除附件
function deleteAttachResult(btn) {
    if(btn == 'yes'){
	 var node = $('#showTree').tree('getSelected');
        $.ajax({
            type: "POST",
            url: appname+'fileUpload/deleteAttachmentDetail',
            data: {attachmentDetailId: node.attributes.attachmentDetailId,optModule:optModule},
            success: function (response) {
             	location.reload();
                $('#showTree').tree('reload');
            },
            error: function (msg) {
                alert('删除失败!');
            }
        });
    }
}

function deleteAttach() {
	  var node = $('#showTree').tree('getSelected');
    //$('#showTree').tree('remove', node.target);
    Ext.MessageBox.confirm('提示', '是否删除附件['+node.text+']', deleteAttachResult);    
}
//下载附件
function downloadAttach() {
    var node = $('#showTree').tree('getSelected');
    var fileDownLoadPath;
    window.open(appname+'fileUpload/downloadAttachmentDetail?attachmentDetailId=' + node.attributes.attachmentDetailId+'&optModule='+optModule, "_blank");
    return;
}
	//批量下载附件打包成zip
function batchDownloadAttach() {
     var node = $('#showTree').tree('getSelected');
    if (node == null || typeof(node.attributes) == 'object') {
          Ext.Msg.alert('提示' ,'批量下载请选中上级目录菜单才可进行操作');
     }
     else
     {
   		Ext.MessageBox.confirm('提示', '是否批量下载文件菜单['+node.text+']下所有文件', batchDownloadAttachResult);    
     }
}
function batchDownloadAttachResult(btn)
{
    if(btn == 'yes'){
	 	var node = $('#showTree').tree('getSelected');
        var fileDownLoadPath;
        var nodeText = encodeURI(encodeURI(node.text));   
        window.open(appname+'fileUpload/batchDownloadAttachmentDetail?nodeText=' + nodeText+'&loanId='+loanId+'&productId='+productId+'&optModule='+optModule, "_blank");
        return;
    }
}
 //批量删除
function batchDeleteAttach() {
     var node = $('#showTree').tree('getSelected');
     if (node == null || typeof(node.attributes) == 'object') {
          Ext.Msg.alert('提示' ,'批量删除请选中上级目录菜单才可进行操作');
     }
     else
     {
   		Ext.MessageBox.confirm('提示', '是否批量删除文件菜单['+node.text+']下所有文件', batchDeleteAttachResult);    
     }
}
function batchDeleteAttachResult(btn)
{
    if(btn == 'yes'){
		 var node = $('#showTree').tree('getSelected');
		  $.ajax({
            type: "POST",
            url: appname+'fileUpload/batchDeleteAttachmentDetail',
            data: {nodeText:node.text ,loanId :loanId,productId:productId,optModule:optModule},
            success: function (response) {
            	location.reload();
                $('#showTree').tree('reload');
            },
            error: function (msg) {
                alert('删除失败!');
            }
        });
    }
}