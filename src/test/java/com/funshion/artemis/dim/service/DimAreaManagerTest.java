package com.funshion.artemis.dim.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;

/**
 * @since 20130829
 * @author guanzx
 *
 */
public class DimAreaManagerTest {
	DimArea dimArea;
	List<DimArea> list;
	List<DimArea> countryList;
	List<DimArea> provenceList;
	List<DimArea> cityList;
	List<DimArea> other;
	DimAreaManager dimAreaManager;
	private DimAreaDao dimAreaDao;
	
	@Before
	public void setUp() {
		dimAreaManager = new DimAreaManager();	
		dimArea = new DimArea();
			
		dimAreaDao = mock(DimAreaDao.class);
		dimAreaManager.setDimAreaDao(dimAreaDao);
	}

	@Test
	public void testAreaJson() {
		mockCountry();
		mockProvence();
		mockCity();
		mockOther();
		String expected = "{\"country\":[{\"name\":\"其他国家\",\"id\":\"999\",\"province\":[]}]}";
		String actual = dimAreaManager.getAllArea();
		Assert.assertEquals(expected, actual);
	
	}
		
	public void mockCountry() {
		int COUNTRY = 1;
		countryList = initList(1,1L,"中国",0L);
		when(dimAreaDao.getByLevel(COUNTRY)).thenReturn(countryList);
	}
	
	public void mockProvence() {
		int PROVINCE = 2;
		provenceList = initList(2,37L,"山东",1L);
		when(dimAreaDao.getByLevel(PROVINCE)).thenReturn(provenceList);
	}
	
	public void mockCity() {
		int CITY = 3;
		cityList = initList(3,370009L,"聊城",37L);
		when(dimAreaDao.getByLevel(CITY)).thenReturn(cityList);
	}
	
	public void mockOther() {
		other = initList(2,999L,"其他国家",2L);
		when(dimAreaDao.getOtherCountrys()).thenReturn(other);
	}
	
	public List<DimArea> initList(int level,Long id,String name,Long parentId) {		
		dimArea.setAreaLevel(level);
		dimArea.setId(id);
		dimArea.setName(name);
		dimArea.setParentId(parentId);
		list = new ArrayList<DimArea>();
		list.add(dimArea);
		return list;
	}

}
