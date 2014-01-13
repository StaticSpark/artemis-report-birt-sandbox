ImpAreaGroupReport =  McReport.extend({
	selectPath : null,
	constructor : function() {
		__imp_area_group_report = this;
		this.prepareData();
		this.initUI();
		this.initEvent();
		this.initColArray();
		this.selectPath = data.path;
		this.refreshReport();
		new McImpSwitchLocation("report", "chart");
	},
	prepareData : function () {
		id = data.monId;
		startDate = data.startDate;
		endDate = data.endDate;
		areaIds = data.areaId;
		path = data.path;
		isAreaGroup = 1;
		areaInfo = data.areaList;
	},
	initUI : function () {
		this.initHeaderUI();
		this.initPathUI();
	},
	initEvent : function () {
		$("[name='pathlink']").live("click", function() {
			__imp_area_group_report.selectPath = $(this).text();
			path = __imp_area_group_report.selectPath;
			if (__imp_area_group_report.selectPath == '-') {
				__imp_area_group_report.selectPath = 0;
			}
			$("[name='pathlink']").each(function(i){
				$(this).css("color", "black");
			});
			$(this).css("color","red");
			__imp_area_group_report.refreshReport();
			
			if($("td[name='chart_multiple_area_names']").length > 0) {
				new ImpChart();
			}
		});
	},
	initHeaderUI : function () {
		$("#mcName").text(data.mcName);
		$("#areaName").text(data.areaName);
		$("#view_period_uv").attr("href", ctx + "/mc/uv-report?id=" + data.monId);
		$("#view_day_uv").attr("href", ctx + "/mc/uv-report-day?id=" + data.monId);
	},
	initPathUI : function () {
		var pathList = new Array();
		pathList = data.pathList;

	    if(data.path == null || data.path == "null") {
	    	data.path = "*";
		}
	 
		for(var i = 0; i < pathList.length; i++) {
			if(pathList[i] == data.path) {
				if (pathList[i] == 0) {
					pathList[i] = '-';
				}
				$("#path").append("<a style='margin-right: 10px;color:red;text-decoration:underline;' name='pathlink' href='javascript:void(0);'>" + pathList[i] + "</a>");			
			} else {		
				if (pathList[i] == 0) {
					pathList[i] = '-';
				}
				$("#path").append("<a style='margin-right: 10px;text-decoration:underline;' name='pathlink' href='javascript:void(0);'>" + pathList[i] + "</a>");		
			}
		}
	},
	//刷新报表
	refreshReport : function() {
		if(this.selectPath == null || this.selectPath == "null") {
			this.selectPath = "";
		}
		var monName = data.mcName;
		var src = $("#report").attr("src");
		if(src.length > 0) {
			src = this.updateUrlParam(src, "path", this.selectPath);
		} else {
			src = ctx + "/frameset?__report=persistence/mc/mc_imp_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&monId="+data.monId 
		    +"&startDate=" + data.startDate + "&endDate=" + data.endDate +"&areaId="+data.areaId+"&path=" + this.selectPath +"&ctx=" + ctx +"&isVisualArea=1" + "&areaGroupName=" + data.areaGroupName+ "&hideColumns=" + getDefaultHideColumns()
		    +"&monName="+monName;
		}
		$("#report").attr("src", src);
	},
	//初始化基础信息
	initColArray : function () {
		colArray = [{"index":"5", "columnDisplayName":"path"},
		            {"index":"6", "columnDisplayName":"计划投放量"},
		          	{"index":"7", "columnDisplayName":"设定投放量"},
		          	{"index":"8", "columnDisplayName":"执行投放量"},
		          	{"index":"9", "columnDisplayName":"执行/计划"},
		          	{"index":"10", "columnDisplayName":"执行/设定"},
		          	{"index":"11", "columnDisplayName":"计划点击量"},
		          	{"index":"12", "columnDisplayName":"执行点击量"},
		          	{"index":"13", "columnDisplayName":"地域播放量"},
		          	{"index":"14", "columnDisplayName":"播放比"},
		          	{"index":"15", "columnDisplayName":"地域点击量"},
		          	{"index":"16", "columnDisplayName":"日点击指标"},
		          	{"index":"17", "columnDisplayName":"点击率"},
		          	{"index":"18", "columnDisplayName":"播放请求量"},
		          	{"index":"19", "columnDisplayName":"点击请求量"},
		          	{"index":"20", "isShow": "0", "columnDisplayName":"无效播放量"},
		          	{"index":"21", "isShow": "0", "columnDisplayName":"异常播放量"},
		          	{"index":"22", "isShow": "0", "columnDisplayName":"无效点击量"},
		          	{"index":"23", "isShow": "0", "columnDisplayName":"异常点击量"}];
		xPox = -80;
		yPox = 270;
	}
});