package org.cyk.system.root.model.generator;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable
public class StringValueGeneratorPadding extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable {

	private static final long serialVersionUID = 2700928054823690772L;

	private String prefix,pattern,suffix;
	private Long length;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public static final String FIELD_PREFIX = "prefix";
	public static final String FIELD_PATTERN = "pattern";
	public static final String FIELD_SUFFIX = "suffix";
	public static final String FIELD_LENGTH = "length";

}
