package org.cyk.system.root.business.impl.file.report;

import java.util.Date;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.utility.common.cdi.AbstractBean;

public class ReportRepositoryAdapter extends AbstractBean implements ReportRepositoryListener {

	private static final long serialVersionUID = -5634358392297775313L;

	@Override
	public Date getLowestFromDate(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
		return null;
	}

	@Override
	public Date getHighestToDate(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
		return null;
	}

}
