xPox = 0;
yPox = 0;
reportName = "test";
function getArrayItemNum (ary){
	var res = [];   
	ary.sort();   
	for(var i = 0;i<ary.length;)   
	{   
	    
	 var count = 0;   
	 for(var j=i;j<ary.length;j++)   
	 {   
	        
	  if(ary[i] == ary[j])   
	  {   
	   count++;   
	  }   
	     
	 }   
	 res.push([ary[i],count]);   
	 i+=count;   
	} 
	return res;
}

Array.prototype.remove = function(from, to) {
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from : from;
return this.push.apply(this, rest);
};
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
	}
	return -1;
};

Array.prototype.removeElement = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

function updateTitle(title) {
	$("#subnav-strips").css({"background":"#F5FAFA", "margin-left":"3px", "text-align":"center"});
	$("#subnav-strips").html("<span class='sub-title'><b>"+title+"</b></span>");
}

//将字符串转换为日期，格式：12/31/2001
function parseDate(str){	
   var r = str.match(/^(\d{1,2})(\/)(\d{1,2})\2(\d{1,4})$/);
   if(r==null)
     return null; 
   return new Date(r[4], r[1]-1, r[3]);
}

//判断数据中是否包含某元素
function contains(array, element) {
	for(var i = 0; i < array.length; i++) {
		if(array[i]==element) return true;
	}
	return false;
} 

//判断某个时间段是否与给定的时间段列表重叠
function timePeriodOverlapping(existPeriods, startDate, endDate) {
	for(var i = 0; i < existPeriods.length; i++) {
		var ePeriod = existPeriods[i];
		//alert(ePeriod[0]+"="+ePeriod[1]+"="+startDate+"="+endDate);
		if(startDate >= ePeriod[0] && startDate <= ePeriod[1]) return true;
		if(endDate >= ePeriod[0] && endDate <= ePeriod[1]) return true;
	}
	return false;
}
//增加replaceAll方法
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
//判断某个时间段是否与给定的时间段列表重叠
function timePeriodOverlapping2(ePeriod, periods) {
		//alert(ePeriod[0]+"="+ePeriod[1]+"="+periods[0]+"="+periods[1]);
		var re1 = compareCalendar(periods[0],ePeriod[1]);
		var re2 = compareCalendar(periods[1],ePeriod[0]);
		//alert(re1+"="+re2);
		if(re1==-1) return false;
		if(re2==1) return false;
	return true;
}

//判断某个时间段是否与给定的时间段列表重叠
function timePeriodOverlappingUv(ePeriod, periods) {
		//alert(ePeriod[0]+"="+ePeriod[1]+"="+periods[0]+"="+periods[1]);
		var re3 = compareCalendar(periods[0],ePeriod[0]);
		var re4 = compareCalendar(periods[1],ePeriod[1]);
		if(re3==0&&re4==0){
			return false;
		}
		var re1 = compareCalendar(periods[0],ePeriod[1]);
		var re2 = compareCalendar(periods[1],ePeriod[0]);
		if(re1==-1) return false;
		if(re2==1) return false;
	return true;
}

//判断某个时间段是否与给定的时间段列表重叠
function timePUvIEqual(ePeriod, periods) {
		//alert(ePeriod[0]+"="+ePeriod[1]+"="+periods[0]+"="+periods[1]);
		var re3 = compareCalendar(periods[0],ePeriod[0]);
		var re4 = compareCalendar(periods[1],ePeriod[1]);
		if(re3==0&&re4==0){
			return false;
		}
	return true;
}

//比较日前大小   
function compareDate(checkStartDate, checkEndDate) {      
    var arys1= new Array();      
    var arys2= new Array();      
	if(checkStartDate != null && checkEndDate != null) {      
	    arys1=checkStartDate.split('-');      
	      var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);      
	    arys2=checkEndDate.split('-');      
	    var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);      
	if(sdate > edate) {      
	    //alert("日期开始时间大于结束时间");         
	    return false;         
	}  else {   
	    //alert("通过");   
	    return true;      
	    }   
    }      
}     
  
 //判断日期，时间大小   
function compareTime(startDate, endDate) {   
	 if (startDate.length > 0 && endDate.length > 0) {   
	    var startDateTemp = startDate.split(" ");   
	    var endDateTemp = endDate.split(" ");   
	                   
	    var arrStartDate = startDateTemp[0].split("-");   
	    var arrEndDate = endDateTemp[0].split("-");   
	  
	    var arrStartTime = startDateTemp[1].split(":");   
	    var arrEndTime = endDateTemp[1].split(":");   
		var allStartDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);   
		var allEndDate = new Date(arrEndDate[0], arrEndDate[1]-1, arrEndDate[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);   
		//alert("start"+arrStartDate[0]+"="+arrStartDate[1]+"="+arrStartDate[2]+"="+arrStartTime[0]+"="+arrStartTime[1]+"="+arrStartTime[2]+"br/ en="+arrEndDate[0]+"="+arrEndDate[1]+"="+arrEndDate[2]+"="+arrEndTime[0]+"="+arrEndTime[1]+"="+arrEndTime[2])
		//alert("++"+allStartDate+"  s="+allEndDate);                
		if (allStartDate.getTime() > allEndDate.getTime()) {   
		        //("startTime不能大于endTime，不能通过");   
		        return -1;   
		} else if(allStartDate.getTime() < allEndDate.getTime()){   
		    //("startTime小于endTime，所以通过了");   
		    return 1;   
	    }  else if(allStartDate.getTime() == allEndDate.getTime()){   
		   // alert("startTime等于endTime，所以通过了");   
		    return 0;   
	    }    
	} else {   
	    alert("时间不能为空");   
	    return false;   
      }   
}   
//比较日期，时间大小   
function compareCalendar(startDate, endDate) {   
	if (startDate.indexOf(" ") != -1 && endDate.indexOf(" ") != -1 ) {   
	    //包含时间，日期   
	       var f = compareTime(startDate, endDate);     
	       var fd = compareCalendarTime(startDate, endDate); 
	       if(fd){
	    	   return -1;
	       }
	       return f;
	} else {   
	    //不包含时间，只包含日期   
	    compareDate(startDate, endDate);   
    }   
}   

	function compareCalendarTime(d1,d2)
	{
		d1 = d1.substring(0,10);
		d2 = d2.substring(0,10);
	  return ((new Date(d1.replace(/-/g,"//"))) > (new Date(d2.replace(/-/g,"//"))));
	}



function isNull(str){
	if(str==''||str==null){
		return true;
	}else return false;
}
function digits(value){
	var pattern = /^[0-9]{1,}$/;
	return pattern.test(value);
}

function checkNumber(num){
	//var reg = new RegExp("^\+?[1-9][0-9]*$");
	var reg = /^[0-9]{1,15}$/;
	return reg.test(num);                    //true:正整数  false 不是正整数   
}

//显示异常信息 
function showError(exception){
	var msg = '发生异常：\n名称：' + exception.name;
	msg += '\n信息：' + exception.message;
	if(!(exception.lineNumber === undefined)){
		msg += '\n行号：' + exception.lineNumber;
	}
	alert(msg); 
}

/**
 * 修改 报告每页显示数量
 * @param num
 */
function changePagerecord(num) {
	var iframe = document.getElementsByTagName("iframe")[0];
	var url = updateUrlParam(iframe.src, "recordNum", num);
	num = parseInt(num);
	var height = iframe.style.height.substring(0, iframe.style.height.length - 2);
	height = parseInt(height);
	var nHeight = height + parseInt((num- 20) * 18);
	changeIframeHeight(nHeight);
	iframe.src = url;
}

function changeIframeHeight(height) {
	var iframe = document.getElementsByTagName("iframe")[0];
	iframe.style.height = height + 'px';
}

/**
 * 修改url 参数
 * @param url
 * @param param
 * @param paramVal
 * @returns
 */
function updateUrlParam(url, param, paramVal) {
	var array = new Array();
	array = url.split("&");
	var recordStr = "";
	for(var i = 0; i < array.length; i++) {
		if(array[i].indexOf(param) > -1) {
			recordStr = array[i];
		}
	}
	if(recordStr.length > 0) {
		var paramKeyValue = param +"=" + paramVal;
		if(recordStr != paramKeyValue) {
			if(recordStr == (param +"=*")) {
				url = url.substring(0, url.indexOf(recordStr)) + param +"=" + paramVal + url.substring(url.indexOf(recordStr) + recordStr.length, url.length);
			} else {
				url = url.replaceAll(recordStr, param +"=" + paramVal);
			}
		}
	} else {
		url = url + "&" + param + "=" + paramVal;
	}
	return url;
}

/**
 * 获取默认隐藏的列序号
 * @returns {String}
 */
function getDefaultHideColumns() {
	var hideColumns = "";
	for(var i = 0; i < colArray.length; i++) {
		if(colArray[i].isShow == "0") {
			hideColumns += colArray[i].index + ",";
		}
	}
	if(hideColumns.length > 0) {
		hideColumns = hideColumns.substring(0, hideColumns.length - 1);
	}
	return hideColumns;
}

//判断当前用户是否登录
function isLogin(){
	if(!ctx) {
		ctx = $("#ctx").val();
	}
	var result = "";
	$.ajax({
		type: "post",
		async:false,
		dataType:'text',
		url: ctx +"/is-login",
		success:function(data) {
			result = data;
		}
	});
//	if(result == 0){
//		alert("session失效，请重新登录");
//		window.location = ctx;
//	}
}

//检验开始时间是否小于结束时间 
function checkEndTime(startTime, endTime){  
  var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
  var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
  if(end<start){  
      return false;  
  }  
  return true;  
}

/**
 * Url规则
 * @param url
 * @returns
 */
function validateUrl(url){
	var	strRegex = "^("                                                          + 
					"((https|http|ftp|rtsp|mms)?://){1}"                                     + // protocal
					"(([0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+:)?"                          + // ftp-user
					"[0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+@)?"                            + // ftp-password
					"(([0-9]{1,3}\\.){3}[0-9]{1,3}"                                          + // IP
					"|"                                                                      + // or
					"([0-9a-zA-Z_!~\\*'\\(\\)\\-]+\\.)*"                                     + // domain
					"([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\."                         + // domain
					"[a-zA-Z]{2,6})"                                                         + // domain
					"(:[0-9]{1,4})?"                                                         + // port
					"((/?)|"                                                                 + // slash
					"(/[0-9a-zA-Z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#\\-\\[\\]\\|\\{\\}]+)+/?)" + // args
					")+$";
	//var reg = /^(http|https):\/\/[\w\$\-\.\+!\*;\/\?:@=&\|]{3,}$/
	var reg = new RegExp(strRegex);
	return  reg.test(url);
}