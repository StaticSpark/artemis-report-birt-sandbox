/**
 * uv 单选地域框
 */
UvChartSingleAreaSelect = ChartSingleAreaSelect.extend({
    constructor: function (id, areaNames) {
    	__chart_single_area_select_uv_chart = this;
        this.base(id, areaNames);
    },
    init: function () {
        this.initEvent();
        __chart_single_area_select.initAreaUI();
    },
    initEvent: function () {
    	$("td[name='"+this.tdName+"']").live("click", function(){
			var isSelect = $(this).attr("isSelect");
			if (isSelect == "true") {				
			}else {
				$(this).css("color", "#ffffff");
				$(this).css("background-color", "#9F4D95");
				$(this).css("border", "inset 1px green");
				$(this).attr("isSelect", "true");
				$(this).find("input[name='"+__chart_single_area_select_uv_chart.radioName+"']").attr("checked","checked");
			}
			
			$("td[name='"+__chart_single_area_select_uv_chart.tdName+"']").each(function(i){
				if ($(this).find("input[name='"+__chart_single_area_select_uv_chart.radioName+"']").attr("checked") == "checked") {				
				}else {
					var isSelect = $(this).attr("isSelect");
					if (isSelect == "true") {
						$(this).css("background-color", "#D1E9E9");
						$(this).css("border", "solid 1px");
						$(this).attr("isSelect", "false");
						$(this).find("span").css("color", "#000000");
					}
				}		
			});
		});	
    	
        $("td[name='"+this.tdName+"']").live("click", function () {
            __uvChart.renderUVChart(__chart_single_area_select_uv_chart.getSelectedAreaNames());
        });
    }
});