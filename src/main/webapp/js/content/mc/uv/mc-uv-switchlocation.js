/**
 * uv报告交换位置
 */
var uvSwitchLocation = SwitchLocation.extend({
	
	constructor : function(reportId,chartId) {
		this.base(reportId,chartId);
		__mc_uv_switchLocation = this;
	},
	/**
	 * 启用切换
	 */
	enableSwitchLoction : function () {
		if (dateChartData.areaList.length == 0) {
			$("#chart").hide();
			 var tag = $("#switchLoction").attr("firstElement");
		     if(tag == "report") {
		    	 //不做什么操作
		     } else {
		    	 this.showReportFirst();
		    	 $("#switchLoction").attr("firstElement", this.reportId);
		     }
		     this.isSwitchCanClick = false;
		     $("#switchLoction").css("color", "gray");
		     this.refreshFootLoction();
		}else {
			$("#switchLoction").css("color", "#ffffff");
			this.isSwitchCanClick = true;	
		}	
	}
	
});