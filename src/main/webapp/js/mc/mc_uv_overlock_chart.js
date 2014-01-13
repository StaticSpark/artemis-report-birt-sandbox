/**
 * 根据参数得到的数据
 */
var areaChartData = null;
var dateChartData = null;

/**
 * 超频uv地域分布图展示
 * @param param
 */
function refreshOverLockAreaChart(param) {
	var type = 'area';
	getChartDateByParam(type,param);
	setChartGlobalProp();
	if (areaChartData.areaxAxis.length != 0) {	
		setChartAreaProp();	
	}else {
		noData();
	}
}

/**
 * 超频uv日期分布图展示
 */
function refreshOverLockDateChart(param) {
	var type = 'date';
	getChartDateByParam(type,param);
	setChartGlobalProp();
	if (dateChartData.areaList.length != 0) {
		setAreaDisplay();
		var areaparam = "firstInitial";
		setChartDateProp(areaparam);
	}	
}

/**
 * 通过ajax传递参数
 * @param param
 */
function getChartDateByParam(type,param) {
	$.ajax({
		type: "post",
		async:false,
		dataType:'json',
		url: ctx + "/mc/mc-overlock-"+type+"-chart?"+param,
		success:function(data) {		
			if (data && data.lenght !=0) {
				if (type == 'area') {
					areaChartData = data;
				}
				if (type == 'date') {
					dateChartData = data;
				}		
			}		
		}
	});
}
/**
 * 当没有数据时显示
 */
function noData() {
	var noDataOptions = { 
	        chart:{
	        	renderTo: 'overlock_area'
	        },
			title: {
	            text: "投放周期UV-超频分布图"          
	        },
	        subtitle:{
	        	text: "无数据"       	
	        },
	        xAxis: {
	            categories: ""
	        },    
	        series: "",        
	    };
	var noDataChart = new Highcharts.Chart(noDataOptions);
}

/**
 * 设置地域分布图表的属性
 */
function setChartAreaProp() {
	var areaxAxis = areaChartData.areaxAxis;
	var seriesData = new Array();
	seriesData = areaChartData.series;
	var uvLimit = areaChartData.uvLimit;	
	var title = "投放周期UV-超频分布图";
	var defaultColumn = setDefaultUVLimit(uvLimit);	
	var chartWidth = getChartWidth(areaxAxis.length,'area');
	//默认显示该指标
	for (var i = 0;i<seriesData.length;i++) {
		if (seriesData[i].name == defaultColumn ){
			seriesData[i].visible = true;
		}
	}
	
	var overlockAreaChartOptions = { 
	        chart:{
	        	renderTo: 'overlock_area',
	        	type: 'column',
	        	witdh:chartWidth
	        },
			title: {
	            text: title          
	        },
	        subtitle:{
	        	text: "",        	
	        },
	        xAxis: {
	            categories: areaxAxis
	        },   
	        yAxis: {
	        	plotBands: [{
	                color: 'red',
	                width: 2,
	                value: 3,
	                label: {
	                    text: '3%',
	                    align: 'right',
	                    x: -10
	                }            
	            }]
	        },
	        series: seriesData,        
	    };
	//设置cookie
	//new ChartCookie("area_cookie", defaultColumn, overlockAreaChartOptions);
	__common.formateChart(overlockAreaChartOptions);
	var overlockAreaChart = new Highcharts.Chart(overlockAreaChartOptions);
}
/**
 * 根据uvlimit设置默认展示的占比
 * @param uvLimit
 * @returns
 */
function setDefaultUVLimit(uvLimit) {
	if (uvLimit.length == 1) {
		if (uvLimit[0] > 10) {
			defaultColumn = "10+UV占比";
		}else {
			if (uvLimit[0] == 0) {
				defaultColumn = (parseInt(uvLimit[1]) + 1) + "+UV占比";
			}else{
				defaultColumn = (parseInt(uvLimit[0]) + 1) + "+UV占比";
			}
			
		}
	}else {	
		if (uvLimit[0] > 10){
			defaultColumn = "10+UV占比";
		}else {
			if (uvLimit[0] == 0) {
				if (uvLimit[1] > 10){
					defaultColumn = "10+UV占比";
				}else {
					defaultColumn = (parseInt(uvLimit[1]) + 1) + "+UV占比";
				}		
			}else{
				defaultColumn = (parseInt(uvLimit[0]) + 1) + "+UV占比";
			}
		}
	}
	return defaultColumn;
}

//得到初试化时第一个地域
function getFirstArea(areaList) {
	var currentArea = null;
	if (areaList.length >1){
		if (areaList[0] != "") {
			currentArea = areaList[0];
		}else {
			currentArea = areaList[1];
		}
	}else {
		currentArea = areaList[0];
	}
	return currentArea;
}

function setAreaDisplay() {
	var areaList = dateChartData.areaList;	
	currentArea = getFirstArea(areaList);
	var areaArray = new Array();
	var allArea = "";
	if(areaList.length > 0) {
		for (var i=0;i<areaList.length;i++) {
			if (areaList[i] == "") {
				allArea = "全部地域";
			}else {
				areaArray.push(areaList[i]);		
			}
		}
		if (allArea == "全部地域") {
			areaArray.push(allArea);
		}
	}
	
	new UVOverLockChartSingleAreaSelect("area_table",areaArray);
}

/**
 * 设置日期分布图表的属性
 */
function setChartDateProp(areaparam) {
	
	if (areaparam != "firstInitial") {
		currentArea = areaparam;
	}
	if (areaparam == "全部地域") {
		currentArea = "";
	}

	var currentxAxis = dateChartData.dayAxiswithKey[currentArea];
	var currentSeris = dateChartData.daySeres[currentArea];
	var title = "投放周期UV-超频分布图";	
	var currentLimit = dateChartData.uvLimitwithKey[currentArea];
	
	if (areaparam == "全部地域") {
		currentLimit = dateChartData.uvLimitwithKey[getFirstArea(dateChartData.areaList)];
	}
	
	var reverseArray = new Array();
	for(var i = currentLimit.length-1;i>=0;i--) {
		reverseArray.push(currentLimit[i]);
	}	
	var defaultColumn = setDefaultUVLimit(reverseArray);
	for (var i = 0;i<currentSeris.length;i++) {
		if (currentSeris[i].name == defaultColumn ){
			currentSeris[i].visible = true;
		}
	}
	var chartWidth = getChartWidth(currentxAxis.length,'date');
	var overlockDateChartOptions = { 
	        chart:{
	        	renderTo: 'overlock_date',
	        	type: 'line',
	        	width: chartWidth
	        },
			title: {
	            text: title          
	        },
	        subtitle:{
	        	text: "",        	
	        },
	        xAxis: {
	            categories: currentxAxis
	        },  
	        yAxis: {
	        	plotBands: [{
	                color: 'red',
	                width: 2,
	                value: 3,
	                label: {
	                    text: '3%',
	                    align: 'right',
	                    x: -10
	                }            
	            }]
	        },
	       
	        series: currentSeris,        
	    };
	//设置cookie
	//new ChartCookie("date_cookie", defaultColumn, overlockDateChartOptions);
	var overlockDateChart = new Highcharts.Chart(overlockDateChartOptions);
}

/**
 * 设置图表的全局属性
 */
function setChartGlobalProp() {
	var chartWidth = $("#report").width();
	Highcharts.setOptions({
		lang: {
	        resetZoom : "重置缩放"     	
	        },
		chart: {
        	borderColor: '',
        	borderRadius: 2,
            borderWidth: 0.5,    	
            zoomType: 'xy',
            width:chartWidth,
            style: {
            	fontFamily: '微软雅黑' // default font
            }
        },
        tooltip:{
        	 pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>%<br/>',
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
        yAxis:{
        	min:0,
        	title:{
            	text:''
            },
        	labels: {
                formatter: function() {
                    return this.value +'%';
                }
            },
            
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
        plotOptions: {
            series: {
            	visible:false
            }
        },
	});
}
/**
 * 获取图表宽度
 * @returns {Number}
 */
function getChartWidth(count,type) {
	var recordNum = count;
	var chartWidth = $("#report").width();
	if(recordNum > 10) {
		chartWidth = chartWidth + (recordNum - 10) * 0.01 * chartWidth;
		$("#overlock_"+type).css("overflow-x","scroll");
		$("#overlock_"+type).css("overflow-y","hidden");
	} 
	return chartWidth;
}