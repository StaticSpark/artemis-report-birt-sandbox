package com.funshion.artemis.custom.bean;

/**
 * 广告广告位实体
 * 与数据库中的表ad_adp对应
 * @author guanzx
 *
 */

public class AdAdp {
	
	private Integer adId;
	private Integer adpId;
	
	/**
	 * @return the adId
	 */
	public Integer getAdId() {
		return adId;
	}
	/**
	 * @param adId the adId to set
	 */
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	/**
	 * @return the adpId
	 */
	public Integer getAdpId() {
		return adpId;
	}
	/**
	 * @param adpId the adpId to set
	 */
	public void setAdpId(Integer adpId) {
		this.adpId = adpId;
	}	
}
