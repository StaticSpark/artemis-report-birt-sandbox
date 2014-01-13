package com.funshion.artemis.report.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.dom4j.Element;

/**
 * 报告xml实体类
 * 
 * @author shenwf Reviewed by
 */
@JsonPropertyOrder(alphabetic = true)
public class ReportXmlBean implements Comparable, Cloneable {
	private Long id;
	private String path;
	private String sql;
	private String rptName;
	private String rptDisplayName;
	private String rptType;
	private String rptTypeName;
	private String isCross;
	private String displayOrder;
	private String dbUrl;
	private String dbUserName;
	private String dbPassword;
	private String jndiName;
	private String totalVal;
	private String columns;
	private String columnDisplayNames;
	private String isShowInCustomPage;
	private List<ColumnXmlBean> columnList = new ArrayList<ColumnXmlBean>();

	public void setValue(Element e) {
		if (e == null || e.getName() == null) {
			return;
		}

		if (e.getName().equals("id")) {
			this.id = Long.parseLong(e.getTextTrim());
		} else if (e.getName().equals("path")) {
			this.path = e.getTextTrim();
		} else if (e.getName().equals("rpt_sql")) {
			this.sql = e.getTextTrim();
		} else if (e.getName().equals("rpt_name")) {
			this.rptName = e.getTextTrim();
		} else if (e.getName().equals("rpt_display_name")) {
			this.rptDisplayName = e.getTextTrim();
		} else if (e.getName().equals("rpt_type")) {
			this.rptType = e.getTextTrim();
		} else if (e.getName().equals("is_cross")) {
			this.isCross = e.getTextTrim();
		} else if (e.getName().equals("display_order")) {
			this.displayOrder = e.getTextTrim();
		} else if (e.getName().equals("columns")) {
			this.columns = e.getTextTrim();
		} else if (e.getName().equals("is_show_in_custom_page")) {
			this.isShowInCustomPage = e.getTextTrim();
		}
	}

	public String getIsShowInCustomPage() {
		return isShowInCustomPage;
	}

	public void setIsShowInCustomPage(String isShowInCustomPage) {
		this.isShowInCustomPage = isShowInCustomPage;
	}

	public String getRptTypeName() {
		return rptTypeName;
	}

	public void setRptTypeName(String rptTypeName) {
		this.rptTypeName = rptTypeName;
	}

	public String getTotalVal() {
		return totalVal;
	}

	public void setTotalVal(String totalVal) {
		this.totalVal = totalVal;
	}

	public List<ColumnXmlBean> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnXmlBean> columnList) {
		this.columnList = columnList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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

	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	public String getIsCross() {
		return isCross;
	}

	public void setIsCross(String isCross) {
		this.isCross = isCross;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
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

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getColumnDisplayNames() {
		return columnDisplayNames;
	}

	public void setColumnDisplayNames(String columnDisplayNames) {
		this.columnDisplayNames = columnDisplayNames;
	}
	
	public ReportXmlBean clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return (ReportXmlBean) o;
	}

	@Override
	public String toString() {
		return "ReportXmlBean [id=" + id + ", path=" + path + ", sql=" + sql + ", rptName=" + rptName + ", rptDisplayName=" + rptDisplayName + ", rptType=" + rptType + ", rptTypeName=" + rptTypeName
				+ ", isCross=" + isCross + ", displayOrder=" + displayOrder + ", dbUrl=" + dbUrl + ", dbUserName=" + dbUserName + ", dbPassword=" + dbPassword + ", jndiName=" + jndiName
				+ ", totalVal=" + totalVal + ", columns=" + columns + ", columnDisplayNames=" + columnDisplayNames + ", isShowInCustomPage=" + isShowInCustomPage + ", columnList=" + columnList + "]";
	}

	@Override
	public int compareTo(Object o) {
		ReportXmlBean reportXmlBean2 = (ReportXmlBean) o;

		Integer order1 = 0;
		Integer order2 = 0;

		try {
            if(getDisplayOrder() != null && getDisplayOrder().length() > 0) {
            	order1 = Integer.parseInt(getDisplayOrder());
            }
            
            if(reportXmlBean2.getDisplayOrder() != null && reportXmlBean2.getDisplayOrder().length() > 0) {
            	order2 = Integer.parseInt(reportXmlBean2.getDisplayOrder());
            }
            
            if(order1 > order2) {
            	return 1;
            } else if(order1 < order2) {
            	return -1;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
