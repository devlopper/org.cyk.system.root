package org.cyk.system.root.persistence.api.file.report;

import java.util.Collection;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.persistence.api.TypedDao;

public interface ReportFileDao extends TypedDao<ReportFile> {

	Collection<ReportFile> readByFile(File file);
	ReportFile readByTemplateByFile(ReportTemplate reportTemplate,File file);
	
}
