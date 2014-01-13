ImpSumDateChart = ImpChart.extend({
	series:null,
	categories:null,
	keyValue : null,
	rawData : null,
	constructor : function() {
		__impSumDateChart = this;
		this.mcKeyValueDefine();
		this.prepareData();
		if(this.rawData.length == 0)
			this.initEmptyChart();
		else
			this.initChart();
		this.initEvent();
	},
	initEvent : function () {
	},
	prepareData : function() {
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var id = data.mcBase.id;
		var param = "id=" + id + "&startDate=" + startDate + "&endDate=" + endDate;
		$.ajax({
			type: "post",
			async:false,
			dataType:'json',
			url: ctx + "/mc/imp-report-sum-area-chart?"+param,
			success:function(data) 
			{
				__impSumDateChart.rawData = data;
			}
		});
	},
	/**
	 * 初始化图表
	 * @param areaNames
	 */
	initChart : function () {
        this.dataFormate();
		this.initDateChartUI();
	},

	dataFormate : function() {
		this.series = [];
		this.categories = [];
		var temp = {};
		for(var k in this.rawData)
		{
			if(isNaN(k))
				continue;
			this.categories.push(this.rawData[k].monDate);
			for(var key in this.keyValue)
			{
				if(!temp.hasOwnProperty(key))
					temp[key] = [];
				var val = this.rawData[k][key];
				val = parseInt(val);
				temp[key].push(val);
			}
		}
		for(var k in this.keyValue)
		{
			if(temp.hasOwnProperty(k))
			{
				var struct = {"name":this.keyValue[k], "data":temp[k]};
				this.series.push(struct);
			}
			else
			{
				var struct = {"name":this.keyValue[k], "data":[0]};
				this.series.push(struct);
			}
		}
		
		this.addImpRate(this.series);
		//alert(JSON.stringify(this.series));
	},
	initDateChartUI : function () {
		//var chartWidth = $("#report").width() * 0.98 ;
		
		var chartObj = {
		        chart: {
		            renderTo: "day_chart_container",
		            type: "line",
		            zoomType : "xy",
		            width : ""
		        },
		        title: {
		            text: "播控IMP-日期分布图"
		        },
		        series: this.series,
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
		                	
		                	str = "<b>" + this.series.name + "</b> <br/>" +
		                	      this.x + "：" + str;
		                	return str;
						}
		            },
		        xAxis: {
		            categories: this.categories
		        }
		    };
		var chartCookie = new ChartCookie("imp_day_sum_date_cookie", "执行投放量,执行/设定", chartObj);
		this.formateChart(chartObj);
		new Highcharts.Chart(chartObj);
		this.legendCategory();
	},
	initEmptyChart : function()
	{
		var chartWidth = $("#report").width() * 0.98 ;
		var chartObj = {
		        chart: {
		            renderTo: "day_chart_container",
		            type: "line",
		            zoomType : "xy",
		            width : chartWidth
		        },
		        title: {
		            text: "播控IMP-日期分布图"
		        },
		        subtitle: {
		            text: '无数据',
		        },
		        series: [],
		        yAxis: {},
		        xAxis: {}
		    };
		new Highcharts.Chart(chartObj);
	}
});
