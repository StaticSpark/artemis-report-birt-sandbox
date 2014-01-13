/**
 * 交换位置组件
 */
var SwitchLocation = Common.extend({
	/**
	 * 最初起始位置
	 */
	baseTop : null,
	/**
	 * 报表id
	 */
	reportId : null,
	/**
	 * 图 id
	 */
	chartId : null,
	/**
	 * 切换开关是否可点击
	 */
	isSwitchCanClick : false,
	/**
	 * 交换2个 id  的位置
	 * @param reportId
	 * @param chartId
	 */
	constructor : function(reportId, chartId) {
		__switchLocation = this;
	   this.initUI(reportId, chartId);
	   this.initEvent();
	   this.disabledSwitchLoction();
	},
	/**
	 * 交换组件标志
	 */
	switchLocationIdentify : function () {
		
	},
	initUI : function (reportId, chartId) {
		this.isSwitchCanClick = false;
		this.reportId = reportId;
		this.chartId = chartId;
		this.baseTop =  $("#" + this.reportId).offset().top;
		$("#" + this.reportId).css("position", "absolute");
		$("#" + this.chartId).css("position", "absolute");
	},
	initEvent : function () {
		$("#switchLoction").live("click", function () {
			if (__switchLocation.isSwitchCanClick == false ){
				return ;
			}else {
				//初始化时的图标隐藏，当点击查询图表示，出现
				$("#chart").show();
				__switchLocation.refreshLocation();
			}
			
		});
		
		$("#search").live("click", function () {
			__switchLocation.disabledSwitchLoction();
		}); 
	},
	/**
	 * 刷新图表位置
	 */
	refreshLocation : function () {
		 var tag = $("#switchLoction").attr("firstElement");
	     if(tag == "report") {
	    	 this.showChartFirst();
	    	 $("#switchLoction").attr("firstElement", this.chartId);
	     } else {
	    	 this.showReportFirst();
	    	 $("#switchLoction").attr("firstElement", this.reportId);
	     }
	     this.refreshFootLoction();
	},
	refreshLocationNoChangedStatus : function () {
		 var tag = $("#switchLoction").attr("firstElement");
	     if(tag == "report") {
	    	this.showReportFirst();
	     } else {
	    	 this.showChartFirst();
	     }
	     this.refreshFootLoction();
	},

	/**
	 * 将报表显示在前面
	 */
	showReportFirst : function () {
		$("#" + this.reportId).animate({top : this.baseTop}, 500);
		$("#" + this.chartId).animate({top : this.baseTop + $("#" + this.reportId).height() + 10}, 500);
		$("#switchLoction").val("查看分布图");
	},
	/**
	 * 将图显示在前面
	 */
	showChartFirst : function () {
		$("#" + this.chartId).animate({top : this.baseTop}, 500);
		$("#" + this.reportId).animate({top : this.baseTop + $("#" + this.chartId).height() + 20}, 500);
		$("#switchLoction").val("查看报表");
	},
	/**
	 * 刷新页面foot位置
	 */
	refreshFootLoction : function () {
		$("#footer").css("position", "absolute");
		var top = this.baseTop + $("#" + this.reportId).height() + $("#" + this.chartId).height() + 120;
		var left = ($("body").width() - $("#footer").width()) / 2;
		
		if(top > $("#" + this.chartId).offset().top + 20 &&  top < $("body").height() - 90) {
		}
		
		$("#footer").css("top", top);
		$("#footer").css("left", left);
	},
	/**
	 * 禁用切换
	 */
	disabledSwitchLoction : function () {	
		$("#switchLoction").css("color", "gray");
		this.isSwitchCanClick = false;
	},
	/**
	 * 启用切换
	 */
	enableSwitchLoction : function () {
		$("#switchLoction").css("color", "#000000");
		this.isSwitchCanClick = true;	
	},
});