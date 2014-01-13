/**
 * 图表多选地域选择组件
 */
var a = null;
ChartSingleAreaSelect = Common.extend({
	areaNames : null,
	selectedAreaNames : null,
	id : null,
	tdName : null,
	radioName : null,
	selectedColor : null,
	unSelectedColor : null,
	borderColor : null,
	constructor : function(id, areaNames) {
		__chart_single_area_select = this;
		this.selectedColor = "#4F81BD";
		this.unSelectedColor = "#e6e6e6";
		this.borderColor = "#bebebe";
		this.id = id;
		this.tdName = "chart_single_area_td_"+this.id;
		this.radioName = "chart_single_area_names_"+this.id;
		this.areaNames = areaNames;
		this.selectedAreaNames = new Array();
		this.clear();
		this.init();
		is_called = "true";
	},
	getRadioName : function () {
		return this.radioName;
	},
	getTdName : function () {
		return this.tdName;
	},
	init : function () {
		if($("td[name='"+this.tdName+"']").length == 0) {
		    this.initEvent();
		    this.initAreaUI();
		}
	},
	clear : function() {
		$("#" + this.id).html("");
	},
	getSelectedAreaNames : function () {
		var name = "";
		$("[name='"+this.tdName+"']").each(function(i){
			var isSelect = $(this).attr("isSelect");
			if(isSelect == "true") {
				name = $(this).children().first().next().html().trim();
			}
		});
		return name;
	},
	initEvent : function () {
		alert("必须在子类中重写该方法");
	},
	initAreaUI : function () {
		$("#" + this.id).css("margin", "0 auto");
		$("#" + this.id).css("margin-top", "2px");
		$("#" + this.id).html("");
		$("#" + this.id).css("border", "1px solid " + __chart_single_area_select.borderColor);
		var str = "<tr>";
		for(var i = 0; i < this.areaNames.length; i++) {
			if(i > 0 && i  % 15 == 0) {
				str += "</tr><tr>";
			}
			str += "<td isSelect='false' style='text-align:left;background-color:"+__chart_single_area_select.unSelectedColor+
			    ";border:solid 1px "+__chart_single_area_select.borderColor+
			     ";cursor: pointer;' name='"+this.tdName+"'><input type='radio'" +
					" name='"+this.radioName+"'></input><span>" + this.areaNames[i] + "</span></td>";
		}
		str += "</tr>";
		$("#" + this.id).html(str);	
	    $("#" + this.id).find("td[name='"+ this.tdName+"']:first").attr("isSelect", "true");
	    $("#" + this.id).find("td[name='"+this.tdName+"']:first").find("input[name='"+this.radioName+"']").attr("checked","checked");
	    $("#" + this.id).find("td[name='"+this.tdName+"']:first").css("background-color", __chart_single_area_select.selectedColor);
	    $("#" + this.id).find("input[name='"+this.radioName+"']:first").css("border", "inset 1px " + __chart_single_area_select.borderColor);
	    $("#" + this.id).find("input[name='"+this.radioName+"']:first").next().css("color", "#ffffff");
	}
});