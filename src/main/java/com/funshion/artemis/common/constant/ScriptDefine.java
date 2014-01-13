package com.funshion.artemis.common.constant;

/**
 * 一些通用的脚本定义（基本上是不会改的，经常改的不存在这里，是存在xml里面）
 * 
 * @author shenwf Reviewed by
 */
public class ScriptDefine {
	/**
	 * 求点击率 通用部分脚本
	 */
	public static final String RATE_PERCENT_COMMON_SCRIPT = "\nvar rate = getPercent(clk, pla);\n  \n this.setDisplayValue(rate);rowt = this.getRowData();\nval = rowt.getExpressionValue( \"row[pla]\"); \n if(val==null) {this.getStyle().display = 'none'};";
	/**
	 * 该脚本是用隔行变色
	 */
	public static final String LINE_COLOR_SCRIPT = "count++;\nif( count % 2 == 0 )\n{\nthis.getStyle().backgroundColor=\"#F5FAFA\";\n}\nelse\n{\nthis.getStyle().backgroundColor=\"#FFFFFF\";\n}\nstyle.textAlign = \"center\";"
			+"rowt = this.getRowData();\nval = rowt.getExpressionValue( \"row[mc_detail_percent]\");if(val != null && val == '占比'){this.getStyle().backgroundColor='#c8e3ff';this.getStyle().fontWeight='bold'}";

	/**
	 * 换行
	 */
	public static final String LINE_BREAK_SCRIPT = "rowCount++;\nif(rowCount > 1 && rowCount % recordNum == 1){\nstyle.pageBreakBefore = \"always\";\n} \n";

	/**
	 * 小时级报表换行
	 */
	public static final String HOUR_LINE_BREAK_SCRIPT = "rowCount++;\nif(rowCount % 25 == 0){\nstyle.pageBreakAfter = \"always\";\n} \n";

	/**
	 * 居中
	 */
	public static String TEXT_ALIGN_SCRIPT = "style.textAlign = \"center\"";
	
	/**
	 * 总计标签脚本
	 */
	public static String TOTAL_TEXT_SCRIPT = "rowt = this.getRowData();\nval = rowt.getExpressionValue( \"row[pla]\"); \n if(val==null) {this.getStyle().display = 'none'}; ";

	/**
	 * 根据列名，获取 点击率 脚本
	 * 
	 * @param clk
	 * @param pla
	 * @return
	 */
	public static String getPercentScript(String clk, String pla) {
		String script = "rowt = this.getRowData();\n";
		script += "clk = rowt.getExpressionValue( \"row[" + clk + "]\");\npla = rowt.getExpressionValue( \"row[" + pla + "]\");";
		script += RATE_PERCENT_COMMON_SCRIPT;
		return script;
	}
	
	//--------广告报表导出格式专业化------------------	
	/**
	 * 广告导出报表格式
	 */
	public static String getAdFormatStyle() {	
		String script = "prefix = \"Microlens.funshion.com \";\n";
		script += getReportType();
		script += CURRENT_DATE_SCRIPT;
		script += "start_time = reportContext.getHttpServletRequest().getParameter(\"startTime\")\n";
		script += "end_time = reportContext.getHttpServletRequest().getParameter(\"endTime\")\n";
		script += "report_period = start_time + \" 至 \" + end_time;\n";
		script += getNameList();
		script += AD_DISPLAY_TEXT;
		script += "\n format = reportContext.getHttpServletRequest().getParameter(\"__format\");\n";
		script += "if(format == \"xls\") {this.getStyle().display = 'block';this.text= display_text}\n";
	    script += "else {this.getStyle().display = 'none'};";
	    return script;	
	}
	
	/**
	 * 广告二维报表导出格式
	 */
	public static String getAdTwoDimensionalStyle() {
		String script = "prefix = \"Microlens.funshion.com\";\n";
	    script += getTwoReportType();
	    script += CURRENT_DATE_SCRIPT;
	    script += "start_time = reportContext.getHttpServletRequest().getParameter(\"startTime\")\n";
		script += "end_time = reportContext.getHttpServletRequest().getParameter(\"endTime\")\n";
		script += "report_period = start_time + \" 至 \" + end_time;\n";
		script += getNameList();
		script += AD_DISPLAY_TEXT;
		script += "\n format = reportContext.getHttpServletRequest().getParameter(\"__format\");\n";
		script += "if(format == \"xls\") {this.getStyle().display = 'block';this.text= display_text}\n";
	    script += "else {this.getStyle().display = 'none'};";
		return script;
	}
		
	/**
	 * 广告导出值
	 */
	public static String AD_DISPLAY_TEXT = "display_text = prefix +\" \"+ report_title + \"<br/>\"+ \"导出报表日期： \" + currentDate + \"<br/>\" +\"报表类型：\"+ report_type + \"报告\"+ \"<br/>\"+\"报表分布：\"+ report_distribute + \"<br/>\" + report_type+\"名称：\"+condition_name+"
			+"\"<br/>\" +\"报告时段：\"+report_period;";
	
	/**
	 * 截取特定的字符串（广告、广告位..）
	 */
	public static String getReportType(){
		String script = "subTitle = reportContext.getHttpServletRequest().getParameter(\"subTitle\")\n";
		script += "var index = subTitle.indexOf(\"-\");\n";
		script += "var report_dim = subTitle.substring(index+1);\n";
		script += "var report_title = subTitle.substring(0, index);\n";
		script += "index = report_dim.indexOf(\":\");\n";
		script += "var report_distribute = report_dim.substring(0, index);\n";
		script += "var report_type = report_dim.substring(index+1);\n";
		return script;
	}
	/**
	 * 截取二维分布报表特定的字符串（广告、广告位..）
	 */
	public static String getTwoReportType(){
		String script = "subTitle = reportContext.getHttpServletRequest().getParameter(\"subTitle\")\n";
		script += "var index = subTitle.indexOf(\"-\");\n";
		script += "var report_dim = subTitle.substring(index+1);\n";
		script += "var report_title = subTitle.substring(0, index);\n";
		script += "index = report_dim.indexOf(\":\");\n";
		script += "var report_distribute = report_dim.substring(0, index);\n";
		script += "var report_type = report_dim.substring(index+1);\n";
		return script;
	}
	
	/**
	 * 得到广告/广告位/...列表
	 */
	public static String getNameList() {
		String script = "condition_name = reportContext.getHttpServletRequest().getParameter(\"conditionNames\")\n";
		script += "if (condition_name ==\"\" || condition_name == \"null\")\n {";
		script += "condition_name = \"全部\" +report_type;};\n";
		script += "if(report_type == \"地域\" && (condition_name == \"国家\" || condition_name == \"省份\")){\n"
				+ "condition_name = \"全部\" + condition_name;};\n";
		script += "condition_name = condition_name.replace(\"<s>\", \"\");";
		script += "condition_name = condition_name.replace(\"</s>\", \"\");";
		script += "condition_name = condition_name.replace(\",\", \", \");";
		return script;
	}
    
	//-----------------播控报表导出格式专业化---------------------------------------
	/**
	 * 播控导出格式
	 * @return
	 */
	public static String mcExportReportFormat() {
		String script = mcCommonModel();
		script += whichReportStyle();
		script += "format = reportContext.getHttpServletRequest().getParameter(\"__format\");\n";		
		script += "if(format == \"xls\") {this.getStyle().display = 'block';this.text= display_text;}\n";
	    script += "else {this.getStyle().display = 'none';}";
	    return script;
	}
	
	/**
	 * 判断是哪种报告形式
	 * @return
	 */
	public static String whichReportStyle() {
		String script = "hour = reportContext.getHttpServletRequest().getParameter(\"hour\");\n";
		script += "if (hour && hour != \"null\" && hour !=\"\"){ \n";
		script += MC_HOUR_DISPLAY_TEXT;
		script += "}\nelse if (!hour){ \n";
		script += MC_TOTAL_DISPLAY_TEXT;
		script += "}\nelse{\n";
		script += MC_DAY_DISPLAY_TEXT;
		script += "}";
		return script;
	}
	
	/**
	 * 播控共同的模块
	 * @return
	 */
	public static String mcCommonModel() {
		String script = "prefix = \"Microlens.funshion.com\" + \" 播控-播放点击日期报告\";\n";
		script += CURRENT_DATE_SCRIPT;
		script += "mc_name = reportContext.getHttpServletRequest().getParameter(\"monName\");\n";
		script += "mon_date = reportContext.getHttpServletRequest().getParameter(\"monDate\");\n";
		script += "start_date = reportContext.getHttpServletRequest().getParameter(\"startDate\");\n";
		script += "end_date = reportContext.getHttpServletRequest().getParameter(\"endDate\");\n";
		script += "area_name = reportContext.getHttpServletRequest().getParameter(\"areaName\");\n";
		script += "time_point = reportContext.getHttpServletRequest().getParameter(\"timePoint\");\n";
		script += "total = reportContext.getHttpServletRequest().getParameter(\"value\");\n";
		script += "path = reportContext.getHttpServletRequest().getParameter(\"path\");\n";
		script += "rpt_name = reportContext.getHttpServletRequest().getParameter(\"rptDisplayName\");\n";
		script += "if (path == \"\" || path == \"null\"){path = \"*\";}";
		return script;
	}
	
	/**
	 * 导出报告的当前时间
	 */
	public static String CURRENT_DATE_SCRIPT = "myDate = new Date();\nmonth = (((myDate.getMonth()+1) < 10) ? \"-0\" : \"-\") + (myDate.getMonth()+1);\nday= ((myDate.getDate() < 10) ? \"-0\" : \"-\") + " 
	                                           +"myDate.getDate();\ncurrentDate = myDate.getFullYear() + month + day;\n";
	
	/**
	 * 播控-播放点击日期分天source报表导出值
	 */
	public static String MC_DAY_DISPLAY_TEXT = "display_text = prefix +\"<br/>\"+\"报表导出日期：\"+currentDate+ \"<br/>\"+\"报表类型：播控报表\"+\"<br/>\"+\"播控名称：\"+mc_name+\"<br/>\"+\"报表日期：\"+mon_date +\"<br>\"+\"地域：\"+ area_name +\"<br/>\"+\"总量：\"+ total +\"</br>\"+\"path：\"+path+\"<br/>\"+\"指标：\"+rpt_name;\n";
	
	/**
	 * 播控-播放点击日期小计source报表导出值
	 */
	public static String MC_TOTAL_DISPLAY_TEXT = "display_text = prefix +\"<br/>\"+\"报表导出日期：\"+currentDate+ \"<br/>\"+\"报表类型：播控报表\"+\"<br/>\"+\"播控名称：\"+mc_name+\"<br/>\"+\"报表时段：\"+start_date +\" 至 \" + end_date +\"<br>\"+\"地域：\"+ area_name +\"<br/>\"+\"总量：\"+ total +\"</br>\"+\"path：\"+path+\"<br/>\"+\"指标：\"+rpt_name;\n";

	/**
	 * 播控-用户小时报告分source报表导出值
	 */
	public static String MC_HOUR_DISPLAY_TEXT = "display_text = prefix +\"<br/>\"+\"报表导出日期：\"+currentDate+ \"<br/>\"+\"报表类型：播控报表\"+\"<br/>\"+\"播控名称：\"+mc_name+\"<br/>\"+\"报表日期：\"+mon_date +\"<br>\"+\"时点：\"+time_point+\"<br/>\"+\"地域：\"+ area_name +\"<br/>\"+\"总量：\"+ total +\"</br>\"+\"path：\"+path+\"<br/>\"+\"指标：\"+rpt_name;\n";

}
