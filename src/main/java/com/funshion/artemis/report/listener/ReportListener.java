package com.funshion.artemis.report.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.funshion.artemis.common.util.ContextTools;
import com.funshion.artemis.report.createreport.CreateReport;

/**
 * 报表监听器
 * @author shenwf
 * Reviewed by zengyc
 */
public class ReportListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext sc= arg0.getServletContext();
//		sc.setInitParameter("BIRT_VIEWER_LOG_DIR", PropertiesConfigParse.getLogHome());
		
		Map<String, String> map = ContextTools.getContextMap(sc);
		try {
			CreateReport.updateReportByType(map, null);//更新报表
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
