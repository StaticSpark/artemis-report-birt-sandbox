package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.ReportSource;

/**
 * 报告来源dao层
 * @author shenwf
 * Reviewed by
 */
@Repository
public class ReportSourceDao extends AbstractJdbcDao {
	
	public List<ReportSource> getAll() {
		String sql = "select * from dim_rpt_source";
		List list = this.findList(sql, null,ReportSource.class);
		return list;
	}
}
