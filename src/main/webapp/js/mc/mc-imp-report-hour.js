$(document).ready(function(){
	json = $("#json").html();
	data = eval('(' + json + ')');
	ctx = $("#ctx").val();
	id = data.mcBase.id;
	name = data.mcBase.name;
	areaId = data.area.id;
	areaName = data.area.name;
	monDate = data.monDate;
	impLimit = data.impLimit;
	areaList = data.areaList;
	initializeBasicInfo();
	initEvent();
	refreshReport();
});

//初始化基础信息
function initializeBasicInfo() {
	$("#name").html(name);	
	if(data.isVisualArea == 1) 
		$("#areaName").html(data.areaGroupName + "--");
	else
		$("#areaName").html("");
	
	for(var i = 0; i < areaList.length; i++)
	{
		var slted = "";
		if(areaList[i].id == areaId)
			slted = "selected";
		
		var	opt = "<option value='"+ areaList[i].id +"' " + slted + ">" + areaList[i].name + "</option>";
		$("#sltAreaName").append(opt);
	}
	$("#date").val(monDate);
	$("#impLimit").html(impLimit);
	$("#mc-header tr td b:contains(播控名称)").html("播 控 名 称：");
	
	$("#view_uv").attr("href", ctx + "/mc/uv-report?id=" + data.mcBase.id);
	reportName = "mc_imp_hour_report";	
	colArray = [{"index":"7", "columnDisplayName":"执行投放量"},
	            {"index":"8", "columnDisplayName":"地域播放量"},
	            {"index":"9", "columnDisplayName":"播放比"},
	            {"index":"10", "columnDisplayName":"执行点击量"},
	            {"index":"11", "columnDisplayName":"地域点击量"},
	            {"index":"12", "columnDisplayName":"点击率"},
	            {"index":"13", "columnDisplayName":"播放请求量"},
	            {"index":"14", "columnDisplayName":"无效播放量"},
	            {"index":"15", "columnDisplayName":"异常播放量"},
	            {"index":"16", "columnDisplayName":"点击请求量"},
	            {"index":"17", "columnDisplayName":"无效点击量"},
	            {"index":"18", "columnDisplayName":"异常点击量"}];

	xPox = -80;
	yPox = 270;
	
	//初始化path信息
	initializePathInfo();
}

//初始化path信息
function initializePathInfo()
{
	$("#sltPath").html("");	
	var pathInfo = data.pathInfo;
	var slted = "";
	var opt = "";
	for(var i = 0; i < pathInfo.length; i++)
	{	
		if (pathInfo[i].path == 0)
			pathInfo[i].path = '-';
		
		if(pathInfo[i].path == data.path || (pathInfo[i].path == '-' && data.path == '0'))
		{
			slted = "selected";
		 	opt = "<option value='"+ pathInfo[i].clkCtrl +"' "+ slted +">" + pathInfo[i].path + "</option>";
		}
		else
			opt = "<option value='"+ pathInfo[i].clkCtrl +"'>" + pathInfo[i].path + "</option>";
				
		$("#sltPath").append(opt);
	}
	//path 没有匹配到	
	if(slted == "")
		$("#sltPath").prepend("<option value='' selected >*</option>");
	else
		$("#sltPath").prepend("<option value='' >*</option>");
}
function initEvent()
{
	$("#date").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd',
			alwaysUseStartDate : false
		});
	});	
	$("#search_btn").click(function()
	{
		refreshReport();
	});
	//初始化点击信息
	$("#timePre").click(function()
	{
		var dt = $("#date").val();
		dt = translateDay(dt, -1);
		$("#date").val(dt);
	});
	$("#timeLater").click(function()
	{
		var dt = $("#date").val();
		dt = translateDay(dt, 1);
		$("#date").val(dt);
	});
	//默认隐藏左右箭头
	$(".NavImg").hide();
	var tOut;
	$("#date").parent().mouseenter(function()
	{
		tOut = setTimeout(function(){$(".NavImg").show();}, 100);
	})
	.mouseleave(function()
	{
		clearTimeout(tOut);
		$(".NavImg").hide();
	});
	
//	$(".timeUp").click(function()
//	{
//		var dt = $("#date").val();
//		dt = translateDay(dt, -1);
//		$("#date").val(dt);
//	});
//	$(".timeDown").click(function()
//	{
//		var dt = $("#date").val();
//		dt = translateDay(dt, 1);
//		$("#date").val(dt);
//	});
}
//刷新报表
function refreshReport() 
{
	
	var selectPath = $("#sltPath :selected").text();
	var clkCtrl = $("#sltPath :selected").val();
	
	if(clkCtrl != null && clkCtrl != "")
	{
		$("#clkCtrl").html("<b>日点击指标</b>&nbsp; : &nbsp;" + clkCtrl);
		$("#clkCtrl").show();
	}
	else
		$("#clkCtrl").hide();
		
	if(selectPath == "*" || selectPath == undefined) {
		selectPath = "";
	}
	if(selectPath == "-") {
		selectPath = 0;
	}
	var monName = name;
	monDate = $("#date").val();
	areaId = $("#sltAreaName :selected").val();
	areaName = $("#sltAreaName :selected").text();
	
	var src = ctx + "/frameset?__report=persistence/mc/mc_imp_hour_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+id 
	    +"&monDate=" + monDate +"&areaId="+areaId+"&path=" + selectPath +"&ctx=" + ctx +"&isVisualArea=" + data.isVisualArea +"&areaGroupName=" + data.areaGroupName +"&recordNum=30"+"&monName="+monName
	    +"&areaName="+areaName+"&impLimit="+impLimit;
	
	$("#report").attr("src", src);
}
//增加日期, 指定 delta ——> [+|-]day 来添加天数
function translateDay(day, delta)
{
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

