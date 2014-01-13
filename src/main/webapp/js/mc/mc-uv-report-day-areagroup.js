$(document).ready(function() {
	json = $("#json").html();
	data = eval('(' + json + ')');
	mcBase = data.mcBase;
	dimArea = data.dimArea;
	monDate = data.monDate;
	ctx = $("#ctx").val();
	
	initializeBasicInfo();
	refreshReport();
});

//刷新报表
function refreshReport() {
	var areaGroupId= dimArea.id;
	var monId = mcBase.id;
	var monName = mcBase.name;
	var src = $("#report").attr("src");
	if(src.length > 0) {
		src = updateUrlParam(src, "monId", monId);
		src = updateUrlParam(src, "areaGroupId", areaGroupId);
	} else {
		src = ctx + "/frameset?__report=persistence/mc/mc_uv_day_areagroup_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+monId+"&areaGroupId="+
		areaGroupId + "&monDate=" + monDate + "&monName=" + monName + "&areaGroupName=" + dimArea.name;
	}
	$("#report").attr("src", src);
}

//初始化基础信息
function initializeBasicInfo() {
    $("#mcName").html(mcBase.name);
    $("#areaGroupName").html(dimArea.name);
    $("#monDate").html(monDate);
    
	$("#view_imp").attr("href", ctx + "/mc/imp-report?id=" + mcBase.id);
	$("#view_uv").attr("href", ctx + "/mc/uv-report?id=" + mcBase.id);
	colArray = [{"index":"2", "columnDisplayName":"1+UV"},
	            {"index":"3", "columnDisplayName":"UV1"},
	            {"index":"4", "columnDisplayName":"UV2"},
	            {"index":"5", "columnDisplayName":"UV3"},
	            {"index":"6", "columnDisplayName":"UV4"},
	            {"index":"7", "columnDisplayName":"UV5"},
	            {"index":"8", "columnDisplayName":"UV6"},
	            {"index":"9", "columnDisplayName":"UV7"},
	            {"index":"10", "columnDisplayName":"UV8"},
	            {"index":"11", "columnDisplayName":"UV9"},
	            {"index":"12", "columnDisplayName":"UV10+"}];
	xPox = -70;
	yPox = 300;
}

//获取上下文
function getCtx() {
	return ctx;
}
