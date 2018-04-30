package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ReportTemplate extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -6245201590820068337L;

	@ManyToOne private File template;//TODO must be not null
	@ManyToOne private File headerImage;
	@ManyToOne private File footerImage;
	@ManyToOne private File backgroundImage;
	
	@ManyToOne private File draftBackgroundImage;
	
	@ManyToOne private Person signer;
	@ManyToOne private Script nameScript;
	@ManyToOne private Script resultFileNamingScript;
	@ManyToOne private Script headerScript;
	@ManyToOne private Script footerScript;
	
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
	public static final String FIELD_FOOTER_IMAGE = "footerImage";
	public static final String FIELD_BACKGROUND_IMAGE = "backgroundImage";
	public static final String FIELD_DRAFT_BACKGROUND_IMAGE = "draftBackgroundImage";
	public static final String FIELD_SIGNER = "signer";
	public static final String FIELD_NAME_SCRIPT = "nameScript";
	public static final String FIELD_RESULT_FILE_NAMING_SCRIPT = "resultFileNamingScript";
	public static final String FIELD_HEADER_SCRIPT = "headerScript";
	public static final String FIELD_FOOTER_SCRIPT = "footerScript";
}
