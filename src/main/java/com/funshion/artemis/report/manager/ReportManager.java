package com.funshion.artemis.report.manager;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.custom.dao.ReportSourceDao;
import com.funshion.artemis.custom.dao.ReportTypeDao;
import com.funshion.artemis.custom.dao.RptColumnDao;
import com.funshion.artemis.report.bean.ColumnInfo;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;

/**
 * 报告管理类
 * 
 * @author shenwf Reviewed by
 */
@Service
public class ReportManager {
	
	@Autowired
	private ReportTypeDao reportTypeDao;
	@Autowired
	private ReportSourceDao reportSourceDao;
	@Autowired
	private RptColumnDao rptColumnDao;
	@Autowired
	private ReportXmlDataHandler reportXmlDataHandler;

	/**
	 * 获取报告信息
	 * 
	 * @param rptId
	 * @return
	 */
	public String getAdReportJson(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"typeList\":" + new GenerateJson().generate(reportTypeDao.getAll()));
		sb.append(",\"sourceList\":" + new GenerateJson().generate(reportSourceDao.getAll()));
		if (id != null && id.length() > 0) {
			Long rptId = Long.parseLong(id);
			sb.append(",\"columnInfo\":" + new GenerateJson().generate(rptColumnDao.getColumnList(rptId)));
			ReportXmlBean report = reportXmlDataHandler.getReportById(rptId);
			sb.append(",\"report\":" + new GenerateJson().generate(report));
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取该报告的列信息
	 * 
	 * @param rptId
	 * @return
	 */
	public String getReportColumnInfo(String id) {
		if (id == null || id.equals("")) {
			return "";
		}
		Long rptId = Long.parseLong(id);
		return new GenerateJson().generate(rptColumnDao.getColumnList(rptId));
	}
	
	/**
	 * 验证sql语句是否合法
	 * 
	 * @param sql
	 * @param columnJson
	 * @return
	 */
	public boolean validateSqlAndColumn(String sql, String columnJson) {
		try {
			if (sql == null || sql.length() == 0 || columnJson == null || columnJson.length() == 0) {
				return false;
			}

			if (!Tools.checkJson(columnJson)) {
				return false;
			}

			Integer selectIndex = sql.toLowerCase().indexOf("select");
			Integer fromIndex = sql.toLowerCase().indexOf(" from ");
			String columns = sql.substring(selectIndex + 6, fromIndex).trim();
			if (columns.equals("*")) {
				return true;
			}

			JSONArray jsonArrayMedCatInput = JSONArray.fromObject(columnJson);
			List<ColumnInfo> columnList = (List<ColumnInfo>) JSONArray.toCollection(jsonArrayMedCatInput, ColumnInfo.class);
			String[] columnArray = columns.split(",");
			for (ColumnInfo column : columnList) {
				boolean isExits = false;
				for (String str : columnArray) {
					if (str.toLowerCase().contains("as")) {
						str = str.substring(str.toLowerCase().indexOf("as ") + 2, str.length());
					}
					str = str.trim();
					if (str.endsWith(column.getName())) {
						isExits = true;
					}
				}
				if (!isExits) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	//----下面的setter()方法利用mockito进行单元测试时会用到----------
	public void setReportTypeDao(ReportTypeDao reportTypeDao) {
		this.reportTypeDao = reportTypeDao;
	}
	
	public void setReportSourceDao(ReportSourceDao reportSourceDao) {
		this.reportSourceDao = reportSourceDao;
	}

	public void setRptColumnDao(RptColumnDao rptColumnDao) {
		this.rptColumnDao = rptColumnDao;
	}

	public void setReportXmlDataHandler(ReportXmlDataHandler reportXmlDataHandler) {
		this.reportXmlDataHandler = reportXmlDataHandler;
	}
    
}
