package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity
public class ReportTemplate extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -6245201590820068337L;

	@ManyToOne private File template;
	@ManyToOne private File headerImage;
	@ManyToOne private File backgroundImage;
	@Column(nullable=false) private Boolean usable = Boolean.TRUE;
	
	public ReportTemplate(String code, File template,File headerImage,File backgroundImage) {
		super(code, code, null, null);
		this.template = template;
		this.headerImage = headerImage;
		this.backgroundImage = backgroundImage;
	}
	
	
	
}
