package com.funshion.artemis.report.bean;

import java.util.List;

/**
 * 报告参数对象
 * 
 * @author shenwf Reviewed by zengyc
 */
public class ParamObj {
	private Long id;
	private String sql;
	private List<ColumnInfo> columnInfo;
	private String rptName;
	private String rptDisplayName;
	private Integer isCross;
	private Integer rptType;
	private String savePath;
	private String birtHome;
	private String dbUrl;
	private String dbUserName;
	private String dbPassword;
	private String jndiName;
	
	public String getRptDisplayName() {
		return rptDisplayName;
	}

	public void setRptDisplayName(String rptDisplayName) {
		this.rptDisplayName = rptDisplayName;
	}

	public Integer getRptType() {
		return rptType;
	}

	public void setRptType(Integer rptType) {
		this.rptType = rptType;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getBirtHome() {
		return birtHome;
	}

	public void setBirtHome(String birtHome) {
		this.birtHome = birtHome;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsCross() {
		return isCross;
	}

	public void setIsCross(Integer isCross) {
		this.isCross = isCross;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<ColumnInfo> getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(List<ColumnInfo> columnInfo) {
		this.columnInfo = columnInfo;
	}

	@Override
	public String toString() {
		return "ParamObj [sql=" + sql + ", columnInfo=" + columnInfo + "]";
	}
}
