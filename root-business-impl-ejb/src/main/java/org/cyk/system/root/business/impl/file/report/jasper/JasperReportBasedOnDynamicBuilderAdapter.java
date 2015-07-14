package org.cyk.system.root.business.impl.file.report.jasper;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;

public class JasperReportBasedOnDynamicBuilderAdapter extends AbstractJasperReportBasedOnDynamicBuilder implements Serializable {

	private static final long serialVersionUID = -6397313866653430863L;
	
	@Override
	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {}
	
	@Override
	public void column(ReportBasedOnDynamicBuilder<?> report, Column column) {}
	
	@Override
	public void report(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report) {}

	@Override
	public void column(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report, ColumnBuilder columnBuilder,Column column) {}

}
