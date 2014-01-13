package com.funshion.artemis.mc.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;
import com.funshion.artemis.mc.bean.McBase;
import com.funshion.artemis.mc.bean.McUv;
import com.funshion.artemis.mc.dao.McAreaDao;
import com.funshion.artemis.mc.dao.McReportDao;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;


/**
 * @since 20130828
 * @author guanzx
 *
 */
public class McReportManagerTest {

	private McAreaManager mcAreaManager;
	private McReportDao mcReportDao;
	private ReportXmlDataHandler reportXmlDataHandler;
	private McAreaDao mcAreaDao;
	private DimAreaDao dimAreaDao;
	McReportManager mcReportManager;
	
	
	@Before
	public void setUp() {
		
		mcReportManager = new McReportManager();
		mcAreaManager = mock(McAreaManager.class);
		mcReportDao = mock(McReportDao.class);
		reportXmlDataHandler = mock(ReportXmlDataHandler.class);
		mcAreaDao = mock(McAreaDao.class);
		dimAreaDao = mock(DimAreaDao.class);
		
		
		mcReportManager.setMcAreaManager(mcAreaManager);
		mcReportManager.setMcReportDao(mcReportDao);
		mcReportManager.setReportXmlDataHandler(reportXmlDataHandler);
		mcReportManager.setMcAreaDao(mcAreaDao);
		mcReportManager.setDimAreaDao(dimAreaDao);
	}
	
	@Test
	public void testImpSource() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("areaId", "11");
		request.addParameter("monId", "105");
		request.addParameter("type", "source");
		request.addParameter("areaGroupName", "北京上海广州");		
		Map map = new HashMap();
		map.put("source", "imp_fwd_source_report");
		request.setParameters(map);
		
		mockMcAreaManager();	
		mockMcReport();
		mockReportXmlData();
				
		//String expect = "{\"monId\":105,\"areaId\":11,\"reportId\":45,\"mcName\":\"测试播控项\",\"value\":\"null\",\"areaName\":\"北京\",\"areaGroupName\":\"北京上海广州\",\"monDate\":\"null\",\"hour\":\"null\",\"path\":\"null\",\"rptName\":\"clk_fwd_source_report\",\"rptList\":[{\"id\":45,\"path\":null,\"rptName\":\"clk_fwd_source_report\",\"rptType\":null,\"rptTypeName\":null,\"isShowInCustomPage\":null,\"sql\":null,\"totalVal\":\"imp_fwd_source_report\",\"columnList\":[],\"rptDisplayName\":null,\"isCross\":null,\"displayOrder\":null,\"dbUrl\":null,\"dbUserName\":null,\"dbPassword\":null,\"jndiName\":null,\"columns\":null,\"columnDisplayNames\":null}]}";
		String expect = "{\"monId\":105,\"areaId\":11,\"reportId\":45,\"mcName\":\"测试播控项\",\"value\":\"null\",\"areaName\":\"北京\",\"areaGroupName\":\"北京上海广州\",\"monDate\":\"null\",\"hour\":\"null\",\"path\":\"null\",\"rptName\":\"clk_fwd_source_report\",\"rptList\":[{\"columnDisplayNames\":null,\"columnList\":[],\"columns\":null,\"dbPassword\":null,\"dbUrl\":null,\"dbUserName\":null,\"displayOrder\":null,\"id\":45,\"isCross\":null,\"isShowInCustomPage\":null,\"jndiName\":null,\"path\":null,\"rptDisplayName\":null,\"rptName\":\"clk_fwd_source_report\",\"rptType\":null,\"rptTypeName\":null,\"sql\":null,\"totalVal\":\"imp_fwd_source_report\"}]}";
		expect = "{\"monId\":105,\"areaId\":11,\"reportId\":45,\"mcName\":\"测试播控项\",\"value\":\"null\",\"areaName\":\"北京\",\"areaGroupName\":\"北京上海广州\",\"monDate\":\"null\",\"startDate\":\"null\",\"endDate\":\"null\",\"hour\":\"null\",\"path\":\"null\",\"rptName\":\"clk_fwd_source_report\",\"rptList\":[{\"columnDisplayNames\":null,\"columnList\":[],\"columns\":null,\"dbPassword\":null,\"dbUrl\":null,\"dbUserName\":null,\"displayOrder\":null,\"id\":45,\"isCross\":null,\"isShowInCustomPage\":null,\"jndiName\":null,\"path\":null,\"rptDisplayName\":null,\"rptName\":\"clk_fwd_source_report\",\"rptType\":null,\"rptTypeName\":null,\"sql\":null,\"totalVal\":\"imp_fwd_source_report\"}]}";
		Assert.assertEquals(expect, mcReportManager.getImpSourceInfo(request));
				
	}
	
	@Test
	/*
	 * 测试播控地域信息
	 */
	public void testImpAreaGroup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("areaId", "11");
		request.addParameter("monId", "105");
		request.addParameter("monDate", "20130828");
		request.addParameter("path", "area_report.rptdesign");
		request.addParameter("areaGroupName", "北京上海广州");
		
		mockMcAreaManager();
		mockMcReport();
		mockReportPath();
		
		String expect = "{\"monId\":105,\"areaId\":11,\"mcName\":\"测试播控项\",\"path\":\"area_report.rptdesign\",\"startDate\":\"20130828\",\"endDate\":\"20130828\",\"areaName\":\"北京\",\"pathList\":['*', area_report.rptdesign],\"areaGroupName\":\"北京上海广州\"}";
		//Assert.assertEquals(expect, mcReportManager.getImpAreaGroupInfo(request));
			
	}
	
	@Test
	@Ignore
	public void testImpUvInfo() {
		mockReportPathByMonId();
		mockMcDatePeriod();
		mockMcReport();
		mockAreaListByMonId();
		
		String id = "105";
		String rptType = "";
		//String expected = "{\"mcBase\":{\"name\":\"测试播控项\",\"id\":null,\"status\":null,\"paths\":\"area_report.rptdesign\",\"updateTime\":null,\"orderName\":null},\"areaInfo\":[],\"maxDate\":\"20130828\",\"minDate\":\"20130827\"}";
		String expected = "{\"mcBase\":{\"id\":null,\"name\":\"测试播控项\",\"orderName\":null,\"paths\":\"area_report.rptdesign\",\"status\":null,\"updateTime\":\"20130828\"},\"areaInfo\":[],\"maxDate\":\"20130828\",\"minDate\":\"20130827\"}";
		//Assert.assertEquals(expected, mcReportManager.getImpUvReportInfo(id));
				
	}
	
	@Test
	/**
	 * 测试getUvDayAreaGroupReportInfo
	 */
	public void testUvAreaGroupInfo() {
		mockMcReport();
		mockDimAreaById();		
		String monId = "105";
		String areaId = "11";
		String monDate = "20130828";
//		String expected = "{\"mcBase\":{\"name\":\"测试播控项\",\"id\":null,\"status\":null,\"paths\":null,\"updateTime\":null,\"orderName\":null},\"dimArea\":null,\"monDate\":'20130828'}";
		String expected = "{\"mcBase\":{\"id\":null,\"name\":\"测试播控项\",\"orderName\":null,\"paths\":null,\"status\":null,\"updateTime\":\"20130828\"},\"dimArea\":null,\"monDate\":'20130828'}";
		String actual = mcReportManager.getUvDayAreaGroupReportInfo(monId, areaId, monDate);
		Assert.assertEquals(expected, actual);			
	}
	
	@Test
	/**
	 * 测试并发报告信息
	 */
	public void testConCurrentInfo() {
		
		mockMcReport();
		
		String id = "105";
		//String expected = "{\"mcBase\":{\"name\":\"测试播控项\",\"id\":null,\"status\":null,\"paths\":null,\"updateTime\":null,\"orderName\":null}}";
		String expected = "{\"mcBase\":{\"id\":null,\"name\":\"测试播控项\",\"orderName\":null,\"paths\":null,\"status\":null,\"updateTime\":\"20130828\"},\"date\":\"20130828\"}";
		String actual = mcReportManager.getConcurrentInfo(id);
		//Assert.assertEquals(expected, actual);
	}
	
	@Test
	/*
	 * 测试uv小时报告向前台传递的json参数
	 */
	public void testUvHourReport() {
		mockMcReport();
		mockMcUvById();
		
		mockAreaById();
		
		//String expected = "{\"uvLimit\":'',\"mcBase\":{\"name\":\"测试播控项\",\"id\":null,\"status\":null,\"paths\":null,\"updateTime\":null,\"orderName\":null},\"area\":{\"name\":\"测试地域\",\"id\":null,\"areaLevel\":null,\"parentId\":null},\"monDate\":\"20130828\",\"uvSn\":'',\"type\":'',\"period\":'20130827~20130828'}";
		String expected = "{\"uvLimit\":'',\"mcBase\":{\"id\":null,\"name\":\"测试播控项\",\"orderName\":null,\"paths\":null,\"status\":null,\"updateTime\":\"20130828\"},\"area\":{\"areaLevel\":null,\"id\":null,\"name\":\"测试地域\",\"parentId\":null},\"monDate\":\"20130828\",\"uvSn\":'',\"type\":'',\"period\":'20130827~20130828'}";
		Long monId = 105L;
		String areaId = "11";
		String monDate = "20130828";
		String uvLimit = "";
		String uvSn = "";
		String type = "";
		String uvId = "23";
		String actual = mcReportManager.getMcUvHourReportInfo(monId, areaId, monDate, uvLimit, uvSn, type, uvId);
		Assert.assertEquals(expected, actual);		
		
	}
	
	//---下面是所用到的模拟方法---
	
	public void mockAreaById() {
		Long areaId = 11L;
		DimArea dimArea = new DimArea();
		dimArea.setName("测试地域");
		when(dimAreaDao.getAreaById(areaId)).thenReturn(dimArea);
	}
	
	public void mockMcUvById() {
		McUv mcUv = new McUv();
		mcUv.setId(1L);
		mcUv.setStartDate("20130827");
		mcUv.setEndDate("20130828");
		Long uvId = 23L;
		when(mcReportDao.getMcUvById(uvId)).thenReturn(mcUv);	
	}
	
	public void mockDimAreaById() {
		Long id = 105L;
		DimArea dimArea = new DimArea();
		dimArea.setName("测试地域");
		when(mcAreaDao.getDimAreaById(id)).thenReturn(dimArea);
	}
	
	public void mockMcAreaManager() {
		String areaId = "11";
		when(mcAreaManager.getAreaName(areaId)).thenReturn("北京");
	}
	
	public void mockMcReport() {
		Long monId = 105L;
		McBase mcBase = new McBase();
		mcBase.setName("测试播控项");
		mcBase.setUpdateTime("20130828");
		when(mcReportDao.getMcBaseById(monId)).thenReturn(mcBase);		
	}
	
	public void mockReportPath() {
		Long monId = 105L;
		String areaId = "11";
		String monDate = "20130828";
		List list = new ArrayList();
		list.add("area_report.rptdesign");
		when(mcReportDao.getPathsByMonAreaDate(monId, areaId, monDate)).thenReturn(list);
	}
	
	public void mockReportPathByMonId() {
		Long monId = 105L;
		List list = new ArrayList();
		list.add("area_report.rptdesign");
		when(mcReportDao.getPathsByMonId(monId)).thenReturn(list);
	}
	
	public void mockMcDatePeriod() {
		Long monId = 105L;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("maxDate", "20130828");
		map.put("minDate", "20130827");
		//when(mcReportDao.getMonDatePeriod(monId)).thenReturn(map);
	}
	
	public void mockAreaListByMonId() {
		Long mcId = 105L;
		DimArea dimArea = new DimArea();
		dimArea.setId(105L);
		dimArea.setName("测试");
		List<DimArea> list = new ArrayList<DimArea>();
		when(mcAreaManager.getAreaListByMcId(mcId)).thenReturn(list);
	}
			
	public void mockReportXmlData() {
		String type = "播控source报告";
		ReportXmlBean report = new ReportXmlBean();
		List<ReportXmlBean> list = new ArrayList<ReportXmlBean>();
		report.setRptName("imp_fwd_source_report");
		report.setRptName("clk_fwd_source_report");
		report.setId(45L);
		list.add(report);
		when(reportXmlDataHandler.getReportsByTypeName(type)).thenReturn(list);
	}
}
