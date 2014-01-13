package com.funshion.artemis.custom.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 物料基础信息
 * @author guanzx
 *
 */
@Component
public class MatBase {

	private Long id;
	private String name;		// 名称
	private String srcUrl;		// 源文件URL	需要获取
	private String type;		// 类型
	private Integer width;		// 宽度
	private Integer height;		// 高度
	private Date dateFrom;		// 开始时间	需要获取
	private Date dateEnd;		// 结束时间	需要获取
	private String lnkUrl;		// 链接地址
	private Integer isPreIss;	// 是否下发
	private Integer playDur;	// 播放时间
	private String detail;		// 说明
	private Long filesize;		//文件大小
	private String checksum;	//校验和
	
	
	
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
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the srcUrl
	 */
	public String getSrcUrl() {
		return srcUrl;
	}
	/**
	 * @param srcUrl the srcUrl to set
	 */
	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}
	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	/**
	 * @return the lnkUrl
	 */
	public String getLnkUrl() {
		return lnkUrl;
	}
	/**
	 * @param lnkUrl the lnkUrl to set
	 */
	public void setLnkUrl(String lnkUrl) {
		this.lnkUrl = lnkUrl;
	}
	/**
	 * @return the isPreIss
	 */
	public Integer getIsPreIss() {
		return isPreIss;
	}
	/**
	 * @param isPreIss the isPreIss to set
	 */
	public void setIsPreIss(Integer isPreIss) {
		this.isPreIss = isPreIss;
	}
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @return the playDur
	 */
	public Integer getPlayDur() {
		return playDur;
	}
	/**
	 * @param playDur the playDur to set
	 */
	public void setPlayDur(Integer playDur) {
		this.playDur = playDur;
	}
	/**
	 * @return the filesize
	 */
	public Long getFilesize() {
		return filesize;
	}
	/**
	 * @param filesize the filesize to set
	 */
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	

}
