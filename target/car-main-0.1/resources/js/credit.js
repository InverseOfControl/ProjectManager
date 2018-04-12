;var seloanContract_plugin = function  ($) {


    var methods = {
        init: function (pageData, nav) {           
        

            $(this).find('.newSeloanBrowse').hide();
            $(this).find('.oldSeloanBrowse').show();
            // renderOtherField(pageData,'auditLoanBasicInfo');

            
            return this;
        }
    };
    
    $.fn.seLoanContract = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance_detail' );
        }
    };
};
seloanContract_plugin(jQuery);

;var wsLoanContract_plugin = function  ($) {
    function initialize (pageData,self) {
        

        $(self).find('.newSeloanBrowse').show();
        $(self).find('.oldSeloanBrowse').hide();
        renderOtherField(pageData,'loanBrowseTab');

    };

    var methods = {
        init: function (pageData, nav) {           
            initialize(pageData,this);
            return this;
        }
    };

    $.fn.wsLoanContract = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.instance_detail' );
        }
    };
};
wsLoanContract_plugin(jQuery);


;function getYMD(datetime){
    if(datetime==''||typeof(datetime) =="undefined"){
        return '';
    }
    return datetime.substr(0, 10);
};
;function formatEnumName(value,type){
   var enumJsondata = eval("("+enumJson+")");   
   try{
		var typeList = enumJsondata.dicData[type];
		if(typeList){
			for(var i = 0; i < typeList.length; i++){
				if(value == typeList[i].enumCode){
					return typeList[i].enumValue;
				}
			}
			return "";
		}else{
			return "";
		}
	}catch(e){
		alert("不存在数据字典对象!");
	}
}

function setFirstPage(ids){
    var opts = $(ids).datagrid('options');
    var pager = $(ids).datagrid('getPager');
    opts.pageNumber = 1;
    opts.pageSize = opts.pageSize;
    pager.pagination('refresh',{
	pageNumber:1,
	pageSize:opts.pageSize
    });
}

function getDialogFileUrl(loanDetailsVo,module){
	if(module == 'BusinessLoanDetail'){
		if ('5' == loanDetailsVo.product.id || loanDetailsVo.product.id == '6'){
			return 'after/businessAccount/toCityWidBusinessLoanDetail'
		}
		else if ('7' == loanDetailsVo.product.id){
			return 'after/businessAccount/towsLoanDetail'
			
		}
		else if('8' == loanDetailsVo.product.id){
			return 'after/businessAccount/toEduLoanDetail'
		}
		else{
			return null;
		}
	}
}

//同城贷特有数据加载
function loadExistedCityWideLoan(loanDetailsVo,form){
    var productId;
    var posOpenDate;

    if (loanDetailsVo.product != undefined)  
      productId = loanDetailsVo.product.id  
    else  productId = loanDetailsVo.productId ;     
    productId == 5?$('#'+form+' #cityWideSeloanOwn').remove() : $('#'+form+' #cityWidePOSLoanOwn').remove()  ;
    
    posOpenDate =  $('#'+form+' #posOpenDate');

    $('#'+form+' #productNames').text(loanDetailsVo.product == undefined?loanDetailsVo.productName:loanDetailsVo.product.productName);
    
    if (loanDetailsVo.company) {
        if (posOpenDate.length > 0){
            $(posOpenDate).is('label') ? $(posOpenDate).text(getYMD(loanDetailsVo.company.posOpenDate)) : $(posOpenDate).datebox('setValue',getYMD(loanDetailsVo.company.posOpenDate));
            $('#'+form+' #posAcquirer').val(loanDetailsVo.company.posAcquirer).text(loanDetailsVo.company.posAcquirer);
            $('#'+form+' #posCapitavolume').val(loanDetailsVo.company.posCapitavolume).text(loanDetailsVo.company.posCapitavolume);
        }else 
            $('#'+form+' #companyDebtBalance').val(loanDetailsVo.company.companyDebtBalance).text(loanDetailsVo.company.companyDebtBalance); 
            
        $('#'+form+' #monthTurnOver').val(loanDetailsVo.company.monthTurnOver).text(loanDetailsVo.company.monthTurnOver);
    }

    if (loanDetailsVo.loan) 
    $('#'+form+' #sourceOfRepay').val(loanDetailsVo.loan.sourceOfRepay).text(loanDetailsVo.loan.sourceOfRepay);   
    
    if (loanDetailsVo.person) {
        $('#'+form+' #placeDomicile').val(loanDetailsVo.person.placeDomicile).text(loanDetailsVo.person.placeDomicile);
        $('#'+form+' #ratioOfInvestments').val(loanDetailsVo.person.ratioOfInvestments).text(loanDetailsVo.person.ratioOfInvestments);
        $('#'+form+' #monthOfProfit').val(loanDetailsVo.person.monthOfProfit).text(loanDetailsVo.person.monthOfProfit);
        $('#'+form+' #monthRepayAmount').val(loanDetailsVo.person.monthRepayAmount).text(loanDetailsVo.person.monthRepayAmount);
        $('#'+form+' #personDebtBalance').val(loanDetailsVo.person.personDebtBalance).text(loanDetailsVo.person.personDebtBalance);
        
    };  

    

};

function renderGuaView (guaranteeBrowseTabId,guaranteeBrowsePanelTemplateId,loanDetails) {
	$('#'+guaranteeBrowseTabId+' > #'+guaranteeBrowsePanelTemplateId+' ~ div').remove();
    if(loanDetails.guaranteeList) {
        for(var i =0;i<loanDetails.guaranteeList.length;i++){
            var guarantee = loanDetails.guaranteeList[i];
            var guaranteeBrowsePanel =  $('#'+guaranteeBrowsePanelTemplateId).clone().show().addClass('easyui-panel');
            var guaranteeBrowsePanelId = "guaranteeBrowsePanel_" + i;                  
            guaranteeBrowsePanel.attr("id",guaranteeBrowsePanelId);
            guaranteeBrowsePanel.attr("title","担保人"+(i+1));
            if(loanDetails.guaranteeList[i].flag){
            	 guaranteeBrowsePanel.find('#flag').text("该担保人为指定担保人");
        	}
            if(guarantee.guaranteeType==0){//自然人
            	 guaranteeBrowsePanel.find('#guaranteeName').text(guarantee.name);
                 guaranteeBrowsePanel.find('#guaranteeType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
                 guaranteeBrowsePanel.find('#guaranteeIdnum').text(transferUndefined(guarantee.idnum));
                 guaranteeBrowsePanel.find('#guaranteeSex').text(transferUndefined(formatSex(guarantee.sex)));
                 guaranteeBrowsePanel.find('#guaranteeMarried').text(transferUndefined(formatMarried(guarantee.married)));
                 guaranteeBrowsePanel.find('#guaranteeEducationLevel').text(transferUndefined(guarantee.educationLevel));
                 guaranteeBrowsePanel.find('#guaranteeHasChildren').text(transferUndefined(formatYes(guarantee.hasChildren)));
                 guaranteeBrowsePanel.find('#guaranteeAddress').text(transferUndefined(guarantee.address));
                 guaranteeBrowsePanel.find('#guaranteeMobilePhone').text(transferUndefined(guarantee.mobilePhone));
                 guaranteeBrowsePanel.find('#guaranteeEmail').text(transferUndefined(guarantee.email));
                 guaranteeBrowsePanel.find('#personHomePhone').text(transferUndefined(guarantee.homePhone));
                 guaranteeBrowsePanel.find('#guaranteeCompanyFullName').text(transferUndefined(guarantee.companyFullName));
                 guaranteeBrowsePanel.find('#guaranteeZipCode').text(transferUndefined(guarantee.zipCode));
                 guaranteeBrowsePanel.find('#guaranteeCompanyAddress').text(transferUndefined(guarantee.companyAddress));
                 guaranteeBrowsePanel.find('#guaranteeCompanyPhone').text(transferUndefined(guarantee.companyPhone));
                
                guaranteeBrowsePanel.find('#tr7').hide();
                guaranteeBrowsePanel.find('#tr8').hide();
               
            }else if(guarantee.guaranteeType==1){//法人
            	 guaranteeBrowsePanel.find('#tr1').hide();
                 guaranteeBrowsePanel.find('#tr2').hide();
                 guaranteeBrowsePanel.find('#tr3').hide();
                 guaranteeBrowsePanel.find('#tr4').hide();
                 guaranteeBrowsePanel.find('#tr5').hide();
                 guaranteeBrowsePanel.find('#tr6').hide();                      
            	 guaranteeBrowsePanel.find('#tr7').show();
                 guaranteeBrowsePanel.find('#tr8').show(); 
                 guaranteeBrowsePanel.find('#guaType').text(transferUndefined(formatGuaranteeType(guarantee.guaranteeType)));
                 guaranteeBrowsePanel.find('#guaCompanyFullName').text(transferUndefined(guarantee.companyFullName));
                 guaranteeBrowsePanel.find('#guaZipCode').text(transferUndefined(guarantee.zipCode));
                 guaranteeBrowsePanel.find('#guaCompanyAddress').text(transferUndefined(guarantee.companyAddress));
                 guaranteeBrowsePanel.find('#guaCompanyPhone').text(transferUndefined(guarantee.companyPhone));
            	
            }
           

            guaranteeBrowsePanel.appendTo($('#'+guaranteeBrowseTabId));
        }
    }
};
function renderOtherField(obj,form){
    $.each(obj,function(name,val){
        if(typeof(val)=="object" && Object.prototype.toString.call(obj) !== '[object Array]'){
            renderOtherField(val,form);
        }else{   
            var ele =  $('#'+form+' #'+name);        
            if($(ele).is('label')){
                $(ele).text(val);
            }else if($(ele).hasClass("easyui-combobox")){
                $(ele).combobox('select',val);
            }else if($(ele).hasClass("easyui-datebox")){
                $(ele).datebox("setValue", getYMD(val));

            }else{
                $(ele).val(val);
            }
        }
    })
};


function CurentTime(num)
{ 
	var preDate = new Date();
	var  now= new Date(preDate.getTime() +num*24*60*60*1000);
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   var hour= now.getHours(); //获取系统时，
   var mins=  now.getMinutes(); //分
   var sec=  now.getSeconds(); //秒
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
    
    if(hour < 10)
        clock += "0";
       
    clock += hour + ":";
    
    if(mins < 10)
        clock += "0";
       
    clock += mins + ":";
    
    if(sec < 10)
        clock += "0";
       
    clock += sec;
    

    return(clock); 
} 

function CurentTimeStand(num)
{ 
	var preDate = new Date();
	var  now= new Date(preDate.getTime() +num*24*60*60*1000);
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   var hour= "00"; //获取系统时，
   var mins=  "00"; //分
   var sec=   "00"; //秒
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
    
       
    clock += hour + ":";
    
       
    clock += mins + ":";
    
       
    clock += sec;
    

    return(clock); 
} 