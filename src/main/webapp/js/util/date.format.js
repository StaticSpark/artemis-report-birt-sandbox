// 一天的增量（毫秒数）
var ONE_DAY_INCREMENT = 86400000;

// 将日期类型变量格式化成 yyyy-MM-dd 形式
function formatDate(d){
	var text = d.getFullYear() + '-';
	var month = 1 + d.getMonth();
	if(month < 10){
		text += '0' + month + '-';
	}else{
		text += month + '-';
	}
	
	if(d.getDate() < 10){
		text += '0' + d.getDate();
	}else{
		text += d.getDate();
	}
	
	return text;
}

// 将一个毫秒数 (new Date().getTime()) 格式化成 yyyy-MM-dd 形式
function formatDateFromMillisecond(d){
	var tmp = new Date();
	tmp.setTime(d);
	return formatDate(tmp);
}

/**
 * 获取当前时间
 * @return
 */
function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ms = now.getMilliseconds();
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   
    if(hh < 10)
        clock += "0";
       
    clock += hh + ":";
    if (mm < 10) clock += '0'; 
    clock += mm; 
    clock = clock+":"+ms
    return(clock); 
} 
//获取指定的时间段
function genrateParam(timeId) {
	var params = new Array();
	var start;
	var end;
	if(timeId == "today") {  //今天
		var myDate = new Date();
		//start = myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate() + " 00:00:00";
		//end = myDate.getFullYear()  + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate() + " 23:59:59";
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
		start = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
		end = myDate.getFullYear()  + "-" + month + "-" + myDate.getDate() + " 23:59:59";
	} else if(timeId == "tomorrow") { //明天
		var myDate = new Date();
		myDate.setTime(myDate.getTime()+24*60*60*1000);
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
		start = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
		end = myDate.getFullYear()  + "-" + month + "-" + myDate.getDate() + " 23:59:59";
	} else if(timeId == "yesterday") { //昨天
		var myDate = new Date();
		myDate.setTime(myDate.getTime()-24*60*60*1000);
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day =  myDate.getDate();
	     if (day<10){
	    	 day = "0"+day;
		 }
		start = myDate.getFullYear() + "-" + month + "-" + day + " 00:00:00";
		end = myDate.getFullYear()  + "-" + month + "-" + day + " 23:59:59";
	} else if(timeId == "lastweek") { //上个星期
		 var today = new Date();
		 var _weekday = today.getDay();
	     if(_weekday == 0){_weekday = 7;}
		 var today2 = new Date();
	     today2.setDate(today2.getDate()-7);
	     var day1 = new Date(today2.getTime() + (1-_weekday) * 24 * 60 * 60 * 1000);
	     var day2 = new Date(today2.getTime() + (7-_weekday) * 24 * 60 * 60 * 1000); 
	     var month1 = day1.getMonth() + 1;
	     var month2 = day2.getMonth() + 1;
	     if (month1<10){
	    	 month1 = "0"+month1;
		 }
		if (month2<10){
			month2 = "0"+month2;
		 }
	     start = day1.getFullYear() + "-" + month1 + "-" + day1.getDate()  + " 00:00:00";
		 end = day2.getFullYear()  + "-" + month2 + "-" + day2.getDate() + " 23:59:59";
	}
	else if(timeId == "thisweek") { //本星期
		 var today = new Date();
		 var _weekday = today.getDay();
	     if(_weekday == 0){_weekday = 7;}
		 var today2 = new Date();
	     today2.setDate(today2.getDate());
	     var day1 = new Date(today2.getTime() + (1-_weekday) * 24 * 60 * 60 * 1000);
	     var day2 = new Date(today2.getTime() + (7-_weekday) * 24 * 60 * 60 * 1000); 
	     var month1 = day1.getMonth() + 1;
	     var month2 = day2.getMonth() + 1;
	     if (month1<10){
	    	 month1 = "0"+month1;
		 }
		if (month2<10){
			month2 = "0"+month2;
		 }
	     start = day1.getFullYear() + "-" + month1 + "-" + day1.getDate()  + " 00:00:00";
		 end = day2.getFullYear()  + "-" + month2 + "-" + day2.getDate() + " 23:59:59";
	}else if(timeId == "for7day") { //前七天
		var myDate1 = new Date();
		var myDate2 = new Date();
		myDate1.setTime(myDate1.getTime()-7*24*60*60*1000);
		myDate2.setTime(myDate2.getTime()-24*60*60*1000);
		var year = myDate1.getFullYear();
		var month = myDate1.getMonth() + 1;
		var year2 = myDate2.getFullYear();
		var month2 = myDate2.getMonth() + 1;
		if (month<10){
		       month = "0"+month;
		 }
		if (month2<10){
			month2 = "0"+month2;
		 }
		start = year + "-" + month+ "-" + myDate1.getDate()  + " 00:00:00";
		end = year2  + "-" + month2 + "-" + myDate2.getDate()  + " 23:59:59";
	} 
	else if(timeId == "thismonth") { //本月
		 var myDate = new Date();
		 var year = myDate.getFullYear();
		 var month = myDate.getMonth()+1;
		 if (month<10){
		       month = "0"+month;
		 }
		 start = year+"-"+month+"-"+"01" + " 00:00:00";
		 myDate = new Date(year,month,0);
		 end = year+"-"+ month+"-"+myDate.getDate() + " 23:59:59";
	} 
	else if(timeId == "lastmonth") { //上月
		 var myDate = new Date();
		 var year = myDate.getFullYear();
		 var month = myDate.getMonth();
		 if(month == 0) {
			 month = 12;
			 year = year - 1;
		 }
		 if (month<10){
		       month = "0"+month;
		 }
		 start = year+"-"+month+"-"+"01"  + " 00:00:00";
		 myDate = new Date(year,month,0);
		 end = year+"-"+ month+"-"+myDate.getDate()  + " 23:59:59";
	} 
	params[0] = start;
	params[1] = end;
	return params;
}

function getDaysBase(baseday, dayNum) {
	var params = new Array();
	var start;
	var end;
	var myDate1 = new Date();
	var myDate2 = new Date();
	myDate1.setTime(baseday.getTime()- dayNum*24*60*60*1000);
	myDate2.setTime(baseday.getTime()-60*60*1000);
	var year = myDate1.getFullYear();
	var month = myDate1.getMonth() + 1;
	var year2 = myDate2.getFullYear();
	var month2 = myDate2.getMonth() + 1;
	if (month<10){
	       month = "0"+month;
	 }
	if (month2<10){
		month2 = "0"+month2;
	 }
	start = year + "-" + month+ "-" + myDate1.getDate();
	end = year2  + "-" + month2 + "-" + myDate2.getDate();
	if(myDate1.getTime() <= myDate2.getTime()) {
		params[0] = start;
		params[1] = end;
	} else {
		params[0] = end;
		params[1] = start;
	}
	return params;
}

function genrateDefaultPubDate(time){
	var start;
	if(time=='today'){
		var myDate = new Date();
		myDate.setTime(myDate.getTime()+24*60*60*1000);
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	     start = myDate.getFullYear() + "-" + month + "-" + day+ " 00:00";
	}
	if(time == "day1") { //上月
		var myDate1 = new Date();
		myDate1.setTime(myDate1.getTime()+1*24*60*60*1000);
		var year = myDate1.getFullYear();
		var month = myDate1.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate1.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate1.getFullYear() + "-" + month + "-" + myDate1.getDate() + " 23:59:59";
		start = myDate1.getFullYear() + "-" + month + "-" + day+ " 23:59";
	} 
	if(time == "day30") { //上月
		var myDate1 = new Date();
		myDate1.setTime(myDate1.getTime()+30*24*60*60*1000);
		var year = myDate1.getFullYear();
		var month = myDate1.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate1.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate1.getFullYear() + "-" + month + "-" + myDate1.getDate() + " 23:59:59";
		start = myDate1.getFullYear() + "-" + month + "-" + day;
	} 
	return start;
}

function genrateDayN(time,type,n){
	var start;
	if(type=='dayN00'){
		var myDate = new Date(time);
		myDate.setTime(myDate.getTime()+Number(n)*24*60*60*1000);
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	     start = myDate.getFullYear() + "-" + month + "-" + day+ " 00:00";
	}
	if(type == "dayN59") { //上月
		var myDate1 = new Date(time);
		myDate1.setTime(myDate1.getTime()+Number(n)*24*60*60*1000);
		var year = myDate1.getFullYear();
		var month = myDate1.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate1.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate1.getFullYear() + "-" + month + "-" + myDate1.getDate() + " 23:59:59";
		start = myDate1.getFullYear() + "-" + month + "-" + day+ " 23:59";
	} 
	if(type=='dayN'){
		var myDate = new Date(time);
		myDate.setTime(myDate.getTime()+Number(n)*24*60*60*1000);
		var month = myDate.getMonth() + 1;
	     if (month<10){
	    	 month = "0"+month;
		 }
	     var day = myDate.getDate();
	     if(day<10){
	    	 day = "0"+day;
	     }
		//start = myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " 00:00:00";
	    // start = myDate.getFullYear() + "-" + month + "-" + day+ " 00:00";
	     start = myDate.getFullYear() + "-" + month + "-" + day;
	}
	return start;
}

String.prototype.toDate = function()
{
var temp = this.toString();

temp = temp.replace(/-/g, "/");

var date = new Date(Date.parse(temp));

return date;
} 