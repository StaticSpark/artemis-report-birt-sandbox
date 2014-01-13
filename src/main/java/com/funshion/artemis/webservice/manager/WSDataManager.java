package com.funshion.artemis.webservice.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.custom.service.AdReportSqlManager;
import com.funshion.artemis.exportUtil.bean.ReportSqlBean;
import com.funshion.artemis.webservice.dao.WSDataDao;

@Service
public class WSDataManager {
	
	@Autowired
	private WSDataDao wsDataDao;

	/**
	 * 根据 sql 名字 和 参数获取数据
	 * @param name rpt_sql.xml 中 sql 中name
	 * @param map 参数
	 * @return 数据
	 */
	public List<Map<String, Object>> getData(String name, Map<String, String> map) {
		ReportSqlBean impSqlBean = ReportXmlReader.getReportSqlByName(name);
		String impSql = AdReportSqlManager.sqlParam(impSqlBean.getSql(), map);
		List<Map<String, Object>> impDayList = wsDataDao.getData(impSql);
		return impDayList;
	}
	
	/**
	 * 根据 sql 名字 和 参数获取数据
	 * @param name
	 * @param map
	 * @param oldSql
	 * @param newSql
	 * @return
	 */
	public List<Map<String, Object>> getData(String name, Map<String, String> map, String oldSql, String newSql) {
		ReportSqlBean impSqlBean = ReportXmlReader.getReportSqlByName(name);
		String impSql = AdReportSqlManager.sqlParam(impSqlBean.getSql(), map);
		impSql = impSql.replaceAll(oldSql, newSql);
		List<Map<String, Object>> impDayList = wsDataDao.getData(impSql);
		return impDayList;
	}
}
