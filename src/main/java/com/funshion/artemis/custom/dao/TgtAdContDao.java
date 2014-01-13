package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.TgtAdCont;

/**
 * 广告媒体内容定向条件Dao
 * @author guanzx
 *
 */
@Repository
public class TgtAdContDao extends AbstractJdbcDao{
	
	/**
	 * 根据题材id获取内容定向信息
	 * @param id
	 * @return
	 */
	public List getTgtContByCatId(Long id) {
		String sql = "select * from tgt_ad_cont where cat_id = "+id;
		List list = this.findList(sql, null, TgtAdCont.class);
		return list;
	}
	
	/**
	 * 根据产地id获取内容定向信息
	 * @param id
	 * @return
	 */
	public List getTgtContByCountryId(Long id) {
		String sql = "select * from tgt_ad_cont where country_id = "+id;
		List list = this.findList(sql, null, TgtAdCont.class);
		return list;
	}
}
