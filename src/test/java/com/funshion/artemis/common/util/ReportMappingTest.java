package com.funshion.artemis.common.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

/**
 * 报告中英文映射测试类
 * @author guanzx
 *
 */
public class ReportMappingTest {

	Document doc = null;
	Element root = null;
	File file = null;
	String currentDate = null;
	
	@Before 
	public void setUp() {
		
		initRootFile();
		initParentFile();
		initDate();		
			
	}
	
	@Test
	public void testMcMappingName() {
		String reportType = "";
		String fileName = "concurrent_report";
		String rootFile = file.getParentFile().getParentFile().getAbsolutePath()+File.separator;
		String result ="Microlens.funshion.com "+"播控-并发报告"+currentDate;
		Assert.assertEquals(ReportMapping.getMappingFileName(reportType, fileName, rootFile), result);
	}
	
	@Test
	public void testAdMappingName() {
		String reportType = "ad";
		String fileName = "day_report";
		String rootFile = file.getParentFile().getParentFile().getAbsolutePath()+File.separator;
		String result ="Microlens.funshion.com "+"广告流量报表"+currentDate;
		Assert.assertEquals(ReportMapping.getMappingFileName(reportType, fileName, rootFile), result);
	}
	
	@Test
	public void testMcReportType() {		
		String reportType = "";
		Assert.assertEquals(ReportMapping.getAdReportType(reportType,root),"播控");		
	}
	
	@Test
	public void testAdReportType() {
		String reportType = "ad";
		Assert.assertEquals(ReportMapping.getAdReportType(reportType, root), "广告");
	}
	
	@Test
	public void testNullRptName() {
		String baseName = "";
		Assert.assertEquals(ReportMapping.getRptName(baseName, root), "-");
	}
	
	@Test
	public void testRptName() {
		String baseName = "concurrent_report";
		Assert.assertEquals(ReportMapping.getRptName(baseName, root), "并发");
	}
	
	/*
	 * 初试化rpt_mapping.xml的路径
	 */
	public void initRootFile() {
		InputStream is = ClassLoader.getSystemResourceAsStream("xml/rpt_mapping.xml");
		SAXReader saxReader = new SAXReader();
		try {
			doc = saxReader.read(is);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化/xml/rpt_mapping.xml的父路径
	 */
	public void initParentFile() {
		URL url = ClassLoader.getSystemResource("xml/rpt_mapping.xml");
		String path = url.getPath();
		file = new File(path);
	}
	
	/*
	 * 初试化当前服务器时间
	 */
	public void initDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		currentDate = "("+formatter.format(date)+")";	
	}
	
}
