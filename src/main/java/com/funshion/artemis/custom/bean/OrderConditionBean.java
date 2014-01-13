package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 订单查询条件
 * @author guanzx
 *
 */
@Component
public class OrderConditionBean {
	
	private Long id;
	private String name;
	private String medUserName;
	private String saleName;
	private String price;

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

	public String getMedUserName() {
		return medUserName;
	}

	public void setMedUserName(String medUserName) {
		this.medUserName = medUserName;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
