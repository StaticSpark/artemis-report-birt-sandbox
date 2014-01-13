package com.funshion.artemis.custom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.GenerateJson;
import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.custom.bean.ChartDataVo;
import com.funshion.artemis.custom.bean.Yseries;
import com.funshion.artemis.report.bean.ReportXmlBean;
import com.funshion.artemis.webservice.dao.WSDataDao;

/**
 * 图表 sql 语句服务类
 * 
 * @author shenwf Reviewed by
 */
@Service
public class AdDataManager {
	@Autowired
	private AdReportSqlManager adReportSqlManager;

	@Autowired
	private WSDataDao chartDataDao;

	/**
	 * 获取报告数据
	 * 
	 * @param sql
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param conditonType
	 * @param chartType
	 * @param freq
	 * @param areaIds
	 * @return
	 */
	public List<Map<String, Object>> getSqlData(String ids, String startTime, String endTime, Long reportId, String conditonType, String chartType, String freq, String areaIds) {
		String sql = adReportSqlManager.getReportSql(null, ids, startTime, endTime, reportId, conditonType, chartType, freq, areaIds);
		List<Map<String, Object>> list = chartDataDao.getData(sql);
		return list;
	}

	/**
	 * 获取图表所需的json数据
	 * 
	 * @param sql
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @param reportId
	 * @param conditonType
	 * @param chartType
	 * @param freq
	 * @param areaIds
	 * @return
	 */
	public String getChartDataJson(String ids, String startTime, String endTime, Long reportId, String conditonType, String chartType, String freq, String areaIds) {
		List<Map<String, Object>> list = getSqlData(ids, startTime, endTime, reportId, conditonType, chartType, freq, areaIds);
		ReportXmlBean reportXmlBean = ReportXmlReader.getReportById(reportId);
		String xNameKey = reportXmlBean.getColumnList().get(1).getColumnName();
		if(reportXmlBean.getRptName().contains("city")) {
			xNameKey = reportXmlBean.getColumnList().get(2).getColumnName();
		}
		ChartDataVo chartDataVo = new ChartDataVo();
		String[] quotas = getQuotas(conditonType);
		for (int i = 0; i < quotas.length; i++) {
			if (!reportXmlBean.getColumns().contains(quotas[i])) {
				continue;
			}
			List<Double> data = new ArrayList<Double>();
			Yseries ySeries = new Yseries();
			ySeries.setName(ReportXmlReader.getColumnByName(quotas[i]).getColumnDisplayName());
			for (Map<String, Object> map : list) {
				Object o = map.get(quotas[i]);
				Double d = 0.0;
				if (o != null) {
					String s = o.toString();
					if (s.contains("-")) {
						d = null;
					} else {
						d = Double.parseDouble(s);
					}
				}
				data.add(d);
			}
			ySeries.setData(data);
			chartDataVo.getySeries().add(ySeries);
		}

		for (Map<String, Object> map : list) {
			String xName = map.get(xNameKey).toString();
			chartDataVo.getxAxisCategories().add(xName);
		}
		
		this.formateData(chartDataVo, reportXmlBean);
		String json = new GenerateJson().generate(chartDataVo);
		return json;
	}
	
	/**
	 * 格式化数据
	 * 
	 * @param chartDataVo
	 */
	public void formateData(ChartDataVo chartDataVo, ReportXmlBean reportXmlBean) {
		List<String> xAxis = new ArrayList<String>();
		
	    for (String xName : chartDataVo.getxAxisCategories()) { 
			if(reportXmlBean.getRptName().contains("hour")) {
				xAxis.add(Tools.formatHour(xName));
			} else {
				xAxis.add(xName);
			}
		}
		chartDataVo.setxAxisCategories(xAxis);
		
		int index = getFirstRateIndex(chartDataVo);//获取** 率的位置
		//在 ** 率插入 “右”
		List<Yseries> ySeries = new ArrayList<Yseries>();
		Yseries left = new Yseries();
		left.setName("<span id='left_lengend'>左轴</span>");
		left.setData(new ArrayList<Double>());
		ySeries.add(left);
		for(int i = 0; i < chartDataVo.getySeries().size(); i++) {
			if(index == i) {
				Yseries right= new Yseries();
				right.setName("<span id='right_lengend'>右轴</span>");
				right.setData(new ArrayList<Double>());
				ySeries.add(right);
			}
			ySeries.add(chartDataVo.getySeries().get(i));
		}
		chartDataVo.setySeries(ySeries);
	}
	
	/**
	 * 获取 ** 率 索引位置
	 * @param chartDataVo
	 * @return
	 */
	public int getFirstRateIndex(ChartDataVo chartDataVo) {
		List<Yseries> ySeries = chartDataVo.getySeries();
		for(int i = 0; i < ySeries.size(); i++) {
			if(ySeries.get(i).getName().contains("率") || ySeries.get(i).getName().contains("比")) {
				return i;
			}
		}
		return  -1;
	}

	public String[] getQuotas(String conditionType) {
		String[] quotas = new String[] { "brw_quota", "req", "brw", "pla_quota", "pla",  "effe_pla","full_pla","clk", "brw_rate", "pla_rate","act_pla_rate",
				"effe_pla_rate", "full_pla_rate", "rate", "clk_rate" };
		return quotas;
	}
}
