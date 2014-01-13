$(document).ready(function(){
	json = $("#json").html();
	data = eval('(' + json + ')');
	ctx = $("#ctx").val();
	id = data.mcBase.id;
	name = data.mcBase.name;
	areaName = data.area.name;
	areaId = data.area.id;
	monDate = data.monDate;
	uvLimit = data.uvLimit;
	uvSn = data.uvSn;
	type = data.type;
	period = data.period;
	initializeBasicInfo();
	refreshReport();
});

//初始化基础信息
function initializeBasicInfo() {
	$("#name").html(name);
	$("#areaName").html(areaName);
	$("#monDate").html(monDate);
	$("#uvLimit").html(uvLimit);
	$("#uvSn").html(uvSn);
	$("#type").html(type);
	$("#period").html(period);
	
	$("#view_imp").attr("href", ctx + "/mc/imp-report?id=" + id);
	colArray = [{"index":"2", "columnDisplayName":"UV1"},
	            {"index":"3", "columnDisplayName":"UV2"},
	            {"index":"4", "columnDisplayName":"UV3"},
	            {"index":"5", "columnDisplayName":"UV4"},
	            {"index":"6", "columnDisplayName":"UV5"},
	            {"index":"7", "columnDisplayName":"UV6"},
	            {"index":"8", "columnDisplayName":"UV7"},
	            {"index":"9", "columnDisplayName":"UV8"},
	            {"index":"10", "columnDisplayName":"UV9"},
	            {"index":"11", "columnDisplayName":"UV10+"}];
}

//刷新报表
function refreshReport() {
	var src = ctx + "/frameset?__report=persistence/mc/mc_uv_hour_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+id 
	    +"&monDate=" + monDate + "&areaId=" + areaId+"&recordNum=30";
	var chartFrame = "<iframe class='iframe_class' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' "+
           "style='margin-bottom:300px;width: 100%; height:535px;min-height: 402px; border: none;' src='"+src+"' id='report'></iframe>";
    $("#report").replaceWith(chartFrame);
}