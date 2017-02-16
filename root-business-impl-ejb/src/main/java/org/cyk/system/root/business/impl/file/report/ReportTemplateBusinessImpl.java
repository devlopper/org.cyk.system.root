package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.file.FileRepresentationTypeBusiness;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;

public class ReportTemplateBusinessImpl extends AbstractEnumerationBusinessImpl<ReportTemplate, ReportTemplateDao> implements ReportTemplateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ReportTemplateBusinessImpl(ReportTemplateDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(ReportTemplate reportTemplate, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{reportTemplate.getTemplate()};
		return super.getPropertyValueTokens(reportTemplate, name);
	}
	
	@Override
	protected void beforeCreate(ReportTemplate reportTemplate) {
		super.beforeCreate(reportTemplate);
		createIfNotIdentified(reportTemplate.getTemplate());
	}
	
	@Override
	protected void afterCreate(ReportTemplate reportTemplate) {
		super.afterCreate(reportTemplate);
		FileRepresentationType fileRepresentationType = read(FileRepresentationType.class, reportTemplate.getCode());
		if(fileRepresentationType==null){
			fileRepresentationType = new FileRepresentationType();
			fileRepresentationType.setCode(reportTemplate.getCode());
			fileRepresentationType.setName(reportTemplate.getName());
			inject(FileRepresentationTypeBusiness.class).create(fileRepresentationType);
		}
	}
	
	@Override
	protected ReportTemplate __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<ReportTemplate> listener) {
		ReportTemplate reportTemplate = super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(10),ReportTemplate.FIELD_TEMPLATE);
		set(listener.getSetListener(), ReportTemplate.FIELD_HEADER_IMAGE);
		set(listener.getSetListener(), ReportTemplate.FIELD_FOOTER_IMAGE);
		set(listener.getSetListener(), ReportTemplate.FIELD_BACKGROUND_IMAGE);
		set(listener.getSetListener(), ReportTemplate.FIELD_DRAFT_BACKGROUND_IMAGE);
		set(listener.getSetListener(), ReportTemplate.FIELD_SIGNER);
		set(listener.getSetListener(), ReportTemplate.FIELD_RESULT_FILE_NAMING_SCRIPT);
		set(listener.getSetListener(), ReportTemplate.FIELD_HEADER_SCRIPT);
		set(listener.getSetListener(), ReportTemplate.FIELD_FOOTER_SCRIPT);
		set(listener.getSetListener(), ReportTemplate.FIELD_NAME_SCRIPT);
		return reportTemplate;
	}
	
}
