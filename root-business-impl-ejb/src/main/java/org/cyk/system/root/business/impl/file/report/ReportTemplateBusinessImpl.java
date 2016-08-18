package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;

public class ReportTemplateBusinessImpl extends AbstractEnumerationBusinessImpl<ReportTemplate, ReportTemplateDao> implements ReportTemplateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ReportTemplateBusinessImpl(ReportTemplateDao dao) {
		super(dao); 
	}   
	
}
