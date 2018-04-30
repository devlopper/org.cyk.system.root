package org.cyk.system.root.model.generator;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.programming.Script;

@Getter @Setter @Entity @NoArgsConstructor
public class StringGenerator extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8742355979465919653L;

	@Embedded private StringValueGeneratorConfiguration configuration = new StringValueGeneratorConfiguration();

	@OneToOne @JoinColumn(name=COLUMN_SCRIPT) private Script script;
	
	public static final String FIELD_CONFIGURATION = "configuration";
	public static final String FIELD_SCRIPT = "script";
	
	public static final String COLUMN_SCRIPT = FIELD_SCRIPT;
}
