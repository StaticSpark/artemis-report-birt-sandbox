package com.funshion.artemis.report.createreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.item.crosstab.core.ICrosstabConstants;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabCellHandle;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabReportItemHandle;
import org.eclipse.birt.report.item.crosstab.core.de.DimensionViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.LevelViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.MeasureViewHandle;
import org.eclipse.birt.report.item.crosstab.core.util.CrosstabExtendedItemFactory;
import org.eclipse.birt.report.model.api.ComputedColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DataSetHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.olap.MeasureGroupHandle;
import org.eclipse.birt.report.model.api.olap.MeasureHandle;
import org.eclipse.birt.report.model.api.olap.TabularCubeHandle;
import org.eclipse.birt.report.model.api.olap.TabularDimensionHandle;
import org.eclipse.birt.report.model.api.olap.TabularHierarchyHandle;
import org.eclipse.birt.report.model.api.olap.TabularLevelHandle;
import org.eclipse.birt.report.model.elements.interfaces.ICubeModel;

import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.report.bean.ColumnXmlBean;
import com.funshion.artemis.report.bean.ReportXmlBean;

/**
 * 交叉表
 * 
 * @author shenwf Reviewed by zengyc
 */
public class CrossReport extends Report {
	public CrossReport(ReportXmlBean report) {
		this.report = report;
		try {
			buildDataSource();
			buildDataSet();
			buildParam();
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
	 * 构建数值cube
	 * 
	 * @param cubeHandle
	 * @throws SemanticException
	 */
	public MeasureHandle buildMeasureCube(TabularCubeHandle cubeHandle) throws SemanticException {
		MeasureGroupHandle measureGroupHandle = designHandle.getElementFactory().newTabularMeasureGroup("MeasureGroup");
		measureGroupHandle.add(MeasureGroupHandle.MEASURES_PROP, designFactory.newTabularMeasure(null));
		MeasureHandle measure = (MeasureHandle) measureGroupHandle.getContent(MeasureGroupHandle.MEASURES_PROP, 0);
		List<ColumnXmlBean> columnList = report.getColumnList();
		ColumnXmlBean column = columnList.get(columnList.size() - 1);
		measure.setName(column.getColumnName());
		measure.setMeasureExpression("dataSetRow['" + column.getColumnName() + "']");
		measure.setFunction(DesignChoiceConstants.MEASURE_FUNCTION_SUM);
		measure.setCalculated(false);
		measure.setDataType(DesignChoiceConstants.COLUMN_DATA_TYPE_FLOAT);
		cubeHandle.setProperty(ICubeModel.MEASURE_GROUPS_PROP, measureGroupHandle);
		return measure;
	}

	/**
	 * 构建维度 cube
	 * @param cubeHandle
	 * @throws SemanticException
	 */
	public List<TabularDimensionHandle> buildDimensionCube(TabularCubeHandle cubeHandle) throws SemanticException {
		List<ColumnXmlBean> columnList = report.getColumnList();
		List<TabularDimensionHandle> dimensionList = new ArrayList<TabularDimensionHandle>();
		for(int i = 0; i < columnList.size() - 1; i++) {
			ColumnXmlBean column = columnList.get(i);
			TabularDimensionHandle dimension = designFactory.newTabularDimension(null);
			cubeHandle.add(TabularCubeHandle.DIMENSIONS_PROP, dimension);
			if(column.getCountType()!=null && column.getCountType().equals("time")) {//是时间类型
			   dimension.setTimeType(true);
			} else {
			   dimension.setTimeType(false);
			}
			dimension.setName(column.getColumnName());
			dimension.add(TabularDimensionHandle.HIERARCHIES_PROP, designFactory.newTabularHierarchy(null));

			TabularHierarchyHandle hierarchy = (TabularHierarchyHandle) dimension.getContent(TabularDimensionHandle.HIERARCHIES_PROP, 0);
			hierarchy.setDataSet((DataSetHandle) designHandle.getDataSets().get(0));
			hierarchy.add(TabularHierarchyHandle.LEVELS_PROP, designFactory.newTabularLevel(dimension, null));

			TabularLevelHandle tabularLeval = (TabularLevelHandle) hierarchy.getContent(TabularHierarchyHandle.LEVELS_PROP, 0);
			tabularLeval.setName(column.getColumnName());
			tabularLeval.setColumnName(column.getColumnName());
			tabularLeval.setDataType(DesignChoiceConstants.COLUMN_DATA_TYPE_STRING);
			dimensionList.add(dimension);
		}
		return dimensionList;
	}
	
	/**
	 * 构建 报告ui
	 * @throws SemanticException
	 */
	public void buildReportUI() throws SemanticException {
		TabularCubeHandle cubeHandle = designHandle.getElementFactory().newTabularCube("MyCube");
		cubeHandle.setDataSet((DataSetHandle) designHandle.getDataSets().get(0));
		designHandle.getCubes().add(cubeHandle);

		MeasureHandle measure = buildMeasureCube(cubeHandle);
		List<TabularDimensionHandle> dimensionList = buildDimensionCube(cubeHandle);

		ExtendedItemHandle xtab = CrosstabExtendedItemFactory.createCrosstabReportItem(designHandle, cubeHandle, "MyCrosstab");
		designHandle.getBody().add(xtab);

		IReportItem reportItem = xtab.getReportItem();
		CrosstabReportItemHandle xtabHandle = (CrosstabReportItemHandle) reportItem;
		DesignElementHandle eii = xtabHandle.getModelHandle();

		List<ColumnXmlBean> columnList = report.getColumnList();
		for(int i = 0; i < dimensionList.size(); i++) {
			TabularDimensionHandle dimension = dimensionList.get(i);
			ColumnXmlBean column = columnList.get(i);
			
			TabularHierarchyHandle hierarchy = (TabularHierarchyHandle) dimension.getContent(TabularDimensionHandle.HIERARCHIES_PROP, 0);
			TabularLevelHandle tabularLeval = (TabularLevelHandle) hierarchy.getContent(TabularHierarchyHandle.LEVELS_PROP, 0);
			
			ComputedColumn bindingColumn = StructureFactory.newComputedColumn(eii, dimension.getName());
			ComputedColumnHandle bindingHandle = ((ReportItemHandle) eii).addColumnBinding(bindingColumn, false);
			String exp = "dimension['" + dimension.getName() + "']['" + column.getColumnName() + "']";
			bindingColumn.setExpression(exp);
	
			DataItemHandle dataHandle = designFactory.newDataItem(column.getColumnName());
			dataHandle.setResultSetColumn(bindingHandle.getName());
	      
			DimensionViewHandle dvh = null;
			if(i == dimensionList.size() - 1) {
				dvh = xtabHandle.insertDimension(dimension, ICrosstabConstants.COLUMN_AXIS_TYPE, i);
			} else {
				dvh = xtabHandle.insertDimension(dimension, ICrosstabConstants.ROW_AXIS_TYPE, i);
			}
			
			LevelViewHandle levelViewHandle = dvh.insertLevel(tabularLeval, i);
			List list = new ArrayList();
			list.add(measure);
			CrosstabCellHandle cell = levelViewHandle.addSubTotal(list, new ArrayList());
//			cell.addContent(dataHandle);
			CrosstabCellHandle cellHandle = levelViewHandle.getCell();
			cellHandle.addContent(dataHandle);
		}
		
		CrosstabCellHandle columnTotalCrosstabCellHandle =  
				xtabHandle.addGrandTotal(ICrosstabConstants.COLUMN_AXIS_TYPE);
		MeasureViewHandle mvh = xtabHandle.insertMeasure(measure, 0);
		//mvh.addAggregation("medName", "", "log_date", "");
		mvh.removeHeader();
	}

	public void buildReport() throws IOException, SemanticException {
		buildDataSource();
		buildDataSet();
		try {
			designHandle.setLayoutPreference(DesignChoiceConstants.REPORT_LAYOUT_PREFERENCE_AUTO_LAYOUT);
			buildReportUI();
			designHandle.saveAs(Tools.getBirtFileRoot() + report.getPath());
			designHandle.close();
			//Platform.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
