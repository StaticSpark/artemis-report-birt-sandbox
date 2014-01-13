package com.funshion.artemis.report.filename;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.birt.report.utility.filename.IFilenameGenerator;

import com.funshion.artemis.common.util.ReportMapping;
import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.report.bean.ColumnMappingXmlBean;

/**
 * 报表导出文件名生成类
 * 
 * @author shenwf Reviewed by zengyc
 */
public class ReportFileNameGenerator implements IFilenameGenerator {
	
	public static final String DEFAULT_FILENAME = "report";
	public static String rootFile = Tools.getClassRoot();
	
	public String getFilename(String baseName, String fileExtension, String outputType, Map options) {		
		HttpServletRequest obj = (HttpServletRequest)options.get(this.OPTIONS_HTTP_REQUEST);
		String reportType = obj.getParameter("conditonType");
		String idGroup = obj.getParameter("idGroup");
		return makeFileName(reportType,baseName,fileExtension,idGroup);
	}
	
	/**
	 * 导出报告的文件名
	 * @param reportType
	 * @param fileName
	 * @param extensionName
	 * @return
	 */
	public static String makeFileName(String reportType,String fileName,String extensionName,String idGroup) {
		
		String baseName = ReportMapping.getMappingFileName(reportType,fileName,rootFile);;
		
		if (idGroup != null && idGroup.contains(",")) {
			baseName = ReportMapping.getTwoDimensionalFileName(reportType,idGroup);
		}
		
		/*if (baseName == null || baseName.trim().length() <= 0) {
			baseName = DEFAULT_FILENAME;
		}*/

		// 检查是否有中文字符
		for (int i =0;i<baseName.length();i++) {
			char c = baseName.charAt(i);
			
			if (c < 0x00 || c >= 0x80) {
				try {
					baseName = new String(baseName.getBytes(Charset.defaultCharset()),"iso-8859-1");
				} catch (UnsupportedEncodingException e) {
					baseName = DEFAULT_FILENAME;
				}
				break;
			}
		}
		
		// 添加扩展名
		if (extensionName != null && extensionName.length() > 0) {
			baseName += "." + extensionName; 
		}
		return baseName;
	}
}
