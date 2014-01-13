$(document).ready(function(){
	var adReportSeniorBrw = new AdReportSeniorBrw();
});

var AdReportSeniorBrw = Common.extend({
	
	constructor : function()
	{
		__adReportSeniorBrw = this;
//		try {
			this.base();
			this.refreshReport();
//		} catch (e) {
//			hiAlert("嗯，有麻烦了<br/>请重新提交查询！");
//		}
	},
	refreshReport : function()
	{
		conditionType = "ad";
		reportPath = "adp_stock_sum.rptdesign";
		reportSql = "";
		recordNum = 20;
		var src = ctx + "/frameset?__report=/persistence/ad/"+reportPath+"&__parameterpage=false&__showtitle=false&__toolbar=false&reportSql="
			+reportSql+"&recordNum="+recordNum +"&conditonType="+conditionType;
		$("#report").attr("src", src);
	}
});