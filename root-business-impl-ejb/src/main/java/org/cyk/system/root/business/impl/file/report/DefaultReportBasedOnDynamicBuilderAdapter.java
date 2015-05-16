package org.cyk.system.root.business.impl.file.report;

import org.cyk.system.root.business.api.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.userinterface.style.Border.Side;

public class DefaultReportBasedOnDynamicBuilderAdapter implements ReportBasedOnDynamicBuilderListener {

	@Override
	public void report(ReportBasedOnDynamicBuilder<?> report) {
		
	}

	@Override
	public void column(ReportBasedOnDynamicBuilder<?> report, Column column) {
		column.getHeaderStyle().getBackground().getColor().setHexademicalCode(COLUMN_HEADER_BACKGROUND_COLOR);
		column.getHeaderStyle().getBorder().setBottom(new Side());
		column.getHeaderStyle().getBorder().getBottom().setSize(2);
		
		column.getDetailStyle().getBackground().getColor().setHexademicalCode(COLUMN_DETAIL_BACKGROUND_COLOR);
		column.getDetailStyle().getBorder().setAll(null);
		
		column.getFooterStyle().getBackground().getColor().setHexademicalCode(COLUMN_FOOTER_BACKGROUND_COLOR);
	}

}
