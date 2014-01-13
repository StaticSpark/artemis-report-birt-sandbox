package com.funshion.artemis.custom.dao;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.DimAdpSize;

/**
 * 广告位尺寸Dao
 * @author guanzx
 *
 */
@Repository
public class DimAdpSizeDao extends AbstractJdbcDao{
	
	/**
	 * 根据广告id获取广告位尺寸
	 * @param size
	 * @return
	 */
	public DimAdpSize get(Long size) {
		String sql = "select name from dim_adp_size where id = "+size;			
		DimAdpSize dimAdpSize = (DimAdpSize) super.findObject(sql, null, DimAdpSize.class);
		return dimAdpSize;
	}
}
