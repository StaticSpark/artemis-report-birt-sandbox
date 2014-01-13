package com.funshion.artemis.mc.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;
import com.funshion.artemis.mc.dao.McAreaDao;

/**
 * @since 20130828
 * @author guanzx
 *
 */
public class McAreaManagerTest {

	private DimAreaDao dimAreaDao;
	private McAreaDao mcAreaDao;
	private McAreaManager mcAreaManager;
	
	@Before
	public void setUp() {
		
		mcAreaManager = new McAreaManager();
		dimAreaDao = mock(DimAreaDao.class);	
		mcAreaDao = mock(McAreaDao.class);
		mcAreaManager.setDimAreaDao(dimAreaDao);
		mcAreaManager.setMcAreaDao(mcAreaDao);
	}
	
	@Test
	/*
	 * 测试id为11的地域北京
	 */
	public void testAreaName() {
		mockDimArea();
		assertEquals("北京", mcAreaManager.getAreaName("11"));
	}
	
	@Test
	/*
	 * 测试id的长度大于6的特殊地域
	 */
	public void testSpecialAreaName() {
		mockSpecialDimArea();
		assertEquals("特殊地域其他", mcAreaManager.getAreaName("1234567"));
	}
	
	
	public void mockDimArea() {	
		DimArea dimArea = new DimArea();
		dimArea.setId(11l);
		dimArea.setName("北京");
		dimArea.setParentId(1l);
		dimArea.setAreaLevel(2);
		Long id = 11l;
		when(dimAreaDao.getAreaById(id)).thenReturn(dimArea);		
	}
	
	public void mockSpecialDimArea() {
		DimArea dimArea = new DimArea();
		dimArea.setId(1234567L);
		dimArea.setName("特殊地域");
		Long id = 2L;
		when(dimAreaDao.getAreaById(id)).thenReturn(dimArea);	
	}
	
}
