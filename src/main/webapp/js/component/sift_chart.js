var SiftChart = Common.extend({
	siftData : null,//筛选框数据
	index : null,//当前tab索引
	pageNum : null,//当前第几页
	PAGE_DATA_COUNT : null,//每页显示记录数
	selectedIds : null,
	selectedNames : null,
	isShowChart : null,
	constructor : function(siftData) {
		isShowChart = 0;
		index = 0;
		pageNum = 1;
		twinkleCount = 1;
		twinkleTime = 300;
		PAGE_DATA_COUNT = 7;
		selectedIds = new Array();
		selectedNames = new Array(); 
		this.siftData = siftData;
		__siftChart = this;
		this.defaultSelected();
		this.initUI();
		this.initEvent();
		this.updateContent();
	},
	defaultSelected : function () {
		selectedIds = new Array();
		selectedNames = new Array();  
		//默认选中的选项
		for(var i = 0; i < siftData.length; i++) {
			for(var j = 0; j < siftData[i].data.length; j++) {
				if(siftData[i].data[j].selected == "selected") {
					selectedIds.push("sift_data_tag"+i+"_" + siftData[i].data[j].id);
					selectedNames.push("sift_data_tag"+i+"_" + siftData[i].data[j].name);
				}
			} 
		}
	},
	initUI : function () {
		if($("[name='sift_title']").length == 0) {
			for(var i = 0; i < siftData.length; i++) {
				var title = "<a style='margin-left:10px;' name='sift_title' id=sift_title_"+i+" href='javascript:void(0)'>"+siftData[i].title+"</a>";
				$("#sift_switch").append(title);
				if(i < siftData.length - 1) {
					$("#sift_title_" + i).after("&nbsp;&nbsp;|");
				}
			}
			$("#sift_title_0").css("color", "red");
		}
		
		$("#pageNum").val(pageNum);
	 },
	 updateSiftHeight : function () {
		 $("#sift_icon").show();
		 $("#sift_icon").attr("tag", "hide");
		 $("html,body").animate({scrollTop: $("#sift_icon").offset().top}, 500);
		 $("#chart").slideDown(1800, function() {
			 var siftChartTop  = $("#sift_icon").position().top + $("#sift_icon").height();
			 var siftChartLeft = $("#chart").position().left + $("#sift_icon").width() + 20;
			 
			 $("#sift_chart").hide();
			 $("#sift_chart").css("margin", "1px");
			 $("#sift_chart").css("top", siftChartTop + "px");
			 $("#sift_chart").css("left", siftChartLeft + "px");
			 $("#sift_chart").css("position", "absolute");
			 $("#sift_chart" ).draggable();
			 
			 $("#close_chart").css("top", ($("#chart").position().top + 7) + "px");
			 $("#close_chart").css("left", ($("#chart").width() + $("#chart").position().left - $("#close_chart").width() - 15 ) + "px");
			 $("#close_chart").show();
			 
			 __common.removeBirtTitleTip();
			 $("#sift_icon").attr("isClick", "true");
			 $("html,body").animate({scrollTop: $("#sift_icon").offset().top}, 1000);
		 });
	 },
     toChart : function () {
    	 isShowChart = 1;
    	 twinkleCount = 0;
    	 this.updateSiftHeight();
	 },
	 hideChartAndSift : function () {
		 $("#sift_icon").attr("tag", "hide");
		 $("#sift_icon").hide();
		 $("#sift_chart").hide();
		 $("#chart").hide();
		 $("#close_chart").hide();
	 },
	 showChart : function () {
		 if(isShowChart == 1) {
			 this.updateSiftHeight();
			 $(this).css("-moz-transform", "");
		 }
	 },
     hideChart : function () {
    	$("#chart").hide();
    	$("#sift_icon").hide();
		this.hide();
		$(this).css("-moz-transform", "rotate(-45deg)");
	 },
	 initEvent : function () {
		function removeImageRound() {
			 var report = document.getElementById("report").contentDocument;
			 if(report && twinkleCount++ < 4) {
				 var view_chart = report.getElementById("view_chart");
				 view_chart.className = "border-around";
				 setTimeout(addImageRound, twinkleTime);
			 };
		}
		
		function addImageRound() {
			 var report = document.getElementById("report").contentDocument;
			 if(report && twinkleCount++ <= 4) {
				 var view_chart = report.getElementById("view_chart");
				 view_chart.className = "";
				 setTimeout(removeImageRound, twinkleTime);
			 };
		}
		 $("#close_chart").click(function(){
			 isShowChart = 0;
			 $("#chart").slideUp("slow");
			 $(this).attr("tag","hide");
			 $(this).hide();
			 $("#sift_icon").hide();
			 $("#sift_chart").hide();
			 $("#sift_icon").attr("isClick", "false");
			 var report = document.getElementById("report").contentDocument;
			 if(report) {
					var view_chart = report.getElementById("view_chart");
					view_chart.className = "border-around";
					setTimeout(removeImageRound,twinkleTime);
			  }
		 });
		 $("[name='sift_title']").live("click", function () {
			 $("[name='sift_title']").each(function(i) {
			     $(this).css("color", "black");
			 });
			 $(this).css("color", "red");
			 pageNum = 1;
			 $("#pageNum").val(pageNum);
			 var id = $(this).attr("id");
			 index = id.substring("sift_title_".length);
			 $("#sift_tables").children().each(function(){
				 var tableId = $(this).attr("id");
				 if(tableId == "sift_table_tag" + index) {
					 $(this).show();
				 } else {
					 $(this).hide();
				 }
			 });
			 __siftChart.updateContent();
		 });
		 
		 $("#sift_icon").click(function() {
			 if($("#sift_icon").attr("isClick") == "true") {
				 if($(this).attr("tag") == "show") {
					 $("#sift_chart").hide();
					 $(this).attr("tag", "hide");
					 $(this).attr("class", "");
				 } else if($(this).attr("tag") == "hide") {
					 $("#sift_chart").show();
					 $(this).attr("tag", "show");
					 $(this).attr("class", "border-around");
				 }
			 } else {
				 $.Prompt("图表正在加载中，请稍等...", 3000);
			 }
 		 });
		 
		 $("[name='chart_display_control']").click(function(){
				if($(this).attr("id") == "show_chart") {
					$("#chart").show();
					__siftChart.show();
				} else {
					$("#chart").hide();
					__siftChart.hide();
				}
		 });
		 
		 $("img").click(function() {
			 var id = $(this).attr("id");
			 var currentDataNum = __siftChart.getCurrentDataNum();
			 if(id == "first_page") {
				 pageNum = 1;
			 }  else if(id == "last_page") {
				pageNum = Math.ceil(currentDataNum / PAGE_DATA_COUNT);
			 } else if(id == "previous_page") {
				 pageNum = pageNum - 1;
				 pageNum = pageNum > 0 ? pageNum : 1;
			 } else if(id == "next_page") {
				 if(pageNum * PAGE_DATA_COUNT < currentDataNum) {
				    pageNum = pageNum + 1;
				 }
			 } 
			 __siftChart.updateContent();
			 $("#pageNum").val(pageNum);
		 });
		 
		 $("[name^='sift_data_tag']").live("click", function() {
			 if($(this).attr("checked") == "checked") {
				 selectedIds.push($(this).attr("id"));
				 selectedNames.push($(this).attr("mark"));
			 } else {
				 selectedIds.removeElement($(this).attr("id"));
				 selectedNames.removeElement($(this).attr("mark"));
			 }
		 });
	 },
	 twinkle : function () {
		 var report = document.getElementById("report").contentDocument;
		 if(report) {
				var view_chart = report.getElementById("view_chart");
				view_chart.className = "border-around";
				setTimeout(removeImageRound,twinkleTime);
		  }
	 },
	 updateContent : function () {
		 this.clearTable();
		 for(var i = 0; i < siftData.length; i++) {
			 var table = document.createElement("table");
			 table.id = "sift_table_tag" + i;
			 if(i == index) {
				 table.style.display = "block";
			 } else {
				 table.style.display = "none";
			 }
			 
			 document.getElementById("sift_tables").appendChild(table);
			 var data = siftData[i].data;
			 var start = (pageNum - 1) * PAGE_DATA_COUNT;
			 var end = pageNum * PAGE_DATA_COUNT;
			 end = end > data.length ? data.length : end;
			 for(var n = start; n < end; n++) {
				 this.addDataToTable("tag" + i, data[n].id, data[n].name);
			 }
		 }
	 },
	 //获取所选id数组 
	 getSelectedIds : function () {
		 var results = new Array();
		 for(var i = 0; i < siftData.length; i++) {
			 var tag = "sift_data_tag" + i + "_";
			 var subResult = new Array();
			 for(var n = 0; n < selectedIds.length; n++) {
				 if(selectedIds[n].indexOf(tag) > -1) {
					 subResult.push(selectedIds[n].substring(tag.length));
				 }
			 }
			 results.push(subResult);
		 }
		 return results;
	 },
	 //获取所选名字数组 
	 getSelectedNames : function () {
		 var results = new Array();
		 for(var i = 0; i < siftData.length; i++) {
			 var tag = "sift_data_tag" + i + "_";
			 var subResult = new Array();
			 for(var n = 0; n < selectedNames.length; n++) {
				 if(selectedNames[n].indexOf(tag) > -1) {
					 subResult.push(selectedNames[n].substring(tag.length));
				 }
			 }
			 results.push(subResult);
		 }
		 return results;
	 },
	 /**
	  * 添加数据到面板中
	  * @param id
	  * @param name
	  */
	 addDataToTable : function (tag, id, name) {
		var str = "<tr><td><input name='sift_data_"+tag+"' mark='sift_data_" + tag + "_" + name + "'  type='checkbox' id='sift_data_" + tag + "_" + id + "'></td><td>"+name+"</td></tr>";
		$("#sift_table_" + tag).append(str);
		if(selectedIds.indexOf("sift_data_" + tag + "_" + id) > -1) {
			$("#sift_data_" + tag + "_" + id).attr("checked", "checked");
		}
	 },
	 //获取当前所选tab的最大数
	 getCurrentDataNum : function () {
		 return siftData[index].data.length;
	 },
	 /**
	  * 清理表格
	  */
	 clearTable : function () {
		 $("#sift_tables").html("");
	 },
	 /**
	  * 清理数据
	  */
	 clearTableData : function () {
		selectedIds = new Array();
		selectedNames = new Array();
	 },
	 setSiftData : function (siftData) {
		 this.siftData = siftData;
	 },
	 hide : function () {
		 $("#sift_chart").hide();
	 },
	 show : function () {
		 $("#sift_chart").show();
	 }
});