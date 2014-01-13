$(document).ready(function() {
	data = json;
	rptList = new Array();
	rptList = data.rptList;
	rptId = data.reportId;
	initializeBasicInfo();
	refreshReport();
});

//初始化基础信息
function initializeBasicInfo() {
	$("#mcName").html(data.mcName);
	if(data.hour && data.hour != "null" && data.hour != "") {
		$("#time_tag").html("<b style='float:left;'>时</b><b style='float:right;'>段：</b>");
	} else {
		$("#time_tag").html("<b style='float:left;'>日</b><b style='float:right;'>期：</b>");
	}
		
	//小计报告 传 startDate endDate  分天报告 传 monDate
	if(data.monDate != "null")
	{
		if(data.areaGroupName && data.areaGroupName.length > 0) {
			$("#areaName").html(data.areaGroupName + "--" +data.areaName);
		} else {
			$("#areaName").html(data.areaName);
		}
		
		$("#date").html(data.monDate);
		if(data.hour && data.hour != "null" && data.hour != "") {
			$("#hour").html(formateHour(data.hour));
		}	
		$("#tSum").hide();
		$("#mcSum").html("(分天)");
		$("#tNormal").show();
		$("#sourceType").html("(分天)");
	}
	else
	{
		
		$("#areaName").html(data.areaName);
		$("#startDate").html(data.startDate);
		$("#endDate").html(data.endDate);
		$("#tNormal").hide();
		$("#mcSum").html("(小计)");
		$("#tSum").show();
		$("#sourceType").html("(小计)");
	}
	if(data.path == "") {
		$("#path").html(" *");
	} else if(data.path == 0) {
		$("#path").html(" -");
	}else {
		$("#path").html(data.path);
	}

	for(var i = 0; i < rptList.length; i++) {
		if(rptList[i].id == data.reportId) {
			$("#rptList").append("<option selected=selected total="+rptList[i].totalVal+" rptId="+rptList[i].id+" value='"+rptList[i].rptName+"'>"+rptList[i].rptDisplayName+"</option>");
		} else {
			$("#rptList").append("<option total="+rptList[i].totalVal+" rptId="+rptList[i].id+" value='"+rptList[i].rptName+"'>"+rptList[i].rptDisplayName+"</option>");
		}
		
	}
	
	$("#rptList").change(function(){
		var total =  $("#rptList option:selected").attr("total");
		var rptDisplayName = $("#rptList option:selected").text();
		if(total <= 0) {
			hiAlert(rptDisplayName + "为 0 ");
			$("option[rptId="+rptId+"]").attr("selected", true);
			return;
		}
		rptId = $("#rptList option:selected").attr("rptId");
	});
	
	$("#search").click(function(){
		refreshReport();
	});
};

//刷新报告
function refreshReport() {
	var type = $("#rptList option:selected").val();
	var rptDisplayName = $("#rptList option:selected").text();
	var total =  $("#rptList option:selected").attr("total");
	var monName = data.mcName;
	var areaName = data.areaName;
	var timePoint = formateHour(data.hour);
	$("#totalValue").html(total);
	var src = "";
	if(data.monDate != "null")
		 src = ctx + "/frameset?__report=" + type + ".rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&"+
	      "monId=" + data.monId + "&monDate="+data.monDate+"&areaId="+data.areaId+"&path="+data.path+"&value="+total +"&rptName="+ type 
	      +"&hour=" + data.hour +"&rptDisplayName=" + rptDisplayName+"&monName="+monName+"&areaName="+areaName+"&timePoint="+timePoint;
	else
		 src = ctx + "/frameset?__report=" + type + ".rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&"+
	      "monId=" + data.monId + "&startDate="+data.startDate + "&endDate=" + data.endDate + "&areaId="+data.areaId+"&path="
	      +data.path+"&value="+total +"&rptName="+ type +"&rptDisplayName=" + rptDisplayName+"&monName="+monName+"&areaName="+areaName+"&timePoint="+timePoint;

    var chartFrame = "<iframe frameborder='0' marginwidth='0' marginheight='0' scrolling='no' "+
       "style='width: 100%; height:510px;min-height: 402px; border: none' src='"+src+"' id='report'></iframe>";
    $("#report").replaceWith(chartFrame);
};

//格式化小时, 比如 1 格式化成 1~2
function formateHour(hour) {
	hourVal = parseInt(hour);
	if(hourVal < 9) {
	    hour = '0' + hourVal;
	    hour = hour + ':00 ~ 0' + (hourVal + 1) + ":00";
	} else if(hourVal == 9)  {
		hour = '0' + hourVal + ":00 ~ ";
	    hour = hour + (hourVal + 1) + ":00";
	} else {
	 	hour = hourVal + ':00 ~ ' + (hourVal + 1) + ":00";
	}
	return hour;
}

