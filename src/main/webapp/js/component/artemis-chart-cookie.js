var ChartCookie = Common.extend({
	/**
	 * cookie 标记 每个页面的标记
	 */
	cookieTag : null,
	/**
	 * 
	 * @param cookieTag cookie 标志，每个页面都需要一个独特的标志，与其他页面的cookie区分开来
	 * @param quotas 指标， 默认需要展现的指标
	 * @param chartObj 图表对象，定义的图表对象
	 */
	constructor : function(cookieTag, quotas, chartObj) {
		this.cookieTag = cookieTag;
		this.initCookie(quotas);
		this.hideSeriesAccordanceToCookie(chartObj.series);
		if(chartObj.plotOptions){
			if(chartObj.plotOptions.series) {
				chartObj.plotOptions.series.events = {
						legendItemClick: this.refreshCookie(this.cookieTag)
				};
			} else {
				chartObj.plotOptions.series = {
			            	   events : {
			            		   legendItemClick: this.refreshCookie(this.cookieTag)
			            	   }
				};
			}
		} else {
			chartObj.plotOptions  = {
            	allowPointSelect :true,
            	series : {
            	   events : {
            		   legendItemClick: this.refreshCookie(this.cookieTag)
            	   }
            	}
			};
		}
	},
	/**
	 * 指标集合 以,分割开来
	 * @param quotas
	 */
	initCookie : function (quotas) {
		var userName = $.trim($("#userName").text());
		this.cookieTag = userName + this.cookieTag;
		var userQuotas = $.cookie(this.cookieTag);
		if(userQuotas == undefined || userQuotas == "") {
			$.cookie(this.cookieTag, quotas, {expires: 7});
		} 
		
		$.cookie(this.cookieTag, userQuotas, {expires: 30});
	},
	/**
	 * 根据cookie 隐藏 部分曲线
	 */
	hideSeriesAccordanceToCookie : function (series) {
		var userQuotas = $.cookie(this.cookieTag).toString() + "";
		var userQuotasArray = new Array();
		userQuotasArray = userQuotas.split(",");
		for(var i = 0; i < series.length; i++) {
			if(userQuotasArray.indexOf(series[i].name) == -1 ) {
				series[i].visible = false;
			} else {
				series[i].visible = true;
			}
		}
	},
	/**
	 * 刷新cookie
	 */
	refreshCookie : function (cookieTag) {
		return function(event) {
			 var userQuotas = $.cookie(cookieTag).toString() + "";
			 var userQuotasArray = new Array();
			 userQuotasArray = userQuotas.split(",");
			 if(this.visible) {//去掉该指标
				 userQuotasArray.removeElement(this.name);
			 } else {//添加该指标
				 userQuotasArray.push(this.name);
			 }
			 $.cookie(cookieTag, userQuotasArray.toString(), {expires: 30});
		};
	}
});