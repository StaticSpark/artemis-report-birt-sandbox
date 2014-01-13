/**
 * imp 单多选地域框
 */
ImpChartSingleAreaSelect = ChartSingleAreaSelect.extend({
	constructor : function(id, areaNames) {
		__chart_single_area_imp_chart = this;
		this.base(id, areaNames);
	},
	initEvent : function () {
		if(!("is_called" in window)) {
		    $("td[name='"+this.tdName+"']").live("click", function(){
					var isSelect = $(this).attr("isSelect");
					if (isSelect == "true") {				
					} else {
						$(this).css("background-color",  __chart_single_area_imp_chart.selectedColor);
						$(this).css("border", "inset 1px " + __chart_single_area_imp_chart.borderColor);
						$(this).attr("isSelect", "true");
						$(this).css("color", "#ffffff");
						$(this).find("input[name='"+__chart_single_area_imp_chart.radioName+"']").attr("checked","checked");
					}
					
					$("td[name='"+__chart_single_area_imp_chart.tdName+"']").each(function(i){
						if ($(this).find("input[name='"+__chart_single_area_imp_chart.radioName+"']").attr("checked") == "checked") {				
						} else {
							var isSelect = $(this).attr("isSelect");
							if (isSelect == "true") {
								$(this).css("background-color", __chart_single_area_imp_chart.unSelectedColor);
								$(this).css("border", "solid 1px " + __chart_single_area_imp_chart.borderColor);
								$(this).find("span").css("color", "#000000");
								$(this).attr("isSelect", "false");
							}
						};		
					});
					__impChart.initHourChart(__chart_single_area_imp_chart.getSelectedAreaNames());
			});	
	    }
	}
});