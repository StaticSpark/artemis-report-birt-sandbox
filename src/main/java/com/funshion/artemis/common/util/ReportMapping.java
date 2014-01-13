package com.funshion.artemis.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.funshion.artemis.report.bean.ColumnMappingXmlBean;

/**
 * 该类主要实现报告名的中英文映射
 * @author guanzx
 *
 */
public class ReportMapping {
	
	public static final String PREFIX    = "Microlens.funshion.com ";
	public static final String AD_SUFFIX = "流量报表";
	
	public static final String AD_TWODIMENSIONAL = "流量报表";
	public static final String MC_SUFFIX = "报告";
	public static final String MC_NAME   = "播控";
	
	public static String getMappingFileName(String reportType,String baseName,String rootFile) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = "("+formatter.format(date)+")";
		String reportTypeText = null;
		String rptName = null;
		SAXReader reader = new SAXReader();
		Document doc = null;		
		try {
			String path = rootFile + "xml" + File.separator + "rpt_mapping.xml";
			doc = reader.read(path);
			Element root = doc.getRootElement();
			reportTypeText = getAdReportType(reportType,root);
			rptName = getRptName(baseName,root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		if (reportTypeText.equals(MC_NAME)) {
			return PREFIX + reportTypeText + "-" +rptName + MC_SUFFIX + currentDate;
		}else{
			//return PREFIX + reportTypeText + "-" +rptName + AD_SUFFIX  + currentDate;
			return PREFIX + reportTypeText + AD_SUFFIX  + currentDate;
		}		
	}
	
	/**
	 * 返回报告的类型中文名
	 * @param reportType
	 * @param root
	 * @return
	 */
	public static String getAdReportType(String reportType,Element root) {
		
		if (reportType == null || reportType =="") {
			return MC_NAME;
		}else{
			String conditionText = root.selectSingleNode("report_type/conditionType[@name='"+reportType+"']").getText();
			return conditionText;
		}				
	}
	
	/**
	 * 返回报告的分布报表中文名
	 * @param baseName
	 * @param root
	 * @return
	 */
	public static String getRptName(String baseName,Element root) {
		
		//当baseName为空时，自定义其为“-”,一般情况下，此种情况不会出现
		if (baseName == null || baseName =="") {
			return "-";
		}else {
			String rptName = root.selectSingleNode("form_type/rptName[@name='"+baseName+"']").getText();
			return rptName;
		}			
	}
	
	
	//--------------------------------------------------------
	/**
	 * 二维分布报表导出文件名
	 * @param reportType
	 * @param idGroup
	 * @return
	 */
	public static String getTwoDimensionalFileName(String reportType,String idGroup) {
//		String[] columns = idGroup.split(",");
//		ColumnMappingXmlBean firstReport = ReportXmlReader.getColumnMappingById(new Long(columns[0]));
//		ColumnMappingXmlBean secondReport = ReportXmlReader.getColumnMappingById(new Long(columns[1]));
//		String firstReportName = firstReport.getRptDisplayName().substring(0, firstReport.getRptDisplayName().length()-2);
//		String secondReportName = secondReport.getRptDisplayName().substring(0, secondReport.getRptDisplayName().length()-2);
		String reportTypeName = getReportTypeNameFromReportType(reportType);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = "("+formatter.format(date)+")";
		//String baseName = PREFIX +reportTypeName+"- [ "+firstReportName+"-"+secondReportName+" ] "+AD_TWODIMENSIONAL+currentDate;
		String baseName = PREFIX + reportTypeName + AD_TWODIMENSIONAL+currentDate;
		return baseName;
	}
	
	public static String getReportTypeNameFromReportType(String reportType) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		String reportTypeText = "";
		try {
			String path = Tools.getClassRoot() + "xml" + File.separator + "rpt_mapping.xml";
			doc = reader.read(path);
			Element root = doc.getRootElement();
			reportTypeText = root.selectSingleNode("report_type/conditionType[@name='"+reportType+"']").getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return reportTypeText;
	}
}
