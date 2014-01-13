package com.funshion.artemis.report.createreport;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

import com.funshion.artemis.report.bean.ReportXmlBean;
import com.ibm.icu.util.ULocale;

/**
 * 报告基类
 * 此类主要是针对报表的数据源,数据集以及参数进行构建，属于前期基础工作
 * @author shenwf Reviewed by zengyc
 */
public abstract class Report {
	ReportDesignHandle designHandle = null;
	ElementFactory designFactory = null;
	ReportXmlBean report;

	/**
	 * 构建数据源
	 * 
	 * @throws SemanticException
	 */
	public void buildDataSource() throws SemanticException {
		DesignConfig config = new DesignConfig();
		IDesignEngine engine = null;
		try {
			Platform.startup(config);
			IDesignEngineFactory factory = (IDesignEngineFactory) Platform.createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
			engine = factory.createDesignEngine(config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SessionHandle session = engine.newSessionHandle(ULocale.CHINESE);

		// open a design or a template
		designHandle = session.createDesign();

		designFactory = designHandle.getElementFactory();

		OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource("Data Source", "org.eclipse.birt.report.data.oda.jdbc");
		dsHandle.setProperty("odaDriverClass", "com.mysql.jdbc.Driver");
		dsHandle.setProperty("odaURL", report.getDbUrl());
		dsHandle.setProperty("odaUser", report.getDbUserName());
		dsHandle.setProperty("odaPassword", report.getDbPassword());
		dsHandle.setProperty("odaJndiName", report.getJndiName());
		dsHandle.setBeforeClose("initializeDb(reportContext, extensionProperties);");
		
		OdaDataSourceHandle scriptHandle = designFactory.newOdaDataSource("script data source", "org.eclipse.birt.report.data.oda.restapi");
		scriptHandle.setProperty("httpRequest", "http://192.168.133.86:8080/artemis/data?ids=15658&startTime=2013-12-04&endTime=2013-12-05&reportId=1&conditonType=ad&freq=&areaIds=");
		designHandle.getDataSources().add(dsHandle);
		designHandle.getDataSources().add(scriptHandle);
	}

	/**
	 * 构建数据集
	 * 
	 * @throws SemanticException
	 */
	public void buildDataSet() throws SemanticException {
		OdaDataSetHandle wsHandle = designFactory.newOdaDataSet("ws", "org.eclipse.birt.report.data.oda.restapi.dataSet");
		wsHandle.setDataSource("script data source");
		wsHandle.setBeforeOpen(getDatasetScript());
		wsHandle.setQueryText("");
		List list = wsHandle.getListProperty("columnHints");
		Iterator iterator = wsHandle.columnHintsIterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			System.out.println("....." + o);
		}
		
		
		String sql = report.getSql();
		sql = sql.replaceAll("\\single", "'");// 将\\single替换成'
		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet("ds", "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet");
		dsHandle.setDataSource("Data Source");
		dsHandle.setQueryText(sql);
		String code = "";
		if (Integer.parseInt(report.getRptType()) < 7 
				|| report.getRptType().equals("10") || report.getRptType().equals("11")) 
		{
			code = " ids = reportContext.getParameterValue(\"ids\");\n"
					+ "startTime =  reportContext.getParameterValue(\"startTime\");\n"
					+ "endTime =  reportContext.getParameterValue(\"endTime\");\n"
					+ "reportId =  reportContext.getParameterValue(\"reportId\");\n"
					+ " conditonType =  reportContext.getParameterValue(\"conditonType\");\n"
					+ " chartType =  reportContext.getParameterValue(\"chartType\");\n"
					+ "freq =  reportContext.getParameterValue(\"freq\");\n"
					+ "areaIds =  reportContext.getParameterValue(\"areaIds\");\n"
					+ "rptSql =  reportContext.getParameterValue(\"rptSql\");\n"
					+ " sql = new java.lang.String();\n"
					+ " if(rptSql != null && rptSql != \"\"){\nthis.queryText = rptSql;\n} else {\n"
					+ "sql = new com.funshion.artemis.custom.service.AdReportSqlManager().getReportSql(reportContext.getHttpServletRequest(),this.queryText,ids, startTime, endTime, reportId,conditonType, chartType, freq, areaIds);"
					+ " \nthis.queryText = sql;}";
		} else if(Integer.parseInt(report.getRptType()) < 8){
			code = "monId = reportContext.getParameterValue(\"monId\");\n" +
		           "monDate = reportContext.getParameterValue(\"monDate\");\n"+
		           "startDate = reportContext.getParameterValue(\"startDate\");\n"+
		           "endDate = reportContext.getParameterValue(\"endDate\");\n"+
				   "areaId = reportContext.getParameterValue(\"areaId\");\n"+
		           "rptName = reportContext.getParameterValue(\"rptName\");\n"+
		           "rptSql =  reportContext.getParameterValue(\"rptSql\");\n" +
		           "hour =  reportContext.getParameterValue(\"hour\");\n" +
		           "path = reportContext.getParameterValue(\"path\");\n "+
					"if(rptSql != null && rptSql != \"\"){\nthis.queryText = rptSql;\n} else {\n" +
		           "sql = new com.funshion.artemis.custom.service.AdReportSqlManager().getMcReportSql(reportContext.getHttpServletRequest(),rptName,monId,monDate,startDate,endDate,areaId,path,hour);  " +
					" \nthis.queryText = sql;}";
		} else {
			code = " ids = reportContext.getParameterValue(\"ids\");\n"
					+ "startTime =  reportContext.getParameterValue(\"startTime\");\n"
					+ "endTime =  reportContext.getParameterValue(\"endTime\");\n"
					+ "reportId =  reportContext.getParameterValue(\"reportId\");\n"
					+ " conditonType =  reportContext.getParameterValue(\"conditonType\");\n"
					+ "areaIds =  reportContext.getParameterValue(\"areaIds\");\n"
					+ "rptSql =  reportContext.getParameterValue(\"rptSql\");\n"
					+ "reportName =  reportContext.getParameterValue(\"reportName\");\n"
					+ "idGroup =  reportContext.getParameterValue(\"idGroup\");\n"
					+ " sql = new java.lang.String();\n"
					+ " if(rptSql != null && rptSql != \"\"){\nthis.queryText = rptSql;\n} else {\n"
					+ "sql = new com.funshion.artemis.custom.service.AdReportSqlManager().getTwoDimensionalReportSql(reportContext.getHttpServletRequest(),this.queryText,ids, startTime, endTime, reportId,idGroup,conditonType,areaIds,reportName);"
					+ " \nthis.queryText = sql;}";
		}
        code += "\nmyPrintln(sql);";
		dsHandle.setBeforeOpen(code);// 执行sql语句查询前执行的脚本
//		PropertyHandle parameterHandle = dsHandle.getPropertyHandle(DataSetHandle.PARAMETERS_PROP);
//		String[] params = new String[] { "monId", "monDate", "areaId", "path" };
//		int i = 0;
//		for (String param : params) {
//			OdaDataSetParameter parameter = StructureFactory.createOdaDataSetParameter();
//			parameter.setName(param);
//			parameter.setAllowNull(true);
//			parameter.setDefaultValue("1");
//			parameter.setDataType("String");
//			parameter.setParameterDataType("String");
//			parameter.setParamName(param);
//			parameterHandle.insertItem(parameter, i++);
//		}

		designHandle.getDataSets().add(dsHandle);
		designHandle.getDataSets().add(wsHandle);
	}

	/**
	 * 构建参数
	 * 
	 * @throws SemanticException
	 */
	public void buildParam() throws SemanticException {
		String[] params = new String[] { "reportSql", "exportName", "ids", "startTime", "endTime", "reportId", "conditonType", "chartType", "freq", "areaIds", "proviceIds", "cityIds", "recordNum","subTitle","conditionNames","hideColumns","reportName","idGroup","firstDisplayName","secondDisplayName"};// 广告相关的参数
		for (String param : params) {
			ScalarParameterHandle sph = designFactory.newScalarParameter(param);
			sph.setName(param);
			sph.setIsRequired(false);
			sph.setAllowNull(true);
			sph.setAllowBlank(true);
			sph.setValueType(DesignChoiceConstants.PARAM_VALUE_TYPE_STATIC);
			sph.setDataType(DesignChoiceConstants.PARAM_TYPE_STRING);
			designHandle.getParameters().add(sph);
		}
		
		params = new String[] { "monId", "monDate","startDate","endDate", "path", "areaId","value", "hour", "rptName", "rptDisplayName","monName","areaName","timePoint"};// 播控相关的参数
		for (String param : params) {
			ScalarParameterHandle sph = designFactory.newScalarParameter(param);
			sph.setName(param);
			sph.setIsRequired(false);
			
			sph.setAllowNull(true);
			sph.setAllowBlank(true);
			sph.setValueType(DesignChoiceConstants.PARAM_VALUE_TYPE_STATIC);
			sph.setDataType(DesignChoiceConstants.PARAM_TYPE_STRING);
			designHandle.getParameters().add(sph);
		}
	}
	
	public String getDatasetScript() {
		String script = " text = new java.lang.String(this.queryText); " 
						+ " pre = text.substring(0, text.lastIndexOf(\",\") + 1); " 
						+ " after = text.substring(text.lastIndexOf(\",\") + 1, text.length()); " 
						+ "  " 
						+ " ids = params[\"ids\"].value; " 
						+ " startTime = params[\"startTime\"].value; " 
						+ " endTime = params[\"endTime\"].value; " 
						+ " reportId = params[\"reportId\"].value; " 
						+ " conditonType = params[\"conditonType\"].value; " 
						+ " freq = params[\"freq\"].value; " 
						+ " areaIds = params[\"areaIds\"].value; " 
						+ " url = \"ids=\" + ids + \"&startTime=\" + startTime + \"&endTime=\" + endTime +  " 
						+ "                 \"&reportId=\" + reportId+ \"&conditonType=\" + conditonType + \"&freq=\" + freq + \"&areaIds=\" + areaIds " 
						+ " src = new java.lang.String(url); " 
						+ " src = src.replaceAll(\",\", \"comma\"); " 
						+ " src = src.replaceAll(\" \", \"space\"); " 
						+ " java.lang.System.out.println(\"http://192.168.133.86:8080/artemis/data?\" + src); " 
						+ " this.queryText = pre + \"http://192.168.133.86:8080/artemis/data?\" + src; ";
		return script;
	}

	/**
	 * 构建报表
	 * 
	 * @throws IOException
	 * @throws SemanticException
	 */
	public abstract void buildReport() throws IOException, SemanticException;
}
