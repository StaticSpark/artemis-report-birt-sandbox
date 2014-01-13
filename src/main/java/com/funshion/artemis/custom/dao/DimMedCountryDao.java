package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.DimMedCountry;
/**
 * 产地信息Dao
 * @author guanzx
 *
 */
@Repository
public class DimMedCountryDao extends AbstractJdbcDao{
	
	/**
	 * 获取产地信息
	 * @return
	 */
	public List getAll() {
		String sql = "select * from dim_med_country";
		List list = this.findList(sql, null,DimMedCountry.class);
		return list;
	}
}
