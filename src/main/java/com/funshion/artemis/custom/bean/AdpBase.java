package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 广告位基础信息实体
 * 与数据表adp_base对应
 * @author guanzx
 *
 */
@Component
public class AdpBase {

	private Long id;
	private String name;
	private String code;
	private Integer size;
	private String status;
	private Integer typeId;
	private String adpDesc;
	private Integer returnAdNum;
	private DimAdpSize dimAdpSize;
	private String isFilter="0";
	private Integer isOptimizer;
	private String adpSize;
	
    //------------setter and getter method--------------
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAdpSize() {
		return adpSize;
	}

	public void setAdpSize(String adpSize) {
		this.adpSize = adpSize;
	}

	public Integer getIsOptimizer() {
		return isOptimizer;
	}

	public void setIsOptimizer(Integer isOptimizer) {
		this.isOptimizer = isOptimizer;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getAdpDesc() {
		return adpDesc;
	}
	
	public void setAdpDesc(String adpDesc) {
		this.adpDesc = adpDesc;
	}
	
	public Integer getReturnAdNum() {
		return returnAdNum;
	}

	public void setReturnAdNum(Integer returnAdNum) {
		this.returnAdNum = returnAdNum;
	}


	public DimAdpSize getDimAdpSize() {
		return dimAdpSize;
	}
	
	public void setDimAdpSize(DimAdpSize dimAdpSize) {
		this.dimAdpSize = dimAdpSize;
	}
	
	public String getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(String isFilter) {
		this.isFilter = isFilter;
	}
}
