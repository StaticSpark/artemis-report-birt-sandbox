package com.funshion.artemis.report.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funshion.artemis.common.util.ContextTools;
import com.funshion.artemis.report.createreport.CreateReport;

/**
 * 报表sevlet类
 * 
 * @author shenwf Reviewed by zengyc
 */
public class ReportServer extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reportName = request.getParameter("reportName");
		try {
			CreateReport.updateReportByType(ContextTools.getContextMap(getServletContext()), reportName);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("update failure");
			return;
		}
		response.getWriter().println("update success " + reportName);
	}
}
