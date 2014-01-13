package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 题材类型
 * @author guanzx
 *
 */
@Component
public class DimMedCategory {
	
	private Long id;
	private String fullname;
	private String medType;
	private String isDelete = "0";
	

	//------------------getter and setter method-------------------
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getMedType() {
		return medType;
	}
	public void setMedType(String medType) {
		this.medType = medType;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
