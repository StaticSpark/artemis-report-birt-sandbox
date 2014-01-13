package com.funshion.artemis.report.bean;

import org.dom4j.Element;

public class ColumnMappingXmlBean {
	private Long id;
	private String rptName;
	private String rptDisplayName;
	private String columnName;
	private String columnDisplayName;
	private String dataTable;
	private String selectName;
	private String groupBy;
	private String union;
	
	public void setValue(Element e) {
		
		if (e == null || e.getName() == null) {
			return;
		}
		
		if (e.getName().equals("id")) {
			this.id = Long.parseLong(e.getTextTrim());
		}else if (e.getName().equals("rpt_name")) {
			this.rptName = e.getTextTrim();
		}else if (e.getName().equals("rpt_display_name")) {
			this.rptDisplayName = e.getTextTrim();
		}else if (e.getName().equals("column_name")) {
			this.columnName = e.getTextTrim();
		}else if (e.getName().equals("column_display_name")) {
			this.columnDisplayName = e.getTextTrim();
		}else if (e.getName().equals("data_table")) {
			this.dataTable = e.getTextTrim();
		}else if (e.getName().equals("select_name")) {
			this.selectName = e.getTextTrim();
		}else if (e.getName().equals("group_by")) {
			this.groupBy = e.getTextTrim();
		}else if (e.getName().equals("union")) {
			this.union = e.getTextTrim();
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDisplayName() {
		return columnDisplayName;
	}
	public void setColumnDisplayName(String columnDisplayName) {
		this.columnDisplayName = columnDisplayName;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}
	
	
	
}
