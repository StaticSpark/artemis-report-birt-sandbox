package com.funshion.artemis.report.bean;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * 列信息实体
 * @author shenwf
 * Reviewed by zengyc
 */
@JsonPropertyOrder(alphabetic = true)
public class ColumnInfo {
	private String name;
	private String displayName;
	
	/**
	 * 统计类型，默认不统计。 sum 为求和
	 */
	private String countType;
	/**
	 * 该列 创建时需要执行的脚步
	 */
	private String script;
	
	private String isGroupColumn;

	public String getIsGroupColumn() {
		return isGroupColumn;
	}

	public void setIsGroupColumn(String isGroupColumn) {
		this.isGroupColumn = isGroupColumn;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", displayName=" + displayName + ", countType=" + countType + ", script=" + script + ", isGroupColumn=" + isGroupColumn + "]";
	}

}
