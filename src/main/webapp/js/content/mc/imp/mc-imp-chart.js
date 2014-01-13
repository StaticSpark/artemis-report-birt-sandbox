ImpChart = McReport.extend({
	areaData : null,
	hourData : null,
	areaNames : null,
	areaCategories : null,
	areaSeries : null,
	hourCategories : null,
	hourSeries : null,
	hourSumCategories : null,
	hourSumSeries : null,
	keyValue : null,
	constructor : function() {
		__impChart = this;
		//this.initUI();
		this.mcKeyValueDefine();
		this.prepareData();
		
		var areaNames = new Array();
		
		
		var selectedAreaNames = $("#areaList").val();
		if(selectedAreaNames && selectedAreaNames != "选择地域" && selectedAreaNames.length > 0) {
			areaNames = selectedAreaNames.split(",");
		} else {
			for(var i = 0; i < areaInfo.length; i++) {
				areaNames.push(areaInfo[i].name);
			}
		}
		new ImpChartMultipleAreaSelect("chart_multiple_area_list", areaNames);
		new ImpChartSingleAreaSelect("chart_single_area_list", areaNames);
		this.initHourChart();
		this.initAreaChart();
		this.initEvent();
	},
	initUI : function () {
		var reportWidth = $("#report").width() * 0.96;
		$("#chart").css("width", reportWidth);
	},
	initEvent : function () {
		$("#area_chart_label").live("click",function() {
			__impChart.showAreaChart();
		});
		$("#hour_chart_label").live("click",function() {
			__impChart.showHourChart();
		});
	},
	/**
	 * 显示日期图表
	 */
	showAreaChart : function () {
		$("#area_chart_label").css("background-color", "#F8F8FF");
		$("#area_chart_area").show();
		$("#hour_chart_label").css("background-color", "#7A991A");	
		$("#hour_chart_area").hide();
	},
	/**
	 * 显示小时图表
	 */
	showHourChart : function () {
		$("#hour_chart_label").css("background-color", "#F8F8FF");
		$("#hour_chart_area").show();
		$("#area_chart_label").css("background-color", "#7A991A");	
		$("#area_chart_area").hide();
	},
	prepareData : function() {
		var param = "id=" + id + "&startDate=" + startDate + "&endDate=" + endDate + "&areaIds=" + areaIds + 
		    "&path=" + path;
		if("isAreaGroup" in window) {
			param +=  "&isAreaGroup=1";
		}
		$.ajax({
			type: "post",
			async:false,
			dataType:'json',
			url: ctx + "/mc/imp-day-chart?"+param,
			success:function(data) {
				__impChart.areaData = data;
			}
		});
		
		$.ajax({
			type: "post",
			async:false,
			dataType:'json',
			url: ctx + "/mc/imp-hour-chart?"+param,
			success:function(data) {
				__impChart.hourData = data;
			}
		});
	},
	/**
	 * 初始化地域图表
	 * @param areaNames
	 */
	initAreaChart : function (areaNames) {
        if(areaNames == undefined || areaNames == null) {
        	areaNames = new Array();
    		areaNames.push(areaInfo[0].name);
		}
		this.areaDataFormate(areaNames);
		var rotation = this.getXseriesItemsNum(this.areaCategories) > 15 ? 90 : 0;
		this.initChartUI("area_chart_container", "column", this.areaCategories, this.areaSeries, rotation, "播控IMP-日期分布图");
		this.showAreaChart();
	},
	/**
	 * 初始化小时图表
	 * @param areaNames
	 */
	initHourChart : function (areaNames) {
        if(areaNames == undefined || areaNames == null) {
        	areaNames = new Array();
    		areaNames.push(areaInfo[0].name);
		}
        var rotation = 70;
        if($("#reportTail").html() == "--天小计") {
        	this.hourSumDataFormate(areaNames);
        	this.initChartUI("hour_chart_container", "line", this.hourSumCategories, this.hourSumSeries, rotation, "播控IMP-时段分布图");
        } else {
        	this.hourDataFormate(areaNames);
        	this.initChartUI("hour_chart_container", "line", this.hourCategories, this.hourSeries, rotation, "播控IMP-时段分布图");
        }
        var a = 1;
	},
	/**
	 * 格式化分地域数据
	 * 思路： 循环 json对象，将 areaNames 中地域信息组合在一起
	 * 结果 this.areaCategories 结构为 [{
	 *      name : "北京",
	 *      categories :['2013-12-01', '2013-12-01']
	 *    }
	 * ]
	 * 
	 * this.areaSeries 结构为 [{
	 *   name : "执行投放量",
	 *   data :[1000, 2000, 3000, 1000]
	 * }
	 * ]
	 * @param areaNames
	 */
	areaDataFormate : function(areaNames) {
		this.areaCategories = new Array();
		this.areaSeries = new Array();
		for(var i = 0; i < areaNames.length; i++) {
			for(var j = 0; j < this.areaData.length; j++) {
				if(this.areaData[j].areaName == areaNames[i]) {
					var cIndex = -1;
					for(var k = 0; k < this.areaCategories.length; k++) {
						if(this.areaCategories[k].name == this.areaData[j].areaName) {
							cIndex = k;
						}
					}
					if(cIndex == -1) {
						var dateArray = new Array();
						dateArray.push(this.areaData[j].monDate);
						var obj = {
								name: this.areaData[j].areaName,
								categories : dateArray
						};
						this.areaCategories.push(obj);
					} else {
						this.areaCategories[cIndex].categories.push(this.areaData[j].monDate);
					}
				}
			}
		}
		
		for(o in this.keyValue) {
			var array = new Array();
			for(var i = 0; i < this.areaCategories.length; i++) {
				for(var j = 0; j < this.areaCategories[i].categories.length; j++) {
					var areaName = this.areaCategories[i].name;
					var date = this.areaCategories[i].categories[j];
					for(var n = 0; n < this.areaData.length; n++) {
						if(this.areaData[n].areaName == areaName && this.areaData[n].monDate == date) {
							var obj = this.areaData[n];
							var val = obj[o];
							if(!val) {
								val = 0;
							}
							array.push(val);
						}
					}
				}
			}
			
			var s = {
					  name : this.keyValue[o],
					  data : array
				    };
			this.areaSeries.push(s);
		}
		this.addImpRate(this.areaSeries);
	},
	areaDataFormate2 : function(areaNames) {
		this.areaCategories = new Array();
		this.areaSeries = new Array();
		for(var i = 0; i < areaNames.length; i++) {
			for(var j = 0; j < this.areaData.length; j++) {
				if(this.areaData[j].areaName == areaNames[i]) {
					var dateArray = new Array();
					var areaDataList = this.areaData[j].list;
					for(var n = 0 ; n < areaDataList.length; n++) {
						dateArray.push(areaDataList[n].monDate);
					}
					var o = {
						name : areaNames[i],
						categories : dateArray
					};
					this.areaCategories.push(o);
					
					for(var o in this.keyValue) {
						var seriesArray = new Array();
						
						for(var n = 0; n < this.areaSeries.length; n++) {
							if(this.areaSeries[n].name == this.keyValue[o]) {
								seriesArray = this.areaSeries[n].data;
							}
						}
						
						for(var n = 0; n < areaDataList.length; n++) {
							var obj = areaDataList[n];
							var val = obj[o];
							if(!val) {
								val = 0;
							}
							seriesArray.push(val);
						}
						var s = {
							name: this.keyValue[o],
							data : seriesArray
						};
						this.areaSeries.push(s);
					}
				}
			}
		}
		
		this.addImpRate(this.areaSeries);
	},
	/**
	 * 小时数据
	 */
	hourDataFormate : function (areaName) {
		this.hourCategories = new Array();
		this.hourSeries = new Array();
		for(var j = 0; j < this.hourData.length; j++) {
			if(this.hourData[j].areaName == areaName) {
				var cIndex = -1;
				for(var n = 0; n < this.hourCategories.length; n++) {
					if(this.hourCategories[n].name == this.hourData[j].monDate) {
						cIndex = n;
					}
				}
				if(cIndex == -1) {
					var hourArray = ['00:00~01:00','01:00~02:00','02:00~03:00','03:00~04:00','04:00~05:00','05:00~06:00','06:00~07:00','07:00~08:00','08:00~09:00',
					                 '09:00~10:00','10:00~11:00','11:00~12:00','12:00~13:00','13:00~14:00','14:00~15:00','15:00~16:00','16:00~17:00','17:00~18:00',
					                 '18:00~19:00','19:00~20:00','20:00~21:00','21:00~22:00','22:00~23:00','23:00~24:00'];
					var obj = {
						name: this.hourData[j].monDate,
						categories : hourArray
					};
					this.hourCategories.push(obj);
				} 
			}
		}
		
		for(o in this.keyValue) {
			var array = new Array();
			for(var n = 0; n < this.hourCategories.length * 24; n++) {
				array.push(0);
			}
			for(var i = 0; i < this.hourCategories.length; i++) {
				var date = this.hourCategories[i].name;
				for(var n = 0; n < this.hourData.length; n++) {
					if(this.hourData[n].areaName == areaName && this.hourData[n].monDate == date) {
						var obj = this.hourData[n];
						var index = i * 24 + this.hourData[n].monHour;
						var val = obj[o];
						if(!val) {
							val = 0;
						}
						array[index] = val;
					}
				}
		    }
			
		    var s = {
				      name : this.keyValue[o],
				      data : array
				    };
		    this.hourSeries.push(s);
		}
		this.addImpRate(this.hourSeries);
	},
	/**
	 * 小计小时数据
	 */
	hourSumDataFormate : function (areaName) {
		this.hourSumCategories = new Array();
		this.hourSumSeries = new Array();
		
		this.hourSumCategories = ['00:00~01:00','01:00~02:00','02:00~03:00','03:00~04:00','04:00~05:00','05:00~06:00','06:00~07:00','07:00~08:00','08:00~09:00',
		                 '09:00~10:00','10:00~11:00','11:00~12:00','12:00~13:00','13:00~14:00','14:00~15:00','15:00~16:00','16:00~17:00','17:00~18:00',
		                 '18:00~19:00','19:00~20:00','20:00~21:00','21:00~22:00','22:00~23:00','23:00~24:00'];
		for(o in this.keyValue) {
			var array = new Array();
			for(var n = 0; n < 24; n++) {
				array.push(0);
			}
			for(var n = 0; n < this.hourData.length; n++) {
				var obj = this.hourData[n];
				var hour = obj.monHour;
				var val = obj[o];
				var old = array[hour];
				if(val && (areaName == obj.areaName)) {
					if(old == undefined || old == null || old == "" || old == "undefined") {
						array[hour] = val;
					} else {
						array[hour] = val + old;
					}
				}
			}
		    var s = {
				      name : this.keyValue[o],
				      data : array
				    };
		    this.hourSumSeries.push(s);
		}
		this.addImpRate(this.hourSumSeries);
	},
	/**
	 * 添加imp **比
	 */
	addImpRate : function (series) {
		var left = {
			       name : "<span name='left_lengend'>左轴</span>",
			       data : []
	            };
		series.unshift(left);
		var right = {
				       name : "<span name='right_lengend'>右轴</span>",
				       data : []
		            };
		series.push(right);
		var rate = [
		             {
		              "name" : "执行/计划",
		              "columns": ['执行投放量', '计划投放量']
		             },
		             {
			              "name" : "执行/设定",
			              "columns": ['执行投放量', '设定投放量']
			         },
			         {
			              "name" : "点击率",
			              "columns": ['执行点击量', '执行投放量']
			         }
		           ];
		var columnNames = new Array();
		for(var i = 0; i < rate.length; i++) {
			columnNames.push(rate[i].columns[0]);
			columnNames.push(rate[i].columns[1]);
		}
		jQuery.unique(columnNames);
		var columnData = new Array(columnNames.length);
		
		for(var i = 0; i < series.length; i++) {
			var index = columnNames.indexOf(series[i].name);
			if(index > -1) {
				columnData[index] = series[i].data;
			}
		}
		for(var i = 0; i < rate.length; i++) {
			var na = rate[i].columns[0];
			var nb = rate[i].columns[1];
			
			var dataA = columnData[columnNames.indexOf(na)];
			var dataB = columnData[columnNames.indexOf(nb)];
			
			rate[i].data = new Array();
			for(var j = 0; j < dataA.length; j++) {
				var percent = dataA[j] / dataB[j];
				percent = dataB[j] == 0 ? 0 : percent;
				rate[i].data.push(percent);
			}
			var s = {
				       name : rate[i].name,
				       data : rate[i].data,
				       yAxis: 1,
				       type : 'line',
				       dashStyle : 'ShortDash'
				    };
			series.push(s);
		}
	},
	/**
	 * 获取x轴记录数
	 */
	getXseriesItemsNum : function(categories) {
		var num = 0;
		if(categories) {
			if(categories[0] && categories[0].categories) {
				for(var i = 0; i < categories.length; i++) {
					num = num + categories[i].categories.length;
				}
			} else {
				num = categories.length;
			}
		}
		
		return num;
	},
	/**
	 * 获取x轴文字长度
	 * @param categories
	 * @returns {Number}
	 */
	getXseriesTextLength : function (categories) {
		var length = 0;
		if(categories) {
			if(categories[0] && categories[0].categories) {
				for(var i = 0; i < categories.length; i++) {
					for(var j = 0; j < categories[i].categories.length; j++) {
						length = length + categories[i].categories[j].length;
					}
				}
			} else {
				return categories.length;
			}
		}
		return length;
	},
	/**
	 * 获取图表宽度
	 */
	getChartWidth : function (categories) {
		var num = this.getXseriesItemsNum(categories);
		
		var chartWidth = $("#report").width();
		var type = $("#area_chart_area").css("display");
		if(num > 30 && type == "block") {
			chartWidth = chartWidth + (num - 30) * 0.01 * chartWidth;
		}
		return chartWidth;
	},
	/**
	 * 格式化X轴，当x轴记录数过多时，不显示小时数据
	 * @param categories
	 */
	formateHourXseriesItems : function () {
		var num = 0;
		if($("#reportTail").html() == "--天小计") {
			num = this.getXseriesItemsNum(this.hourSumCategories);
		} else {
			num = this.getXseriesItemsNum(this.hourCategories);
		}
		
		if(num > 48) {
			for(var i = 0; i < this.hourCategories.length; i++) {
				for(var j = 0; j < this.hourCategories[i].categories.length; j++) {
					this.hourCategories[i].categories[j] = " ";
				}
			}
		} 
		return num;
	},
	/**
	 * 初始化 图表 UI
	 * @param chartId
	 * @param chartType
	 * @param categories
	 * @param series
	 * @param rotation
	 */
	initChartUI : function (chartId, chartType, categories, series, rotation, title) {
		this.formateHourXseriesItems();
		var chartWidth = this.getChartWidth(categories);
		if(this.getXseriesTextLength() < 200) {
			chartWidth = "";
			$("#" + chartId).css("overflow-x","");
			$("#" + chartId).css("overflow-y","");
		} else {
			$("#" + chartId).css("overflow-x","scroll");
			$("#" + chartId).css("overflow-y","hidden");
		}
		
		var haveData = (categories.length == 0) ? false : true;
		
		var chartObj = {
		        chart: {
		            renderTo: chartId,
		            type: chartType,
		            zoomType : "xy",
		            width : chartWidth
		        },
		        title: {
		            text: title
		        },
		        subtitle : {
		        	text : haveData ? "" : "无数据",
		        	style : {
		        		color : "red"
		        	}
		        },
		        series: series,
		        exporting:{ 
                    enabled:false 
                },
		        yAxis:[
		                {
			                min: 0, 
			                labels: {
			                     formatter: function() {
			                    	var num = Highcharts.numberFormat(this.value, 0, ".", ","); 
			                    	return num;
			                     }
			                },
		                    title : {
		                    	text : ""
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
			                min: 0, 
			                opposite: true,
		                    title : {
		                    	text : ""
		                    }
			            }
		            ],
		         tooltip: {
		                valueSuffix: '',
		                formatter: function() {
		                	var str = "";
		                	if(this.series.name.indexOf("率") > -1 || this.series.name.indexOf("比") > -1 || this.series.name.indexOf("/") > -1) {
		                		var num = new Number(this.y);
		               		    num = num * 100;
		               		    var rate = num.toFixed(2) + "";
		                		if(rate.length > 5) {
		                			rate = rate.substring(0, 5);
		                		}
		                		str = rate + "%";
		                	} else {
		                		str = this.y;
		                	}
		                	
		                	
		                	var seriesName = this.x + "";
		                	if(seriesName.indexOf(",") > -1) {
		                		var seriesNameArray = new Array();
			                	seriesNameArray = seriesName.split(",");
			                	seriesName = seriesNameArray[1] + "," + seriesNameArray[0];
		                	}
		                	str = seriesName + "<br/>" + this.series.name + "：" + str;
		                	return str;
						}
		            },
		        xAxis: {
		            categories: categories,
		            labels : {
		            	rotation : rotation
		            }
		        }
		    };
		new ChartCookie(chartId, "执行投放量,执行/设定", chartObj);
		this.formateChart(chartObj);
		new Highcharts.Chart(chartObj);
		this.legendCategory();
	},
	/**
	 * 图例分组
	 */
	legendCategory : function () {
		 $("span[name='left_lengend']").each(function(i){
			 $(this).parent().parent().css("white-space", "nowrap");
			 $(this).parent().parent().html("<b>左轴</b>");
		 });
		 
		 $("span[name='right_lengend']").each(function(i){
			 $(this).parent().parent().css("white-space", "nowrap");
			 $(this).parent().parent().html("<b>右轴</b>");
		 });
	},
	mcKeyValueDefine : function () {
		this.keyValue = {
			planImp : "计划投放量",
			impLimit : "设定投放量",
			impFwd : "执行投放量",
			planClk : "计划点击量",
			clkFwd : "执行点击量",
			impChokeArea : "地域播放量",
			clkChokeArea : "地域点击量",
			actImp : "播放请求量",
			actClk : "点击请求量",
			impBlock : "无效播放量",
			impChokeSum : "异常播放量",
			clkBlock : "无效点击量",
			clkChokeSum : "异常点击量"
		};
	}
});