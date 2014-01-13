package com.funshion.artemis.mc.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * 播控实体
 * @author shenwf
 * Reviewed by
 */
@JsonPropertyOrder(alphabetic = true)
public class McBase {
	private Long id;
	private String name;
	private String orderName;
	private String updateTime;
	private String status;
	private String paths;
	
	public String getPaths() {
		return paths;
	}

	public void setPaths(String paths) {
		this.paths = paths;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
//		if(updateTime != null && updateTime.length() > 19 ) {
//			return updateTime.substring(0, updateTime.length() - 2);
//		}
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
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

	@Override
	public String toString() {
		return "McBase [id=" + id + ", name=" + name + ", orderName=" + orderName + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
