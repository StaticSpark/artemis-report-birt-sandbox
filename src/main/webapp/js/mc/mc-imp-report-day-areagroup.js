$(document).ready(function() {
	initializeBasicInfo();
	selectPath = data.path;
	refreshReport();
	__siftChart = new SiftChart(siftData);
	refreshChart();
});

/**
 * 刷新图表
 */
function refreshChart() {
	var selectedAreaIds = __siftChart.getSelectedIds()[0];
	var selectedQuotaNames = __siftChart.getSelectedIds()[1];
	var selectedAreaNames = __siftChart.getSelectedNames()[0];
	var selectedQuotaDisplayNames = __siftChart.getSelectedNames()[1];
	if(selectedAreaIds.length == 0 || selectedQuotaNames.length == 0) {
		 hiAlert("图表信息(地域/指标)选择不完整");
		 return;
	 }
	 var sift_chart_left =  __common.getChartWidth();
	 var chartSrc = ctx +"/frameset?__report=persistence/mc/mc_imp_chart.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__toolbar=false&__navigationbar=false" +
	 		"&areaIds="+selectedAreaIds.toString() +"&quotas="+selectedQuotaNames.toString()+"&areaNames=" + selectedAreaNames.toString() + "&quotaNames=" + 
	 		selectedQuotaDisplayNames.toString() + "&startDate=" + data.startDate + "&endDate=" + data.endDate + "&path=" + selectPath + "&id=" + data.monId + "&width=" + sift_chart_left+
	 		"&chartDimension=d2&isAreaGroup=1&isMcSumReport=1";
	 $("#chart").attr("src", chartSrc);
}

//初始化基础信息
function initializeBasicInfo() {
	$("#mcName").text(data.mcName);
	$("#areaName").text(data.areaName);
	
	pathList = new Array();
	pathList = data.pathList;

    if(data.path == null || data.path == "null") {
    	data.path = "*";
	}
 
	for(var i = 0; i < pathList.length; i++) {
		if(pathList[i] == data.path) {
			if (pathList[i] == 0) {
				pathList[i] = '-';
			}
			$("#path").append("<a style='margin-right: 10px;color:red;text-decoration:underline;' name='pathlink' href='javascript:void(0);'>" + pathList[i] + "</a>");			
		} else {		
			if (pathList[i] == 0) {
				pathList[i] = '-';
			}
			$("#path").append("<a style='margin-right: 10px;text-decoration:underline;' name='pathlink' href='javascript:void(0);'>" + pathList[i] + "</a>");		
		}
	}
	
	$("[name='pathlink']").live("click", function() {
		selectPath = $(this).text();
		if (selectPath == '-') {
			selectPath = 0;
		}
		$("[name='pathlink']").each(function(i){
			$(this).css("color", "black");
		});
		$(this).css("color","red");
		refreshReport();
		__siftChart.hideChartAndSift();
		refreshChart();
	});
	
	$("#refresh_chart").click(function() {
		refreshChart();
		$("#sift_chart").hide();
		$("#sift_icon").attr("tag", "hide");
	 });
	
	siftData = 
	    [
	      {
	    	  "title":"地域",
		      "data": data.areaList
		  },
		  {
	    	  "title":"指标",
		      "data":
		    	  [
		            {"id": "impFwd", "name": "执行投放量","selected" : "selected" },
		            {"id": "impChokeArea", "name": "地域播放量"},
		            {"id": "clkFwd", "name": "执行点击量"},
		            {"id": "clkChokeArea", "name": "地域点击量"},
		            {"id": "actImp", "name": "播放请求量"},
		            {"id": "impBlock", "name": "无效播放量" },
		            {"id": "impChokeSum", "name": "异常播放量" },
		            {"id": "actClk", "name": "点击请求量" },
		            {"id": "clkBlock", "name": "无效点击量" },
		            {"id": "clkChokeSum", "name": "异常点击量" }
		         ]
		  }
	    ];
	
	siftData[0].data.sort(function(a, b){return  a.name.localeCompare(b.name);});
	siftData[0].data[0].selected = "selected";
	$("#view_period_uv").attr("href", ctx + "/mc/uv-report?id=" + data.monId);
	$("#view_day_uv").attr("href", ctx + "/mc/uv-report-day?id=" + data.monId);
	
	colArray = [{"index":"5", "columnDisplayName":"path"},
	            {"index":"6", "columnDisplayName":"计划投放量"},
	          	{"index":"7", "columnDisplayName":"设定投放量"},
	          	{"index":"8", "columnDisplayName":"执行投放量"},
	          	{"index":"9", "columnDisplayName":"执行/计划"},
	          	{"index":"10", "columnDisplayName":"执行/设定"},
	          	{"index":"11", "columnDisplayName":"计划点击量"},
	          	{"index":"12", "columnDisplayName":"执行点击量"},
	          	{"index":"13", "columnDisplayName":"地域播放量"},
	          	{"index":"14", "columnDisplayName":"播放比"},
	          	{"index":"15", "columnDisplayName":"地域点击量"},
	          	{"index":"16", "columnDisplayName":"日点击指标"},
	          	{"index":"17", "columnDisplayName":"点击率"},
	          	{"index":"18", "columnDisplayName":"播放请求量"},
	          	{"index":"19", "columnDisplayName":"点击请求量"},
	          	{"index":"20", "isShow": "0", "columnDisplayName":"无效播放量"},
	          	{"index":"21", "isShow": "0", "columnDisplayName":"异常播放量"},
	          	{"index":"22", "isShow": "0", "columnDisplayName":"无效点击量"},
	          	{"index":"23", "isShow": "0", "columnDisplayName":"异常点击量"}];
	xPox = -80;
	yPox = 270;
};

function getChartWidth () {
	 var sift_chart_left =  $("#sift_chart").position().left - $("#chart").position().left;
	 return sift_chart_left;
}

//刷新报表
function refreshReport() {
	if(selectPath == null || selectPath == "null") {
		selectPath = "";
	}
	var monName = data.mcName;
	var src = $("#report").attr("src");
	if(src.length > 0) {
		src = updateUrlParam(src, "path", selectPath);
	} else {
		src = ctx + "/frameset?__report=persistence/mc/mc_imp_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+data.monId 
	    +"&startDate=" + data.startDate + "&endDate=" + data.endDate +"&areaId="+data.areaId+"&path=" + selectPath +"&ctx=" + ctx +"&isVisualArea=1" + "&areaGroupName=" + data.areaGroupName+ "&hideColumns=" + getDefaultHideColumns()
	    +"&monName="+monName;
	}
	$("#report").attr("src", src);
}