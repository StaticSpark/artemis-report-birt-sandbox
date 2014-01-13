package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 广告查询条件实体
 * @author guanzx
 *
 */
@Component
public class AdConditionBean {
	
	private String adId;
	private String adName;
	private String weight;
	private String priority;
	private String statusName;
	private String adpName;
	private String orderName;
	private String adCtgory;
	private String pubDate;
	
	public AdConditionBean() {
		super();
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAdpName() {
		return adpName;
	}

	public void setAdpName(String adpName) {
		this.adpName = adpName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getAdCtgory() {
		return adCtgory;
	}

	public void setAdCtgory(String adCtgory) {
		this.adCtgory = adCtgory;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
