/**
 * 图表多选地域选择组件
 */
ChartMultipleAreaSelect = Common.extend({
	areaNames : null,
	selectedAreaNames : null,
	id : null,
	selectedColor : null,
	unSelectedColor : null,
	borderColor : null,
	constructor : function(id, areaNames) {
		__chart_multiple_area_select = this;
		this.selectedColor = "#4F81BD";
		this.unSelectedColor = "#e6e6e6";
		this.borderColor = "#bebebe";
		this.id = id;
		this.areaNames = areaNames;
		this.selectedAreaNames = new Array();
		this.clear();
		this.init();
	},
	init : function () {
		if($("td[name='chart_multiple_area_names']").length == 0) {
			this.initAreaUI();
			this.initEvent();
		}
	},
	clear : function() {
		$("td[name='chart_multiple_area_names']").unbind("click");
		$("#" + this.id).html("");
	},
	getSelectedAreaNames : function () {
		__chart_multiple_area_select.selectedAreaNames = new Array();
		$("td[name='chart_multiple_area_names']").each(function(){
			if($(this).attr("isSelect") == "true") {
				__chart_multiple_area_select.selectedAreaNames.push($(this).html());
			}
		});
		return this.selectedAreaNames;
	},
	initEvent : function () {
		$("td[name='chart_multiple_area_names']").live("click", function(){
			var isSelect = $(this).attr("isSelect");
			if(isSelect == "false") {
				$(this).css("background-color", __chart_multiple_area_select.selectedColor);
				$(this).css("border", "inset 1px green");
				$(this).attr("isSelect", "true");
			} else if(__chart_multiple_area_select.getSelectedAreaNames().length > 1) {
				$(this).css("background-color", __chart_multiple_area_select.unSelectedColor);
				$(this).css("border", "solid 1px");
				$(this).attr("isSelect", "false");
			}
		});
	},
	initAreaUI : function () {
		$("#" + this.id).css("margin", "0 auto");
		$("#" + this.id).css("margin-top", "2px");
		$("#" + this.id).css("border", "1px solid " + __chart_multiple_area_select.borderColor);
		$("#" + this.id).html("");
		var str = "<tr>";
		for(var i = 0; i < this.areaNames.length; i++) {
			if(i > 0 && i  % 15 == 0) {
				str += "</tr><tr>";
			}
			str += "<td name='chart_multiple_area_names' isSelect='false' rel='nofollow' style='text-align:center;background-color:"+ 
			   __chart_multiple_area_select.unSelectedColor+";cursor: pointer;border:solid 1px gray;'>" + this.areaNames[i] + "</td>";
		}
		str += "</tr>";
		$("#" + this.id).html(str);
		
		$("td[name='chart_multiple_area_names']:first").attr("isSelect", "true");
		$("td[name='chart_multiple_area_names']:first").css("background-color", __chart_multiple_area_select.selectedColor);
		$("td[name='chart_multiple_area_names']:first").css("border", "inset 1px " + __chart_multiple_area_select.borderColor);
		$("td[name='chart_multiple_area_names']:first").css("color", "#ffffff");
	}
});