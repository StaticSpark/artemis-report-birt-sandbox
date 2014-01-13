var ChartEngine = Common.extend({
	data : null,
	/**
	 * x 轴数据
	 */
	categories : null,
	/**
	 * y 轴数据
	 */
	series : null,
	/**
	 * 图表类型
	 */
	chartType : null,
	/**
	 * cookie 标记
	 */
	cookieTag : null,
	/**
	 * 开关状态
	 */
	state : "on",
	/**
	 * 自上次查询后有没有更改查询条件
	 */
	isChanged : true,
	/**
	 * 最后一次点击 开关时间
	 */
	switchLastClickTime : null,
	/**
	 * 交换位置开关是否可用
	 */
	isSwitchCanClick : false,
	
	constructor : function() {
		__chartEngine = this;
		this.initPageEvent();
	},
	/**
	 * 初始化图表
	 */
	initChart : function () {
		if(this.state == "off") {
			if(this.isChanged) {//只有重新提交了查询条件才会去重新请求数据
				this.prepareData();
			}
			this.intUI();
			this.intChartEvent();
			this.isChanged = false;
			this.refreshLocationWithNoAnimate();
		}
	},
	alertInfo : function (info) {
		alert("parent:" + info);
	},
	/**
	 * 准备数据
	 */
	prepareData : function () {
		alert("请重写 prepareData 方法");
	},
	/**
	 * 初始化ui
	 */
	intUI : function () {
		this.initChartTypeUI();
		this.defaultChartType();
		this.initChartUI();
	},
	/**
	 * 初始化页面事件
	 */
	initPageEvent : function () {
		$("#switchLoction").click(function() {
			if(!__chartEngine.isSwitchCanClick) {
				return false;
			}
			__chartEngine.switchLoctionWithAnimate();
			$("html,body").animate({scrollTop: 0}, 500);
		});
		
		$("#search").click(function() {
			__chartEngine.isChanged = true;
			__chartEngine.disabledSwitchLoction();
		});
	},
	/**
	 * 初始化图表事件
	 */
	intChartEvent : function () {
		$("[name='chart_type']").live("click", function() {
			var chartType = $(this).attr("id");
			__chartEngine.chartType = chartType;
			__chartEngine.selectChartTypeStyle(chartType);
			__chartEngine.initChartUI();
		});
		
		$(".iphone_switch").click(function(event) {
			var date = new Date();
			var nowTime = date.getTime();
			
			if(this.switchLastClickTime != null &&  nowTime - this.switchLastClickTime < 1000 && nowTime - this.switchLastClickTime > 100) {
				hiAlert("点击过于频繁， 慢点！");
				event.stopPropagation();  
			}
			this.switchLastClickTime = nowTime;
		});
	},
	initSwitch : function () {
		$("#chart_control").show();
		var state = __chartEngine.getState() == "on" ? "on" : "off";
		__chartEngine.initSwitchUI(state);
	},
	dataHandler : function() {
		__chartEngine.categories = this.data.xAxisCategories;
		var columnNames = __chartEngine.getReportColumn();
		var array = new Array();
		//过滤掉没用的列，只留下跟这个分布有关的列
		for(var i = 0; i < this.data.ySeries.length; i++) {
			for(var j = 0; j < columnNames.length; j++) {
				if(columnNames[j].indexOf(this.data.ySeries[i].name) > -1 || this.data.ySeries[i].name.indexOf('左') > -1 || this.data.ySeries[i].name.indexOf('右') > -1) {
					array.push(this.data.ySeries[i]);
				}
			}
		}
		jQuery.unique(array);//排重
		jQuery.unique(array);//排重
		__chartEngine.series = array;
	},
	/*
	 * 刷新报表和图表位置(无动画效果)
	 */
	refreshLocationWithNoAnimate : function () {
		 var tag = $("#switchLoction").attr("firstElement");
	     var baseTop = $("#rpt_id").offset().top + $("#rpt_id").height() + 20;
	     if(tag == "report") {
	    	 $("#report").css("top", baseTop);
	    	 $("#chart_area").css("top",  baseTop + $("#report").height() + 10);
	     } else {
	         $("#chart_area").css("top", baseTop);
	         $("#report").css("top",baseTop + $("#chart_area").height() + 30);
	     }
	},
	initSwitchUI : function (state) {
		$("#switch_chart").remove();
		$("#switch_chart_td").html("<div id='switch_chart' style='display:inline'></div>");
		var tag = $("#switchLoction").attr("firstElement");
		if(tag == "report") {
			$("#chart_area").css("top", $("#report").offset().top + $("#report").height());
		}
        
		$("#switch_chart").iphoneSwitch(state, 
			     function() {
					 __chartEngine.hideChart();
				 },
				 function() {
					 __chartEngine.displayChart();
				 },
				 {
					 switch_on_container_path: 'images/iphone_switch_container_off.png'
				 }
		);
	},
	
	/**
	 * 刷新页面foot位置
	 */
	refreshFootLoction : function () {
		$("#footer").css("position", "absolute");
		var baseTop = $("#rpt_id").offset().top + $("#rpt_id").height() + 20;
		var top = baseTop + $("#report").height() + $("#chart").height() + 100;
		var left = ($("body").width() - $("#footer").width()) / 2;
		
		if(top > $("#chart_area").offset().top + 20 &&  top < $("body").height() - 90) {
		}
		$("#sideBar").css("min-height", top-141);
		$("#footer").css("top", top);
		$("#footer").css("left", left);
	},
	
	/**
	 * 显示图表
	 */
	displayChart : function () {
		var tag = $("#switchLoction").attr("firstElement");
		 __chartEngine.setState("off");
	     $("#chart").show();
		 __chartEngine.initChart();
		 $("#chart").hide();
		 $("#chart").slideDown("slow");
		 $('#ajax').load('on.html');
		 if(tag == "report") {
			 $("html,body").animate({scrollTop: $("#chart").offset().top - 50}, 500); 
		 }
		 __chartEngine.initChartTypeUI();
	},
	/**
	 * 隐藏图表
	 */
	hideChart : function () {
		 var tag = $("#switchLoction").attr("firstElement");
		 __chartEngine.setState("on");
		 $('#ajax').load('off.html');
		 $("#chart").slideUp("slow");
		 $("#chart_types").hide();
		 $("#tips").hide();
		 if(tag == "report") {
			 $("html,body").animate({scrollTop: 0}, 500);
		 }
		 if(tag == "report") {
			 setTimeout(__chartEngine.showReportFirst, 200);
		 } else {
			 setTimeout(__chartEngine.showChartFirst, 500);
		 }
		 setTimeout(__chartEngine.refreshFootLoction, 550);
	},
	/**
	 * 交换位置(有动画效果)
	 */
	switchLoctionWithAnimate : function () {
		var tag = $("#switchLoction").attr("firstElement");
		var state = __chartEngine.getState() == "on" ? "on" : "off";
		if(state == "on") {
			__chartEngine.displayChart();
			__chartEngine.setState("off");
			__chartEngine.initSwitchUI("off");
		}
		
		if(tag == "report") {
			setTimeout(__chartEngine.showChartFirst, 500);
		} else {
			setTimeout(__chartEngine.showReportFirst, 200);
		}
		setTimeout(__chartEngine.refreshFootLoction, 550);
	},
	/**
	 * 将报表显示在前面
	 */
	showReportFirst : function () {
		var baseTop = $("#rpt_id").offset().top + $("#rpt_id").height() + 20;
		$("#report").animate({top : baseTop}, 500);
		$("#chart_area").animate({top : baseTop + $("#report").height() + 10}, 500);
		$("#switchLoction").attr("firstElement", "report");
		$("#switchLoction").val("查看分布图");
	},
	/**
	 * 将图显示在前面
	 */
	showChartFirst : function () {
		var baseTop = $("#rpt_id").offset().top + $("#rpt_id").height() + 20;
		$("#chart_area").animate({top : baseTop}, 500);
		$("#report").animate({top : baseTop + $("#chart_area").height() + 20}, 500);
		$("#switchLoction").attr("firstElement", "chart");
		$("#switchLoction").val("查看报表");
	},
	/**
	 * 初始化cookie
	 */
	initCookie : function () {
		var userName = $.trim($("#userName").text());
		this.cookieTag = userName + "_ad_quotas";
		var userQuotas = $.cookie(this.cookieTag);
		if(userQuotas == undefined || userQuotas == "") {
			$.cookie(this.cookieTag, "浏览量,播放量,点击量,点击率1", {expires: 7});
		} 
		
		$.cookie(this.cookieTag, userQuotas, {expires: 30});
	},
	
	/**
	 * 是否竖排显示
	 */
	isLengendVertical : function() {
		var length = this.getXseriesItemsLength();
		if(length > 70) {
			return true;
		}
		return false;
	},
	/**
	 * 获取 X 轴文字长度
	 * @returns {Number}
	 */
	getXseriesItemsLength : function() {
		var length = 0;
		for(var i = 0; i < __chartEngine.categories.length; i++) {
			length += __chartEngine.categories[i].length;
		}
		return length;
	},
	/**
	 * 获取图表宽度
	 * @returns {Number}
	 */
	getChartWidth : function () {
		this.isLengendVertical();
		var recordNum = this.getChartRecords();
		var chartWidth = $("#report").width() * 0.96;
		if(recordNum > 30) {
			chartWidth = chartWidth + (recordNum - 30) * 0.04 * chartWidth;
			$("#chart").css("overflow", "auto");
		} 
		return chartWidth;
	},
	/**
	 * 获取图表记录数
	 * @returns
	 */
	getChartRecords : function () {
		var count = __chartEngine.series[1].data.length;
		return count;
	},
	/**
	 * 将**率 **比放到第二个坐标系
	 */
	changeRateToSecondCoordinate : function () {
		for(var i = 0; i < this.series.length; i++) {
			if(this.series[i].name.indexOf("率") > -1 || this.series[i].name.indexOf("比") > -1) {
				this.series[i].yAxis = 1;
				this.series[i].type = 'line';
				this.series[i].dashStyle = 'ShortDash';
				this.series[i].tooltip = {
						valueSuffix:'%',
						formatter: function() {
							return this.value * 100;
						}
				};
			}
		}
	},
	initChartTypeUI : function () {
		$("#chart_types").show();
	},
	//获取报告列名
	getReportColumn : function() {
		var if_obj = document.getElementById("report").contentWindow;
        var nodeList = $(if_obj.document).find(".style_5 > a , .style_4 > a");
        var columnNames = new Array();
        for (var i = 0; i < nodeList.length; i++) {
        	var parentStyle = $(nodeList[i]).parent().parent().attr("style");
        	if(parentStyle && parentStyle.indexOf("none")) {
        	} else {
        		columnNames.push($(nodeList[i]).text());
        	}
        }
        return columnNames;
	},
	//默认展示类型
	defaultChartType : function() {
		var lineReport = ['day_report', 'hour_report','month_report'];//需要以折线图展现的
		for(var i = 0; i < lineReport.length; i++) {
			if(reportName.indexOf(lineReport[i]) > -1) {
				this.chartType = "line";
				break;
			} else {
				this.chartType = "column";
			}
		}
		this.selectChartTypeStyle(this.chartType);
	},
	/**
	 * 根据cookie 隐藏 部分曲线
	 */
	hideSeriesAccordanceToCookie : function () {
		var userQuotas = $.cookie(__chartEngine.cookieTag).toString() + "";
		var userQuotasArray = new Array();
		userQuotasArray = userQuotas.split(",");
		for(var i = 0; i < this.series.length; i++) {
			if(userQuotasArray.indexOf(this.series[i].name) == -1 ) {
				this.series[i].visible = false;
			}
		}
	},
	selectChartTypeStyle : function(chartType) {
		$("#" + chartType).attr("class", "chart_type_selected");
		$("[name='chart_type']").not("#" + chartType).attr("class", "chart_type");
	},
	getState : function () {
		return this.state;
	},
	setState : function (state) {
		this.state = state;
	},
	showTip : function () {
		var tips = [
		             "拖动图中某一块区域可进行放大或缩小。",
		             "点击右边指标筛选框可进行指标筛选。"
		           ];
		var index = Math.floor(Math.random() * tips.length);
		$("#tips").show();
		$("#tips").html("<I>ps：" +tips[index] + "</I>");
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
	getTitle : function () {
		return "标题";
	},
	getSubTitle : function () {
		return "副标题";
	},
	chartIdentify : function () {
		return "commonChart";
	},
	initChartUI : function() {
		 this.dataHandler();
		 var width = this.getChartWidth();
		 var title = this.getTitle();
		 var subTitle = this.getSubTitle();
		 
		 this.changeRateToSecondCoordinate();
		 this.showTip();
//		 this.hideSeriesAccordanceToCookie();
		 
		 var recordNum = this.getChartRecords();
		 if(recordNum > 30) {
			 $("#chart").css("width", $("#report").width());
		 } else {
			 $("#chart").css("width", "100%");
		 }
		 
		 if(this.isLengendVertical()) {
			 $("#chart").css("height", 400);
		 } else {
			 $("#chart").css("height", 300);
		 }
		 
		 chartObj = {
				    chart: {
		                type: this.chartType,
		                renderTo: 'chart',
		                width: width,
		                height: this.isLengendVertical() ? 400 : 300,
		            },
		            title: {
		                text: title,
		                x: -20 //center
		            },
		            exporting : {
		            	width: 2000,
		            },
		            subtitle: {
		                text: subTitle,
		                x: -20,
		            },
		            xAxis: {
		                categories: this.categories,
		                labels: {
		                	rotation : this.isLengendVertical() ? 45 : 0
		                }
		            },
		            yAxis:[
		                {
			                title: {
			                    text: ''
			                },
			                plotLines: [{
			                    value: 0,
			                    width: 1,
			                    color: '#808080'
			                }],
			                min: 0, 
			                labels: {
			                     formatter: function() {
			                    	var num = Highcharts.numberFormat(this.value, 0, ".", ","); 
			                    	return num;
			                     }
			                }
			            },
			            {
			               labels: {
			                     formatter: function() {
			                    	 var rate = this.value * 100 + "";
				                	 if(rate.length > 5) {
				                		rate = rate.substring(0, 5);
				                	 }
				                	 rate = rate + "%";
			                         return rate;
			                     }
			                },
			                title: {
			                    text: ''
			                },
			                min: 0, 
			                plotLines: [{
			                    value: 0,
			                    width: 1,
			                    color: '#808080'
			                }],
			                opposite: true
			            }
		            ],
		            tooltip: {
		                valueSuffix: '',
		                formatter: function() {
		                	var str = "";
		                	if(this.series.name.indexOf("率") > -1 || this.series.name.indexOf("比") > -1) {
		                		var rate = this.y * 100 + "";
		                		if(rate.length > 5) {
		                			rate = rate.substring(0, 5);
		                		}
		                		str = rate + "%";
		                	} else {
		                		str = this.y;
		                	}
		                	
		                	str = "<b>" + this.series.name + "</b> <br/>" +
		                	      this.x + "：" + str;
		                	return str;
						}
		            },
		            series: this.series,
		            plotOptions :{
		            	allowPointSelect :true,
		            	series : {
		            	}
		            }
		        };
		 this.formateChart(chartObj);
		 var chartCookie = new ChartCookie("ad_cookie", "浏览量,播放量,点击量,点击率1", chartObj);
		 chart = new Highcharts.Chart(chartObj);
		 this.refreshFootLoction();
		 $("#chart_line_split").show();
	},
});