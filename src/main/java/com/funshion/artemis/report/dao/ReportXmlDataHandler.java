package com.funshion.artemis.report.dao;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.report.bean.ReportTypeXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;


/**
 * 报告数据（xml格式）处理类型
 * @author shenwf
 * Reviewed by
 */
@Service
public class ReportXmlDataHandler {
	/**
	 * 根据id获取报告
	 * @param id
	 * @return
	 */
	public ReportXmlBean getReportById(Long id) {
		return ReportXmlReader.getReportById(id);
	}
	
	/**
	 * 获取报告列表
	 * @return
	 */
	public List<ReportXmlBean> getReportList() {
		return ReportXmlReader.getReportList();
	}
	
	/**
	 * 根据名字获取报告
	 * @param name
	 * @return
	 */
	public ReportXmlBean getByRptName(String name) {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		for(ReportXmlBean report : reportList) {
			if(report.getRptName().equals(name)) {
				return report;
			}
		}
		return null;
	}
	
	/**
	 * 根据类型获取报告列表
	 * @param type
	 * @return
	 */
	public List<ReportXmlBean> getReportsByTypeId(String typeId) {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		List<ReportXmlBean> typeList = new ArrayList<ReportXmlBean>();
		for(ReportXmlBean report : reportList) {
			if(report.getRptType().equals(typeId)) {
				typeList.add(report);
			}
		}
		return typeList;
	}
	
	/**
	 * 根据类型名字获取报告列表(自定义 查询页面的)
	 * @param type
	 * @return
	 */
	public List<ReportXmlBean> getCustromReportsByTypeName(String typeName) {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		List<ReportXmlBean> typeList = new ArrayList<ReportXmlBean>();
		for(ReportXmlBean report : reportList) {
			if(report.getRptTypeName().equals(typeName) && report.getIsShowInCustomPage().equals("1")) {
				typeList.add(report);
			}
		}
		return typeList;
	}
	
	/**
	 * 根据类型名字获取报告列表
	 * @param type
	 * @return
	 */
	public List<ReportXmlBean> getReportsByTypeName(String typeName) {
		List<ReportXmlBean> reportList = ReportXmlReader.getReportList();
		List<ReportXmlBean> typeList = new ArrayList<ReportXmlBean>();
		
		for(ReportXmlBean report : reportList) {
			if(report.getRptTypeName().equals(typeName)) {
				typeList.add(report);
			}
		}
		return typeList;
	}
	
	/**
	 * 获取报告类型列表
	 * @return
	 */
	public List<ReportTypeXmlBean> getReportTypes() {
		return ReportXmlReader.getReportTypeList();
	}
}
