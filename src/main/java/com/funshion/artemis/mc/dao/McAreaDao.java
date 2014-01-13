package com.funshion.artemis.mc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.AdpBase;
import com.funshion.artemis.dim.bean.DimArea;

/**
 * 播控地域 dao
 * 
 * @author shenwf Reviewed by
 */
@Repository
public class McAreaDao extends AbstractJdbcDao {
	/**
	 * 根据播控id 获取stg表中的 地域信息
	 * 
	 * @param mcId
	 * @return
	 */
	public List getStgAreaInfo(Long mcId) {
		String sql = " select stg.area_id,stg.area_type FROM stg_mc_imp_d stg LEFT JOIN mc_detail md ON  md.mc_id = stg.mon_id AND md.area_id = stg.area_id "
				+ "WHERE mon_id = "+ mcId +" and path > 0 and (area_type is NULL OR area_type = 'area' OR area_type = 'group') and mon_id = " + mcId 
				+ " GROUP BY area_id order by md.sn ASC, area_type";
		List list = this.getTemplate().queryForList(sql);
		return list;
	}
	
	/**
	 * @author zhaojj
	 */
	public DimArea getDimAreaById(Long id) {
		String sql = "select id, name from dim_area where id = " + id;
		return (DimArea) this.findObject(sql, null, DimArea.class);
	}
	
	/**
	 * 获取该地域组下面的地域信息
	 * @param monId
	 * @param monDate
	 * @param areaId
	 * @param groupId
	 * @return
	 */
	public List getGroupAreaList(Long monId, String monDate,String areaId) {
		String sql = "SELECT " +
							" d.id, " +
							" d. NAME,d.area_level as areaLevel,d.parent_id as parentId " +
						" FROM " +
							"dim_area d " +
						" RIGHT JOIN ( " +
						" 	SELECT DISTINCT " +
						" area_id " +
								" FROM " +
								" stg_mc_imp_de s " +
								" WHERE " +
								" s.mon_id =  " +monId +
								" AND s.mon_date = '"+monDate+"' " +
								" AND s.area_type =  " +areaId +
								" ) s ON d.id = s.area_id ORDER BY  CONVERT (d.NAME USING gbk) ASC ";
		List list = this.findList(sql, null, DimArea.class);		
		return list;
	}
}
