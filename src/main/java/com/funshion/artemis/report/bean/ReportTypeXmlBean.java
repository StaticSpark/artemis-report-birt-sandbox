package com.funshion.artemis.report.bean;

import org.dom4j.Element;

/**
 * 报告类型实体
 * @author shenwf
 * Reviewed by
 */
public class ReportTypeXmlBean {
	private Long id;
	private String name;
	
	public void setValue(Element e) {
		if(e == null || e.getName() == null) {
			return;
		}
		
		if(e.getName().equals("id")) {
			this.id = Long.parseLong(e.getTextTrim());
		} else if(e.getName().equals("name")) {
			this.name = e.getTextTrim();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
