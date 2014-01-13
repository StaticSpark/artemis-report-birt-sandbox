ImpReportTest = CommonTest.extend({
	 constructor:function(){
		 this.base();
	 },
	 testCase : function () {
		 test("数据准备测试", function() {
			 ok(mcBase, "测试后端返回json是否正常");
			 ok(reportName.length > 0, "测试名字是否存在");
			 ok(areaInfo.length > 0, "测试是否有地域");
			 ok(path == "*", "测试path 是不是默认为*");
		 });
		 
		 test("ui 初始化测试", function() {
			 var paths = "";
			 $("#pathList option").each(function(i){
				 paths += "," + $(this).html();
			 });
			 paths = paths.substring(4, paths.length);
			 	
			 ok(paths == mcBase.paths, "测试初始化path 下拉框");
			 ok($("#mcName").html() == mcBase.name, "测试播控名字");
			 ok($("option[value='*']").attr("selected") == "selected", "测试 path 为*是否被选中");
			 ok($("#startDate").length > 0, "测试开始时间是否有值");
			 ok($("#endDate").length > 0, "测试结束时间是否有值");
		 });
		 
		 test("页面事件测试", function() {
			 $("#mc_imp_sum_report").click();//点击小计
			 ok(reportName == "mc_imp_sum_report", "小计报告名称测试");
			 ok($("#mc_imp_sum_report").css("color") == "rgb(255, 0, 0)", "颜色改变测试");
			 ok($("#mc_imp_report").css("color") == "rgb(0, 0, 0)", "颜色改变测试");
			 ok($("#startDate").val().length == 0, "小计报告开始时间是否为空");
			 ok($("#endDate").val().length == 0, "小计报告结束时间是否为空");
			 ok($("#pathInfo").css("display") == "none", "小计报告隐藏path");
			 ok(colArray.length == 17, "小计报告列名长度测试");
			 
			 $("#mc_imp_report").click();//点击分天
			 ok(reportName == "mc_imp_report", "分天报告名称测试");
			 ok($("#mc_imp_report").css("color") == "rgb(255, 0, 0)", "颜色改变测试");
			 ok($("#mc_imp_sum_report").css("color") == "rgb(0, 0, 0)", "颜色改变测试");
			 ok($("#startDate").val().length > 0, "分天报告开始时间是否有值");
			 ok($("#endDate").val().length > 0, "分天报告结束时间是否有值");
			 ok($("#pathInfo").css("display") == "inline", "分天报告path出现");
			 ok(colArray.length == 19, "分天报告列名长度测试");
		 });
		 
		 test("查询报告测试", function(){
			 $("#startDate").val("2013-12-31");
			 $("#endDate").val("2013-01-01");
			 ok(!__imp.refreshReport(), "结束时间小于开始时间 测试");
			 $("#popup_ok").click();
			 $("#startDate").val("2013-12-30");
			 $("#endDate").val("2013-12-31");
			 $("#pathList").find("option[value=1]").attr("selected",true);
			 var url = __imp.refreshReport();
			 ok(url.indexOf("startDate=2013-12-30") > -1, "测试选择开始时间是否正确");
			 ok(url.indexOf("endDate=2013-12-31") > -1, "测试选择结束时间是否正确");
			 ok(url.indexOf("path=1") > -1, "测试选择path是否正确");
			 __imp.updateReportTime();
			 $("#pathList").find("option[value='*']").attr("selected",true);
		 });
	 }
});