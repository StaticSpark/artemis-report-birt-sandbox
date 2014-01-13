$(document).ready(function() {
	json = $("#json").html();
	data = eval('(' + json + ')');
	mcBase = data.mcBase;
	areaInfo = new Array();
	areaInfo = data.areaInfo;
	ctx = $("#ctx").val();
	initializeBasicInfo();
    $("#chart").css("display","none");
	new McReport();
	refreshReport();

	//报表切换
	new uvSwitchLocation("report", "chart");
    //超频分布
    //初始化选择框,地域分布默认选中
    initSelectItem();
    report_convert_change();
    initDateEvent();
    dateChange();
    
	//报表切换样式
	chartStyle();
});

function chartStyle() {
	$("#uv_chart").show();
	$("#uvlockswitch_id").css("background-color", "#7A991A");
	$("#overlock_chart").hide();
	//点击uv分布图
	$("#uvswich_id").live("click",function() {
		$(this).css("background-color", "#F8F8FF");	
		$("#uvlockswitch_id").css("background-color", "#7A991A");	
		$("#overlock_chart").hide();
		$("#uv_chart").show();
		__switchLocation.refreshLocationNoChangedStatus();
	});
	//点击uv超频分布图
	$("#uvlockswitch_id").live("click",function() {
		$(this).css("background-color", "#F8F8FF");
		$("#uvswich_id").css("background-color", "#7A991A");
		$("#overlock_chart").show();
		$("#uv_chart").hide();
		__switchLocation.refreshLocationNoChangedStatus();
	});
}

function initSelectItem() {
	$("#area_id").attr("selected",true);
}

//刷新报表
function refreshReport() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var id= mcBase.id;
	var monName = mcBase.name;
	areaIds = $("#areaIds").val();
	
	if(!checkEndTime(startDate, endDate)) {
		 hiAlert("开始时间不得小于结束时间!");
    	 return;
	 }
	 
	 var src = $("#report").attr("src");
	 if(src.length > 0) {
		 src = updateUrlParam(src, "areaIds", areaIds);
		 src = updateUrlParam(src, "startDate", startDate);
		 src = updateUrlParam(src, "endDate", endDate);
	 } else {
		 src = ctx + "/frameset?__report=persistence/mc/mc_uv_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+
			id+"&startDate="+startDate+"&endDate="+endDate+"&areaIds="+areaIds + "&ctx=" + ctx+"&monName="+monName;
	 }
	 $("#report").attr("src", src);
	 //超频分布
	 $("#chartDate").val(endDate);
	 var param = "monId=" +id +"&startDate="+ startDate+ "&endDate="+endDate +"&areaId="+areaIds;
	 refreshOverLockAreaChart(param);
	 refreshOverLockDateChart(param);
     new uvChart("uv");
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
	//点击结束时间 
	$("#endDate").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd',
			alwaysUseStartDate : false
		});
	});
	
	$("#chartDate").click(function() {
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
	colArray = [{"index":"7", "columnDisplayName":"1+UV"},
	            {"index":"8", "columnDisplayName":"2+UV"},
	            {"index":"9", "columnDisplayName":"3+UV"},
	            {"index":"10", "columnDisplayName":"4+UV"},
	            {"index":"11", "columnDisplayName":"5+UV"},
	            {"index":"12", "columnDisplayName":"6+UV"},
	            {"index":"13", "columnDisplayName":"7+UV"},
	            {"index":"14", "columnDisplayName":"8+UV"},
	            {"index":"15", "columnDisplayName":"9+UV"},
	            {"index":"16", "columnDisplayName":"10+UV"}];
	xPox = -70;
	yPox = 280;
	updateReportTime();
	
}

//获取上下文
function getCtx() {
	return ctx;
}

function report_convert_change() {
	//控制地域分布与日期分布的展现与隐藏
	$("#rpt_id").change(function(){
		var currentValue = $(this).children('option:selected').val();
		if (currentValue == "1") {
			$("#overlock_date_chart").css("display","none");
			$("#overlock_area_chart").removeAttr("style");
		}else{
			$("#overlock_area_chart").css("display","none");
			$("#overlock_date_chart").removeAttr("style");
		}
	});
}

function dateChange() {
	//当点击图表的日期时，实现同步刷新
	$("#chartDate").blur(function(){
		latterDate = $("#chartDate").val();
		setTimeout('displayDate()','100');
	});
}

function displayDate() {
	lastDate = $("#chartDate").val();
	if (isRationalDate(lastDate)) {
		if (latterDate == lastDate) {	
		}else{
			var param = "monId=" +mcBase.id +"&startDate="+ lastDate+ "&endDate="+lastDate +"&areaId="+$("#areaIds").val();
			refreshOverLockAreaChart(param);
		}
	}else {
		$("#chartDate").val(latterDate);
		hiAlert("所选日期不在查询日期范围内");	
	}
}

function isRationalDate(currentDate) {	
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var flag = true;
	if (currentDate > endDate || currentDate < startDate) {
		flag = false;
	}
	return flag;
}
//初始化点击信息
function initDateEvent() {
	$("#timePre").click(function() {
		var dt = $("#chartDate").val();
		dt = translateDay(dt, -1);
		if (isRationalDate(dt)) {
			$("#chartDate").val(dt);
			var param = "monId=" +mcBase.id +"&startDate="+ dt+ "&endDate="+dt +"&areaId="+$("#areaIds").val();
			refreshOverLockAreaChart(param);
		}else{
			hiAlert("所选日期不在查询日期范围内");
		}		
	});
	$("#timeLater").click(function() {
		var dt = $("#chartDate").val();
		dt = translateDay(dt, 1);	
		if (isRationalDate(dt)) {
			$("#chartDate").val(dt);
			var param = "monId=" +mcBase.id +"&startDate="+ dt+ "&endDate="+dt +"&areaId="+$("#areaIds").val();
			refreshOverLockAreaChart(param);
		}else {
			hiAlert("所选日期不在查询日期范围内");
		}
	});
}
	
//增加日期, 指定 delta ——> [+|-]day 来添加天数
function translateDay(day, delta) {
	var dtArr = day.split("-");
	var yyyy = parseInt(dtArr[0]);
	var mm = parseInt(dtArr[1]) - 1;
	var dd = parseInt(dtArr[2]);
	var nDate = new Date();
	nDate.setFullYear(yyyy, mm, dd);
	nDate.setTime(nDate.getTime() + delta * 24 * 3600 * 1000);
	
	yyyy = nDate.getFullYear();
	mm = nDate.getMonth() + 1;
	dd = nDate.getDate();
	if(mm < 10)
		mm = "0" + mm;
	if(dd < 10)
		dd = "0" + dd;
	
	return yyyy + "-" + mm + "-" + dd; 
}

function areaRadioChange() {
	$("input[name='overlockarea']").live("click",function() {
		var clickArea = $(this).next("span").html();
		setChartDateProp(clickArea);
	});
}
