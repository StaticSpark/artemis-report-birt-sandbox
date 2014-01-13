package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.AdBase;
/**
 * 广告基本信息Dao
 * @author guanzx
 *
 */
@Repository
public class AdBaseDao extends AbstractJdbcDao {
	
	/**
	 * 根据订单Id获取广告基本信息列表
	 * @param id
	 * @return
	 */
	public List getByOrderId(Long id) {
		String sql = "select * from ad_base where ord_id="+id;
		List list = this.findList(sql, null, AdBase.class);		
		return list;
	}
	
	/**
	 * 根据广告id 获取广告对象
	 * @param id
	 * @return
	 */
	public AdBase get(Long id) {
		String sql = " select * from ad_base where id = " + id;
		return (AdBase) super.findObject(sql, null, AdBase.class);
	}
	
	/**
	 * 根据广告id 获取广告对象
	 * @param id
	 * @return
	 */
	public List getAll() {
		String sql = "select * from ad_base limit 200";
		List list = this.findList(sql, null, AdBase.class);				
		return list;
	}
}
