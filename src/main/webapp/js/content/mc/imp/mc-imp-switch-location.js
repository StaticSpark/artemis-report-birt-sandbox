var McImpSwitchLocation = SwitchLocation.extend({
	constructor : function(reportId, chartId) {
		this.base(reportId, chartId);
		__mcImpSwitchLocation = this;
	},
	/**
	 * 禁用切换
	 */
	disabledSwitchLoction : function () {	
		$("#path_switch").css("color", "gray");
		this.isSwitchCanClick = false;
	},
	/**
	 * 启用切换
	 */
	enableSwitchLoction : function () {
		var report = document.getElementById("report").contentWindow; 
		var table = report.document.getElementById("__bookmark_1");
		var trList = table.getElementsByTagName("tr");
		var length = trList.length;//表中记录数
		if(reportName == "mc_imp_report") {
			length = length - 1;
		}
		//小计报告的数据长度一直为0 
		if (length <= 1) //无数据
		{
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
		     $("#path_switch").val("查看分布图");
		     $("#path_switch").css("color", "gray");
		     
		}else {
			$("#path_switch").css("color", "#ffffff");
			this.isSwitchCanClick = true;	
		}
	},
	initEvent : function () {
		$("#switchLoction").live("click", function () {
			if($("td[name='chart_multiple_area_names']").length == 0) {
				new ImpChart();
			}
			if (__switchLocation.isSwitchCanClick == false ){
				return ;
			}else {
				//初始化时的图标隐藏，当点击查询图表示，出现
				$("#chart").show();
				__switchLocation.refreshLocation();
			}
			
		});
		
		$("#path_search").live("click", function () {
			__switchLocation.disabledSwitchLoction();
		}); 
	},
});