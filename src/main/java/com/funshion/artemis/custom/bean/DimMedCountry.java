package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 产地类型
 * @author guanzx
 *
 */
@Component
public class DimMedCountry {
	
	private Long id;
	private String fullname;
	private Long medType;
	
	//--------getter and setter methods-------------
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
	public Long getMedType() {
		return medType;
	}
	public void setMedType(Long medType) {
		this.medType = medType;
	}
	
	
}
