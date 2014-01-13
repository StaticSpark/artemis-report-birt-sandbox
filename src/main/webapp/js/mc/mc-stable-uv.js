$(document).ready(function() {

	data = json;
	monType = data.monType;
	monDate = data.monDate;
	
	initializeBasicInfo();
	initEvent();
	
	refreshReport();
	
});

//刷新报表
function refreshReport() {
	
	if(monType == "1")
	{
		$("#fwdRef").css("color", "#E3170D");
		$("#reqRef").css("color", "#0000FF");
	}
	else
	{
		$("#reqRef").css("color", "#E3170D");
		$("#fwdRef").css("color", "#0000FF");
	}
	
	monDate = $("#month").val();
	var uvSum = $("#uvSum").html();
	
	$.post(ctx + "/mc/ajax-stable-uv", 
			{'monType':monType, 'monDate':monDate}, 
			function (rspData){
				$("#uvSum").html(rspData.uvSum);
				uvSum = rspData.uvSum;
				
				var src = ctx + "/frameset?__report=persistence/mc/" + reportName + ".rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monType="
					+ monType + "&monDate="+monDate + "&uvSum="+ uvSum +"&recordNum=50&ctx=" + ctx;
				$("#report").attr("src", src);
			
			}, "json");
	
}

//初始化基础信息
function initializeBasicInfo() {
    $("#mcName").html("全部播控");
	
    $("#month").val(monDate);
	
	$("#reportType").html("稳定UV月度报告");
		
	reportName = "mc_stable_uv_report";
	
	colArray = [{"index":"2", "columnDisplayName":"UV1"},
	            {"index":"3", "columnDisplayName":"UV2"},
	            {"index":"4", "columnDisplayName":"UV3"},
	            {"index":"5", "columnDisplayName":"UV4"},
	            {"index":"6", "columnDisplayName":"UV5"},
	            {"index":"7", "columnDisplayName":"UV6"},
	            {"index":"8", "columnDisplayName":"UV7"},
	            {"index":"9", "columnDisplayName":"UV8"},
	            {"index":"10", "columnDisplayName":"UV9"},
	            {"index":"11", "columnDisplayName":"UV10"},
	            {"index":"12", "columnDisplayName":"UV11"},
	            {"index":"13", "columnDisplayName":"UV12"},
	            {"index":"14", "columnDisplayName":"UV13"},
	            {"index":"15", "columnDisplayName":"UV14"},
	            {"index":"16", "columnDisplayName":"UV15"},
	            {"index":"17", "columnDisplayName":"UV16"},
	            {"index":"18", "columnDisplayName":"UV17"},
	            {"index":"19", "columnDisplayName":"UV18"},
	            {"index":"20", "columnDisplayName":"UV19"},
	            {"index":"21", "columnDisplayName":"UV20"},
	            {"index":"22", "columnDisplayName":"UV21+"}];

	xPox = -80;
	yPox = 270;
	
}
//初始化事件绑定
function initEvent()
{
	$("#search").click(function(){
		refreshReport();
	});
	 
	//点击开始时间 
	$("#month").click(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM',
			alwaysUseStartDate : false
		});
	});	
	$("#reqRef").click(function(){monType="0"; refreshReport();});
	$("#fwdRef").click(function(){monType="1"; refreshReport();});
}



