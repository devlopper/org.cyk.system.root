package org.cyk.system.root.business.api.file;

import java.util.Collection;

import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.system.root.model.userinterface.style.Style;

public interface ReportBusiness<STYLE> {
    
	void buildTable(ReportTable<?> aReport,Boolean print);
	
	void build(Report<?> aReport,Boolean print);
	
	void write(java.io.File directory,AbstractReport<?> aReport);
	
	/**/
	
	Collection<Column> findColumns(Class<?> aClass);
	
	STYLE buildStyle(Style style);
	
}
