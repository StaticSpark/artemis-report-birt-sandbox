package com.funshion.artemis.ad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IDataExtractionTask;
import org.eclipse.birt.report.engine.api.IDataIterator;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IResultMetaData;
import org.eclipse.birt.report.engine.api.IResultSetItem;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.engine.dataextraction.CSVDataExtractionOption;

/**
 * 测试 报告 从 rptdocument 取出数据 然后 跟数据库中对比
 * 
 * @author shenwf Reviewed by
 */
public class TestAd {
	public static void test1() throws BirtException {
		EngineConfig config = new EngineConfig();

		try {
			Platform.startup(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		String documentFile = "D:\\BI\\mc_imp_report.rptdocument";
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

		HTMLRenderOption htmlOptions = new HTMLRenderOption();
		htmlOptions.setImageDirectory("d:\\");
		htmlOptions.setOutputFileName("d:\\eventhandlerjar.html");
		htmlOptions.setOutputFormat("html");

		IRenderOption options = new RenderOption();
		options.setOutputFormat("excel");
		options.setOutputFileName("D:\\BI\\eventorder.html");

		EXCELRenderOption excelOptions = new EXCELRenderOption();
		excelOptions.setOutputFormat("xls");
		excelOptions.setOutputFileName("d:\\customers.xls");
		excelOptions.setWrappingText(true);

		IExtractionResults extractionResults = dataExtractionTask.extract();

		// dataExtractionTask.setRenderOption(options);
		// dataExtractionTask.run();
		// dataExtractionTask.close();

		IDataIterator dataIterator = extractionResults.nextResultIterator();
		StringBuffer stringBuffer = new StringBuffer();
		while (dataIterator.next()) {
			for (int j = 0; j < columnNames.length; j++) {
				Object object = dataIterator.getValue(j);
				if (object != null) {
					stringBuffer.append(object.toString());
					if (j < columnNames.length - 1) {
						stringBuffer.append(" , ");
					}
				}
			}
			stringBuffer.append("\n");
		}

		System.out.println(stringBuffer);
	}

	public static void test2() throws BirtException, FileNotFoundException {
		EngineConfig config = new EngineConfig();
		Platform.startup(config);

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(config);

		IReportDocument iReportDocument = engine.openReportDocument("D:\\BI\\new_report.rptdocument");
		IRenderTask task = engine.createRenderTask(iReportDocument);
		
		IDataExtractionTask iDataExtract = engine.createDataExtractionTask(iReportDocument);
		
		List<?> list = iDataExtract.getResultSetList();
		IResultSetItem resultSetItem = (IResultSetItem) list.get(0);
		String resultSetName = resultSetItem.getResultSetName();
		System.out.println(resultSetName + ":" + list.size());

		IRenderOption options = new RenderOption();
		options.setOutputFormat("csv");
		
		if (options.getOutputFormat().equalsIgnoreCase("html")) {
			HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
			htmlOptions.setImageDirectory("output/image");
			htmlOptions.setHtmlPagination(false);
			htmlOptions.setOutputFileName("d:\\BI\\file\\output.html");

			htmlOptions.setHtmlRtLFlag(false);
			htmlOptions.setEmbeddable(false);
		} else if (options.getOutputFormat().equalsIgnoreCase("pdf")) {
			PDFRenderOption pdfOptions = new PDFRenderOption(options);
			pdfOptions.setOption(IPDFRenderOption.FIT_TO_PAGE, new Boolean(true));
			pdfOptions.setOption(IPDFRenderOption.PAGEBREAK_PAGINATION_ONLY, new Boolean(true));
			pdfOptions.setOutputFileName("d:\\BI\\file\\output.pdf");
		} else  if (options.getOutputFormat().equalsIgnoreCase("xls")){
			EXCELRenderOption excelOptions = new EXCELRenderOption(options);
			excelOptions.setOutputFormat("xls");
			excelOptions.setOutputFileName("D:\\BI\\file\\output.xls");
			excelOptions.setWrappingText(true);
		} else {
			CSVDataExtractionOption csvOptions = new CSVDataExtractionOption();
			csvOptions.setOutputFormat("csv");
			OutputStream os = new FileOutputStream("d:\\BI\\file\\output.csv");
			csvOptions.setOutputStream(os);
			iDataExtract.extract(csvOptions);
		}

		task.setRenderOption(options);
		task.setPageRange("1");
		task.render();
		iReportDocument.close();
	}

	public static void test3() {
		IRunAndRenderTask task = null;
		IReportEngine engine = null;
		EngineConfig config = null;

		try {
			config = new EngineConfig();
			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);

			IReportRunnable design = engine.openReportDesign("d:\\dataseteventhandler.rptdesign");
			IReportDocument iReportDocument = engine.openReportDocument("D:\\BI\\day_report.rptdocument");
			// task.setParameterValue("Top Count", (new Integer(5)));
			task.validateParameters();

//			HTMLRenderOption options = new HTMLRenderOption();
//			options.setImageDirectory("d:\\");
//			options.setOutputFileName("d:\\eventhandlerjar.html");
//			options.setOutputFormat("html");

			// PDFRenderOption options = new PDFRenderOption();
			// options.setOutputFileName("d:\\topn.pdf");
			// options.setSupportedImageFormats("PNG;GIF;JPG;BMP;SWF;SVG");
			// options.setOutputFormat("pdf");

			 EXCELRenderOption options = new EXCELRenderOption();
			 options.setOutputFormat("xls");
			 options.setOutputFileName("d:\\customers3.xls");
			 options.setWrappingText(true);

			task.setRenderOption(options);
			task.run();
			task.close();
			engine.destroy();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (!task.getErrors().isEmpty())
			{
				for (Object e : task.getErrors())

				{

					((Exception) e).printStackTrace();

				}

			}
			Platform.shutdown();
			System.out.println("Finished");
		}
	}
	
	public static void createDoucumentFile(String designFile, String documentFile) throws EngineException {
		 EngineConfig config = new EngineConfig(); 
				
		 try { 
		      Platform.startup(config); 
		 } catch (Exception e) { 
		      e.printStackTrace(); 
			 //handle the exception here 
		 } 
		 
		 IReportEngineFactory factory = (IReportEngineFactory) Platform 
		      .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY); 
		 IReportEngine engine = factory.createReportEngine(config);
		 IReportRunnable runnable = engine.openReportDesign(designFile); 
		 IRunTask task = engine.createRunTask(runnable);
		 task.setParameterValue("id", 30 + "");
		 task.run(documentFile);;
	}
	
	public static String readDocumentFile(String documentFile) throws BirtException {
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

		System.out.println(stringBuffer);
		return stringBuffer.toString();
	}
	
	public static void percent() {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#.00");
		Double d = 5.0/160.0 *100;
		System.out.println(d);
		System.out.println(df.format(d));
		System.out.println(new java.math.BigDecimal(Double.toString(d)).setScale(2,java.math.BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	public static void main(String[] args) throws Exception {
//		test2();
		percent();
//		String documentFile = "D:\\BI\\mc_imp_report.rptdocument";
//		String designFile = "D:\\BI\\new_report.rptdesign";
//		createDoucumentFile(designFile, documentFile);
//		readDocumentFile(documentFile);
	}
}
