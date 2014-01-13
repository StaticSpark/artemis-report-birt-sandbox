$(document).ready(function() {
	json = $("#json").html();
	data = eval('(' + json + ')');
	mcBase = data.mcBase;
	areaInfo = new Array();
	areaInfo = data.areaInfo;
	ctx = $("#ctx").val();
	$("#chart").hide();
	initializeBasicInfo();
	new McReport();
	refreshReport();
	new SwitchLocation("report", "chart");
});

//刷新报表
function refreshReport() {
	
	areaIds = $("#areaIds").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var id= mcBase.id;
	var monName = mcBase.name;
	
	 if(!checkEndTime(startDate, endDate)) {
		 hiAlert("开始时间不得小于结束时间!");
    	 return;
	 }
	 
	var src = ctx + "/frameset?__report=persistence/mc/mc_uv_day_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+
			id+"&startDate="+startDate+"&endDate="+endDate+"&areaIds="+areaIds + "&ctx=" + ctx+"&monName="+monName;
	var chartFrame = "<iframe class='iframe_class' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' "+
		       "style='width: 96%; height:435px; border: none;' src='"+src+"' id='report'></iframe>";
	//$("#report").replaceWith(chartFrame);
    $("#report").attr("src", src);
	new uvChart("daily-uv");
}

//初始化基础信息
function initializeBasicInfo() {
    $("#mcName").html(mcBase.name);
	var optionStr = "<option value=''></option>";
	$("#areaList").append(optionStr);
    for(var i = 0; i < areaInfo.length;  i++) {
    	optionStr = "<option value='"+areaInfo[i].id+"'>"+areaInfo[i].name+"</option>";
    	$("#areaList").append(optionStr);
    }
    
   //点击开始时间 
	$("#startDate").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd',
			alwaysUseStartDate : false
		});
	});
	//点击结束时间 
	$("#endDate").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd',
			alwaysUseStartDate : false
		});
	});
	
	//导航切换
	$("a[name='navBtn']").live("click", function(){
		var id = $(this).attr("id");
		if(id.indexOf("imp") > -1)
		{
			var rptName = $(this).attr("reportName");
			window.open(ctx + "/mc/imp-report?id=" + mcBase.id + "&reportName=" + rptName);
		}
		else if(id == "uvPeriod")
			window.open(ctx + "/mc/uv-report?id=" + mcBase.id);
		else if(id == "uvDay")
			window.open(ctx + "/mc/uv-report-day?id=" + mcBase.id);
	});
	//显示按钮
	$("#imp-btn").show();
	$("input[name='search']").click(function(){
		refreshReport();
	});
	colArray = [{"index":"4", "columnDisplayName":"1+UV"},
	            {"index":"5", "columnDisplayName":"UV1"},
	            {"index":"6", "columnDisplayName":"UV2"},
	            {"index":"7", "columnDisplayName":"UV3"},
	            {"index":"8", "columnDisplayName":"UV4"},
	            {"index":"9", "columnDisplayName":"UV5"},
	            {"index":"10", "columnDisplayName":"UV6"},
	            {"index":"11", "columnDisplayName":"UV7"},
	            {"index":"12", "columnDisplayName":"UV8"},
	            {"index":"13", "columnDisplayName":"UV9"},
	            {"index":"14", "columnDisplayName":"UV10+"}];
	yPox = 270;
	xPox = -70;
	updateReportTime();
}


//获取上下文
function getCtx() {
	return ctx;
}
