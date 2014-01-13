package com.funshion.artemis.mc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.custom.service.AdReportSqlManager;

@Service
public class McSqlManager {
	public static String getSqlByCondition(String condition) {
		String sql = ReportXmlReader.getReportSqlByName("mc_period_uv").getSql();
		Map map = new HashMap();
		map.put("condition", condition);
		sql = AdReportSqlManager.sqlParam(sql, map);
		return sql;
	}
}
