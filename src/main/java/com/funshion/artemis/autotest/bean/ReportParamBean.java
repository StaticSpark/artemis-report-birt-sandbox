package com.funshion.artemis.autotest.bean;

/**
 * 报告参数实体
 * 
 * @author shenwf Reviewed by
 */
public class ReportParamBean {
	/**
	 * 报告id
	 */
	private Long reportId;
	/**
	 * id集
	 */
	private String ids;
	/**
	 * 地域id
	 */
	private String areaIds;
	/**
	 * 条件类型
	 */
	private String conditonType;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 频次
	 */
	private String freq;
	
	private String rptName;

	public String getAreaIds() {
		if(areaIds == null) {
			return "";
		}
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getConditonType() {
		if(conditonType == null) {
			return "";
		}
		return conditonType;
	}

	public void setConditonType(String conditonType) {
		this.conditonType = conditonType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFreq() {
		if(freq == null) {
			return "";
		}
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getIds() {
		if(ids == null) {
			return "";
		}
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}
}
