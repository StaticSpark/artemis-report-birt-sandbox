$(document).ready(function(){
	json = $("#json").html();
	data = eval('(' + json + ')');
	ctx = $("#ctx").val();
	id = data.mcBase.id;
	monName = data.mcBase.name;
	areaId = data.dimArea.id;
	areaName = data.dimArea.name;
	startDate = data.startDate;
	endDate = data.endDate;
	impLimit = data.impLimit;
	path = data.path;	
	refreshReport();
});

//刷新报表
function refreshReport() {
	initializeBasicInfo();
	//播控标题	
	var subTitle = "播控-用户小时报告";	
	$("#subnav-strips").html("<span class='sub-title'><b>"+subTitle+"</b></span>");
	src = ctx + "/frameset?__report=persistence/mc/mc_imp_sum_hour_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+id 
    +"&startDate=" + startDate +"&endDate=" + endDate +"&areaId="+areaId+"&path=" + path +"&ctx=" + ctx +"&monName="+monName+"&areaName="+areaName +"&recordNum=30"+"&impLimit="+impLimit;
	$("#report").attr("src", src);
}

//初始化基础信息
function initializeBasicInfo() {
	$("#subnav-strips").css({"background":"#ECF3FE", "margin-left":"3px", "text-align":"center"});
	$("#mc-header tr td b:contains(播控名称)").html("播 控 名 称：");
	$("#name").html(monName);	
	$("#areaName").html(areaName);
	$("#startDate").html(startDate);
	$("#endDate").html(endDate);
	
	if (impLimit == 'null' || impLimit == '0') {
		$("#impLimit").html('-');
	}else {
		$("#impLimit").html(impLimit);
	}
		
	if (path == 'null') {
		$("#path").html('*');
	}else {
		$("#path").html(path);
	}
	
	reportName = "mc_imp_sum_hour_report";	
	colArray = [{"index":"1", "columnDisplayName":"执行投放量"},
	        	{"index":"2", "columnDisplayName":"地域播放量"},
	        	{"index":"3", "columnDisplayName":"执行点击量"},
	        	{"index":"4", "columnDisplayName":"地域点击量"},
	        	{"index":"5", "columnDisplayName":"点击率"},
	        	{"index":"6", "columnDisplayName":"播放请求量"},
	        	{"index":"7", "columnDisplayName":"无效播放量"},
	          	{"index":"8", "columnDisplayName":"异常播放量"},
	          	{"index":"9", "columnDisplayName":"点击请求量"},
	          	{"index":"10", "columnDisplayName":"无效点击量"},
	          	{"index":"11", "columnDisplayName":"异常点击量"}];	          	
}
