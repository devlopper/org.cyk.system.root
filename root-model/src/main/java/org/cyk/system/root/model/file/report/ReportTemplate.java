package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;

@Getter @Setter @NoArgsConstructor @Entity
public class ReportTemplate extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -6245201590820068337L;

	@ManyToOne private File template;
	@ManyToOne private File headerImage;
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
	
	
	
}
