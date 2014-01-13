package com.funshion.artemis.custom.bean;

import java.util.Date;

/**
 * 广告物料实体
 * 与数据表ad_mat对应
 * @author guanzx
 *
 */
public class AdMat {
	
	private Integer adId;
	private Integer matId;
	private Date preisstime;
		
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
	 * @return the matId
	 */
	public Integer getMatId() {
		return matId;
	}
	/**
	 * @param matId the matId to set
	 */
	public void setMatId(Integer matId) {
		this.matId = matId;
	}
	/**
	 * @return the preisstime
	 */
	public Date getPreisstime() {
		return preisstime;
	}
	/**
	 * @param preisstime the preisstime to set
	 */
	public void setPreisstime(Date preisstime) {
		this.preisstime = preisstime;
	}
}
