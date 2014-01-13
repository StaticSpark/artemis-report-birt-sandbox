package com.funshion.artemis.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.user.bean.User;
import com.funshion.artemis.user.dao.UserDao;

@Service
public class UserManager {
	@Autowired
	private UserDao userDao;

	public Integer getLength() {
		return userDao.getUserLength();
	}
	
	public String changeToDataTable(String start, String length) {
		List list = userDao.getUserList(start, length);
		StringBuffer sb = new StringBuffer();
		for(Object o : list) {
			User user = (User) o;
			sb.append("[");
			sb.append("\"" + user.getId() + "\",");
			sb.append("\"" + user.getName() + "\"");
			sb.append("],");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
