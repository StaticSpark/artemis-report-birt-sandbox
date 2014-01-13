package com.funshion.artemis.report.createreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SharedStyleHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.TableGroupHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.TextDataHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.IncludeScript;
import org.eclipse.birt.report.model.elements.interfaces.IStyleModel;

import com.funshion.artemis.common.constant.ScriptDefine;
import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.report.bean.ColumnXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;

/**
 * 普通型报表
 * 
 * @author shenwf Reviewed by zengyc
 */
public class NormalReport extends Report {
	public NormalReport(ReportXmlBean report) {
		this.report = report;
		try {
			buildDataSource();
			buildDataSet();
			buildParam();
			initialize();
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 构建参数
	 * 
	 * @throws SemanticException
	 */
	public void buildParam() throws SemanticException {
		super.buildParam();
	}

	/**
	 * 初始化
	 */
	public void initialize() {
		String script = "initializeReport(" + getOrderColumns() +","+getHiddenColumns()+");";
		designHandle.setInitialize(script);
	}

	public void buildReport() throws IOException, SemanticException {
		try {
			//添加一个新的标签Dynamic Text,用于存放报表导出时的表头		
			TextDataHandle textData = designFactory.newTextData("dText");			
			//设置Dynamic Text的报表样式以及展现值
			setTextData(textData);
			// 获取表
			TableHandle table = designFactory.newTableItem("table", report.getColumnList().size());
			table.setDataSet(designHandle.findDataSet("ds"));
			// 设置报表样式
			setTableStyle(table);
			// 设置数据关联
			setDataBlinding(table);
			// 添加表数据结构
			addTableData(table);
			designHandle.getBody().add(textData);
			designHandle.getBody().add(table);
			// 生成表
			designHandle.saveAs(Tools.getBirtFileRoot() + report.getPath());
			designHandle.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置Dynamic Text的报表样式以及展现值
	 * @param textData
	 * @throws SemanticException
	 */
	public void setTextData(TextDataHandle textData) throws SemanticException {
		textData.setContentType("html");		
		if (isAdReport()){
			textData.setOnRender(ScriptDefine.getAdFormatStyle());
		}else if(isTwoDimensionalReport()){
			textData.setOnRender(ScriptDefine.getAdTwoDimensionalStyle());
		}else{			
			textData.setOnRender(ScriptDefine.mcExportReportFormat());
		}
	}

	public boolean isTwoDimensionalReport() {
		boolean flag = false;
		if (Integer.parseInt(report.getRptType()) == 9) {
			flag = true;
		}
		return flag;
	}
	
	/*
	 * 判断是否为广告相关报表
	 */
	public boolean isAdReport() {
		boolean flag = false;
		if (Integer.parseInt(report.getRptType()) < 7 
				|| Integer.parseInt(report.getRptType()) == 10 
				|| Integer.parseInt(report.getRptType()) == 11 ) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 设置报表样式
	 * 
	 * @param table
	 * @throws SemanticException
	 */
	public void setTableStyle(TableHandle table) throws SemanticException {
		table.setWidth("100%");
		table.setPageBreakInterval(100);
		table.setSortByGroups(false);// 不按默认分组排序
		designHandle.setLayoutPreference(DesignChoiceConstants.REPORT_LAYOUT_PREFERENCE_AUTO_LAYOUT);
		IncludeScript includeScript = new IncludeScript();
		includeScript.setFileName("/js/common/report_tool.js");
		designHandle.addIncludeScript(includeScript);
		// 获取表头部
		RowHandle tableheader = (RowHandle) table.getHeader().get(0);
		// 获取具体行的第一行
		RowHandle tabledetail = (RowHandle) table.getDetail().get(0);
		tabledetail.setOnRender(ScriptDefine.LINE_COLOR_SCRIPT);
		if (isHourReport()) {// 小时报表是显示25行
			tabledetail.setOnCreate(ScriptDefine.HOUR_LINE_BREAK_SCRIPT);
		} else {
			tabledetail.setOnCreate(ScriptDefine.LINE_BREAK_SCRIPT);
		}
		tabledetail.setOnPrepare("rowCount = 0");
		RowHandle footer = (RowHandle) table.getFooter().get(0);
		// 让统计行 居中
		footer.setOnCreate(ScriptDefine.TEXT_ALIGN_SCRIPT);
		// 加入总计标签
		if (isSumReport()) {// 如果有统计字段，就加入总计标签
			LabelHandle label = designFactory.newLabel("总计");
			label.setText("总计");
			label.setOnRender(ScriptDefine.TOTAL_TEXT_SCRIPT);
			CellHandle footCellTotal = (CellHandle) footer.getCells().get(1);
			footCellTotal.getContent().add(label);
		}
	}

	/**
	 * 判断是否是小时级报表
	 * 
	 * @return
	 */
	public boolean isHourReport() {
		for (ColumnXmlBean column : report.getColumnList()) {
			if (column.getColumnName().toLowerCase().contains("hour")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断该报表里面是否有统计字段
	 * 
	 * @return
	 */
	public boolean isSumReport() {
		for (ColumnXmlBean column : report.getColumnList()) {
			if (column.getCountType().toLowerCase().contains("sum")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取需要排序的列
	 * 
	 * @return
	 */
	public String getOrderColumns() {
		StringBuffer orderColumns = new StringBuffer();
		orderColumns.append("[");
		for (ColumnXmlBean column : report.getColumnList()) {
			if (column.getIsSort() != null && column.getIsSort().equals("1")) {
				orderColumns.append("\"" + column.getColumnName() + "\",");
			}
		}

		if (orderColumns.length() > 2) {
			orderColumns.deleteCharAt(orderColumns.length() - 1);
		}
		orderColumns.append("]");
		return orderColumns.toString();
	}
	
	/**
	 * 获取非adp报告需要隐藏的列
	 */
	public String getHiddenColumns() {
		StringBuffer hideColumns = new StringBuffer();
		String columns = report.getColumns();
		hideColumns.append("[");
		if (columns.contains("act_pla_rate")){
			String column[] = columns.split(",");
			for (int i=0;i<column.length;i++) {
				if (column[i].equals("act_pla_rate")){
					hideColumns.append(i);
					hideColumns.append(",");
				}
				if (column[i].equals("effe_pla_rate")){
					hideColumns.append(i);
					hideColumns.append(",");
				}
				if (column[i].equals("full_pla_rate")){
					hideColumns.append(i);
				}
			}
		}
		hideColumns.append("]");
		return hideColumns.toString();
	}


	/**
	 * 添加数据到表
	 * 
	 * @param table
	 * @throws SemanticException
	 */
	public void addTableData(TableHandle table) throws SemanticException {
		int i = 0;
		// 获取具体行的第一行
		RowHandle tabledetail = (RowHandle) table.getDetail().get(0);
		// 该脚本是格式化百分比
		RowHandle footer = (RowHandle) table.getFooter().get(0);
		List<Integer> hiddenColumnList = new ArrayList<Integer>();

		for (ColumnXmlBean columnInfo : report.getColumnList()) {
			if (columnInfo.getIsShow().equals("0")) {
				hiddenColumnList.add(i);
			}
			if (columnInfo.getIsGroupColumn().equals("1")) {
				// Table Group
				TableGroupHandle group = designFactory.newTableGroup();
				group.setName("MyGroup");
				group.setKeyExpr("dataSetRow[\"" + columnInfo.getColumnName() + "\"]");
				group.setInterval("none");
				group.setSortDirection("asc");
				table.getGroups().add(group);

				RowHandle groupHeader = designFactory.newTableRow(3);
				groupHeader.setOnCreate("style.textAlign = \"center\"");
				CellHandle tcell = (CellHandle) groupHeader.getCells().get(0);
				DataItemHandle groupData = designFactory.newDataItem(null);

				groupData.setResultSetColumn(columnInfo.getColumnName());
				groupData.setOnRender(columnInfo.getScript());
				tcell.getContent().add(groupData);
				group.getHeader().add(groupHeader);
			} else {
				// 设置数据
				CellHandle dataCell = (CellHandle) tabledetail.getCells().get(i);
				DataItemHandle data = designFactory.newDataItem("data_" + columnInfo.getColumnName());
				data.setResultSetColumn(columnInfo.getColumnName());
				dataCell.getContent().add(data);
				String script = columnInfo.getScript();
				data.setOnRender(script);// 添加脚本

				if (columnInfo.getColumnName().equals("rate")) {
					// 设置 总和中 点击率的百分比
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_clk", "total_pla"));
				} else if (columnInfo.getColumnName().equals("rate_s")) {
					// 设置 总和中 独立点击率的百分比
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_clk_s", "total_pla_s"));
				}else if (columnInfo.getColumnName().equals("act_pla_rate")) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_pla", "total_brw"));
				}else if (columnInfo.getColumnName().equals("effe_pla_rate")) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_effe_pla", "total_brw"));
				}else if (columnInfo.getColumnName().equals("full_pla_rate")) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_full_pla", "total_brw"));
				}else if (columnInfo.getColumnName().equals("clk_rate")) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
					dataCount.setOnRender(ScriptDefine.getPercentScript("total_clk", "total_brw"));
				}
				
				if (columnInfo.getCountType() != null && columnInfo.getCountType().length() > 0) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("total_" + columnInfo.getColumnName());
					dataCount.setResultSetColumn("total_" + columnInfo.getColumnName());
					footCell.getContent().add(dataCount);
				}
				
				if(columnInfo.getColumnName().equals("operation")) {
					CellHandle footCell = (CellHandle) footer.getCells().get(i);
					DataItemHandle dataCount = designFactory.newDataItem("sift_total");
					footCell.getContent().add(dataCount);
				}
			}

			i++;
		}

		SharedStyleHandle styleHandle = designFactory.newStyle("style_hidden");
		styleHandle.setProperty(IStyleModel.DISPLAY_PROP, "none");
		designHandle.getStyles().add(styleHandle);
		for (Integer colNum : hiddenColumnList) {
			ColumnHandle colHandle = (ColumnHandle) table.getColumns().get(colNum);
			try {
				colHandle.setStyle(styleHandle);
			} catch (Exception e) {
				System.out.println("不能隐藏的报告:" + report.getRptName() + " -- " + report.getRptDisplayName());
			}
		}
		
		if (!isSumReport()) {
			 table.getFooter().get(0).drop();
		}
	}
	
	/**
	 * 设置数据关联
	 * 
	 * @param table
	 * @throws SemanticException
	 */
	public void setDataBlinding(TableHandle table) throws SemanticException {
		RowHandle tableheader = (RowHandle) table.getHeader().get(0);
		ComputedColumn cs = null;
		PropertyHandle computedSet = table.getColumnBindings();
		int i = 0;
		
		for (ColumnXmlBean columnInfo : report.getColumnList()) {
			// 设置数据关联
			cs = StructureFactory.createComputedColumn();
			cs.setName(columnInfo.getColumnName());
			cs.setDisplayName(columnInfo.getColumnDisplayName());
			cs.setExpression("dataSetRow[\"" + columnInfo.getColumnName() + "\"]");
			computedSet.addItem(cs);

			if (columnInfo.getCountType() != null && columnInfo.getCountType().length() > 0) {
				ComputedColumn csTotal = StructureFactory.createComputedColumn();
				csTotal.setName("total_" + columnInfo.getColumnName());
				csTotal.setExpression("dataSetRow[\"" + columnInfo.getColumnName() + "\"]");
				csTotal.setDataType("Float");
				csTotal.setAggregateFunction(columnInfo.getCountType());
				computedSet.addItem(csTotal);
			}

			if (columnInfo.getColumnName().contains("rate")) {
				ComputedColumn csTotal = StructureFactory.createComputedColumn();
				csTotal.setName("total_rate_" + columnInfo.getColumnName());
			}
			
			CellHandle labelCell = (CellHandle) tableheader.getCells().get(i);
			String headerScript = columnInfo.getHeaderScript();
			// 设置列标签
			if(columnInfo.getColumnName().equals("operation")) {
				 TextDataHandle textData = designFactory.newTextData(columnInfo.getColumnName());
				 textData.setContentType("html");
				 textData.setOnRender(headerScript);
				 textData.setName("sift_sub");
				 labelCell.getContent().add(textData);
				 
				 TextDataHandle textTotalData = designFactory.newTextData("sift_total_data");
				 textTotalData.setContentType("html");
				 textTotalData.setName("sift_total_data");
				 labelCell.getContent().add(textTotalData);
			} else {
				
				LabelHandle label = designFactory.newLabel(columnInfo.getColumnName());
				label.setText(columnInfo.getColumnDisplayName());
				label.setName(columnInfo.getColumnName());
				label.setDisplayName(columnInfo.getColumnDisplayName());
				label.setOnRender(headerScript);
				labelCell.getContent().add(label);
			}
			
			i++;
		}
	}
}