package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderIdentifiableConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractReportRepository extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6917567891985885124L;
	
	@Inject protected ReportBusiness reportBusiness;
	
	public abstract void build();
	
	protected void addListener(ReportBasedOnDynamicBuilderListener listener){
		ReportBasedOnDynamicBuilderListener.GLOBALS.add(listener);
		logInfo("Global dynamic report builder listener added : {}",listener);
	}
	
	protected void addConfiguration(ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> configuration){
		ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS.add(configuration);
		logInfo("Dynamic report configuration added. Identifier is {} , Entity is {} , Model is {}"
				,configuration.getReportBasedOnDynamicBuilderIdentifier(),configuration.getIdentifiableClass(),configuration.getModelClass());
	}
	
	protected <MODEL, REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL, REPORT> configuration) {
		reportBusiness.registerConfiguration(configuration);
		logInfo("Report configuration added. Identifier is {}",configuration.getIdentifier());
	}
	
}