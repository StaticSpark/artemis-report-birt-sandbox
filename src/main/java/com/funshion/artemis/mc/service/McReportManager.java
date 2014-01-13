package com.funshion.artemis.mc.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.custom.bean.McUVData;
import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;
import com.funshion.artemis.mc.bean.ConcurrentChartsData;
import com.funshion.artemis.mc.bean.McBase;
import com.funshion.artemis.mc.bean.McImpChartAreaBean;
import com.funshion.artemis.mc.bean.McImpHourParamVo;
import com.funshion.artemis.mc.bean.McUv;
import com.funshion.artemis.mc.bean.Series;
import com.funshion.artemis.mc.bean.UVChartsData;
import com.funshion.artemis.mc.dao.McAreaDao;
import com.funshion.artemis.mc.dao.McReportDao;
import com.funshion.artemis.mc.overlock.McUvPeriodChartsData;
import com.funshion.artemis.mc.overlock.PeriodUV;
import com.funshion.artemis.mc.overlock.UVData;
import com.funshion.artemis.mc.overlock.UVSeries;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;
import com.funshion.artemis.webservice.manager.WSDataManager;
import com.funshion.tools.Utils;
import com.ibm.icu.util.Calendar;

/**
 * 播控报告处理类
 * 
 * @author shenwf Reviewed by
 */
@Service
public class McReportManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private McReportDao mcReportDao;
	@Autowired
	private DimAreaDao dimAreaDao;
	@Autowired
	private McAreaDao mcAreaDao;
	@Autowired
	private McAreaManager mcAreaManager;
	@Autowired
	private ReportXmlDataHandler reportXmlDataHandler;
	@Autowired
	private WSDataManager wsDataManager;

	/**
	 * 获取地域小计数据。
	 * PS:
	 * po定义, sum xxx group by date 为 sum area 即地域小计
	 * sum xxx group by area 为 sum day 即 天小计
	 * @param request
	 * @return
	 */
	public String getImpSumAreaChartData(HttpServletRequest request)
	{
		Map<String, String> paramMap = new HashMap<String, String>();
		String paramStr = getParamStr(request);
		paramStr = paramStr.replaceAll("area_type", "s.area_type");
		
		paramMap.put("condition", paramStr);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> impDayList = wsDataManager.getData("mc_imp_day", paramMap);
		List<Map<String, Object>> clkDayList = wsDataManager.getData("mc_clk_day", paramMap);
		
		List<Map<String, Object>> impChokeDayList = wsDataManager.getData("mc_imp_choke_day", paramMap);
		List<Map<String, Object>> clkChokeDayList = wsDataManager.getData("mc_clk_choke_day", paramMap);
		
		List<Map<String, Object>> impChokeAreaDayList = wsDataManager.getData("mc_imp_choke_area_day", paramMap);
		List<Map<String, Object>> clkChokeAreaDayList = wsDataManager.getData("mc_clk_choke_area_day", paramMap);
		
		combinaData(resultList, impDayList);
		combinaData(resultList, clkDayList);
		
		combinaData(resultList, impChokeDayList);
		combinaData(resultList, clkChokeDayList);
		
		combinaData(resultList, impChokeAreaDayList);
		combinaData(resultList, clkChokeAreaDayList);

//		System.out.println(new GenerateJson().generate(resultList));
		resultList = sumDataByArea(resultList);	
		String json = new GenerateJson().generate(resultList); 
//		System.out.println(json);
		return json;
	}
	/**
	 * 汇总数据以天为维度
	 * @param resultList
	 */
	public List<Map<String, Object>> sumDataByArea(List<Map<String, Object>> resultList) 
	{
		HashMap<String, Map<String, Object>> midData = new HashMap<String, Map<String, Object>> ();
		
		String[] sumKey = {"impLimit","planImp","planClk","impFwd",
				"actImp","impBlock","impRate","clkRateLimit","clkRate","clkFwd",
				"actClk","clkBlock","impChokeSum","clkChokeSum","impChokeArea","clkChokeArea"};
		
		ArrayList<String> minKey = new ArrayList();
		minKey.add("impLimit");
		minKey.add("planImp");
		minKey.add("planClk");
		String midKey = "";
		Map<String, Object> midRs = null;
		for(Map<String, Object> map : resultList)
		{
			//以日期为维度
			midKey = map.get("monDate").toString();
			if(midData.containsKey(midKey))
			{
				midRs = midData.get(midKey);
				//sum某一项的值
				for(String k : sumKey)
				{	
					try
					{
						String dstNum = map.get(k) + "";
						if(!"".equals(dstNum) && !"null".equals(dstNum) && ! "-1".equals(dstNum))
						{
							//在minKey中 如有null 或-1 则 记为0  其他则同sum
							if(minKey.contains(k))
							{
								if("0".equals(midRs.get(k)))
									continue;
							}
							if(midRs.get(k) != null)
							{
								midRs.put(k, Integer.parseInt(midRs.get(k) + "")
										+ Integer.parseInt(dstNum));
							}
							else
								midRs.put(k, Integer.parseInt(dstNum));
						}
						else
						{
							if(midRs.get(k) == null)
								midRs.put(k, 0);
						}
					}
					catch (NumberFormatException e) { ; } //pass
				}
			}
			else
			{
				Map<String, Object> rsMap = new HashMap<String, Object>();
				for(String mk : map.keySet())
				{
					String tmpVar = map.get(mk) + "";
					if("-1".equals(tmpVar) || "null".equals(tmpVar))
						tmpVar = "0";
					rsMap.put(mk, tmpVar);
				}
				midData.put(midKey, rsMap);
			}
		}
	
		Object[] sortArr = midData.keySet().toArray();
		Arrays.sort(sortArr);
		resultList.clear();
		for(Object idx : sortArr)
			resultList.add(midData.get(idx.toString()));
		
		return resultList;	
	}
	/**
	 * 获取 imp 分天图表数据
	 * @param request
	 * @return
	 */
	public String getImpDayChartData(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String paramStr = getParamStr(request);
		paramStr = paramStr.replaceAll("area_type", "s.area_type");
		paramMap.put("condition", paramStr);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> impDayList = wsDataManager.getData("mc_imp_day", paramMap);
		List<Map<String, Object>> clkDayList = wsDataManager.getData("mc_clk_day", paramMap);
		
		List<Map<String, Object>> impChokeDayList = wsDataManager.getData("mc_imp_choke_day", paramMap);
		List<Map<String, Object>> clkChokeDayList = wsDataManager.getData("mc_clk_choke_day", paramMap);
		
		List<Map<String, Object>> impChokeAreaDayList = wsDataManager.getData("mc_imp_choke_area_day", paramMap);
		List<Map<String, Object>> clkChokeAreaDayList = wsDataManager.getData("mc_clk_choke_area_day", paramMap);
		
		combinaData(resultList, impDayList);
		combinaData(resultList, clkDayList);
		
		combinaData(resultList, impChokeDayList);
		combinaData(resultList, clkChokeDayList);
		
		combinaData(resultList, impChokeAreaDayList);
		combinaData(resultList, clkChokeAreaDayList);
		
		List<Map<String,Object>> list = sortData(resultList);
		String json = new GenerateJson().generate(list); 
		//System.out.println(json);
		return json;
	}
	
	/**
	 * 获取小时图表数据
	 * @param request
	 * @return
	 */
	public String getImpHourChartData(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String paramStr = getParamStr(request);
		paramMap.put("condition", paramStr);
		List<Map<String, Object>> impDayList = wsDataManager.getData("imp_hour_report", paramMap, "m.monDate DESC,", "m.monDate ASC,");
		String json = new GenerateJson().generate(impDayList); 
		System.out.println(json);
		return json;
	}
	
	/**
	 * 根据 http 参数生成数据库查询条件
	 * @param request
	 * @return
	 */
	public String getParamStr(HttpServletRequest request) {
		String areaIds = request.getParameter("areaIds");
		Long mcId = Long.parseLong(request.getParameter("id"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String path = request.getParameter("path");
		String isAreaGroup = request.getParameter("isAreaGroup");
		String paramStr = " mon_id = " + mcId;
		if(isAreaGroup != null && isAreaGroup.equals("1")) {//地域组
			paramStr += " and  area_type = " + areaIds;
		} else {
			paramStr += " and (area_type is NULL OR area_type = 'area' OR area_type = 'group')";
			if(areaIds != null && !areaIds.equals("")) {
				paramStr += " and area_id in (" + areaIds + ")";
			}
		}
		
		if(startDate != null && !startDate.equals("")) {
			paramStr += " and mon_date >= '" + startDate + "'";
		}
		
		if(endDate != null && !endDate.equals("")) {
			paramStr += " and mon_date <= '" + endDate + "'";
		}
		
		
		if(path != null && !path.equals("") && !path.equals("*") && !path.equals("null")) {
			paramStr += " and path = " + path;
		}
		
		return paramStr;
	}
	
	/**
	 * 格式化数据，按地域汇总到一起
	 * @param resultList
	 * @return
	 */
	public List<McImpChartAreaBean> formateData(List<Map<String, Object>> srcList) {
		List<McImpChartAreaBean> list = new ArrayList<McImpChartAreaBean>();
		for(Map<String, Object> map : srcList) {
			String areaName = map.get("areaName").toString();
			List<Map<String, Object>> areaList = null;
			for(McImpChartAreaBean areaBean : list) {
				if(areaBean.getAreaName().equals(areaName)) {
					areaList = areaBean.getList();
				}
			}
			
			if(areaList == null) {
				areaList = new ArrayList<Map<String, Object>>();
			} 
			
			areaList.add(map);
			McImpChartAreaBean bean = new McImpChartAreaBean();
			bean.setAreaName(areaName);
			bean.setList(areaList);
			list.add(bean);
		}
		
		return list;
	}
	
	/**
	 * 排序 (将 数据 按地域顺序排序)
	 * @param resultList
	 * @return
	 */
	public List<Map<String, Object>> sortData(List<Map<String, Object>> srcList) {
		Set<String> areaSet = new TreeSet<String>();
		for(Map<String, Object> map : srcList) {
			String areaId = map.get("areaId").toString();
			areaSet.add(areaId);
		}
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		for(String areaId : areaSet) {
			for(Map<String, Object> map : srcList) {
				if(areaId.equals(map.get("areaId").toString())) {
					resultList.add(map);
				}
			}
		}
		
		return resultList;
	}
	
	
	/**
	 * 将 各个表数据组合起来 (比如将 imp 表和 clk 表记录组合起来)
	 * 实现方式就是将2个list 中map key相对于的 数据拼到一起
	 * 比如： resultList 有一个 map_a， 里面记录为 mark=a, imp=33
	 *        subList 有一个map_b，里面记录为 mark=a ,clk = 22
	 *        那么这个map_a 最后为 mark=a, imp = 33,clk = 22
	 * @param resultList
	 * @param subList
	 */
	public void combinaData(List<Map<String, Object>> resultList, List<Map<String, Object>> subList) {
	     	for(Map<String, Object> map : subList) {
	     		String mark = map.get("mark").toString();
	     		boolean isExits = false;
	     		for(Map<String, Object> resultMap : resultList) {
	     			if(resultMap.get("mark").toString().equals(mark)) {//去找相同条件的记录
	     				isExits = true;
	     				resultMap.get("mark");
	     				
	     				for(Map.Entry<String, Object> entry : map.entrySet()) {
	     					if(!resultMap.containsKey(entry.getKey())) {//如果不存在就添加进去
	     						resultMap.put(entry.getKey(), entry.getValue());
	     					}
	     				}
	     			}
	     		}
	     		
	     		if(!isExits) {//resultList 不存在想对应的条件
	     			resultList.add(map);
	     		}
	     	}
	}

	/**
	 * 获取imp source 报告相关的信息
	 * 
	 * @return
	 */
	public String getImpSourceInfo(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		try {
			Long areaId = Long.parseLong(request.getParameter("areaId"));
			Long monId = Long.parseLong(request.getParameter("monId"));
			String type = request.getParameter("type");
			String areaGroupName = request.getParameter("areaGroupName");
			String areaName = mcAreaManager.getAreaName(areaId + "");
			String mcName = mcReportDao.getMcBaseById(monId).getName();
			List<ReportXmlBean> list = reportXmlDataHandler.getReportsByTypeName("播控source报告");
			ReportXmlBean selectReport = null;// 选择的那个报告
			for (ReportXmlBean report : list) {
				Enumeration<String> enumeration = request.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String paramName = enumeration.nextElement();
					if (report.getRptName().contains(paramName)) {
						report.setTotalVal(request.getParameter(paramName));
					}
				}

				if (report.getRptName().contains(type)) {
					selectReport = report;
				}
			}
			json.append("{");
			json.append("\"monId\":" + monId);
			json.append(",\"areaId\":" + areaId);
			json.append(",\"reportId\":" + selectReport.getId());
			json.append(",\"mcName\":\"" + mcName + "\"");
			json.append(",\"value\":\"" + request.getParameter("value") + "\"");
			json.append(",\"areaName\":\"" + areaName + "\"");
			json.append(",\"areaGroupName\":\"" + areaGroupName + "\"");
			json.append(",\"monDate\":\"" + request.getParameter("monDate") + "\"");
			json.append(",\"startDate\":\"" + request.getParameter("startDate") + "\"");
			json.append(",\"endDate\":\"" + request.getParameter("endDate") + "\"");
			json.append(",\"hour\":\"" + request.getParameter("hour") + "\"");
			json.append(",\"path\":\"" + request.getParameter("path") + "\"");
			json.append(",\"rptName\":\"" + selectReport.getRptName() + "\"");
			json.append(",\"rptList\":" + new GenerateJson().generate(list));
			json.append("}");
		} catch (Exception e) {
			logger.error("", e);
		}
		return json.toString();
	}

	/**
	 * 获取该播控相关的信息
	 * 
	 * @param request
	 * @return
	 */
	public String getImpAreaGroupInfo(HttpServletRequest request) {
		String areaId = request.getParameter("areaId");
		Long monId = Long.parseLong(request.getParameter("id"));
		String monDate = request.getParameter("monDate");
		String path = request.getParameter("path");
		String areaGroupName = request.getParameter("areaGroupName");
		String areaName = mcAreaManager.getAreaName(areaId);
		String mcName = mcReportDao.getMcBaseById(monId).getName();
		List pathList = mcReportDao.getPathsByMonAreaDate(monId, areaId, monDate);
		List areaGroupList = mcAreaDao.getGroupAreaList(monId, monDate, areaId);
		Collections.sort(pathList);
		pathList.add(0, "'*'");
		StringBuffer json = new StringBuffer("{");
		json.append("\"monId\":" + monId);
		json.append(",\"areaId\":" + areaId);
		json.append(",\"mcName\":\"" + mcName + "\"");
		json.append(",\"path\":\"" + path + "\"");
		json.append(",\"startDate\":\"" + monDate + "\"");
		json.append(",\"endDate\":\"" + monDate + "\"");
		json.append(",\"areaName\":\"" + areaName + "\"");
		json.append(",\"pathList\":" + pathList);
		json.append(",\"areaGroupName\":\"" + areaGroupName + "\"");
		json.append(",\"areaList\":" + new GenerateJson().generate(areaGroupList));
		json.append("}");
		return json.toString();
	}

	/**
	 * 获取播控列表(jqgrid组件所需要的数据)
	 * 
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	public String getMcInfoForGrid(String search, String page, String rows) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		List list = mcReportDao.getMcBaseBySearchText(search, Integer.parseInt(page), Integer.parseInt(rows));
		String json = new GenerateJson().generate(list);
		int allMcSize = mcReportDao.getMcBaseCountBySearchText(search).size();
		int pageSize = allMcSize % Integer.parseInt(rows) == 0 ? allMcSize / Integer.parseInt(rows) : allMcSize / Integer.parseInt(rows) + 1;

		String result = "{\"records\":" + allMcSize + ",\"total\":" + pageSize + ",\"userdata\":null,\"rows\":" + json + "}";
		return result;
	}

	/**
	 * 根据播控id获取相关信息
	 * 
	 * @param id
	 * @param rptType
	 * @return
	 */
	public String getImpUvReportInfo(String id, String type) {
		Long monId = Long.parseLong(id);
		List pathList = mcReportDao.getPathsByMonId(monId);
		Collections.sort(pathList);
		String paths = pathList.toString();
		paths = paths.length() > 2 ? paths.substring(1, paths.length() - 1) : "";
		Map<String, Object> map = mcReportDao.getMonDatePeriod(monId, type);
		McBase mcBase = mcReportDao.getMcBaseById(monId);
		mcBase.setPaths(paths);
		List<DimArea> list = mcAreaManager.getAreaListByMcId(monId);
		if("imp".equals(type)) {
			list.remove(list.size() - 1);
		}
		
		GenerateJson gj = new GenerateJson();
		String json = "{";
		json += "\"mcBase\":" + gj.generate(mcBase);
		json += ",\"areaInfo\":" + gj.generate(list);
		json += ",\"maxDate\":\"" + map.get("maxDate") + "\"";
		json += ",\"minDate\":\"" + map.get("minDate") + "\"";
		json += "}";
		return json;
	}

	/**
	 * 获取uv地域组信息
	 * 
	 * @param monId
	 * @param areaId
	 * @param monDate
	 * @return
	 */
	public String getUvDayAreaGroupReportInfo(String monId, String areaId, String monDate) {
		Long mcId = Long.parseLong(monId);
		Long areaGroupId = Long.parseLong(areaId);
		McBase mcBase = mcReportDao.getMcBaseById(mcId);
		DimArea dimArea = mcAreaDao.getDimAreaById(areaGroupId);
		GenerateJson gj = new GenerateJson();
		String json = "{";
		json += "\"mcBase\":" + gj.generate(mcBase);
		json += ",\"dimArea\":" + gj.generate(dimArea);
		json += ",\"monDate\":'" + monDate + "'";
		json += "}";
		return json;
	}

	/**
	 * 根据条件获取path 和 点击控制率列表
	 * 
	 * @param monId
	 * @param areaId
	 * @param monDate
	 * @return
	 */
	public String getMcImpHourReportInfo(Long monId, Long areaId, String monDate, String impLimit, String requestPath, String isVisualArea, String areaGroupName, String areaType) {
		List<McImpHourParamVo> paramList = new ArrayList<McImpHourParamVo>();
		try {
			Long formateId = null;
			if (areaId.toString().length() >= 7) {
				formateId = Long.parseLong(areaId.toString().substring(1, areaId.toString().length() - 5));
			} else {
				formateId = areaId;
			}
			List list = mcReportDao.getMcClkCtrl(monId, areaId);

			for (Object obj : list) {
				Map map = (Map) obj;
				if ((Integer) map.get("path") >= 0) {
					String path = (Integer) map.get("path") + "";
					McImpHourParamVo mcParamVo = new McImpHourParamVo();
					mcParamVo.setPath(path);
					Object clkLimit = map.get("clkrate_ctrl");
					if (clkLimit == null || ((String) clkLimit).equals("")) {
						mcParamVo.setClkCtrl("");
					} else {
						Float clkrateCtrl = Float.parseFloat((String) map.get("clkrate_ctrl"));
						mcParamVo.setClkCtrl(clkrateCtrl == null ? "" : Tools.getPercent(clkrateCtrl, 100));
					}
					paramList.add(mcParamVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		McBase mcBase = mcReportDao.getMcBaseById(monId);
		DimArea dimArea = null;
		if (areaId.toString().length() >= 7) {
			dimArea = mcAreaManager.getSpecialArea(areaId.toString());
		} else {
			dimArea = dimAreaDao.getAreaById(areaId.longValue());
		}

		List<DimArea> areaList = null;

		if ("1".equals(isVisualArea))
			areaList = mcAreaDao.getGroupAreaList(monId, monDate, areaType);
		else
			areaList = mcAreaManager.getAreaListByMcId(monId);

		StringBuffer json = new StringBuffer("{");
		json.append("\"impLimit\":" + impLimit + ",");
		json.append("\"mcBase\":" + new GenerateJson().generate(mcBase));
		json.append(",\"path\":'" + requestPath + "'");
		json.append(",\"monDate\":\"" + monDate + "\"");
		json.append(",\"area\":" + new GenerateJson().generate(dimArea));
		json.append(",\"areaGroupName\":\"" + areaGroupName + "\"");
		json.append(",\"pathInfo\":" + new GenerateJson().generate(paramList));
		json.append(",\"isVisualArea\":\"" + (isVisualArea == null ? "" : isVisualArea) + "\"");
		json.append(",\"areaList\":" + new GenerateJson().generate(areaList));
		json.append("}");

		return json.toString();
	}

	/*
	 * 播控imp小计小时报告中所需的信息封装
	 */
	public String getMcImpSumHourReportInfo(Long monId, Long areaId, String startDate, String endDate, String impLimit, String path) {
		GenerateJson gj = new GenerateJson();
		McBase mcBase = mcReportDao.getMcBaseById(monId);// mcBase中封装播控id,播控项名称
		DimArea dimArea = dimAreaDao.getAreaById(areaId);// dimArea中封装地域id,地域名称
		Map map = new HashMap();
		map.put("mcBase", mcBase);
		map.put("dimArea", dimArea);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("impLimit", impLimit);
		map.put("path", path);

		return gj.generate(map);
	}

	/**
	 * uv小时级报告向前台传的json参数
	 * 
	 * @param monId
	 * @param areaId
	 * @param monDate
	 * @param uvLimit
	 * @param uvSn
	 * @param type
	 * @param uvId
	 * @return
	 */
	public String getMcUvHourReportInfo(Long monId, String areaId, String monDate, String uvLimit, String uvSn, String type, String uvId) {

		McBase mcBase = mcReportDao.getMcBaseById(monId);
		String period = "";
		if (uvId != null) {
			McUv mcUv = mcReportDao.getMcUvById(Long.parseLong(uvId));
			if (mcUv != null) {
				period = mcUv.getStartDate() + "~" + mcUv.getEndDate();
			}
		}
		DimArea dimArea = null;
		if (areaId.toString().length() >= 7) {
			dimArea = mcAreaManager.getSpecialArea(areaId.toString());
		} else {
			dimArea = dimAreaDao.getAreaById(Long.parseLong(areaId));
		}

		StringBuffer json = new StringBuffer("{");
		json.append("\"uvLimit\":'" + uvLimit + "'");
		json.append(",\"mcBase\":" + new GenerateJson().generate(mcBase));
		json.append(",\"area\":" + new GenerateJson().generate(dimArea));
		json.append(",\"monDate\":\"" + monDate + "\"");
		json.append(",\"uvSn\":'" + uvSn + "'");
		json.append(",\"type\":'" + type + "'");
		json.append(",\"period\":'" + period + "'");
		json.append("}");
		return json.toString();
	}

	/**
	 * 并发报告播控id与name的对应信息，将数据封装在json串中发送到前端页面
	 * 
	 * @author guanzx
	 * @param id
	 * @return
	 */
	public String getConcurrentInfo(String id) {
		Long monId = Long.parseLong(id);
		McBase mcBase = null;
		GenerateJson gj = new GenerateJson();
		Map map = new HashMap();

		if (id.equals("-1")) {
			mcBase = new McBase();
			mcBase.setName("全部播控");
			mcBase.setStatus("1");
		} else
			mcBase = mcReportDao.getMcBaseById(monId);

		map.put("mcBase", mcBase);
		// 设置默认日期
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_MONTH, -1);
		
		Map<String, Object> uvDateMap = mcReportDao.getMonDatePeriod(monId, "concurrent_uv");
		// 1 in use 默认昨天 else
		map.put("uvMaxDate", uvDateMap.get("maxDate"));
		
		Map<String, Object> ipDateMap = mcReportDao.getMonDatePeriod(monId, "concurrent_ip");
		// 1 in use 默认昨天 else
		map.put("ipMaxDate", ipDateMap.get("maxDate"));
		return gj.generate(map);
	}

	/**
	 * 得到并发报告可视化所需的数据
	 * 
	 * @param mcReportDao
	 */
	public String getConcurrentDate(String monId, String monDate, String type) {
		List<Map<String, Object>> list = mcReportDao.getConcurrentDateBymonIdandMonDate(monId, monDate, type);
		// 指定时段的时段点
		String[] period = { "1s", "5s", "15s", "1min", "10min", "1d" };
		String[] column = getColumn(type);
		ConcurrentChartsData chartDate = new ConcurrentChartsData();

		for (int i = 0; i < column.length; i++) {
			Series periodSeries = new Series();
			Series timeSeries = new Series();
			String name = getHeaderName(i + 1, type);
			periodSeries.setName(name);
			timeSeries.setName(name);
			List<Long> periodList = new ArrayList<Long>();
			List<Long> timeList = new ArrayList<Long>();
			for (Map<String, Object> map : list) {

				Object o = map.get(column[i]);
				Long value = 0L;
				if (o != null) {
					value = Long.parseLong(o.toString());
				}
				int count = 0;
				for (int j = 0; j < period.length; j++) {
					String per = (String) map.get("period");
					if (per.equals(period[j])) {
						periodList.add(value);
					} else {
						count++;
					}
				}
				// 指定时段分布中有6个时段，当统计量等于6的时候，说明该指标不是指定时段分布的，则加入小时报告
				if (count == 6) {
					timeList.add(value);
				}
			}
			periodSeries.setData(periodList);
			timeSeries.setData(timeList);
			chartDate.getPeriodSeries().add(periodSeries);
			chartDate.getTimeSeries().add(timeSeries);
		}

		for (Map<String, Object> map : list) {
			String xName = map.get("period").toString();
			int count = 0;
			for (int i = 0; i < period.length; i++) {
				if (xName.equals((period[i]))) {
					chartDate.getPeriodXAxis().add(xName);
				} else {
					count++;
				}
			}
			if (count == 6) {
				chartDate.getTimexAxis().add(xName);
			}
		}
		GenerateJson gj = new GenerateJson();
		return gj.generate(chartDate);
	}

	/**
	 * 根据类型来返回series对象随需要的name值
	 * 
	 * @param i
	 * @param type
	 * @return
	 */
	public String getHeaderName(int i, String type) {
		if (type.equals("uv")) {
			return i + "+UV";
		} else {
			return i + "+IP";
		}
	}

	/**
	 * 通过传递来的并发报告类型返回相应的值，该值与数据库查询的字段名称保持一致
	 * 
	 * @param type
	 * @return
	 */
	public String[] getColumn(String type) {
		String[] column = { "uv1_plus", "uv2_plus", "uv3_plus", "uv4_plus", "uv5_plus", "uv6_plus", "uv7_plus", "uv8_plus", "uv9_plus", "uv10_plus" };
		String[] ipColumn = { "ip1_plus", "ip2_plus", "ip3_plus", "ip4_plus", "ip5_plus", "ip6_plus", "ip7_plus", "ip8_plus", "ip9_plus", "ip10_plus" };
		if (type.equals("uv")) {
			return column;
		} else {
			return ipColumn;
		}
	}
	
	/**
	 * 播控UV超频-地域分布
	 * @param monId
	 * @param startDate
	 * @param endDate
	 * @param areaId
	 * @return
	 * @throws SecurityException
	 * @throws Exception
	 */
	public String getMcOverLockAreaJson (String monId,String startDate,String endDate,String areaId) throws SecurityException, Exception {
		List<Map<String,Object>> list = mcReportDao.getMcOverLockAreaData(monId,startDate,endDate,areaId);
		String json = getMcOverlockJson(list);
		return json;
	}
	
	/**
	 * 播控uv超频-日期分布
	 */
	public String getMcOverLockDateJson (String monId,String startDate,String endDate,String areaId) throws SecurityException, Exception {
		List<Map<String,Object>> list = mcReportDao.getMcOverLockDateData(monId,startDate,endDate,areaId);
		String json = getMcOverlockDateJson(list);
		return json;
	}
	/**
	 * 播控uv超频地域分布数据生成
	 * 
	 * @throws Exception 
	 * @throws SecurityException 
	 */
	public String getMcOverlockJson(List<Map<String,Object>> list) throws SecurityException, Exception{		
		String[] column = {"uv1","uv2","uv3","uv4","uv5","uv6","uv7","uv8","uv9","uv10"};		
		McUvPeriodChartsData chartDate = new McUvPeriodChartsData();
		//System.out.println(list);
		UVData uvData = packagingDataList(list,column);	
	
		for (int i=0;i<column.length;i++) {
			UVSeries uvSeries = new UVSeries();
			String legentName = getLegendName(column[i],i+1);
			uvSeries.setName(legentName);
			List<Double> dataList = new ArrayList<Double>();
			for (PeriodUV periodUV: uvData.getPeriodUvList()) {		 
				dataList = getEachUVList(periodUV,column[i],dataList); 
			}
			uvSeries.setData(dataList);
			chartDate.getSeries().add(uvSeries);
		}
		
		for (Map<String,Object> map:list) {			
			String areaName = map.get("area_name").toString();
			if (areaName.isEmpty()) {
				areaName = "全部地域";
			}
			chartDate.getAreaxAxis().add(areaName);
			chartDate.getDayxAxis().add(map.get("mon_date").toString());		
		}
		
		for (Map<String,Object> map:list) {		
			String uvLimit = map.get("uv_limit").toString();
			chartDate.getUvLimit().add(uvLimit);				
		}
		
		GenerateJson gj = new GenerateJson();
		return gj.generate(chartDate);
	}
	/**
	 * 得到播控uv超频分布的Legend表示
	 * @param name
	 * @param index
	 * @return
	 */
	public String getLegendName(String name,int index) {	
		return index + "+UV占比";
	}
	
	/**
	 * 利用java的反射机制获得相应uv的数据
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public List<Double> getEachUVList(PeriodUV periodUV,String currentUV,List<Double> dataList) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		 Field[] fields = periodUV.getClass().getDeclaredFields();
		 for (int j = 0;j<fields.length;j++) {
			 String fieldName = fields[j].getName();
			 if (fieldName.equals(currentUV)) {
				 String getMethodName = "get"
		                   + fieldName.substring(0, 1).toUpperCase()
		                   + fieldName.substring(1);
				 Method method = periodUV.getClass().getMethod(getMethodName, new Class[]{});
				 Object value = method.invoke(periodUV, new Class[]{});
				 dataList.add((Double)value);
			 }
			 
		 }
		return dataList;	
	}
	
	/**
	 * 将每一个列表封装为一个对象，以对象为基础进行解决问题:求和以及获得播放比
	 * @param list
	 * @return
	 */
	public UVData packagingDataList(List<Map<String,Object>> list,String[] column) {
		UVData uvData = new UVData();
		for (Map<String,Object> map:list) {		
			PeriodUV periodUv = new PeriodUV();
			periodUv.setFieldValue(map,column);
			uvData.getPeriodUvList().add(periodUv);						
		}
		return uvData;
	}
	
	/**
	 * 将series结果数据按照地域划分，每一个key为一个地域
	 * @param periodUVList
	 * @return
	 */
	public Map<String,List<PeriodUV>> getDateSerieswithKey(List<PeriodUV> periodUVList) {
		Map<String,List<PeriodUV>> map = new TreeMap<String,List<PeriodUV>> ();
		for (PeriodUV periodUV:periodUVList) {			
			if (!map.containsKey(periodUV.getAreaName())) {
				for(PeriodUV subPeriodUV:periodUVList) {		
					if (periodUV.getAreaName().equals(subPeriodUV.getAreaName())) {
						if (map.containsKey(periodUV.getAreaName())) {
							map.get(subPeriodUV.getAreaName()).add(subPeriodUV);
						}else {
							List<PeriodUV> sepList = new ArrayList<PeriodUV>();
							sepList.add(subPeriodUV);
							map.put(subPeriodUV.getAreaName(), sepList);
						}
					}
				}
			}		
		}		
		return map;
	}
	/**
	 * 生成k-v值
	 * @param chartDate
	 * @param key
	 * @param uvSeries
	 */
	public void generateKeyValue(McUvPeriodChartsData chartDate,String key,UVSeries uvSeries) {
		if(chartDate.getDaySeres().containsKey(key)) {
			chartDate.getDaySeres().get(key).add(uvSeries);
		}else {
			List<UVSeries> uvSeriesList = new ArrayList<UVSeries>();
			uvSeriesList.add(uvSeries);
			chartDate.getDaySeres().put(key, uvSeriesList);
		}
	}
	/**
	 * 播控uv超频图表数据生成
	 * @throws Exception 
	 * @throws SecurityException 
	 */
	public String getMcOverlockDateJson(List<Map<String,Object>> list) throws SecurityException, Exception{		
		String[] column = {"uv1","uv2","uv3","uv4","uv5","uv6","uv7","uv8","uv9","uv10"};		
		McUvPeriodChartsData chartDate = new McUvPeriodChartsData();
		//System.out.println(list);
		UVData uvData = packagingDataList(list, column);
	
		Map<String,List<PeriodUV>> map = new HashMap<String,List<PeriodUV>> ();
		map = getDateSerieswithKey(uvData.getPeriodUvList());	
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry)iter.next();
			String key = entry.getKey().toString();
			chartDate.getAreaList().add(key);
			List<PeriodUV> valueList = (List<PeriodUV>) entry.getValue();
			for (int i=0;i<column.length;i++) {
				UVSeries uvSeries = new UVSeries();
				String legentName = getLegendName(column[i],i+1);
				uvSeries.setName(legentName);
				List<Double> dataList = new ArrayList<Double>();	
				for (PeriodUV periodUV: valueList) {					
					dataList = getEachUVList(periodUV,column[i],dataList);			
				}
				uvSeries.setData(dataList);
				generateKeyValue(chartDate,key,uvSeries);
			}
			
			for(PeriodUV periodUV: valueList) {
				if (chartDate.getDayAxiswithKey().containsKey(key)) {
					chartDate.getDayAxiswithKey().get(key).add(periodUV.getMonDate());
					chartDate.getUvLimitwithKey().get(key).add(periodUV.getUvLimit());
				}else {
					List<String> axisList = new ArrayList<String>();
					List<String> limitList = new ArrayList<String>();
					axisList.add(periodUV.getMonDate());
					limitList.add(periodUV.getUvLimit());
					chartDate.getDayAxiswithKey().put(key, axisList);
					chartDate.getUvLimitwithKey().put(key, limitList);
				}
			}
			
		}
	
		GenerateJson gj = new GenerateJson();
		return gj.generate(chartDate);
	}

    /**
     * 获取每日UV数据的chart数据
     *
     * @param monId
     * @param monDateFrom
     * @param monDateTo
     * @param areaIds
     * @return
     */
    public String getDailyUVData(String monId, String monDateFrom, String monDateTo, String areaIds) {
        List<Map<String, Object>> data = mcReportDao.getDailyUVDataByMcIdAreaAndDateRange(monId, monDateFrom, monDateTo, areaIds);
        Map<Integer, UVChartsData> uvChartDataByAreas = getUVChartsDataWithAreas(data);
        GenerateJson generateJson = new GenerateJson();
        String retJson = generateJson.generate(uvChartDataByAreas);
        return retJson;
    }

    private Map<Integer, UVChartsData> getUVChartsDataWithAreas(List<Map<String, Object>> data) {
        Set<String> dateLists = getDataByColName(data, "mon_date");

//        groupByUvn 将输入数据按UVn分组，uv1:date1,date2,date3
        Map<Integer, McUVData> groupByUvn = convertListToMap(data, dateLists);
        Map<Integer, UVChartsData> uvChartDataByAreas = new HashMap<Integer, UVChartsData>();

        for (Entry<Integer, McUVData> dataEntry : groupByUvn.entrySet()) {
            Integer area = dataEntry.getKey();
            McUVData uvData = dataEntry.getValue();
            UVChartsData uvChartsData = new UVChartsData();
            uvChartsData.setDateXAxis(Arrays.asList(dateLists.toArray(new String[dateLists.size()])));
            for (int i = 1; i <= 10; i++) {
                String uvn = i + "+uv";
                List<Long> uvns = uvData.getUVnData(uvn);
                Series uvnSeries = new Series();
                uvnSeries.setName(uvn);
                uvnSeries.setData(uvns);
                uvChartsData.getUvnSeries().add(uvnSeries);
            }
            uvChartDataByAreas.put(area, uvChartsData);
        }
        return uvChartDataByAreas;
    }

    /**
     * 转换List<String,Map<String,Object>> 为 Map<String,List<Long>>
     * 其中返回map的key为uvn，list为在日期范围内的所有uvn数据
     *
     * @param data
     * @param dateLists
     * @return
     */
    Map<Integer, McUVData> convertListToMap(List<Map<String, Object>> data, Set<String> dateLists) {

//        对输入的data文件进行排序
        Collections.sort(data, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Date date1 = (Date) o1.get("mon_date");
                Date date2 = (Date) o2.get("mon_date");
                if (date1 != null && date2 != null) {
                    return date1.compareTo(date2);
                }
                return 0;
            }
        });

        Map<Integer, McUVData> areaUvDataMap = new HashMap<Integer, McUVData>();
        for (int i = 1; i <= 10; i++) {
            String tmpKey = i + "+uv";
            for (Map<String, Object> row : data) {
                Integer areaId = (Integer) row.get("area_id");
                McUVData areaUvData = areaUvDataMap.get(areaId);
                if (areaUvData == null) {
                    areaUvData = new McUVData();
                    areaUvDataMap.put(areaId, areaUvData);
                }
                Date monDate = (Date) row.get("mon_date");
                if (dateLists.contains(Utils.dateToString(monDate, "yyyy-MM-dd")) && i == (Integer)row.get("uvn")) {
                    areaUvData.addUVnData(tmpKey, (Long) row.get("uv_stat"));
                }
            }
        }

//       统计每个地域的N+UV
        for (McUVData mcUVData : areaUvDataMap.values()) {
            mcUVData.computeUVNPlus();
        }

        return areaUvDataMap;
    }

    public Set<String> getDataByColName(List<Map<String, Object>> datas, String colName) {
        Set<String> ret = new TreeSet<String>();
        for (Map<String, Object> rowData : datas) {
            ret.add(Utils.dateToString((Date) rowData.get(colName), "yyyy-MM-dd"));
        }
        return ret;
    }

    /**
     * 根据传入的参数，返回UV报告图表所需要的json字符串
     *
     * @param monId
     * @param monDateFrom
     * @param monDateTo
     * @param areaIds
     * @return
     */
    public String getPeriodUVData(String monId, String monDateFrom, String monDateTo, String areaIds) {
        List<Map<String, Object>> data = mcReportDao.getUVDataByMcIdAreaAndDateRange(monId, monDateFrom, monDateTo, areaIds);
        Map<Integer, UVChartsData> uvChartDataByAreas = getUVChartsDataWithAreas(data);

        GenerateJson generateJson = new GenerateJson();
        String retJson = generateJson.generate(uvChartDataByAreas);
        return retJson;
    }
	
	/**
	 * 查出全部稳定UV的和
	 * 
	 * @param monDate
	 * @param monType
	 * @return
	 */
	public String getStableUvInfo(String monDate, String monType) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (monDate == null || monDate.equals("")) {
			Map<String, Object> dateMap = mcReportDao.getMonDatePeriod(null, "stable");
			String maxDate  = dateMap.get("maxDate") == null ? "" : dateMap.get("maxDate").toString();
			if(!"".equals(maxDate)) {
				monDate = maxDate.substring(0, 4) + "-" + maxDate.substring(4, 6);
			} else {
				Calendar c = Calendar.getInstance();
				monDate = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH);
			}
		}
		if (monType == null || monType.equals(""))
			monType = "0";

		map.put("monDate", monDate);
		map.put("monType", monType);

		List list = mcReportDao.getAllStableUv(monType, monDate);
		int uvSum = 0;
		for (Object o : list) {
			Map row = (Map) o;
			for (int i = 1; i <= 21; i++) {
				uvSum += Integer.parseInt(row.get("uv" + i) + "");
			}
		}
		map.put("uvSum", uvSum + "");

		return new GenerateJson().generate(map);
	}

	public void setMcReportDao(McReportDao mcReportDao) {
		this.mcReportDao = mcReportDao;
	}

	public void setDimAreaDao(DimAreaDao dimAreaDao) {
		this.dimAreaDao = dimAreaDao;
	}

	public void setMcAreaDao(McAreaDao mcAreaDao) {
		this.mcAreaDao = mcAreaDao;
	}

	public void setMcAreaManager(McAreaManager mcAreaManager) {
		this.mcAreaManager = mcAreaManager;
	}

	public void setReportXmlDataHandler(ReportXmlDataHandler reportXmlDataHandler) {
		this.reportXmlDataHandler = reportXmlDataHandler;
	}

}
