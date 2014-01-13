package com.funshion.artemis.log.dao;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;

@Repository
public class OperationDao extends AbstractJdbcDao {
	public void save(String url) {
		Double d = Math.random() * 1000;
		this.getTemplate()
				.update("INSERT rpt_opt_base(obj_type, obj_id, obj_name, url, usr_id, time, opt_type) VALUES ('ad', 1, '京东商城"+d+"', '"+url+"', 11, '2013-12-10', 'auto')");
	}
}
