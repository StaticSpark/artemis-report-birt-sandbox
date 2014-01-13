package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.AdAdp;

/**
 * 广告广告位Dao
 * @author guanzx
 *
 */
@Repository
public class AdAdpDao extends AbstractJdbcDao{
	
	/**
	 * 根据广告位Id获取广告广告位信息
	 * @param id
	 * @return
	 */
	public List getAdAdpsByAdpId(Long id) {
		String sql = "select * from ad_adp where adp_id = "+id;
		List list = this.findList(sql, null, AdAdp.class);
		return list;
	}
	
	/**
	 * 根据广告Id获取广告广告位信息
	 * @param id
	 * @return
	 */
	public List getAdAdpsByAdId(Long id) {
		String sql = "select * from ad_adp where ad_id = "+id;
		List list = this.findList(sql, null, AdAdp.class);
		return list;
	}
}
