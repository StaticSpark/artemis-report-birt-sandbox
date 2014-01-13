var AdChart = ChartEngine.extend({
	constructor : function() {
		__adChart = this;
		this.base();
	},
	/**
	 * 准备数据
	 */
	prepareData : function () {
		var param = __reportEngine.getChartParam();
		$.ajax({
			type: "post",
			async:false,
			dataType:'json',
			url: ctx + "/ad/ad-chart-data?"+param,
			success:function(data) {
				__adChart.data = data;
			}
		});
	},
	initChartUI : function () {
		ChartEngine.prototype.initChartUI.apply(this); //调用父类
		this.legendCategory();
	},
	/**
	 * 图例分组
	 */
	legendCategory : function () {
		 $("#left_lengend").parent().parent().css("white-space", "nowrap");
		 $("#left_lengend").parent().parent().html("<b>左轴</b>");
		 
		 $("#right_lengend").parent().parent().css("white-space", "nowrap");
		 $("#right_lengend").parent().parent().html("<b>右轴</b>");
	},
	getTitle : function () {
		 var title = "";
		 if(this.categories.length > 0) {
			 title = $("#conditonTypeText").text() + "-" + $("#rptList :selected").html() + "图";
		 }
		 return title;
	},
	getSubTitle : function () {
		 var subTitle = "";
		 if(this.categories.length == 0) {
			 subTitle = "无数据";
		 } 
		 return subTitle;
	}
});