package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.DimMedCategory;
/**
 * 题材Dao
 * @author guanzx
 *
 */
@Repository
public class DimMedCategoryDao extends AbstractJdbcDao{
	/**
	 * 获取题材信息
	 * @return
	 */
	public List getAll() {
		String sql = "select * from dim_med_cat";
		List list = this.findList(sql, null,DimMedCategory.class);
		return list;
	}
}
