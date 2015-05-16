package org.cyk.system.root.business.api.file.report;

import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;

import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public interface JasperReportBasedOnDynamicBuilderListener {

	void report(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report);
	
	void column(DynamicReportBuilder dynamicReportBuilder,ReportBasedOnDynamicBuilder<?> report,AbstractColumn columnBuilder,Column column);
	
}
