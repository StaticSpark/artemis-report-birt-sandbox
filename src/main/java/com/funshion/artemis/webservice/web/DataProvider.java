package com.funshion.artemis.webservice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.custom.bean.AdBase;
import com.funshion.artemis.custom.dao.AdBaseDao;
import com.funshion.artemis.custom.service.AdDataManager;
import com.funshion.artemis.custom.service.AdpManager;
import com.funshion.artemis.report.manager.ReportManager;

@Controller
public class DataProvider {
	@Autowired
	private ReportManager reportManager;
	
	@Autowired
	private AdpManager adpManager;
	
	@Autowired
	private AdBaseDao adBaseDao;
	
	@Autowired
	private AdDataManager adChartManager;
	
	public GenerateJson generateJson = new GenerateJson();
	
	@RequestMapping("/birt/connection")
	public void birtConnection (HttpServletResponse response) throws IOException {
		String data = " {\"table\": [{\"connection\": \"success\" }]} ";
		System.out.println("connection:" + data);
		response.getWriter().write(data);
	}
	
	@RequestMapping("/data")
	public void reportTest(HttpServletRequest request,  HttpServletResponse response) throws IOException {
		System.out.println(request.getParameterMap());
		String ids = request.getParameter("ids");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String reportIdStr = request.getParameter("reportId");
		Long reportId = Long.parseLong(reportIdStr);
		String conditonType = request.getParameter("conditonType");
		String freq = request.getParameter("freq");
		String areaIds = request.getParameter("areaIds");
		
		ids = ids.replaceAll("comma", ",");
		areaIds = areaIds.replaceAll("#", ",");
		
		startTime = startTime.replace("space", " ");
		endTime = endTime.replace("space", " ");
		
		List list = adChartManager.getSqlData(ids, startTime, endTime, reportId, conditonType, null, freq, areaIds);
		String data = generateJson.generate(list);
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		data = " {\"table\":" + data + "} ";
		System.out.println("data:" + data);
		response.getWriter().write(data);
	}
}
