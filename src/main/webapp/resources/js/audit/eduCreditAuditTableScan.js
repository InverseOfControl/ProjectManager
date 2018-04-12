var seqToC=0;
var seqToP=0;
$().ready(function() {
	
	$('.commonCloseBut').click(function(){
		window.close();
	});

//	//开启表单验证
//    $('input.validateItem').validatebox({
//        required:true
//    });
//    //开启表单验证
//    $('select.validateItem').combo({
//        required:true
//        //multiple:true
//    }); 
//    $('textarea.validateItem').validatebox({
//        required:true
//    });

	$("#addToC").click(
					function() {
						seqToC+=1;
						var html = "" +
								"<tr id='trFC"+seqToC+"'><td class='tdblue'><label id='seqNameC"+seqToC+"'>对公贷流水"+seqToC+"</label></td>"+
									"<td><input  validType='moneyCheck'  id='seqOneC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='seqSecC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><input  validType='moneyCheck'  id='seqThrC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='seqFourC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><input  validType='moneyCheck'  id='seqFiveC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='seqSixC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='remarkDel(1,this)'>删除</a></td>"+
									"</tr>"+
									"<tr id='trSC"+seqToC+"'>"+
									"<td class='tdblue'><label id='monthAmountC"+seqToC+"'>月末余额"+seqToC+"</label></td>"+
									"<td><input  validType='moneyCheck'  id='monthAmountOneC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='monthAmountSecC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><input  validType='moneyCheck'  id='monthAmountThrC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='monthAmountFourC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><input  validType='moneyCheck'  id='monthAmountFiveC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td ><input  validType='moneyCheck'  id='monthAmountSixC"+seqToC+"'  type='number'"+
									"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,1)' oninput='calSumSeqNew(this,this.value,1)'/></td>"+
									"<td><input  validType='moneyCheck'  id='typeC"+seqToC+"' value='1' type='hidden'></td>"+
									"</tr>";
						$("#ToC").append(html);
						//开启表单验证
					    $('#ToC input.validateItem').validatebox({
					        required:true
					    });
	});

	$("#addToP").click(
					function() {
						//开启表单验证
						seqToP+=1;
						var html = "" +
						"<tr id='trFP"+seqToP+"'><td class='tdblue'><label id='seqNameP"+seqToP+"'>对私贷流水"+seqToP+"</label></td>"+
							"<td><input  validType='moneyCheck'  id='seqOneP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='seqSecP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><input  validType='moneyCheck'  id='seqThrP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='seqFourP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><input  validType='moneyCheck'  id='seqFiveP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='seqSixP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='remarkDel(0,this)'>删除</a></td>"+
							"</tr>"+
							"<tr id='trSP"+seqToP+"'>"+
							"<td class='tdblue'><label id='monthAmountP"+seqToP+"'>月末余额"+seqToP+"</label></td>"+
							"<td><input  validType='moneyCheck'  id='monthAmountOneP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='monthAmountSecP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><input  validType='moneyCheck'  id='monthAmountThrP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='monthAmountFourP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><input  validType='moneyCheck'  id='monthAmountFiveP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td ><input  validType='moneyCheck'  id='monthAmountSixP"+seqToP+"'  type='number'"+
							"	class='easyui-validatebox ' onpropertychange='calSumSeqNew(this,this.value,0)' oninput='calSumSeqNew(this,this.value,0)'/></td>"+
							"<td><input  validType='moneyCheck'  id='typeP"+seqToP+"' value='0' type='hidden'></td>"+
							"</tr>";
						$("#ToP").append(html);
					    $('#ToP input.validateItem').validatebox({
					        required:true
					    });
	});
	$("#nowDate").html(CurentTime());
	
	loadAuditTableToWindow(loanId);
	loadAuditTableListToWindow(loanId);
	
	$('#auditListForm').find('textarea').attr("disabled","disabled");
	$('#auditListForm').find('input').attr("disabled","disabled");
});




//往数据库插入对贷和对公的list
function toAddOrUpdate(){
    var list = [];
    for(var i=1;i<=seqToP;i++){
    	var seqOne=	$("#seqOneP"+i).val();
    	var seqSec=	$("#seqSecP"+i).val();
    	var seqThr=	$("#seqThrP"+i).val();
    	var seqFour	=$("#seqFourP"+i).val();
    	var seqFive	=$("#seqFiveP"+i).val();
    	var seqSix=	$("#seqSixP"+i).val();
    	var monthAmountOne=	$("#monthAmountOneP"+i).val();
    	var monthAmountSec=	$("#monthAmountSecP"+i).val();
    	var monthAmountThr=	$("#monthAmountThrP"+i).val();
    	var monthAmountFour	=$("#monthAmountFourP"+i).val();
    	var monthAmountFive	=$("#monthAmountFiveP"+i).val();
    	var monthAmountSix=	$("#monthAmountSixP"+i).val();
    	var type=$("#typeP"+i).val();
       	var id=$("#idP"+i).val();
    	var seqValue="{" ;
    	if(monthAmountOne!=null&&monthAmountOne!=''){
    		seqValue+='monthAmountOne:"'+monthAmountOne+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountOne:"'+monthAmountOne+'",';
    	}
    	if(monthAmountSec!=null&&monthAmountSec!=''){
    		seqValue+='monthAmountSec:"'+monthAmountSec+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountSec:"'+monthAmountSec+'",';
    	}
    	if(monthAmountThr!=null&&monthAmountThr!=''){
    		seqValue+='monthAmountThr:"'+monthAmountThr+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountThr:"'+monthAmountThr+'",';
    	}
    	if(monthAmountFour!=null&&monthAmountFour!=''){
    		seqValue+='monthAmountFour:"'+monthAmountFour+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountFour:"'+monthAmountFour+'",';
    	}
    	if(monthAmountFive!=null&&monthAmountFive!=''){
    		seqValue+='monthAmountFive:"'+monthAmountFive+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountFive:"'+monthAmountFive+'",';
    	}
    	if(monthAmountSix!=null&&monthAmountSix!=''){
    		seqValue+='monthAmountSix:"'+monthAmountSix+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountSix:"'+monthAmountSix+'",';
    	}
    	if(seqOne!=null&&seqOne!=''){
    		seqValue+='seqOne:"'+seqOne+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqOne:"'+seqOne+'",';
    	}
    	if(seqSec!=null&&seqSec!=''){
    		seqValue+='seqSec:"'+seqSec+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqSec:"'+seqSec+'",';
    	}
    	if(seqThr!=null&&seqThr!=''){
    		seqValue+='seqThr:"'+seqThr+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqThr:"'+seqThr+'",';
    	}
    	if(seqFour!=null&&seqFour!=''){
    		seqValue+='seqFour:"'+seqFour+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqFour:"'+seqFour+'",';
    	}
    	if(seqFive!=null&&seqFive!=''){
    		seqValue+='seqFive:"'+seqFive+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqFive:"'+seqFive+'",';
    	}
    	if(seqSix!=null&&seqSix!=''){
    		seqValue+='seqSix:"'+seqSix+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqSix:"'+seqSix+'",';
    	}
    	if(seqValue!='{'){
	    	if(id!=null){
	    		seqValue+='id:"'+id+'",';
	    	}
	    	if(loanId!=null){
	    		seqValue+='loanId:"'+loanId+'",';
	    	}
	    	if(type!=null){
	    		seqValue+='type:"'+type+'"';
	    	}
    	}
    	seqValue+='}';
    	list.push(seqValue);
    }
    for(var i=1;i<=seqToC;i++){
    	var seqOne=	$("#seqOneC"+i).val();
    	var seqSec=	$("#seqSecC"+i).val();
    	var seqThr=	$("#seqThrC"+i).val();
    	var seqFour	=$("#seqFourC"+i).val();
    	var seqFive	=$("#seqFiveC"+i).val();
    	var seqSix=	$("#seqSixC"+i).val();
    	var monthAmountOne=	$("#monthAmountOneC"+i).val();
    	var monthAmountSec=	$("#monthAmountSecC"+i).val();
    	var monthAmountThr=	$("#monthAmountThrC"+i).val();
    	var monthAmountFour	=$("#monthAmountFourC"+i).val();
    	var monthAmountFive	=$("#monthAmountFiveC"+i).val();
    	var monthAmountSix=	$("#monthAmountSixC"+i).val();
    	var id=$("#idC"+i).val();
    	var type=$("#typeC"+i).val();
    	var seqValue="{" ;
    	if(monthAmountOne!=null&&monthAmountOne!=''){
    		seqValue+='monthAmountOne:"'+monthAmountOne+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountOne:"'+monthAmountOne+'",';
    	}
    	if(monthAmountSec!=null&&monthAmountSec!=''){
    		seqValue+='monthAmountSec:"'+monthAmountSec+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountSec:"'+monthAmountSec+'",';
    	}
    	if(monthAmountThr!=null&&monthAmountThr!=''){
    		seqValue+='monthAmountThr:"'+monthAmountThr+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountThr:"'+monthAmountThr+'",';
    	}
    	if(monthAmountFour!=null&&monthAmountFour!=''){
    		seqValue+='monthAmountFour:"'+monthAmountFour+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountFour:"'+monthAmountFour+'",';
    	}
    	if(monthAmountFive!=null&&monthAmountFive!=''){
    		seqValue+='monthAmountFive:"'+monthAmountFive+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountFive:"'+monthAmountFive+'",';
    	}
    	if(monthAmountSix!=null&&monthAmountSix!=''){
    		seqValue+='monthAmountSix:"'+monthAmountSix+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='monthAmountSix:"'+monthAmountSix+'",';
    	}
    	if(seqOne!=null&&seqOne!=''){
    		seqValue+='seqOne:"'+seqOne+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqOne:"'+seqOne+'",';
    	}
    	if(seqSec!=null&&seqSec!=''){
    		seqValue+='seqSec:"'+seqSec+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqSec:"'+seqSec+'",';
    	}
    	if(seqThr!=null&&seqThr!=''){
    		seqValue+='seqThr:"'+seqThr+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqThr:"'+seqThr+'",';
    	}
    	if(seqFour!=null&&seqFour!=''){
    		seqValue+='seqFour:"'+seqFour+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqFour:"'+seqFour+'",';
    	}
    	if(seqFive!=null&&seqFive!=''){
    		seqValue+='seqFive:"'+seqFive+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqFive:"'+seqFive+'",';
    	}
    	if(seqSix!=null&&seqSix!=''){
    		seqValue+='seqSix:"'+seqSix+'",';
    	}else if(monthAmountOne!=null&&id!=null){
    		seqValue+='seqSix:"'+seqSix+'",';
    	}
    	if(seqValue!='{'){
	    	if(id!=null){
	    		seqValue+='id:"'+id+'",';
	    	}
	    	if(loanId!=null){
	    		seqValue+='loanId:"'+loanId+'",';
	    	}
	    	if(type!=null){
	    		seqValue+='type:"'+type+'"';
	    	}
    	}
    	seqValue+='}';
    	list.push(seqValue);
    }
    var jsonT = '['+list+']';
	$.ajax({
		url : 'audit/eduLoanList/saveAuditTableSeq',
		data : {
			auditTableList : jsonT						
		},
		dataType : "json",
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}
function calSumSeq(id,value,name){
	if(name==0){
		name="P";
	}else if(name==1){
		name="C";
	}
	var sumLabel=name+"Sum";
	var sumLabelId=name+id+"Sum";
	var sumSeq=Number($('#seqOne'+name+id).val())+Number($('#seqSec'+name+id).val())+Number($('#seqThr'+name+id).val())
			+Number($('#seqFour'+name+id).val())+Number($('#seqFive'+name+id).val())+Number($('#seqSix'+name+id).val());
	var avgSumSeq=(sumSeq/6).toFixed(2);
	var sumAmount=Number($('#monthAmountOne'+name+id).val())+Number($('#monthAmountSec'+name+id).val())+Number($('#monthAmountThr'+name+id).val())
			+Number($('#monthAmountFour'+name+id).val())+Number($('#monthAmountFive'+name+id).val())+Number($('#monthAmountSix'+name+id).val());
	var avgSumAmount=(sumAmount/6).toFixed(2);
	var seqName=$('#seqName'+name+id).text();
	var AmountName=$('#monthAmount'+name+id).text();
	var html="<label id="+sumLabelId+">"+seqName+"均:"+avgSumSeq+"  "+AmountName+"均:"+avgSumAmount+"</br></label>    ";
	if($("#"+sumLabelId+"").text()==""){
		$("#"+sumLabel+"").append(html);
	}else{
		$("#"+sumLabelId+"").html(""+seqName+"均:"+avgSumSeq+"  "+AmountName+"均:"+avgSumAmount+"</br>");
	}
};

function calSumSeqNew(newNameId,value,name){
	if(name==0){
		name="P";
	}else if(name==1){
		name="C";
	}
	var id=newNameId.parentNode.parentNode.id.substring(4);
	var sumLabel=name+"Sum";
	var sumLabelId=name+id+"Sum";
	var sumSeq=Number($('#seqOne'+name+id).val())+Number($('#seqSec'+name+id).val())+Number($('#seqThr'+name+id).val())
			+Number($('#seqFour'+name+id).val())+Number($('#seqFive'+name+id).val())+Number($('#seqSix'+name+id).val());
	var avgSumSeq=(sumSeq/6).toFixed(2);
	var sumAmount=Number($('#monthAmountOne'+name+id).val())+Number($('#monthAmountSec'+name+id).val())+Number($('#monthAmountThr'+name+id).val())
			+Number($('#monthAmountFour'+name+id).val())+Number($('#monthAmountFive'+name+id).val())+Number($('#monthAmountSix'+name+id).val());
	var avgSumAmount=(sumAmount/6).toFixed(2);
	var seqName=$('#seqName'+name+id).text();
	var AmountName=$('#monthAmount'+name+id).text();
	var html="<label id="+sumLabelId+">"+seqName+"均:"+avgSumSeq+"  "+AmountName+"均:"+avgSumAmount+"</br></label>    ";
	if($("#"+sumLabelId+"").text()==""){
		$("#"+sumLabel+"").append(html);
	}else{
		$("#"+sumLabelId+"").html(""+seqName+"均:"+avgSumSeq+"  "+AmountName+"均:"+avgSumAmount+"</br>");
	}
};

//加载审核信息表填充到表单
function loadAuditTableToWindow (loanId) {
	$.ajax({
		url : 'audit/eduLoanList/loadAuditTableInfo',
		data : {
			id : loanId						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				$('#auditListForm').form('load', result.auditTable);
				if(result.auditTable.rentDate!=null){
				$('#auditListForm').find('#rentDate').val(result.auditTable.rentDate.substring(0,10));
				}
				if(result.auditTable.compCreateDate!=null){
				$('#auditListForm').find('#foundedDate').val(result.auditTable.compCreateDate.substring(0,10));
				}
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//加载审核信息列到表单
function loadAuditTableListToWindow(loanId){
	$.ajax({
		url : 'audit/eduLoanList/loadAuditTableListInfo',
		data : {
			loanId : loanId						
		},
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				if(result.auditTableList!=null&&result.auditTableList.length!='0'){
					var list=result.auditTableList;
					var seqToC2=0;
					var seqToP2=0;
					for(var i=0;i<list.length;i++){
						if(result.auditTableList[i].type=='0'){
							seqToP2+=1;
							var html = "" +
							"<tr id='trFP"+seqToP2+"'><td class='tdblue'><label id='seqNameP"+seqToP2+"'>对私贷流水"+seqToP2+"</label></td>";
								html =html+"<td><input  validType='moneyCheck'  id='seqOneP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqOne!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqOne+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								
								html =html+"<td ><input  validType='moneyCheck'  id='seqSecP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqSec!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqSec+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='seqThrP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqThr!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqThr+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='seqFourP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqFour!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqFour+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='seqFiveP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqFive!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqFive+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='seqSixP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].seqSix!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqSix+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='idP"+seqToP2+"' type='hidden' value='"+result.auditTableList[i].id+"'/></td>"+
								"</tr>"+
								"<tr id='trSP"+seqToP2+"'>"+
								"<td class='tdblue'><label id='monthAmountP"+seqToP2+"'>月末余额"+seqToP2+"</label></td>";
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountOneP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountOne!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountOne+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountSecP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountSec!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountSec+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountThrP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountThr!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountThr+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountFourP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountFour!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountFour+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountFiveP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountFive!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountFive+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountSixP"+seqToP2+"'  type='number'";
								if(result.auditTableList[i].monthAmountSix!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountSix+"' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,0)'  oninput='calSumSeqNew(this,this.value,0)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='typeP"+seqToP2+"' value='0' type='hidden'></td>"+
								"</tr>";
							
							$("#ToP").append(html);
							calSumSeq(seqToP2,seqToP2,'0');
						}else if(result.auditTableList[i].type=='1'){
							seqToC2 +=1;
							var html = "" +
							"<tr id='trFC"+seqToC2+"'><td class='tdblue'><label id='seqNameC"+seqToC2+"'>对公贷流水"+seqToC2+"</label></td>";
								html =html+"<td><input  validType='moneyCheck'  id='seqOneC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqOne!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqOne+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								
								html =html+"<td ><input  validType='moneyCheck'  id='seqSecC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqSec!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqSec+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='seqThrC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqThr!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqThr+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='seqFourC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqFour!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqFour+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='seqFiveC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqFive!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqFive+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='seqSixC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].seqSix!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].seqSix+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='idC"+seqToC2+"' type='hidden' value='"+result.auditTableList[i].id+"'/></td>"+
								"</tr>"+
								"<tr id='trSC"+seqToC2+"'>"+
								"<td class='tdblue'><label id='monthAmountC"+seqToC2+"'>月末余额"+seqToC2+"</label></td>";
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountOneC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountOne!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountOne+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountSecC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountSec!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountSec+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountThrC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountThr!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountThr+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountFourC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountFour!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountFour+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='monthAmountFiveC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountFive!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountFive+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td ><input  validType='moneyCheck'  id='monthAmountSixC"+seqToC2+"'  type='number'";
								if(result.auditTableList[i].monthAmountSix!=null){
									html =html+"	class='easyui-validatebox ' value='"+result.auditTableList[i].monthAmountSix+"' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}else{
									html =html+"	class='easyui-validatebox ' value='' onpropertychange='calSumSeqNew(this,this.value,1)'  oninput='calSumSeqNew(this,this.value,1)'/></td>";
								}
								html =html+"<td><input  validType='moneyCheck'  id='typeC"+seqToC2+"' value='1' type='hidden'></td>"+
							"</tr>";
							$("#ToC").append(html);
							calSumSeq(seqToC2,seqToC2,'1');
						}
						seqToC=seqToC2;
						seqToP=seqToP2;
					}
					}
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
}

//审核表格删除功能
var delList=[];

function remarkDel(type,newNameId){
	if(type==0){
		type="P";
	}else if(type==1){
		type="C";
	}
	var nameId=newNameId.parentNode.parentNode.id.substring(4);
	var trf=$("#trF"+type+nameId);
	var trs=$("#trS"+type+nameId);

	var sumLabelId=type+nameId+"Sum";
	if($("#id"+type+nameId).val()!=null){
		var seqValue="{" ;
		seqValue+='id:"'+$("#id"+type+nameId).val()+'",';
		seqValue+='}';
		delList.push(seqValue);
	}
	$("#"+sumLabelId+"").remove();
	trf.remove();     
	trs.remove();
	
	if(type=="C"){
		var seqToCbak=0;
		seqToCbak=seqToC;
		for(var i=1;i<=seqToC;i++){
				if(i == nameId){
					seqToCbak--;
				}else if(i > nameId){
					var j=i-1;
					$("#trF"+type+i).attr("id","trF"+type+j);
					$("#trS"+type+i).attr("id","trS"+type+j);
			    	$("#seqOneC"+i).attr("id","seqOneC"+j);
			    	$("#seqSecC"+i).attr("id","seqSecC"+j);
			    	$("#seqThrC"+i).attr("id","seqThrC"+j);
			    	$("#seqFourC"+i).attr("id","seqFourC"+j);
			    	$("#seqFiveC"+i).attr("id","seqFiveC"+j);
			    	$("#seqSixC"+i).attr("id","seqSixC"+j);
			    	$("#monthAmountOneC"+i).attr("id","monthAmountOneC"+j);
			    	$("#monthAmountSecC"+i).attr("id","monthAmountSecC"+j);
			    	$("#monthAmountThrC"+i).attr("id","monthAmountThrC"+j);
			    	$("#monthAmountFourC"+i).attr("id","monthAmountFourC"+j);
			    	$("#monthAmountFiveC"+i).attr("id","monthAmountFiveC"+j);
			    	$("#monthAmountSixC"+i).attr("id","monthAmountSixC"+j);
			    	$("#idC"+i).attr("id","idC"+j);
			    	$("#typeC"+i).attr("id","typeC"+j);
					$("#seqNameC"+i).attr("id","seqNameC"+j);
					$("#seqNameC"+j).text("对公贷流水"+j);
					$("#monthAmountC"+i).attr("id","monthAmountC"+j);
					$("#monthAmountC"+j).text("月末余额"+j);
					var sumLabelId=type+i+"Sum";
					$("#"+sumLabelId+"").remove();
					calSumSeq(j,"",1);
				}else{
					calSumSeq(i,"",1);
				}
			}
			seqToC=seqToCbak;
	}else if(type=="P"){
		var seqToPbak=0;
		seqToPbak=seqToP;
		for(var i=1;i<=seqToP;i++){
				if(i == nameId){
					seqToPbak--;
				}else if(i > nameId){
					var j=i-1;
					$("#trF"+type+i).attr("id","trF"+type+j);
					$("#trS"+type+i).attr("id","trS"+type+j);
			    	$("#seqOneP"+i).attr("id","seqOneP"+j);
			    	$("#seqSecP"+i).attr("id","seqSecP"+j);
			    	$("#seqThrP"+i).attr("id","seqThrP"+j);
			    	$("#seqFourP"+i).attr("id","seqFourP"+j);
			    	$("#seqFiveP"+i).attr("id","seqFiveP"+j);
			    	$("#seqSixP"+i).attr("id","seqSixP"+j);
			    	$("#monthAmountOneP"+i).attr("id","monthAmountOneP"+j);
			    	$("#monthAmountSecP"+i).attr("id","monthAmountSecP"+j);
			    	$("#monthAmountThrP"+i).attr("id","monthAmountThrP"+j);
			    	$("#monthAmountFourP"+i).attr("id","monthAmountFourP"+j);
			    	$("#monthAmountFiveP"+i).attr("id","monthAmountFiveP"+j);
			    	$("#monthAmountSixP"+i).attr("id","monthAmountSixP"+j);
			    	$("#idP"+i).attr("id","idP"+j);
			    	$("#typeP"+i).attr("id","typeP"+j);
					$("#seqNameP"+i).attr("id","seqNameP"+j);
					$("#seqNameP"+j).text("对私贷流水"+j);
					$("#monthAmountP"+i).attr("id","monthAmountP"+j);
					$("#monthAmountP"+j).text("月末余额"+j);
					var sumLabelId=type+i+"Sum";
					$("#"+sumLabelId+"").remove();
					calSumSeq(j,"",0);
				}else{
					calSumSeq(i,"",0);
				}
			}
			seqToP=seqToPbak;
	}
	
	
}

function toDel(){
    var jsonT = '['+delList+']';
	$.ajax({
		url : 'audit/eduLoanList/delAuditTableSeq',
		data : {
			auditTableList : jsonT						
		},
		dataType : "json",
		type:'POST',
		success : function(result){
			if (result.isSuccess) {
				
			} else {
				$.messager.alert('操作提示', result.msg,'error');
			}
		},
		error:function(data){
			$.messager.alert('操作提示', 'error','error');
		}
	});
	
}

function CurentTime() {
	var now = new Date();

	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day;
	return (clock);
};

function AuditSubmitBt(){
//	if($('#rentDate').val()==null || $('#rentDate').val()=='' ){
//		parent.$.messager.show({
//			title : '提示',
//			msg : '单位租赁有效期未填!'
//		});
//		return;
//	}
//	if($('#foundedDate').val()==null || $('#foundedDate').val()=='' ){
//		parent.$.messager.show({
//			title : '提示',
//			msg : '公司成立时间未填!'
//		});
//		return;
//	}
	var formObj = $('#auditListForm');
	if (formObj.form("validate")) {
	$.messager.confirm("提示","是否提交审核列表",function(r){
		if(r){
				$.ajax({
					type : 'post',
					url : 'audit/eduLoanList/saveAuditTable',
					data : $('#auditListForm').serialize(),
					async : false,
					success : function(result) {
						if (result.isSuccess) {
							toAddOrUpdate();
							toDel();
							$.messager.alert('操作提示', result.msg,'提示',function(){
								window.close();
							});
						} else {
							var isErr = 'error';
						}
					}
				});
		}
	});
	}
}
$.extend($.fn.validatebox.defaults.rules, {
	//邮编校验
	zipCheck: {
        validator: function (value) {
            return /^[0-9]\d{5}$/.test(value);
        },
        message: '邮政编码不正确'
    },
    //地址校验
    addressCheck:{
          validator:function(value){
               var a1,a2,a3,a4, i,l;
               l=value.length-1;
               if(value!=""&&value!=undefined){
                   for(i=0;i<value.length;i++){
                       if(value[i]=="省"){
                           a1=i;
                       } else if(value[i]=="市"){
                           a2=i;
                       } else if(value[i]=="区"){
                           a3=i;
                       } else if(value[i]=="县"){
                           a4=i;
                       }
                   }
                   if(a1!=undefined){
                       if(a1<a2&&a2<a3||a2<a4){
                           if(a3==l||a4==l){
                              return false;
                           }else{
                               return true;
                           }
                       }else{
                           return false;
                       }
                   }else{
                       if(a2<a3||a2<a4){
                           if(a3==l||a4==l){
                                return false;
                           }else{
                               return true;
                           }
                       }else{
                           return false;
                       }
                   }

               }else{
                  return false;
               }
          },
          message:'地址不正确'
      },
      requestMoneyCheck:{
    	  validator: function (value) {
    		  if(value%1000!=0){
    			  false;
    		  }else{
    			  return true;
    		  }
             
          },
          message: '申请金额是一千的倍数'
      },
       requestMoneyCheckW:{
        validator: function (value) {
          if(value%10000!=0){
            false;
          }else{
            return true;
          }
             
          },
          message: '申请金额是一万的倍数'
      },
mobileCheck:{
    validator: function (value) {
        return /^(?:13\d|14\d|15\d|17\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
    },
    message: '手机号码不正确'
}, 
telCheck:{
    validator:function(value,param){
        return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
    },
    message:'电话号码不正确'
},
phoneCheck:{
    validator:function(value,param){
    	
        return (/^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value))||(/^(?:13\d|14\d|15\d|17\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value));
    },
    message:'联系电话不正确'
},
integerCheck:{
    validator:function(value){
        return /^[+]?[0-9]\d*$/.test(value);
    },
    message: '请输入整数'
},//金额校验
moneyCheck:{
    validator: function (value) {
        return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
     },
     message:'请输入正确的金额'
} ,
percentCheck:{
    validator: function (value) {
        return (/^(([1-9]\d*)|\d)(\.\d{1,4})?$/).test(value);
     },
     message:'请输入正确的比率'
} ,
//邮箱校验
emailCheck:{
	
	validator:function(value){
        return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value);
    },
    message: '请输入正确的邮箱地址'
} ,

//车牌校验
carNoCheck:{
    validator: function (value) {
        return (/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/).test(value);
     },
     message:'请输入正确的车牌号码'

},
CHSAndENCheck:{
    validator: function (value) {
        return ( /^[\u4e00-\u9fa5a-zA-Z]+$/).test(value);
     },
     message:'请输入正确的格式'
},//汉字校验
CHSCheck:{
    validator: function (value) {
        return ( /^[\u4e00-\u9fa5]+$/).test(value);
     },
     message:'请输入正确的中文格式'
},
idCheck:{
    validator:function(idCard){
    	 
    	    idCard = trim(idCard.replace(/(^\s*)|(\s*$)/g, ""));               //去掉字符串头尾空格                     
    	    if (idCard.length == 15) {   
    	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    	    } else if (idCard.length == 18) {   
    	        var a_idCard = idCard.split("");  
    	       
    	        // 得到身份证数组   
    	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
    	            return true;   
    	        }else {   
    	            return false;   
    	        }   
    	    } else {   
    	        return false;   
    	    }   
    },
    message:'请输入正确的身份证号'
  }

  });

