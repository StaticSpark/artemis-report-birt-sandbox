ImpReport =  McReport.extend({
	 mcBase : null,
	 areaInfo : null,
	 reportName : null,
	 areaInfo: null,
	 path : null,
	 id : null,
	 constructor:function(){
		__imp = this; 
		this.base();
		this.dataPrepare();
		this.initUI();
		this.initEvent();
		this.refreshReport();
		new McImpSwitchLocation("report", "chart");
	 },
	 //数据准备
	 dataPrepare : function () {
		mcBase = data.mcBase;
		areaInfo = new Array();
		areaInfo = data.areaInfo;
		reportName = data.reportName;
		path = "*";
		id= mcBase.id;
	 },
	 //初始化UI
	 initUI : function () {
		 xPox = -80;
		 yPox = 270;
		 this.initPathUI();
		 $("#mcName").html(mcBase.name);
		 $("option[value='*']").attr("selected", true);
		 this.updateReportTime();
		 this.pathDisplay();
	 },
	 //初始化path信息
	 initPathUI : function () {
		$("#pathList").html("");
		var optionStr = "<option value=''></option><option value='*'>*</option>";
		paths = mcBase.paths;
		var pathArray = new Array();
		pathArray = paths.split(",");
		$("#pathList").append(optionStr);
		for(var i = 0; i < pathArray.length; i++) {
		   if(pathArray[i].length > 0 ) {
		     if (pathArray[i] == 0){
		    	 optionStr = "<option value='" + pathArray[i] + "'>" + "-" + "</option>";
		     } else{
		    	 optionStr = "<option value='" + pathArray[i] + "'>" + pathArray[i] + "</option>"; 
		     }		
		     $("#pathList").append(optionStr);
		   }
	    }  	
	 },
	 //初始化 事件监听
	 initEvent : function () {
		//点击开始时间 
			$("#startDate").click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					alwaysUseStartDate : false
				});
			});
				//点击结束时间 
			$("#endDate").click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					alwaysUseStartDate : false
				});
			});
				
			$("#pathList").live("change", function() {
				path = $("#pathList option:selected").val();
			});
				
			$("a[name='report_type_chioce']").click(function(){
				reportName = $(this).attr("id");
				__imp.changeLabelColor();
		});
			
		$("#pathList").live("change", function() {
			path = $("#pathList option:selected").val();
		});
			
		$("input[name='search']").live("click", function(){
			__imp.refreshReport();
			if($("td[name='chart_multiple_area_names']").length > 0) {
				new ImpChart();
			}
		});
		$("#path_switch").click(function(){
			$("#switchLoction").click();
			$(this).val($("#switchLoction").val());
		});
		
		$("a[name='navBtn']").live("click", function(){
			var id = $(this).attr("id");
			if(id.indexOf("imp") > -1)
			{
				reportName = $(this).attr("reportName");
//				__imp.updateReportTime();
//				__imp.pathDisplay();
//				__imp.refreshReport();
				window.open(ctx + "/mc/imp-report?id=" + mcBase.id + "&reportName=" + reportName);
			}
			else if(id == "uvPeriod")
				window.open(ctx + "/mc/uv-report?id=" + mcBase.id);
			else if(id == "uvDay")
				window.open(ctx + "/mc/uv-report-day?id=" + mcBase.id);
		});
	

			
		    //一键导出
			$("#one-key").click(function(){
				var obj = document.getElementById("report").contentWindow;
				var selectedPath = $("#pathList").val();
				if (selectedPath == "") {
					var nodeList = obj.document.getElementsByClassName("style_7");
					var json = {};
					for (var i=0; i<nodeList.length;i++) {
						var node = obj.document.getElementById("__TOC_"+i);
					    var timeNode = $(node).children().first().next().html();
						//对属性进行解析，获得相应的参数
						var key = __imp.parseProperty(timeNode);
						json[key]="";	
						
						if ($(node).next().attr("class") == "style_8") {		
						    var timeNode = $(node).next().children().first().next().html();
							//对属性进行解析，获得相应的参数
							var key = __imp.parseProperty(timeNode);
							json[key]="";			
						}
					}
					var url = "one-key-export?json="+JSON.stringify(json);
					window.location = url;
				}else if (selectedPath == "*") {
					var nodeList = obj.document.getElementsByClassName("style_7");
					var json = {};
					for (var i=0; i<nodeList.length;i++) {
						var node = obj.document.getElementById("__TOC_"+i);
					    var timeNode = $(node).children().first().next().html();
						//对属性进行解析，获得相应的参数
						var key = __imp.parseProperty(timeNode);
						json[key]="";
					}
					var url = "one-key-export?json="+JSON.stringify(json);
					window.location = url;
				}else {
					var nodeList = obj.document.getElementsByClassName("style_8");
					var json = {};
					for (var i=0; i<nodeList.length;i++) {
					    var timeNode = $(nodeList[i]).children().first().next().html();
						//对属性进行解析，获得相应的参数
						var key = __imp.parseProperty(timeNode);
						json[key]="";
					}
					var url = "one-key-export?json="+JSON.stringify(json);
					window.location = url;
				}
			});
	 },
	 oneKeyExport:function() {
		var obj = document.getElementById("report").contentWindow;
		var selectedPath = $("#pathList").val();
		if (selectedPath == "") {
			var nodeList = obj.document.getElementsByClassName("style_7");
			var json = {};
			for (var i=0; i<nodeList.length;i++) {
				var node = obj.document.getElementById("__TOC_"+i);
			    var timeNode = $(node).children().first().next().html();
				//对属性进行解析，获得相应的参数
				var key = __imp.parseProperty(timeNode);
				json[key]="";	
				var subNode = $(node).next();
				if ($(subNode).attr("class") == "style_8") {		
				    var timeNode = $(node).next().children().first().next().html();
					//对属性进行解析，获得相应的参数
					var key = __imp.parseProperty(timeNode);
					json[key]="";			
				}
				if ($(subNode).next().attr("class") == "style_8") {
					var timeNode = $(subNode).next().children().first().next().html();
					//对属性进行解析，获得相应的参数
					var key = __imp.parseProperty(timeNode);
					json[key]="";	
				}			
			}
		}else if (selectedPath == "*") {
			var nodeList = obj.document.getElementsByClassName("style_7");
			var json = {};
			for (var i=0; i<nodeList.length;i++) {
				var node = obj.document.getElementById("__TOC_"+i);
			    var timeNode = $(node).children().first().next().html();
				//对属性进行解析，获得相应的参数
				var key = __imp.parseProperty(timeNode);
				json[key]="";
			}
		}else {
			var nodeList = obj.document.getElementsByClassName("style_8");
			var json = {};
			for (var i=0; i<nodeList.length;i++) {
			    var timeNode = $(nodeList[i]).children().first().next().html();
				//对属性进行解析，获得相应的参数
				var key = __imp.parseProperty(timeNode);
				json[key]="";
			}
		}			
		if (JSON.stringify(json)=='{}') {
			hiAlert("无时段数据供导出!");
		}else {
			var url = ctx +"/mc/one-key-export?json="+JSON.stringify(json);
			window.location = url;
		}	
	 },
	 //一键导出解析属性字段
	 parseProperty: function(timeNode) {
		var timeProperty = $($(timeNode).html()).attr("href");
		var url1 = timeProperty.split("monId=")[0];
		var monId = timeProperty.split("monId=")[1].split("&")[0];
		var areaId = timeProperty.split("areaId=")[1].split("&")[0];
		var monDate = timeProperty.split("monDate=")[1].split("&")[0];
		var areaName = timeProperty.split("areaName=")[1].split("&")[0];
		var isVisualArea = timeProperty.split("isVisualArea=")[1].split("&")[0];
		var path = timeProperty.split("path=")[1].split("&")[0];
		var impLimit = timeProperty.split("impLimit=")[1].split("&")[0];
		var mcName = $("#mcName").html();
		if (path == "") {
			path = "*";
		}
		return monId+","+areaId+","+monDate+","+areaName+","+isVisualArea+","+path+","+mcName+","+impLimit;
	 },
	 
	 //改变点击标签的颜色
	 changeLabelColor : function () {
		 var otherReportName = $("a[name='report_type_chioce']").not("#" + reportName).attr("id");
		 $("#" + reportName).css("color","red");
		 $("#" + otherReportName).css("color","black");
		 $("#startDate").val("");
		 $("#endDate").val("");
	 },
	 //根据报告名字是否展现
	 pathDisplay : function () {
		 $("#imp-day-tr").show();
		if(reportName == "mc_imp_report" || reportName == "")
		{
			$("#pathList").show();
			$("#pathStar").hide();
		}
		else
		{
			$("#pathList").hide();
			$("#pathStar").show();
		}
			
		if(reportName == "mc_imp_sum_date_report")
		{
			$("#areaList").hide();
			$("#allArea").show();
		}
		else
		{
			$("#areaList").show();
			$("#allArea").hide();
		}
	 },
	 //刷新报告
	 refreshReport : function () {
		    startDate = $("#startDate").val();
		 	endDate = $("#endDate").val();
			areaIds = $("#areaIds").val();
			areaNames = $("#areaList").val();
			path = $("#pathList option:selected").val();
		    monName = mcBase.name;
			if(!checkEndTime(startDate, endDate)) {
				 hiAlert("开始时间不得小于结束时间!");
		    	 return;
			}
			
			if(reportName == "" || reportName == "mc_imp_report") 
			{
				reportName = "mc_imp_report";
				$("#reportTail").html("--分天");
			}  
			else if(reportName == "mc_imp_sum_report")
			{
				if(startDate == "")
				    startDate = data.minDate;
				if(endDate == "") 
					endDate = data.maxDate;
				
				$("#reportTail").html("--天小计");
			} 
			else if(reportName == "mc_imp_sum_date_report")
			{
				if(startDate == "")
				    startDate = data.minDate;
				if(endDate == "") 
					endDate = data.maxDate;
				
				$("#reportTail").html("--地域小计");
			}
			
			this.reportColumnsDataPrepare();
			
			var src = $("#report").attr("src");
			if(src.length > 0) {
				src = this.updateUrlParam(src, "areaIds", areaIds);
				src = this.updateUrlParam(src, "path", path);
				src = this.updateUrlParam(src, "startDate", startDate);
				src = this.updateUrlParam(src, "endDate", endDate);
				src = this.updateUrlParam(src, "monId", id);
				src = this.updateUrlParam(src, "monName", monName);
				src = this.updateUrlParam(src, "hideColumns", this.getDefaultHideColumns());
				src = this.updateUrlParam(src, "__report", "persistence/mc/" +reportName + ".rptdesign");
			} else {
				src = ctx + "/frameset?__report=persistence/mc/"+reportName+".rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__toolbar=false&monId="+
				      id+"&startDate="+startDate+"&endDate="+endDate+"&areaIds="+areaIds + "&ctx=" + ctx + "&path=" + path + "&hideColumns=" + this.getDefaultHideColumns()+"&monName="+monName;
			}
			
			$("#report").attr("src", src);

			
			if(reportName == "mc_imp_sum_date_report")
			{
				new ImpSumDateChart();
				$("#imp_container").hide();
				$("#day_chart_container").parent().show();
			}
			else
			{
				$("#imp_container").show();
				$("#day_chart_container").parent().hide();
			}
			return src;
	 },

	 //报告筛选列数据准备
	 reportColumnsDataPrepare : function () {
		 if(reportName == "mc_imp_sum_report")
		 {
			colArray =     [{"index":"4", "columnDisplayName":"path"},
				            {"index":"5", "columnDisplayName":"计划投放量"},
				            {"index":"6", "columnDisplayName":"设定投放量"},
				            {"index":"7", "columnDisplayName":"执行投放量"},
				            {"index":"8", "columnDisplayName":"执行/计划"},
				            {"index":"9", "columnDisplayName":"执行/设定"},
				            {"index":"10", "columnDisplayName":"计划点击量"},
				            {"index":"11", "columnDisplayName":"执行点击量"},
				            {"index":"12", "columnDisplayName":"地域播放量"},
				            {"index":"13", "columnDisplayName":"地域点击量"},
				            {"index":"14", "columnDisplayName":"点击率"},
				            {"index":"15", "columnDisplayName":"播放请求量"},
				            {"index":"16", "columnDisplayName":"点击请求量"},
				            {"index":"17","isShow": "0", "columnDisplayName":"无效播放量"},
				            {"index":"18","isShow": "0", "columnDisplayName":"异常播放量"},
				            {"index":"19", "isShow": "0","columnDisplayName":"无效点击量"},
				            {"index":"20", "isShow": "0","columnDisplayName":"异常点击量"}
							];
		}
		else if(reportName == "mc_imp_sum_date_report")
		{
			colArray =     [{"index":"2", "columnDisplayName":"path"},
				            {"index":"3", "columnDisplayName":"计划投放量"},
				            {"index":"4", "columnDisplayName":"设定投放量"},
				            {"index":"5", "columnDisplayName":"执行投放量"},
				            {"index":"6", "columnDisplayName":"执行/计划"},
				            {"index":"7", "columnDisplayName":"执行/设定"},
				            {"index":"8", "columnDisplayName":"计划点击量"},
				            {"index":"9", "columnDisplayName":"执行点击量"},
				            {"index":"10", "columnDisplayName":"地域播放量"},
				            {"index":"11", "columnDisplayName":"地域点击量"},
				            {"index":"12", "columnDisplayName":"点击率"},
				            {"index":"13", "columnDisplayName":"播放请求量"},
				            {"index":"14", "columnDisplayName":"点击请求量"},
				            {"index":"15","isShow": "0", "columnDisplayName":"无效播放量"},
				            {"index":"16","isShow": "0", "columnDisplayName":"异常播放量"},
				            {"index":"17", "isShow": "0","columnDisplayName":"无效点击量"},
				            {"index":"18", "isShow": "0","columnDisplayName":"异常点击量"}
							];
		}
		else
		{ 
			colArray =      [{"index":"5", "columnDisplayName":"path"},
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
		}
	 }
});