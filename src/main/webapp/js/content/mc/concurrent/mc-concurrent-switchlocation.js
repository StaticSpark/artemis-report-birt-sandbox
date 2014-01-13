/**
 * 并发报告交换位置组件
 */
var concurrentSwitchLocation = SwitchLocation.extend({
	constructor : function(reportId, chartId) {
		this.base(reportId, chartId);
		__mc_concurrent_switchLocation = this;
	},
	/**
	 * 启用切换
	 */
	enableSwitchLoction : function () {
		if (periodSeries[0].data.length == 0 && timeSeries[0].data.length == 0) {
			$("#chart").hide();
			 var tag = $("#switchLoction").attr("firstElement");
		     if(tag == "report") {
		    	 //不做什么操作
		     } else {
		    	 this.showReportFirst();
		    	 $("#switchLoction").attr("firstElement", this.reportId);
		     }
		     this.refreshFootLoction();
		}else {
			$("#switchLoction").css("color", "#000000");
			this.isSwitchCanClick = true;	
		}	
	}
});