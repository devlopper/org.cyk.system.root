package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;

public class CreateReportFileArgumentsAdapter extends TypedBusiness.CreateReportFileArguments.Builder.Listener.Adapter.Default implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public ReportTemplate getReportTemplate(String code) {
		return inject(ReportTemplateDao.class).read(code);
	}
	
	@Override
	public File getFile(AbstractIdentifiable identifiable, ReportTemplate reportTemplate,Boolean updateExisting) {
		Collection<File> files = inject(FileBusiness.class)
    			.findByRepresentationTypeByIdentifiable(inject(FileRepresentationTypeDao.class).read(reportTemplate.getCode()), identifiable);
		File file = null;
		if(files.isEmpty())
			file = new File();
		else if(Boolean.TRUE.equals(updateExisting)){
			ExceptionUtils.getInstance().exception(files.size() > 1, "too.much.filereport.found.for.update");
			file = files.iterator().next();
		}else
			file = new File();
		return file;
	}
	
}
