package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.ClientBase;

/**
 * 客户基本信息Dao
 * @author guanzx
 *
 */
@Repository
public class ClientBaseDao extends AbstractJdbcDao{
	
	/**
	 * 获取客户基本信息
	 * @return
	 */
	public List getAll() {
		String sql = "select * from acc_base  order by id desc";
		List list = this.findList(sql, null,ClientBase.class);
		return list;
	}
}
