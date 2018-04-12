var textResourceMap = new Dictionary();
$(function () { 

 

});
function getTextResource(arr){
	var textResourceJsonData=eval("("+textResourceJson+")");
	for(var i = 0; i < arr.length; i++){
		var typeList = textResourceJsonData.textJson[arr[i]];
		if(typeList){
			for(var n = 0; n < typeList.length; n++){
				textResourceMap.put(typeList[n].code+typeList[n].type, typeList[n].text);
			}
		}
	}
 
	
	 
} 
function Dictionary(){
	 this.data = new Array();
	 
	 this.put = function(key,value){
	  this.data[key] = value;
	 };

	 this.get = function(key){
	  return this.data[key];
	 };

	 this.remove = function(key){
	  this.data[key] = null;
	 };
	 
	 this.isEmpty = function(){
	  return this.data.length == 0;
	 };

	 this.size = function(){
	  return this.data.length;
	 };
	}

 
