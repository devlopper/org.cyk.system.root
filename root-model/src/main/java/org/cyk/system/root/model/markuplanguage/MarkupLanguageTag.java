package org.cyk.system.root.model.markuplanguage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MarkupLanguageTag implements Serializable {
	
	private static final long serialVersionUID = -6056328070972266554L;

	private String name,text;
	private Map<String,String> attributes = new HashMap<>();
	
	private MarkupLanguageTag parent;
}
