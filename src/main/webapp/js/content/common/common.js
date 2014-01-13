$(document).ready(function() {
	__common = new Common();
});

//系统公用类
var Common = Base.extend({
	//构造器 
	chart_width : 0,
	constructor : function() {
		Array.prototype.remove = function(from, to) {
			var rest = this.slice((to || from) + 1 || this.length);
			this.length = from < 0 ? this.length + from : from;
		return this.push.apply(this, rest);
		};
		Array.prototype.indexOf = function(val) {
			for (var i = 0; i < this.length; i++) {
				if (this[i] == val) return i;
			}
			return -1;
		};
		
		Array.prototype.removeElement = function(val) {
			var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
		};
		//增加replaceAll方法
		String.prototype.replaceAll = function(s1,s2) { 
		    return this.replace(new RegExp(s1,"gm"),s2); 
		};
		
		Number.prototype.toPercent = function(n){
			 n = n || 0;
			 return ( Math.round( this * Math.pow( 10, n + 2 ) ) / Math.pow( 10, n ) ).toFixed( n ) + '%';
		};
		this.init();
	},
	getBodyWidth : function () {
		return document.body.clientWidth;
	},
	//初始化
	init : function () {
		this.initPage();
		this.initMcHeader();
		this.initCommonEvent();
	},
	initPage : function () {
		$("body").css("font-family", "微软雅黑");
		$("input[value='查询']").attr("class", "zocial");
		$("input[value='提交']").attr("class", "zocial");
		$("input[value='查看分布图']").attr("class", "zocial");
		$("input[value='查看分布图']").css("background-color", "#8ABA42");
		$("input[value='查看分布图']").css("margin-left", "30px");
		
//		$("button").attr("class", "zocial flattr");
		if($("#miansin").length > 0) {
			$("#subnav-strips").css("margin-left", $("#miansin").position().left + "px");
			$("#subnav-strips").css("border-collapse", "collapse");
		}
	},
	initCommonEvent : function () {
		$("#serach").live("click", function () {
			__common.isLogin();
		});
	},
	initMcHeader : function () {
		$("#mc-header").css("margin-bottom", "7px");
		$("#mc-header tr td").css("word-break", "keep-all");
		$("#mc-header tr td:even").css("text-align", "left");
		$("#mc-header tr td:eq(0)").css("width", "65px");
		$("#mc-header tr td:eq(2)").css("width", "65px");
		
		$("#mc-header tr td b:contains(path)").html("path<span class='colon'>：</span>");
		$("#mc-header tr td b:contains(地域)").html("<b style='float:left;'>地</b><b style='float:right;'>域：</b>");
		$("#mc-header tr td b:contains(时段)").html("<b style='float:left;'>时</b><b style='float:right;'>段：</b>");
		$("#mc-header tr td b:contains(指标)").html("<b style='float:left;'>指</b><b style='float:right;'>标：</b>");
		$("#mc-header tr td b:contains(总量)").html("<b style='float:left;'>总</b><b style='float:right;'>量：</b>");
		$("#mc-header tr td b:contains(日期)").html("<b style='float:left;'>日</b><b style='float:right;'>期：</b>");
		$("#mc-header tr td b:contains(月份)").html("<b style='float:left;'>月</b><b style='float:right;'>份：</b>");
		$("#mc-header tr td b:contains(UV总数)").html("<b style='float:left;'>UV</b><b style='float:right;'>总数：</b>");
//		var width = $("#mc-header tr td b:contains(报表类型)").width();
//		$("#mc-header tr td:even").css("width", width + "px");
		$("#mc-header tr td b:contains(报表类型)").html("<b style='float:right;'>报表类型：</b>");
		$("#mc-header tr td b:contains(选择日期)").html("<b style='float:right;'>选择日期：</b>");
		$("#mc-header tr td b:contains(播控名称)").html("<b style='float:right;'>播控名称：</span>");
		$("#mc-header tr td b:contains(链接到)").html("<b stype='float:left'>链接到</b><b class='colon'>：</span>");
	},
	/**
	 * 报告查询完之后 执行函数
	 */
	reportRender : function () {
		if("__reportEngine" in window) {
			__reportEngine.renderHeaderTips();
		}
		var chartObject = this.getChartObject();
		
		if(chartObject != null) {
			if(reportId && (reportId == "51" || reportId == "54")) {
			} else {
				chartObject.initSwitch();
				chartObject.initChart();
				chartObject.enableSwitchLoction();
			}
		}
		if("__switchLocation" in window) {
			__switchLocation.refreshLocationNoChangedStatus();
			__switchLocation.enableSwitchLoction();
		}
	},
	/**
	 * 找到自定义的图表对象
	 * @returns
	 */
	getChartObject : function () {
		try {
			var chartObject = null;
			for(var prop in window) {//找到图表对象
				if(window[prop] != null && window[prop] != undefined && typeof window[prop] == "object") {
					if("chartIdentify" in window[prop] && prop != "ChartEngine" && prop != "__chartEngine") {
						chartObject = window[prop];
				   }
			    }
			}
		} catch(e) {
			//return null;
		}
		
		return chartObject;
	},
	showObjectAttrs : function (o) {
		var str = "";
		for(var a in o) {
			str += a + "\n";
		}
		alert(str);
	},
	buttonRgb :function (){ 
		ID=arguments[0];
		oRGB=eval(arguments[1]);
		nRGB=eval(arguments[2]);
		var IMG= "";
		for(var i = 20;i > 0;i = i - 2){ 
			RGB="rgb("+(oRGB[0]-1)+","+(oRGB[1]-1)+","+(oRGB[2]-1)+")";
			IMG+="<img style=\"width:100%;height:1px;background:"+RGB+"\">";
		} 
		for(var i = 20; i > 0; i= i - 2){ 
			RGB="rgb("+(nRGB[0]-1)+","+(nRGB[1]-1)+","+(nRGB[2]-1)+")";
			IMG+="<img style=\"width:100%;height:1px;background:"+RGB+"\">"; 
		} 
		eval(ID+".innerHTML=IMG");
	},
	getChartWidth : function () {
		 if(this.chart_width == 0) {
			 this.chart_width =  $("#report").width() + 5; 
		 }
		 var width = Math.floor(this.chart_width / 1.34);
		 if(this.chart_width > 1600) {
			 width = Math.floor(this.chart_width / 1.35);
		 }
		 return width;
	 },
	 /**
	  * 获取百分数
	  * @param a 分子
	  * @param b 分母
	  */
	 getPercent : function(a, b) {
		 if(b == undefined || b == "" || b == null || b == "0") {
			 return "-";
		 }
		 
		 var rate = a / b;
		 var num = new Number(rate);
		 num = num * 100;
		 var percent = num.toFixed(2);
		 return percent;
	 },
	 formateHour : function (hour) {
		//格式化时段分布中的小时显示, 比如 1格式化成 01:00~02:00
		 if(hour < 9) {
		 	 hour = '0' + hour + ':00~0' + (hour + 1) + ":00";
		 } else if(hour == 9)  {
		 	 hour = '0' + hour + ":00~" + (hour + 1) + ":00";
		 } else {
		 	 hour = hour + ':00~' + (hour + 1) + ":00";
		 }
		 return hour;
	 },
	 /*
	  * 获取这段时间内的 相差天数
	  */
	 getPeriodDay :function (startDate, endDate) {
		 var start = new Date(startDate);
		 var end = new Date(endDate);
		 var day = (end.getTime() - start.getTime())/(24*3600*1000);
		 return day;
	 },
	// 判断数组是否包含某个元素
	contains : function(array, element) {
		for(var i = 0; i < array.length; i++) {
			if (array[i] == element) 
				return true;
		}
		return false;
	},
	removeBirtTitleTip : function () {
		__common.removeBirtTitleTipByIframeId("report");
		__common.removeBirtTitleTipByIframeId("chart");
	},
	removeBirtTitleTipByIframeId : function (iframeId) {
		 var report = document.getElementById(iframeId).contentDocument;
		 if(report) {
			 var divList = report.getElementsByTagName("div");
			 for(var i = 0; i < divList.length; i++) {
				 if(divList[i].id == "__BIRT_ROOT") {
					 divList[i].title = ""; 
				 }
			 }
		 }
	},
	//解析日期
	parseDate : function(str) {
		var r = str.match(/^(\d{1,2})(\/)(\d{1,2})\2(\d{1,4})$/);
		if(r==null)
		   return null; 
	    return new Date(r[4], r[1]-1, r[3]);
	},
	//改变每页记录数
	changePagerecord : function(num) {
		var iframe = document.getElementsByTagName("iframe")[0];
		var url = this.updateUrlParam(iframe.src, "recordNum", num);
		num = parseInt(num);
		var height = iframe.style.height.substring(0, iframe.style.height.length - 2);
		height = parseInt(height);
		var nHeight = height + parseInt((num- 20) * 18);
		this.changeIframeHeight(nHeight);
		iframe.src = url;
	},
	//改变iframe 高度
	changeIframeHeight : function (height) {
		if(height < 100) {
			height = 100;
		}
		var iframe = document.getElementsByTagName("iframe")[0]; 
		var oldHeight = iframe.style.height;
		iframe.style.height = height + 'px';
		var chartObject = this.getChartObject();
		if(chartObject) {
			if(reportId && (reportId == "51" || reportId == "54")) {
			} else {
				chartObject.refreshLocationWithNoAnimate();
				chartObject.refreshFootLoction();
			}
		}
		
		//当报表的数据过少时，为防止导出页面与图标重合，调用下面的方法
		if("__switchLocation" in window) {
			__switchLocation.refreshLocationNoChangedStatus();
		}
//		if($("#sift_chart").length > 0 && oldHeight != height && isShowChart == 1) {
//			__siftChart.updateSiftHeight();
//		}
	},
	//更新url参数
	updateUrlParam : function(url, param, paramVal) {
		var array = new Array();
		params = url.substring(url.indexOf("?") + 1, url.length);
		array = params.split("&");
		var recordStr = "";
		for(var i = 0; i < array.length; i++) {
			if(array[i].indexOf(param) == 0) {
				recordStr = array[i];
			}
		}
		if(recordStr.length > 0) {
			url = url.substring(0, url.indexOf(recordStr)) + param +"=" + paramVal + url.substring(url.indexOf(recordStr) + recordStr.length, url.length);
		} else {
			url = url + "&" + param + "=" + paramVal;
		}
		return url;
	},
	//获取默认隐藏列
	getDefaultHideColumns : function () {
		var hideColumns = "";
		for(var i = 0; i < colArray.length; i++) {
			if(colArray[i].isShow == "0") {
				hideColumns += colArray[i].index + ",";
			}
		}
		if(hideColumns.length > 0) {
			hideColumns = hideColumns.substring(0, hideColumns.length - 1);
		}
		return hideColumns;
	},
	getHideColumns : function () {
		
	},
	//判断是否登录
	isLogin : function () {
		if(!ctx) {
			ctx = $("#ctx").val();
		}
		var result = "";
		$.ajax({
			type: "post",
			async:false,
			dataType:'text',
			url: ctx +"/is-login",
			success:function(data) {
				result = data;
			}
		});
//		if(result == 0){
//			alert("session失效，请重新登录");
//			window.location = ctx;
//		}
	},
	//检查开始时间是否小于结束时间
	checkEndTime : function (startTime, endTime) {
		 var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
		  var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
		  if(end<start){  
		      return false;  
		  }  
		  return true;  
	},
	//验证url是否合法
	validateUrl : function (url) {
		var	strRegex = "^("                                                          + 
		"((https|http|ftp|rtsp|mms)?://){1}"                                     + // protocal
		"(([0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+:)?"                          + // ftp-user
		"[0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+@)?"                            + // ftp-password
		"(([0-9]{1,3}\\.){3}[0-9]{1,3}"                                          + // IP
		"|"                                                                      + // or
		"([0-9a-zA-Z_!~\\*'\\(\\)\\-]+\\.)*"                                     + // domain
		"([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\."                         + // domain
		"[a-zA-Z]{2,6})"                                                         + // domain
		"(:[0-9]{1,4})?"                                                         + // port
		"((/?)|"                                                                 + // slash
		"(/[0-9a-zA-Z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#\\-\\[\\]\\|\\{\\}]+)+/?)" + // args
		")+$";
		//var reg = /^(http|https):\/\/[\w\$\-\.\+!\*;\/\?:@=&\|]{3,}$/
		var reg = new RegExp(strRegex);
		return  reg.test(url);
	},
	/**
	 * 格式化图表
	 * @param chart
	 */
	formateChart : function (chart) {
		 Highcharts.setOptions({
	            global: {
	                useUTC: false
	            },
		        lang: {
		        	downloadJPEG : "下载 JPEG 图片",
		        	downloadPDF : "下载 PDF 文档",
		        	downloadPNG : "下载 PNG 图片",
		        	downloadSVG : "下载 SVG 文档",
		        	printChart : "打印图表",
		        	resetZoom : "重置缩放",
		        	contextButtonTitle : "导出按钮"
		        }
	     });
		 
		if(chart.credits) {
			chart.credits.text =  '版权归 Artemis 所有';
		} else {
			chart.credits = {
					text :'版权归 Artemis 所有'
			};
		}
		
		if(chart.chart) {
			chart.chart.zoomType = 'xy';
			chart.chart.animation = {"duration":1100};
			if(chart.chart.style) {
				chart.chart.style.fontFamily = '微软雅黑';
			} else {
				chart.chart.style = {
					fontFamily : '微软雅黑'
				};
			};
		} else {
			chart.chart = {
				zoomType:'xy',
				style : {
					fontFamily : '微软雅黑'
				},
				animation : {"duration":1100}
			}
		}
		
		chart.legend = {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            draggable: true,
            borderWidth: 1,
            borderRadius: 2,
            useHTML:true,
            navigation : {
            	animation :true,
            },
            title: {
                text: '',
                fontWeight: 'normal'
            }
        };
		var itemCount = this.getDisplayDataNums(chart.series);
		if(itemCount < 9) {
			if(!chart.plotOptions) {
				chart.plotOptions = {
					    column: {
					        pointPadding: 0.2,
					        borderWidth: 0,
					        pointWidth: 30
					    }
					}
			} else {
				if(chart.plotOptions.column) {
					chart.plotOptions.column.pointWidth = 30;
				} else {
					chart.plotOptions.column = {
							 pointPadding: 0.2,
						        borderWidth: 0,
						        pointWidth: 30	
					}
				}
			};
		}
	},
	/**
	 * 获取数据显示的数目
	 * @param series
	 */
	getDisplayDataNums : function (series) {
		var count = 0;
		for(var i = 0; i < series.length; i++) {
			if(series[i].visible) {
				count = count + series[i].data.length;
			}
		}
		return count;
	}
});