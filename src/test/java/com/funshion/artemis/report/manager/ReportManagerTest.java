package com.funshion.artemis.report.manager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.funshion.artemis.custom.bean.ReportSource;
import com.funshion.artemis.custom.bean.ReportType;
import com.funshion.artemis.custom.dao.ReportSourceDao;
import com.funshion.artemis.custom.dao.ReportTypeDao;
import com.funshion.artemis.custom.dao.RptColumnDao;
import com.funshion.artemis.report.bean.ColumnInfo;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.report.dao.ReportXmlDataHandler;


/**
 * 
 * @author guanzx
 *
 */

public class ReportManagerTest {
	   
	private ReportTypeDao reportTypeDao;
	private ReportSourceDao reportSourceDao;
	private RptColumnDao rptColumnDao;
	private ReportXmlDataHandler reportXmlDataHandler;
	
	ReportManager reportManager;
	ReportType reportType;
	ReportSource reportSource;
	ColumnInfo columnInfo;
	ReportXmlBean reportXmlBean;
	
	@Before
	public void setUp() {
		reportManager = new ReportManager();
		reportTypeDao = mock(ReportTypeDao.class);
		reportSourceDao = mock(ReportSourceDao.class);
		rptColumnDao = mock(RptColumnDao.class);
		reportXmlDataHandler = mock(ReportXmlDataHandler.class);
		
		reportManager.setReportTypeDao(reportTypeDao);
		reportManager.setReportSourceDao(reportSourceDao);
		reportManager.setRptColumnDao(rptColumnDao);
		reportManager.setReportXmlDataHandler(reportXmlDataHandler);
		
		reportType = new ReportType();
		reportSource = new ReportSource();
		columnInfo = new ColumnInfo();
		reportXmlBean = new ReportXmlBean();
		
	}
	
	@Test
	public void testAdReportJsonWhenIdisNull() {	
			
		mockReportTypeDao();
		mockReportSourceDao();
		
		String expect = "{\"typeList\":[{\"id\":12,\"name\":\"ad\"}],\"sourceList\":[{\"isInUse\":1,\"name\":\"adp\"}]}";
		Assert.assertEquals(reportManager.getAdReportJson(""),expect);		
	}
	
	@Test
	public void testAdReportJson() {
		
		mockReportTypeDao();
		mockReportSourceDao();
		mockRptColumnDao();		
		mockReportXmlDataHandler();
		String result = "{\"typeList\":[{\"id\":12,\"name\":\"ad\"}],\"sourceList\":[{\"isInUse\":1,\"name\":\"adp\"}],\"columnInfo\":[],\"report\":{\"columnDisplayNames\":null,\"columnList\":[],\"columns\":null,\"dbPassword\":null,\"dbUrl\":null,\"dbUserName\":null,\"displayOrder\":null,\"id\":1,\"isCross\":null,\"isShowInCustomPage\":null,\"jndiName\":null,\"path\":null,\"rptDisplayName\":null,\"rptName\":null,\"rptType\":null,\"rptTypeName\":null,\"sql\":null,\"totalVal\":null}}";
		Assert.assertEquals(reportManager.getAdReportJson("1"),result);	
		
	}
	
	@Test
	public void testReportColumnInfoWhenIdisNull() {		
		Assert.assertEquals(reportManager.getReportColumnInfo(null), "");
	}
	
	@Test
	public void testReportColumnInfo() {
		mockRptColumnDao();
		Assert.assertEquals(reportManager.getReportColumnInfo("1"), "[]");
	}
	
	//@Test
	public void testSqlandColumnIsNull() {
		Assert.assertFalse(reportManager.validateSqlAndColumn(null, null));
		Assert.assertFalse(reportManager.validateSqlAndColumn("0", "0"));
	}
	
	//@Test
	public void testJsonisValidate() {
		String sql = "select sd as 'ss',sd as 'd' from table";
		String json = "[{\"id\":1,\"displayName\":\"sd\",\"countType\":\"sum\",\"script\":null,\"isGroupColumn\":null}]";
		Assert.assertFalse(reportManager.validateSqlAndColumn(sql, json));
	}
	
	//@Test
	public void testSql() {
		String sql = "select * from table";
		String json = "[{\"id\":1,\"displayName\":\"sd\",\"countType\":\"sum\",\"script\":null,\"isGroupColumn\":null}]";
		Assert.assertTrue(reportManager.validateSqlAndColumn(sql, json));
	}
	
	//@Test
	public void testSqlColumn() {
		String sql = "select as,ad,af,ag from table";
		String json = "[]";
		Assert.assertTrue(reportManager.validateSqlAndColumn(sql, json));
	}
		
	
	//----利用mock模拟相应的DAO----- 
	public void mockReportTypeDao() {
		reportType.setId(12);
		reportType.setName("ad");
		List<ReportType> reportTypeList = new ArrayList<ReportType>();
		reportTypeList.add(reportType);	
		when(reportTypeDao.getAll()).thenReturn(reportTypeList);
	}
	
	public void mockReportSourceDao() {
		reportSource.setIsInUse(1);
		reportSource.setName("adp");
		List<ReportSource> reportSourceList = new ArrayList<ReportSource>();
		reportSourceList.add(reportSource);
		when(reportSourceDao.getAll()).thenReturn(reportSourceList);
	}
	
	public void mockRptColumnDao() {
		columnInfo.setCountType("sum");
		columnInfo.setDisplayName("播放量");
		columnInfo.setIsGroupColumn("1");
		columnInfo.setName("ad");
		columnInfo.setScript("/js");
		Long rptId = 1L;
		List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
		when(rptColumnDao.getColumnList(rptId)).thenReturn(columnInfoList);			
	}
	
	public void mockReportXmlDataHandler() {
		Long rptId = 1L;
		reportXmlBean.setId(1L);
		when(reportXmlDataHandler.getReportById(rptId)).thenReturn(reportXmlBean);
	}	
}
