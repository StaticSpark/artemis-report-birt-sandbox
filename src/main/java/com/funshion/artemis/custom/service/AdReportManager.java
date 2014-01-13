package com.funshion.artemis.custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.custom.bean.AdConditionBean;
import com.funshion.artemis.custom.bean.ClientBase;
import com.funshion.artemis.custom.bean.DimMedCategory;
import com.funshion.artemis.custom.bean.DimMedCountry;
import com.funshion.artemis.custom.bean.OrderBase;
import com.funshion.artemis.custom.bean.OrderConditionBean;
import com.funshion.artemis.custom.dao.AdAdpDao;
import com.funshion.artemis.custom.dao.AdBaseDao;
import com.funshion.artemis.custom.dao.AdConditionDao;
import com.funshion.artemis.custom.dao.AdMatDao;
import com.funshion.artemis.custom.dao.AdpBaseDao;
import com.funshion.artemis.custom.dao.ClientBaseDao;
import com.funshion.artemis.custom.dao.DimMedCategoryDao;
import com.funshion.artemis.custom.dao.DimMedCountryDao;
import com.funshion.artemis.custom.dao.MatBaseDao;
import com.funshion.artemis.custom.dao.OrderBaseDao;
import com.funshion.artemis.custom.dao.ReportTypeDao;
import com.funshion.artemis.dim.service.DimAreaManager;
import com.funshion.artemis.report.bean.ColumnMappingXmlBean;
import com.funshion.artemis.report.bean.ReportTypeXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;
import com.funshion.artemis.user.bean.User;
import com.funshion.artemis.user.dao.UserDao;

/**
 * 报告处理类
 * 
 * @author guanzx
 * 
 */
@Service
public class AdReportManager {
	@Autowired
	private ReportTypeDao reportTypeDao;
	@Autowired
	private AdConditionDao adConditionDao;
	@Autowired
	private OrderBaseDao orderBaseDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ClientBaseDao clientBaseDao;
	@Autowired
	private DimMedCategoryDao dimMedCategoryDao;
	@Autowired
	private DimMedCountryDao dimMedCountryDao;
	@Autowired
	private AdMatDao adMatDao;
	@Autowired
	private AdBaseDao adBaseDao;
	@Autowired
	private AdAdpDao adAdpDao;
	@Autowired
	private DimAreaManager dimAreaManager;
	@Autowired
	private MatBaseDao matBaseDao;
	@Autowired
	private AdpBaseDao adpBaseDao;
	@Autowired
	private ReportXmlDataHandler reportXmlDataHandler;

	/**
	 * 根据报告类型得到相应的报告列表（json 格式）
	 * @return
	 */
	public String getReportListJson() {
		String condition = "{";

		List<ReportXmlBean> reportList = reportXmlDataHandler.getReportList();
		StringBuffer sb = new StringBuffer();
		sb.append("\"resource\" :[");
		for (ReportXmlBean report : reportList) {
			if(report.getIsShowInCustomPage().equals("1")) {
				sb.append("{");
				sb.append("\"id\":" + report.getId() + ",");
				sb.append("\"value\":\"" + report.getRptName() + "\",");
				sb.append("\"name\":\"" + report.getRptDisplayName() + "\",");
				sb.append("\"type\":\"" + report.getRptTypeName() + "\"");
				sb.append("},");
			}
		}

		if (reportList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		sb.append("}");
		return condition + sb.toString();
	}

	/**
	 * 将参数转换成json字符串
	 * @param startDate
	 * @param endDate
	 * @param conditionNames
	 * @param conditonTypeText
	 * @param reportId
	 * @param conditonType
	 * @param ids
	 * @param freq
	 * @param areaIds
	 * @param reportPath
	 * @param rptList
	 * @param reportName
	 * @return
	 */
	public String convertParamtoJson(String startDate, String endDate, String conditionNames, String conditonTypeText, Long reportId, String idGroup,
			String conditonType, String ids, String freq, String areaIds, String reportPath, List<ReportXmlBean> rptList, String reportName,String firstColumn,String secondColumn) {
		GenerateJson gj = new GenerateJson();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"startDate\":\"" + startDate + "\"");
		sb.append(",\"endDate\":\"" + endDate + "\"");
		sb.append(",\"conditionNames\":\"" + conditionNames + "\"");
		sb.append(",\"conditonTypeText\":\"" + conditonTypeText + "\"");
		sb.append(",\"reportId\":" + reportId);
		sb.append(",\"conditonType\":\"" + conditonType + "\"");
		sb.append(",\"ids\":\"" + ids + "\"");
		sb.append(",\"idGroup\":\"" + idGroup+ "\"");
		sb.append(",\"freq\":\"" + freq + "\"");
		sb.append(",\"areaIds\":\"" + areaIds + "\"");
		sb.append(",\"reportPath\":\"" + reportPath + "\"");
		sb.append(",\"rptList\":" + gj.generate(rptList));
		sb.append(",\"reportName\":\"" + reportName + "\"");
		sb.append(",\"firstColumn\":\"" + firstColumn + "\"");
		sb.append(",\"secondColumn\":\"" + secondColumn + "\"");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取报告类型(json)
	 * 
	 * @return
	 */
	public String getReportType() {
		List<ReportTypeXmlBean> list = reportXmlDataHandler.getReportTypes();
		String json = "[";
		for (ReportTypeXmlBean reportType : list) {
			json += "{";
			json += "\"id\":" + reportType.getId() + ",";
			json += "\"name\":\"" + reportType.getName() + "\"";
			json += "},";
		}
		if (list.size() > 0) {
			json = json.substring(0, json.length() - 1);
		}
		json += "]";
		return json;
	}

	/**
	 * 得到广告查询条件列表
	 * 
	 * @return
	 */
	public List<AdConditionBean> getAdConditionList() {
		List<Map> list = adConditionDao.getAllAdConditionBeanList("0", "100000", "");
		List<AdConditionBean> conditionList = new ArrayList<AdConditionBean>();

		for (Map map : list) {
			AdConditionBean con = new AdConditionBean();
			String adName = map.get("adName").toString();
			Integer isDelete = (Integer) map.get("isDelete");
			if (isDelete > 0) {
				adName = "<s>" + adName + "</s>";
			}
			con.setAdCtgory(map.get("name") == null ? "" : map.get("name").toString());
			con.setAdId(map.get("adId").toString());
			con.setAdName(adName);
			con.setAdpName(map.get("adpName") == null ? "" : map.get("adpName").toString());
			con.setOrderName(map.get("orderName") == null ? "" : map.get("orderName").toString());
			con.setPriority(map.get("priority") == null ? "" : map.get("priority").toString());
			con.setStatusName(map.get("statusName") == null ? "" : map.get("statusName").toString());
			con.setWeight(map.get("weight") == null ? "" : map.get("weight").toString());
			conditionList.add(con);
		}
		
		return conditionList;
	}
	
	/**
	 * 得到广告精确查询列表
	 * @param param
	 * @return
	 */
	public List<AdConditionBean> getPreciseAdConditionList(String param) {
		List<Map> list = adConditionDao.getPreciseAdConditionBeanList(param);
		List<AdConditionBean> conditionList = getConditonList(list);
		return conditionList;
	}
	
	/**
	 * 获得全选数据列表
	 * @param list
	 * @return
	 */
	public List<AdConditionBean> getAdAllCheckList(String param) {
		List<Map> list = adConditionDao.getAdAllCheckList(param);
		List<AdConditionBean> conditionList = getConditonList(list);
		return conditionList;
	}
	
	public List<AdConditionBean> getConditonList(List<Map> list) {
		List<AdConditionBean> conditionList = new ArrayList<AdConditionBean>();
		for (Map map : list) {
			AdConditionBean con = new AdConditionBean();
			String adName = map.get("adName").toString();
			Integer isDelete = (Integer) map.get("isDelete");
			if (isDelete > 0) {
				adName = "<s>" + adName + "</s>";
			}
			String startDate = map.get("minDate") == null ? "":map.get("minDate").toString().substring(0, map.get("minDate").toString().length()-2);
			String endDate = map.get("maxDate") == null ? "":map.get("maxDate").toString().substring(0, map.get("maxDate").toString().length()-2);
			String periodDate = startDate + "~" + endDate;
			
			con.setAdId(map.get("adId").toString());
			con.setAdName(adName);
			con.setOrderName(map.get("orderName") == null ? "" : map.get("orderName").toString());
			con.setPriority(map.get("priority") == null ? "" : map.get("priority").toString());
			con.setStatusName(map.get("statusName") == null ? "" : map.get("statusName").toString());
			con.setWeight(map.get("weight") == null ? "" : map.get("weight").toString());
			con.setPubDate(periodDate);
			conditionList.add(con);
		}		
		return conditionList;
	}

	/**
	 * 得到广告查询条件列表
	 * 
	 * @return
	 */
	public String getAdCondition(String path ,Map<String, String[]> paramMap) {
		String[] iDisplayStart = paramMap.get("iDisplayStart");
		String[] iDisplayLength = paramMap.get("iDisplayLength");
		String[] sEcho = paramMap.get("sEcho");
		String[] searchs = paramMap.get("sSearch");
		String start = iDisplayStart[0];
		String length = iDisplayLength[0];
		String search = searchs[0];
		List<Map> list = adConditionDao.getAllAdConditionBeanList(start, length, search);
		List<AdConditionBean> conditionList = new ArrayList<AdConditionBean>();
		StringBuffer sb = new StringBuffer();
		for (Map map : list) {
			sb.append("[");
			String adName = map.get("adName").toString();
			Integer isDelete = (Integer) map.get("isDelete");
			if (isDelete > 0) {
				adName = "<s>" + adName + "</s>";
			}
			Object adId =  map.get("adId");
			String statusName = map.get("statusName") == null ? "" : map.get("statusName").toString();
			String orderName = map.get("orderName") == null ? "" : map.get("orderName").toString();
			String startDate = map.get("minDate") == null ? "":map.get("minDate").toString().substring(0, map.get("minDate").toString().length()-2);
			String endDate = map.get("maxDate") == null ? "":map.get("maxDate").toString().substring(0, map.get("maxDate").toString().length()-2);
			String periodDate = startDate + "~" + endDate;
			sb.append("\"<input type='checkbox' mark='ad-c' id='ad-c-" + adId + "' name='con-check'></input>\",");
			sb.append("\"<span style='float: left'><font size=2>" + adId + "</font></span>\",");
			sb.append("\"<span style='float: left'><font size=2><a style='color:blue' href='http://atlas-beta.funshion.com/atlas/ad/ad-base!detail.action?id="+adId+"' target='_blank'>" + adName.replaceAll("\"", "") + "</a></font></span>\",");
			sb.append("\"<span style='float: left'><font size=2>" + orderName.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left;width:37px'><font size=2>" + statusName.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left;width:260px'><font size=2>" + periodDate + "</font></span>\",");
			sb.append("\"<span style='float: left'><img src='"+path+"/images/report/list_view.gif' alt='' /></span>\"");
			sb.append("],");
		}
		if(sb.length() > 1) {
		  sb.deleteCharAt(sb.length() - 1);
		}
		Long count = adConditionDao.getAdCount(search);
		String message = "{\"sEcho\":" + sEcho[0] + ",\"iTotalRecords\":\" " + count + "\",\"iTotalDisplayRecords\":\"" + count + "\",\"aaData\":["
				+ sb.toString() + "]}";
		return message;
	}

	/**
	 * 获取订单查询条件列表
	 * 
	 * @return
	 */
	public List<OrderConditionBean> getOrderConditionList() {
		// List<OrderBase> list = orderBaseDao.getAll();
		// Collections.sort(list, new OrderBase());
		return orderBaseDao.getOrderConditionList();
	}

	/**
	 * 将数据库查出的结果转化成 页面上显示的参数列表
	 * 
	 * @param list
	 * @return
	 */
	public List<OrderConditionBean> convertOrderConditon(List<OrderBase> list) {
		List<OrderConditionBean> conList = new ArrayList<OrderConditionBean>();

		for (OrderBase orderBase : list) {
			User sale = userDao.get(orderBase.getSalesId().longValue());
			User medUser = orderBase.getAeId() == null ? null : userDao.get(orderBase.getAeId().longValue());
			OrderConditionBean orderCondition = new OrderConditionBean();
			orderCondition.setId(orderBase.getId());
			orderCondition.setName(orderBase.getName());

			orderCondition.setSaleName(sale.getName());
			orderCondition.setMedUserName(medUser == null ? "" : medUser.getName());
			orderCondition.setPrice(orderBase.getAmount() + "");
			conList.add(orderCondition);
		}
		return conList;
	}

	/**
	 * 获取客户列表
	 * 
	 * @return
	 */
	public List<ClientBase> getClientBaseList() {
		List<ClientBase> list = clientBaseDao.getAll();
		if (list == null) {
			return new ArrayList<ClientBase>();
		}
		// Collections.sort(list, new ClientBase());
		return list;
	}

	/**
	 * 获取销售列表
	 * 
	 * @return
	 */
	public List<User> getSaleList() {
		return orderBaseDao.getSaleConditionList();
	}

	public List<DimMedCategory> getDimMedCategoryList() {
		List<DimMedCategory> list = dimMedCategoryDao.getAll();
		if (list == null) {
			return new ArrayList<DimMedCategory>();
		}
		// Collections.sort(list, new DimMedCategory());//TODO 如何排序
		return list;
	}

	/**
	 * 获取产地列表
	 * 
	 * @return
	 */
	public List<DimMedCountry> getDimMedCountryList() {
		List<DimMedCountry> list = dimMedCountryDao.getAll();
		if (list == null) {
			return new ArrayList<DimMedCountry>();
		}
		return list;
	}

	/**
	 * 根据id 获取报告path
	 * 
	 * @param id
	 * @return
	 */
	public String getRptPathById(Long id) {
		return reportXmlDataHandler.getReportById(id).getPath();
	}

	/**
	 * 根据类型标识获取类型名字
	 * 
	 * @param type
	 * @return
	 */
	public String getConditionTypeText(String type) {
		if (type.equals("ad")) {
			return "广告报告";
		} else if (type.equals("mat")) {
			return "物料报告";
		} else if (type.equals("order")) {
			return "订单报告";
		} else if (type.equals("adp")) {
			return "广告位报告";
		}
		return "";
	}

	/**
	 * 获取日期报告id
	 * @param type
	 * @return
	 */
	public Long getDayReportId() {
		return reportXmlDataHandler.getByRptName("day_report").getId();
	}

	/**
	 * 根据id 和条件类型获取名字
	 * 
	 * @param type
	 * @param conId
	 * @return
	 */
	public String getCondtionNames(String type, String conId) {
		Long id = Long.parseLong(conId);
		if (type.equals("ad")) {
			return adBaseDao.get(id).getName();
		} else if (type.equals("mat")) {
			return matBaseDao.get(id).getName();
		} else if (type.equals("order")) {
			return orderBaseDao.get(id).getName();
		} else if (type.equals("adp")) {
			return adpBaseDao.get(id).getName();
		}
		return "";
	}
	

	/**
	 * 根据查询条件显示报表列表
	 * 
	 * @param conditonType
	 * @return
	 */
	public List<ReportXmlBean> getRptList(String conditonType) {
		List<ReportXmlBean> conList = new ArrayList<ReportXmlBean>();
		List<ReportXmlBean> adList = reportXmlDataHandler.getReportsByTypeName("广告分布");
		if ("cat".equals(conditonType)) {// 媒体报告
			List<ReportXmlBean> catList = reportXmlDataHandler.getReportsByTypeName("题材报告");
			conList.addAll(catList);	
		}else if ("country".equals(conditonType)) {// 媒体报告
			List<ReportXmlBean> catList = reportXmlDataHandler.getReportsByTypeName("产地报告");
			conList.addAll(catList);	
		} else {
			List<ReportXmlBean> baseList = reportXmlDataHandler.getReportsByTypeName("基础分布");
			List<ReportXmlBean> twoDimensionList = reportXmlDataHandler.getReportsByTypeName("二维分布");
			conList.addAll(baseList);
			conList.addAll(twoDimensionList);
			conList.addAll(adList);
		}
		
		List<ReportXmlBean> resultList = new ArrayList<ReportXmlBean>();
		
		for(ReportXmlBean report : conList) {
			ReportXmlBean copy = report.clone();
			copy.setSql("");
			resultList.add(copy);
		}
		return resultList;
	}

	/**
	 * 根据id获取报告名
	 * @param id
	 * @return
	 */
	public String getRptDisplayNameById(Long id) {
		ReportXmlBean report = reportXmlDataHandler.getReportById(id);
		return report.getRptName();
	}

	/**
	 * 获取导出文件名
	 * 
	 * @param rptId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String getExportName(Long rptId, String startDate, String endDate) {
		ReportXmlBean report = reportXmlDataHandler.getReportById(rptId);
		String reportName = report.getRptDisplayName();
		return reportName + "-" + startDate + "-" + endDate;
	}
	
	/**
	 * 根据id 获取所选则的报告对应的字段名称（用于二维报表）column_mapping.xml
	 */
	public String getColumnName(Long id) {
		ColumnMappingXmlBean columnMapping = ReportXmlReader.getColumnMappingById(id);
		return columnMapping.getColumnDisplayName();
	}
	
	/*
	 * 得到二维组合报表的组合报表名称
	 */
	public String getReportName(String firstReport,String secondReport) {
		int fIndex = firstReport.indexOf("_");			
		int sIndex = secondReport.indexOf("_");
		firstReport = firstReport.substring(0,fIndex);
		secondReport = secondReport.substring(0,sIndex);
		String reportName = firstReport+"_"+secondReport+"_report";
		return reportName;
	}
}