package com.funshion.artemis.dim.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.dim.bean.DimArea;

/**
 * 地域dao
 * @author shenwf
 * Reviewed by
 */
@Repository
public class DimAreaDao extends AbstractJdbcDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 根据id获取地域
	 * @param id
	 * @return
	 */
	public DimArea getAreaById(Long id) {
		String sql = "select id, name,parent_id as parentId, area_level as areaLevel  from dim_area where id = " + id;
		return (DimArea) this.findObject(sql, null, DimArea.class);
	}
	
	/**
	 * 根据地域层次获取地域列表
	 * @param level 地域层次
	 */
	public List getByLevel(int level) {
		String sql = "select * from dim_area where area_level="+level+" and belong_to is null order by id";
		List list = this.findList(sql, null, DimArea.class);
		return list;
	}
	
	/**
	 * 根据父节点获取 所有子地域
	 * @param areaId
	 * @return
	 */
	public List getByParentId(Long areaId) {
		String sql = "select id, name, parent_id as parentId, area_level as areaLevel from dim_area where parent_id = " + areaId;
		List list = this.findList(sql, null, DimArea.class);
		return list;
	}
	
	/**
	 * 查找其他国家
	 * @return
	 */
	public List getOtherCountrys() {		
		String sql = "select * from dim_area where area_level = 2 and parent_id = 2 and belong_to is null";
		List list = this.findList(sql, null, DimArea.class);
		return list;
	} 
}
