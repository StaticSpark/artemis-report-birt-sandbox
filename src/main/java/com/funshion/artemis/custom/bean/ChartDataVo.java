package com.funshion.artemis.custom.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表数据视图对象
 * @author shenwf
 * Reviewed by
 */
public class ChartDataVo {
	/**
	 * x 轴 坐标数据
	 */
	private List<String> xAxisCategories = new ArrayList<String>();
	/**
	 * y 轴坐标数据列表
	 */
	private List<Yseries> ySeries = new ArrayList<Yseries>();

	public List<String> getxAxisCategories() {
		return xAxisCategories;
	}

	public void setxAxisCategories(List<String> xAxisCategories) {
		this.xAxisCategories = xAxisCategories;
	}

	public List<Yseries> getySeries() {
		return ySeries;
	}

	public void setySeries(List<Yseries> ySeries) {
		this.ySeries = ySeries;
	}
}
