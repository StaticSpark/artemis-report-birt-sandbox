package com.funshion.artemis.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.funshion.artemis.exportUtil.bean.ReportSqlBean;
import com.funshion.artemis.report.bean.ColumnMappingXmlBean;
import com.funshion.artemis.report.bean.ColumnXmlBean;
import com.funshion.artemis.report.bean.ReportTypeXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;

/**
 * 解析报告xml文件
 * 
 * @author shenwf Reviewed by
 */
public class ReportXmlReader {
	private static List<ReportXmlBean> reportList = new ArrayList<ReportXmlBean>();
	private static List<ColumnXmlBean> columnList = new ArrayList<ColumnXmlBean>();
	private static List<ReportTypeXmlBean> reportTypeList = new ArrayList<ReportTypeXmlBean>();
	private static List<ColumnMappingXmlBean> columnMappingList = new ArrayList<ColumnMappingXmlBean>();
	private static List<ReportSqlBean> reportSqlList = new ArrayList<ReportSqlBean>();

	static {
		readReportBasicData();
		readColumnInfo();
		readReportType();
		packagingReportBean();
		readColumnMappingInfo();
		readReportSql();
	}

	/**
	 * 获取报告基础数据
	 */
	public static void readReportBasicData() {
		try {
			String path = Tools.getClassRoot() + "xml" + File.separator + "rpt_base.xml";
			Document document = new SAXReader().read(path);
			Iterator<Element> iterator = document.getRootElement().elementIterator("report");
			//把每一个分布作为对象放在reportList列表中
			while (iterator.hasNext()) {
				Element report = (Element) iterator.next();
				List elements = report.elements();
				ReportXmlBean reportXmlBean = new ReportXmlBean();
				for (Object o : elements) {
					Element e = (Element) o;
					reportXmlBean.setValue(e);
				}
				reportList.add(reportXmlBean);
			}
			Collections.sort(reportList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取列信息
	 */
	public static void readColumnInfo() {
		try {
			columnList = new ArrayList<ColumnXmlBean>();
			String path = Tools.getClassRoot() + "xml" + File.separator + "rpt_column.xml";
			Document document = new SAXReader().read(path);
			Iterator<Element> iterator = document.getRootElement().elementIterator("column");
			while (iterator.hasNext()) {
				Element column = (Element) iterator.next();
				List elements = column.elements();
				ColumnXmlBean columnXmlBean = new ColumnXmlBean();
				for (Object o : elements) {
					Element e = (Element) o;
					columnXmlBean.setValue(e);
				}
				columnList.add(columnXmlBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取报告类型列表
	 */
	public static void readReportType() {
		try {
			String path = Tools.getClassRoot() + "xml" + File.separator + "dim_rpt_type.xml";
			Document document = new SAXReader().read(path);
			Iterator<Element> iterator = document.getRootElement().elementIterator("type");
			while (iterator.hasNext()) {
				Element column = (Element) iterator.next();
				List elements = column.elements();
				ReportTypeXmlBean reportType = new ReportTypeXmlBean();
				for (Object o : elements) {
					Element e = (Element) o;
					reportType.setValue(e);
				}
				reportTypeList.add(reportType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将 列信息组装到报告去（本来报告和列是分开的，此方法是将报告和列相对应起来）
	 */
	public static void packagingReportBean() {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		for (ReportXmlBean report : reportList) {
			String[] columns = report.getColumns().split(",");
			String columnDisplayNames = "";
			int i = 0;
			
			List<ColumnXmlBean> copyColumnList = new ArrayList<ColumnXmlBean>();
			for(int n = 0; n < columnList.size(); n++) {
				ColumnXmlBean copy = columnList.get(n).clone();
				copyColumnList.add(copy);
			}
			
			for (String colName : columns) {
				colName = colName.trim();
				boolean isExists = false;
				for (ColumnXmlBean column : copyColumnList) {
					if (column.getColumnName().equals(colName)) {
						columnDisplayNames = columnDisplayNames + column.getColumnDisplayName() + ",";
						column.setIndex(i++ + "");
						report.getColumnList().add(column);
						isExists = true;
					}
				}
				
				if(!isExists) {
					System.out.println(report.getRptDisplayName() + "该列不存在:" + colName);
				}
			}
			
			if(columnDisplayNames.length() > 0) {
				columnDisplayNames = columnDisplayNames.substring(0, columnDisplayNames.length() - 1);
				report.setColumnDisplayNames(columnDisplayNames);
			}

			for (ReportTypeXmlBean type : reportTypeList) {
				if (new String(type.getId() + "").equals(report.getRptType())) {
					report.setRptTypeName(type.getName());
				}
			}
		}
	}
	
	
	/*
	 * 
	 */
	public static void readColumnMappingInfo () {
		try{
			String path = Tools.getClassRoot() + "xml" + File.separator + "column_mapping.xml";
			Document document = new SAXReader().read(path);
			Iterator<Element> iterator = document.getRootElement().elementIterator("mapping");
			while (iterator.hasNext()) {
				Element mapping = (Element)iterator.next();
				List elements = mapping.elements();
				ColumnMappingXmlBean columnMappingXmlBean = new ColumnMappingXmlBean();
				for (Object o:elements) {
					Element e = (Element)o;
					columnMappingXmlBean.setValue(e);
				}
				columnMappingList.add(columnMappingXmlBean);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取rpt_sql.xml文件对象
	 */
	public static void readReportSql() {
		String path = Tools.getClassRoot()+"xml" +File.separator +"rpt_sql.xml";
		Document document;
		try {
			document = new SAXReader().read(path);
			Iterator<Element> iterator = document.getRootElement().elementIterator("sql");
			while(iterator.hasNext()) {
				Element sqls = (Element)iterator.next();
				List elements = sqls.elements();
				ReportSqlBean reportSql = new ReportSqlBean();
				for (Object o:elements) {
					Element e = (Element)o;
					reportSql.setValue(e);
				}
				reportSqlList.add(reportSql);
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}	
	}

	/**
	 * 根据id获取报告
	 * 
	 * @param id
	 * @return
	 */
	public static ReportXmlBean getReportById(Long id) {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		for (ReportXmlBean report : reportList) {
			if (report.getId().equals(id)) {
				return report;
			}
		}
		return null;
	}
	
	/**
	 * 根据列名标志获取列
	 * @param name
	 * @return
	 */
	public static ColumnXmlBean getColumnByName(String name) {
		List<ColumnXmlBean> list = ReportXmlReader.getColumnList();
		for(ColumnXmlBean column : list) {
			if(column.getColumnName().equals(name)) {
				return column;
			}
		}
		return null;
	}
	
	/**
	 * 根据Id 获取字段映射
	 * @return
	 */
	public static ColumnMappingXmlBean getColumnMappingById(Long id) {
		List<ColumnMappingXmlBean> columnMappingList = ReportXmlReader.gerColumnMappingList();
		for (ColumnMappingXmlBean columnMapping : columnMappingList) {
			if (columnMapping.getId().equals(id)){
				return columnMapping;
			}
		}
		return null;
	}
	
	/**
	 * 得到所需的sql，若需根据某个属性值获得，可以扩展
	 * @return
	 */
	public static ReportSqlBean getReportSqlByName(String name) {
		List<ReportSqlBean> reportSql = ReportXmlReader.getReportSqlList();
		for (ReportSqlBean sql : reportSql) {
			if(name.equals(sql.getName())) {
				return sql;
			}
		}
		return null;
	}
	
	public static List<ColumnMappingXmlBean> gerColumnMappingList() {
		return columnMappingList;
	}
	
	public static List<ReportTypeXmlBean> getReportTypeList() {
		return reportTypeList;
	}

	public static List<ReportXmlBean> getReportList() {
		return reportList;
	}

	public static List<ColumnXmlBean> getColumnList() {
		return columnList;
	}
	public static List<ReportSqlBean> getReportSqlList() {
		return reportSqlList;
	}
}
