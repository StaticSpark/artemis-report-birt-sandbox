
function getAreaName(areaId) {
	alert(areaId);
	var ctx = $("#ctx").val();
	$.ajax({
		type: "get",
		dataType:"text",
		async:false,
		url: ctx + "/mc/mc-area?areaId=" +areaId,
		success:function(data) {
			return data;
		}
	});
}

//打开子报表
function openSubReport(monId, areaId, monDate, path, maxImpFwd, hour, x, y) {
	y = parseInt(y) + 363;
	y = y + 'px';
	var width = document.body.clientWidth;
	var windowparam = 'height=100,width='+width+',top='+y+',left=20,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no,location=no,menubar=no,titlebar=no';
	var reportParam =  '&monId=' + monId + '&areaId=' + areaId + '&monDate=' + monDate + '&path=' + path + '&maxImpFwd=' + maxImpFwd;
	var url = ctx +'/frameset?__report=persistence/mc/mc_imp_sub_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__navigationbar=false&__toolbar=false' +
	           reportParam;
	if(hour > -1) {
		url = ctx + '/frameset?__report=persistence/mc/mc_imp_hour_sub_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__navigationbar=false&__toolbar=false' +
        reportParam + '&hour=' + hour;
	}
	window.open (url,'播控子路径报告',windowparam);
}

/**
 * 更新imp报告查询时间
 */
function updateReportTime() {
	var yesterdayStartTime = genrateParam("yesterday")[0];
	var yesterdayEndTime = genrateParam("yesterday")[1];
	if(data.maxDate == null || data.maxDate == "null") {
		yesterdayStartTime = yesterdayStartTime.substring(0, yesterdayStartTime.indexOf(" "));
		yesterdayEndTime = yesterdayEndTime.substring(0, yesterdayEndTime.indexOf(" "));
		$("#startDate").val(yesterdayStartTime);
		$("#endDate").val(yesterdayStartTime);
		$("input[value='查询']").attr("disabled", "");
		$("input[value='查询']").css("color", "gray");
	} else if(checkEndTime(yesterdayEndTime, data.maxDate)) {
		yesterdayStartTime = yesterdayStartTime.substring(0, yesterdayStartTime.indexOf(" "));
		yesterdayEndTime = yesterdayEndTime.substring(0, yesterdayEndTime.indexOf(" "));
		$("#startDate").val(yesterdayStartTime);
		$("#endDate").val(yesterdayEndTime);
		if(reportName == "mc_imp_sum_report") {
			$("#startDate").val(data.minDate);
		}
	} else {
		$("#startDate").val(data.maxDate);
		$("#endDate").val(data.maxDate);
		if(reportName == "mc_imp_sum_report") {
			$("#startDate").val(data.minDate);
		}
	}
}