package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileRepresentationTypeBusiness;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;

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
	public ReportTemplate instanciateOne(String[] values) {
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Instanciate", "report template from values "+StringUtils.join(values,Constant.CHARACTER_COMA.toString()));
		ReportTemplate reportTemplate = super.instanciateOne(values);
		SetListener listener = new SetListener.Adapter.Default(values, 10, logMessageBuilder);
		set(reportTemplate, ReportTemplate.FIELD_TEMPLATE,  listener);
		set(reportTemplate, ReportTemplate.FIELD_HEADER_IMAGE, listener);
		set(reportTemplate, ReportTemplate.FIELD_FOOTER_IMAGE, listener);
		set(reportTemplate, ReportTemplate.FIELD_BACKGROUND_IMAGE, listener);
		set(reportTemplate, ReportTemplate.FIELD_DRAFT_BACKGROUND_IMAGE, listener);
		set(reportTemplate, ReportTemplate.FIELD_SIGNER, listener);
		set(reportTemplate, ReportTemplate.FIELD_RESULT_FILE_NAMING_SCRIPT, listener);
		/*
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setTemplate(read(File.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setHeaderImage(read(File.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setFooterImage(read(File.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setBackgroundImage(read(File.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setDraftBackgroundImage(read(File.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setSigner(read(Person.class, value));
		if(StringUtils.isNotBlank(value = values[index++]))
			reportTemplate.setResultFileNamingScript(read(Script.class, value));
		*/
		logMessageBuilder.addParameters("Template Code",reportTemplate.getTemplate().getCode());
		if(reportTemplate.getHeaderImage()!=null)
			logMessageBuilder.addParameters("Header image code",reportTemplate.getHeaderImage().getCode());
		if(reportTemplate.getFooterImage()!=null)
			logMessageBuilder.addParameters("Footer image code",reportTemplate.getFooterImage().getCode());
		if(reportTemplate.getBackgroundImage()!=null)
			logMessageBuilder.addParameters("Background image code",reportTemplate.getBackgroundImage().getCode());
		if(reportTemplate.getDraftBackgroundImage()!=null)
			logMessageBuilder.addParameters("Draft background image code",reportTemplate.getDraftBackgroundImage().getCode());
		if(reportTemplate.getSigner()!=null)
			logMessageBuilder.addParameters("Signer code",reportTemplate.getSigner().getCode());
		if(reportTemplate.getResultFileNamingScript()!=null)
			logMessageBuilder.addParameters("result file naming script code",reportTemplate.getResultFileNamingScript().getCode());
		logTrace(logMessageBuilder);
		return reportTemplate;
	}
	
}
