package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.ReportType;

/**
 * 报告类型Dao
 * @author guanzx
 *
 */
@Repository
public class ReportTypeDao extends AbstractJdbcDao{

	public List getAll() {
		String sql = "select * from dim_rpt_type";
		List list = this.findList(sql, null,ReportType.class);
		return list;
	}
	
	/**
	 * 根据报表ID获取报告类型信息
	 * @param rpttype
	 * @return
	 */
	public List getReportName(int rpttype){
		String sql = "select * from dim_rpt_type where id ="+rpttype;
		List list = this.findList(sql, null,ReportType.class);
		return list;
	}

}

