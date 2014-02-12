package org.cyk.system.root.model.language;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor
public class Language extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public Language(String code, String libelle, String description) {
		super(code, libelle, description);
	}
	
	private Collection<LanguageEntry> entries = new LinkedHashSet<>();

}
