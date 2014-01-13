/**
 * imp 多选地域框
 */
ImpChartMultipleAreaSelect = ChartMultipleAreaSelect.extend({
	constructor : function(id, areaNames) {
		this.base(id, areaNames);
	},
	initEvent : function () {
		$("td[name='chart_multiple_area_names']").unbind("click");
		$("td[name='chart_multiple_area_names']").click(function(){
			var isSelect = $(this).attr("isSelect");
			if(isSelect == "false") {
				$(this).css("background-color", __chart_multiple_area_select.selectedColor);
				$(this).css("color", "#ffffff");
				$(this).css("border", "inset 1px " + __chart_multiple_area_select.borderColor);
				$(this).attr("isSelect", "true");
			} else if(__chart_multiple_area_select.getSelectedAreaNames().length > 1) {
				$(this).css("background-color", __chart_multiple_area_select.unSelectedColor);
				$(this).css("border", "solid 1px gray");
				$(this).attr("isSelect", "false");
				$(this).css("color", "#000000");
			}
			__impChart.initAreaChart(__chart_multiple_area_select.getSelectedAreaNames());
		});
	}
});