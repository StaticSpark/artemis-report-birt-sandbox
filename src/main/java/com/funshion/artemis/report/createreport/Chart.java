package com.funshion.artemis.report.createreport;

import java.io.IOException;

import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.MarkerType;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;

import com.funshion.artemis.report.bean.ReportXmlBean;

public class Chart extends Report {
	public Chart(ReportXmlBean report) {
		this.report = report;
		try {
			buildDataSource();
			buildDataSet();
			buildParam();
		} catch (SemanticException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buildReport() throws IOException, SemanticException {
		ChartWithAxesImpl chartIns = (ChartWithAxesImpl) ((ExtendedItemHandle) designHandle.findElement("chart")).getReportItem().getProperty("chart.instance");
		PropertyHandle computedSet = ((ExtendedItemHandle) designHandle.findElement("chart")).getColumnBindings();
		ComputedColumn cs = StructureFactory.createComputedColumn();
		cs.setName("");
		cs.setDisplayName("");
		cs.setExpression("dataSetRow[\"\"]");
		computedSet.addItem(cs);

		Axis xAxis = chartIns.getAxes().get(0); // 得到x轴对象
		Axis yAxis1 = xAxis.getAssociatedAxes().get(0); // 得到y轴对象
		SeriesDefinitionImpl.create();
		LineSeries lineSeries = (LineSeries)LineSeriesImpl.create(); // 生成一个折线系列
		lineSeries.getLabel().setVisible(true);
	}
}
