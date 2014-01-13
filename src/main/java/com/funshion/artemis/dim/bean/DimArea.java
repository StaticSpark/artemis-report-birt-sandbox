package com.funshion.artemis.dim.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;


/**
 * 地域实体
 * @author shenwf
 * Reviewed by gongpb
 */
@JsonPropertyOrder(alphabetic = true)
public class DimArea {
	private Long id;
	private String name;
	private Integer areaLevel;
	private Long parentId;
	
	public DimArea(){
		
	}
	public DimArea(Long id,String name,Integer areaLevel,Long parentId) {
		this.id = id;
		this.name = name;
		this.areaLevel = areaLevel;
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
