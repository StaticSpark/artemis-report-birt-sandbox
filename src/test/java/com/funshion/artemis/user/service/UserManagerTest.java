package com.funshion.artemis.user.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.funshion.artemis.user.bean.User;
import com.funshion.artemis.user.dao.UserDao;

/**
 * @see 
 * @author guanzx
 *
 */
public class UserManagerTest {

	UserManager userManager;
	private UserDao userDao;
	@Before
	public void setUp(){
		userManager = new UserManager();
		userDao = mock(UserDao.class);
		userManager.setUserDao(userDao);
	}
	
	@Test
	public void testChangeToDataTable() {
		String start = "";
		String length = "";
		User user = new User();
		user.setName("测试");
		user.setId(1L);
		List list = new ArrayList();
		list.add(user);
		when(userDao.getUserList(start, length)).thenReturn(list);
		
		String expected = "[\"1\",\"测试\"]";
		String actual = userManager.changeToDataTable(start, length);
		Assert.assertEquals(expected, actual);
	}
}
