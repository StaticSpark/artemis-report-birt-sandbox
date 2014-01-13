package com.funshion.artemis.custom.bean;

/**
 * 媒体内容定向
 * @author guanzx
 *
 */
public class TgtAdCont {
	private Long adId; //广告Id
	private Long pkgId; //包Id
	private Long typeId ; //媒体类型Id
	private Long countryId; //产地Id
	private Long catId; //题材Id
	
	/**
	 * @return the adId
	 */
	public Long getAdId() {
		return adId;
	}
	/**
	 * @param adId the adId to set
	 */
	public void setAdId(Long adId) {
		this.adId = adId;
	}
	/**
	 * @return the pkgId
	 */
	public Long getPkgId() {
		return pkgId;
	}
	/**
	 * @param pkgId the pkgId to set
	 */
	public void setPkgId(Long pkgId) {
		this.pkgId = pkgId;
	}
	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the countryId
	 */
	public Long getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the catId
	 */
	public Long getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(Long catId) {
		this.catId = catId;
	}
}
