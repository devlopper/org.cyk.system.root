package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractReportBasedOnDynamicBuilder extends AbstractBean implements ReportBasedOnDynamicBuilderListener,Serializable {

	private static final long serialVersionUID = -6397313866653430863L;

	@Override
	public Boolean ignoreField(Field field) {
		return Boolean.FALSE;
	}
	
	@Override
	public Set<String> fieldToIgnore() {
		return null;
	}
	
}
