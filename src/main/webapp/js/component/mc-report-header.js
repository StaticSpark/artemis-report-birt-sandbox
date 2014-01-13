$(document).ready(function() {
	__reportSwitch = new ReportSwitch();
});

var ReportSwitch = Common.extend({
	titles : null,
	title : null,
	reportType : null,
	constructor : function() {
		titles = {};
		this.dataPapare();
		this.initUI();
		this.initSwitch();
		this.initEvent();
	},
	initUI :function () {
		this.updateTitle(title);
		$("#reportType").html(title);
	},
	initSwitch : function () {
		var switchStr = "";
		for(var o in titles) {
			if(o.indexOf("source") == -1 && o.indexOf("areagroup") == -1 && o.indexOf("hour") == -1) {
				if(o == reportType) {
					switchStr += "<option selected='selected'  tag='" + o + "'>" + titles[o] + "</option>";
				} else {
					switchStr += "<option tag='" + o + "'>" + titles[o] + "</option>";
				}
			}
		}
		$("#switchReport").append(switchStr);
	},
	initEvent: function () {
		$("#switchReport").live("change", function(){
			url = location + ""; 
			var type =  $("#switchReport option:selected").attr("tag");
			url = url.substring(0, url.lastIndexOf("/") + 1) + type + url.substring(url.indexOf("?"), url.length);
			location = url;
		});
	},
	dataPapare :function () {
		titles["imp-report"] = "播放点击日期报告";
		titles["imp-source-report"] = "播放点击日期报告";
		titles["imp-report-day-areagroup"] = "播放点击日期报告";
		titles["uv-report-day-areagroup"] = "UV每日报告";
		titles["uv-report-areagroup"] = "投放周期UV报告";
		titles["imp-report-hour"] = "用户小时报告";
		titles["uv-report-day"] = "UV每日报告";
		titles["uv-report"] = "投放周期UV报告";
		titles["concurrent-report"] = "并发报告";
		titles["stable-uv"] = "稳定UV月度报告";
		url = location + ""; 
		reportType = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
		title = titles[reportType];
	},
	updateTitle :function (title) {
		$("#subnav-strips").css({"background":"#F5FAFA", "margin-left":"3px", "text-align":"center"});
		$("#subnav-strips").html("<span class='sub-title'><b>播控-"+title+"</b></span>");
	},
});