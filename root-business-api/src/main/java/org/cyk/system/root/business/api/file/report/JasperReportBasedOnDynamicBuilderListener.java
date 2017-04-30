package org.cyk.system.root.business.api.file.report;

import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;

public interface JasperReportBasedOnDynamicBuilderListener extends ReportBasedOnDynamicBuilderListener {

	void report(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report);
	
	void column(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report,ColumnBuilder columnBuilder,Column column);
	
}
