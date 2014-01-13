package com.funshion.artemis.mc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.mc.service.McAreaManager;
import com.funshion.artemis.mc.service.McReportManager;

@Controller
public class McActionWeb {
	@Autowired
	private McReportManager mcReportManager;
	@Autowired
	private McAreaManager mcAreaManager;
	@Autowired
	private HttpServletRequest request;
	private static final Logger log = LoggerFactory.getLogger(McActionWeb.class);

	/**
	 * imp source 数据来源报告页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mc/imp-source-report")
	public ModelAndView impSourceReport() {
		String json = mcReportManager.getImpSourceInfo(request);
		return new ModelAndView("mc/imp-source-report", "json", json);
	}

	/**
	 * imp source 虚拟地域报告页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mc/imp-report-day-areagroup")
	public ModelAndView impAreaGroupReport() {
		String json = mcReportManager.getImpAreaGroupInfo(request);
		return new ModelAndView("mc/imp-report-areagroup", "json", json);
	}

	/**
	 * 播控列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mc/mc-list")
	public ModelAndView mcList() {
		return new ModelAndView("mc/mc-list");
	}

	/**
	 * 播控数据列表
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mc/data-list")
	public void getMcData(HttpServletResponse response) throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf8");
		String search = request.getParameter("search");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String data = mcReportManager.getMcInfoForGrid(search, page, rows);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			log.error("", e);
			try {
				response.getWriter().println("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// uv投放周期报告
	@RequestMapping("/mc/uv-report")
	public ModelAndView uvReport() {
		String id = "";
		String json = "";
		try {
			id = request.getParameter("id");
			json = mcReportManager.getImpUvReportInfo(id, "uv");
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/uv-report", "json", json);
	}

	// uv每日报告
	@RequestMapping("/mc/uv-report-day")
	public ModelAndView uvDayReport() {
		String id = "";
		String json = "";
		try {
			id = request.getParameter("id");
			json = mcReportManager.getImpUvReportInfo(id, "uv-day");
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/uv-report-day", "json", json);
	}

	@RequestMapping("/mc/uv-report-day-areagroup")
	public ModelAndView uvDayAreaGroupReport() {
		String monId = "";
		String areaId = "";
		String monDate = "";
		String json = "";
		try {
			monId = request.getParameter("monId");
			areaId = request.getParameter("areaId");
			monDate = request.getParameter("monDate");
			json = mcReportManager.getUvDayAreaGroupReportInfo(monId, areaId, monDate);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/uv-report-day-areagroup", "json", json);
	}

	@RequestMapping("/mc/uv-report-areagroup")
	public ModelAndView uvAreaGroupReport() {
		String monId = "";
		String areaId = "";
		String monDate = "";
		String json = "";
		try {
			monId = request.getParameter("monId");
			areaId = request.getParameter("areaId");
			monDate = request.getParameter("monDate");
			json = mcReportManager.getUvDayAreaGroupReportInfo(monId, areaId, monDate);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/uv-report-areagroup", "json", json);
	}

	// uv小时报告
	@RequestMapping("/mc/uv-report-hour")
	public ModelAndView uvHourReport() {
		try {
			String monId = request.getParameter("id");
			String areaId = request.getParameter("areaId");
			String monDate = request.getParameter("date");
			String uvLimit = request.getParameter("uvLimit");
			String uvSn = request.getParameter("uvSn");
			String type = request.getParameter("type");
			String uvId = request.getParameter("uvId");
			String json = mcReportManager.getMcUvHourReportInfo(Long.parseLong(monId), areaId, monDate, uvLimit, uvSn, type, uvId);
			return new ModelAndView("mc/uv-report-hour", "json", json);
		} catch (Exception e) {
			log.error("", e);
			return new ModelAndView("mc/uv-report-hour", "error", "error");
		}
	}

	// imp日期报告
	@RequestMapping("/mc/imp-report")
	public ModelAndView impReport() {
		String id = "";
		String json = "";
		String reportName = "";
		try {
			id = request.getParameter("id");
			reportName = request.getParameter("reportName");
			json = mcReportManager.getImpUvReportInfo(id, "imp");
			//将reportName追加到json尾部,供报告切换使用
			json = json.substring(0, json.length() - 1);
			reportName = reportName == null ? "" : reportName;
			json += ",\"reportName\":\"" + reportName +"\"}";
			
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/imp-report", "json", json);
	}

	// imp日期报告
	@RequestMapping("/mc/imp-day-chart")
	public void impDayChart(HttpServletResponse response) {
		String json = "";
		long start = System.currentTimeMillis();
		try {
			json = mcReportManager.getImpDayChartData(request);
		} catch (Exception e) {
			log.error("", e);
		}
		long end = System.currentTimeMillis();
		System.out.println("图表耗时(天):" + (end - start));
		Tools.transmitDataToJsp(request, response, json);
	}
	
	   // imp日期报告
		@RequestMapping("/mc/imp-hour-chart")
	  public void impHourChart(HttpServletResponse response) {
			String json = "";
			long start = System.currentTimeMillis();
			try {
				json = mcReportManager.getImpHourChartData(request);
			} catch (Exception e) {
				log.error("", e);
			}
			long end = System.currentTimeMillis();
			System.out.println("图表耗时(小时):" + (end - start));
			Tools.transmitDataToJsp(request, response, json);
		}

	// imp小时报告
	@RequestMapping("/mc/imp-report-hour")
	public ModelAndView impHourReport() {
		try {
			String monId = request.getParameter("monId");
			String areaId = request.getParameter("areaId");
			String monDate = request.getParameter("monDate");
			String impLimit = request.getParameter("impLimit");
			String path = request.getParameter("path");
			String isVisualArea = request.getParameter("isVisualArea");
			String areaGroupName = request.getParameter("areaGroupName");
			String areaType = request.getParameter("areaType");
			String json = mcReportManager.getMcImpHourReportInfo(Long.parseLong(monId), Long.parseLong(areaId), monDate, impLimit, path, isVisualArea, areaGroupName, areaType);
			return new ModelAndView("mc/imp-report-hour", "json", json);
		} catch (Exception e) {
			log.error("", e);
			return new ModelAndView("mc/imp-report-hour", "error", "error");
		}
	}

	/*
	 * 播控imp小计小时报告
	 * 
	 * @author guanzx
	 */
	@RequestMapping("/mc/imp-report-sum-hour")
	public ModelAndView impSumHourReport() {
		try {
			String monId = request.getParameter("monId");
			String areaId = request.getParameter("areaId");
			String areaName = request.getParameter("areaName");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String impLimit = request.getParameter("impLimit");
			String path = request.getParameter("path");
			String json = mcReportManager.getMcImpSumHourReportInfo(Long.parseLong(monId), Long.parseLong(areaId), startDate, endDate, impLimit, path);
			return new ModelAndView("mc/imp-report-sum-hour", "json", json);
		} catch (Exception e) {
			log.error("", e);
			return new ModelAndView("mc/imp-report-sum-hour", "error", "error");
		}
	}

	/**
	 * 并发报告
	 * 
	 * @author guanzx
	 */
	@RequestMapping("/mc/concurrent-report")
	public ModelAndView concurrentReport() {
		String id = "";
		String json = "";
		try {
			id = request.getParameter("id");
			json = mcReportManager.getConcurrentInfo(id);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/concurrent-report", "json", json);
	}

	/**
	 * 并发报告
	 * 
	 * @author guanzx
	 */
	@RequestMapping("/mc/concurrent-report-uv")
	public ModelAndView concurrentUvReport() {
		String id = "";
		String json = "";
		try {
			id = request.getParameter("id");
			json = mcReportManager.getConcurrentInfo(id);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/concurrent-report-uv", "json", json);
	}

	/**
	 * 并发报告
	 * 
	 * @author guanzx
	 */
	@RequestMapping("/mc/concurrent-report-ip")
	public ModelAndView concurrentIpReport() {
		String id = "";
		String json = "";
		try {
			id = request.getParameter("id");
			json = mcReportManager.getConcurrentInfo(id);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/concurrent-report-ip", "json", json);
	}

	/**
	 * 获取并发报告分布图所需的数据
	 * 
	 * @param response
	 */
	@RequestMapping("/mc/concurrent-report-chart")
	public void getConcurrentReportChartData(HttpServletResponse response) {
		String monId = request.getParameter("monId");
		String monDate = request.getParameter("monDate");
		String type = request.getParameter("type");
		String json = mcReportManager.getConcurrentDate(monId, monDate, type);
		//System.out.println(json);
		Tools.transmitDataToJsp(request, response, json);
	}
	
	/**
	 * 获取播控超频uv地域分布所需的数据
	 * @throws Exception 
	 * @throws SecurityException 
	 */
	@RequestMapping("/mc/mc-overlock-area-chart")
	public void getMcOverlockAreaChart(HttpServletResponse response) throws SecurityException, Exception {
		String monId = request.getParameter("monId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String areaId = request.getParameter("areaId");
		String json = mcReportManager.getMcOverLockAreaJson(monId,startDate,endDate,areaId);
		//System.out.println(json);
		Tools.transmitDataToJsp(request, response, json);
	}
	/**
	 * 获取播控超频uv日期分布所需的数据
	 * @param response
	 * @throws SecurityException
	 * @throws Exception
	 */
	@RequestMapping("/mc/mc-overlock-date-chart")
	public void getMcOverlockDateChart(HttpServletResponse response) throws SecurityException, Exception {
		String monId = request.getParameter("monId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String areaId = request.getParameter("areaId");
		String json = mcReportManager.getMcOverLockDateJson(monId,startDate,endDate,areaId);
		//System.out.println(json);
		Tools.transmitDataToJsp(request, response, json);
	}

    /**
     * 获取UV报告分布图所需的数据
     *
     * @param response
     */
    @RequestMapping("/mc/uv-report-chart")
    public void getPeriodUVReportChartData(HttpServletResponse response) {
        String json = getUvDataByUvChartType("period");
        Tools.transmitDataToJsp(request, response, json);
    }

    /**
     * 获取每日UV报告分布图数据
     * @param response
     */
    @RequestMapping("/mc/daily-uv-report-chart")
    public void getDailyUVReportChartData(HttpServletResponse response) {
        String json = getUvDataByUvChartType("daily");
        Tools.transmitDataToJsp(request, response, json);
    }

    /**
     * 根据不同的uv数据类型返回数据
     *
     * @param uvChartType
     * @return
     */
    private String getUvDataByUvChartType(String uvChartType) {
        String monId = request.getParameter("monId");
        String monDateFrom = request.getParameter("monDateFrom");
        String monDateTo = request.getParameter("monDateTo");
        String areaId = request.getParameter("area_id");
        String retJson = null;
        if ("daily".equalsIgnoreCase(uvChartType)) {
            retJson = mcReportManager.getDailyUVData(monId, monDateFrom,monDateTo,areaId);
        } else if ("period".equalsIgnoreCase(uvChartType)) {
            retJson = mcReportManager.getPeriodUVData(monId, monDateFrom, monDateTo, areaId);
        }
        return retJson;
    }

	/**
	 * 获取地域名字
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/mc/mc-area")
	public void getAreaName(HttpServletResponse response) {
		String name = mcAreaManager.getAreaName(request.getParameter("areaId"));
		Tools.transmitDataToJsp(request, response, name);
	}

	/**
	 * 播控获取月份稳定UV数据
	 * 
	 * @return
	 */
	@RequestMapping("/mc/stable-uv")
	public ModelAndView getStableUv() {
		String monDate = "";
		String monType = "";
		String json = "";
		try {
			monDate = request.getParameter("monDate");
			monType = request.getParameter("monType");
			json = mcReportManager.getStableUvInfo(monDate, monType);
		} catch (Exception e) {
			log.error("", e);
		}
		return new ModelAndView("mc/stable-uv", "json", json);
	}

	/**
	 * ajax 返回稳定UV的和，动态更新页面的UV总数显示
	 * 
	 * @param response
	 */
	@RequestMapping("/mc/ajax-stable-uv")
	public void ajaxStableUv(HttpServletResponse response) {
		String monDate = "";
		String monType = "";
		String json = "";
		try {
			monDate = request.getParameter("monDate");
			monType = request.getParameter("monType");
			json = mcReportManager.getStableUvInfo(monDate, monType);
		} catch (Exception e) {
			log.error("", e);
		}
		Tools.transmitDataToJsp(request, response, json);
	}
	//imp分天汇总地域图标
	@RequestMapping("/mc/imp-report-sum-area-chart")
	public void impReportSumDateChart(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String json = mcReportManager.getImpSumAreaChartData(request);
			Tools.transmitDataToJsp(request, response, json);
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
