package com.funshion.artemis.custom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.custom.bean.AdAdp;
import com.funshion.artemis.custom.bean.AdBase;
import com.funshion.artemis.custom.bean.AdMat;
import com.funshion.artemis.custom.bean.OrderBase;
import com.funshion.artemis.custom.bean.TgtAdCont;
import com.funshion.artemis.custom.dao.AdAdpDao;
import com.funshion.artemis.custom.dao.AdBaseDao;
import com.funshion.artemis.custom.dao.AdMatDao;
import com.funshion.artemis.custom.dao.AdpBaseDao;
import com.funshion.artemis.custom.dao.ClientBaseDao;
import com.funshion.artemis.custom.dao.DimMedCategoryDao;
import com.funshion.artemis.custom.dao.DimMedCountryDao;
import com.funshion.artemis.custom.dao.MatBaseDao;
import com.funshion.artemis.custom.dao.OrderBaseDao;
import com.funshion.artemis.custom.dao.ReportSourceDao;
import com.funshion.artemis.custom.dao.TgtAdContDao;
import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.service.DimAreaManager;
import com.funshion.artemis.report.bean.ColumnMappingXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;
import com.funshion.artemis.user.dao.UserDao;

/**
 * 报告sql语句处理类
 * 
 * @author shenwf Reviewed by
 */
@Service
public class AdReportSqlManager {
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
	private TgtAdContDao tgtAdContDao;
	@Autowired
	private ReportSourceDao reportSourceDao;
	@Autowired
	private DimAreaManager dimAreaManager;
	@Autowired
	private MatBaseDao matBaseDao;
	@Autowired
	private AdpBaseDao adpBaseDao;
	@Autowired
	private ReportXmlDataHandler reportXmlDataHandler;
	
	//定义常量
	public static final String TIME_DISTRIBUTION_ID ="13";
	public static final String ADP_DISTRIBUTION_ID = "4";
	public static final String AD_DISTRIBUTION_ID = "1";
	public static final String ORDER_DISTRIBUTION_ID = "2";
	public static final String MAT_DISTRIBUTION_ID = "3";
	public static final String CITY_DISTRIBUTION_ID = "16";
	public static final String PROVINCE_DISTRIBUTION_ID = "15";
	public static final String AREAR_DISTRIBUTION_ID = "49";
	public static final String COUNTRY_DISTRIBUTION_ID = "14";
	public static final String CAT_DISTRIBUTION_ID = "52";
	public static final String CD_DISTRIBUTION_ID = "53";
	public static final String MONTH_DISTRIBUTION_ID = "21";
	public static final String DAY_DISTRIBUTION_ID = "10";

	/**
	 * 根据参数条件生成sql
	 * 
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param conditonType
	 * @param chartType
	 * @param freq
	 * @param areaIds
	 * @return
	 */
	public String getReportSql(String sql, String ids, String startTime, String endTime, Long reportId, String conditonType, String chartType, String freq, String areaIds) {
		Map<String, String> map = getParamFromRequest(ids, startTime, endTime, reportId, conditonType, chartType, freq, areaIds);//生成参数
		if(sql == null || sql.length() == 0) {
			sql = reportXmlDataHandler.getReportById(reportId).getSql();
		}
		sql = sqlParam(sql, map);//用参数替换掉 sql语句中的占位符
		sql = " select * from ( " + sql + " ) s";
		return sql;
	}
	
	/**
	 * 该方法返回查询所需的sql
	 * @param sql 定义查询报表的sql,此处传递来的参数为空值
	 * @param ids 查询的相应报告的Id,查询的广告id：1052,4970,4968,1053,1050,1049,1048,1047,1046,1045,1044,1043,1042,1041,1040,1052
	 * @param startTime 报告的开始时间
	 * @param endTime  报告的结束时间
	 * @param reportId 分布报表的id,二维分布的为51/54
	 * @param idGroup  二维分布的查询组合，时段-广告位（13,4）
	 * @param conditonType 报告类型，广告报告：ad;广告位报告adp
	 * @param areaIds 地域id
	 * @param reportName 组合报告对应的名称 时段-广告位：hour_adp_report,目前该参数还未使用到
	 * @return
	 */
	public String getTwoDimensionalReportSql(String sql, String ids, String startTime, String endTime, Long reportId, String idGroup,String conditonType,String areaIds,String reportName) {
		Map<String,String> map = getParamAccordingtoRequest(ids, startTime, endTime, reportId, idGroup,conditonType, areaIds,reportName);
		if(sql == null || sql.length() == 0) {
			sql = reportXmlDataHandler.getReportById(reportId).getSql();			
		}
		//若组合分布中含有国家分布	
		if (idGroup.contains(COUNTRY_DISTRIBUTION_ID)) {
			if (sql.contains("order by")){
				//将orderby 去除 放在最后
				int orderIndex = sql.indexOf("order by");
				String orderBy = sql.substring(orderIndex, sql.length());
				sql = sql.substring(0, orderIndex);
				sql = "select * from ("+sql + " " +ReportXmlReader.getColumnMappingById(14L).getUnion()+") ss "+orderBy;
			}else{
				sql = sql + " " +ReportXmlReader.getColumnMappingById(14L).getUnion();
			}
		}
		//若组合分布中含有地域分布
		if (idGroup.contains(AREAR_DISTRIBUTION_ID)) {
			//替换sql中的area_id 为 parentId in{and parentId IN (#provinceIds#) }
			if (sql.contains("area_id in (#areaIds#)")) {
				sql = sql.replace("area_id in (#areaIds#)"," parentId IN (#provinceIds#)");
			}
			if(sql.contains("order by")) {
				int orderIndex = sql.indexOf("order by");
				String orderBy = sql.substring(orderIndex, sql.length());
				sql = sql.substring(0, orderIndex);
				sql = "select * from ("+sql + " " +ReportXmlReader.getColumnMappingById(49L).getUnion()+") ss "+orderBy;
			}else {
				sql = sql + " " + ReportXmlReader.getColumnMappingById(49L).getUnion();
				// 若组合为日期与地域的组合，且日期在前时
				if (isDateAndAreaAndDateisFirst(idGroup)) {
					sql = "select * from ("+ sql +") ss order by first_columns";
				}
				
			}			
		}
		//若组合分布中含有题材分布
		if(idGroup.contains(CAT_DISTRIBUTION_ID) || idGroup.contains(CD_DISTRIBUTION_ID)) {
			if (sql.contains("area_id in (#areaIds#)")) {
				sql = sql.replace("area_id in (#areaIds#)"," and a.cat_id in (#catId#)");
			}
		}
		
		sql = sqlParam(sql, map);//用参数替换掉 sql语句中的占位符
		sql = " select * from ( " + sql + " ) s";	
		return sql;
	}
	
	public static Boolean isDateAndAreaAndDateisFirst(String idGroup) {
		boolean flag = false;
		String[] columns = idGroup.split(",");
		if (columns[0].equals(TIME_DISTRIBUTION_ID) || columns[0].equals(DAY_DISTRIBUTION_ID) || columns[0].equals(MONTH_DISTRIBUTION_ID)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据传递的参数得到查询条件
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param idGroup
	 * @param conditonType
	 * @param areaIds
	 * @param reportName
	 * @return
	 */
	public Map<String, String> getParamAccordingtoRequest(String ids,String startTime,String endTime,Long reportId,String idGroup,String conditonType,String areaIds,String reportName) {	
		
		//报表分布的id
		String[] subReports = idGroup.split(",");
	
		//获取字段映射字段
		ColumnMappingXmlBean fColumnMappingXmlBean = ReportXmlReader.getColumnMappingById(new Long(subReports[0]));
		ColumnMappingXmlBean sColumnMappingXmlBean = ReportXmlReader.getColumnMappingById(new Long(subReports[1]));
		String firstColumn = fColumnMappingXmlBean.getSelectName();
		String secondColumn = sColumnMappingXmlBean.getSelectName();
		String groupBy = fColumnMappingXmlBean.getGroupBy()+","+sColumnMappingXmlBean.getGroupBy();
		String commonCondition = "a.log_date >= "+"'"+startTime+"'"+" and a.log_date <= "+"'"+endTime +"'";	
		String dataTable = getDataTable(fColumnMappingXmlBean.getDataTable(),sColumnMappingXmlBean.getDataTable());	
		
		//如果是时段分布，则对数据表再次进行筛选,具有时段分布的二维分布将统一采用stg_ad_effe
		if (subReports[0].equals(TIME_DISTRIBUTION_ID) || subReports[1].equals(TIME_DISTRIBUTION_ID)) {		
			if (firstColumn.contains("log_date")) {
				firstColumn = firstColumn.replaceAll("log_date", "log_time");
			}
			if (secondColumn.contains("log_date")) {
				secondColumn = secondColumn.replaceAll("log_date", "log_time");
			}
			if (groupBy.contains("log_date")) {
				groupBy = groupBy.replaceAll("log_date", "log_time");
			}
		    if (commonCondition.contains("log_date")) {
		    	commonCondition = commonCondition.replaceAll("log_date", "log_time");
		    }
			//去除数据表中的stg_ad_effe_d
		    dataTable = removeSuplusTable(dataTable);		   
		}
		
		String adpCondition = "";
		String adpIdCondition = "";
		//如果二维报表中有广告位分布时，需要添加下列查询条件
		if (subReports[0].equals(ADP_DISTRIBUTION_ID) || subReports[1].equals(ADP_DISTRIBUTION_ID)) {
			adpCondition = "and a.adp_id = p.id";
			adpIdCondition = "p.id as adpId,";
		}
		//如果有广告分布时，需要添加下列条件
		String adCondition = "";
		String adIdCondition = "";
		if (subReports[0].equals(AD_DISTRIBUTION_ID) || subReports[1].equals(AD_DISTRIBUTION_ID)) {
			adCondition = "and a.ad_id = b.id";
			adIdCondition = "b.id as adId,";
		}
		//如果是含有订单分布时
		String orderCondition = "";
		String orderIdCondition = "";
		if (subReports[0].equals(ORDER_DISTRIBUTION_ID) || subReports[1].equals(ORDER_DISTRIBUTION_ID)) {
			orderCondition = " and a.ad_id = b.id AND o.id = b.ord_id and b.ad_process = 4 ";
			orderIdCondition = "o.id as ordId,";
		}
		//如果含有物料分布时
		String matCondition = "";
		String matIdCondition = "";
		if (subReports[0].equals(MAT_DISTRIBUTION_ID) || subReports[1].equals(MAT_DISTRIBUTION_ID)) {
			matCondition = " and b.id= a.ad_id and b.ad_process = 4 and a.ad_id = am.ad_id AND m.id = am.mat_id";
			matIdCondition = "m.id as matId,";
		}
		//如果含有城市分布
		String cityCondition = "";
		if(idGroup.contains(CITY_DISTRIBUTION_ID)) {
			cityCondition = "and a.area_id = d.id and (d.area_level = 3 or d.id in (11,31,12,50))";
		}
		
		//如果含有省份分布
		String provinceCondition = "";
		if (idGroup.contains(PROVINCE_DISTRIBUTION_ID)) {
			provinceCondition = "and a.area_id = s.cityId";
		}
		
		//如果含有地域分布
		String areaRptFColumn = "";
		String areaRptSColumn = "";
		String areaGroupByF = "";
		String areaGroupByS = "";
		String areaCondition = "";
		String areaInnerTable = "";
		if(idGroup.contains(AREAR_DISTRIBUTION_ID)) {
			areaCondition = " and a.area_id = s.id";
			if (subReports[0].equals(AREAR_DISTRIBUTION_ID)) {
				areaRptFColumn = "d.name";
				areaRptSColumn = secondColumn;
				areaGroupByF = "a.area_id";
				areaGroupByS = sColumnMappingXmlBean.getGroupBy();
			}else if (subReports[1].equals(AREAR_DISTRIBUTION_ID)) {
				areaRptFColumn = firstColumn;
				areaRptSColumn = "d.name";
				areaGroupByF = fColumnMappingXmlBean.getGroupBy();
				areaGroupByS = "a.area_id";
			}
			areaInnerTable = removeNeedlessTable(dataTable);
		}
		
		//如果含有国家分布
		String countryCondition = "";
		String innerFirstColumn = "";
		String innerSecondColumn = "";
		if (idGroup.contains(COUNTRY_DISTRIBUTION_ID)) {
			countryCondition = "and a.area_id = d.id and d.parent_id = 2 ";
			if (subReports[0].equals(COUNTRY_DISTRIBUTION_ID)){
				innerFirstColumn = "'中国'";
				innerSecondColumn = secondColumn;
			}else if (subReports[1].equals(COUNTRY_DISTRIBUTION_ID)) {
				innerFirstColumn = firstColumn;
				innerSecondColumn = "'中国'";
			}
		}
		
		//如果含有题材分布
		String catCondition = "";
		if(idGroup.contains(CAT_DISTRIBUTION_ID)) {
			catCondition = " and dmc.id = a.cat_id ";
			//去除多余的表stg_ad_effe_d a
			dataTable = removeSuplusTable(dataTable);
		}
		//如果含有产地分布
		String cdCondition = "";
		if (idGroup.contains(CD_DISTRIBUTION_ID)) {
			cdCondition = " and dmc.id = a.country_id ";
			dataTable = removeSuplusTable(dataTable);
		}
		
		String adIds = "";
		//如果是广告报告
		if (conditonType.equals("ad")) {
			adIds = ids;
			//如果是广告报告和日期报告的组合，则启用不同的数据表
			if (idGroup.equals("10,1") || idGroup.equals("1,10")) {
				dataTable = replaceDataTable(dataTable);
			}
		}
		
		String adpId = "";
		//如果是广告位报告
		if (conditonType.equals("adp")) {
			adpId = ids;
		}
						
		Map<String, String> map = new HashMap<String, String>();
		map.put("first_column", firstColumn);
		map.put("second_column", secondColumn);
		map.put("innerf_column", innerFirstColumn);
		map.put("inners_column", innerSecondColumn);
		map.put("areaRptF_column", areaRptFColumn);
		map.put("areaRptS_column", areaRptSColumn);
		map.put("condition_group",groupBy);		
		map.put("areaGroupByF", areaGroupByF);
		map.put("areaGroupByS", areaGroupByS);
		map.put("common_condition", commonCondition);
		map.put("data_table", dataTable);
		map.put("areaInner_table", areaInnerTable);
		map.put("adId", adIds);
		map.put("adpId", adpId);
		map.put("adp_condition", adpCondition);
		map.put("ad_condition", adCondition);
		map.put("order_condition", orderCondition);
		map.put("mat_condition", matCondition);
		map.put("city_condition", cityCondition);
		map.put("country_condition", countryCondition);
		map.put("province_condition",provinceCondition);
		map.put("area_condition", areaCondition);
		map.put("cat_condition", catCondition);
		map.put("cd_condition", cdCondition);
		map.put("ad_id_condition", adIdCondition);
		map.put("adp_id_condition", adpIdCondition);
		map.put("order_id_condition", orderIdCondition);
		map.put("mat_id_condition", matIdCondition);
		map.put("areaIds", dimAreaManager.changeProviceIdToCityId(areaIds));
		separateAreaIds(areaIds, map);
	    return map;
	}
	
	public String removeSuplusTable(String dataTable) {
		StringBuilder stringBuilder = new StringBuilder();
		String[] columns = dataTable.split(",");
		for (int i = 0; i < columns.length; i++) {
			if (!columns[i].equals("stg_ad_effe_d a")) {
				stringBuilder.append(columns[i]).append(",");
			}
		}		 
		return stringBuilder.toString().substring(0, stringBuilder.toString().length()-1);		
	}
	
	public String replaceDataTable(String dataTable) {
		dataTable = dataTable.replace("stg_ad_effe_d a", "stg_ad_effe_d_quota a");
		return dataTable;
	}
	
	public String removeNeedlessTable(String dataTable) {
		int firstBracket = dataTable.indexOf("(");
		int secondBracket = dataTable.indexOf(")");
		String sub = dataTable.substring(0, firstBracket).trim() + dataTable.substring(secondBracket+4, dataTable.length());
		return sub;
	}
	
	/*
	 * 组合两个分布的sql,若有重复的则排重
	 */
	public String getDataTable(String fDataTable,String sDataTable) {
		Set set = new HashSet();	
		//该表 当有省份分布的时候需要用到
		String proviceTable = "( " +
						"SELECT p.id as provinceId, p.name as provinceName,d.id as cityId,d.name as cityName " + 
						"from dim_area d, dim_area p " + 
						"where d.parent_id = p.id and d.area_level = 3 GROUP BY p.id,d.id "+ 
						"UNION " + 
						"SELECT id as provinceId, d.name as provinceName, id as cityId, '' as cityName "+ 
						"FROM dim_area d "+ 
						"where d.parent_id = 1 and d.area_level = 2 AND NOT EXISTS ( SELECT * FROM dim_area f where d.id = f.parent_id) "+ 
                        ") s";
		String areaTable = "( " +
			             " SELECT d.id as id,d.NAME AS NAME,p.id AS parentId,p.NAME AS parentName " + 
			             "FROM dim_area d LEFT JOIN dim_area p ON d.parent_id = p.id "+ 
		                 ") s";
		
		if (fDataTable.contains(",")) {
			String fPerTable[] = fDataTable.trim().split(",");
			for (int i = 0;i<fPerTable.length;i++) {
				if (fPerTable[i].contains("province_table")) {
					set.add(proviceTable);
				}else if(fPerTable[i].contains("area_table")) {
					set.add(areaTable);
				}else {
					set.add(fPerTable[i].trim());
				}				
			}				
		}else {
			set.add(fDataTable.trim());
		}
			
		if (sDataTable.contains(",")) {
			String sPerTable[] = sDataTable.trim().split(",");
			for (int i = 0;i<sPerTable.length;i++) {
				if (sPerTable[i].contains("province_table")) {
					set.add(proviceTable);
				}else if(sPerTable[i].contains("area_table")) {
					set.add(areaTable);
				}else {
					set.add(sPerTable[i].trim());
				}	
			}
		}else {
			set.add(sDataTable.trim());
		}
		
		Iterator<String> iter = set.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		while(iter.hasNext()) {
			stringBuilder.append(iter.next()).append(",");
		}
		return  stringBuilder.toString().substring(0, stringBuilder.toString().length()-1);
	}
			
	/**
	 * 根据参数条件生成sql(for mc)
	 * 
	 * @param monId
	 * @param monDate
	 * @param areaId
	 * @param path
	 * @return
	 */
	public String getMcReportSql(String rptName, String monId, String monDate, String startDate, String endDate, String areaId, String path, String hour) {
		ReportXmlBean report = reportXmlDataHandler.getByRptName(rptName);
		String sql = report.getSql();
		Map<String, String> map = new HashMap<String, String>();
		map.put("monId", monId);
		map.put("monDate", "'" + monDate + "'");
		map.put("startDate", "'" + startDate + "'");
		map.put("endDate", "'" + endDate + "'");
		map.put("areaId", areaId);
		map.put("path", path);
		sql = sqlParam(sql, map);
		try {
			if(Integer.parseInt(hour) > -1) {
				sql = mcSourceHourReportSqlHandle(sql, hour);
			}
		} catch(Exception e) {
			//不做异常处理，只是过滤掉非法传入的 小时
		}
		sql = " select * from ( " + sql + " ) s";
		return sql;
	}

	/**
	 * 根据参数条件生成sql(for mc)
	 * 
	 * @param monId
	 * @param monDate
	 * @param areaId
	 * @param path
	 * @return
	 */
	public String getMcReportSql(HttpServletRequest request, String rptName, String monId, String monDate, String startDate, String endDate, String areaId, String path, String hour) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		AdReportSqlManager reportManager = (AdReportSqlManager) ctx.getBean("adReportSqlManager");
		return reportManager.getMcReportSql(rptName, monId, monDate, startDate, endDate, areaId, path, hour);
	}

	/**
	 * 播控 数据来源 小时报告 sql 语句处理
	 *  将天级报告sql语句替换成小时sql语句
	 * @param sql
	 * @return
	 */
	public String mcSourceHourReportSqlHandle(String sql, String hour) {
		String reg = "stg_mc_\\w+_d";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(sql);
		String tableName = "";
		if (matcher.find()) {
			tableName = matcher.group();
		} 
		sql = sql.replaceAll(tableName, tableName + "e");
		int whereIndex = sql.indexOf("WHERE");
		if(whereIndex != sql.toUpperCase().indexOf("WHERE")) {
			whereIndex = sql.indexOf("where");
		}
		
		sql = sql.substring(0, whereIndex + 6) + " mon_hour = " + hour + " and " + sql.substring(whereIndex + 6);
		
		return sql;
	}

	/**
	 * 根据参数条件生成sql
	 * 
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param conditonType
	 * @param chartType
	 * @param freq
	 * @param areaIds
	 * @return
	 */
	public String getReportSql(HttpServletRequest request, String sql, String ids, String startTime, String endTime, String reportId, String conditonType, String chartType, String freq, String areaIds) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		AdReportSqlManager reportManager = (AdReportSqlManager) ctx.getBean("adReportSqlManager");
		return reportManager.getReportSql(sql, ids, startTime, endTime, Long.parseLong(reportId), conditonType, chartType, freq, areaIds);
	}
	
	/*
	 * 该方法来自于report类中的sql，将其放入spring容器中
	 */
	public String getTwoDimensionalReportSql(HttpServletRequest request, String sql, String ids, String startTime, String endTime, String reportId, String idGroup,String conditonType, String areaIds,String reportName) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		AdReportSqlManager reportManager = (AdReportSqlManager) ctx.getBean("adReportSqlManager");
		return reportManager.getTwoDimensionalReportSql(sql, ids, startTime, endTime, Long.parseLong(reportId), idGroup,conditonType,areaIds,reportName);
	}

	/**
	 * 根据页面传过来的参数 生成 sql语句需要的参数
	 * 
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param conditonType
	 * @param chartType
	 * @param freq
	 * @param areaIds
	 * @return
	 */
	public Map<String, String> getParamFromRequest(String ids, String startTime, String endTime, Long reportId, String conditonType, String chartType, String freq, String areaIds) {
		ReportXmlBean report = reportXmlDataHandler.getReportById(reportId);
		String adIds = "";
		String adpIds = "";
		String catIds = "";
		String countryIds = "";
		String rptTypeName = report.getRptTypeName();
		String paraGroup = "";
		if ("adp".equals(conditonType)) {
			adpIds = ids;
			adIds = "";
		} else if (rptTypeName.equals("媒体基础分布")) {// 选了题材条件和产地报告
			if ("cat".equals(conditonType)) {
				catIds = ids;
				if (ids != null && ids.length() > 0) {
					paraGroup = "T";
				}
			} else if ("country".equals(conditonType)) {
				countryIds = ids;
				if (ids != null && ids.length() > 0) {
					paraGroup = "C";
				}
			}

			if (report.getSql().contains("area_id")) {// 地域分布
				paraGroup = "D" + paraGroup;
			} else if (ids == null || ids.length() == 0) {
				paraGroup = "ALL";
			}
		} else {
			adIds = getAdIds(ids, conditonType);
			if (report.getRptDisplayName().equals("广告位分布")) {
				if("adp".equals(conditonType)) {
					adpIds = ids;
					adIds = "";
				} 
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("ids", ids);
		map.put("startTime", "'" + startTime + "'");
		map.put("endTime", "'" + endTime + "'");
		map.put("adId", adIds);
		map.put("para_group", "'" + paraGroup + "'");
		map.put("catId", catIds);
		map.put("countryId", countryIds);
		map.put("adpId", adpIds);
		map.put("freq", freq);
		map.put("areaIds", dimAreaManager.changeProviceIdToCityId(areaIds));
		separateAreaIds(areaIds, map);

		return map;
	}
	
	/**
	 * @author zhaojj
	 * 将地域以省、城市分开 
	 */
	public void separateAreaIds(String areaIds, Map<String, String> map) {
		if(areaIds != null && areaIds.length() >0) {
			String [] areaIdArr = areaIds.split(",");
			String provinceIds = "";
			String cityIds = "";
			String [] tempArr = {"11","12","31","50","71","81","82","90","98","99"};//省级地域但在stg_ad_effe_d表中有记录的地域id
			
			for(String s : areaIdArr) {
				boolean isExit = isInArr(s,tempArr);
				if(isExit == true) {
					cityIds += s +",";
				} else {
					DimArea d = dimAreaManager.getDimAreaById(Long.parseLong(s));
					if(d.getAreaLevel() == 2 && d.getParentId() == 1l) {
						provinceIds += s +",";
					} else {
						cityIds += s +",";
					}
				}
			}
			if(provinceIds != "") {
				provinceIds = provinceIds.substring(0,provinceIds.length()-1);
			}else {
				provinceIds = "-99999";
			}
			if(cityIds != "") {
				cityIds = cityIds.substring(0,cityIds.length()-1);
			}else {
				cityIds = "-99999";
			}
			
			map.put("provinceIds", provinceIds);
			map.put("cityIds", cityIds);
		}
		
	}
	//判断字符串是否在数组内
	public boolean isInArr(String s, String [] arr) {
		for(String temp : arr) {
			if(s.equals(temp)) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 根据 查询条件获取相关 连的广告id 以,分割开来
	 * 
	 * @param ids
	 * @param conditonType
	 * @return
	 */
	public String getAdIds(String ids, String conditonType) {
		if (ids == null || ids.length() == 0) {
			return "";
		}

		if ("ad".contains(conditonType)) {// 如果是广告id 直接返回
			return ids;
		}
		if("cat".equals(conditonType) || "country".equals(conditonType))
    		return ids;		//题材报告 和产地报告 直接返回题材 产地 id
		String[] idArray = ids.split(",");
		String adIds = "";

		Set<Long> set = new TreeSet<Long>();

		for (String str : idArray) {
			try {
				Long id = Long.parseLong(str);
				if ("mat".equals(conditonType)) {
					List<AdMat> list = adMatDao.getByMatId(id);
					if(list.size() <= 0) {
						set.add(-1111l);
					}
					for (AdMat adMat : list) {
						set.add(adMat.getAdId().longValue());
					}
				} else if ("order".equals(conditonType)) {
					List<AdBase> list = adBaseDao.getByOrderId(id);
					if(list.size() <= 0) {
						set.add(-1111l);
					}
					for (AdBase adBase : list) {
						set.add(adBase.getId());
					}
				} else if ("adp".equals(conditonType)) {
					List<AdAdp> list = adAdpDao.getAdAdpsByAdpId(id);
					if(list.size() <= 0) {
						set.add(-1111l);
					}
					for (AdAdp adAdp : list) {
						set.add(adAdp.getAdId().longValue());
					}
				} else if ("acc".equals(conditonType)) {
					List<OrderBase> list = orderBaseDao.getByAccountId(id);
					if(list.size() <= 0) {
						set.add(-1111l);
					}
					for (OrderBase orderBase : list) {
						List<AdBase> adList = adBaseDao.getByOrderId(orderBase.getId());
						for (AdBase adBase : adList) {
							set.add(adBase.getId());
						}
					}
				} else if ("sale".equals(conditonType)) {
					List<OrderBase> list = orderBaseDao.getBySalesId(id);
					if(list.size() <= 0) {
						set.add(-1111l);
					}
					for (OrderBase orderBase : list) {
						List<AdBase> adList = adBaseDao.getByOrderId(orderBase.getId());
						for (AdBase adBase : adList) {
							set.add(adBase.getId());
						}
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (set.size() > 0) {
			adIds = set.toString().substring(1, set.toString().length() - 1);
		}

		return adIds;
	}

	/**
	 * 根据广告id列表获取广告位id列表
	 * 
	 * @param adIds
	 * @return
	 */
	public String getAdpIds(String adIds) {
		if (adIds == null || adIds.length() == 0) {
			return "";
		}

		Set<Long> set = new TreeSet<Long>();
		String[] idArray = adIds.split(",");
		for (String adId : idArray) {
			adId = adId.trim();
			Long id = Long.parseLong(adId);
			try {
				List<AdAdp> list = adAdpDao.getAdAdpsByAdId(id);
				for (AdAdp adAdp : list) {
					set.add(adAdp.getAdpId().longValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String adpIds = "";
		if (set.size() > 0) {
			adpIds = set.toString().substring(1, set.toString().length() - 1);
		}
		return adpIds;
	}

	/**
	 * 替换掉sql语句中参数标识
	 * 
	 * @param sql
	 * @return
	 */
	public static String sqlParam(String sql, Map<String, String> map) {
		List<String> sqlParamList = getParamList(sql);
		if (sqlParamList.size() == 0) {
			return sql;
		}

		for (String sqlParam : sqlParamList) {// 循环 检查 参数列表是否有 参数，如果有
												// 就替换掉sql语句的参数标识
			
			boolean isExis = false;
			for (Map.Entry<String, String> requestParamMap : map.entrySet()) {
				String key = requestParamMap.getKey();
				String value = requestParamMap.getValue();
				if (sqlParam.contains(key)) {
					if (value != null && !value.equals("") && !value.equals("'null'") && !value.equals("null")) {
						String sqlParamVal = sqlParam.replace("#" + key + "#", value);
						sqlParamVal = sqlParamVal.trim();
						sqlParamVal = sqlParamVal.substring(1, sqlParamVal.length() - 1);
						sql = sql.replace(sqlParam, sqlParamVal);
						isExis = true;
					}
				}
			}
			if (!isExis) {
				sql = sql.replace(sqlParam, "");
			}
		}

		if (getTwoStringBetweenStr(sql, "where").equals("") || getTwoStringBetweenStr(sql, "where").equals("group") || getTwoStringBetweenStr(sql, "where").equals(")")
				|| getTwoStringBetweenStr(sql, "where").equals("and")) {
			sql = sql.replace("where", "where 1 = 1 ");
		}

		return sql;
	}
	
	
	/**
	 * 获取sql中参数占位标记列表 {}
	 * 
	 * @param sql
	 * @return
	 */
	public static List<String> getParamList(String sql) {
		Pattern p = Pattern.compile("\\{");
		Matcher matcher = p.matcher(sql);
		List<Integer> leftList = new ArrayList<Integer>();
		while (matcher.find()) {
			int left = matcher.start();
			leftList.add(left);
		}
		// todo 名字歧义
		p = Pattern.compile("\\}");
		matcher = p.matcher(sql);
		List<Integer> rightList = new ArrayList<Integer>();
		while (matcher.find()) {
			int right = matcher.start();
			rightList.add(right);
		}
		List<String> paramList = new ArrayList<String>();
		for (Integer left : leftList) {
			for (Integer right : rightList) {
				if (right > left) {
					paramList.add(sql.substring(left, right + 1));
					break;
				}
			}
		}
		return paramList;
	}

	/**
	 * 得到 该字符的下一个字符
	 * 
	 * @param sql
	 * @param str
	 * @return
	 */
	public static String getTwoStringBetweenStr(String sql, String str) {
		if (sql.trim().endsWith(str)) {
			return "";
		}
		sql = sql.toLowerCase();
		str = str.toLowerCase();
		int index = sql.indexOf(str);
		if (index > -1) {
			sql = sql.substring(index + str.length(), sql.length()).trim();
			String result = "";
			int count = 0;
			while (sql.charAt(count) != ' ') {
				result = result + sql.charAt(count);
				count++;
			}

			return result.trim();
		}
		return "";
	}
}
