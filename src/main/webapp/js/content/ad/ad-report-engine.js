/* 当前菜单及次级菜单不能为空 */
var current_nav = 3;
var current_subnav = 8;
$(document).ready(function() {
   adReportEngine = new AdReportEngine();
});
//主界面
var AdReportEngine = Common.extend({
	constructor : function() {
		__reportEngine = this;
		try {
			this.base();
			this.dataPrepare();
			this.initDimensionData();
			this.initUI();
			this.initEvent();
			this.refreshReport();
			this.initSiftData();
			new AdChart();
		} catch (e) {
			hiAlert("嗯，有麻烦了<br/>请重新提交查询！");
		}
	},
	// 数据准备
	dataPrepare : function() {
	    a = ['国家分布', '省份分布', '城市分布', '地域分布'];
		startDate = dataJson.startDate;
		endDate = dataJson.endDate;
		conditonTypeText = dataJson.conditonTypeText;		
		conditionNames = dataJson.conditionNames;
		reportId = dataJson.reportId;
		ids= dataJson.ids;	
		conditonType = dataJson.conditonType;
		freq = dataJson.freq;
		areaIds = dataJson.areaIds;	
		$("#dimarea").val(areaIds);//存储地域ids
		
		conditionParam = dataJson.conditionNames;

		$("#conditionNames").html(conditionNames);
		conditonTypeTextHeader = conditonTypeText.substring(0,conditonTypeText.length - 2);
		
		if(a.indexOf(conditonTypeText.substring(0, 2) + "分布") > -1) {
			$("#conditonTypeText").html("地域报告");
			$("#conditonTypeTextHeader").html("地域");
		} else {
			$("#conditonTypeText").html(conditonTypeText);
			$("#conditonTypeTextHeader").html(conditonTypeTextHeader);
		}
			
		$("#s_datetime").val(startDate);
		$("#e_datetime").val(endDate);
		
		reportPath = dataJson.reportPath;

		exportName = "";
		reportName = dataJson.reportName;
		reportSql = "";	
		
		rptList = new Array();
		rptList = dataJson.rptList;
		ctx = $("#ctx").val();
	},
	//初始化UI
	initUI : function () {
		//初始化选择框
		this.initializeSelect();
		
		//让所选的报告处于选中状态
		$("#rptList").find("option[value="+reportId+"]").attr("selected",true);	
		var nameList = $("#conditionNames").html();
		if(nameList ==""){//如果没有选择条件，那就选上全部
			$("#conditionNames").html("全部" + conditonTypeTextHeader);		
		}
		
		//若是二维报表，则隐藏该标签
		if (reportPath.contains("#")) {
			$("#rpt_id").hide();
		}
		
		//当选择为频次分布时，频次周期出现
		if(reportName.indexOf("freq") > -1) {
			$("#choseTime").hide();
			$("#freqPeriod").show();	
			
		}
		
		
		//如果名字太长，则剩余部分不显示
		if(conditionNames.length > 70) {
			var indexA = conditionNames.indexOf(",");
			conditionNames = conditionNames.substring(0, indexA) + "<span style='display:none'>"+ conditionNames.substring(indexA, conditionNames.length)+"</span>" + " 等";
			$("#conditionNames").html(conditionNames);
		}
		
		//让所选的频次周期为选中状态
		$("[name='reports_freq']").each(function(i){
			var val = $(this).val();	
			//var freq = $("#freq").val();
			if(val == freq) {
			    var checkObj = $(this);
	  	        checkObj[0].checked = true;
			}
		});
			
		//只有条件是广告位，才会显示广告位频次报告
		if(conditonType != "adp") {
			$("#rptList").children().each(function(i){
				if($(this).html() == "广告位频次分布"){
					$(this).hide();
				}
			});
		} else {
			$("#rptList").children().each(function(i){
				if($(this).html() == "广告频次分布"){
					$(this).hide();
				}
			});
		}
		//如果是选择的地域分布中的国家，则隐藏客户、销售、题材、产地分布
		if (conditonType == "area") {			
			$("#rptList").children().each(function(i){
				if($(this).html() == "客户分布"){
					$(this).hide();
				}
				if($(this).html() == "销售分布"){
					$(this).hide();
				}
				if($(this).html() == "题材分布"){
					$(this).hide();
				}
				if($(this).html() == "产地分布"){
					$(this).hide();
				}
			});			
		}		
		//标题样式
		$("#subnav-strips").css({"background":"#F5FAFA", "margin-left":"3px", "text-align":"center"});
		xPox = -60;
		yPox = 250;
		
		if(reportId != "51" && reportId != "54") {
			$("#report").css("position", "absolute");
			$("#switchLoction").show();
			$("#search").show();
			$("#search2").hide();
		} else {
			$("#switchLoction").hide();
			$("#search2").show();
			$("#search").hide();
		}
	},
	initDimensionData : function () {
		idGroup = dataJson.idGroup;	
		firstDisplayName = dataJson.firstColumn;
		secondDisplayName = dataJson.secondColumn;
		subReportPath = new Array();
		if (reportPath.contains("#")) {
			var twoReportPath = new Array();
			twoReportPath = reportPath.split("#");
			for (var i = 0;i < twoReportPath.length;i++) {
				var index = twoReportPath[i].indexOf(".rptdesign");
				var sub_path = twoReportPath[i].substring(0,index);
				 for(var j = 0; j < rptList.length;  j++) {
					 if (rptList[j].rptName == sub_path) {
						 subReportPath.push(rptList[j].rptDisplayName);
					 }
				 }
			}		
		}
	},
	initEvent : function () {
		$("#s_datetime").click(function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd HH:mm:ss',
				alwaysUseStartDate : false
			});
		});
		
		$("#e_datetime").click(function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd HH:mm:ss',
				alwaysUseStartDate : false
			});
		});
		
		$("#area-cdt-input").live("mouseover",function(){
			if(($("#area-cdt-input").val()) != "") {
				showAreaInfo(this,($("#area-cdt-input").val()));
			}
		});
		$("#area-cdt-input").live("mouseout",function(){
			__reportEngine.hiddenTip();
		});
		
		$("#refresh_chart").click(function(){
			__reportEngine.refreshChart();
		});
		
		$("[name='search']").click(function(e){		
			ids = dataJson.ids;
			if(reportPath.contains("two_dimensional_report")) {
				reportId = '51';//rpt_base中的id		
				reportText ='';
			}else if(reportPath.contains("two_dimensional_special_report")){
				reportId = '54';//rpt_base中的id		
				reportText ='';
			}else {
				reportId = $("select option:selected").val();//rpt_base中的id
				reportText = $("select option:selected").text();	
				reportPath = $("select option:selected").attr("path");
			}		
			reportName = reportPath.substring(0, reportPath.indexOf("."));
			conditonType = dataJson.conditonType;
			conditonTypeText = $("#conditonTypeText").html();
			
			conditionNames = $("#conditionNames").html();	
			startDate = $("#s_datetime").val();
	    	endDate = $("#e_datetime").val();
	    	freq = $(":radio[name='reports_freq']:checked").val();			
	    	areaIds = $("#dimarea").val();
	    	
	    	 if(!checkEndTime(startDate, endDate)) {
	    		 hiAlert("结束时间不得小于开始时间!");
		    	 return;
	    	 }
	    	
	    	//由其他报表切换到地域报表时，判断已选择地域否
	    	var isAreaSelected = true;
			if(reportText == "地域分布" && areaIds == "") {
				hiConfirm('请选择地域 !', '确认框', function(r) {
					if(r) {
						isAreaSelected = true;
						window.location.href = ctx + "/ad-report-condition";
					} else {
						isAreaSelected = false;
					}
				});
				
				e = e | window.event;
				e.stopPropagation(); 
				e.preventDefault(); 
			} else if((reportText == "广告频次分布" || reportText == "广告位频次分布") && areaIds != "") {
				isAreaSelected = false;
				hiAlert("暂无数据，需清空地域");
			}
	 		if(isAreaSelected == true) {
	 			__reportEngine.refreshReport();
	 		}
		});
		
		//当选择为频次报告时，时段值消失，反之，则出现	
		$("#rptList").live("change",function(){
			$("#reportId").val($("#rptList :selected").val());
			var name = $("#rptList :selected").text();
			var lastName= name.substring(name.length - 4, name.length);
			if(lastName == '频次分布') {
				$("#choseTime").hide();
				$("#freqPeriod").show();
			} else {
				$("#choseTime").show();
				$("#freqPeriod").hide();
			}
		});
		
		$("#conditionNames").mouseover(function() {
			__reportEngine.showInfo();
		});
		
		$("#conditionNames").mouseout(function() {
			__reportEngine.hiddenTip();
		});
	},
	refreshChart : function () {
//		var selectedQuotaNames = __siftChart.getSelectedIds()[0];
//		var selectedQuotaDisplayNames = __siftChart.getSelectedNames()[0];
//		if(selectedQuotaNames.length == 0) {
//			 hiAlert("请选择指标！");
//			 return;
//		}
//		var param = "&ids=" +ids + "&conditonType=" + conditonType + "&startTime=" +  startDate + "&endTime=" + endDate + "&areaIds="+areaIds +"&quotas="+selectedQuotaNames.toString() 
//		+ "&quotaNames=" + selectedQuotaDisplayNames.toString() ;
//		var chartSrc = ctx +"/frameset?__report=persistence/ad/ad_time_chart.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__toolbar" +
//				"=false&__navigationbar=false" + param;
//		$("#chart").attr("src", chartSrc);
	},
	getChartParam : function () {
		var param = "ids=" +ids + "&reportId=" + reportId + "&conditonType=" + conditonType + "&startTime=" +  startDate +
		   "&endTime=" + endDate + "&freq=" +freq+ "&areaIds="+areaIds;
		return param;
	},
	refreshReport : function () {
		var recordNum = 20;
		if (reportPath == 'hour_report.rptdesign') {
			recordNum = 30;
		}
		//二维报表隐藏报表切换
		if (reportPath.contains("#")) {
			$("#rpt_id").hide();
		}
		//报告类型
		rptSelected = $("#rptList :selected").html();
		
		
		//国家，省份，城市，地域 被归入地域分布
		if(a.indexOf(conditonTypeText.substring(0, 2) + "分布") > -1) {
			conditonTypeTextHeader =  '地域';
		} 
		//标题
		var subTitle = conditonTypeTextHeader + '流量报表';
		//二维组合报表 表头显示样式
		if (reportPath.contains("#") || reportPath.contains("two_dimensional"))		
			//二维分布的报告分布名称
			rptSelected = subReportPath[0].substring(0,subReportPath[0].length-2)+"、"+subReportPath[1].substring(0,subReportPath[1].length-2)+'二维分布';
		

		//地域报告中，选择 国家 或省份分布的情况，  将国家，省份传给birt
		if(conditonType == "area" && conditionParam == "")
		{
			conditionParam = $("#conditionNames").html().substring(2);
		}
		//报告分布与报表切换联动
		$("#reportDim").html(rptSelected);
		//报告标题内容替换
		$("#subnav-strips").html("<span class='sub-title'><b>"+subTitle+"</b></span>");
		//将标题传给birt供报表导出用
		subTitle = subTitle + "-" + rptSelected + ":" + conditonTypeTextHeader;
		
		//广告报告 添加浏览量指标等字段
		if((conditonType == "ad" && reportPath == "ad_report.rptdesign") || (conditonType == "ad" && reportPath == "ad_report_quota.rptdesign"))
		{
			var sDate = startDate.split(" ");
			var eDate = endDate.split(" ");
			if(sDate[0] == eDate[0] && sDate[1] == "00:00:00" && eDate[1] == "23:59:59" && areaIds == "")
			{
				reportPath = "ad_report_quota.rptdesign";
				reportId = "5";
				reportName = "ad_report_quota";
			}
		}
		var param = "&ids=" +ids + "&reportId=" + reportId + "&conditonType=" + conditonType + "&startTime=" +  startDate +
		   "&endTime=" + endDate + "&freq=" +freq+ "&areaIds="+areaIds + "&subTitle="+subTitle+"&conditionNames="+conditionParam;
		src = ctx + "/frameset?__report="+reportPath+"&__parameterpage=false&__showtitle=false&__toolbar=false&reportSql="+reportSql+"&recordNum="+recordNum+"&exportName="+exportName + param;
		
		//创建二维组合报表
		if (reportPath.contains("#") || reportPath.contains("two_dimensional")) {
			reportPath = "two_dimensional_report.rptdesign";		
			//如果为广告分布以及日期分布的组合，则采用该reportPath
			if (conditonType == 'ad' && (idGroup == '10,1' || idGroup == '1,10')) {
				reportPath = "two_dimensional_special_report.rptdesign";
			}
			src = ctx + "/frameset?__report="+reportPath+"&__parameterpage=false&__showtitle=false&__toolbar=false&reportSql="+reportSql+"&recordNum="+recordNum+"&exportName="+exportName + param+"&reportName="+reportName+"&idGroup="+idGroup+"&firstDisplayName="+firstDisplayName+"&secondDisplayName="+secondDisplayName;
		}
		ws_src = src.replace(reportPath, "persistence/json.rptdesign");
		$("#report").attr("src", src);
		$("#ws-report").attr("src", ws_src);
	},

    renderHeaderTips: function () {
        var if_obj = document.getElementById("report").contentWindow;
        var nodeList = $(if_obj.document).find(".style_5 > a , .style_4 > a");
        for (var i = 0; i < nodeList.length; i++) {
            $(nodeList[i]).mouseenter(function (e) {
                var x = $(this).offset().left;
                var y = $(this).offset().top;
                var titleName = $(this).text();
                if(titleName.indexOf("↑") != -1|| titleName.indexOf("↓") != -1){
                    titleName = titleName.substring(0,titleName.length-1);
                }
                showAdReportTitleTips(x, y, titleName);
            });
            $(nodeList[i]).mouseleave(function () {
                    hideAdReportTitleTips();
                }
            );
        }
    },

	//初始化报表切换下拉框
	initializeSelect : function() {
		var optionStr = "<option value=''></option>";
	    for(var i = 0; i < rptList.length;  i++) {
	    	if(rptList[i].isShowInCustomPage == "1") {
	    		if(a.indexOf(rptList[i].rptDisplayName) > -1 && a.indexOf(conditonTypeText.substring(0, 2) + "分布") > -1 ) {//地域报告点过来的
	    			if(rptList[i].rptDisplayName.substring(0, 2) == conditonTypeText.substring(0, 2)  || conditonTypeText.substring(0, 2) == "地域") {
	    				
	    				optionStr = "<option path='" + rptList[i].path + "' value='"+rptList[i].id+"'>"+rptList[i].rptDisplayName+"</option>";
	        			$("#rptList").append(optionStr);
	    			}
	    		}else{
	    			if(conditonType == "area")
	    			{
	    				if(rptList[i].rptDisplayName == "客户分布" || rptList[i].rptDisplayName == "销售分布"
	    					|| rptList[i].rptDisplayName == "题材分布" || rptList[i].rptDisplayName == "产地分布")
	    					continue;
	    			}
	    			//客户 和销售报告 隐藏题材 产地分布 否则 隐藏 客户 及 销售分布
	    			else if(conditonType == "acc" || conditonType == "sale")
	    			{
	    				if(rptList[i].rptDisplayName == "题材分布" || rptList[i].rptDisplayName == "产地分布")
	    					continue;
	    			}
	    			else
	    			{
	    				if(rptList[i].rptDisplayName == "客户分布" || rptList[i].rptDisplayName == "销售分布")
	    					continue;
	    			}
	    				
	    			optionStr = "<option path='" + rptList[i].path + "' value='"+rptList[i].id+"'>"+rptList[i].rptDisplayName+"</option>";
	    			$("#rptList").append(optionStr);
	    		}
	    	}
	    }
	},
	initSiftData : function () {
		 siftData = 
		    [
			  {
		    	  "title":"指标",
			      "data":
			    	  [
			            {"id": "brw", "name": "浏览量","selected" : "selected" },
			            {"id": "pla", "name": "播放量"},
			            {"id": "effe_pla", "name": "有效播放量"},
			            {"id": "full_pla", "name": "完整播放量"},
			            {"id": "clk", "name": "点击量"},
			            {"id": "rate", "name": "点击率"}
			         ]
			  }
		    ];
	 },

	//地域组件
	showAreaInfo : function (adObj, info) {
		var v = adObj.getBoundingClientRect();
		var tip = document.getElementById("tipInfo");
		tip.style.display = 'block';
		tip.style.margin = '100px';
		var y = v.top - 80;
		var x = v.left - 160;
		tip.style.top = y + 'px';
		tip.style.left = x + 'px';
		$("#tipInfo").html(info);
	},
	//显示悬浮层
	showInfo : function () {
		var left =  $("#conditionNames").position().left;
		var top =  $("#conditionNames").position().top;
		
		var tip = document.getElementById("tipInfo");
		tip.style.display='block';
		tip.style.margin='100px';
		var x = left - 100;
		var y = top - 70;
		tip.style.top= y +'px';
		tip.style.left= x + 'px';
		
		var conditionNames = $("#conditionNames").html();
		conditionNames = conditionNames.replace("display:none","");
		conditionNames = conditionNames.replace("...","");
		var nameLength = conditionNames.length;
		var rowLength = 70;
		var row = nameLength / rowLength;
		var displayName = "";
		for(var i = 0; i < row + 1; i++) {
			displayName = displayName  + conditionNames.substring(i * rowLength, (i+1) * rowLength) + "<br/>";
		}
		
		$("#tipInfo").html(displayName);
	},
	//隐藏悬浮窗
	hiddenTip : function (){
		var tip = document.getElementById("tipInfo");
		tip.style.display='none';
	}
});