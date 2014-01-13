package com.funshion.artemis.mc.overlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 播控UV周期图表数据
 * @author guanzx
 *
 */
public class McUvPeriodChartsData {

	/**
	 * 图表x轴数据
	 */
	private List<String> dayxAxis = new ArrayList<String>();
	
	/**
	 * 
	 */
	private List<String> areaxAxis = new ArrayList<String>();
	
	/**
	 * 图表series数据
	 */
	private List<UVSeries> series = new ArrayList<UVSeries>();
		
	/**
	 * 频次限制列表
	 * @return
	 */
	private Set uvLimit = new HashSet();
	
	private Map<String,List<UVSeries>> daySeres = new HashMap<String,List<UVSeries>>();
	private Map<String,List<String>> dayAxiswithKey = new HashMap<String,List<String>>();
	private List<String> areaList = new ArrayList<String>();
	private Map<String,List<String>> uvLimitwithKey = new HashMap<String,List<String>>();
	
	public List<String> getDayxAxis() {
		return dayxAxis;
	}

	public void setDayxAxis(List<String> dayxAxis) {
		this.dayxAxis = dayxAxis;
	}

	public List<String> getAreaxAxis() {
		return areaxAxis;
	}

	public void setAreaxAxis(List<String> areaxAxis) {
		this.areaxAxis = areaxAxis;
	}

	public List<UVSeries> getSeries() {
		return series;
	}

	public void setSeries(List<UVSeries> series) {
		this.series = series;
	}

	public Set getUvLimit() {
		return uvLimit;
	}

	public void setUvLimit(Set uvLimit) {
		this.uvLimit = uvLimit;
	}

	public Map<String, List<UVSeries>> getDaySeres() {
		return daySeres;
	}

	public void setDaySeres(Map<String, List<UVSeries>> daySeres) {
		this.daySeres = daySeres;
	}

	public Map<String, List<String>> getDayAxiswithKey() {
		return dayAxiswithKey;
	}

	public void setDayAxiswithKey(Map<String, List<String>> dayAxiswithKey) {
		this.dayAxiswithKey = dayAxiswithKey;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	public Map<String, List<String>> getUvLimitwithKey() {
		return uvLimitwithKey;
	}

	public void setUvLimitwithKey(Map<String, List<String>> uvLimitwithKey) {
		this.uvLimitwithKey = uvLimitwithKey;
	}
}
