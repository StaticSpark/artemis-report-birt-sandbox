package com.funshion.artemis.custom.bean;

import org.springframework.stereotype.Component;

/**
 * 广告报告实体
 * 与数据表rpt_base对应
 * @author guanzx
 *
 */
@Component
public class AdReport {
	
	private String id;
	private String path;
	private String rptSql;
	private String rptName;
	private String rptDisplayName;
	private Integer rptType;
	private Integer isCross;
	private Integer displayOrder;
	private Integer isGroupReport;	
	private Integer rptSource;
	private String totalVal;
	
	public String getTotalVal() {
		return totalVal;
	}

	public void setTotalVal(String totalVal) {
		this.totalVal = totalVal;
	}

	public Integer getRptSource() {
		return rptSource;
	}

	public void setRptSource(Integer rptSource) {
		this.rptSource = rptSource;
	}

	public Integer getIsGroupReport() {
		return isGroupReport;
	}

	public void setIsGroupReport(Integer isGroupReport) {
		this.isGroupReport = isGroupReport;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getIsCross() {
		return isCross;
	}

	public void setIsCross(Integer isCross) {
		this.isCross = isCross;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRptSql() {
		return rptSql;
	}

	public void setRptSql(String rptSql) {
		this.rptSql = rptSql;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}

	public String getRptDisplayName() {
		return rptDisplayName;
	}

	public void setRptDisplayName(String rptDisplayName) {
		this.rptDisplayName = rptDisplayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the rptType
	 */
	public Integer getRptType() {
		return rptType;
	}

	/**
	 * @param rptType the rptType to set
	 */
	public void setRptType(Integer rptType) {
		this.rptType = rptType;
	}


	@Override
	public String toString() {
		return rptDisplayName;
	}
	
}
