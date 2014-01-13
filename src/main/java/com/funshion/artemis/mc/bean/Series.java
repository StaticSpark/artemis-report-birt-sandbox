package com.funshion.artemis.mc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * series数据对象
 * @author guanzx
 *
 */
public class Series {

	private String name;
	private List<Long> data = new ArrayList<Long>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getData() {
		return data;
	}
	public void setData(List<Long> data) {
		this.data = data;
	}
	
}
