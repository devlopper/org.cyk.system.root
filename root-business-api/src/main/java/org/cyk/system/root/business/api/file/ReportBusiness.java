package org.cyk.system.root.business.api.file;

import java.util.Collection;

import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.system.root.model.userinterface.style.Style;


public interface ReportBusiness<STYLE> {
    
	Collection<Column> findColumns(Class<?> aClass);
	
	void build(Report<?> aReport,Boolean print);
	
	STYLE buildStyle(Style style);
	
}
