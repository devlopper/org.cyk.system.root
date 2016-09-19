package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.report.ReportFileBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.persistence.api.file.report.ReportFileDao;

public class ReportFileBusinessImpl extends AbstractTypedBusinessService<ReportFile, ReportFileDao> implements ReportFileBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ReportFileBusinessImpl(ReportFileDao dao) {
		super(dao); 
	}

	@Override
	public Collection<ReportFile> findByFile(File file) {
		return dao.readByFile(file);
	}

	@Override
	public ReportFile findByTemplateByFile(ReportTemplate reportTemplate,File file) {
		return dao.readByTemplateByFile(reportTemplate, file);
	}

}
