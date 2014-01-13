package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 订单基础信息
 * @author guanzx
 *
 */
@Component
public class OrderBase {
	
	private Long id;	
	private String name;
	private Integer accountId;
	private Integer agencyId;
	private Integer salesId;
	private Integer aeId;
	private Double amount;
	private Integer isDelete = 0;    //是否已删除，1-已删除；0-未删除
	public int version;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getSalesId() {
		return salesId;
	}
	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}
	public Integer getAeId() {
		return aeId;
	}
	public void setAeId(Integer aeId) {
		this.aeId = aeId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}		
}
