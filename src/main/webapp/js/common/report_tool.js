//得到2个数的百分比 param1 分子 param2 分母
function getPercent(mol, den) {
	importPackage(Packages.java.text); 
	if (den == 0 || den == null || den < 0) {
		return "-";
	}

	if (den == null || den == "") {
		return "";
	}

	percent = mol / den;
	percent = percent * 100;
	result = new java.math.BigDecimal(percent).setScale(2,java.math.BigDecimal.ROUND_HALF_UP).toPlainString();
	return result + "%";
}

/**
 * 根据id获取地域名字（处理特殊地域）
 * @param areaId
 * @returns
 */
function getAreaName(areaId){
	request = reportContext.getHttpServletRequest( );
	var areaName = new com.funshion.artemis.mc.service.McAreaManager().getAreaName(request,areaId);
	return areaName;
}

//imp 报告上 数字 点击链接
function mcImpUrl(type, isSum, value) {
	if(value <= 0) {
		return javascript;
	}
	
	var totalValueUrl = "";
	if(isSum == 1) {
		totalValueUrl = "imp_fwd=" + row["impFwdSum"] + "&clk_fwd=" + row["clkFwdSum"] + "&act_imp=" + row["actImpSum"] + "&act_clk=" + row["actClkSum"] + "&imp_block=" + row["impBlockSum"]
		+  "&path=&clk_block=" + row["clkBlockSum"] +"&imp_choke=" + row["impChokeSum"] +"&clk_choke=" + row["clkChokeSum"];
	} else {
		totalValueUrl = "imp_fwd=" + row["impFwd"] + "&clk_fwd=" + row["clkFwd"] + "&act_imp=" + row["actImp"] + "&act_clk=" + row["actClk"] + "&imp_block=" + row["impBlock"]
		+  "&path="+ row["path"]+ "&clk_block=" + row["clkBlock"] +"&imp_choke=" + row["impChoke"] +"&clk_choke=" + row["clkChoke"];
	}
	var url  = params["ctx"].value +"/mc/imp-source-report?monId=" + row["monId"] + "&areaName=" + row["areaName"] + "&areaId=" +row["areaId"] + "&monDate=" + 
	row["monDate"] +"&type="+ type + "&" + totalValueUrl+ "&areaType=" + row["area_type"] + "&areaGroupName=" + params["areaGroupName"].value;
	return url;
}

//imp 小时报告上 数字 点击链接
function mcImpHourUrl(type, value) {
	if(value <= 0) {
		return javascript;
	}
	var totalValueUrl = "imp_fwd=" + row["impFwd"] + "&clk_fwd=" + row["clkFwd"] + "&act_imp=" + row["actImp"] + "&act_clk=" + row["actClk"] + "&imp_block=" + row["impBlock"]
		+  "&path="+ params["path"].value + "&clk_block=" + row["clkBlock"] +"&imp_choke=" + row["impChoke"] +"&clk_choke=" + row["clkChoke"] + "&hour="+ row["monHour"];
	var url  = params["ctx"].value +"/mc/imp-source-report?monId=" + row["monId"] + "&areaName=" + row["areaName"] + "&areaId=" +row["areaId"] + "&monDate=" + 
	row["monDate"] +"&type="+ type + "&" + totalValueUrl + "&areaType=" + row["area_type"]+ "&areaGroupName=" + params["areaGroupName"].value;
	return url;
//	return value;
}
//imp 小计报告的数字点击链接
function mcImpSumUrl(type, value)
{
	if(value <= 0) {
		return javascript;
	}
	var totalValueUrl = "imp_fwd=" + row["impFwd"] + "&clk_fwd=" + row["clkFwd"] + "&act_imp=" + row["actImp"] + "&act_clk=" + row["actClk"] + "&imp_block=" + row["impBlock"]
		 + "&clk_block=" + row["clkBlock"] +"&imp_choke=" + row["impChoke"] +"&clk_choke=" + row["clkChoke"];
	var url = params["ctx"].value +"/mc/imp-source-report?monId=" + row["monId"] + "&areaName=" + row["areaName"] + "&areaId=" +row["areaId"] + "&startDate=" + 
	params["startDate"].value + "&endDate=" + params["endDate"].value + "&type=" + type + "&" + totalValueUrl + "&path=";
	return url;
}
//是否只包含一个path
function isOnePath(str) {
	return false;
}

//imp 时段图表 补全24小时 sql 语句
function getImpChartUnExistsHourSql(monId, areaId, startDate, endDate) {
	 var sql = new com.funshion.artemis.common.util.TimeUtils().getImpChartUnExistsHourSql(monId, areaId, startDate, endDate);
	 return sql;
}

//读取数据库文件初始化报告数据配置
function initializeDb(reportContext, extensionProperties) {
	importPackage(Packages.java.io,Packages.java.util,Packages.java.net);
	//导入java类
	importPackage(Packages.javax.servlet.http);
	req = reportContext.getHttpServletRequest( );
	//得到HttpServletRequest对象
	propPath = new String("WEB-INF/classes/jdbc-follow.properties");
	if ( propPath.charAt(0) != "/" ) {
	  propPath = "/" + propPath;
	  url = req.getSession( ).getServletContext( ).getResource( propPath );
	}

	//得到相对路径下的URL
	if ( url != null ) {  
		props = new java.util.Properties( );
		//得到Properties对象
		props.load( url.openStream( ) );
		//为birt数据源属性赋值 
		extensionProperties.odaURL = new String(props.getProperty("jdbc.url"));
		extensionProperties.odaDriverClass = new String(props.getProperty("jdbc.driver"));
		extensionProperties.odaUser = new String(props.getProperty("jdbc.username"));
		extensionProperties.odaPassword = new String(props.getProperty("jdbc.password"));
		extensionProperties.odaJndiName = new String(props.getProperty("jndi.name"));
	}
}

//初始化报表 colNamesStr 为需要排序的列集合
function initializeReport(colNamesStr,hidColumnIndex) {
	importPackage(Packages.org.eclipse.birt.report.model.api ); 
	importPackage(Packages.org.eclipse.birt.report.model.api.elements);
	importPackage(Packages.org.eclipse.birt.report.model.api.elements.structures);
	importPackage(Packages.org.eclipse.birt.report.model.elements.interfaces);
	
	tableObj = reportContext.getDesignHandle().findElement("table");
	columnNum = tableObj.getColumnCount();
	addParams();
	recordNum = reportContext.getParameterValue("recordNum");
	if(recordNum == null || recordNum == "null" || recordNum == "") {
		recordNum = 20;
	};
//	addOperationColumn();
	sortColumns(colNamesStr);//列排序
	hideSelectedColumns(colNamesStr,hidColumnIndex);//隐藏列
	tableStyle();
}

function tableStyle() {
	tableObj = reportContext.getDesignHandle().findElement("table");
	tableheader = tableObj.getHeader().get(0);
	tabledetail = tableObj.getDetail().get(0);
	footer = tableObj.getFooter().get(0);
	header_color = "this.getStyle().backgroundColor=\"F5FAFA\";\n this.getStyle().fontSize = 10;\n this.getStyle().fontWeight = 'normal';";
	line_color = "count++;\nif( count % 2 == 0 )\n{\nthis.getStyle().backgroundColor=\"#F5FAFA\";\n}\nelse\n{\nthis.getStyle().backgroundColor=\"#FFFFFF\";\n}\nstyle.textAlign = \"center\";" 
		+"rowt = this.getRowData();\nval = rowt.getExpressionValue( \"row[mc_detail_percent]\");if(val != null && val == '占比'){this.getStyle().backgroundColor='#c8e3ff';this.getStyle().fontWeight='bold'}";
	//导出报表取消隔行变色
	line_color += "\nvar format = reportContext.getHttpServletRequest().getParameter(\"__format\");\n"
		+"if(format == \"xls\") {\n"
		+"   this.getStyle().backgroundColor = \"#FFFFFF\";}" ;
	if(reportContext.getDesignHandle().getFileName().indexOf("mc_imp_report.rptdesign") == -1) {
		tableheader.setOnRender(header_color);
		if(footer != null) {
			footer.setOnRender(header_color);
		}
		tabledetail.setOnRender(line_color);
	}
}

//初始化图表
function initializeChart() {
	importPackage(Packages.org.eclipse.birt.report.model.api ); 
	importPackage(Packages.org.eclipse.birt.report.model.api.elements);
	importPackage(Packages.org.eclipse.birt.report.model.api.elements.structures);
	importPackage(Packages.org.eclipse.birt.report.model.elements.interfaces);
	importPackage(Packages.org.eclipse.birt.chart.model.data.impl);
	importPackage(Packages.org.eclipse.birt.chart.model.type.impl);
	importPackage(Packages.org.eclipse.birt.chart.model.attribute);
	importPackage(Packages.org.eclipse.birt.chart.model.type);
	chart =  reportContext.getDesignHandle().findElement("chart");
	chartIns = chart.getReportItem().getProperty("chart.instance");
	chartDimension = reportContext.getParameterValue("chartDimension");
	if(chartDimension == "d3") {
    	chartIns.setDimension(ChartDimension.THREE_DIMENSIONAL_LITERAL);
    } else {
    	chartIns.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
    }
	chartIns.getBlock().getBounds().setWidth(50);
	xAxis =chartIns.getAxes().get(0);            //得到x轴对象
    yAxis1 = xAxis.getAssociatedAxes().get(0);        //得到y轴对象
    
    areaIds = params["areaIds"].value;
    areaNames = params["areaNames"].value;
    quotas = params["quotas"].value;
    quotaNames = params["quotaNames"].value;
    var seriesNames  = getSeriesNames(areaNames, quotaNames);
    computedSet = chart.getColumnBindings();
    var series1 = yAxis1.getSeriesDefinitions().get(0).getSeries().get(0);
    series1.setSeriesIdentifier(seriesNames[0]);
    if(yAxis1.getSeriesDefinitions().size() <= 1) {
	    for(var i = 2; i <= seriesNames.length; i++) {
	    	try {
		    	cs = StructureFactory.createComputedColumn();
				cs.setName("series"+i);
				cs.setDisplayName("series"+i);
				cs.setExpression("dataSetRow[\"series"+i+"\"]");
				computedSet.addItem(cs);
			} catch (e) {
				    		
			}
				
		    var seriesDefinit = SeriesDefinitionImpl.create();   //生成一个SeriesDefinition对象
		    var lineSeries = LineSeriesImpl.create();  //生成一个折线系列
		    lineSeries.getLabel().setVisible(true);                     //设置折线显示数值
		    var data = QueryImpl.create("row[\"series"+i+"\"]" );       //得到我们选择的参数相应的数值
		    lineSeries.getDataDefinition().add(data);                 //把数据添加到折线里
		    seriesDefinit.getSeries().add( lineSeries );        //添加这个折线到SeriesDefinition对象里
		    lineSeries.setSeriesIdentifier(seriesNames[i - 1]);
		    yAxis1.getSeriesDefinitions().add( seriesDefinit );                
	    }
    }
    
    var plotTypes = new Array();
    plotTypes.push(MarkerType.CIRCLE_LITERAL);
    plotTypes.push(MarkerType.STAR_LITERAL);
    
    var num = Math.floor(Math.random() * (plotTypes.length));
    var plotType = plotTypes[num];
    var startDate = params["startDate"].value;
    var endDate = params["endDate"].value;
    var dayDiff = "";
    if(startDate == "" || endDate == "") {
    	dayDiff = 9;
    } else {
    	dayDiff = getPeriodDay(startDate, endDate);
    }
    for(var i = 0; i < yAxis1.getSeriesDefinitions().size(); i++) {//设置样式
    	var series = yAxis1.getSeriesDefinitions().get(i).getSeries().get(0);
    	series.getMarkers().get(0).setType(plotType);          //设置拆线上的标记为圆型
    	if(dayDiff > 3) {
    		series.getMarkers().get(0).setSize(0);
    	} else {
    		series.getMarkers().get(0).setSize(2);
    	}
    }
}

/*
 * 获取这段时间内的 相差天数
*/
function getPeriodDay(startDate, endDate) {
  importPackage(Packages.java.text);
  formate = new SimpleDateFormat("yyyy-MM-dd");
  start = formate.parse(startDate);
  end = formate.parse(endDate);
  day = (end.getTime() - start.getTime())/(24*3600*1000) + 1;
  return day;
}


function getSeriesNames (areas, quotas) {
	if(areas == null || areas.length == 0 || quotas == null || quotas.length == 0) {
		return;
	}
	var areaArray = new Array();
	var quotaArray = new Array();
	areaArray = areas.split(",");
	quotaArray = quotas.split(",");
	var result = new Array();
	for(var i = 0; i < areaArray.length; i++) {
		for(var j = 0; j < quotaArray.length; j++) {
			result.push(areaArray[i] + "-" + quotaArray[j]);
		}
	}	
	return result;
}

/**
 * 获取 折线标识名字
 * @param areaNames
 * @param quotas
 * @returns {Array}
 */
function getSeriesDisplayNames(areaNames, quotas) {
	if(areaNames == null || areaNames.length == 0 || quotas == null || quotas.length == 0) {
		return;
	}
	var areaArray = new Array();
	var quotaArray = new Array();
	areaArray = areaNames.split(",");
	quotaArray = quotas.split(",");
	var result = new Array();
	for(var i = 0; i < areaArray.length; i++) {
		for(var j = 0; j < quotaArray.length; j++) {
			result.push(areaArray[i] + "-" + quotaArray[j]);
		}
	}	
	return result;
}

/**
 * 根据参数生成图表 sql 语句
 * @param areaIds 地域集
 * @param quotas 指标
 */
function createSeriesSql(areaIds, quotas) {
	if(areaIds == null || areaIds.length == 0 || quotas == null || quotas.length == 0) {
		return;
	}
	var areaArray = new Array();
	var quotaArray = new Array();
	areaArray = areaIds.split(",");
	quotaArray = quotas.split(",");
	
	var sql = "";
	var count = 1;
	for(var i = 0; i < areaArray.length; i++) {
		for(var j = 0; j < quotaArray.length; j++) {
			sql += ",CASE  WHEN SUM(CASE WHEN areaId = " +  areaArray[i] + " THEN " + quotaArray[j] + " END) is null then 0 else SUM(CASE WHEN areaId = " +  areaArray[i] + " THEN " + quotaArray[j] + " END) end as series" + count++;
		}
	}
	return sql;
}

function getSeriesSumSql(areaIds, quotas) {
	var sumSql  = "monId,monDate, monHour,time,areaName";
	var areaArray = new Array();
	var quotaArray = new Array();
	areaArray = areaIds.split(",");
	quotaArray = quotas.split(",");
	var num = areaArray.length * quotaArray.length;
	for(var i = 1; i <= num; i++) {
		sumSql += ",sum(series" + i + ") as series" + i;
	}
	return sumSql;
}

//添加参数
function addParams() {
	paramList = new Array();
	paramList = ["recordNum","hideColumns"];//需要添加的参数
	for(var i =0; i < paramList.length; i++) {
		if(!isParamExists(paramList[i])) {
			sph = reportContext.getDesignHandle().getElementFactory().newScalarParameter(paramList[i]);
			sph.setName(paramList[i]);
			sph.setIsRequired(false);
			sph.setAllowNull(true);
			sph.setAllowBlank(true);
			sph.setValueType(DesignChoiceConstants.PARAM_VALUE_TYPE_STATIC);
			sph.setDataType(DesignChoiceConstants.PARAM_TYPE_STRING);
			reportContext.getDesignHandle().getParameters().add(sph);
		}
	}
}

//添加报表操作列
function addOperationColumn() {
	 textData = reportContext.getDesignHandle().getElementFactory().newTextData("operation");
	 textData.setContentType("html");
	 headerScript = "this.text = \"&lt;a href='javascript:void(0);' onclick=\"new function(e){oEvent=e||event;var x =oEvent.clientX;var y =oEvent.clientY;parent.showColsSelectInfo('ad',x,y);}\""
			 +"&gt; &lt;img style='float:right' src='/images/report/selectcols.gif'/&gt;&lt;/a&gt;\";";
	 textData.setOnRender(headerScript);
	 tableObj = reportContext.getDesignHandle().findElement("table");
	 tableheader = tableObj.getHeader().get(0);
	 labelCell = tableheader.getCells().get(columnNum);
	 labelCell.getContent().add(textData);
}

//判断该参数是否已经定义了
function isParamExists(param) {
	var columnNum = reportContext.getDesignHandle().getParameters().getCount();
	for(var i = 0; i < columnNum; i++) {
		var name = reportContext.getDesignHandle().getParameters().get(i).getName();
		if(name == param) {
			return true;
		}
	}
	return false;
}

//列排序
function sortColumns(colNamesStr) {
	count = 0;
	baseUrl = reportContext.getHttpServletRequest().getRequestURL() + "?" + reportContext.getHttpServletRequest().getQueryString();//获取请求url
	sortColumn = reportContext.getHttpServletRequest().getParameter("sortColumn");//排序字段
	sortType = reportContext.getHttpServletRequest().getParameter("sortType");//排序类型
	if(sortColumn != null && sortColumn !="") {
		baseUrl = baseUrl.substring(0, baseUrl.indexOf("sortColumn") - 1);
	}
	
	styleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_a_color");
	styleHandle.setProperty(IStyleModel.COLOR_PROP, "#33729b");
	reportContext.getDesignHandle().getStyles().add(styleHandle);
	
	colNames = new Array();
	colNames = colNamesStr;//需要排序的字段
	for(var i = 0; i < colNames.length; i++) {
		var label = reportContext.getDesignHandle().findElement(colNames[i]);
		if(sortColumn != null && sortColumn !="" && colNames[i] == sortColumn) {
			sql = reportContext.getReportRunnable().getDesignInstance().getDataSet("ds").queryText;
			if(sql.indexOf("order by") > -1) {
				sql = sql.substring(0, sql.indexOf("order by"));
			}
			orderSql = " order by " + sortColumn + " " + sortType;
            sql = sql + orderSql;
            reportContext.getReportRunnable().getDesignInstance().getDataSet("ds").queryText = sql;
           
			if(sortType == "asc") {
				label.setText(label.getDisplayText() + "↑");
			    sortType = "desc";
			} else {
			    sortType= "asc";
			    label.setText(label.getDisplayText() + "↓");
			}
			url = baseUrl + "&sortColumn=" + sortColumn + "&sortType=" + sortType;
		} else {
		    url = baseUrl + "&sortColumn=" + colNames[i] + "&sortType=asc";
		}
		
		if(label != null && label != "null") {
		    label.setStyle(styleHandle);
			actionHandle = label.setAction(new Action());
			actionHandle.setLinkType(DesignChoiceConstants.ACTION_LINK_TYPE_HYPERLINK);
			actionHandle.setURI("'"+url+"'");
			actionHandle.setTargetWindow("_self");
		}
	}
}

//隐藏报表上所选择的列
function hideSelectedColumns(colNamesStr,hidColumnIndex) {
	hideColumns = reportContext.getHttpServletRequest().getParameter("hideColumns");
	tableObj = reportContext.getDesignHandle().findElement("table");
	hideStyleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_hidden_s");
	hideStyleHandle.setProperty(IStyleModel.DISPLAY_PROP, "none");
	showStyleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_show_s");
	showStyleHandle.setProperty(IStyleModel.DISPLAY_PROP, "block");
	
	gridStyleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_grid_s");
	gridStyleHandle.setProperty(IStyleModel.DISPLAY_PROP, "block");
	gridStyleHandle.setProperty(IStyleModel.BORDER_LEFT_COLOR_PROP, "#CAD1D7");
	gridStyleHandle.setProperty(IStyleModel.BORDER_LEFT_WIDTH_PROP, "1px");
	gridStyleHandle.setProperty(IStyleModel.BORDER_LEFT_STYLE_PROP, "solid");
	gridStyleHandle.setProperty(IStyleModel.BORDER_TOP_COLOR_PROP, "#CAD1D7");
	gridStyleHandle.setProperty(IStyleModel.BORDER_TOP_WIDTH_PROP, "1px");
	gridStyleHandle.setProperty(IStyleModel.BORDER_TOP_STYLE_PROP, "solid");
	
	gridNoRightStyleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_grid_no_right_s");
	gridNoRightStyleHandle.setProperty(IStyleModel.DISPLAY_PROP, "block");
	gridNoRightStyleHandle.setProperty(IStyleModel.BORDER_TOP_COLOR_PROP, "#CAD1D7");
	gridNoRightStyleHandle.setProperty(IStyleModel.BORDER_TOP_WIDTH_PROP, "1px");
	gridNoRightStyleHandle.setProperty(IStyleModel.BORDER_TOP_STYLE_PROP, "solid");
	
	gridBottomStyleHandle = reportContext.getDesignHandle().getElementFactory().newStyle("style_grid_bottom_s");
	gridBottomStyleHandle.setProperty(IStyleModel.DISPLAY_PROP, "block");
	gridBottomStyleHandle.setProperty(IStyleModel.BORDER_TOP_COLOR_PROP, "#CAD1D7");
	gridBottomStyleHandle.setProperty(IStyleModel.BORDER_TOP_WIDTH_PROP, "1px");
	gridBottomStyleHandle.setProperty(IStyleModel.BORDER_TOP_STYLE_PROP, "solid");
	
	reportContext.getDesignHandle().getStyles().add(hideStyleHandle);
	reportContext.getDesignHandle().getStyles().add(showStyleHandle);
	reportContext.getDesignHandle().getStyles().add(gridStyleHandle);
	reportContext.getDesignHandle().getStyles().add(gridNoRightStyleHandle);
	reportContext.getDesignHandle().getStyles().add(gridBottomStyleHandle);
	
	if(reportContext.getDesignHandle().findElement("sift_total") != null) {
		reportContext.getDesignHandle().findElement("sift_total").getContainer().setStyle(gridNoRightStyleHandle);
	}
	
	if( reportContext.getDesignHandle().findElement("sift_sub") != null) {
		reportContext.getDesignHandle().findElement("sift_sub").getContainer().setStyle(gridNoRightStyleHandle);
	} 
	
	if( reportContext.getDesignHandle().findElement("sift_footer") != null) {
		reportContext.getDesignHandle().findElement("sift_footer").getContainer().setStyle(gridNoRightStyleHandle);
	} 
	if(reportContext.getDesignHandle().findElement("data_operation") != null) {
		reportContext.getDesignHandle().findElement("data_operation").getContainer().setStyle(gridBottomStyleHandle);
	}
			
	hideColumnArray = new Array();
	//如果hideColumns只含有一个字符位置
	if(hideColumns != null && hideColumns != "") {
		if (hideColumns.contains(",")){
			var tempArray = new Array();
			tempArray = hideColumns.split(",");
			for(var i = 0; i < tempArray.length; i++) {
				hideColumnArray.push(tempArray[i]);
			}
		}else{
			hideColumnArray.push(hideColumns);
		}	
	}
	//广告报告中，当报告类型非adp时，隐藏播放比、有效播放比、完整播放比三个字段
	if(colNamesStr.length != 0 && hidColumnIndex.length !=0 ) {
		conditonType = reportContext.getHttpServletRequest().getParameter("conditonType");
		if (conditonType != "adp") {
			for (var n = 0; n < tableObj.getColumnCount() - 1; n++) {
				for (var i =0;i<hidColumnIndex.length;i++) {
					if (n == parseInt(hidColumnIndex[i])) {
						hideColumnArray.push(n);
					}
				}
			}
		}
		//广告位报告，广告位分布及基础分布报表添加请求量指标，其余报表隐藏请求量	
		var noReqLst = {'4':'', '10':'', '13':'', '21':'', '14':'', '15':'', '16':'', '49':'', '51':''};
		var rptId = reportContext.getHttpServletRequest().getParameter("reportId");
		var idGroup = reportContext.getHttpServletRequest().getParameter("idGroup");
		if(conditonType != "adp" && conditonType != "area" && noReqLst.hasOwnProperty(rptId) )
		{
			if(rptId == '16' || rptId == '49' || rptId == '51')
				hideColumnArray.push(3);
			else
				hideColumnArray.push(2);
		}
		else
		{
			//adp 二维报表，不包含广告位分布的二维报表，需要隐藏广告位请求量
			if(idGroup)
			{
				var cols = idGroup.split(",");
				var id_f = cols[0];
				var id_l = cols[1];
				var noReqTab = {"1":"", "2":"","3":"","52":"","53":""};
				
				if(noReqTab.hasOwnProperty(id_f) || noReqTab.hasOwnProperty(id_l))
					hideColumnArray.push(3);
			}
		}
		
	}
	var columnCount = tableObj.getColumnCount() - 1;
	if(reportContext.getDesignHandle().findElement("data_operation") == null && reportContext.getDesignHandle().findElement("sift_sub") == null) {
		columnCount = tableObj.getColumnCount();
	}
	
    for(var n = 0; n < columnCount; n++) {
		var isExists = false;
		for(var i = 0; i < hideColumnArray.length; i++) {
			if(n == hideColumnArray[i]) {
				isExists = true;
			}
		}
		if(isExists) {
			tableObj.getColumns().get(n).setStyle(hideStyleHandle);
		} else {
			tableObj.getColumns().get(n).setStyle(gridStyleHandle);
		}
	}
}

//得到实际播放量数字集中 最小值除以最大值的百分比
function getMinDiveMaxPercent(numStr) {
	if(numStr == undefined || numStr == "") {
		return "";
	}
//	numStr = getDistinctImpFwd(numStr);
	var array = new Array();
	array = eval("[" + numStr + "]");
	var min = smallest(array);
	var max = largest(array);
	return getPercent(min, max);
} 


//获取字符串中 各个subpath的 实际播放量
function getDistinctImpFwd(numStr) {
	if(numStr == undefined || numStr == "") {
		return "";
	}
	var array = new Array();
	array = numStr.split(",");
	importPackage( Packages.java.util);
	map = new java.util.HashMap();
	for(var i = 0; i < array.length; i++) {
		var subArray = new Array();
		subArray = array[i].split("@");
		val = map.get(subArray[1]);
		if(val == null || val == "" || val.equals("") || val == undefined) {
			map.put(subArray[1], subArray[0]);
		} else {
			val = parseInt(val) + parseInt(subArray[0]) + '';
			if(val.charAt(val.length -1) == '0' && val.charAt(val.length -2) == '.') {
				val = val.substring(0, val.length -2);
			}
			map.remove(subArray[1]);
			map.put(subArray[1], val);
		}
	}
	
	var str = map.toString() + '';
    str = str.substring(1, str.length -1);
    var a = new Array();
    a = str.split(",");
    var result  = "";
    for(var j = 0 ; j < a.length; j++) {
    	var b = new Array();
    	b = a[j].split("=");
    	result = result + b[1] + ",";
    }
    result = result.substring(0, result.length - 1);
    return result;
}


//到播控转发量数字集中 最大值
function getMaxValInImpFwd(numStr) {
	if(numStr == undefined || numStr == "") {
		return "";
	}
	//numStr = getDistinctImpFwd(numStr);
	var array = new Array();
	array = eval("[" + numStr + "]");
	var max = largest(array);
	return max;
}

function smallest(array){
   return Math.min.apply(Math,array);
}

function largest(array){
   return Math.max.apply(Math,array);
}

//打开子报表 impPercent：播放比数字字符串  123@0,32@1
function openSubReport(monId, monDate, areaId, path, impPercent,hour) {
	//impStr = getDistinctImpFwd(impPercent);
	impPercent = new java.lang.String(impPercent);
	
	if(!impPercent.contains(",")) {
		return "100.00%";
	}
	var text = "<a href=\"javascript:void(0)\" onclick=\"new function(e){" +
	   "oEvent=e||event; " +
	   "var x =oEvent.clientX;" +
	   "var y =oEvent.clientY;" +
	   "parent.openSubReport("+monId+"," +areaId +",'" + monDate +"'," +path+ ","+getMaxValInImpFwd(impPercent)+","+hour+ ",x,y)" +
	   "}\">"+getMinDiveMaxPercent(impPercent)+"</a>";
	return text;
}

//调用并发报告的悬浮窗信息
function showConcurrentReportTipInfo(uv1_plus,uv_plus) {
	
	var rptId = "__BIRT_ROOT";
	var title = " ";
	var text = "<span href=\"javascript:void(0)\" onmouseout=\"(function(obj){parent.mouseOutAction();})(this)\";"  
		+ " onmouseover='(function(obj){"
		+ " var root_title = document.getElementById(\"" 
		+ rptId
		+"\");root_title.setAttribute(\"title\",\"\"); "
		+ " var posObj = obj.getBoundingClientRect(); var x = posObj.left; var y = posObj.top; "
		+ " parent.showConcurrentReportInfo(" + uv1_plus + "," + uv_plus + ", x, y)})(this)' >" + uv_plus + "</span>";
	return text;
}

//获取主路径上的播放转发量 例如123@0,32@1  那么返回值是 32 因为1 是 主路径
function getMainPathValue(str) {
	if(str ==undefined || str == "") {
		return "";
	}
	var impFwdArray = new Array();
	impFwdArray = str.split(",");
	var result = "";
	for(var i= 0; i < impFwdArray.length; i++) {
		var impArray = new Array();
		impArray = impFwdArray[i].split("@");
		if(impArray.length < 2) {
			return "";
		}
		if(impArray[1] == 1) {
			result = impArray[0];
		}
	}
	return result;
}

function myPrintln(str) {
	java.lang.System.out.println(str);
}

//格式化时段分布中的小时显示, 比如 1格式化成 01:00~02:00
function formateHour(hour) {
	importPackage(Packages.java.lang);
	hourVal = Integer.parseInt(hour);
	if(hourVal < 9) {
	    hour = '0' + hourVal;
	    hour = hour + ':00~0' + (hourVal + 1) + ":00";
	} else if(hourVal == 9)  {
		hour = '0' + hourVal + ":00~";
	    hour = hour + (hourVal + 1) + ":00";
	} else {
	 	hour = hourVal + ':00~' + (hourVal + 1) + ":00";
	}
	return hour;
}

// 隐藏报表工具栏
function hideToolbar() {
	var elements = new Array();
	elements = getElementsByClassName("body_caption");
	if (elements.length > 0) {
		var bodyCaption = elements[0];
		bodyCaption.style.display = "none";
	}
}

// 根据 class名 获取元素
function getElementsByClassName(className) {
	var classElements = [], allElements = document.getElementsByTagName('*');
	for ( var i = 0; i < allElements.length; i++) {
		if (allElements[i].className == className) {
			classElements[classElements.length] = allElements[i];
		}
	}
	return classElements;
}

// 格式化报表，使报表颜色相间。并且分页 obj:行对象 pagesize:每页大小 count:当前是多少行
function formateRowStyle(obj, pagesize, count) {
	if (count % 2 == 0) {
		obj.getStyle().backgroundColor = "#ECF3FE";
	} else {
		obj.getStyle().backgroundColor = "#FCFFFE";
	}

	if (count % pagesize == 0) {
		obj.style.pageBreakAfter = "always";
	}
}

// 得到sql 语句参数URL reportContext:当前报表上下文对象 ,返回格式化的sql语句
function getSQLParamURL(reportContext) {
	var adId = reportContext.getParameterValue("adId");
	var areaIds = reportContext.getParameterValue("areaIds");
	var adIds = reportContext.getParameterValue("adIds");
	var adpId = reportContext.getParameterValue("adpId");
	var paramUrl = "";
	if (adId != null && adId != "") {
		paramUrl += " and ad_id = " + adId;

	}

	if (adpId != null && adpId != "") {
		paramUrl += " and adp_id = " + adpId;

	}

	if (areaIds != null && areaIds != "") {
		paramUrl += " and (area_id in (" + areaIds + ") or d.parent_id in ("
				+ areaIds + ")) ";
	}

	if (adIds != null && adIds != "") {
		paramUrl += " and ad_id in (" + adIds + ")";
	}

	return paramUrl;
}

//打印信息到 
function printlnLogInfo(txt) {
	importPackage(Packages.java.lang); 
	System.out.println(txt);

}

// 格式化小时级报表sql语句
function formateHourReportSql(txt) {
	var sqlStr = new java.lang.String(txt);
	sqlStr = sqlStr.replace("%Y-%m-%d", "%H");
	sqlStr = sqlStr.replace("stg_ad_effe_d", "stg_ad_effe");
	sqlStr = sqlStr.replace("log_date", "log_time");
	return sqlStr;
}

function test() {
	return "test";
}