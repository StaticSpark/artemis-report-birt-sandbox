$(document).ready(function() {
	json = $("#json").html();
	data = eval('(' + json + ')');
	mcBase = data.mcBase;
	ctx = $("#ctx").val();
	$("#chart").css("display","none");

	refreshReport();
	$("#search").click(function(){
		refreshReport();
	});
	
	//并发报告分布图
	$("#periodchart").show();
	$("#timeswitch_id").css("background-color", "#7A991A");
	$("#timechart").hide();
	//指定时段分布图
	$("#periodswich_id").live("click",function() {
		$(this).css("background-color", "#F8F8FF");	
		$("#timeswitch_id").css("background-color", "#7A991A");	
		$("#timechart").hide();
		$("#periodchart").show();
	});
	//小时分布图
	$("#timeswitch_id").live("click",function() {
		$(this).css("background-color", "#F8F8FF");
		$("#periodswich_id").css("background-color", "#7A991A");
		$("#timechart").show();
		$("#periodchart").hide();
	});
	
	$("#uvRef").click(function(){switchReport('uv');});
	$("#ipRef").click(function(){switchReport('ip');});
	
	var rptType = $("#rptType").val();
	if(rptType == "uv")
	{
		$("#reportType").html("UV并发报告");
		$("#ipName").hide();
		$("#uvName").show();
		$("#uvRef").css("color", "#E3170D");
		$("#ipRef").css("color", "#0000FF");
		$("#rptType").val("uv");
	}
	else if(rptType == "ip")
	{
		$("#reportType").html("IP并发报告");
		$("#uvName").hide();
		$("#ipName").show();
		$("#ipRef").css("color", "#E3170D");
		$("#uvRef").css("color", "#0000FF");
		$("#rptType").val("ip");
	}
	
	//分布图与报表实现转换
	new concurrentSwitchLocation("report", "chart");	
});

/**
 * 初始化日期
 * @param date
 */
function initDate (date) {
	var yesterdayEndTime = genrateParam("yesterday")[1];
	if(date == "" || date == "null" || date == null) {
		yesterdayEndTime = yesterdayEndTime.substring(0, yesterdayEndTime.indexOf(" "));
		$("#startDate").val(yesterdayEndTime);
		$("input[value='查询']").attr("disabled", "");
		$("input[value='查询']").css("color", "gray");
	} else if(checkEndTime(yesterdayEndTime, date)){
		yesterdayEndTime = yesterdayEndTime.substring(0, yesterdayEndTime.indexOf(" "));
		$("#startDate").val(yesterdayStartTime);
	} else {
		$("#startDate").val(date);
	}
}
//刷新报表
function refreshReport(type) {
	initializeBasicInfo();
	if (reportName == "concurrent_report" ){
		type = "uv";
		initDate(data.uvMaxDate);
	}else{
		type = "ip";
		initDate(data.ipMaxDate);
	}	
	var startDate = $("#startDate").val();
	//默认日期 
	var id= mcBase.id;
	var monName = mcBase.name;
	
	src = ctx + "/frameset?__report=persistence/mc/" + reportName + ".rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+
		id + "&monDate="+startDate + "&monName="+monName + "&recordNum=30&ctx=" + ctx;

	$("#report").attr("src", src);
	
	
	var param = "monId="+mcBase.id+ "&monDate="+startDate +"&type="+type;	
	refreshChart(param,type);	
}

/**
 * 并发报告分布图
 * @param param
 * @param type
 */
function refreshChart(param,type) {
	var title = "UV并发-指定时间段分布图";
	var title1 = "UV并发-小时分布图";
	var defaultColumn = "1+UV,2+UV,3+UV";
	var period_cookie = "cocurrent_uv_period_cookie";
	var time_cookie = "cocurrent_uv_time_cookie";
	if (type == "ip") {
		title = "IP并发-指定时间段分布图";
		title1 = "IP并发-小时分布图";
		defaultColumn = "1+IP,2+IP,3+IP";
		period_cookie = "cocurrent_ip_period_cookie";
		time_cookie = "cocurrent_ip_time_cookie";
	}
	
	var periodsubTitle = "";
	var timesubTitle = "";
	var periodxAxis = null;
    periodSeries = null;
	var timeAxis = null;
    timeSeries = null;
	
	// ajax后台返回数据
	$.ajax({
		type: "post",
		async:false,
		dataType:'json',
		url: ctx + "/mc/concurrent-report-chart?"+param,
		success:function(data) {		
			if (data && data.lenght !=0) {
				periodxAxis = data.periodXAxis;
				periodSeries = data.periodSeries;
				timeAxis = data.timexAxis;
				timeSeries = data.timeSeries;
			}		
		}
	});
	
	if (timeAxis.length == 0) {
		timesubTitle = "小时分布无数据!";	
	}else if (periodxAxis.length == 0) {
		periodsubTitle = "指定时段分布无数据!";
	}
	
	//设置并发图标的全局属性
	Highcharts.setOptions({
		lang: {
	        resetZoom : "重置缩放"     	
	        },
		chart: {
        	borderColor: '',
        	borderRadius: 2,
            borderWidth: 0.5,    	
            zoomType: 'xy',
            type: 'column',
            style: {
            	fontFamily: '微软雅黑' // default font
            }
        },
        title:{
        	style: {
                color: '#3E576F',
                fontWeight: 'bold',
                fontSize: '14px'
            }
        },
        subtitle:{
        	style:{
        		color: 'red',
        		fontSize:'13px'
        	}
        },
        yAxis: {
            title:{
            	text:''
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            draggable: true,
            borderWidth: 0,
            borderRadius: 0,
            useHTML:true,
            navigation : {
            	animation :true,
            },
            title: {
                text: '',
                fontWeight: 'normal'
            }
        },
        credits : {
        	text : ''
        },
       /* plotOptions: {
            series: {
            	visible:false
            }
        },*/
	});
	
	var chartWidth = $("#report").width()*1.02 ;
	var periodChartOptions = { 
	        chart:{
	        	renderTo: 'periodchart',
	        	width :chartWidth,
	        },
			title: {
	            text: title          
	        },
	        subtitle:{
	        	text: periodsubTitle,        	
	        },
	        tooltip: {        	
	            borderRadius: 3,
	            borderWidth: 1,
	            formatter: function() {	        			        		  
	        		var dataList =  periodSeries[0].data;
	        		var nameList = periodxAxis;   
	        		var s = '<b>'+this.x+'<b>';
	                $.each(this.points, function(i, point) {              	
	                     var index = null;
	                	 for (var i = 0;i<nameList.length;i++) {
	                    	if (nameList[i] == this.x) {
	                    		 index = i;
	                    	}
	                     }
	                	 var currentUV1 = dataList[index];  
	                	 if (currentUV1 != 0 && currentUV1 != '-'){
	                		 percent = point.y/currentUV1;
	                	 }
	                     
	                	 s += '<br/>'+'<span '+'style="color:'+point.series.color+'"'+'>'+point.series.name +':</span>'+
                         '<b>'+point.y +'</b>';     	
	                	 percent = percent * 100;
	                	 result = changeTwoDecimal_f(percent);
	                	 if (result == 100) {	 
	                	 }else {
	                		 s += ' ( '+result + "%";
		                	 s += ' )'; 
	                	 }
	                	 
	                 });         
	        		 return s;    
	            },  
	            shared:true
	        },
	        
	        xAxis: {
	            categories: periodxAxis
	        },              
	        series: periodSeries,        
	    };
	//设置cookie
	new ChartCookie(period_cookie, defaultColumn, periodChartOptions);
	var periodChart = new Highcharts.Chart(periodChartOptions);

	
	$("#timechart").css("width",chartWidth+"px");
	//得到小时x轴的记录数
	var count = timeAxis.length;	
	var timeWidth = getChartWidth(count);	
	var timeChartOption = {
	        chart: {
	            renderTo: 'timechart',
	            width :timeWidth,
	            height:400
	        },
	        title: {
	            text: title1
	        },
	        subtitle:{
	        	text: timesubTitle
	        },
	        tooltip: {        	
	            borderRadius: 3,
	            borderWidth: 1,
	        	formatter: function() {	        			        		  
	        		var dataList =  timeSeries[0].data;
	        		var nameList = timeAxis;   
	        		var s = '<b>'+this.x+'<b>';
	                $.each(this.points, function(i, point) {              	
	                     var index = null;
	                	 for (var i = 0;i<nameList.length;i++) {
	                    	if (nameList[i] == this.x) {
	                    		 index = i;
	                    	}
	                     }
	                	 var currentUV1 = dataList[index];  
	                	 if (currentUV1 != 0 && currentUV1 != '-'){
	                		 percent = point.y/currentUV1;
	                	 }
	                     
	                	 s += '<br/>'+'<span '+'style="color:'+point.series.color+'"'+'>'+point.series.name +':</span>'+
	                         '<b>'+point.y +'</b>';           	
	                	 percent = percent * 100;
	                	 result = changeTwoDecimal_f(percent);
	                	 if (result == 100) {	 
	                	 }else {
	                		 s += ' ( '+result + "%";
		                	 s += ' )'; 
	                	 }
	                 });   
	        		 return s;    
	            },  
	           shared:true
	        },
	        xAxis: {
	            categories: timeAxis,
	            labels: {
	            	rotation :325
	            }
	        },
	        yAxis: {
	            title:{
	            	text:''
	            }
	        },  
	        series: timeSeries
	    };
	//设置cookie
	new ChartCookie(time_cookie,defaultColumn,timeChartOption);
	var timeChart = new Highcharts.Chart(timeChartOption);
	
}
/**
 * 获取图表宽度
 * @returns {Number}
 */
function getChartWidth(count) {
	var recordNum = count;
	var chartWidth = $("#report").width();
	if(recordNum > 8) {
		chartWidth = chartWidth + (recordNum - 8) * 0.01 * chartWidth;
		$("#timechart").css("overflow-x","scroll");
		$("#timechart").css("overflow-y","hidden")
	} 
	return chartWidth;
}
/**
 * 保留两位小数的百分数
 * @param x
 * @returns
 */
function changeTwoDecimal_f(x)  {  
	var f_x = parseFloat(x);   
	var f_x = Math.round(x*100)/100;  
	var s_x = f_x.toString();  
	var pos_decimal = s_x.indexOf('.');  
	
	if (pos_decimal < 0) {  
		pos_decimal = s_x.length;  
		s_x += '.';  
	}  
	while (s_x.length <= pos_decimal + 2) {  
		s_x += '0';  
	} 	
	return s_x;  
} 
//初始化基础信息
function initializeBasicInfo() {
    $("#mcName").html(mcBase.name);
	    
   //点击开始时间 
	$("#startDate").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd',
			alwaysUseStartDate : false
		});
	});
	
	var rptType = $("#rptType").val();
	
	//切换报告的列名
	if(rptType == "uv")
	{
		colArray = [{"index":"1", "columnDisplayName":"1+UV"},
		            {"index":"2", "columnDisplayName":"2+UV"},
		            {"index":"3", "columnDisplayName":"3+UV"},
		            {"index":"4", "columnDisplayName":"4+UV"},
		            {"index":"5", "columnDisplayName":"5+UV"},
		            {"index":"6", "columnDisplayName":"6+UV"},
		            {"index":"7", "columnDisplayName":"7+UV"},
		            {"index":"8", "columnDisplayName":"8+UV"},
		            {"index":"9", "columnDisplayName":"9+UV"},
		            {"index":"10", "columnDisplayName":"10+UV"}];
		reportName = "concurrent_report";
	}
	else if(rptType == "ip")
	{
		colArray = [{"index":"1", "columnDisplayName":"1+IP"},
		            {"index":"2", "columnDisplayName":"2+IP"},
		            {"index":"3", "columnDisplayName":"3+IP"},
		            {"index":"4", "columnDisplayName":"4+IP"},
		            {"index":"5", "columnDisplayName":"5+IP"},
		            {"index":"6", "columnDisplayName":"6+IP"},
		            {"index":"7", "columnDisplayName":"7+IP"},
		            {"index":"8", "columnDisplayName":"8+IP"},
		            {"index":"9", "columnDisplayName":"9+IP"},
		            {"index":"10", "columnDisplayName":"10+IP"}];
		reportName = "concurrent_base_ip_report";
	}

	//链接到其他的报告类型
	//$("#view_imp").attr("href", ctx + "/mc/imp-report?id=" + mcBase.id);
	//$("#view_uv_day").attr("href", ctx + "/mc/uv-report-day?id=" + mcBase.id);
}
function switchReport(rpt)
{
	var rptType = $("#rptType").val();
	if(rpt == rptType)
		return;
	if(rpt == "uv")
	{
		$("#reportType").html("UV并发报告");
		$("#ipName").hide();
		$("#uvName").show();
		$("#uvRef").css("color", "#E3170D");
		$("#ipRef").css("color", "#0000FF");
		$("#rptType").val("uv");
	}
	else if(rpt == "ip")
	{
		$("#reportType").html("IP并发报告");
		$("#uvName").hide();
		$("#ipName").show();
		$("#ipRef").css("color", "#E3170D");
		$("#uvRef").css("color", "#0000FF");
		$("#rptType").val("ip");
	}
	refreshReport();	
}
