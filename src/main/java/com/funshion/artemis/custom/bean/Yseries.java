package com.funshion.artemis.custom.bean;

import java.util.List;

/**
 * y 轴数据对象
 * 
 * @author shenwf Reviewed by
 */
public class Yseries {
	private String name;
	private List<Double> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
}
