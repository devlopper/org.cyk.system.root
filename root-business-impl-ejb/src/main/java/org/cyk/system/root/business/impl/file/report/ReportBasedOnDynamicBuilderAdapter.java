package org.cyk.system.root.business.impl.file.report;

import java.lang.reflect.Field;
import java.util.Set;

import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;

public class ReportBasedOnDynamicBuilderAdapter implements ReportBasedOnDynamicBuilderListener {

	@Override
	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {
		
	}

	@Override
	public void column(ReportBasedOnDynamicBuilder<?> report, Column column) {
		
	}

	@Override
	public Boolean ignoreField(Field field) {
		return null;
	}

	@Override
	public Set<String> fieldToIgnore() {
		return null;
	}

}
