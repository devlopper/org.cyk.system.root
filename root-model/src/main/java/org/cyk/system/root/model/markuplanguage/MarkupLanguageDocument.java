package org.cyk.system.root.model.markuplanguage;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MarkupLanguageDocument implements Serializable {
	
	private static final long serialVersionUID = -6056328070972266554L;

	private String namespace,text;
	private Object rootElement;

}
