package com.funshion.artemis.custom.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.custom.bean.AdConditionBean;
import com.funshion.artemis.custom.bean.AdpBase;
import com.funshion.artemis.custom.bean.MatBase;
import com.funshion.artemis.custom.service.AdDataManager;
import com.funshion.artemis.custom.service.AdReportManager;
import com.funshion.artemis.custom.service.AdpManager;
import com.funshion.artemis.custom.service.MatBaseManager;

/**
 * 
 * @author zhaojj 
 * @since 2013-5-15
 * Reviewer 
 */
@Controller
public class AdReportDataAction {
	
	@Autowired
	private AdReportManager adReportManager;
	@Autowired
	private AdpManager adpManager;
	@Autowired
	private MatBaseManager matBaseManager;
	@Autowired
	private AdDataManager adChartManager;
	
	public GenerateJson generateJson = new GenerateJson();
    
	@RequestMapping("/ad/ad-chart-data")
	public void getChartData(HttpServletRequest request,  HttpServletResponse response) {
		String json = "";
		try {
			String startDate = request.getParameter("startTime");
			String endDate = request.getParameter("endTime");
			String reportId = request.getParameter("reportId");
			String conditonType = request.getParameter("conditonType");
			String ids = request.getParameter("ids");
			String areaIds = request.getParameter("areaIds");
			String freq = request.getParameter("freq");
			json = adChartManager.getChartDataJson(ids, startDate, endDate, Long.parseLong(reportId), conditonType, "", freq, areaIds);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Tools.transmitDataToJsp(request, response, json);
	}
	
	//ad、mat分页加载数据
	@RequestMapping("/ad/ad-report-page")
	public void reportAdPageList(HttpServletRequest request,  HttpServletResponse response) {
		String data = adReportManager.getAdCondition(request.getContextPath(),request.getParameterMap());
		Tools.transmitDataToJsp(request, response, data);
	}
	@RequestMapping("/ad/mat-report-page")
	public void reportMatPageList(HttpServletRequest request,  HttpServletResponse response) {
		String data = matBaseManager.getMatCondition(request.getParameterMap());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	//异步加载dataTable显示数据
	@RequestMapping("/ad/ad-report")
	public void reportAdList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getAdConditionList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	//加载精确查询的广告报告的数据
	@RequestMapping("/ad/ad-precise-report")
	public void reportPreciseAdList(HttpServletRequest request,  HttpServletResponse response) {
		String param = request.getParameter("param");
		List<AdConditionBean> conditionList = adReportManager.getPreciseAdConditionList(param);		
		String data = generateJson.generate(conditionList);
		Tools.transmitDataToJsp(request, response, data);
	}
	
	//加载精确查询的广告位报告的数据
	@RequestMapping("/ad/adp-precise-report")
	public void reportPreciseAdpList(HttpServletRequest request,  HttpServletResponse response) {
		String param = request.getParameter("param");
		List<AdpBase> adpList = adpManager.getPreciseReadyAdpBase(param);
		String data = generateJson.generate(adpList);	
		Tools.transmitDataToJsp(request, response, data);
	}
	
	//全选按钮所需要的数据
	@RequestMapping("/ad/ad-checkall-report")
	public void adReportAllCheck(HttpServletRequest request,HttpServletResponse response) {
		String param = request.getParameter("param");
		List<AdConditionBean> conditionList = adReportManager.getAdAllCheckList(param);		
		String data = generateJson.generate(conditionList);
		Tools.transmitDataToJsp(request, response, data);
	}
	@RequestMapping("/ad/mat-checkall-report")
	public void matReportAllCheck(HttpServletRequest request,HttpServletResponse response) {
		String param = request.getParameter("param");
		List<MatBase> matBaseList = matBaseManager.getMatAllCheckList(param);		
		String data = generateJson.generate(matBaseList);
		Tools.transmitDataToJsp(request, response, data);
	} 
	
		
	@RequestMapping("/ad/mat-report")
	public void reportMatList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(matBaseManager.getAllMat());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	@RequestMapping("/ad/adp-report")
	public void reportAdpList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adpManager.getReadyAdpBase());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	
	@RequestMapping("/ad/order-report")
	public void reportOrderList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getOrderConditionList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	@RequestMapping("/ad/acc-report")
	public void reportAccList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getClientBaseList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	@RequestMapping("/ad/sale-report")
	public void reportSaleList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getSaleList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	@RequestMapping("/ad/cat-report")
	public void reportCatList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getDimMedCategoryList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	@RequestMapping("/ad/country-report")
	public void reportCountryList(HttpServletRequest request,  HttpServletResponse response) {
		String data = generateJson.generate(adReportManager.getDimMedCountryList());
		Tools.transmitDataToJsp(request, response, data);
	}
	
	//--------------set and get method-----------------
	public AdReportManager getAdReportManager() {
		return adReportManager;
	}

	public void setAdReportManager(AdReportManager adReportManager) {
		this.adReportManager = adReportManager;
	}

	public AdpManager getAdpManager() {
		return adpManager;
	}

	public void setAdpManager(AdpManager adpManager) {
		this.adpManager = adpManager;
	}

	public MatBaseManager getMatBaseManager() {
		return matBaseManager;
	}

	public void setMatBaseManager(MatBaseManager matBaseManager) {
		this.matBaseManager = matBaseManager;
	}
	
	
}
