package com.funshion.artemis.report.createreport;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.report.bean.ColumnXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;

/**
 * 创建报表
 * 
 * @author shenwf Reviewed by zengyc
 */
public class CreateReport {
	/**
	 * 创建报表
	 * 
	 * @param type
	 *            0 创建全部报表， 1 只创建 未生成的报表
	 * @param configMap
	 *            里面封装了系统参数
	 * @throws Exception
	 */
	public static void updateReportByType(Map<String, String> configMap, String rptName) throws Exception {
		String url = configMap.get("jdbc.url");
		String userName =  configMap.get("jdbc.username");
		String password = configMap.get("jdbc.password");
		String jndiName = configMap.get("jndi.name");
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		
		if(reportList.size() == 0) {
			throw new Exception();
		}
		
		cleanReport();

		for (ReportXmlBean reportXmlBean : reportList) {
			reportXmlBean.setDbUrl(url);
			reportXmlBean.setDbUserName(userName);
			reportXmlBean.setDbPassword(password);
			reportXmlBean.setJndiName(jndiName);
			Report report = null;
			if (reportXmlBean.getIsCross().equals("0")) {
				report = new NormalReport(reportXmlBean);
			} else {
				report = new CrossReport(reportXmlBean);
			}
			report.buildReport();
		}
	}
	
	/**
	 * 清理报告文件
	 * @param workFolder
	 */
	public static void cleanReport() {
		File file = new File(Tools.getBirtFileRoot());
		if(file.exists() && file.listFiles().length > 0) {
			File[] fileList = file.listFiles();
			for(File f : fileList) {
				if(f.getName().endsWith("rptdesign")) {
				   f.delete();
				}
			}
		}
	}
}
