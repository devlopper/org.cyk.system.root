package org.cyk.system.root.business.api.file;

import java.util.Collection;

import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.system.root.model.userinterface.style.Style;

public interface ReportBusiness<STYLE> {
    
	<MODEL,REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL,REPORT> builder);
	<MODEL,REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL,REPORT> findConfiguration(String identifier);
	
	<MODEL> ReportTable<MODEL> buildTable(Class<MODEL> aClass,Collection<MODEL> datas,String fileExtension,Boolean print);
	<MODEL> ReportTable<MODEL> buildTable(Class<MODEL> aClass,String fileExtension,Boolean print);
	
	void buildTable(ReportTable<?> aReport,Boolean print);
	
	void build(Report<?> aReport,Boolean print);
	
	void write(java.io.File directory,AbstractReport<?> aReport);
	
	/**/
	
	Collection<Column> findColumns(Class<?> aClass);
	
	STYLE buildStyle(Style style);
	
}
