package com.funshion.artemis.webservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;

/**
 * 图表数据处理层
 * 
 * @author shenwf Reviewed by
 */
@Repository
public class WSDataDao extends AbstractJdbcDao {
	/**
	 * 根据sql 获取数据
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getData(String sql) {
		List<Map<String, Object>> list = this.getTemplate().queryForList(sql);
		return list;
	}
}
