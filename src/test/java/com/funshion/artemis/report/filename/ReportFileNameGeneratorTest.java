package com.funshion.artemis.report.filename;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * 报告名生成类测试
 * @author guanzx
 *
 */
public class ReportFileNameGeneratorTest extends TestCase {
	
	public String currentDate = null;
	public ReportFileNameGenerator reportFileNameGenerator;
	
	@Before 
	public void setUp() {
		reportFileNameGenerator = new ReportFileNameGenerator();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		currentDate = "("+formatter.format(date)+")";
	}
	
	@Test
	/**
	 * 测试报告名为中文名
	 */
	public void testBaseNameHasChinese() {		
		String reportType = "";
		String fileName = "concurrent_report";
		String extensionName = "xls";
		String result ="Microlens.funshion.com "+"播控-并发报告"+currentDate +".xls";
		URL url = ClassLoader.getSystemResource("xml/rpt_mapping.xml");
		String path = url.getPath();
		File file = new File(path);		
		reportFileNameGenerator.rootFile = file.getParentFile().getParentFile().getAbsolutePath()+File.separator;
		String idGroup = "";
		try {
			result = new String(result.getBytes(),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
        Assert.assertEquals(ReportFileNameGenerator.makeFileName(reportType, fileName, extensionName,idGroup), result);	
	}
}
