package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportTemplateDetails extends AbstractOutputDetails<ReportTemplate> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String template,headerImage,backgroundImage,draftBackgroundImage;
	
	public ReportTemplateDetails(ReportTemplate reportTemplate) {
		super(reportTemplate);
		
	}
	
	/**/
	
	public static final String FIELD_TEMPLATE = "template";
	public static final String FIELD_HEADER_IMAGE = "headerImage";
	public static final String FIELD_BACKGROUND_IMAGE = "backgroundImage";
	public static final String FIELD_DRAFT_BACKGROUND_IMAGE = "draftBackgroundImage";
}