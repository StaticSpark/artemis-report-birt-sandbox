package com.funshion.artemis.exportUtil.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.exportUtil.bean.ExportColumnBean;
import com.funshion.artemis.exportUtil.service.McExportManager;

/**
 * 
 * @author guanzx
 *
 */
@Repository
public class McExportDao extends AbstractJdbcDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public  List<ExportColumnBean> getReportInfoByParams(String monId,String areaId,String monDate,String isVisualArea,String path) {
		
		String conditions = "";
		if (!monId.isEmpty()) {
			conditions += " mon_id = " + monId;
		}	
		if (!areaId.isEmpty() && areaId != "") {
			conditions += " and area_id=" + areaId;
			if(!isVisualArea.isEmpty() && isVisualArea == "1") {
			 	 conditions += " and area_type is not NULL and area_type != 'area' and area_type != 'group' ";
			  } else {
			     conditions += " and (area_type is NULL OR area_type = 'area' or area_type = 'group') ";
			  }
		}
		if (!monDate.isEmpty()) {
			conditions += " and mon_date = '" + monDate + "' ";
		}
		if (!path.equals("*")) {
			conditions += " and path = " + path;
		}			
		String sql = McExportManager.getRptSql(conditions);
		List<ExportColumnBean> list = this.findList(sql, null,ExportColumnBean.class);
		return list;
	}
	
}
