package com.funshion.artemis.custom.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.springframework.stereotype.Component;

/**
 * 广告报告类型实体
 * @author guanzx
 *
 */
@Component
@JsonPropertyOrder(alphabetic = true)
public class ReportType {

	private String name;
	private Integer id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
