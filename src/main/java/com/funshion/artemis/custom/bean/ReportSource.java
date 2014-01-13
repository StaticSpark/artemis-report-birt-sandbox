package com.funshion.artemis.custom.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;


/**
 * 报告 来源
 * 
 * @author shenwf 
 */
@JsonPropertyOrder(alphabetic = true)
public class ReportSource{
	private String name;
	private Integer isInUse;
	
	public Integer getIsInUse() {
		return isInUse;
	}

	public void setIsInUse(Integer isInUse) {
		this.isInUse = isInUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
