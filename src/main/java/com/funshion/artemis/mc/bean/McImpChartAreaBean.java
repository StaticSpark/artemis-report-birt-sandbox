package com.funshion.artemis.mc.bean;

import java.util.List;
import java.util.Map;

/**
 * imp 地域图表数据Bean
 * @author shenwf
 * Reviewed by
 */
public class McImpChartAreaBean {
	private String areaName;
	private List<Map<String, Object>> list;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

}
