package org.cyk.system.root.business.api.file.report;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;

public interface ReportFileBusiness extends TypedBusiness<ReportFile> {
    
	Collection<ReportFile> findByFile(File file);
	ReportFile findByTemplateByFile(ReportTemplate reportTemplate,File file);
    
}
