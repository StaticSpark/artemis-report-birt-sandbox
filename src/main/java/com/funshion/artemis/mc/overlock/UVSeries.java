package com.funshion.artemis.mc.overlock;

import java.util.ArrayList;
import java.util.List;

public class UVSeries {
	
	private String name;
	
	private List<Double> data = new ArrayList<Double>();

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