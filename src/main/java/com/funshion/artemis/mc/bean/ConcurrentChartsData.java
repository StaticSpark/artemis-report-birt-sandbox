package com.funshion.artemis.mc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>并发报告可视化所需的数据对象(json对象)</b></p>
 * { 
 *  "periodSeries":[{"name":"1+UV","data":[2039,2039,2039,2039,2039,2039]},{"name":"2+UV","data":[9,22,31,38,57,76]}...], </br>
 *  "timeSeries":[{"name":"1+UV","data":[33,14,8,5,11,9,19,45,62,108,130,105,113,109,112,101,110,138,152,166,190,145,126,65]}...],</br>
 *  "periodXAxis":["1s","5s","15s","1min","10min","1d"],</br>
 *  "timexAxis":["00:00 ~ 01:00","01:00 ~ 02:00","02:00 ~ 03:00"...]</br>
 * }
 * 
 * @author guanzx
 *
 */
public class ConcurrentChartsData {
	//指定时段分布图的x轴数据
	private List<String> periodXAxis = new ArrayList<String>();
	//小时分布图的x轴数据
	private List<String> timexAxis = new ArrayList<String>();
	//指定时段分布的series数据
	private List<Series> periodSeries = new ArrayList<Series>();
	//小时分布的series数据
	private List<Series> timeSeries = new ArrayList<Series>();
	
	
	public List<String> getPeriodXAxis() {
		return periodXAxis;
	}
	public void setPeriodXAxis(List<String> periodXAxis) {
		this.periodXAxis = periodXAxis;
	}
	public List<String> getTimexAxis() {
		return timexAxis;
	}
	public void setTimexAxis(List<String> timexAxis) {
		this.timexAxis = timexAxis;
	}
	public List<Series> getPeriodSeries() {
		return periodSeries;
	}
	public void setPeriodSeries(List<Series> periodSeries) {
		this.periodSeries = periodSeries;
	}
	public List<Series> getTimeSeries() {
		return timeSeries;
	}
	public void setTimeSeries(List<Series> timeSeries) {
		this.timeSeries = timeSeries;
	}	
}
