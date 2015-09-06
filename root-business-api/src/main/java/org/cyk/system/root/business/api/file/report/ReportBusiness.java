package org.cyk.system.root.business.api.file.report;

import java.util.Collection;

import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;

public interface ReportBusiness {
    
	<MODEL,REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL,REPORT> builder);
	<MODEL,REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL,REPORT> findConfiguration(String identifier);
	
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(ReportBasedOnDynamicBuilderParameters<MODEL> parameters);
	void build(ReportBasedOnTemplateFile<?> aReport,Boolean print);
	
	void write(java.io.File directory,AbstractReport<?> aReport);
	
	Collection<Column> findColumns(Class<?> aClass,ReportBasedOnDynamicBuilderParameters<?> parameters);
		
}
