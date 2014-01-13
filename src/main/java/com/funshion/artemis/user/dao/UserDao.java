package com.funshion.artemis.user.dao;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.user.bean.User;
@Repository
public class UserDao extends AbstractJdbcDao {
	public List getUserList(String start, String length) {
		String sql = "select * from acct_user limit " + start + "," + length;
		List list = this.findList(sql, null, User.class);
		return list;
	}
	
	public Integer getUserLength() {
		String sql = "select * from acct_user";
		List list = this.findList(sql, null, User.class);
		return list.size();
	}
	
	public User get(Long saleId) {
		String sql = "select * from acct_user where id = ?";
		User user = (User) super.findObject(sql, saleId, User.class);
		return user;
	}

//	@Override
//	protected User findObject(String sql, List<Object> params) {
//		return (User) super.findObject(sql, params, User.class);

//		User result = null;
//		ParameterizedBeanPropertyRowMapper<User> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(User.class);
//		if (params == null) {
//			result = template.queryForObject(sql, rowMapper);
//		} else {
//			result = template.queryForObject(sql, params.toArray(), rowMapper);
//		}
//		return result;
//	}
}
