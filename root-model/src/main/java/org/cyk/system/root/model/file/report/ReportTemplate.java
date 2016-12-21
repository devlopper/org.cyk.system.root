package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ReportTemplate extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -6245201590820068337L;

	@ManyToOne private File template;
	@ManyToOne private File headerImage;
	@ManyToOne private File footerImage;
	@ManyToOne private File backgroundImage;
	
	@ManyToOne private File draftBackgroundImage;
	
	public ReportTemplate(String code,String name,Boolean male, File template,File headerImage,File backgroundImage,File draftBackgroundImage) {
		super(code, name, null, null);
		getGlobalIdentifierCreateIfNull().setMale(male);
		this.template = template;
		this.headerImage = headerImage;
		this.backgroundImage = backgroundImage;
		this.draftBackgroundImage = draftBackgroundImage;
	}
	
	public static final String FIELD_TEMPLATE = "template";
	public static final String FIELD_HEADER_IMAGE = "headerImage";
	public static final String FIELD_BACKGROUND_IMAGE = "backgroundImage";
	public static final String FIELD_DRAFT_BACKGROUND_IMAGE = "draftBackgroundImage";
	
}
