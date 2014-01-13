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
		src = ctx + "/frameset?__report=persistence/mc/mc_uv_areagroup_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+monId+"&areaGroupId="+
		   areaGroupId + "&monDate=" + monDate + "&monName=" + monName+ "&areaGroupName=" + dimArea.name;
	}
	$("#report").attr("src", src);
}

//初始化基础信息
function initializeBasicInfo() {
    $("#mcName").html(mcBase.name);
    $("#areaGroupName").html(dimArea.name);
    $("#monDate").html(monDate);
    
	$("#view_imp").attr("href", ctx + "/mc/imp-report?id=" + mcBase.id);
	$("#view_uv_day").attr("href", ctx + "/mc/uv-report-day?id=" + mcBase.id);
	colArray = [{"index":"2", "columnDisplayName":"1+UV"},
	            {"index":"3", "columnDisplayName":"2+UV"},
	            {"index":"4", "columnDisplayName":"3+UV"},
	            {"index":"5", "columnDisplayName":"4+UV"},
	            {"index":"6", "columnDisplayName":"5+UV"},
	            {"index":"7", "columnDisplayName":"6+UV"},
	            {"index":"8", "columnDisplayName":"7+UV"},
	            {"index":"9", "columnDisplayName":"8+UV"},
	            {"index":"10", "columnDisplayName":"9+UV"},
	            {"index":"11", "columnDisplayName":"10+UV"}];
}

//获取上下文
function getCtx() {
	return ctx;
}
