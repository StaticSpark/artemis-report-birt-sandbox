package com.funshion.artemis.report.bean;

import org.dom4j.Element;

/**
 * 列信息 （xml）实体类
 * 
 * @author shenwf Reviewed by
 */
public class ColumnXmlBean implements  Cloneable{
	private Long id;
	private String columnName;
	private String columnDisplayName;
	private String countType;
	private String script;
	private String headerScript;
	private String isShow;
	private String isGroupColumn;
	private String isSort;
	private String isSift;
	/**
	 * 列索引（该列在报告中的位置）
	 */
	private String index;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setValue(Element e) {
		if (e == null || e.getName() == null) {
			return;
		}

		if (e.getName().equals("id")) {
			this.id = Long.parseLong(e.getTextTrim());
		} else if (e.getName().equals("column_name")) {
			this.columnName = e.getTextTrim();
		} else if (e.getName().equals("column_display_name")) {
			this.columnDisplayName = e.getTextTrim();
		} else if (e.getName().equals("count_type")) {
			this.countType = e.getTextTrim();
		} else if (e.getName().equals("script")) {
			this.script = e.getTextTrim();
		} else if (e.getName().equals("is_show")) {
			this.isShow = e.getTextTrim();
		} else if (e.getName().equals("is_group_column")) {
			this.isGroupColumn = e.getTextTrim();
		} else if (e.getName().equals("is_sort")) {
			this.isSort = e.getTextTrim();
		} else if (e.getName().equals("header_script")) {
			this.headerScript = e.getTextTrim();
		} else if (e.getName().equals("is_sift")) {
			this.isSift = e.getTextTrim();
		}
	}

	public String getIsSift() {
		return isSift;
	}

	public void setIsSift(String isSift) {
		this.isSift = isSift;
	}

	public String getIsSort() {
		return isSort;
	}

	public void setIsSort(String isSort) {
		this.isSort = isSort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsGroupColumn() {
		return isGroupColumn;
	}

	public void setIsGroupColumn(String isGroupColumn) {
		this.isGroupColumn = isGroupColumn;
	}

	public String getHeaderScript() {
		return headerScript;
	}

	public void setHeaderScript(String headerScript) {
		this.headerScript = headerScript;
	}

	public ColumnXmlBean clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return (ColumnXmlBean) o;
	}

	@Override
	public String toString() {
		return "ColumnXmlBean [id=" + id + ", columnName=" + columnName + ", columnDisplayName=" + columnDisplayName + ", countType=" + countType + ", script=" + script + ", headerScript="
				+ headerScript + ", isShow=" + isShow + ", isGroupColumn=" + isGroupColumn + ", isSort=" + isSort + ", isSift=" + isSift + ", index=" + index + "]";
	}
}
