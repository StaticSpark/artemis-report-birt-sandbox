package com.funshion.artemis.custom.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.AdMat;

/**
 * 广告物料Dao
 * @author guanzx
 *
 */
@Repository
public class AdMatDao extends AbstractJdbcDao{
	/**
	 * 根据物料Id获取广告物料信息列表
	 * @param id
	 * @return
	 */
	public List getByMatId (Long id) {
		String sql = "select * from ad_mat where mat_id ="+id;;
		List list = this.findList(sql, null, AdMat.class);		
		return list;
	}
}
