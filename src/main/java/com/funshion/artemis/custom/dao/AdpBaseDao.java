package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.AdpBase;

/**
 * 广告位基础信息Dao
 * @author guanzx
 *
 */
@Repository
public class AdpBaseDao extends AbstractJdbcDao {
	
	/**
	 * 根据广告位状态获取广告位信息
	 * @param readyStatus
	 * @return
	 */
	public List getAdpList(String readyStatus) {		
		String sql = "select * from adp_base where status ="+readyStatus + "  order by id desc ";
		List list = this.findList(sql, null,AdpBase.class);		
		return list;		
	}
	
	public AdpBase get(Long id) {
		String sql = " select * from adp_base where id = " + id;
		return (AdpBase) super.findObject(sql, null, AdpBase.class);
	}
	
	/**
	 * 
	 * @param param
	 * @param readyStatus
	 * @return
	 */
	public List getPreciseAdpList(String param,String readyStatus) {				
		String searchCondition = "";
		String array[] = null;
		String sql = "";
		String sqlPlus = "";
		List list = null;
		//如果参数中包含逗号，则参数为组合参数
		if (param.contains(",")) {
			array = param.split(",");
		}else {
			searchCondition = " and (id like '"+param+"' or name like '"+param+"')";
			sql = "select * from adp_base where status ="+ readyStatus + searchCondition + " order by id desc ";
		}		
			
		if (array != null && array.length > 0) {
			for (int i=0;i<array.length;i++) {
				searchCondition = " and (id like '"+array[i]+"' or name like '"+array[i]+"')";
				if (sqlPlus.isEmpty()) {
					sqlPlus = "select * from adp_base where status ="+readyStatus +searchCondition;
				}else {
					sqlPlus += " union select * from adp_base where status ="+readyStatus +searchCondition;
				}				
			}
			sqlPlus = sqlPlus + "order by id desc ";
			list = this.findList(sqlPlus, null,AdpBase.class);	
		}else {
			list = this.findList(sql, null,AdpBase.class);	
		}			
		return list;		
	}
}
