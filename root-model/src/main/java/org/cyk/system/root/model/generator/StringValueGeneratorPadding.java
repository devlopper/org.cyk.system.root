package org.cyk.system.root.model.generator;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable
public class StringValueGeneratorPadding extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 2700928054823690772L;

	private String pattern;
	private Long lenght;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public static final String FIELD_PATTERN = "pattern";
	public static final String FIELD_LENGHT = "lenght";

}
