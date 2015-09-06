package org.cyk.system.root.business.impl.file.report;

import java.util.Date;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;

public interface ReportRepositoryListener {

	Date getLowestFromDate(ReportBasedOnDynamicBuilderParameters<Object> parameters);
	Date getHighestToDate(ReportBasedOnDynamicBuilderParameters<Object> parameters);
}
