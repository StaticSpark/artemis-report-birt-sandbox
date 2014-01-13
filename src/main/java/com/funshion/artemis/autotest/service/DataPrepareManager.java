package com.funshion.artemis.autotest.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IDataExtractionTask;
import org.eclipse.birt.report.engine.api.IDataIterator;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IResultMetaData;
import org.eclipse.birt.report.engine.api.IResultSetItem;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.autotest.bean.ReportParamBean;
import com.funshion.artemis.common.config.PropertiesConfigParse;
import com.funshion.artemis.common.util.HttpTools;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.custom.service.AdReportSqlManager;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;

/**
 * 测试数据准备
 * 
 * @author shenwf Reviewed by
 */
@Service
public class DataPrepareManager {
	@Autowired
	private ReportXmlDataHandler reportXmlDataHandler;
	@Autowired
	private AdReportSqlManager adReportSqlManager;

	/**
	 * 根据查询条件创建数据文档
	 * 
	 * @param reportParamBean
	 *            参数实体
	 * @param rptFilePath
	 *            报告文件根路径
	 * @throws EngineException
	 */
	public String createDoucumentFile(ReportParamBean reportParamBean, HttpServletRequest request) throws EngineException {
		EngineConfig config = new EngineConfig();

		try {
			Platform.startup(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		ReportXmlBean report = reportXmlDataHandler.getReportById(reportParamBean.getReportId());
		String documentFile = request.getRealPath("/") + "temp" + File.separator + report.getRptName() + "-" + System.currentTimeMillis() + ".rptdocument";
		String rptdesignFile = Tools.getRptDesignFilePath(request) + File.separator + report.getRptName() + ".rptdesign";
		IReportRunnable runnable = engine.openReportDesign(rptdesignFile);
		IRunTask task = engine.createRunTask(runnable);
		String sql = adReportSqlManager.getReportSql(null,reportParamBean.getIds(), reportParamBean.getStartTime(), reportParamBean.getEndTime(), reportParamBean.getReportId(),
				reportParamBean.getConditonType(), "", reportParamBean.getFreq(), reportParamBean.getAreaIds());
		task.setParameterValue("rptSql", sql);
		task.run(documentFile);
		return documentFile;
	}
	
	/**
	 * 创建播控持久化文档
	 * @param reportParamBean
	 * @param request
	 * @return
	 * @throws EngineException
	 */
	public String createMcDoucumentFile(HttpServletRequest request) throws EngineException {
		EngineConfig config = new EngineConfig();

		try {
			Platform.startup(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String rptName = request.getParameter("rptName");

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		String documentFile = request.getRealPath("/") + "temp" + File.separator + rptName + "-" + System.currentTimeMillis() + ".rptdocument";
		String rptdesignFile = Tools.getBirtFileRoot() + File.separator + "persistence" + File.separator + "mc" + File.separator + rptName+ ".rptdesign";
		IReportRunnable runnable = engine.openReportDesign(rptdesignFile);
		IRunTask task = engine.createRunTask(runnable);
		Enumeration<String> e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String paramName = e.nextElement();
			String paramValue = request.getParameter(paramName);
			task.setParameterValue(paramName, paramValue);
		}
		task.run(documentFile);
		return documentFile;
	}

	/**
	 * 将数据文档文件转换成xls文件
	 * 
	 * @param reportParamBean
	 * @param sc
	 * @throws BirtException
	 */
	public String documentToXls(ReportParamBean reportParamBean, HttpServletRequest request) throws BirtException {
		String documentFile = "";
		if(reportParamBean.getReportId()== null || reportParamBean.getReportId() <= 0) {
			documentFile = createMcDoucumentFile(request);
		} else {
			documentFile = createDoucumentFile(reportParamBean, request);
		}
		
		String xlsFile = documentFile.substring(0, documentFile.lastIndexOf(".") + 1) + "xls";
		EngineConfig config = new EngineConfig();
		Platform.startup(config);

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		IReportDocument iReportDocument = engine.openReportDocument(documentFile);
		IRenderTask task = engine.createRenderTask(iReportDocument);
		IRenderOption options = new RenderOption();
		options.setOutputFormat("xls");
		EXCELRenderOption excelOptions = new EXCELRenderOption(options);
		excelOptions.setOutputFormat("xls");
		excelOptions.setOutputFileName(xlsFile);
		excelOptions.setWrappingText(true);
		task.setRenderOption(options);
		//task.setPageRange("2");
		task.render();
		iReportDocument.close();
		new File(documentFile).delete();//删除临时文件
		return xlsFile;
	}

	/**
	 * 将xls文件转换到csv文件
	 * 
	 * @param reportParamBean
	 * @param sc
	 * @return
	 * @throws BirtException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String xlsToCsv(ReportParamBean reportParamBean, HttpServletRequest request) throws BirtException, DocumentException, IOException {
		String xlsFile = documentToXls(reportParamBean, request);
		String csvFile = xlsFile.substring(0, xlsFile.lastIndexOf(".") + 1) + "csv";
		List<String> list = OldExcelParse.parseExcel(xlsFile);
		new File(xlsFile).delete();
		writeDataToCsv(list, csvFile);
		csvFile = csvFile.substring(csvFile.lastIndexOf(File.separator) + 1, csvFile.length());
		return csvFile;
	}

	/**
	 * 将list容器中的数据写入csv文件
	 * 
	 * @param dataList
	 * @param outPutFileName
	 * @throws IOException
	 */
	public void writeDataToCsv(List<String> dataList, String outPutFileName) throws IOException {
		File file = new File(outPutFileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPutFileName),"UTF-8"));
		for (String line : dataList) {
			bw.write(line);
		}
		bw.close();
	}

	/**
	 * 读取文本文档数据
	 * 
	 * @param reportParamBean
	 * @param sc
	 * @return
	 * @throws BirtException
	 */
	public String readDocumentFile(ReportParamBean reportParamBean, HttpServletRequest request) throws BirtException {
		ReportXmlBean report = reportXmlDataHandler.getReportById(reportParamBean.getReportId());
		reportParamBean.setRptName(report.getRptName());
		String documentFile = createDoucumentFile(reportParamBean, request);

		EngineConfig config = new EngineConfig();

		try {
			Platform.startup(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		IReportDocument reportDocument = engine.openReportDocument(documentFile);
		IDataExtractionTask dataExtractionTask = engine.createDataExtractionTask(reportDocument);

		List<?> list = dataExtractionTask.getResultSetList();
		IResultSetItem resultSetItem = (IResultSetItem) list.get(0);
		String resultSetName = resultSetItem.getResultSetName();
		dataExtractionTask.selectResultSet(resultSetName);

		IResultMetaData resultMetaData = resultSetItem.getResultMetaData();
		int columnCount = resultMetaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = resultMetaData.getColumnName(i);
		}
		dataExtractionTask.selectColumns(columnNames);
		IExtractionResults extractionResults = dataExtractionTask.extract();
		IDataIterator dataIterator = extractionResults.nextResultIterator();

		StringBuffer stringBuffer = new StringBuffer();
		while (dataIterator.next()) {
			for (int j = 0; j < columnNames.length; j++) {
				Object object = dataIterator.getValue(j);
				if (object != null) {
					stringBuffer.append(object.toString());
					if (j < columnNames.length - 1) {
						stringBuffer.append(",");
					}
				}
			}
			stringBuffer.append("\n");
		}

		return stringBuffer.toString();
	}

	/**
	 * 获取csv数据
	 * @param request
	 * @param response
	 * @return
	 * @throws BirtException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String getReportData(HttpServletRequest request, HttpServletResponse response) throws BirtException, DocumentException, IOException {
		ReportParamBean reportParamBean = convertHttpParamToBean(request);
		String csvFile = xlsToCsv(reportParamBean, request);
		return csvFile;
	}
	
	/**
	 * 获取报告数据，以字符串形式返回
	 * @param request
	 * @param response
	 * @return
	 * @throws BirtException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public String getReportStringData(HttpServletRequest request, HttpServletResponse response) throws BirtException, DocumentException, IOException {
		ReportParamBean reportParamBean = convertHttpParamToBean(request);
		String xlsFile = documentToXls(reportParamBean, request);
		List<String> list = OldExcelParse.parseExcel(xlsFile);
		StringBuffer sb = new StringBuffer();
		for(String line : list) {
			sb.append(line);
		}
		return sb.toString();
	}
	
	/**
	 * 将参数转换成实体
	 * @param request
	 * @return
	 */
	private ReportParamBean convertHttpParamToBean(HttpServletRequest request) {
		ReportParamBean reportParamBean = new ReportParamBean();
		try {
			reportParamBean.setReportId(Long.parseLong(request.getParameter("rptId")));
			reportParamBean.setStartTime(request.getParameter("startTime"));
			reportParamBean.setEndTime(request.getParameter("endTime"));
			reportParamBean.setConditonType(request.getParameter("conditionType"));
			reportParamBean.setAreaIds(request.getParameter("areaIds"));
			reportParamBean.setFreq(request.getParameter("freq"));
			reportParamBean.setIds(request.getParameter("ids"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reportParamBean;
	}
	
	/**
	 * 获取报告数据
	 * @param request
	 * @param response
	 * @return
	 * @throws DocumentException
	 */
	public String getReportStringData2(HttpServletRequest request, HttpServletResponse response) throws DocumentException {
		String paramStr = request.getQueryString() + "&__format=xls&__asattachment=true&__overwrite=false&ctx=/artemis";
		String url = PropertiesConfigParse.getArtemisHome() + "/frameset?" + paramStr;
		String fileName = request.getRealPath("/") + "temp" + File.separator + "temp.xls";
		HttpTools.saveUrlAs(url, fileName);
		List<String> list = OldExcelParse.parseExcel(fileName);
		new File(fileName).delete();//删除临时文件
		StringBuffer sb = new StringBuffer();
		for(String line : list) {
			sb.append(line);
		}
		return sb.toString();
	}
}
