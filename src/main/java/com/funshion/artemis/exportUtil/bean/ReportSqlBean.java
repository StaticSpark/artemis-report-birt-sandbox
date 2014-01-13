package com.funshion.artemis.exportUtil.bean;

import org.dom4j.Element;

/**
 * rpt_sql.xml对应的字段对象
 * @author guanzx
 *
 */
public class ReportSqlBean {
	
	private String rptType;
	private String name;
	private String sql;
	
	public void setValue(Element e) {
		if (e == null || e.getName() == null) {
			return;
		}	
		if (e.getName().equals("rpt_type")) {
			this.rptType = e.getTextTrim();
		} else if (e.getName().equals("text")) {
			this.sql= e.getTextTrim();
		} else if(e.getName().equals("name")) {
			this.name = e.getTextTrim();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRptType() {
		return rptType;
	}
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
}
