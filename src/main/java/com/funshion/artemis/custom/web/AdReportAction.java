package com.funshion.artemis.custom.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.funshion.artemis.common.util.TimeUtils;
import com.funshion.artemis.custom.service.AdDataManager;
import com.funshion.artemis.custom.service.AdReportManager;
import com.funshion.artemis.custom.service.AdReportSqlManager;
import com.funshion.artemis.custom.service.AdpManager;
import com.funshion.artemis.custom.service.MatBaseManager;
import com.funshion.artemis.report.bean.ReportXmlBean;

/**
 * 报告控制跳转类
 * 
 * @author guanzx
 * 
 */
@Controller
public class AdReportAction {
	@Autowired
	private AdReportManager adReportManager;
	@Autowired
	private AdpManager adpManager;
	@Autowired
	private MatBaseManager matBaseManager;
	@Autowired
	private AdReportSqlManager adReportSqlManager;
	
	private static final Logger log = LoggerFactory.getLogger(AdReportAction.class);

	/**
	 * 自定义查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/ad-report-condition")
	public ModelAndView reportCondition() {
		ModelAndView conditionView = new ModelAndView("report/ad-report-condition");
		try {
			String reportJson = adReportManager.getReportListJson();
			String reportTypeJson = adReportManager.getReportType();
			conditionView.addObject("reportJson", reportJson);
			conditionView.addObject("reportTypeJson", reportTypeJson);
		} catch (Exception e) {
			log.error("", e);
		}
		return conditionView;
	}

	/**
	 * 报告展示页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/ad-report-engine")
	public ModelAndView reportEngine(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// json里提取前台参数
		String paramJson = request.getParameter("paramJson");
		String startDate = "";
		String endDate = "";
		String reportId = "";
		String conditonType = "";
		String conditionNames = "";
		String conditonTypeText = "";
		String ids = "";
		String freq = "";
		String areaIds = "";
		Long rptId = 0L;
		String reportPath = "";
		String reportName = "";
		List<ReportXmlBean> rptList;
		//二维组合报表需要的字段
		String idGroup = "";
		String firstColumn = "";
		String secondColumn = "";
		try {
			if (paramJson != null && !paramJson.equals("")) {// 通过自定义查询页面点击过来
				JSONObject jsonObject = JSONObject.fromObject(paramJson);
				startDate = (String) jsonObject.get("startDate");
				endDate = (String) jsonObject.get("endDate");
				reportId = (String) jsonObject.get("reportId");
				conditonType = (String) jsonObject.get("conditonType");
				conditionNames = (String) jsonObject.get("conditionNames");
				conditonTypeText = (String) jsonObject.get("conditonTypeText");
				ids = (String) jsonObject.get("ids");
				freq = (String) jsonObject.get("freq");
				areaIds = (String) jsonObject.get("areaIds");
			} else {// 通过atlas url链接过来的
				conditonType = request.getParameter("conditonType");
				conditonTypeText = adReportManager.getConditionTypeText(conditonType);
				ids = request.getParameter("id");
				conditionNames = adReportManager.getCondtionNames(conditonType, ids);
				startDate = TimeUtils.getYesterdayStartTime();
				endDate = TimeUtils.getYesterdayEndTime();
				rptId = adReportManager.getDayReportId();
				reportId = rptId + "";
			}
			//交叉报表的展现，若reportId包含逗号，则其为交叉报表
			if (reportId.contains(",")){
				String columns[] = reportId.split(",");
				reportPath = adReportManager.getRptPathById(new Long(columns[0])) + "#" +adReportManager.getRptPathById(new Long(columns[1]));
				String firstReport = adReportManager.getRptDisplayNameById(new Long(columns[0]));
				String secondReport = adReportManager.getRptDisplayNameById(new Long(columns[1]));			
				reportName = adReportManager.getReportName(firstReport,secondReport);		
				rptList = adReportManager.getRptList(conditonType);
				idGroup = reportId;
				rptId = 51L;
				//若id组合为广告-日期或者为日期-广告报告
				if(conditonType.equals("ad") && (idGroup.equals("10,1") || idGroup.equals("1,10"))){
					rptId = 54L;
				}	
				firstColumn = adReportManager.getColumnName(new Long(columns[0]));
				secondColumn = adReportManager.getColumnName(new Long(columns[1]));				
			} else {
				rptId = new Long(reportId);
				reportPath = adReportManager.getRptPathById(rptId);			
				reportName = adReportManager.getRptDisplayNameById(rptId);
				rptList = adReportManager.getRptList(conditonType);
			}
					
		} catch (Exception e) {
			log.error("", e);
			ModelAndView modelAndView = new ModelAndView("/report/ad-report-engine");
			modelAndView.addObject("Json", "");
			modelAndView.addObject("ParamJson", "");
			return modelAndView;
		}
		String resultjson = adReportManager.convertParamtoJson(startDate, endDate, conditionNames, conditonTypeText, rptId, idGroup,conditonType, ids, freq,areaIds, reportPath, rptList, reportName,firstColumn,secondColumn);
		ModelAndView modelAndView = new ModelAndView("/report/ad-report-engine");
		modelAndView.addObject("resultjson", resultjson);
		return modelAndView;
	}
	/**
	 * 固定报告
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ad-report-senior")
	public ModelAndView seniorReport(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/report/ad-report-senior");
	}
	/**
	 * 固定报告浏览
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ad-report-senior-browse")
	public ModelAndView seniorBrowse(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("/report/ad-report-senior-browse");
	}
}
