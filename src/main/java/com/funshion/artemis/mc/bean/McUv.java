package com.funshion.artemis.mc.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * 
 * @author zhaojj 
 * @since 2013-5-31
 * Reviewer 
 */
@JsonPropertyOrder(alphabetic = true)
public class McUv {
	private Long id;
	private String startDate;
	private String endDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
