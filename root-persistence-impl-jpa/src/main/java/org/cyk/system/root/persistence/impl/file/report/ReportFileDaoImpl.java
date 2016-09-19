package org.cyk.system.root.persistence.impl.file.report;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.persistence.api.file.report.ReportFileDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ReportFileDaoImpl extends AbstractTypedDao<ReportFile> implements ReportFileDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByFile,readByTemplateByFile;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByFile, _select().where(ReportFile.FIELD_FILE));
		registerNamedQuery(readByTemplateByFile, _select().where(ReportFile.FIELD_REPORT_TEMPLATE).and(ReportFile.FIELD_FILE));
	}
	
	@Override
	public Collection<ReportFile> readByFile(File file) {
		return namedQuery(readByFile).parameter(ReportFile.FIELD_FILE, file).resultMany();
	}

	@Override
	public ReportFile readByTemplateByFile(ReportTemplate reportTemplate,File file) {
		return namedQuery(readByTemplateByFile).ignoreThrowable(NoResultException.class).parameter(ReportFile.FIELD_REPORT_TEMPLATE, reportTemplate)
				.parameter(ReportFile.FIELD_FILE, file).resultOne();
	}

}
 