package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.report.bean.ColumnInfo;


@Repository
public class RptColumnDao extends AbstractJdbcDao {
	/**
	 * 获取该报告的列信息
	 * @param rptId
	 * @return
	 */
	public List getColumnList(Long rptId) {
		String sql = "SELECT " +
						" rc.column_name as name,rc.column_display_name as displayName,rc.count_type as countType,rc.is_group_column as isGroupColumn,rc.script " +
						" FROM  " +
						" rpt_base base, rpt_base_column rbc,rpt_column rc " +
						" WHERE  " +
						" base.id = rbc.rpt_id AND rbc.rpt_column_id = rc.id and base.id = " + rptId;
		List list = this.findList(sql, null,ColumnInfo.class);
		return list;
	}
}
