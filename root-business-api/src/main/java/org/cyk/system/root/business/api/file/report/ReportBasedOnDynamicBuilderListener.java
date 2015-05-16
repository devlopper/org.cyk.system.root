package org.cyk.system.root.business.api.file.report;

import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;

public interface ReportBasedOnDynamicBuilderListener {

	String COLUMN_HEADER_BACKGROUND_COLOR = "CAD9C7";
	String COLUMN_DETAIL_BACKGROUND_COLOR = "FFFFFF";
	String COLUMN_FOOTER_BACKGROUND_COLOR = "CAD9C7";
	
	void report(ReportBasedOnDynamicBuilder<?> report);
	
	void column(ReportBasedOnDynamicBuilder<?> report,Column column);
	
}
